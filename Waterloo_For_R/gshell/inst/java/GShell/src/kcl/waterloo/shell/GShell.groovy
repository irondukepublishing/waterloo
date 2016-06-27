/*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2013-
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package kcl.waterloo.shell

import groovy.swing.SwingBuilder
import groovy.ui.Console
import groovy.ui.ConsoleTextEditor
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.MultipleCompilationErrorsException

import javax.swing.*
import java.awt.*
import java.awt.BorderLayout as BL
import java.awt.datatransfer.StringSelection
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.LinkedHashMap

import static java.awt.Toolkit.getDefaultToolkit

/**
 * <code>GShell</code> provides static methods for running scripts written in Java and Groovy from
 * an environment such as MATLAB, Python, R, SciLab etc.
 *
 * <code>GShell</code> has a <code>GroovyShell</code> embedded as a static property. Scripts can be compiled and run in the
 * context of that ShellWrapper.getShell().
 *
 * <h3>Current Script</h3>
 *     The <code>load</code> command can be used to set the current script. Call load providing:
 *     <ul>
 *         <li>a <code>java.io.File</code> instance for the script to load</li>
 *         <li>a <code>string</code> providing the fully qualified file name for the script</li>
 *     </ul>
 *
 *     The <code>load</code> method returns <code>true</code> if the specified file exists,
 *     compiles it and makes it the <code>currentScript</code>.
 *     The <code>currentScript</code> can be run by invoking one of the <code>run</code> commands:
 *     <ul>
 *         <li><code>run()</code> - runs the script on the default thread</li>
 *         <li><code>runEDT()</code> - runs the script on the AWT event despatch thread  and waits for it to complete.</li>
 *         <li><code>runLater()</code> - queues the script on the AWT event despatch thread and returns control immediately to the calling thread</li>
 *      </ul>
 *
 * <h3>The context</h3>
 *     Scripts are run in a context. This context pre-defines variables that may be referenced in the script.
 *     Variables created during script execution will be added to this context within the scope of current run.
 *     <h4>Default context</h4>
 *     The default context is defined in the <code>GroovyShell</code> instance. This can be used to define variables
 *     that need to be accessed in multiple scripts. To set a variable from an extrernal environment use:<br>
 *          <code>setVariable(name, value)</code><br>
 *     To retrieve a variable use:<br>
 *          <code>getVariable(name)</code><br>
 *     All variables are accessible through the <code>getContext</code> method that returns
 *     the context for the shell as a <code>groovy.lang.Binding</code>.
 *     <h4>Script specific context</h4>
 *     A script specific context can be added to the <code>currentScript</code> by using the
 *     <code>setBinding</code> method. This overrides the default context -it does not supplement it.
 *     The <code>getBinding</code> returns the <code>groovy.lang.Binding</code> for the current script.
 *
 *     Note that, within scripts, these variables can be accessed by name so,
 *     after <code>setVariable("x",10)</code> is called
 *     <br>
 *     <code>def y=2*x</code>
 *     <br>
 *     would be valid Groovy code in a script.
 *     
 *     The push() and pull() methods allow the context to be saved and restored from a LIFO stack.
 *     The context is cleared of all variables after a push call.
 *     This can be useful when a script makes use of variable names that are already defined in the
 *     context.
 *
 * <h3>Saving scripts</h3>
 *      The <code>currentScript</code> together with its current binding can be saved by calling:<br>
 *          <code>putScript(name)</code><br>
 *      and later retrieved by calling:<br>
 *          <code>pullScript(name)</code><br>
 *      The context is cloned and saved independently. the <code>pullScript(name)</code>
 *      retores the script's binding to the state it was saved in.
 *
 *  <h3>Running arbitrary scripts</h3>
 *      <code>eval, evalEDT</code> and <code>evalLater</code> also accept arbitrary scripts supplied as Strings.
 *      The content of the string will be compiled and run on the appropriate thread using the default context from
 *      the ShellWrapper.getShell().
 *      The string may also be replaced with a <code>java.io.File</code> or <code>java.io.Reader</code> instance.
 *      A <code>groovy.lang.GroovyCodeSource</code> instance can also be supplied.
 *
 *  <h4>Returned values</h4>
 *       The value returned by a script can be set by explicitly adding a <code>return</code> command as its last line
 *       except in the case of <code>runLater</code> (which returns <code>void</code> as the method returns before
 *       running the code).
 *
 *       All run methods place the last returned result in storage and this may be accessed using the <code>getEval()</code>
 *       method.  The <code>runLater</code> methods set and clear a done flag that can be tested by calling <code>isDone</code>.
 *       Note that this is not locked - with multiple calls to <code>runLater</code> are chained, there is no way to determine
 *       which script has completed and created the result returned by <code>getEval()</code>
 *
 * <h4>Using JavaFX</h4>
 *      Explicit support for JavaFX has not been added as most target environments currently only support Java 6.
 *
 *      To run JavaFX code, you can add appropriate constructs to the script e.g.
 *      <code>Platform.runLater {
 *          ... user code goes here ....
 *       } as Runnable<code>
 *
 *
 * @author Malcolm Lidierth
 */
