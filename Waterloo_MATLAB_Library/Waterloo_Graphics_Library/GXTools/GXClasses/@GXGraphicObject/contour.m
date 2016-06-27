function plotobj=contour(target, varargin)
% contour method for GXGraphicObject objects
% Inputs should be compatible with the MATLAB contour or contourc function
% C = contour(target, Z)
% C = contour(target, Z,n)
% C = contour(target,Z,v)
% C = contour(target,x,y,Z)
% C = contour(target,x,y,Z,n)
% C = contour(target,x,y,Z,v)
% C = contour(target, 'fillable', ...)
% 
% If the 'fillable' keyword is supplied, the resulting contour will be
% suitable for filling. Filling
% 
% See also: contour, contourc
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

if ischar(varargin{1})
    if strcmpi(varargin{1}, 'fillable')
        varargin(1)=[];
        f=figure('IntegerHandle', 'off','Visible','off');
        C=contourf(varargin{:});
        delete(f);
        plotobj=GXPlot(target, 'contournofill', C);
        target.getObject().getView().autoScale();
        return
    else
        f=figure('IntegerHandle', 'off','Visible','off');
        C=contour(varargin{:});
        delete(f);
    end
else
    C=contourc(varargin{:});
end
plotobj=GXPlot(target, 'contour', C);
target.getObject().getView().autoScale();
return
end