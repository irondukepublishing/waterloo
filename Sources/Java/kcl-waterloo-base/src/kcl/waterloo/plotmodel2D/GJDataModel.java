 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.plotmodel2D;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.graphics.data.GJDataVectorInterface;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.observable.GJAbstractObservable;

/**
 * Data model used by all plot classes implementing the {@code GJPlotInterface}.
 *
 * Security: Note that data within the model is exposed by reference via the
 * setter and getter methods.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJDataModel extends GJAbstractObservable {

    /**
     * Vector of values of X for display. Values are plotted in sequence /
     * together with the corresponding element from yData and/or Marker
     */
    private GJDataVectorInterface<?> xData = null;
    /**
     * Vector of Y-values corresponding element-by-element to the values in
     * xData. yData must have the same length as xData. Missing values can be
     * set to NaN.
     */
    private GJDataVectorInterface<?> yData = null;
    /**
     * A double[] array. Use is plot type specific.
     */
    private double[] extraData0 = new double[0];//EAST, RIGHT
    /**
     * A double[] array. Use is plot type specific.
     */
    private double[] extraData1 = new double[0];//NORTH, UPPER
    /**
     * A double[] array. Use is plot type specific.
     */
    private double[] extraData2 = new double[0];//WEST, LEFT
    /**
     * A double[] array. Use is plot type specific.
     */
    private double[] extraData3 = new double[0];//SOUTH, LOWER
    /**
     * A java.lang.Object. Use is plot type specific.
     */
    private Object extraObject;

    private ArrayList<GJPlotInterface> plotList = new ArrayList<GJPlotInterface>();

    /**
     * Constructor for XML de-serialization only. Do not use.
     */
    public GJDataModel() {
    }

    /**
     *
     * @param clzz
     */
    private GJDataModel(Class clzz) {
        init(clzz);
    }

    /**
     * Factory method for construction.
     *
     * The class of the underlying XData and YData fields will be the class
     * returned by calling {@code GJDefaults.getDataClass()}. Installs the model
     * as a listener to itself.
     *
     * @return the GJDataModel
     */
    public static GJDataModel createInstance() {
        return new GJDataModel(GJDefaults.getDataClass());
    }

    /**
     * Factory method for construction.
     *
     * The class of the underlying XData and YData fields will be the class
     * returned by calling {@code GJDefaults.getDataClass()}.
     *
     * Installs the model as a listener to itself.
     *
     * Installs the supplied plot as a listener to the model.
     *
     * @param p the plot this data model will be associated with.
     * @return the GJDataModel
     */
    @Deprecated
    public static GJDataModel createInstance(GJPlotInterface p) {
        GJDataModel model = new GJDataModel(GJDefaults.getDataClass());
        model.addPropertyChangeListener(model);
        if (p != null) {
            model.getXData().addPropertyChangeListener(p);
            model.getYData().addPropertyChangeListener(p);
            model.addPropertyChangeListener(p);
        }
        return model;
    }

    private void init(Class<?> clzz) {
        if (clzz != null) {
            if (GJDataVectorInterface.class.isAssignableFrom(clzz)) {
                Constructor constructor;
                try {
                    constructor = clzz.getConstructor(new Class[]{});
                } catch (NoSuchMethodException ex) {
                    return;
                } catch (SecurityException ex) {
                    return;
                }
                try {
                    GJDataVectorInterface<?> instance = (GJDataVectorInterface<?>) constructor.newInstance();
                    xData = instance;
                    instance = (GJDataVectorInterface<?>) constructor.newInstance();
                    yData = instance;
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (InvocationTargetException ex) {
                }
            } else {
            }
        }
    }

    /**
     * @return the xData object
     */
    public final GJDataVectorInterface<?> getXData() {
        return xData;
    }

    /**
     * @return the yData object
     */
    public final GJDataVectorInterface<?> getYData() {
        return yData;
    }

    /**
     * @return the extraData0
     */
    public final double[] getExtraData0() {
        return extraData0;
    }

    /**
     * @return the extraData1
     */
    public final double[] getExtraData1() {
        return extraData1;
    }

    /**
     * @return the extraData2
     */
    public final double[] getExtraData2() {
        return extraData2;
    }

    /**
     * @return the extraData3
     */
    public final double[] getExtraData3() {
        return extraData3;
    }

    public final Object getExtraObject() {
        return extraObject;
    }

    public final void setExtraObject(Object o) {
        Object old = extraObject;
        extraObject = o;
        getPCS().firePropertyChange("extraObject", old, o);
    }


    /**
     * @param extraData0 the extraData0 to set
     */
    public final void setExtraData0(double[] extraData0) {
        double[] old = this.extraData0;
        this.extraData0 = extraData0;
        getPCS().firePropertyChange("extraData0", old, extraData0);
    }

    /**
     * @param extraData1 the extraData1 to set
     */
    public final void setExtraData1(double[] extraData1) {
        double[] old = this.extraData1;
        this.extraData1 = extraData1;
        getPCS().firePropertyChange("extraData1", old, extraData0);
    }

    /**
     * @param extraData2 the extraData2 to set
     */
    public final void setExtraData2(double[] extraData2) {
        double[] old = this.extraData2;
        this.extraData2 = extraData2;
        getPCS().firePropertyChange("extraData2", old, extraData0);
    }

    /**
     * @param extraData3 the extraData3 to set
     */
    public final void setExtraData3(double[] extraData3) {
        double[] old = this.extraData3;
        this.extraData3 = extraData3;
        getPCS().firePropertyChange("extraData3", old, extraData0);
    }

    /**
     * @param xData
     */
    public final void setXData(GJDataVectorInterface<?> xData) {
        GJDataVectorInterface<?> old = this.xData;
        this.xData = xData;
        getPCS().firePropertyChange("xData", old, xData);
    }

    /**
     * @param yData
     */
    public final void setYData(GJDataVectorInterface<?> yData) {
        GJDataVectorInterface<?> old = this.yData;
        this.yData = yData;
        getPCS().firePropertyChange("yData", old, yData);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }

    /**
     * @return the plotList
     */
    @Deprecated
    public ArrayList<GJPlotInterface> getPlotList() {
        return plotList;
    }

    /**
     * @param plotList the plotList to set
     */
    @Deprecated
    public void setPlotList(ArrayList<GJPlotInterface> plotList) {
        this.plotList = plotList;
    }

}
