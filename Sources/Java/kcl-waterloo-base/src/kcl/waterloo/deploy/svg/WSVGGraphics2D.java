/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.deploy.svg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;
import kcl.waterloo.common.deploy.AbstractDeployableGraphics2D;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.Element;

/**
 * <p>
 * WSVGGraphics2D - a Graphics2D subclass that generates SVG.</p>
 *
 * <p>
 * WSVGGraphics2D is simply a wrapper class for Apache Batik SVGGraphics2D
 * instances. Apache Batik does all the real work. WSVGGraphics2D justs adds a
 * few groups to the DOM to facilitate interaction with the charts using
 * JavaScript, e.g. via d3d.js - and adds HTML file writing support together
 * with support for translating the SVG to HTML5 canvas commands using
 * canvg.js.</p>
 *
 * <p>
 * For info about canvg.js and known issues see the README.txt file in package
 * {@code kcl.waterloo.deploy.canvg}</p>
 *
 * <p>
 * <strong>Usage:</strong></p>
 *
 * <p>
 * WSVGGraphics2D instances are used in AWT/Swing component paint methods in the
 * same way as any other Graphics2D class object. When painting is complete, a
 * Processing script can be accessed by calling the {@code getCode()} method on
 * the instance which returns the SVG as a String. Alternatively, call one of
 * the {@code write(...)} methods to save the code to file. These methods
 * optionally support the creation of HTML files, together with CSS style
 * sheets, to display the generated script in an HTML5 browser canvas via
 * canvg.js [see https://code.google.com/p/canvg/].</p>
 *
 * <p>
 * <strong>The methods are not thread-safe. All painting should be done on the
 * EDT.</strong><p>
 *
 * <p>
 * To create a PDEGraphics2D instance, pass the constructor an instance of a
 * standard Graphics2D object</p>
 *
 * <p>
 * PDEGraphics2D g= new PDEGraphics2D(Graphics2D g)<br>
 *
 * <p>
 * <strong>Write methods:</strong></p>
 * <p>
 * The {@code write} methods create files following a convention that is
 * intended to mimic those used in PDEGraphics2D.</p>
 *
 * <p>
 * The convention is as follows</p>
 * <p>
 * If you write a file named e.g. "script_name.svg", the file will be created in
 * a folder named "script_name". This folder will be created if it does not
 * exist. Thus a file specified as "/Users/ML/Documents/script_name", would
 * create "/Users/ML/Documents/script_name/script_name.svg" - the .svg extension
 * will be added if not specified.</p>
 *
 * <p>
 * If the the paint methods called with the PDEGraphics2D instance include calls
 * to the {@code drawImage} methods, the required images will be added to a
 * "/script_name/data/" folder as PNG images. Gradient paints, including
 * MultipleGradientPaints, are also supported through drawing images and require
 * a /data folder.<p>
 *
 * <p>
 * The {@code write} methods also support the creation of HTML files. If the
 * {@code htmlFlag} on input is {@code true}, the following files can be
 * created:</p>
 * <p>
 * <strong>script_name.html - </strong> a simple file that shows only the
 * graphics</p>
 * <p>
 * <strong>index.html - </strong> a styled file that includes titles etc</p>
 * <p>
 * <strong>WSVGGraphics.css - </strong> styling sheet for index.html</p>
 * <p>
 * <strong>index.xml - </strong>an xml file describing the contents of the /data
 * folder</p>
 * <p>
 * <strong>htppd.py - </strong> a Python 2.7 script that displays index.html in
 * the system default brpwser using a local server. Use this if the default
 * browser's security settings prevent JavaScript accessing the local file
 * system directly.</p>
 *
 * See the {@code write{)} methods for full details of the options available.
 *
 *
 * <p>
 * WSVGGraphics2D has been tested with the following browsers</p>
 * <p>
 * <center>
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Safari.png"
 * title="Safari">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Chrome.png"
 * title="Google Chrome">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Opera.png"
 * title="Opera">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/OmniWeb.png"
 * title="OmniWeb">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Firefox.png"
 * title="Firefox">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Stainless.png"
 * title="Stainless">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Camino.png"
 * title="Camino">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/SeaMonkey.png"
 * title="SeaMonkey">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Explorer.png"
 * title="IE9+">
 * <img src="http://sigtool.sourceforge.net/images/javadoc/Javafx.png"
 * title="JavaFX WebView">
 * </center></p>
 *
 * <p>
 * <strong>Adding code and comments to the scripts:</strong></p>
 * Several methods allow the generated script to be modified on-the-fly when
 * users have access to the paint methods of the components being painted. These
 * methods are defined in AbstractDeployableGraphics2D, the WSVGGraphics
 * superclass.</p>
 * <ul>
 * <li><strong>public void setPaintObject(Object referenceObject)</strong></li>
 * Adds an SVG group to DOM and adds subsequent elements to that group.
 * <li><strong>public void addComment(String comment)</strong></li>
 * Adds a comment to the script at the current writing location. Format markers
 * will be added automatically.
 * </ul>
 *
 * <p>
 * <strong>Titles etc</strong></p>
 * <ul>
 * <li><strong>public void setTitle(String title)</strong></li>
 * Gives the script a title. This will added as the name of the displayed Window
 * or displayed in HTML files. It will also be used to set the title tag in
 * HTML.
 * <li><strong>public void setDescription(String description)</strong></li>
 * The description will be displayed with index.html
 * <li><strong>public void setKeyWords(ArrayList<String> keyWords)</strong></li>
 * Adds the specified strings as keywords to HTML documents.
 * <li><strong>public void addKeyWord(String word)</strong></li>
 * Adds the keyword to the current list for HTML files.
 * </ul>
 *
 * <p>
 * <strong>WSVGGraphics can only render graphics normally rendered through
 * Java2D graphics calls. When painting using a proprietary, platform-specific,
 * Java look-and-feel, some features may be painted using native graphics calls
 * and will not appear in the generated scripts. For example, JFrames will not
 * be decorated with close/maximize buttons etc.</strong></p>
 *
 * <p>
 * In addition, some components may not render fully perhaps because the
 * componentUIs are cloning the wrapped Graphics2D instance instead of the
 * WSVGGraphics instance or maybe generating a new instance with a call to
 * Component.getGraphics(). This seems to affect mainly the Mac Aqua L&F. Some
 * fixes/workarounds are included for specific L&Fs and AWT/Swing components
 * where the cause of the problem has been identified.</p>
 *
 * @version 1.0.0 @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/pdegraphics2d/discussion/?source=navbar">
 * [Contact]</a>
 *
 */
