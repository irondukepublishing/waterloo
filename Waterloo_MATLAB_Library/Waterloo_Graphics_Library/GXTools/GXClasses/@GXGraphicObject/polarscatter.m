function plotobj=polarscatter(target, varargin)
% polarscatter method for GXGraphicObject objects
% Examples:
% polarscatter(GXGraphicObject, X,Y,S,C) 
% polarscatter(GXGraphicObject, X,Y)
% polarscatter(GXGraphicObject, X,Y,S)
% polarscatter(...,markertype) 
% polarscatter(...,'filled') 
% polarscatter(...,'PropertyName',propertyvalue)
% 
% See also: scatter
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

varargin=horzcat(varargin, 'UsePolar');
plotobj=scatter(target, varargin{:});

return
end