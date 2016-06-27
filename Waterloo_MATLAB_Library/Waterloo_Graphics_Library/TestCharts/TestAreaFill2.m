function TestAreaFill2()
% TestAreaFill illustrates area fills with line plots
%
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestAreaFill2');
x=0.5:0.5:10;
y=sin(x);


% N.B. For area fills, details of the result depend on the order: adding a1
% to b1 below will give subtly different results in the ovelap areas.
gr1=subplot(f, 2, 2, 1);
a1=line(gxgca, x+1, y+3, 'LineSpec', '-sg');
v=kcl.waterloo.graphics.plots2D.GJFill(a1.getObject(), 2);
a1.getObject().setAreaFill(v);
v.setAreaPaint(java.awt.Color(0,1,0,0.5));
gr1.getObject().getView().setAxesBounds(0,0,12,5);


gr2=subplot(f, 2, 2, 2);
a2=line(gxgca, x, numel(x):-1:1, 'LineSpec', '-ob');
v=kcl.waterloo.graphics.plots2D.GJFill(a2.getObject(), 10);
v.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','HORIZONTAL'));
a2.getObject().setAreaFill(v);
gr2.getObject().getView().setAxesBounds(-5,-5,20,30);

gr3=subplot(f, 2, 2, 3);
a3=line(gxgca, x+1, y+3, 'LineSpec', '-sg');
v=kcl.waterloo.graphics.plots2D.GJFill(a3.getObject());
v.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','ARBITRARY'));
a3.getObject().setAreaFill(v);
v.setArbitraryArea(java.awt.geom.Area(javaObject('java.awt.geom.Rectangle2D$Double',1,1,5,5)));
v.setAreaPaint(java.awt.Color(0,1,0,0.5));
gr3.getObject().getView().setAxesBounds(0,0,12,5);

gr4=subplot(f, 2, 2, 4);
a4=line(gxgca, x+1, y+3, 'LineSpec', '-sg');
v1=kcl.waterloo.graphics.plots2D.GJFill(a4.getObject());
v1.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','ARBITRARY'));
a4.getObject().setAreaFill(v1);
area=java.awt.geom.Area(javaObject('java.awt.geom.Rectangle2D$Double',1,1,5,5));
area.add(java.awt.geom.Area(javaObject('java.awt.geom.Rectangle2D$Double',8,1,2,5)));
v1.setArbitraryArea(java.awt.geom.Area(area));
v1.setAreaPaint(java.awt.Color(0,1,0,0.5));
gr4.getObject().getView().setAxesBounds(0,0,12,5);

% N.B. It is not usually necessary to issue a repaint when creating graphs
% on Waterloo as changing bound properties will issue a repaint as
% required. But for area fills, the filling algorithm requires both plot to
% % have been painted so issuing a 
gr1.getObject().repaint();
gr2.getObject().repaint();
gr3.getObject().repaint();
gr4.getObject().repaint();

return 
end