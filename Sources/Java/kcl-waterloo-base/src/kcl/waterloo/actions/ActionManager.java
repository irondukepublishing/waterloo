 /*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.actions;

import kcl.waterloo.common.deploy.GJSequencerInterface;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.deploy.gif.GJGifSequencer;
import kcl.waterloo.graphics.*;
import kcl.waterloo.serviceproviders.ToolBar;
import kcl.waterloo.swing.GCFrame;
import kcl.waterloo.swing.GCGridContainerInterface;
import kcl.waterloo.xml.FileWrapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import kcl.waterloo.gui.file.AnimationFileChooser;

/**
 * This class provides a static method for processing action events for common
 * menu and button actions used in various Waterloo project packages.
 *
 * Unlike the methods in ActionServices which it calls, ActionManager does not
 * return any references to Waterloo objects. Potentially lengthy actions are
 * submitted to a background thread so these are non-blocking and the worker is
 * returned - users can test that for thread completion. Note that, in some
 * cases, the worker doInBackground returns another worker from the call to
 * ActionServices.
 *
 * This class is intended primarily for use within Waterloo code - but may also
 * be useful in user-developed GUIs.
 *
 * @author Malcolm Lidierth
 */
public class ActionManager {

    private static final LinkedHashMap<Component, Timer> TIMERLOOKUP = new LinkedHashMap<Component, Timer>();
    private static final LinkedHashMap<Component, GJSequencerInterface> SEQUENCERLOOKUP = new LinkedHashMap<Component, GJSequencerInterface>();

    private ActionManager() {
    }

