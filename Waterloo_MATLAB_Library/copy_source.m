
thisFolder=fileparts (which('waterloo.m'));
thisFolder=fullfile(thisFolder,'..');
srcFolder=fullfile(thisFolder,'..','waterloo_src_only','Waterloo-Scientific-Graphics');
d=dir(srcFolder);
if ~isempty(d)
    % base
    source=fullfile(thisFolder, 'Sources', 'Java', 'kcl-waterloo-base', 'src');
    target=fullfile(srcFolder, 'kcl-waterloo-base','src');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);
    
    
    %GraphExplorer
    source=fullfile(thisFolder, 'Sources', 'Java', 'GraphExplorer', 'src');
    target=fullfile(srcFolder, 'GraphExplorer','src');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
    source=fullfile(thisFolder, 'Waterloo_For_Groovy');
    target=fullfile(srcFolder, 'Waterloo_For_Groovy');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
    source=fullfile(thisFolder, 'Waterloo_For_Python','demos');
    target=fullfile(srcFolder, 'Waterloo_For_Python','demos');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
    source=fullfile(thisFolder, 'Waterloo_For_R', 'gshell','R');
    target=fullfile(srcFolder, 'Waterloo_For_R','gshell','R');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
    source=fullfile(thisFolder, 'Waterloo_For_R', 'waterloo','R');
    target=fullfile(srcFolder, 'Waterloo_For_R','waterloo','R');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);
    
    source=fullfile(thisFolder, 'Waterloo_For_R', 'waterloo','demos');
    target=fullfile(srcFolder, 'Waterloo_For_R','waterloo','demos');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
    source=fullfile(thisFolder, 'Waterloo_MATLAB_Library');
    target=fullfile(srcFolder, 'Waterloo_MATLAB_Library');
    if(~isdir(target))
        mkdir(target)
    else
        delete(fullfile(target,'*.*'));
    end
    fprintf('Copying to %s\n', target);
    copyfile(fullfile(source,'*.*'),target);

    
end