/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.plots2D;

import java.awt.Graphics2D;

/**
 *
 * @author ML
 */
public interface GJFastPlotInterface {

    /**
     * plotRedraw repaints this plot without re-painting the container hierarchy
     * and, therefore, without erasing the previous copy of the plot on screen.
     *
     */
    public void plotRedraw();

    /**
     * Performs a plot redraw using the supplied Graphics instance
     *
     * @param a Graphic2Ds instance
     */
    public void plotRedraw(Graphics2D g);

    /**
     * In this abstract class implementations, plotUpdate will generally just
     * call plotRedraw. In concrete plot classes, plotUpdate may be overridden
     * to paint only points that have been added to end of a plot since the last
     * plot.
     *
     * @return true if this method has been overloaded, false if not so that it
     * just calls plotRedraw.
     */
    public boolean plotUpdate();

    /**
     * Performs a plot append using the supplied Graphics instance
     *
     * @param a Graphics2D instance
     */
    public boolean plotUpdate(Graphics2D g);
}
