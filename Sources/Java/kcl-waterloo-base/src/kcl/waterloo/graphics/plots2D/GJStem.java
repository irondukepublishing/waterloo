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
public final class GJStem extends GJAbstractPlot {

    public GJStem() {
        super();
    }

    public GJStem(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJStem());
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        super.paintPlot(g2);

        final double[] xposition = getParentGraph().xPositionToPixeli(getXDataValues());
        final double[] yposition = getParentGraph().yPositionToPixeli(getYDataValues());
        double baseline = getParentGraph().yPositionToPixel(0);

        getScreenDataArray().clear();

        for (int idx = 0; idx < xposition.length; idx++) {
            Path2D path = new Path2D.Double();
            if (!Double.isNaN(xposition[idx]) && !Double.isNaN(yposition[idx])
                    && !Double.isInfinite(xposition[idx]) && !Double.isInfinite(yposition[idx])) {
                path.moveTo(xposition[idx], baseline);
                path.lineTo(xposition[idx], yposition[idx]);
            }
            g2.setStroke(getVisualModel().getLineStroke().get(idx));
            g2.setPaint(getVisualModel().getLineColor().get(idx));
            g2.draw(path);
            getScreenDataArray().add(path);
        }

    }
}
