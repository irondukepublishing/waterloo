 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.plots2D.contour;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import kcl.waterloo.graphics.data.GJXYSeries;
import kcl.waterloo.graphics.plots2D.GJContour;
import kcl.waterloo.marker.ShapeUtils;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.math.ArrayUtils;

/**
 * Class for representing contour data.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ContourExtra {

    /**
     * {@code LinkedHashMap} containing the raw data. The keySet contains the
     * level for each contour as a <@code Double>. The values are represented as
     * a {@code GJXYSeries} for each level representing the x-series and
     * y-series data.
     */
    private TreeMap<Double, ArrayList<GJXYSeries>> levelData = null;
    /**
     * {@code ContourSet} of objects representing the contours.
     */
    private final ContourSet contourSet = new ContourSet();
    /**
     * {@code Path2D.Double} objects representing the contours. Each element
     * represents the contour line for a single level. These are stored in a
     * sortable TreeMap such that contour lines for each level are collected
     * together and can be given the same stroke/color in the GJContour
     * paintPlot method. The Path2D objects here are clones of those in
     * contourSet with all contours at the same level appended together.
     */
    private final TreeMap<Double, Path2D> compoundLines = new TreeMap<Double, Path2D>();
    /**
     * Contours as SortedContours return sorted in the order in which they
     * should be rendered.
     */
    private final ArrayList<SortedContour> sortedSet = new ArrayList<SortedContour>();

    BasicStroke zeroStroke = null;
    private Paint positiveLineColor = null;
    private Paint negativeLineColor = null;

    public ContourExtra() {
    }

    public ArrayList<SortedContour> sort() {
        if (sortedSet.isEmpty()) {
            ArrayList<Contour> sortedList = contourSet.sort();
            Area accumulator = new Area();
            for (int k = 0; k < sortedList.size(); k++) {
                Contour contour = sortedList.get(k);
                int index = getColorIndex(contour.getLevel());
                if (k > 0 && k < sortedList.size() - 1) {
                    if (ShapeUtils.contains(sortedList.get(k - 1).getContourAsArea(), contour.getContourAsArea())) {
                        if (sortedList.get(k + 1).getLevel() > contour.getLevel()) {
                            index = getColorIndex(sortedList.get(k + 1).getLevel());
                        }
                    }
                }
                SortedContour sortedContour = new SortedContour(contour, index);
                if (k > 0 && !ShapeUtils.contains(accumulator, contour.getContourAsArea())
                        && ShapeUtils.intersects(accumulator, contour.getContourAsArea())) {
                    Area area2 = contour.getContourAsArea();
                    area2.subtract(accumulator);
                    sortedContour.setFillArea(area2);
                }
                sortedSet.add(sortedContour);
                accumulator.add(contour.getContourAsArea());
            }
        }
        return sortedSet;
        //}
    }

    /**
     * Returns the index into the fill & edgeColor arrays for a level.
     *
     * @param key the level to find the index for
     * @return integer index
     */
    public final int getColorIndex(Double key) {
        int k = 0;
        for (Double level : levelData.keySet()) {
            if (key.equals(level)) {
                return k;
            } else {
                k++;
            }
        }
        return -1;
    }

    /**
     * Returns the levels for the contours.
     *
     * @return the levels as a Double[]
     */
    public Double[] getLevels() {
        Double[] levels = new Double[levelData.size()];
        int k = 0;
        for (Double level : levelData.keySet()) {
            levels[k] = level;
            k++;
        }
        return levels;
    }

    /**
     *
     * @return the level data as a LinkedHashMap<Double, ArrayList<GJXYSeries>>
     */
    public final TreeMap<Double, ArrayList<GJXYSeries>> getLevelData() {
        return levelData;
    }

    /**
     * Sets the levels for this instance
     *
     * @param levels a TreeMap<Double, ArrayList<GJXYSeries>>
     */
    public final void setLevelData(TreeMap<Double, ArrayList<GJXYSeries>> levels) {
        levelData = levels;
        generateContours();
    }

    public final Area getCommonPath() {
        return contourSet.getCommonArea();
    }

    public final ArrayList<Contour> getLevel(Double level) {
        return contourSet.get(level);
    }

    public final int size() {
        return levelData.size();
    }

    public final Set<Double> keySet() {
        return contourSet.keySet();
    }

    public final TreeMap<Double, Path2D> getCompounds() {
        return compoundLines;
    }

    public final Path2D getCompound(Double level) {
        return (Path2D) compoundLines.get(level).clone();
    }

    public final void generateCompounds() {
        for (Double level : contourSet.keySet()) {
            Path2D path = new Path2D.Double();
            for (Contour c : contourSet.get(level)) {
                path.append(c.getPath(), false);
            }
            compoundLines.put(level, path);
        }
    }

    /**
     * Generates the contour lines from the data stored in {@code levelData}.
     *
     */
    public void generateContours() {
        contourSet.clear();
        for (Double thisLevel : getLevelData().keySet()) {
            ArrayList<GJXYSeries> collection = getLevelData().get(thisLevel);
            for (GJXYSeries series : collection) {
                ArrayList<Double> x = series.getX();
                ArrayList<Double> y = series.getY();
                if (x.size() > 2) {
                    Double[] x0 = new Double[x.size()];
                    x0 = x.toArray(x0);
                    Double[] y0 = new Double[y.size()];
                    y0 = y.toArray(y0);
                    Contour contour = new Contour(x0, y0, thisLevel);
                    if (!contourSet.containsKey(thisLevel)) {
                        contourSet.put(thisLevel, new ArrayList<Contour>());
                    }
                    contourSet.get(thisLevel).add(contour);
                }
            }
        }
        generateCompounds();
        sortedSet.clear();
        contourSet.setCommonArea();
    }

    /**
     * Generates the contour lines from the data stored in {@code levelData}.
     *
     *
     * @param plot the plot to generate contours for
     */
    public final void generateContours(final GJContour plot) {
        if (plot.getParentGraph() == null) {
            return;
        }
        double xoffset;
        if (plot.getXData().getDimension() <= 0) {
            xoffset = 0;
        } else {
            xoffset = plot.getXData().getRawDataValues()[0];
        }
        double yoffset;
        if (plot.getYData().getDimension() <= 0) {
            yoffset = 0;
        } else {
            yoffset = plot.getYData().getRawDataValues()[0];
        }
        if (getLevelData().keySet().size() > 0) {
            contourSet.clear();
            for (Double thisLevel : getLevelData().keySet()) {
                ArrayList<GJXYSeries> collection = getLevelData().get(thisLevel);
                for (GJXYSeries series : collection) {
                    double[] x0 = ArrayUtils.asDouble(series.getX());
                    ArrayMath.addi(x0, xoffset);
                    x0 = plot.getParentGraph().getXTransform().getData(x0);
                    if (x0.length > 2) {
                        double[] y0 = ArrayUtils.asDouble(series.getY());
                        ArrayMath.addi(y0, yoffset);
                        y0 = plot.getParentGraph().getYTransform().getData(y0);
                        Contour contour = new Contour(x0, y0, thisLevel);
                        if (!contourSet.containsKey(thisLevel)) {
                            contourSet.put(thisLevel, new ArrayList<Contour>());
                        }
                        contourSet.get(thisLevel).add(contour);
                    }
                }
            }
        }
        generateCompounds();
        contourSet.setCommonArea();
        sortedSet.clear();
    }

    public static ContourExtra createInstance() {
        ContourExtra contours = new ContourExtra();
        contours.levelData = new TreeMap<Double, ArrayList<GJXYSeries>>();
        return contours;
    }

    public void addContour(ArrayList<ArrayList<Double>> data, double level) {
        addContour(new GJXYSeries(data), level);
    }

    public void addContour(GJXYSeries data, double level) {
        if (getLevelData().get(level) == null) {
            getLevelData().put(level, new ArrayList<GJXYSeries>());
        }
        getLevelData().get(level).add(data);

    }

    /**
     * Accepts a MATLAB-style contour matrix on input and returns a Waterloo
     * ContourExtra object.
     *
     * If a filled contour might be required, use the MATLAB {@code contourf}
     * function to generate the matrix.
     *
     * @param C MATLAB-style contour matrix
     * @return Waterloo contour object.
     */
    public static ContourExtra createFromMatrix(double[][] C) {
        ContourExtra contours = new ContourExtra();
        contours.levelData = new TreeMap<Double, ArrayList<GJXYSeries>>();
        int k = 0;
        double thisLevel;
        int thisN;
        double[][] data;
        while (k < C[0].length) {
            thisLevel = C[0][k];
            thisN = (int) C[1][k];
            if (thisN > 2) {
                data = new double[2][thisN];
                for (int n = 1; n <= thisN; n++) {
                    data[0][n - 1] = C[0][k + n];
                    data[1][n - 1] = C[1][k + n];
                }
                if (contours.getLevelData().get(thisLevel) == null) {
                    contours.getLevelData().put(thisLevel, new ArrayList<GJXYSeries>());
                }
                contours.getLevelData().get(thisLevel).add(new GJXYSeries(data[0], data[1]));
            }
            k = k + thisN + 1;
        }
        return contours;
    }

    /**
     * @return the sortedSet
     */
    public ArrayList<SortedContour> getSortedSet() {
        return sort();
    }

    public void setZeroStroke(BasicStroke s) {
        zeroStroke = s;
    }

    public BasicStroke getZeroStroke() {
        return zeroStroke;
    }

    /**
     * @return the positiveLineColor
     */
    public Paint getPositiveLineColor() {
        return positiveLineColor;
    }

    /**
     * @param positiveLineColor the positiveLineColor to set
     */
    public void setPositiveLineColor(Paint positiveLineColor) {
        this.positiveLineColor = positiveLineColor;
    }

    /**
     * @return the negativeLineColor
     */
    public Paint getNegativeLineColor() {
        return negativeLineColor;
    }

    /**
     * @param negativeLineColor the negativeLineColor to set
     */
    public void setNegativeLineColor(Paint negativeLineColor) {
        this.negativeLineColor = negativeLineColor;
    }

    /**
     *
     */
    public static final class SortedContour {

        private Contour contour = null;
        private int colorIndex = -1;
        private Area fillArea = null;

        private SortedContour(Contour contour, int index) {
            this.contour = contour;
            this.colorIndex = index;
            setFillArea(contour.getContourAsArea());
        }

        /**
         * @return the contour
         */
        public final Contour getContour() {
            return contour;
        }

        /**
         * @param contour the contour to set
         */
        public final void setContour(Contour contour) {
            this.contour = contour;
        }

        /**
         * @return the color
         */
        public final int getColorIndex() {
            return colorIndex;
        }

        /**
         * @param color the color to set
         */
        public final void setColorIndex(int index) {
            colorIndex = index;
        }

        /**
         * @return the fillArea
         */
        public final Area getFillArea() {
            return new Area(fillArea);//(Area) fillArea.clone();
        }

        /**
         * @param fillArea the fillArea to set
         */
        public final void setFillArea(Area fillArea) {
            this.fillArea = new Area(fillArea);//(Area) fillArea.clone();
        }
    }
}
