function plotobj=pie(target, Y, varargin)
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

if ischar(target)
    [target, varargin]=ProcessPairedInputs(target, X, Y, varargin{:});
    X=[];Y=[];
else
    if numel(varargin)>0 && islogical(varargin{1})
        varargin=horzcat('explode', varargin);
    end
end

plotobj=GXPlot(target, 'pie', 'YData', Y, varargin{:});

return
end


