function varargout=waterloo(option, useSplash)
% WATERLOO adds Project Waterloo files/folders to the MATLAB path/Java class path
%
% Example:
%       waterloo()
%           Loads all of Project waterloo
%
% To add components individually, or incrementally, supply an input argument
% which is the sum of the following
%      1 for the Graphics Library
%      2 for the Swing Library
%      4 for the Utilities functions
%      8 for the platform specific features
%     16 *Dev only*
% Thus, waterloo(15) would be equivalent to waterloo() with no arguments.
% Graphics/Swing libraries have dependencies on the Utilities.
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
% Author: Malcolm Lidierth 12/10
% Copyright The Author & King's College London 2011-
% ---------------------------------------------------------------------


wversion=1.1;

if nargin<2
    useSplash=true;
end

if nargin==1 && ischar(option) && strcmpi(option, 'version')
    d=dir(which('waterloo.m'));
    fprintf('Project Waterloo [Version=%g Dated:%s]\n', wversion, d.date);
    if nargout>0;varargout{1}=wversion;end
    return
end

% % Do  we have a dev release of Batik 1.8 - font support will be specific to the dev release so turn
% % off PDF support.
% if  ~verLessThan('matlab','8.0') && ~strfind(version(), '9.0')
%     fprintf('\nSuppressing PDF output via Batik from Waterloo - Apache Batik files not compatible\n');
%     fprintf('SVG and EPS formats are available\n\n');
%     java.lang.System.setProperty('waterloo_pdf_supported', 'false');
% end

if nargin==0
    option=15;
end
option=uint16(option);

loaded=java.lang.System.getProperty('Waterloo.MCODELoaded');

% Get rid of options if they are already loaded
if ~isempty(loaded)
    loaded=uint16(str2double(loaded));
    and=bitxor(loaded,option);
    option=bitand(and, option);
end

% Option selection
Graphics=bitget(option,1);
Swing=bitget(option,2);
Utilities=bitget(option,3);
Platform=bitget(option,4);

d=dir(which('waterloo.m'));

% Get the main waterloo folder path
thisFolder=fileparts(which('waterloo.m'));

folder=fullfile(thisFolder, '..', 'Sources');
if isdir(folder)
    DEV=true;
else
    DEV=false;
end


IDEA=false;

% Now add MATLAB code

if option
    
    % Note that with incremental additions, addpath may be called for
    % folders that are already added, but this is harmless
    
    fprintf('\nProject Waterloo');
    % Now install those components that are present
    folder=fullfile(thisFolder, 'Waterloo_Graphics_Library');
    if isdir(folder) && Graphics
        addpath(genpath(folder));
        fprintf('...Graphics Library loaded');
    end
    
    folder=fullfile(thisFolder, 'Waterloo_Swing_Library');
    if isdir(folder) && Swing
        addpath(genpath(folder));
        fprintf('...Swing Library loaded');
    end
    
    folder=fullfile(thisFolder, 'Utilities');
    if isdir(folder) && Utilities
        addpath(genpath(folder));
        fprintf('...Utilities loaded');
    end
    
    fprintf('\n');
    
    folder=fullfile(thisFolder, 'platform', computer());
    if isdir(folder) && Platform
        addpath(genpath(folder));
        fprintf('\nProject Waterloo Platform Library loaded [%s]\n', computer());
    end
    
end

% Set the Loaded flag
java.lang.System.setProperty('Waterloo.MCODELoaded',...
    num2str(bitor(option,uint16(str2double(java.lang.System.getProperty('Waterloo.MCODELoaded'))))));


fprintf('\nProject Waterloo [Version=%g Dated:%s]\n', wversion, d.date);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Add jars to the dynamic java class path if it's not there already
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



% Move up in folder tree using '..'

thisFolder=fullfile(thisFolder, '..');

