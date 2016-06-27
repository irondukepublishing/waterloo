function TestAnimation(thisDelay)
% TestAnimation illustrates a simple line/scatter plot animations
% This calls the plotAppend method
% Note, at Waterloo 1.1 Beta, plotUpdate works by calling plotRefresh but
% more efficient plotAppend methods may be added to individual plot types
% in the future. As we are using plotRefresh here, we also use the alpha
% setting on the color to get color density variation over the plot.
%
% How these work:
%
% plotRefresh requests a Graphics object from the host graph and uses that
% to paint the plot. The background is not repainted, so the plot appears
% as additional screen content (like using plot hold in MATLAB).
%
% plotUpdate, when overloaded, is intended to be used to paint plot
% features that were not present for the previous paint. plotUpdate returns
% true when the method is overloaded and false otherwise.
%
% plotUpdate simply calls plotRefresh for standard plot types - that is not
% really inefficient as the AWT/Swing paint manager should, in any case,
% collapse multiple repaint calls on the EDT.
%
% Note that graphs can generally be edited as usual during an animation e.g. by
% changing axis limits so that plotRefresh and plotUpdate calls can be mixed 
% with normal paint calls. This demo illustrates an example where that will 
% work and where it will not work well: for plot p1 in green, only two data 
% points are present in the data model during plotting so the normal paint 
% mechanisms will omit data painted previously via plotRefresh (to see that 
% effect, slow the animation down by changing the "DELAY" value below 
% or calling TestAnimation(delay) and rescale the graph using the mouse 
% during plotting).


if nargin==0
    DELAY=0.01;
else
    DELAY=thisDelay;
end

% Set up and create some data
f=GXFigure();
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8], 'Name', 'TestAnimation');
x=0.5:0.5:50;
y=log(x);

% Now  the plots
% 1
gr1=subplot(f, 1,1, 1);
a1=line(gxgca, [], [], 'LineSpec', '-ob', 'Fill', java.awt.Color(0,0,1,0.015));
% 2
a2=line(gxgca, [], [], 'LineSpec', '-og');
gr1.getObject().getView().setAxesBounds(0,-2,numel(x)/2+1,2*max(y)+3);

% We could just pass a subvector of x and y using setXData and setYData in
% the loop.
% Instead we'll set up the plot with a set of data padded with NaNs 
% and replace them on demand. This saves doing repeat pass-as-copy calls 
% on the vectors between MATLAB and Java. 
% Note the vectors are seeded with x(1) and y(1). That ensures Waterloo
% paints the axes etc before we enter the loop below.
a1.getObject().setXData([x(1), nan(1,numel(x)-1)]);
a1.getObject().setYData([y(1), nan(1,numel(y))-1]);

% For the second plot, we'll draw 2 points only in each timer call below
% (2 point needed for the line between them). 
% This plot will therefore show only these points when the normal paint
% mechanisms are used unless all the data are added at the end: which the
% timer callback below does
a2.getObject().setXData(x(1:2));
a2.getObject().setYData(y(1:2)*2);

drawnow()
t=timer('ExecutionMode', 'fixedSpacing', 'Period', DELAY, 'TimerFcn', {@localTimer, a1.getObject(), a2.getObject(), x, y});
start(t);


function localTimer(t, EventData, p0, p1, x, y)
% Avoid pause/drawnow in here as this will allow the JRE to manage paints and 
% collapse repeated calls to the AWT/Swing paint methods.
% plotUpdate can be expected to return in about 1-2ms - but remember that
% the real work will be done by the AWT/Swing repaint manager in the queue
% on the EDT
tic();
k=get(t,'TasksExecuted');
if (k>numel(x))
    stop(t);
    p1.setXData(x);
    p1.setYData(y*2);
    p1.plotUpdate();
    % Set the fill for non-animation on p0
    p0.setFill(java.awt.Color.BLUE);
    return;
end
p0.getXData().setEntry(k-1, x(k));
p0.getYData().setEntry(k-1, y(k));
p0.plotUpdate();
if k>1 
p1.setXData(x(k-1:k));
p1.setYData(y(k-1:k)*2);
p1.plotUpdate();
end
end

end

