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

import java.util.ArrayList;

/**
 * A generic interface defining the visual properties of plot objects.
 *
 * @param <T> The marker class
 * @param <U> The color or paint class
 * @param <V> The stroke class for drawing lines
 * @param <X> A dimension class defining the size of dynamically sized markers
 * @param <Y> A shape defining class
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJVisualsInterface<T, U, V, X, Y>  {

    /**
     * @return the MarkerArray
     */
    public GJCyclicArrayList<T> getMarkerArray();

    //MARKERARRAY
    public void setMarkerArray(GJCyclicArrayList<T> MarkerArray);

    public void setMarkerArray(T m);

    public void setMarkerArray(T[] m);

    public void setMarker(T s);

    public void setMarker(int i, T s);

    public T getMarker(int i);

    //EDGECOLOR
    public GJCyclicArrayList<U> getEdgeColor();

    public void setEdgeColor(GJCyclicArrayList<U> EdgeColor);

    public void setEdgeColor(ArrayList<U> p);

    public void setEdgeColor(U[] p);

    public void setEdgeColor(int i, U p);

    public void setEdgeColor(U p);

    //EDGESTROKE
    public GJCyclicArrayList<V> getEdgeStroke();

    public void setEdgeStroke(GJCyclicArrayList<V> EdgeStroke);

    public void setEdgeStroke(ArrayList<V> EdgeStroke);

    public void setEdgeStroke(V[] s);

    public void setEdgeStroke(int i, V s);

    public void setEdgeStroke(V s);

    //FILL
    public GJCyclicArrayList<U> getFill();

    public void setFill(GJCyclicArrayList<U> Fill);

    public void setFill(ArrayList<U> p);

    public void setFill(U[] f);

    public void setFill(U f);

    public void setFill(int i, U f);

    //LINESTROKE
    public GJCyclicArrayList<V> getLineStroke();

    public void setLineStroke(GJCyclicArrayList<V> s);

    public void setLineStroke(ArrayList<V> s);

    public void setLineStroke(V[] s);

    public void setLineStroke(V s);

    public void setLineStroke(int i, V p);

    //LINECOLOR
    public GJCyclicArrayList<U> getLineColor();

    public void setLineColor(GJCyclicArrayList<U> LineColor);

    public void setLineColor(U p);

    public void setLineColor(int i, U p);

    public void setLineColor(U[] p);

    /**
     * @return the Alpha
     */
    public float getAlpha();

    /**
     * @param Alpha the Alpha to set
     */
    public void setAlpha(float Alpha);

    /**
     * @return the CompositeMode
     */
    public int getCompositeMode();

    /**
     * @param CompositeMode the CompositeMode to set
     */
    public void setCompositeMode(int CompositeMode);

    /**
     * @return the renderHintState
     */
    public Object getRenderHintState();

    /**
     * @param renderHintState the renderHintState to set
     */
    public void setRenderHintState(Object renderHintState);

    public Object getTextHintState();

    public void setTextHintState(Object textHintState);

//    public Y getWidestMarker();
//
//    public Y getTallestMarker();
    public GJCyclicArrayList<X> getDynamicMarkerSize();

    public void setDynamicMarkerSize(GJCyclicArrayList<X> DynamicMarkerSize);
}
