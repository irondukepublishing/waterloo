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

import java.math.BigDecimal;
import java.util.AbstractList;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJBigDecimalDataVector extends GJAbstractDataVector<BigDecimal[]> {

    private static final long serialVersionUID = 1L;

//    /**
//     *
//     * @param p
//     * @param axis
//     */
//    public GJBigDecimalDataVector(GJPlotInterface p, GJDataVectorInterface.AXIS axis) {
//        super(p, axis, new BigDecimal[0]);
//    }
    /**
     * Used for JUnit tests
     */
    public GJBigDecimalDataVector() {
        super(new BigDecimal[0]);
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
            BigDecimal[] buffer = new BigDecimal[v.length];
            for (int k = 0; k < v.length; k++) {
                buffer[k] = new BigDecimal(v[k]);
            }
            setDataBuffer(buffer);
        } else {
            clearDataBufferData();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v an int[] which will be copied to a BigDecimal[] as the data
     * buffer
     */
    @Override
    public final void setDataBufferData(int[] v) {
        if (v != null) {
            BigDecimal[] v2 = new BigDecimal[v.length];
            for (int k = 0; k < v.length; k++) {
                v2[k] = new BigDecimal(v[k]);
            }
            setDataBuffer(v2);
        } else {
            clearDataBufferData();
        }
    }

    public final void setDataBufferData(BigDecimal[] v) {
        if (v != null) {
            BigDecimal[] v2 = new BigDecimal[v.length];
            for (int k = 0; k < v.length; k++) {
                v2[k] = new BigDecimal(v[k].toString());
            }
            setDataBuffer(v2);
        } else {
            clearDataBufferData();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v an AbstractList<? extends Number>, values from which which will
     * be deep copied to a BigDecimal[] as the data buffer.
     */
    @Override
    public final void setDataBufferData(AbstractList<? extends Number> v) {
        BigDecimal[] data = new BigDecimal[v.size()];
        for (int k = 0; k < v.size(); k++) {
            data[k] = new BigDecimal(v.get(k).toString());
        }
        setDataBuffer(data);
    }

    @Override
    public final void setEntry(int index, double val) {
        setEntry(index, new BigDecimal(val));
    }

    public final void setEntry(int index, BigDecimal val) {
        BigDecimal old = getDataBuffer()[index];
        getDataBuffer()[index] = val;
        getPCS().fireIndexedPropertyChange("dataBuffer", index, old, getDataBuffer()[index]);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public final synchronized BigDecimal[] getDataBuffer() {
//        return dataBuffer;
//    }
    /**
     * Override superclass {@code setDataBuffer} method. Needed for XMLCoder -
     * BigDecimal not supported otherwise.
     *
     * @param buffer BigDecimal[] with the data
     */
    public final synchronized void setDataBuffer(BigDecimal[] buffer) {
        super.setDataBuffer(buffer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getDataValues(GJDataTransformInterface transform) {
        if (getDataBuffer() == null) {
            return new double[0];
        } else {
            BigDecimal[] buffer = getDataBuffer();
            double[] arr = new double[buffer.length];
            for (int k = 0; k < arr.length; k++) {
                arr[k] = buffer[k].doubleValue();
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
            BigDecimal[] buffer = getDataBuffer();
            double[] arr = new double[buffer.length];
            for (int k = 0; k < arr.length; k++) {
                arr[k] = buffer[k].doubleValue();
            }
            return arr;
        }
    }

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
