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

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.SwingUtilities;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJAddedComponentMouseHandler extends MouseAdapter {

    /**
     * Point associated with the current mouse event but inverse transformed to
     * unrotated, unzoomed pixel coordinates in the container's space.
     */
    private Point swingPoint = null;
    /**
     * The Swing component that the mouse is presently over (therefore beneath
     * swingPoint in the untransformed view). This is the deepest component
     * descending from the container at the mouse location. Null if there is no
     * child component at his location.
     */
    private Component swingComponent = null;
    /**
     * The last component clicked on. Null if there has been no mouse click
     */
    private Component clickedComponent = null;
    /**
     * The coordinates of swingPoint expressed relative to the origin of the
     * currentLayer in the View (linear axes only) and in the units/coordinates
     * of the view.
     */
    private Point2D xyCoord = null;
    private GJAbstractGraphContainer parentComponent;

    /**
     * Sets the parentComponent for this mouse handler. This method is called
     * from the graph container constructor. It is made public final for use by
     * the XML coder/decoder
     *
     * @param c the graph container to control
     */
    public final void setParentComponent(GJAbstractGraphContainer c) {
        parentComponent = c;
    }

    public final GJAbstractGraphContainer getParentComponent() {
        return parentComponent;
    }

    /**
     * This mouse listener is for the GJAbstractGraphContainer. Obtain a copy of
     * the handler by calling getHandler()
     *
     * @param e
     */
    @Override
    public final void mouseEntered(MouseEvent e) {
        Component old = getSwingComponent();
        setSwingPoint(e);
        if (getSwingComponent() != null || (old != null && !old.equals(swingComponent))) {
            MouseAdapter m = getComponentMouseAdapter(getSwingComponent());
            if (m != null) {
                MouseEvent e2 = convertEvent(e);
                m.mouseEntered(e2);
            }
        }
    }

    @Override
    public final void mouseExited(MouseEvent e) {
        Component old = getSwingComponent();
        setSwingPoint(e);
        if (old != null && getSwingComponent() != null && !old.equals(swingComponent)) {
            MouseAdapter m = getComponentMouseAdapter(old);
            if (m != null) {
                MouseEvent e2 = convertEvent(e);
                m.mouseExited(e2);
            }
        }
    }

    @Override
    public final void mouseMoved(MouseEvent e) {
        Component old = getSwingComponent();
        setSwingPoint(e);
        if (old != null && getSwingComponent() != null && !old.equals(swingComponent)) {
            //If we have changed to a new swingComponent, force enter/exit calls
            MouseAdapter m = getComponentMouseAdapter(old);
            if (m != null) {
                MouseEvent e2 = convertEvent(e);
                m.mouseExited(e2);
            }
            m = getComponentMouseAdapter(getSwingComponent());
            if (m != null) {
                MouseEvent e2 = convertEvent(e);
                m.mouseEntered(e2);
            }
        }
        MouseAdapter m = getComponentMouseAdapter();
        if (m != null) {
            MouseEvent e2 = convertEvent(e);
            m.mouseMoved(e2);
        }
    }

    @Override
    public final void mousePressed(MouseEvent e) {
        setSwingPoint(e);
        MouseAdapter m = getComponentMouseAdapter();
        if (m != null) {
            MouseEvent e2 = convertEvent(e);
            m.mousePressed(e2);
        }
    }

    @Override
    public final void mouseReleased(MouseEvent e) {
        setSwingPoint(e);
        MouseAdapter m = getComponentMouseAdapter();
        if (m != null) {
            MouseEvent e2 = convertEvent(e);
            m.mouseReleased(e2);
        }
    }

    @Override
    public final void mouseClicked(MouseEvent e) {
        setSwingPoint(e);
        setClickedComponent(getSwingComponent());
        MouseAdapter m = getComponentMouseAdapter();
        if (m != null) {
            MouseEvent e2 = convertEvent(e);
            m.mouseClicked(e2);
        }
        GJContainerMouseHandler.setLastMouseClick(e);
    }

    @Override
    public final void mouseDragged(MouseEvent e) {
        setSwingPoint(e);
        MouseAdapter m = getComponentMouseAdapter();
        if (m != null) {
            MouseEvent e2 = convertEvent(e);
            m.mouseDragged(e2);
        }
    }

    private MouseEvent convertEvent(MouseEvent e) {
        MouseEvent e2 = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, getParentComponent());
        e2.getPoint().setLocation(getSwingPoint());
        e2 = SwingUtilities.convertMouseEvent(getParentComponent(), e2, getSwingComponent());
        return e2;
    }

    private MouseAdapter getComponentMouseAdapter() {
        return getComponentMouseAdapter(getSwingComponent());
    }

    private MouseAdapter getComponentMouseAdapter(Component comp) {
        if (comp == null) {
            return null;
        }
        Method method;
        try {
            method = comp.getClass().getMethod("getMouseHandler");
        } catch (NoSuchMethodException ex) {
            // This is OK - standard component that we have no interest in
            return null;
        } catch (SecurityException ex) {
            return null;
        }
        MouseAdapter m = null;
        if (method != null) {
            try {
                m = (MouseAdapter) method.invoke(comp);
            } catch (IllegalAccessException ex) {
                return null;
            } catch (IllegalArgumentException ex) {
                return null;
            } catch (InvocationTargetException ex) {
                return null;
            }
        }
        return m;
    }

    /**
     *
     * @return a Point
     */
    Point getSwingPoint() {
        return swingPoint;
    }

    /**
     *
     * @return a Component
     */
    Component getSwingComponent() {
        return swingComponent;
    }

    Component getClickedComponent() {
        return clickedComponent;
    }

    Point2D getXYCoord() {
        return xyCoord;
    }

    void setSwingPoint(MouseEvent e) {
        Point p;
        // get the Point corresponding to the current mouse position
        // int this instance in untranslated coordinates...
        if (!e.getSource().equals(parentComponent)) {
            p = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), getParentComponent());
        } else {
            p = e.getPoint();
        }
        setSwingPoint(p);
    }

    void setSwingPoint(Point p) {
        swingPoint = (Point) getParentComponent().inverseTransform(p);
        // ... then locate the Swing component this point corresponds to
        if (swingPoint == null) {
            setSwingComponent(null);
            setXYCoord(null);
        } else {
            setSwingComponent(getParentComponent().findComponentAt(swingPoint));

            if (getSwingComponent() != null
                    && getSwingComponent().getClass().getSuperclass().equals(GJAbstractGraph.class)) {
                setSwingComponent((Component) getParentComponent().getView().getCurrentLayer());
                getParentComponent().getView().setCurrentLayer(getParentComponent().getView().getCurrentLayer());
            }

            Point p2 = SwingUtilities.convertPoint(getParentComponent(), swingPoint, (Component) getParentComponent().getView());
            setXYCoord(new Point2D.Double(getParentComponent().getView().getCurrentLayer().xPixelToPosition(p2.getX()),
                    getParentComponent().getView().getCurrentLayer().yPixelToPosition(p2.getY())));
        }
    }

    /**
     * @param swingComponent the swingComponent to set
     */
    public void setSwingComponent(Component swingComponent) {
        this.swingComponent = swingComponent;
    }

    /**
     * @param clickedComponent the clickedComponent to set
     */
    public void setClickedComponent(Component clickedComponent) {
        this.clickedComponent = clickedComponent;
    }

//    /**
//     * @return the xyCoord
//     */
//    public Point2D getXYCoord() {
//        return xyCoord;
//    }
//
    /**
     * @param xyCoord the xyCoord to set
     */
    public void setXYCoord(Point2D xyCoord) {
        this.xyCoord = xyCoord;
    }
}
