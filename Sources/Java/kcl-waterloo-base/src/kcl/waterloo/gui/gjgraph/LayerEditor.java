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
package kcl.waterloo.gui.gjgraph;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import kcl.waterloo.widget.GJColorComboBox;
import org.jdesktop.swingx.VerticalLayout;

public class LayerEditor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    AxisEditor XEditor = new AxisEditor("X-Axis");
    AxisEditor YEditor = new AxisEditor("Y-Axis");
    private GJColorComboBox gridCombo;
    private GJColorComboBox backGroundCombo;
    private GJColorComboBox axisCombo;

    /**
     * Create the panel.
     */
    /**
     *
     */
    /**
     *
     */
    public LayerEditor() {
        setPreferredSize(new Dimension(550, 308));

        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.SOUTH, YEditor, 115, SpringLayout.SOUTH, XEditor);
        springLayout.putConstraint(SpringLayout.SOUTH, XEditor, 110, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, YEditor, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, YEditor, -100, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.WEST, XEditor, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, XEditor, -100, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.NORTH, YEditor, 5, SpringLayout.SOUTH, XEditor);

        setLayout(springLayout);

        JPanel hintsPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.SOUTH, hintsPanel, 30, SpringLayout.SOUTH, YEditor);
        springLayout.putConstraint(SpringLayout.WEST, hintsPanel, -100, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.NORTH, hintsPanel, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, hintsPanel, -5, SpringLayout.EAST, this);
        hintsPanel.setBorder(new TitledBorder(null, "Hints", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        add(hintsPanel);
        SpringLayout sl_panel_1 = new SpringLayout();
        hintsPanel.setLayout(sl_panel_1);

        JToggleButton chckbxMajor = new JToggleButton();
        chckbxMajor.setName("$MajorGrid");
        chckbxMajor.setIcon(new ImageIcon(LayerEditor.class.getResource("/kcl/waterloo/gui/images/major_grid_add.png")));
        chckbxMajor.setSelectedIcon(new ImageIcon(LayerEditor.class.getResource("/kcl/waterloo/gui/images/major_grid_remove.png")));
        chckbxMajor.setToolTipText("Major Grid");
        sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxMajor, -12, SpringLayout.NORTH, hintsPanel);
        chckbxMajor.setPreferredSize(new Dimension(24, 24));
        hintsPanel.add(chckbxMajor);

        JToggleButton chckbxMinor = new JToggleButton();
        chckbxMinor.setName("$MinorGrid");
        chckbxMinor.setToolTipText("Minor Grid");
        chckbxMinor.setSelectedIcon(new ImageIcon(LayerEditor.class.getResource("/kcl/waterloo/gui/images/minor_grid_remove.png")));
        chckbxMinor.setIcon(new ImageIcon(LayerEditor.class.getResource("/kcl/waterloo/gui/images/minor_grid_add.png")));
        sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxMinor, 3, SpringLayout.SOUTH, chckbxMajor);
        chckbxMinor.setPreferredSize(new Dimension(24, 24));
        sl_panel_1.putConstraint(SpringLayout.WEST, chckbxMinor, 0, SpringLayout.WEST, chckbxMajor);
        sl_panel_1.putConstraint(SpringLayout.EAST, chckbxMinor, 0, SpringLayout.EAST, chckbxMajor);
        hintsPanel.add(chckbxMinor);

        JLabel lblGrid = new JLabel("Grids");
        sl_panel_1.putConstraint(SpringLayout.NORTH, lblGrid, 0, SpringLayout.NORTH, hintsPanel);
        sl_panel_1.putConstraint(SpringLayout.WEST, lblGrid, 3, SpringLayout.WEST, hintsPanel);
        sl_panel_1.putConstraint(SpringLayout.WEST, chckbxMajor, 10, SpringLayout.EAST, lblGrid);
        hintsPanel.add(lblGrid);

        JLabel lblInnerAxis = new JLabel("Inner axis");
        sl_panel_1.putConstraint(SpringLayout.WEST, lblInnerAxis, 0, SpringLayout.WEST, lblGrid);
        hintsPanel.add(lblInnerAxis);

        JCheckBox chckbxVisible = new JCheckBox("Show");
        chckbxVisible.setName("$InnerAxis");
        sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxVisible, 6, SpringLayout.SOUTH, lblInnerAxis);
        sl_panel_1.putConstraint(SpringLayout.WEST, chckbxVisible, 0, SpringLayout.WEST, lblGrid);
        hintsPanel.add(chckbxVisible);

        JCheckBox chckbxLabelled = new JCheckBox("Labels");
        chckbxLabelled.setName("$InnerAxisLabels");
        sl_panel_1.putConstraint(SpringLayout.NORTH, chckbxLabelled, 6, SpringLayout.SOUTH, chckbxVisible);
        sl_panel_1.putConstraint(SpringLayout.WEST, chckbxLabelled, 0, SpringLayout.WEST, lblGrid);
        hintsPanel.add(chckbxLabelled);

        JSeparator separator = new JSeparator();
        sl_panel_1.putConstraint(SpringLayout.NORTH, separator, 2, SpringLayout.SOUTH, chckbxMinor);
        sl_panel_1.putConstraint(SpringLayout.NORTH, lblInnerAxis, 12, SpringLayout.SOUTH, separator);
        sl_panel_1.putConstraint(SpringLayout.SOUTH, lblGrid, -22, SpringLayout.NORTH, separator);
        sl_panel_1.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, hintsPanel);
        sl_panel_1.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, hintsPanel);
        hintsPanel.add(separator);

        JSeparator separator_1 = new JSeparator();
        sl_panel_1.putConstraint(SpringLayout.NORTH, separator_1, 5, SpringLayout.SOUTH, chckbxLabelled);
        sl_panel_1.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, hintsPanel);
        sl_panel_1.putConstraint(SpringLayout.SOUTH, separator_1, 22, SpringLayout.SOUTH, chckbxLabelled);
        sl_panel_1.putConstraint(SpringLayout.EAST, separator_1, 0, SpringLayout.EAST, hintsPanel);
        hintsPanel.add(separator_1);

        JSpinner spinner0 = new JSpinner();
        spinner0.setName("$XDiv");
        sl_panel_1.putConstraint(SpringLayout.EAST, spinner0, 0, SpringLayout.EAST, hintsPanel);
        hintsPanel.add(spinner0);
        spinner0.setModel(new SpinnerNumberModel(4d, 0d, 10d, 1d));
        //spinner0.addChangeListener(spinner0);

        JSpinner spinner1 = new JSpinner();
        sl_panel_1.putConstraint(SpringLayout.NORTH, spinner1, 2, SpringLayout.SOUTH, spinner0);
        spinner1.setName("$YDiv");
        sl_panel_1.putConstraint(SpringLayout.WEST, spinner1, 0, SpringLayout.WEST, spinner0);
        hintsPanel.add(spinner1);
        spinner1.setModel(new SpinnerNumberModel(4d, 0d, 10d, 1d));
        //spinner1.addChangeListener(spinner1);

        JLabel lblDivisions = new JLabel("Divisions");
        sl_panel_1.putConstraint(SpringLayout.NORTH, lblDivisions, 5, SpringLayout.NORTH, separator_1);
        sl_panel_1.putConstraint(SpringLayout.NORTH, spinner0, 6, SpringLayout.SOUTH, lblDivisions);
        sl_panel_1.putConstraint(SpringLayout.WEST, lblDivisions, 5, SpringLayout.WEST, hintsPanel);
        hintsPanel.add(lblDivisions);

        JLabel lblX = new JLabel("X");
        sl_panel_1.putConstraint(SpringLayout.NORTH, lblX, 0, SpringLayout.NORTH, spinner0);
        sl_panel_1.putConstraint(SpringLayout.WEST, lblX, -10, SpringLayout.WEST, spinner0);
        hintsPanel.add(lblX);

        JLabel lblY = new JLabel("Y");
        sl_panel_1.putConstraint(SpringLayout.NORTH, lblY, 0, SpringLayout.NORTH, spinner1);
        sl_panel_1.putConstraint(SpringLayout.WEST, lblY, 0, SpringLayout.WEST, lblX);
        hintsPanel.add(lblY);

        add(XEditor);
        springLayout.putConstraint(SpringLayout.NORTH, XEditor, 0, SpringLayout.NORTH, this);

        JPanel panel = new JPanel();
        XEditor.add(panel);
        panel.setLayout(new VerticalLayout());
        SpringLayout springLayout_1 = (SpringLayout) YEditor.getLayout();

        add(YEditor);

        backGroundCombo = new GJColorComboBox();
        springLayout.putConstraint(SpringLayout.EAST, backGroundCombo, -164, SpringLayout.EAST, this);
        backGroundCombo.setName("$Background");
        springLayout_1.putConstraint(SpringLayout.SOUTH, backGroundCombo, 0, SpringLayout.SOUTH, hintsPanel);
        springLayout_1.putConstraint(SpringLayout.EAST, backGroundCombo, 0, SpringLayout.EAST, XEditor);
        add(backGroundCombo);
        backGroundCombo.setPreferredSize(new Dimension(150, 25));

        gridCombo = new GJColorComboBox();
        springLayout.putConstraint(SpringLayout.WEST, backGroundCombo, 4, SpringLayout.EAST, gridCombo);
        springLayout.putConstraint(SpringLayout.EAST, gridCombo, -319, SpringLayout.EAST, this);
        gridCombo.setName("$GridColor");

        add(gridCombo);
        gridCombo.setPreferredSize(new Dimension(150, 25));

        axisCombo = new GJColorComboBox();
        springLayout.putConstraint(SpringLayout.NORTH, axisCombo, 15, SpringLayout.SOUTH, gridCombo);
        springLayout.putConstraint(SpringLayout.WEST, axisCombo, 0, SpringLayout.WEST, gridCombo);
        springLayout.putConstraint(SpringLayout.EAST, axisCombo, 0, SpringLayout.EAST, gridCombo);
        axisCombo.setName("$AxisColor");
        axisCombo.setPreferredSize(new Dimension(150, 25));
        add(axisCombo);

        JLabel lblForeground = new JLabel("Grid color");
        springLayout.putConstraint(SpringLayout.WEST, gridCombo, 0, SpringLayout.WEST, lblForeground);
        lblForeground.setName("");

        add(lblForeground);

        JLabel lblBacgground = new JLabel("Background");
        springLayout.putConstraint(SpringLayout.WEST, lblBacgground, 0, SpringLayout.WEST, backGroundCombo);

        add(lblBacgground);

        JLabel lblAlpha = new JLabel("Alpha");
        springLayout.putConstraint(SpringLayout.NORTH, lblAlpha, 0, SpringLayout.SOUTH, YEditor);
        springLayout.putConstraint(SpringLayout.SOUTH, lblBacgground, 0, SpringLayout.SOUTH, lblAlpha);
        springLayout.putConstraint(SpringLayout.NORTH, lblForeground, 0, SpringLayout.NORTH, lblAlpha);
        springLayout.putConstraint(SpringLayout.WEST, lblForeground, 43, SpringLayout.EAST, lblAlpha);
        springLayout.putConstraint(SpringLayout.WEST, lblAlpha, 10, SpringLayout.WEST, YEditor);
        springLayout_1.putConstraint(SpringLayout.WEST, lblAlpha, 32, SpringLayout.WEST, YEditor);
        add(lblAlpha);

        JComboBox alphaCombo = new JComboBox();
        springLayout.putConstraint(SpringLayout.NORTH, backGroundCombo, 0, SpringLayout.NORTH, alphaCombo);
        springLayout.putConstraint(SpringLayout.NORTH, gridCombo, 0, SpringLayout.NORTH, alphaCombo);
        springLayout.putConstraint(SpringLayout.NORTH, alphaCombo, 0, SpringLayout.SOUTH, lblAlpha);
        springLayout.putConstraint(SpringLayout.WEST, alphaCombo, 5, SpringLayout.WEST, XEditor);
        alphaCombo.setName("$Alpha");
        alphaCombo.addItem("1.0");
        alphaCombo.addItem("0.8");
        alphaCombo.addItem("0.6");
        alphaCombo.addItem("0.4");
        alphaCombo.addItem("0.2");
        alphaCombo.setEditable(true);
        add(alphaCombo);
        alphaCombo.setPreferredSize(new Dimension(75, 25));

        JCheckBox chckbxNewCheckBox = new JCheckBox("");
        springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, -3, SpringLayout.NORTH, lblBacgground);
        springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 10, SpringLayout.EAST, lblBacgground);
        chckbxNewCheckBox.setName("$BackgroundPainted");
        add(chckbxNewCheckBox);

        JLabel lblNewLabel = new JLabel("Axis color");
        springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, gridCombo);
        springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 2, SpringLayout.NORTH, axisCombo);
        add(lblNewLabel);

        JCheckBox chckbxNewCheckBox_1 = new JCheckBox("<html>Polar<br>plot</html>");
        springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_1, 0, SpringLayout.NORTH, lblNewLabel);
        springLayout.putConstraint(SpringLayout.EAST, chckbxNewCheckBox_1, -29, SpringLayout.EAST, this);
        chckbxNewCheckBox_1.setName("$Polar");
        add(chckbxNewCheckBox_1);
        revalidate();
    }

    public GJColorComboBox getGridCombo() {
        return gridCombo;
    }

    public GJColorComboBox getBackGroundCombo() {
        return backGroundCombo;
    }

    /**
     * @return the axisCombo
     */
    public GJColorComboBox getAxisCombo() {
        return axisCombo;
    }
}
