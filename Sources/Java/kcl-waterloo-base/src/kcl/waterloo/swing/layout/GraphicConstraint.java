 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.swing.layout;

import java.awt.Component;
import javax.swing.SwingConstants;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GraphicConstraint {

    private Component component = null;
    /**
     * x-position in graph units.
     */
    private double x;
    /**
     * y-position in graph units.
     */
    private double y;
    /**
     * The x-axis alignment.
     */
    private int alignX;
    /**
     * The y-axis alignment.
     */
    private int alignY;

    /**
     * Default constructor.
     */
    public GraphicConstraint() {
        this(null, 0, 0, SwingConstants.LEFT, SwingConstants.TOP);
    }

    /**
     * Creates a new graphics constraint.
     *
     * @param x
     * @param y
     * @param alignX
     * @param alignY
     */
    GraphicConstraint(Component c, double x, double y, int alignX, int alignY) {
        this.component = c;
        this.x = x;
        this.y = y;
        this.alignX = alignX;
        this.alignY = alignY;
    }

    /**
     * @return the x-value (in graph units).
     */
    public double getX() {
        return x;
    }

    /**
     * @param x sets the x-value (in graph units).
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y-value (in graph units).
     */
    public double getY() {
        return y;
    }

    /**
     * @param y sets the x-value (in graph units).
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the x-alignment as a {@code SwingConstant}-defined int.
     */
    public int getAlignX() {
        return alignX;
    }

    /**
     * @param alignX the alignX to set
     */
    public void setAlignX(int alignX) {
        this.alignX = alignX;
    }

    /**
     * @return the y-alignment as a {@code SwingConstant}-defined int.
     */
    public int getAlignY() {
        return alignY;
    }

    /**
     * @param alignY the alignY to set
     */
    public void setAlignY(int alignY) {
        this.alignY = alignY;
    }

    /**
     * @return the component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Component component) {
        this.component = component;
    }
}