class GShell  {

    /**
     * SwingBuilder used for the edt code
     */
    private static SwingBuilder swing = new SwingBuilder()
    /**
     * List of stored scripts
     */
    private static LinkedHashMap<String, Script> scripts = new LinkedHashMap<String, Script>()
    /**
     * Contexts for the stored scripts
     */
    private static LinkedHashMap<String, Binding> contexts = new LinkedHashMap<String, Binding>()
    /**
     * If set, contains the script loaded via the <code>load</code> method and parsed
     * for use in the shell
     */
    private static Script currentScript = null
    /**
     * Scripts loaded via the <code>load</code> method will also be stored
     * as a GroovyCodeSource.
     */
    private static GroovyCodeSource codeSource=null
    /**
     * Result of the last evaluation - typically of the last line of a script
     */
    private static Object eval0
    /**
     * Done flag - get and set internally by some methods.
     * isDone can be called to test if a script has completed running.
     */
    private static boolean done = false
    
    private static Pattern pattern = Pattern.compile("((for)|(while)|(if))[\\s\\(].+[^\\{\\}]\n")
    
    private static ConsoleTextEditor editor;
    
    private static FileDialog dialog=null
    
    private static File latestFile=null
    


    private GShell() {
    }


    /**
     * Initiates and runs a Groovy Console instance with a context created by
     * copying the current <code>GShell</code> context.
     *
     * The console uses a <strong>shallow</strong> copy of the GShell binding.
     * Changes made to objects by reference in the console will therefore change
     * the values in the <code>GShell<code> context. This method is aimed
     * primarily at developers.
     *
     * @return reference to the created console
     */
    static Console console(){
        Console console=new Console()
        console.setShell(ShellWrapper.getShell())
        console.run()
        return console
    }

