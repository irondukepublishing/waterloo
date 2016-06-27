\name{G.asMap}
\alias{G.asMap}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
G.asMap
}
\description{
Returns java.util.LinkedHashMap<String, Object> given an R list.
}
\usage{
G.asMap(inList)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
\item{inList}{
An R list
}
}


\value{
The returned java.util.LinkedHashMap<String, Object> instance will hold the the key/value pairs form the R list as the key/value pairs of the Map. 
}

\author{
Malcolm Lidierth
}


\seealso{
G.asList
}

\examples{
example <- function(){
G.asMap(list(a='10d', b=99, c=TRUE))
}
}


% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
