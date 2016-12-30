ECHO OFF
IF EXIST C:\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar (
ECHO C:\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar
java -jar  C:\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar
) ELSE (
IF EXIST "%userprofile%\My Documents\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar" (
ECHO "%userprofile%\My Documents\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar"
java -jar  "%userprofile%\My Documents\waterloo\Sources\Java\GPLDistribution\GraphExplorer\dist\GraphExplorer.jar"
)
)
