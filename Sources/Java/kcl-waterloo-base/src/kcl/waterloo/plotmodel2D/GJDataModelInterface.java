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

import java.util.AbstractList;
import java.util.ArrayList;
import kcl.waterloo.graphics.data.GJDataVectorInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * @param <T>
 * @param <U>
 */
public interface GJDataModelInterface<T, U> {

    public GJDataModel getDataModel();

    public void setDataModel(GJDataModel model);

    public void setData(GJDataVectorInterface o1, GJDataVectorInterface o2);

    public void setXData(GJDataVectorInterface<?> o1);

    public void setYData(GJDataVectorInterface<?> o1);

//    public void setData(double[] x, double[] y);
//
//    public void setData(int[] x, int[] y);
//
//    public void setData(AbstractList<? extends Number> x, AbstractList<? extends Number> y);
//
//    public void setData(Map x, Map y);
//
//    public void setData(double[] x, int[] y);
//
//    public void setData(double[] x, AbstractList<? extends Number> y);
//
//    public void setData(double[] x, Map y);
//
//    public void setData(int[] x, double[] y);
//
//    public void setData(int[] x, AbstractList<? extends Number> y);
//
//    public void setData(int[] x, Map y);
//
//    public void setData(AbstractList<? extends Number> x, double[] y);
//
//    public void setData(AbstractList<? extends Number> x, int[] y);
//
//    public void setData(AbstractList<? extends Number> x, Map y);
//
//    public void setData(Map x, double[] y);
//
//    public void setData(Map x, int[] y);
//
//    public void setData(Map x, AbstractList<? extends Number> y);
    public GJDataVectorInterface<?> getXData();

    public void setXData(AbstractList<? extends Number> x);

    public void setXData(Object x);

    public void setXData(double[] x);

    public void setXData(int[] x);

    public GJDataVectorInterface<?> getYData();

    public void setYData(AbstractList<? extends Number> y);

    public void setYData(double[] y);

    public void setYData(int[] y);

    public void setYData(Object y);

    public void setAlpha(float alpha);

    public double[] getXDataValues();

    public double[] getYDataValues();

    public boolean isMultiplexible();

    public boolean isMultiplexed();

    public int getMultiplexLength();

    public GJDataTransformInterface getXTransform();

    public GJDataTransformInterface getYTransform();

    //public int getDimension();
    public T getDataRange();

    public T getVisualRange();

    public void setExtraData0(double[] val);

    public void setExtraData1(double[] val);

    public void setExtraData2(double[] val);

    public void setExtraData3(double[] val);

    public ArrayList<String> getName();

    public void setName(ArrayList<String> s);
}
