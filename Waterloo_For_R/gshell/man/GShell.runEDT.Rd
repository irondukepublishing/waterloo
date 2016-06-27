\name{GShell.runEDT}
\alias{GShell.runEDT}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.runEDT
}
\description{
Evaluates the current script in the shell using the current shell context and on the event dispatch thread using an invokeAndWait call.
}
\usage{
GShell.runEDT()
}


\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(){
GShell.runEDT()
}

## The function is currently defined as
function () 
J("kcl/waterloo/shell/GShell", "runEDT")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