if DEV==true  
    % This codes runs for development version only.
    % Synch the required dev folders to the distribution folder. Between
    % them, GraphExplorer and GraphExplorerFX contain all dependencies so
    % we'll use those for the LGPL distro. Dependencies in the /lib folders
    % are specified in the jar file manifests and will be available to
    % MATLAB once the main jars are installed on the classpath.
    % GraphExplorerFX will be available only if JavaFX has been set up on
    % the target installation.
    % GPL code in the supplement needs to be added by the end user.
    if(option>15)
        
        % GraphExplorer

        source=fullfile(thisFolder, 'Sources', 'Java', 'GraphExplorer', 'dist');
        target=fullfile(thisFolder, 'Waterloo_Java_Library','GraphExplorer', 'dist');
        if(~isdir(target))
            mkdir(target)
        else
            delete(fullfile(target,'lib', '*.*'));
        end
        copyfile(fullfile(source,'*.*'),target);
        % Delete GPL content (+ associated Waterloo code) from the LGPL distro
        jlatexmath=fullfile(target, 'lib', 'jlatexmath-1.0.3.jar');
        if exist(jlatexmath,'file')
            delete(jlatexmath);
        end
        gpl=fullfile(target, 'lib', 'kcl-gpl.jar');
        if exist(gpl,'file')
            delete(gpl);
        end
        
%         % IDEA build - dev only
%         IDEA=true;
%         source=fullfile(thisFolder, 'Sources', 'Java', 'kcl-waterloo-base', 'out','artifacts','kcl_waterloo_base_all_jar');
%         target=source;

        
%         % GraphExplorerFX
%         source=fullfile(thisFolder, 'Sources', 'Java', 'GraphExplorerFX', 'dist');
%         target=fullfile(thisFolder, 'Waterloo_Java_Library','GraphExplorerFX', 'dist');
%         if(~isdir(target))
%             mkdir(target)
%         else
%             delete(fullfile(target,'lib', '*.*'));
%         end
%         copyfile(source,target);
%         % If it is present delete the fx jar file - need to use the system
%         % installed copy
%         fxjar=fullfile(target, 'lib', 'jfxrt.jar');
%         if exist(fxjar,'file')
%             delete(fxjar);
%         end

%     if (strfind(char(java.lang.System.getProperty('java.version')),'1.8.'))
%         source=fullfile(thisFolder, 'Sources', 'Java', 'waterlooFX', 'dist', 'waterlooFX.jar');
%     end
    end 

    
    
end




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% NOW ADD THE JARS TO THE MATLAB DYNAMIC JAVA CLASS PATH
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
disp('Setting up Waterloo distribution');

if exist(folder, 'dir')
    % Standard distribution for users
    folder=fullfile(thisFolder, 'Waterloo_Java_Library');
else
    % Convenience for in-house dev. Saves copying over.
    folder=fullfile(thisFolder, 'Sources', 'Java');
end

% LGPL DISTRIBUTION

if IDEA==true
    if exist(target,'file')
        file=fullfile(target, 'kcl-waterloo-base-all.jar');
        dbase=dir(file);
        if ~isempty(dbase)
            javaaddpath(file);
            fprintf('IDEA build library dated ');disp(dbase.date);
        end
    end
else
    if exist(fullfile(folder, 'GraphExplorer', 'dist'),'file')
        file=fullfile(folder, 'GraphExplorer', 'dist', 'GraphExplorer.jar');
        dbase=dir(file);
        if ~isempty(dbase)
            javaaddpath(file);
            fprintf('Base library dated ');disp(dbase.date);
        end
    end
    
    
    % OPTIONAL GPL SUPPLEMENTS - these are distributed under the
    % GNU GPL not the GNU Lesser GPL and therefore not included in
    % the main distribution
    file=fullfile(folder, 'GPLSupplement', 'kcl-gpl', 'dist', 'kcl-gpl.jar');
    dbase=dir(file);
    if ~isempty(dbase)
        GPL=true;
        % If present, delete duplicate base jar file
        base=fullfile(folder, 'GPLSupplement', 'kcl-gpl', 'dist', 'lib', 'kcl-waterloo-base.jar');
        if exist(base, 'file')
            delete(base)
        end
        javaaddpath(file);
    else
        GPL=false;
    end
    

    % OLD stuff - will be dropped eventually
    %folder=fullfile(thisFolder,'kcl-waterloo-matlab', 'dist');
    file=fullfile(folder, 'kcl-waterloo-matlab', 'dist', 'kcl-waterloo-matlab.jar');
    dbase=dir(file);
    if ~isempty(dbase)
        javaaddpath(file);
    end
    
    
    % Set GShell search path
    kcl.waterloo.shell.GShell.addImport('kcl.waterloo.plot.WPlot');
    
