classdef GXFrame < hgsetget
    % GXFrame simple MATLAB wrapper for a GCFrame
    
    properties
        Object;
    end
    
    methods
        
        function obj=GXFrame(str, varargin)
            if nargin==1 && isjava(str)
                % Java object as input. Just wrap it and return
                obj.Object=str;
                return;
            end
            if nargin<1
                % Frame title
                str='';
            end
%             %Grid dimensions
%             if nargin<2
%                 m=1;
%             end
%             if nargin<3
%                 n=1;
%             end
            % Create the frame
            obj.Object=javaObjectEDT(kcl.waterloo.swing.GCFrame(str));
        end
        
        function thisObj=getObject(obj)
            thisObj=obj.Object;
            return
        end
        
    end
end

