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
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import kcl.waterloo.defaults.Colors;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.common.deploy.AbstractDeployableGraphics2D;
import kcl.waterloo.graphics.GJAbstractGraph;
import kcl.waterloo.graphics.GJGraph;
import kcl.waterloo.graphics.GJGraphContainer;
import kcl.waterloo.graphics.GJUtilities;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.marker.ShapeUtils;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.plotmodel2D.GJCyclicArrayList;
import kcl.waterloo.plotmodel2D.GJDataModel;
import kcl.waterloo.plotmodel2D.GJFillableInterface;
import kcl.waterloo.swing.GCFrame;
import kcl.waterloo.xml.GJEncoder;
import org.jdesktop.swingx.painter.effects.AreaEffect;
import org.jdesktop.swingx.painter.effects.GlowPathEffect;

/**
 * Abstract superclass for the individual plot types. GJAbstractPlot defines
 * methods and properties common to each of them. Some subclasses will override
 * the methods in the abstract class.
 *
 * Most plot sub-classes support multiplexed data. For these sub-classes,
 * isMultiplexible returns true and properties such as the Marker, EdgeColor,
 * Fill etc should be an array of values that will be cycled through during
 * plotting.
 *
 * <p>
 * Required methods are defined in the GJPlotInterface interface. Among these,
 * subclasses must implement the following methods:
 * <p>
 * a public createInstance method that calls the constructor</p>
 * <p>
 * paintPlot</p>
 *
 * In addition, if multiplexed plots are not supported <@code isMultiplexible>
 * should be overridden to return <@code false>.
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * Revisions: 18.05.2013 Now implements GJFastPlotInterface
 */
