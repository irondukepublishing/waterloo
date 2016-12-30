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

import java.awt.AlphaComposite;
import java.util.ArrayList;
import kcl.waterloo.defaults.GJDefaults;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <X>
 * @param <Y>
 */
abstract class GJAbstractVisualModel<T, U, V, X, Y>
        implements GJVisualsInterface<T, U, V, X, Y> {

    /**
     * Shape or Shape array for the marker(s). For simple scatter plots etc, the
     * marker will generally be a simple circle, triangle etc.
     */
    private GJCyclicArrayList<T> markerArray = new GJCyclicArrayList<T>();
    private GJCyclicArrayList<X> dynamicMarkerSize = new GJCyclicArrayList<X>();
    /**
     * Paint[] for the marker edges e.g. a Color
     */
    private GJCyclicArrayList<U> edgeColor = new GJCyclicArrayList<U>();
    /**
     * Stroke for the marker edges
     */
    private GJCyclicArrayList<V> edgeStroke = new GJCyclicArrayList<V>();
    /**
     * Paint for line color
     */
    private GJCyclicArrayList<U> lineColor = new GJCyclicArrayList<U>();
    /**
     * Paint for filling markers, bars etc
     */
    private GJCyclicArrayList<U> fill = new GJCyclicArrayList<U>();
    /**
     * Stroke for line
     */
    private GJCyclicArrayList<V> lineStroke = new GJCyclicArrayList<V>();
    /**
     * alpha level used for the all Graphics2D drawing. Default=1.0f
     */
    private float alpha = 1f;
    /**
     * Composite mode used for the all Graphics2D drawing. Default=SRC_OVER
     */
    private int compositeMode = AlphaComposite.SRC_OVER;
    /**
     * The rendering hint for graphics
     */
    private Object renderHintState = GJDefaults.getMap2().get("GJAbstractVisualModel.keyAntialiasing");
    /**
     * The rendering hint for text
     */
    private Object textHintState = GJDefaults.getMap2().get("GJAbstractVisualModel.textAntialiasing");

    public GJAbstractVisualModel() {
    }

    @Override
    public final GJCyclicArrayList<T> getMarkerArray() {
        return markerArray;
    }

    @Override
    public final GJCyclicArrayList<X> getDynamicMarkerSize() {
        return dynamicMarkerSize;
    }

    public final void setDynamicMarkerSize(X... markersizes) {
        this.setDynamicMarkerSize(new GJCyclicArrayList<X>(markersizes));
    }

    @Override
    public final GJCyclicArrayList<U> getEdgeColor() {
        return edgeColor;
    }

    @Override
    public final void setEdgeColor(GJCyclicArrayList<U> EdgeColor) {
        this.edgeColor = EdgeColor;
    }

    @Override
    public final GJCyclicArrayList<V> getEdgeStroke() {
        return edgeStroke;
    }

    @Override
    public final void setEdgeStroke(GJCyclicArrayList<V> EdgeStroke) {
        this.edgeStroke = EdgeStroke;
    }

    @Override
    public final void setEdgeStroke(ArrayList<V> EdgeStroke) {
        this.edgeStroke = new GJCyclicArrayList<V>(EdgeStroke);
    }

    @Override
    public final void setEdgeStroke(V[] s) {
        edgeStroke = new GJCyclicArrayList<V>(s);
    }

    @Override
    public final void setEdgeStroke(V s) {
        edgeStroke = new GJCyclicArrayList<V>(s);
    }

    @Override
    public final void setEdgeStroke(int index, V s) {
        edgeStroke.set(index, s);
    }

//    public V getEdgeStroke(int index) {
//        return getEdgeStroke().get(index);
//    }
    @Override
    public final GJCyclicArrayList<U> getLineColor() {
        return lineColor;
    }

    @Override
    public final void setLineColor(GJCyclicArrayList<U> LineColor) {
        this.lineColor = LineColor;
    }

    @Override
    public final GJCyclicArrayList<U> getFill() {
        return fill;
    }

    @Override
    public final void setFill(GJCyclicArrayList<U> Fill) {
        this.fill = Fill;
    }

    @Override
    public final GJCyclicArrayList<V> getLineStroke() {
        return lineStroke;
    }



    @Override
    public final float getAlpha() {
        return alpha;
    }

    @Override
    public final void setAlpha(float Alpha) {
        this.alpha = Alpha;
    }

    @Override
    public final int getCompositeMode() {
        return compositeMode;
    }

    @Override
    public final void setCompositeMode(int CompositeMode) {
        this.compositeMode = CompositeMode;
    }

    @Override
    public final Object getRenderHintState() {
        return renderHintState;
    }

    @Override
    public final void setRenderHintState(Object renderHintState) {
        this.renderHintState = renderHintState;
    }

    @Override
    public final Object getTextHintState() {
        return textHintState;
    }

    @Override
    public final void setTextHintState(Object textHintState) {
        this.textHintState = textHintState;
    }

    @Override
    public final void setMarkerArray(T m) {
        setMarkerArray(new GJCyclicArrayList<T>(m));
    }

    @Override
    public final void setMarker(T m) {
        setMarkerArray(m);
    }

    @Override
    public final void setMarkerArray(T[] m) {
        setMarkerArray(new GJCyclicArrayList<T>(m));
    }

    @Override
    public final void setLineColor(U[] p) {
        lineColor = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setLineColor(U p) {
        lineColor = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setLineColor(int i, U p) {
        lineColor.set(i, p);
    }

    @Override
    public final void setMarker(int i, T s) {
        markerArray.set(i, s);
    }

    @Override
    public final T getMarker(int i) {
        return markerArray.get(i);
    }

    @Override
    public final void setLineStroke(GJCyclicArrayList<V> LineStroke) {
        this.lineStroke = LineStroke;
    }

    @Override
    public final void setLineStroke(ArrayList<V> LineStroke) {
        this.lineStroke = new GJCyclicArrayList<V>(LineStroke);
    }

    @Override
    public final void setLineStroke(V s) {
        lineStroke = new GJCyclicArrayList<V>(s);
    }

    @Override
    public final void setLineStroke(int i, V s) {
        lineStroke.set(i, s);
    }

    @Override
    public final void setLineStroke(V[] s) {
        lineStroke = new GJCyclicArrayList<V>(s);
    }

    @Override
    public final void setEdgeColor(ArrayList<U> p) {
        edgeColor = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setEdgeColor(U[] p) {
        edgeColor = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setEdgeColor(int i, U p) {
        edgeColor.set(i, p);
    }

    @Override
    public final void setEdgeColor(U p) {
        edgeColor = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setFill(ArrayList<U> p) {
        fill = new GJCyclicArrayList<U>(p);
    }

    @Override
    public final void setFill(U[] f) {
        fill = new GJCyclicArrayList<U>(f);
    }

    @Override
    public final void setFill(U f) {
        if (f == null) {
            fill = null;
        } else {
            fill = new GJCyclicArrayList<U>(f);
        }
    }

    @Override
    public final void setFill(int i, U f) {
        fill.set(i, f);
    }

//    @Override
//    public final Y getWidestMarker() {
//        return null;
//    }
//
//    @Override
//    public final Y getTallestMarker() {
//        return null;
//    }
    @Override
    public final void setMarkerArray(GJCyclicArrayList<T> MarkerArray) {
        this.markerArray = MarkerArray;
    }

    @Override
    public final void setDynamicMarkerSize(GJCyclicArrayList<X> DynamicMarkerSize) {
        this.dynamicMarkerSize = DynamicMarkerSize;
    }
}
