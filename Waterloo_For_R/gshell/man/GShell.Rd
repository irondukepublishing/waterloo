\name{GShell}
\alias{GShell}

\title{
GShell - Groovy/Java script support for R.
}
\description{

gshell - provides access from R to the JVM via a Groovy shell instance.

The gshell package provides a set of functions that simplify the use of JVM languages such
as Groovy and Java from R.

With gshell, data can be shared with the JVM and arbitrary code provided as a string or file can be executed on-the-fly using a Groovy shell instance. The shell provides support for code written in the Groovy or Java languanges.

While gshell was developed to support Waterloo graphics in R, it provides a generic mechanism for compiling and running Groovy/Java using standard syntax and without using rJava directly. 
}



\details{
Calls to gshell functions take the form of:

GShell.methodname

where methodname maps directly to a static method in the GShell Groovy class, e.g.

y=GShell.evalEDT('new javax.swing.JPanel()')

returns a JPanel in y.

GShell.setVariable('x', 10)
y=GShell.eval('x * 10')

returns 100 in y.

The use of this convention here is preferred because code written as part of an R script using this convention can be cut-and-pasted with little or no change into e.g. a  Groovy, MATLAB or SciLab script.

Two factors limit such portability:
[1] Use of single quotes or double quotes as delimiters
[2] Line continuation conventions
To help, GShell.editor() function invokes a GUI based editor for Groovy and Java code in R  that includes automatic formatting of quotes and code-minification to remove line breaks.

In addition, gshell provides some convenience methods primarily intended for use internally but which may prove useful more generally. These methods take the form:

G.methodname.

E.g. file=G.file(string) takes an R formatted string as a path/file spec and returns a
java.io.File instance.

}

\references{
For the Groovy documentation see:
\url{http://waterloo.sourceforge.net/groovydoc/waterlooPlot/kcl/waterloo/shell/GShell.html
}
}

\author{
Malcolm Lidierth
}
\note{
The required groovy-all.jar files are bundled with the package and placed on the java class path when the library is loaded.

MacOS users:
To avoid "Apple AWT Java VM was loaded on first thread -- can't start AWT" errors or java.awt.HeadlessExceptions, use JGR.
}

%% ~Make other sections like Warning with \section{Warning }{....} ~

\seealso{
\code{\link{GShell.setVariable}}
}
\examples{
example <- function(x){
GShell.setVariable('x', G.value(x))
y=GShell.eval('def y=x * 10') 
print(y)
}
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
