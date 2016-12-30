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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Precise {

    private Precise() {
    }

    /**
     * Returns the sum for a double[]. The sum is accumulated as a
     * {@code BigDecimal}
     *
     * @param in
     * @return a BigDecimal
     */
    public static BigDecimal sum(final double[] in) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int k = 0; k < in.length; k++) {
            sum = sum.add(new BigDecimal(in[k]));
        }
        return sum;
    }

    /**
     * Returns the cumulative sum for a double[] as a {@code BigDecimal}
     *
     * @param in
     * @return BigDecimal
     */
    public static BigDecimal[] cumsum(final double[] in) {
        BigDecimal[] sum = new BigDecimal[in.length];
        sum[0] = new BigDecimal(in[0]);
        for (int k = 1; k < in.length; k++) {
            sum[k] = sum[k - 1].add(new BigDecimal(in[k]));
        }
        return sum;
    }

    /**
     * Returns the cumulative sum for a double[] as a {@code BigDecimal}
     *
     * @param in
     * @return a BigDecimal
     */
    public static BigDecimal[] cumprod(final double[] in) {
        BigDecimal[] sum = new BigDecimal[in.length];
        sum[0] = new BigDecimal(in[0]);
        for (int k = 1; k < in.length; k++) {
            sum[k] = sum[k - 1].multiply(new BigDecimal(in[k]));
        }
        return sum;
    }

    /**
     * Returns the sum of squares for a double[] accumulated as a
     * {@code BigDecimal}
     *
     * @param in BigDecimal
     * @return `
     */
    public static BigDecimal sumsq(final double[] in) {
        BigDecimal sumsq = BigDecimal.ZERO;
        BigDecimal temp;
        for (int k = 0; k < in.length; k++) {
            temp = new BigDecimal(in[k]);
            temp = temp.multiply(temp);
            sumsq = sumsq.add(temp);
        }
        return sumsq;
    }

    public static BigDecimal dot(final double[] in1, final double[] in2) {
        BigDecimal dot = BigDecimal.ZERO;
        for (int k = 0; k < in1.length; k++) {
            dot = dot.add(new BigDecimal(in1[k]).multiply(new BigDecimal(in2[k])));
        }
        return dot;
    }

    /**
     * Returns the sumsq for a double[]. The mean is calculated as a
     * {@code BigDecimal} but returned as double
     *
     * @param in
     * @return a BigDecimal
     */
    public static BigDecimal mean(final double[] in) {
        return sum(in).divide(new BigDecimal(in.length));
    }
    /**
     * Factorials and "n choose k"
     *
     */
    final static LinkedHashMap<BigDecimal, BigDecimal> factorialTable = fT();

    private static LinkedHashMap<BigDecimal, BigDecimal> fT() {
        LinkedHashMap<BigDecimal, BigDecimal> t = new LinkedHashMap< BigDecimal, BigDecimal>();
        t.put(BigDecimal.ZERO, BigDecimal.ONE);
        t.put(BigDecimal.ONE, BigDecimal.ONE);
        return t;
    }

    /**
     * Resets the internal hashmaps - thus freeing memory
     */
    public static void reset() {
        factorialTable.clear();
    }

    public static LinkedHashMap getFactorialTable() {
        return (LinkedHashMap) factorialTable.clone();
    }

