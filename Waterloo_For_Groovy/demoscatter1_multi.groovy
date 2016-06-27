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

/*
 * Add two more lines
 */
def w3=WPlot.scatter('YData': [1, 3, 5, 7, 9, 11, 13, 15, 17, 19],
        'Marker': GJMarker.triangle(5))
w3+=WPlot.line()
w1+=w3

def w5=WPlot.scatter('YData': [1, 5, 9, 13, 17, 21, 25, 29, 34, 38],
        'Marker': GJMarker.square(5))
w5+=WPlot.line()
w1+=w5

/**
 * Display the graph and scale it
 */
def frame = w1.createFrame()
frame.setSize(400, 300);
w1.getPlot().getParentGraph().autoScale()

/**
 *Add some labeling
 */
w1.getPlot().getParentGraph().setXLabel("X Label")
w1.getPlot().getParentGraph().setYLabel("Y Label")
w1.getPlot().getParentGraph().getGraphContainer().setTitleText("Simple X-Y plot")


/**
 * autoScale returns null - so explicitly tell the script to return
 * a reference to the plot
 */
return w1





