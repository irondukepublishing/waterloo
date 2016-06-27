\name{GShell.addToBinding}
\alias{GShell.addToBinding}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.addToBinding
}
\description{
Sets a named variable in the current scripts context.

}
\usage{
GShell.addToBinding(varList)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varList}{
 An R list of representing the variable name/value pairs
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

\seealso{
\code{\link{GShell.getBinding}}
}

\examples{
example <-function(input){
GShell.addToBinding('key', input)
}

## The function is currently defined as
function (varList) 
J("kcl/waterloo/shell/GShell", "addToBinding", varList)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
