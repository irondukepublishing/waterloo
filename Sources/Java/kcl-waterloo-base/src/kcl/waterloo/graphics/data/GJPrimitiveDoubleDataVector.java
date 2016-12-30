 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
package kcl.waterloo.graphics.data;

import java.util.AbstractList;

import kcl.waterloo.graphics.transforms.GJDataTransformInterface;

/**
 * @author Malcolm Lidierth <malcolm.lidierth at kcl.ac.uk>
 */
public final class GJPrimitiveDoubleDataVector
        extends GJAbstractDataVector<PrimitiveDoubleBuffer> {

    private static final long serialVersionUID = 1L;

//    public GJPrimitiveDoubleDataVector(GJPlotInterface p, GJDataVectorInterface.AXIS axis, PrimitiveDoubleBuffer b) {
//        super(p, axis, b);
//    }
    /**
     * Used for JUnit tests
     */
    public GJPrimitiveDoubleDataVector() {
        super(new PrimitiveDoubleBuffer(new double[0]));
    }

    /**
     * {@inheritDoc}
     *
     * @param v a double[] which will be deep copied to a new ArrayRealVector in
     * the data buffer
     */
    @Override
    public final void setDataBufferData(double[] v) {
        if (v != null) {
            setDataBuffer(new PrimitiveDoubleBuffer(v));
        } else {
            clearDataBufferData();
        }
    }

    /**
     * Need this for de-serialization
     *
     * @return a double[]
     */
    public final double[] getDataBufferData() {
        return getDataBuffer().getDataRef();
    }

    /**
     * {@inheritDoc}
     *
     * @param v an int[] which will be cast to double[] and copied to a new
     * PrimitiveDoubleBuffer in the data buffer
     */
    @Override
    public final void setDataBufferData(int[] v) {
        if (v != null) {
            double[] v2 = new double[v.length];
            for (int k = 0; k < v.length; k++) {
                v2[k] = v[k];
            }
            setDataBuffer(new PrimitiveDoubleBuffer(v2, false));
        } else {
            clearDataBufferData();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v a List whose data will be copied, cast to double and copied to a
     * new <code>PrimitiveDoubleBuffer</code> in the data buffer
     */
    @Override
    public final void setDataBufferData(AbstractList<? extends Number> v) {
        double[] v2 = new double[v.size()];
        for (int k = 0; k < v.size(); k++) {
            v2[k] = v.get(k).doubleValue();
        }
        setDataBuffer(new PrimitiveDoubleBuffer(v2, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getDataValues(GJDataTransformInterface transform) {
        if (getDataBuffer() == null) {
            return new double[0];
        } else {
            double[] arr = getDataBuffer().getDataRef().clone();
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
            return getDataBuffer().getDataRef().clone();
        }
    }

    @Override
    public final void setEntry(int index, double val) {
        double old = getDataBuffer().getEntry(index);
        getDataBuffer().setEntry(index, val);
        getPCS().fireIndexedPropertyChange("dataBuffer", index, old, getDataBuffer().getEntry(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getDimension() {
        if (getDataBuffer() == null) {
            return 0;
        }
        return getDataBuffer().getDimension();
    }
}
