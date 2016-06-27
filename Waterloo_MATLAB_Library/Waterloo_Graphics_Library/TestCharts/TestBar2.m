function f=TestBar2()
% TestScatter illustrates some simple bar plots
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
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestBar2');

y=-5:5;
z=rand(1,11);

% GRAPH 1
gr1=subplot(f, 2, 2, 1);
a=bar(gr1, 1:11, y);
gr1.getObject().setTitleText('Vertical bars with errors');
e1=errorbar(GXGraphicObject(), 1:11, -5:5, z, z);
% Set mode to ErrorBarExtra.MODE.DATASIGN. This causes upper error bars to be
% drawn only when the y-values are positive and lower error bars only when
% the y-values are negative.
e1.getObject().getDataModel().getExtraObject().setMode('DATASIGN');
a.getObject().add(e1.getObject());
gr1.getObject().getView().autoScale();


% GRAPH 2
% Variable sized markers
gr2=subplot(f, 2, 2, 2);
b=bar(gr2, 1:11, y, 'hist');
gr2.getObject().setTitleText('Hist option');
e2=errorbar(GXGraphicObject(), 1:11, -5:5, z,z, 'LineColor', 'SEAGREEN');
e2.getObject().getDataModel().getExtraObject().setMode('DATASIGN');
b.getObject().add(e2.getObject());
gr2.getObject().getView().autoScale();

% GRAPH 3
gr3=subplot(f, 2, 2, 3);
c=bar(gr3, 1:11, y, 'histc');
gr3.getObject().setTitleText('Histc option');
% Need to explicitly center the error bars on the histogram bars
e3=errorbar(GXGraphicObject(), (1:11)+0.5, -5:5, z,z);
e1.getObject().getDataModel().getExtraObject().setMode('DATASIGN');
c.getObject().add(e3.getObject());
gr3.getObject().getView().autoScale();

% GRAPH 4
gr4=subplot(f, 2, 2, 4);
d=barh(gr4, 1:11, y);
gr4.getObject().setTitleText('Horizontal');
% Need to explicitly swap x and y-values for the error bars on a horizontal
% bar chart.
e4=errorbar(GXGraphicObject(),  -5:5, 1:11, [],[]);
% Could set the mode again here, but engineer the same effect instead using
% NaNs. NaNs will not be drawn.
zu=z;
zu(y<0)=NaN;
zl=z;
zl(y>=0)=NaN;
e4.getObject().getDataModel().setExtraData0(zu);
e4.getObject().getDataModel().setExtraData2(zl);
d.getObject().add(e4.getObject());
gr4.getObject().getView().autoScale();

return 
end