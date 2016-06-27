function script=TestUpdate1()
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
set(gcf, 'Units', 'normalized', 'Position', [0.3 0.3 0.275 0.275], 'Name', 'TestUpdate1');

% Create some data and plot a bar chart
Y=hist(randn(1,100), 50);
p=bar(gxgca, -1:0.0408:1, Y, 'BarWidth', 0.0408, 'Fill', 'LIGHTCORAL');
p.getObject().getParentGraph().setAxesBounds(-1,0,2,225);
p.getObject().getParentGraph().setBackground(kcl.waterloo.defaults.Colors.getColor('WHEAT'));
p.getObject().getParentGraph().setBackgroundPainted(true);
refresh();


%script=kcl.waterloo.deploy.pde.PDEGraphics2D.paint(p.getObject().getParentGraph().getGraphContainer());

container=p.getObject().getParentGraph().getGraphContainer();

% ATTACH THE GRAPH AS A LISTENER TO THE YDATA BUFFER 
p.getObject().getYData().addPropertyChangeListener(p.getObject().getParentGraph());
file=fullfile('testUpdate1.gif');
writer=GIFWriter(file, p.getObject().getParentGraph().getGraphContainer(),0.1,true);
writer.add();

% NOW UPDATE THE YDATA IN A LOOP - PAUSING FOR 0.1s ON EACH ITERATION
for k=1:50
Y=hist(randn(1,100),50);
Y=Y+p.getObject().getYData().getRawDataValues().';
p.getObject().getYData().setDataBufferData(Y);
%script.append(p.getObject().getParentGraph().getGraphContainer());
pause(0.1);
writer.add();
end

writer.close();

fprintf('File created at %s\n', file);
web(file);
end



