function plotobj=barh(target, X, Y, varargin)
% barh method for GXGraphicObject objects
% barh(target, Y)
% barh(target, X,Y)
% barh(...,width)
% barh(...,'style')
% barh(...,'bar_color')
% barh(...,'PropertyName',PropertyValue,...)
% 
% See also: barh
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

plotobj=bar(target, X, Y, varargin{:}, 'orientation','horizontal');
return
end

