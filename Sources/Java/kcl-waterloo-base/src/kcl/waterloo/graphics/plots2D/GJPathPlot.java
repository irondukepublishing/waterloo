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
import kcl.waterloo.marker.GJMarker;

/**
 * GJPathPlot allows arbitrary Path2D objects to be painted as a plot.
 *
 * These should be specified in pixels. To specify them in axes units, create a
 * scatter plot with the arbitrary shapes instead.
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJPathPlot extends GJAbstractPlot {

    protected GJPathPlot() {
    }

    @Override
    public void paintPlot(Graphics2D g2) {
        super.paintPlot(g2);

        Path2D path;

        getScreenDataArray().clear();

        int N = getMultiplexLength();
        for (int idx = 0; idx < N; idx += 1) {
            GJMarker thisMarker = getVisualModel().getMarkerArray().get(idx);
            path = new Path2D.Double();
            path.append(thisMarker.getPath(), false);
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

    /**
     * @return true
     */
    @Override
    public boolean isMultiplexible() {
        return false;
    }
}
