function explorer=GraphExplorer()
% GraphExplorer creates a GraphExplorer instance in MATLAB
% ---------------------------------------------------------------------
% Part of the sigTOOL Project and Project Waterloo from King's College
% London.
% http://sigtool.sourceforge.net/
% http://sourceforge.net/projects/waterloo/
%
% Contact: ($$)sigtool(at)kcl($$).ac($$).uk($$)
%
% Author: Malcolm Lidierth 12/10
% Copyright The Author & King's College London 2011-
% ---------------------------------------------------------------------

% % Suppress built in Groovy Console support for com.apple.eawt.Application methods
% % by temporarily changing the os.name. It's a quick fix that will stop
% % exceptions for the unfound quit handler etc. 
% % NOTE: THIS NOW DONE IN GROOVY CODE

% % All Java version specific.
% osname=java.lang.System.getProperty('os.name');
% if ismac()
%     java.lang.System.setProperty('os.name','')
% end

% % Create and run the instance
explorer=kcl.waterloo.explorer.GraphExplorer.createInstance();

% % Restore prefs
% if ismac()
%     java.lang.System.setProperty('os.name',osname)
% end
return
end
