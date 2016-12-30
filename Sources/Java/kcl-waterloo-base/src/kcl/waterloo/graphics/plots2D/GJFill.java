/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.plots2D;

import java.awt.AlphaComposite;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import kcl.waterloo.defaults.Colors;
import kcl.waterloo.marker.GJPathSegmentInfo;
import kcl.waterloo.marker.ShapeUtils;
import kcl.waterloo.plotmodel2D.GJFillableInterface;

/**
 * GJFill class used to set or modify painted area fill for a plot.
 *
 * @author ML
 */
public class GJFill implements GJFillableInterface<Shape, Paint> {

    /**
     * Orientation of the fill.
     *
     * <ul>
     * <li>VERTICAL - fill vertically between the plot and a specified
     * horizontal value.</li>
     * <li>HORIZONTAL - fill horizontally between the plot and a specified
     * vertical value.</li>
     * <li> ARBITRARY - return an area that will either be filled area or
     * combined with an area generated within the plot method (the combination
     * being determined by the mode setting).
     * </li>
     * <ul>
     */
    public enum ORIENTATION {

        HORIZONTAL, VERTICAL, ARBITRARY
    }

    /**
     * <p>
     * Use the arbitrary area (if orientation is ARBITRARY as follows:</p>.
     * <ul>
     * <li>SET - simply paint the area</li>
     * <li>ADD - add the area to the area supplied by the plot and fill the
     * sum.</li>
     * <li>SUBTRACT - subtract the area from the area supplied by the plot and
     * fill the difference</li>.
     * <li>INTERSECT - fill only the area that overlaps with that supplied by
     * the plot</li>.
     * <li>XOR - fill only those areas exclusive to this area or that supplied
     * by the plot but not common to both.</li>.
     */
    public enum MODE {

        SET, ADD, SUBTRACT, INTERSECT, XOR
    }

    /**
     * The plot this instance is associated with
     */
    private GJPlotInterface parentPlot = null;
    /**
     * Orientation of fill for this instance.
     */
    private ORIENTATION orientation = ORIENTATION.VERTICAL;
    /**
     * The reference value to fill towards for VERTICAL and HORIZONTAL fills. If
     * -Inf or +Inf, the low or high axes limit from the parentPlot will be
     * used.
     */
    private double reference = Double.NaN;
    /**
     * When ORIENTATION is ARBITRARY, this is the abitrary area (in axes units).
     */
    private Area arbitraryArea = null;
    /**
     * Mode for this instance.
     */
    private MODE mode = MODE.INTERSECT;
    /**
     * A Paint instance used for filling the area.
     */
    private Paint areaPaint = Colors.getColor("AREAFILL");

    private AlphaComposite fillComposite = null;

    /**
     * Null constructor. Need for serialization/de-serialization. Do not use.
     */
    public GJFill() {
    }

    /**
     * Constructs an instance and associates it with a plot.
     *
     * @param p a GJPlotInterface
     */
    public GJFill(GJPlotInterface p) {
        parentPlot = p;
    }

    /**
     * Constructs a fully usable instance with default VERTICAL fill.
     *
     * @param p the associated plot
     * @param ref the reference level for HORIZONTAL & VERTICAL fills
     */
    public GJFill(GJPlotInterface p, double ref) {
        parentPlot = p;
        reference = ref;
    }

    /**
     * Constructs a usable instance.
     *
     * @param p the associated ploat
     * @param ref the reference level for HORIZONTAL & VERTICAL fills
     * @param or the orientation: HORIZONTAL, VERTICAL or ARBITRARY
     */
    public GJFill(GJPlotInterface p, double ref, ORIENTATION or) {
        parentPlot = p;
        reference = ref;
        orientation = or;
    }

    /**
     * Gets the fillable area for HORIZONTAL & VERTICAL fills
     *
     * @return an Area instance
     */
    @Override
    public Shape getFillable() {
        final Area area0 = new Area();
        double refValue = reference;
        switch (orientation) {
            case VERTICAL:
                if (refValue == Double.NEGATIVE_INFINITY) {
                    refValue = parentPlot.getParentGraph().getYMax();
                } else if (refValue == Double.POSITIVE_INFINITY) {
                    refValue = parentPlot.getParentGraph().getYMin();
                }
                for (GJPlotInterface plot : parentPlot.getNode()) {
                    for (Shape s : plot.getScreenDataArray()) {
                        area0.add(new Area(ShapeUtils.getFromX(parentPlot.getParentGraph(), s, refValue)));
                    }
                }
                break;
            case HORIZONTAL:
                if (refValue == Double.NEGATIVE_INFINITY) {
                    refValue = parentPlot.getParentGraph().getXMax();
                } else if (refValue == Double.POSITIVE_INFINITY) {
                    refValue = parentPlot.getParentGraph().getXMin();
                }
                for (GJPlotInterface plot : parentPlot.getNode()) {
                    for (Shape s : plot.getScreenDataArray()) {
                        area0.add(new Area(ShapeUtils.getFromY(parentPlot.getParentGraph(), s, refValue)));
                    }
                }
        }
        return area0;
    }

    /**
     * Returns the Paint instance to be used for filling.
     *
     * @return a Paint instance
     */
    @Override
    public Paint getAreaPaint() {
        return areaPaint;
    }

