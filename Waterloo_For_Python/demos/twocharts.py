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
# THIS CODE IS NOT THREAD-SAFE.
# No effort is made here to run the Swing code on the Java
# event dispatch thread (EDT). To do that, use GShell.
############################################################

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

# Set up some data
t = np.arange(0.01, 10.0, 0.02)
s1 = np.exp(t)
s2 = np.sin(2*np.pi*t)

# Create two plots
p1=WPlot.line(['xData',t,'yData', s1, 'lineColor', 'b'])
p2=WPlot.scatter(['xData',t,'yData', s2, 'fill', 'lightGray'])
p2=p2.plus(WPlot.line(['lineStyle', '.']))

# Draw the first plot by creating a frame
# The createFrame method is thread-safe
f=p2.createFrame()

########################################################
# From here, properties of Swing components are changed
# off the EDT. This is quite likely to work OK from a Python
# console session, but is not reliable. Avoid this in
# 'real' work.
########################################################
#
# Access the host graph from the plot reference
g1=p2.getPlot().getParentGraph()
# Add a second graph as a child of the first - these can have separate
# axes
g2=gateway.jvm.kcl.waterloo.graphics.GJGraph.createInstance()
g1.add(g2)
# Add the second plot to the second graph
g2.add(p1.getPlot())
# Set the axes limits
g2.setAxesBounds(0.,0.,10.,25000.)
g1.setAxesBounds(0.,-1.,10.,2.)
# ... and one x-axis as they are the same
g2.setBottomAxisPainted(False)
g1.setXLabel('t')
g1.setYLabel('sin(t)')
g2.setYLabel('exp(t)')

# Try to bring the frame to the front
f.toFront()
#Clear referecne
WPlot=None