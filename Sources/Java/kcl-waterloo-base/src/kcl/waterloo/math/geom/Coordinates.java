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
package kcl.waterloo.math.geom;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Coordinates {

    private Coordinates() {
    }

    public static Polar cartesianToPolar(Cartesian cart) {
        return cartesianToPolar(cart.x, cart.y);
    }

    public static Polar cartesianToPolar(double[] x, double[] y) {
        Polar pol = new Polar(x.length);
        for (int k = 0; k < x.length; k++) {
            pol.radius[k] = Math.hypot(x[k], y[k]);
            pol.theta[k] = Math.atan2(y[k], x[k]);
        }
        return pol;
    }

    public static Cartesian polarToCartesian(Polar pol) {
        return polarToCartesian(pol.theta, pol.radius);
    }

    public static Cartesian polarToCartesian(double[] theta, double[] radius) {
        Cartesian cart = new Cartesian(theta.length);
        for (int k = 0; k < theta.length; k++) {
            cart.x[k] = Math.cos(theta[k]) * radius[k];
            cart.y[k] = Math.sin(theta[k]) * radius[k];
        }
        return cart;
    }

    public static Cartesian polarToCartesian(double theta, double radius) {
        Cartesian cart = new Cartesian(1);
        cart.x[0] = Math.cos(theta) * radius;
        cart.y[0] = Math.sin(theta) * radius;
        return cart;
    }
}
