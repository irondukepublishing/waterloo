function rail()

arrivals=[13890,19978,27583,11296,29450,37728,9883,7244,11344];
departures=[68176,107833,137759,56122,145870,187959,53742,43331,65051];
station={'Bristol', 'Cardiff', 'Leeds', 'Leicester', 'Liverpool','Manchester','Newcastle','Nottingham','Sheffield'};

f=GXFigure();
g=subplot(f,1,1,1);


g.getObject().getView().setMajorGridPainted(false);
g.getObject().getView().setMinorGridPainted(false);
g.getObject().getView().setTopAxisPainted(false);
g.getObject().getView().setRightAxisPainted(false);

b=bar(g, (1:numel(arrivals)*2)-1, horzcat(arrivals,departures), 'stacked', 'BarWidth', 2);


b.getObject().setFill([java.awt.Color.yellow, java.awt.Color.blue]);
g.getObject().getView().autoScale();

g.getObject().getView().setXLabel('Station');
g.getObject().getView().setYLabel('Number of Passengers');

INDEX=0;
for k=0:2:18-1
    INDEX=INDEX+1;
    b.getObject().getDataModel().getXData().setCategory(k, station{INDEX});
end

g.getObject().setTitleText('Passengers travelling through major UK provincial rail hubs at peak time');
g.getObject().setSubTitleText('Source: Department of Transport');


set(gcf,'Units','normal','Position',[0.2 0.2 0.4 0.4]);
g.getObject().revalidate();
g.getObject().repaint();
drawnow();

end




