\name{GShell.setBinding}
\alias{GShell.setBinding}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.setBinding
}
\description{
Sets the context of the current script
}
\usage{
GShell.setBinding(varList)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{varList}{
An R list of the variables and their values
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
GShell.setBinding(list(a=100,b=1:10,c="ABCDE"))
print(GShell.getVariable('a'))
print(GShell.getVariable('b'))
print(GShell.getVariable('c'))
}

## The function is currently defined as
function (varname, value) 
J("kcl/waterloo/shell/GShell", "setBinding", varList, G.value(value))
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