public class WSVGGraphics2D extends AbstractDeployableGraphics2D {

    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
    private Element lastAddedElement;
    private String title = "Title";
    private String description = "Description:";

    public WSVGGraphics2D(SVGGraphics2D g) {
        super(g);
    }

    @Override
    public void append(Component c, int elapsedTime) {
    }

    @Override
    public void append(Component c) {
    }

    @Override
    public void setPaintObject(Object referenceObject) {
        Element element = ((SVGGraphics2D) parentGraphics).getDOMFactory().createElementNS(svgNS, "g");
        element.setAttribute("id", String.format("id%x_%x", (int) (System.currentTimeMillis() - startTime), referenceObject.hashCode()));
        element.setAttribute("class", String.format("%s", referenceObject.getClass().toString()));
        Element desc = ((SVGGraphics2D) parentGraphics).getDOMFactory().createElementNS(svgNS, "desc");
        desc.setTextContent("wgraphic");
        element.appendChild(desc);
        if (lastAddedElement == null) {
            ((SVGGraphics2D) parentGraphics).getDOMTreeManager().appendGroup(element, null);
            lastAddedElement = element;
        } else {
            lastAddedElement.appendChild(element);
            ((SVGGraphics2D) parentGraphics).getDOMTreeManager().appendGroup(element, null);
            lastAddedElement = element;
        }
    }

    @Override
    public void addComment(String comment) {
        ((SVGGraphics2D) parentGraphics).getDOMFactory().createComment(comment);
    }

    @Override
    public void prependCode(String code) {
    }

    @Override
    public void addCode(String code) {
    }

    @Override
    public void appendCode(String code) {
    }

    @Override
    public boolean copyImage(Image img, int dx, int dy, int sx, int sy, int width, int height, Color bgcolor, ImageObserver observer) {
        return true;
    }

    @Override
    public Object clone() {
        WSVGGraphics2D g = new WSVGGraphics2D((SVGGraphics2D) parentGraphics.create());
        g.startTime = this.startTime;
        g.lastAddedElement = this.lastAddedElement;
        return g;
    }

    @Override
    public Graphics create() {
        return (Graphics) clone();
    }

    @Override
    public Graphics create(int x, int y, int width, int height) {
        WSVGGraphics2D g = new WSVGGraphics2D((SVGGraphics2D) parentGraphics.create(x, y, width, height));
        g.startTime = this.startTime;
        g.lastAddedElement = this.lastAddedElement;
        return g;
    }

