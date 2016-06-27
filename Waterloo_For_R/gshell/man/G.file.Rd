\name{G.file}
\alias{G.file}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
G.file
}
\description{
Returns a java.io.File instance given a string description.
}
\usage{
G.file(string)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
\item{string}{
An R string describing the full file path details.
}
}


\value{
A java.io.File instance
}

\author{
Malcolm Lidierth
}


%% ~Make other sections like Warning with \section{Warning }{....} ~


\examples{
example <- function(){
print("This demo does nothing unless you uncomment the GShell.clear() call")
# Uncomment the line below to get this script to work
#G.file()
}
}


% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
