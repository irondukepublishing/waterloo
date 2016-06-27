function TestGXGraph()
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

% Set up and create some data
f=GTabbedPane(figure());

% Waterloo graphics in Tabs 1 & 2
tab1=f.addTab('Tab 1');
tab2=f.addTab('Tab 2');
g1=GXGraph(tab1);
g2=GXGraph(tab2);
p1=scatter(g1,1:10,1:10, 'Color', 'SEAGREEN');
g1.getObject().getView().autoScale();
t= 0:.035:2*pi;
[a,b]=pol2cart(t,sin(2*t).*cos(2*t));
p2=line(g2, a, b, 'LineSpec','-om');
g2.getObject().getView().autoScale();

% MATLAB graphics in Tab 3
tab3=f.addTab('Tab 3');
k = 5;
n = 2^k-1;
[x,y,z] = sphere(n);
c = hadamard(2^k);
surf(x,y,z,c);
colormap([1  1  0; 0  1  1])
axis equal


return 
end