function population

% Stats from ONS
year=[1000,1500,1650,1750,1804,1850,1900,1927,1950,1960,1975,1980,1987,1999,2011];
p_year=[2020,2024,2030,2040,2050,2062,2100];
ptn=[0.275,0.45,0.5,0.7,1,1.2,1.6,2,2.55,3,4,4.5,5,6,7];
p_ptn=[7.7,8,8.4,9,9.5,10,10.8];

f=GXFigure();
g=subplot(f,1,1,1);

g.getObject().getView().setMinorGridPainted(false);
g.getObject().getView().setTopAxisPainted(false);
g.getObject().getView().setRightAxisPainted(false);
g.getObject().getView().setMajorGridColor(kcl.waterloo.plot.WPlot.convertColor('SLATEGREY'));

li=scatter(g, year, ptn, 'o','MarkerSize', 5, 'Fill', 'DARKBLUE');

li2=scatter(g, p_year, p_ptn, 'o','MarkerSize', 5, 'Fill', 'DARKBLUE', 'Alpha', 0.3);

g.getObject().getView().setYLabel('Number of people (billions)');
g.getObject().getView().setXLabel('Year');

g.getObject().getView().setAxesPadding(0.01);
g.getObject().getView().autoScale();

g.getObject().setTitleText('Estimated world population 1000-2011 A.D. with predicted growth to 2100');
g.getObject().setSubTitleText('Source: http://www.worldometers.info/world-population/');

% The standard number formatter places commas after the 1000s. Supress
% these to display years.
g.getObject().getView().getXTransform().getNumberFormatter().setGroupingUsed(false);

set(gcf,'Units','normal','Position',[0.2 0.2 0.4 0.4]);
g.getObject().revalidate();
g.getObject().repaint();
drawnow();

end

