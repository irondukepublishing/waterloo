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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import kcl.waterloo.actions.GJEventManager;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.common.deploy.AbstractDeployableGraphics2D;
import kcl.waterloo.math.ArrayMath;

/**
 * Concrete class extending the {@code GJAbstractGraph} class.
 *
 * While {@code GJGraph} instances can be independent of their containers, they
 * will normally be added to a {@code GJGraphContainer} instance.
 *
 * The {@code GJGraph} class supports adding of plots, provides objects to
 * transform the data held in those plots as well as coordinating the painting
 * of grids and internal axes.
 *
 * Axes shown outside of the viewable area of the graph are painted by the
 * {@code GraphContainer} if it has one. These components are properties of the
 * graph, but are members of the Swing hierarchy of the container.
 *
 * @author Malcolm Lidierth
 */
public final class GJGraph extends GJAbstractGraph {

    private transient Stroke minorGridStroke;
    private transient Stroke localAxisStroke;
    private final ArrayList<Path2D> v = new ArrayList<Path2D>();
    private final ArrayList<Path2D> h = new ArrayList<Path2D>();

    /**
     * <p>
     * Creates a new graph display. The following properties are automatically
     * set:</p> <ul> <li><i>view</i>: -1.0 to +1.0 on both axis</li>
     * <li><i>origin</i>: At <code>(0, 0)</code></li> <li><i>grid</i>: Spacing
     * of 0.2 between major lines; minor lines count is 4</li> </ul>
     */
    public GJGraph() {
        this(0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.2, 4, 0.2, 4);
    }

    /**
     * <p>
     * Creates a new graph display with the specified view, origin and grid
     * lines.</p>
     *
     * @param originX the coordinate of the inner axis
     * @param originY the coordinate of the inner axis
     * @param xleft the left coordinate on the X axis for the view
     * @param xright the right coordinate on the X axis for the view
     * @param ybottom the bottom coordinate on the Y axis for the view
     * @param ytop the top coordinate on the Y axis for the view
     * @param majorX the majorX interval
     * @param minorCountX number of minor divisions to a major division
     * @param majorY the majorY interval
     * @param minorCountY number of minor divisions to a major division
     */
    private GJGraph(double originX, double originY,
            double xleft, double xright,
            double ybottom, double ytop,
            double majorX, int minorCountX,
            double majorY, int minorCountY) {

        super();

        this.originX = originX;
        this.originY = originY;

        this.xLeft = xleft;
        this.xRight = xright;
        this.yBottom = ybottom;
        this.yTop = ytop;

        this.setMajorXHint(majorX);
        this.setMinorCountXHint(minorCountX);
        this.setMajorYHint(majorY);
        this.setMinorCountYHint(minorCountY);

        setBackground(Color.WHITE);
        setFocusable(true);
        //addKeyListener(GraphCycler.getInstance());
        getMainFormatter().setMaximumFractionDigits(2);
        //getLayers().add(this);
        setCurrentLayer(this);
//        addPropertyChangeListener(this);

    }

    public static GJGraph createInstance() {
        GJGraph gr = new GJGraph();

        gr.setLeftAxisPainted(((Boolean) GJDefaults.getMap().get("GJGraph.leftAxisPainted")));
        gr.setRightAxisPainted(((Boolean) GJDefaults.getMap().get("GJGraph.rightAxisPainted")));
        gr.setTopAxisPainted(((Boolean) GJDefaults.getMap().get("GJGraph.topAxisPainted")));
        gr.setBottomAxisPainted(((Boolean) GJDefaults.getMap().get("GJGraph.bottomAxisPainted")));

        gr.setLeftAxisLabelled(((Boolean) GJDefaults.getMap().get("GJGraph.leftAxisLabelled")));
        gr.setRightAxisLabelled(((Boolean) GJDefaults.getMap().get("GJGraph.rightAxisLabelled")));
        gr.setTopAxisLabelled(((Boolean) GJDefaults.getMap().get("GJGraph.topAxisLabelled")));
        gr.setBottomAxisLabelled(((Boolean) GJDefaults.getMap().get("GJGraph.bottomAxisLabelled")));

        gr.addMouseListener(GJEventManager.getInstance());
        gr.addListeners();
        gr.addPropertyChangeListener(gr);

        return gr;
    }

