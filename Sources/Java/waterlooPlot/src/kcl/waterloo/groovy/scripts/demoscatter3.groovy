import kcl.waterloo.marker.GJMarker
import kcl.waterloo.plot.WPlot

println "************************************************************"
println "This script requires that variable x and y are set externally"
println "It will fail otherwise."
println "************************************************************"
/**
* Use WPlot for these examples
* Create a scatter plot
*/
def w1=WPlot.scatter('XData': x,
      'YData': y,
      'Marker': GJMarker.circle(5))
/**
* Add a line to it. The data can be shared
*/
def w2=WPlot.line()
w1+=w2

/**
* Display the graph and scale it
*/
w1.createFrame()
w1.getPlot().getParentGraph().autoScale()

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
