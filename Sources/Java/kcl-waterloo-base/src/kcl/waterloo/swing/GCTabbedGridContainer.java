/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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
package kcl.waterloo.swing;

import java.awt.Component;
import java.awt.Container;
import java.util.LinkedHashMap;
import javax.swing.JTabbedPane;

/**
 *
 * @author Malcolm Lidierth
 */
public class GCTabbedGridContainer extends JTabbedPane implements GCGridContainerInterface {

    public GCTabbedGridContainer() {
        super();
    }

    public GCFrame createFrame() {
        GCFrame f = new GCFrame();
        f.setGraphicsContainer(this);
        return f;
    }

    public Component add(Component c, double row, double column)
            throws UnsupportedOperationException {
        return add(c, row, column, 100d, 100d);
    }

    public Component add(Component c, double row, double column, double width, double height)
            throws UnsupportedOperationException {
        return add(c, row, column, width, height, indexOfComponent(getSelectedComponent()));
    }

    public Component add(Component c, double row, double column, double width, double height, int tab) {
        if (getComponentAt(tab) instanceof GCGridContainer) {
            return ((GCGridContainerInterface) getComponentAt(tab)).add(c, row, column, width, height);
        } else {
            throw (new UnsupportedOperationException("Target component is not a GCGridContainerInterface"));
        }
    }

    public LinkedHashMap<Integer, GCGridContainer> getGridMap() {
        LinkedHashMap<Integer, GCGridContainer> gridList = new LinkedHashMap<Integer, GCGridContainer>();
        for (int k = 0; k < getTabCount(); k++) {
            if (getComponentAt(k) instanceof GCGridContainer) {
                gridList.put(k, (GCGridContainer) getComponentAt(k));
            }
        }
        return gridList;
    }

    public Container getSelected() {
        return (Container) getSelectedComponent();
    }

    public GCInfoBar getInfoBar() {
        if (getSelectedComponent() instanceof GCGridContainerInterface) {
            return ((GCGridContainerInterface) getSelectedComponent()).getInfoBar();
        } else {
            return null;
        }
    }
}
