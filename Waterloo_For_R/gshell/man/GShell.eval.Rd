\name{GShell.eval}
\alias{GShell.eval}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.eval
}
\description{
Evaluates the supplied script in the shell using the current shell context and on the current thread.
}
\usage{
GShell.eval(string)
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
GShell.eval(string)
}
}

% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
