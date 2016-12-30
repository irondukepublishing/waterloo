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
package kcl.waterloo.serviceproviders;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import kcl.waterloo.gui.images.Images;
import org.jdesktop.swingx.HorizontalLayout;

public class ToolBar extends JPanel {

    /**
     *
     */
    private final JButton saveButton = new JButton("");
    private final JButton saveAsButton = new JButton("");
    private final JButton deployButton = new JButton("");
    private final JButton refreshButton = new JButton("");
    private final JButton openButton = new JButton("");
    private final JButton printButton = new JButton("");
    private final JButton copyButton = new JButton("");
    private final JButton copyAsImageButton = new JButton("");
    private final JButton defaultsButton = new JButton("");
    private final JButton editPlotsButton = new JButton("");
    private final JButton annotateButton = new JButton("");
    private final JButton startRecording = new JButton("");
    private final JButton pauseRecording = new JButton("");
    private final JButton stopRecording = new JButton("");
    private final JButton saveRecording = new JButton("");
    private final JComboBox recordInterval = new JComboBox();

    /**
     * Create the panel.
     */
    public ToolBar(Component gr) {

        setBorder(new LineBorder(new Color(0, 0, 0)));
        setLayout(new HorizontalLayout());

        JToolBar toolBar = new JToolBar();
        add(toolBar);

        openButton.setToolTipText("Open file");
        openButton.setActionCommand("Open Graph");
        openButton.setIcon(Images.getIcon("book_open.png"));
        toolBar.add(openButton);

        saveButton.setToolTipText("Save");
        saveButton.setActionCommand("Save Graph");
        saveButton.setIcon(Images.getIcon("disk.png"));
        toolBar.add(saveButton);

        saveAsButton.setToolTipText("Save As....");
        saveAsButton.setActionCommand("Save Graph As");
        saveAsButton.setIcon(Images.getIcon("save_as.png"));
        toolBar.add(saveAsButton);

        deployButton.setToolTipText("Deploy to web");
        deployButton.setActionCommand("Deploy");
        deployButton.setIcon(Images.getIcon("world.png"));
        toolBar.add(deployButton);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_1);

        copyButton.setToolTipText("Copy");
        copyButton.setActionCommand("Copy");
        copyButton.setIcon(Images.getIcon("page_copy.png"));
        toolBar.add(copyButton);

        copyAsImageButton.setToolTipText("Copy As Image");
        copyAsImageButton.setActionCommand("Copy As Image");
        copyAsImageButton.setIcon(Images.getIcon("camera_go.png"));
        toolBar.add(copyAsImageButton);

        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_2);
        printButton.setToolTipText("Print");
        printButton.setActionCommand("Print Graph");
        printButton.setIcon(Images.getIcon("printer.png"));
        toolBar.add(printButton);

        Component horizontalStrut_3 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut_3);
        toolBar.addSeparator();
        startRecording.setToolTipText("Start Recording");
        startRecording.setActionCommand("Start Recording");
        startRecording.setIcon(Images.getIcon("film.png"));
        toolBar.add(startRecording);
        pauseRecording.setToolTipText("Pause Recording");
        pauseRecording.setActionCommand("Pause Recording");
        pauseRecording.setIcon(Images.getIcon("control_pause.png"));
        pauseRecording.setSelected(false);
        toolBar.add(pauseRecording);
        stopRecording.setToolTipText("Stop Recording");
        stopRecording.setActionCommand("Stop Recording");
        stopRecording.setIcon(Images.getIcon("film_delete.png"));
        toolBar.add(stopRecording);
        recordInterval.addItem("500ms");
        recordInterval.addItem("200ms");
        recordInterval.addItem("100ms");
        recordInterval.addItem("50ms");
        recordInterval.addItem("20ms");
        recordInterval.setSelectedItem("100ms");
        recordInterval.setActionCommand("Recording Interval");
        recordInterval.setToolTipText("Recording Interval");
        //recordInterval.setPreferredSize(new Dimension(90, 45));
        toolBar.add(recordInterval);
        saveRecording.setToolTipText("Save Recording or Create File");
        saveRecording.setActionCommand("Save Recording");
        saveRecording.setIcon(Images.getIcon("film_go.png"));
        toolBar.add(saveRecording);
        toolBar.addSeparator();

        Component horizontalStrut_4 = Box.createHorizontalStrut(40);
        toolBar.add(horizontalStrut_4);
        defaultsButton.setToolTipText("Edit Defaults");
        defaultsButton.setActionCommand("Edit Defaults");
        defaultsButton.setIcon(Images.getIcon("key.png"));
        toolBar.add(defaultsButton);

//        toolBar.add(horizontalStrut_4);
//        editPlotsButton.setIcon(new ImageIcon(ToolBar.class.getResource("/kcl/waterloo/gui/images/database_table.png")));
//        editPlotsButton.setToolTipText("Edit plots");
//
//        toolBar.add(editPlotsButton);
//        annotateButton.setToolTipText("Annotate");
//        annotateButton.setIcon(new ImageIcon(ToolBar.class.getResource("/kcl/waterloo/gui/images/chart_bar_edit.png")));
//        toolBar.add(annotateButton);
//
//        refreshButton.setToolTipText("Refresh graph");
//        toolBar.add(refreshButton);
//        refreshButton.setIcon(new ImageIcon(ToolBar.class.getResource("/kcl/waterloo/gui/images/arrow_refresh.png")));
    }

    public JButton getOpenButton() {
        return openButton;
    }

    public JButton getPrintButton() {
        return printButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * @return the editPlotsButton
     */
    public JButton getEditPlotsButton() {
        return editPlotsButton;
    }

    /**
     * @return the saveAsButton
     */
    public JButton getSaveAsButton() {
        return saveAsButton;
    }

    /**
     * @return the annotateButton
     */
    public JButton getAnnotateButton() {
        return annotateButton;
    }

    /**
     * @return the saveButton
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    /**
     * @return the copyButton
     */
    protected JButton getCopyButton() {
        return copyButton;
    }

    /**
     * @return the copyAsImageButton
     */
    protected JButton getCopyAsImageButton() {
        return copyAsImageButton;
    }

    /**
     * @return the deployButton
     */
    public JButton getDeployButton() {
        return deployButton;
    }

    public JButton getDefaultsButton() {
        return defaultsButton;
    }

    /**
     * @return the startRecording
     */
    public JButton getStartRecording() {
        return startRecording;
    }

    /**
     * @return the stopRecording
     */
    public JButton getStopRecording() {
        return stopRecording;
    }

    /**
     * @return the recordInterval
     */
    public JComboBox getRecordInterval() {
        return recordInterval;
    }

    /**
     * @return the saveRecording
     */
    public JButton getSaveRecording() {
        return saveRecording;
    }

    /**
     * @return the pauseRecording
     */
    public JButton getPauseRecording() {
        return pauseRecording;
    }
}
