function f=TestGantt()
% TestGantt illustrates the synthesis of a Gantt chart
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestBar1');


x=[2012, 2012, 2012, 2013, 2013, 2013, 2014, 2014, 2014];
y=[6,3,3,3,6,3,1,4,7];
p=barh(gxgca, x, y,'stacked');
% p.getObject().getYData().setCategory(2012,'2012');
% p.getObject().getYData().setCategory(2013,'2013');
% p.getObject().getYData().setCategory(2014,'2014');
colors=[java.awt.Color.yellow,java.awt.Color.blue, java.awt.Color.red];
p.getObject().setFill(colors);
return 
end