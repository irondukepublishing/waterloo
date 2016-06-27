\name{GShell.runLater}
\alias{GShell.runLater}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.runLater
}
\description{
Evaluates the current script in the shell using the current shell context and on the event dispatch thread using an invokeLater call.
}
\usage{
GShell.runLater()
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(){
GShell.runLater()
}

## The function is currently defined as
function () 
J("kcl/waterloo/shell/GShell", "runLater")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