    /**
     * Displays a text editor for creating, loading saving and running .groovy scripts.
     * 
     * Note: to run scripts, the Format option must be used from the menu.
     * A formatted script can be submitted as a single line to the eval
     * methods.
     * 
     */
    static void editor(){
        new SwingBuilder().doLater() { 
            frame(title:"GShell script editor", size:[500,400], show: true) {
                borderLayout()
                label(text:"GShell.eval(\'", constraints: BL.NORTH)
                editor=new ConsoleTextEditor()
                widget(editor, size:[400,400])
                panel(constraints: BL.EAST){
                    gridLayout(cols:1, rows: 14)
                    button(
                        text: "New",
                        toolTipText: "Clear editor and create a new script",
                        actionPerformed: {
                            int ans=JOptionPane.showConfirmDialog(null,
                                 "<html>This will lose any changes you may have made<br>"
                                + "Do you want to carry on?</html>",
                                "New Script",
                                JOptionPane.YES_NO_OPTION);
                            if (ans==JOptionPane.YES_OPTION) {
                                String code="";
                                for (String s : ShellWrapper.getImportList()){
                                    code=code.concat(String.format("import %s%n", s))
                                }
                                editor.getTextEditor().setText(code)
                                latestFile=null
                            }
                        })
                    button(text: "Open",
                        toolTipText: "Clear editor and open a .groovy script file",
                        actionPerformed: {
                            GShell.open()
                        } 
                    )
                    button(text: "Save",
                        toolTipText: "Save the editor contents",
                        actionPerformed: {
                            if (latestFile==null){
                                GShell.saveAs()
                            } else {
                                GShell.save()
                            }
                        }
                    )
                    button(text: "Save As",
                        toolTipText: "Save the editor contents to a file",
                        actionPerformed: {
                            GShell.saveAs()
                        } 
                    )
                    
                    widget(new JSeparator(javax.swing.SwingConstants.HORIZONTAL))
                    button(text: "Copy", 
                        toolTipText: "Copy the editor contents  to the clipboard as a GShell.eval call",
                        actionPerformed: {
                            def txt=editor.getTextEditor().getText()
                            txt=txt.replace("\n",";")
                            txt="GShell.eval(\'" + txt + "\')"
                            def ss = new StringSelection(txt)
                            defaultToolkit.systemClipboard.setContents(ss, null)   
                        })
                    widget(new JSeparator(javax.swing.SwingConstants.HORIZONTAL))
                    button(text: "Format",
                        toolTipText: "Format the editor contents for use with eval",
                        actionPerformed: { 
                            def txt=editor.getTextEditor().getText()
                            txt=GShell.format(txt)
                            editor.getTextEditor().setText(txt)
                        })
                    widget(new JSeparator(javax.swing.SwingConstants.HORIZONTAL))
                    button(text: "Minify",
                        toolTipText: "Minify the editor contents for use with eval",
                        actionPerformed: { 
                            def txt=editor.getTextEditor().getText()
                            txt=GShell.format(txt)
                            txt=GShell.minify(txt)
                            editor.getTextEditor().setText(txt)
                        })
                    widget(new JSeparator(javax.swing.SwingConstants.HORIZONTAL))
                    button(text: "Run",
                        toolTipText: "Runs the contents directly in the shell",
                        actionPerformed: {
                            def txt=editor.getTextEditor().getText()
                            txt=GShell.minify(txt)
                            try {
                                ShellWrapper.getShell().evaluate(txt)
                            } catch (ex) {
                                if (ex.getMessage().contains("unexpected token")){
                                    System.err.println("Unexpected token: did you forget to format the script?")
                                    throw(ex)
                                } else {
                                    throw(ex)
                                }
                            }
                        })
                }
                label(text:"\')", constraints: BL.SOUTH)
            }
        }
    }

    // TODO: Remove comments?
    static minify =  {String txt ->
        txt=txt.replace("\n",";")
    }

    /**
     * Opens a .groovy script file
     */
    static open = {->
        if (dialog == null) {
            dialog = new FileDialog(editor.getTopLevelAncestor(),
                    "Open Script", FileDialog.LOAD)
        }
        dialog.setMode(FileDialog.LOAD)
        dialog.setTitle("Open Script")
        dialog.setVisible(true)
        String p = dialog.getDirectory()?.concat(dialog.getFile())
        if (!p.isEmpty()) {
            File f = new File(p)
            if (f.isFile()) {
                GroovyCodeSource source = new GroovyCodeSource(f)
                editor.getTextEditor().setText(source.getScriptText())
                latestFile = f
            }
        }
    }

    /**
     * Saves the editor text context to <code>latestFile</code>
     */
    static save = { ->
        FileOutputStream stream = new FileOutputStream(latestFile)
        String txt=editor.getTextEditor().getText()
        stream.write(txt.getBytes())
        stream.close()
    }
     
    static saveAs = { ->
        if (dialog == null) {
            dialog = new FileDialog(editor.getTopLevelAncestor(),
                    "Save Script As", FileDialog.SAVE)
        }
        dialog.setMode(FileDialog.SAVE)
        dialog.setTitle("Save Script As")
        dialog.setVisible(true)
        if (dialog.getFile()==null) {
            return
        } else {
            String p = dialog.getDirectory()?.concat(dialog.getFile())
            if (p!=null){
                if (!p.isEmpty()) {
                    if (!p.endsWith(".groovy")){
                        p=p.concat(".groovy");
                    }
                    File f = new File(p)
                    if (!f.isFile()) {
                        f.createNewFile()
                    }
                    latestFile = f
                    GShell.save()
                }
            }
        }
    }

        
    private static format = { String txt ->
        try {
            ShellWrapper.getShell().parse(txt)
            
            // Remove single quotes
            txt = txt.replace("'", "\"")
            // Remove multiple line breaks and leading/trailing white space
            txt = txt.replaceAll("\n+", "\n")
            txt = txt.replaceAll("\n\\s+", "\n")
            txt = txt.replaceAll("\\s+\n", "\n")
            // Space in lists
            txt = txt.replaceAll(",\n", ",")
            // Empty statements
            txt = txt.replaceAll(";;", ";")
            txt = txt.replaceAll("\\{\n", "{")
            txt = txt.replaceAll("\n\\}", "}")
            
            // Breaks in chained calls/properties
            txt = txt.replaceAll("\n\\.", "\\.")
            

            // for loop without braces
            txt = GShell.appendOpenCurly(txt)
            txt = GShell.appendCloseCurly(txt)
            
            txt = txt.replaceAll("\nelse\n", " else ")
            txt = txt.replaceAll("\ncatch", " catch")


        } catch (ex) {
            println "Not formatting because code has errors"
            println ex
        }
        return txt

    }

