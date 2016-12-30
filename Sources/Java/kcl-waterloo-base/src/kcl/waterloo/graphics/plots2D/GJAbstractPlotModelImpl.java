 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
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
package kcl.waterloo.graphics.plots2D;

import java.awt.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kcl.waterloo.effects.GJEffectorInterface;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.graphics.data.GJDataVectorInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.graphics.transforms.NOPTransform;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.observable.GJAbstractObservable;
import kcl.waterloo.plotmodel2D.GJCyclicArrayList;
import kcl.waterloo.plotmodel2D.GJDataModel;
import kcl.waterloo.plotmodel2D.GJScreenDataInterface;
import kcl.waterloo.plotmodel2D.GJVisualModel;

/**
 * Abstract class providing support for both the data model and visual model.
 * Subclassed in the {@code GJAbstractPlot} class
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAbstractPlotModelImpl extends GJAbstractObservable
        implements GJPlotInterface, GJEffectorInterface, GJScreenDataInterface<Shape> {

    /**
     * Visual model see {@code GJVisualModel}.
     */
    private GJVisualModel visualModel = new GJVisualModel();
    /**
     * Data model see {@code GJDataModel}.
     */
    private GJDataModel dataModel = new GJDataModel();
    /**
     * Reference to the graph that this plot is in. This is set when isTopPlot()
     * returns true.
     *
     * The {@code getParentGraph} method for other plots search up the plot
     * hierarchy and return the parentGraph value from the top plot.
     *
     * If a new value is set via {@code setParentGraph}, the plot will be copied
     * to the specified graph.
     *
     * This value should <em>not</em> normally be set to {@code null} after a
     * plot is added to graph although {@code setParentGraph} supports this for
     * use in serialization.
     */
    private GJGraphInterface parentGraph = null;

    private ArrayList<String> name = new ArrayList<String>();

    /**
     * A list of plots that are direct descendants of this plot i.e. those for
     * which getAncestorPlot returns this instance.
     */
    private final ArrayList<GJPlotInterface> plotList = new ArrayList<GJPlotInterface>();
    /**
     * Screen buffer used to select plots with mouse
     */
    public ArrayList<Shape> screenDataArray = new ArrayList<Shape>(20);

    /**
     * Package-private constructor. This will be invoked implicitly when
     * constructing of any concrete class instance extending
     * {@code GJAbstractPlot}.
     *
     */
    GJAbstractPlotModelImpl() {
    }

    @Override
    public final GJGraphInterface getParentGraph() {
        GJAbstractPlotModelImpl topPlot = (GJAbstractPlotModelImpl) getTopPlot();
        return topPlot.parentGraph;
    }

    protected void initNewInstance() {
        if (parentGraph != null) {
            parentGraph.remove(this);
            parentGraph = null;
        }
    }

    @Override
    public final void setParentGraph(GJGraphInterface gr) {
        GJGraphInterface old = getParentGraph();
        if (getParentGraph() == null || getParentGraph() == gr) {
            if (isTopPlot()) {
                // Already a top plot. Just check it is added to the parentGraph -
                // this will call add during serialization/de-serialization of
                // XML (remember plots are not Components).
                parentGraph = gr;
                if (!parentGraph.getPlots().contains(this)) {
                    parentGraph.add(this, false);
                }
            } else {
                // Promoting a plot to become a top plot in the same graph
                copyToOtherGraph(gr);
            }
        } else {
            if (isTopPlot()) {
                // Moving a top plot between graphs
                parentGraph.remove(this);
                parentGraph = gr;
                if (gr != null) {
                    gr.add(this);
                }
            } else {
                // Moving a child plot between graphs
                copyToOtherGraph(gr);
            }
        }
        getPCS().firePropertyChange("parent", old, parentGraph);
    }

    private void copyToOtherGraph(GJGraphInterface gr) {
        if (getDataModel().getXData() == null) {
            setXData(getXData());
        }
        if (getDataModel().getYData() == null) {
            setYData(getYData());
        }
        getParentPlot().getPlotList().remove(this);
        setParentPlot(null);
        parentGraph = gr;
        gr.add(this);
    }

    @Override
    public final void setData(GJDataVectorInterface o1, GJDataVectorInterface o2) {
        setXData(o1);
        setYData(o2);
    }

    @Override
    public final void setXData(GJDataVectorInterface<?> o) {
        GJDataVectorInterface<?> old = getDataModel().getXData();
        getDataModel().setXData(o);
        getPCS().firePropertyChange("XData", old, getDataModel().getXData());
    }

    @Override
    public final void setYData(GJDataVectorInterface<?> o) {
        GJDataVectorInterface old = getDataModel().getYData();
        getDataModel().setYData(o);
        getPCS().firePropertyChange("YData", old, getDataModel().getYData());

    }

    @Override
    public final void setXData(double[] x) {
        getDataModel().getXData().setDataBufferData(x);
    }

    @Override
    public final void setYData(double[] y) {
        getDataModel().getYData().setDataBufferData(y);
    }

    @Override
    public final void setXData(AbstractList<? extends Number> x) {
        getDataModel().getXData().setDataBufferData(x);
    }

    @Override
    public final void setXData(Object mx) {
        if (mx == null) {
            getDataModel().setXData(null);
        } else {
            if (mx instanceof Map) {
                getDataModel().getXData().setDataBufferData((Map) mx);
            }
        }
    }

    @Override
    public final void setXData(int[] x) {
        getDataModel().getXData().setDataBufferData(x);
    }

    @Override
    public final void setYData(int[] y) {
        getDataModel().getYData().setDataBufferData(y);
    }

    @Override
    public final void setYData(AbstractList<? extends Number> y) {
        getDataModel().getYData().setDataBufferData(y);
    }

    @Override
    public final void setYData(Object my) {
        if (my == null) {
            getDataModel().setXData(null);
        } else {
            if (my instanceof Map) {
                getDataModel().getYData().setDataBufferData((Map) my);
            }
        }
    }

    /**
     * Returns the tranformed x-values as a double[].
     *
     * If the data model XData==null, or the underlying data vector has a length
     * of zero, data will be retrieved form the parent plot.
     *
     * @return the x-axis data
     */
    @Override
    public final double[] getXDataValues() {
        if (getDataModel().getXData() == null || getDataModel().getXData().getDimension() == 0) {
            if (!isTopPlot()) {
                return getTopPlot().getXDataValues();
            } else {
                return null;
            }
        } else {
            return getDataModel().getXData().getDataValues(getParentGraph().getXTransform());
        }
        //return getXData().getDataValues();
    }

    /**
     * Returns the x data as a GJDataVectorInterface
     *
     * @return the x-axis data
     */
    @Override
    public final GJDataVectorInterface<?> getXData() {
        if (getDataModel().getXData() == null) {
            if (!isTopPlot()) {
                return getParentPlot().getXData();
            } else {
                return null;
            }
        } else {
            return getDataModel().getXData();
        }
    }

    /**
     * Get method for the y-axis data.
     *
     * If the data model XData==null, or the underlying data vector has a length
     * of zero, data will be retrieved form the parent plot.
     *
     * @return the y-axis data
     */
    @Override
    public final double[] getYDataValues() {
        if (getDataModel().getYData() == null || getDataModel().getYData().getDimension() == 0) {
            if (!isTopPlot()) {
                return getParentPlot().getYDataValues();
            } else {
                return null;
            }
        } else {
            return getDataModel().getYData().getDataValues(getParentGraph().getYTransform());
        }
        //return getYData().getDataValues();
    }

    @Override
    public final GJDataVectorInterface<?> getYData() {
        if (getDataModel().getYData() == null) {
            if (!isTopPlot()) {
                return getParentPlot().getYData();
            } else {
                return null;
            }
        } else {
            return getDataModel().getYData();
        }
    }

    @Override
    public final void setExtraData0(double[] val) {
        double[] old = getDataModel().getExtraData0();
        getDataModel().setExtraData0(val);
        getPCS().firePropertyChange("extraData0", old, getDataModel().getExtraData0());
    }

    @Override
    public final void setExtraData1(double[] val) {
        double[] old = getDataModel().getExtraData1();
        getDataModel().setExtraData1(val);
        getPCS().firePropertyChange("extraData1", old, getDataModel().getExtraData1());
    }

    @Override
    public final void setExtraData2(double[] val) {
        double[] old = getDataModel().getExtraData2();
        getDataModel().setExtraData2(val);
        getPCS().firePropertyChange("extraData2", old, getDataModel().getExtraData2());
    }

    @Override
    public final void setExtraData3(double[] val) {
        double[] old = getDataModel().getExtraData3();
        getDataModel().setExtraData3(val);
        getPCS().firePropertyChange("extraData3", old, getDataModel().getExtraData3());
    }


    /**
     * Returns true if this plot is Multiplexed. Refer to the overview above for
     * details of how multiplexing is implemented
     *
     * @return true if multiplexed
     */
    @Override
    public final boolean isMultiplexed() {
        return isMultiplexible() && this.getMultiplexLength() > 1;
    }

    /**
     * Returns the number of series for this plot This is the maximum of the
     * lengths of the MarkerArray, EdgeStroke, EdgeColor, LineStroke and
     * LineColor arrays. Refer to the overview above for details of how
     * multiplexing is implemented
     *
     * @return true if multiplexed
     */
    @Override
    public final int getMultiplexLength() {
        int[] N = {1, 1, 1, 1, 1, 1};
        if (getVisualModel().getMarkerArray() != null) {
            N[0] = getVisualModel().getMarkerArray().size();
        }
        if (getVisualModel().getEdgeStroke() != null) {
            N[1] = getVisualModel().getEdgeStroke().size();
        }
        if (getVisualModel().getEdgeColor() != null) {
            N[2] = getVisualModel().getEdgeColor().size();
        }
        if (getVisualModel().getLineColor() != null) {
            N[3] = getVisualModel().getLineColor().size();
        }
        if (getVisualModel().getLineStroke() != null) {
            N[4] = getVisualModel().getLineStroke().size();
        }
        if (getVisualModel().getFill() != null) {
            N[5] = getVisualModel().getFill().size();
        }
        int n[] = ArrayMath.minmax(N);
        return n[1];
    }

    @Override
    public final GJDataTransformInterface getXTransform() {
        if (getParentGraph() == null) {
            return NOPTransform.getInstance();
        } else {
            return getParentGraph().getXTransform();
        }
    }

    @Override
    public final GJDataTransformInterface getYTransform() {
        if (getParentGraph() == null) {
            return NOPTransform.getInstance();
        } else {
            return getParentGraph().getYTransform();
        }
    }


    /**
     * Sets the alpha level on the composite used to render this plot. All
     * rendering for this plot will be affected. To affect specific features
     * only, e.g. a line, set the alpha of the Paint object used for that
     * feature instead.
     *
     * @param val the alpha level 0=transparent 1=opaque (default)
     */
    @Override
    public final void setAlpha(float val) {
        getVisualModel().setAlpha(val);
        if (getPlotList() != null) {
            for (Iterator<GJPlotInterface> it = getPlotList().iterator(); it.hasNext();) {
                GJPlotInterface p = it.next();
                p.setAlpha(val);
            }
        }
    }

    @Override
    public final synchronized ArrayList<Shape> getScreenDataArray() {
        return screenDataArray;
    }

    @Override
    public final ArrayList<Shape> getScreenDataArrayAsCopy() {
        return new ArrayList<Shape>(getScreenDataArray());
    }

    @Override
    public final synchronized void setScreenDataArray(ArrayList<Shape> ScreenDataArray) {
        this.screenDataArray = ScreenDataArray;
    }

