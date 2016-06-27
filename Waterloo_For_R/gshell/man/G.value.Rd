\name{G.value}
\alias{G.value}

%- Also NEED an '\alias' for EACH other topic documented here.
\title{
G.value
}

\description{
Returns a Java object given a Java or R  input.

If the input is an R reference, a suitable java object will be returned for subsequent use in gshell code.
If the input is already a Java object, it will be returned as-is.

G.value always returns a true Java object which may therefore be passed to Groovy methods specifying an Object as input. Groovy's reflection/auto-boxing/unboxing mechanisms can then be left to work, typically without any need to perform any casting of the Java object in R using rJava.

G.value is called internally as required by gshell code and should not need to be called explicitly to pass arguments to GShell functions.

}
\usage{
G.value(value)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
\item{value}{
Any variable or value
}
}

\value{

A java object.

If the input is a 1x1 R vector, a suitable boxed type will be returned.

For R vectors of length > 1, a suitable Java array will be returned representing the data by invoking the rJava .jarray function. 
}

\author{
Malcolm Lidierth
}

\seealso{
rJava/.jarray
}

\examples{
example <- function(){
G.value('10d')
}
}

% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
