import kcl.waterloo.graphics.transforms.*
import kcl.waterloo.marker.GJMarker
import kcl.waterloo.plot.WPlot

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

/**
* Display the graph and scale it
*/
def frame = w1.createFrame()
frame.setSize(400, 300);

/**
 *Add some labeling
 */
w1.getPlot().getParentGraph().setXLabel("X Label")
w1.getPlot().getParentGraph().setXTransform(Log10Transform.getInstance())
w1.getPlot().getParentGraph().autoScale()
w1.getPlot().getParentGraph().setYLabel("Y Label")
w1.getPlot().getParentGraph().getGraphContainer().setTitleText("Simple X-Y plot")



/**
* autoScale returns null - so explicitly tell the script to return 
* a reference to the plot
*/
return w1



