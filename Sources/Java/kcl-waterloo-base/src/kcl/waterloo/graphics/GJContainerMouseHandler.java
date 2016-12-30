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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class provides mouse support for a {@code GJAbstractGraphContainer}.
 *
 * Together with the {@code GJAddedComponentMouseHandler} class, this provides
 * support when views are rotated and/or zoomed.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJContainerMouseHandler extends MouseAdapter {

    /**
     * Contains the last mouse event among all graphs/containers
     */
    private static MouseEvent lastMouseEvent = null;
    /**
     * Contains the last click event among all graphs/containers
     */
    private static MouseEvent lastMouseClick = null;
    /**
     * Contains a reference to the last selected plot among all
     * graphs/containers
     */
    private static Object lastSelected = null;

    /**
     * @return the lastMouseEvent
     */
    public static MouseEvent getLastMouseEvent() {
        return lastMouseEvent;
    }

    /**
     * @param aLastMouseEvent the lastMouseEvent to set
     */
    public static void setLastMouseEvent(MouseEvent aLastMouseEvent) {
        lastMouseEvent = aLastMouseEvent;
    }

    /**
     * @return the lastMouseClick
     */
    public static MouseEvent getLastMouseClick() {
        return lastMouseClick;
    }

    /**
     * @param aLastMouseClick the lastMouseClick to set
     */
    public static void setLastMouseClick(MouseEvent aLastMouseClick) {
        lastMouseClick = aLastMouseClick;
    }

    /**
     * @return the lastSelected
     */
    public static Object getLastSelected() {
        return lastSelected;
    }

    /**
     * @param aLastSelected the lastSelected to set
     */
    public static void setLastSelected(Object aLastSelected) {
        lastSelected = aLastSelected;
    }

    /**
     * The Swing container that this mouse adapter supports
     */
    private GJAbstractGraphContainer parentContainer = null;

    /**
     * Sets the Swing container that this mouse adapter supports.
     *
     * @param comp
     */
    public void setParentContainer(GJAbstractGraphContainer comp) {
        parentContainer = comp;
    }

    /**
     * @return the parentContainer
     */
    public GJAbstractGraphContainer getParentContainer() {
        return parentContainer;
    }

    /**
     * This mouse listener is for the GJAbstractGraphContainer. Obtain a copy of
     * the handler by calling getContainerMouseHandler()
     *
     * @param e MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //setSwingPoint(e);
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().mouseEntered(e);
        }
        setLastMouseEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //setSwingPoint(e);
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().mouseExited(e);
        }
        setLastMouseEvent(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (getParentContainer() != null) {
            Component old = getParentContainer().getAddedComponentMouseHandler().getSwingComponent();
            getParentContainer().getAddedComponentMouseHandler().setSwingPoint(e);
            if (getParentContainer().getAddedComponentMouseHandler().getSwingComponent() != old) {
                getParentContainer().getAddedComponentMouseHandler().mouseEntered(e);

            }
            getParentContainer().getAddedComponentMouseHandler().mouseMoved(e);
        }
        setLastMouseEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //setSwingPoint(e);
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().mousePressed(e);
        }
        setLastMouseEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //setSwingPoint(e);
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().mouseReleased(e);
        }
        setLastMouseEvent(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().setSwingPoint(e);
            getParentContainer().getAddedComponentMouseHandler().setClickedComponent(getParentContainer().getAddedComponentMouseHandler().getSwingComponent());
            getParentContainer().getAddedComponentMouseHandler().mouseClicked(e);
        }
        setLastMouseEvent(e);
        setLastMouseClick(e);
        setLastSelected(e.getSource());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //setSwingPoint(e);
        if (getParentContainer() != null) {
            getParentContainer().getAddedComponentMouseHandler().mouseDragged(e);
        }
        setLastMouseEvent(e);
    }
}
