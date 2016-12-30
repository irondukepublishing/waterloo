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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kcl.waterloo.actions.ActionManager;
import kcl.waterloo.logging.CommonLogger;

/**
 * A {@code GCFrame} provides a JFrame subclass that supports graphs and grids
 * of graphs.
 *
 * Management of multiple {@code GCFrame}s in a JVM instance is coordinated via
 * an instance of the {@code FrameManager} class.
 *
 * Typically, the graphics container for a GCFrame is either:
 * <ol>
 * <li>a <code>GCGridContainer</code> - for single grids</li>
 * <li>a <code>GCTabbedGridContainer</code> - for multiply tabbed grids</li>
 * </ol>
 *
 * By default, a {@code GCGridContainer} is used. Its contents will be
 * automatically added to Tab 0 of a {@code GCTabbedGridContainer} when required
 * and the {@code GCTabbedGridContainer} will become the content pane.
 *
 * However, the content pane can be another component parenting the graphics
 * container e.g. a JPanel with a border layout and the graphics container in
 * its center in custom implementations. Use setGraphicsContainer() to direct a
 * GCFrame to the appropriate component.
 *
 *
 * For convenience, this class includes add and makeTabbed methods that forward
 * calls to the content pane. For other methods, the content pane needs to be
 * accessed explicitly.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GCFrame extends JFrame implements GCFrameInterface, ChangeListener {

    private GCGridContainerInterface graphicsContainer = new GCGridContainer();
    private final static CommonLogger logger = new CommonLogger(GCFrame.class);

    /**
     * Default constructor. Creates a {@code GCFrame} with a single graph (i.e.
     * a 1x1 grid) in a GCGridContainer.
     */
    public GCFrame() {
        this("");
        setContentPane((Container) graphicsContainer);
    }

    /**
     * Creates a {@code GCFrame} with a single graph (i.e. a 1x1 grid)
     *
     * @param titleText the Title for the frame.
     */
    public GCFrame(String titleText) {
        super();
        int frameCount = FrameManager.getLowestAvailable();
        setTitle("Frame " + frameCount + ": " + titleText);
        setName("Frame" + frameCount);
        init(frameCount);
        setSize(500, 500);
        setBackground(Color.WHITE);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void init(int frameCount) {
        graphicsContainer = new GCGridContainer();
        setContentPane((Container) graphicsContainer);
        FrameManager.getList().put(Double.valueOf(frameCount), this);
        setJMenuBar(MenuFactory.createMenu(this));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(FrameManager.getInstance());
    }

    /**
     * Returns the graphics container for this instance. While all standard
     * implementations use the content pane of the frame as the graphics
     * container, this need not be the case with user written replacements.
     *
     * @return the GCGridContainerInterface
     */
    public GCGridContainerInterface getGraphicsContainer() {
        return graphicsContainer;
    }

    /**
     * Sets the supplied graphics container as the content pane for the frame.
     *
     * @param c a GCGridContainerInterface
     */
    public final void setGraphicsContainer(GCGridContainerInterface c) {
        setContentPane((Container) c);
        graphicsContainer = c;
    }

    /**
     * Adds the specified Component to the graphics container at grid location
     * 0,0 in tab 0.
     *
     * @param c the Component to add
     * @return the added Component
     */
    @Override
    public Component add(Component c) {
        return add(c, 0f, 0f, 0);
    }

    /**
     * Adds a component to the grid at the specified row and column position in
     * the specified tab.
     *
     * @param c the component to add.
     * @param row the row position in grid elements
     * @param column the column position in grid elements
     * @param tab the tab position
     * @return the added component
     */
    public final Component add(Component c, double row, double column, int tab)
            throws UnsupportedOperationException {
        return add(c, row, column, 1d, 1d, tab);
    }

    /**
     * Adds a component to the grid at the specified row and column position in
     * the specified tab. The width and height of the component are also
     * specified.
     *
     * @param c the component to add.
     * @param row the row position in grid elements
     * @param column the column position in grid elements
     * @param width the width of the component in grid elements
     * @param height the height of the component in grid elements
     * @param tab the tab position
     * @return the added component
     */
    public final Component add(Component c, double row, double column, double width, double height, int tab)
            throws UnsupportedOperationException {
        GCGridContainerInterface contents = getGraphicsContainer();
        if (contents instanceof GCTabbedGridContainer) {
            GCGridContainerInterface thisTab = (GCGridContainerInterface) contents.getComponentAt(tab);
            return thisTab.add(c, row, column, width, height);
        } else if (contents instanceof GCGridContainer && tab == 0) {
            return contents.add(c, row, column, width, height);
        } else {
            logger.error("Target does not implement the GCGridContainerInterface");
            return null;
        }
    }

    /**
     * /**
     * Adds a tab with no title to the content pane. If required, a
     * GCTabbedGridContainer will be created and set as the content pane. The
     * previous contents will be copied to tab 0.
     *
     * @param title for the tab
     */
    public void makeTabbed(String title) {
        JTabbedPane tabbedPanel;
        if (getGraphicsContainer() instanceof GCGridContainer) {
            Component contents = (Component) getGraphicsContainer();
            tabbedPanel = new GCTabbedGridContainer();
            tabbedPanel.addChangeListener(this);
            setContentPane(tabbedPanel);
            setGraphicsContainer((GCGridContainerInterface) tabbedPanel);
            tabbedPanel.insertTab(title, null, contents, "", 0);
        }
    }

    /**
     * Adds a tab using the supplied Component.
     *
     * @param s the name for the tab
     * @param c the Component to form the tab
     * @return the Component c
     */
    public Component addTab(String s, Component c) throws UnsupportedOperationException {
        if (graphicsContainer instanceof JTabbedPane) {
            ((JTabbedPane) getGraphicsContainer()).addTab(s, c);
            return c;
        } else {
            logger.error("Target does not support tabs");
            return null;
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        toFront();
        ActionManager.processAction(ae, (Component) this.getGraphicsContainer());
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
    }
}
