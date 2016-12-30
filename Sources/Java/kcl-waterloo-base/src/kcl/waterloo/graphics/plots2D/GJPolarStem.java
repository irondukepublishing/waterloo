 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2013-
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
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.math.geom.Cartesian;
import kcl.waterloo.math.geom.Coordinates;

/**
 * GJPolarBar chart.
 *
 * @author malcolm
 */
public class GJPolarStem extends GJAbstractPolarPlot {

    final AffineTransform af = new AffineTransform();

    public GJPolarStem() {
        super();
    }

    public GJPolarStem(GJAbstractPlot p) {
        super(p);
    }

//    private boolean newFlag = true;
    public static GJPlotInterface createInstance() {
        GJPlotInterface instance = GJAbstractPlot.createInstance(new GJPolarStem());
        instance.getDataModel().setExtraObject(new PolarExtra());
        return instance;
    }

    @Override
    public final void paintPlot(Graphics2D g2) {
        if (getParentGraph() == null || getDataModel() == null) {
            return;
        }

        super.paintPlot(g2);
        getScreenDataArray().clear();

        /**
         * Pinning the pie chart to a point on the axis allows it to be moved
         * with the axes.
         */
        final double X = this.getParentGraph().xPositionToPixel(0);
        final double Y = this.getParentGraph().yPositionToPixel(0);

        double scx = getParentGraph().getPixelWidth();
        double scy = getParentGraph().getPixelHeight();

        double[] dX;

        double[] dY;

        if (((PolarExtra) getDataModel().getExtraObject()).getDataMode() == PolarExtra.DATAMODE.CARTESIAN) {
            dX = ArrayMath.divi(getXData().getRawDataValues(), scx);
            dY = ArrayMath.divi(getYData().getRawDataValues(), scy);
        } else {
            dX = getXData().getRawDataValues();
            dY = getYData().getRawDataValues();
            Cartesian cart = Coordinates.polarToCartesian(dX, dY);
            dX = ArrayMath.divi(cart.getX(), scx);
            dY = ArrayMath.divi(cart.getY(), scy);
        }
        /**
         * Draws lines for each segment.
         */
        for (int k = 0; k < dX.length; k++) {
            Path2D p = new Path2D.Double();
            g2.setPaint(getLineColor().get(k));
            g2.setStroke(getLineStroke().get(k));
            p.moveTo(X, Y);
            p.lineTo(X + dX[k], Y + dY[k]);
            g2.draw(p);

            double xsz, ysz;

            if (getVisualModel().getDynamicMarkerSize().size() == 0) {
                xsz = Math.hypot(dX[k], dY[k]) / 20d;
                ysz = xsz;
            } else {
                xsz = getVisualModel().getDynamicMarkerSize().get(k).getWidth();
                ysz = getVisualModel().getDynamicMarkerSize().get(k).getHeight();
            }

            Shape arrowhead = GJMarker.makeArrow(X + dX[k], Y + dY[k], xsz, ysz, getVisualModel().getFill() != null);
            af.setToRotation(Math.atan2(dY[k], dX[k]), X + dX[k], Y + dY[k]);
            arrowhead = af.createTransformedShape(arrowhead);

            if (getVisualModel().getFill() != null) {
                g2.setPaint(getVisualModel().getFill().get(k));
                g2.fill(arrowhead);
            }
            g2.setPaint(getVisualModel().getLineColor().get(k));
            g2.draw(arrowhead);
            p.append(arrowhead, false);
            getScreenDataArray().add(p);
        }
    }

//
}