    private static appendOpenCurly = {String txt ->
        Matcher matcher = pattern.matcher(txt)
        String txt2
        if (matcher.find()) {
            txt2 = txt.substring(0, matcher.end()-1) + "{"
            def idx = matcher.end();
            while (matcher.find(idx)) {
                println matcher.end()
                txt2 = txt2 + txt.substring(idx, matcher.end()-1) + "{"
                idx = matcher.end()
            }
            txt2 = txt2 + txt.substring(idx, txt.length())
            return txt2
        } else {
            return txt;
        }
    }

    private static appendCloseCurly = {String txt ->
        Matcher matcher = pattern.matcher(txt)
        String txt2
        if (matcher.find()) {
            println matcher.end()
            txt2 = txt.substring(0, matcher.end()-1) + "}\n"
            def idx = matcher.end();
            while (matcher.find(idx)) {
                println matcher.end()
                txt2 = txt2 + txt.substring(idx, matcher.end()-1) + "}\n"
                idx = matcher.end()
            }
            txt2 = txt2 + txt.substring(idx, txt.length())
            return txt2
        } else {
            return txt;
        }
    }
    
    /**
     * Returns the SwingBuilder used by this class
     * @return a SwingBuilder instance
     */
    static SwingBuilder getSwing() {
        return swing
    }


    /**
     * Sets a named variable to a value in the shell - creating the variable
     * if required.
     *
     * @param var name of the variable
     * @param o the value to assign
     */
    static void setBinding(Object var, Object o) {
        ShellWrapper.context.setVariable(var as String, o)
    }

    /**
     * Returns the value associated with a named variable in the shell
     * @param var the variable
     * @return its value
     */
    static Object getVariable(Object var) {
        if (ShellWrapper.context.getVariables().keySet().contains(var as String)) {
            return ShellWrapper.context.getVariable(var as String)
        } else {
            System.err.format("The variable '%s' does not exist. Returning null.\n", var as String)
            return null
        }
    }
    
    static void setVariable(Object key, Object value) {
        ShellWrapper.context.getVariables().put(key as String, value)
    }

    /**
     * 
     */
    static void addVariables(Object o) {
        if (o instanceof Object[]) {
            Binding b = ShellWrapper.context
            for (def k = 0; k < o.length; k += 2) {
                b.putAt(o[k] as String, o[k + 1])
            }
            return
        } else if (o instanceof Map){
            ShellWrapper.context.variables.putAll(o)
        }
    }

    /**
     * Clears the shell ShellWrapper.context
     */
    static void clear(){
        ShellWrapper.context.getVariables().clear()
    }

    /**
     * Removes the named entry from the shell context
     *
     * @param var the variable to delete
     */
    static void remove(Object var){
        ShellWrapper.context.getVariables().remove(var as String)
    }
    
    /**
     * Stores a clone of the current context to a on a LIFO stack and clears 
     * the shell context.
     */
    static void push(){
        ShellWrapper.push();
    }
    
    /**
     * Retrieves the last pushed context and sets it as the current shell context.
     */
    static void pull(){
        ShellWrapper.pull();
    }


    /**
     * Adds the class specified by the input string a an import in the
     * shell compiler configuration.
     * This can be used instead of a import statement in a script.
     * 
     * @param s the class to import
     */
    static void addImport(String s){
        ShellWrapper.addImport(s)
    }
    
