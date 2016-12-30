# Project Waterloo base library

# This is a modular project.

You can build this project to create a pure Java version of Waterloo
or build it as part of the GraphExplorer module which makes the Groovy modules
available also.


## To build a pure Java library:

Change: the base project is now self-contained and has no external Netbeans project dependencies

### Netbeans
- Open the kcl-waterloo-base folder - this contains a Netbeans project
- Right-click on the kcl-waterloo-base node and select "Properties/Build/Packaging" then select "Copy Dependent Libraries".
- Right-click on the kcl-waterloo-base node and select "Clean and Build"

kcl-waterloo-base.jar will be added to the /dist folder. All dependent jars will be copied to
the /dist/lib folder

### IntelliJ IDEA
- Open the kcl-waterloo-base folder - this contains an IDEA Project
- Select Build -> Build artifacts
- A single jar containing Waterloo and all dependencies will be built as
  ".../kcl-waterloo-base/out/artifacts/kcl_waterloo_base_all_jar/kcl-waterloo-base-all.jar"

## To build a library including both the Java and Groovy modules:

### Netbeans
 - Open the GraphExplorer project
- Right-click on the project node and select "Open Required Projects"
- Right-click on the GraphExplorer node and select "Properties/Libraries" then select "Build Projects on Classpath".
This needs to be selected in <strong>all</strong> projects.
- Right-click on the GraphExplorer node and select "Properties/Build/Packaging" then select "Copy Dependent Libraries".
- Right-click on the GraphExplorer and select "Clean and Build"

GraphExplorer.jar will be added to the /dist folder. All dependent jars will be copied to
the /dist/lib folder


 /