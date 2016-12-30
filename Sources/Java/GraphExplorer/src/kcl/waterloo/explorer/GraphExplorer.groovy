/*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package kcl.waterloo.explorer

import kcl.waterloo.actions.ActionServices
import kcl.waterloo.actions.WorkerListener
import kcl.waterloo.graphics.GJAbstractGraphContainer
import kcl.waterloo.graphics.GJGraph
import kcl.waterloo.graphics.GJGraphContainer
import kcl.waterloo.graphics.GJGraphInterface
import kcl.waterloo.graphics.plots2D.GJPlotInterface
import kcl.waterloo.gui.images.Images
import kcl.waterloo.swing.GCGridContainer
import kcl.waterloo.swing.explorer.ConsoleHost
import kcl.waterloo.xml.FileWrapper



import py4j.GatewayServer
import py4j.Py4JNetworkException

import javax.swing.*
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener
import java.awt.*

/**
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GraphExplorer extends ConsoleHost {
    

    /**
     */
    public GraphExplorer() {
        this(new groovy.ui.Console())
        FrameDelegate.metaChange(console)
    }
    
    /**
     * Constructor used for integration in a Scalea Console using
     * Micheal Rans' GraphExplorerShim 
     * 
     */
    public GraphExplorer(groovy.ui.Console c) {
        super(c)
    }
    
    public static GraphExplorer createInstance() {
        return createInstance(new groovy.ui.Console())
    }
    
    /**
     * Static factory method to create a {@code GraphExplorer} instance
     *
     * @return a {@code GraphExplorer}
     */
    public static GraphExplorer createInstance(groovy.ui.Console c) {
            final GraphExplorer host = new GraphExplorer(c)
            EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        host.init()
                    }       
                })
            return host
    }        

    /**
     * Method to inititialize and display a {@code GraphExplorer} constructed
     * via the null constructor.
     */
    public void run() {
        final GraphExplorer host = this;
        EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    host.init();
                }
            });
    }

    private void init() {
        def osname=System.getProperty('os.name')
        if (osname.contains('Mac OS X')) {
            System.setProperty('os.name','')
        }
        getConsole().run();
        if (osname.contains('Mac OS X')) {
            System.setProperty('os.name',osname)
        }
        topPanel = translateConsole(getConsole());
        addNewMenus();
        showAbout(console);
        console.setVariable("Host", this);
        console.setVariable("Console", console);
        console.setVariable("TabPane", this.getTopPanel().getTabPane());
        addGPL()
        
        GatewayServer gateway = new GatewayServer(console);
        try {
            gateway.start();
            println "Py4j Gateway Server Started on Port: " + gateway.getListeningPort()
        } catch (Py4JNetworkException ex) {
            println ex.getMessage()
        }
    }

    @Override
    public void addTab(Object newObj) {
        super.addTab(newObj, "Graph ");
    }

    @Override
    protected void addNewMenus() {

        def toolBar = GraphToolBar.getToolBar(this);
        topPanel.getCenterPanel().add(toolBar, BorderLayout.NORTH);

        JMenuBar menuBar = getFrame().getJMenuBar();
                
        if (menuBar!=null) {
            
            Component[] menus;
            Component[] fileItems;
            
            JMenu fileMenu = menuBar.getMenu(0);
            menus = menuBar.getComponents();
            fileItems = fileMenu.getMenuComponents();
        

            JMenuBar newMenu = MenuBuilder.createMenu(this);
            //fileMenu.add(new JSeparator());

            JMenu scriptMenu = new JMenu("Groovy Scripting");
        
            if (fileItems!=null) {
                for (int k = 0; k < fileItems.length; k++) {
                    scriptMenu.add(fileItems[k]);
                }
            }
        
            if (menus!=null) {
                for (int k = 1; k < menus.length; k++) {
                    scriptMenu.add(menus[k]);
                }
            }
        
            newMenu.add(scriptMenu);
            newMenu.add(MenuBuilder.createHelpMenu(this))
        
            getFrame().setJMenuBar(newMenu)
        }

    }



    public static String Version() {
        return "Graph Explorer Version 0.8";
    }

    def openGraph = {
        console.showMessage("Opening graphics file....")
        
        FileWrapper fileWrapper = ActionServices.open(getFrame());
        
        if (fileWrapper.hasContents()) {
            if (fileWrapper.getContents() instanceof ArrayList) {
                for (Object o : ((ArrayList<?>) fileWrapper.getContents())) {
                    if (o instanceof GJAbstractGraphContainer) {
                        delegate.addTab(o);
                    }
                }
            } else {
                Object o = fileWrapper.getContents();
                if (o instanceof GJAbstractGraphContainer) {
                    delegate.addTab(o);
                } else if (o instanceof GJGraphInterface) {
                    GJGraphInterface gr = (GJGraphInterface) o;
                    if (gr.getGraphContainer() != null) {
                        delegate.addTab(gr.getGraphContainer());
                    } else {
                        GJGraphContainer grc = GJGraphContainer.createInstance((GJGraphInterface) o);
                        delegate.addTab(grc);
                    }
                } else if (o instanceof GJPlotInterface) {
                    GJGraphContainer c = GJGraphContainer.createInstance(GJGraph.createInstance());
                    c.getView().add((GJPlotInterface) o);
                    delegate.addTab(c);
                    c.getView().autoScale();
                } else if (o instanceof GCGridContainer) {
                    delegate.addTab(o);
                }
            }
            if (fileWrapper.hasSupplements()){
                LoadSupplements.load(fileWrapper, delegate);
            }
            console.showMessage("Done.")
        }
    }

    def saveGraph = {
        if (getTopPanel().getTabPane().getTabCount()>0)
        ActionServices.save(getFrame(), getTopPanel().getTabPane().getSelectedComponent())
    }
    
    def saveGraphAs = {
        if (getTopPanel().getTabPane().getTabCount()>0)
        ActionServices.saveAs(getFrame(), getTopPanel().getTabPane().getSelectedComponent())
    }
    
    def deployGraph = {
        if (getTopPanel().getTabPane().getTabCount()>0)
        ActionServices.deployAs(getFrame(), getTopPanel().getTabPane().getSelectedComponent())
    }
    
    def closeGraph = {
        int idx = getTopPanel().getTabPane().getSelectedIndex()
        if (idx>=0){
            getTopPanel().getTabPane().removeTabAt(idx)
        }
    }
    
    def printGraph = {
        if (getTopPanel().getTabPane().getTabCount()>0){
            console.showMessage("Printing....")
            getTopPanel().getTabPane().getSelectedComponent().print()
            console.showMessage("Done.")
        }
    }
    
    def copyGraph = {
        if  (getTopPanel().getTabPane().getTabCount()>0){
            console.showMessage("Copying graphics to clipboard in background....")
            SwingWorker<Boolean, Void> worker=ActionServices.copyGraphics(getTopPanel().getTabPane().getSelectedComponent())
            worker.addPropertyChangeListener(new WorkerListener(console, "Graphics copied"))
        }
    }
    

    def copyGraphAsImage = {
        if  (getTopPanel().getTabPane().getTabCount()>0){
            console.getSwing().edt{console.showMessage("Copying graphics to clipboard....")}
            ActionServices.copyAsImage(getTopPanel().getTabPane().getSelectedComponent())
            console.showMessage("Graphics copied.")
        }
    }
    
    
    public void loadScriptFile(String s){
        def file = new File(s)
        console.loadScriptFile(file)
    }


    public static showAbout = {groovy.ui.Console console ->
        def version = GroovySystem.getVersion()
        def pane = console.getSwing().optionPane()

        StringBuffer style = new StringBuffer();

        JEditorPane text = new JEditorPane("text/html", '<html><center>'
            + '<br>Waterloo Graph Explorer using the Groovy Console for evaluating Groovy scripts.'
            + '<br>[Groovy Version ' + version + ']. See <a href=\"http://groovy.codehaus.org\">http://groovy.codehaus.org</a>'
            + '<br>[Java Version ' + System.getProperty("java.version") + '].'
            + '<p>Project Waterloo, Waterloo Scientific Graphics and the Waterloo Graph Explorer<br>'
            + 'are copyright \u00A9 King\'s College London 2011-</p>'
            + '<p>The Waterloo Scientific Graphics core libraries provide publication-quality<br>'
            + 'scientific graphics for the Java Virtual Machine and are distributed under<br>'
            + 'Version 3 of the <a href=\"http://www.gnu.org/licenses/lgpl.html\">GNU Lesser General Public License.</a></p>'
            + '<p>The "GPL Supplement", if installed, includes supplementary libraries and third-party code '
            + 'and is distributed under Version 3 of the <a href=\"http://www.gnu.org/copyleft/gpl.html\">GNU General Public License.</a></p>'
            + '<p>For details of how to use these libraries in Java\u2122, Groovy, MATLAB\u00AE, R, Scala, SciLab etc. see the project website: <a href=\"http://sigtool.sourceforge.net/\">http://sigtool.sourceforge.net/</a>.</p>'
            + '<p>Author: Malcolm Lidierth.</p>'
            + '<p>SourceForge <a href=\"http://sourceforge.net/p/waterloo/discussion\">Discussion</a> Page </p>'
            + '<p>Bug Reports <a href=\"http://sourceforge.net/p/waterloo/bugs\">Page</a></p>'
            + '<p>' + kcl.waterloo.util.Version.getVersion().toString() + ' </p>'
            + '<p>' + GraphExplorer.Version() + ' </p>'
            + ' </center></body></html>')

        setupText.call(text)
        text.setBackground(pane.getBackground());
        pane.setMessage(new JScrollPane(text))
        pane.setIcon(Images.getIcon("KingsLogo.png"))
        def dialog = pane.createDialog(console.getFrame(), 'About Waterloo Graph Explorer')
        dialog.setLocationByPlatform(true)
        dialog.show()
    }
    
    public static showAcknowlegements = {groovy.ui.Console console ->
        def version = GroovySystem.getVersion()
        def pane = console.getSwing().optionPane()

        StringBuffer style = new StringBuffer();

        JEditorPane text = new JEditorPane("text/html", '<html><center>'
            + '<p>Waterloo Graphics were developed from the JXGraph originally written by Romain Guy for '
            + 'the book Filthy Rich Clients by Chet Haase and Romain Guy, Addison Wesley 2008.'
            + '<br>See <a href=\"http://www.pearsoned.co.uk/bookshop/detail.asp?WT.oss=filthy+rich+clients&WT.oss_r=1&item=100000000248311"> here</a> for details.</p>'
            + '<p>JXGraph is now part of the SwingLabs <a href=\"http://java.net/projects/swingx\">SwingX</a> Project which is included with Project Waterloo</p>'
            + '<p>Waterloo also contains code developed by third parties:<br></p>'
            + '<p>The <a href=\"http://www.apache.org/\">Apache Software Foundation</a> Batik FOP and XML Graphics Projects.<br></p>'
            + '<p>GraphExplorer extends several classes provided as part of the <a href=\"http://groovy.codehaus.org/\">Groovy</a> programming language'
            + '<br> and uses the Groovy Console.</p>'
            + '<p>The GPL supplement (if installed) includes <a href=\"http://forge.scilab.org/index.php/p/jlatexmath/\">JLatexMath</a> which part of the <a href=\"http://www.scilab.org/\">Scilab Project</a>.</p>'
            + '<p>Icons used here are from (or derived from) the <a href=\"http://www.famfamfam.com/\">famfamfam silk</a> by Mark James'
            + '<br>and are distributed under the Creative Commons Attribution 2.5 License.</p>'
            + ' </center></body></html>')

        setupText.call(text)
        text.setBackground(pane.getBackground());
        pane.setMessage(new JScrollPane(text))
        pane.setIcon(Images.getIcon("KingsLogo.png"))
        def dialog = pane.createDialog(console.getFrame(), 'Acknowlegements')
        dialog.setLocationByPlatform(true)
        dialog.show()
    }

    private static setupText = {text ->
        if (Desktop.isDesktopSupported()) {
            text.addHyperlinkListener({ HyperlinkEvent e ->
                    if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                        def deskTop = Desktop.getDesktop()
                        if (deskTop.isSupported(Desktop.Action.BROWSE)) {
                            deskTop.browse(e.URL.toURI())
                        }
                    }
                } as HyperlinkListener);
        }
        text.setEditable(false);
        text.setPreferredSize(new Dimension(550, 450))
    }

    /**
     * Checks if the Waterloo General Public License supplements are already loaded.
     * If they are not, and they are available, they will then be loaded.
     * These supplements are in the kcl-gpl.jar file which needs to be located in a
     * "GPLSupplement/dist" folder where GPLSupplement is a sibling of GraphExplorer in the
     * folder hierarchy.
     */
    public static addGPL = {->
        print "Looking for GPL supplementary code...."
        URL url = GraphExplorer.class.getResource("/kcl/waterloo/explorer/GraphExplorer.class")    
        if (GraphExplorer.class.getResource("/kcl/gpl/tex/TeX.class") == null) {
            String s = url.getFile().toString()
            s=s.replaceFirst("file:","")
            int pos = s.indexOf("/GraphExplorer")
            if (pos > 0) {
                String gpl = s.substring(0, pos)
                gpl = gpl.concat("/GPLSupplement/kcl-gpl/dist/kcl-gpl.jar")
                File file = new File(gpl)
                if (file.exists()) {
                    url = file.toURL()
                    def ldr = new GroovyClassLoader()
                    ldr.addURL(url)
                    println "located and added to the class path"
                } else {
                    println("code not located or loaded from" + file.toString() +"\n")
                    println("Files that require this  code will fail to load. " + file.isFile() + " " + file.canRead())
                }
            } else {
                println "...GPL loading failed."
                println("Files that require this  code will fail to load.")
            }
        } else {
            println "already loaded"
        }
        

    }
    
    public static addJavaFX = {->
        String s=System.getProperty("javafx.runtime.version")
        if (s!=null){
            return
        }
        println "Looking for JavaFX ...."
        s=System.getProperty("javafx.location")
        if (s!=null){
            s = s.concat("/jfxrt.jar")
            File file = new File(s)
            def url = file.toURL()
            try {
                def ldr = new GroovyClassLoader()
                ldr.addURL(url)
                println "located and added to the class path"
            } catch (Exception ex) {
                println "code not located or loaded:\n"
                println("Files that require this  code will fail to load.")
            }
        } else {
            println "No java.location returned by System.getProperty"
        }
    }


    
}


