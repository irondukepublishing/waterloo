\name{GShell.runQuery}
\alias{GShell.runQuery}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.runQuery
}
\description{
Evaluates the current script in the shell using the current shell context and on the event dispatch  thread using an invokeAndWait call if not on the EDT or a invokeLater call if on the EDT.
}
\usage{
GShell.runQuery()
}


\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(){
GShell.runQuery()
}

## The function is currently defined as
function () 
J("kcl/waterloo/shell/GShell", "runQuery")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
