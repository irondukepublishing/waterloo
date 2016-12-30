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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

/**
 * Provides convenience static library for math operations across vectors. Most
 * methods are defined for double[] only.
 * <p/>
 * <strong>Methods appended with "i" perform operations in-place and so
 * over-write the input data by reference.</strong>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class ArrayMath {

    /**
     * Returns the sum of the elements of the input. Compensates for IEEE
     * precision loss.
     *
     * @param in the input array
     * @return a double
     */
    public static double sum(double[] in) {
        return fp.sum(in);
    }

    public static double sum(double[] in, int idx0, int idx1) {
        return fp.sum(in, idx0, idx1);
    }

    /**
     * Returns the cumulative sum of the elements of the input.
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] cusum(double[] in) {
        return fp.cusum(in);
    }

    /**
     * Returns the cumulative y of the elements of the input (in-place).
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] cusumi(double[] in) {
        return fp.cusumi(in);
    }

    /**
     * Returns the absolute values for a double[]
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] abs(final double[] in) {
        final double[] abs = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            abs[k] = (in[k] < 0.0) ? -in[k] : (in[k] == 0.0) ? 0.0 : in[k];
        }
        return abs;
    }

    /**
     * Returns the absolute values for a double[] (in-place)
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] absi(final double[] in) {
        for (int k = 0; k < in.length; k++) {
            in[k] = (in[k] < 0.0) ? -in[k] : (in[k] == 0.0) ? 0.0 : in[k];
        }
        return in;
    }

    /**
     * Returns the square roots for a double[]
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] sqrt(final double[] in) {
        final double[] sqrt = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            sqrt[k] = Math.sqrt(in[k]);
        }
        return sqrt;
    }

    /**
     * Returns the square roots for a double[] (in-place)
     *
     * @param in the input array
     * @return double[]
     */
    public static double[] sqrti(final double[] in) {
        for (int k = 0; k < in.length; k++) {
            in[k] = Math.sqrt(in[k]);
        }
        return in;
    }

    /**
     * Returns the negated input
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] neg(final double[] in) {
        final double[] neg = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            neg[k] = -in[k];
        }
        return neg;
    }

    /**
     * Returns the negated input (in-place)
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] negi(final double[] in) {
        for (int k = 0; k < in.length; k++) {
            in[k] = -in[k];
        }
        return in;
    }

    /**
     * Returns the Math.log10() for a double[]
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] log10(final double[] in) {
        final double[] out = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = Math.log10(in[k]);
        }
        return out;
    }

    /**
     * Returns the Math.log10() for a double[] (in-place)
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] log10i(final double[] in) {
        for (int k = 0; k < in.length; k++) {
            in[k] = Math.log10(in[k]);
        }
        return in;
    }

    /**
     * Returns the Math.pow() for a double[]
     *
     * @param in the input array
     * @param exponent the exponent
     * @return a double[]
     */
    public static double[] pow(final double[] in, double exponent) {
        final double[] out = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = Math.pow(in[k], exponent);
        }
        return out;
    }

    /**
     * Returns the Math.pow() for a double[] (in-place)
     *
     * @param in the input array
     * @param exponent the exponent
     * @return a double[]
     */
    public static double[] powi(final double[] in, double exponent) {
        for (int k = 0; k < in.length; k++) {
            in[k] = Math.pow(in[k], exponent);
        }
        return in;
    }

    /**
     * Returns the first derivative for a double[]
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] diff(final double[] in) {
        final double[] diff = new double[in.length - 1];
        for (int k = 0; k < in.length - 1; k++) {
            diff[k] = in[k + 1] - in[k];
        }
        return diff;
    }

    public static double[] diffpad(final double[] in) {
        final double[] diff = new double[in.length];
        for (int k = 0; k < in.length - 1; k++) {
            diff[k] = in[k + 1] - in[k];
        }
        diff[in.length - 1] = Double.NaN;
        return diff;
    }

    /**
     * Returns the first derivative for a double[] (in-place). The final element
     * will be set to NaN.
     *
     * @param in the input array
     * @return a double[]
     */
    public static double[] diffi(final double[] in) {
        for (int k = 0; k < in.length - 1; k++) {
            in[k] = in[k + 1] - in[k];
        }
        in[in.length - 1] = Double.NaN;
        return in;
    }

    /**
     * Fills a double[] of length len, with values beginning with start and
     * incrementing by inc. start and inc must be integers Output is reliable
     * only for values with a magnitude less than or equal to 2^53
     *
     * @param len the length of the array to create
     * @param start the starting value
     * @param inc the increment between elements
     * @return a double[]
     */
    public static double[] fill(int len, int start, int inc) {
        final double[] fill = new double[len];
        for (int k = 0; k < len; k++) {
            fill[k] = start + (k * inc);
        }
        return fill;
    }

    /**
     * Fills the input with the specified value (in-place)
     *
     * @param in the input array
     * @param constant the constant value to fill the array with
     * @return a double[]
     */
    public static double[] filli(final double[] in, double constant) {
        Arrays.fill(in, constant);
        return in;
    }

    /**
     * Fills the input with the specified value (in-place)
     *
     * @param in the input array
     * @param constant the constant value to fill the array with
     * @return a boolean[]
     */
    public static boolean[] filli(final boolean[] in, boolean constant) {
        Arrays.fill(in, constant);
        return in;
    }

    /**
     * Returns in1/in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 the numerator array
     * @param in2 the divisor array
     * @return a double[] the returned array
     */
    public static double[] div(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        final double[] div = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            div[k] = in1[k] / in2[k];
        }
        return div;
    }

    /**
     * Returns in1/in2 element-wise where in1 and in2 are double[]s (in-place)
     *
     * @param in1 the numerator array
     * @param in2 the divisor array
     * @return a double[] the returned array (i.e. the numerator array)
     */
    public static double[] divi(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] = in1[k] / in2[k];
        }
        return in1;
    }

    /**
     * Returns in1/factor element-wise where in1 is a double[] and factor is
     * scalar
     *
     * @param in1 the numerator array
     * @param factor the scalar divisor
     * @return a double[] the returned array
     */
    public static double[] div(final double[] in1, final double factor) {
        final double[] div = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            div[k] = in1[k] / factor;
        }
        return div;
    }

    /**
     * Returns in1/factor element-wise where in1 is a double[] and factor is
     * scalar (in-place)
     *
     * @param in1 the input array
     * @param factor the divisor
     * @return a double[] the output array (i.e. in1)
     */
    public static double[] divi(final double[] in1, final double factor) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] = in1[k] / factor;
        }
        return in1;
    }

    /**
     * Returns factor - in1 element-wise where in1 is a double[] and factor is
     * scalar (in-place)
     *
     * @param in1 the input array
     * @param factor the scalar to subtract
     * @return a double[] the output array (i.e. in1)
     */
    public static double[] subi(final double factor, final double[] in1) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] = factor - in1[k];
        }
        return in1;
    }

    /**
     * Returns in1*in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return a double[] the element-wise product
     */
    public static double[] mul(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        final double[] mul = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            mul[k] = in1[k] * in2[k];
        }
        return mul;
    }

    /**
     * Returns in1*in2 element-wise where in1 and in2 are double[]s (in-place)
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return a double[], the element-wise product (i.e. in1)
     */
    public static double[] muli(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] *= in2[k];
        }
        return in1;
    }

    /**
     * Returns in1*factor element-wise where in1 is a double[] and factor is
     * scalar
     *
     * @param in1 the input array
     * @param factor a scalar factor
     * @return a double[] the product
     */
    public static double[] mul(final double[] in1, final double factor) {
        final double[] mul = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            mul[k] = in1[k] * factor;
        }
        return mul;
    }

    /**
     * Returns in1*factor element-wise where in1 is a double[] and factor is
     * scalar (in-place)
     *
     * @param in1 the input array
     * @param factor a scalar factor
     * @return a double[] the product (i.e. in1)
     */
    public static double[] muli(final double[] in1, final double factor) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] *= factor;
        }
        return in1;
    }

    /**
     * Returns in1+in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return a double[] the sum
     */
    public static double[] add(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        final double[] mul = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            mul[k] = in1[k] + in2[k];
        }
        return mul;
    }

    /**
     * Returns in1+in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return a double[] the sum (i.e. in1)
     */
    public static double[] addi(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] += in2[k];
        }
        return in1;
    }

    /**
     * Returns in1 + factor element-wise
     *
     * @param in1 the input array
     * @param factor the factor to add
     * @return a double[] the sum
     */
    public static double[] add(final double[] in1, double factor) {
        final double[] mul = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            mul[k] = in1[k] + factor;
        }
        return mul;
    }

    /**
     * Returns in1 + factor element-wise (in-place)
     *
     * @param in1 the input array
     * @param factor the factor to add
     * @return a double[] the sum (i.e. in1)
     */
    public static double[] addi(final double[] in1, double factor) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] += factor;
        }
        return in1;
    }

    /**
     * Returns in1-in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return double[] the difference
     */
    public static double[] sub(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        final double[] diff = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            diff[k] = in1[k] - in2[k];
        }
        return diff;
    }

    /**
     * Returns in1-in2 element-wise where in1 and in2 are double[]s
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return double[] the difference (i.e. in1)
     */
    public static double[] subi(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] -= in2[k];
        }
        return in1;
    }

    /**
     * Returns in-factor element-wise
     *
     * @param in input array
     * @param factor the value to subtract
     * @return double[] the difference (i.e. in1)
     */
    public static double[] sub(final double[] in, double factor) {
        final double[] diff = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            diff[k] = in[k] - factor;
        }
        return diff;
    }

    /**
     * Returns in-factor element-wise (in-place)
     *
     * @param in input array
     * @param factor the value to subtract
     * @return double[] the difference (i.e. in1)
     */
    public static double[] subi(final double[] in, double factor) {
        for (int k = 0; k < in.length; k++) {
            in[k] -= factor;
        }
        return in;
    }

    /**
     * Returns scalar dot product of in1 & in2
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return a double, the dot product of the inputs
     */
    public static double dot(final double[] in1, final double[] in2) {
        return fp.dot(in1, in2);
    }

    /**
     * Returns true for in==0 (or -0) element-wise
     *
     * @param in the input array
     * @return a boolean[]
     */
    public static boolean[] isZero(final double[] in) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] == 0d;
        }
        return flags;
    }

    /**
     * Returns true for in==x element-wise.
     *
     * @param in the input array
     * @param x the value to compare to
     * @return a boolean[]
     */
    public static boolean[] isEqual(final double[] in, double x) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] == x;
        }
        return flags;
    }

    /**
     * Returns true where in has magnitude less than x, element-wise.
     *
     * @param in the input array
     * @param x the value to compare to
     * @return a boolean[]
     */
    public static boolean[] isLT(final double[] in, double x) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] < x;
        }
        return flags;
    }

    /**
     * Returns true where in has magnitude less than or equal to x,
     * element-wise.
     *
     * @param in the input array
     * @param x the value to compare to
     * @return a boolean[]
     */
    public static boolean[] isLE(final double[] in, double x) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] <= x;
        }
        return flags;
    }

    /**
     * Returns true where in is greater than x, element-wise.
     *
     * @param in the input array
     * @param x the value to compare to
     * @return a boolean[]
     */
    public static boolean[] isGT(final double[] in, double x) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] > x;
        }
        return flags;
    }

    /**
     * Returns true where in is greater than or equal to x, element-wise.
     *
     * @param in the input array
     * @param x the value to compare to
     * @return a boolean[]
     */
    public static boolean[] isGE(final double[] in, double x) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = in[k] >= x;
        }
        return flags;
    }

    /**
     * Returns true where in is Not-a-Number element-wise.
     *
     * @param in the input array
     * @return a boolean[]
     */
    public static boolean[] isNaN(final double[] in) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = Double.isNaN(in[k]);
        }
        return flags;
    }

    /**
     * Returns true where in equals +infinity or -infinity, element-wise.
     *
     * @param in the input array
     * @return a boolean[]
     */
    public static boolean[] isInfinite(final double[] in) {
        final boolean[] flags = new boolean[in.length];
        for (int k = 0; k < in.length; k++) {
            flags[k] = (in[k] == Double.POSITIVE_INFINITY
                    || in[k] == Double.NEGATIVE_INFINITY);
        }
        return flags;
    }

    /**
     * Returns true where any element in the input is true
     *
     * @param in the input array
     * @return a boolean
     */
    public static boolean any(boolean[] in) {
        for (boolean anIn : in) {
            if (anIn) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns minimum value ignoring NaNs.
     *
     * @param in a double[]
     * @return the minimum value from the input
     */
    public static double min(final double[] in) {
        double mn = in[0];
        for (int k = 1; k < in.length; k++) {
            mn = (in[k] < mn) ? in[k] : mn;
        }
        return mn;
    }

    /**
     * Returns maximum value ignoring NaNs.
     *
     * @param in a double[]
     * @return the maximum value from the input
     */
    public static double max(final double[] in) {
        double mx = in[0];
        for (double anIn : in) {
            mx = (anIn > mx) ? anIn : mx;
        }
        return mx;
    }

    /**
     * Returns a vector containing the minimum for each element from the inputs.
     * NaNs will always be judged greater than any other value.
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return the minimum values from the inputs
     */
    public static double[] min(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        double[] out = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            out[k] = Math.min(in1[k], in2[k]);
        }
        return out;
    }

    /**
     * Returns a vector containing the minimum for each element from the inputs.
     * NaNs will always be judged greater than any other value. In-place
     * operation: the output will be put into the first input vector. (in-place)
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return the minimum values from the inputs (in in1)
     */
    public static double[] mini(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] = Math.min(in1[k], in2[k]);
        }
        return in1;
    }

    /**
     * Returns a vector containing the minimum for each element from the input
     * and the factor supplied.
     *
     * @param in input array
     * @param factor to comapre the input to
     * @return the minimum values from the input and factor (in in1)
     */
    public static double[] min(final double[] in, final double factor) {
        double[] out = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = Math.min(in[k], factor);
        }
        return out;
    }

    /**
     * Returns a vector containing the maximum for each element from the input
     * and the factor supplied.
     *
     * @param in input array
     * @param factor to compare to
     * @return the maximum values from the input and factor
     */
    public static double[] max(final double[] in, final double factor) {
        double[] out = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = Math.max(in[k], factor);
        }
        return out;
    }

    /**
     * Returns a vector containing the maximum for each element from the inputs.
     * DOES NOT ignore NaNs which will be judged larger than any other value.
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return the maximum values from the inputs
     */
    public static double[] max(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        double[] out = new double[in1.length];
        for (int k = 0; k < in1.length; k++) {
            out[k] = Math.max(in1[k], in2[k]);
        }
        return out;
    }

    /**
     * Returns a vector containing the maximum for each element from the inputs.
     * DOES NOT ignore NaNs which will be judged larger than any other value.
     * (in-place)
     *
     * @param in1 input array 1
     * @param in2 input array 2
     * @return the maximum values from the inputs (in in1)
     */
    public static double[] maxi(final double[] in1, final double[] in2) {
        assertSameLength(in1, in2);
        for (int k = 0; k < in1.length; k++) {
            in1[k] = Math.max(in1[k], in2[k]);
        }
        return in1;
    }

    /**
     * Returns a vector containing the maximum for each element from the input
     * and factor. DOES NOT ignore NaNs which will be judged larger than any
     * other value (in-place).
     *
     * @param in1 input array 1
     * @param factor
     * @return the maximum values from the input and factor (in in1)
     */
    public static double[] maxi(final double[] in1, final double factor) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] = Math.max(in1[k], factor);
        }
        return in1;
    }

    /**
     * Returns a vector containing the minimum for each element from the inputs.
     * DOES NOT ignore NaNs which will be judged larger than any other value.
     *
     * @param in1 input array 1
     * @param factor a scalar value
     * @return the minimum values
     */
    public static double[] mini(final double[] in1, final double factor) {
        for (int k = 0; k < in1.length; k++) {
            in1[k] = Math.min(in1[k], factor);
        }
        return in1;
    }

    /**
     * Returns double[2] array containing the minimum and maximum values
     * ignoring NaNs.
     *
     * @param in a double[]
     * @return the minimum & maximum value from the input
     */
    public static double[] minmax(final double[] in) {
        double mx = in[0];
        double mn = in[0];
        for (int k = 1; k < in.length; k++) {
            mx = (in[k] > mx) ? in[k] : mx;
            mn = (in[k] < mn) ? in[k] : mn;
        }
        return new double[]{mn, mx};
    }

    /**
     * Returns int[2] array containing the minimum and maximum values ignoring
     * NaNs.
     *
     * @param in a int[]
     * @return the minimum & maximum value from the input
     */
    public static int[] minmax(final int[] in) {
        int mx = in[0];
        int mn = in[0];
        for (int k = 1; k < in.length; k++) {
            mx = (in[k] > mx) ? in[k] : mx;
            mn = (in[k] < mn) ? in[k] : mn;
        }
        return new int[]{mn, mx};
    }

    /**
     * Repetitively fills the vector in with the values from vec
     *
     * @param in the array to fill
     * @param vec the source values
     * @return the returned repeated vector
     */
    public double[] repVeci(final double[] in, double[] vec) {
        for (int k = 0; k < in.length; k++) {
            in[k] = vec[k % vec.length];
        }
        return in;
    }

    public static double[] linspace(double start, double stop, int n) {
        BigDecimal lim0 = new BigDecimal(start, MathContext.DECIMAL128);
        BigDecimal lim1 = new BigDecimal(stop, MathContext.DECIMAL128);
        BigDecimal step = lim1.subtract(lim0, MathContext.DECIMAL128);
        step = step.divide(new BigDecimal(Double.valueOf((double) n - 1)), MathContext.DECIMAL128);
        double[] darr = new double[n];
        darr[0] = lim0.doubleValue();
        for (int k = 1; k < darr.length; k++) {
            lim0 = lim0.add(step);
            darr[k] = lim0.doubleValue();
        }
        return darr;
    }

    private static void assertSameLength(double[] X, double[] Y) {
        if (X.length != Y.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "Vectors must be the same length: not " + X.length + " and " + Y.length);
        }
    }
}
