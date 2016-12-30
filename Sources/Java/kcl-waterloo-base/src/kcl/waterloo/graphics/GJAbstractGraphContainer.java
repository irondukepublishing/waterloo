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

import java.awt.*;
import java.awt.geom.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import kcl.waterloo.annotation.GJAnnotatableInterface;
import kcl.waterloo.annotation.GJAnnotationInterface;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.effects.GJEffectorInterface;
import kcl.waterloo.marker.ShapeUtils;
import kcl.waterloo.observable.GJLinkableInterface;
import kcl.waterloo.observable.GJObservableInterface;
import kcl.waterloo.swing.GCFrame;
import org.jdesktop.swingx.painter.effects.AreaEffect;

/**
 * <p>
 * GJAbstractGraphContainer implements most of the methods for graph
 * containers</p>
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAbstractGraphContainer extends GJBasicPanel
        implements GJAnnotatableInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>,
        GJObservableInterface,
        GJLinkableInterface,
        GJEffectorInterface {

    private Object keyAntialiasing = GJDefaults.getMap2().get("GJAbstractGraphContainer.keyAntialiasing");
    private Object textAntialiasing = GJDefaults.getMap2().get("GJAbstractGraphContainer.textAntialiasing");
    private static final long serialVersionUID = 1L;
    private final AffineTransform aft = new AffineTransform();
    private GJContainerMouseHandler containerMouseHandler = new GJContainerMouseHandler();
    private GJAddedComponentMouseHandler addedComponentMouseHandler = new GJAddedComponentMouseHandler();
    private boolean translatable = true;
    /**
     * set true to a paint box around the graph
     */
    private boolean axisBox = false;
    /**
     * The view contained in this container
     */
    private GJGraphInterface view = null;
    /**
     * Component for graph title
     */
    private JFormattedTextField title = new JFormattedTextField();
    /**
     * Component for graph sub-title
     */
    private JFormattedTextField subtitle = new JFormattedTextField();
    private double rotation = 0;
    private double zoom = 1.0;
    private ArrayList<Object> links = new ArrayList<Object>();
    private JComponent featurePane = null;
    private ArrayList<GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>> annotations = new ArrayList<GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>>();
    /**
     * Sets the required aspect ratio for the view dimensions (width/height).
     * Zero means no preference. The aspect ratio needs to be managed via the
     * layout manager.
     */
    private double aspectRatio = 0d;
    private AreaEffect effect = null;

    /**
     * Default constructor returns a {@code GJAbstractGraphContainer} instance.
     */
    GJAbstractGraphContainer() {
        super();
        setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        backgroundPainted = ((Boolean) GJDefaults.getMap().get("GJAbstractGraphContainer.backgroundPainted")).booleanValue();
        setBackground((Color) GJDefaults.getMap().get("GJAbstractGraphContainer.backgroundColor"));
        setLayout(new GraphContainerLayout());
        setLayout(new GraphContainerLayout());
        setFont((Font) GJDefaults.getMap().get("GJAbstractGraphContainer.font"), true);
        installTitle(title);
        installSubTitle(subtitle);
    }

    /**
     * @param c
     * @return the added component </p>
     */
    @Override
    public final Component add(Component c) {
        Component[] old = getComponents();
        c = super.add(c);
        c.addMouseListener(getAddedComponentMouseHandler());
        c.addMouseMotionListener(getAddedComponentMouseHandler());
        firePropertyChange("components", old, getComponents());
        if (((GraphContainerLayout) getLayout()).getComponentMap().containsKey(c)) {
            // This will run during deserialization when the standard add 
            // method will be called by XMLDecoder.
        }
        return c;
    }

    /**
     * Adds a component to the layout of the container centered at the
     * coordinates specified in x and y. The coordinates are relative to the
     * axes of the graph contained in the component (but added components may
     * extend beyond the clip limits of the graph). The added component is
     * promoted to zero Z-order, so components that are added successively
     * appear above one another (and above the graph). The pixel-based
     * constraints of the added component will be updated internally as
     * necessary.
     *
     *
     * @param c the component to add
     * @param x the x coordinate in GJAbstractGraph space
     * @param y the y coordinate in GJAbstractGraph space
     * @return the added component </p>
     */
    public Component add(Component c, double x, double y) {
        return add(c, x, y, SwingConstants.CENTER, SwingConstants.CENTER);
    }

    /**
     * <p>
     * Adds a component to the layout of the container at the coordinates
     * specified by x and y. Horizontal and vertical alignnment. are specified
     * using an appropriate value from {@code SwingConstants}: TOP, BOTTOM,
     * LEFT, RIGHT or CENTERED. The coordinates are relative to the axes of the
     * graph contained in the component (but added components may extend beyond
     * the clip limits of the graph.). The added component is promoted to zero
     * Z-order, so components that are added successively appear above one
     * another (and above the graph).. The pixel-based constraints of the added
     * component will be updated internally as necessary. Alternatively, use the
     *
     *
     * @param c the component to add
     * @param x the x-axis coordinate
     * @param y the y-axis coordinate
     * @param alignX Horizontal alignment specified using a SwingConstant value
     * @param alignY Vertical alignment specified using a SwingConstant value
     * @return the added Component
     */
    public Component add(Component c, double x, double y, int alignX, int alignY) {
        return local_add(c, x, y, alignX, alignY);
    }

    @Override
    public void add(GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> a) {
        if (a.getParentGraph() == null) {
            a.setParentGraph(getView());
        }
        getAnnotations().add(a);
    }

    private Component local_add(Component c, double x, double y, int alignX, int alignY) {
        c = super.add(c, 0);
        GraphContainerLayout layout = (GraphContainerLayout) getLayout();
        layout.putGraphicConstraint(c, x, y, alignX, alignY);
        //setComponentZOrder(c, 0);
        revalidate();
        c.addMouseListener(getAddedComponentMouseHandler());
        c.addMouseMotionListener(getAddedComponentMouseHandler());
        return c;
    }

    public JComponent setFeaturePane(JComponent comp) {
        if (featurePane != null) {
            remove(featurePane);
        }
        featurePane = comp;
        comp.addKeyListener(((GJAbstractGraph) getView()).getKeyListener());
        return (JComponent) super.add(comp);
    }

    public JComponent getFeaturePane() {
        return featurePane;
    }

    public boolean transformContains(int x, int y) {
        return inverseTransform(getBounds()).contains(x, y);
    }

    public boolean transformContains(Point p) {
        return inverseTransform(getBounds()).contains(p);
    }

    @Override
    public final void setFont(final Font f) {
        setFont(f, false);
    }

    public final void setFont(final Font f, boolean flag) {
        if (f.equals(getFont())) {
            return;
        }
        Font old = this.getFont();
        if (flag) {
            ArrayList<JComponent> arr = GJAbstractGraphContainer.getAllComponents(this);
            for (JComponent c : arr) {
                if (c instanceof GJAbstractGraphContainer) {
                    super.setFont(f);
                } else {
                    c.setFont(f);
                }
            }
        } else {
            super.setFont(f);
        }
        firePropertyChange("font", old, f);
    }

    @Override
    public final void setForeground(final Color color) {
        setForeground(color, false);
    }

    public final void setForeground(final Color color, boolean flag) {
        if (color.equals(getForeground())) {
            return;
        }
        Color old = this.getForeground();
        if (flag) {
            ArrayList<JComponent> arr = GJAbstractGraphContainer.getAllComponents(this);
            for (JComponent c : arr) {
                if (c instanceof GJAbstractGraphContainer) {
                    super.setForeground(color);
                } else {
                    c.setForeground(color);
                }
            }
        } else {
            super.setForeground(color);
        }
        firePropertyChange("foreGround", old, color);
    }

    public void setTranslatable(boolean flag) {
        if (!flag) {
            setRotation(0.0d);
            setZoom(1.0d);
        }
        translatable = flag;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    /**
     * Returns the view containing the graph in the container
     *
     * @return the embedded view
     */
    public GJGraphInterface getView() {
        return this.view;
    }

    public void setRotation(double theta) {
        double old = rotation;
        if (translatable) {
            rotation = theta;
        }
        firePropertyChange("rotation", old, rotation);
    }

    public double getRotation() {
        return rotation;
    }

    public void setZoom(double zoom) {
        double old = this.zoom;
        if (translatable) {
            this.zoom = zoom;
        }
        firePropertyChange("zoom", old, this.zoom);
    }

    public double getZoom() {
        return zoom;
    }

    public boolean getAxisBox() {
        return axisBox;
    }

    public void setAxisBox(boolean flag) {
        axisBox = flag;
    }

    @Override
    public ArrayList<GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>> getAnnotations() {
        return annotations;
    }

    @Override
    public void setAnnotations(ArrayList<GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>> a) {
        ArrayList<GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font>> old = getAnnotations();
        annotations = a;
        firePropertyChange("annotations", old, getAnnotations());
    }

    /**
     * Place the view in the container.
     *
     * @param graph - a GJGraphInterface instance
     */
    public void setView(GJGraphInterface graph) {
        if (view != null) {
            remove((Component) view);
        }
        super.add((Component) graph);
        this.view = graph;
        this.view.setGraphContainer(this);

//        //this.view.createAxes();
//
//        if (!GJDefaults.getDeserializing()) {
//            graph.setTopAxisPainted(true);
//            graph.setRightAxisPainted(true);
//        }
        this.validate();
        this.repaint();
        addMouseListener(getContainerMouseHandler());
        addMouseMotionListener(getContainerMouseHandler());
    }

    @Override
    public void remove(Component c) {
        GraphContainerLayout layout = (GraphContainerLayout) getLayout();
        if (layout.getComponentMap().containsKey(c)) {
            layout.getComponentMap().remove(c);
        }
        if (c instanceof GJAbstractGraph) {
            ((GJAbstractGraph) c).nullifyParent();
        }
        super.remove(c);
    }

    public void setKeyAntialiasing(Object o) {
        keyAntialiasing = o;
    }

    public void setTextAntialiasing(Object o) {
        textAntialiasing = o;
    }

    protected void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, keyAntialiasing);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, textAntialiasing);
        g2.setFont(getFont());
    }

    @Override
    public void setBackgroundPainted(boolean flag) {
        boolean old = backgroundPainted;
        backgroundPainted = flag;
        firePropertyChange("backPainted", old, backgroundPainted);
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        setupGraphics(g2);
        paintComponent(g2);
        paintBorder(g2);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        setupGraphics(g2);
        paintBackground(g2);
        if (getView() != null) {
            doTransform(g2);
            super.paintComponent(g2);
            if (effect != null) {
                Rectangle r = getBounds();
                r.setRect(r.x + 10, r.y + 10, r.width - 20, r.height - 20);
                effect.apply(g2, r, (int) r.getWidth(), (int) r.getHeight());
            }
            paintChildren(g2);
            paintAxes(g2);
            paintAnnotations(g2);
        }
        g2.dispose();
    }

    /**
     * <p>
     * This painting method can to be overridden by subclasses of
     * <code>GJAbstractGraph</code>. This method is called after all the
     * painting is done. By overriding this method, a subclass can display extra
     * information on top of the graph.</p>
     * <p>
     * The graphics surface passed as parameter is configured by
     * {@code #setupGraphics(Graphics2D)}.</p>
     *
     * @param g2 the graphics surface on which the graph is drawn
     */
    protected void paintAnnotations(Graphics2D g2) {
        g2 = (Graphics2D) g2.create();

        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(0.5f));
        for (GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> annot : getAnnotations()) {
            GJGraphInterface gr = annot.getParentGraph();
            if (gr == null) {
                gr = getView();
            }
            double xscale = gr.xPositionToPixel(1) - gr.xPositionToPixel(0);
            double yscale = gr.yPositionToPixel(1) - gr.yPositionToPixel(0);
            AffineTransform af1 = AffineTransform.getScaleInstance(xscale, yscale);
            Point pt = SwingUtilities.convertPoint((Component) gr,
                    (int) gr.xPositionToPixel(0), (int) gr.yPositionToPixel(0), this);
            AffineTransform af2 = AffineTransform.getTranslateInstance(pt.getX(), pt.getY());
            af2.concatenate(af1);
            annot.paintAnnotation(g2, af2);
        }
    }

    private void doTransform(Graphics2D g2) {
        if (rotation != 0d) {
            g2.rotate(rotation,
                    ((Component) getView()).getBounds().getCenterX(),
                    ((Component) getView()).getBounds().getCenterY());
        }

        if (zoom != 1d) {
            g2.translate((1 - zoom) * ((Component) getView()).getWidth() / 2d,
                    (1 - zoom) * ((Component) getView()).getHeight() / 2d);
            g2.scale(zoom, zoom);
        }
    }

    /**
     * paintAxes method This methods takes many settings from the
     * GJAbstractGraph
     *
     * @param g Graphics object
     */
    protected void paintAxes(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        if (getView() == null) {
            return;
        }

        Rectangle2D vr = ((Component) getView()).getBounds();

        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(getView().getAxisStrokeWeight() * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        setupGraphics(g2);

        double x = vr.getX();
        double y = vr.getY();
        double w = vr.getWidth();
        double h = vr.getHeight();

        g2.setColor(getView().getAxisColor());

        if (axisBox) {
            g2.draw(new Rectangle2D.Double(x, y, w, h));
        }

        ArrayList<GJGraphInterface> layerList = this.getView().getLayers();
        for (GJGraphInterface thisGraph : layerList) {
            if (thisGraph.isPolar()) {
                HashMap<Point2D, Map.Entry<String, Double>> labels = ((GJGraph) thisGraph).getPolarRayLabels(g2);
                if (labels.size() > 0) {
                    Component c = (Component) thisGraph;
                    double xoffset = c.getX();
                    double yoffset = c.getY();
                    Point2D p2;
                    if (c.getParent().equals(this)) {
                        p2 = new Point2D.Double(xoffset, yoffset);
                    } else {
                        p2 = SwingUtilities.convertPoint(c, (int) xoffset, (int) yoffset, this);
                    }
                    g2.draw(new Ellipse2D.Double(p2.getX(), p2.getY(), c.getWidth(), c.getHeight()));
                    for (Point2D p : labels.keySet()) {
                        if (p != null) {
                            Entry<String, Double> entry = labels.get(p);
                            String s = entry.getKey();
                            double theta = entry.getValue().doubleValue();
                            double[] xy = ShapeUtils.getStringLocationToArc(
                                    new double[]{xoffset + p.getX(), yoffset + p.getY()},
                                    theta,
                                    s,
                                    g2.getFontMetrics(g2.getFont()));
                            g2.drawString(s, (float) xy[0], (float) xy[1]);
                        }
                    }
                }
            }
        }

        ArrayList<GJGraphInterface> localLayers = new ArrayList<GJGraphInterface>(getView().getLayers());
        for (GJGraphInterface layer : localLayers) {

            if (layer.isLeftAxisPainted() && layer.getLeftAxisPanel() != null) {
                layer.getLeftAxisPanel().draw(g2);
            }

            if (layer.isRightAxisPainted() && layer.getRightAxisPanel() != null) {
                layer.getRightAxisPanel().draw(g2);
            }
            if (layer.isTopAxisPainted() && layer.getTopAxisPanel() != null) {
                layer.getTopAxisPanel().draw(g2);
            }
            if (layer.isBottomAxisPainted() && layer.getBottomAxisPanel() != null) {
                layer.getBottomAxisPanel().draw(g2);
            }
        }

        g2.setStroke(stroke);
        g2.dispose();
    }

    public JFormattedTextField getTitle() {
        return title;
    }

    public void setTitle(JFormattedTextField title) {
        installTitle(title);
    }

    /**
     * @param newString String for the title
     */
    public void setTitleText(String newString) {
        String old = getTitle().getText();
        getTitle().setText(newString);
        firePropertyChange("title", old, getTitle().getText());
    }

    @Override
    public void setCursor(Cursor cursor) {
        super.setCursor(cursor);
        for (int k = 0; k < getComponentCount(); k++) {
            getComponent(k).setCursor(cursor);
        }
    }

    private void installTitle(JFormattedTextField titleObject) {
        remove(title);
        title = titleObject;
        add(title);
        getTitle().setEnabled(true);
        getTitle().setOpaque(false);
        getTitle().setBackground(new Color(0f, 0f, 0f, 0f));
        getTitle().setBorder(null);
        getTitle().setFont(getFont());
    }

    public String getTitleText() {
        if (getTitle().getText() != null) {
            return getTitle().getText();
        } else {
            return "";
        }
    }

    /**
     * Thread-safe method for setting the sub-title
     *
     * @param newString String for the sub-title
     */
    public void setSubTitleText(String newString) {
        JFormattedTextField old = subtitle;
        subtitle.setText(newString);
        firePropertyChange("subtitletext", old, subtitle);
    }

    private void installSubTitle(JFormattedTextField title) {
        remove(subtitle);
        subtitle = title;
        add(title);
        subtitle.setEnabled(true);
        subtitle.setOpaque(false);
        subtitle.setBackground(new Color(0f, 0f, 0f, 0f));
        subtitle.setBorder(null);
        subtitle.setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
    }

    public JFormattedTextField getSubTitle() {
        return subtitle;
    }

    public void setSubTitle(JFormattedTextField title) {
        installSubTitle(title);
    }

    public String getSubTitleText() {
        return subtitle.getText();
    }

    public AffineTransform getTransform() {
        aft.setToIdentity();
        return initTransform();
    }

    public AffineTransform getTransform(double translateX, double translateY) {
        aft.setToIdentity();
        aft.translate(translateX, translateY);
        return initTransform();
    }

    private AffineTransform initTransform() {
        if (rotation != 0d) {
            aft.rotate(rotation,
                    ((Component) getView()).getBounds().getCenterX(),
                    ((Component) getView()).getBounds().getCenterY());
        }
        if (zoom != 1d) {
            aft.translate((1 - zoom) * ((Component) getView()).getWidth() / 2d,
                    (1 - zoom) * ((Component) getView()).getHeight() / 2d);
            aft.scale(zoom, zoom);
        }
        return aft;
    }

    public Point2D transform(int x, int y) {
        return transform(new Point(x, y));
    }

    public Point2D transform(double x, double y) {
        return transform(new Point2D.Double(x, y));
    }

    public Point2D transform(Point2D p) {
        if (rotation == 0d && zoom == 1d) {
            return p;
        } else {
            p = getTransform().transform(p, null);
            return p;
        }
    }

    public Shape transform(Shape s) {
        PathIterator p = s.getPathIterator(null);
        Path2D pth = new Path2D.Double();
        pth.append(p, false);
        return pth.createTransformedShape(getTransform());
    }

    /**
     *
     * @param s
     * @return a Path2D
     */
    public Path2D transform(Path2D s) {
        if (rotation == 0d && zoom == 1d) {
            return s;
        } else {
            s.transform(getTransform());
            return s;
        }
    }

    /**
     * Convenience caller to inverseTransform(Point)
     *
     * @param x Position in the container
     * @param y Position in the container
     * @return Point rotated,scaled and translated according to the container's
     * rotation and zoom settings.
     */
    public Point2D inverseTransform(int x, int y) {
        return inverseTransform(new Point(x, y));
    }

    /**
     * Convenience caller to inverseTransform(Component, Point)
     *
     * @param c the reference component
     * @param x Position in the c
     * @param y Position in the c
     * @return Point rotated,scaled and translated according to the container's
     * rotation and zoom settings bu referenced to Component c .
     */
    public Point2D inverseTransform(Component c, int x, int y) {
        return inverseTransform(c, new Point(x, y));
    }

    /**
     * Inverse transform a point according to latest rotate and zoom settings
     *
     * @param p Point in container
     * @return Point2D.
     *
     * inverseTransform should be used before xPixelToPosition and
     * yPixelToPosition.
     */
    public Point2D inverseTransform(Point2D p) {
        if (rotation == 0d && zoom == 1d) {
            return p;
        } else {
            try {
                getTransform().inverseTransform(p, p);
            } catch (NoninvertibleTransformException ex) {
                //Logger.getLogger(GJAbstractGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
            return p;
        }
    }

    /**
     * Transfrom a point according to latest rotate and zoom settings
     *
     * @param c
     * @param p Point in graph
     * @return Point2D. If there is no AffineTransForm, returns the input
     *
     * inverseTransform should be used before xPixelToPosition and
     * yPixelToPosition.
     */
    public Point2D inverseTransform(Component c, Point2D p) {
        p = SwingUtilities.convertPoint(c, (int) p.getX(), (int) p.getY(), this);
        p = inverseTransform(p);
        p = SwingUtilities.convertPoint(this, (int) p.getX(), (int) p.getY(), c);
        return p;
    }

    public Shape inverseTransform(Shape s) {
        PathIterator p = s.getPathIterator(null);
        Path2D pth = new Path2D.Double();
        pth.append(p, false);
        pth = inverseTransform(pth);
        return pth.createTransformedShape(null);
    }

    public Path2D inverseTransform(Path2D p) {
        if (rotation == 0d && zoom == 1d) {
            return p;
        } else {
            try {
                AffineTransform tf = getTransform();
                tf.invert();
                p.transform(tf);
            } catch (NoninvertibleTransformException ex) {
                //Logger.getLogger(GJAbstractGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
            return p;
        }
    }

    public GJContainerMouseHandler getContainerMouseHandler() {
        return containerMouseHandler;
    }

    public GJAddedComponentMouseHandler getAddedComponentMouseHandler() {
        return addedComponentMouseHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLink(Object o) {
        links.add(o);
    }

    @Override
    public final void setLinks(ArrayList<Object> links) {
        this.links = links;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLink(Object o) {
        links.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Object> getLinks() {
        return links;
    }

    @Override
    public final boolean hasListeners(String propertyName) {
        return getPropertyChangeListeners().length > 0;
    }

    @Override
    public boolean hasSelfListener() {
        return Arrays.asList(getPropertyChangeListeners()).contains(this);
    }

    /**
     * Creates and returns a GCFrame containing this graph container.
     *
     * @return a GCFrame
     */
    public GCFrame createFrame() {
        final GCFrame frame = new GCFrame();
        final GJAbstractGraphContainer gc = this;
        frame.add(gc);
        return frame;
    }

    /**
     * @param containerMouseHandler the containerMouseHandler to set
     */
    void setContainerMouseHandler(GJContainerMouseHandler containerMouseHandler) {
        this.containerMouseHandler = containerMouseHandler;
    }

    /**
     * @param addedComponentMouseHandler the addedComponentMouseHandler to set
     */
    void setAddedComponentMouseHandler(GJAddedComponentMouseHandler addedComponentMouseHandler) {
        this.addedComponentMouseHandler = addedComponentMouseHandler;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().matches("swingComponent")) {

            if (getAddedComponentMouseHandler().getSwingComponent() != null) {
                if (getAddedComponentMouseHandler().getSwingComponent().getClass().getName().contains("GJAxisPanel")) {
                    GJAxisPanel panel = (GJAxisPanel) getAddedComponentMouseHandler().getSwingComponent();
                    switch (panel.getPosition()) {
                        case LEFT:
                        case RIGHT:
                            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                            break;
                        case TOP:
                        case BOTTOM:
                            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                            break;
                        default:
                    }
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
            return;
        }

        if (!evt.getPropertyName().contains(".")) {
            revalidate();
            repaint();
        }

        if (getView() != null
                && getView().fetchEditor() != null
                && evt.getOldValue() != null
                && evt.getNewValue() != null
                && evt.getOldValue() != evt.getNewValue()) {
            getView().fetchEditor().propertyChange(evt);
        }

    }

    @Override
    public void firePropertyChange(PropertyChangeEvent pce) {
        propertyChange(pce);

    }

    /**
     * @return the aspectRatio
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * @param aspectRatio the aspectRatio to set
     */
    public void setAspectRatio(double aspectRatio) {
        double old = this.aspectRatio;
        this.aspectRatio = aspectRatio;
        firePropertyChange("aspectRatio", old, aspectRatio);
    }

    @Override
    public void setEffect(AreaEffect effect) {
        this.effect = effect;
    }

    @Override
    public AreaEffect getEffect() {
        return effect;
    }

}
