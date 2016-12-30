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
 * -----------------------------------------------------------------------------
 *
 * GJAbstractGraph. Developed from the JXGraph class from the
 * SwingLabs SwingX Project.
 *
 * JXGraph was originally written by Romain Guy to display functions.
 * See Filthy Rich Clients, C. Haase & R. Guy - Addison Wesley (2008).
 *
 * GJAbstractGraph and its subclasses were developed to display experimental data.
 *
 * -----------------------------------------------------------------------------
 *
 * $Id: JXGraph.java 3671 2010-04-23 20:57:49Z kschaefe $
 *
 * Copyright 2006 Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * -----------------------------------------------------------------------------
 *
 * <p>This version is part of the Waterloo Scientific Graphics package from
 * King's College London <http://waterloo.sourceforge.net/></p>
 *
 * The Waterloo Scientific Graphics library is free software; you can redistribute
 * it and/or modify it under the terms of the GNU Lesser Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
package kcl.waterloo.graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import kcl.waterloo.actions.ActionManager;
import kcl.waterloo.annotation.GJAnnotationInterface;
import kcl.waterloo.defaults.Colors;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.graphics.data.Category;
import kcl.waterloo.graphics.images.Images;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.graphics.plots2D.GJPolarPlotInterface;
import kcl.waterloo.graphics.plots2D.GJTransformUpdateInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.graphics.transforms.NOPTransform;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.serviceproviders.GJEditorInterface;
import kcl.waterloo.serviceproviders.GUIFactory;
import kcl.waterloo.swing.GCGrid;
import kcl.waterloo.xml.GJEncoder;

/**
 * Abstract class implementing the {@code GJGraphInterface}. At present, the
 * only concrete implementation is through the {@code GJGraph} class.
 *
 * While {@code GJGraph} instances can be independent of their containers, they
 * will normally be added to a {@code GJAbstractGraphContainer} instance.
 *
 * <p>
 * GJAbstractGraph. Developed from the JXGraph class from the SwingLabs SwingX
 * Project.</p>
 *
 * <p>
 * JXGraph was originally written by Romain Guy to display functions. See <a
 * href="http://filthyrichclients.org/"> Filthy Rich Clients </a>, C. Haase & R.
 * Guy - Addison Wesley (2008).</p>
 *
 * <p>
 * GJAbstractGraph and its subclasses were developed to display experimental
 * data.</p>
 *
 * <p>
 * -----------------------------------------------------------------------------</p>
 *
 * <p>
 * $Id: JXGraph.java 3671 2010-04-23 20:57:49Z kschaefe $</p>
 *
 * <p>
 * Copyright 2006 Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * California 95054, U.S.A. All rights reserved.</p>
 *
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.</p>
 *
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser Lesser General Public License
 * for more details.</p>
 *
 * <p>
 * You should have received a copy of the GNU Lesser Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA</p>
 *
 * -----------------------------------------------------------------------------
 *
 * <p>
 * This version is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * <p>
 * The Waterloo Scientific Graphics library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.</p>
 *
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser Lesser General Public License
 * for more details.</p>
 *
 * <p>
 * You should have received a copy of the GNU Lesser Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA</p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAbstractGraph extends GJAbstractGraph1 {

    /**
     * stroke width used to draw the main axis and grid
     *
     */
    private float axisStrokeWeight = ((Float) GJDefaults.getMap().get("GJAbstractGraph.axisStroke"));
    /**
     * stroke width used to draw the minor grid
     */
    float minorGridStrokeWeight = ((Float) GJDefaults.getMap().get("GJAbstractGraph.minorGridStrokeWeight"));
    float majorGridStrokeWeight = ((Float) GJDefaults.getMap().get("GJAbstractGraph.majorGridStrokeWeight"));
    /**
     * Key anti-aliasing value
     */
    private Object keyAntialiasing = GJDefaults.getMap2().get("GJAbstractGraph.keyAntialiasing");
    /**
     * Text anti-aliasing value
     */
    private Object textAntialiasing = GJDefaults.getMap2().get("GJAbstractGraph.textAntialiasing");
    /**
     * Set true to paint the major grid
     */
    private boolean majorGridPainted = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.majorGridPainted"));
    /**
     * Set true to paint the minor grid
     */
    private boolean minorGridPainted = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.minorGridPainted"));
    /**
     * Major grid color
     */
    private Color majorGridColor = (Color) GJDefaults.getMap().get("GJAbstractGraph.majorGridColor");
    /**
     * Minor grid color
     */
    private Color minorGridColor = (Color) GJDefaults.getMap().get("GJAbstractGraph.minorGridColor");
    /**
     * Inner axis color
     */
    private Color axisColor = (Color) GJDefaults.getMap().get("GJAbstractGraph.axisColor");
    /**
     * set true to paint the axes within the view
     */
    private boolean innerAxisPainted = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.innerAxisPainted"));
    /**
     * set true to paint the labels for axes within the view
     */
    private boolean innerAxisLabelled = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.innerAxisLabelled"));
    /**
     * Set true to painted left axis
     */
    private boolean leftAxisPainted = true;
    /**
     * Set true to painted right axis
     */
    private boolean rightAxisPainted = false;
    /**
     * Set true to painted top axis
     */
    private boolean topAxisPainted = false;
    /**
     * Set true to painted bottom axis
     */
    private boolean bottomAxisPainted = true;
    /**
     * Set true to label the left axis
     */
    private boolean leftAxisLabelled = true;
    /**
     * Set true to label the right axis
     */
    private boolean rightAxisLabelled = false;
    /**
     * Set true to label the top axis
     */
    private boolean topAxisLabelled = false;
    /**
     * Set true to label the bottom axis
     */
    private boolean bottomAxisLabelled = true;
    /**
     * If true, displayed mouse positions will be inverse transformed for
     * display via the transform for the relevant axis
     */
    private boolean mouseTextAsInverse = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.mouseTextAsInverse")).booleanValue();
    /**
     * Main formatter for axis labels
     */
    /**
     * Formatter for the mouse Position text
     */
    private String mousePositionTextFormat = (String) GJDefaults.getMap().get("GJAbstractGraph.mousePositionTextFormat");
    private NumberFormat mainFormatter = (NumberFormat) GJDefaults.getMap().get("GJAbstractGraph.mainFormatter");
    /**
     * Secondary formatter for axis labels e.g. to use enginering notation for
     * small or large numbers
     */
    private NumberFormat secondFormatter = (NumberFormat) GJDefaults.getMap().get("GJAbstractGraph.secondFormatter");
    /**
     * Value for the separation between major grid lines on the x-axis.
     */
    private double majorXHint;
    /**
     * Value for the separation between major grid lines on the y-axis.
     */
    private double majorYHint;
    /**
     * Number of minor grid lines to paint between major grids
     */
    private int minorCountXHint;
    /**
     * Number of minor grid lines to paint between major grids
     */
    private int minorCountYHint;
    /**
     * used by the GraphMouseHandler to move the view
     */
    private Point2D dragStart = new Point2D.Double();
    private Point2D mouseDown;
    /**
     * If true, axes will not be padded
     */
    boolean tightAxes = ((Boolean) GJDefaults.getMap().get("GJAbstractGraph.tightAxes"));
    /**
     * Fraction to pad axes by - 0 to 1.
     */
    double axesPadding = ((Double) GJDefaults.getMap().get("GJAbstractGraph.axesPadding")).floatValue();
    /**
     * Mouse Position - used internally
     */
    private Point2D currentMousePosition;
    /**
     * Internal flag
     */
    private boolean dragX = false;
    /**
     * Internal flag
     */
    private boolean dragY = false;
    /**
     * The current ROI in pixels
     */
    private final Rectangle2D.Double pixelROI = new Rectangle2D.Double(Double.NaN, Double.NaN, 0d, 0d);
    /**
     * Current ROI in axes units
     */
    private Rectangle2D.Double currentROI = null;
    /**
     * List of ROIs in axes units.
     */
    private ArrayList<Rectangle2D> availableROI = null;
    /**
     * Reference to the leftAxisPanel that will be painted in the
     * {@code GJGraphContainer} if there is one.
     */
    private GJAxisPanel leftAxisPanel = null;
    /**
     * Reference to the rightAxisPanel that will be painted in the
     * {@code GJGraphContainer} if there is one.
     */
    private GJAxisPanel rightAxisPanel = null;
    /**
     * Reference to the topAxisPanel that will be painted in the
     * {@code GJGraphContainer} if there is one.
     */
    private GJAxisPanel topAxisPanel = null;
    /**
     * Reference to the bottomAxisPanel that will be painted in the
     * {@code GJGraphContainer} if there is one.
     */
    private GJAxisPanel bottomAxisPanel = null;
    /**
     * List of plots that are top plots for this graph
     */
    private final ArrayList<GJPlotInterface> plots = new ArrayList<GJPlotInterface>();
    /**
     * List of plots that are currently selected (e.g. with the mouse).
     */
    private ArrayList<GJPlotInterface> selectedPlots = null;
    /**
     * Reference to the graph container (if there is one).
     */
    private GJAbstractGraphContainer graphContainer = null;
    /**
     * Used internally
     */
    private final static Cursor originCursor = Toolkit.getDefaultToolkit().createCustomCursor(Images.getImage("origin.gif"), new Point(8, 8), "Origin");
    /**
     * List of layers. The first element is the View, i.e. the principal
     * ancestor of other graphs in the array
     */
    private final ArrayList<GJGraphInterface> layers = new ArrayList<GJGraphInterface>();
    /**
     * List of linked objects for this graph
     */
    private final ArrayList<Object> links = new ArrayList<Object>();
    /**
     * {@code currentLayer} is maintained in the top level graph of a hierarchy
     * of graphs and provides a reference to the graph that presently has focus.
     * Code that changes the focus should also update the value of
     * {@code currentLayer}. {@code currentLayer} has no useful meaning for
     * graphs that are not the principal ancestor (layers[0]) and should be left
     * as null in those.
     */
    private GJGraphInterface currentLayer = null;
    /*
     * JTextField that will be updated with the current mouse Position
     */
    JLabel mousePosition;
    /**
     * Editor GUI for this graph.
     */
    private transient GJEditorInterface editor = null;
    /**
     * Handler for mouse events and drags over the outer axes
     */
    private final MouseAdapter axisMouseHandler = new AxisMouseHandler();
    /**
     * listens to changes to plots and repaints the graph
     */
    PropertyChangeListener plotChangeListener = null;
    /**
     * Set true for polar grid, false for Cartesian.
     */
    private boolean polar = false;
    /**
     * If this graph (or its container) is housed in a higher level container
     * supporting the {code GJGridInterface}, that container should be
     * referenced here.
     */
    private GJGridInterface gridInterface = null;

    private final JPopupMenu contextMenu = new JPopupMenu();

    private JMenu gridMenu = null;

    /**
     * Default constructor.
     */
    GJAbstractGraph() {
        super();
        setLayout(new GraphLayout());
        setFocusable(true);
        setEnabled(true);
        setForeground(Color.BLACK);
        setBackground((Color) GJDefaults.getMap().get("GJAbstractGraph.backgroundColor"));
        setBackgroundPainted(((Boolean) GJDefaults.getMap().get("GJAbstractGraph.backgroundPainted")));
//        addMouseListener(GJEventManager.getInstance());
        addMouseWheelListener(GraphMouseWheelHandler.getInstance());
//        addListeners();
        createPopup();
        getLayers().add(this);
        this.addPropertyChangeListener(this);
    }

    /**
     *
     */
    private void createPopup() {
        GraphMenuAction listener0 = new GraphMenuAction(this);
        // Main menu items
        JPopupMenu mainMenu = contextMenu;
        // Set up for save
        JMenuItem graphSave = new JMenuItem("Save Graph");
        graphSave.setActionCommand("Save Graph");
        graphSave.addActionListener(listener0);
        mainMenu.add(graphSave);
        // Set up for export
        graphSave = new JMenuItem("Export Graph");
        graphSave.setActionCommand("Save As");
        graphSave.addActionListener(listener0);
        mainMenu.add(graphSave);
        //Copy As Image
        graphSave = new JMenuItem("Copy Graph");
        graphSave.setActionCommand("Copy");
        graphSave.addActionListener(listener0);
        mainMenu.add(graphSave);
        //Edit
        graphSave = new JMenuItem("Edit Graph");
        graphSave.addActionListener(new EditMenuAction(this));
        mainMenu.add(graphSave);
        graphSave = new JMenuItem("Autoscale Axes");
        graphSave.setActionCommand("Autoscale axes");
        graphSave.addActionListener(listener0);
        mainMenu.add(graphSave);
    }

    @Override
    public final void createAxes() {
        if (getGraphContainer() != null) {
            if (leftAxisPanel == null) {
                leftAxisPanel = GJAxisPanel.createInstance(this, GJAxisPanel.Position.LEFT);
            }
            //getGraphContainer().add(leftAxisPanel);
            leftAxisPanel.setTickLabelsPainted(leftAxisLabelled);

            if (rightAxisPanel == null) {
                rightAxisPanel = GJAxisPanel.createInstance(this, GJAxisPanel.Position.RIGHT);
            }
            //getGraphContainer().add(rightAxisPanel);
            rightAxisPanel.setTickLabelsPainted(rightAxisLabelled);

            if (topAxisPanel == null) {
                topAxisPanel = GJAxisPanel.createInstance(this, GJAxisPanel.Position.TOP);
            }
            //getGraphContainer().add(topAxisPanel);
            topAxisPanel.setTickLabelsPainted(topAxisLabelled);

            if (bottomAxisPanel == null) {
                bottomAxisPanel = GJAxisPanel.createInstance(this, GJAxisPanel.Position.BOTTOM);
            }
            //getGraphContainer().add(bottomAxisPanel);
            bottomAxisPanel.setTickLabelsPainted(bottomAxisLabelled);
            getGraphContainer().revalidate();
            getGraphContainer().repaint();
        }
    }

    void removeAxes() {
        if (getParent() != null) {
            if (leftAxisPanel != null) {
                getParent().remove(leftAxisPanel);
            }
            if (rightAxisPanel != null) {
                getParent().remove(rightAxisPanel);
            }
            if (topAxisPanel != null) {
                getParent().remove(topAxisPanel);
            }
            if (bottomAxisPanel != null) {
                getParent().remove(bottomAxisPanel);
            }
        }
        leftAxisPanel = null;
        rightAxisPanel = null;
        topAxisPanel = null;
        bottomAxisPanel = null;
    }

    /**
     * Adds the specified graph as a new layer.
     *
     * @param graph the graph to add
     * @return the added graph
     */
    public final GJGraphInterface plus(GJGraphInterface graph) {
        return add(graph);
    }

    @Override
    public final GJGraphInterface add(GJGraphInterface graph) {
        GraphLayout layout = (GraphLayout) this.getLayout();

        layout.putConstraint(SpringLayout.NORTH, (Component) graph, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, (Component) graph, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, (Component) graph, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, (Component) graph, 0, SpringLayout.EAST, this);

        if (graphContainer != null) {
            graph.setGraphContainer(this.graphContainer);
        }

        //graph.addListeners();
        ((GJAbstractGraph) graph).setEnabled(true);

        if (graphContainer != null) {
            graph.createAxes();
            graph.addPropertyChangeListener(graphContainer);
        }

        graph.setBackgroundPainted(false);
        ((Component) graph).setBackground(Colors.getColor("TRANSPARENT"));
        graph.setMajorGridPainted(false);
        graph.setMinorGridPainted(false);

        super.add((Component) graph);
        this.getLayers().add(graph);
        this.setCurrentLayer(graph);

        return graph;
    }

    /**
     * Overridden {@code add} method. Performs some class checking to ensure the
     * correct {@code add} method gets called during XML deserialization.
     *
     * Users calling this method need to add appropriate constraints to the
     * layout.
     *
     * @param c
     * @return the added Component
     */
    @Override
    public Component add(final Component c) {
        if (c instanceof GJGraphInterface) {
            add((GJGraphInterface) c);
        } else if (c instanceof GJRoi) {
            GJRoi roi = (GJRoi) c;
            super.add(roi);
            addComponentListener(roi);
            addPropertyChangeListener(roi);
            revalidate();
            repaint();
        } else {
            super.add(c);
            revalidate();
            repaint();
        }
        return c;
    }

    // For deserialisation
    public GJAbstractGraph add(GJAbstractGraph gr) {
        return (GJAbstractGraph) add((GJGraphInterface) gr);
    }

    @Override
    public final void add(GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> a) {
        if (graphContainer != null) {
            graphContainer.getAnnotations().add(a);
            a.setParentGraph(this);
        }
    }