end
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

java.lang.System.setProperty('Waterloo.JavaLoaded', 'true');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fprintf('\nProject Waterloo Java files added to MATLAB Java class path\n');


% Workarounds

% Mac OS X
if ismac()
    v=java.lang.System.getProperty('java.version');
    if v.startsWith('1.7')
        % Among them:
        % Various JFileChooser issues (not all fixed)
        % http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7124253
        if ~isempty(strfind(v, '_6')) || ~isempty(strfind(v, '_7')) || ~isempty(strfind(v, '_8'))
        else
            warning('waterloo:java', 'Java version 1.7.0_60 or higher needed for clipboard functionality on Mac - version installed is %s\n', char(v));
        end
    end
    
    if ~v.startsWith('1.6')
        kcl.waterloo.actions.ActionServices.setUseChooser(false);
    end
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% LOGGING
% MATLAB ships with log4j - so use that.

% SET LEVEL
% 2 >= turn off for all classes below;
% 1 >= turn off for Waterloo classes
% 0 == attach appender for all classes below

LOGGING=0;

% Waterloo

logLevel=org.apache.log4j.Level.DEBUG;
logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.demo.Main');
logger.setLevel(org.apache.log4j.Level.OFF);

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.logging.CommonLogger');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.actions.ActionServices');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.defaults.GJDefaults');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.deploy.gif.GJGifSequencer');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.deploy.pde.PDEGraphics2D');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.xml.GJDecoder');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.xml.GJEncoder');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end

logger = org.apache.log4j.Logger.getLogger('kcl.waterloo.swing.GCFrame');
if LOGGING>=1
    logger.setLevel(org.apache.log4j.Level.OFF);
else
    logger.setLevel(logLevel);
    logger.addAppender(org.apache.log4j.ConsoleAppender(org.apache.log4j.SimpleLayout()));
