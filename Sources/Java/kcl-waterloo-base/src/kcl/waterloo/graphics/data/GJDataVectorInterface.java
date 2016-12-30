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
import java.util.LinkedHashMap;
import java.util.Map;

import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.observable.GJObservableInterface;

/**
 * The <code>GJDataVectorInterface</code> interface defines the methods that
 * must be implemented by classes representing the x and y data for a 2D plot.
 *
 * <p>
 * Each {@code GJDataVectorInterface}supports a DataBuffer of class <b>T</b>
 * that contains the data for the x <i>or</i> y coordinates</p>
 *
 * <p>
 * Data will be supplied to the paint methods of individual plots via the
 * {@code getDataValues</>} method. This method returns data as a double
 * precision vector after transforming the data via a transform set in the host
 * graph. Thus all plots and data in a single view will share the same
 * transform. A layered view, with multiple overlapping graphs can be used to
 * superimpose plots using different transforms</p>
 *
 * <p>
 * A skeletal implementation of the {@code GJDataVectorInterface}is supplied in
 * the {@code GJAbstractDataVector} class.
 *
 *
 * @param <T> type of DataBuffer used to store the data.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJDataVectorInterface<T> extends GJObservableInterface {

    public static enum AXIS {

        NONE, XAXIS, YAXIS, ZAXIS, COLOR
    }

    /**
     * Returns the transform for this data set as specified by the
     * {@code GJDataTransformInterface}.
     *
     * The transform is recovered from the host graph via the associated plot
     * object. When the plot is not associated with a graph, the default
     * NOPTransform instance will be returned.
     *
     * @return the specified data transform
     */
//    @Deprecated
//    public GJDataTransformInterface getTransform();
    /**
     * Determines if this data set is categorical i.e. has that categories is
     * non-null.
     *
     * @return true if Categories != null
     */
    public boolean isCategorical();

    /**
     * Returns the LinkedHasMap of category settings. See
     * {@code GJAbstractDataVector#categories categories} in
     * {@code GJAbstractDataVector}.
     *
     * @return a LinkedHashMap<Double, Category>
     */
    public LinkedHashMap<Double, Category> getCategories();

    /**
     * Returns the category associated with the specified double value.
     *
     * @param val
     * @return a Category
     */
    public Category getCategory(double val);

    /**
     * Associates a {@code Category} containing {code String} s with the
     * specified {@code double} value.
     *
     * @param val
     * @param s
     */
    public void setCategory(double val, String s);

    /**
     * Sets the LinkedHashMap containing the categories.
     *
     * @param cat a LinkedHashMap<Double, Category> map.
     */
    public void setCategories(LinkedHashMap<Double, Category> cat);

    /**
     * Returns the data as an IEEE double precision array transformed by the
     * class specified by {@code getTranform}
     *
     * @param transform
     * @return a double[]
     */
    public double[] getDataValues(GJDataTransformInterface transform);

    /**
     * Returns the data as an IEEE double precision array <em>without</em>
     * applying a transform
     *
     * @return a double[]
     */
    public double[] getRawDataValues();

    /**
     * Returns the DataBuffer.
     *
     * @return DataBuffer of type {@code T}
     */
    public T getDataBuffer();

    /**
     * Sets the DataBuffer used to store the data. The buffer will be copied by
     * reference.
     *
     * @param o the object array or wrapper.
     */
    public void setDataBuffer(Object o);

    /**
     * Length of the vector in the DataBuffer
     *
     * @return the length of the vector
     */
    public int getDimension();

    /**
     *
     * @return the class of the DataBuffer
     */
    public Class getBufferClass();

    /**
     * Sets the vector in the DataBuffer to the <italic>values</italic> in the
     * input. Data may be cast and lose precision in the copy process.
     *
     * This method should fire a PropertyChangeEvent when the buffer data are
     * changed
     *
     * @param arr
     */
    public void setDataBufferData(double[] arr);

    /**
     * Sets the vector in the DataBuffer to the <italic>values</italic> in the
     * input. Data may be cast and lose precision in the copy process.
     *
     * This method should fire a PropertyChangeEvent when the buffer data are
     * changed
     *
     * @param arr
     */
    public void setDataBufferData(int[] arr);

    /**
     * Sets the vector in the DataBuffer to the <italic>values</italic> in the
     * input. Data may be cast and lose precision in the copy process.
     *
     * This method should fire a PropertyChangeEvent when the buffer data are
     * changed
     *
     * @param arr
     */
    public void setDataBufferData(AbstractList<? extends Number> arr);

    /**
     * Sets the vector in the DataBuffer to a series incrementing by 1 for each
     * element. <code>m</code> must be a Map with a single key, which determines
     * the start value of the series while the value corresponding to the key
     * represents the upper limit of the series. TODO: Should this be removed??
     *
     * This method should fire a PropertyChangeEvent when the buffer data are
     * changed
     *
     * @param m a Map of size equal to 1.
     */
    public void setDataBufferData(Map m);

    /**
     * Sets the vector in the DataBuffer to null
     *
     * This method should fire a PropertyChangeEvent
     *
     */
    public void clearDataBufferData();

    /**
     * Returns a value from the DataBuffer
     *
     * @param index of the required entry
     * @return the value a a double
     */
    public Object getEntry(int index);

    /**
     * Sets a value in the DataBuffer
     *
     * This method should fire a PropertyChangeEvent when the buffer data are
     * changed
     *
     * @param index
     * @param val the new value as double
     */
    public void setEntry(int index, double val);

//    /** 
//     * @return 
//     */
//    public GJPlotInterface getParentPlot();
//        
//    /**
//     * This method is called internally by Waterloo. It is available for use in
//     * de-serialization of XML and will not normally be called by users. 
//     * 
//     * @param p 
//     */
//    public void setParentPlot(GJPlotInterface p);
//    public GJDataVectorInterface.AXIS getAxis();
//
//    /**
//     * This method is called internally by Waterloo. It is available for use in
//     * de-serialization of XML and will not normally be called by users. 
//     * 
//     * @param axis a GJDataVectorInterface.AXIS value
//     */
//    public void setAxis(GJDataVectorInterface.AXIS axis);
    public String getName();

    public void setName(String s);
}
