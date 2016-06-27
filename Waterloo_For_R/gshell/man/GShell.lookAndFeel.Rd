\name{GShell.lookAndFeel}
\alias{GShell.lookAndFeel}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.lookAndFeel
}
\description{
Returns TRUE if the call is made on the event dispatch thread.
}
\usage{
GShell.lookAndFeel(string)
}
\arguments{
\item{string}{
The name of the required look and feel.
}
}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}

\examples{
example <-function(string){
GShell.lookAndFeel(string)
}

## The function is currently defined as
function (string) 
J("kcl/waterloo/shell/GShell", "lookAndFeel", string)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
