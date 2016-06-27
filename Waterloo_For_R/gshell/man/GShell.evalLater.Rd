\name{GShell.evalLater}
\alias{GShell.evalLater}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.evalLater
}
\description{
Evaluates the supplied script in the shell using the current shell context and on the event dispatch  thread using an invokeLater call.
}
\usage{
GShell.evalLater(string)
}
%- maybe also 'usage' for other objects documented here.


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
GShell.evalLater(string)
}

## The function is currently defined as
function (string) 
J("kcl/waterloo/shell/GShell", "evalLater", string)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
