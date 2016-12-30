/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.demo;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import javax.swing.JFrame;
import kcl.waterloo.graphics.plots2D.GJLine;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.graphics.plots2D.GJScatter;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.xml.GJDecoder;

/**
 *
 * @author ML
 */
public class Demo {

    private final static TestDataManager data = TestDataManager.getInstance();

    private Demo() {
    }

    public static JFrame demo1() {
        GJPlotInterface plot = GJScatter.createInstance();
        plot.setXData(data.x1);
        plot.setYData(data.y1);
        plot = plot.add(GJLine.createInstance());
        JFrame f = plot.createFrame();
        f.setVisible(true);
        f.repaint();
        return f;
    }

    public static void demo2() {
        GJPlotInterface plot = GJScatter.createInstance();
        plot.setXData(data.x1);
        plot.setYData(data.y1);
        plot.setMarker(0, GJMarker.triangle(5));
        plot.setEdgeColor(Color.blue);
        plot.setFill(Color.yellow);
        GJPlotInterface plot2 = GJLine.createInstance();
        plot = plot.add(plot2);
        plot2.setLineColor(Color.blue);
        plot.createFrame();
    }

    public static void demo3() {
        GJPlotInterface plot = GJScatter.createInstance();
        plot.setXData(data.x1);
        plot.setYData(data.y1);
        plot.setMarkerArray(new GJMarker[]{GJMarker.circle(5), GJMarker.triangle(5), GJMarker.square(5)});
        plot.setFill(new Color[]{Color.blue, Color.blue, Color.blue, Color.green, Color.green, Color.green});
        GJPlotInterface plot2 = GJLine.createInstance();
        plot = plot.add(plot2);
        plot2.setLineColor(Color.blue);
        plot.createFrame();
    }

    public static void demo4() {
        GJPlotInterface plot = GJScatter.createInstance();
        double[] d = data.y1.clone();
        for (int k = 0; k < d.length; k += 6) {
            d[k + 1] += 2;
            d[k + 2] += 4;
            d[k + 3] += 6;
            d[k + 4] += 8;
            d[k + 5] += 10;
        }
        plot.setXData(data.x1);
        plot.setYData(d);
        plot.setMarkerArray(new GJMarker[]{GJMarker.circle(5), GJMarker.triangle(5), GJMarker.square(5)});
        plot.setFill(new Color[]{Color.blue, Color.blue, Color.blue, Color.green, Color.green, Color.green});
        GJPlotInterface plot2 = GJLine.createInstance();
        plot = plot.add(plot2);
        plot2.setLineColor(new Color[]{Color.cyan, Color.magenta, Color.orange, Color.red, Color.yellow, Color.blue});
        plot.createFrame();
    }

    public static Object file1() {
        try {
            InputStream buffer;
            buffer = Demo.class.getResourceAsStream("resources/Demo1.gz.xml");
            buffer = new GZIPInputStream(buffer);
            return GJDecoder.load("Demo1", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file2() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo2.gz.xml"));
            return GJDecoder.load("Demo2", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file3() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo3.gz.xml"));
            return GJDecoder.load("Demo3", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file4() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo4.gz.xml"));
            return GJDecoder.load("Demo4", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file5() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo5.gz.xml"));
            return GJDecoder.load("Demo5", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file6() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo6.gz.xml"));
            return GJDecoder.load("Demo6", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file7() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo7.gz.xml"));
            return GJDecoder.load("Demo7", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static Object file8() {
        try {
            InputStream buffer;
            buffer = new GZIPInputStream(Demo.class.getResourceAsStream("resources/Demo8.gz.xml"));
            return GJDecoder.load("Demo8", buffer);
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }
}
