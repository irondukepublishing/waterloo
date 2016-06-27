#
# This code is part of Project Waterloo from King's College London
# <http://waterloo.sourceforge.net/>
#
# Copyright King's College London 2012-
#
# @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#

# This demo requires numpy
#
############################################################
# THIS CODE IS THREAD-SAFE.
############################################################

# Import Numpy...
import numpy as np
# ... and the Py4j gateway
from py4j.java_gateway import JavaGateway
# Set up the gateway - this connects to the GraphExplorer.jar JVM
# which needs to be running
gateway=JavaGateway(auto_convert=True)


# Set up some data
t = np.arange(0.01, 10.0, 0.02)
s1 = np.exp(t)
s2 = np.sin(2*np.pi*t)

# Get a reference to the shell
GShell=gateway.jvm.kcl.waterloo.shell.GShell
# Save then clear the variables list...
GShell.push()
# ... and add those created above
GShell.setVariable('t', t)
GShell.setVariable('s1', s1)
GShell.setVariable('s2', s2)

# Now plot the graphs on the EDT
# This uses the Python line continuation marker "\" so will not work elsewhere -
# but the code is Groovy so could be placed in a .groovy script file
# and run using GShell.runEDT(fileName). That would be portable.

# We use invokeAndWait so the context can be reset after completion.
# Using evalLater here would probably fail because the GShell.pull
# call below would be executed before this code completes on the EDT.
GShell.evalEDT('import kcl.waterloo.plot.WPlot; \
def p1=WPlot.line(["xData",t,"yData", s1, "lineColor", "b"]); \
def p2=WPlot.scatter(["xData",t,"yData", s2, "fill", "lightGray"]); \
p2+=WPlot.line(["lineStyle", "-"]); \
def f=p2.createFrame(); \
def g1=p2.getPlot().getParentGraph(); \
def g2=kcl.waterloo.graphics.GJGraph.createInstance(); \
g1.add(g2);g2.add(p1.getPlot()); \
g1.setMajorGridPainted(false); \
g1.setMinorGridPainted(false); \
g2.setBottomAxisPainted(false); \
g2.setAxisColor(java.awt.Color.BLUE); \
g2.getLeftAxisPanel().getLabel().setForeground(java.awt.Color.BLUE); \
g1.setXLabel("t"); \
g1.setYLabel("sin(t)"); \
g2.setYLabel("exp(t)"); \
g2.setAxesBounds(0,0,10,25000); \
g1.setAxesBounds(0,-1,10,2);')
# Restore the shell context.
GShell.pull()
# Clear reference
GShell=None