end


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% JAVAFX support. For Java 6 the users needs to install the JavaFX runtime
% (it's included in Java7u4 onwards)
if DEV;addFXSupport(DEV, fullfile(thisFolder, 'Waterloo_Java_Library'));end;


% % Is sigTOOL available? Load it of true.
% fprintf('\nLooking for sigTOOL support...');
% filename=which('sigTOOL.m');
% if ~isempty(filename)
%     fprintf('Found.\nAdding sigTOOL support.\n');
%     jar=fullfile(thisFolder, 'eclipse', 'sigTOOLGUI', 'dist', 'sigTOOLGUI.jar');
%     if exist(jar,'file')
%         javaaddpath(jar);
%     end
% end

try
    if useSplash
        welcomeFrame=kcl.waterloo.gui.Welcome.createWelcome();
        t=timer('StartDelay', 5, 'TimerFcn', @TimerCallback, 'ExecutionMode', 'singleShot',  'UserData', welcomeFrame);
        start(t);
    end
catch
    d=dir(fullfile(thisFolder,'Waterloo_Java_Library'));
    if isempty(d)
        fprintf('\nThe Waterloo MATLAB library appears to have been installed without the accompying Java code.\nPlease install following the instructions <a href="matlab:web(''-browser'',''http://waterloo.sourceforge.net/installation.html'')">here</a>.\n');
    else
        fprintf('\nUnexpected error');
    end
    return
end

% System checks
if ismac()
    quartzOn=java.lang.System.getProperty('apple.awt.graphics.UseQuartz');
    if isempty(quartzOn) || strcmpi(quartzOn, 'false')
        disp('----------------------------------------------------------------------');
        disp('The Quartz graphics pipelines is not enabled so Waterloo graphics will be much slower.');
        disp('To enable Quartz edit/create the MATLAB java.opts file and add "-Dapple.awt.graphics.UseQuartz=true"');
        disp('See the Waterloo Setup PDF for details');
        disp('----------------------------------------------------------------------');
    end
elseif ispc()
    directXOn=java.lang.System.getProperty('sun.java2d.noddraw');
    if isempty(directXOn) || strcmpi(directXOn, 'true')
        disp('----------------------------------------------------------------------');
        disp('You are on Windows and the DirectX graphics pipeline is not enabled.');
        disp('You might see better performance by creating a java.opts file with:');
        disp('-Dsun.java2d.noddraw=false');
        disp('Also, upgrading to a recent release of Java 6 can improve performance');
        disp('As of 06.10.2012, the latest is update 1.6.0_35');
        disp('You have:');
        version('-java');
        disp('----------------------------------------------------------------------');
    end
end


% Now set options


% Set up compression as the default for XML output
kcl.waterloo.xml.GJEncoder.setCompression(true);

% If wstartup.m exists, run it. Users can put their own options in the
% file.
if exist('wstartup.m','file')
    wstartup();
end

if Graphics
    % Need the full distro with charting for this
    disp('For a demo type <a href="matlab:WaterlooTest">WaterlooTest</a> at the MATLAB command line');
end

disp('');
disp('----------------------------------------------------------------------------');
disp('Project Waterloo is free software:  you can redistribute it and/or modify');
disp('it under the terms of the GNU Lesser General Public License as published by');
disp('the Free Software Foundation, either version 3 of the License, or');
disp('(at your option) any later version.');
disp(' ');
disp('Project Waterloo is distributed in the hope that it will  be useful,');
disp('but WITHOUT ANY WARRANTY; without even the implied warranty of');
disp('MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the');
disp('GNU Lesser General Public License for more details.');
disp(' ');
disp('You should have received a copy of the GNU Lesser General Public License');
disp('along with this program.  If not, see <a href="matlab:web(''-browser'',''http://www.gnu.org/licenses'')">http://www.gnu.org/licenses/</a>.');
disp('----------------------------------------------------------------------------');
disp('');

if GPL
disp('----------------------------------------------------------------------------');
    disp(' GNU General Public License supplementary code found and loaded');
    disp('This code may be redistributed under the GPL license only (not the LGPL)');
disp('----------------------------------------------------------------------------');
end
disp('');

disp('For further information see the <a href="matlab:web(''-browser'',''http://www.irondukepublishing.com'')">Ironduke Publishing Ltd</a> website.');
return
end

% Gets rid of the splash screen
function TimerCallback(tim, EventData)
welcomeFrame=get(tim, 'UserData');
if ~isempty(welcomeFrame)
    welcomeFrame.dispose();
end
stop(tim);
delete(tim);
end

function addFXSupport(DEVELOPER, thisFolder)
% FX=java.lang.System.getProperty('javafx.runtime.version');
% 
% if isempty(FX) && DEVELOPER
%     % JRE6
%     fprintf('\nLooking for JavaFX support...');
%     JavaFX_HOME=java.lang.System.getProperties().get('JAVAFX_RUNTIME');
%     if isempty(JavaFX_HOME)
%         disp('skipping JavaFX installation - "JAVAFX_RUNTIME" not set');
%         return
%     else
%         jar=fullfile(JavaFX_HOME, 'jfxrt.jar');
%         if exist(jar,'file')
%             fprintf('Found.\nAdding JavaFX support.\n');
%             % Users can comment this out if the path is already set
%             if ismac()
%                 setenv('DYLD_LIBRARY_PATH', [getenv('DYLD_LIBRARY_PATH') JavaFX_HOME]);
%                 fprintf('Adding %s to DYLD_LIBRARY_PATH\n', JavaFX_HOME);
%             elseif isunix()
%                 setenv('LD_LIBRARY_PATH', [getenv('LD_LIBRARY_PATH') JavaFX_HOME]);
%                 fprintf('Adding %s to LD_LIBRARY_PATH\n', JavaFX_HOME);
%             end
%             javaaddpath(jar);
%         end
%     end
% end
% 
% if (DEVELOPER)
%     % GraphExplorerFX not presently distributed
%     jar=fullfile(thisFolder, 'GraphExplorerFX', 'dist', 'GraphExplorerFX.jar');
%     if exist(jar,'file')
%         javaaddpath(jar);
%     end
%     prop=java.lang.System.getProperties();
%     prop.put('WATERLOO_JAVAFX_LOADED', 'TRUE');
% else
%     fprintf('Not Found: %s\n', jar);
% end
end