    /**
     * Returns a list of strings describing the classes that have been
     * imported to the shell compiler configuration
     * 
     * @return ArrayList<String> of classes
     */
    static ArrayList<String> getImportList() {
        return ShellWrapper.getImportList();
    }

    /**
     * Returns the done flag
     * @return true if the script has completed running
     */
    static boolean isDone() {
        return done
    }

    /**
     * Returns the result, if any, returned by the script run by the 
     * the last call to evalLater or runLater
     */
    static Object getEval() {
        return eval0
    }

    /**
     * Loads a script from file, parsing it via the shell and making it the current script.
     * Run the script by calling one of the run() methods with no arguments.
     * @param o 
     * <ol>
     * <li> A java.io.File or java.net.URL instance</li>
     * <li> or a String providing full path info for a .groovy script</li>
     * </ol>
     * @return true if the file exists, false otherwise
     */
    static boolean load(Object o) throws CompilationFailedException, MultipleCompilationErrorsException, IOException{
        File f=null
        switch (o.getClass()) {
        case File:
            f = o
            break
        case URL:
            f=((URL)o).getFile()
        case String:
            f = new File(o)
            break
        default:
            println "Not a valid input argument"
        }
        if (f!=null && f.exists()) {
            codeSource= new GroovyCodeSource(f)
            currentScript = ShellWrapper.getShell().parse(codeSource)
            return true
        } else {
            println "File not found"
            currentScript=null
            return false
        }
    }

    /**
     * Returns the currentScript as a GroovyCodeSource instance.
     *
     * @return the script
     */
    static GroovyCodeSource getSource(){
        return codeSource
    }

    /**
     * Sets the currentSource by parsing the text of a supplied
     * GroovyCodeSource instance.
     * @para, source the GroovyCodeSource
     */
    static void setSource(GroovyCodeSource source){
        codeSource=source
        currentScript=ShellWrapper.getShell().parse(codeSource)
    }

    /**
     * Stores the current script - created using load -
     * for later use using the specified name.
     * The binding for the script will be cloned and saved independently,
     * and restored to the script when it is retrieved with pullScript.
     * If the current script has no binding, that from the shell will
     * be saved instead.
     * @param name name of the script
     */
    static void putScript(Object name) {
        scripts.put(name as String, currentScript)
        // Clone the binding - changes to the reference in currentScript
        // will have no effect on the copy
        if (currentScript.getBinding().getVariables().size() > 0) {
            contexts.put(name as String, new Binding(currentScript.getBinding().getVariables()))
        } else {
            contexts.put(name as String, new Binding(ShellWrapper.getShell().getBinding().getVariables()))
        }
    }

    /**
     * Retrieves a script previously stored using pushScript
     * and makes its the current script.
     * The script's context will be restored to the state when
     * saved using <code>putScript</code>
     * @param name name of the script
     */
    static void pullScript(Object name) {
        Script thisScript = scripts.get(name as String)
        if (thisScript != null) {
            currentScript = scripts.get(name as String)
            // Restore the binding associated with the script when it was saved.
            currentScript.setBinding(contexts.get(name as String))
        } else {
            println "No script with that name found"
        }
    }

    /**
     * Returns a list of named scripts saved using <code>putScript</code>
     */
    static String[] getScriptList() {
        scripts.keySet() as String[]
    }


    /**
     * Associates a binding with the currentScript.
     * @param o the binding
     *  This may be an Object[] with parameter name/value pairs in alternate
     *  elements
     */
    static void setBinding(Object o) {
        if (currentScript!=null){
            if (o instanceof Object[]) {
                Binding b = new Binding()
                for (def k = 0; k < o.length; k += 2) {
                    b.putAt(o[k] as String, o[k + 1])
                }
                o = b
            } else if (o instanceof Map){
                 currentScript.getBinding().getVariables().clear()
                 addToBinding(o);
            } else if (o instanceof Binding){
                currentScript.setBinding(o)
            } else {
                println "setBinding: Unexpected input of class " + o.getClass()
            }
        } else {
            println "No script is current"
        }
    }