    public static void deploy(File f, String svgCode, Dimension dim, boolean inline, String jsLoc, String cssLoc, boolean httpdFlag) throws IOException {
        deploy(f, svgCode, dim, inline, jsLoc, cssLoc, httpdFlag, null, "Title", "Description");
    }

    /**
     *
     * @param f SVG File to write to.
     * @param svgCode SVG code as String
     * @param dim the Dimensions of the component being painted
     * @param inline if true, SVG code will be inlined in the HTML file
     * @param jsLoc Location of the canvg.js file
     * @param cssLoc Location of the CSS file
     * @param httpdFlag If true, generated a Python script
     * @throws IOException
     */
    public static void deploy(File f, String svgCode, Dimension dim, boolean inline, String jsLoc, String cssLoc, boolean httpdFlag, ArrayList<String> keyWords, String title, String description) throws IOException {

        if (!f.getName().endsWith(".svg") && !f.getName().endsWith(".svg.gz")) {
            throw new IOException("Not an svg file");
        }

        // Create a folder to parent the generated files
        File folder;
        folder = new File(f.getPath().replace(".svg", ""));
        if (!folder.isDirectory()) {
            folder.mkdir();
        }

        // If required, create the JavaScript files
        if (jsLoc.equals("canvg-min.js") || jsLoc.equals("./canvg-min.js")) {
            InputStream js = AbstractDeployableGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/common/deploy/canvg/canvg-min.js");
            FileOutputStream output = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("canvg-min.js"));
            while (js.available() > 0) {
                byte[] buffer = new byte[js.available()];
                int n = js.read(buffer, 0, js.available());
                output.write(buffer, 0, n);
            }
            output.close();
            js.close();

            js = AbstractDeployableGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/common/deploy/canvg/rgbcolor-min.js");
            output = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("rgbcolor-min.js"));
            while (js.available() > 0) {
                byte[] buffer = new byte[js.available()];
                int n = js.read(buffer, 0, js.available());
                output.write(buffer, 0, n);
            }
            output.close();
            js.close();

