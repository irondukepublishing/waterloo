# Use a 1x2 grid to display two charts:
# This code is specific to R
# For better portability, place the call to WPlot inside the GShell.eval
# using Groovy syntax.
#
# The variables "p2" and "p3" exists in the R workspace after the call and
# contains a reference to a Waterloo WPlot class instance.

p2=WPlot.scatter(list(xData= mtcars[,6]*1000, yData= mtcars[,1],
                      xname= "Weight (lbs)", yname="Miles per gallon"))
p3=WPlot.scatter(list(xData= mtcars[,6]*1000, yData= mtcars[,3],
                      xname= "Weight (lbs)", yname="Displacement (cu.in.)"))


# This code would be portable if "row.names(mtcars)" were understood in
# the support environment. The arguments will be made available 
# for use by Groovy or Java code running in GShell.
GShell.setVariable('p2', p2)
GShell.setVariable('p3', p3)

GShell.evalEDT('
            import kcl.waterloo.graphics.GJGraphContainer
            import kcl.waterloo.graphics.GJGraph
            def f=p2.createFrame()
            f.setSize(800,400)
            def g2=p3.createGraph()
            f.add(g2, 0d, 1d, 1d, 1d, 0)
            g2.getView().autoScale()  
            f.revalidate()
            f.repaint()
               ')
