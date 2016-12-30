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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import kcl.waterloo.actions.ActionManager;
import kcl.waterloo.graphics.GJBasicPanel;
import kcl.waterloo.swing.layout.SpringGridLayout;

/**
 * {@code GCGrid} supports grids of graph containers. {@code GCGrid} uses a
 * subclass of the SpringLayout to maintain the relative positions of the graph
 * containers.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GCGrid extends GJBasicPanel {

    SpringGridLayout gridLayout = new SpringGridLayout();
    private GCGlassLayer glassLayer = new GCGlassLayer();

    /**
     * Default constructor. Creates a 1x1 gridDimensions
     */
    public GCGrid() {
        setLayout(gridLayout);
        super.add(glassLayer);
        setBackground(Color.WHITE);
        setBackgroundPainted(true);
        setOpaque(true);
        getGlassLayer().setOpaque(false);
    }


    /**
     * Adds grid support to the context menu
     *
     * @return
     */
    public JMenu getGridMenu() {
        JMenu gridMenu = new JMenu("Grid");

        JMenuItem saveMenu = new JMenuItem("Save All");
        JMenuItem exportMenu = new JMenuItem("Export Grid");
        JMenuItem copyMenu = new JMenuItem("Copy Grid");

        saveMenu.setActionCommand("Save Graph");
        exportMenu.setActionCommand("Save Graph As");
        copyMenu.setActionCommand("Copy");

        ActionListener action = new GCGrid.GridMenuAction(this);
        saveMenu.addActionListener(action);
        exportMenu.addActionListener(action);
        copyMenu.addActionListener(action);

        gridMenu.add(saveMenu);
        gridMenu.add(exportMenu);
        gridMenu.add(copyMenu);

        return gridMenu;
    }

    @Override
    public SpringGridLayout getLayout() {
        return gridLayout;
    }

    /**
     * Add method that wraps the added component in a GCGridElement as required.
     *
     * @param c the component to add
     * @return the added GCGridElement
     */
    @Override
    public Component add(Component c) {
        if (c instanceof GCGridElement) {
            return super.add(c);
        } else {
            return add(c, 0d, 0d, 1d, 1d);
        }
    }

    /**
     * Add method that wraps the added component in a GCGridElement as required.
     *
     * @param c the component to add
     * @param row the target row
     * @param column the target column
     * @return the added GCGridElement
     */
    public Component add(Component c, double row, double column) {
        return add(c, row, column, 1d, 1d);
    }

    /**
     *
     * Add method that wraps the added component in a GCGridElement as required.
     *
     * @param c the component to add
     * @param row the target row
     * @param column the target column
     * @param width the width of the component
     * @param height the height of the component
     * @return the added GCGridElement
     */
    public Component add(Component c, double row, double column, double width, double height) {
        GCGridElement el;
        if (c instanceof GCGridElement) {
            // Needed for de-serialization where components are already wrapped.
            return super.add(c);
        } else {
            el = new GCGridElement(c);
            el.getProperties().setRow(row);
            el.getProperties().setColumn(column);
            el.getProperties().setColumnWidth(width);
            el.getProperties().setRowHeight(height);
            return super.add(el);
        }
    }

    /**
     * Returns the grid element(s) located at the point {x=row, y=column} as an
     * ArrayList<GCGridElement>.
     *
     * Note: Row and column are specified as doubles, and it is up to the user
     * to ensure that the comparison for an exact match does not fail due to
     * IEEE rounding errors.
     *
     * @param row
     * @param column
     * @return the element(s) as an ArrayList<GCGridElement>
     */
    public ArrayList<GCGridElement> getElementsAt(double row, double column) {
        ArrayList<GCGridElement> arr = getElements();
        ArrayList<GCGridElement> arr2 = new ArrayList<GCGridElement>();
        for (GCGridElement el : arr) {
            if (el.getProperties().getRow() == row && el.getProperties().getColumn() == column) {
                arr2.add(el);
            }
        }
        return arr2;
    }

    /**
     * Returns the glass layer
     *
     * @return the GCGlassLayer
     */
    public GCGlassLayer getGlassLayer() {
        return glassLayer;
    }

    public void setGlassLayer(GCGlassLayer gl) {
        glassLayer = gl;
    }

    public double[] getDimensions() {
        double columnCount = 0f;
        double rowCount = 0f;
        for (int k = 0; k < getComponentCount(); k++) {
            if (getComponent(k) instanceof GCGridElement) {
                GCGridElement el = (GCGridElement) getComponent(k);
                rowCount = Math.max(el.getProperties().getRow() + el.getProperties().getRowHeight(), rowCount);
                columnCount = Math.max(el.getProperties().getColumn() + el.getProperties().getColumnWidth(), columnCount);
            }
        }
        return new double[]{rowCount, columnCount};
    }

    public ArrayList<GCGridElement> getElements() {
        ArrayList<GCGridElement> arr = new ArrayList<GCGridElement>();
        for (int k = 0; k < getComponentCount(); k++) {
            if (getComponent(k) instanceof GCGridElement) {
                arr.add((GCGridElement) getComponent(k));
            }
        }
        return arr;
    }

    @Override
    public void revalidate() {
        for (int k = 0; k < getComponentCount(); k++) {
            if (getComponent(k) instanceof GCGridElement) {
                GCGridElement el = (GCGridElement) getComponent(k);
                if (el.getProperties().getZOrder() > 0) {
                    setComponentZOrder(el, el.getProperties().getZOrder());
                }
            }
        }
        super.validate();
    }

    public static class GridMenuAction implements ActionListener {

        final JComponent target;

        public GridMenuAction(JComponent target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component c = target;
            Component c2;
            if (e.getActionCommand().equals("Copy")) {
                c2 = SwingUtilities.getAncestorOfClass(GCTabbedGridContainer.class, c);
                if (c2 == null) {
                    c2 = SwingUtilities.getAncestorOfClass(GCGridContainer.class, c);
                }
            } else {
                c2 = SwingUtilities.getAncestorOfClass(GCTabbedGridContainer.class, c);
                if (c2 == null) {
                    c2 = SwingUtilities.getAncestorOfClass(GCGridContainer.class, c);
                }
            }
            if (c2 != null) {
                e.setSource(c2);
            }
            ActionManager.processAction(e, (Component) e.getSource());
        }
    }
}
