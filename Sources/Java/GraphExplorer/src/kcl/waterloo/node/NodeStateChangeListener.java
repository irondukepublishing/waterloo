 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.node;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
class NodeStateChangeListener implements ChangeListener {

    VisualNode node;

    NodeStateChangeListener(VisualNode node) {
        this.node = node;
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        Method m;
        try {
            m = ce.getSource().getClass().getMethod("getSelectedComponent");
        } catch (NoSuchMethodException ex) {
            return;
        } catch (SecurityException ex) {
            return;
        }
        Component c = null;
        if (m != null) {
            try {
                c = (Component) m.invoke(ce.getSource());
            } catch (IllegalAccessException ex) {
                return;
            } catch (IllegalArgumentException ex) {
                return;
            } catch (InvocationTargetException ex) {
                return;
            }
        }
        if (c != null) {
            VisualNode thisNode = (VisualNode) node.findNodeForValue(c);
            thisNode.findTreePathToRoot();
            for (JTree tree : node.getTreeLookup()) {
                tree.setSelectionPath(thisNode.findTreePathToRoot());
            }
        }
    }
}
