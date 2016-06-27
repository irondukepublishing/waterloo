\name{GShell.getBinding}
\alias{GShell.getBinding}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.getBinding
}
\description{
Returns the context of the current script

}
\usage{
GShell.getBinding()
}
%- maybe also 'usage' for other objects documented here.

\value{
Returns the context
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}


%% ~Make other sections like Warning with \section{Warning }{....} ~

\seealso{
\code{\link{GShell.setBinding}}
}

\examples{
example <-function(input){
GShell.getBinding()
}

## The function is currently defined as
function (varname, value) 
J("kcl/waterloo/shell/GShell", "getBinding")
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
