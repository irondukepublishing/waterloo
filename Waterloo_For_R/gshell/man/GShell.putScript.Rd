\name{GShell.putScript}
\alias{GShell.putScript}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.putScript
}
\description{
Places the current script as a GroovyCodeSource instance in a stack of scripts using the specified name.
}
\usage{
GShell.putScript(string)
}

\arguments{
\item{string}{
The name to apply to the saved GroovyCodeSOurce instance.
}
}



\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(string){
GShell.putScript(string)
}
}


% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
