 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
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
package kcl.waterloo.graphics;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JLabel;
import kcl.waterloo.annotation.GJAnnotationInterface;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.observable.GJLinkableInterface;
import kcl.waterloo.observable.GJObservableInterface;
import kcl.waterloo.serviceproviders.GJEditorInterface;

/**
 * Defines the set of methods to be exposed as public in graph classes.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJGraphInterface extends GJLayerInterface<GJGraphInterface>,
        GJAxisSupportInterface,
        GJObservableInterface,
        GJLinkableInterface {

    /**
     * Sets the {@code GJAbstractGraphContainer} for this graph.
     *
     * @param c the Container to add this GJAbstractGraph to
     */
    public void setGraphContainer(GJAbstractGraphContainer c);

    /**
     * Adds a plot to the graph. Equivalent to {@code add(plot, true);}
     *
     * @param plot the plot to add to the graph
     * @return a reference to the added plot
     */
    public GJPlotInterface add(GJPlotInterface plot);

    /**
     * Adds a plot to a graph
     *
     * @param plot the plot to add
     * @param flag if true, the graph will be rescaled to accomodate the added
     * plot.
     * @return a reference to the added plot
     */
    public GJPlotInterface add(GJPlotInterface plot, boolean flag);

    /**
     * Removes a plot from this graph's hierarchy.
     *
     * @param plot the plot to remove.
     */
    public void remove(GJPlotInterface plot);

    /**
     * Add a component at the specified axis location
     *
     * @param c The component to add
     * @param x The x-axis position
     * @param y The y-axis position
     * @return the added component
     */
    public Component add(Component c, double x, double y);

    /**
     * Add a component at the specified axis location with the specified
     * alignment
     *
     * @param c The component to add
     * @param x The x-axis position
     * @param y The y-axis position
     * @param alignX The X-alignment of the component on x,y
     * @param alignY The Y-alignment of the component on x,y
     * @return the added component
     */
    public Component add(Component c, double x, double y, int alignX, int alignY);

    /**
     * Adds an annotation to the container for the graph. The annotation
     * setGraph method is used to reference this graph in the annotation
     * instance.
     *
     * @param a the {@code GJAnnotationInterface} to add
     */
    public void add(GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> a);

    /**
     * @param c the {@code GJAbstractGraphContainer} to add this GJAbstractGraph
     * to
     * @param insets an Insets object specifying the margins
     */
    public void setGraphContainer(GJAbstractGraphContainer c, Insets insets);

    /**
     * Returns the container for this graph. Null if the graph has no container.
     *
     * @return the GJAbstractGraphContainer
     */
    public GJAbstractGraphContainer getGraphContainer();

    /**
     * Creates the objects for drawing axes for this graph.
     */
    public void createAxes();

    /**
     * Convert a pixel location to a graph coordinate on the x-axis. This method
     * does not compensate for rotation or zoom.
     *
     * @param pixel location on x
     * @return the position on the x-axis
     */
    public double xPixelToPosition(double pixel);

    /**
     * Convert an array of pixel locations to graph coordinates on the x-axis.
     * This method does not compensate for rotation or zoom.
     *
     * @param pixel location on x
     * @return the positions on the x-axis
     */
    public double[] xPixelToPosition(double[] pixel);

    /**
     * Convert an array of pixel locations to graph coordinates on the x-axis.
     * The conversion may be done in-place, so the input data are destroyed in
     * the process. This method does not compensate for rotation or zoom.
     *
     * @param pixel location on x
     * @return the positions on the x-axis
     */
    public double[] xPixelToPositioni(double[] pixel);

    /**
     * Convert a pixel location to a graph coordinate on the y-axis. This method
     * does not compensate for rotation or zoom.
     *
     * @param pixel location on y
     * @return the position on the y-axis
     */
    public double yPixelToPosition(double pixel);

    /**
     * Convert an array of pixel locations to graph coordinates on the y-axis.
     * This method does not compensate for rotation or zoom.
     *
     * @param pixel location on y
     * @return the positions on the y-axis
     */
    public double[] yPixelToPosition(double[] pixel);

    /**
     * Convert an array of pixel locations to graph coordinates on the y-axis.
     * The conversion may be done in-place, so the input data are destroyed in
     * the process. This method does not compensate for rotation or zoom.
     *
     * @param pixel location on y
     * @return the positions on the y-axis
     */
    public double[] yPixelToPositioni(double[] pixel);

    /**
     * Returns the width of a pixel in graph coordinates. This method does not
     * compensate for rotation or zoom.
     *
     * @return the value
     */
    public double getPixelWidth();

    /**
     * Returns the height of a pixel in graph coordinates. This method does not
     * compensate for rotation or zoom.
     *
     * @return the value
     */
    public double getPixelHeight();

    /**
     * Convert an axis location to a pixel value This method does not compensate
     * for rotation or zoom.
     *
     * @param position on x-axis
     * @return pixel location on x
     */
    public double xPositionToPixel(double position);

    /**
     * Convert an array of axis locations to a pixel values. This method does
     * not compensate for rotation or zoom.
     *
     * @param positions on x-axis
     * @return pixel locations on x
     */
    public double[] xPositionToPixel(double[] positions);

    /**
     * Convert an array of axis locations to a pixel values. The conversion may
     * be done in-place, thus destroying the input values. This method does not
     * compensate for rotation or zoom.
     *
     * @param positions on x-axis
     * @return pixel locations on x
     */
    public double[] xPositionToPixeli(double[] positions);

    /**
     * Convert an array of axis locations to a pixel values. This method does
     * not compensate for rotation or zoom.
     *
     * @param position on y-axis
     * @return pixel locations on y
     */
    public double yPositionToPixel(double position);

    /**
     * Convert an array of axis locations to a pixel values. This method does
     * not compensate for rotation or zoom.
     *
     * @param positions on y-axis
     * @return pixel locations on y
     */
    public double[] yPositionToPixel(double[] positions);

    /**
     * Convert an array of axis locations to a pixel values. The conversion may
     * be done in-place, thus destroying the input values. This method does not
     * compensate for rotation or zoom.
     *
     * @param positions on y-axis
     * @return pixel locations on y
     */
    public double[] yPositionToPixeli(double[] positions);

    /**
     * Returns a rectangle defined in axes units from a rectangle defined in
     * pixels.
     *
     * @param r a Rectangle2D in pixels
     * @return a Rectangle2D in axes units
     */
    public Rectangle2D convertPixelsToPosition(Rectangle2D r);

    /**
     * Get the transform for the x-data
     *
     * @return a {@code GJDataTransformInterface}
     */
    public GJDataTransformInterface getXTransform();

    /**
     * Sets the transform for the x-data
     *
     * @param tr a {@code GJDataTransformInterface}
     */
    public void setXTransform(GJDataTransformInterface tr);

    /**
     * Get the transform for the y-data
     *
     * @return a {@code GJDataTransformInterface}
     */
    public GJDataTransformInterface getYTransform();

    /**
     * Sets the transform for the y-data
     *
     * @param tr a {@code GJDataTransformInterface}
     */
    public void setYTransform(GJDataTransformInterface tr);

    /**
     * Returns the list of top plots associated with this graph. It is
     * recommended that this should be a clone of the internal ArrayList.
     *
     * @return a ArrayList<GJPlotInterface>
     */
    public ArrayList<GJPlotInterface> getPlots();

    /**
     * Replaces the internal plot list (or its contents) with the supplied
     * {@code ArrayList} (or its contents). Copying the contents is recommended.
     *
     * This method is called during de-serialization of xml files. It is public
     * for that reason only and is not intended for use elsewhere.
     *
     * @param list a {@code ArrayList<GJPlotInterface>}
     */
    public void setPlots(ArrayList<GJPlotInterface> list);

    /**
     * Returns a list of plot that are currently selected (e.g. by mouse click).
     *
     * @return a {@code ArrayList<GJPlotInterface>}
     */
    public ArrayList<GJPlotInterface> getSelectedPlots();

    /**
     * Programmatically selects plots.
     *
     * @param p a {@code ArrayList<GJPlotInterface>}
     */
    public void setSelectedPlots(ArrayList<GJPlotInterface> p);

    /**
     * Sets the editor to be activated when this graph is double-clicked.
     *
     * @param editor an object implementing the {@code GJEditorInterface}.
     */
    public void setEditor(GJEditorInterface editor);

    /**
     * Returns the editor.
     *
     * @return a {@code GJEditorInterface}.
     */
    public GJEditorInterface fetchEditor();

    /**
     * Convenience method to save a graph to a "kclf" file.
     *
     * @param s String description of the file.
     */
    public void saveAsXML(String s);

    /**
     * Sets the anti-aliasing key for rendering this graph. Note this setting
     * will be overriden while rendering plots.
     *
     * @param o a rendering key value
     */
    public void setKeyAntialiasing(Object o);

    /**
     * Sets the text anti-aliasing key for rendering this graph. Note this
     * setting will be overriden while rendering plots.
     *
     * @param o a rendering key value
     */
    public void setTextAntialiasing(Object o);

    /**
     * Returns a reference to a JLabel that will be updated with the mouse
     * position (unless null).
     *
     * @return a JLabel
     */
    public JLabel getMousePositionTextField();

    /**
     * Sets a reference to a JLabel that will be updated with the mouse position
     * (unless null).
     *
     * @param t a JLabel
     */
    public void setMousePositionTextField(JLabel t);

    public boolean isPolar();

    /**
     * Returns true if the data shown in the {@code mousePositionTextField} are
     * inverse transformed.
     *
     * @return the flag
     */
    public boolean isTextAsInverse();

    /**
     * Sets the textAsInverse flag. When set true, mouse position values shown
     * in the mousePositionTextField will be inverse transformed.
     *
     * @param flag the textAsInverse to set
     */
    public void setTextAsInverse(boolean flag);

    /**
     * Determines whether the background is painted. Background painting needs
     * to be turned off to support multi-layered graphs so that graphs at the
     * top of the z-order does not obscure those below.
     *
     * @param flag boolean value
     */
    public void setBackgroundPainted(boolean flag);

    /**
     * Returns a MouseAdapter for this component. The methods of the handler can
     * then be called by the mouse listeners of components that are higher in
     * the Swing hierarchy.
     *
     * This is used in Waterloo e.g. by the {@code GJAddedComponentMouseHandler}
     * to support rotated and zoomed graphs. Instead of adding mouse listeners
     * directly to graphs, they are located by reflection and the methods called
     * after on the target graph after accounting for any rotation/zoom of the
     * graphics output.
     *
     * @return the MouseAdapter
     */
    public MouseAdapter getMouseHandler();

    /**
     * Adds listeners or registers the mouse handle to be returned by
     * {@code getMouseHandler}.
     */
    public void addListeners();

    //public void firePropertyChange(String s, int m, int n);
    //public KeyListener getKeyListener();
    /**
     * Auto-scales the graph's axes
     */
    public void autoScale();
}
