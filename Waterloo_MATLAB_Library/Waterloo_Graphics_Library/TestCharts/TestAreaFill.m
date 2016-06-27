function TestAreaFill()
% TestAreaFill illustrates area fills with line plots
%
% Modes 1 to 4 show how you can mix primitive data with shared references
% to a data object to achieve improved memory performance.
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

% Set up and create some data
f=GXFigure();
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestAreaFill');
x=0.5:0.5:10;
y=sin(x);


% N.B. For area fills, details of the result depend on the order: adding a1
% to b1 below will give subtly different results in the ovelap areas.
gr1=subplot(f, 2, 1, 1);
a1=line(gxgca, x, y, 'LineSpec', '-ob');
b1=line(gxgca, x, y*2, 'LineSpec', '-sg');
a1.getObject().setAreaFill(b1.getObject());
gr1.getObject().getView().autoScale();
gr1.getObject().setEffect(org.jdesktop.swingx.painter.effects.ShadowPathEffect());


gr2=subplot(f, 2, 1, 2);
a2=line(gxgca, x, y, 'LineSpec', '-ob');
b2=line(gxgca, x, cos(x), 'LineSpec', '-sg');
b2.getObject().setAreaPaint(java.awt.Color(1,0,0,0.5));
a2.getObject().setAreaFill(b2.getObject());
gr2.getObject().getView().autoScale();
effect=org.jdesktop.swingx.painter.effects.NeonBorderEffect();
effect.setEdgeColor(java.awt.Color.DARK_GRAY);
gr2.getObject().setEffect(effect);

% N.B. It is not usually necessary to issue a repaint when creating graphs
% on Waterloo as changing bound properties will issue a repaint as
% required. But for area fills, the filling algorithm requires both plot to
% % have been painted so issuing a 
gr1.getObject().repaint();
gr2.getObject().repaint();


return 
end