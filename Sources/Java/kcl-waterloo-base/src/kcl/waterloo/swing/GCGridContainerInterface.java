/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.swing;

import java.awt.Component;
import java.awt.Container;

/**
 * An interface to define common behaviour for implementing classes intended to
 * contain GCGrid instances.
 *
 * @author ML
 */
public interface GCGridContainerInterface {

    /**
     * Returns the presently selected item. For a GCGridContainer instance this
     * returns the instance. For a GCTabbedGridContainer, it returns the
     * selected component which may or may not be a GCGridContainer.
     *
     * @return the Container
     */
    public Container getSelected();

    /**
     * Returns the presently selected GCInfoBar for the selected container if it
     * is a GCGridContainer. Throws UnsupportedOperationException otherwise.
     *
     * @return the GCInfoBar
     */
    public GCInfoBar getInfoBar() throws UnsupportedOperationException;

    /**
     * Adds a component to the contained grid. Throws
     * UnsupportedOperationException if the target is not a
     * GCGridContainerInterface.
     *
     * @param c - the added component, which will normally be wrapped in a
     * GCGridElement instance.
     * @param row
     * @param column
     *
     * @return the added component
     */
    public Component add(Component c, double row, double column);

    /**
     * Adds a component to the contained grid.
     *
     * Throws UnsupportedOperationException if the target is not a
     * GCGridContainerInterface.
     *
     * @param c - the added component, which will normally be wrapped in a
     * GCGridElement instance.
     * @param row
     * @param column
     * @param width
     * @param height
     *
     * @return the added component
     */
    public Component add(Component c, double row, double column, double width, double height);

    public Component add(Component c, double row, double column, double width, double height, int tab)
            throws UnsupportedOperationException;

    //public void addTab(String s, Component c);
    /**
     * Returns the component at the specified tab location.
     *
     * @param tab
     * @return a Component
     */
    public Component getComponentAt(int tab);
}
