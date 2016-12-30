 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJGraphInterface;

/**
 * {@code GraphUnitLayout} extends {@code SpringLayout} to provide support for
 * components that are positioned in graph coordinate space.
 *
 * Use the {@code putGraphicConstraint} method to add components in graph space.
 * Note that the {@code putConstraint} method may still be used to position
 * components using pixel coordinates.
 *
 * {@code GraphUnitLayout} is an abstract class.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GraphUnitLayout extends SpringLayout {

    /**
     * {@code LinkedHashMap} containing those components that are laid out using
     * graph units as the keys and their constraints (in graph units) as the
     * values.
     */
    private final LinkedHashMap<Component, GraphicConstraint> componentMap
            = new LinkedHashMap<Component, GraphicConstraint>();

    public GraphUnitLayout() {
    }

    /**
     * Registers the specified component to be laid out using graph units.
     *
     * @param c the {@code Component}
     * @param x x-position in graph units
     * @param y y-position in graph units
     * @param alignX relative X alignment specified using a
     * {@code SwingConstants} value.
     * @param alignY relative Y alignment specified using a
     * {@code SwingConstants} value.
     */
    public void putGraphicConstraint(Component c, double x, double y, int alignX, int alignY) {
        componentMap.put(c, new GraphicConstraint(c, x, y, alignX, alignY));
    }

    /**
     * Returns the {@code LinkedHashMap} of {@code Components} to be laid out
     * using graph coordinates.
     *
     * @return the {@code LinkedHashMap} of {@code Components}
     */
    public LinkedHashMap<Component, GraphicConstraint> getComponentMap() {
        return componentMap;
    }

    /**
     * Adds the input to the existing component map. Entries for components
     * already present will be updated. Entries for components not present in
     * the existing componenMap will be added.
     *
     * @param map
     */
    public void setComponentMap(LinkedHashMap<Component, GraphicConstraint> map) {
        componentMap.putAll(map);
    }

    /**
     * Returns an ArrayList of the preferredSize of each component in the
     * componentMap. This method will be used in serialization/deserialization
     * to preserve the dimensions of Swing annotations.
     *
     * @return ArrayList of Dimensions
     */
    public ArrayList<Dimension> getDimensions() {
        ArrayList<Dimension> dim = new ArrayList<Dimension>(componentMap.size());
        for (Component c : componentMap.keySet()) {
            dim.add(c.getPreferredSize());
        }
        return dim;
    }

    /**
     * Sets the dimensions of components in the componentMap. The input size
     * must match the componentMap size.This method will be used in
     * serialization/deserialization to preserve the dimensions of Swing
     * annotations.
     *
     * @param dim ArrayList of Dimensions
     */
    public void setDimensions(ArrayList<Dimension> dim) {
        int k = 0;
        for (Component c : componentMap.keySet()) {
            c.setPreferredSize(dim.get(k));
            k++;
        }
    }

    protected final void revalidateComponent(Component c, GJAbstractGraphContainer container,
            GJGraphInterface gr, boolean translateCoordinates) {
        double x, y, xpixel, ypixel;
        int alignX, alignY;
        GraphicConstraint constraint = getComponentMap().get(c);
        if (constraint != null) {
            //TODO: Check this - changed 26.12.2012
            x = gr.getXTransform().getData(constraint.getX());
            y = gr.getYTransform().getData(constraint.getY());
            alignX = constraint.getAlignX();
            alignY = constraint.getAlignY();
            xpixel = (int) gr.xPositionToPixel(x);
            ypixel = (int) gr.yPositionToPixel(y);
            switch (alignX) {
                case SwingConstants.LEFT:
                    xpixel = xpixel - c.getWidth();
                    break;
                case SwingConstants.CENTER:
                    xpixel = xpixel - c.getWidth() / 2.0d;
                    break;
                case SwingConstants.RIGHT:
                default:
                    break;
            }
            switch (alignY) {
                case SwingConstants.TOP:
                    ypixel = ypixel - c.getHeight();
                    break;
                case SwingConstants.CENTER:
                    ypixel = ypixel - c.getHeight() / 2.0d;
                    break;
                case SwingConstants.BOTTOM:
                default:
                    break;
            }

            if (translateCoordinates) {
                Insets theseInsets = container.getInsets();
                xpixel = xpixel - theseInsets.left;
                ypixel = ypixel - theseInsets.top;
                Point point = SwingUtilities.convertPoint((Component) gr, (int) xpixel, (int) ypixel, container);
                putConstraint(SpringLayout.WEST, c, (int) point.getX(), SpringLayout.WEST, container);
                putConstraint(SpringLayout.NORTH, c, (int) point.getY(), SpringLayout.NORTH, container);
            } else {
                putConstraint(SpringLayout.WEST, c, (int) Math.round(xpixel), SpringLayout.WEST, (Component) gr);
                putConstraint(SpringLayout.NORTH, c, (int) Math.round(ypixel), SpringLayout.NORTH, (Component) gr);
            }
        }
    }

    /**
     * This method does nothing and needs to be overridden.
     *
     * @param c
     */
    public void doSuperLayoutContainer(Container c) {
    }
}
