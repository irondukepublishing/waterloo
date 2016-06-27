# Import Numpy...
import numpy as np
# ... and the Py4j gateway
from py4j.java_gateway import JavaGateway
# Set up the gateway - this connects to the GraphExplorer.jar JVM
# which needs to be running
gateway=JavaGateway(auto_convert=True)

# Use WPlot for this example - need to import this from
# the gateway JVM
WPlot=gateway.jvm.kcl.waterloo.plot.WPlot


x = np.linspace(0, 1)
y = np.sin(4 * np.pi * x) * np.exp(-5 * x)

p = WPlot.line(['xData', x, 'yData', y])
# Create a fill object - this will fill the area between the line and zero
f=gateway.jvm.kcl.waterloo.graphics.plots2D.GJFill(p.getPlot(), 0.0)
# The color for the fill
f.setAreaPaint(gateway.jvm.java.awt.Color(0.,1.,0.,0.5))
# Fill horizontally
f.setOrientation(gateway.jvm.kcl.waterloo.graphics.plots2D.GJFill.ORIENTATION.HORIZONTAL)
# Add it to the plot
p.getPlot().setAreaFill(f);
# Draw it
p.createFrame()

#Clear reference
WPlot=None
