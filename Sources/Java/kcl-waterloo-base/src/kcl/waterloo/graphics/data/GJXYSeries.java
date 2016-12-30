/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.data;

import java.util.ArrayList;
import java.util.Arrays;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.math.ArrayUtils;
import kcl.waterloo.math.geom.Matrix;

/**
 * Class for representing paired x,y data as ArrayList<Double> lists of equal
 * length.
 *
 * The x, y data are immutable - getX() and getY() methods return copies.
 *
 * There is no public null constructor. To serialize this class to XML with the
 * beans encoder, define a custom persistence delegate, e.g.:
 *
 * <@code xmlencoder.setPersistenceDelegate(GJXYSeries.class, new
 * DefaultPersistenceDelegate(new String[]{"x", "y"}));>
 *
 * @author ML
 */
public class GJXYSeries {

    private final ArrayList<Double> x;
    private final ArrayList<Double> y;

    /**
     * Set x and y. These are copied by reference so the capacity to maintain an
     * external reference to the ArrayLists is preserved if that is needed. To
     * avoid that, provide a temporary copy of the ArrayLists as input. Asserts
     * ArrayIndexOutOfBoundsException if x and y are not the same size.
     *
     * @param x ArrayList<Double> copied by reference from the input
     * @param y ArrayList<Double> copied by reference from the input
     */
    public GJXYSeries(ArrayList<Double> x, ArrayList<Double> y) {
        assertSameLength(x, y);
        this.x = x;
        this.y = y;
    }

    public GJXYSeries(ArrayList<ArrayList<Double>> xy) {
        x = new ArrayList<Double>(xy.size());
        y = new ArrayList<Double>(xy.size());
        for (ArrayList<Double> pair : xy) {
            x.add(pair.get(0));
            y.add(pair.get(1));
        }
    }

    /**
     * Creates an instance by forming ArrayLists from the inputs.
     *
     * @param x Double[]
     * @param y Double[]
     */
    public GJXYSeries(Double[] x, Double[] y) {
        this(new ArrayList<Double>(Arrays.asList(x)), new ArrayList<Double>(Arrays.asList(y)));
    }

    /**
     * Creates an instance by forming ArrayLists from the inputs.
     *
     * @param x double[]
     * @param y double[]
     */
    public GJXYSeries(double[] x, double[] y) {
        this(Matrix.toDouble(x), Matrix.toDouble(y));
    }

    /**
     * Size of the x & y vectors.
     *
     * @return the size
     */
    public int size() {
        return x.size();
    }

    /**
     * Returns a copy of the x-data
     *
     * @return the x
     */
    public ArrayList<Double> getX() {
        return new ArrayList<Double>(x);
    }

    /**
     * Returns a copy of the y data
     *
     * @return the y
     */
    public ArrayList<Double> getY() {
        return new ArrayList<Double>(y);
    }

    public double getMinX() {
        return ArrayMath.min((ArrayUtils.asDouble(x)));
    }

    public double getMaxX() {
        return ArrayMath.min((ArrayUtils.asDouble(x)));
    }

    public double getMinY() {
        return ArrayMath.min((ArrayUtils.asDouble(y)));
    }

    public double getMaxY() {
        return ArrayMath.min((ArrayUtils.asDouble(y)));
    }

    public double getCenterX() {
        return (getMaxX() - getMinX()) / 2;
    }

    public double getCenterY() {
        return (getMaxY() - getMinY()) / 2;
    }

    public static void assertSameLength(ArrayList<Double> x, ArrayList<Double> y) {
        if (x.size() != y.size()) {
            throw new ArrayIndexOutOfBoundsException(
                    "Vectors must be the same length");
        }
    }
}
