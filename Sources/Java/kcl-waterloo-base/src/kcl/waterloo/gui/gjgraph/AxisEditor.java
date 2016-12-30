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
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

class AxisEditor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    AxisEditor(String title) {
        setMinimumSize(new Dimension(300, 150));
        setPreferredSize(new Dimension(500, 111));

        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        JPanel axisTitle = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, axisTitle, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, axisTitle, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, axisTitle, 0, SpringLayout.SOUTH, this);
        axisTitle.setMinimumSize(new Dimension(300, 10));
        axisTitle.setPreferredSize(new Dimension(225, 10));
        add(axisTitle);
        axisTitle.setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        SpringLayout sl_axisTitle = new SpringLayout();
        axisTitle.setLayout(sl_axisTitle);

        JTextField axisLabel = new JTextField();
        sl_axisTitle.putConstraint(SpringLayout.NORTH, axisLabel, 0, SpringLayout.NORTH, axisTitle);
        sl_axisTitle.putConstraint(SpringLayout.WEST, axisLabel, 0, SpringLayout.WEST, axisTitle);
        sl_axisTitle.putConstraint(SpringLayout.SOUTH, axisLabel, 25, SpringLayout.NORTH, axisTitle);
        sl_axisTitle.putConstraint(SpringLayout.EAST, axisLabel, 0, SpringLayout.EAST, axisTitle);
        axisLabel.setName("$XAxisLabel");
        axisTitle.add(axisLabel);

        JTextField axisRangeRight = new JTextField();
        sl_axisTitle.putConstraint(SpringLayout.NORTH, axisRangeRight, 20, SpringLayout.SOUTH, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.WEST, axisRangeRight, -80, SpringLayout.EAST, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.SOUTH, axisRangeRight, 45, SpringLayout.SOUTH, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.EAST, axisRangeRight, 0, SpringLayout.EAST, axisLabel);
        axisTitle.add(axisRangeRight);
        axisRangeRight.setName("$AxisRangeRight");
        axisRangeRight.setColumns(10);

        JLabel lblRight = new JLabel("");
        sl_axisTitle.putConstraint(SpringLayout.NORTH, lblRight, -20, SpringLayout.NORTH, axisRangeRight);
        sl_axisTitle.putConstraint(SpringLayout.WEST, lblRight, -15, SpringLayout.WEST, axisRangeRight);
        sl_axisTitle.putConstraint(SpringLayout.SOUTH, lblRight, 0, SpringLayout.SOUTH, axisRangeRight);
        sl_axisTitle.putConstraint(SpringLayout.EAST, lblRight, 0, SpringLayout.WEST, axisRangeRight);
        axisTitle.add(lblRight);
        lblRight.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/right_axis.png")));

        JTextField axisRangeLeft = new JTextField();
        sl_axisTitle.putConstraint(SpringLayout.NORTH, axisRangeLeft, 20, SpringLayout.SOUTH, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.WEST, axisRangeLeft, 20, SpringLayout.WEST, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.SOUTH, axisRangeLeft, 45, SpringLayout.SOUTH, axisLabel);
        sl_axisTitle.putConstraint(SpringLayout.EAST, axisRangeLeft, 100, SpringLayout.WEST, axisLabel);
        axisTitle.add(axisRangeLeft);
        axisRangeLeft.setName("$AxisRangeLeft");

        JLabel lblLeft = new JLabel("");
        sl_axisTitle.putConstraint(SpringLayout.NORTH, lblLeft, -10, SpringLayout.NORTH, axisRangeLeft);
        sl_axisTitle.putConstraint(SpringLayout.WEST, lblLeft, -20, SpringLayout.WEST, axisRangeLeft);
        sl_axisTitle.putConstraint(SpringLayout.EAST, lblLeft, 23, SpringLayout.WEST, axisTitle);
        axisTitle.add(lblLeft);
        lblLeft.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/left_axis.png")));

        JPanel panel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, panel, 235, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, axisTitle);
        panel.setMinimumSize(new Dimension(80, 10));
        panel.setPreferredSize(new Dimension(100, 10));
        add(panel);
        panel.setBorder(new TitledBorder(null, "Axes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));

        JPanel TransformPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, TransformPanel, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, TransformPanel, 345, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, TransformPanel, 0, SpringLayout.SOUTH, axisTitle);
        springLayout.putConstraint(SpringLayout.EAST, TransformPanel, 0, SpringLayout.EAST, this);
        TransformPanel.setPreferredSize(new Dimension(120, 10));
        add(TransformPanel);
        TransformPanel.setBorder(new TitledBorder(null, "Transform", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        SpringLayout sl_panel_4 = new SpringLayout();
        TransformPanel.setLayout(sl_panel_4);

        JCheckBox chckbxReverse = new JCheckBox("Reverse");
        sl_panel_4.putConstraint(SpringLayout.NORTH, chckbxReverse, 0, SpringLayout.NORTH, TransformPanel);
        chckbxReverse.setName("$XReverse");
        chckbxReverse.setHorizontalAlignment(SwingConstants.RIGHT);
        TransformPanel.add(chckbxReverse);
        SpringLayout sl_panel = new SpringLayout();
        panel.setLayout(sl_panel);

        JToggleButton leftAxisControl = new JToggleButton();
        sl_panel.putConstraint(SpringLayout.NORTH, leftAxisControl, 10, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, leftAxisControl, 10, SpringLayout.WEST, panel);
        leftAxisControl.setPreferredSize(new Dimension(24, 24));
        leftAxisControl.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/xtop.png")));
        leftAxisControl.setName("$Top");
        panel.add(leftAxisControl);

        JToggleButton leftAxisLabels = new JToggleButton();
        sl_panel.putConstraint(SpringLayout.NORTH, leftAxisLabels, 0, SpringLayout.NORTH, leftAxisControl);
        sl_panel.putConstraint(SpringLayout.WEST, leftAxisLabels, 6, SpringLayout.EAST, leftAxisControl);
        leftAxisLabels.setSelectedIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/numeric_labels_remove.png")));
        leftAxisLabels.setPreferredSize(new Dimension(24, 24));
        leftAxisLabels.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/numeric_labels.png")));
        leftAxisLabels.setName("$TopLabels");
        panel.add(leftAxisLabels);
        JToggleButton rightAxisControl = new JToggleButton();
        sl_panel.putConstraint(SpringLayout.NORTH, rightAxisControl, 6, SpringLayout.SOUTH, leftAxisControl);
        sl_panel.putConstraint(SpringLayout.WEST, rightAxisControl, 0, SpringLayout.WEST, leftAxisControl);
        rightAxisControl.setPreferredSize(new Dimension(24, 24));
        rightAxisControl.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/xbottom.png")));
        rightAxisControl.setName("$Bottom");
        panel.add(rightAxisControl);

        JToggleButton rightAxisLabels = new JToggleButton("");
        sl_panel.putConstraint(SpringLayout.NORTH, rightAxisLabels, 6, SpringLayout.SOUTH, leftAxisControl);
        sl_panel.putConstraint(SpringLayout.WEST, rightAxisLabels, 0, SpringLayout.WEST, leftAxisLabels);
        rightAxisLabels.setSelectedIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/numeric_labels_remove.png")));
        rightAxisLabels.setPreferredSize(new Dimension(24, 24));
        rightAxisLabels.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/numeric_labels.png")));
        rightAxisLabels.setName("$BottomLabels");
        panel.add(rightAxisLabels);

        JComboBox transformSelector = new JComboBox();
        sl_panel_4.putConstraint(SpringLayout.NORTH, transformSelector, 6, SpringLayout.SOUTH, chckbxReverse);
        sl_panel_4.putConstraint(SpringLayout.WEST, chckbxReverse, 0, SpringLayout.WEST, transformSelector);
        transformSelector.setName("$XTransform");
        sl_panel_4.putConstraint(SpringLayout.WEST, transformSelector, 2, SpringLayout.WEST, TransformPanel);
        sl_panel_4.putConstraint(SpringLayout.EAST, transformSelector, -2, SpringLayout.EAST, TransformPanel);
        TransformPanel.add(transformSelector);

        if (title.equals("Y-Axis")) {
            axisLabel.setName("$YAxisLabel");
            leftAxisControl.setName("$Left");
            leftAxisControl.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/yleft.png")));
            rightAxisControl.setName("$Right");
            rightAxisControl.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/yright.png")));
            leftAxisLabels.setName("$LeftLabels");
            rightAxisLabels.setName("$RightLabels");
            axisRangeLeft.setName("$AxisRangeBottom");
            axisRangeRight.setName("$AxisRangeTop");
            lblLeft.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/bottom_axis.png")));
            lblRight.setIcon(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/gui/images/top_axis.png")));
            chckbxReverse.setName("$YReverse");
            transformSelector.setName("$YTransform");
        }
    }
}
