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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.plot

import groovy.swing.SwingBuilder
import kcl.waterloo.actions.GJEventManager
import kcl.waterloo.defaults.GJDefaults
import kcl.waterloo.graphics.GJGraph
import kcl.waterloo.graphics.GJGraphContainer
import kcl.waterloo.graphics.GJUtilities
import kcl.waterloo.graphics.plots2D.*
import kcl.waterloo.marker.GJMarker
import kcl.waterloo.plotmodel2D.GJCyclicArrayList
import kcl.waterloo.swing.GCFrame
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import java.awt.*

/**
 * WPlot class.
 * Provides a static library for creating plots and a wrapper for them.
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * */
class WPlot {

    /**
     * WPlot constructor
     */
    WPlot(GJPlotInterface thisPlot) {
        plot = thisPlot
    }

    /**
     * The created GJPlotInterface.
     */
    GJPlotInterface plot = null

    /**Closure providing warning message to System.out when an unsupported option is provided on input*/
    private static
    final Closure UnsupportedOn = { opt -> println("WPlot.parseArgs: The \"${opt}\" option is presently unsupported") }
    /**Do nothing closure used to suppress warnings*/
    private static final Closure UnsupportedOff = { opt -> }
    /**Current warning closure*/
    private static Closure UnsupportedWarning = UnsupportedOff

    private static Map colorStore = new LinkedHashMap()

    private static shell = new GroovyShell()

    // Individual static methods for each plot type

    /**
     * Static method to construct a new scatter plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot scatter(options) {
        return buildPlot(GJScatter.createInstance(), options)
    }

    /**
     * Static method to construct a new cloud plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot cloud(options) {
        return buildPlot(GJCloud.createInstance(), options)
    }

    /**
     * Static method to construct a new component plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot component(options) {
        return buildPlot(GJComponentPlot.createInstance(), options)
    }

    /**
     * Static method to construct a new line plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot line(options) {
        return buildPlot(GJLine.createInstance(), options)
    }

    /**
     * Static method to construct a new fast rendered line plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot fastline(options) {
        return buildPlot(GJFastLine.createInstance(), options)
    }

    /**
     * Static method to construct a new error bar plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot errorbar(options) {
        return buildPlot(GJErrorBar.createInstance(), options)
    }

    /**
     * Static method to construct a new stair plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot stairs(options) {
        return buildPlot(GJStairs.createInstance(), options)
    }

    /**
     * Static method to construct a new sten plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot stem(options) {
        return buildPlot(GJStem.createInstance(), options)
    }

    /**
     * Static method to construct a new feather plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot feather(options) {
        return buildPlot(GJFeather.createInstance(), options)
    }

    /**
     * Static method to construct a new quiver plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot quiver(options) {
        return buildPlot(GJQuiver.createInstance(), options)
    }

    /**
     * Static method to construct a new contour plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot contour(options) {
        return buildPlot(GJContour.createInstance(), options)
    }

    /**
     * Static method to construct a new bar plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot bar(options) {
        return buildPlot(GJBar.createInstance(), options)
    }

    /**
     * Static method to construct a new pie chart
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot pie(options) {
        return buildPlot(GJPie.createInstance(), options)
    }

    /**
     * Static method to construct a new polar line plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot polarline(options) {
        return buildPlot(GJPolarLine.createInstance(), options)
    }

    /**
     * Static method to construct a new polar scatter plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot polarscatter(options) {
        return buildPlot(GJPolarScatter.createInstance(), options)
    }

    /**
     * Static method to construct a new polar bar plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot polarbar(options) {
        return buildPlot(GJPolarBar.createInstance(), options)
    }

    /**
     * Static method to construct a new polar stem plot
     * @param options for the plot
     * @return a WPlot instance wrapping a GJAbstractPlot subclass
     */
    static WPlot polarstem(options) {
        return buildPlot(GJPolarStem.createInstance(), options)
    }

    //    static WPlot polarcloud(options) {
    //        return buildPlot(GJPolarScatter.createInstance(), options)
    //    }

    // WPlot class proper

