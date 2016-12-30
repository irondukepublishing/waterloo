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
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJLine extends GJAbstractPlot {

    public GJLine() {
        super();
    }

    public GJLine(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJLine());
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

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

        super.paintPlot(g2);
        getScreenDataArray().clear();

        for (int idx = 0; idx < getMultiplexLength(); idx += 1) {
            Path2D path = new Path2D.Double();

            //Skip over any NaNs
            int idx2;
            idx2 = idx;
            while (Double.isNaN(xposition[idx2]) || Double.isNaN(yposition[idx2])) {
                idx2 += getMultiplexLength();
            }

            //First point...
            path.moveTo(xposition[idx2], yposition[idx2]);

            boolean newSection = false;
            int N = getMultiplexLength();
            /* ... and create series path from there*/
            for (int i = idx2; i < xposition.length; i += N) {
                if (!Double.isNaN(xposition[i]) && !Double.isNaN(yposition[i])
                        && !Double.isInfinite(xposition[i]) && !Double.isInfinite(yposition[i])) {
                    if (newSection) {
                        path.moveTo(xposition[i], yposition[i]);
                    } else {
                        path.lineTo(xposition[i], yposition[i]);
                    }
                    newSection = false;
                } else {
                    newSection = true;
                }
            }
            getScreenDataArray().add(path);

            g2.setStroke(getVisualModel().getLineStroke().get(idx));
            g2.setPaint(getVisualModel().getLineColor().get(idx));
            g2.draw(path);
        }

    }
}
