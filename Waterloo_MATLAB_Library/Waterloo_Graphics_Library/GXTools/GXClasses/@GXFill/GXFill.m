classdef GXFill < GXGraphicObject
    % obj=GXFill(target, value, orientation)
    % returns a GXFill instance. The object will contain a java
    % object implementing the GJFillableInterface interface
    % Inputs:
    %   target: Another object implementing GJFillableInterface
    %           e.g. a plot
    %   value:  a scalar or java Shape
    %   orientation: as dfined in the GJFill class: vertical,
    %   horizontal or arbitrary specified here as a string.
    % ---------------------------------------------------------------------
    % Part of the sigTOOL Project and Project Waterloo from King's College
    % London.
    % http://sigtool.sourceforge.net/
    % http://sourceforge.net/projects/waterloo/
    %
    % Contact: ($$)sigtool(at)kcl($$).ac($$).uk($$)
    %
    % Author: Malcolm Lidierth 06/13
    % Copyright The Author & King's College London 2013-
    % ---------------------------------------------------------------------
    properties
        Type;
        Parent;
    end
    
    methods
        
        % Constructor
        function obj=GXFill(target, value, orientation, color, alpha)
            if nargin==0
                error('Too few input arguments: you must supply a plot to perform the fill operation');
            end
            if ~isjava(target)
                target=target.getObject();
            end
            switch nargin
                case 1
                    obj.Object=kcl.waterloo.graphics.plots2D.GJFill(target);
                case 2
                    obj.Object=kcl.waterloo.graphics.plots2D.GJFill(target, value);
                otherwise
                    obj.Object=kcl.waterloo.graphics.plots2D.GJFill(target, value);
                    switch lower(orientation)
                        case {'vertical', 'vert', 'v'}
                            obj.Object.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','VERTICAL'));
                        case {'horizontal', 'hor', 'h'}
                            obj.Object.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','HORIZONTAL'));
                        case {'arbitrary', 'arb'}
                            obj.Object.setOrientation(javaMethod('valueOf', 'kcl.waterloo.graphics.plots2D.GJFill$ORIENTATION','ARBITRARY'));
                    end
            end
            
            if nargin>3
                color=kcl.waterloo.plot.WPlot.convertColor(color);
                obj.Object.setAreaPaint(color);
            end
            
            if nargin>4
                if (isnumeric(alpha))
                    % Simple alpha value
                    obj.Object.setFillAlpha(alpha);
                elseif strcmpi(class(alpha),'java.awt.AlphaComposite')
                    % Support an alpha composite also
                    obj.Object.setFillComposite(alpha);
                end
            end
            
            % Add this fill to the target
            target.setAreaFill(obj.Object);
        end
        
        
        
    end
    
end