    /**
     * Plus method to add two plots together.
     * In environments that permit it, this method can be called as a '+' operation:
     * e.g.<br>
     *     w0+=w1<br>
     * Alternatively, use:<br>
     * w0.plus(w1);<br>
     *
     * @param w1 a WPlot instance
     * @return w0 with the plot from w1 added to its wrapped GJPlotInterface object.
     */
    WPlot plus(WPlot w1) {
        getPlot().add(w1.getPlot())
        return this
    }

    /**
     * Plus method to add a GJPlotInterface instance to a WPlot.
     * In environments that permit it, this method can be called as a '+' operation:
     * e.g.<br>
     *     w0+=p
     * Alternatively, use:
     * w0.plus(p);
     * @param p
     * @return w0 with p added to it.
     */
    WPlot plus(GJPlotInterface p) {
        getPlot().add(p)
        return this
    }

    /**
     * gePlot returns the GJPlotInterface wrapped by a WPlot instance
     * @return
     */
    GJPlotInterface getPlot() {
        return plot
    }

    /**
     * Sets warnings on or off. Warnings are directed to System.out via the <code>UnsupportedOn</code> closure
     * @param flag
     */
    static void setWarnings(boolean flag) {
        switch (flag) {
        case true:
            UnsupportedWarning = UnsupportedOn
            break
        case false:
            UnsupportedWarning = UnsupportedOff
        }
    }


    private static buildPlot(plotInstance, options) {
        def props = preParseArgs(options);
        WPlot thisPlot = new WPlot(processArgs(plotInstance, props))
        return thisPlot
    }

    /**
     *   preParseArgs pre-parses the inputs - creating Waterloo compatible properties
     *   and values.
     *
     *   This static Closure is made public so that users can use it to process their
     *   own inputs in an external environment.
     *
     *
     */
    static preParseArgs = { options ->
        if (options == null) {
            return null
        } else if (options instanceof Map) {
            // LinkedHashMap on input. Pre-parse the colors only.
            if (options.containsKey("LineColor"))
            options.LineColor = convertColor(options.get("LineColor"))
            if (options.containsKey("EdgeColor"))
            options.EdgeColor = convertColor(options.get("EdgeColor"))
            if (options.containsKey("Fill"))
            options.Fill = convertColor(options.get("Fill"))
            if (options.containsKey("Color"))
            options.Color = convertColor(options.get("Color"))
            if (options.containsKey("Marker"))
            if (options.Marker instanceof ArrayList)
            options.Marker = options.Marker as GJMarker[]
            return options
        } else if (options instanceof ArrayList) {
            // ArrayList - treat as Object[]
            return parseArgs(options as Object[])
        } else {
            // Object[] - preParse arguments
            return parseArgs(options)
        }
    }


