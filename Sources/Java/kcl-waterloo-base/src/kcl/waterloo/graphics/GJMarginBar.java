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
package kcl.waterloo.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJMarginBar extends GJBasicPanel {

    private int orientation;
    private GJAbstractGraphContainer parentContainer;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    public GJMarginBar() {
        add(panel1);
        add(panel2);
    }

    public GJMarginBar(int orientation, GJAbstractGraphContainer container) {
        setBackground(Color.BLUE);
        setLayout(new SpringLayout());
        setOrientation(orientation);
        parentContainer = container;
        add(panel1);
        add(panel2);
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
    public final void setOrientation(int orientation) {
        switch (orientation) {
            case SwingConstants.HORIZONTAL:
                this.orientation = orientation;
                panel1.setPreferredSize(new Dimension(7, 32767));
                panel2.setPreferredSize(new Dimension(7, 32767));
                break;
            case SwingConstants.VERTICAL:
                this.orientation = orientation;
                panel1.setPreferredSize(new Dimension(32767, 7));
                panel2.setPreferredSize(new Dimension(32767, 7));
                break;
            default:
                break;
        }
    }

    /**
     * @return the parentContainer
     */
    public GJAbstractGraphContainer getParentContainer() {
        return parentContainer;
    }

    /**
     * @param parentContainer the parentContainer to set
     */
    public final void setParentContainer(GJAbstractGraphContainer parentContainer) {
        this.parentContainer = parentContainer;
    }

    @Override
    public void doLayout() {
        if (getLayout() instanceof SpringLayout) {
            Rectangle r = ((Component) getParentContainer().getView()).getBounds();
            switch (orientation) {
                case SwingConstants.HORIZONTAL:
                    ((SpringLayout) getLayout()).putConstraint(SpringLayout.WEST, panel1, r.x, SpringLayout.WEST, this);
                    ((SpringLayout) getLayout()).putConstraint(SpringLayout.WEST, panel2, r.x + r.width - 7, SpringLayout.WEST, this);
                    break;
                case SwingConstants.VERTICAL:
                    ((SpringLayout) getLayout()).putConstraint(SpringLayout.NORTH, panel1, r.y, SpringLayout.NORTH, this);
                    ((SpringLayout) getLayout()).putConstraint(SpringLayout.SOUTH, panel2, r.y + r.height - 7, SpringLayout.SOUTH, this);
                default:
                    break;
            }
        }
        revalidate();
    }
}