    /**
     * Sets the Paint instance.
     *
     * @param paint a Paint instance
     */
    @Override
    public void setAreaPaint(Paint paint) {
        this.areaPaint = paint;
    }

    /**
     * @return the parentPlot
     */
    public GJPlotInterface getParentPlot() {
        return parentPlot;
    }

    /**
     * @param parentPlot the parentPlot to set
     */
    public void setParentPlot(GJPlotInterface parentPlot) {
        this.parentPlot = parentPlot;
    }

    /**
     * @return the reference
     */
    public double getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(double reference) {
        this.reference = reference;
    }

    /**
     * @return the orientation
     */
    public ORIENTATION getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the arbitraryArea in graph units.
     *
     * @return the arbitraryArea
     */
    public Area getArbitraryArea() {
        return arbitraryArea;
    }

    /**
     * Sets the arbitraryArea in graph units.
     *
     * @param arbitraryArea the arbitraryArea to set
     */
    public void setArbitraryArea(Area arbitraryArea) {
        this.arbitraryArea = arbitraryArea;
    }

    /**
     * Returns an ArrayList<GJPathSegmentInfo> for use in XML serialization.
     *
     * @return an ArrayList<GJPathSegmentInfo>
     */
    public final ArrayList<GJPathSegmentInfo> getSegments() {
        if (arbitraryArea != null) {
            ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
            double[] d = new double[6];
            int type;
            for (PathIterator it = arbitraryArea.getPathIterator(null); !it.isDone(); it.next()) {
                type = it.currentSegment(d);
                segments.add(new GJPathSegmentInfo(type, d));
            }
            return segments;
        } else {
            return null;
        }
    }

    /**
     * Reconstructs an arbitraryArea on XML de-serialization using an
     * ArrayList<GJPathSegmentInfo> as input.
     *
     * @param arr the ArrayList<GJPathSegmentInfo>
     */
    public final void setSegments(ArrayList<GJPathSegmentInfo> arr) {
        Path2D path = new Path2D.Double();
        for (GJPathSegmentInfo segment : arr) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    path.moveTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    path.lineTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    path.quadTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    path.curveTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3],
                            segment.getData()[4], segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    path.closePath();
            }
        }
        arbitraryArea = new Area(path);
    }

    /**
     * Gets the mode used when orientation is ARBITRARY. Possible values are
     * SET, ADD, SUBTRACT, INTERSECT, XOR.
     *
     * @return the mode
     */
    public MODE getMode() {
        return mode;
    }

    /**
     * Sets the mode used when orientation is ARBITRARY. Possible values are
     * SET, ADD, SUBTRACT, INTERSECT, XOR.
     *
     * @param mode the mode to set
     */
    public void setMode(MODE mode) {
        this.mode = mode;
    }

    /**
     * Returns the aritraryArea scaled and translated to pixels according to the
     * current axes limits of the parent graph.
     *
     * @return a pixelArea
     */
    public Area getPixelArea() {
        if (arbitraryArea != null) {
            double scx = parentPlot.getParentGraph().xPositionToPixel(1) - parentPlot.getParentGraph().xPositionToPixel(0);
            double scy = parentPlot.getParentGraph().yPositionToPixel(0) - parentPlot.getParentGraph().yPositionToPixel(1);
            AffineTransform af = AffineTransform.getScaleInstance(scx, -scy);
            Area area = (Area) arbitraryArea.clone();
            area.transform(af);
            area.transform(AffineTransform.getTranslateInstance(parentPlot.getParentGraph().xPositionToPixel(0), parentPlot.getParentGraph().yPositionToPixel(0)));

            if (!getOrientation().equals(GJFill.ORIENTATION.ARBITRARY)) {
                if (getMode().equals(GJFill.MODE.SET)) {
                    return area;
                }
            } else {
                final double ymin = parentPlot.getParentGraph().getYMin();
                ArrayList<GJPlotInterface> p2 = parentPlot.getNode();
                final Area area0 = new Area();
                for (GJPlotInterface plot : p2) {
                    for (Shape s : plot.getScreenDataArray()) {
                        area0.add(new Area(ShapeUtils.getFromX(parentPlot.getParentGraph(), s, ymin)));
                    }
                }
                switch (getMode()) {
                    case INTERSECT:
                        area0.intersect(area);
                        break;
                    case ADD:
                        area0.add(area);
                        break;
                    case SUBTRACT:
                        area0.subtract(area);
                        break;
                    case XOR:
                        area0.exclusiveOr(area);
                        break;
                }
                return area0;
            }
        }
        return null;
    }

    /**
     * @return the composite
     */
    @Override
    public AlphaComposite getFillComposite() {
        return fillComposite;
    }

    /**
     * @param composite the composite to set
     */
    @Override
    public void setFillComposite(AlphaComposite composite) {
        this.fillComposite = composite;
    }

    @Override
    public float getFillAlpha() {
        if (fillComposite != null) {
            return fillComposite.getAlpha();
        } else {
            return 1f;
        }
    }

    @Override
    public void setFillAlpha(float alpha) {
        if (fillComposite != null) {
            fillComposite = fillComposite.derive(alpha);
        } else {
            fillComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        }
    }
}
