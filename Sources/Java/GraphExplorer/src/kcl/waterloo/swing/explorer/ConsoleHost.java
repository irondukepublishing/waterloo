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
package kcl.waterloo.swing.explorer;

import groovy.ui.Console;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * ConsoleHost contains and modifies a copy of the groovy.ui.Console.
 *
 * It provides a GUI for viewing:<p>the hierarchy of Java Swing components</p>
 * <p>their contents</p><p>and<p>their properties.</p>
 *
 * ConsoleHost is not fully functional. The class, and others in the package are
 * intended to be sub-classed for more specific purposes as, for example, with
 * the Waterloo Graphics Explorer. For that reason, most items implementing
 * actions (e.g. toolbars and menus) do nothing here.
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ConsoleHost  {

    protected final Console console;
    protected TopPanel topPanel;
    private int counter=0;

    /**
     * Constructor. Requires an instance of the groovy console or a subclass of
     * it on input.
     */
    public ConsoleHost(Console console) {
        this.console = console;
    }

    /**
     * Static factory constructor. Uses the standard Groovy Console.
     * @return 
     */
    public static ConsoleHost createInstance() {
        ConsoleHost host = new ConsoleHost(new groovy.ui.Console());
        SwingDelegates.metaChange(host.console);
        host.console.run();
        host.topPanel = translateConsole(host.console);
        host.addNewMenus();
        return host;
    }

    public void addTab(Object newObj) {
        addTab(newObj, "Component");
    }

    public void addTab(final Object newObj, final String s) {
        if (newObj != null) {
            if (newObj instanceof Component) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int tab = getTopPanel().getTabPane().getTabCount();
                        getTopPanel().getTabPane().add((Component) newObj, s + counter++);
                        ((Component) newObj).validate();
                        ((Component) newObj).repaint();
                        getTopPanel().getTabPane().setSelectedIndex(tab);
                    }
                });
            }
        }
    }

    protected void addNewMenus() {




        JMenuBar menuBar = getFrame().getJMenuBar();
        JMenu fileMenu = menuBar.getMenu(0);
        Component[] menus = menuBar.getComponents();
        Component[] fileItems = fileMenu.getMenuComponents();

//        JMenuItem newOpen = fileMenu.add(getAction("Open"));
//        JMenuItem newSave = fileMenu.add(getAction("Save"));
//        JMenuItem newClose = fileMenu.add(getAction("Close"));
//        JMenuItem newPrint = fileMenu.add(getAction("Print"));

        JMenu scriptMenu = new JMenu("Groovy Scripting");
        for (int k = 0; k < fileItems.length; k++) {
            scriptMenu.add(fileItems[k]);
        }
        for (int k = 1; k < menus.length; k++) {
            scriptMenu.add(menus[k]);
        }
        //scriptMenu.add(menus[menus.length-1]);
        menuBar.add(scriptMenu);

    }

    public JFrame getFrame() {
        return (JFrame) console.getFrame();
    }

    public boolean isVisible() {
        if (getFrame() == null) {
            return false;
        } else {
            return getFrame().isVisible();
        }
    }

    /**
     * @return the groovy.ui.ConsoleHost instance
     */
    public groovy.ui.Console getConsole() {
        return console;
    }

    protected static TopPanel translateConsole(final groovy.ui.Console thisConsole) {

        final TopPanel topPanel = new TopPanel();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                //Create a split pane in the lower component of the standard console's
                // splitpane
                JSplitPane newSplitPane = (JSplitPane) new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                JSplitPane existingSplitPane = (JSplitPane) thisConsole.getSplitPane();
                existingSplitPane.setBottomComponent(newSplitPane);

                // Place the editor on the left of the new split pane and transfer
                // the toolbar to it.
                // Create a host for the toolbar/editor
                JPanel editorHost = new JPanel(new BorderLayout());
                editorHost.add(thisConsole.getToolbar(), BorderLayout.NORTH);
                editorHost.add(existingSplitPane.getTopComponent(), BorderLayout.CENTER);
                newSplitPane.setLeftComponent(editorHost);
                // Place the output frame on the right
                JScrollPane scrollPane = new JScrollPane(thisConsole.getOutputArea());
                scrollPane.setPreferredSize(new Dimension(32767, 32767));
                newSplitPane.setRightComponent(scrollPane);

                // Put a viewer for the graphics in the top component of the main
                // spilt pane

                existingSplitPane.setTopComponent(topPanel);

                existingSplitPane.setContinuousLayout(true);
                newSplitPane.setContinuousLayout(true);

                JFrame frame = (JFrame) thisConsole.getFrame();
                if (frame != null) {
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    frame.setSize((int) (dim.getWidth() * 0.75), (int) (dim.getHeight() * 0.8));
                    frame.setLocation(50, 20);
                }

            }
        });
        return topPanel;
    }

    /**
     * @return the topPanel
     */
    public TopPanel getTopPanel() {
        return topPanel;
    }






}