//    /**
//     * Returns the factorial as a BigDecimal.
//     * For {@code{n less than 0} returns null.
//     * For {@code n=0,1} returns 1.
//     * Otherwise returns n! Once
//     * calculated, values of n and n! are stored in a HashMap. Subsequent calls
//     * with input n will return the stored value of n!.
//     *
//     * @param n
//     * @return n!
//     */
//    public static BigDecimal factorial(int n) {
//        return factorial(new BigDecimal(new Integer(n).toString()));
//    }
    /**
     * Returns the factorial as a BigDecimal. For {@code n less than 0} returns
     * null. For {@code n=0,1} returns 1. Otherwise returns n!. Once calculated,
     * values of n and n! are stored in a HashMap. Subsequent calls with input n
     * will return the stored value of n!.
     *
     * @param n - type long
     * @return n! - type BigDecimal
     */
    public static BigDecimal factorial(long n) {
        return factorial(new BigDecimal(n));
    }

    /**
     * Returns the factorial as a BigDecimal. For {@code n less than 0} returns
     * null. For {@code n=0,1} returns 1. Otherwise returns n! Once calculated,
     * values of n and n! are stored in a HashMap. Subsequent calls with input n
     * will return the stored value of n!.
     *
     * @param n - type BigDecimal
     * @return n! - type BigDecimal
     */
    public static BigDecimal factorial(BigDecimal n) {
        if (n.signum() == -1) {
            return null;
        }
        if (factorialTable.containsKey(n)) {
            // Already in the table
            return factorialTable.get(n);
        } else {
            //Not in the table
            int count = 0;
            Collection<BigDecimal> c = factorialTable.keySet();
            BigDecimal[] seed = c.toArray(new BigDecimal[c.size()]);
            BigDecimal product;
            BigDecimal n2;
            for (Iterator<BigDecimal> it = c.iterator(); it.hasNext();) {
                BigDecimal entry = it.next();
                if (entry.compareTo(n) > 0) {
                    product = factorialTable.get(seed[count]);
                    n2 = seed[count];
                    while (!n2.equals(n)) {
                        product = product.divide(n2);
                        n2 = n2.subtract(BigDecimal.ONE);
                    }
                    factorialTable.put(n, product);
                    return product;
                }
                count++;
            }
            // Iterator did not find a bigger entry
            product = factorialTable.get(seed[seed.length - 1]);
            n2 = seed[seed.length - 1].add(BigDecimal.ONE);
            while (n2.compareTo(n) <= 0) {
                product = product.multiply(n2);
                n2 = n2.add(BigDecimal.ONE);
            }
            factorialTable.put(n, product);
            return product;
        }
    }

    /**
     * <p>
     * Accurately returns the binomial (N choose K) as a BigDecimal</p>
     * <p>
     * For k< 0, return ZERO</p> <p>For k==0 or
     * k
     * ==n return ONE</p>
     * <p>
     * For k==1 return n</p>
     * <p>
     * Otherwise returns
     * <pre>n!/((n*k)! k!)</pre></p>
     *
     * @param n - int
     * @param k - int
     * @return <pre>n!/((n*k)! k!)</pre> as a BigDecimal
     */
    public static BigDecimal nCk(int n, int k) {
        return nCk(new BigDecimal(n),
                new BigDecimal(k));
    }

    /**
     * <p>
     * Accurately returns the binomial (N choose K) as a BigDecimal</p>
     * <p>
     * For k< 0, return ZERO</p> <p>For k==0 or
     * k
     * ==n return ONE</p>
     * <p>
     * For k==1 return n</p>
     * <p>
     * Otherwise returns
     * <pre>n!/((n*k)! k!)</pre></p>
     *
     * @param n - long
     * @param k - long
     * @return <pre>n!/((n*k)! k!)</pre> as a BigDecimal
     */
    public static BigDecimal nCk(long n, long k) {
        return nCk(new BigDecimal(Long.toString(n)),
                new BigDecimal(Long.toString(k)));
    }

    /**
     * <p>
     * Accurately returns the binomial (N choose K) as a BigDecimal</p>
     * <p>
     * For k< 0, return ZERO</p> <p>For k==0 or
     * k
     * ==n return ONE</p>
     * <p>
     * For k==1 return n</p>
     * <p>
     * Otherwise returns
     * <pre>n!/((n*k)! k!)</pre></p>
     *
     * @param n - BigDecimal
     * @param k - BigDecimal
     * @return <pre>n!/((n*k)! k!)</pre> as a BigDecimal
     */
    public static BigDecimal nCk(BigDecimal n, BigDecimal k) {
        switch (k.signum()) {
            case -1:
                // k<0
                return BigDecimal.ZERO;
            case 0:
                // k==0
                return BigDecimal.ONE;
            case 1:
                // k==1 
                if (k.equals(BigDecimal.ONE)) {
                    return n;
                }
        }
        switch (n.subtract(k).signum()) {
            case -1:
                // k>n
                return BigDecimal.ZERO;
            case 0:
                // k==n
                return BigDecimal.ONE;
            default:
                // Note, k=n-1 and k>n/2 are not treated specially as n! and/or
                // k! may be in the factorialTable or required later.
                return factorial(n).divide(factorial(n.subtract(k)).multiply(factorial(k)));
        }
    }
}
