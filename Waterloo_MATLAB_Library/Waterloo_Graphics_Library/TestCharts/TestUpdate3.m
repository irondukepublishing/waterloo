function f=TestUpdate3()
% TestUpdate1 illustrates the use of a callback to update a graph.
%
% ---------------------------------------------------------------------
% Part of the sigTOOL Project and Project Waterloo from King's College
% London.
% http://sigtool.sourceforge.net/
% http://sourceforge.net/projects/waterloo/
%
% Contact: ($$)sigtool(at)kcl($$).ac($$).uk($$)
%
% Author: Malcolm Lidierth 
% Copyright The Author & King's College London 2013-
% ---------------------------------------------------------------------
%   
%  This program is free software: you can redistribute it and/or modify
%  it under the terms of the GNU General Public License as published by
%  the Free Software Foundation, either version 3 of the License, or
%  (at your option) any later version.
%  
%  This program is distributed in the hope that it will be useful,
%  but WITHOUT ANY WARRANTY; without even the implied warranty of
%  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%  GNU General Public License for more details.
%  
%  You should have received a copy of the GNU General Public License
%  along with this program.  If not, see <http://www.gnu.org/licenses/>.
%
% ---------------------------------------------------------------------

% Create a GXFigure instead of a plain MATLAB figure...
f=GXFigure();
% ... we can still size it using a MATLAB call - it's a MATLAB figure window
% after all
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestUpdate2');

N=500;

% Create some data and plot a bar chart
Y=nan(1,N);
g=gxgca();
g.getObject().setTitleText('Updating line plot');

% SET ANTI-ALIASING OFF FOR HIGH N...
g.getObject().getView().setAntialiasing(false);
% ... AND TURN OFF THE MINOR GRID ALSO - IT'S FASTER THEN
g.getObject().getView().setMinorGridPainted(false);
% Choose a geometrically simple marker such as a square for faster
% rendering
p=line(g, 1:N, Y, 'LineSpec', 's-k');

% Flush the EDT before setting axes bounds as we have all NaNs.
% TODO: ML check why this is needed
pause(0.05);

g.getObject().getView().setAxesBounds(0,-2,N,4);
g.getObject().getView().getParent().repaint();

% NOW UPDATE THE YDATA IN A LOOP - DO THIS ON A TIMER THIS TIME
t=timer('ExecutionMode', 'fixedSpacing', 'Period', 0.02, 'TasksToExecute', N, 'TimerFcn', {@Update, p})
start(t);

end

function Update(tObj, EventData, p)
% Remember zero-based indexing on Java objects
% setEntry fires an IndexedPropertyChange event - but it's simpler to post 
% a repaint request here rather than set up a callback. Direct the repaint
% to the container rather than the graph to update the axes etc - needed
% here for the 1st iteration only.
p.getObject().getYData().setEntry(get(tObj,'TasksExecuted')-1, rand(1,1));
p.getObject.getParentGraph().repaint();
end