    /**
     * Draw the grid. First draw the vertical lines, then the horizontal lines.
     *
     * @param g2 Graphics2D instance
     */
    private void drawGrid(Graphics2D g2) {
        Stroke stroke = g2.getStroke();
        minorGridStroke = new BasicStroke(super.minorGridStrokeWeight);
        Stroke majorGridStroke = new BasicStroke(super.majorGridStrokeWeight);
        localAxisStroke = new BasicStroke(getAxisStrokeWeight());
        if (isMajorGridPainted() || isMinorGridPainted() || isInnerAxisPainted()) {
            if (isPolar()) {
                polarDrawVerticalGrid(g2);
                polarDrawHorizontalGrid(g2);
            } else {
                // Draw minor and major grids with single calls to draw on g2.
                // This creates them as a single editable unit (group/clipping mask etc)
                // in vector graphics based editors.
                drawVerticalGrid(g2);
                drawHorizontalGrid(g2);

                g2.setStroke(minorGridStroke);
                g2.setPaint(getMinorGridColor());
                g2.setColor(getMinorGridColor());
                v.get(0).append(h.get(0), false);
                g2.draw(v.get(0));

                v.get(1).append(h.get(1), false);
                g2.setStroke(majorGridStroke);
                g2.setPaint(getMajorGridColor());
                g2.draw(v.get(1));

                v.get(2).append(h.get(2), false);
                g2.setPaint(getForeground());
                g2.setStroke(localAxisStroke);
                g2.draw(v.get(2));
            }
        }
        g2.setStroke(stroke);
    }

    /**
     * Draw all labels. First draws labels on the horizontal axis, then labels
     * on the vertical axis. If the axis is set not to be painted, this method
     * draws the origin as a straight cross.
     *
     * @param g2 Graphics2D instance
     */
    private void drawLabels(Graphics2D g2) {
        if (isInnerAxisLabelled()) {
            g2.setPaint(getForeground());
            drawHorizontalAxisLabels(g2);
            drawVerticalAxisLabels(g2);
        }
    }

    /**
     * Draws labels on the vertical axis. First draws labels below the origin,
     * then draw labels on top of the origin.
     *
     * @param g2 Graphics2D instance
     */
    private void drawVerticalAxisLabels(Graphics2D g2) {
        if (isInnerAxisLabelled()) {
            double axisV = xPositionToPixel(getOriginX());
            ArrayList<Double> it;
            if (isPolar()) {
                it = getPolarAxisLabels();
            } else {
                double startY = Math.floor(getYMin() / getMajorYHint()) * getMajorYHint();
                it = getYTransform().getAxisTickPositions(
                        startY,
                        getYMax() + getMajorYHint(),
                        getMajorYHint());
            }
            double y;
            for (Double s : it) {
                y = s;
                if (((y - getMajorYHint() / 2.0) < getOriginY())
                        && ((y + getMajorYHint() / 2.0) > getOriginY())) {
                    continue;
                }
                int position = (int) yPositionToPixel(y);
                String str = format(y);
                FontMetrics metrics = g2.getFontMetrics();
                Shape clip = g2.getClip();
                if (clip == null) {
                    return;
                }
                if (clip.contains(axisV + 5, position - metrics.getMaxAscent() / 2)
                        && clip.contains(axisV + 5, position + metrics.getMaxAscent() / 2 + metrics.getMaxDescent())) {
                    g2.drawString(str, (int) axisV + 5, position + metrics.getMaxAscent() / 2);
                }
            }
        }
    }

