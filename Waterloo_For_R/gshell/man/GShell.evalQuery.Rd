\name{GShell.evalQuery}
\alias{GShell.evalQuery}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.evalQuery
}
\description{
Evaluates the supplied script in the shell using the current shell context and on the event dispatch  thread using an invokeAndWait call if not on the EDT or a invokeLater call if on the EDT.
}
\usage{
GShell.evalQuery(string)
}

\arguments{
\item{string}{
The code to evaluate.
}

}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(string){
GShell.evalQuery(string)
}

## The function is currently defined as
function (string) 
J("kcl/waterloo/shell/GShell", "evalQuery", string)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
