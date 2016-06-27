\name{GShell.remove}
\alias{GShell.remove}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.remove
}
\description{
Remove a variable from the Groovy shell's context.
}
\usage{
GShell.remove(varname)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varname}{
A named variable
}
}

\value{
Returns nothing.
}

\author{
Malcolm Lidierth
}

\seealso{
\code{\link{GShell.clear}}
}
\examples{
example <- function() {
GShell.addVariables(list(a=100,b=1:10,c="ABCDE"))
print(GShell.remove('a'))
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "varname", varname)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
