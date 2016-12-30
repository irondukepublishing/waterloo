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
package kcl.waterloo.swing.layout;

import java.awt.Container;
import javax.swing.SpringLayout;
import kcl.waterloo.swing.GCGrid;
import kcl.waterloo.swing.GCGridElement;

/**
 * {@code SpringGridLayout} provides a simple grid layout that resizes
 * automatically when the host container is revalidated.
 *
 * Create a {@code SpringGridLayout} supplying the number of rows and columns
 * for the grid. Components will be displayed in the grid in row-major order,
 * i.e. left-to-right, wrapping to a new row as required.
 *
 * Components are drawn in the layout in the order maintained in the parent
 * component: i.e. the component returned by getComponent(0) will be drawn at
 * 0,0; that returned getComponent(1) at 0,1 (assuming the number of columns
 * >=2. Components at indices >= m*n will not be visible.
 *
 * Components can span more than 1 element of the grid in either or both the
 * x-direction and y-direction. To set the span to a value other than 1, use
 * setSpan, setSpanX or setSpanY.
 *
 * Elements of the grid that are to the right and/or below a component with a
 * span>1 will share screen-space with that component. They should have their
 * Visible property set to false (unless they are intended to be drawn above the
 * component).
 *
 * The dimensions of the grid can be altered by calling setGrid after it is
 * initiated and populated.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class SpringGridLayout extends SpringLayout {

    public SpringGridLayout() {
    }

    /**
     * Layouts out the parent container according to the current layout.
     * Constraints will be updated when {@code layoutContainer} is called -
     * typically from its Component ancestor.
     *
     * @param parent the container managed by this layout manager.
     */
    @Override
    public final void layoutContainer(Container parent) {

        double[] dim = ((GCGrid) parent).getDimensions();
        double rowCount = dim[0];
        double columnCount = dim[1];

        double columnWidth = parent.getWidth() / columnCount;
        double rowHeight = parent.getHeight() / rowCount;

        int n = parent.getComponentCount();
        for (int k = 0; k < n; k++) {
            if (parent.getComponent(k) instanceof GCGridElement) {

                GCGridElement thisComp = (GCGridElement) parent.getComponent(k);

                double thisRow = thisComp.getProperties().getRow();
                double thisColumn = thisComp.getProperties().getColumn();
                double spanX = thisComp.getProperties().getColumnWidth();
                double spanY = thisComp.getProperties().getRowHeight();

                this.putConstraint(SpringLayout.WEST,
                        thisComp,
                        (int) (thisColumn * columnWidth),
                        SpringLayout.WEST,
                        parent);
                this.putConstraint(SpringLayout.EAST,
                        thisComp,
                        (int) ((thisColumn * columnWidth) + columnWidth * spanX),
                        SpringLayout.WEST,
                        parent);
                this.putConstraint(SpringLayout.NORTH,
                        thisComp,
                        (int) (thisRow * rowHeight),
                        SpringLayout.NORTH,
                        parent);
                this.putConstraint(SpringLayout.SOUTH,
                        thisComp,
                        (int) ((thisRow * rowHeight) + rowHeight * spanY),
                        SpringLayout.NORTH,
                        parent);
            }

        }
        super.layoutContainer(parent);
    }
}
