\name{GShell.push}
\alias{GShell.push}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.push
}
\description{
Pushes the current shell context to LIFO stack.
}
\usage{
GShell.push()
}

\value{
Returns nothing.
}

\author{
Malcolm Lidierth
}


\examples{
example <- function() {
GShell.push()
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "push")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
