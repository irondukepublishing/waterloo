function ax=TestContour()
% TestContour
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

colors=[kcl.waterloo.defaults.Colors.getColor(0)];
for k=1:17
    colors=horzcat(colors,kcl.waterloo.defaults.Colors.getColor(k));
end

% Set up and create some data
f=GXFigure();
set(gcf, 'Name', 'TestContour', 'Units', 'normalized', 'Position', [0.2 0.2 0.4 0.4])
ax=subplot(f,1,1,1);
ax.getObject().setAspectRatio(1);
p1=contourf(ax, peaks(100), 20);
p1.getObject().setFill(colors);
p1.getObject().setAlpha(0.3);

% load penny;
% ax2=subplot(f,1,2,2);
% ax2.getObject().setAspectRatio(1);
% p2=contourf(ax2, flipud(P), 18, 'EdgeStyle', '-', 'EdgeWidth', 0.4);
% p2.getObject().setFillClipping(false);
% p2.getObject().setFill(colors);
% % cbar=kcl.waterloo.graphics.GJColorBar.createInstance(p2.getObject());
% % cbar.setLabels(p2.getObject().getDataModel().getExtraObject().getLevels());
drawnow();
return 
end