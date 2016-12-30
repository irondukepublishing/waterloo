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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class FontAspectCombo extends JPanel {

    private FontSelector fontSelector;

    /**
     * Create the panel.
     */
    public FontAspectCombo() {
        setPreferredSize(new Dimension(264, 128));
        setLayout(new BorderLayout(0, 0));
        fontSelector = new FontSelector();
        fontSelector.setPreferredSize(new Dimension(32767, 100));
        add(fontSelector, BorderLayout.CENTER);

    }

    /**
     * @return the fontSelector
     */
    public FontSelector getFontSelector() {
        return fontSelector;
    }

}