public abstract class GJAbstractPlot extends GJAbstractPlotModelImpl
        implements GJFillableInterface<Shape, Paint>,
        GJFastPlotInterface {

    /**
     * The parent of this plot. Null for a topPlot
     */
    private GJPlotInterface parentPlot = null;
    /**
     * Flag to set the visibility of the plot. Set false to prevent this plot
     * being painted. It may be useful to set this to false while updating the
     * data or visual model for a plot rather than include conditional
     * statements in the paintPlot method.
     */
    boolean visible = true;
    /**
     * True if this plot has been selected (by mouse or programmatically).
     */
    boolean selected = false;
    /**
     * A SwingX area effect to apply to this plot e.g. a shadow.
     */
    private AreaEffect effect;
    /**
     * For a topPlot only, specifies another GJFillableInterface - the area
     * returned by the getAreaFill method of this plot will be painted.
     */
    private GJFillableInterface<Shape, Paint> areaFill = null;
    /**
     * Paint to use for filling the getAreaFill region.
     */
    private Paint areaPaint = Colors.getColor("AREAFILL");
    private static GlowPathEffect glow = (GlowPathEffect) GJDefaults.getMap().get("GJAbstractPlot.selection");
    private AlphaComposite fillComposite = null;

    /**
     * Default protected constructor. This will be called implicitly during
     * subclass construction.
     */
    protected GJAbstractPlot() {
        //setPlotList(null);
    }

    /**
     * Creates a new plot from an existing plot.
     * <ol>
     * <li>The data and visual model of the source is copied by reference and
     * shared with the destination<li>
     * <li>A new screen data array is created</li>
     * <li>All parentPlot references are set to null</li>
     * </ol>
     *
     * @param p
     */
    protected GJAbstractPlot(GJAbstractPlot p) {
        super();
        initNewInstance();
        removePropertyChangeListener(p);
        setVisualModel(p.getVisualModel());
        GJDataModel model = p.getDataModel();
        setDataModel(model);
        model.getXData().addPropertyChangeListener(this);
        model.getYData().addPropertyChangeListener(this);
        model.addPropertyChangeListener(this);
        setScreenDataArray(new ArrayList<Shape>());
        addPropertyChangeListener(this);
        parentPlot = null;
    }

    /**
     * Factory method for initializing a plot. Call this from the
     * {@code createInstance} method in the specific plot class passing in an
     * instance of the plot created using its private constructor i.e. call
     * {@code super.createInstance} from the {@code createInstance} method of
     * each concrete subclass. Example:
     * <p>
     * <i> <br>class myPlot extends GJAbstractPlot {<br> public GJPlotInterface
     * createInstance(){<br> return super.createInstance(new myPlot());<br>
     * }<br> ....<br> }<br>
     * }<br></i></p>
     *
     * @param p
     * @return a GJPlotInterface
     */
    protected static GJPlotInterface createInstance(GJPlotInterface p) {

        p.setDataModel(GJDataModel.createInstance());

        //((GJAbstractPlot) p).setPlotList(new ArrayList<GJPlotInterface>());
        String symbol = (String) GJDefaults.getMap().get("GJAbstractPlot.markerSymbol");

        double markerSize = ((Double) GJDefaults.getMap().get("GJAbstractPlot.markerSize")).doubleValue();
        p.getVisualModel().setMarkerArray(new GJCyclicArrayList<GJMarker>(GJMarker.getMarker(symbol, markerSize)));
        p.getVisualModel().setDynamicMarkerSize(new GJCyclicArrayList<Dimension>());

        Paint edgeColor = (Paint) GJDefaults.getMap().get("GJAbstractPlot.edgeColor");
        p.getVisualModel().setEdgeColor(new GJCyclicArrayList<Paint>(edgeColor));

        Float edgeWidth = (Float) GJDefaults.getMap().get("GJAbstractPlot.edgeStrokeWidth");

        GJCyclicArrayList<BasicStroke> edgeStroke
                = new GJCyclicArrayList<BasicStroke>(new BasicStroke(edgeWidth));
        p.getVisualModel().setEdgeStroke(edgeStroke);

        Paint lineColor = (Paint) GJDefaults.getMap().get("GJAbstractPlot.lineColor");
        p.getVisualModel().setLineColor(new GJCyclicArrayList<Paint>(lineColor));

        float lineWidth = ((Float) GJDefaults.getMap().get("GJAbstractPlot.lineStrokeWidth"));
        GJCyclicArrayList<BasicStroke> lineStroke
                = new GJCyclicArrayList<BasicStroke>(GJUtilities.makeStroke(lineWidth, (String) GJDefaults.getMap().get("GJAbstractPlot.lineStyle")));
        p.getVisualModel().setLineStroke(lineStroke);

        Paint fill = (Paint) GJDefaults.getMap().get("GJAbstractPlot.fill");
        p.getVisualModel().setFill(new GJCyclicArrayList<Paint>(fill));

        p.setAntialiasing(((Boolean) GJDefaults.getMap().get("GJAbstractPlot.antiAliasing")));

        p.addPropertyChangeListener(p);
        return p;
    }

    @Override
    public boolean isMultiplexible() {
        return true;
    }

    /**
     * Returns true if this is a top plot, i.e. a plot that is not a descendant
     * of another plot
     *
     * @return true/false
     */
    @Override
    public final boolean isTopPlot() {
        return (parentPlot == null);
    }

    @Override
    public final void setVisible(boolean flag) {
        visible = flag;
    }

    @Override
    public final boolean isVisible() {
        return visible;
    }

    /**
     * Returns true if this plot is visible and in a host container that returns
     * isVisible==true.
     *
     * @return the flag
     */
    public final boolean isShowing() {
        Container c = (Container) getParentGraph();
        return c != null && c.isShowing() && isVisible();
    }

    @Override
    public final void setSelected(boolean flag) {
        boolean old = selected;
        if (!isTopPlot()) {
            getParentPlot().setSelected(flag);
        }
        selected = flag;
        for (GJPlotInterface p : getPlotList()) {
            p.setSelectionFlag(flag);
        }
        getPCS().firePropertyChange("selected", old, isSelected());
    }

    @Override
    public final void setSelectionFlag(boolean flag) {
        selected = flag;
        for (GJPlotInterface p : getPlotList()) {
            p.setSelectionFlag(flag);
        }
    }

    @Override
    public final boolean isSelected() {
        return selected;
    }

    @Override
    public final GJPlotInterface plus(GJPlotInterface p2) {
        return this.add(p2);
    }

    @Override
    public final GJPlotInterface add(GJPlotInterface plot) {
        if (this.getNode().contains(plot)
                || plot.getNode().contains(this)) {
            throw (new IllegalArgumentException("Plots are already in the same plot hierarchy"));
        }
        if (plot.getParentPlot() != null) {
            plot.getParentPlot().getPlotList().remove(plot);
        }
        plot.setParentPlot(this);
        getPlotList().add(plot);
        if (getParentGraph() != null) {
            ((Component) getParentGraph()).repaint();
        }
        return this;
    }

    @Override
    public final GJPlotInterface getParentPlot() {
        return parentPlot;
    }

    @Override
    public final void setParentPlot(GJPlotInterface p) {
        parentPlot = p;
    }

    @Override
    public final boolean intersects(Point2D p) {
        return intersects(p.getX(), p.getY());
    }

    @Override
    public final boolean intersects(double x, double y) {
        for (Iterator<Shape> it = getScreenDataArray().iterator(); it.hasNext();) {
            if (it.next().intersects(x - 5, y - 5, 10, 10)) {
                return true;
            }
        }
        boolean flag;
        for (Iterator<GJPlotInterface> it = getPlotList().iterator(); it.hasNext();) {
            GJPlotInterface p = it.next();
            flag = p.intersects(x, y);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final ArrayList<GJPlotInterface> findPlotBelow(Point2D p) {
        return findPlotBelow(p.getX(), p.getY());
    }

    @Override
    public final ArrayList<GJPlotInterface> findPlotBelow(double x, double y) {
        ArrayList<GJPlotInterface> arr = new ArrayList<GJPlotInterface>();
        boolean flag;
        for (GJPlotInterface p : getPlotList()) {
            flag = p.intersects(x, y);
            if (flag) {
                arr.add(p);
            }
        }
        for (Iterator<Shape> it = getScreenDataArray().iterator(); it.hasNext();) {
            if (it.next().intersects(x - 5, y - 5, 10, 10)) {
                arr.add(this);
            }
        }
        return arr;
    }

    @Override
    @Deprecated
    public final ArrayList<GJPlotInterface> getPlots() {
        return getPlotList();
    }

    @Override
    @SuppressWarnings("deprecation")
    public final ArrayList<GJPlotInterface> getNode() {
        ArrayList<GJPlotInterface> list = new ArrayList<GJPlotInterface>();

        // TODO: Remove this in future release
        /**
         * Back-compatibility code
         */
        /**
         * This code will run when de-serializing XML files saved before the 1.1
         * release and will move the relevant plot list from the data model to
         * the plot instance.
         */
        if (getDataModel().getPlotList() != null && getDataModel().getPlotList().size() > 0) {
            for (GJPlotInterface p : getDataModel().getPlotList()) {
                p.setParentPlot(this);
            }
            getPlotList().addAll(getDataModel().getPlotList());
            getDataModel().setPlotList(null);
        }
        /**
         * End of back-compatibility code
         */

        list.add(this);
        if (getPlotList() != null) {
            for (GJPlotInterface p : getPlotList()) {
                list.addAll(p.getNode());
            }
        }
        return list;
    }

    /**
     * getDataRange returns the range on the X and Y axes that can potentially
     * be filled by the data points given the current contents of the XData and
     * YData properties.
     *
     * Note that this is not necessarily the same as the range of the data
     * contained in the XData and YData properties (although it often will be
     * for a simple Cartesian plot).
     *
     * getVisualRange can be used instead to assess the required axes limits
     * needed to render the data given e.g. the set marker size etc. However,
     * getVisualRange can only be used to update the display: it requires that
     * data have already been rendered to screen at least once.
     *
     * @return a Rectangle2D instance
     */
    @Override
    public Rectangle2D getDataRange() {
        if (this.getXDataValues() != null && this.getYDataValues() != null) {
            double[] x = ArrayMath.minmax(this.getXDataValues());
            double[] y = ArrayMath.minmax(this.getYDataValues());
            Rectangle2D r = new Rectangle.Double(x[0], y[0], x[1] - x[0], y[1] - y[0]);
            Rectangle2D r2;
            for (GJPlotInterface p : getPlotList()) {
                r2 = p.getDataRange();
                r = r.createUnion(r2);
            }
            return r;
        } else {
            return new Rectangle.Double(0, 0, 0, 0);
        }
    }

    @Override
    public GJPlotInterface getTopPlot() {
        GJPlotInterface p = this;
        while (!p.isTopPlot()) {
            p = p.getParentPlot();
        }
        return p;
    }

    @Override
    public final Rectangle2D getVisualRange() {
        if (getParentGraph() == null) {
            return new Rectangle.Double(0, 0, 0, 0);
        } else {
            if (!isTopPlot()) {
                return getTopPlot().getVisualRange();
            }
            if (getScreenDataArray().size() > 0) {
                Rectangle2D bounds = getScreenDataArray().get(0).getBounds2D();
                for (GJPlotInterface p : getNode()) {
                    if (p.getScreenDataArray().size() > 0) {
                        for (Shape s : p.getScreenDataArrayAsCopy()) {
                            bounds = bounds.createUnion(s.getBounds2D());
                        }
                    }
                }
                return getParentGraph().convertPixelsToPosition(bounds);
            } else {
                return getParentGraph().getAxesBounds();
            }
        }
    }

    /**
     * Entry point for plotting called from the graph methods.
     *
     * @param g2
     */
    @Override
    public final void paintPlotEntry(Graphics2D g2) {
        double[] x = getXDataValues();
        if (x != null && x.length > 0 && isVisible()) {
            Graphics2D g = (Graphics2D) g2.create();
            setupGraphics(g);
            paintPlot(g);
            paintSelected(g);
            g.dispose();
        }
    }

    /**
     * Note that this method is overloaded by all concrete sub-classes which
     * should call this super-class method.
     *
     * @param g2
     */
    @Override
    public void paintPlot(Graphics2D g2) {
        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g2).setPaintObject(this);
        }
        paintChildPlots(g2);
        if (isTopPlot() && getAreaFill() != null) {
            Composite composite = null;
            if (getFillComposite() != null) {
                composite = g2.getComposite();
                g2.setComposite(getFillComposite());
            }
            paintAreaFill(g2);
            paintChildPlots(g2);
            if (composite != null) {
                g2.setComposite(composite);
            }
        }
        if (getEffect() != null) {
            drawEffect(g2);
        }
    }

    /**
     * Paints the child plots
     *
     * @param g2 Graphics2D object
     */
    final void paintChildPlots(Graphics2D g2) {
        for (GJPlotInterface p : new ArrayList<GJPlotInterface>(getPlotList())) {
            Graphics2D g = (Graphics2D) g2.create();
            ((GJAbstractPlot) p).setupGraphics(g);
            p.paintPlot(g2);
            ((GJAbstractPlot) p).paintSelected(g);
            g.dispose();
        }
    }

    /**
     * Paints the area returned by the areaFill getFillable call.
     */
    final void paintAreaFill(Graphics2D g2) {
        if (getAreaFill() != null) {
            Shape s = getFillable();
            if (s != null) {
                Composite composite = null;
                if (getAreaFill().getFillComposite() != null) {
                    composite = g2.getComposite();
                    g2.setComposite(getAreaFill().getFillComposite());
                }
                g2.setPaint(getAreaFill().getAreaPaint());
                g2.fill(s);
                if (composite != null) {
                    g2.setComposite(composite);
                }
            }
        }
    }

    @Override
    public void plotRedraw() {
        Graphics2D g = (Graphics2D) ((Component) getParentGraph()).getGraphics();
        setupGraphics(g);
        paintPlot(g);
    }

    @Override
    public void plotRedraw(Graphics2D g) {
        paintPlot(g);
    }

    @Override
    public boolean plotUpdate() {
        plotRedraw();
        return false;
    }

    @Override
    public boolean plotUpdate(Graphics2D g) {
        plotRedraw(g);
        return false;
    }

    /**
     * getFillable provides the Area instance to fill. This method can be
     * overridden in custom-classes to return a different Area instance.
     *
     * @return the Area to fill when painting the plot.
     */
    @Override
    public Shape getFillable() {
        if (getAreaFill() != null) {
            if (getAreaFill() instanceof GJPlotInterface) {
                return localFill();
            } else if (getAreaFill() instanceof GJFill) {
                if (((GJFill) getAreaFill()).getOrientation().equals(GJFill.ORIENTATION.ARBITRARY)) {
                    return ((GJFill) getAreaFill()).getPixelArea();
                } else {
                    return getAreaFill().getFillable();
                }
            }
        }
        return null;
    }

    private Shape localFill() {
        final Area area0 = new Area();
        final double ymin = getParentGraph().getYMin();
        ArrayList<GJPlotInterface> p2 = ((GJPlotInterface) getAreaFill()).getNode();
        for (GJPlotInterface plot : p2) {
            for (Shape s : plot.getScreenDataArray()) {
                area0.add(new Area(ShapeUtils.getFromX(getParentGraph(), s, ymin)));
            }
        }
        p2 = getNode();
        final Area area1 = new Area();
        for (GJPlotInterface plot : p2) {
            for (Shape s : plot.getScreenDataArray()) {
                area1.add(new Area(ShapeUtils.getFromX(getParentGraph(), s, ymin)));
            }
        }
        Rectangle2D r0 = area0.getBounds2D();
        Rectangle2D r1 = area1.getBounds2D();
        r0 = r0.createIntersection(r1);
        double w = this.getMarker(0).getPath().getBounds2D().getWidth();
        r1 = new Rectangle2D.Double(Math.ceil(r0.getMinX() + w / 2) + 1, ymin, Math.floor(r0.getWidth() - w) - 2, r0.getMaxY() - getParentGraph().getYMin());
        final Area area3 = (Area) area0.clone();
        area3.subtract(area1);
        area1.subtract(area0);
        area3.add(area1);
        area3.intersect(new Area(r1));
        return area3;
    }

    /**
     * Paints the selected plots.
     *
     * @param g2
     */
    private void paintSelected(Graphics2D g2) {
        if (isSelected()) {
            if (getXDataValues().length < 500) {
                long t = System.currentTimeMillis();
                Composite old = g2.getComposite();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                for (Shape thisPath : getScreenDataArray()) {
                    if (System.currentTimeMillis() - t > 200L) {
                        ((GJAbstractGraph) getParentGraph()).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    }
                    try {
                        glow.apply(g2, thisPath, 20, 20);
                    } catch (IllegalArgumentException ex) {
                    }
                }
                ((GJAbstractGraph) getParentGraph()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                g2.setComposite(old);
            }
        }
    }

    @Override
    public final void setAntialiasing(boolean flag) {
        if (flag) {
            setRenderHintState(RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            setRenderHintState(RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Called by paintPlot method for subclasses to set up default graphics
     * mode. This is only called on a TopLevel plot.
     *
     * @param g2 Graphics 2D object
     */
    private void setupGraphics(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(getCompositeMode(), getAlpha()));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, getRenderHintState());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, getTextHintState());
    }

    @Override
    public void saveAsXML(String fileName) {
        GJEncoder.save(fileName, this);
    }

    @Override
    public GCFrame createFrame() {
        final GCFrame f = new GCFrame();
        final GJPlotInterface plot = this;
        GJAbstractGraph graph = GJGraph.createInstance();
        GJGraphContainer gr = GJGraphContainer.createInstance(graph);
        f.add(gr);
        f.setSize(500, 500);
        graph.add(plot);
        graph.autoScale();
        return f;
    }

    public boolean isOnEDT() {
        return EventQueue.isDispatchThread();
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }

    @Override
    public String toString() {
        return toString(false, "");
    }

    public String toString(boolean flag) {
        return toString(flag, "");
    }

    protected String toString(boolean flag, String tab) {
        StringBuilder s = new StringBuilder();
        Formatter f = new Formatter(s);
        String clss = getClass().toString().replace("kcl.waterloo.graphics.plots2D.", "");
        clss = clss.replace("class", "");
        f.format(tab + "Waterloo graphics %s plot [@%x]:\n", clss, hashCode());
        if (getParentGraph() == null) {
            f.format(tab + "Parent Graph: null\n");

        } else {
            f.format(tab + "Parent Graph: [@%x]\n", getParentGraph().hashCode());
        }
        if (getParentPlot() == null) {
            f.format(tab + "Parent plot: null\n");

        } else {
            f.format(tab + "Parent Plot: [@%x]\n", getParentPlot().hashCode());
        }
        f.format(tab + "Is top plot:\t" + isTopPlot() + "\n");
        f.format(tab + "Child plots:\t" + (getPlots().size() > 0) + "\n");
        f.format(tab + "Multiplexed:\t" + isMultiplexed() + "\n");
        f.format(tab + "Alpha:\t" + getAlpha() + "\n");
        f.format(tab + "Data (untransformed):\n");
        f.format(tab + "x:\t");
        printData(f, getXData().getRawDataValues());
        f.format(tab + "y:\t");
        printData(f, getYData().getRawDataValues());
        f.format(tab + stringSupplement());
        if (flag && getPlotList().size() > 0) {
            for (GJPlotInterface p : getPlotList()) {
                f.format("\n" + tab + "----Child-----\n");
                f.format(tab + ((GJAbstractPlot) p).toString(true, tab));
                tab = tab + "\t";
            }
        }
        return f.toString();
    }

    /**
     * Adds a supplement to the {@code fromString} returned by the toString()
     * method. Subclasses can override this method to add information to the end
     * of the toString() output.
     *
     * @return the supplementary {@code fromString}
     */
    protected String stringSupplement() {
        return "";
    }

    protected static void printData(Formatter f, double[] in) {
        final int N = 10;
        if (in.length == 1) {
            f.format("%5.2f%n", in[0]);
            return;
        }
        for (int k = 0; k < Math.min(N, in.length); k++) {
            f.format("%5.2f", in[k]);
            if (k < N) {
                f.format(", ");
            }
        }
        if (in.length > N) {
            f.format("%5.2f...%n", in[N]);
        } else {
            f.format("%n");
        }
    }

    @Override
    public void setEffect(AreaEffect effect) {
        this.effect = effect;
    }

    @Override
    public AreaEffect getEffect() {
        return effect;
    }

    protected final void drawEffect(final Graphics2D g) {
        if (effect != null) {
            for (Shape s : getScreenDataArrayAsCopy()) {
                effect.apply(g, s, (int) s.getBounds().getWidth(), (int) s.getBounds().getHeight());
            }
        }
    }

    /**
     * @return the areaFill
     */
    public GJFillableInterface<Shape, Paint> getAreaFill() {
        return areaFill;
    }

    /**
     * @param areaFill the areaFill to set
     */
    public void setAreaFill(GJFillableInterface<Shape, Paint> areaFill) {
        if (areaPaint != null) {
            areaFill.setAreaPaint(areaPaint);
        }
        this.areaFill = areaFill;
    }

    @Override
    public Paint getAreaPaint() {
        if (getAreaFill() != null) {
            return getAreaFill().getAreaPaint();
        } else {
            return areaPaint;
        }
    }

    @Override
    public void setAreaPaint(Paint p) {
        if (getAreaFill() != null) {
            getAreaFill().setAreaPaint(p);
        }
        areaPaint = p;
    }

    /**
     * @return the composite
     */
    @Override
    public AlphaComposite getFillComposite() {
        return fillComposite;
    }

    /**
     * @param composite the composite to set
     */
    @Override
    public void setFillComposite(AlphaComposite composite) {
        this.fillComposite = composite;
    }

    @Override
    public float getFillAlpha() {
        if (fillComposite != null) {
            return fillComposite.getAlpha();
        } else {
            return this.getAlpha();
        }
    }

    @Override
    public void setFillAlpha(float alpha) {
        if (fillComposite != null) {
            fillComposite = fillComposite.derive(alpha);
        } else {
            fillComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        }
    }
}
