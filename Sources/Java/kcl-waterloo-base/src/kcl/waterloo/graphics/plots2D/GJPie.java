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

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;

import kcl.waterloo.graphics.GJAbstractGraph;
import static kcl.waterloo.graphics.plots2D.PieExtra.MODE.DEGREES;
import static kcl.waterloo.graphics.plots2D.PieExtra.MODE.PERCENTAGES;
import static kcl.waterloo.graphics.plots2D.PieExtra.MODE.QUANTITIES;
import static kcl.waterloo.graphics.plots2D.PieExtra.MODE.RADIANS;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.math.geom.Cartesian;
import kcl.waterloo.math.geom.Coordinates;

/**
 * GJPie chart.
 *
 * @author malcolm
 */
public class GJPie extends GJAbstractPlot {

    private boolean newFlag = true;

    public GJPie() {
        super();
    }

    public GJPie(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface instance = GJAbstractPlot.createInstance(new GJPie());
        instance.getDataModel().setExtraObject(new PieExtra());
        instance.setXData(new double[]{0d});
        return instance;
    }

    @Override
    public final void paintPlot(Graphics2D g2) {
        if (getParentGraph() == null || getDataModel() == null) {
            return;
        }

        /**
         * Note these changes will take effect only when the plot is first
         * created. The settings can be edited subsequently
         */
        if (newFlag) {
            newFlag = false;
            getParentGraph().setLeftAxisPainted(false);
            getParentGraph().setRightAxisPainted(false);
            getParentGraph().setTopAxisPainted(false);
            getParentGraph().setBottomAxisPainted(false);
            getParentGraph().setMajorGridPainted(false);
            getParentGraph().setMinorGridPainted(false);
            if (getParentGraph().getGraphContainer() != null) {
                getParentGraph().getGraphContainer().setAspectRatio(1d);
            }
            ((GJAbstractGraph) getParentGraph()).setAxesBounds(-1, -1, 2, 2);
        }

        super.paintPlot(g2);
        getScreenDataArray().clear();

        PieExtra extra = (PieExtra) getDataModel().getExtraObject();
        final double minDim = Math.min(((Component) this.getParentGraph()).getWidth(),
                ((Component) this.getParentGraph()).getHeight());

        final double outerDiameter = extra.getOuterRadius();
        double innerDiameter = extra.getInnerRadius();

        final double[] y = getYData().getRawDataValues();
        final double[] y2 = getYData().getRawDataValues();
        switch (extra.getMode()) {
            case PERCENTAGES:
                ArrayMath.muli(y, 3.6d);
                break;
            case RADIANS:
                ArrayMath.muli(y, 180d / Math.PI);
                break;
            case DEGREES:
                break;
            case QUANTITIES:
            default:
                ArrayMath.divi(y, ArrayMath.sum(y));
                ArrayMath.muli(y, 360d);
        }

        /**
         * Pinning the pie chart to a point on the axis allows it to be moved
         * with the axes.
         */
        double X = this.getParentGraph().xPositionToPixel(-1);
        double Y = this.getParentGraph().yPositionToPixel(1);

        /**
         * Create the center circle if required.
         */
        double xyTranslate = (minDim * outerDiameter - minDim * innerDiameter) / 2d;
        Area center = new Area(new Arc2D.Double(X + xyTranslate, Y + xyTranslate,
                minDim * innerDiameter,
                minDim * innerDiameter,
                0d, 360d, Arc2D.OPEN));

        /**
         * Fills each segment.
         */
        double sum = 0;
        double explodeExtent = extra.getExplodeExtent();
        for (int k = 0; k < y.length; k++) {
            Arc2D arc;
            double X2, Y2;
            if (extra.getExplode() != null && extra.getExplode().length > k
                    && extra.getExplode()[k]) {
                /**
                 * Translation for exploded segments
                 */
                double centerTheta = -(sum + y[k] / 2d) * (Math.PI / 180d);
                Cartesian a = Coordinates.polarToCartesian(centerTheta, outerDiameter + explodeExtent);
                Cartesian b = Coordinates.polarToCartesian(centerTheta, outerDiameter);
                double xoffset = minDim * (a.getX()[0] - b.getX()[0]);
                double yoffset = minDim * (a.getY()[0] - b.getY()[0]);
                X2 = X + xoffset;
                Y2 = Y + yoffset;
            } else {
                X2 = X;
                Y2 = Y;
            }
            arc = new Arc2D.Double(X2, Y2,
                    minDim * outerDiameter,
                    minDim * outerDiameter,
                    sum, y[k], Arc2D.PIE);
            g2.setPaint(getFill().get(k));
            g2.fill(arc);
            getScreenDataArray().add(arc);
            sum += y[k];
        }

        /**
         * Draws lines for each segment together with fill if "wheel" style.
         */
        for (int k = 0; k < getScreenDataArray().size(); k++) {
            g2.setPaint(getEdgeColor().get(k));
            g2.setStroke(getEdgeStroke().get(k));
            Arc2D arc = (Arc2D) getScreenDataArray().get(k);
            g2.draw(arc);

            if (innerDiameter != 0d) {
                g2.setPaint(Color.WHITE);
                g2.fill(center);
                g2.setPaint(Color.BLACK);
                g2.draw(center);
            }
        }

        /**
         * Add labels.
         */
        if (extra.isLabeled()) {
            FontMetrics metrics = g2.getFontMetrics();
            xyTranslate = (minDim * outerDiameter - minDim * extra.getLabelRadius()) / 2d;
            Arc2D lbl = new Arc2D.Double(X + xyTranslate, Y + xyTranslate,
                    minDim * extra.getLabelRadius(),
                    minDim * extra.getLabelRadius(),
                    0d, 360d, Arc2D.OPEN);
            sum = 0d;
            for (int k = 0; k < getScreenDataArray().size(); k++) {
                Arc2D arc = (Arc2D) getScreenDataArray().get(k);
                double theta = -(sum + y[k] / 2d) * (Math.PI / 180d);
                double xy[] = getXY(arc, lbl, theta);
                String s;
                if (extra.getLabels().size() > 0) {
                    s = extra.getLabels().get(k);
                } else if (extra.getLabelFormat() != null) {
                    s = extra.getLabelFormat().format(y2[k]);
                } else {
                    switch (extra.getMode()) {
                        case PERCENTAGES:
                            s = String.format("%3.1f%%", y2[k]);
                            break;
                        case RADIANS:
                            s = String.format("%3.2f", y2[k]);
                            break;
                        case DEGREES:
                        case QUANTITIES:
                        default:
                            s = String.format("%4.1f", y2[k]);
                    }
                }

                g2.drawString(s, (float) (xy[0] - metrics.stringWidth(s) / 2f), (float) (xy[1] + metrics.getMaxAscent() / 2d));
                sum += y[k];

            }
        }
    }

    private double[] getXY(Arc2D arc0, Arc2D arc1, double theta) {
        double x1 = arc0.getCenterX();
        double y1 = arc0.getCenterY();
        double x2 = arc0.getCenterX() + (Math.cos(theta) * arc1.getHeight());
        double y2 = arc0.getCenterY() + (Math.sin(theta) * arc1.getWidth());
        double horzAngle = (Math.atan2(y2 - y1, x2 - x1) + Math.PI * 2) % (Math.PI * 2);

        double xc = arc1.getCenterX();
        double yc = arc1.getCenterY();
        double r1 = arc1.getWidth() / 2d - arc1.getWidth() / 30d;
        double r2 = arc1.getHeight() / 2d - arc1.getHeight() / 30d;
        double cot = Math.cos(0);
        double sit = Math.sin(0);

        double xl = xc + r1 * Math.cos(horzAngle) * cot - r2 * Math.sin(horzAngle) * sit;
        double yl = yc + r1 * Math.cos(horzAngle) * sit + r2 * Math.sin(horzAngle) * cot;

        return new double[]{xl, yl};
    }
}
