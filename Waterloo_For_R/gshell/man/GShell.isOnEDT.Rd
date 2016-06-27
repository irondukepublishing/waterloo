\name{GShell.isOnEDT}
\alias{GShell.isOnEDT}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.isOnEDT
}
\description{
Returns TRUE if the call is made on the event dispatch thread.
}
\usage{
GShell.isOnEDT()
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(){
GShell.isOnEDT()
}

## The function is currently defined as
function () 
J("kcl/waterloo/shell/GShell", "isOnEDT")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