    /**
     * Draws the horizontal lines of the grid. Draws both minor and major grid
     * lines.
     *
     * @param g2 Graphics2D instance
     */
    private void drawHorizontalGrid(Graphics2D g2) {
        double axisV = xPositionToPixel(getOriginX());

        Rectangle clip = g2.getClipBounds();
        if (clip == null) {
            return;
        }
        int position;
        Path2D path0 = new Path2D.Double();
        Path2D path1 = new Path2D.Double();
        Path2D path2 = new Path2D.Double();

        if (isInnerAxisPainted()) {
            g2.setStroke(localAxisStroke);
            g2.setPaint(getAxisColor());
            position = (int) xPositionToPixel(getOriginX());
            if (position >= clip.x && position <= clip.x + clip.width) {
                g2.setColor(getMajorGridColor());
                g2.drawLine(position, clip.y, position, clip.y + clip.height);
            }
        }

        final double myh = getMajorYHint();
        double startY = Math.floor(getYMin() / myh) * myh;
        double y;

        if (isMinorGridPainted()) {
            ArrayList<Double> MinorGrid = getYTransform().getMinorGridPositions(
                    startY,
                    getYMax() + myh,
                    myh,
                    this.getMinorCountYHint());
            for (Double s : MinorGrid) {
                y = s.doubleValue();
                position = (int) yPositionToPixel(y);
                if (position >= clip.y && position <= clip.y + clip.height) {
                    path0.moveTo(clip.x, position);
                    path0.lineTo(clip.x + clip.width, position);
                }
            }
        }

        if (isMajorGridPainted() || isInnerAxisPainted()) {
            ArrayList<Double> MajorGrid = getYTransform().getAxisTickPositions(
                    startY,
                    getYMax() + myh,
                    myh);
            for (Double s : MajorGrid) {
                y = s;
                position = (int) yPositionToPixel(y);
                if (position >= clip.y && position <= clip.y + clip.height) {
                    if (isMajorGridPainted()) {
                        path1.moveTo(clip.x, position);
                        path1.lineTo(clip.x + clip.width, position);
                    }
                    // Draw the inner axis if required
                    if (isInnerAxisPainted()) {
                        path2.moveTo(axisV - 3, position);
                        path2.lineTo(axisV + 3, position);
                    }
                }
            }
        }
        h.clear();
        h.add(path0);
        h.add(path1);
        h.add(path2);
    }

    /**
     * Draws labels on the horizontal axis. First draws labels on the right of
     * the origin, then on the left.
     *
     * @param g2 Graphics2D instance
     */
    private void drawHorizontalAxisLabels(Graphics2D g2) {
        double axisH = yPositionToPixel(getOriginY());
        FontMetrics metrics = g2.getFontMetrics();
        final double mxh = getMajorXHint();
        ArrayList<Double> it;
        if (isPolar()) {
            it = getPolarAxisLabels();
        } else {
            double startX = Math.floor(getXMin() / mxh) * mxh;
            it = getXTransform().getAxisTickPositions(
                    startX,
                    getXMax() + mxh,
                    mxh);
        }
        double x;
        for (Double s : it) {
            x = s.doubleValue();

            if (((x - mxh / 2.0) < getOriginX())
                    && ((x + mxh / 2.0) > getOriginX())) {
                continue;
            }

            int position = (int) xPositionToPixel(x);
            String str = formatXAxisLabel(x);
            // GJAbstractGraph change
            Shape clip = g2.getClip();
            if (clip == null) {
                return;
            }
            position = position - metrics.stringWidth(str) / 2;
            if (clip.contains(position, axisH + metrics.getHeight())
                    && clip.contains(position + metrics.stringWidth(str),
                            axisH + metrics.getHeight())) {
                g2.drawString(str, position,
                        (int) axisH + metrics.getHeight());
            } else if (clip.contains(position, axisH - metrics.getHeight())
                    && clip.contains(position + metrics.stringWidth(str),
                            axisH - 2 * metrics.getDescent())) {
                g2.drawString(str, position,
                        (int) axisH - 2 * metrics.getDescent());
            }

        }
    }

    /**
     * Draws the vertical lines of the grid. Draws both minor and major grid
     * lines.
     *
     * @param g2 Graphics2D instance
     */
    private void drawVerticalGrid(Graphics2D g2) {
        double axisH = yPositionToPixel(getOriginY());

        Rectangle clip = g2.getClipBounds();
        if (clip == null) {
            return;
        }
        int position;
        Path2D path0 = new Path2D.Double();
        Path2D path1 = new Path2D.Double();
        Path2D path2 = new Path2D.Double();

        if (isInnerAxisPainted()) {
            g2.setStroke(localAxisStroke);
            g2.setPaint(getAxisColor());
            position = (int) yPositionToPixel(getOriginY());
            if (position >= clip.y && position <= clip.y + clip.height) {
                g2.drawLine(clip.x, position, clip.x + clip.width, position);
            }
        }

        double mxh = getMajorXHint();
        double startX = Math.floor(getXMin() / mxh) * mxh;
        double x;

        if (isMinorGridPainted()) {
            ArrayList<Double> MinorGrid = getXTransform().getMinorGridPositions(
                    startX,
                    getXMax() + mxh,
                    mxh,
                    getMinorCountXHint());

            // Minor Grid
            for (Double s : MinorGrid) {
                x = s.doubleValue();
                position = (int) xPositionToPixel(x);
                if (position >= clip.x && position <= clip.x + clip.width) {
                    path0.moveTo(position, clip.y);
                    path0.lineTo(position, clip.y + clip.height);
                }
            }
        }

        if (isMajorGridPainted() || isInnerAxisPainted()) {
            ArrayList<Double> MajorGrid = getXTransform().getAxisTickPositions(
                    startX,
                    getXMax() + mxh,
                    mxh);

            for (Double s : MajorGrid) {
                x = s;
                position = (int) xPositionToPixel(x);
                if (position >= clip.x && position <= clip.x + clip.width) {
                    if (isMajorGridPainted()) {
                        path1.moveTo(position, clip.y);
                        path1.lineTo(position, clip.y + clip.height);
                    }
                    if (isInnerAxisPainted()) {
                        path2.moveTo(position, (int) axisH - 3);
                        path2.lineTo(position, (int) axisH + 3);
                    }
                }
            }
        }
        v.clear();
        v.add(path0);
        v.add(path1);
        v.add(path2);
    }

