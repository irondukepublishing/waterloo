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

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import kcl.waterloo.widget.GJColorComboBox;
import javax.swing.JLabel;

public class FontSelector extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private GJColorComboBox colorCombo;

    /**
     * Create the panel.
     */
    public FontSelector() {
        init();
    }

    private void init() {
        setName("");
        setBorder(new TitledBorder(null, "Font", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(null);

        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(6, 22, 145, 27);
        comboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        comboBox.setName("$Font");
        add(comboBox);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(6, 54, 70, 27);
        comboBox_1.setName("$FontSize");
        add(comboBox_1);

        JToggleButton tglbtnNewToggleButton = new JToggleButton("");
        tglbtnNewToggleButton.setBounds(82, 55, 25, 25);
        tglbtnNewToggleButton.setIcon(new ImageIcon(FontSelector.class.getResource("/kcl/waterloo/gui/images/text_bold.png")));
        tglbtnNewToggleButton.setName("$Bold");
        tglbtnNewToggleButton.setFont(new Font("Garamond", Font.BOLD, 12));
        tglbtnNewToggleButton.setPreferredSize(new Dimension(25, 25));
        add(tglbtnNewToggleButton);

        JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("");
        tglbtnNewToggleButton_1.setBounds(113, 55, 25, 25);
        tglbtnNewToggleButton_1.setIcon(new ImageIcon(FontSelector.class.getResource("/kcl/waterloo/gui/images/text_italic.png")));
        tglbtnNewToggleButton_1.setName("$Italic");
        tglbtnNewToggleButton_1.setFont(new Font("Garamond", Font.ITALIC, 12));
        tglbtnNewToggleButton_1.setPreferredSize(new Dimension(25, 25));
        add(tglbtnNewToggleButton_1);

        setColorCombo(new GJColorComboBox());
        getColorCombo().setBounds(6, 100, 145, 27);
        add(getColorCombo());

        JLabel lblColor = new JLabel("Color");
        lblColor.setBounds(10, 83, 61, 16);
        add(lblColor);

    }

    /**
     * @return the colorCombo
     */
    public GJColorComboBox getColorCombo() {
        return colorCombo;
    }

    /**
     * @param colorCombo the colorCombo to set
     */
    public void setColorCombo(GJColorComboBox colorCombo) {
        this.colorCombo = colorCombo;
        colorCombo.setName("$ContainerForeground");
    }
}
