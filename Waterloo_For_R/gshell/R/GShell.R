# This code is part of Project Waterloo from King's College London
# <http://waterloo.sourceforge.net/>
#
# Copyright King's College London  2013-
# 
# @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
# 
# Project Waterloo is free software:  you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Project Waterloo is distributed in the hope that it will  be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

# Returns a java.io.File given an R style string file name
G.file <- function(string) .jnew("java/io/File", string)

# For scalar inputs (i.e. 1x1 vectors in R ), create a 1x1 Java object. 
# For R vectors, create a Java array.
# For java objects, return the object unchanged.
G.value <- function(value) {
  if (is.numeric(value) && length(value)<2) {
    # Use BigDecimal here. No point for R but other systems support int64, quadruple IEEE etc
    # and this should preserve the available precision in those systems.
    .jnew("java/math/BigDecimal", value) 
  } else if (is.character(value) && length(value)<2) {
    .jnew("java/lang/String", value)
  } else if (is.logical(value) && length(value)<2) {
    .jnew("java/lang/Boolean", value)
  } else {
    tryCatch({
      if (.jinstanceof(value, "java.lang.Object")){
        value
      }
    }, error = function(e) {
      .jarray(value)
    })
  }
}

G.asTable <- function(inMatrix) {
  if (is.matrix(inMatrix)){
    y=.jnew("kcl/waterloo/data/dataset/GJDataTable")
    J(y, "setColumnNames", colnames(inMatrix))
    J(y, "setRowNames", rownames(inMatrix))
    dim=dim(inMatrix)
    for (k in 1:dim[2]){
      J(y, "addDataColumn", G.asList(as.vector(inMatrix[,k])))
    }
    y
  } else {
    print("G.asMatrix: Not a matrix")
  }
}
  
# Returns a java.util.HashMap given an R style list as input
G.asMap <-  function(inList) {
  if (is.list(inList)) {
    arrList=.jnew("java.util.HashMap")
    nm=names(inList)
    for (k in 1:length(nm)) {
      name = .jnew("java/lang/String", nm[k])
      J(arrList, "put", name, G.value(inList[[k]]))
    }
    arrList
  } else {
    print(sprintf("G.asMap: %s not supported as input. R list required.", class(inList)))
  }
}

# Returns a java.util.ArrayList given an R style list or vector as input
G.asList <-  function(input) {
  if (is.list(input)) {
    arrList=.jnew("java.util.ArrayList")
    nm=names(input)
    for (k in 1:length(nm)) {
      name = .jnew("java/lang/String", nm[k])
      J(arrList, "add", name)
      J(arrList, "add", G.value(input[[k]]))
    }
    arrList
  } else if (is.vector(input)) {
    arrList=.jnew("java.util.ArrayList")
    for (k in 1:length(input)) {
      J(arrList, "add", G.value(input[k]))
    }
    arrList
  } else {
    print(sprintf("G.asList: %s not supported as input. R list or vector required.", class(input)))
  }
}


# Creates or replaces the value in the named variable with the supplied value
GShell.setVariable <- function(varname, value) J("kcl/waterloo/shell/GShell", "setVariable", varname, G.value(value))
# Returns the value of the named variable (by reference? Check)
GShell.getVariable <- function(varname) J("kcl/waterloo/shell/GShell", "getVariable", varname)
# Supplements the shell context by adding or replacing the values in
# the specified variables.
GShell.addVariables <-  function(varList) {
  nm=names(varList)
  for (k in 1:length(nm)) {
    name = .jnew("java/lang/String", nm[k])
    val = varList[[k]]
    GShell.setVariable(name, val)
  }
}
# Clears the shell context
GShell.clear <- function() J("kcl/waterloo/shell/GShell", "clear")
# Removes the specified variable from the shell context
GShell.remove <- function(varname) J("kcl/waterloo/shell/GShell", "remove", varname)

GShell.addImport <- function(package) J("kcl/waterloo/shell/GShell", "addImport", package)

# Returns the binding for the current script. If this is empty, the shell context will
# be used.
GShell.getBinding <- function() J("kcl/waterloo/shell/GShell", "getBinding")

# Sets the binding for the current script.
GShell.setBinding <-  function(varList) {
  binding=GShell.eval("new groovy.lang.Binding()")
  J("kcl/waterloo/shell/GShell", "setBinding", binding)
  nm=names(varList)
  for (k in 1:length(nm)) {
    name = .jnew("java/lang/String", nm[k])
    value = varList[[k]]
    J("kcl/waterloo/shell/GShell", "addToBinding", name, G.value(value))
  }
}

