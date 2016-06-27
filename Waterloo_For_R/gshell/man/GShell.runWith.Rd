\name{GShell.runWith}
\alias{GShell.runWith}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.runWith
}
\description{
Runs the current script in the shell using the supplied shell context on the current thread.
}
\usage{
GShell.runWith(map)
}

\arguments{
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
GShell.eval(string)
}

## The function is currently defined as
function (string) 
J("kcl/waterloo/shell/GShell", "eval", string)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
