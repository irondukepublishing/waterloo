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
package kcl.waterloo.docking;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;


/**
 * A {@code DockContainer} acts as host to {@code Dockable} components.
 *
 * {@code DockContainer} supports a vertical or horizontal layout. Components
 * added to a {@code DockContainer} will be wrapped in a {@code Dockable}
 * instance.
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
@Deprecated
public class DockContainer extends JPanel {

    private int orientation = SwingConstants.VERTICAL;

    /**
     * Create a {@code DockContainer} with default vertical layout.
     */
    public DockContainer() {
        this(SwingConstants.VERTICAL);
    }

    /**
     * Create a {@code DockContainer} with the specified layout either
     * SwingConstants.HORIZONTAL or SwingConstants.VERTICAL.
     *
     * @param or the layout orientation
     */
    public DockContainer(int or) {
        orientation = or;
        if (orientation == SwingConstants.HORIZONTAL) {
            setLayout(new HorizontalLayout());
        } else {
            setLayout(new VerticalLayout());
        }
    }

    /**
     * Adds a component to {@code DockContainer}.
     *
     * If the specified component is a {@code Dockable} instance, it will be
     * added and returned.
     *
     * If the specified component is not a {@code Dockable}, it will be wrapped
     * in a {@code Dockable} which will then be added to the
     * {@code DockContainer}. In this case, the component will be returned, not
     * the {@code DockContainer}
     *
     * @param c the component to add
     * @return
     */
    @Override
    public Component add(Component c) {
        if (c instanceof Dockable) {
            return super.add(c);
        } else {
            return add(new Dockable(c));
        }
    }

    /**
     * @return the orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
