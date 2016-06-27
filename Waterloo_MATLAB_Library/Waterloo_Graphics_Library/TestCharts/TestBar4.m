function f=TestBar4()
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestBar4');

m={'Jan', 'Feb', 'March', 'April', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'};

% GRAPH 1
gr1=subplot(f, 2, 2, 1);
a=bar(gr1, 1:12, 1:12);
for k=1:12
   a.getObject().getDataModel().getXData().setCategory(k, m{k});
end
gr1.getObject().setTitleText('Label using the XData categories');
gr1.getObject().getView().autoScale();


% GRAPH 2
% Variable sized markers
gr2=subplot(f, 2, 2, 2);
b=barh(gr2, 1:12, 1:12);
gr2.getObject().setTitleText('Label using the bar plot');
b.getObject().getDataModel().getExtraObject().setLabelOrientation('HORIZONTAL');
for k=1:12
   b.getObject().getDataModel().getExtraObject().getLabels().add(k-1, m{k});
end
gr2.getObject().getView().autoScale();

% GRAPH 3
gr3=subplot(f, 2, 2, 3);
c=barh(gr3, 1:10, 1:10,'stacked');
gr3.getObject().setTitleText('Set the label color for each series in a multiplexed plot');
c.getObject().setFill([java.awt.Color.yellow, java.awt.Color.blue]);
c.getObject().getDataModel().getExtraObject().setFontForeground([java.awt.Color.BLACK, java.awt.Color.WHITE]);
for k=1:12
   c.getObject().getDataModel().getExtraObject().getLabels().add(k-1, m{k});
end
gr3.getObject().getView().autoScale();

% GRAPH 4
gr4=subplot(f, 2, 2, 4);
d=bar(gr4, 1:12, 1:12);
gr4.getObject().setTitleText('Set the background for the labels');
d.getObject().getDataModel().getExtraObject().setFontBackground(java.awt.Color.WHITE);
for k=1:12
   d.getObject().getDataModel().getExtraObject().getLabels().add(k-1, java.lang.String(char('A' + k-1)));
end
gr1.getObject().getView().autoScale();

return 
end