    /**
     * Associates variables/values with the currentScript binding.
     * 
     * @param o
     *  This may be an Object[] with parameter name/value pairs in alternate
     *  elements or a Map
     */
    static void addToBinding(Object o) {
        if (currentScript!=null){
            if (o instanceof Object[]) {
                Binding b = currentScript.getBinding()
                for (def k = 0; k < o.length; k += 2) {
                    b.putAt(o[k] as String, o[k + 1])
                }
                return
            } else if (o instanceof Map){
                currentScript.getBinding().variables.putAll(o)
            }
        } else {
            println "No script is current"
        }
    }

    /**
     * Adds the supplied inputs to the binding of the
     * current script.
     *
     * @param s A String - the variable name
     * @param o - value to associate with the variable
     */
    static void addToBinding(String s, Object o) {
        if (currentScript!=null){
            Binding b = currentScript.getBinding().putAt(s,o)
        } else {
            println "No script is current"
        }
    }

    /**
     * Returns the binding associated with the current script.
     */
    static Binding getBinding() {
        if (currentScript!=null){
            return currentScript.getBinding()
        } else {
            println "No script is current"
        }
    }
    
    /**
     * Returns true if the call to the method was made from the EDT; false
     * otherwise.
     */
    static boolean isOnEDT(){
        return EventQueue.isDispatchThread()
    }

    /**
     *  Runs the currentScript
     * @return the value returned by the script - the output from its final line if not specified otherwise
     */
    static Object run() {
        return eval(currentScript)
    }

    /**
     *  Runs the currentScript on the edt using invokeAndWait
     *  @return the value returned by the script = the output from
     *  its final line if not specified otherwise
     */
    static Object runEDT() {
        return evalEDT(currentScript)
    }

    /**
     *  Runs the currentScript on the edt using invokeLater
     *  No return value (void).
     */
    static void runLater() {
        evalLater(currentScript)
    }

    /**
     * Runs the current script on the edt using invokeLater if called
     * from the EDT and invokeAndWait otherwise.
     *
     * @param o the script to run as a String
     */
    static void runQuery() {
        if (EventQueue.isDispatchThread()){
            evalLater(currentScript)
        } else {
            evalEDT(currentScript)
        }
    }
    
    /**
     * Run the current script using the supplied Map to as the shell context.
     * The  shell context will be restored on completion of script execution
     * 
     * @param context a Map describing the context to use for execution
     */
    static Object runWith(Map context) {
        try {
            push()
        } catch (Exception ex) {
            throw(ex)
        }
        try {
            checkMap(context)
            GroovyShell sh=ShellWrapper.getShell().getContext().getVariables().putAll(context)
            run()
        } finally {
            pull()
        }
    }

    /**
     * Runs the current script using the supplied Map to as the shell context.
     * The  shell context will be restored on completion of script execution
     * 
     * @param context a Map describing the context to use for execution
     */
    static Object runEDTWith(Map context) {
        try {
            push()
        } catch (Exception ex) {
            throw(ex)
        }
        try {
            checkMap(context)
            GroovyShell sh=ShellWrapper.getShell().getContext().getVariables().putAll(context)
            runEDT()
        } finally {
            pull()
        }
    }

    /**
     * Runs a user-specified .groovy script.
     * The script may be specified as a String, a File, a Reader or a GroovyCodeSource.
     *
     * If a String, the content will be treated as the script
     *
     * @param o the script to run
     * @return the value returned by the script - the created on its final line if not specified otherwise
     */
    static Object eval(Object o) {
        try {
            switch (o.getClass()) {
            case Script:
                eval0 = o.run()
                break
            case File:
            case Reader:
            case GroovyCodeSource:
                eval0 = ShellWrapper.getShell().evaluate(o)
                break
            case String:
                eval0 = ShellWrapper.getShell().evaluate(o)
                break
            default:
                println "Sorry, that input was not recognised"
            }
            return eval0 as Object
        } catch(ex) {
            processEx(ex)
        }
    }


    /**
     * Runs a user-specified .groovy script using invokeAndWait.
     * The script may be specified as a String, a File, a Reader or a GroovyCodeSource.
     *
     * If a String, the content will be treated as the script
     *
     * @param o the script to run
     * @return the value returned by the script - the created on its final line if not specified otherwise
     */
    static Object evalEDT(Object o) {
        if (swing==null){
            swing = new SwingBuilder()
        }
        try {
            swing.edt {
                switch (o.getClass()) {
                case Script:
                    eval0 = o.run()
                    break
                case File:
                case Reader:
                case GroovyCodeSource:
                    eval0 = ShellWrapper.getShell().evaluate(o)
                    break
                case String:
                    eval0 = ShellWrapper.getShell().evaluate(o)
                    break
                default:
                    println "Sorry, that input was not recognised"
                }
            }
            return eval0
        } catch(ex) {
            processEx(ex)
        }
    }

