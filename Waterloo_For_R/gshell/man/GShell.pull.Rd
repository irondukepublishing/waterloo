\name{GShell.pull}
\alias{GShell.pull}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.pull
}
\description{
Pulls a context from the head of a LIFO stack  and sets it as the current shell context.
The context should previously have been pushed to the stack.
}
\usage{
GShell.pull()
}

\value{
Returns nothing.
}

\author{
Malcolm Lidierth
}


%% ~Make other sections like Warning with \section{Warning }{....} ~

\seealso{
\code{\link{GShell.clear}}
}
\examples{
example <- function() {
GShell.pull()
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "pull")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
