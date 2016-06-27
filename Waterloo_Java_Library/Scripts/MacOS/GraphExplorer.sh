SCRIPT=$0
#export DYLD_LIBRARY_PATH=/Library/JavaVirtualMachines/jdk1.8.0.jdk/jre/lib:$DYLD_LIBRARY_PATH
#export DYLD_LIBRARY_PATH=/Library/JavaVirtualMachines/jdk1.7.0_12.jdk/jre/lib:$DYLD_LIBRARY_PATH
java -jar -Xdock:name="GraphExplorer"  -Dapple.awt.graphics.UseQuartz="true"  "$SCRIPT/../../../GraphExplorer/dist/GraphExplorer.jar"

