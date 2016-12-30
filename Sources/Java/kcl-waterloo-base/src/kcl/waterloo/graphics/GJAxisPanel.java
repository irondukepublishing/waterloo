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
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import kcl.waterloo.common.deploy.AbstractDeployableGraphics2D;
import org.jdesktop.swingx.JXLabel;

/**
 * {@code GJAxisPanel}- provides axis support for graph layers within a graph
 * container.
 *
 * Note that a default constructor is made public only for use in XML
 * serialization/de-serialization.
 *
 * To create instances of this class use the graph {@code setXXXXAxisPainted()}
 * methods which will create the instance through the package-private
 * constructor.
 *
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJAxisPanel extends GJBasicPanel implements PropertyChangeListener {

    private static final int minimumMargin = 30;
    private static final int interAxisSpace = 10;
    /**
     * Title of this axis including any units
     */
    private JLabel label = new JXLabel("");

    /**
     * Position of the axis - one of
     * <code>SwingConstants LEFT, RIGHT, TOP or BOTTOM</code>
     */
    public enum Position {

        RIGHT, TOP, LEFT, BOTTOM
    }

    public enum Orientation {

        X, Y, Z
    }

    private GJAxisPanel.Position position;
    /**
     * Offset (in pixels) of this axis from the View of the parent container.
     * This allows a gap to be left between the axis and graph. Default=0
     */
    private int offset = 0;
    /**
     * Length of the major tick marks (in pixels). Default=5
     */
    private int majorTickLength = 5;
    /**
     * Set true for outward ticks, false for inward (= default)
     */
    private boolean ticksOutward = false;
    /**
     * Set true to paint the tick labels
     */
    private boolean tickLabelsPainted = false;
    /**
     * The graph this axis is associated with (N.B. axes are painted in the
     * container for the graph)
     */
    private GJGraphInterface parentGraph = null;
    private Dimension dim = new Dimension();
    private double dynamicExtent = 20;
    private int extent;

    /**
     * Factory method for creating a new GJAxisPanel instance. This instantiates
     * a GJAxisPanel and associates it with a graph.
     *
     * @param gr GJGraphInterface this axis is associated with
     * @param pos axis Position as defined by {@code GJAxisPanel#Position}.
     * @return a new GJAxisPanel instance.
     */
    static GJAxisPanel createInstance(GJGraphInterface gr, GJAxisPanel.Position pos) {
        GJAxisPanel ax = new GJAxisPanel(gr, pos);
        if (gr.getGraphContainer() != null) {
            gr.getGraphContainer().add(ax);
            ax.addMouseMotionListener(gr.getGraphContainer().getContainerMouseHandler());
            ax.addMouseListener(gr.getGraphContainer().getContainerMouseHandler());
        }
        return ax;
    }

    /**
     * Public constructor used for XML serialization/de-serialization. Do not
     * use.
     */
    public GJAxisPanel() {
        super(true);
        setOpaque(true);
        //setBackground(new Color(0f, 0f, 0f, 0f));
        setPreferredSize(dim);
        setLayout(new AxisLayout());
        add(label);
        label.setVisible(tickLabelsPainted);
        setLabelFont(getFont());
    }

    /**
     * Constructor used by the factory method.
     *
     * @param source
     * @param pos
     */
    private GJAxisPanel(GJGraphInterface source, GJAxisPanel.Position pos) {
        this();
        setForeground(((JComponent) source).getForeground());
        setParentGraph(source);
        setPosition(pos);
//        getParentGraph().getGraphContainer().add((Component) this);
//        addMouseMotionListener(getParentGraph().getGraphContainer().getContainerMouseHandler());
//        addMouseListener(getParentGraph().getGraphContainer().getContainerMouseHandler());
    }

    /**
     * Called during construction. Not intended for public use.
     *
     * @param source
     */
    public final void setParentGraph(GJGraphInterface source) {
        parentGraph = source;
    }

    /**
     * Returns the graph this axis is associated with
     *
     * @return the parent GJGraphInterface
     */
    public final GJGraphInterface getParentGraph() {
        return parentGraph;

    }

    /**
     * Called during construction. Not intended for public use.
     *
     * @param pos
     */
    public final void setPosition(GJAxisPanel.Position pos) {
        position = pos;
        switch (pos) {
            case LEFT:
                setTickLabelsPainted(true);
                setTextRotation(label, 3 * Math.PI / 2);
                dim = new Dimension(extent, 32767);
                break;
            case RIGHT:
                dim = new Dimension(extent, 32767);
                setTextRotation(label, 3 * Math.PI / 2);
                break;
            case TOP:
                dim = new Dimension(32767, extent);
                break;
            case BOTTOM:
                setTickLabelsPainted(true);
                dim = new Dimension(32767, extent);
                break;
            default:
        }
    }

    /**
     * Private static method called to rotate the axis labels. This works for
     * objects implementing {@code setTextRotation} method (e.g. JXLabel and any
     * components from the GPL kcl.gpl.tex package).
     */
    private void setTextRotation(Object label, double theta) {
        try {
            Method m = label.getClass().getMethod("setTextRotation", new Class[]{double.class});
            try {
                m.invoke(label, theta);
            } catch (IllegalAccessException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
            }
        } catch (NoSuchMethodException ex) {
        } catch (SecurityException ex) {
        }

    }

    /**
     * Returns an integer constant (as defined in {@code SwingConstants}) which
     * defines this axis location as LEFT, RIGHT, TOP, BOTTOM.
     *
     * @return the int Position
     */
    public GJAxisPanel.Position getPosition() {
        return position;
    }

    public GJAxisPanel.Orientation getOrientation() {
        switch (position) {
            case LEFT:
            case RIGHT:
                return Orientation.Y;
            case TOP:
            case BOTTOM:
                return Orientation.X;
            default:
                return Orientation.Z;
        }
    }

    /**
     * Returns a reference to the {@code MouseAdapter} that this axis will use.
     *
     * @return the MouseAdapter
     */
    public final MouseAdapter getMouseHandler() {
        return parentGraph.getAxisMouseHandler();
    }

    /**
     * Sets the font for the axis title.
     *
     * @return the lable {@code Font}
     */
    public Font getLabelFont() {
        return label.getFont();
    }

    /**
     * Returns the font for the axis title
     *
     * @param f
     */
    public final void setLabelFont(final Font f) {
        Font old = label.getFont();
        label.setFont(f);
        localValidate();
        firePropertyChange("Font", old, label.getFont());
    }

    /**
     * Sets the title string
     *
     * @param s
     */
    public final void setText(String s) {
        if (label != null) {
            String old = label.getText();
            label.setText(s);
            localValidate();
            firePropertyChange("text", old, label.getText());
        }
    }

    public final String getText() {
        return label.getText();
    }

    public final void setTickLabelsPainted(boolean flag) {
        boolean old = tickLabelsPainted;
        if (flag) {
            if (label.getParent() != this) {
                this.add(label);
            }
        } else {
            this.remove(label);
        }
        tickLabelsPainted = flag;
        label.setVisible(flag);
        localValidate();
        firePropertyChange("tickLabelsPainted", old, tickLabelsPainted);
    }

    public final boolean isTickLabelsPainted() {
        return tickLabelsPainted;
    }

    public final JLabel getLabel() {
        return label;
    }

    public final void setLabel(JLabel lbl) {
        JLabel old = label;
        remove(label);
        label = lbl;
        add(label);
        setPosition(position);
        firePropertyChange("label", old, label);
    }

    /**
     * Revalidate - recalculates the SpringLayout
     *
     */
    @Override
    public final void revalidate() {
        if (EventQueue.isDispatchThread()) {
            if (getLayout() instanceof AxisLayout) {
                // Call localRevalidate before revalidate
                AxisLayout layout = (AxisLayout) getLayout();
                layout.localRevalidate(this);
            }
            super.revalidate();
        } else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    revalidate();
                }
            });
        }
    }

    /**
     * Need double validation for some changes as the layout is dependent of the
     * view Position.
     *
     */
    private void localValidate() {
        if (getParent() != null) {
            getParent().validate();
        } else {
            super.validate();
        }
    }

    /**
     * Sets the rotation of the title text.
     *
     * @param theta
     */
    public final void setTitleRotation(double theta) {
        if (label != null) {
            setTextRotation(label, theta);
        }
    }

    /**
     * Sets the direction for drawing the ticks
     *
     * @param flag
     */
    public final void setTicksOutward(boolean flag) {
        ticksOutward = flag;
    }

    /**
     *
     * @return the tick direction - true for outward ticks
     */
    public final boolean getTicksOutward() {
        return ticksOutward;
    }

    /**
     * Sets the major tick length
     *
     * @param x tick length in pixels
     */
    public final void setMajorTickLength(int x) {
        majorTickLength = x;
    }

    /**
     * Returns the major tick length
     *
     * @return length (in pixels)
     */
    public final int getMajorTickLength() {
        return majorTickLength;
    }

    /**
     * Sets the minor tick length
     *
     * @param x tick length in pixels
     */
    public final void setMinorTickLength(int x) {
        majorTickLength = x;
    }

    /**
     * Returns the minor tick length
     *
     * @return length (in pixels)
     */
    public final int getMinorTickLength() {
        return majorTickLength;
    }

    /**
     * @return the offset
     */
    final int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    final void setOffset(int offset) {
        double old = this.offset;
        this.offset = offset;
        firePropertyChange("offset", old, offset);
    }

    /**
     * @return the dim
     */
    public final Dimension getDim() {
        return dim;
    }

