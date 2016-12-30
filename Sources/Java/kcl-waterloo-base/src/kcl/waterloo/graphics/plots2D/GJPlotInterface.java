 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.plots2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.observable.GJLinkableInterface;
import kcl.waterloo.observable.GJObservableInterface;
import kcl.waterloo.plotmodel2D.*;

/**
 * GJPlotInterface defines methods that must be implemented by all plots.
 *
 * All but one of these have concrete implementations in {@code GJAbstractPlot}
 * and will therefore not generally need to be implemented by user-created
 * plots.
 *
 * A {@code paintPlot} method will need to be implemented for individual
 * {@code GJAbstractPlot} subclasses.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJPlotInterface extends
        GJVisualsInterface<GJMarker, Paint, BasicStroke, Dimension, Shape>,
        GJDataModelInterface<Rectangle2D, GJGraphInterface>,
        GJObservableInterface,
        GJLinkableInterface,
        GJScreenDataInterface<Shape> {

    /**
     * Sets the parent graph for this plot. A plot can exist in only one graph
     * at any time.
     *
     * @param gr
     */
    public void setParentGraph(GJGraphInterface gr);

    /**
     *
     * @return the parent graph
     */
    public GJGraphInterface getParentGraph();

    /**
     * Returns the {@code GJAbstractVisualModel} for the plot.
     *
     * @return the GJVisualModel
     */
    public GJVisualModel getVisualModel();

    /**
     * Sets the {@code GJAbstractVisualModel} for the plot.
     *
     * @param m the {@code GJAbstractVisualModel}
     */
    public void setVisualModel(GJVisualModel m);

    /**
     * Returns a reference to parent plot. If this plot is a "TopPlot", it has
     * no plot ancestor and returns null.
     *
     * @return the parent GJPlotInterface if one exists
     */
    public GJPlotInterface getParentPlot();

    /**
     * Sets the parent plot for this plot. This will be called from other
     * methods when required and should not normally need to be called from
     * user-code.
     *
     * @param p the parent plot
     */
    public void setParentPlot(GJPlotInterface p);

    /**
     * Adds a plot as a child plot of this plot.
     *
     * @param plot
     * @return this GJPlotInterface instance after the addition.
     */
    public GJPlotInterface add(GJPlotInterface plot);

    /**
     * Simply calls {@code add}. Provided for use in systems that allow addition
     * via a "+" operator.
     *
     * @param plot
     * @return this instance after addition of plot
     */
    public GJPlotInterface plus(GJPlotInterface plot);

    /**
     * Returns the ancestor plot that is the TopPlot in this plot's hierarchy.
     *
     * @return the GJPlotInterface
     */
    public GJPlotInterface getTopPlot();

    /**
     * Returns true if this plot is a "TopPlot"
     *
     * @return the flag
     */
    public boolean isTopPlot();

    /**
     * Returns an ArrayList of plots that are child plots of this plot.
     *
     * @return an ArrayList<GJPlotInterface>
     */
    public ArrayList<GJPlotInterface> getPlots();

    /**
     * Returns an ArrayList of plots that are descendant plots of this plot.
     *
     * @return a ArrayList<GJPlotInterface>
     */
    public ArrayList<GJPlotInterface> getNode();

    /**
     * Returns an ArrayList of plots located beneath [x,y] in the coordinate
     * space of the parent plot.
     *
     * @param x
     * @param y
     * @return an ArrayList<GJPlotInterface>
     */
    public ArrayList<GJPlotInterface> findPlotBelow(double x, double y);

    /**
     * Returns an ArrayList of plots located beneath {@code java.awt.Point} in
     * the coordinate space of the parent plot.
     *
     * @param p
     * @return an ArrayList<GJPlotInterface>
     */
    public ArrayList<GJPlotInterface> findPlotBelow(Point2D p);

    /**
     * Method to paint the graphics within the clip limits of the parent graph.
     *
     * @param g2
     */
    public void paintPlot(Graphics2D g2);

    /**
     * Method called by the graph paint methods to coordinate the painting of
     * individual plot hierarchies.
     *
     * @param g2 Graphics2D instance from the graph paint methods.
     */
    public void paintPlotEntry(Graphics2D g2);

    /**
     * Determines whether the specified point intersects the data in the
     * ScreenDataArray.
     *
     * Note: this method calls the Path2D intersect method on the elements of
     * the ScreenDataArray.
     *
     * @param p the Point
     * @return true if the Point intesects the ScreenDataArray.
     */
    public boolean intersects(Point2D p);

    /**
     * Determines whether the specified point intersects the data in the
     * ScreenDataArray.
     *
     * Note: this method calls the Path2D intersect method on the elements of
     * the ScreenDataArray.
     *
     * @param x the x location
     * @param y the y location
     * @return true if the Point intesects the ScreenDataArray.
     */
    public boolean intersects(double x, double y);

    /**
     * Returns true if painting of this plot is enabled
     *
     * @return the flag
     */
    public boolean isVisible();

    /**
     * Enables/disables painting of this plot. This can be useful when updating
     * the data model for the plot if the there is a risk that the transient
     * state of the plot will cause the paintPlot method to throw exceptions.
     * When visible is set false, the paintPlot method will not be called. You
     * can therefore avoid the need for conditional statements in the the
     * paintPlot method that might slow down rendering when it is being called
     * frequently.
     *
     * @param flag true so enable painting
     */
    public void setVisible(boolean flag);

    /**
     * Returns true if this plot is presently selected
     *
     * @return the selection state
     */
    public boolean isSelected();

    /**
     * Sets or clears the selected flag for this plot, together with its
     * ancestors and descendant plots
     *
     * @param flag
     */
    public void setSelected(boolean flag);

    /**
     * Sets or clears the selected flag for this plot, together with its
     * descendant plots but <strong>not</strong> ancestors
     *
     * @param flag
     */
    public void setSelectionFlag(boolean flag);

    public void saveAsXML(String s);

    public void setAntialiasing(boolean flag);

    public JFrame createFrame();

    public ArrayList<GJPlotInterface> getPlotList();

    public void setPlotList(ArrayList<GJPlotInterface> list);

}
