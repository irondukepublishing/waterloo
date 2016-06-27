function f=TestBar1()
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestBar1');


% GRAPH 1
gr1=subplot(f, 2, 2, 1);
a=bar(gr1, 1:10, (1:10)/2);
gr1.getObject().setTitleText('Vertical bars');
gr1.getObject().getView().autoScale();


% GRAPH 2
% Variable sized markers
gr2=subplot(f, 2, 2, 2);
b=barh(gr2, 1:10, (1:10)/2);
gr2.getObject().setTitleText('Horizontal bars');
gr2.getObject().getView().autoScale();

% GRAPH 3
gr3=subplot(f, 2, 2, 3);
b=barh(gr3, 1:10, (1:10)/2,'stacked');
gr3.getObject().setTitleText('Stacked bars');
b.getObject().setFill([java.awt.Color.yellow, java.awt.Color.blue]);
gr3.getObject().getView().autoScale();

% GRAPH 4
gr4=subplot(f, 2, 2, 4);
b=barh(gr4, 1:10, (1:10)/2, 'BaseValue', 4);
gr4.getObject().setTitleText('Non-zero baseline and semi-log plot');
% Note the bars with one or more corners yielding NaNs of Infs will not be
% rendered. Also, bar plot axes can not be reversed at present - that
% yields negative widths/heights
gr4.getObject().getView().setXTransform(kcl.waterloo.graphics.transforms.Log10Transform.getInstance());
gr4.getObject().getView().setAxesBounds(-1,0,2.5,11);

return 
end