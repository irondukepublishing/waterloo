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

import kcl.waterloo.annotation.GJAnnotation
import kcl.waterloo.graphics.GJUtilities
import javax.swing.SwingConstants

/**
 * Created with IntelliJ IDEA.
 * User: ML
 * Date: 12/08/2012
 * Time: 01:02
 * To change this template use File | Settings | File Templates.
 */
class WAnnotation {

    GJAnnotation annotation = null

    WAnnotation(GJAnnotation thisAnnotation) {
        annotation = thisAnnotation
    }

    // Individual static methods for each plot type

    static WAnnotation line(options) {
        def props = process(options)
        def data = getData(props)
        def annot
        switch (data[0].length) {
            case 2:
                annot = GJAnnotation.createLine(data[0][0], data[1][0], data[0][1], data[1][1])
                break
            case 3:
                annot = GJAnnotation.createLine(data[0][0], data[1][0], data[0][1], data[1][1], data[0][2], data[1][2])
                break
            default:
                println("No construction")
                break
        }
        return new WAnnotation(processArgs(annot, props));
    }

    static WAnnotation arrow(options) {
        def props = process(options)
        def data = getData(props)
        def annot
        switch (data[0].length) {
            case 2:
                annot = GJAnnotation.createArrow(data[0][0], data[1][0], data[0][1], data[1][1],
                        props.HeadWidth, props.HeadLength)
                break
            case 3:
                annot = GJAnnotation.createArrow(data[0][0], data[1][0], data[0][1], data[1][1], data[0][2], data[1][2],
                        props.HeadWidth, props.HeadLength)
                break
            default:
                println("No construction")
                break
        }
        return new WAnnotation(processArgs(annot, props));
    }

    static WAnnotation text(options) {
        def props = process(options)
        def data = getData(props)
        def annot
        annot = GJAnnotation.createText(data[0][0], data[1][0], props.Text)
        return new WAnnotation(processArgs(annot, props));
    }

    static WAnnotation box(options) {
        def props = process(options)
        def data = getData(props)
        def annot
        annot = GJAnnotation.createBox(data[0][0], data[1][0], props.Width, props.Height)
        return new WAnnotation(processArgs(annot, props));
    }

    static WAnnotation ellipse(options) {
        def props = process(options)
        def data = getData(props)
        def annot
        annot = GJAnnotation.createEllipse(data[0][0], data[1][0], props.Width, props.Height)
        return new WAnnotation(processArgs(annot, props));
    }



    static WAnnotation shape(options) {
        def props = process(options)
        def annot
        annot = GJAnnotation.createShape(props.Shape)
        return new WAnnotation(processArgs(annot, props));
    }

    private static process(options) {
        return preParseArgs(options);
    }

    static double[][] getData(props) {
        return [props.XData, props.YData, props.Width, props.Height]
    }

    static preParseArgs = {options ->
        if (options == null) {
            return null
        } else if (options instanceof LinkedHashMap) {
//            if (options.containsKey("LineColor"))
//                options.LineColor = WPlot.convertColor(options.get("LineColor"))
//            if (options.containsKey("EdgeColor"))
//                options.TextColor = WPlot.convertColor(options.get("TextColor"))
//            if (options.containsKey("TextBackgroundColor"))
//                options.TextBackgroundColor = WPlot.convertColor(options.get("TextBackgroundColor"))
//            if (options.containsKey("Fill"))
//                options.Fill = WPlot.convertColor(options.get("Fill"))
            return options
        } else {
            return parseArgs(options)
        }
    }

