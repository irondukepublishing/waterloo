\name{GShell.getEval}
\alias{GShell.getEval}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.getEval
}
\description{
Use this to retrieve the result of a previous eval- or run- Later call.
To check that the code has completed running, use GShell.isDone(). 
}
\usage{
GShell.getEval()
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
