#--Define data file directory:
dir <- "http://www.sr.bham.ac.uk/~ajrs/papers/sanderson06"

#--Read in table of data (from Sanderson et al. 2006):
# This refers to a sample of 20 clusters of galaxies with Chandra X-ray data
CC <- read.table(paste(dir, "mean_Tprofile-CC.txt", sep="/"), header=TRUE)
nCC <- read.table(paste(dir, "mean_Tprofile-nCC.txt", sep="/"), header=TRUE)

x1=CC[,'r.r500']
p2=WPlot.line(list(xData=x1,
	 yData= CC[,'sckT'],
       xname= "Radius (R500)",
	 yname="Scaled Temperature",
	 color="DARKBLUE"))
p2u=WPlot.line(list(xData=x1,yData= CC[,'sckT.up'], color="none"))
p2l=WPlot.line(list(xData=x1,yData= CC[,'sckT.lo'], color="none"))


x2=nCC[,'r.r500']
p3=WPlot.line(list(xData= x2, yData= nCC[,'sckT'], color="DARKORANGE"))
p3u=WPlot.line(list(xData= x2,yData= nCC[,'sckT.up'], color="none"))
p3l=WPlot.line(list(xData= x2,yData= nCC[,'sckT.lo'], color="none"))



# Add the plots as independent children of a single graph - this is needed for
# area fills
GShell.setVariable('p2', p2)
GShell.setVariable('p2u', p2u)
GShell.setVariable('p2l', p2l)
GShell.setVariable('p3', p3)
GShell.setVariable('p3u', p3u)
GShell.setVariable('p3l', p3l)


GShell.evalEDT('
			import kcl.waterloo.graphics.plots2D.GJFill
            // Create a frame
			def f=p2.createFrame()
			// Reference to the created graph
			def gr=p2.getPlot().getParentGraph();
			// Add the other plots and fills. 
            gr.add(p2u.getPlot())
            gr.add(p2l.getPlot())
            f.setSize(800,700)
            p2l.getPlot().setAreaFill(p2u.getPlot())
            p2l.getPlot().setAreaPaint(kcl.waterloo.plot.WPlot.convertColor("BLUE",0.5f))
		    gr.add(p3.getPlot())
            gr.add(p3u.getPlot())
            gr.add(p3l.getPlot())
            p3l.getPlot().setAreaFill(p3u.getPlot())
			p3l.getPlot().setAreaPaint(kcl.waterloo.plot.WPlot.convertColor("ORANGE",0.75f))
			// Set the transforms on the axes
			gr.setXTransform(kcl.waterloo.graphics.transforms.Log10Transform.getInstance())
			gr.setYTransform(kcl.waterloo.graphics.transforms.Log10Transform.getInstance())
			// Scale and repaint
			gr.autoScale()
            gr.revalidate()
            gr.repaint()
               ')