    /**
     * Runs a user-specified .groovy script on the edt using invokeLater.
     * The script may be specified as a String, a File, a Reader or a GroovyCodeSource.
     *
     * If a String, the content will be treated as the script
     *
     * @param o the script to run
     * @return the value returned by the script - the created on its final line if not specified otherwise
     */
    static void evalLater(Object o) {
        if (swing==null){
            swing = new SwingBuilder()
        }
        swing.doLater {
            done = false
            try {
                switch (o.getClass()) {
                case Script:
                    eval0 = o.run()
                    break
                case File:
                case Reader:
                case GroovyCodeSource:
                    eval0 = ShellWrapper.getShell().evaluate(o)
                    break
                case String:
                    eval0 = ShellWrapper.getShell().evaluate(o)
                    break
                default:
                    println "Sorry, that input was not recognised"
                }
            } catch(ex) {
                processEx(ex)
            }  finally {
                done = true
            }
        }
    }

    /**
     * Runs a user-specified .groovy script on the edt using invokeLater if called
     * from the EDT and invokeAndWait otherwise.
     *
     * @param o the script to run as a String
     */
    static void evalQuery(Object o) {
        if (EventQueue.isDispatchThread()){
            evalLater(o)
        } else {
            evalEDT(o)
        }
    }
    
    /**
     * Evaluates the script using the supplied Map as the shell context.
     * The  shell context will be restored on completion of script execution
     * 
     * @param o the script to run as a String
     * @param context a Map describing the context to use for execution
     */
    static Object evalWith(Object o, Map context) {
        try {
            push()
        } catch (Exception ex) {
            throw(ex)
        }
        try {
            checkMap(context)
            GroovyShell sh=ShellWrapper.getShell().getContext().getVariables().putAll(context)
            eval(o)
        } finally {
            pull()
        }
    }
    
    /**
     * Evaluates the script on the EDT using the supplied Map to as the shell context.
     * The  shell context will be restored on completion of script execution which is
     * run on the EDT using invokeAndWait.
     * 
     * @param o the script to run as a String
     * @param context a Map describing the context to use for execution
     */
    static Object evalEDTWith(Object o, Map context) {
        try {
            push()
        } catch (Exception ex) {
            throw(ex)
        }
        try {
            checkMap(context)
            GroovyShell sh=ShellWrapper.getShell().getContext().getVariables().putAll(context)
            evalEDT(o)
        } finally {
            pull()
        }
    }
    
    /**
     * This converts any non-String entries in the Map to Strings
     * (where possible). It is needed as some systems will use characters
     * instead of strings for single characters.
     */
    private static checkMap(Map context) {
        for (Object o: context.keySet()){
            if (!(o instanceof String)){
                Object o2=context.get(o)
                context.remove(o)
                context.put(o as String, o2)
            }
        }
    }



    /**
     * Sets the look and feel
     * The input is a string e.g. "system" or "nimbus"
     *
     * <strong>This should not be done from inside an existing
     *  Java-based GUI such as the MATLAB desktop</strong> - exceptions
     *  will occur then.
     */
    static void lookAndFeel(String s){
        swing.lookAndFeel(s)
    }
    

    private def static processEx={
        ex ->
        switch (ex.getClass()) {
        case MissingPropertyException:
            missingvar(ex)
            break
        case MultipleCompilationErrorsException:
            multicomp(ex)
            break
        default:
            println "GShell - not sure why this happened. Check your code."
            throw(ex)
        }
    }

    private def static missingvar={
        ex ->
        println("Exception:")
        println "Possible explanations"
        println(String.format("The script refers to a variable \"%s\" that has not been defined", ex.getProperty()))
        println("in either the script, its context or the current shell context (using setVariable).")
    }

    private def static multicomp={
        ex ->
        println("Exception:")
        println(String.format("Multiple compilation errors were encountered", ex.getCause()))
    }


}

