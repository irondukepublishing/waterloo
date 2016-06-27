classdef GXPlot < GXGraphicObject
    % GXPlot graph. Provides high-quality, anti-aliased 2D graphics in MATLAB
    % using the Project Waterloo Graphics Library.
    %
    % GXPlot is called from the various methods for individual plot types
    % (line, scatter etc.) and provides a common wrapper class for the plots.
    % This MATLAB wrapper is used instead of the WPlot Groovy wrapper.
    %
    % The call takes the form
    %       obj=GXPlot(Target, Style, Map)
    %           where Target is the handle or reference to the object that will
    %                   parent the plot
    %                 Style is a string describing the plot style e.g.
    %                   'scatter'
    %                 Map is a java.util.LinkedHashMap typically supplied as
    %                   output from a call to kcl.waterloo.plot.WPlot.parseArgs
    %
    %                 bar
    %                 contour
    %                 contourf
    %                 errorbar
    %                 feather
    %                 line
    %                 quiver
    %                 scatter
    %                 stairs
    %                 stem
    %
    % GXPlot can also be invoked directly if the appropriate parameter
    % name/values are supplied as input.
    %       obj=GXPlot(Target, Style, PropName1, PropValue1,....)
    % The call to WPlot.parseArgs will then be made within the Groovy code.
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
    
    
    properties
        Style;
        Type='GXPlot';
        Parent;
    end
    
    
    methods
        
        function obj=GXPlot(varargin)
            
            target=varargin{1};
            style=varargin{2};
            args=[];
            if nargin>2
                if isjava(varargin{3})
                    % LinkedHashMap
                    args=varargin{3};
                else
                    % MATLAB style prop/value pairs
                    args=varargin(3:end);
                end
            end
            
            % Instantiate object
            style(1)=upper(style(1));
            switch lower(style)
                case {'cloud', 'polarcloud', 'scatter', 'line', 'stairs','stem', 'bar', 'feather', 'errorbar','pie',...
                        'quiver', 'polarline', 'polarscatter','polarbar', 'polarstem'}
                    % Standard calls with XData & YData. The groovy code
                    % will call the createInstance method
                    x=kcl.waterloo.plot.WPlot.(lower(style))(args);
                    % TODO: Should these be on EDT?
                    obj.Object=x.getPlot();
                case 'fastline'
                    x=kcl.waterloo.plot.WPlot.fastline(args);
                    obj.Object=x.getPlot();
                case 'cscatter'
                    style='GJComponentPlot';
                    obj.Object=javaObjectEDT(kcl.waterloo.graphics.plots2D.GJComponentPlot.createInstance());
                    if nargin>2
                        obj.setData(args{1},args{2});
                    end
                case {'contour', 'contourf', 'contournofill'}
                    % Get contours
                    if size(args{1},1)==2
                        C=args{1};
                        if numel(args)>1
                            args=args(2:end);
                        else 
                            args=[];
                        end
                    else
                        if numel(args)>1
                            % Need to generate a contour matrix
                            if isvector(args{1}) && isvector(args{2})
                                C=contourc(args{:});
                            else
                                tmpF=figure('Visible','off');
                                [C]=contour('Parent', gca, args{:});
                                delete(tmpF);
                            end
                        else
                            if size(args{1},1)==2
                                % The input is a contour matrix
                                C=args{1};
                            else
                                % The input is a data matrix
                                tmpF=figure('Visible','off');
                                [C]=contour('Parent', gca, args{:});
                                delete(tmpF);
                            end
                        end
                    end
                    obj.Object=kcl.waterloo.plot.WPlot.contour(args);
                    obj.Object=obj.Object.getPlot();
                    contourObject=kcl.waterloo.graphics.plots2D.contour.ContourExtra.createFromMatrix(C);
                    obj.Object.getDataModel().setExtraObject(contourObject);
                    N=contourObject.size();
                    obj.Object.setXData(0);
                    obj.Object.setYData(0);
                    if strcmpi('contourf', style)
                        %colors=[java.awt.Color.RED java.awt.Color.BLUE java.awt.Color.RED java.awt.Color.BLUE java.awt.Color.RED java.awt.Color.BLUE java.awt.Color.RED java.awt.Color.BLUE];
                        colors=GColor.getArcSeries(java.awt.Color.blue, N,180);
                        obj.Object.setFill(colors);
                    else
                        colors=GColor.getArcSeries(java.awt.Color.blue, N, 180);
                        obj.Object.setEdgeColor(colors);
                        obj.Object.setFilled(false);
                    end
            end
            
            
            % Add to target object
            if ~isempty(target)
                switch class(target)
                    case 'double'
                        % The parent is a MATLAB HG handle. Find the
                        % relevant GXGraph (only one per HG container
                        % allowed).
                        if ~isappdata(target, 'GXGraphObject')
                            warning('GXPlot:InvalidTarget','Specified target (%d) has no associated GXGraphObject', target);
                            return
                        else
                            parent=getappdata(target, 'GXGraphObject');
                            parent.addPlot(obj);
                            obj.Parent=parent;
                        end
                        
                    case {'GXGraph', 'jcontrol', 'kcl.waterloo.graphics.GJGraph', ...
                            'javahandle_withcallbacks.kcl.waterloo.graphics.GJGraph'}
                        % Parent is a GXGraph object - add this plot to it
                        target.getObject().getView().add(obj.getObject());
                        obj.Parent=target;
                        drawnow();
                    case {'wwrap'}
                        if (isa(target.getObject(), 'kcl.waterloo.graphics.GJGraphContainer'))
                            target.getObject().getView().add(obj.getObject());
                        elseif (isa(target.getObject(), 'kcl.waterloo.swing.GCGridElement'))
                                target.getObject().get().getView().add(obj.getObject());
                        else
                            target.getObject().add(obj.getObject());
                        end
                        obj.Parent=target;
                        
                        
                    case 'GXPlot'
                        % Parent is another GXPlot object - add this plot
                        % to it. The parent plot may or may not yet be
                        % associated with a GXGraph
                        newplot=obj.getObject();
                        target.getObject().add(newplot);
                        obj.Parent=target;
                        
                end
            end
            obj.Style=style;
            
            if strcmpi('contourf', style)
                if rem(obj.Object.getFill().size(), 2)~=0
                    color=obj.Object.getFill.get(obj.Object.getFill().size()/2);
                else
                    idx=floor(obj.Object.getFill().size()/2);
                    w=obj.Object.getDataModel().getExtraObject().getLevelData().keySet().toArray();
                    color=GColor.getWeightedAverage(obj.Object.getFill().get(idx-1),...
                        w(idx-1),...
                        obj.Object.getFill().get(idx),...
                        w(idx));
                end
                obj.Object.getParentGraph().setBackground(color);
            end
            
            return
        end
        
        function view=getView(obj)
            view=obj.Parent.getView();
            return
        end
        
        
    end
    
end

function [levels newC]=convertContour(C)
% convertContour - Internal helper for converting MATLAB contour matrices
% Pre-allocate memory for up to 200 contours - levels and newC will grow in
% the loop if more are needed
levels=zeros(1,200);
newC=cell(1,200);
count=0;
k=1;
while k<size(C,2)
    count=count+1;
    levels(count)=C(1,k);
    n=C(2,k);
    newC{count}=zeros(n,2);
    newC{count}(:,1)=C(1, k+1:k+n);
    newC{count}(:,2)=C(2, k+1:k+n);
    k=k+n+1;
end
levels=levels(1:count);
newC=newC(1:count);
return
end

% function theta=getTheta(sintheta,x,y)
% if x>=0 && y>=0
%     theta=asin(sintheta);
% elseif x<=0 && y>=0
%     theta=pi-asin(sintheta);
% elseif x<=0 && y<=0
%     theta=-(pi+asin(sintheta));
% else
%     theta=asin(sintheta);
% end
% return
% end

