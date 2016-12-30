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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import kcl.waterloo.graphics.GJBasicPanel;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GCInfoBar extends GJBasicPanel {

    private JLabel left = new JLabel();
    private GJBasicPanel centerleft = new GJBasicPanel(true);
    private JLabel centerright = new JLabel();
    private JLabel right = new JLabel();

    public GCInfoBar() {
        super(true);
        setPreferredSize(new Dimension(32767, 20));
        setBorder(LineBorder.createGrayLineBorder());
        setLayout(new GridLayout(1, 4));
        add(left);
        add(centerleft);
        add(centerright);
        add(right);
        right.setHorizontalAlignment(JTextField.LEFT);
        centerright.setHorizontalAlignment(JTextField.LEFT);
        right.setFont(new Font("Sans Serif", Font.PLAIN, 10));
        centerright.setFont(new Font("Sans Serif", Font.PLAIN, 10));
    }

    /**
     * @return the left
     */
    public JLabel getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(JLabel left) {
        this.left = left;
    }

    /**
     * @return the centerleft
     */
    public GJBasicPanel getCenterleft() {
        return centerleft;
    }

    /**
     * @param centerleft the centerleft to set
     */
    public void setCenterleft(GJBasicPanel centerleft) {
        this.centerleft = centerleft;
    }

    /**
     * @return the centerright
     */
    public JLabel getCenterright() {
        return centerright;
    }

    /**
     * @param centerright the centerright to set
     */
    public void setCenterright(JLabel centerright) {
        this.centerright = centerright;
    }

    /**
     * @return the right
     */
    public JLabel getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(JLabel right) {
        this.right = right;
    }
}
