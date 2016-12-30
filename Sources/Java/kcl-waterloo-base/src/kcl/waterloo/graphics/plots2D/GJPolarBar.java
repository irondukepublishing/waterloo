 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import kcl.waterloo.math.ArrayMath;

/**
 * GJPolarBar chart.
 *
 * @author malcolm
 */
public class GJPolarBar extends GJAbstractPolarPlot {

    public GJPolarBar() {
        super();
    }

    public GJPolarBar(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface instance = GJAbstractPlot.createInstance(new GJPolarBar());
        PolarExtra extra = new PolarExtra();
        instance.getDataModel().setExtraObject(extra);
        instance.setXData(new double[]{0d});
        return instance;
    }

    @Override
    public final void paintPlot(Graphics2D g2) {
        if (getParentGraph() == null || getDataModel() == null) {
            return;
        }

        super.paintPlot(g2);
        getScreenDataArray().clear();

        final double[] y = getYData().getRawDataValues();

//        if (((PolarExtra) getDataModel().getExtraObject()).getDisplayMode()
//                == PolarExtra.DISPLAYMODE.POLAR_SQRT) {
        ArrayMath.sqrti(y);
//        }

        /**
         * Pinning the pie chart to a point on the axis allows it to be moved
         * with the axes.
         */
        final double X = this.getParentGraph().xPositionToPixel(0);
        final double Y = this.getParentGraph().yPositionToPixel(0);

        double scx = getParentGraph().getPixelWidth();
        double scy = getParentGraph().getPixelHeight();

        /**
         * Fills each segment.
         */
        double sum = 0d;
        final double arcWidth = 360d / y.length;
        for (int k = 0; k < y.length; k++) {
            Arc2D arc;
            final double radius = y[k];
            arc = new Arc2D.Double(X - radius / scx,
                    Y - radius / scy,
                    2 * radius / scx,
                    2 * radius / scy,
                    sum, arcWidth, Arc2D.PIE);
            g2.setPaint(getFill().get(k));
            g2.fill(arc);
            getScreenDataArray().add(arc);
            sum += arcWidth;
        }

        /**
         * Draws lines for each segment.
         */
        for (int k = 0; k < getScreenDataArray().size(); k++) {
            g2.setPaint(getEdgeColor().get(k));
            g2.setStroke(getEdgeStroke().get(k));
            Arc2D arc = (Arc2D) getScreenDataArray().get(k);
            g2.draw(arc);
        }

//        /**
//         * Add labels.
//         */
//        if (extra.isLabeled()) {
//            FontMetrics metrics = g2.getFontMetrics();
//            double xyTranslate = (minDim * outerDiameter - minDim * extra.getLabelRadius()) / 2d;
//            Arc2D lbl = new Arc2D.Double(X + xyTranslate, Y + xyTranslate,
//                    minDim * extra.getLabelRadius(),
//                    minDim * extra.getLabelRadius(),
//                    0d, 360d, Arc2D.OPEN);
//            sum = 0d;
//            for (int k = 0; k < getScreenDataArray().size(); k++) {
//                Arc2D arc = (Arc2D) getScreenDataArray().get(k);
//                double theta = -(sum + y[k] / 2d) * (Math.PI / 180d);
//                double xy[] = getXY(arc, lbl, theta);
//                String s;
//                if (extra.getLabels().size() > 0) {
//                    s = extra.getLabels().get(k);
//                } else if (extra.getLabelFormat() != null) {
//                    s = extra.getLabelFormat().format(y[k]);
//                } else {
//                    switch (extra.getMode()) {
//                        case PERCENTAGES:
//                            s = String.format("%3.1f/%", y[k]);
//                            break;
//                        case QUANTITIES:
//                        default:
//                            s = String.format("%4.1f", y[k]);
//                    }
//                }
//
//                g2.drawString(s, (float) (xy[0] - metrics.stringWidth(s) / 2f), (float) (xy[1] + metrics.getMaxAscent() / 2d));
//                sum += arcWidth;
//
//            }
//        }
    }

//    private double[] getXY(Arc2D arc0, Arc2D arc1, double theta) {
//        double x1 = arc0.getCenterX();
//        double y1 = arc0.getCenterY();
//        double x2 = arc0.getCenterX() + (Math.cos(theta) * arc1.getHeight());
//        double y2 = arc0.getCenterY() + (Math.sin(theta) * arc1.getWidth());
//        double horzAngle = (Math.atan2(y2 - y1, x2 - x1) + Math.PI * 2) % (Math.PI * 2);
//
//        double xc = arc1.getCenterX();
//        double yc = arc1.getCenterY();
//        double r1 = arc1.getWidth() / 2d - arc1.getWidth() / 30d;
//        double r2 = arc1.getHeight() / 2d - arc1.getHeight() / 30d;
//        double cot = Math.cos(0);
//        double sit = Math.sin(0);
//
//        double xl = xc + r1 * Math.cos(horzAngle) * cot - r2 * Math.sin(horzAngle) * sit;
//        double yl = yc + r1 * Math.cos(horzAngle) * sit + r2 * Math.sin(horzAngle) * cot;
//
//        return new double[]{xl, yl};
//    }
    @Override
    public Rectangle2D getDataRange() {
        if (this.getXDataValues() != null && this.getYDataValues() != null) {
            double[] y = ArrayMath.minmax(this.getYDataValues());
//            if (((PolarExtra) getDataModel().getExtraObject()).getDisplayMode()
//                    == PolarExtra.DISPLAYMODE.POLAR_SQRT) {
            y = ArrayMath.sqrti(y);
//            }
            //double radius = Math.max(y[0], y[1]) * Math.sqrt(2) / 2d;
            //Rectangle2D r = new Rectangle.Double(-radius, -radius, 2 * radius, 2 * radius);
            Rectangle2D r = new Rectangle.Double(ArrayMath.min(y),
                    ArrayMath.min(y), ArrayMath.max(y), ArrayMath.max(y));
            Rectangle2D r2;
            for (GJPlotInterface p : getPlotList()) {
                r2 = p.getDataRange();
                r = r.createUnion(r2);
            }
            return r;
        } else {
            return new Rectangle.Double(-1, -1, 2, 2);
        }
    }
}
