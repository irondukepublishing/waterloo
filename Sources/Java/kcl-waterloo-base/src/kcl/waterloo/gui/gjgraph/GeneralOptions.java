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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import kcl.waterloo.gui.FontAspectCombo;
import kcl.waterloo.widget.GJColorComboBox;
import kcl.waterloo.widget.GJDial;

public class GeneralOptions extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final GJColorComboBox backGroundCombo;
    private final FontAspectCombo fontPanel;
    private JCheckBox shadowEffect;
    private JCheckBox frameEffect;

    /**
     * Create the panel.
     */
    public GeneralOptions() {
        setPreferredSize(new Dimension(573, 186));
        setBorder(new LineBorder(new Color(0, 0, 0)));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        JPanel titlePanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, titlePanel, 1, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, titlePanel, 1, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, titlePanel, 70, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, titlePanel, 286, SpringLayout.WEST, this);
        titlePanel.setBorder(new TitledBorder(null, "Title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(titlePanel);
        springLayout.putConstraint(SpringLayout.NORTH, titlePanel, 1, SpringLayout.NORTH, this);
        SpringLayout titlePanelLayout = new SpringLayout();
        titlePanel.setLayout(titlePanelLayout);
        JTextField textField = new JTextField();
        titlePanelLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, titlePanel);
        titlePanelLayout.putConstraint(SpringLayout.SOUTH, textField, 0, SpringLayout.SOUTH, titlePanel);
        textField.setName("$Title");
        titlePanel.add(textField);
        titlePanelLayout.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.WEST, titlePanel);
        titlePanelLayout.putConstraint(SpringLayout.EAST, textField, -5, SpringLayout.EAST, titlePanel);

        JPanel subTitlePanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, subTitlePanel, 0, SpringLayout.SOUTH, titlePanel);
        springLayout.putConstraint(SpringLayout.SOUTH, subTitlePanel, 70, SpringLayout.SOUTH, titlePanel);
        titlePanelLayout = new SpringLayout();
        subTitlePanel.setLayout(titlePanelLayout);
        springLayout.putConstraint(SpringLayout.WEST, subTitlePanel, 1, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, subTitlePanel, 286, SpringLayout.WEST, this);
        subTitlePanel.setBorder(new TitledBorder(null, "SubTitle", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(subTitlePanel);
        JTextField textField_1 = new JTextField();
        titlePanelLayout.putConstraint(SpringLayout.NORTH, textField_1, 0, SpringLayout.NORTH, subTitlePanel);
        titlePanelLayout.putConstraint(SpringLayout.SOUTH, textField_1, 0, SpringLayout.SOUTH, subTitlePanel);
        textField_1.setName("$SubTitle");
        subTitlePanel.add(textField_1);
        titlePanelLayout.putConstraint(SpringLayout.WEST, textField_1, 5, SpringLayout.WEST, subTitlePanel);
        titlePanelLayout.putConstraint(SpringLayout.EAST, textField_1, -5, SpringLayout.EAST, subTitlePanel);

        JPanel rotatePanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, rotatePanel, 1, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, rotatePanel, 291, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, rotatePanel, 0, SpringLayout.SOUTH, titlePanel);
        springLayout.putConstraint(SpringLayout.EAST, rotatePanel, 411, SpringLayout.WEST, this);
        //rotatePanel.setBorder(new IconBorder(new ImageIcon(AxisEditor.class.getResource("/kcl/waterloo/graphics/gui/gjgraph/arrow_refresh.png"))));
        rotatePanel.setBorder(new TitledBorder(null, "Rotation (\u00b0)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(rotatePanel);
        GridBagLayout gbl_rotatePanel = new GridBagLayout();
        gbl_rotatePanel.columnWidths = new int[]{160, 25, 0};
        gbl_rotatePanel.rowHeights = new int[]{22, 0};
        gbl_rotatePanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_rotatePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        rotatePanel.setLayout(gbl_rotatePanel);

        JComboBox rotateCombo = new JComboBox();
        rotateCombo.setName("$Rotation");
        rotateCombo.addItem("0");
        rotateCombo.addItem("-45");
        rotateCombo.addItem("-90");
        rotateCombo.addItem("-135");
        rotateCombo.addItem("180");
        rotateCombo.addItem("+135");
        rotateCombo.addItem("+90");
        rotateCombo.addItem("+45");
        rotateCombo.setEditable(true);
        GridBagConstraints gbc_rotateCombo = new GridBagConstraints();
        gbc_rotateCombo.anchor = GridBagConstraints.WEST;
        gbc_rotateCombo.fill = GridBagConstraints.VERTICAL;
        gbc_rotateCombo.insets = new Insets(2, 0, 0, 5);
        gbc_rotateCombo.gridx = 0;
        gbc_rotateCombo.gridy = 0;
        rotatePanel.add(rotateCombo, gbc_rotateCombo);

        JPanel zoomPanel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, zoomPanel, 0, SpringLayout.NORTH, subTitlePanel);
        springLayout.putConstraint(SpringLayout.WEST, zoomPanel, 291, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, zoomPanel, 0, SpringLayout.SOUTH, subTitlePanel);
        springLayout.putConstraint(SpringLayout.EAST, zoomPanel, 0, SpringLayout.EAST, rotatePanel);
        zoomPanel.setBorder(new TitledBorder(null, "View size(%)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(zoomPanel);
        GridBagLayout gbl_zoomPanel = new GridBagLayout();
        gbl_zoomPanel.columnWidths = new int[]{160, 25, 0};
        gbl_zoomPanel.rowHeights = new int[]{21, 0};
        gbl_zoomPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_zoomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        zoomPanel.setLayout(gbl_zoomPanel);

        JComboBox zoomCombo = new JComboBox();
        zoomCombo.setName("$Zoom");
        GridBagConstraints gbc_zoomCombo = new GridBagConstraints();
        gbc_zoomCombo.anchor = GridBagConstraints.WEST;
        gbc_zoomCombo.fill = GridBagConstraints.VERTICAL;
        gbc_zoomCombo.insets = new Insets(2, 0, 0, 5);
        gbc_zoomCombo.gridx = 0;
        gbc_zoomCombo.gridy = 0;
        zoomPanel.add(zoomCombo, gbc_zoomCombo);
        zoomCombo.setEditable(true);
        zoomCombo.addItem("1000");
        zoomCombo.addItem("500");
        zoomCombo.addItem("200");
        zoomCombo.addItem("100");
        zoomCombo.addItem("90");
        zoomCombo.addItem("80");
        zoomCombo.addItem("70");
        zoomCombo.addItem("60");
        zoomCombo.addItem("50");

        GJDial dial = new GJDial(25, 10);
        dial.setMinimum(10);
        dial.setMaximum(2000);
        dial.setValue(100);
        dial.setName("$zoomDial");
        //dial.setPreferredSize(new Dimension(25, 25));
        GridBagConstraints gbc_dial = new GridBagConstraints();
        gbc_dial.insets = new Insets(2, 0, 0, 0);
        gbc_dial.anchor = GridBagConstraints.WEST;
        gbc_dial.fill = GridBagConstraints.VERTICAL;
        gbc_dial.gridx = 1;
        gbc_dial.gridy = 0;
        zoomPanel.add(dial, gbc_dial);

        JPanel panel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel, 1, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, panel, 4, SpringLayout.EAST, rotatePanel);
        springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, zoomPanel);
        springLayout.putConstraint(SpringLayout.EAST, panel, -1, SpringLayout.EAST, this);

        GJDial dialR = new GJDial();
        dialR.setMinimum(-180);
        dialR.setMaximum(180);
        dialR.setValue(0);
        dialR.setName("$rotateDial");
        dialR.setPreferredSize(new Dimension(25, 25));
        GridBagConstraints gbc_dialR = new GridBagConstraints();
        gbc_dialR.insets = new Insets(2, 0, 0, 0);
        gbc_dialR.anchor = GridBagConstraints.WEST;
        gbc_dialR.fill = GridBagConstraints.VERTICAL;
        gbc_dialR.gridx = 1;
        gbc_dialR.gridy = 0;
        rotatePanel.add(dialR, gbc_dialR);
        add(panel);
        panel.setLayout(new GridLayout());
        fontPanel = new FontAspectCombo();
        panel.add(fontPanel);

        backGroundCombo = new GJColorComboBox();
        springLayout.putConstraint(SpringLayout.NORTH, backGroundCombo, 15, SpringLayout.SOUTH, subTitlePanel);
        springLayout.putConstraint(SpringLayout.WEST, backGroundCombo, 5, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, backGroundCombo, 40, SpringLayout.SOUTH, subTitlePanel);
        backGroundCombo.setName("$ContainerBackground");
        backGroundCombo.setPreferredSize(new Dimension(150, 25));
        add(backGroundCombo);

        JLabel lblNewLabel_1 = new JLabel("Background");
        springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, backGroundCombo);
        springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, -1, SpringLayout.NORTH, backGroundCombo);
        add(lblNewLabel_1);

        JComboBox spinner = new JComboBox();
        springLayout.putConstraint(SpringLayout.WEST, spinner, 10, SpringLayout.EAST, zoomPanel);

        add(spinner);
        spinner.setEditable(true);
        spinner.setName("$AspectRatio");
        spinner.addItem("Auto");
        spinner.addItem("2");
        spinner.addItem("1.5");
        spinner.addItem("1");
        spinner.addItem("0.75");
        spinner.addItem("0.5");

        JLabel lblNewLabel_2 = new JLabel("Aspect Ratio");
        springLayout.putConstraint(SpringLayout.NORTH, spinner, 0, SpringLayout.SOUTH, lblNewLabel_2);
        springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 2, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_2, 10, SpringLayout.WEST, panel);
        lblNewLabel_2.setName("");
        add(lblNewLabel_2);

        JCheckBox chckbxNewCheckBox = new JCheckBox("");
        springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 0, SpringLayout.NORTH, lblNewLabel_1);
        chckbxNewCheckBox.setName("$ContainerBackgroundPainted");
        springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 20, SpringLayout.EAST, lblNewLabel_1);
        add(chckbxNewCheckBox);

        shadowEffect = new JCheckBox("Shadow");
        shadowEffect.setName("$Shadow");
        springLayout.putConstraint(SpringLayout.NORTH, shadowEffect, 6, SpringLayout.SOUTH, subTitlePanel);
        add(shadowEffect);

        frameEffect = new JCheckBox("Frame");
        frameEffect.setName("$Frame");
        springLayout.putConstraint(SpringLayout.EAST, shadowEffect, -11, SpringLayout.WEST, frameEffect);
        springLayout.putConstraint(SpringLayout.NORTH, frameEffect, 6, SpringLayout.SOUTH, zoomPanel);
        springLayout.putConstraint(SpringLayout.WEST, frameEffect, 10, SpringLayout.WEST, zoomPanel);
        add(frameEffect);

    }

    /**
     * @return the backGroundCombo
     */
    public GJColorComboBox getBackGroundCombo() {
        return backGroundCombo;
    }

    /**
     * @return the fontPanel
     */
    public FontAspectCombo getFontPanel() {
        return fontPanel;
    }

    public JCheckBox getShadowEffect() {
        return shadowEffect;
    }

    public void setShadow(JCheckBox Shadow) {
        this.shadowEffect = Shadow;
    }

    public JCheckBox getFrameEffect() {
        return frameEffect;
    }

    public void setFrameEffect(JCheckBox frameEffect) {
        this.frameEffect = frameEffect;
    }
}
