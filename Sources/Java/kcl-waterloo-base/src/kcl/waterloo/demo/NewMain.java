/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kcl.waterloo.demo;

import java.util.Arrays;
import kcl.waterloo.graphics.GJGraph;
import kcl.waterloo.graphics.GJGraphContainer;
import kcl.waterloo.graphics.plots2D.GJLine;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.swing.GCFrame;

/**
 *
 * @author ML
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     GJPlotInterface linePlot = GJLine.createInstance();

        linePlot.setXData(new double[]{1});
        linePlot.setYData(new double[]{0});

        GJGraph graph = GJGraph.createInstance();

        graph.add(linePlot);

        GJGraphContainer graphContainer = GJGraphContainer.createInstance(graph);
        GCFrame frame = graphContainer.createFrame();
        frame.pack();
        frame.setVisible(true);

        double x = 1;
        double y = 1;
        while (x < 10.1){
            double[] yData = Arrays.copyOf(linePlot.getYData().getRawDataValues(), linePlot.getYData().getRawDataValues().length + 1);
            yData[yData.length - 1] = x;
            linePlot.setYData(yData);

            double[] xData = Arrays.copyOf(linePlot.getXData().getRawDataValues(), linePlot.getXData().getRawDataValues().length + 1);
            xData[xData.length - 1] = x++;
            linePlot.setXData(xData);

            graph.autoScale();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
    }
    
}
