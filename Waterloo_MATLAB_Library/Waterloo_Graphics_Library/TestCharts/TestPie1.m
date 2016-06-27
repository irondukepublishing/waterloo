function f=TestPie1()
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



% % Create a GXFigure instead of a plain MATLAB figure...
f=GXFigure();
% % ... we can still size it using a MATLAB call - it's a MATLAB figure window
% % after all
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestPie1');


colors=[kcl.waterloo.defaults.Colors.getColor(0)];
for k=1:17
    colors=horzcat(colors,kcl.waterloo.defaults.Colors.getColor(k));
end
y=ones(1,18)*100/18;


gr1=subplot(f, 2, 2, 1);
a=pie(gr1,y,'FaceColor',colors);

gr2=subplot(f, 2, 2, 2);
b=pie(gr2,y,'FaceColor',colors);
b.getObject().getDataModel().getExtraObject().setInnerRadius(0.8);
gr2.getObject().repaint();

gr3=subplot(f, 2, 2, 3);
colors=[kcl.waterloo.defaults.Colors.getColor(1), kcl.waterloo.defaults.Colors.getColor(17), kcl.waterloo.defaults.Colors.getColor(2),...
    kcl.waterloo.defaults.Colors.getColor(16), kcl.waterloo.defaults.Colors.getColor(3), kcl.waterloo.defaults.Colors.getColor(15),...
    kcl.waterloo.defaults.Colors.getColor(4),kcl.waterloo.defaults.Colors.getColor(14)];
c=pie(gr3,[10 20 45 42 22 26 42 20],logical([0 0 1]),'FaceColor',colors);

gr4=subplot(f, 2, 2, 4);
d=pie(gr4,[10 20 45 42 ],logical([0 0 1]),'FaceColor',colors);
d.getObject().getDataModel().getExtraObject().setLabels({'Label A', 'Label B', 'Label C', 'Label D'});
gr4.getObject().repaint();
return
end