    static parseArgs(Object... options) {
        def props = new LinkedHashMap<String, Object>()
        if (options != null) {
            for (int k = 0; k < options.length; k += 2) {
                switch (options[k].toString().toLowerCase()) {

                case "xdata":
                    props.XData = options[k + 1] as double[]
                    break
                case "ydata":
                    props.YData = options[k + 1] as double[]
                    break
                case "zdata":
                    props.ZData = options[k + 1] as double[]
                    break
                case "leftdata":
                case "extradata2":
                    props.LeftData = options[k + 1] as double[]
                    break
                case "lowerdata":
                case "extradata3":
                    props.LowerData = options[k + 1] as double[]
                    break
                case "rightdata":
                case "extradata0":
                    props.RightData = options[k + 1] as double[]
                    break
                case "upperdata":
                case "extradata1":
                    props.UpperData = options[k + 1] as double[]
                    break


                case "alpha":
                    props.Alpha = options[k + 1] as float
                case "barlayout":
                    props.BarLayout = options[k + 1] as String
                    break
                case "barwidth":
                    props.BarWidth = options[k + 1] as double
                    break
                case "baseline":
                    break
                case "basevalue":
                    props.BaseValue = options[k + 1] as double
                    break
                case "offset":
                    props.Offset = options[k + 1] as double
                    break
                case "xcategories":
                    props.XCategories = options[k + 1]
                    break
                case "ycategories":
                    props.YCategories = options[k + 1]
                    break
                case "cdata":
                    break
                case "color":
                    props.Color = convertColor(options[k + 1]);
                    break
                case "createfcn":
                    UnsupportedWarning(options[k] as Object)
                    break
                case "datamode":
                    props.DataMode = options[k + 1]
                    break
                case "deletefcn":
                    UnsupportedWarning(options[k])
                    break
                case "displayname":
                    break
                case "edgecolor":
                    props.EdgeColor = convertColor(options[k + 1]);
                    break
                case "edgestroke":
                    props.EdgeStroke = options[k + 1]
                    break
                case "edgestyle":
                    props.EdgeStyle = options[k + 1]
                    break
                case "edgewidth":
                    props.EdgeWidth = options[k + 1]
                    break
                case "explode":
                    props.Explode = options[k + 1]
                    break
                case "facecolor":
                case "fill":
                    props.Fill = convertColor(options[k + 1]);
                    break
                case "hittest":
                    switch (options[k + 1]) {
                    case "on":
                        props.SetActive = true;
                        break
                    case "off":
                        props.SetActive = false;
                        break
                    }
                    break
                case "linecolor":
                    props.LineColor = convertColor(options[k + 1])
                    props.EdgeColor = props.LineColor
                    break
                case "linewidth":
                    props.LineWidth = options[k + 1] as double
                    props.EdgeWidth = props.LineWidth
                    break
                case "linespec":
                    // MATLAB style linespec e.g "-ob" -> solid line with circle in blue.
                    kcl.waterloo.plot.LineSpec spec = new kcl.waterloo.plot.LineSpec(options[k + 1].toString())
                    if (props.LineColor == null) {
                        props.LineColor = spec.color.darker()
                    }
                    if (props.EdgeColor == null) {
                        props.EdgeColor = spec.color.darker()
                    }
                    if (props.MarkerFcn == null) {
                        props.MarkerFcn = spec.marker
                    }
                    if (props.MarkerSize == 0) {
                        props.MarkerSize = 5
                    }
                    if (props.LineStyle == null) {
                        props.LineStyle = spec._stroke
                    }
                    if (props.Fill == null) {
                        props.Fill = spec.color
                    }
                    break
                case "linestyle":
                    props.LineStyle = options[k + 1]
                    props.EdgeStyle = props.LineStyle
                    break
                case "marker":
                case "markerarray":
                    if (options[k + 1] instanceof GJMarker || options[k + 1] instanceof GJMarker[])
                    props.Marker = options[k + 1]
                    else
                    props.MarkerFcn = convertMarkerType(options[k + 1])
                    break
                case "markeredgecolor":
                    props.EdgeColor = convertColor(options[k + 1])
                    break
                case "markerfacecolor":
                    props.Fill = convertColor(options[k + 1])
                    break
                case "markersize":
                    props.MarkerSize = options[k + 1]
                    break
                case "mode":
                    props.Mode = options[k + 1]
                    break
                case "orientation":
                    props.Orientation = options[k + 1]
                    break
                case "scale":
                    props.Scale = options[k + 1] as double
                    break
                case "showbaseline":
                    break
                case "sizedata":
                    props.SizeData = options[k + 1]
                    break
                case "visible":
                    break

                case "name":
                    props.Name = options[k + 1]
                    break
                case "xname":
                    props.xName = options[k + 1]
                    break
                case "yname":
                    props.yName = options[k + 1]
                    break


                default:
                    props.put(options[k].toString(), options[k + 1])
                }
            }
        }
        return props
    }

    static Closure convertMarkerType(input) {
        switch (input) {
        case "+":
            return { sz -> GJMarker.plus(sz) }
        case ".":
            return { sz -> GJMarker.dot(sz) }
        case "o":
            return { sz -> GJMarker.circle(sz) }
        case "*":
            return { sz -> GJMarker.makeCharMarker("*") }
        case "x":
            return { sz -> GJMarker.makeCross(sz) }
        case "square":
        case "s":
            return { sz -> GJMarker.square(sz) }
        case "diamond":
        case "d":
            return { sz -> GJMarker.diamond(sz) }
        case "^":
            return { sz -> GJMarker.triangle(sz) }
        case "v":
            return { sz -> GJMarker.invertedTriangle(sz) }
        case "<":
            return { sz -> GJMarker.leftTriangle(sz) }
        case ">":
            return { sz -> GJMarker.rightTriangle(sz) }
        case "pentagram":
        case "p":
            //TODO
            return { sz -> GJMarker.circle(sz) }
        case "hexag":
        case "h":
            //TODO
            return { sz -> GJMarker.circle(sz) }
        }

    }

