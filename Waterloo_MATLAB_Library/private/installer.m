% To use Waterloo, you need to type "waterloo" at the MATLAB prompt
% at the start of each subsequent MATLAB session
if ~isempty(which('waterloo.m'))
    fprintf('It looks like Waterloo is already present at:\n%s\n',strrep(which('waterloo.m'),[filesep 'Waterloo_MATLAB_Library' filesep 'waterloo.m'],''));
    disp('Delete the old waterloo folder and remove it from your MATLAB path first');
else
    installFolder=fullfile(char(java.lang.System.getProperty('user.home')), 'Documents');
    disp('Downloading zip.....this will take a few minutes');
    % N.B. Using latest here does is not guaranteed to  give the same result as clicking latest on
    % the sourceforge site.
    f=unzip('http://sourceforge.net/projects/waterloo/files/Waterloo%5B1.1Beta%5D/waterloo.zip/download', installFolder);
    disp('Setting MATLAB Path.....');
    if isdir(fullfile(installFolder,'waterloo','Waterloo_MATLAB_Library'))
        addpath(fullfile(installFolder,'waterloo','Waterloo_MATLAB_Library'));
        savepath();
        fprintf('Installed the following %d files/folders:\n', numel(f));
        for k=1:numel(f)
            disp(f{k});
        end
        disp('To use Waterloo, you need to type "waterloo" at the MATLAB prompt during each MATLAB session');
        waterloo();
        disp('Note: You may be prompted to add a Waterloo Setting folder when first running Waterloo');
        disp('This allows you to set user-specified default colors etc.');
    else
        disp('Installation was not successful');
    end
end