            InputStream lic = AbstractDeployableGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/common/deploy/canvg/MIT-LICENSE.txt");
            FileOutputStream output2 = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("CANVG-LICENSE.TXT"));
            while (lic.available() > 0) {
                byte[] buffer = new byte[lic.available()];
                int n = lic.read(buffer, 0, lic.available());
                output2.write(buffer, 0, n);
            }
            output2.close();
            lic.close();
        } else {
        }

        Formatter cssFormatter = new Formatter(new StringBuilder(512));
        if (cssLoc.equals("WSVGGraphics2D.css") || cssLoc.equals("./WSVGGraphics2D.css")) {
            InputStream css = WSVGGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/svg/WSVGGraphics2D.css");
            FileOutputStream cssFile = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("WSVGGraphics2D.css"));
            OutputStreamWriter cssStream = new OutputStreamWriter(cssFile, "UTF-8");
            while (css.available() > 0) {
                byte[] buffer = new byte[css.available()];
                int n = css.read(buffer, 0, css.available());
                String s = new String(buffer);
                s = s.replaceAll("1111111111", Integer.valueOf(dim.width).toString());
                s = s.replaceAll("2222222222", Integer.valueOf(dim.height).toString());
                cssFile.write(s.getBytes("UTF-8"));
            }
            css.close();
            cssFile.close();
            cssStream.close();
        } else if (cssLoc.equals("index.html") || cssLoc.equals("./index.html")) {
            InputStream css = WSVGGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/svg/WSVGGraphics2D.css");
            while (css.available() > 0) {
                byte[] buffer = new byte[css.available()];
                int n = css.read(buffer, 0, css.available());
                cssFormatter.format("%s", new String(buffer, 0, n));
            }
        }

        if (httpdFlag) {
            InputStream py = WSVGGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/svg/httpd.py");
            FileOutputStream output = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("httpd.py"));
            while (py.available() > 0) {
                byte[] buffer = new byte[py.available()];
                int n = py.read(buffer, 0, py.available());
                output.write(buffer, 0, n);
            }
            output.close();
        }

        Formatter keyWordString = new Formatter(new StringBuilder(512));
        keyWordString.format("<meta name=\"keywords\" content=\"Waterloo, WSVGGraphics2D, ");
        if (keyWords != null) {
            for (Iterator<String> it = keyWords.iterator(); it.hasNext();) {
                String word = it.next();
                if (it.hasNext()) {
                    keyWordString.format("%s, ", word);
                } else {
                    keyWordString.format("%s", word);
                }
            }
        }
        keyWordString.format("\">%n");

        InputStream index;
        /*
         User-specified HTML template to use instead of the one embedded in the
         jar file.
         */
        String userHTML = "";
        if (!userHTML.isEmpty() && new File(userHTML).isFile()) {
            index = new FileInputStream(new File(userHTML));
        } else {
            index = WSVGGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/svg/index.html");
        }
        FileOutputStream indexFile = new FileOutputStream(
                folder.getPath()
                .concat(File.separator)
                .concat("index.html"));
        OutputStreamWriter indexStream = new OutputStreamWriter(indexFile, "UTF-8");
        while (index.available() > 0) {
            byte[] buffer = new byte[index.available()];
            int n = index.read(buffer, 0, index.available());
            String s = new String(buffer);
            if (s.contains("<!-- CODE -->")) {
                if (inline) {
                    // Inline the svg code
                    int i = svgCode.indexOf("<svg xmlns:xlink=");
                    String code = svgCode.substring(i);
                    s = s.replace("<!-- CODE -->", String.format("<div id=\"%s\">%n%s</div>",
                            f.getName().replace(".svg", ""),
                            code));
                } else if (jsLoc.isEmpty()) {
                    // Not using canvg so embed as object
                    s = s.replace("<!-- CODE -->",
                            String.format("<object id=\"%s\" data=\"%s\" type=\"image/svg+xml\"></object>",
                                    f.getName().replace(".svg", ""),
                                    f.getName()));
                } else {
                    s = s.replace("<!-- CODE -->", String.format("<canvas id=\"%s\"></canvas>", f.getName().replace(".svg", "")));
                }
            }

            if (s.contains("<!-- KEYWORDS -->")) {
                s = s.replace("<!-- KEYWORDS -->", keyWordString.toString());
            }
            if (s.contains("cccccccccc")) {
                s = s.replaceAll("cccccccccc", "./" + f.getName());
            }
            if (s.contains("ididididid")) {
                s = s.replaceAll("ididididid", f.getName().replace(".svg", ""));
            }
            if (s.contains("<!-- TITLE -->")) {
                s = s.replace("<!-- TITLE -->", title);
            }
            if (s.contains("<!-- DESCRIPTION -->")) {
                s = s.replace("<!-- DESCRIPTION -->", description);
            }
            if (s.contains("tttttttttt")) {
                s = s.replaceAll("tttttttttt", String.format("%s", title));
            }
            if (s.contains("dddddddddd")) {
                s = s.replaceAll("dddddddddd", String.format("%s", description));
            }
            if (s.contains("canvgcanvg")) {
                s = s.replaceAll("canvgcanvg", jsLoc);
            }
            if (s.contains("rgbrgbrgb")) {
                s = s.replaceAll("rgbrgbrgb", jsLoc.replace("canvg-min.js", "rgbcolor-min.js"));
            }
            if (s.contains("1111111111")) {
                s = s.replaceAll("1111111111", Integer.valueOf(dim.width).toString());
            }
            if (s.contains("2222222222")) {
                s = s.replaceAll("2222222222", Integer.valueOf(dim.height).toString());
            }
            if (cssLoc.equals("index.html") || cssLoc.equals("./index.html")) {
                //in-line css
                String cssText = cssFormatter.toString();
                cssText = cssText.replaceAll("1111111111", Integer.valueOf(dim.width).toString());
                cssText = cssText.replaceAll("2222222222", Integer.valueOf(dim.height).toString());
                s = s.replace("<!-- optional styling -->", String.format("<style type=\"text/css\">%n%s%n</style>", cssText));
            } else {
                // add css link
                s = s.replace("<!-- optional styling -->", String.format("<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\">", cssLoc));
            }
            indexFile.write(s.getBytes("UTF-8"));
        }
        index.close();
        indexFile.close();
        indexStream.close();

        if (!inline) {
            File f2 = new File(folder.getPath().concat(File.separator).concat(f.getName()));
            if (f2.getName().endsWith(".svg")) {
                OutputStreamWriter outSVG = new OutputStreamWriter(new FileOutputStream(f2), "UTF-8");
                outSVG.write(svgCode);
                outSVG.close();
            } else if (f2.getName().endsWith(".svg.gz")) {
                GZIPOutputStream outSVG = new GZIPOutputStream(new BufferedOutputStream(new java.io.FileOutputStream(f2)));
                outSVG.write(svgCode.getBytes());
                outSVG.close();
            }
        }
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
