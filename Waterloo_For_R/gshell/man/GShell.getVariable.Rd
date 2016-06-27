\name{GShell.getVariable}
\alias{GShell.getVariable}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.getVariable
}
\description{
Retrieves a variable from the Groovy shell's context.
}
\usage{
GShell.getVariable(varname)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varname}{
A string representing the name of the variable.
}
}

\value{
Returns the value associated with the variable in the Groovy shell context.
Returns null (and prints an error mesage) if the variable is not defined.
}

\author{
Malcolm Lidierth
}


\seealso{
\code{\link{GShell.setVariable}}
}
\examples{
example <- function(){
GShell.setVariable('Here is a message from Groovy')
print(GShell.getVariable('message'))
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "getVariable", varname)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