//    public void addFast(final GJPlotInterface plot) {
//        if (plot.isTopPlot()) {
//            plots.add(plot);
//            plot.setParentGraph(this);
//        }
//    }
    /**
     * Adds the specified plot as a top plot
     *
     * @param plot the plot to add
     * @return the added plot
     */
    public final GJPlotInterface plus(final GJPlotInterface plot) {
        return add(plot, true);
    }

    @Override
    public final GJPlotInterface add(final GJPlotInterface plot) {
        return add(plot, true);
    }

    @Override
    public final GJPlotInterface add(final GJPlotInterface plot, final boolean flag) {
        if (plot.isTopPlot()) {
            plots.add(plot);
            plot.setParentGraph(this);
            if (flag) {
                if (plot instanceof GJPolarPlotInterface) {
                    setPolar(true);
                    getGraphContainer().setAspectRatio(1d);
                    setInnerAxisLabelled(true);
                    setTopAxisPainted(false);
                    setBottomAxisPainted(false);
                    setLeftAxisPainted(false);
                    setRightAxisPainted(false);
                    updatePlots();
                    autoScale();
                } else {
                    updatePlots();
                    boolean previous = tightAxes;
                    tightAxes = true;
                    autoScale();
                    tightAxes = previous;
                }
                plot.addPropertyChangeListener(this);
            }
            if (plot instanceof GJTransformUpdateInterface) {
                plot.firePropertyChange(new PropertyChangeEvent(this, "parentGraph", null, this));
            }
        } else {
        }

        // If titles not yet set, take them from the added plot
        if (getBottomAxisPanel() != null && getBottomAxisPanel().getText().isEmpty()) {
            if (plot.getXData() != null) {
                getBottomAxisPanel().setText(plot.getXData().getName());
            }
        }

        if (getTopAxisPanel() != null && getTopAxisPanel().getText().isEmpty()) {
            if (plot.getXData() != null) {
                getTopAxisPanel().setText(plot.getXData().getName());
            }
        }

        if (getLeftAxisPanel() != null && getLeftAxisPanel().getText().isEmpty()) {
            if (plot.getYData() != null) {
                getLeftAxisPanel().setText(plot.getYData().getName());
            }
        }

        if (getRightAxisPanel() != null && getRightAxisPanel().getText().isEmpty()) {
            if (plot.getYData() != null) {
                getRightAxisPanel().setText(plot.getYData().getName());
            }
        }

        return plot;
    }

    @Override
    public void remove(GJPlotInterface plot) {
        if (plots.contains(plot)) {
            plot.removePropertyChangeListener(this);
            plots.remove(plot);
        }
    }

    /**
     * Returns the plots below the screen pixel location specified.
     *
     * @param x the x-Position
     * @param y the y-Position
     * @return the plots as an ArrayList
     */
    public final ArrayList<GJPlotInterface> getPlotAt(double x, double y) {
        ArrayList<GJGraphInterface> ch = this.getLayers();
        ArrayList<GJPlotInterface> found = new ArrayList<GJPlotInterface>();
        ArrayList<GJPlotInterface> temp;
        for (GJGraphInterface gr : ch) {
            for (GJPlotInterface p : gr.getPlots()) {
                temp = p.findPlotBelow(x, y);
                found.addAll(temp);
                if (found.size() > 0) {
                    return found;
                }
            }
        }
        return found;
    }

    @Override
    public final ArrayList<GJGraphInterface> getLayers() {
        return layers;
    }

    @Override
    public final GJGraphInterface getLayer(int n) {
        return layers.get(n);
    }

    @Override
    public final GJGraphInterface getCurrentLayer() {
        return currentLayer;
    }

    @Override
    public final void setCurrentLayer(final GJGraphInterface layer) {
        int idx = layers.indexOf(layer);
        if (idx >= 0) {
            currentLayer = layer;
            ((GJAbstractGraph) currentLayer).requestFocusInWindow();
        }

        if (editor != null) {
            int i = layer.indexOf();
            editor.setSelectedTab(i);
        }

    }

    @Override
    public final void setCurrentLayerIndex(int n) {
        if (n < layers.size()) {
            setCurrentLayer(layers.get(n));
        }
    }

    @Override
    public final int getCurrentLayerIndex() {
        return layers.indexOf(currentLayer);
    }

    @Override
    public final int indexOf() {
        return this.getAncestorGraph().getLayers().indexOf(this);
    }

    @Override
    public final GJAbstractGraph getAncestorGraph() {
        Component previous = this;
        while (previous.getParent() != null
                && previous.getParent() instanceof GJGraphInterface) {
            previous = previous.getParent();
        }
        return (GJAbstractGraph) previous;
    }

    @Override
    public final Component add(Component c, double x, double y) {
        return this.add(c, x, y, SwingConstants.CENTER, SwingConstants.CENTER);
    }

    @Override
    public final Component add(Component c, double x, double y, int alignX, int alignY) {
        c = super.add(c, 0);
        GraphLayout layout = (GraphLayout) getLayout();
        layout.putGraphicConstraint(c, x, y, alignX, alignY);
        //setComponentZOrder(c, 0);
        revalidate();
        if (this.graphContainer != null) {
            c.addMouseListener(this.graphContainer.getContainerMouseHandler());
            c.addMouseMotionListener(this.graphContainer.getContainerMouseHandler());
        }
        return c;
    }

    @Override
    public final void remove(Component c) {
        GraphLayout layout = (GraphLayout) getLayout();
        if (layout.getComponentMap().containsKey(c)) {
            layout.getComponentMap().remove(c);
        }
        super.remove(c);
    }

    final void nullifyParent() {
        getLayout().removeLayoutComponent(getParent());
        validate();
        graphContainer = null;
    }

    @Override
    public final void setGraphContainer(GJAbstractGraphContainer c) {
        //Insets insets = new Insets(50, 50, 50, 50);
        this.setGraphContainer(c, null);
    }

    // TODO: Insets not used, get rid of this.
    @Override
    public final void setGraphContainer(GJAbstractGraphContainer c, Insets insets) {
        if (c == null && graphContainer != null) {
            graphContainer.remove(this);
            return;
        }
        if (c != null) {
            SpringLayout layout = (SpringLayout) getLayout();
            graphContainer = c;
            Insets summedInsets = graphContainer.getInsets();
            layout.putConstraint(SpringLayout.WEST, this, summedInsets.left, SpringLayout.WEST, c);
            layout.putConstraint(SpringLayout.NORTH, this, summedInsets.top, SpringLayout.NORTH, c);
            layout.putConstraint(SpringLayout.EAST, c, summedInsets.right, SpringLayout.EAST, this);
            layout.putConstraint(SpringLayout.SOUTH, c, summedInsets.bottom, SpringLayout.SOUTH, this);
            setFont(c.getFont());
        } else {
            graphContainer = null;
        }
    }

    @Override
    public final GJAbstractGraphContainer getGraphContainer() {
        return graphContainer;
//        // This fixes the break from non-standard bean getter setter 
//        if (graphContainer == null && (getParent() instanceof GJAbstractGraphContainer)) {
//            graphContainer = (GJAbstractGraphContainer) getParent();
//            return graphContainer;
//        }
//        if (getAncestorGraph() == this) {
//            return graphContainer;
//        } else if (getAncestorGraph() != null) {
//            // recursive call
//            return getAncestorGraph().getGraphContainer();
//        } else {
//            return null;
//        }
    }

    @Override
    public final boolean isInnerAxisPainted() {
        return innerAxisPainted;
    }

    @Override
    public final void setInnerAxisPainted(boolean flag) {
        boolean old = innerAxisPainted;
        innerAxisPainted = flag;
        firePropertyChange("InnerAxisPainted", old, this.innerAxisPainted);
    }

    @Override
    public final boolean isInnerAxisLabelled() {
        return innerAxisLabelled;
    }

    @Override
    public final void setInnerAxisLabelled(boolean textPainted) {
        boolean old = isInnerAxisLabelled();
        this.innerAxisLabelled = textPainted;
        firePropertyChange("innerAxisLabelsPainted", old, this.isInnerAxisLabelled());
    }

    @Override
    public final boolean isMajorGridPainted() {
        return majorGridPainted;
    }

    @Override
    public final void setMajorGridPainted(boolean gridPainted) {
        boolean old = isMajorGridPainted();
        this.majorGridPainted = gridPainted;
        firePropertyChange("majorGridPainted", old, isMajorGridPainted());
    }

    @Override
    public final boolean isMinorGridPainted() {
        return minorGridPainted;
    }

    @Override
    public final void setMinorGridPainted(boolean gridPainted) {
        boolean old = isMinorGridPainted();
        this.minorGridPainted = gridPainted;
        firePropertyChange("minorGridPainted", old, isMinorGridPainted());
    }

    @Override
    public final boolean isLeftAxisPainted() {
        return leftAxisPainted;
    }

    @Override
    public final boolean isRightAxisPainted() {
        return rightAxisPainted;
    }

    @Override
    public final boolean isTopAxisPainted() {
        return topAxisPainted;
    }

    @Override
    public final boolean isBottomAxisPainted() {
        return bottomAxisPainted;
    }

    @Override
    public final boolean isLeftAxisLabelled() {
        return leftAxisLabelled;
    }

    @Override
    public final void setLeftAxisLabelled(boolean flag) {
        leftAxisLabelled = flag;
        if (leftAxisPanel != null) {
            leftAxisPanel.setTickLabelsPainted(flag);
        }
    }

    @Override
    public final boolean isRightAxisLabelled() {
        return rightAxisLabelled;
    }

    @Override
    public final void setRightAxisLabelled(boolean flag) {
        rightAxisLabelled = flag;
        if (rightAxisPanel != null) {
            rightAxisPanel.setTickLabelsPainted(flag);
        }
    }

    @Override
    public final boolean isTopAxisLabelled() {
        return topAxisLabelled;
    }

    @Override
    public final void setTopAxisLabelled(boolean flag) {
        topAxisLabelled = flag;
        if (topAxisPanel != null) {
            topAxisPanel.setTickLabelsPainted(flag);
        }
    }

    @Override
    public final boolean isBottomAxisLabelled() {
        return bottomAxisLabelled;
    }

    @Override
    public final void setBottomAxisLabelled(boolean flag) {
        bottomAxisLabelled = flag;
        if (bottomAxisPanel != null) {
            bottomAxisPanel.setTickLabelsPainted(flag);
        }
    }

    @Override
    public final GJAxisPanel getLeftAxisPanel() {
        return leftAxisPanel;
    }

    public final void setLeftAxisPanel(GJAxisPanel axp) {
        leftAxisPanel = axp;
    }

    @Override
    public final GJAxisPanel getRightAxisPanel() {
        return rightAxisPanel;
    }

    public final void setRightAxisPanel(GJAxisPanel axp) {
        rightAxisPanel = axp;
    }

    @Override
    public final GJAxisPanel getTopAxisPanel() {
        return topAxisPanel;
    }

    public final void setTopAxisPanel(GJAxisPanel axp) {
        topAxisPanel = axp;
    }

    @Override
    public final GJAxisPanel getBottomAxisPanel() {
        return bottomAxisPanel;
    }

    public final void setBottomAxisPanel(GJAxisPanel axp) {
        bottomAxisPanel = axp;
    }

    @Override
    public final void setBackgroundPainted(boolean backPainted) {
        boolean old = isBackgroundPainted();
        super.setBackgroundPainted(backPainted);
        firePropertyChange("backgroundPainted", old, isBackgroundPainted());
    }

    @Override
    public final Color getMajorGridColor() {
        return majorGridColor;
    }

    @Override
    public final void setMajorGridColor(Color majorGridColor) {
        if (majorGridColor == null) {
            majorGridColor=Color.DARK_GRAY;
        }
        Color old = getMajorGridColor();
        this.majorGridColor = majorGridColor;
        firePropertyChange("majorGridColor", old, getMajorGridColor());
    }

    @Override
    public final Color getMinorGridColor() {
        return minorGridColor;
    }

    @Override
    public final void setMinorGridColor(Color minorGridColor) {
        if (minorGridColor == null) {
            majorGridColor=Color.DARK_GRAY;
        }
        Paint old = getMinorGridColor();
        this.minorGridColor = minorGridColor;
        firePropertyChange("minorGridColor", old, getMinorGridColor());
    }

    @Override
    public final double getMajorXHint() {
        return majorXHint;
    }

    private double calcMajorX() {
        double width = Math.abs(this.getAxesBounds().getWidth());
        double lg = Math.log10(width);
        double rem = lg - Math.floor(lg);
        double scope = Math.pow(10, rem);
        double scale = Math.pow(10, Math.floor(lg));
        double inc;
        if (scope > 5) {
            inc = scale;
        } else if (scope > 2) {
            inc = scale / 2d;
        } else {
            inc = scale / 5d;
        }
        return inc;
    }

    @Override
    public final void setMajorXHint() {
        setMajorXHint(calcMajorX());
    }

    @Override
    public final void setMajorXHint(double majorX) {
        if (majorX <= 0.0) {
            majorX = 1e-15;
        }
        double old = getMajorXHint();
        this.majorXHint = majorX;
        firePropertyChange("majorXHint", old, getMajorXHint());
    }

    @Override
    public final void setMinorCountXHint(int minorCountX) {
        if (minorCountX < 0) {
            return;
        }
        int old = getMinorCountXHint();
        this.minorCountXHint = minorCountX;
        //repaint();
        firePropertyChange("minorCountXHint", old, getMinorCountXHint());
    }

    @Override
    public final double getMajorYHint() {
        return majorYHint;
    }

    private double calcMajorY() {
        double height = Math.abs(this.getAxesBounds().getHeight());
        double lg = Math.log10(height);
        double rem = lg - Math.floor(lg);
        double scope = Math.pow(10, rem);
        double scale = Math.pow(10, Math.floor(lg));
        double inc;
        if (scope > 5) {
            inc = scale;
        } else if (scope > 2) {
            inc = scale / 2d;
        } else {
            inc = scale / 5d;
        }
        return inc;
    }

    @Override
    public final void setMajorYHint() {
        this.setMajorYHint(calcMajorY());
    }

    @Override
    public final void setMajorYHint(double majorY) {
        if (majorY <= 0.0) {
            majorY = 1e-15;
        }
        double old = getMajorYHint();
        this.majorYHint = majorY;
        firePropertyChange("majorYHint", old, getMajorYHint());
    }

    @Override
    public final int getMinorCountXHint() {
        return minorCountXHint;
    }

    @Override
    public final int getMinorCountYHint() {
        return minorCountYHint;
    }

    @Override
    public final void setMinorCountYHint(int minorCountY) {
        if (minorCountY < 0) {
            return;
        }
        int old = getMinorCountYHint();
        this.minorCountYHint = minorCountY;
        //repaint();
        firePropertyChange("minorCountYHint", old, getMinorCountYHint());
    }

    /**
     * <p>
     * Sets the view and the origin of the graph at the same time. The view
     * minimum boundaries are defined by the location of the rectangle passed as
     * parameter. The width and height of the rectangle define the distance
     * between the minimum and maximum boundaries:</p>
     *
     * <ul> <li><i>xLeft</i>: bounds.getX()</li> <li><i>yBottom</i>:
     * bounds.getY()</li> <li><i>yTop</i>: bounds.getMaxX() (xLeft +
     * bounds.getWidth())</li> <li><i>xRight</i>: bounds.getMaxY() (yBottom +
     * bounds.getHeight())</li> </ul>
     *
     * <p>
     * The origin is located at the center of the view. Its coordinates are
     * defined by calling bounds.getCenterX() and bounds.getCenterY().</p>
     *
     * @param bounds the rectangle defining the graph's view and its origin
     */
    public final void setAxesBoundsAndOrigin(Rectangle2D bounds) {
        setAxesBounds(bounds);
        setOrigin(new Point2D.Double(bounds.getCenterX(), bounds.getCenterY()));
    }

    public final void setAxesBounds(double x, double y, double w, double h) {
        setAxesBounds(new Rectangle.Double(x, y, w, h));
    }

    @Override
    public final void setAxesBounds(final Rectangle2D bounds) {
        if (bounds == null) {
            return;
        }
        Rectangle2D old = getAxesBounds();
        xLeft = bounds.getMinX();
        xRight = bounds.getMaxX();
        yBottom = bounds.getMinY();
        yTop = bounds.getMaxY();
        setMajorXHint();
        setMajorYHint();
        firePropertyChange("axesBounds", old, getAxesBounds());
    }

    @Override
    public final Rectangle2D getAxesBounds() {
        return new Rectangle2D.Double(xLeft, yBottom, xRight - xLeft, yTop - yBottom);
    }

    /**
     * <p>
     * Resets the view to the default view if it has been changed by the user by
     * panning and zooming. The default view is defined by the view last
     * specified in a constructor call or a call to the methods
     * {@code #setAxesBounds(Rectangle2D)} and
     * {@code #setAxesBoundsAndOrigin(Rectangle2D)}.</p>
     *
     * @see #setAxesBounds(Rectangle2D)
     * @see #setAxesBoundsAndOrigin(Rectangle2D)
     */
    public final void resetView() {
        if (plots.isEmpty()) {
            setAxesBounds(-1, -1, 2, 2);
        } else {
            setAxesBounds(plots.get(0).getDataRange());
            updatePlots();
        }
    }

    public final void updatePlots() {
        recalcAxes();
    }

    /**
     * Returns the data range for the x- and y-data of the plots for this graph.
     * This range takes no account of the size of markers and does not require
     * the plot to have been rendered to the graphics device.
     *
     * @return the data range as a Rectangle2D object [X min, Y min
     * ,width,height]
     */
    public final Rectangle2D getDataRange() {
        Rectangle2D r;
        if (plots.size() > 0) {
            r = plots.get(0).getDataRange();
            for (GJPlotInterface p : plots) {
                r = r.createUnion(p.getDataRange());
            }
        } else {
            r = new Rectangle2D.Double(0.1, 0.1, 2, 2);
        }
        return r;
    }

    /**
     * Calculates the required axes ranges for the plot in this graph. Uses
     * {@code getDataRange} so may be called before a plot is rendered.
     *
     */
    private void recalcAxes() {
        Rectangle2D bounds = getDataRange();
        if (Double.isNaN(bounds.getX()) || Double.isNaN(bounds.getY())
                || Double.isNaN(bounds.getWidth()) || Double.isNaN(bounds.getHeight())) {
            return;
        }
        if (isPolar()) {
            double radius;
            radius = ArrayMath.max(ArrayMath.absi(new double[]{bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY()}));
            radius *= Math.sqrt(2) / 2d;
            bounds.setRect(-radius, -radius, 2 * radius, 2 * radius);
        }
        setAxesBounds(bounds);
        setMajorXHint();
        setMajorYHint();
    }

    @Override
    public final Color getAxisColor() {
        return axisColor;
    }

    @Override
    public final void setAxisColor(Color axisColor) {
        if (axisColor == null) {
            axisColor = new Color(0, 0, 0, 0);
        }
        Paint old = getAxisColor();
        this.axisColor = axisColor;
        firePropertyChange("axisColor", old, getAxisColor());
    }

    @Override
    public final String getXLabel() {
        if (this.bottomAxisPanel != null) {
            return this.bottomAxisPanel.getText();
        } else if (this.topAxisPanel != null) {
            return this.topAxisPanel.getText();
        } else {
            return "";
        }
    }

    @Override
    public final void setXLabel(String s) {
        String old = getXLabel();
        if (this.bottomAxisPanel != null) {
            this.bottomAxisPanel.setText(s);
        }
        if (this.topAxisPanel != null) {
            this.topAxisPanel.setText(s);
        }
        firePropertyChange("XLabel", old, getXLabel());
    }

    @Override
    public final String getYLabel() {
        if (this.leftAxisPanel != null) {
            return this.leftAxisPanel.getText();
        } else if (this.rightAxisPanel != null) {
            return this.rightAxisPanel.getText();
        } else {
            return "";
        }
    }

    @Override
    public final void setYLabel(String s) {
        String old = getYLabel();
        if (this.leftAxisPanel != null) {
            this.leftAxisPanel.setText(s);
        }
        if (this.rightAxisPanel != null) {
            this.rightAxisPanel.setText(s);
        }
        firePropertyChange("YLabel", old, getYLabel());
    }

    @Override
    public final ArrayList<GJPlotInterface> getPlots() {
        ArrayList<GJPlotInterface> list = new ArrayList<GJPlotInterface>();
        list.addAll(plots);
        return list;
    }

    public final ArrayList<GJPlotInterface> getAllPlots() {
        ArrayList<GJPlotInterface> list = new ArrayList<GJPlotInterface>();
        for (GJPlotInterface p : plots) {
            if (p.getNode() != null) {
                list.addAll(p.getNode());
            }
        }
        return list;
    }

    @Override
    public final void setPlots(final ArrayList<GJPlotInterface> list) {
        /**
         * N.B. As this method is called during de-serialization, force reset of
         * some properties that may not have invoked property change listeners
         * because they have not yet been installed in the de-serialization
         * process.
         */
        Rectangle2D r = getAxesBounds();
        plots.clear();
        plots.addAll(list);
        boolean hasTransformUpdateInterface = false;
        for (GJPlotInterface p : getAllPlots()) {
            if (p instanceof GJTransformUpdateInterface) {
                hasTransformUpdateInterface = true;
            }
        }
        if (hasTransformUpdateInterface) {

            GJDataTransformInterface tr = getXTransform();
            if (!tr.equals(NOPTransform.getInstance())) {
                setXTransform(NOPTransform.getInstance());
                setXTransform(tr);
            }
            tr = getYTransform();
            if (!tr.equals(NOPTransform.getInstance())) {
                setYTransform(NOPTransform.getInstance());
                setYTransform(tr);
            }
        }
        setAxesBounds(r);
    }

    /**
     * <p>
     * Removes the specified plot from this layer.</p>
     *
     * @param n
     */
    public final void removePlotAt(int n) {
        plots.remove(n);
        repaint();
    }

    /**
     * <p>
     * Removes all the plots currently associated with this layer.</p>
     */
    public final void removeAllPlots() {
        for (GJPlotInterface p : plots) {
            remove(p);
        }
        repaint();
    }

    public final void removeListeners() {
        Component c;
        if (graphContainer != null) {
            c = graphContainer;
        } else {
            c = this;
        }
        MouseMotionListener[] mmlist = c.getMouseMotionListeners();
        for (int k = 0; k < mmlist.length - 1; k++) {
            c.removeMouseMotionListener(mmlist[k]);
        }
        MouseListener[] mlist = c.getMouseListeners();
        for (int k = 0; k < mlist.length - 1; k++) {
            c.removeMouseListener(mlist[k]);
        }
        MouseWheelListener[] mwlist = c.getMouseWheelListeners();
        for (int k = 0; k < mlist.length - 1; k++) {
            c.removeMouseWheelListener(mwlist[k]);
        }
        removeMouseWheelListener(GraphMouseWheelHandler.getInstance());
        removeKeyListener(GraphCycler.getInstance());
    }

    @Override
    public final void addListeners() {
        removeListeners();
        if (graphContainer != null) {
            addMouseListener(graphContainer.getAddedComponentMouseHandler());
            addMouseMotionListener(graphContainer.getAddedComponentMouseHandler());
        } else {
            addMouseListener(GraphMouseHandler.getInstance());
            addMouseMotionListener(GraphMouseHandler.getInstance());
        }
        addMouseWheelListener(GraphMouseWheelHandler.getInstance());
        addKeyListener(GraphCycler.getInstance());
    }

    /**
     * Convenience caller to inverseTransformPoint(Point)
     *
     * @param x Position in the graph
     * @param y Position in the graph
     * @return Point rotated,scaled and translated according to the
     * graphContainer's Rotation and Zoom settings. Null if there is no
     * graphContainer associated with this graph
     */
    public final Point2D inverseTransform(int x, int y) {
        return inverseTransform(new Point(x, y));
    }

    /**
     * Transfrom a point according to latest AffineTransform
     *
     * @param p Point in graph
     * @return Point2D. If there is no AffineTransForm, returns the input
     *
     * inverseTransformPoint should be used before xPixelToPosition and
     * yPixelToPosition.
     */
    private Point2D inverseTransform(Point2D p) {
        if (graphContainer == null) {
            return p;
        } else {
            p = graphContainer.inverseTransform(this, p);
            return p;
        }
    }

    @Override
    public final void autoScale() {
        if (!this.equals(this.getAncestorGraph())) {
            // Redirect call to the View
            getAncestorGraph().autoScale();
            return;
        }

        for (GJGraphInterface thisLayer : layers) {

            if (thisLayer instanceof GJAbstractGraph) {
                GJAbstractGraph thisGraph = (GJAbstractGraph) thisLayer;
                thisGraph.updatePlots();
                Rectangle2D bounds = thisGraph.getDataRange();

                if (Double.isNaN(bounds.getX()) || Double.isNaN(bounds.getY())
                        || Double.isNaN(bounds.getWidth()) || Double.isNaN(bounds.getHeight())) {
                    setAxesBounds(0.1, 0.1, 2, 2);
                }

                if (bounds.getWidth() == 0 && bounds.getHeight() == 0) {
                    thisGraph.setAxesBounds(-1, -1, 1, 1);
                } else if (bounds.getWidth() == 0) {
                    thisGraph.setAxesBounds(bounds.getX(), bounds.getY(), 1, bounds.getHeight());
                } else if (bounds.getHeight() == 0) {
                    thisGraph.setAxesBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), 1);
                }

                try {
                    thisGraph.paintImmediately(getBounds());
                } catch (Exception ex) {
                    // JComponent.paintImmediately may throw exception if set up not complete
                    thisGraph.setAxesBounds(-1, -1, 1, 1);
                    thisGraph.paintImmediately(getBounds());
                }

                bounds = thisGraph.getAxesBounds();

                for (GJPlotInterface p : thisGraph.getPlots()) {
                    bounds = bounds.createUnion(p.getVisualRange());
                }

                if (!thisGraph.tightAxes) {
                    bounds = new Rectangle2D.Double(bounds.getX() - thisGraph.axesPadding * bounds.getWidth(),
                            bounds.getY() - thisGraph.axesPadding * bounds.getHeight(),
                            bounds.getWidth() + 2 * thisGraph.axesPadding * bounds.getWidth(),
                            bounds.getHeight() + 2 * thisGraph.axesPadding * bounds.getHeight());
                }

                if (thisGraph.getAxesBounds().getX() != bounds.getX()
                        || thisGraph.getAxesBounds().getY() != bounds.getY()
                        || thisGraph.getAxesBounds().getWidth() != bounds.getWidth()
                        || thisGraph.getAxesBounds().getHeight() != bounds.getHeight()) {
                    if (thisGraph.isPolar()) {
                        double extent = Math.max(Math.abs(bounds.getX()), Math.abs(bounds.getX() + bounds.getWidth()));
                        extent = Math.floor(Math.sqrt(2 * Math.pow(extent, 2)) + 0.5d);
                        thisGraph.setAxesBounds(-extent, -extent, 2 * extent, 2 * extent);
                    } else {
                        thisGraph.setAxesBounds(bounds);
                    }
                }
            }
        }
    }

    /**
     * Draw all plots to the current canvas
     *
     * @param g2 current Graphics2D object
     */
    protected void drawPlots(Graphics2D g2) {
        for (GJPlotInterface p : plots) {
            drawPlot(g2, p);
        }
    }

    /**
     * Draws a plot to the current canvas. Child plots of the plot will also be
     * painted by the paintPlot method of the specified plot
     *
     * @param g2 Graphics2D object
     * @param plot a GJAbstractPlot subclass
     */
    protected void drawPlot(Graphics2D g2, GJPlotInterface plot) {
        plot.paintPlotEntry(g2);
    }

    @Override
    public final JLabel getMousePositionTextField() {
        if (this.equals(getAncestorGraph())) {
            return mousePosition;
        } else {
            return getAncestorGraph().getMousePositionTextField();
        }
    }

    @Override
    public final void setMousePositionTextField(JLabel t) {
        if (this.equals(getAncestorGraph())) {
            mousePosition = t;
        } else {
            getAncestorGraph().setMousePositionTextField(t);
        }
    }

    /**
     * Sets the keyAntialiasing value
     *
     * @param o
     */
    @Override
    public final void setKeyAntialiasing(Object o) {
        keyAntialiasing = o;
    }

    public final void setAntialiasing(boolean flag) {
        if (flag) {
            setKeyAntialiasing(RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            setKeyAntialiasing(RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Sets the textAntialiasing value
     *
     * @param o
     */
    @Override
    public final void setTextAntialiasing(Object o) {
        textAntialiasing = o;
    }

    /**
     * <p>
     * This method is called by the component prior to any drawing operation to
     * configure the drawing surface. The default implementation enables
     * antialiasing on the graphics.</p>
     * <p>
     * This method can be overriden by subclasses to modify the drawing surface
     * before any painting happens.</p>
     *
     * @param g2 the graphics surface to set up
     */
    protected void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, keyAntialiasing);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, textAntialiasing);
    }

    @Override
    public final void setReverseX(boolean flag) {
        double temp = xLeft, xleft = xLeft, xright = xRight;
        boolean old = isXReversed();
        if (flag) {
            if (xLeft < xRight) {
                xleft = xRight;
                xright = temp;
            }
        } else {
            if (xLeft > xRight) {
                xleft = xRight;
                xright = temp;
            }
        }
        setAxesBounds(xleft, yBottom, xright - xleft, yTop - yBottom);
        firePropertyChange("xReversed", old, flag);
    }

    @Override
    public final boolean isXReversed() {
        return xLeft > xRight;
    }

    @Override
    public final void setReverseY(boolean flag) {
        boolean old = isYReversed();
        double temp = yBottom, ybottom = yBottom, ytop = yTop;
        if (flag) {
            if (yBottom < yTop) {
                ybottom = yTop;
                ytop = temp;
            }
        } else {
            if (yBottom > yTop) {
                ybottom = yTop;
                ytop = temp;
            }
        }
        setAxesBounds(xLeft, ybottom, xRight - xLeft, ytop - ybottom);
        firePropertyChange("yReversed", old, flag);
    }

    @Override
    public final boolean isYReversed() {
        return yBottom > yTop;
    }

    @Override
    public final String format(double number) {
        boolean farAway = (!isCloseToZero(number) && Math.abs(number) < 0.001d)
                || Math.abs(number) > 10000.0d;
        if (!isCloseToZero(number)) {
            return (farAway ? getSecondFormatter() : getMainFormatter()).format(number);
        } else {
            return (farAway ? getSecondFormatter() : getMainFormatter()).format(0);
        }
    }

    /**
     * Returns true if any plot contained in this graph has categorical labels
     * associated with its data for the specified axis.
     *
     * @param or
     * @return the flag
     */
    @Override
    public boolean isCategorical(GJAxisPanel.Orientation or) {
        if (getPlots() != null) {
            switch (or) {
                case X:
                    for (GJPlotInterface p : getPlots()) {
                        if (p.getXData() != null && p.getXData().isCategorical()) {
                            return true;
                        }
                    }
                    break;
                case Y:
                    for (GJPlotInterface p : getPlots()) {
                        if (p.getYData() != null && p.getYData().isCategorical()) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Category> getCategoricalLabels(GJAxisPanel.Orientation o) {
        ArrayList<Category> coll = new ArrayList<Category>();
        if (isCategorical(o)) {
            for (GJPlotInterface p : getPlots()) {
                if (p.getXData().getCategories() != null) {
                    coll.addAll(p.getXData().getCategories().values());
                }
            }
        }
        return coll;
    }

    // Zero-test with tolerance
    protected boolean isCloseToZero(double number) {
        return (Math.abs(number) < 1e-15);
    }

    @Override
    public final void setLeftAxisPainted(boolean flag) {
        boolean old = leftAxisPainted;
        leftAxisPainted = flag;
        firePropertyChange("leftAxisPainted", old, isLeftAxisPainted());
    }

    @Override
    public final void setRightAxisPainted(boolean flag) {
        boolean old = rightAxisPainted;
        rightAxisPainted = flag;
        firePropertyChange("rightAxisPainted", old, isRightAxisPainted());
    }

    @Override
    public final void setBottomAxisPainted(boolean flag) {
        boolean old = bottomAxisPainted;
        bottomAxisPainted = flag;
        firePropertyChange("bottomAxisPainted", old, isBottomAxisPainted());
    }

    @Override
    public final void setTopAxisPainted(boolean flag) {
        boolean old = topAxisPainted;
        topAxisPainted = flag;
        firePropertyChange("topAxisPainted", old, isTopAxisPainted());
    }

    @Override
    public final ArrayList<GJPlotInterface> getSelectedPlots() {
        return selectedPlots;
    }

    @Override
    public final void setSelectedPlots(ArrayList<GJPlotInterface> arr) {
        if (selectedPlots != null) {
            for (GJPlotInterface p : selectedPlots) {
                p.setSelected(false);
            }
        }
        selectedPlots = arr;
        if (selectedPlots != null) {
            for (GJPlotInterface p : selectedPlots) {
                p.setSelected(true);
            }
        }
    }

    @Override
    public final void setEditor(GJEditorInterface Editor) {
        editor = Editor;
    }

    @Override
    public final GJEditorInterface fetchEditor() {
        return editor;
    }

    @Override
    public final MouseAdapter getMouseHandler() {
        return GraphMouseHandler.getInstance();
    }

    @Override
    public final MouseAdapter getAxisMouseHandler() {
        return axisMouseHandler;
    }

    public final MouseWheelListener getMouseWheelHandler() {
        return GraphMouseWheelHandler.getInstance();
    }

    /**
     * @return the tightAxes
     */
    public final boolean isTightAxes() {
        return tightAxes;
    }

    /**
     * @param tightAxes the tightAxes to set
     */
    public final void setTightAxes(boolean tightAxes) {
        this.tightAxes = tightAxes;
    }

    /**
     * @return the axesPadding
     */
    public final double getAxesPadding() {
        return axesPadding;
    }

    /**
     * @param axesPadding the axesPadding to set
     */
    public final void setAxesPadding(double axesPadding) {
        this.axesPadding = axesPadding;
    }

    /**
     * @return the mouseTextAsInverse
     */
    @Override
    public final boolean isTextAsInverse() {
        return isMouseTextAsInverse();
    }

    /**
     * {@inheritDoc}
     *
     * @param flag
     */
    @Override
    public final void setTextAsInverse(boolean flag) {
        this.setMouseTextAsInverse(flag);
    }

    /**
     * @return the mainFormatter
     */
    public final NumberFormat getMainFormatter() {
        return mainFormatter;
    }

    /**
     * @param mainFormatter the mainFormatter to set
     */
    public final void setMainFormatter(NumberFormat mainFormatter) {
        this.mainFormatter = mainFormatter;
    }

    /**
     * @return the mouseTextAsInverse
     */
    public final boolean isMouseTextAsInverse() {
        return mouseTextAsInverse;
    }

    /**
     * @param mouseTextAsInverse the mouseTextAsInverse to set
     */
    public final void setMouseTextAsInverse(boolean mouseTextAsInverse) {
        this.mouseTextAsInverse = mouseTextAsInverse;
    }

    /**
     * @return the secondFormatter
     */
    public final NumberFormat getSecondFormatter() {
        return secondFormatter;
    }

    /**
     * @param secondFormatter the secondFormatter to set
     */
    public final void setSecondFormatter(NumberFormat secondFormatter) {
        this.secondFormatter = secondFormatter;
    }

    /**
     * @return the mousePositionTextFormat
     */
    public final String getMousePositionTextFormat() {
        return mousePositionTextFormat;
    }

    /**
     * @param mousePositionTextFormat the mousePositionTextFormat to set
     */
    public final void setMousePositionTextFormat(String mousePositionTextFormat) {
        this.mousePositionTextFormat = mousePositionTextFormat;
    }

    /**
     * @return the dragStart
     */
    public final Point2D getDragStart() {
        return dragStart;
    }

    /**
     * @param dragStart the dragStart to set
     */
    public final void setDragStart(Point2D dragStart) {
        this.dragStart = dragStart;
    }

    /**
     * @return the dragX
     */
    public final boolean isDragX() {
        return dragX;
    }

    /**
     * @param dragX the dragX to set
     */
    public final void setDragX(boolean dragX) {
        this.dragX = dragX;
    }

    /**
     * @return the dragY
     */
    public final boolean isDragY() {
        return dragY;
    }

    /**
     * @param dragY the dragY to set
     */
    public final void setDragY(boolean dragY) {
        this.dragY = dragY;
    }

    /**
     * @return the currentMousePosition
     */
    public final Point2D getCurrentMousePosition() {
        return currentMousePosition;
    }

    /**
     * @param currentMousePosition the currentMousePosition to set
     */
    private void setCurrentMousePosition(Point2D currentMousePosition) {
        this.currentMousePosition = currentMousePosition;
    }

    /**
     * @return the polar
     */
    @Override
    public boolean isPolar() {
        return polar;
    }

    /**
     * @param polar the polar to set
     */
    public void setPolar(boolean polar) {
        boolean old = this.polar;
        this.polar = polar;
        firePropertyChange("polar", this.polar, old);
    }

    @Override
    public float getAxisStrokeWeight() {
        return axisStrokeWeight;
    }

    @Override
    public void setAxisStrokeWeight(float axisStrokeWeight) {
        this.axisStrokeWeight = axisStrokeWeight;
    }

    @Override
    public float getMinorGridStrokeWeight() {
        return minorGridStrokeWeight;
    }

    @Override
    public void setMinorGridStrokeWeight(float minorGridStrokeWeight) {
        this.minorGridStrokeWeight = minorGridStrokeWeight;
    }

    @Override
    public float getMajorGridStrokeWeight() {
        return majorGridStrokeWeight;
    }

    @Override
    public void setMajorGridStrokeWeight(float majorGridStrokeWeight) {
        this.majorGridStrokeWeight = majorGridStrokeWeight;
    }

    /**
     *
     * @return the gridInterface
     */
    public GJGridInterface getGridInterface() {
        return gridInterface;
    }

    /**
     * @param gridInterface the gridInterface to set
     */
    public void setGridInterface(GJGridInterface gridInterface) {
        this.gridInterface = gridInterface;
    }

    /**
     * @return a copy of the currentROI
     */
    public Rectangle2D.Double getCurrentROI() {
        if (currentROI != null) {
            return (Rectangle2D.Double) currentROI.clone();
        } else {
            return null;
        }
    }

    /**
     * @param currentROI the currentROI to set
     */
    public void setCurrentROI(Rectangle2D.Double currentROI) {
        this.currentROI = (Rectangle2D.Double) currentROI.clone();
        firePropertyChange("selectedRegion", null, getCurrentROI());
    }

    /**
     * @return the availableROI
     */
    public ArrayList<Rectangle2D> getAvailableROI() {
        return availableROI;
    }

    /**
     * @param availableROI the availableROI to set
     */
    public void setAvailableROI(ArrayList<Rectangle2D> availableROI) {
        this.availableROI = availableROI;
    }

    public void clearSelectedRegions() {
        availableROI.clear();
    }

    /**
     * @return the mouseDown
     */
    public Point2D getMouseDown() {
        return mouseDown;
    }

    /**
     * @param mouseDown the mouseDown to set
     */
    public void setMouseDown(Point2D mouseDown) {
        this.mouseDown = mouseDown;
    }

    /**
     * @return the contentMenu
     */
    public JPopupMenu getContextMenu() {
        return contextMenu;
    }

    /**
     * Links both the x- and y-axes of this graph to the specified graph
     *
     * @param gr
     */
    public void linkAxes(GJAbstractGraph gr) {
        addLink(gr);
        gr.addLink(this);
    }

    private void linkAxes(AxisLink link) {
        addLink(link);
    }

    /**
     * Links the x-axis of this graph to that of the specified graph.
     *
     * @param gr the graph to link the axis of.
     */
    public void linkAxesXX(GJAbstractGraph gr) {
        if (!gr.equals(this)) {
            linkAxes(new AxisLink(gr, AxisLink.PAIRING.XX));
            gr.linkAxes(new AxisLink(this, AxisLink.PAIRING.XX));
        }
    }

    /**
     * Links the y-axis of this graph to that of the specified graph.
     *
     * @param gr the graph to link the axis of.
     */
    public void linkAxesYY(GJAbstractGraph gr) {
        if (!gr.equals(this)) {
            linkAxes(new AxisLink(gr, AxisLink.PAIRING.YY));
            gr.linkAxes(new AxisLink(this, AxisLink.PAIRING.YY));
        }
    }

    /**
     * Links the x-axis of this graph to the y-axis of the specified graph.
     *
     * @param gr the graph to link the axis of.
     */
    public void linkAxesXY(GJAbstractGraph gr) {
        linkAxes(new AxisLink(gr, AxisLink.PAIRING.XY));
        gr.linkAxes(new AxisLink(this, AxisLink.PAIRING.YX));
    }

    /**
     * Links the y-axis of this graph to the x-axis of the specified graph.
     *
     * @param gr the graph to link the axis of.
     */
    public void linkAxesYX(GJAbstractGraph gr) {
        linkAxes(new AxisLink(gr, AxisLink.PAIRING.YX));
        gr.linkAxes(new AxisLink(this, AxisLink.PAIRING.XY));
    }

    /**
     * Makes any linked graphs referenced by this graph visible - housing them
     * in a standard Waterloo swing hierarchy if required.
     */
    public void openLinks() {
        for (Object o : links) {
            if (o instanceof GJAbstractGraph) {
                GJAbstractGraph gr = (GJAbstractGraph) o;
                if (gr.getTopLevelAncestor() == null) {
                    GJGraphContainer.createInstance(gr).createFrame();
                } else {
                    gr.getTopLevelAncestor().setVisible(true);
                }
            } else if (o instanceof AxisLink) {
                GJAbstractGraph gr = ((AxisLink) o).getPairedTarget();
                if (gr.getTopLevelAncestor() == null) {
                    GJGraphContainer.createInstance(gr).createFrame();
                } else {
                    gr.getTopLevelAncestor().setVisible(true);
                }
            }
        }
    }

    private static final class GraphMouseWheelHandler implements MouseWheelListener {

        private static final GraphMouseWheelHandler instance = new GraphMouseWheelHandler();

        private GraphMouseWheelHandler() {
        }

        private static GraphMouseWheelHandler getInstance() {
            return instance;
        }

        @Override
        public final void mouseWheelMoved(MouseWheelEvent e) {
            GJAbstractGraph gr = (GJAbstractGraph) ((GJAbstractGraph) e.getSource()).getAncestorGraph().getCurrentLayer();

            if (e.getWheelRotation() < 0) {
                double xdiff = (gr.getXMax() - gr.getXMin());
                double ydiff = (gr.getYMax() - gr.getYMin());
                gr.setAxesBounds(gr.getXMin() + xdiff / 100.0,
                        gr.getYMin() + ydiff / 100.0,
                        xdiff - 2 * xdiff / 100.0,
                        ydiff - 2 * ydiff / 100.0);
            } else if (e.getWheelRotation() > 0) {
                double xdiff = (gr.getXMax() - gr.getXMin());
                double ydiff = (gr.getYMax() - gr.getYMin());
                gr.setAxesBounds(gr.getXMin() - xdiff / 100.0,
                        gr.getYMin() - ydiff / 100.0,
                        xdiff + 2 * xdiff / 100.0,
                        ydiff + 2 * ydiff / 100.0);
            }
        }
    }

    private static final class GraphMouseHandler extends MouseAdapter {

        private static final GraphMouseHandler instance = new GraphMouseHandler();
        GJAbstractGraph gr;

        private GraphMouseHandler() {
        }

        private static GraphMouseHandler getInstance() {
            return instance;
        }

        @Override
        public final void mouseClicked(MouseEvent e) {
            GJGraphInterface source = (GJGraphInterface) e.getSource();
            source = source.getAncestorGraph();
            gr = (GJAbstractGraph) source.getCurrentLayer();

            if (e.getButton() == MouseEvent.BUTTON1) {
                switch (e.getClickCount()) {
                    case 1:
                        gr = (GJAbstractGraph) e.getSource();
                        gr = (GJAbstractGraph) gr.getAncestorGraph().getCurrentLayer();
                        gr.setDragStart(gr.inverseTransform(e.getPoint()));
                        ArrayList<GJPlotInterface> old = gr.selectedPlots;
                        ArrayList<GJPlotInterface> o = gr.getPlotAt(gr.getDragStart().getX(), gr.getDragStart().getY());
                        gr.setSelectedPlots(o);
                        gr.firePropertyChange("selectedPlots", old, gr.selectedPlots);
                        if (gr.selectedPlots.isEmpty()) {
                            GJContainerMouseHandler.setLastSelected(gr);
                        } else {
                            ArrayList<GJPlotInterface> pp = new ArrayList<GJPlotInterface>();
                            for (GJPlotInterface ppp : gr.selectedPlots) {
                                if (!pp.contains(ppp.getTopPlot())) {
                                    pp.add(ppp.getTopPlot());
                                }
                            }
                            GJContainerMouseHandler.setLastSelected(pp);
                        }
                        break;
                    default:
                        if (source.fetchEditor() == null) {
                            GUIFactory.graphEditor(source.getGraphContainer().getView());
                        } else {
                            GJEditorInterface ed = source.fetchEditor();
                            if (!ed.isShowing()) {
                                ed.setVisible(true);
                            }
                            ed.setState(Frame.NORMAL);
                            ed.refresh();
                        }

                }
            }
        }

        @Override
        public final void mousePressed(MouseEvent e) {
            gr = (GJAbstractGraph) ((GJAbstractGraph) e.getSource()).getAncestorGraph().getCurrentLayer();
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (gr != null) {
                    gr.setMouseDown(gr.inverseTransform(e.getPoint()));
                    gr.pixelROI.setRect(Double.NaN, Double.NaN, 0d, 0d);
                    if (gr.currentROI == null) {
                        gr.currentROI = new Rectangle2D.Double(Double.NaN, Double.NaN, 0d, 0d);
                        gr.availableROI = new ArrayList<Rectangle2D>();
                    }
                    gr.currentROI.setRect(Double.NaN, Double.NaN, 0d, 0d);
                    gr.repaint();
                    if (e.isShiftDown()) {
                        // ROI Selection start
                        double x = gr.xPixelToPosition(e.getPoint().getX());
                        double y = gr.yPixelToPosition(e.getPoint().getY());
                        gr.setCurrentMousePosition(new Point.Double(x, y));
                        gr.pixelROI.setRect(e.getPoint().getX(), e.getPoint().getY(), 0d, 0d);
                    }
                }
            } else if (SwingUtilities.isRightMouseButton(e) && !e.isShiftDown()) {
                // If the parent is a grid with more than one element, supplement
                // the context menu.
                GCGrid grid = (GCGrid) SwingUtilities.getAncestorOfClass(GCGrid.class, gr);
                if ((grid == null || grid.getElements().size() < 2) && gr.gridMenu != null) {
                    gr.contextMenu.remove(gr.gridMenu);
                    gr.gridMenu = null;
                } else if (grid != null && grid.getElements().size() > 1 && gr.gridMenu == null) {
                    gr.gridMenu = grid.getGridMenu();
                    gr.contextMenu.add(gr.gridMenu);
                }
                gr.contextMenu.show(gr, e.getX(), e.getY());
            }
        }

        @Override
        public final void mouseReleased(MouseEvent e) {
            gr = (GJAbstractGraph) ((GJAbstractGraph) e.getSource()).getAncestorGraph().getCurrentLayer();
            gr.contextMenu.setVisible(false);
            if (SwingUtilities.isRightMouseButton(e)) {
                Rectangle2D.Double r = gr.getCurrentROI();
                if (r != null && !Double.isNaN(r.x) && !Double.isNaN(r.y)) {
                    gr.setAxesBounds(r.x, r.y - r.height, r.width, r.height);
                    // This is needed when there is an area fill in a plot.
                    // Call always as this code is run rarely.
                    gr.paintImmediately(0, 0, gr.getWidth(), gr.getHeight());
                }
            }
        }

        @Override
        public final void mouseEntered(MouseEvent e) {
        }

        @Override
        public final void mouseExited(MouseEvent e) {
//            if (gr != null && gr.contextMenu != null && gr.contextMenu.isShowing()) {
//                gr.contextMenu.setVisible(false);
//            }
        }

        @Override
        public final void mouseMoved(MouseEvent e) {
            gr = (GJAbstractGraph) ((GJAbstractGraph) e.getSource()).getAncestorGraph().getCurrentLayer();
            double x, y;
            if (!gr.contextMenu.isShowing()) {
                gr.setCursor(Cursor.getDefaultCursor());
                gr.setDragStart(e.getPoint());
                gr.setDragStart(gr.inverseTransform(gr.getDragStart()));
                x = gr.xPixelToPosition(gr.getDragStart().getX());
                y = gr.yPixelToPosition(gr.getDragStart().getY());
                gr.setCurrentMousePosition(new Point.Double(x, y));
                if (gr.isTextAsInverse()) {
                    x = gr.getXTransform().getInverse(x);
                    y = gr.getYTransform().getInverse(y);
                }
                String str = String.format(gr.getMousePositionTextFormat(), x, y);
                if (gr.getMousePositionTextField() != null) {
                    gr.getMousePositionTextField().setText(str);
                }
                GJAbstractGraph View = gr.getAncestorGraph();
                if (View.getGridInterface() != null) {
                    double[] mn = View.getGridInterface().getLocation(View);
                    View.getGridInterface().getGridIndicator().setText("Subplot [" + mn[0] + ", " + mn[1] + "] Layer:" + View.getLayers().indexOf(gr));
                    View.getGridInterface().getMousePositionIndicator().setText(str);
                }

                /*
                 * Set dragX and dragY which control mouse-driven axis expansion and
                 * location of the origin
                 */
                double tol = 10;
                if ((x > gr.originX && x < gr.originX + gr.majorXHint / tol) || (x < gr.originX && x > gr.originX - gr.majorXHint / tol)) {
                    gr.setDragY(true);
                } else {
                    gr.setDragY(false);
                }
                if ((y > gr.originY && y < gr.originY + gr.majorYHint / tol) || (y < gr.originY && y > gr.originY - gr.majorYHint / tol)) {
                    gr.setDragX(true);
                } else {
                    gr.setDragX(false);
                }

                if (!gr.isDragX() && !gr.isDragY()) {
                    gr.setCursor(Cursor.getDefaultCursor());
                } else if (gr.isDragX() && gr.isDragY()) {
                    gr.setCursor(originCursor);
                } else if (gr.isDragX()) {
                    gr.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                } else if (gr.isDragY()) {
                    gr.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                }
            }
        }

        @Override
        public final void mouseDragged(MouseEvent e) {
            gr = (GJAbstractGraph) ((GJAbstractGraph) e.getSource()).getAncestorGraph().getCurrentLayer();

            if (gr.contextMenu == null || !gr.contextMenu.isShowing()) {
                Rectangle2D old = gr.getAxesBounds();
                if (e.isShiftDown()) {
                    double xmin = Math.min(gr.inverseTransform(e.getPoint()).getX(), gr.getMouseDown().getX());
                    double ymin = Math.min(gr.inverseTransform(e.getPoint()).getY(), gr.getMouseDown().getY());
                    double w = Math.max(gr.inverseTransform(e.getPoint()).getX(), gr.getMouseDown().getX()) - xmin;
                    double h = Math.max(gr.inverseTransform(e.getPoint()).getY(), gr.getMouseDown().getY()) - ymin;
                    gr.pixelROI.setRect(xmin, ymin, w, h);
                    gr.currentROI.setRect(gr.xPixelToPosition(xmin),
                            gr.yPixelToPosition(ymin),
                            w * gr.getPixelWidth(),
                            h * gr.getPixelHeight());
                    double x = gr.xPixelToPosition(e.getPoint().getX());
                    double y = gr.yPixelToPosition(gr.getDragStart().getY());
                    gr.setCurrentMousePosition(new Point.Double(x, y));
                    gr.repaint();
                }

                if (gr.getDragStart() == null) {
                    gr.setDragStart(gr.inverseTransform(e.getPoint()));
                    return;
                }

                Point2D dragEnd = gr.inverseTransform(e.getPoint());
                double distanceX = gr.xPixelToPosition(dragEnd.getX())
                        - gr.xPixelToPosition(gr.getDragStart().getX());
                double distanceY = gr.yPixelToPosition(dragEnd.getY())
                        - gr.yPixelToPosition(gr.getDragStart().getY());
                double diff;

                if (!gr.isDragX() && !gr.isDragY() && !e.isShiftDown()) {
                    /*
                     * Move the axes
                     */
                    gr.xLeft = gr.xLeft - distanceX;
                    gr.xRight = gr.xRight - distanceX;
                    gr.yBottom = gr.yBottom - distanceY;
                    gr.yTop = gr.yTop - distanceY;
                } else if (gr.isDragX() && gr.isDragY()) {
                    /*
                     * Move the origin
                     */
                    gr.setOrigin(new Point2D.Double(
                            gr.xPixelToPosition(dragEnd.getX()),
                            gr.yPixelToPosition(dragEnd.getY())));

                } else if (gr.isDragX()) {
                    /*
                     * Change the X-Axis range
                     */
                    double pos = gr.xPixelToPosition(gr.getDragStart().getX());
                    diff = Math.signum(pos) * distanceX;
                    if (gr.xRight < gr.xLeft) {
                        diff = -diff;
                    }
                    gr.xRight = gr.xRight - diff;
                    if (Math.signum(gr.xRight) * Math.signum(gr.xLeft) > 0) {
                        gr.xLeft = gr.xLeft + diff;
                    }
                    gr.setAxesBounds(new Rectangle2D.Double(gr.xLeft, gr.yBottom,
                            gr.xRight - gr.xLeft, gr.yTop - gr.yBottom));
                    gr.setMajorXHint();
                } else if (gr.isDragY()) {
                    /*
                     * Change the Y-Axis range
                     */
                    double pos = gr.yPixelToPosition(gr.getDragStart().getY());
                    diff = Math.signum(pos) * distanceY;
                    if (gr.yTop < gr.yBottom) {
                        diff = -diff;
                    }
                    gr.yTop = gr.yTop - diff;
                    if (Math.signum(gr.yTop) * Math.signum(gr.yBottom) > 0) {
                        gr.yBottom = gr.yBottom + diff;
                    }
                    gr.setAxesBounds(new Rectangle2D.Double(gr.xLeft, gr.yBottom,
                            gr.xRight - gr.xLeft, gr.yTop - gr.yBottom));
                    gr.setMajorYHint();
                }

                gr.setDragStart(dragEnd);
                gr.firePropertyChange("axesBounds", old, gr.getAxesBounds());
            }
        }

    }

    /**
     * Mouse motion handler for the Axis Panels
     */
    private class AxisMouseHandler extends MouseAdapter {

        public boolean dragOriginX = false, dragOriginY = false;

        @Override
        public final void mouseEntered(MouseEvent e) {
            GJAxisPanel panel = (GJAxisPanel) e.getSource();
            switch (panel.getPosition()) {
                case TOP:
                case BOTTOM:
                    if (graphContainer != null
                            && graphContainer.getAddedComponentMouseHandler().getSwingComponent() != null) {
                        graphContainer.getAddedComponentMouseHandler().getSwingComponent().
                                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if (graphContainer != null
                            && graphContainer.getAddedComponentMouseHandler().getSwingComponent() != null) {
                        graphContainer.getAddedComponentMouseHandler().getSwingComponent().
                                setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    }
                    break;
                default:
            }
        }

        @Override
        public final void mouseExited(MouseEvent e) {
            setDragX(false);
            setDragY(false);
            setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public final void mouseReleased(MouseEvent e) {
            GJAxisPanel panel = (GJAxisPanel) e.getSource();
            GJAbstractGraph View = (GJAbstractGraph) panel.getParentGraph();
            View.setDragX(false);
            View.setDragY(false);
        }

        @Override
        public final void mousePressed(MouseEvent e) {
            GJAxisPanel panel = (GJAxisPanel) e.getSource();
            Component View = (Component) panel.getParentGraph();
            setDragStart(inverseTransform(SwingUtilities.convertPoint(panel, e.getPoint(), View)));
        }

        @Override
        public final void mouseMoved(MouseEvent e) {
            GJAxisPanel panel = (GJAxisPanel) e.getSource();
            GJAbstractGraphContainer container = (GJAbstractGraphContainer) panel.getParent();
            GJAbstractGraph View = (GJAbstractGraph) panel.getParentGraph();
            if (View.getAncestorGraph().getMousePositionTextField() != null) {
                View.getAncestorGraph().getMousePositionTextField().setText("");
                if (View.getGridInterface() != null) {
                    View.getGridInterface().getGridIndicator().setText("");
                }
            }
            Point2D dragStartLocal = SwingUtilities.convertPoint(panel, e.getPoint(), View);
            dragStartLocal = inverseTransform(dragStartLocal);
            double x, y;
            x = View.xPixelToPosition(dragStartLocal.getX());
            y = View.yPixelToPosition(dragStartLocal.getY());

            switch (panel.getPosition()) {
                case TOP:
                case BOTTOM:
                    if ((x < View.originX + 0.1 * View.majorXHint)
                            && (x > View.originX - 0.1 * View.majorXHint)) {
                        dragOriginX = true;
                        View.setDragX(false);
                        container.getAddedComponentMouseHandler().getSwingComponent().setCursor(originCursor);
                    } else {
                        View.setDragX(true);
                        dragOriginX = false;
                        container.getAddedComponentMouseHandler().getSwingComponent().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if ((y < View.originY + 0.1 * View.majorYHint)
                            && (y > View.originY - 0.1 * View.majorYHint)) {
                        dragOriginY = true;
                        View.setDragY(false);
                        container.getAddedComponentMouseHandler().getSwingComponent().setCursor(originCursor);
                    } else {
                        dragOriginY = false;
                        View.setDragY(true);
                        container.getAddedComponentMouseHandler().getSwingComponent().setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    }
                default:
            }
        }

        @Override
        public final void mouseDragged(MouseEvent e) {

            Rectangle2D old = getAxesBounds();

            GJAxisPanel panel = (GJAxisPanel) e.getSource();
            GJAbstractGraph View = (GJAbstractGraph) panel.getParentGraph();
            GJAbstractGraphContainer container = View.getGraphContainer();

            View.getAncestorGraph().setCurrentLayer(View);

            Point2D dragEnd = SwingUtilities.convertPoint(panel, e.getPoint(), View);
            dragEnd = inverseTransform(dragEnd);

            double distanceX = View.xPixelToPosition(dragEnd.getX())
                    - View.xPixelToPosition(getDragStart().getX());
            double distanceY = View.yPixelToPosition(dragEnd.getY())
                    - View.yPixelToPosition(getDragStart().getY());
            double diff;

            if (dragOriginX) {
                /*
                 * Move the axes
                 */
                View.setOrigin(new Point2D.Double(View.xPixelToPosition(dragEnd.getX()), View.getOriginY()));
            } else if (dragOriginY) {
                /*
                 * Move the axes
                 */
                View.setOrigin(new Point2D.Double(View.getOriginX(), View.yPixelToPosition(dragEnd.getY())));
            } else if (View.isDragX()) {
                /*
                 * Change the X-Axis range
                 */
                double pos = View.xPixelToPosition(getDragStart().getX());
                diff = Math.signum(pos) * distanceX;
                if (View.xRight < View.xLeft) {
                    diff = -diff;
                }
                View.xRight = View.xRight - diff;
                if (Math.signum(View.xRight) * Math.signum(View.xLeft) > 0) {
                    View.xLeft = View.xLeft + diff;
                }
                View.setAxesBounds(new Rectangle2D.Double(View.xLeft, View.yBottom,
                        View.xRight - View.xLeft, View.yTop - View.yBottom));
                View.setMajorXHint();
            } else if (View.isDragY()) {
                /*
                 * Change the Y-Axis range
                 */
                double pos = View.yPixelToPosition(getDragStart().getY());
                diff = Math.signum(pos) * distanceY;
                if (View.yTop < View.yBottom) {
                    diff = -diff;
                }
                View.yTop = View.yTop - diff;
                if (Math.signum(View.yTop) * Math.signum(View.yBottom) > 0) {
                    View.yBottom = View.yBottom + diff;
                }
                View.setAxesBounds(new Rectangle2D.Double(View.xLeft, View.yBottom,
                        View.xRight - View.xLeft, View.yTop - View.yBottom));
                View.setMajorYHint();
            }

            setDragStart(dragEnd);
            //revalidate();
            if (container != null) {
                container.validate();
            }
            //container.repaint();
            firePropertyChange("axesBounds", old, getAxesBounds());
        }
    }

    public final KeyListener getKeyListener() {
        return GraphCycler.getInstance();

    }

    /**
     * Handles keyboard input when the graph has focus. GraphCycler is a
     * singleton instance of a KeyListener that is shared by all graphs in a JVM
     * session.
     */
    public static class GraphCycler implements KeyListener {

        private static final GraphCycler instance = new GraphCycler();

        private GraphCycler() {
        }

        /**
         * Return the singleton instance
         *
         * @return the GraphCycler instance
         */
        public static GraphCycler getInstance() {
            return instance;
        }

        @Override
        public final void keyTyped(KeyEvent e) {
        }

        @Override
        public final void keyPressed(KeyEvent e) {
            GJAbstractGraph thisGraph;
            if (GJAbstractGraph.class.isAssignableFrom(e.getSource().getClass())) {
                thisGraph = (GJAbstractGraph) e.getSource();
            } else {
                JComponent comp = (JComponent) e.getSource();
                comp.getParent().setComponentZOrder(comp,
                        comp.getParent().getComponentCount() - 1);
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (e.getSource() instanceof GJAbstractGraph) {
                    thisGraph = (GJAbstractGraph) e.getSource();
                    ArrayList<GJGraphInterface> LocalLayers = thisGraph.getAncestorGraph().getLayers();
                    if (LocalLayers.size() == 1) {
                        return;
                    }

                    int idx = LocalLayers.indexOf(thisGraph) + 1;
                    if (idx >= LocalLayers.size()) {
                        idx = 0;
                    }
                    GJGraphInterface thisLayer = thisGraph.getAncestorGraph().getLayers().get(idx);
                    thisGraph.getAncestorGraph().setCurrentLayer(thisLayer);
                }
            } else if (e.getKeyChar() == 'f') {
                if (thisGraph.getGraphContainer() != null) {
                    GJAbstractGraphContainer container = thisGraph.getGraphContainer();
                    JComponent comp = container.getFeaturePane();
                    if (comp != null) {
                        if (container.getComponentZOrder(comp) != 0) {
                            container.setComponentZOrder(comp, 0);
                        } else {
                            container.setComponentZOrder(comp, container.getComponentCount() - 1);
                        }
                        container.repaint();
                    }
                }
            }
        }

        @Override
        public final void keyReleased(KeyEvent ke) {
        }
    }

    @Override
    public final void addLink(Object o) {
        links.add(o);
    }

    @Override
    public final void removeLink(Object o) {
        links.remove(o);
    }

    public final void clearLinks() {
        links.clear();
    }

    @Override
    public final ArrayList<Object> getLinks() {
        return new ArrayList<Object>(links);
    }

    @Override
    public final void setLinks(ArrayList<Object> links) {
        this.links.clear();
        this.links.addAll(links);
    }

    @Override
    public final boolean hasListeners(String propertyName) {
        return getPropertyChangeListeners().length > 0;
    }

    @Override
    public boolean hasSelfListener() {
        return Arrays.asList(getPropertyChangeListeners()).contains(this);
    }

    @Override
    public final void saveAsXML(String fileName) {
        GJEncoder.save(fileName, this);
    }

    @Override
    public void firePropertyChange(PropertyChangeEvent pce) {
        propertyChange(pce);

    }

    private static class GraphMenuAction implements ActionListener {

        final Component target;

        public GraphMenuAction(Component target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component c2 = SwingUtilities.getAncestorOfClass(GJGraphContainer.class, target);
            e.setSource(c2);

            if (e.getActionCommand().equals("Autoscale axes")) {
                ((GJAbstractGraphContainer) e.getSource()).getView().getCurrentLayer().autoScale();
                return;
            }

            if (c2 != null) {
                ActionManager.processAction(e, (Component) e.getSource());
            }
        }
    }

    private static class EditMenuAction implements ActionListener {

        final GJAbstractGraph target;

        public EditMenuAction(GJAbstractGraph target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 1.1

            if (target.fetchEditor() == null && target.getGraphContainer() != null) {
                GUIFactory.graphEditor(target.getGraphContainer().getView());
            } else {
                GJEditorInterface ed = target.fetchEditor();
                if (!ed.isShowing()) {
                    ed.setVisible(true);
                }
                ed.setState(Frame.NORMAL);
                ed.refresh();
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {

        if (!EventQueue.isDispatchThread()) {
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    propertyChange(evt);
                }

            });
            return;
        }

        if (evt.getPropertyName().contains("XTransform") || evt.getPropertyName().contains("YTransform")) {
            boolean needsUpdate = false;
            for (GJPlotInterface p : getAllPlots()) {
                if (p instanceof GJTransformUpdateInterface) {
                    needsUpdate = needsUpdate | ((GJTransformUpdateInterface) p).transformUpdate();
                }
            }
        }

        if (!evt.getPropertyName().contains(".")) {
            revalidate();
            repaint();
        }

        if (links.size() > 0 && evt.getPropertyName().contains("axesBounds")) {
            for (Object linkedGr : new ArrayList<Object>(links)) {
                if (linkedGr instanceof Reference) {
                    if (((Reference) linkedGr).get() == null) {
                        // Remove reference to gc'd object from original
                        links.remove(linkedGr);
                        break;
                    }
                    // Get the referenced object
                    linkedGr = ((Reference) linkedGr).get();
                }
                // Do the work
                if (linkedGr instanceof GJAbstractGraph) {
                    ((GJAbstractGraph) linkedGr).setAxesBounds(getAxesBounds());
                } else if (linkedGr instanceof AxisLink) {
                    AxisLink arr = (AxisLink) linkedGr;
                    updateLinkedAxes(arr);
                }
            }
        }
    }

    /**
     * Updates the linked axes from the propertyChange listener
     *
     * @param link
     */
    private void updateLinkedAxes(AxisLink link) {
        if (link.getPairing().equals(AxisLink.PAIRING.XX)) {
            link.getPairedTarget().setXLeft(this.getXLeft());
            link.getPairedTarget().setXRight(this.getXRight());
        } else if (link.getPairing().equals(AxisLink.PAIRING.XY)) {
            link.getPairedTarget().setYBottom(this.getXLeft());
            link.getPairedTarget().setYTop(this.getXRight());
        } else if (link.getPairing().equals(AxisLink.PAIRING.YY)) {
            link.getPairedTarget().setYBottom(this.getYBottom());
            link.getPairedTarget().setYTop(this.getYTop());
        } else if (link.getPairing().equals(AxisLink.PAIRING.YX)) {
            link.getPairedTarget().setXLeft(this.getYBottom());
            link.getPairedTarget().setXRight(this.getYTop());
        }
    }
}
