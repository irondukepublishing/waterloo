\name{GShell.evalEDT}
\alias{GShell.evalEDT}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.evalEDT
}
\description{
Evaluates the supplied script in the shell using the current shell context and on the event dispatch  thread using an invokeAndWait call.
}
\usage{
GShell.evalEDT(string)
}

\arguments{
\item{string}{
The code to evaluate.
}

}

\author{
%%  ~~who you are~~
Malcolm Lidierth
}


\examples{
example <-function(string){
GShell.evalEDT(string)
}
}

% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