# Supplements the script binding by adding or replacing the values in
# the specified variables.
GShell.addToBinding <-  function(varList) {
  nm=names(varList)
  for (k in 1:length(nm)) {
    name = .jnew("java/lang/String", nm[k])
    value= varList[[k]]
    J("kcl/waterloo/shell/GShell", "addToBinding", name, G.value(value))
  }
}

# Evaluation of scripts
# Compiles and runs the supplied script which may
# be a specified as a java.lang.String, java.io.File or java.io.Reader
GShell.eval <- function(string) J("kcl/waterloo/shell/GShell", "eval", string)
# Compiles and runs the supplied script on the EDT using invokeAndWait. The script may
# be a specified as a java.lang.String, java.io.File or java.io.Reader
GShell.evalEDT <- function(string) J("kcl/waterloo/shell/GShell", "evalEDT", string)
# Compiles and runs the supplied script on the EDT using invokeLater. The script may
# be a specified as a java.lang.String, java.io.File or java.io.Reader
GShell.evalLater <- function(string) J("kcl/waterloo/shell/GShell", "evalLater", string)
GShell.evalQuery <- function(string) J("kcl/waterloo/shell/GShell", "evalQuery", string)
GShell.evalWith <- function(string, map) J("kcl/waterloo/shell/GShell", "evalWith", string, map)
GShell.evalEDTWith <- function(string, map) J("kcl/waterloo/shell/GShell", "evalEDTWith", string, map)
# Loads and compiles the specified script and sets it as the "current script". The script may
# be a specified as:
# [1] a java.lang.String giving a fully qualified file name
# [2] a java.io.File or java.net.URL
GShell.load <- function(script) J("kcl/waterloo/shell/GShell", "load", J(G.file(script),"getPath"))
# Runs the current script
GShell.run <- function() J("kcl/waterloo/shell/GShell", "run")
# Runs the current script on the EDT using invokeAndWait
GShell.runEDT <- function() J("kcl/waterloo/shell/GShell", "runEDT")
# Runs the current script on the EDT using invokeLater
GShell.runLater <- function() J("kcl/waterloo/shell/GShell", "runLater")
GShell.runQuery <- function() J("kcl/waterloo/shell/GShell", "runQuery")
GShell.runWith <- function(map) J("kcl/waterloo/shell/GShell", "runWith", map)
GShell.runEDTWith <- function(map) J("kcl/waterloo/shell/GShell", "runEDTWith", map)
# Saves the current script to internal storage using the supplied name
GShell.putScript <- function(string) J("kcl/waterloo/shell/GShell", "putScript", string)
# Retrieves the named script from internal storage and makes it the current script
GShell.pullScript <- function(string) J("kcl/waterloo/shell/GShell", "pullScript", string)
# Returns a list of the scripts presently in the internal store
GShell.getScriptList <- function() J("kcl/waterloo/shell/GShell", "getScriptList")
# Returns the current script as a GroovyCodeSource instance
GShell.getSource <- function() J("kcl/waterloo/shell/GShell", "getSource")
# Sets the current script using the supplied GroovyCodeSource instance
GShell.setSource <- function(script) J("kcl/waterloo/shell/GShell", "setSource", script)

GShell.getEval <- function() J("kcl/waterloo/shell/GShell", "getEval")
GShell.isDone <- function() J("kcl/waterloo/shell/GShell", "isDone")
GShell.isOnEDT <- function() J("kcl/waterloo/shell/GShell", "isOnEDT")

GShell.push <- function() J("kcl/waterloo/shell/GShell", "push")
GShell.pull <- function() J("kcl/waterloo/shell/GShell", "pull")

GShell.getSwing <- function() J("kcl/waterloo/shell/GShell", "getSwing")
GShell.console <- function() J("kcl/waterloo/shell/GShell", "console")
GShell.editor <- function() J("kcl/waterloo/shell/GShell", "editor")
# Sets the Java Look and Feel
# Use GShell.lookAndFeel("system") to choose the default L&F for your platform.
# Note that there is presently an issue with Nimbus - avoid for the moment
GShell.lookAndFeel <- function(string) J("kcl/waterloo/shell/GShell", "lookAndFeel", string)
