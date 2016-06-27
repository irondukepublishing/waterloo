function f=TestUpdate2()
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

N=3000;

% Create some data and plot a bar chart
Y=rand(1,N);
g=gxgca();
g.getObject().setTitleText('Updating line plot');

% SET ANTI-ALIASING OFF FOR HIGH N...
g.getObject().getView().setAntialiasing(false);
% ... AND TURN OFF THE MINOR GRID ALSO - IT'S FASTER THEN
g.getObject().getView().setMinorGridPainted(false);

p=line(g, 1:N, Y);
g.getObject().getView().setAxesBounds(0,-2,N,4);
drawnow();

% ATTACH THE GRAPH AS A LISTENER TO THE YDATA BUFFER
p.getObject().getYData().addPropertyChangeListener(p.getObject().getParentGraph());

% NOW UPDATE THE YDATA IN A LOOP - DO THIS ON A TIMER THIS TIME
t=timer('ExecutionMode', 'fixedSpacing', 'Period', 0.5,'TasksToExecute', 30, 'TimerFcn', {@Update, p, N})
start(t);

end

function Update(tObj, EventData, p, N)
Y=rand(1,N);
p.getObject().getYData().setDataBufferData(Y);
end