    /**
     * Draws the main axis.
     *
     * @param g2 Graphics2D instance
     */
    private void drawAxis(Graphics2D g2) {
        if (isPolar()) {
            polarDrawAxis(g2);
        }
        if (isInnerAxisPainted()) {
            double axisH = yPositionToPixel(getOriginY());
            double axisV = xPositionToPixel(getOriginX());

            Rectangle clip = g2.getClipBounds();
            if (clip == null) {
                return;
            }

            g2.setPaint(getForeground());
            Stroke stroke = g2.getStroke();
            g2.setStroke(localAxisStroke);

            if (axisH >= clip.y && axisH <= clip.y + clip.height) {
                g2.drawLine(clip.x, (int) axisH, clip.x + clip.width, (int) axisH);
            }
            if (axisV >= clip.x && axisV <= clip.x + clip.width) {
                g2.drawLine((int) axisV, clip.y, (int) axisV, clip.y + clip.height);
            }
            g2.setStroke(stroke);
        }
    }

    private void polarDrawVerticalGrid(Graphics2D g2) {
        double position;
        if (isMajorGridPainted()) {
            Shape clip = g2.getClip();
            if (clip == null) {
                return;
            }
            g2.clip(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
            ArrayList<Double> it = getPolarAxisLabels();
            g2.setStroke(minorGridStroke);
            g2.setPaint(getMinorGridColor());
            for (Double s : it) {
                int x, y, w, h;
                position = s;
                if (position < 0) {
                    x = (int) xPositionToPixel(position);
                    y = (int) yPositionToPixel(-position);
                } else {
                    x = (int) xPositionToPixel(-position);
                    y = (int) yPositionToPixel(position);
                }
                w = (int) (xPositionToPixel(position) - x);
                h = (int) (yPositionToPixel(-position) - y);
                g2.drawOval(x, y, w, h);
                //g2.drawArc(x, y, w, h, 0, 360);
            }
            g2.setClip(clip);
        }
    }

    private ArrayList<Double> getPolarAxisLabels() {
//        if (getMajorYHint() < 1e-14) {
//            updatePlots();
//        }
        double factor = getMajorXHint() % 2 == 0 ? 2d : 1d;
        double start = ArrayMath.min(new double[]{-getYMax(), getYMin(), -getXMax(), getXMin()})
                + getMajorYHint() / factor;
        double stop = ArrayMath.max(new double[]{getYMax(), -getYMin(), getXMax(), -getXMin()})
                + getMajorYHint() / factor;

        start = Math.floor(start / getMajorYHint() * getMajorYHint());
        stop = Math.ceil(stop / getMajorYHint() * getMajorYHint());

        if (Math.abs(start) > stop) {
            stop = Math.abs(start);
        }

        start = 0;

        ArrayList<Double> arr = getXTransform().getAxisTickPositions(
                start,
                stop,
                getMajorXHint() / factor);
        ArrayList<Double> arr2 = new ArrayList<Double>();
        arr2.addAll(arr);
        for (Double x : arr2) {
            arr.add(-x);
        }
        return arr;
    }

    private void polarDrawHorizontalGrid(Graphics2D g2) {
    }

    private void polarDrawAxis(Graphics2D g2) {
        Paint paint = g2.getPaint();
        g2.setPaint(getMajorGridColor());
        Stroke stroke = g2.getStroke();
        g2.setStroke(minorGridStroke);
        Shape clip = g2.getClip();
        if (clip == null) {
            return;
        }
        g2.clip(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 6) {
            g2.drawLine((int) xPositionToPixel(0),
                    (int) yPositionToPixel(0),
                    (int) xPositionToPixel(Math.cos(theta) * getWidth() / 2d),
                    (int) yPositionToPixel(Math.sin(theta) * getHeight() / 2d));
        }
        g2.setClip(clip);
        g2.setStroke(stroke);
        g2.setPaint(paint);
    }

    HashMap<Point2D, Entry<String, Double>> getPolarRayLabels(Graphics2D g) {
        HashMap<Point2D, Entry<String, Double>> map = new HashMap<Point2D, Entry<String, Double>>();
        //Ellipse2D e0 = new Ellipse2D.Double(-5, -5, getWidth() + 10, getHeight() + 10);
        double x0 = xPositionToPixel(0);
        double y0 = yPositionToPixel(0);
        for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 6) {
            String s = String.format("%3.0f\u00b0", theta * 180 / Math.PI);
            double x1 = xPositionToPixel(Math.cos(theta) * getWidth() / 2d);
            double y1 = yPositionToPixel(Math.sin(theta) * getHeight() / 2d);
            //Point2D xy = Ellipse.intersections(e0, x0, y0, x1, y1);
            Point2D xy =intersectEllipse(getWidth() + 10, getHeight() + 10, x0, y0, x1, y1);
            map.put(xy, new AbstractMap.SimpleEntry<String, Double>(s, theta));
        }
        return map;
    }

