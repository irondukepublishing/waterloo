function plotobj=contourf(target, varargin)
% contourf method for GXGraphicObject objects
% Inputs should be compatible with the MATLAB contourf function
% C = contourf(Z)
% C = contourf(Z,n)
% C = contourf(Z,v)
% C = contourf(x,y,Z)
% C = contourf(x,y,Z,n)
% C = contourf(x,y,Z,v)
%
% Provides filled contours.
% 
% See also: contour, contourf, GXGraphicObject/contour
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

if numel(varargin)==1
    C=varargin{1};
else
    f=figure('IntegerHandle', 'off','Visible','off');
    TF=cellfun(@ischar, varargin);
    if any(TF)
        args=varargin(1:find(TF,1,'first')-1);
        C=contourf(args{:});
    else
        C=contourf(varargin{:});
    end
    delete(f);
    if any(TF)
        idx=find(TF,1);
        varargin=varargin(idx:end);
    else
        varargin={};
    end
end
plotobj=GXPlot(target, 'contourf', C, varargin{:});
target.getObject().getView().autoScale();
return
end