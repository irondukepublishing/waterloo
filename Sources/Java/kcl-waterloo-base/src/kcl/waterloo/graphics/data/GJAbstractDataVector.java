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

import java.beans.PropertyChangeEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import kcl.waterloo.graphics.transforms.NOPTransform;
import kcl.waterloo.observable.GJAbstractObservable;

/**
 * Abstract implementaion of the <code>GJDataVectorInterface</code> interface.
 * For details see the doc for the <code>GJDataVectorInterface</code> interface.
 *
 * <code>GJDataVectorInterfaces</code> are used for the XData and YData fields
 * in the plot data model.
 *
 * @see GJDataVectorInterface
 *
 * @param <T>
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAbstractDataVector<T> extends GJAbstractObservable
        implements GJDataVectorInterface<T> {

    /**
     * The parent plot is the plot that this data vector is associated with. The
     * only use of this is to retrieve the data transform from the host graph.
     * Note that while data vectors may not be copied between plots, plots may
     * be copied between graphs.
     */
//    @Deprecated
//    private GJPlotInterface parentPlot;
    /**
     * The axis that this data is associated with.
     */
//    @Deprecated
//    private GJDataVectorInterface.AXIS axis;
    /**
     * The underlying data vector. The class of the vector is set by the
     * concrete implementations of this abstract class.
     */
    private T dataBuffer = null;
    /**
     * Associates a string with a specific Double value. When the associated
     * plot is drawn, these can be used to replace the Double values for the
     * purpose of labeling the relevant axis. When {@code categories} is
     * non-null, isCategorical returns true for that
     * {@code GJDataVectorInterface} instance.
     *
     * Strings are stored in a {@code Category} instance which also specifies an
     * xOffset, yOffset and rotation for rendering the strings.
     */
    protected LinkedHashMap<Double, Category> categories = null;
    private String name = "";

    GJAbstractDataVector(T dataBuffer) {
        this.dataBuffer = dataBuffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void clearDataBufferData() {
        T old = getDataBuffer();
        setDataBuffer(null);
        getPCS().firePropertyChange("dataBuffer", old, null);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public final synchronized void setDataBuffer(Object buffer) {
        Object old = getDataBuffer();
        dataBuffer = (T) buffer;
        getPCS().firePropertyChange("dataBuffer", old, getDataBuffer());
    }

    @Override
    public final synchronized T getDataBuffer() {
        return dataBuffer;
    }

    @Override
    public final Object getEntry(int index) {
        if (getDataBuffer() instanceof PrimitiveDoubleBuffer) {
            PrimitiveDoubleBuffer v = (PrimitiveDoubleBuffer) getDataBuffer();
            return v.data[index];

        } else {
            Object[] obj = (Object[]) getDataBuffer();
            return obj[index];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCategorical() {
        return (categories != null);
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @param val
     */
    @Override
    public void setCategory(double index, String val) {
        Category old;
        if (categories == null) {
            categories = new LinkedHashMap<Double, Category>();
            old = Category.getEmptyInstance();
        } else {
            old = categories.get(index);
        }
        categories.put(index, new Category(val));
        getPCS().firePropertyChange("categories", old, categories);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<Double, Category> getCategories() {
        return categories;
    }

    @Override
    public Category getCategory(double val) {
        return categories.get(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCategories(LinkedHashMap<Double, Category> cat) {
        LinkedHashMap<Double, Category> old = categories;
        categories = cat;
        getPCS().firePropertyChange("categories", old, categories);
    }

    public void setCategories(String... cat) {
        LinkedHashMap<Double, Category> map = new LinkedHashMap<Double, Category>();
        double[] values = getDataValues(NOPTransform.getInstance());
        if (cat == null) {
            LinkedHashMap<Double, Category> old = categories;
            categories = null;
            getPCS().firePropertyChange("categories", old, categories);
        } else {
            for (int k = 0; k < cat.length; k++) {
                map.put(Double.valueOf(values[k]), new Category(cat[k]));
            }
            setCategories(map);
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public final Class getBufferClass() {
        return getDataBuffer().getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataBufferData(Map m) {
        T old = getDataBuffer();
        if (m.size() == 1) {
            Object[] start = m.keySet().toArray();
            Object[] stop = m.values().toArray();
            int s1 = Integer.parseInt(start[0].toString());
            int s2 = Integer.parseInt(stop[0].toString());
            int[] v = new int[s2 - s1 + 1];
            for (int k = 0; k < v.length; k++) {
                v[k] = s1 + k;
            }
            setDataBufferData(v);
        }
        getPCS().firePropertyChange("dataBuffer", old, getDataBuffer());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String s) {
        name = s;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }
}
