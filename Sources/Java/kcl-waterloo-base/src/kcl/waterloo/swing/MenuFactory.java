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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class MenuFactory {

    private static JMenuBar applicationMenuBar = null;

    private MenuFactory() {
    }

    /**
     * @return the applicationMenuBar
     */
    public static JMenuBar getApplicationMenuBar() {
        return applicationMenuBar;
    }

    /**
     * @param aApplicationMenuBar the applicationMenuBar to set
     */
    public static void setApplicationMenuBar(JMenuBar aApplicationMenuBar) {
        applicationMenuBar = aApplicationMenuBar;
    }

    static JMenuBar createMenu(ActionListener actionListener) {

        int acceleratorKey, copyKey;
        boolean useMnemonics = false;

        if (System.getProperty("os.name").contains("Mac")) {
            acceleratorKey = ActionEvent.META_MASK;
            copyKey = ActionEvent.META_MASK;
        } else {
            useMnemonics = true;
            acceleratorKey = ActionEvent.ALT_MASK;
            copyKey = ActionEvent.CTRL_MASK;
        }

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the File menu.
        menu = new JMenu("File");
        if (useMnemonics) {
            menu.setMnemonic(KeyEvent.VK_F);
        }

        menuItem = menu.add(new JMenuItem("Open"));
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, acceleratorKey));
        menuItem.setActionCommand("Open Graph");
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Save"));
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, acceleratorKey));
        menuItem.setActionCommand("Save Graph");
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Save As..."));
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, acceleratorKey));
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Deploy to Web"));
        menuItem.setActionCommand("Deploy");
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Close"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Print"));
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, acceleratorKey));
        menuItem.addActionListener(actionListener);

        menuItem = menu.add(new JMenuItem("Print Preview"));
        menuItem.setMnemonic(KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, acceleratorKey));
        menuItem.addActionListener(actionListener);

        menuBar.add(menu);

        //Build the Edit menu.
        menu = new JMenu("Edit");
        if (useMnemonics) {
            menu.setMnemonic(KeyEvent.VK_E);
        }
        menuItem = menu.add(new JMenuItem("Copy Frame"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, acceleratorKey));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, copyKey));
        menuItem.addActionListener(actionListener);

        //Build the Grid menu.
        JMenu submenu = new JMenu("Grid");
        menuItem = submenu.add(new JMenuItem("Append Column"));
        menuItem.addActionListener(actionListener);
        menuItem = submenu.add(new JMenuItem("Append Row"));
        menuItem.addActionListener(actionListener);
        menu.add(submenu);
        menuBar.add(menu);

        //Build the Window menu.
        menu = new JMenu("Window");
        if (useMnemonics) {
            menu.setMnemonic(KeyEvent.VK_W);
        }
        menuBar.add(menu);
        return menuBar;
    }
}
