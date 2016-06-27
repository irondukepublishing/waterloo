\name{GShell.isDone}
\alias{GShell.isDone}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.isDone
}
\description{
Checks that the code from a previous call to eval- or run- Later has completed running. Note that chained calls can give wrong results.
}
\usage{
GShell.isDone()
}


\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(){
GShell.getEval()
}

## The function is currently defined as
function (string) 
J("kcl/waterloo/shell/GShell", "getEval")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
