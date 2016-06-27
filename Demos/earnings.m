function earnings

% Stats from ONS
growth=[70,56,49,48,47,47,46,47,47,46,47,46,47,47,47,48,49,49,49,49,50,50,...
    50,50,50,50,51,51,51,51,52,52,53,53,54,54,55,55,56,57,57,57,58,58,59,60,...
    60,61,62,62,62,63,64,64,64,65,66,66,66,67,68,68,69,69,69,70,70,71,72,72,73,...
    73,74,74,74,74,74,75,76,76,76,77,77,77,78,78,78,78,79,81,81,81,84,88,90,96,98,105,117;];

f=GXFigure();
g=subplot(f,1,1,1);

g.getObject().getView().setMinorGridPainted(false);
g.getObject().getView().setTopAxisPainted(false);
g.getObject().getView().setRightAxisPainted(false);
g.getObject().getView().setMajorGridColor(kcl.waterloo.plot.WPlot.convertColor('DARKGREEN'));

li=scatter(g, 1:numel(growth), growth, 'o','MarkerSize', 3, 'Fill', 'GREY');

g.getObject().getView().setYLabel('Growth in real earnings (%)');
g.getObject().getView().setXLabel('Income percentile');

g.getObject().getView().setAxesBounds(0, 40, 100.1, 80);

g.getObject().setTitleText('UK Growth in real earnings 1986-2011 by income percentile');
g.getObject().setSubTitleText('Source: Office of National Statistics');

set(gcf,'Units','normal','Position',[0.2 0.2 0.4 0.4]);
g.getObject().revalidate();
g.getObject().repaint();
drawnow();

end

