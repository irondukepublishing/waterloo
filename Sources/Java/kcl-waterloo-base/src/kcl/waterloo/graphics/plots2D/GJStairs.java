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
 * Cyclic properties: None
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJStairs extends GJAbstractPlot {

    private static final long serialVersionUID = 1L;

    public GJStairs() {
        super();
    }

    public GJStairs(GJAbstractPlot p) {
        super(p);
    }

//    private GJStairs() {
//    }
    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJStairs());
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        final double[] xposition = getParentGraph().xPositionToPixeli(getXDataValues());
        final double[] yposition = getParentGraph().yPositionToPixeli(getYDataValues());

        super.paintPlot(g2);
        getScreenDataArray().clear();

        int N = getMultiplexLength();

        Path2D path;
        int idx2;
        for (int idx = 0; idx < N; idx += 1) {
            path = new Path2D.Double();
            /*
             * Loop to first non-NaN in this series...
             */
            idx2 = idx;
            while (Double.isNaN(yposition[idx2])) {
                idx2 += N;
            }
            path.moveTo(xposition[idx2], yposition[idx2]);
            for (int i = idx2; i < xposition.length - 1; i += N) {
                if (!Double.isNaN(xposition[i]) && !Double.isNaN(yposition[i])
                        && !Double.isInfinite(xposition[i]) && !Double.isInfinite(yposition[i])) {
                    path.lineTo(xposition[i + 1], yposition[i]);
                    path.lineTo(xposition[i + 1], yposition[i + 1]);
                }
            }
            if (!Double.isNaN(xposition[xposition.length - 1]) && !Double.isNaN(yposition[yposition.length - 1])
                    && !Double.isInfinite(xposition[xposition.length - 1]) && !Double.isInfinite(yposition[yposition.length - 1])) {
                path.lineTo(xposition[xposition.length - 1], yposition[yposition.length - 1]);
            }
            g2.setPaint(getVisualModel().getLineColor().get(idx));
            g2.setStroke(getVisualModel().getLineStroke().get(idx));
            g2.draw(path);
            getScreenDataArray().add(path);
        }

    }
}
