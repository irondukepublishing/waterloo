import numpy as np

# Means values in a vector for 5 treatment groups G1 through G5.
# Values for men and women alternate to give the 10 values.
Means = (20, 23, 32, 34, 35, 30, 20, 35, 27, 25)
# To show the error bars we need the cumulative sum of the
#  two means for each group.
StackPos= (20, 43, 32, 66, 35, 65, 20, 55, 27, 52)
# Errors
SD = (2, 3, 5, 3, 4, 2, 1, 3, 2, 3)

# Set up the gateway - this connects to the GraphExplorer.jar JVM
# which needs to be running
from py4j.java_gateway import JavaGateway
gateway=JavaGateway(auto_convert=True)
WPlot=gateway.jvm.kcl.waterloo.plot.WPlot
# Create the error plot...
p1=WPlot.errorbar(['xData', [1,1,3,3,5,5,7,7,9,9],'yData', StackPos, \
                   'extraData1', SD, 'lineColor', ['y', 'b'], \
                   'xCategories', [1,"G1", 3, "G2", 5, "G3", 7, "G4", 9, "G5"]])
# ...and the bars
p2=WPlot.bar(['xData', np.arange(1,11),'yData', Means, 'fill', ['y','b'], \
              'mode', gateway.jvm.kcl.waterloo.graphics.plots2D.BarExtra.MODE.STACKED])
# Add the bars to the errors. Errors will then be rendered last and be
# superimposed on the bars
p1.getPlot().add(p2.getPlot())


# Create the frame
p1.createFrame().setAlwaysOnTop(True)
# Turn off the grids...
p1.getPlot().getParentGraph().setMajorGridPainted(False)
p1.getPlot().getParentGraph().setMinorGridPainted(False)
# Adjust the labelling

p1.getPlot().getParentGraph().setXLabel('Group')
p1.getPlot().getParentGraph().setYLabel('Score')
p1.getPlot().getParentGraph().setAxesBounds(0., 0., 9.8, 75.)



WPlot=None




