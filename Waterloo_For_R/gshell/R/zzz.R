# 
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
#############################################################################
#
# THIS CODE IS CURRENTLY UNDER DEVELOPMENT.
#
#############################################################################
# This R script sets up R to integrate easily with the Waterloo Graphics
# Java-based scientific charting package.
#
# The R functions defined here are designed to work as though direct calls
# to the corresponding Java/Groovy methods were being made. Therefore,
# the Java/Groovy Docs usage descriptions should apply equally 
# to the R functions defined here.
#
# For a description with examples see the project web site at:
#     	http://waterloo.sourceforge.net
# which also provides links to the binary downloads for the project
# and the Git source repository.
#
.onLoad <- function(libname, pkgname) {

  switch(Sys.info()[['sysname']],
         Windows= {
           .jinit("-Dsun.java2d.noddraw=false")    
         },
         Linux  = {
         },
         Darwin = {.jinit("-Dapple.awt.graphics.UseQuartz=true")       
         })
  
  .jpackage(pkgname, jars="/GShell/dist/GShell.jar")
  
}





  
