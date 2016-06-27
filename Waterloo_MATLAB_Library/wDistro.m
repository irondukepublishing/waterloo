function d=wDistro()
% Builds the folder for the distribution zip files - for dev use only

[folder file]=fileparts(which('waterloo.m'));

if isdir(fullfile(folder, '..', 'zipdistro'))
    rmdir(fullfile(folder, '..', 'zipdistro'),'s');
end

mkdir(fullfile(folder, '..', 'zipdistro'));
folder=fullfile(folder, '..');

% Main zip
zipfolder=fullfile(folder, 'zipdistro', 'waterloo');
mkdir(zipfolder);
copyfile(fullfile(folder, 'Waterloo_MATLAB_Library'), fullfile(zipfolder, 'Waterloo_MATLAB_Library'));
copyfile(fullfile(folder, 'Waterloo_Java_Library'), fullfile(zipfolder, 'Waterloo_Java_Library'));
if isdir(fullfile(zipfolder, 'Waterloo_Java_Library', 'GPLSupplement'))
    rmdir(fullfile(zipfolder, 'Waterloo_Java_Library', 'GPLSupplement'),'s');
end
copyfile(fullfile(folder, 'Waterloo_For_Groovy'), fullfile(zipfolder, 'Waterloo_For_Groovy'));
mkdir(fullfile(zipfolder, 'Waterloo_For_Groovy', 'demos'));
copyfile(fullfile(folder, 'Sources/Java/waterlooPlot/src/kcl/waterloo/groovy/scripts'), fullfile(zipfolder, 'Waterloo_For_Groovy', 'demos'));

copyfile(fullfile(folder, 'Waterloo_For_R'), fullfile(zipfolder, 'Waterloo_For_R'));
copyfile(fullfile(folder, 'Waterloo_For_Scilab'), fullfile(zipfolder, 'Waterloo_For_Scilab'));
copyfile(fullfile(folder, 'Waterloo_For_Python'), fullfile(zipfolder, 'Waterloo_For_Python'));

% GPL
zipfolder=fullfile(folder, 'zipdistro', 'GPLSupplement');
mkdir(zipfolder);
copyfile(fullfile(folder, 'Waterloo_Java_Library', 'GPLSupplement'), fullfile(zipfolder, 'GPLSupplement'));

% % Scala
% zipfolder=fullfile(folder, 'zipdistro', 'Waterloo_For_Scala');
% mkdir(zipfolder);
% copyfile(fullfile(folder, 'Waterloo_For_Scala'), fullfile(zipfolder, 'Waterloo_For_Scala'));

end
