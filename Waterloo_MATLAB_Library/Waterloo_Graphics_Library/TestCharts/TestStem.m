function f=TestStem()
% TestStem illustrates some simple stem plots
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

f=GXFigure();

% Now create 2 plots in one figure 
% Create some data
t = linspace(-2*pi,2*pi,10);
gr1=subplot(f,1,2,1);
stem(gr1,t,cos(t),'LineSpec','o--r');
gr1.getObject().getView().autoScale();

% Note dimensions of y
x = 0:25;
y = [exp(-.07*x).*cos(x);exp(.05*x).*cos(x)]';
gr2=subplot(f,1,2,2);
% Specify a fill color using a web name
h = stem(gr2, x, y, 'Fill', 'DARKGREEN');
gr2.getObject().getView().autoScale();


drawnow();
return 
end