    /**
     * A static method for processing action events.
     *
     * @param ae ActionEvent
     * @param thisComponent Relevant Component
     * @return a java.lang.Object instance
     */
    public static SwingWorker<?, ?> processAction(ActionEvent ae, final Component thisComponent) {

        String command = ae.getActionCommand();

        ToolBar toolBar = null;
        if (ae.getSource() instanceof JButton) {
            toolBar = ((ToolBar) ((JButton) ae.getSource()).getParent().getParent());
        }

        if (command.matches("Open Graph")) {
            SwingWorker<FileWrapper, Void> worker = new SwingWorker<FileWrapper, Void>() {
                /**
                 * Loads the file on a background thread.
                 */
                @Override
                protected FileWrapper doInBackground() throws Exception {
                    return ActionServices.open();
                }

                /**
                 * Build the output on the EDT.
                 */
                @Override
                public void done() {

                    Object o;
                    try {
                        o = get().getContents();
                    } catch (InterruptedException ex) {
                        return;
                    } catch (ExecutionException ex) {
                        return;
                    }
                    if (o instanceof GJAbstractGraphContainer) {
                        GCFrame f = new GCFrame();
                        f.add((GJAbstractGraphContainer) o);
                    } else if (o instanceof GJAbstractGraph) {
                        GJGraphContainer gr;
                        if (((GJGraphInterface) o).getGraphContainer() != null) {
                            gr = (GJGraphContainer) ((GJAbstractGraph) o).getGraphContainer();
                        } else {
                            gr = GJGraphContainer.createInstance((GJAbstractGraph) o);
                        }
                        GCFrame f = new GCFrame();
                        f.add(gr);
                    } else if (o instanceof GCGridContainerInterface) {
                        GCFrame f = new GCFrame();
                        f.setContentPane((Container) o);
                    } else if (o instanceof ArrayList) {
                        for (Object o2 : (ArrayList) o) {
                            if (o2 instanceof GJAbstractGraphContainer) {
                                GCFrame f = new GCFrame();
                                f.add((GJAbstractGraphContainer) o2);
                            }
                        }
                    }
                }
            };

            worker.execute();
            return worker;
        } else if (command.matches("Save Graph")) {
            SwingWorker<FileWrapper, Void> worker = new SwingWorker<FileWrapper, Void>() {
                @Override
                protected FileWrapper doInBackground() throws Exception {
                    return ActionServices.save(thisComponent);
                }
            };
            worker.execute();
            return worker;
        } else if (command.matches("Save Graph As") || command.contains("Save As")) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ActionServices.saveAs(thisComponent);
                    return null;
                }
            };
            worker.execute();
            return worker;
        } else if (command.matches("Deploy")) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ActionServices.deployAs(thisComponent);
                    return null;
                }
            };
            worker.execute();
            return worker;
        } else if (command.matches("Copy")) {
            if (thisComponent instanceof GJBasicPanel) {
                SwingWorker<SwingWorker<Boolean, Void>, Void> worker = new SwingWorker<SwingWorker<Boolean, Void>, Void>() {
                    @Override
                    protected SwingWorker<Boolean, Void> doInBackground() throws Exception {
                        return ActionServices.copyGraphics((GJBasicPanel) thisComponent);
                    }
                };
                worker.execute();
                return worker;
            }
        } else if (command.matches("Copy As Image")) {
            if (thisComponent instanceof GJBasicPanel) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        ActionServices.copyAsImage((GJBasicPanel) thisComponent);
                        return null;
                    }
                };
                worker.execute();
                return worker;

            }
        } else if (command.matches("Print Graph")) {
            if (thisComponent instanceof GJBasicPanel) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        ((GJBasicPanel) thisComponent).print();
                        return null;
                    }
                };
                worker.execute();
                return worker;
            }
        } else if (command.matches("Edit Defaults")) {
            if (thisComponent instanceof GJBasicPanel) {
                GJDefaults.editDefaults();
            }
        } else if (command.matches("Start Recording")) {
            if (toolBar != null) {
                if (TIMERLOOKUP.containsKey(thisComponent)) {
                    // Start a recording already associated with a file
                    TIMERLOOKUP.get(thisComponent).start();
                    toolBar.getStartRecording().setSelected(true);
                } else {
                    // Create a memory cached writer
                    String interval = (String) toolBar.getRecordInterval().getSelectedItem();
                    double ninterval = Double.valueOf(interval.replaceFirst("ms", ""));
                    GJGifSequencer sequencer;
                    try {
                        sequencer = GJGifSequencer.createInstance(thisComponent, (int) ninterval, true);
                        Timer timer = new Timer((int) ninterval, sequencer);
                        timer.start();
                        TIMERLOOKUP.put(thisComponent, timer);
                        SEQUENCERLOOKUP.put(thisComponent, sequencer);
                    } catch (IOException ex) {
                    }
                    toolBar.getStartRecording().setSelected(true);
                }
            }
        } else if (command.matches("Pause Recording")) {
            if (toolBar != null) {
                if (TIMERLOOKUP.containsKey(thisComponent)) {
                    if (TIMERLOOKUP.get(thisComponent).isRunning()) {
                        TIMERLOOKUP.get(thisComponent).stop();
                        toolBar.getPauseRecording().setSelected(true);
                    } else {
                        TIMERLOOKUP.get(thisComponent).start();
                        toolBar.getPauseRecording().setSelected(false);
                    }
                }
            }
        } else if (command.matches("Stop Recording")) {
            // Stop and remove a timer
            if (toolBar != null) {
                if (TIMERLOOKUP.containsKey(thisComponent)) {
                    TIMERLOOKUP.get(thisComponent).stop();
                    TIMERLOOKUP.remove(thisComponent);
                    toolBar.getStartRecording().setSelected(false);
                }
                // If file-based, close the stream
                if (SEQUENCERLOOKUP.containsKey(thisComponent)) {
                    if (!((GJGifSequencer) SEQUENCERLOOKUP.get(thisComponent)).isMemoryCached()) {
                        try {
                            ((GJGifSequencer) SEQUENCERLOOKUP.get(thisComponent)).close();
                            SEQUENCERLOOKUP.remove(thisComponent);
                            toolBar.getSaveRecording().setSelected(false);
                        } catch (IOException ex) {
                        }
                    }
                }
                toolBar.getPauseRecording().setSelected(false);
                toolBar.getStopRecording().setSelected(false);
            }
        } else if (command.matches("Save Recording")) {
            if (toolBar != null) {
                if (SEQUENCERLOOKUP.containsKey(thisComponent)) {
                    if (((GJGifSequencer) SEQUENCERLOOKUP.get(thisComponent)).isMemoryCached()) {
                        String filename;
                        if (ActionServices.isUseChooser()) {
                            AnimationFileChooser chooser = AnimationFileChooser.getInstance();
                            int val = chooser.showOpenDialog(null);
                            if (val != javax.swing.JFileChooser.APPROVE_OPTION) {
                                return null;
                            }
                            filename = chooser.getSelectedFile().getPath();
                        } else {
                            FileDialog chooser = new FileDialog((Frame) null, "Save GIF", FileDialog.SAVE);
                            chooser.setFilenameFilter(new GIFSaveFilter());
                            chooser.setVisible(true);
                            filename = chooser.getFile();

                            if (filename != null && !filename.isEmpty()) {
                                if (!filename.toLowerCase(Locale.getDefault()).endsWith(".gif")) {
                                    filename = filename.concat(".gif");
                                }
                                filename = new File(chooser.getDirectory(), filename).getPath();
                            }
                        }
                        try {
                            ((GJGifSequencer) SEQUENCERLOOKUP.get(thisComponent)).close(filename);
                            SEQUENCERLOOKUP.remove(thisComponent);
                        } catch (IOException ex) {
                        }
                    }
                    ((JButton) ae.getSource()).setSelected(false);
                }
            }
        } else {
            // Create a file cached stream
            ((JButton) ae.getSource()).setSelected(true);
            FileDialog chooser = new FileDialog((Frame) null, "Save GIF", FileDialog.SAVE);
            chooser.setFilenameFilter(new GIFSaveFilter());
            chooser.setVisible(true);
            String filename = chooser.getFile();
            if (filename != null && !filename.isEmpty()) {
                if (!filename.toLowerCase().endsWith(".gif")) {
                    filename = filename.concat(".gif");
                }
                String interval = (String) ((ToolBar) ((JButton) ae.getSource()).getParent().getParent()).getRecordInterval().getSelectedItem();
                interval = interval.replaceFirst("ms", "");
                double ninterval = Double.valueOf(interval);
                GJGifSequencer sequencer;
                try {
                    sequencer = GJGifSequencer.createInstance(filename, thisComponent, (int) ninterval, true);
                    Timer timer = new Timer((int) ninterval, sequencer);
                    timer.start();
                    TIMERLOOKUP.put(thisComponent, timer);
                    SEQUENCERLOOKUP.put(thisComponent, sequencer);
                } catch (IOException ex) {
                }
            }
        }

        return null;
    }

    private static class GIFSaveFilter implements FilenameFilter {

        private GIFSaveFilter() {
        }

        @Override
        public boolean accept(File f, String s) {
            String extension = ActionServices.getExtension(s);
            if (extension != null) {
                return extension.equals("gif");
            } else {
                return false;
            }
        }

    }
}
