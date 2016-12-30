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
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import kcl.waterloo.math.geom.Cartesian;
import kcl.waterloo.math.geom.Coordinates;
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
public class GJScatter extends GJAbstractPlot {

    private static final long serialVersionUID = 1L;

    public GJScatter() {
        super();
    }

    public GJScatter(GJAbstractPlot p) {
        super(p);
    }

//    private GJScatter(){
//    }
    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJScatter());
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        super.paintPlot(g2);

        double[] xposition;
        double[] yposition;
        if (this instanceof GJPolarPlotInterface
                && ((PolarExtra) getDataModel().getExtraObject()).getDataMode() == PolarExtra.DATAMODE.POLAR) {
            Cartesian cart = Coordinates.polarToCartesian(getXDataValues(), getYDataValues());
            xposition = getParentGraph().xPositionToPixeli(cart.getX());
            yposition = getParentGraph().yPositionToPixeli(cart.getY());
        } else {
            xposition = getParentGraph().xPositionToPixeli(getXDataValues());
            yposition = getParentGraph().yPositionToPixeli(getYDataValues());
        }

        if (xposition == null || yposition == null) {
            return;
        }

        Path2D path;

        getScreenDataArray().clear();

        int N = getMultiplexLength();
        for (int idx = 0; idx < N; idx++) {
            GJMarker thisMarker = getVisualModel().getMarkerArray().get(idx);
            path = new Path2D.Double();
            for (int i = idx; i < xposition.length; i += N) {
                if (!Double.isNaN(xposition[i]) && !Double.isNaN(yposition[i])
                        && !Double.isInfinite(xposition[i]) && !Double.isInfinite(yposition[i])) {
                    path.append(thisMarker.getPathIterator(
                            AffineTransform.getTranslateInstance(xposition[i], yposition[i])),
                            false);
                }
            }

            if (getVisualModel().getFill() != null) {
                g2.setPaint(getVisualModel().getFill().get(idx));
                g2.fill(path);
            }

            g2.setStroke(getVisualModel().getEdgeStroke().get(idx));
            g2.setPaint(getVisualModel().getEdgeColor().get(idx));
            g2.draw(path);
            getScreenDataArray().add(path);
        }

    }
}
