import numpy as np
import matplotlib.mlab as mlab
from matplotlib import _cntr as cntr


delta = 0.025
x = np.arange(-3.0, 3.0, delta)
y = np.arange(-2.0, 2.0, delta)
X, Y = np.meshgrid(x, y)
Z1 = mlab.bivariate_normal(X, Y, 1.0, 1.0, 0.0, 0.0)
Z2 = mlab.bivariate_normal(X, Y, 1.5, 0.5, 1, 1)
Z = 10.0 * (Z2 - Z1)
levels = np.arange(-1.2, 1.6, 0.2)

# Set up the gateway - this connects to the GraphExplorer.jar JVM
# which needs to be running
from py4j.java_gateway import JavaGateway
gateway=JavaGateway(auto_convert=True)

p=gateway.jvm.kcl.waterloo.plot.WPlot.contour(None)
contourObject=gateway.jvm.kcl.waterloo.graphics.plots2D.contour.ContourExtra.createInstance()
# Set up the contours:

# Generate the contour lines using matplotlib
CS = cntr.Cntr(X,Y,Z)
for k in np.arange(1,len(levels)) :
    list=CS.trace(levels[k])
    if len(list)>0 :
        contourObject.addContour(list.pop(0), levels[k])

# Turn off filling of contours - it's on by default
p.getPlot().setFilled(False)
#  Fill clipping should be off for Python-generated contours
# (if setFilled is true).
p.getPlot().setFillClipping(False)

# The ContourExtra object has some non-standard properties that
# cause the "normal" line color settings to be ignored and paint all
# positive and all negative levels to be painted with the set color...
contourObject.setNegativeLineColor(gateway.jvm.java.awt.Color.BLUE.darker())
contourObject.setPositiveLineColor(gateway.jvm.java.awt.Color.ORANGE.darker())
# ... and the zero line to be painted at a different thickness
contourObject.setZeroStroke(gateway.jvm.java.awt.BasicStroke(2.0))

p.getPlot().getDataModel().setExtraObject(contourObject)

p.getPlot().setLabelled(True)

p.createFrame()