//    /**
//     * @param dim the dim to set
//     */
//    public final void setDim(Dimension dim) {
//        this.dim = dim;
//    }
    /**
     * @return the extent
     */
    final int getExtent() {
        if (isTickLabelsPainted()) {
            return extent;
        } else {
            // 5-pixel width for mouse listener activity
            return 5;
        }
    }

    /**
     * @param extent the extent to set
     */
    final void setExtent(int extent) {
        this.extent = extent;
    }

    /**
     * @return the interAxisSpace
     */
    public static int getInterAxisSpace() {
        return interAxisSpace;
    }

    /**
     * @return the minimumMargin
     */
    public static int getMinimumMargin() {
        return minimumMargin;
    }

    /**
     * @return the dynamicExtent
     */
    public double getDynamicExtent() {
        return dynamicExtent;
    }

    /**
     * @param dynamicExtent the dynamicExtent to set
     */
    public void setDynamicExtent(double dynamicExtent) {
        this.dynamicExtent = dynamicExtent;
    }

//    /**
//     * Added at 1.1Beta. No need for super call here. Axis panels are dealt with
//     * from the graph paint mechanism.
//     */
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        draw((Graphics2D)g);
//    }
    final void draw(Graphics2D g2) {
        Paint p = g2.getPaint();
        g2.setPaint(getForeground());
        if (g2 instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g2).setPaintObject(this);
        }
        paintBackground(g2);
        switch (position) {
            case LEFT:
                drawVerticalAxisLeft(g2);
                break;
            case BOTTOM:
                drawHorizontalAxisBottom(g2);
                break;
            case RIGHT:
                drawVerticalAxisRight(g2);
                break;
            case TOP:
                drawHorizontalAxisTop(g2);
                break;
            default:
        }
        g2.setPaint(p);
    }

    /**
     * Axis painting method called from the graph container. As the Graphics2D
     * object supplied on input is from the container, it honours the clip
     * region of the container, not of the View.
     *
     * @param g2 Graphics2D supplied from the container
     */
    private void drawHorizontalAxisBottom(Graphics2D g2) {
        Path2D ax = new Path2D.Double();
        FontMetrics metrics = g2.getFontMetrics();
        double pos;
        int tick = this.majorTickLength;
        if (!this.ticksOutward) {
            tick = -tick;
        }
        ax.moveTo(this.getX(), this.getY());
        ax.lineTo(this.getX() + this.getWidth(), this.getY());
        double thisMin, thisMax;
        thisMin = this.getParentGraph().getXMin();
        thisMax = this.getParentGraph().getXMax();
        double startX = Math.floor(thisMin / this.getParentGraph().getMajorXHint()) * this.getParentGraph().getMajorXHint();
        ArrayList<Double> it = this.getParentGraph().getXTransform().getAxisTickPositions(
                startX,
                thisMax + this.getParentGraph().getMajorXHint(),
                this.getParentGraph().getMajorXHint());
        int y = this.getY();
        double x2;
        g2.setPaint(getParentGraph().getAxisColor());
        //g2.setPaint(((Component) getParentGraph()).getForeground());
        for (Double s : it) {
            x2 = s.doubleValue();
            int xpixel = (int) this.getParentGraph().xPositionToPixel(x2);
            if (x2 >= thisMin && x2 <= thisMax) {
                Point point = SwingUtilities.convertPoint(this, xpixel, 0, this.getParentGraph().getGraphContainer());
                ax.moveTo(point.getX(), y);
                ax.lineTo(point.getX(), y + tick);
                if (isTickLabelsPainted()) {
                    String str = this.getParentGraph().formatXAxisLabel(x2);
                    pos = point.getX() - metrics.stringWidth(str) / 2d;
                    if (this.getParentGraph().isCategorical(GJAxisPanel.Orientation.X)) {
                        // Categorical labels - so rotate them
                        AffineTransform af = g2.getTransform();
                        double r = this.getParentGraph().getXAxisLabelRotation(x2);
                        g2.rotate(r, pos + metrics.stringWidth(str) / 2d, y + 10);
                        g2.drawString(str, (int) (pos - metrics.stringWidth(str) / 2d), y + 10);
                        g2.setTransform(af);
                    } else {
                        g2.drawString(str, (int) pos,
                                y + metrics.getAscent() + this.majorTickLength);
                    }
                }
            }
        }
        g2.setPaint(getParentGraph().getAxisColor());
        g2.setStroke(new BasicStroke(getParentGraph().getAxisStrokeWeight()));
        g2.draw(ax);
    }

    /**
     * Axis painting method called from the graph container. As the Graphics2D
     * object supplied on input is from the container, it honours the clip
     * region of the container, not of the View.
     *
     * @param g2 Graphics2D supplied from the container
     */
    private void drawHorizontalAxisTop(Graphics2D g2) {
        Path2D ax = new Path2D.Double();
        FontMetrics metrics = g2.getFontMetrics();
        double pos;
        int tick = this.majorTickLength;
        if (this.ticksOutward) {
            tick = -tick;
        }
        ax.moveTo(this.getX(), this.getY() + this.getHeight());
        ax.lineTo(this.getX() + this.getWidth(), this.getY() + this.getHeight());
        double startX = Math.floor(this.getParentGraph().getXMin() / this.getParentGraph().getMajorXHint()) * this.getParentGraph().getMajorXHint();
        ArrayList<Double> it = this.getParentGraph().getXTransform().getAxisTickPositions(
                startX,
                this.getParentGraph().getXMax() + this.getParentGraph().getMajorXHint(),
                this.getParentGraph().getMajorXHint());
        int y = this.getY() + this.getHeight();
        double x2;
        g2.setPaint(getParentGraph().getAxisColor());
        for (Double s : it) {
            x2 = s.doubleValue();
            int xpixel = (int) this.getParentGraph().xPositionToPixel(x2);
            if (x2 >= this.getParentGraph().getXMin() && x2 <= this.getParentGraph().getXMax()) {
                Point point = SwingUtilities.convertPoint(this, xpixel, 0, this.getParentGraph().getGraphContainer());
                ax.moveTo(point.getX(), y);
                ax.lineTo(point.getX(), y + tick);
                if (isTickLabelsPainted()) {
                    //g2.setPaint(((Component) getParentGraph()).getForeground());
                    String str = this.getParentGraph().formatXAxisLabel(x2);
                    pos = point.getX() - metrics.stringWidth(str) / 2d;
                    if (this.getParentGraph().isCategorical(GJAxisPanel.Orientation.X)) {
                        // Categorical labels - so rotate them
                        AffineTransform af = g2.getTransform();
                        double r = this.getParentGraph().getXAxisLabelRotation(x2);
                        g2.rotate(r, pos + metrics.stringWidth(str) / 2d, y - 2);
                        g2.drawString(str, (int) (pos + metrics.stringWidth(str) / 2d), y - 2);
                        g2.setTransform(af);
                    } else {
                        g2.drawString(str, (int) pos,
                                y - metrics.getDescent() - this.majorTickLength);
                    }
                }
            }
        }
        g2.setPaint(getParentGraph().getAxisColor());
        g2.setStroke(new BasicStroke(getParentGraph().getAxisStrokeWeight()));
        g2.draw(ax);
    }

    /**
     * Axis painting method called from the graph container. As the Graphics2D
     * object supplied on input is from the container, it honours the clip
     * region of the container, not of the View.
     *
     * @param g2 Graphics2D supplied from the container
     */
    private void drawVerticalAxisLeft(Graphics2D g2) {

        final Path2D ax = new Path2D.Double();
        FontMetrics metrics = g2.getFontMetrics();
        double pos;
        int tick = this.majorTickLength;
        if (this.ticksOutward) {
            tick = -tick;
        }
        ax.moveTo(this.getX() + this.getWidth(), this.getY());
        ax.lineTo(this.getX() + this.getWidth(), this.getY() + this.getHeight());
        double startY = Math.floor(this.getParentGraph().getYMin() / this.getParentGraph().getMajorYHint()) * this.getParentGraph().getMajorYHint();
        int x = this.getX() + this.getWidth();
        ArrayList<Double> it = this.getParentGraph().getYTransform().getAxisTickPositions(
                startY,
                this.getParentGraph().getYMax() + this.getParentGraph().getMajorYHint(),
                this.getParentGraph().getMajorYHint());
        double y2;
        g2.setPaint(getParentGraph().getAxisColor());
        for (Double s : it) {
            y2 = s.doubleValue();
            int ypixel = (int) this.getParentGraph().yPositionToPixel(y2);
            if (y2 >= this.getParentGraph().getYMin() && y2 <= this.getParentGraph().getYMax()) {
                Point point = SwingUtilities.convertPoint(this, 0, ypixel, this.getParentGraph().getGraphContainer());
                ax.moveTo(x, point.getY());
                ax.lineTo(x + tick, (int) point.getY());
                //g2.drawLine(x, (int) point.getY(), x + tick, (int) point.getY());
                if (isTickLabelsPainted()) {
                    //g2.setPaint(((Component) getParentGraph()).getForeground());
                    String str = this.getParentGraph().formatYAxisLabel(y2);
                    pos = point.getY() + 2 * metrics.getDescent();
                    g2.drawString(str, x - metrics.stringWidth(str) - this.majorTickLength,
                            (int) pos);
                }
            }
        }
        g2.setPaint(getParentGraph().getAxisColor());
        g2.setStroke(new BasicStroke(getParentGraph().getAxisStrokeWeight()));
        g2.draw(ax);
    }

    /**
     * Axis painting method called from the graph container. As the Graphics2D
     * object supplied on input is from the container, it honours the clip
     * region of the container, not of the View.
     *
     * @param g2 Graphics2D supplied from the container
     */
    private void drawVerticalAxisRight(Graphics2D g2) {
        final Path2D ax = new Path2D.Double();
        FontMetrics metrics = g2.getFontMetrics();
        double pos;
        int tick = this.majorTickLength;
        if (!this.ticksOutward) {
            tick = -tick;
        }
        ax.moveTo(this.getX(), this.getY());
        ax.lineTo(this.getX(), this.getY() + this.getHeight());
        double startY = Math.floor(this.getParentGraph().getYMin() / this.getParentGraph().getMajorYHint()) * this.getParentGraph().getMajorYHint();
        int x = this.getX();
        ArrayList<Double> it = this.getParentGraph().getYTransform().getAxisTickPositions(
                startY,
                this.getParentGraph().getYMax() + this.getParentGraph().getMajorYHint(),
                this.getParentGraph().getMajorYHint());
        double y2;
        g2.setPaint(getParentGraph().getAxisColor());
        for (Double s : it) {
            //g2.setPaint(getParentGraph().getAxisColor());
            y2 = s.doubleValue();
            int ypixel = (int) this.getParentGraph().yPositionToPixel(y2);
            if (y2 >= this.getParentGraph().getYMin() && y2 <= this.getParentGraph().getYMax()) {
                Point point = SwingUtilities.convertPoint(this, 0, ypixel, this.getParentGraph().getGraphContainer());
                ax.moveTo(x, point.getY());
                ax.lineTo(x + tick, point.getY());
                //g2.drawLine(x, (int) point.getY(), x + tick, (int) point.getY());
                if (isTickLabelsPainted()) {
                    //g2.setPaint(((Component) getParentGraph()).getForeground());
                    String str = this.getParentGraph().formatYAxisLabel(y2);
                    pos = point.getY() + 2 * metrics.getDescent();
                    g2.drawString(str, x + this.majorTickLength, (int) pos);
                }
            }
        }
        g2.setPaint(getParentGraph().getAxisColor());
        g2.setStroke(new BasicStroke(getParentGraph().getAxisStrokeWeight()));
        g2.draw(ax);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }
}