    public static processArgs(Object plot, Map props) {

        if (props == null) {
            return plot
        }

        // Set any unspecified props to the following defaults
        if (!props.containsKey("MarkerSize"))
        props.MarkerSize = 5

        // Process props arguments
        for (obj in props) {
            switch (obj.key) {
            case "BarLayout":
                if (plot instanceof GJBar){
                    switch (props.BarLayout) {
                    case 'grouped':
                        plot.getDataModel().getExtraObject().setMode(BarExtra.MODE.GROUPED)
                        break
                    case 'stacked':
                        plot.getDataModel().getExtraObject().setMode(BarExtra.MODE.STACKED)
                        break
                    case 'hist':
                        plot.getDataModel().getExtraObject().setMode(BarExtra.MODE.HIST)
                        break
                    case 'histc':
                        plot.getDataModel().getExtraObject().setMode(BarExtra.MODE.HISTC)
                        break
                    }
                }
                break
            case "BarWidth":
                if (plot instanceof GJBar){
                    plot.getDataModel().getExtraObject().setBarWidth(props.BarWidth);
                }
                break
            case "BaseLine":
                break
            case "BaseValue":
                plot.getDataModel().getExtraObject().setBaseValue(props.BaseValue)
                break
            case "Offset":
                plot.getDataModel().getExtraObject().setOffset(props.Offset)
                break
            case "CData":
                break
            case "Color":
                //                if (props.Color instanceof java.awt.Paint[]) {
                //                    props.Color = (java.awt.Paint[]) props.Color
                //                } else if (props.(obj.key) instanceof java.awt.Paint){
                //                    props.Color = (java.awt.Paint) props.Color
                //                }
                plot.setEdgeColor(props.Color);
                plot.setLineColor(props.Color);
                break
            case "CreateFcn":
                break
            case "DeleteFcn":
                break
            case "DisplayName":
                break
            case "Explode":
                plot.getDataModel().getExtraObject().setExplode(props.Explode)
                break
            case "HitTest":
                break
            case "LineWidth":
            case "LineStyle":
                Float width = props.LineWidth
                if (width == null) {
                    width = GJDefaults.getMap().get("GJAbstractPlot.lineStrokeWidth").floatValue()
                }
                String style = props.LineStyle
                if (style == null) {
                    style = "-"
                }
                plot.setLineStroke(GJUtilities.makeStroke(width, style))
                break
            case "EdgeStroke":
                plot.setEdgeStroke(props.EdgeStroke)
                break
            case "EdgeWidth":
            case "EdgeStyle":
                Float width = props.EdgeWidth
                if (width == null) {
                    width = GJDefaults.getMap().get("GJAbstractPlot.edgeStrokeWidth").floatValue()
                }
                String style = props.EdgeStyle
                if (style == null) {
                    style = "-"
                }
                plot.setEdgeStroke(GJUtilities.makeStroke(width, style))
                break
            case "Marker":
                plot.setMarkerArray(props.Marker)
                break
            case "MarkerFcn":
                plot.setMarkerArray(props.MarkerFcn(props.MarkerSize));
                break
            case "MarkerSize":
                // If MarkerSize  contains dimensions set the DynamicMarkerSizes
                if (props.MarkerSize instanceof Dimension) {
                    def p = new GJCyclicArrayList(props.MarkerSize)
                    plot.setDynamicMarkerSize(p)
                }
                break
            case "Mode":
                plot.getDataModel().getExtraObject().setMode(props.Mode)
                break
            case "DataMode":
                if (plot.getDataModel().getExtraObject() instanceof PolarExtra) {
                    if (props.DataMode instanceof String) {
                        switch (props.DataMode.toUpperCase()) {
                        case "POLAR":
                            plot.getDataModel().getExtraObject().setDataMode(PolarExtra.DATAMODE.POLAR)
                            break
                        case "CARTESIAN":
                            plot.getDataModel().getExtraObject().setDataMode(PolarExtra.DATAMODE.CARTESIAN)
                            break
                        case "CUSTOM":
                            plot.getDataModel().getExtraObject().setDataMode(PolarExtra.DATAMODE.CUSTOM)
                            break
                        default:
                            break
                        }
                    } else {
                        plot.getDataModel().getExtraObject().setDataMode(props.DataMode)
                    }
                }
                break
            case "Scale":
                try {
                    plot.setScale(props.Scale)
                } catch (MissingMethodException ex) {
                }
                break
            case "ShowBaseLine":
                break
            case "SizeData":
                def fcn
                if (props.MarkerFcn == null) {
                    fcn = convertMarkerType("o")
                } else {
                    fcn = props.MarkerFcn
                }
                def GJMarker[] markers;
                if (props.SizeData.getClass().isArray()) {
                    markers = props.SizeData.collect { (GJMarker) fcn(it) }.toArray()
                } else {
                    markers = fcn(props.SizeData)
                }
                plot.setMarkerArray(markers)
                break
            case "Visible":
                break
            case "RightData":
            case "ExtraData0":
                plot.setExtraData0(props.(obj.key))
                break
            case "UpperData":
            case "ExtraData1":
                plot.setExtraData1(props.(obj.key))
                break
            case "LeftData":
            case "ExtraData2":
                plot.setExtraData2(props.(obj.key))
                break
            case "LowerData":
            case "ExtraData3":
                plot.setExtraData3(props.(obj.key))
                break
            case "EdgeColor":
            case "Fill":
            case "LineColor":
                if (props.(obj.key) instanceof java.awt.Paint[])
                plot.("set" + obj.key)((java.awt.Paint[]) props.(obj.key))
                else if (props.(obj.key) instanceof java.awt.Paint)
                plot.("set" + obj.key)((java.awt.Paint) props.(obj.key))
                else if (props.(obj.key) instanceof ArrayList)
                plot.("set" + obj.key)(props.(obj.key))
                break
            case "Alpha":
                plot.setAlpha(props.(obj.key) as float)
                break
            case "XData":
            case "YData":
            case "ZData":
                plot.("set" + obj.key)(props.(obj.key))
                break
            case "Orientation":
                if (plot instanceof GJBar) {
                    switch (props.(obj.key)) {
                    case "horizontal":
                        plot.getDataModel().getExtraObject().setOrientation BarExtra.ORIENTATION.HORIZONTAL
                        break
                    default:
                        plot.getDataModel().getExtraObject().setOrientation BarExtra.ORIENTATION.VERTICAL
                    }
                }
                break

            case "Name":
                plot.setName(props.(obj.key) as String)
                break
            case "xName":
                plot.getXData().setName(props.(obj.key) as String)
                break
            case "yName":
                plot.getYData().setName(props.(obj.key) as String)
                break
            case "XCategories":
                if (props.(obj.key) instanceof LinkedHashMap) {
                    LinkedHashMap m = props.(obj.key)
                    for (Object val : m.keys()) {
                        plot.getXData().setCategory(val as double, m.get(val) as String)
                    }
                } else if (props.(obj.key) instanceof ArrayList) {
                    for (int k = 0; k < props.(obj.key).size(); k += 2) {
                        plot.getXData().setCategory(props.(obj.key).get(k) as double, props.(obj.key).get(k + 1) as String)
                    }
                }
                break
            case "YCategories":
                if (props.(obj.key) instanceof LinkedHashMap) {
                    LinkedHashMap m = props.(obj.key)
                    for (Object val : m.keys()) {
                        plot.getYData().setCategory(val as double, m.get(val) as String)
                    }
                } else if (props.(obj.key) instanceof ArrayList) {
                    for (int k = 0; k < props.(obj.key).size(); k += 2) {
                        plot.getYData().setCategory(props.(obj.key).get(k) as double, props.(obj.key).get(k + 1) as String)
                    }
                }
                break
            default:
                try {
                    plot.("set" + obj.key)(props.(obj.key))
                } catch (Exception ex) {
                    switch (ex.getClass()) {
                    case MissingMethodException:
                        System.err.println "WPlot: encountered a 'MissingMethodException'"
                        System.err.println "message: " + ex.message
                        System.err.println "method: " + ex.method
                        if (ex.method.contains("set")) {
                            System.err.println ""
                            System.err.println "------------------------------------------------"
                            System.err.println "Suggestions:"
                            System.err.println "Have you used'=' in your argument list thus replacing the 'key' with its 'value'?"
                            System.err.println "E.g. '[XData = 1..10, ...]' instead of '[XData: 1..10, ... ]' or '[\'XData\', 1..10, ...]'"
                        }
                        break
                    default:
                        println("WPlot processArgs: unrecognised option for " + plot.getClass() + ":"
                            + " Property: " + obj.key + " Value: " + props.(obj.key))
                        throw (ex)
                    }

                    println "Type WPlot.lastException() to view details."
                }
                break
            }
        }
        return plot
    }

