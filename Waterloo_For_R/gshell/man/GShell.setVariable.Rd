\name{GShell.setVariable}
\alias{GShell.setVariable}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.setVariable
}
\description{
Sets a named variable to a supplied value in the default context of the Groovy shell instance.

The variables can be used name in script run using the eval() and run() methods.

Note that the default context is used inly by scripts that do not have their own binding set.
}
\usage{
GShell.setVariable(varname, value)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varname}{
 A string representing the variable name
}
  \item{value}{
 The value to associate with the variable.
}
}
\details{
If the variable already exists, the supplied value will replace the existing value. Otherwise, the variable will be created.
}
\value{
Does not return a value.
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}
\note{
Variables defined within the shell context can be accessed by their name in Groovy scripts run with the GShell eval and run methods.

They may be retrieved in an R script using GShell.getVariable(varname).
}

\seealso{
\code{\link{GShell.getVariable}}
}

\examples{
example <-function(input){
GShell.setVariable('key', input)
y=GShell.eval('input*10')
print(sprintf('The value of the input * 100 is:',y))
}

## The function is currently defined as
function (varname, value) 
J("kcl/waterloo/shell/GShell", "setVariable", varname, G.value(value))
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
