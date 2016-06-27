\name{GShell.pullScript}
\alias{GShell.pullScript}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.putScript
}
\description{
Retrievs the named script as a GroovyCodeSource instance from a stack of scripts using the specified name and sets it as the current script.
}
\usage{
GShell.pullScript(string)
}

\arguments{
\item{string}{
The name of the script.
}
}


\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(string){
GShell.pullScript(string)
}
}


% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
