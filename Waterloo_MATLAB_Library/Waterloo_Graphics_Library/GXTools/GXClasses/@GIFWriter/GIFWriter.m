classdef GIFWriter
    % GIFWriter for creating animated GIFS
    %
    % Use:
    % writer=GIFWriter(fileName, source, interval, looping, timerFlag)
    % where:
    %   fileName is the file to create e.g. mygif.gif. If fileName is
    %            empty, the animation will be created in a memory cache.
    %   source   is a handle to a MATLAB container with a JavaFrame e.g. a
    %               figure (or a Java container)
    %   interval is the interval between frames in seconds
    %   looping  is true or false. When true the gif will cycle
    %            continuously when shown.
    %   timerFlag if present and true will attach a timer to the object.
    %             Control the timer using the start and stop methods
    %             (of the GIFWriter). The timer will automatically paint a
    %             frame to the GIF on each clock tick.
    %
    % To add frames to the GIFWriter manually, just call the add method 
    % after painting the object:
    %       writer.add();
    %
    % When the sequence is complete, close the output streams using
    %       write.close();%if a fileName was specified at construction time
    %       or
    %       write.close(fileName);% otherwise
    % That's it.
    %
    % ---------------------------------------------------------------------
    % Part of the sigTOOL Project and Project Waterloo from King's College
    % London.
    % http://sigtool.sourceforge.net/
    % http://sourceforge.net/projects/waterloo/
    %
    % Contact: ($$)sigtool(at)kcl($$).ac($$).uk($$)
    %
    % Author: Malcolm Lidierth 06/13
    % Copyright King's College London 2013-
    % ---------------------------------------------------------------------
    
    properties
        Object;
    end
    
    properties (GetAccess=private, SetAccess=private)
        timer=[];
        interval;
    end
    
    
    methods
        
        % Constructor
        function obj=GIFWriter(fileName, source, interval, looping, timerFlag)
            if ~isjava(source)
                if ispc
                    error('Sorry: GIFWriter does not work with MATLAB graphics on Windows platforms');
                end
                drawnow();
                [lastWarnMsg lastWarnId] = lastwarn;
                oldJFWarning=warning('off','MATLAB:HandleGraphics:ObsoletedProperty:JavaFrame');
                h=get(double(source),'JavaFrame');
                warning(oldJFWarning.state, 'MATLAB:HandleGraphics:ObsoletedProperty:JavaFrame');
                source=h.getFigurePanelContainer();
                source=javax.swing.SwingUtilities.getRootPane(source);
            end
            obj.interval=interval;
            if ~isempty(fileName)
                % Write to file
                obj.Object=kcl.waterloo.deploy.gif.GJGifSequencer.createInstance(fileName, source, interval*1000, looping);
            else
                % Write to memory
                obj.Object=kcl.waterloo.deploy.gif.GJGifSequencer.createInstance(source, interval*1000, looping);
            end
            javaObjectEDT(obj.Object);
            % N.B. This timer calls the add method Java-side (fast)
            if nargin>4 && timerFlag==true
                obj.timer=javax.swing.Timer(interval*1000,obj.Object);
            end
        end
        
        % Adds a frame
        function add(obj)
            obj.Object.add();
        end
        
        % Closes the GIF writer and all associated streams Java-side.
        function close(obj, varargin)
            % Stop timer first to avoid IllegalStateExceptions
            obj.stop();
            if nargin<2
                obj.Object.close();
            else
                obj.Object.close(varargin{1});
            end
        end
        
        % Starts the timer - adds one if needed
        function start(obj)
            if ~isempty(obj.timer)
                obj.timer.start();
            else
                % Add a timer if not specified at construction
                obj.timer=javax.swing.Timer(obj.interval*1000,obj.Object);
                obj.timer.start();
            end
        end
        
        % Stops the timer if present
        function stop(obj)
            if ~isempty(obj.timer)
                obj.timer.stop();
                obj.timer.removeActionListener(obj.Object);
            end
        end
        
    end
    
end



