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
public class Matrix {

    private Matrix() {
    }

    public static double[][] mult(double[][] A, double[][] B) {
        assertSquare(A, B);
        int N = A.length;
        double[][] C = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                for (int j = 0; j < N; j++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static double[][] multopt(double[][] A, double[][] B) {
        assertSquare(A, B);
        int N = A.length;
        double[][] C = new double[N][N];
        for (int i = 0; i < N; i++) {
            double[] arowi = A[i];
            double[] crowi = C[i];
            for (int k = 0; k < N; k++) {
                double[] browk = B[k];
                double aik = arowi[k];
                for (int j = 0; j < N; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
        return C;
    }

    /**
     * Return a {@code Double[][]} given a {@code double[][]} as input
     *
     * @param in a {@code Double[][]}
     * @return a {@code double[][]}
     */
    public static Double[][] toDouble(final double[][] in) {
        final Double[][] out = new Double[in.length][in[0].length];
        for (int n = 0; n < in[0].length; n++) {
            for (int m = 0; m < in.length; m++) {
                out[m][n] = in[m][n];
            }
        }
        return out;
    }

    public static Double[] toDouble(final double[] in) {
        final Double[] out = new Double[in.length];
        for (int m = 0; m < in.length; m++) {
            out[m] = in[m];
        }
        return out;
    }

    private static void assertSquare(double[][] X, double[][] Y) {
        if (X.length != Y.length || X[0].length != X.length || Y[0].length != Y.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "Matrices must be square");
        }
    }
}
