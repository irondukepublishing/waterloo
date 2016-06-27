# This code is specific to R
# It calls into the "WPlot.component" function in R which in turns
# calls the compiled WPlot Groovy class component method 
# which is part of the Waterloo distribution.
# The "WPlot.component" function in R formats the list provided as input.
# In MATLAB, the arguments list would need to be presented as
# a cell array WPlot.component('xData', mtcars(:,6) etc....)
# which is why this code is less portable.
#
# For better portability, place the call to WPlot inside the GShell.eval
# using Groovy syntax.
#
# The variable "p2" exists in the R workspace after the call and
# contains a reference to a Waterloo WPlot class instance.
p2=WPlot.component(list(xData= mtcars[,6]*1000, yData= mtcars[,1],
	xname= "Weight (lbs)", yname="Miles per gallon"))
	

# This code would be portable if "row.names(mtcars)" were understood in
# the support environment. The arguments will be made available 
# for use by Groovy or Java code running in GShell.
GShell.setVariable('rownames', row.names(mtcars))
GShell.setVariable('p2', p2)

# Except for the use of line-breaks, this code can be cut-and-pasted
# into MATLAB, SciLab etc. To avoid incompatibilities due to line-breaks,
# either:
# [1] put everything on a single line e.g. using the GShell.editor
# [2] split it into multiple calls to GShell.evalEDT
# [3] run a .groovy script file instead.
GShell.evalEDT('
    import kcl.waterloo.gui.images.Images
	import kcl.waterloo.widget.GJButton
	def thisfont=new java.awt.Font("sans serif",java.awt.Font.PLAIN,8);
	p2.getPlot().setComponents(new kcl.waterloo.widget.GJButton());
	p2.createFrame()
	for (def k=0; k<rownames.length;k++){
	p2.getPlot().componentArray[k].setPreferredSize(new java.awt.Dimension(30,30))
	p2.getPlot().componentArray[k].setFileName(k+1 + ".png")
	p2.getPlot().componentArray[k].setToolTipText(String.format("<HTML><STRONG>%d: %s</STRONG><BR><EM>Click for link</EM></HTML>", k+1, rownames[k]))
	// This adds a URL descripton string as the actionCommand
	p2.getPlot().componentArray[k].setActionCommand(String.format("http://www.wikipedia.org/?search=%s", rownames[k].replace(" ", "%20")))
	p2.getPlot().componentArray[k].addActionListener(kcl.waterloo.actions.ComponentActionListener.getInstance())
	};
	p2.getPlot().getParentGraph().getGraphContainer().setTitleText("Relationship between vehicle weight and fuel consumption")
	p2.getPlot().getParentGraph().getGraphContainer().setSubTitleText("Hover over a data point with the mouse to see its origin and click for its Wikipdiedia entry")
	p2.getPlot().getParentGraph().setMajorGridPainted(false)
	p2.getPlot().getParentGraph().setMinorGridPainted(false)
	')
