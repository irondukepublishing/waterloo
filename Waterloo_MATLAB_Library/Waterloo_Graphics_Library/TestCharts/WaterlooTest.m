function WaterlooTest()
% WaterlooTest
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
%   
%  This program is free software: you can redistribute it and/or modify
%  it under the terms of the GNU General Public License as published by
%  the Free Software Foundation, either version 3 of the License, or
%  (at your option) any later version.
%  
%  This program is distributed in the hope that it will be useful,
%  but WITHOUT ANY WARRANTY; without even the implied warranty of
%  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
%  GNU General Public License for more details.
%  
%  You should have received a copy of the GNU General Public License
%  along with this program.  If not, see <http://www.gnu.org/licenses/>.
%
% ---------------------------------------------------------------------

disp(' ');
disp('WaterlooTest:');
disp('These demos test the Waterloo graphics code.');
disp('The m-files are extensively commented to show users how to use this library.');

disp(' ');
disp('A few plots look "wrong": they are tests as well as demos, so that may be deliberate.');


tTest=tic;
TestDemo1();
TestScatter();
TestScatter2();
TestStairs();
TestAreaFill();
TestAreaFill2();
TestError();

TestBar1();
TestBar2();
TestBar3();
TestBar4();
TestPie1();
TestPolarBar();
TestStem();
TestCompass();
TestFeather();
TestPaint();
TestPaint2();
TestQuiver();
TestAnnotation();
TestCategories();
TestMixed();
TestContour();
TestComponentAnnotation();
TestWFrame();
TestLayers();

TestAnimation();
kcl.waterloo.demo.Main.main([]);

toc(tTest);
% % These tests are not run as part of the standard test run:
% 
% % Demo of timed updates of plots for "real-time" plotting
% TestUpdate1();
% TestUpdate2();
% TestUpdate3();


 
% % Shows how to install MATLAB callbacks on Waterloo data wrappers, plots,
% % graphs and graph containers.
% TestCallbacks();



return
end