function [t,t2] = wbench()
% 2-D graphics. Vibrating logo.
x = (0:1/256:1)';


hh = figure('doublebuffer','on');
aa = axes('parent',hh);
plot(aa,x,bernstein(x,0))
drawnow
tic
for j = 1:2
    for n = [1:12 11:-1:2]
        plot(aa,x,bernstein(x,n))
        drawnow
    end
end
t = toc;
close(hh)


colors=GColor.getArcSeries(java.awt.Color.red,14,360);
f=GXFigure();
ax=subplot(f,1,1,1);
ax.getObject().getView().setAxesBounds(0,0,1,1);
ax.getObject().getView().setBackgroundPainted(true);
ax.getObject().setBackground(java.awt.Color.white);
ax.getObject().getView().setMajorGridPainted(false);
ax.getObject().getView().setMinorGridPainted(false);
% Sequence to get illusion of movement
idx=1:14;%[10 3 11 2 12 1 14 7 8 6 9 5 4];
tic
for j = 1:2
    for n = [1:12 11:-1:2] 
        b=bernstein(x,n); 
        idx2=idx(idx<=size(b,2));
        for k=idx2
            try
                % Plot already created so update it
                lh(n*j).setYData(b(:,k));
                lh(n*j).setLineColor(colors(k));
                lh(n*j).plotUpdate();
            catch
                % Create plot
                lh(n*j)=kcl.waterloo.graphics.plots2D.GJLine.createInstance();
                lh(n*j).setXData(x);
                lh(n*j).setYData(b(:,k));
                lh(n*j).setLineColor(colors(k));
                %lh(n*j).setAntialiasing(false);
                ax.getObject().getView().addFast(lh(n*j));
                lh(n*j).plotUpdate();
            end
        end
        ax.getObject().getView().paintGrid();
    end
end
t2 = toc;
close(gcf);


end

% ----------------------------------------------- %
function B = bernstein(x,n)
% BERNSTEIN  Generate Bernstein polynomials.
% B = bernstein(x,n) is a length(x)-by-n+1 matrix whose columns
% are the Bernstein polynomials of degree n evaluated at x,
% B_sub_k(x) = nchoosek(n,k)*x.^k.*(1-x).^(n-k), k = 0:n.

x = x(:);
B = zeros(length(x),n+1);
B(:,1) = 1;
for k = 2:n+1
    B(:,k) = x.*B(:,k-1);
    for j = k-1:-1:2
        B(:,j) = x.*B(:,j-1) + (1-x).*B(:,j);
    end
    B(:,1) = (1-x).*B(:,1);
end
end
% ----------------------------------------------- %