    static convertColor(Object col, float alpha) {
        java.awt.Color color = convertColor(col)
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), alpha * 255f as int);
    }

    static convertColor(Object col) {

        if (col == null)
        return null

        final noColor = new Color(0, 0, 0, 0)

        // AWT Paint or Paint[] or ArrayList<AWT Paint>
        // TODO: base package should really allow ArrayList on input so that cast to array
        // could be avoided

        if (col instanceof java.awt.Paint)
        return col

        if (col instanceof ArrayList && (col[0] instanceof String || col[0] instanceof Character)) {
            ArrayList<Paint> p = new ArrayList<Paint>()
            for (String s : col) {
                p.add(convertColor(s))
            }
            return p as Paint[]
        }

        if ((col.getClass().isArray() || col instanceof ArrayList) &&
            col[0] instanceof java.awt.Paint)
        return col as java.awt.Paint[]

        // Numerical RGB or RGB+alpha array

        // Floating point: values 0-1.
        if (col.getClass().isArray() &&
            (col[0] instanceof Double || col[0] instanceof Float)) {
            col = col.collect() { (float) it }
            if (col.size() == 3)
            return new Color(col[0], col[1], col[2])
            else
            return new Color(col[0], col[1], col[2], col[3])
        }

        // Integer: values 0-255.
        if (col.getClass().isArray() && col[0] instanceof Integer) {
            col = col.collect() { (int) it }
            if (col.size() == 3)
            return new Color(col[0], col[1], col[2])
            else
            return new Color(col[0], col[1], col[2], col[3])
        }

        // Text:
        // Can be single character or string describing MATLAB-compatible colors
        // or
        // A string describing a JavaFX enumerated color e.g. "SEAGREEN"
        if (col instanceof String || col instanceof Character) {
            col = col.toString()
            switch (col) {
            case "y":
            case "yellow":
                return Color.yellow
            case "m":
            case "magenta":
                return Color.magenta
            case "c":
            case "cyan":
                return Color.cyan
            case "r":
            case "red":
                return Color.red
            case "g":
            case "green":
                return Color.green
            case "b":
            case "blue":
                return Color.blue
            case "w":
            case "white":
                return Color.white
            case "k":
            case "black":
                return Color.black
            case "none":
                return noColor
            default:
                if (kcl.waterloo.defaults.Colors.getColors().containsKey(col.toUpperCase())) {
                    return kcl.waterloo.defaults.Colors.getColor(col);
                }
            }
        }

        return java.awt.Color.darkGray

    }

    static isColor(Object col) {

        if (col == null)
        return false

        final noColor = new Color(0, 0, 0, 0)

        // AWT Paint or Paint[] or ArrayList<AWT Paint>
        // TODO: base package should really allow ArrayList on input so that cast to array could be avoided
        if (col instanceof java.awt.Paint)
        return true

        if ((col.getClass().isArray() || col instanceof ArrayList) &&
            col[0] instanceof java.awt.Paint)
        return true

        // Numerical RGB or RGB+alpha array

        // Floating point: values 0-1.
        if (col.getClass().isArray() &&
            (col[0] instanceof Double || col[0] instanceof Float)) {
            col = col.collect() { (float) it }
            if (col.size() == 3 || col.size() == 4)
            return true
            else
            return false
        }

        // Integer: values 0-255.
        if (col.getClass().isArray() && col[0] instanceof Integer) {
            col = col.collect() { (int) it }
            if (col.size() == 3 || col.size() == 4)
            return true
            else
            return false
        }

        // Text:
        // Can be single character or string describing MATLAB-compatible colors
        // or
        // A string describing a JavaFX enumerated color e.g. "SEAGREEN"
        if (col instanceof String || col instanceof Character) {
            col = col.toString()
            switch (col) {
            case "y":
            case "yellow":
            case "m":
            case "magenta":
            case "c":
            case "cyan":
            case "r":
            case "red":
            case "g":
            case "green":
            case "b":
            case "blue":
            case "w":
            case "white":
            case "k":
            case "black":
                return true
            case "none":
                return true
            default:
                if (kcl.waterloo.defaults.Colors.getColors().containsKey(col.toUpperCase())) {
                    if (kcl.waterloo.defaults.Colors.getColor(col) != null)
                    return true
                }
            }
        }
        return false
    }

    /**
     * Thread-safe method to creates a GCFrame containing this instance.
     * @return the GCFrame
     */
    GCFrame createFrame() {
        GCFrame f;
        new SwingBuilder().edt {
            f = new GCFrame()
            GJGraph gr = GJGraph.createInstance()
            GJGraphContainer grc = GJGraphContainer.createInstance(gr)
            grc.getView().add(plot)
            f.add(grc)
            f.setSize(500, 500)
            f.setVisible(true)
            gr.autoScale()
        }
        return f
    }

    GJGraphContainer createGraph() {
        GJGraphContainer grc
        new SwingBuilder().edt {
            grc = GJGraphContainer.createInstance(GJGraph.createInstance())
            grc.getView().add(plot)
        }
        return grc
    }

    // Utility static methods

    /**
     * Range method returns a row vector of values
     * Range can be used instead of the "range operator" which differs both in syntax and in its output
     * between environments.
     * Calls <code>range(String start, "1", String stop)</code>
     * @param start value as a String
     * @param stop value as a String
     * @return a double[1][n] vector of values from start to <=stop in steps of 1.
     */
    static double[][] range(String start, String stop) {
        return range(start, "1", stop);
    }

    /**
     * Range method returns a row vector of values
     * Range can be used instead of the "range operator" which differs both in syntax and in its output
     * between environments.
     * Calls <@code range(BigDecimal e1, BigDecimal inc, BigDecimal e2)>
     * @param start value as a String
     * @param step the step size as a String
     * @param stop value as a String
     * @return a double[1][n] vector of values from start to <=stop in the specified steps
     */
    static double[][] range(String start, String step, String stop) {
        BigDecimal e1 = new BigDecimal(start)
        BigDecimal e2 = new BigDecimal(stop)
        BigDecimal inc = new BigDecimal(step)
        return range(e1, inc, e2)
    }

    /**
     * Range method returns a row vector of values within the specified limits
     * Range can be used for consistency instead of a "range operator" which differ in syntax and in
     * implementation between environments.
     * The method here uses BigDecimal arithmetic internally to avoid rounding errors and casts the result to
     * IEEE754 64-bit doubles only at the final stage. This should avoid cumulative errors associated
     * with inexact IEEE754 step sizes and ensure symmetry of the returned vector (when it should be symmetrical).
     *
     * See The Mathworks Technical Solution 1-4FLI96 for a discussion of the background and example of how ":" works
     * there.
     *
     * @param e1 start value
     * @param inc increment
     * @param e2 stop value
     * @return a double[1][n] vector of values from start to <=stop in the specified steps
     */
    static double[][] range(BigDecimal e1, BigDecimal inc, BigDecimal e2) {
        ArrayList<BigDecimal> result = new ArrayList<BigDecimal>()
        if (inc.compareTo(BigDecimal.ZERO) < 0) {
            for (BigDecimal rr = e1; rr >= e2; rr += inc) {
                result.add(rr)
            }
        } else {
            for (BigDecimal rr = e1; rr <= e2; rr += inc) {
                result.add(rr)
            }
        }
        return [result.toArray() as double[]]
    }

    static Object gxgcf() {
        return GJEventManager.getInstance().getGxgcf()
    }

    static Object gxgca() {
        return GJEventManager.getInstance().getGxgca()
    }

    static Object gxgco() {
        return GJEventManager.getInstance().getGxgco()
    }


    static void lastException() {
    }

    /**
     * Returns a CompilerConfiguration that can be used to set up
     * kcl.waterloo.shell.GShell to recognize the kcl.waterloo.plot
     * classes.
     * This is equivalent to adding:
     *
     * <code>import kcl.waterloo.plot.*</code>
     *
     * to the start of each script.
     */
    static CompilerConfiguration getCompilerConfiguration() {
        def importCustomizer = new ImportCustomizer()
        //importCustomizer.addStarImport 'kcl.waterloo.plot'
        importCustomizer.addImports 'kcl.waterloo.plot.WPlot'
        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(importCustomizer)
        return configuration
    }


}








