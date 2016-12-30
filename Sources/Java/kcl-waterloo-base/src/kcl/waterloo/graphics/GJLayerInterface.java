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
package kcl.waterloo.graphics;

import java.util.ArrayList;

/**
 * Provides an interface to support multi-layered graphs
 *
 * @param <T>
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJLayerInterface<T> {

    /**
     * Returns the graph that forms the view in this graph's ancestry.
     *
     * @return the graph
     */
    public T getAncestorGraph();

    /**
     * Returns a list of the layers for this graph. The list includes this graph
     * at element 0.
     *
     * @return an ArrayList
     */
    public ArrayList<T> getLayers();

    /**
     * Adds a graph as a child of this graph. Provides support for nested
     * graphs.
     *
     * @param graph
     * @return the added graph
     */
    public T add(T graph);

    /**
     * Returns the layer at the specified index
     *
     * @param n the layer number - zero is this layer
     * @return the layer
     */
    public T getLayer(int n);

    /**
     * Returns the currently selected layer
     *
     * @return a GJGraphInterface instance
     */
    public T getCurrentLayer();

    /**
     * Sets the current layer for this graph.
     *
     * @param layer
     */
    public void setCurrentLayer(T layer);

    /**
     * Returns the index of the current layer.
     *
     * @return the index
     */
    public int getCurrentLayerIndex();

    /**
     * Sets the current layer by index.
     *
     * @param n
     */
    public void setCurrentLayerIndex(int n);

    /**
     * Returns the index of this graph as a layer in the view.
     *
     * @return the index
     */
    public int indexOf();

}
