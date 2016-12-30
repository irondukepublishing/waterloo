 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
package kcl.waterloo.graphics.data;

import java.util.AbstractList;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJDoubleDataVector extends GJAbstractDataVector<Double[]> {

    private static final long serialVersionUID = 1L;

//    /**
//     *
//     * @param p
//     * @param axis
//     */
//    public GJDoubleDataVector(GJPlotInterface p, GJDataVectorInterface.AXIS axis) {
//        super(p, axis, new Double[0]);
//    }
    /**
     * Used for JUnit tests/XML
     */
    public GJDoubleDataVector() {
        super(new Double[0]);
    }

    /**
     * {@inheritDoc}
     *
     * @param v a double[] which will be deep copied to a Double[] as the data
     * buffer
     */
    @Override
    public final void setDataBufferData(double[] v) {
        if (v != null) {
            Double[] buffer = new Double[v.length];
            for (int k = 0; k < v.length; k++) {
                buffer[k] = v[k];
            }
            setDataBuffer(buffer);
        } else {
            clearDataBufferData();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v an int[] which will be deep copied to a Double[] as the data
     * buffer
     */
    @Override
    public final void setDataBufferData(int[] v) {
        if (v != null) {
            double[] v2 = new double[v.length];
            for (int k = 0; k < v.length; k++) {
                v2[k] = v[k];
            }
            setDataBufferData(v2);
        } else {
            clearDataBufferData();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v an AbstractList<? extends Number>, values from which which will
     * be deep copied to a Double[] as the data buffer. Precision will be lost
     * if the AbstractList contains data such a BigDecimal or Long.
     */
    @Override
    public final void setDataBufferData(AbstractList<? extends Number> v) {
        Double[] data = new Double[v.size()];
        for (int k = 0; k < v.size(); k++) {
            data[k] = new Double(v.get(k).toString());
        }
        setDataBuffer(data);
    }

    @Override
    public final void setEntry(int index, double val) {
        setEntry(index, Double.valueOf(val));
    }

    public final void setEntry(int index, Double val) {
        Double old = getDataBuffer()[index];
        getDataBuffer()[index] = val;
        getPCS().fireIndexedPropertyChange("dataBuffer", index, old, getDataBuffer()[index]);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public final synchronized Double[] getDataBuffer() {
//        return dataBuffer;
//    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getDataValues(GJDataTransformInterface transform) {
        if (getDataBuffer() == null) {
            return new double[0];
        } else {
            double[] arr = new double[getDataBuffer().length];
            for (int k = 0; k < arr.length; k++) {
                arr[k] = getDataBuffer()[k].doubleValue();
            }
            return transform.getData(arr);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getRawDataValues() {
        if (getDataBuffer() == null) {
            return new double[0];
        } else {
            double[] arr = new double[getDataBuffer().length];
            for (int k = 0; k < getDataBuffer().length; k++) {
                arr[k] = getDataBuffer()[k].doubleValue();
            }
            return arr;
        }
    }

//    @Override
//    public void setRawDataValues(double[] v){
//        setDataBufferData(v);
//    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final int getDimension() {
        if (getDataBuffer() == null) {
            return 0;
        }
        return getDataBuffer().length;
    }
}
