\name{GShell.evalWith}
\alias{GShell.evalWith}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.evalWith
}
\description{
Evaluates the supplied script in the shell using the supplied shell context and on the current thread.
}
\usage{
GShell.evalWith(string, map)
}
%- maybe also 'usage' for other objects documented here.


\arguments{
\item{string}{
The code to evaluate.
}
\item{map}{
The map to use.
}
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}


\examples{
example <-function(string){
GShell.evalWith(string)
}
}


% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
