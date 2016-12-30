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
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class BasePanel extends GradientPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private SpringLayout springLayout;

    /**
     * Create the panel.
     */
    public BasePanel() {
        setPreferredSize(new Dimension(1000, 15));
        JLabel lblProjectWaterlooScientific = new JLabel("Waterloo Scientific Graphics: Author: Malcolm Lidierth, Wolfson Centre for Age-Related Diseases");
        lblProjectWaterlooScientific.setFont(new Font("SansSerif", Font.PLAIN, 8));
        lblProjectWaterlooScientific.setForeground(Color.BLACK);
        add(lblProjectWaterlooScientific);
        revalidate();
    }

}