    static parseArgs(Object[] options) {
        def props = new LinkedHashMap<String, Object>()
        if (options != null) {
            for (int k = 0; k < options.length; k += 2) {
                switch (options[k].toString().toLowerCase()) {
                    case "alpha":
                        props.Alpha = (float) options[k + 1]
                    case "color":
                        props.Color = WPlot.convertColor(options[k + 1]);
                        break
                    case "edgecolor":
                        props.EdgeColor = WPlot.convertColor(options[k + 1]);
                        break
                    case "fill":
                        props.Fill = WPlot.convertColor(options[k + 1]);
                        break
                    case "font":
                        props.Font = options[k + 1]
                    case "headlength":
                        props.HeadLength = options[k + 1] * 0.5
                        break
                    case "headwidth":
                        props.HeadWidth = options[k + 1]
                        break
                    case "headstyle":
                        break
                    case "linecolor":
                        props.LineColor = WPlot.convertColor(options[k + 1])
                        break
                    case "linewidth":
                        props.LineWidth = options[k + 1]
                        break
                    case "linetextposition":
                        if (options[k + 1] instanceof String) {
                            props.LineTextPosition = SwingConstants.(options[k + 1])
                        } else {
                            props.LineTextPosition = options[k + 1] as int
                        }
                        break
                    case "linestyle":
                        props.LineStyle = options[k + 1];
                        break
                    case "position":
                        def data = options[k + 1] as double[]
                        props.XData = [data[0], data[0] + data[2]] as double[]
                        props.YData = [data[1], data[1] + data[3]] as double[]
                    case 'shape':
                        props.Shape = options[k + 1]
                        break
                    case "string":
                    case "text":
                        props.Text = options[k + 1]
                        break
                    case "textcolor":
                        props.TextColor = WPlot.convertColor(options[k + 1])
                        break
                    case "textbackground":
                        props.TextBackground = WPlot.convertColor(options[k + 1])
                        break
                    case "xdata":
                        props.XData = options[k + 1] as double[]
                        break
                    case "ydata":
                        props.YData = options[k + 1] as double[]
                        break
                    case "width":
                        props.Width = options[k + 1] as double
                        break
                    case "height":
                        props.Height = options[k + 1] as double
                        break
                    default:
                        //Ignore
                        break
                }
            }
        }
        return props
    }

    public static processArgs(GJAnnotation annotation, Map props) {

        if (props == null) {
            return annotation
        }

        // Process props arguments
        for (obj in props) {
            switch (obj.key) {
                case "Alpha":
                    annotation.setAlpha(props.Alpha)
                    break
                case "Color":
                    if (props.Color instanceof java.awt.Paint[])
                        props.Color = (java.awt.Paint[]) props.Color
                    else if (props.(obj.key) instanceof java.awt.Paint)
                        props.Color = (java.awt.Paint) props.Color
                    annotation.setTextColor(props.Color)
                    annotation.setLineColor(props.Color)
                    annotation.setFill(props.Color)
                    break
                case "Font":
                    annotation.setFont(props.Font)
                    break
                case "LineWidth":
                case "LineStyle":
                    Float width = props.LineWidth
                    if (width == null) {width = 1.5f}
                    String style = props.LineStyle
                    if (style == null) {style = "-"}
                    annotation.setLineStroke(GJUtilities.makeStroke(width, style));
                    break
                case "LineTextPosition":
                    annotation.setLineTextPosition(props.(obj.key))
                    break
                case "Text":
                    annotation.setText(props.Text);
                case "Visible":
                    break
                case "EdgeColor":
                case "Fill":
                case "LineColor":
                case "TextColor":
                case "TextBackground":
                    if (props.(obj.key) instanceof java.awt.Paint[])
                        annotation.("set" + obj.key)((java.awt.Paint[]) props.(obj.key))
                    else if (props.(obj.key) instanceof java.awt.Paint)
                        annotation.("set" + obj.key)((java.awt.Paint) props.(obj.key))
                    else
                        annotation.("set" + obj.key)(props.(obj.key))
                    break
                case "Text":
                    annotation.setText(props.(obj.key))
                case "XData":
                case "YData":
                case "Width":
                case "Height":
                case "HeadWidth":
                case "HeadLength":
                case "Shape":
                    break
                default:
                    println("Unrecognised option" + obj.key)
                    break
            }
        }
        return annotation
    }

}
