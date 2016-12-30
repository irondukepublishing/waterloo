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

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static kcl.waterloo.graphics.plots2D.BarExtra.MODE.GROUPED;
import static kcl.waterloo.graphics.plots2D.BarExtra.MODE.STACKED;
import static kcl.waterloo.graphics.plots2D.BarExtra.ORIENTATION.HORIZONTAL;
import static kcl.waterloo.graphics.plots2D.BarExtra.ORIENTATION.VERTICAL;
import kcl.waterloo.math.ArrayMath;

/**
 * Abstract class that is extended by box-style concrete classes such as GJBar.
 *
 * Subclasses should implement the painting mechanism.
 *
 * @author ML
 */
public abstract class GJBox extends GJAbstractPlot {

    public GJBox() {
        super();
    }

    public GJBox(GJAbstractPlot p) {
        super(p);
    }

    /**
     * Returns an ArrayList of rectangles as Path2D instances.
     *
     * The position of each rectangle is set by the values in the XData property
     * and the height by the YData values.
     *
     * @param x the x data
     * @param y the y data
     * @return an ArrayList<Path2D>, each element of which describes a rectangle
     */
    public final ArrayList<Shape> createBoxes(final double[] x, final double[] y) {

        ArrayList<Shape> array = new ArrayList<Shape>();

        double[] right = new double[x.length];
        double[] upper = new double[x.length];
        double[] left = new double[x.length];
        double[] lower = new double[x.length];

        BarExtra extra = (BarExtra) getDataModel().getExtraObject();

        if (extra == null) {
            return null;
        }

        double baseValue = extra.getBaseValue();

        switch (extra.getOrientation()) {
            case HORIZONTAL:
                left = ArrayMath.min(y, baseValue);
                right = ArrayMath.max(y, baseValue);
                break;
            case VERTICAL:
            default:
                lower = ArrayMath.min(y, baseValue);
                upper = ArrayMath.max(y, baseValue);
        }

        final double[] dXl = new double[x.length];
        final double[] dXr = new double[x.length];
        switch (extra.getMode()) {
            case HIST:
                dXl[0] = 0.5d;
                dXr[0] = (x[1] - x[0]) / 2d;
                for (int k = 1; k < x.length - 1; k++) {
                    dXl[k] = (x[k] - x[k - 1]) / 2d;
                    dXr[k] = (x[k + 1] - x[k]) / 2d;
                }
                dXl[x.length - 1] = dXr[x.length - 2];
                dXr[x.length - 1] = 0.5d;
                break;
            case HISTC:
                dXl[0] = 0d;
                dXr[0] = x[1] - x[0];
                for (int k = 1; k < x.length - 1; k++) {
                    dXl[k] = 0d;
                    dXr[k] = x[k + 1] - x[k];
                }
                dXl[x.length - 1] = 0d;
                dXr[x.length - 1] = 1d;
                break;
            case GROUPED:
            case STACKED:
            default:
                ArrayMath.filli(dXl, extra.getBarWidth() / 2d);
                ArrayMath.filli(dXr, extra.getBarWidth() / 2d);
        }

        int N = getMultiplexLength();

        // Deal with stacking
        if (N > 1 && ((BarExtra) getDataModel().getExtraObject()).getMode() == BarExtra.MODE.STACKED) {
            double[] posLimit = new double[x.length / N + 1];
            double[] negLimit = new double[x.length / N + 1];
            ArrayMath.filli(posLimit, 0d);
            ArrayMath.filli(negLimit, 0d);
            for (int idx = 0; idx < x.length; idx++) {
                int ref = (int) Math.floor((double) idx / (double) N);
                switch (extra.getOrientation()) {
                    case HORIZONTAL:
                        if (y[idx] >= 0) {
                            left[idx] += posLimit[ref];
                            right[idx] += posLimit[ref];
                        } else {
                            left[idx] += negLimit[ref];
                            right[idx] += negLimit[ref];
                        }
                        break;
                    case VERTICAL:
                        if (y[idx] >= 0) {
                            lower[idx] += posLimit[ref];
                            upper[idx] += posLimit[ref];
                        } else {
                            lower[idx] += negLimit[ref];
                            upper[idx] += negLimit[ref];
                        }
                }
                if (y[idx] >= 0) {
                    posLimit[ref] += y[idx];
                } else {
                    negLimit[ref] += y[idx];
                }
            }
        }

        switch (extra.getOrientation()) {
            case HORIZONTAL:
                left = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(left));
                right = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(right));
                if (N > 1 && ((BarExtra) getDataModel().getExtraObject()).getMode() == BarExtra.MODE.STACKED) {
                    // Align stacked bars
                    for (int k = 0; k < x.length; k++) {
                        int ref = (int) Math.floor((double) k / (double) N) * N;
                        lower[k] = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(x[ref] - dXl[k]));
                        upper[k] = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(x[ref] + dXr[k]));
                    }
                } else {
                    lower = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(ArrayMath.sub(x, dXl)));
                    upper = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(ArrayMath.add(x, dXr)));
                }
                break;
            case VERTICAL:
            default:
                lower = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(lower));
                upper = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(upper));
                if (N > 1 && ((BarExtra) getDataModel().getExtraObject()).getMode() == BarExtra.MODE.STACKED) {
                    // Align stacked bars
                    for (int k = 0; k < x.length; k++) {
                        int ref = (int) Math.floor((double) k / (double) N) * N;
                        left[k] = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData(x[ref] - dXl[k]));
                        right[k] = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData(x[ref] + dXr[k]));
                    }
                } else {
                    left = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(ArrayMath.sub(x, dXl)));
                    right = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(ArrayMath.add(x, dXr)));
                }
        }

        for (int i = 0; i < x.length; i++) {
            if (((Double.isNaN(left[i])
                    || Double.isNaN(right[i]))
                    || Double.isNaN(upper[i]))
                    || Double.isNaN(lower[i])) {
                array.add(null);
            } else {
                array.add(new Rectangle2D.Double(left[i], upper[i],
                        right[i] - left[i], lower[i] - upper[i]));
            }
        }

        return array;
    }

    @Override
    public final Rectangle2D getDataRange() {
        if (this.getXDataValues() != null && this.getYDataValues() != null) {
            double[] x, y;
            if (((BarExtra) getDataModel().getExtraObject()).getOrientation() == BarExtra.ORIENTATION.VERTICAL) {
                x = ArrayMath.minmax(this.getXDataValues());
                y = ArrayMath.minmax(this.getYDataValues());
            } else {
                y = ArrayMath.minmax(this.getXDataValues());
                x = ArrayMath.minmax(this.getYDataValues());
            }
            Rectangle2D r = new Rectangle.Double(x[0], y[0], x[1] - x[0], y[1] - y[0]);
            Rectangle2D r2;
            for (GJPlotInterface p : getPlotList()) {
                r2 = p.getDataRange();
                r = r.createUnion(r2);
            }
            return r;
        } else {
            return new Rectangle.Double(0, 0, 0, 0);
        }
    }
}
