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
package kcl.waterloo.swing;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JPanel;

import kcl.waterloo.swing.layout.SpringRelativeLayout;

/**
 * <p>
 * <strong>This class, and others in the {@code kcl.waterloo.swing package}, are
 * under development. They are likely to change in the future.</strong></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GCGlassLayer extends JPanel {

    SpringRelativeLayout layout = new SpringRelativeLayout();

    public GCGlassLayer() {
        super(false);
        setOpaque(false);
        setEnabled(false);
        setLayout(layout);
    }

    public Component add(Component comp, double x, double y, String alignX, String alignY) {
        super.add(comp);
        layout.putAnchor(comp, x, y, alignX, alignY);
        return comp;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
