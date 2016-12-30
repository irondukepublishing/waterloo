 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics;

import java.awt.Component;
import java.awt.Container;
import kcl.waterloo.swing.layout.GraphUnitLayout;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GraphLayout extends GraphUnitLayout {

    public GraphLayout() {
    }

    @Override
    public final void layoutContainer(Container parent) {
        revalidateAdded(parent);
        for (Component c : parent.getComponents()) {
            if (c instanceof GJCustomGraphLayoutInterface) {
                ((GJCustomGraphLayoutInterface) c).setConstraints();
            }
        }
        super.layoutContainer(parent);
    }

    @Override
    public final void doSuperLayoutContainer(Container c) {
        super.layoutContainer(c);
    }


    /*
     * Recalculate the constraints for added components
     */
    private void revalidateAdded(Container container) {
        GJGraphInterface gr = (GJGraphInterface) container;
        if (gr.getGraphContainer() != null) {
            GJAbstractGraphContainer grc = gr.getGraphContainer();
            for (Component c : getComponentMap().keySet()) {
                super.revalidateComponent(c, grc, gr, false);
            }
        }
    }
}
