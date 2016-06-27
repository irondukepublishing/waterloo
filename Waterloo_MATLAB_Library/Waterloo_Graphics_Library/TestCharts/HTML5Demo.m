function s=HTML5Demo()
% HTML5Demo
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

% Picture 0
f=GXFigure();
set(gcf,  'Units', 'pixels', 'Position', [100 100 500 500])
ax=subplot(f,1,1,1);
ax.getObject().setAspectRatio(1);
p1=contourf(ax, peaks(100), 20);
p1.getObject().setFill(colors);
p1.getObject().setAlpha(0.3);
pause(1);

s=kcl.waterloo.deploy.pde.PDEGraphics2D.paint(ax.getObject().hgcontrol);

% Picture 1
f=GXFigure();
set(gcf,  'Units', 'pixels', 'Position', [100 100 500 500])
ax=subplot(f,1,1,1);
Z = eig(randn(20,20));
compass(ax,real(Z),imag(Z), 'LineColor','r');

pause(1);
s.append(ax.getObject().hgcontrol, 5000);%APPEND

% Picture 2
f=GXFigure();
set(gcf,  'Units', 'pixels', 'Position', [100 100 500 500])
ax1=subplot(f,1,1,1);

theta = (-90:10:90)*pi/180;
r = 2*ones(size(theta));
[u,v] = pol2cart(theta,r);
feather(ax1,u,v, 'MarkerFaceColor', [java.awt.Color.RED,java.awt.Color.GREEN.darker(), java.awt.Color.BLUE], 'MarkerSize', java.awt.Dimension(15,5),...
                'LineColor', [java.awt.Color.RED,java.awt.Color.GREEN.darker(), java.awt.Color.BLUE], 'LineWidth', 2);
ax1.getObject().setAxisBox(true);

pause(1);

ax2=ax1.getView().add(javaObjectEDT(kcl.waterloo.graphics.GJGraph.createInstance()));
t= 0:.035:2*pi;
[x,y]=pol2cart(t,sin(2*t).*cos(2*t));
b=scatter(wwrap(ax2), x, y, [], 'MarkerFaceColor',java.awt.Color.GREEN.darker());
b.getObject().setAlpha(0.55);

drawnow();

ax3=ax1.getView().add(javaObjectEDT(kcl.waterloo.graphics.GJGraph.createInstance()));
c=scatter(wwrap(ax3), x, y, [], 'MarkerFaceColor',java.awt.Color.RED.darker());
ax3.setAxesBounds(-0.5,-0.5,2,2);
c.getObject().setAlpha(0.75);
drawnow();

ax1.getObject().getView().setReverseY(true);
ax2.setAxesBounds(-1.5,-1.5,2,2);
pause(1);
s.append(ax1.getObject().hgcontrol, 10000);%APPEND

% Picture 3
f=GXFigure();
set(gcf,  'Units', 'pixels', 'Position', [100 100 500 500]);
load sunspot.dat  % Contains a 2-column vector named sunspot

colors=[kcl.waterloo.defaults.Colors.getColor(0)];
for k=1:17
    colors=horzcat(colors,kcl.waterloo.defaults.Colors.getColor(k));
end

gr1=subplot(f, 1,1, 1);
a=polarbar(gr1,(sunspot(1:48,2)),'FaceColor', colors, 'EdgeWidth', 0.5);

pause(1);
s.append(gr1.getObject().hgcontrol, 15000);%APPEND

% Picture 4
f=GXFigure();
set(gcf,  'Units', 'pixels', 'Position', [100 100 500 500]);

[X,Y] = meshgrid(-2:.2:2);
Z = X.*exp(-X.^2 - Y.^2);
[DX,DY] = gradient(Z,.2,.2);
gr1=subplot(f,1,1,1);
q1=quiver(gr1,X,Y,DX,DY, 0.9, 'LineWidth', 0.5);

pause(1);
s.append(gr1.getObject().hgcontrol, 20000);%APPEND

s.setActionDelay(100);
return 
end