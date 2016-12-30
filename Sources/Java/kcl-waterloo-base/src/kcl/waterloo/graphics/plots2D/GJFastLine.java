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
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

/**
 * GJFastLine class for fast painting of line plots.
 *
 * This class does not support multiplexing or lines with gaps (NaNs in the
 * data). For best performance use a thin stroke (&lt 1 pixel)
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJFastLine extends GJAbstractPlot {

    public GJFastLine() {
        super();
    }

    public GJFastLine(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface p = GJAbstractPlot.createInstance(new GJFastLine());
        p.getScreenDataArray().clear();
        return p;
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        double[] xposition;
        double[] yposition;

        xposition = getParentGraph().xPositionToPixeli(getXDataValues());
        yposition = getParentGraph().yPositionToPixeli(getYDataValues());

        super.paintPlot(g2);

        Path2D path = new Path2D.Double();
        path.moveTo(xposition[0], yposition[0]);
        for (int idx = 0; idx < xposition.length; idx++) {
            path.lineTo(xposition[idx], yposition[idx]);
        }
        g2.setStroke(getVisualModel().getLineStroke().get(0));
        g2.setPaint(getVisualModel().getLineColor().get(0));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.draw(path);

    }

    @Override
    public boolean isMultiplexible() {
        return false;
    }
}
