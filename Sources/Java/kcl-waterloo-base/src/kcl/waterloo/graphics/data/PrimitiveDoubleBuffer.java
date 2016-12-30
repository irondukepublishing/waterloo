 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.data;

/**
 * Wrapper class for a double[].
 *
 * This class permits double arrays contained as a property in the wrapper to be
 * passed by reference - useful in environments that support that for Java
 * objects but not for primitive data types e.g. MATLAB
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class PrimitiveDoubleBuffer {

    /**
     * Data vector.
     */
    double[] data = null;

    /**
     * Constructs a new instance with a zero length vector.
     */
    public PrimitiveDoubleBuffer() {
    }

    /**
     * Constructs a new instance with a vector of length sz.
     *
     * @param sz
     */
    public PrimitiveDoubleBuffer(int sz) {
        data = new double[sz];
    }

    /**
     * Constructs a new instance copying the input.
     *
     * @param arr
     */
    public PrimitiveDoubleBuffer(double[] arr) {
        this(arr, true);
    }

//        /**
//     * Constructs a new instance copying the input.
//     *
//     * @param arr
//     */
//    public PrimitiveDoubleBuffer(Double[] arr) {
//        this(new double[arr.length],false);
//        double[] arr2=getDataRef();
//        for (int k=0; k<arr.length; k++){
//            arr2[k]=arr[k];
//        }
//    }
    /**
     * Constructs a new instance. If copyFlag is true the input is copied, if
     * false is is referenced in the wrapper.
     *
     * @param arr
     * @param copyFlag
     */
    public PrimitiveDoubleBuffer(double[] arr, boolean copyFlag) {
        if (arr != null) {
            data = copyFlag ? arr.clone() : arr;
        } else {
            data = null;
        }
    }

    /**
     * Returns a copy of the wrapped double[] vector
     *
     * @return the data
     */
    public double[] getData() {
        return data.clone();
    }

    /**
     * Returns a reference to the wrapped data vector.
     *
     * Note: This exposes a reference to the data vector to external code.
     *
     * @return a reference to the data vector.
     */
    public final double[] getDataRef() {
        return data;
    }

    /**
     * Replaces the current wrapped double[] with a copy of the input.
     *
     * @param arr
     */
    public final void setData(double[] arr) {
        if (arr != null) {
            data = arr.clone();
        } else {
            data = null;
        }
    }

    /**
     * Replaces the current wrapped double[] with a reference to the input.
     *
     * NOTE: As the reference is external, this exposes {@code data} as an
     * external mutable array.
     *
     * @param arr
     */
    public final void setDataRef(double[] arr) {
        data = arr;
    }

    /**
     * Returns the entry at specified index
     *
     * @param index
     * @return a double
     */
    public double getEntry(int index) {
        return data[index];
    }

    /**
     * Sets the entry at the specified index
     *
     * @param index
     * @param val
     */
    public void setEntry(int index, double val) {
        data[index] = val;
    }

    /**
     * Returns the length of the wrapped double[]
     *
     * @return an int
     */
    public int getDimension() {
        return data.length;
    }
}
