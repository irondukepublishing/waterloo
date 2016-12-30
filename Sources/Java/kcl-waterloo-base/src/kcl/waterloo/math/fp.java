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
package kcl.waterloo.math;

//import math.geom2d.circulinear.CirculinearContourArray2D;
/**
 * Provides static methods for double arithmetic with corrections for loss of
 * precision.
 *
 * See:
 * <p>
 * <a href="http://www.math.twcu.ac.jp/~ogita/math/presen/dag2005.pdf">
 * Fast and Accurate Computation of Sum and Dot Product</a> </p> or
 * <p>
 * <a href="http://dl.acm.org/citation.cfm?id=1064925.1071692"> Accurate
 * Computation of Sum and Dot Product</a> [NOTE TYPO IN THE TWOSUM ALGORITHM IN
 * THIS ONE] </p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class fp {

//    public static CirculinearContourArray2D createStatic(){
//        return CirculinearContourArray2D.create(null);
//    }
    /**
     * Private constructor. This is a non-instantiable class providing a static
     * method library.
     */
    private fp() {
    }

    /**
     * Splits a double value into two double values where the sum of the two is
     * the input.
     *
     * See T. J. Dekker, A floating-point technique for extending the available
     * precision, Numer. Math., 18 (1971), pp. 224-242.
     *
     * @param val a double input
     * @return a double[2] array - where the sum of values==the input.
     */
    public static double[] split(final double val) {
        final double factor = Math.scalb(1, 27) + 1;
        final double c = val * factor;
        final double x = c - (c - val);
        return new double[]{x, val - x};
    }

    /**
     * Returns the product of two double values and a correction for loss of
     * precision in the multiplication.
     *
     * @param a a double value.
     * @param b a double value.
     * @return a double[2] array containing in [0]: the product, and in [1]: the
     * required correction.
     */
    public static double[] twoProduct(final double a, final double b) {
        final double x = a * b;
        double[] as = split(a);
        double[] bs = split(b);
        return new double[]{x, (as[1] * bs[1] - (((x - as[0] * bs[0]) - as[1] * bs[0] - as[0] * bs[1])))};
    }

    /**
     * Returns the sum of two double values and a correction for loss of
     * precision in the summation.
     *
     * @param a a double value.
     * @param b a double value.
     * @return a double[2] array containing in [0]: the sum, and in [1]: the
     * required correction.
     */
    public static double[] twoSum(final double a, final double b) {
        final double x = a + b;
        final double z = x - a;
        return new double[]{x, (a - (x - z) + (b - z))};

    }

    /**
     * Returns the sum of {@code in} using Knuth's correction to accumulate the
     * error. The accumulated error is added to the output.
     *
     * @param in
     * @return the sum
     */
    public static double sum(double[] in) {
        double sum = 0d;
        double x, y = 0d, z;
        for (int k = 0; k < in.length; k++) {
            x = sum + in[k];
            z = x - sum;
            y += ((sum - (x - z)) + (in[k] - z));
            sum = x;
        }
        return sum + y;
    }

    /**
     * Returns the sum between two indices (inclusive).
     *
     * @param in input array
     * @param idx0 start index
     * @param idx1 end index
     * @return the sum of in[idx0] thtough in[idx1]
     */
    public static double sum(double[] in, int idx0, int idx1) {
        double sum = 0d;
        double x, y = 0d, z;
        for (int k = idx0; k <= idx1; k++) {
            x = sum + in[k];
            z = x - sum;
            y += ((sum - (x - z)) + (in[k] - z));
            sum = x;
        }
        return sum + y;
    }

    /**
     * Returns the cumulative sum of the elements of the input using Knuth's
     * correction to accumulate the error. The accumulated error for each
     * element is added to the output.
     *
     * @param in
     * @return a double[]
     */
    public static double[] cusum(double[] in) {
        double s[] = new double[in.length];
        double sum = 0d;
        double x, y = 0d, z;
        for (int k = 0; k < in.length; k++) {
            x = sum + in[k];
            z = x - sum;
            y += ((sum - (x - z)) + (in[k] - z));
            sum = x;
            s[k] = sum + y;
        }
        return s;
    }

    /**
     *
     * Returns the cumulative sum of the elements of the input using Knuth's
     * correction to accumulate the error (in-place). The accumulated error for
     * each element is added to the output.
     *
     * @param in
     * @return a double[]
     */
    public static double[] cusumi(double[] in) {
        double sum = 0d;
        double x, y = 0d, z;
        for (int k = 0; k < in.length; k++) {
            x = sum + in[k];
            z = x - sum;
            y += ((sum - (x - z)) + (in[k] - z));
            sum = x;
            in[k] = sum + y;
        }
        return in;
    }

    /**
     * Returns the dot product.
     *
     * @param a double[] input a
     * @param b double[] input b
     * @return the sum of a[k]*b[k] for k = 0 .. length(a)-1
     */
    public static double dot(double[] a, double[] b) {
        assertSameLength(a, b);
        double s = 0d;
        double[] p = twoProduct(a[0], b[0]);
        double[] h;
        for (int k = 1; k < a.length; k++) {
            h = twoProduct(a[k], b[k]);
            p = twoSum(p[0], h[0]);
            s += p[1] + h[1];
        }
        return p[0] + s;
    }

    public static boolean tolEquals(double a, double b, double tol) {
        return a == b || a <= b + tol || a >= b - tol;
    }

    private static void assertSameLength(double[] X, double[] Y) {
        if (X.length != Y.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "Vectors must be the same length: not " + X.length + " and " + Y.length);
        }
    }
}