//    @Override
//    public final ArrayList<Shape> getScreenDataArray() {
//        return getScreenDataArray();
//    }
//
//    @Override
//    public final ArrayList<Shape> getScreenDataArrayAsCopy() {
//        return getVisualModel().getScreenDataArrayAsCopy();
//    }
    @Override
    public final float getAlpha() {
        return getVisualModel().getAlpha();
    }

    @Override
    public final void setCompositeMode(int val) {
        getVisualModel().setCompositeMode(val);
    }

    @Override
    public final int getCompositeMode() {
        return getVisualModel().getCompositeMode();
    }

    @Override
    public final void setLineColor(GJCyclicArrayList<Paint> p) {
        getVisualModel().setLineColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setLineStroke(BasicStroke s) {
        getVisualModel().setLineStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final void setLineStroke(int i, BasicStroke s) {
        getVisualModel().setLineStroke(i, s);
    }

    @Override
    public final void setLineStroke(BasicStroke[] s) {
        getVisualModel().setLineStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final void setLineStroke(GJCyclicArrayList<BasicStroke> s) {
        getVisualModel().setLineStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final void setLineStroke(ArrayList<BasicStroke> s) {
        getVisualModel().setLineStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final GJCyclicArrayList<BasicStroke> getLineStroke() {
        return getVisualModel().getLineStroke();
    }

    @Override
    public final GJCyclicArrayList<Paint> getLineColor() {
        return getVisualModel().getLineColor();
    }

    @Override
    public final void setEdgeStroke(BasicStroke[] s) {
        getVisualModel().setEdgeStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final void setEdgeStroke(BasicStroke s) {
        getVisualModel().setEdgeStroke(new GJCyclicArrayList<BasicStroke>(s));
    }

    @Override
    public final void setEdgeStroke(int index, BasicStroke s) {
        getVisualModel().getEdgeStroke().set(index, s);
    }

    @Override
    public final GJCyclicArrayList<BasicStroke> getEdgeStroke() {
        return getVisualModel().getEdgeStroke();
    }

    @Override
    public final void setEdgeColor(ArrayList<Paint> p) {
        getVisualModel().setEdgeColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setEdgeColor(Paint[] p) {
        getVisualModel().setEdgeColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setEdgeColor(Paint p) {
        getVisualModel().setEdgeColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setEdgeColor(int i, Paint p) {
        getVisualModel().setEdgeColor(i, p);
    }

    @Override
    public final GJCyclicArrayList<Paint> getEdgeColor() {
        return getVisualModel().getEdgeColor();
    }

    @Override
    public final void setFill(ArrayList<Paint> p) {
        getVisualModel().setFill(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setFill(Paint[] f) {
        getVisualModel().setFill(new GJCyclicArrayList<Paint>(f));
    }

    @Override
    public final void setFill(Paint f) {
        getVisualModel().setFill(new GJCyclicArrayList<Paint>(f));
    }

    @Override
    public final void setFill(int i, Paint f) {
        getVisualModel().setFill(i, f);
    }

    @Override
    public final GJCyclicArrayList<Paint> getFill() {
        return getVisualModel().getFill();
    }

    @Override
    public final GJMarker getMarker(int i) {
        return getVisualModel().getMarkerArray().get(i);
    }

    @Override
    public final GJCyclicArrayList<GJMarker> getMarkerArray() {
        return getVisualModel().getMarkerArray();
    }

    @Override
    public final void setMarkerArray(GJCyclicArrayList<GJMarker> arr) {
        getVisualModel().setMarkerArray(arr);
    }

    @Override
    public final void setMarkerArray(GJMarker m) {
        getVisualModel().setMarkerArray(new GJCyclicArrayList<GJMarker>(m));
    }

    @Override
    public final void setMarker(int i, GJMarker s) {
        getVisualModel().getMarkerArray().set(i, s);
    }

    @Override
    public final void setMarker(GJMarker s) {
        getVisualModel().setMarker(s);
    }

    @Override
    public final void setLineColor(int i, Paint p) {
        getVisualModel().getLineColor().set(i, p);
    }

    @Override
    public final void setLineColor(Paint p) {
        getVisualModel().setLineColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final void setLineColor(Paint[] p) {
        getVisualModel().setLineColor(new GJCyclicArrayList<Paint>(p));
    }

    @Override
    public final synchronized GJVisualModel getVisualModel() {
        return visualModel;
    }

    /**
     * @param visualModel the visualModel to set
     */
    @Override
    public final synchronized void setVisualModel(GJVisualModel visualModel) {
        this.visualModel = visualModel;
    }

    @Override
    public final void setMarkerArray(GJMarker[] m) {
        getVisualModel().setMarkerArray(m);
    }

    @Override
    public final GJCyclicArrayList<Dimension> getDynamicMarkerSize() {
        return getVisualModel().getDynamicMarkerSize();
    }

    @Override
    public final void setDynamicMarkerSize(GJCyclicArrayList<Dimension> DynamicMarkerSize) {
        getVisualModel().setDynamicMarkerSize(DynamicMarkerSize);
    }

    @Override
    public final void setEdgeColor(GJCyclicArrayList<Paint> EdgeColor) {
        getVisualModel().setEdgeColor(EdgeColor);
    }

    @Override
    public final void setEdgeStroke(GJCyclicArrayList<BasicStroke> EdgeStroke) {
        getVisualModel().setEdgeStroke(EdgeStroke);
    }

    @Override
    public final void setEdgeStroke(ArrayList<BasicStroke> EdgeStroke) {
        getVisualModel().setEdgeStroke(EdgeStroke);
    }


    @Override
    public final void setFill(GJCyclicArrayList<Paint> Fill) {
        getVisualModel().setFill(Fill);
    }


    @Override
    public final Object getRenderHintState() {
        return getVisualModel().getRenderHintState();
    }

    @Override
    public final void setRenderHintState(Object renderHintState) {
        getVisualModel().setRenderHintState(renderHintState);
    }

    @Override
    public final Object getTextHintState() {
        return getVisualModel().getTextHintState();
    }
//

    @Override
    public final void setTextHintState(Object setTextHintState) {
        getVisualModel().setTextHintState(setTextHintState);
    }

    /**
     * @return the dataModel
     */
    @Override
    public final GJDataModel getDataModel() {
        return dataModel;
    }

    /**
     * @param dataModel the dataModel to set
     */
    @Override
    public final void setDataModel(GJDataModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * @return the name
     */
    @Override
    public ArrayList<String> getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.clear();
        this.name.add(name);
    }

    /**
     * @param plotList
     */
    @Override
    public final void setPlotList(ArrayList<GJPlotInterface> plotList) {
        ArrayList<GJPlotInterface> old = new ArrayList<GJPlotInterface>(this.plotList);
        this.plotList.clear();
        this.plotList.addAll(plotList);
        getPCS().firePropertyChange("plotList", old, plotList);
    }

    /**
     * @return the plotList
     */
    @Override
    public final ArrayList<GJPlotInterface> getPlotList() {
        return plotList;
    }

}
