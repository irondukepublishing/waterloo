function a=TestPolarBar()
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

load sunspot.dat  % Contains a 2-column vector named sunspot

colors=[kcl.waterloo.defaults.Colors.getColor(0)];
for k=1:17
    colors=horzcat(colors,kcl.waterloo.defaults.Colors.getColor(k));
end

gr1=subplot(f, 1,2, 1);
a=polarbar(gr1,(sunspot(1:48,2)),'FaceColor', colors, 'EdgeWidth', 0.5);



[a,b]=hist(sunspot(:,2),12);
gr2=subplot(f, 1,2, 2);
b=polarbar(gr2,a,'FaceColor', colors);

return
end