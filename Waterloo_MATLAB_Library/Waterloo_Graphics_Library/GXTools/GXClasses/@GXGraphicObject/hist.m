function plotobj=hist(target, varargin)
% hist method for GXGraphicObject objects
%
% barh(...,'PropertyName',PropertyValue,...)
% 
% See also: hist
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

[n xout]=hist(varargin{:});
plotobj=bar(target, xout, n, 'hist', xout(2)-xout(1));
plotobj.getObject().getParentGraph().autoScale();
return
end

