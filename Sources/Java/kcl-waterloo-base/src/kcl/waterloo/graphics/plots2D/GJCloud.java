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
import java.awt.geom.Path2D;
import kcl.waterloo.math.geom.Cartesian;
import kcl.waterloo.math.geom.Coordinates;

/**
 *
 * GJCloud provides an alternative to the GJScatter plot when drawing large
 * numbers of points.
 *
 * Markers are not rendered - just a short dash. GJCloud respects the Fill color
 * setting - which sets the color of the dash - and allows multiplexing of the
 * colors. Otherwise all marker and line settings are ignored.
 *
 * This provides for extremely fast rendering.
 *
 * Use GJCloud in preference to GJScatter when there are many thousands of data
 * points to display. How many will depend on your system/hardware: for some
 * Macs, GJScatter can give slow performance with as few as 5000 points. On
 * Windows systems, 10000-20000 points can usually be handled OK.
 *
 */
public class GJCloud extends GJAbstractPlot {

    public GJCloud() {
        super();
    }

    public GJCloud(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJCloud());
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

        Path2D path;

        getScreenDataArray().clear();

        int N = getMultiplexLength();

        for (int idx = 0; idx < N; idx++) {
            path = new Path2D.Double();
            for (int i = idx; i < xposition.length; i += N) {
                if (!Double.isNaN(xposition[i]) && !Double.isNaN(yposition[i])
                        && !Double.isInfinite(xposition[i]) && !Double.isInfinite(yposition[i])) {
                    path.moveTo(xposition[i] - 0.1, yposition[i] - 0.1);
                    path.lineTo(xposition[i] + 0.1, yposition[i] + 0.1);
                }
            }

            g2.setPaint(getVisualModel().getFill().get(idx));
            g2.draw(path);
            getScreenDataArray().add(path);
        }

    }
}
