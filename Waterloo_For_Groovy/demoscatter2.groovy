import groovy.swing.SwingBuilder
import kcl.waterloo.marker.GJMarker
import kcl.waterloo.plot.WPlot
import kcl.waterloo.shell.GShell


/**
* Use WPlot for these examples
* Create a scatter plot
*/
def w1=WPlot.scatter('XData': 1..10,
      'YData': 1..10,
      'Marker': GJMarker.circle(5))
/**
* Add a line to it. The data can be shared
*/
def w2=WPlot.line()
w1+=w2

def w3=WPlot.scatter('YData': [1,3,5,7,9,11,13,15,17,19],
      'Marker': GJMarker.square(5))
def w4=WPlot.line()
w3+=w4

w1+=w3

/**
* Display the graph and scale it
*/
new SwingBuilder().edt{
w1.createFrame()
w1.getPlot().getParentGraph().autoScale()
}

/**
 *Add some labeling
 */
w1.getPlot().getParentGraph().setXLabel("X")
w1.getPlot().getParentGraph().setYLabel("Y")
w1.getPlot().getParentGraph().getGraphContainer().setTitleText("Simple X-Y plot")


/**
* autoScale returns null - so explicitly tell the script to return 
* a reference to the plot
*/
return w1
