\name{GShell.addImport}
\alias{GShell.addImport}
%- Also NEED an '\alias' for EACH other topic documented here.
\title{
GShell.addImport
}
\description{
Add a java package to the default list of the Groovy shell's context.
Packages added in this way will be available to all scripts in the current
session. 
}
\usage{
GShell.addImport(package)
}
%- maybe also 'usage' for other objects documented here.
\arguments{
  \item{package}{
The Java/Groovy/Scala package to import
}
}

\value{
Returns nothing.
}

\author{
Malcolm Lidierth
}


\seealso{
\code{\link{GShell.addImport}}
}
\examples{
example <- function() {
GShell.addImport("javax.swing.JButton")
}

## The function is currently defined as
function (varname) 
J("kcl/waterloo/shell/GShell", "addImport", package)
}
% Add one or more standard keywords, see file 'KEYWORDS' in the
% R documentation directory.
\keyword{programming}
\keyword{Interfaces to Other Languages}% __ONLY ONE__ keyword per line
