/*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import kcl.waterloo.defaults.Colors;
import kcl.waterloo.graphics.images.Images;
import kcl.waterloo.math.ArrayMath;

/**
 * Provides a mouse-changeable region of interest that can be added to a graph.
 *
 * GJRoi instances should be instantiated using the static factory method:
 * {@code GJRoi.createInstance(GJGraph gr, Rectangle2D.Double r);}
 *
 * GJRoi instance mouse-interaction does not support rotation/zoom of the view
 * in the parent graph container.
 *
 * @author ML
 */
public class GJRoi extends GJBasicPanel
        implements GJCustomGraphLayoutInterface,
        MouseListener, MouseMotionListener, ComponentListener, PropertyChangeListener {

    private JLabel panelNW = new JLabel(Images.getIcon("arrow_corner.png"));
    private JLabel panelNE = new JLabel(Images.getIcon("arrow_corner.png"));
    private JLabel panelSW = new JLabel(Images.getIcon("arrow_corner.png"));
    private JLabel panelSE = new JLabel(Images.getIcon("arrow_corner.png"));
    private JLabel panelS = new JLabel(Images.getIcon("arrow_updown.png"));
    private JLabel panelN = new JLabel(Images.getIcon("arrow_updown.png"));
    private JLabel panelE = new JLabel(Images.getIcon("arrow_leftright.png"));
    private JLabel panelW = new JLabel(Images.getIcon("arrow_leftright.png"));
    private static int sz = 10;
    private Point2D dragStart = null;
    private final Rectangle2D.Double pixelRectangle = new Rectangle2D.Double();
    private final Rectangle2D.Double axesRectangle = new Rectangle2D.Double();
    protected int minSiz = sz * 3;
    //private AtomicBoolean validateFlag = new AtomicBoolean(false);

    /**
     * Public constructor for XML serialization/de-serialization. Do not use -
     * call the factory createInstance methods to create instances.
     */
    public GJRoi() {
        super(new SpringLayout());
        setBorder(new LineBorder(Colors.getColor("DARKGREEN"), 2, true));
        setBackgroundPainted(true);
        setBackground(new Color(0f, 1f, 0f, 0.4f));
        SpringLayout layout = (SpringLayout) getLayout();
        add(panelNW);
        add(panelNE);
        add(panelSW);
        add(panelSE);
        add(panelN);
        add(panelS);
        add(panelE);
        add(panelW);

        layout.putConstraint(SpringLayout.EAST, panelNE, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, panelNE, -sz, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, panelNE, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, panelNE, sz, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, panelSE, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, panelSE, -sz, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, panelSE, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.NORTH, panelSE, -sz, SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.WEST, panelNW, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, panelNW, sz, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, panelNW, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, panelNW, sz, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, panelSW, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, panelSW, sz, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, panelSW, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.NORTH, panelSW, -sz, SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelN, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, panelN, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, panelN, sz, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelS, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, panelS, -sz, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.SOUTH, panelS, 0, SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.VERTICAL_CENTER, panelW, 0, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.EAST, panelW, sz, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, panelW, 0, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.VERTICAL_CENTER, panelE, 0, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.EAST, panelE, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, panelE, -sz, SpringLayout.EAST, this);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelNE.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        panelNW.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        panelSE.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        panelSW.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
        panelS.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        panelN.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        panelE.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        panelW.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));

        this.addMouseListener(this);
        panelNW.addMouseListener(this);
        panelNE.addMouseListener(this);
        panelSW.addMouseListener(this);
        panelSE.addMouseListener(this);
        panelS.addMouseListener(this);
        panelE.addMouseListener(this);
        panelN.addMouseListener(this);
        panelW.addMouseListener(this);

        this.addMouseMotionListener(this);
        panelNW.addMouseMotionListener(this);
        panelNE.addMouseMotionListener(this);
        panelSW.addMouseMotionListener(this);
        panelSE.addMouseMotionListener(this);
        panelS.addMouseMotionListener(this);
        panelE.addMouseMotionListener(this);
        panelN.addMouseMotionListener(this);
        panelW.addMouseMotionListener(this);
    }

    public GJRoi(Rectangle2D.Double r) {
        this();
        axesRectangle.setRect(r);

    }

    /**
     * Factory method for creating an instance in the center of the specified
     * graph.
     *
     * @param gr the target graph
     * @return
     */
    public static GJRoi createInstance(GJAbstractGraph gr) {
        Rectangle2D r = gr.getAxesBounds();
        return createInstance(gr, new Rectangle2D.Double(r.getCenterX() - (0.1 * r.getWidth()),
                r.getCenterY() + (0.1 * r.getHeight()),
                0.2 * r.getWidth(),
                0.2 * r.getHeight()));
    }

    /**
     * Factory method for creating an instance.
     *
     * @param gr the target graph
     * @param r a Rectangle2D in axes units
     * @return
     */
    public static GJRoi createInstance(final GJAbstractGraph gr, final Rectangle2D.Double r) {
        final GJRoi roi = new GJRoi();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gr.add(roi);
                roi.setAxesRectangle(r);
                gr.addComponentListener(roi);
                gr.addPropertyChangeListener(roi);
                gr.revalidate();
                gr.repaint();
            }
        });
        return roi;
    }

    /**
     * Returns a copy of the axes-unit based bounds for this instance.
     *
     * @return the axesRectangle
     */
    public final Rectangle2D.Double getAxesRectangle() {
        if (getParent() != null) {
            axesRectangle.setRect(((GJAbstractGraph) getParent()).transformPixelToPosition(pixelRectangle));
        }
        return (Rectangle2D.Double) axesRectangle.clone();
    }

    /**
     * Sets the axes-unit based bounds for this instance together with the
     * corresponding pixel-based bounds. This method should only be used when
     * the instance is parented by a graph.
     *
     * @param axesRectangle the axesRectangle to set
     */
    public final void setAxesRectangle(Rectangle2D.Double axesRectangle) {
        this.axesRectangle.setRect(axesRectangle);
        if (getParent() != null) {
            setPixelRectangle(((GJAbstractGraph) getParent()).transformPositionToPixel(axesRectangle));
        }
    }

    /**
     * Returns a copy of the pixel-based bounds for this instance.
     *
     * @return the pixelRectangle
     */
    public final Rectangle2D.Double getPixelRectangle() {
        return (Rectangle2D.Double) pixelRectangle.clone();
    }

    /**
     * Sets the pixel-based bounds for this instance together with the
     * corresponding axes-units based bounds. This method should only be used
     * when the instance is parented by a graph.
     *
     * @param pixelRectangle the pixelRectangle to set
     */
    public final void setPixelRectangle(Rectangle2D.Double pixelRectangle) {
        this.pixelRectangle.setRect(pixelRectangle);
        if (getParent() != null) {
            this.axesRectangle.setRect(((GJAbstractGraph) getParent()).transformPixelToPosition(pixelRectangle));
            ((JComponent)getParent()).revalidate();
        }
    }

    private void reposition(MouseEvent e, double dX, double dY) {
        if (getParent() != null) {
            if (((GJAbstractGraph) getParent()).getGraphContainer().getRotation() != 0
                    || ((GJAbstractGraph) getParent()).getGraphContainer().getZoom() != 1d) {
                return;
            }
            Rectangle2D.Double r = pixelRectangle;
            if (e == null || e.getSource().equals(this)) {
                r.setRect(r.getX() + dX, r.getY() + dY,
                        r.getWidth(), r.getHeight());
            } else if (e.getSource().equals(this.panelNE)) {
                r.setRect(r.getX(), r.getY() + dY,
                        r.getWidth() + dX, r.getHeight() - dY);
            } else if (e.getSource().equals(this.panelNW)) {
                r.setRect(r.getX() + dX, r.getY() + dY,
                        r.getWidth() - dX, r.getHeight() - dY);
            } else if (e.getSource().equals(this.panelSE)) {
                r.setRect(r.getX(), r.getY(),
                        r.getWidth() + dX, r.getHeight() + dY);
            } else if (e.getSource().equals(this.panelSW)) {
                r.setRect(r.getX() + dX, r.getY(),
                        r.getWidth() - dX, r.getHeight() + dY);
            } else if (e.getSource().equals(this.panelN)) {
                r.setRect(r.getX(), r.getY() + dY,
                        r.getWidth(), r.getHeight() - dY);
            } else if (e.getSource().equals(this.panelS)) {
                r.setRect(r.getX(), r.getY(),
                        r.getWidth(), r.getHeight() + dY);
            } else if (e.getSource().equals(this.panelE)) {
                r.setRect(r.getX(), r.getY(),
                        r.getWidth() + dX, r.getHeight());
            } else if (e.getSource().equals(this.panelW)) {
                r.setRect(r.getX() + dX, r.getY(),
                        r.getWidth() - dX, r.getHeight());
            }
            r.setRect(r.x, r.y, Math.max(r.width, minSiz), Math.max(r.height, minSiz));
            this.axesRectangle.setRect(((GJAbstractGraph) getParent()).transformPixelToPosition(pixelRectangle));
        }
    }

    @Override
    public final void setConstraints() {
        if (getParent() != null) {
            SpringLayout layout = (SpringLayout) getParent().getLayout();
            Rectangle2D r = pixelRectangle;
            layout.putConstraint(SpringLayout.WEST, this, (int) r.getX(), SpringLayout.WEST, getParent());
            layout.putConstraint(SpringLayout.NORTH, this, (int) r.getY(), SpringLayout.NORTH, getParent());
            layout.putConstraint(SpringLayout.EAST, this, (int) (r.getX() + r.getWidth()), SpringLayout.WEST, getParent());
            layout.putConstraint(SpringLayout.SOUTH, this, (int) (r.getY() + r.getHeight()), SpringLayout.NORTH, getParent());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        e = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this.getParent());
        dragStart = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public final void mouseDragged(MouseEvent e) {
        if (getParent() != null && dragStart != null) {
            MouseEvent e2 = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, this.getParent());
            double dX = e2.getX() - dragStart.getX();
            double dY = e2.getY() - dragStart.getY();
            reposition(e, dX, dY);
            ((JComponent)getParent()).revalidate();
            dragStart = e2.getPoint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (getParent() != null) {
            setPixelRectangle(((GJAbstractGraph) getParent()).transformPositionToPixel(axesRectangle));
            getParent().repaint();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //componentResized(e);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        componentResized(e);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * PropertyChangeListener to be installed in the parent graph. This provided
     * support for changing the transform/reversal of axes.
     *
     * @param evt PropertyChangeEvent
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof GJAbstractGraph) {
            GJAbstractGraph gr = (GJAbstractGraph) evt.getSource();
            if (evt.getPropertyName().equals("XTransform") || evt.getPropertyName().equals("YTransform")
                    || evt.getPropertyName().equals("axesBounds")) {
                Rectangle2D.Double old = pixelRectangle;
                pixelRectangle.setRect(gr.transformPositionToPixel(axesRectangle));
                if (ArrayMath.any(ArrayMath.isNaN(new double[]{pixelRectangle.x, pixelRectangle.y, pixelRectangle.width, pixelRectangle.height}))) {
                    // If the transformed roi contains NaNs, restore the previous value and set the roi
                    // visibility off. 
                    setPixelRectangle(old);
                    setVisible(false);
                } else {
                    // Restore visibility if no NaNs.
                    setVisible(true);
                    gr.revalidate();
                }
            }
        }
    }

    /**
     * Removes the icons and places a space character in the label's text field
     * (to maintain non-zero size).
     */
    public void removeIcons() {
        panelNE.setIcon(null);
        panelNW.setIcon(null);
        panelSW.setIcon(null);
        panelSE.setIcon(null);
        panelN.setIcon(null);
        panelS.setIcon(null);
        panelE.setIcon(null);
        panelW.setIcon(null);
        panelNE.setText(" ");
        panelNW.setText(" ");
        panelSW.setText(" ");
        panelSE.setText(" ");
        panelN.setText(" ");
        panelS.setText(" ");
        panelE.setText(" ");
        panelW.setText(" ");
    }
}
