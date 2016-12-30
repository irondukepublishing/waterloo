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
import java.util.LinkedHashMap;
import javax.swing.SpringLayout;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class SpringRelativeLayout extends SpringLayout {

    private LinkedHashMap<Component, RelativeConstraint> componentMap1
            = new LinkedHashMap<Component, RelativeConstraint>();
    private LinkedHashMap<Component, RelativeConstraint> componentMap2
            = new LinkedHashMap<Component, RelativeConstraint>();

    public SpringRelativeLayout() {
    }

    public final void putAnchor(Component c, double x, double y, String alignX, String alignY) {
        if (componentMap2.containsKey(c)) {
            componentMap2.remove(c);
        }
        componentMap1.put(c, new RelativeConstraint(x, y, alignX, alignY));
    }

    public final void putAnchors(Component c, double x1, double y1, String alignX1, String alignY1,
            double x2, double y2, String alignX2, String alignY2) {
        componentMap1.put(c, new RelativeConstraint(x1, y1, alignX1, alignY1));
        componentMap2.put(c, new RelativeConstraint(x2, y2, alignX2, alignY2));
    }

    @Override
    public final void layoutContainer(Container parent) {
        for (Component c : componentMap1.keySet()) {
            RelativeConstraint constraints = componentMap1.get(c);
            this.putConstraint(constraints.alignX,
                    c,
                    (int) (constraints.x * parent.getWidth()),
                    SpringLayout.WEST,
                    parent);
            this.putConstraint(constraints.alignY,
                    c,
                    (int) (constraints.y * parent.getHeight()),
                    SpringLayout.NORTH,
                    parent);
        }
        for (Component c : componentMap2.keySet()) {
            RelativeConstraint constraints = componentMap2.get(c);
            this.putConstraint(constraints.alignX,
                    c,
                    (int) (constraints.x * parent.getWidth()),
                    SpringLayout.WEST,
                    parent);
            this.putConstraint(constraints.alignY,
                    c,
                    (int) (constraints.y * parent.getHeight()),
                    SpringLayout.NORTH,
                    parent);
        }
        super.layoutContainer(parent);
    }

    /**
     * Internal class used to store the coordinates and alignments.
     *
     */
    public static final class RelativeConstraint {

        /**
         * x-position in normalized units.
         */
        private double x;
        /**
         * y-position in normalized units.
         */
        private double y;
        /**
         * The x-axis alignment.
         */
        private String alignX;
        /**
         * The y-axis alignment.
         */
        private String alignY;

        /**
         * Default constructor.
         */
        RelativeConstraint() {
            this(0, 0, SpringLayout.WEST, SpringLayout.NORTH);
        }

        /**
         * Creates a new relative constraint.
         *
         * @param x
         * @param y
         * @param alignX
         * @param alignY
         */
        RelativeConstraint(double x, double y, String alignX, String alignY) {
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
         * @return the x-alignment as a {@code SpringLayout}-defined String.
         */
        public String getAlignX() {
            return alignX;
        }

        /**
         * @param alignX the alignX to set
         */
        public void setAlignX(String alignX) {
            this.alignX = alignX;
        }

        /**
         * @return the y-alignment as a {@code SpringLayout}-defined String.
         */
        public String getAlignY() {
            return alignY;
        }

        /**
         * @param alignY the alignY to set
         */
        public void setAlignY(String alignY) {
            this.alignY = alignY;
        }
    }
}
