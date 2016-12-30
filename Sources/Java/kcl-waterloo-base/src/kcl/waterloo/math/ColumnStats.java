 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
package kcl.waterloo.math;

import java.util.Arrays;
import java.util.Formatter;

/**
 * Provides basic statistics for the columns of a double[][] array.
 *
 * Columns should contain the same number of elements. Missing values should be
 * entered as NaNs.
 *
 * Statistics available are :
 * <p>
 * Sum</p>
 * <p>
 * Mean</p>
 * <p>
 * Variance</p>
 * <p>
 * Standard deviation</p>
 * <p>
 * Skew</p>
 * <p>
 * Kurtosis</p>
 * <p>
 * Minimum</p>
 * <p>
 * Maximum</p>
 * <p>
 * Median</p>
 * <p>
 * Upper and Lower percentiles</p>
 * <p>
 * Number of valid (non-NaN) values</p><p>
 * Geometric mean</p>
 *
 * <p>
 * NaNs are treated as missing values and discounted from all calculations.</p>
 *
 * An internal matrix contains the following parameters in the rows specified by
 * ColumnStats.X where X is a static final int.
 *
 * <p>
 * ColumnStats.SUM the sum of values.</p>
 * <p>
 * ColumnStats.SUMDELTA2 the sum of squares of residuals.</p>
 * <p>
 * ColumnStats.SUMDELTA3 the sum of cubes of residuals.</p><p>
 * ColumnStats.SUMDELTA4 the sum of residuals to power 4.</p><p>
 * ColumnStats.MIN minimum value.</p>
 * <p>
 * ColumnStats.MAX maximum value.</p>
 * <p>
 * ColumnStats.MEDIAN median value.</p>
 * <p>
 * ColumnStats.LOWER lower percent.</p>
 * <p>
 * ColumnStats.UPPER upper percent.</p>
 * <p>
 * ColumnStats.SUMLOG sum of logs.</p>
 * <p>
 * ColumnStats.N number of observations (discounting NaNs).</p>
 * <p>
 * ColumnStats.Pct the requested percent (0 to 50): </p>
 * <p>
 * LOWER contains these values while UPPER contains values for percent =
 * (100-ColumnStats.Pct).</p>
 *
 *
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ColumnStats {

    /**
     * An internal buffer for intermediate results. Contents of the columns are
     * as defined by the names of the static int fields below.
     */
    private final double[][] summary;
    /**
     *
     */
    public static final int SUM = 0, SUMDELTA2 = 1, SUMDELTA3 = 2, SUMDELTA4 = 3,
            MIN = 4, MAX = 5, MEDIAN = 6,
            LOWER = 7, UPPER = 8, SUMLOG = 9, N = 10;
    /**
     * When true, variance will be corrected by (N-1) instead of N. Default is
     * true.
     */
    private boolean varianceCorrected = true;
    /**
     * When true, the skew will be adjusted by multiplying by by Math.sqrt(N *
     * (N - 1)) / (N - 2). Default is false.
     */
    private boolean skewCorrected = false;
    /**
     * When true, the kurtosis will be adjusted as ((N+1) * unadjusted kurtosis
     * - 3*(N-1)) * (N-1) /((N-2)*(N-3)). Default is false.
     */
    private boolean kurtosisCorrected = false;
    /**
     *
     */
    private final int percent;

    /**
     * Returns statistics using 25th/75th percentiles
     *
     * @param data the data to analyze
     */
    public ColumnStats(double[][] data) {
        this(data, 25);
    }

    /**
     * Returns statistics using specified percentiles
     *
     * @param data the data to analyze
     * @param percentile the percentiles to use
     */
    public ColumnStats(double[][] data, int percentile) {
        summary = initialMatrix(data[0].length);
        this.percent = percentile;
        createSummary(data);
    }

    private static double[][] initialMatrix(int N) {
        double[][] matrix = new double[11][N];
        ArrayMath.filli(matrix[MIN], Double.POSITIVE_INFINITY);
        ArrayMath.filli(matrix[MAX], Double.NEGATIVE_INFINITY);
        return matrix;
    }

    /**
     * Returns the number of columns for the input array.
     *
     * @return number of columns
     */
    public int getDimension() {
        return summary[0].length;
    }

    /**
     * Returns the sum of the data in each column of the input.
     *
     * @return the sum
     */
    public double[][] getSum() {
        return new double[][]{summary[SUM].clone()};
    }

    /**
     * Returns the mean of the data in each column of the input.
     *
     * @return the mean
     */
    public double[][] getMean() {
        return new double[][]{ArrayMath.div(summary[SUM], summary[N])};
    }

    /**
     * Returns the variance of the data in each column of the input. Bias
     * correction is applied if {@code varianeCorrected} is true.
     *
     * @return the variance
     */
    public double[][] getVariance() {
        double[] factor;
        if (varianceCorrected) {
            factor = ArrayMath.div(ArrayMath.filli(new double[getDimension()], 1d), ArrayMath.sub(summary[N], 1));
        } else {
            factor = ArrayMath.div(ArrayMath.filli(new double[getDimension()], 1d), summary[N]);
        }
        return new double[][]{ArrayMath.muli(factor, summary[SUMDELTA2])};
    }

    /**
     * Returns the standard deviation of the data in each column of the input.
     * Bias correction is applied to the variance if {@code varianceCorrected}
     * is true.
     *
     * @return the standard deviation
     */
    public double[][] getSD() {
        return new double[][]{ArrayMath.sqrt(getVariance()[0])};
    }

    /**
     * Returns the skew of the data in each column of the input. Bias correction
     * is applied to the variance if {@code skewCorrected} is true.
     *
     * @return the skew
     */
    public double[][] getSkew() {
        double[] NN = getN()[0];
        double[] num = ArrayMath.div(summary[SUMDELTA3], NN);
        // Uncorrected SD
        boolean old = varianceCorrected;
        varianceCorrected = false;
        double[] den = getSD()[0];
        varianceCorrected = old;
        den = ArrayMath.powi(den, 3);
        double[] result = ArrayMath.div(num, den);
        if (skewCorrected) {
            for (int k = 0; k < NN.length; k++) {
                if (NN[k] >= 3) {
                    result[k] *= Math.sqrt(NN[k] * (NN[k] - 1)) / (NN[k] - 2);
                }
            }
        }
        return new double[][]{result};
    }

    /**
     * Returns the kurtosis of the data in each column of the input. Bias
     * correction is applied to the variance if {@code kurtosisCorrected} is
     * true.
     *
     * @return the kurtosis
     */
    public double[][] getKurtosis() {
        double[] NN = getN()[0];
        double[] num = ArrayMath.div(summary[SUMDELTA4], NN);
        // Uncorrected SD
        boolean old = varianceCorrected;
        varianceCorrected = false;
        double[] den = getVariance()[0];
        varianceCorrected = old;
        den = ArrayMath.powi(den, 2);
        double[] result = ArrayMath.div(num, den);
        if (kurtosisCorrected) {
            for (int k = 0; k < NN.length; k++) {
                if (NN[k] >= 4) {
                    result[k] = ((NN[k] + 1) * result[k] - 3 * (NN[k] - 1))
                            * (NN[k] - 1) / ((NN[k] - 2) * (NN[k] - 3)) + 3;
                }
            }
        }
        return new double[][]{result};
    }

    /**
     * Returns the minimum value for each column of the data.
     *
     * @return the minimum
     */
    public double[][] getMinimum() {
        return new double[][]{summary[MIN].clone()};
    }

    /**
     * Returns the maximum value for each column of the data.
     *
     * @return the maximum
     */
    public double[][] getMaximum() {
        return new double[][]{summary[MAX].clone()};
    }

    /**
     * Returns the geometric mean for each column of the data.
     *
     * @return the geometric mean
     */
    public double[][] getGeometricMean() {
        double[] g = ArrayMath.div(summary[SUMLOG], summary[N]);
        for (int k = 0; k < g.length; k++) {
            g[k] = Math.exp(g[k]);
        }
        return new double[][]{g};
    }

    /**
     * Returns the number of data points for each column of the data. NaNs are
     * excluded.
     *
     * @return the number of data points
     */
    public double[][] getN() {
        return new double[][]{summary[N].clone()};
    }

    /**
     * Returns the median value for each column of the data.
     *
     * @return the median
     */
    public double[][] getMedian() {
        return new double[][]{summary[MEDIAN].clone()};
    }

    /**
     * Returns the lower pecentile for each column of the data.
     *
     * @return the lower pecentile
     */
    public double[][] getLower() {
        return new double[][]{summary[LOWER].clone()};
    }

    /**
     * Returns the upper pecentile for each column of the data.
     *
     * @return the upper pecentile
     */
    public double[][] getUpper() {
        return new double[][]{summary[UPPER].clone()};
    }

    /**
     * Returns the lower pecentile used. The upper percentile is (100-lower
     * percentile)
     *
     * @return the pecentile
     */
    public double getPercentile() {
        return percent;
    }

    /**
     * Returns a copy of the intermediate array used in calculations.
     *
     * @return a double[][]
     */
    public double[][] getSummary() {
        return summary.clone();
    }

    /**
     * Returns true if bias correction is to be applied to the variance
     *
     * @return the correction flag
     */
    public boolean isVarianceCorrected() {
        return varianceCorrected;
    }

    /**
     * Sets bias correction mode for the variance (and standard deviation)
     *
     * @param flag
     */
    public void setVarianceCorrected(boolean flag) {
        varianceCorrected = flag;
    }

    /**
     * Returns true if bias correction is to be applied to the skew
     *
     * @return the correction flag
     */
    public boolean isSkewCorrected() {
        return skewCorrected;
    }

    /**
     * Sets bias correction mode for the skew
     *
     * @param flag
     */
    public void setSkewCorrected(boolean flag) {
        skewCorrected = flag;
    }

    /**
     * Returns true if bias correction is to be applied to the kurtosis
     *
     * @return the correction flag
     */
    public boolean isKurtosisCorrected() {
        return kurtosisCorrected;
    }

    /**
     * Sets bias correction mode for the kurtosis
     *
     * @param flag
     */
    public void setKurtosisCorrected(boolean flag) {
        kurtosisCorrected = flag;
    }

    /**
     * Called by the constructor.
     *
     * @param data
     * @return a double[][]
     */
    private double[][] createSummary(double[][] data) {

        if (percent < 0d || percent > 50d) {
            return new double[0][0];
        }

        double[][] tr = new double[data[0].length][data.length];

        // Pass 1: Search through rows of the input to find min/max and N
        // creating a tranposed copy on the way
        for (int k = 0; k < data.length; k++) {

            for (int kk = 0; kk < data[k].length; kk++) {
                if (!Double.isNaN(data[k][kk])) {
                    summary[SUMLOG][kk] += Math.log(data[k][kk]);
                    summary[MIN][kk] = Math.min(summary[MIN][kk], data[k][kk]);
                    summary[MAX][kk] = Math.max(summary[MAX][kk], data[k][kk]);
                    summary[N][kk]++;
                }
                // Create transposed data array - include NaNs
                tr[kk][k] = data[k][kk];
            }
        }

        // Find the sums of (x-xhat)^2 long-hand
        for (int m = 0; m < data[0].length; m++) {
            summary[SUM][m] = localSum(tr[m]);
            double mean = summary[SUM][m] / summary[N][m];
            double[] residuals = ArrayMath.sub(tr[m], mean);
            double[] raised = ArrayMath.pow(residuals, 2d);//.^2
            summary[SUMDELTA2][m] = localSum(raised);
            raised = ArrayMath.pow(residuals, 3d);//.^3
            summary[SUMDELTA3][m] = localSum(raised);
            raised = ArrayMath.pow(residuals, 4d);//.^4
            summary[SUMDELTA4][m] = localSum(raised);
        }

        for (int n = 0; n < tr.length; n++) {
            // Sort tr in-place: -Inf at start, +Inf then NaN at end
            Arrays.sort(tr[n]);
            // Find median and percentiles - use N from above and thus ignore
            // NaN entries
            summary[MEDIAN][n] = median(tr[n], (int) summary[N][n]);
            summary[LOWER][n] = percentile(tr[n], (int) summary[N][n], percent);
            summary[UPPER][n] = percentile(tr[n], (int) summary[N][n], 100 - percent);
        }

        for (int k = 0; k < summary.length; k++) {
            for (int kk = 0; kk < summary[k].length; kk++) {
                if (summary[N][kk] == 0) {
                    summary[SUM][kk] = Double.NaN;
                    summary[MIN][kk] = Double.NaN;
                    summary[MAX][kk] = Double.NaN;
                }
            }
        }

        return summary;

    }

    /**
     * Compensated sum
     *
     * @param in
     * @return a double
     */
    private static double localSum(double[] in) {
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

    private static double median(final double[] in, int N) {
        if (N == 0) {
            return Double.NaN;
        }
        if (N == 1) {
            return in[0];
        }
        if (N % 2 != 0) {
            return in[N / 2];
        } else {
            return (in[N / 2 - 1] + in[N / 2]) / 2;
        }
    }

    /**
     * Percentile from linear interpolation between nearest ranks.
     *
     * See <a
     * http://commons.apache.org/math/apidocs/org/apache/commons/math3/...
     * ...stat/descriptive/rank/Percentile.html"> here</a> for details.
     *
     *
     */
    private static double percentile(final double[] in, int N, int percentile) {
        if (N == 1) {
            return in[0];
        }
        double pos = percentile * (N + 1) / 100d;
        if (pos < 1) {
            return in[0];
        } else if (pos >= N) {
            return in[N - 1];
        } else {
            double f = Math.floor(pos);
            double d = pos - f;
            int idx = (int) f - 1;
            double result = in[idx] + d * (in[idx + 1] - in[idx]);
            if (Double.isNaN(result)) {
                if (Double.isInfinite(in[idx])) {
                    return in[idx];
                } else if (Double.isInfinite(in[idx + 1])) {
                    return in[idx + 1];
                }
            }
            return result;
        }
    }

//    public static double BVar(double[] in) {
//        BigDecimal mean = BigDecimal.ZERO;
//        for (int k = 0; k < in.length; k++) {
//            mean = mean.add(new BigDecimal(in[k]));
//        }
//        mean = mean.divide(new BigDecimal(in.length));
//        BigDecimal var = BigDecimal.ZERO;
//        BigDecimal diff;
//        for (int k = 0; k < in.length; k++) {
//            diff = new BigDecimal(in[k]).subtract(mean);
//            var = var.add(diff.multiply(diff));
//        }
//        BigDecimal factor = new BigDecimal((double) in.length - 1);
//        return var.divide(factor, MathContext.DECIMAL128).doubleValue();
//    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Formatter f = new Formatter(s);
        f.format("Summary statistics:%n");
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t    [" + Integer.toString(k) + "]\t");
        }
        f.format("%nMean:");
        double[][] mean = getMean();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", mean[0][k]);
        }
        f.format("%nSD:");
        if (varianceCorrected) {
            f.format("%3s", "[+]");
        }
        double[][] sd = getSD();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", sd[0][k]);
        }
        f.format("%nSkew:");
        if (skewCorrected) {
            f.format("[+]");
        }
        double[][] skew = getSkew();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", skew[0][k]);
        }
        f.format("%nK'osis:");
        if (kurtosisCorrected) {
            f.format("[+]");
        }
        double[][] kurtosis = getKurtosis();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", kurtosis[0][k]);
        }
        f.format("%nMin:");
        double[][] min = getMinimum();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", min[0][k]);
        }
        f.format("%nMax:");
        double[][] max = getMaximum();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", max[0][k]);
        }
        f.format("%nN:");
        double[][] n = getN();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10d", (int) n[0][k]);
        }
        f.format("%nPercentiles:");
        f.format("%20s", "%nMedian:");
        double[][] median = getMedian();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", median[0][k]);
        }
        f.format("%n%2d%%:", percent);
        double[][] lower = getLower();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", lower[0][k]);
        }
        f.format("%n%2d%%:", 100 - percent);
        double[][] upper = getUpper();
        for (int k = 0; k < summary[0].length; k++) {
            f.format("\t%10.6f", upper[0][k]);
        }
        f.format("%n%n  [+] indicates bias correction applied");
        return f.toString();
    }
}
