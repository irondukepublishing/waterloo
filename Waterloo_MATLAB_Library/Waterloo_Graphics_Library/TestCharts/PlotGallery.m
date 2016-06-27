function PlotGallery()

f1=GXFigure();
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.25 0.25]);
line(gxgca(),1:10,1:10, 'Marker', 'o');



f1=GXFigure();
g=line(gxgca(),0:10,0:10, 'Marker', 'o');
line(g,0:10,(0:10)*2, 'Marker', '^');
line(g,0:10,(0:10)*3, 'Marker', 's');


f1=GXFigure();
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.25 0.25]);
Z = eig(randn(20,20));
a=compass(gxgca(),real(Z),imag(Z));



f1=GXFigure();
set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.25 0.25]);
load sunspot.dat  % Contains a 2-column vector named sunspot
colors=[kcl.waterloo.defaults.Colors.getColor(0)];
for k=1:17
    colors=horzcat(colors,kcl.waterloo.defaults.Colors.getColor(k));
end
a=polarbar(gxgca(),(sunspot(1:48,2)),'FaceColor', colors, 'EdgeWidth', 0.5);

% 
% 
% 
% f1=GXFigure();
% set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8]);
% 
% 
% 
% f1=GXFigure();
% set(gcf, 'Units', 'normalized', 'Position', [0.1 0.1 0.8 0.8]);

end

