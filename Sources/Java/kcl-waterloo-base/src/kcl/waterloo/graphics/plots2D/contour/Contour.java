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

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import kcl.waterloo.graphics.data.GJXYSeries;
import kcl.waterloo.marker.ShapeUtils;
import kcl.waterloo.math.ArrayUtils;

/**
 *
 * @author ML
 */
public class Contour {

    private Double level = Double.NEGATIVE_INFINITY;
    private Path2D path = null;
    private Area contourAsArea = null;
    private GJXYSeries data;
    private double area = Double.NaN;
    private ContourLabel label;
    private long ignoredDataPoints = 0;

    Contour(double[] x, double[] y, Double level) {
        this(ArrayUtils.boxed(x), ArrayUtils.boxed(y), level);
    }

    Contour(Double[] x, Double[] y, Double level) {
        data = new GJXYSeries(x, y);
        this.level = level;
        path = new Path2D.Double();
        int k;
        for (k = 0; k < x.length; k++) {
            if (!x[k].isNaN() && !y[k].isNaN() && !x[k].isInfinite() && !y[k].isInfinite()) {
                break;
            } else {
                ignoredDataPoints++;
            }
        }
        path.moveTo(x[k], y[k]);
        for (k = k + 1; k < x.length; k++) {
            if (!x[k].isNaN() && !y[k].isNaN() && !x[k].isInfinite() && !y[k].isInfinite()) {
                path.lineTo(x[k], y[k]);
            } else {
                ignoredDataPoints++;
            }
        }
        contourAsArea = new Area(path);
        double dy = y[Math.min(3, y.length - 1)] - y[0];
        double dx = x[Math.min(3, x.length - 1)] - x[0];
        double theta = -Math.atan2(dy, dx);
        label = new ContourLabel(x[0], y[0], theta);
        area = ShapeUtils.getArea(path);
    }

    public final ArrayList<Double> getX() {
        return data.getX();
    }

    public final ArrayList<Double> getY() {
        return data.getY();
    }

    public final double getTheta() {
        return label.getTheta();
    }

    public final double getArea() {
        if (Double.isNaN(area)) {
            area = ShapeUtils.getArea(path);
        }
        return area;
    }

    public final double getBoundingArea() {
        return path.getBounds2D().getWidth() * path.getBounds2D().getHeight();
    }

    /**
     * @return the level
     */
    public final Double getLevel() {
        return level;
    }

    public final Path2D getLabelPath(AffineTransform af1, AffineTransform af2) {
        return getLabelPath(af1, af2, getX().get(0).doubleValue(), getY().get(0).doubleValue());
    }

    public final Path2D getLabelPath(AffineTransform af1, AffineTransform af2, double x, double y) {
        Path2D p = new Path2D.Double();
        p.transform(af1);
        p.transform(af2);
        p.moveTo(x, y);
        return p;
    }

    /**
     * @return the path
     */
    public final Path2D getPath() {
        return (Path2D) path.clone();
    }

    /**
     * @return the label
     */
    public final ContourLabel getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public final void setLabel(ContourLabel label) {
        this.label = label;
    }

    /**
     * @return the contourAsArea
     */
    public final Area getContourAsArea() {
        return (Area) contourAsArea.clone();
    }

    public final void setContourAsArea(Area area) {
        contourAsArea = (Area) area.clone();
    }

    public final boolean isClosed() {
        return ShapeUtils.isClosed(path);
    }

    /**
     * @return the ignoredDataPoints
     */
    public long getIgnoredDataPoints() {
        return ignoredDataPoints;
    }
}
