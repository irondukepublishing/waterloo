imimport kcl.waterloo.marker.GJMarker
import kcl.waterloo.plot.WPlot

/**
 * Use WPlot for these examples
 * Create a scatter plot
 */
def w1=WPlot.scatter('XData': -5..5,
        'YData': -5..5,
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
w1.getPlot().getParentGraph().autoScale()


w1.getPlot().getParentGraph().setLeftAxisPainted(false)
w1.getPlot().getParentGraph().setBottomAxisPainted(false)
w1.getPlot().getParentGraph().setInnerAxisPainted(true)
w1.getPlot().getParentGraph().setInnerAxisLabelled(true)

/**
 *Add some labeling
 */
w1.getPlot().getParentGraph().getGraphContainer().setTitleText("Simple X-Y plot")
/**
 * autoScale returns null - so explicitly tell the script to return
 * a reference to the plot
 */
return w1




