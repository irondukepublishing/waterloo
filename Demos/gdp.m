function gdp()

% Load the data (Source: Office for National Statistics, UK)
% data(1,:) containes quarterly growth as a percentage
% data(2,:) contains GDP as £million
load gdp

% Data are for for 1st quarter 2003 through 1st quarter 2013
x=2003:0.25:2013.25;


% Create a figure and single subplot. Supress the grid
f=GXFigure();
g=subplot(f,1,1,1);

% Turn off unwanted axes
g.getObject().getView().setMajorGridPainted(false);
g.getObject().getView().setMinorGridPainted(false);
g.getObject().getView().setBottomAxisPainted(false);


% QUATERLY GROWTH
bar(g, x, data(:,1), 'histc', 'BarWidth',0.25, 'Fill', 'LIGHTGREY'); %#ok<NODEF>
g.getObject().getView().setYLabel('Quarterly growth (%)');
g.getObject().setTitleText('UK Quaterlery Growth and GDP 2003-present');
g.getObject().setSubTitleText('Source: Office for National Statistics');

% GDP
% Add a layer to the view
g2=g.getObject().getView().add(kcl.waterloo.graphics.GJGraph.createInstance());
% Wwrap it so we can use MATLAB commands
g2=wwrap(g2);
line(g2, x, data(:,2)/1000, 'LineColor', 'k', 'LineWidth', 3);
% g2 contains a GJGraph - not a GJGraphContainer - so do not use getView.
g2.getObject().setYLabel('GDP [£million x 1000]');
g2.getObject().setXLabel('Year');


% Set axes
g2.getObject().setAxesBounds(2003, 300, 10.25,100);
g.getObject().getView().setAxesBounds(2003,-3, 10.25, 6);

% Resize the window
set(gcf,'Units','pixels','Position',[200 200 500 350]);
drawnow();

% The standard number formatter places commas after the 1000s. Supress
% these to display years.
g2.getObject().getXTransform().getNumberFormatter().setGroupingUsed(false);

set(gcf,'Units','normal','Position',[0.2 0.2 0.4 0.4]);
g.getObject().revalidate();
g.getObject().repaint();
drawnow();



end


