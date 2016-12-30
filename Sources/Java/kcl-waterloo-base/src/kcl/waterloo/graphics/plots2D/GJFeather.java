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
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import kcl.waterloo.marker.GJMarker;

/**
 *
 * Cyclic properties: Marker Fill EdgeStroke EdgeColor
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJFeather extends GJAbstractPlot {

    public GJFeather() {
        super();
    }

    public GJFeather(GJAbstractPlot p) {
        super(p);
    }

    //private static final long serialVersionUID = 1L;
    final AffineTransform af = new AffineTransform();

//    private GJFeather() {
//    }
    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJFeather());
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        if (getXData().getDimension() == 0) {
            return;
        }

        final double[] dX = getXData().getRawDataValues();
        final double[] dY = getYData().getRawDataValues();
        final double Ybase = this.getParentGraph().getOriginY();
        final double Yopix = getParentGraph().yPositionToPixel(Ybase);
        double Xopix, dXpix, dYpix;

        super.paintPlot(g2);
        Path2D path;
        getScreenDataArray().clear();
        double xsz, ysz;
        Shape arrowhead;

        for (int i = 0; i < dX.length; i++) {
            if (!Double.isNaN(dX[i]) && !Double.isInfinite(dX[i])) {
                path = new Path2D.Double();
                Xopix = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData((double) i + 1));
                dXpix = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData((double) i + 1 + dX[i]));
                dYpix = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(
                        getParentGraph().getYTransform().getInverse(Ybase) + dY[i]));

                if (!Double.isNaN(dYpix) && !Double.isInfinite(dYpix)) {

                    path.moveTo(Xopix, Yopix);
                    path.lineTo(dXpix, dYpix);

                    if (getVisualModel().getDynamicMarkerSize().size() == 0) {
                        xsz = 7;
                        ysz = 7;
                    } else {
                        xsz = getVisualModel().getDynamicMarkerSize().get(i).getWidth();
                        ysz = getVisualModel().getDynamicMarkerSize().get(i).getHeight();
                    }

                    g2.setStroke(getVisualModel().getLineStroke().get(i));
                    g2.setPaint(getVisualModel().getLineColor().get(i));
                    g2.draw(path);

                    arrowhead = GJMarker.makeArrow(dXpix, dYpix, xsz, ysz, getVisualModel().getFill() != null);
                    af.setToRotation(Math.atan2(dYpix - Yopix, dXpix - Xopix), dXpix, dYpix);
                    arrowhead = af.createTransformedShape(arrowhead);

                    if (getVisualModel().getFill() != null) {
                        g2.setPaint(getVisualModel().getFill().get(i));
                        g2.fill(arrowhead);
                    }
                    g2.setPaint(getVisualModel().getLineColor().get(i));
                    g2.draw(arrowhead);

                    path.append(arrowhead, false);
                    getScreenDataArray().add(path);
                }
            }
        }
    }
}
