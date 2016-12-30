package kcl.waterloo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import kcl.waterloo.widget.GJColorComboBox;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.ComponentOrientation;
import java.awt.Font;

public class PreferencesDialog extends JDialog {

    public static PreferencesDialog getInstance() {
        PreferencesDialog dialog = new PreferencesDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        return dialog;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            PreferencesDialog dialog = new PreferencesDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private final JCheckBox antiAliased = new JCheckBox("Anti-aliased");
    private final JButton applyButton = new JButton("Apply");
    private final GJColorComboBox axesColorCombo = new GJColorComboBox();
    private final JComboBox axesPadding = new JComboBox();
    private final JComboBox axisWeightCombo = new JComboBox();
    private final JCheckBox bottomAxisLabelled = new JCheckBox("Bottom");
    private final JCheckBox bottomAxisPainted = new JCheckBox("Bottom");
    private final JButton cancelButton = new JButton("Cancel");
    private final GJColorComboBox containerBackground = new GJColorComboBox();
    private final JCheckBox containerBackgroundPainted = new JCheckBox("");
    private final JPanel contentPanel = new JPanel();
    private final JCheckBox formatEPS = new JCheckBox("EPS");
    private final JCheckBox formatPDF = new JCheckBox("PDF");
    private final JCheckBox formatPS = new JCheckBox("Postscript");
    private final JCheckBox formatSVG = new JCheckBox("SVG");
    private final JCheckBox formatSVGAsText = new JCheckBox("SVG as text");
    private final JCheckBox innerAxisLabelled = new JCheckBox("Labels");
    private final JCheckBox innerAxisPainted = new JCheckBox("Visible");
    private final JCheckBox leftAxisLabelled = new JCheckBox("Left");
    private final JCheckBox leftAxisPainted = new JCheckBox("Left");
    private final GJColorComboBox lineColor = new GJColorComboBox();
    private final JComboBox lineStyle = new JComboBox();
    private final JComboBox lineWeight = new JComboBox();
    private final JButton loadButton = new JButton("Load");
    private final GJColorComboBox majorGridColorCombo = new GJColorComboBox();
    private final JCheckBox majorGridPainted = new JCheckBox("");
    private final JComboBox majorGridWeightCombo = new JComboBox();
    private final GJColorComboBox markerEdgeColor = new GJColorComboBox();
    private final JComboBox markerEdgeWeight = new JComboBox();
    private final GJColorComboBox markerFill = new GJColorComboBox();
    private final JComboBox markerSize = new JComboBox();
    private final JComboBox markerSymbol = new JComboBox();
    private final GJColorComboBox minorGridColorCombo = new GJColorComboBox();
    private final JCheckBox minorGridPainted = new JCheckBox("");
    private final JComboBox minorGridWeightCombo = new JComboBox();
    private final JToggleButton padAxes = new JToggleButton("on");
    private final JPanel previewPanel = new JPanel();
    private final JButton resetButton = new JButton("Reset");
    private final JCheckBox rightAxisLabelled = new JCheckBox("Right");
    private final JCheckBox rightAxisPainted = new JCheckBox("Right");
    private final JButton saveButton = new JButton("Save");
    private final JCheckBox topAxisLabelled = new JCheckBox("Top");
    private final JCheckBox topAxisPainted = new JCheckBox("Top");
    private final GJColorComboBox viewBackground = new GJColorComboBox();
    private final JCheckBox viewBackgroundPainted = new JCheckBox("");
    private final JSpinner xDivSpinner = new JSpinner();
    private final JSpinner yDivSpinner = new JSpinner();
    private final GJColorComboBox highlightColor = new GJColorComboBox();
    private final JComboBox javaScriptLocation = new JComboBox();
    private final JComboBox cssLocation = new JComboBox();
    private final JCheckBox httpd = new JCheckBox("Create httpd.py file");
    private final JCheckBox inline = new JCheckBox("Inline SVG");
    private final JCheckBox canvg = new JCheckBox("Use canvg.js");
    private final JCheckBox svgHTTPD = new JCheckBox("Create httpd.py file");
    private final JComboBox svgCSSLoc = new JComboBox();
    private final JComboBox svgJSLoc = new JComboBox();

    /**
     * Create the dialog.
     */
    public PreferencesDialog() {
        setTitle("Preferences");
        setBounds(100, 100, 731, 467);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        getContentPane().add(new LogoPanel("Preferences"), BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{400, 150, 0};
        gbl_contentPanel.rowHeights = new int[]{305, 0};
        gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        JPanel clipboard = new JPanel();
        JPanel session = new JPanel();
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
            gbc_tabbedPane.fill = GridBagConstraints.BOTH;
            gbc_tabbedPane.insets = new Insets(0, 0, 0, 5);
            gbc_tabbedPane.gridx = 0;
            gbc_tabbedPane.gridy = 0;
            contentPanel.add(tabbedPane, gbc_tabbedPane);
            JPanel panel = new JPanel();
            {
                JPanel Axes_and_Grids = new JPanel();
                Axes_and_Grids.setMinimumSize(new Dimension(100, 10));
                tabbedPane.addTab("Axes & Grids", null, Axes_and_Grids, null);
                Axes_and_Grids.setPreferredSize(new Dimension(150, 75));
                GridBagLayout gbl_Axes_and_Grids = new GridBagLayout();
                gbl_Axes_and_Grids.columnWidths = new int[]{100, 0, 170, 75, 75, 0, 0};
                gbl_Axes_and_Grids.rowHeights = new int[]{30, 35, 35, 30, 5, 30, 30, 20, 0, 30, 0};
                gbl_Axes_and_Grids.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                gbl_Axes_and_Grids.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
                Axes_and_Grids.setLayout(gbl_Axes_and_Grids);
                {
                    JLabel lblAxes = new JLabel("<html><strong>Axes</strong></html>");
                    GridBagConstraints gbc_lblAxes = new GridBagConstraints();
                    gbc_lblAxes.insets = new Insets(0, 0, 5, 5);
                    gbc_lblAxes.gridx = 2;
                    gbc_lblAxes.gridy = 0;
                    Axes_and_Grids.add(lblAxes, gbc_lblAxes);
                }
                {
                    JLabel lblVisible = new JLabel("Visible");
                    GridBagConstraints gbc_lblVisible = new GridBagConstraints();
                    gbc_lblVisible.fill = GridBagConstraints.VERTICAL;
                    gbc_lblVisible.insets = new Insets(0, 0, 5, 5);
                    gbc_lblVisible.gridx = 0;
                    gbc_lblVisible.gridy = 1;
                    Axes_and_Grids.add(lblVisible, gbc_lblVisible);
                }
                JPanel panel_1 = new JPanel();
                {
                    GridBagConstraints gbc_panel_1 = new GridBagConstraints();
                    gbc_panel_1.gridwidth = 3;
                    gbc_panel_1.insets = new Insets(0, 0, 5, 5);
                    gbc_panel_1.fill = GridBagConstraints.BOTH;
                    gbc_panel_1.gridx = 2;
                    gbc_panel_1.gridy = 1;
                    panel_1.setMaximumSize(new Dimension(200, 32767));
                    panel_1.setBorder(null);
                    Axes_and_Grids.add(panel_1, gbc_panel_1);
                }
                panel_1.setLayout(new GridLayout(1, 0, 0, 0));
                {
                    leftAxisPainted.setActionCommand("leftAxisPainted");
                    panel_1.add(leftAxisPainted);
                }
                {
                    rightAxisPainted.setActionCommand("rightAxisPainted");
                    panel_1.add(rightAxisPainted);
                }
                {
                    topAxisPainted.setActionCommand("topAxisPainted");
                    panel_1.add(topAxisPainted);
                }
                {
                    bottomAxisPainted.setActionCommand("bottomAxisPainted");
                    bottomAxisPainted.setHorizontalAlignment(SwingConstants.LEFT);
                    panel_1.add(bottomAxisPainted);
                }
                {
                    JLabel lblNewLabel = new JLabel("Labels");
                    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                    gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
                    gbc_lblNewLabel.gridx = 0;
                    gbc_lblNewLabel.gridy = 2;
                    Axes_and_Grids.add(lblNewLabel, gbc_lblNewLabel);
                }
                JPanel panel_2 = new JPanel();
                {
                    GridBagConstraints gbc_panel_2 = new GridBagConstraints();
                    gbc_panel_2.gridwidth = 3;
                    gbc_panel_2.insets = new Insets(0, 0, 5, 5);
                    gbc_panel_2.fill = GridBagConstraints.BOTH;
                    gbc_panel_2.gridx = 2;
                    gbc_panel_2.gridy = 2;
                    Axes_and_Grids.add(panel_2, gbc_panel_2);
                }
                panel_2.setLayout(new GridLayout(1, 0, 0, 0));
                {
                    leftAxisLabelled.setActionCommand("leftAxisLabelled");
                    panel_2.add(leftAxisLabelled);
                }
                {
                    rightAxisLabelled.setActionCommand("rightAxisLabelled");
                    panel_2.add(rightAxisLabelled);
                }
                {
                    topAxisLabelled.setActionCommand("topAxisLabelled");
                    panel_2.add(topAxisLabelled);
                }
                {
                    bottomAxisLabelled.setActionCommand("bottomAxisLabelled");
                    panel_2.add(bottomAxisLabelled);
                }
                {
                    GridBagConstraints gbc_lblColor = new GridBagConstraints();
                    gbc_lblColor.fill = GridBagConstraints.VERTICAL;
                    gbc_lblColor.insets = new Insets(0, 0, 5, 5);
                    gbc_lblColor.gridx = 0;
                    gbc_lblColor.gridy = 3;
                    JLabel lblColor = new JLabel("Color");
                    Axes_and_Grids.add(lblColor, gbc_lblColor);
                }
                GridBagConstraints gbc_axesColorCombo = new GridBagConstraints();
                gbc_axesColorCombo.insets = new Insets(0, 0, 5, 5);
                gbc_axesColorCombo.fill = GridBagConstraints.BOTH;
                gbc_axesColorCombo.gridx = 2;
                gbc_axesColorCombo.gridy = 3;
                axesColorCombo.setActionCommand("axisColor");

                Axes_and_Grids.add(axesColorCombo, gbc_axesColorCombo);
                {
                    JLabel lblWeight = new JLabel("Weight");
                    GridBagConstraints gbc_lblWeight = new GridBagConstraints();
                    gbc_lblWeight.fill = GridBagConstraints.VERTICAL;
                    gbc_lblWeight.insets = new Insets(0, 0, 5, 5);
                    gbc_lblWeight.gridx = 3;
                    gbc_lblWeight.gridy = 3;
                    Axes_and_Grids.add(lblWeight, gbc_lblWeight);
                }
                {
                    axisWeightCombo.setActionCommand("axisWeight");
                    axisWeightCombo.setPreferredSize(new Dimension(25, 25));
                    axisWeightCombo.setMinimumSize(new Dimension(10, 27));
                    axisWeightCombo.addItem(0.5f);
                    axisWeightCombo.addItem(1.0f);
                    axisWeightCombo.addItem(1.5f);
                    axisWeightCombo.addItem(2.0f);
                    axisWeightCombo.setEditable(true);
                    GridBagConstraints gbc_axisWeightCombo = new GridBagConstraints();
                    gbc_axisWeightCombo.fill = GridBagConstraints.BOTH;
                    gbc_axisWeightCombo.insets = new Insets(0, 0, 5, 5);
                    gbc_axisWeightCombo.gridx = 4;
                    gbc_axisWeightCombo.gridy = 3;
                    Axes_and_Grids.add(axisWeightCombo, gbc_axisWeightCombo);
                }

                {
                    GridBagConstraints gbc_lblGrids = new GridBagConstraints();
                    gbc_lblGrids.insets = new Insets(0, 0, 5, 5);
                    gbc_lblGrids.gridx = 2;
                    gbc_lblGrids.gridy = 4;
                    JLabel lblGrids = new JLabel("<html><strong>Grids</strong></html>");
                    Axes_and_Grids.add(lblGrids, gbc_lblGrids);
                }
                {
                    GridBagConstraints gbc_lblVisible_1 = new GridBagConstraints();
                    gbc_lblVisible_1.insets = new Insets(0, 0, 5, 5);
                    gbc_lblVisible_1.gridx = 4;
                    gbc_lblVisible_1.gridy = 4;
                    JLabel lblVisible_1 = new JLabel("Visible");
                    Axes_and_Grids.add(lblVisible_1, gbc_lblVisible_1);
                }
                {
                    JLabel lblMajorGrid = new JLabel("Major Grid");
                    GridBagConstraints gbc_lblMajorGrid = new GridBagConstraints();
                    gbc_lblMajorGrid.fill = GridBagConstraints.VERTICAL;
                    gbc_lblMajorGrid.insets = new Insets(0, 0, 5, 5);
                    gbc_lblMajorGrid.gridx = 0;
                    gbc_lblMajorGrid.gridy = 5;
                    Axes_and_Grids.add(lblMajorGrid, gbc_lblMajorGrid);
                }

                GridBagConstraints gbc_majorGridColorCombo = new GridBagConstraints();
                gbc_majorGridColorCombo.fill = GridBagConstraints.BOTH;
                gbc_majorGridColorCombo.insets = new Insets(0, 0, 5, 5);
                gbc_majorGridColorCombo.gridx = 2;
                gbc_majorGridColorCombo.gridy = 5;
                majorGridColorCombo.setActionCommand("majorGridColor");
                Axes_and_Grids.add(majorGridColorCombo, gbc_majorGridColorCombo);
                {
                    majorGridWeightCombo.setActionCommand("majorGridStrokeWeight");
                    majorGridWeightCombo.setPreferredSize(new Dimension(25, 25));
                    majorGridWeightCombo.setMinimumSize(new Dimension(20, 27));
                    majorGridWeightCombo.addItem(0.5f);
                    majorGridWeightCombo.addItem(1.0f);
                    majorGridWeightCombo.addItem(1.5f);
                    majorGridWeightCombo.addItem(2.0f);
                    majorGridWeightCombo.setEditable(true);
                    GridBagConstraints gbc_majorGridWeightCombo = new GridBagConstraints();
                    gbc_majorGridWeightCombo.insets = new Insets(0, 0, 5, 5);
                    gbc_majorGridWeightCombo.fill = GridBagConstraints.BOTH;
                    gbc_majorGridWeightCombo.gridx = 3;
                    gbc_majorGridWeightCombo.gridy = 5;
                    Axes_and_Grids.add(majorGridWeightCombo, gbc_majorGridWeightCombo);
                }
                {
                    GridBagConstraints gbc_majorGridPainted = new GridBagConstraints();
                    gbc_majorGridPainted.insets = new Insets(0, 0, 5, 5);
                    gbc_majorGridPainted.gridx = 4;
                    gbc_majorGridPainted.gridy = 5;
                    majorGridPainted.setActionCommand("majorGridPainted");
                    Axes_and_Grids.add(majorGridPainted, gbc_majorGridPainted);
                }
                {
                    JLabel lblGrid = new JLabel("Minor Grid");
                    GridBagConstraints gbc_lblGrid = new GridBagConstraints();
                    gbc_lblGrid.fill = GridBagConstraints.VERTICAL;
                    gbc_lblGrid.insets = new Insets(0, 0, 5, 5);
                    gbc_lblGrid.gridx = 0;
                    gbc_lblGrid.gridy = 6;
                    Axes_and_Grids.add(lblGrid, gbc_lblGrid);
                }

                GridBagConstraints gbc_gridColorCombo = new GridBagConstraints();
                gbc_gridColorCombo.fill = GridBagConstraints.BOTH;
                gbc_gridColorCombo.insets = new Insets(0, 0, 5, 5);
                gbc_gridColorCombo.gridx = 2;
                gbc_gridColorCombo.gridy = 6;
                minorGridColorCombo.setActionCommand("minorGridColor");
                Axes_and_Grids.add(minorGridColorCombo, gbc_gridColorCombo);
                {
                    minorGridWeightCombo.setActionCommand("minorGridStrokeWeight");
                    minorGridWeightCombo.setPreferredSize(new Dimension(25, 25));
                    minorGridWeightCombo.setMinimumSize(new Dimension(20, 27));
                    minorGridWeightCombo.addItem(0.5f);
                    minorGridWeightCombo.addItem(1.0f);
                    minorGridWeightCombo.addItem(1.5f);
                    minorGridWeightCombo.addItem(2.0f);
                    minorGridWeightCombo.setEditable(true);
                    GridBagConstraints gbc_minorGridWeigthCombo = new GridBagConstraints();
                    gbc_minorGridWeigthCombo.insets = new Insets(0, 0, 5, 5);
                    gbc_minorGridWeigthCombo.fill = GridBagConstraints.BOTH;
                    gbc_minorGridWeigthCombo.gridx = 3;
                    gbc_minorGridWeigthCombo.gridy = 6;
                    Axes_and_Grids.add(minorGridWeightCombo, gbc_minorGridWeigthCombo);
                }
                {
                    GridBagConstraints gbc_minorGridPainted = new GridBagConstraints();
                    gbc_minorGridPainted.insets = new Insets(0, 0, 5, 5);
                    gbc_minorGridPainted.gridx = 4;
                    gbc_minorGridPainted.gridy = 6;
                    minorGridPainted.setActionCommand("minorGridPainted");
                    Axes_and_Grids.add(minorGridPainted, gbc_minorGridPainted);
                }
                JPanel panel_3 = new JPanel();
                {
                    GridBagConstraints gbc_panel_3 = new GridBagConstraints();
                    gbc_panel_3.insets = new Insets(0, 0, 5, 5);
                    gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
                    gbc_panel_3.gridx = 2;
                    gbc_panel_3.gridy = 7;
                    Axes_and_Grids.add(panel_3, gbc_panel_3);
                }
                panel_3.setLayout(new GridLayout(1, 0, 0, 0));
                {
                    JLabel lblX = new JLabel("X");
                    lblX.setHorizontalAlignment(SwingConstants.CENTER);
                    panel_3.add(lblX);
                }
                {
                    JLabel lblY = new JLabel("Y");
                    lblY.setHorizontalAlignment(SwingConstants.CENTER);
                    panel_3.add(lblY);
                }
                {
                    GridBagConstraints gbc_lblInternalAxis = new GridBagConstraints();
                    gbc_lblInternalAxis.gridwidth = 2;
                    gbc_lblInternalAxis.insets = new Insets(0, 0, 5, 5);
                    gbc_lblInternalAxis.gridx = 3;
                    gbc_lblInternalAxis.gridy = 7;
                    JLabel lblInternalAxis = new JLabel("Internal Axis");
                    Axes_and_Grids.add(lblInternalAxis, gbc_lblInternalAxis);
                }
                {
                    GridBagConstraints gbc_separator = new GridBagConstraints();
                    gbc_separator.gridwidth = 2;
                    gbc_separator.insets = new Insets(0, 0, 5, 5);
                    gbc_separator.gridx = 3;
                    gbc_separator.gridy = 8;
                    JSeparator separator = new JSeparator();
                    separator.setForeground(Color.BLACK);
                    separator.setBackground(Color.BLACK);
                    separator.setMinimumSize(new Dimension(100, 3));
                    separator.setBorder(null);
                    Axes_and_Grids.add(separator, gbc_separator);
                }
                {
                    JLabel lblDivisions = new JLabel("Divisions");
                    GridBagConstraints gbc_lblDivisions = new GridBagConstraints();
                    gbc_lblDivisions.fill = GridBagConstraints.VERTICAL;
                    gbc_lblDivisions.insets = new Insets(0, 0, 0, 5);
                    gbc_lblDivisions.gridx = 0;
                    gbc_lblDivisions.gridy = 9;
                    Axes_and_Grids.add(lblDivisions, gbc_lblDivisions);
                }
                {
                    GridBagConstraints gbc_panel = new GridBagConstraints();
                    gbc_panel.insets = new Insets(0, 0, 0, 5);
                    gbc_panel.fill = GridBagConstraints.BOTH;
                    gbc_panel.gridx = 2;
                    gbc_panel.gridy = 9;
                    panel.setBorder(new EmptyBorder(5, 5, 5, 5));
                    Axes_and_Grids.add(panel, gbc_panel);
                }
                {
                    GridBagConstraints gbc_innerAxisPainted = new GridBagConstraints();
                    gbc_innerAxisPainted.insets = new Insets(0, 0, 0, 5);
                    gbc_innerAxisPainted.gridx = 3;
                    gbc_innerAxisPainted.gridy = 9;
                    innerAxisPainted.setActionCommand("innerAxisPainted");
                    Axes_and_Grids.add(innerAxisPainted, gbc_innerAxisPainted);
                }
                {
                    GridBagConstraints gbc_innerAxisLabelled = new GridBagConstraints();
                    gbc_innerAxisLabelled.insets = new Insets(0, 0, 0, 5);
                    gbc_innerAxisLabelled.fill = GridBagConstraints.VERTICAL;
                    gbc_innerAxisLabelled.gridx = 4;
                    gbc_innerAxisLabelled.gridy = 9;
                    innerAxisLabelled.setActionCommand("innerAxisLabelled");
                    Axes_and_Grids.add(innerAxisLabelled, gbc_innerAxisLabelled);
                }
            }
            panel.setLayout(new GridLayout(1, 0, 0, 0));
            {
                xDivSpinner.setName("$XDivSpinner");
                panel.add(xDivSpinner);
            }
            {
                yDivSpinner.setName("$YDivSpinner");
                panel.add(yDivSpinner);
            }
            {
                JPanel Graphs = new JPanel();
                tabbedPane.addTab("Graphs", null, Graphs, null);
                GridBagLayout gbl_Graphs = new GridBagLayout();
                gbl_Graphs.columnWidths = new int[]{50, 175, 50, 97, 30, 0};
                gbl_Graphs.rowHeights = new int[]{50, 25, 30, 25, 30, 44, 24, 0, 0};
                gbl_Graphs.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
                gbl_Graphs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
                Graphs.setLayout(gbl_Graphs);
                {
                    GridBagConstraints gbc_lblContainerBackground = new GridBagConstraints();
                    gbc_lblContainerBackground.anchor = GridBagConstraints.SOUTH;
                    gbc_lblContainerBackground.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblContainerBackground.insets = new Insets(0, 0, 5, 5);
                    gbc_lblContainerBackground.gridx = 1;
                    gbc_lblContainerBackground.gridy = 1;
                    JLabel lblContainerBackground = new JLabel("Container background");
                    Graphs.add(lblContainerBackground, gbc_lblContainerBackground);
                }
                {
                    GridBagConstraints gbc_lblPainted = new GridBagConstraints();
                    gbc_lblPainted.anchor = GridBagConstraints.SOUTH;
                    gbc_lblPainted.insets = new Insets(0, 0, 5, 5);
                    gbc_lblPainted.gridx = 2;
                    gbc_lblPainted.gridy = 1;
                    JLabel lblPainted = new JLabel("Visible");
                    Graphs.add(lblPainted, gbc_lblPainted);
                }
                {
                    GridBagConstraints gbc_lblAxisPadding = new GridBagConstraints();
                    gbc_lblAxisPadding.anchor = GridBagConstraints.SOUTH;
                    gbc_lblAxisPadding.insets = new Insets(0, 0, 5, 5);
                    gbc_lblAxisPadding.gridx = 3;
                    gbc_lblAxisPadding.gridy = 1;
                    JLabel lblAxisPadding = new JLabel("Axis padding (%)");
                    Graphs.add(lblAxisPadding, gbc_lblAxisPadding);
                }
                {
                    GridBagConstraints gbc_containerBackground = new GridBagConstraints();
                    gbc_containerBackground.fill = GridBagConstraints.BOTH;
                    gbc_containerBackground.insets = new Insets(0, 0, 5, 5);
                    gbc_containerBackground.gridx = 1;
                    gbc_containerBackground.gridy = 2;
                    containerBackground.setActionCommand("containerBackground");
                    Graphs.add(containerBackground, gbc_containerBackground);
                }
                {
                    GridBagConstraints gbc_containerBackgroundPainted = new GridBagConstraints();
                    gbc_containerBackgroundPainted.fill = GridBagConstraints.VERTICAL;
                    gbc_containerBackgroundPainted.insets = new Insets(0, 0, 5, 5);
                    gbc_containerBackgroundPainted.gridx = 2;
                    gbc_containerBackgroundPainted.gridy = 2;
                    containerBackgroundPainted.setActionCommand("containerBackgroundPainted");
                    Graphs.add(containerBackgroundPainted, gbc_containerBackgroundPainted);
                }
                {
                    GridBagConstraints gbc_axesPadding = new GridBagConstraints();
                    gbc_axesPadding.fill = GridBagConstraints.VERTICAL;
                    gbc_axesPadding.insets = new Insets(0, 0, 5, 5);
                    gbc_axesPadding.gridx = 3;
                    gbc_axesPadding.gridy = 2;
                    axesPadding.setMaximumSize(new Dimension(50, 32767));
                    axesPadding.setEditable(true);
                    axesPadding.setActionCommand("axesPadding");
                    axesPadding.addItem(1d);
                    axesPadding.addItem(2d);
                    axesPadding.addItem(5d);
                    axesPadding.addItem(10d);
                    Graphs.add(axesPadding, gbc_axesPadding);
                }
                {
                    {
                        GridBagConstraints gbc_lblGraphBackground = new GridBagConstraints();
                        gbc_lblGraphBackground.anchor = GridBagConstraints.SOUTH;
                        gbc_lblGraphBackground.fill = GridBagConstraints.HORIZONTAL;
                        gbc_lblGraphBackground.insets = new Insets(0, 0, 5, 5);
                        gbc_lblGraphBackground.gridx = 1;
                        gbc_lblGraphBackground.gridy = 3;
                        JLabel lblGraphBackground = new JLabel("Graph background *");
                        Graphs.add(lblGraphBackground, gbc_lblGraphBackground);
                    }
                }
                GridBagConstraints gbc_tightAxes = new GridBagConstraints();
                gbc_tightAxes.anchor = GridBagConstraints.SOUTH;
                gbc_tightAxes.insets = new Insets(0, 0, 5, 5);
                gbc_tightAxes.gridx = 3;
                gbc_tightAxes.gridy = 3;
                padAxes.setSelected(true);
                padAxes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (padAxes.isSelected()) {
                            padAxes.setText("on");
                        } else {
                            padAxes.setText("off");
                        }
                    }
                });
                padAxes.setActionCommand("padAxes");
                Graphs.add(padAxes, gbc_tightAxes);
                {
                    GridBagConstraints gbc_viewBackground = new GridBagConstraints();
                    gbc_viewBackground.insets = new Insets(0, 0, 5, 5);
                    gbc_viewBackground.fill = GridBagConstraints.BOTH;
                    gbc_viewBackground.gridx = 1;
                    gbc_viewBackground.gridy = 4;
                    viewBackground.setActionCommand("viewBackground");
                    //Graphs.add(viewBackground, gbc_viewBackground);
                }
                {
                    GridBagConstraints gbc_viewBackgroundPainted = new GridBagConstraints();
                    gbc_viewBackgroundPainted.fill = GridBagConstraints.VERTICAL;
                    gbc_viewBackgroundPainted.insets = new Insets(0, 0, 5, 5);
                    gbc_viewBackgroundPainted.gridx = 2;
                    gbc_viewBackgroundPainted.gridy = 4;
                    viewBackgroundPainted.setActionCommand("viewBackgroundPainted");
                    Graphs.add(viewBackgroundPainted, gbc_viewBackgroundPainted);
                }
                {
                    GridBagConstraints gbc_lblForMultilayered = new GridBagConstraints();
                    gbc_lblForMultilayered.gridwidth = 3;
                    gbc_lblForMultilayered.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblForMultilayered.insets = new Insets(0, 0, 5, 5);
                    gbc_lblForMultilayered.gridx = 1;
                    gbc_lblForMultilayered.gridy = 5;
                    JLabel lblForMultilayered = new JLabel("<html><small>*For multi-layered graphs, this value will be applied only for the first. Superimposed graphs will have a transparent background.</small><html>");
                    lblForMultilayered.setVerticalAlignment(SwingConstants.TOP);
                    lblForMultilayered.setVerticalTextPosition(SwingConstants.TOP);
                    lblForMultilayered.setPreferredSize(new Dimension(100, 41));
                    Graphs.add(lblForMultilayered, gbc_lblForMultilayered);
                }
                {
                    GridBagConstraints gbc_colorComboBox = new GridBagConstraints();
                    gbc_colorComboBox.insets = new Insets(0, 0, 0, 5);
                    gbc_colorComboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_colorComboBox.gridx = 1;
                    gbc_colorComboBox.gridy = 7;
                    viewBackground.setActionCommand("viewBackground");
                    Graphs.add(viewBackground, gbc_colorComboBox);
                }
            }
            {
                JPanel Plots = new JPanel();
                tabbedPane.addTab("Plots", null, Plots, null);
                GridBagLayout gbl_Plots = new GridBagLayout();
                gbl_Plots.columnWidths = new int[]{5, 110, 80, 130, 5};
                gbl_Plots.rowHeights = new int[]{0, 20, 0, 30, 0, 0, 0, 0, 0, 0, 0};
                gbl_Plots.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0};
                gbl_Plots.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                Plots.setLayout(gbl_Plots);
                {
                    GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
                    gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
                    gbc_horizontalStrut.gridx = 4;
                    gbc_horizontalStrut.gridy = 1;
                    Component horizontalStrut = Box.createHorizontalStrut(20);
                    horizontalStrut.setPreferredSize(new Dimension(30, 0));
                    horizontalStrut.setMinimumSize(new Dimension(30, 0));
                    horizontalStrut.setMaximumSize(new Dimension(30, 32767));
                    Plots.add(horizontalStrut, gbc_horizontalStrut);
                }
                {
                    GridBagConstraints gbc_lblMarkers = new GridBagConstraints();
                    gbc_lblMarkers.gridwidth = 2;
                    gbc_lblMarkers.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblMarkers.insets = new Insets(0, 0, 5, 5);
                    gbc_lblMarkers.gridx = 1;
                    gbc_lblMarkers.gridy = 1;
                    JLabel lblMarkers = new JLabel("<html><strong>Markers</strong></html>");
                    lblMarkers.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblMarkers, gbc_lblMarkers);
                }
                {
                    GridBagConstraints gbc_lblLines = new GridBagConstraints();
                    gbc_lblLines.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblLines.insets = new Insets(0, 0, 5, 5);
                    gbc_lblLines.gridx = 3;
                    gbc_lblLines.gridy = 1;
                    JLabel lblLines = new JLabel("<html><strong>Lines</strong></html>");
                    lblLines.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblLines, gbc_lblLines);
                }
                {
                    GridBagConstraints gbc_lblSynbol = new GridBagConstraints();
                    gbc_lblSynbol.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblSynbol.insets = new Insets(0, 0, 5, 5);
                    gbc_lblSynbol.gridx = 1;
                    gbc_lblSynbol.gridy = 2;
                    JLabel lblSymbol = new JLabel("Symbol");
                    lblSymbol.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblSymbol, gbc_lblSynbol);
                }
                {
                    GridBagConstraints gbc_lblSize = new GridBagConstraints();
                    gbc_lblSize.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblSize.insets = new Insets(0, 0, 5, 5);
                    gbc_lblSize.gridx = 2;
                    gbc_lblSize.gridy = 2;
                    JLabel lblSize = new JLabel("Size");
                    lblSize.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblSize, gbc_lblSize);
                }
                {
                    GridBagConstraints gbc_lblColor_1 = new GridBagConstraints();
                    gbc_lblColor_1.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblColor_1.insets = new Insets(0, 0, 5, 5);
                    gbc_lblColor_1.gridx = 3;
                    gbc_lblColor_1.gridy = 2;
                    JLabel lblColor_1 = new JLabel("Color");
                    lblColor_1.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblColor_1, gbc_lblColor_1);
                }
                {
                    GridBagConstraints gbc_markerSymbol = new GridBagConstraints();
                    gbc_markerSymbol.fill = GridBagConstraints.BOTH;
                    gbc_markerSymbol.insets = new Insets(0, 0, 5, 5);
                    gbc_markerSymbol.gridx = 1;
                    gbc_markerSymbol.gridy = 3;
                    markerSymbol.setActionCommand("markerSymbol");
                    markerSymbol.addItem("Circle");
                    markerSymbol.addItem("Square");
                    markerSymbol.addItem("Triangle");
                    markerSymbol.addItem("Diamond");
                    markerSymbol.addItem("Inverted Triangle");
                    markerSymbol.addItem("Left Triangle");
                    markerSymbol.addItem("Right Triangle");
                    markerSymbol.addItem("Dot");
                    Plots.add(markerSymbol, gbc_markerSymbol);
                }
                {
                    GridBagConstraints gbc_markerSize = new GridBagConstraints();
                    gbc_markerSize.fill = GridBagConstraints.BOTH;
                    gbc_markerSize.insets = new Insets(0, 0, 5, 5);
                    gbc_markerSize.gridx = 2;
                    gbc_markerSize.gridy = 3;
                    markerSize.setMinimumSize(new Dimension(40, 25));
                    markerSize.setEditable(true);
                    markerSize.setActionCommand("markerSize");
                    markerSize.addItem(2d);
                    markerSize.addItem(3d);
                    markerSize.addItem(4d);
                    markerSize.addItem(5d);
                    markerSize.addItem(7.5d);
                    markerSize.addItem(10d);
                    Plots.add(markerSize, gbc_markerSize);
                }
                {
                    GridBagConstraints gbc_lineColor = new GridBagConstraints();
                    gbc_lineColor.fill = GridBagConstraints.BOTH;
                    gbc_lineColor.insets = new Insets(0, 0, 5, 5);
                    gbc_lineColor.gridx = 3;
                    gbc_lineColor.gridy = 3;
                    lineColor.setActionCommand("lineColor");
                    Plots.add(lineColor, gbc_lineColor);
                }
                {
                    GridBagConstraints gbc_lblEdge = new GridBagConstraints();
                    gbc_lblEdge.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblEdge.insets = new Insets(0, 0, 5, 5);
                    gbc_lblEdge.gridx = 1;
                    gbc_lblEdge.gridy = 4;
                    JLabel lblEdge = new JLabel("Edge Color");
                    lblEdge.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblEdge, gbc_lblEdge);
                }
                {
                    GridBagConstraints gbc_lblLineWeight = new GridBagConstraints();
                    gbc_lblLineWeight.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblLineWeight.insets = new Insets(0, 0, 5, 5);
                    gbc_lblLineWeight.gridx = 2;
                    gbc_lblLineWeight.gridy = 4;
                    JLabel lblLineWeight = new JLabel("<html><center>Edge Line<br>Weight</center></html>");
                    lblLineWeight.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblLineWeight, gbc_lblLineWeight);
                }
                {
                    GridBagConstraints gbc_lblWeight_1 = new GridBagConstraints();
                    gbc_lblWeight_1.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblWeight_1.insets = new Insets(0, 0, 5, 5);
                    gbc_lblWeight_1.gridx = 3;
                    gbc_lblWeight_1.gridy = 4;
                    JLabel lblWeight_1 = new JLabel("Weight");
                    lblWeight_1.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblWeight_1, gbc_lblWeight_1);
                }
                {
                    GridBagConstraints gbc_markerEdgeColor = new GridBagConstraints();
                    gbc_markerEdgeColor.fill = GridBagConstraints.BOTH;
                    gbc_markerEdgeColor.insets = new Insets(0, 0, 5, 5);
                    gbc_markerEdgeColor.gridx = 1;
                    gbc_markerEdgeColor.gridy = 5;
                    markerEdgeColor.setActionCommand("markerEdgeColor");
                    Plots.add(markerEdgeColor, gbc_markerEdgeColor);
                }
                {
                    GridBagConstraints gbc_markerEdgeWeight = new GridBagConstraints();
                    gbc_markerEdgeWeight.fill = GridBagConstraints.BOTH;
                    gbc_markerEdgeWeight.insets = new Insets(0, 0, 5, 5);
                    gbc_markerEdgeWeight.gridx = 2;
                    gbc_markerEdgeWeight.gridy = 5;
                    markerEdgeWeight.setEditable(true);
                    markerEdgeWeight.setMinimumSize(new Dimension(40, 25));
                    markerEdgeWeight.setActionCommand("markerEdgeWeight");
                    markerEdgeWeight.addItem(0.5f);
                    markerEdgeWeight.addItem(1f);
                    markerEdgeWeight.addItem(1.5f);
                    markerEdgeWeight.addItem(2f);
                    markerEdgeWeight.addItem(2.5f);
                    markerEdgeWeight.addItem(3f);
                    Plots.add(markerEdgeWeight, gbc_markerEdgeWeight);
                }
                {
                    GridBagConstraints gbc_lineWeight = new GridBagConstraints();
                    gbc_lineWeight.fill = GridBagConstraints.BOTH;
                    gbc_lineWeight.insets = new Insets(0, 0, 5, 5);
                    gbc_lineWeight.gridx = 3;
                    gbc_lineWeight.gridy = 5;
                    lineWeight.setEditable(true);
                    lineWeight.setActionCommand("lineWeight");
                    lineWeight.addItem(0.5f);
                    lineWeight.addItem(1f);
                    lineWeight.addItem(1.5f);
                    lineWeight.addItem(2f);
                    lineWeight.addItem(2.5f);
                    lineWeight.addItem(3f);
                    Plots.add(lineWeight, gbc_lineWeight);
                }
                {
                    GridBagConstraints gbc_lblFill = new GridBagConstraints();
                    gbc_lblFill.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblFill.insets = new Insets(0, 0, 5, 5);
                    gbc_lblFill.gridx = 1;
                    gbc_lblFill.gridy = 6;
                    JLabel lblFill = new JLabel("Fill Color");
                    lblFill.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblFill, gbc_lblFill);
                }
                {
                    GridBagConstraints gbc_lblStyle = new GridBagConstraints();
                    gbc_lblStyle.fill = GridBagConstraints.HORIZONTAL;
                    gbc_lblStyle.insets = new Insets(0, 0, 5, 5);
                    gbc_lblStyle.gridx = 3;
                    gbc_lblStyle.gridy = 6;
                    JLabel lblStyle = new JLabel("Style");
                    lblStyle.setHorizontalAlignment(SwingConstants.CENTER);
                    Plots.add(lblStyle, gbc_lblStyle);
                }
                {
                    GridBagConstraints gbc_markerFill = new GridBagConstraints();
                    gbc_markerFill.fill = GridBagConstraints.BOTH;
                    gbc_markerFill.insets = new Insets(0, 0, 5, 5);
                    gbc_markerFill.gridx = 1;
                    gbc_markerFill.gridy = 7;
                    markerFill.setActionCommand("markerFill");
                    Plots.add(markerFill, gbc_markerFill);
                }
                {
                    GridBagConstraints gbc_lineStyle = new GridBagConstraints();
                    gbc_lineStyle.fill = GridBagConstraints.BOTH;
                    gbc_lineStyle.insets = new Insets(0, 0, 5, 5);
                    gbc_lineStyle.gridx = 3;
                    gbc_lineStyle.gridy = 7;
                    lineStyle.setActionCommand("lineStyle");
                    lineStyle.addItem("Solid");
                    lineStyle.addItem("Dashed");
                    lineStyle.addItem("Dotted");
                    lineStyle.addItem("Dot-Dash");
                    Plots.add(lineStyle, gbc_lineStyle);
                }
                {
                    GridBagConstraints gbc_antiAliased = new GridBagConstraints();
                    gbc_antiAliased.fill = GridBagConstraints.HORIZONTAL;
                    gbc_antiAliased.insets = new Insets(0, 0, 5, 5);
                    gbc_antiAliased.gridx = 1;
                    gbc_antiAliased.gridy = 8;
                    antiAliased.setActionCommand("antiAliased");
                    antiAliased.setHorizontalAlignment(SwingConstants.CENTER);
                    antiAliased.setSelected(true);
                    Plots.add(antiAliased, gbc_antiAliased);
                }
                {
                    GridBagConstraints gbc_lblHighlight = new GridBagConstraints();
                    gbc_lblHighlight.gridwidth = 2;
                    gbc_lblHighlight.insets = new Insets(0, 0, 5, 5);
                    gbc_lblHighlight.gridx = 2;
                    gbc_lblHighlight.gridy = 8;
                    JLabel lblHighlight = new JLabel("Selection Highlight");
                    Plots.add(lblHighlight, gbc_lblHighlight);
                }
                {
                    GridBagConstraints gbc_comboBox = new GridBagConstraints();
                    gbc_comboBox.gridwidth = 2;
                    gbc_comboBox.insets = new Insets(0, 0, 0, 5);
                    gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
                    gbc_comboBox.gridx = 2;
                    gbc_comboBox.gridy = 9;
                    highlightColor.setActionCommand("highlightColor");
                    Plots.add(highlightColor, gbc_comboBox);
                }

            }
            {
                tabbedPane.addTab("Clipboard", null, clipboard, null);

            }

            JPanel Web = new JPanel();

            {
                tabbedPane.addTab("Web", null, Web, null);
                Web.setLayout(new BorderLayout(0, 0));
                JTabbedPane languageTop = new JTabbedPane(JTabbedPane.TOP);
                Web.add(languageTop);

                JPanel SVG = new JPanel();

                JTabbedPane language = new JTabbedPane(JTabbedPane.TOP);
                languageTop.addTab("SVG", null, SVG, null);

                GridBagLayout gbl_SVG = new GridBagLayout();
                gbl_SVG.columnWidths = new int[]{0, 0, 0, 0};
                gbl_SVG.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
                gbl_SVG.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
                gbl_SVG.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
                SVG.setLayout(gbl_SVG);
                {
                    GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
                    gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
                    gbc_btnNewButton_1.gridx = 1;
                    gbc_btnNewButton_1.gridy = 1;
                    JButton btnNewButton_1 = new JButton("");
                    btnNewButton_1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        }
                    });
                    btnNewButton_1.setToolTipText("If selected, SVG code will be included in the generated HTML file");
                    btnNewButton_1.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    SVG.add(btnNewButton_1, gbc_btnNewButton_1);
                }
                {
                    GridBagConstraints gbc_inline = new GridBagConstraints();
                    gbc_inline.fill = GridBagConstraints.HORIZONTAL;
                    gbc_inline.insets = new Insets(0, 0, 5, 0);
                    gbc_inline.gridx = 2;
                    gbc_inline.gridy = 1;
                    inline.setActionCommand("svgInline");
                    SVG.add(inline, gbc_inline);
                }
                {
                    GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
                    gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
                    gbc_btnNewButton_2.gridx = 1;
                    gbc_btnNewButton_2.gridy = 2;
                    JButton btnNewButton_2 = new JButton("");
                    btnNewButton_2.setToolTipText("If selected, a copy of canvg.js will be added to the created folder and the JavaScript needed to translate from SVG to HTML5 canvas graphics added to the generated HTML file.");
                    btnNewButton_2.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    SVG.add(btnNewButton_2, gbc_btnNewButton_2);
                }
                {
                    GridBagConstraints gbc_canvg = new GridBagConstraints();
                    gbc_canvg.fill = GridBagConstraints.HORIZONTAL;
                    gbc_canvg.insets = new Insets(0, 0, 5, 0);
                    gbc_canvg.gridx = 2;
                    gbc_canvg.gridy = 2;
                    canvg.setActionCommand("canvg");
                    SVG.add(canvg, gbc_canvg);
                }
                {
                    GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
                    gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
                    gbc_btnNewButton_3.gridx = 1;
                    gbc_btnNewButton_3.gridy = 3;
                    JButton btnNewButton_3 = new JButton("");
                    btnNewButton_3.setToolTipText("Location of the JavaScript. If \"./canvg-min.js\" is selected, the required file will be added to the generated folder.");
                    btnNewButton_3.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    SVG.add(btnNewButton_3, gbc_btnNewButton_3);
                }
                {
                    GridBagConstraints gbc_svgJSLoc = new GridBagConstraints();
                    gbc_svgJSLoc.insets = new Insets(0, 0, 5, 0);
                    gbc_svgJSLoc.fill = GridBagConstraints.HORIZONTAL;
                    gbc_svgJSLoc.gridx = 2;
                    gbc_svgJSLoc.gridy = 3;
                    svgJSLoc.setEditable(true);
                    svgJSLoc.setActionCommand("canvgLoc");
                    svgJSLoc.setModel(new DefaultComboBoxModel(new String[]{"./canvg-min.js"}));
                    SVG.add(svgJSLoc, gbc_svgJSLoc);
                }
                {
                    GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
                    gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
                    gbc_btnNewButton_4.gridx = 1;
                    gbc_btnNewButton_4.gridy = 5;
                    JButton btnNewButton_4 = new JButton("");
                    btnNewButton_4.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    SVG.add(btnNewButton_4, gbc_btnNewButton_4);
                }
                {
                    GridBagConstraints gbc_svgCSSLoc = new GridBagConstraints();
                    gbc_svgCSSLoc.insets = new Insets(0, 0, 5, 0);
                    gbc_svgCSSLoc.fill = GridBagConstraints.HORIZONTAL;
                    gbc_svgCSSLoc.gridx = 2;
                    gbc_svgCSSLoc.gridy = 5;
                    svgCSSLoc.setEditable(true);
                    svgCSSLoc.setActionCommand("svgCSSLoc");
                    svgCSSLoc.setModel(new DefaultComboBoxModel(new String[]{"./WSVGGraphics.css"}));
                    SVG.add(svgCSSLoc, gbc_svgCSSLoc);
                }
                {
                    GridBagConstraints gbc_httpd_svg = new GridBagConstraints();
                    gbc_httpd_svg.fill = GridBagConstraints.HORIZONTAL;
                    gbc_httpd_svg.gridx = 2;
                    gbc_httpd_svg.gridy = 6;
                    svgHTTPD.setActionCommand("svgHTTPD");
                    SVG.add(svgHTTPD, gbc_httpd_svg);
                }
                JPanel processing = new JPanel();
                {
                    processing.setToolTipText("<html>\nTo add a CSS file to the generated folder select <strong>\"Add to folder\"</strong><br>\nTo embed the CSS code in the HTML file select <strong>\"Add to index.html\"</strong><br>\nTo specify a relative file location or a URL\n</html>");
                    language = new JTabbedPane(JTabbedPane.TOP);
                    languageTop.addTab("Processing", null, processing, null);
                }
                GridBagLayout gbl_Processing = new GridBagLayout();
                gbl_Processing.columnWidths = new int[]{0, 0, 0, 0};
                gbl_Processing.rowHeights = new int[]{0, 21, -19, 45, 0, 0, 0, 0};
                gbl_Processing.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
                gbl_Processing.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                processing.setLayout(gbl_Processing);
                {
                    GridBagConstraints gbc_lblJavascriptLocation = new GridBagConstraints();
                    gbc_lblJavascriptLocation.anchor = GridBagConstraints.WEST;
                    gbc_lblJavascriptLocation.insets = new Insets(0, 0, 5, 0);
                    gbc_lblJavascriptLocation.gridx = 2;
                    gbc_lblJavascriptLocation.gridy = 2;
                    JLabel lblJavascriptLocation = new JLabel("JavaScript Location");
                    lblJavascriptLocation.setToolTipText("");
                    lblJavascriptLocation.setHorizontalAlignment(SwingConstants.LEFT);
                    processing.add(lblJavascriptLocation, gbc_lblJavascriptLocation);
                    javaScriptLocation.setModel(new DefaultComboBoxModel(new String[]{"./processing.js", "http://cloud.github.com/downloads/processing-js/processing-js/processing-1.4.1.min.js"}));
                    javaScriptLocation.setActionCommand("javaScriptLocation");
                    javaScriptLocation.setEditable(true);
                }
                {
                    GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
                    gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
                    gbc_btnNewButton.gridx = 1;
                    gbc_btnNewButton.gridy = 3;
                    JButton btnNewButton = new JButton("");
                    btnNewButton.setToolTipText("<html>\nTo add a copy of ProcessingJS to the generated folder, select\n<strong>\"./processing.js\"</strong><br>\nTo link to the GitHub copy via the web, select <strong>\"http://cloud.github.com/.../processing-1.4.1.min.js\"</strong><br>\nAlternatively, type a location relative to the intended folder on your website (e.g. ../../javascript/processing.js)<br>or using a URL.\n</html>");
                    btnNewButton.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    processing.add(btnNewButton, gbc_btnNewButton);
                }
                {
                    GridBagConstraints gbc_javaScriptLocation = new GridBagConstraints();
                    gbc_javaScriptLocation.insets = new Insets(0, 0, 5, 0);
                    gbc_javaScriptLocation.fill = GridBagConstraints.HORIZONTAL;
                    gbc_javaScriptLocation.gridx = 2;
                    gbc_javaScriptLocation.gridy = 3;
                    processing.add(javaScriptLocation, gbc_javaScriptLocation);
                }
                {
                    GridBagConstraints gbc_lblCss = new GridBagConstraints();
                    gbc_lblCss.anchor = GridBagConstraints.WEST;
                    gbc_lblCss.insets = new Insets(0, 0, 5, 0);
                    gbc_lblCss.gridx = 2;
                    gbc_lblCss.gridy = 4;
                    JLabel lblCss = new JLabel("Style sheet");
                    processing.add(lblCss, gbc_lblCss);
                }
                {
                    GridBagConstraints gbc_button = new GridBagConstraints();
                    gbc_button.insets = new Insets(0, 0, 5, 5);
                    gbc_button.gridx = 1;
                    gbc_button.gridy = 5;
                    JButton button = new JButton("");
                    button.setToolTipText("<html>\nTo add a default stylesheet to the generated folder, select <strong>\"./PDEGraphics2D.css\"</strong><br>\nTo embed default styling in the HTML file <strong>\"./index.html\"</strong><br>\nAlternatively, specify a custom style by typing a location relative to the intended folder on your website (e.g. ../../styling/myStyle.css)<br>or using a URL.\n</html>");
                    button.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    processing.add(button, gbc_button);
                }
                {
                    GridBagConstraints gbc_cssLocation = new GridBagConstraints();
                    gbc_cssLocation.insets = new Insets(0, 0, 5, 0);
                    gbc_cssLocation.fill = GridBagConstraints.HORIZONTAL;
                    gbc_cssLocation.gridx = 2;
                    gbc_cssLocation.gridy = 5;
                    cssLocation.setActionCommand("cssLocation");
                    cssLocation.setEditable(true);
                    cssLocation.setModel(new DefaultComboBoxModel(new String[]{"./PDEGraphics2D.css", "./index.html"}));
                    processing.add(cssLocation, gbc_cssLocation);
                }
                {
                    GridBagConstraints gbc_button_1 = new GridBagConstraints();
                    gbc_button_1.insets = new Insets(0, 0, 5, 5);
                    gbc_button_1.gridx = 1;
                    gbc_button_1.gridy = 6;
                    JButton button_1 = new JButton("");
                    button_1.setToolTipText("<html>\nIf checked, a Python 2.7 script file will be created in the generated folder<br>\nthat will display index.html in the system web browser using a local server.<br>\nThis allows the generated files to be viewed locally where system/browser<br>\nsecurity settings otherwise prevent access to the file system.\n</html>");
                    button_1.setIcon(new ImageIcon(PreferencesDialog.class.getResource("/kcl/waterloo/gui/images/information.png")));
                    processing.add(button_1, gbc_button_1);
                }
                {
                    GridBagConstraints gbc_httpd = new GridBagConstraints();
                    gbc_httpd.anchor = GridBagConstraints.WEST;
                    gbc_httpd.insets = new Insets(0, 0, 5, 0);
                    gbc_httpd.gridx = 2;
                    gbc_httpd.gridy = 6;
                    httpd.setHorizontalAlignment(SwingConstants.LEFT);
                    httpd.setActionCommand("httpd");
                    processing.add(httpd, gbc_httpd);
                }
                {
                    GridBagConstraints gbc_lblTheseSettingsControl = new GridBagConstraints();
                    gbc_lblTheseSettingsControl.fill = GridBagConstraints.VERTICAL;
                    gbc_lblTheseSettingsControl.anchor = GridBagConstraints.WEST;
                    gbc_lblTheseSettingsControl.gridx = 2;
                    gbc_lblTheseSettingsControl.gridy = 7;
                    JLabel lblTheseSettingsControl = new JLabel("<html>These settings control the deployment of graphics to the web using HTML5 and Processing JavaScript.\n<br>For details see <a href=\"http://processingjs.org\">http://processingjs.org</a><html>");
                    lblTheseSettingsControl.setMaximumSize(new Dimension(2147483647, 2147483647));
                    lblTheseSettingsControl.setMinimumSize(new Dimension(300, 50));
                    lblTheseSettingsControl.setPreferredSize(new Dimension(200, 50));
                    lblTheseSettingsControl.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
                    lblTheseSettingsControl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    processing.add(lblTheseSettingsControl, gbc_lblTheseSettingsControl);
                }
                {
                    tabbedPane.addTab("Session", null, session, null);
                }
            }
            GridBagLayout gbl_Session = new GridBagLayout();
            gbl_Session.columnWidths = new int[]{0, 0, 0, 0};
            gbl_Session.rowHeights = new int[]{50, 0, 0, 0, 0, 0};
            gbl_Session.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gbl_Session.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            session.setLayout(gbl_Session);
            {
                GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
                gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
                gbc_lblNewLabel_1.gridx = 1;
                gbc_lblNewLabel_1.gridy = 1;
                JLabel lblNewLabel_1 = new JLabel("<html>Session settings apply only to the current<br>Java Virtual Machine session.<br>They are not preserved across sessions.</html>");
                session.add(lblNewLabel_1, gbc_lblNewLabel_1);
            }
            {
                GridBagConstraints gbc_lblFont = new GridBagConstraints();
                gbc_lblFont.insets = new Insets(0, 0, 5, 5);
                gbc_lblFont.gridx = 1;
                gbc_lblFont.gridy = 3;
                JLabel lblFont = new JLabel("Data vector format");
                session.add(lblFont, gbc_lblFont);
            }
            {
                GridBagConstraints gbc_dataVectorFormat = new GridBagConstraints();
                gbc_dataVectorFormat.insets = new Insets(0, 0, 0, 5);
                gbc_dataVectorFormat.fill = GridBagConstraints.HORIZONTAL;
                gbc_dataVectorFormat.gridx = 1;
                gbc_dataVectorFormat.gridy = 4;
                JComboBox dataVectorFormat = new JComboBox();
                dataVectorFormat.setActionCommand("dataVectorFormat");
                dataVectorFormat.addItem("java.lang.Double[]");
                dataVectorFormat.addItem("java.lang.Float[]");
                dataVectorFormat.addItem("java.math.BigDecimal[]");
                dataVectorFormat.addItem("Primitive double[]");
                session.add(dataVectorFormat, gbc_dataVectorFormat);
            }
            GridBagLayout gbl_Clipboard = new GridBagLayout();
            gbl_Clipboard.columnWidths = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_Clipboard.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_Clipboard.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            gbl_Clipboard.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            clipboard.setLayout(gbl_Clipboard);
            {
                GridBagConstraints gbc_lblVectorFormats = new GridBagConstraints();
                gbc_lblVectorFormats.insets = new Insets(0, 0, 5, 5);
                gbc_lblVectorFormats.gridx = 1;
                gbc_lblVectorFormats.gridy = 1;
                JLabel lblVectorFormats = new JLabel("Vector Formats");
                clipboard.add(lblVectorFormats, gbc_lblVectorFormats);
            }
            {
                GridBagConstraints gbc_formatSVG = new GridBagConstraints();
                gbc_formatSVG.insets = new Insets(0, 0, 5, 5);
                gbc_formatSVG.gridx = 1;
                gbc_formatSVG.gridy = 2;
                formatSVG.setActionCommand("formatSVG");
                formatSVG.setSelected(true);
                clipboard.add(formatSVG, gbc_formatSVG);
            }
            {
                GridBagConstraints gbc_formatSVGAsText = new GridBagConstraints();
                gbc_formatSVGAsText.fill = GridBagConstraints.HORIZONTAL;
                gbc_formatSVGAsText.insets = new Insets(0, 0, 5, 5);
                gbc_formatSVGAsText.gridx = 3;
                gbc_formatSVGAsText.gridy = 2;
                formatSVGAsText.setActionCommand("formatSVGAsText");
                formatSVGAsText.setSelected(true);
                clipboard.add(formatSVGAsText, gbc_formatSVGAsText);
            }
            {
                GridBagConstraints gbc_formatEPS = new GridBagConstraints();
                gbc_formatEPS.insets = new Insets(0, 0, 5, 5);
                gbc_formatEPS.gridx = 1;
                gbc_formatEPS.gridy = 3;
                formatEPS.setActionCommand("formatEPS");
                formatEPS.setSelected(true);
                clipboard.add(formatEPS, gbc_formatEPS);
            }
            {
                GridBagConstraints gbc_formatPS = new GridBagConstraints();
                gbc_formatPS.fill = GridBagConstraints.HORIZONTAL;
                gbc_formatPS.insets = new Insets(0, 0, 5, 5);
                gbc_formatPS.gridx = 3;
                gbc_formatPS.gridy = 3;
                formatPS.setActionCommand("formatPS");
                formatPS.setSelected(true);
                clipboard.add(formatPS, gbc_formatPS);
            }
            {
                GridBagConstraints gbc_formatPDF = new GridBagConstraints();
                gbc_formatPDF.insets = new Insets(0, 0, 5, 5);
                gbc_formatPDF.anchor = GridBagConstraints.WEST;
                gbc_formatPDF.gridx = 5;
                gbc_formatPDF.gridy = 3;
                formatPDF.setActionCommand("formatPDF");
                formatPDF.setSelected(true);
                clipboard.add(formatPDF, gbc_formatPDF);
            }
            {
                GridBagConstraints gbc_lblTheAbilityTo = new GridBagConstraints();
                gbc_lblTheAbilityTo.fill = GridBagConstraints.HORIZONTAL;
                gbc_lblTheAbilityTo.anchor = GridBagConstraints.NORTH;
                gbc_lblTheAbilityTo.weighty = 1.0;
                gbc_lblTheAbilityTo.weightx = 1.0;
                gbc_lblTheAbilityTo.gridheight = 3;
                gbc_lblTheAbilityTo.gridwidth = 8;
                gbc_lblTheAbilityTo.insets = new Insets(0, 0, 5, 0);
                gbc_lblTheAbilityTo.gridx = 1;
                gbc_lblTheAbilityTo.gridy = 5;
                JLabel lblTheAbilityTo = new JLabel("<html><small>SVG must be selected to PDF output.<br>The ability to place data on the clipboard is subject<br>to your Java security settings.<br>Bitmap images will also be generated.<br>Availability of selected formats is system dependent.<small><html>");
                lblTheAbilityTo.setHorizontalTextPosition(SwingConstants.CENTER);
                lblTheAbilityTo.setVerticalTextPosition(SwingConstants.TOP);
                lblTheAbilityTo.setVerticalAlignment(SwingConstants.TOP);
                lblTheAbilityTo.setHorizontalAlignment(SwingConstants.LEFT);
                clipboard.add(lblTheAbilityTo, gbc_lblTheAbilityTo);
            }
            {
                GridBagConstraints gbc_lblBitmap = new GridBagConstraints();
                gbc_lblBitmap.gridwidth = 7;
                gbc_lblBitmap.insets = new Insets(0, 0, 5, 0);
                gbc_lblBitmap.gridx = 1;
                gbc_lblBitmap.gridy = 8;
                JLabel lblBitmap = new JLabel("<html><small></small><html>");
                lblBitmap.setHorizontalAlignment(SwingConstants.CENTER);
                clipboard.add(lblBitmap, gbc_lblBitmap);
            }
            GridBagConstraints gbc_previewPane = new GridBagConstraints();
            gbc_previewPane.fill = GridBagConstraints.BOTH;
            gbc_previewPane.gridx = 1;
            gbc_previewPane.gridy = 0;
            JPanel previewPane = new JPanel();
            contentPanel.add(previewPane, gbc_previewPane);
            GridBagLayout gbl_previewPane = new GridBagLayout();
            gbl_previewPane.columnWidths = new int[]{10, 200, 10, 0};
            gbl_previewPane.rowHeights = new int[]{50, 0, 0, 0};
            gbl_previewPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gbl_previewPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            previewPane.setLayout(gbl_previewPane);

            JLabel lblPreview = new JLabel("Preview");
            GridBagConstraints gbc_lblPreview = new GridBagConstraints();
            gbc_lblPreview.insets = new Insets(0, 0, 5, 5);
            gbc_lblPreview.gridx = 1;
            gbc_lblPreview.gridy = 0;
            previewPane.add(lblPreview, gbc_lblPreview);
            {
                GridBagConstraints gbc_previewPanel = new GridBagConstraints();
                gbc_previewPanel.insets = new Insets(0, 0, 5, 0);
                gbc_previewPanel.fill = GridBagConstraints.BOTH;
                gbc_previewPanel.gridx = 1;
                gbc_previewPanel.gridy = 1;
                previewPanel.setBorder(new LineBorder(Color.DARK_GRAY));
                previewPane.add(previewPanel, gbc_previewPanel);
            }
            previewPanel.setLayout(new BorderLayout(0, 0));

            {
                JPanel buttonPane = new JPanel();
                buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                getContentPane().add(buttonPane, BorderLayout.SOUTH);
                {
                    buttonPane.add(resetButton);
                    resetButton.setActionCommand("Reset");
                }
                {
                    loadButton.setActionCommand("Load");
                    buttonPane.add(loadButton);
                }
                {
                    buttonPane.add(Box.createHorizontalGlue());
                    buttonPane.add(Box.createVerticalGlue());
                    buttonPane.add(Box.createHorizontalStrut(50));
                }
                {
                    applyButton.setActionCommand("Apply");
                    buttonPane.add(applyButton);
                }
                {
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                    //getRootPane().setDefaultButton(cancelButton);
                }
                {
                    saveButton.setActionCommand("Save");
                    buttonPane.add(saveButton);
                }
            }
        }
    }

    public JCheckBox getAntialiased() {
        return antiAliased;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public GJColorComboBox getAxesColorCombo() {
        return axesColorCombo;
    }

    public JComboBox getAxisPadding() {
        return axesPadding;
    }

    public JComboBox getAxisWeightCombo() {
        return axisWeightCombo;
    }

    public JCheckBox getBottomAxisLabelled() {
        return bottomAxisLabelled;
    }

    public JCheckBox getBottomAxisPainted() {
        return bottomAxisPainted;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public GJColorComboBox getContainerBackground() {
        return containerBackground;
    }

    public JCheckBox getContainerBackgroundPainted() {
        return containerBackgroundPainted;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JCheckBox getFormatEPS() {
        return formatEPS;
    }

    public JCheckBox getFormatPDF() {
        return formatPDF;
    }

    public JCheckBox getFormatPS() {
        return formatPS;
    }

    public JCheckBox getFormatSVG() {
        return formatSVG;
    }

    public JCheckBox getFormatSVGAsText() {
        return formatSVGAsText;
    }

    public JCheckBox getInnerAxisLabelled() {
        return innerAxisLabelled;
    }

    public JCheckBox getInnerAxisPainted() {
        return innerAxisPainted;
    }

    public JCheckBox getLeftAxisLabelled() {
        return leftAxisLabelled;
    }

    public JCheckBox getLeftAxisPainted() {
        return leftAxisPainted;
    }

    public GJColorComboBox getLineColor() {
        return lineColor;
    }

    public JComboBox getLineStyle() {
        return lineStyle;
    }

    public JComboBox getLineWeight() {
        return lineWeight;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public GJColorComboBox getMajorGridColorCombo() {
        return majorGridColorCombo;
    }

    public JCheckBox getMajorGridPainted() {
        return majorGridPainted;
    }

    public JComboBox getMajorGridWeigthCombo() {
        return majorGridWeightCombo;
    }

    public GJColorComboBox getMarkerEdgeColor() {
        return markerEdgeColor;
    }

    public JComboBox getMarkerEdgeWeight() {
        return markerEdgeWeight;
    }

    public GJColorComboBox getMarkerFill() {
        return markerFill;
    }

    public JComboBox getMarkerSize() {
        return markerSize;
    }

    public JComboBox getMarkerSymbol() {
        return markerSymbol;
    }

    public GJColorComboBox getMinorGridColorCombo() {
        return minorGridColorCombo;
    }

    public JCheckBox getMinorGridPainted() {
        return minorGridPainted;
    }

    public JComboBox getMinorGridWeigthCombo() {
        return minorGridWeightCombo;
    }

    public JToggleButton getPadAxes() {
        return padAxes;
    }

    public JPanel getPreviewPanel() {
        return previewPanel;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JCheckBox getRightAxisLabelled() {
        return rightAxisLabelled;
    }

    public JCheckBox getRightAxisPainted() {
        return rightAxisPainted;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JCheckBox getTopAxisLabelled() {
        return topAxisLabelled;
    }

    public JCheckBox getTopAxisPainted() {
        return topAxisPainted;
    }

    public GJColorComboBox getViewBackground() {
        return viewBackground;
    }

    public JCheckBox getViewBackgroundPainted() {
        return viewBackgroundPainted;
    }

    public JSpinner getxDivSpinner() {
        return xDivSpinner;
    }

    public JSpinner getXDivSpinner() {
        return xDivSpinner;
    }

    public JSpinner getyDivSpinner() {
        return yDivSpinner;
    }

    public JSpinner getYDivSpinner() {
        return yDivSpinner;
    }

    public GJColorComboBox getHighlightColor() {
        return highlightColor;
    }

    public JComboBox getCssLocation() {
        return cssLocation;
    }

    public JCheckBox getHttpd() {
        return httpd;
    }

    public JComboBox getJavaScriptLocation() {
        return javaScriptLocation;
    }

    public JCheckBox getInline() {
        return inline;
    }

    public JCheckBox getCanvg() {
        return canvg;
    }

    public JComboBox getSvgCSSLoc() {
        return svgCSSLoc;
    }

    public JComboBox getSvgJSLoc() {
        return svgJSLoc;
    }

    /**
     * @return the svgHTTPD
     */
    public JCheckBox getSvgHTTPD() {
        return svgHTTPD;
    }
}