    //http://stackoverflow.com/questions/10692541/how-to-find-a-point-where-a-line-intersects-an-ellipse-in-2d-c
    /**
     * 
     * @param r0
     * @param r1
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return 
     */
    private static Point2D intersectEllipse(float r0, float r1, double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        double theta = Math.atan2(dy, dx);
        double distance = Math.hypot(dx, dy);
        double r = distance - ((r0 * r1) / Math.sqrt(Math.pow(r1 * Math.cos(theta), 2) + Math.pow(r0 * Math.sin(theta), 2)));
        return new Point2D.Double(x0 + r * Math.cos(theta), y0 + r * Math.sin(theta));
    }

    @Override
    public void paint(Graphics g) {
        paintComponent(g);
        paintBorder(g);
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    public final void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g2).setPaintObject(this);
        }
        setupGraphics(g2);
        paintBackground(g2);
        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g).addComment("Painting grid");
        }
        drawGrid(g2);
        drawAxis(g2);
        if (getPlots() != null) {
            drawPlots(g2);
        }
        drawLabels(g2);
        if (getCurrentROI() != null && getCurrentROI().getWidth() > 0) {
            g2.setPaint(new Color(245, 222, 179, 100));
            Rectangle2D r = getCurrentROI();
            int x1 = (int) xPositionToPixel(r.getX());
            int y1 = (int) yPositionToPixel(r.getY());
            int w1 = (int) (r.getWidth() / getPixelWidth());
            int h1 = (int) (r.getHeight() / getPixelHeight());
            g2.fillRect(x1, y1, w1, h1);
            g2.setPaint(Color.DARK_GRAY);
            g2.drawRect(x1, y1, w1, h1);
        }
        if (getAvailableROI() != null) {
            for (Rectangle2D r : getAvailableROI()) {
                if (r.getWidth() > 0) {
                    g2.setPaint(new Color(0f, 1f, 1f, 0.3f));
                    int x1 = (int) xPositionToPixel(r.getX());
                    int y1 = (int) yPositionToPixel(r.getY());
                    int w1 = (int) (r.getWidth() / getPixelWidth());
                    int h1 = (int) (r.getHeight() / getPixelHeight());
                    g2.fillRect(x1, y1, w1, h1);
                    g2.setPaint(Color.BLACK);
                    g2.drawRect(x1, y1, w1, h1);
                }
            }
        }
        paintChildren(g2);
        g2.dispose();
    }

    public void paintGrid() {
        if (!isVisible()) {
            return;
        }
        Graphics g = getGraphics();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setClip(0, 0, getWidth(), getHeight());
        super.paintBackground(g2);
        super.paintComponent(g2);

        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g).setPaintObject(this);
        }
        setupGraphics(g2);
        paintBackground(g2);
        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g).addComment("Painting grid");
        }
        drawGrid(g2);
        drawAxis(g2);
    }

    @Override
    public LocalTransferable getTransferable() {
        if (getGraphContainer() != null) {
            return getGraphContainer().getTransferable();
        } else {
            return super.getTransferable();
        }
    }
}
