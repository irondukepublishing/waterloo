function plotobj=compass(target, X, Y, varargin)
% polarbar method for GXGraphicObject objects
%
% polarbar(target, Y)
% polarbar(...,'PropertyName',PropertyValue,...)
%
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
end

% MATLAB's compass uses cartesian data: need to set that explicitly for a
% polarstem
plotobj=GXPlot(target, 'polarstem', 'XData', X, 'YData', Y, 'DataMode', 'CARTESIAN', varargin{:});


return
end


