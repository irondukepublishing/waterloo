function TestBessel()

f=GXFigure();
g=subplot(f,1,1,1);
g.getObject.getView().setMajorGridPainted(false);
g.getObject.getView().setMinorGridPainted(false);

g.getObject.getView().setYLabel('J');
g.getObject.getView().setXLabel('x');

% Create some data
x = 0:0.2:10;
y0 = besselj(0,x);
y1 = besselj(1,x);
y2 = besselj(2,x);
y3 = besselj(3,x);
y4 = besselj(4,x);
y5 = besselj(5,x);
y6 = besselj(6,x);

% Generate the lines
a0=line(g, x, y0);
a1=line(g, x, y1);
a2=line(g, x, y2);
a3=line(g, x, y3);
a4=line(g, x, y4);
a5=line(g, x, y5);
a6=line(g, x, y6);

% GXFill accepts simple alpha values (0-1) but also allows
% java.awt.AlphaComposites. Here we use SRC_OVER: which is the default
% anyway.
c=java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,0.2);

% Create some fills
GXFill(a0,0,'v','DARKGREEN',c);
GXFill(a1,0,'v', 'DARKGREEN',c);
GXFill(a2,0,'v', 'DARKGREEN',c);
GXFill(a3,0,'v', 'DARKGREEN',c);
GXFill(a4,0,'v', 'DARKGREEN',c);
GXFill(a5,0,'v', 'DARKGREEN',c);
GXFill(a6,0,'v', 'DARKGREEN',c);

% Repaint once the fills are added
g.getObject().repaint();

end


