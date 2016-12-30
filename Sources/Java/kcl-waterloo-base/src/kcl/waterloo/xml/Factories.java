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
package kcl.waterloo.xml;

import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;

/**
 * Convenience class providing factory methods for some standard inner classes
 * that can be a pain when saving files outside of Java.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Factories {

    /*
     * Non-instantiable class
     */
    private Factories() {
    }

    /**
     * Create a Rectangle2D.Double instance
     *
     * @param x x position
     * @param y y position
     * @param w width
     * @param h height
     * @return a Rectangle2D.Double
     */
    public static Rectangle2D.Double createRectangle2D(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }

    public static LinkedHashMap<String, Object> createComponentMap() {
        return new LinkedHashMap<String, Object>();

    }
}
