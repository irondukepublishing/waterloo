 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
package kcl.waterloo.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GradientPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private GradientPaint Gradient = new GradientPaint(0, 0, new Color(25, 50, 120),
            500, 100, Color.white, true);

    /**
     * Create the panel.
     */
    public GradientPanel() {
        super();
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setPaint(Gradient);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

}
