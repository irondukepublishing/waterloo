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

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A singleton instance class for managing WFrames in a JVM session.
 * {@code FrameManager} maintains a hash map of WFrames and coordinates the
 * Window menu for the frames.
 * <p/>
 * The {@code FrameManager} instance implements the WindowListener interface and
 * is added as a listener to each {@code GCFrame} instance in the
 * {@code GCFrame} constructor.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
class FrameManager implements WindowListener, ActionListener {

    final static FrameManager instance = new FrameManager();
    private final static LinkedHashMap<Double, GCFrameInterface> frameMap = new LinkedHashMap<Double, GCFrameInterface>();

    /**
     * Private constructor. Singleton instance class.
     */
    private FrameManager() {
    }

    /**
     * Instance getter
     *
     * @return the singleton instance.
     */
    public static FrameManager getInstance() {
        return instance;
    }

    static LinkedHashMap<Double, GCFrameInterface> getList() {
        return frameMap;
    }

    /**
     * Returns an array of all existing WFrames as a Window[].
     *
     * @return a GCFrameInterface[]
     */
    public static Window[] getWindows() {
        GCFrameInterface[] w = new GCFrameInterface[frameMap.size()];
        w = frameMap.values().toArray(w);
        return (Window[]) w;
    }

    /**
     * Returns the lowest numbered available "slot" for a new GCFrame.
     *
     * @return {@code int} value for GCFrame number.
     */
    static int getLowestAvailable() {
        for (int k = 0; k < frameMap.size(); k++) {
            if (!frameMap.containsKey(new Double(k))) {
                return k;
            }
        }
        return frameMap.size();
    }

    /**
     * Creates/replaces the Window menu for each {@code GCFrame} instance when a
     * new {@code GCFrame} is created or when one is disposed of.
     */
    private static void updateWindowMenu() {
        JMenu menu;
        JMenuItem menuItem;
        Double[] arr = new Double[0];
        arr = frameMap.keySet().toArray(arr);
        Arrays.sort(arr);
        for (Double arr1 : arr) {
            menu = new JMenu("Window");
            for (Double arr2 : arr) {
                menuItem = menu.add(new JMenuItem(frameMap.get(arr2).getTitle()));
                menuItem.addActionListener(frameMap.get(arr2));
            }
            List mArray = Arrays.asList(frameMap.get(arr1).getJMenuBar().getComponents());
            for (Object c : mArray) {
                JMenu m = (JMenu) c;
                if (m.getText().matches("Window")) {
                    frameMap.get(arr1).getJMenuBar().remove(m);
                    menu.addSeparator();
                    menuItem = menu.add(new JMenuItem("Close All"));
                    menuItem.addActionListener(instance);
                    frameMap.get(arr1).getJMenuBar().add(menu);
                }
            }
        }
        if (MenuFactory.getApplicationMenuBar() != null) {
            menu = new JMenu("Frame");
            for (Double arr1 : arr) {
                menuItem = menu.add(new JMenuItem(frameMap.get(arr1).getTitle()));
                menuItem.addActionListener(frameMap.get(arr1));
            }
            menu.addSeparator();
            menuItem = menu.add(new JMenuItem("Close All"));
            menuItem.addActionListener(instance);
            MenuFactory.getApplicationMenuBar().add(menu);
        }
    }

    // WindowListener interface
    @Override
    public void windowOpened(WindowEvent we) {
        updateWindowMenu();
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
        if (frameMap.containsValue(we.getWindow())) {
            for (Double index : frameMap.keySet()) {
                if (frameMap.get(index).equals(we.getWindow())) {
                    frameMap.remove(index);
                    updateWindowMenu();
                    return;
                }
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (GCFrameInterface f : frameMap.values()) {
            f.dispose();
        }
        frameMap.clear();
    }
}
