\name{G.asList}
\alias{G.asList}

\title{
G.asList
}

\description{
Returns java.util.ArrayList<Object> given an R list or vector as input.
}
\usage{
G.asList(input)
}

\arguments{
\item{input}{
An R list or vector
}
}
\details{
If the input is an R vector:

The returned java.util.ArrayList<Object> instance will hold the the values in the vector.

If the input is an R list:

The returned java.util.ArrayList instance will hold the the key/value pairs form the R list as the even/odd numbered elements in the list. 
}
\value{
A java.util.ArrayList<Object>  
}

\author{
Malcolm Lidierth
}
\note{
The values in an ArrayList may not all be of the same type.
}

%% ~Make other sections like Warning with \section{Warning }{....} ~

\seealso{
G.asMap
}

\examples{
example <- function(){
out=G.asList(list(a=10, b=2, x="99"))
}
}

\keyword{programming}
\keyword{Interfaces to Other Languages}
