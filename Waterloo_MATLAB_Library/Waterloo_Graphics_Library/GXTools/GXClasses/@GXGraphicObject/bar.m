function plotobj=bar(target, X, Y, varargin)
% scatter method for GXGraphicObject objects
% bar(target, Y)
% bar(target, X,Y)
% bar(...,width)
% bar(...,'style')
% bar(...,'bar_color')
% bar(...,'PropertyName',PropertyValue,...)
%
% See also: bar
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


width=0;
mode='grouped';

if ischar(target)
    [target, varargin]=ProcessPairedInputs(target, X, Y, varargin{:});
    X=[];Y=[];
else
    if nargin==2
        % bar(target, Y)
        Y=X;
        N=numel(X);
        args={'XData', 1:N, 'YData', Y};
    elseif nargin>2
        if exist('Y', 'var')
            if isnumeric(Y)
                if isscalar(Y)
                    % bar(target, Y, width)
                    width=Y;
                    Y=X;
                    args={'XData', X, 'YData', Y};
                else
                    % bar(target, X, Y)
                    args={'XData', X, 'YData', Y};
                end
            elseif ischar(Y)
                % bar(target, X, option)
                varargin=horzcat(Y,varargin{:});
                Y=X;
                args={'XData', X, 'YData', Y};
            end
        end
    end
end

if ~isempty(varargin)
    idx=[];
    for k=1:min(numel(varargin),3)
        if ischar(varargin{k})
            if ischar(varargin{k}) && strcmpi(varargin{k},'barwidth')
                width=varargin{k+1};
            else
                switch varargin{k}
                    case 'stacked'
                        mode='stacked';
                        idx(end+1)=k; %#ok<AGROW>
                    case 'grouped'
                        idx(end+1)=k; %#ok<AGROW>
                    case 'hist'
                        mode='hist';
                        idx(end+1)=k; %#ok<AGROW>
                    case 'histc'
                        mode='histc';
                        idx(end+1)=k; %#ok<AGROW>
                    otherwise
                        if kcl.waterloo.plot.WPlot.isColor(varargin{k})
                            args=horzcat(args,{'fill', varargin{k}}); %#ok<AGROW>
                            idx(end+1)=k; %#ok<AGROW>
                        end
                end
            end
        elseif kcl.waterloo.plot.WPlot.isColor(varargin{k})
            args=horzcat(args,{'fill', varargin{k}}); %#ok<AGROW>
            idx(end+1)=k; %#ok<AGROW>
        elseif isnumeric((varargin{k})) && isscalar(varargin{k})
            if k==1
                width=(varargin{k});
                idx(end+1)=k;
            elseif k>1 && ~ischar(varargin{k-1})
                width=(varargin{k});
                idx(end+1)=k;
            end
        end
    end
    
%     if isnumeric(varargin{1})
%         idx(end+1)=1;
%     end
    varargin(idx)=[];
    args=horzcat(args,varargin{:});
    
end



plotobj=GXPlot(target, 'bar', args{:});


if width~=0
    plotobj.getObject().getDataModel().getExtraObject().setBarWidth(width);
end

switch mode
    case 'stacked'
        plotobj.getObject().getDataModel().getExtraObject().setMode(javaMethod('valueOf','kcl.waterloo.graphics.plots2D.BarExtra$MODE','STACKED'));
    case 'hist'
        plotobj.getObject().getDataModel().getExtraObject().setMode(javaMethod('valueOf','kcl.waterloo.graphics.plots2D.BarExtra$MODE','HIST'));
    case 'histc'
        plotobj.getObject().getDataModel().getExtraObject().setMode(javaMethod('valueOf','kcl.waterloo.graphics.plots2D.BarExtra$MODE','HISTC'));
end



return
end


