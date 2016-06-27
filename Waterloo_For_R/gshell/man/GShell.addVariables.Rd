\name{GShell.addVariables}
\alias{GShell.addVariables}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.addVariables
}
\description{
Retrieves a variable from the Groovy shell's context.
}
\usage{
GShell.addVariables(varList)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varList}{
An R list containing the the variable name/value pairs to add to the shell context.
}
}

\value{
Returns nothing.
}

\author{
Malcolm Lidierth
}


%% ~Make other sections like Warning with \section{Warning }{....} ~

\seealso{
\code{\link{GShell.setVariable}}
}
\examples{
example <- function() {
GShell.addVariables(list(a=100,b=1:10,c="ABCDE"))
print(GShell.getVariable('a'))
print(GShell.getVariable('b'))
print(GShell.getVariable('c'))
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "addVariables", varList)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
