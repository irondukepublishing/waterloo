function f=TestBar3()
% TestScatter illustrates some simple scatter plots
%
% ---------------------------------------------------------------------
% Part of the sigTOOL Project and Project Waterloo from King's College
% London.
% http://sigtool.sourceforge.net/
% http://sourceforge.net/projects/waterloo/
%
% Contact: ($$)sigtool(at)kcl($$).ac($$).uk($$)
%
% Author: Malcolm Lidierth 12/11
% Copyright The Author & King's College London 2011-
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestBar3');

y1=rand(1,11);
y2=rand(1,11)*2;

% GRAPH 1
gr1=subplot(f, 2, 1, 1);
a=bar(gr1, 1:11, y1);
gr1.getObject().setTitleText('Superimposed bars');
b=bar(gr1, 1:11, y2);
b.getObject().add(a.getObject());
b.getObject().setFill(java.awt.Color(1,0,0,0.5));
a.getObject().setFill(java.awt.Color(0,0,1,0.5));
gr1.getObject().getView().autoScale();


% GRAPH 2
% Variable sized markers
gr2=subplot(f, 2,1, 2);
a=bar(gr2, 1:11, y1);
gr2.getObject().setTitleText('Superimposed line');
b=line(gr2, 1:11, y2, 'LineSpec', 'b-o');
gr2.getObject().getView().autoScale();