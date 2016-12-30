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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJBasicPanel;
import kcl.waterloo.graphics.GJGraph;
import kcl.waterloo.graphics.GJGraphContainer;
import kcl.waterloo.graphics.GJGridInterface;

/**
 * GCGridContainer class: houses a GCGrid and a GCInfoBar.
 *
 * Within the kcl.waterloo.swing package, a <code>GCGridContainer</code>
 * provides for support of a single GCGrid instance. It can form the content
 * pane of a <code>GXFrame</code>, or be contained in the tabs of a
 * <code>GCTabbedContainer</code>.
 *
 *
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GCGridContainer extends GJBasicPanel
        implements GCGridContainerInterface, GJGridInterface, ContainerListener {

    /**
     *
     */
    private GCGrid grid = new GCGrid();
    /**
     *
     */
    private GCInfoBar infoBar = new GCInfoBar();

    public GCGridContainer() {
        super(true);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        super.add(grid, BorderLayout.CENTER);
        grid.addContainerListener(this);
        infoBar = new GCInfoBar();
        super.add(infoBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(500, 500));
    }

    public GCFrame createFrame() {
        GCFrame f = new GCFrame();
        f.setGraphicsContainer(this);
        return f;
    }

    @Override
    public Component add(Component comp) {
        if (comp instanceof GJGraphContainer) {
            return grid.add(comp);
        } else {
            return super.add(comp);
        }
    }

    @Override
    public Component add(Component comp, double row, double column) {
        return grid.add(comp, row, column, 100d, 100d);
    }

    @Override
    public Component add(Component comp, double row, double column, double width, double height) {
        return grid.add(comp, row, column, width, height);
    }

    @Override
    public Component add(Component comp, double row, double column, double width, double height, int tab)
            throws UnsupportedOperationException {
        if (tab == 0) {
            return grid.add(comp, row, column, width, height);
        } else {
            throw (new UnsupportedOperationException("GCGridContainer does not support tab indices>0"));
        }
    }

    /**
     *
     * @param tab
     * @return
     */
    @Override
    public Component getComponentAt(int tab) {
        if (tab == 0) {
            return grid;
        } else {
            return null;
        }
    }

    /**
     * @return the grid
     */
    public GCGrid getGrid() {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(GCGrid grid) {
        remove(grid);
        super.add(grid);
        this.grid = grid;
    }

    /**
     * @return the infoBar
     */
    @Override
    public GCInfoBar getInfoBar() {
        return infoBar;
    }

    /**
     *
     * @param bar
     */
    public void setInfoBar(GCInfoBar bar) {
        remove(infoBar);
        super.add(bar);
        infoBar = bar;
    }

    @Override
    public Container getSelected() {
        return this;
    }

    @Override
    public JLabel getGridIndicator() {
        return getInfoBar().getCenterright();
    }

    @Override
    public JLabel getMousePositionIndicator() {
        return getInfoBar().getRight();
    }

    /**
     * Returns the 2-D coordinates given a linear index. dx
     *
     * @param c
     * @return the coordinates as an int[]
     */
    @Override
    public double[] getLocation(Component c) {
        if (SwingUtilities.isDescendingFrom(c, this)) {
            GCGridElement el;
            if (c instanceof GCGridElement) {
                el = (GCGridElement) c;
            } else {
                el = (GCGridElement) SwingUtilities.getAncestorOfClass(GCGridElement.class, c);
            }
            if (el != null) {
                return new double[]{el.getProperties().getRow(), el.getProperties().getColumn()};
            }
        }
        return new double[]{-1d, -1d};
    }

    @Override
    public void componentAdded(ContainerEvent e) {
        if (e.getChild() instanceof GCGridElement) {
            for (int k = 0; k < ((GCGridElement) e.getChild()).getComponentCount(); k++) {
                if (((GCGridElement) e.getChild()).getComponent(k) instanceof GJAbstractGraphContainer) {
                    Component c = ((GCGridElement) e.getChild()).getComponent(k);
                    if (c instanceof GJAbstractGraphContainer) {
                        GJGraph gr = (GJGraph) ((GJAbstractGraphContainer) c).getView();
                        // TODO: This could be null in deserialization
                        // Check issues
                        if (gr != null) {
                            gr.setGridInterface(this);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
        if (e.getChild() instanceof GCGridElement) {
            for (int k = 0; k < ((GCGridElement) e.getChild()).getComponentCount(); k++) {
                if (((GCGridElement) e.getChild()).getComponent(k) instanceof GJAbstractGraphContainer) {
                    Component c = ((GCGridElement) e.getChild()).getComponent(k);
                    if (c instanceof GJAbstractGraphContainer) {
                        GJGraph gr = (GJGraph) ((GJAbstractGraphContainer) c).getView();
                        // TODO: This could be null in deserialization
                        // Check issues
                        if (gr != null) {
                            gr.setGridInterface(null);
                        }
                    }
                }
            }
        }
    }

    public void addTab(String s, Component c) {
        throw (new UnsupportedOperationException("GCGridContainer does not support tab indices>0"));
    }
}
