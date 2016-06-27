/*
 * This code is part of Project Waterloo from King's College London
 * <http://sigtool.sourceforge.net/>
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
package kcl.waterloo.deploy.pde;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RasterFormatException;
import java.awt.image.RenderedImage;
import java.awt.image.VolatileImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import kcl.waterloo.deploy.AbstractGraphics2D;

/**
 * <p>PDEGraphics2D - a Graphics2D subclass that generates a script using the
 * Processing graphics language.</p>
 *
 * <p>The generated script may be displayed using the open-source,
 * multi-platform <a href="http://www.processing.org" target="_blank">Processing
 * application</a>
 * and displayed in web pages using the <a href="http://processingjs.org"
 * target="_blank">processing.js javascript</a> when graphics will be rendered
 * to an HTML5 canvas element.</p>
 *
 * <p><strong>Usage:</strong></p>
 *
 * <p>PDEGraphics2D instances are used in AWT/Swing component paint methods in
 * the same way as any other Graphics2D class object. When painting is complete,
 * a Processing script can be accessed by calling the {@code getCode()} method
 * on the instance which returns the script as a String. Alternatively, call one
 * of the {@code write(...)} methods to save the code to file. These methods
 * optionally support the creation of HTML files, together with CSS style
 * sheets, to display the generated script in an HTML5 browser canvas.</p>
 *
 * <p>To create a PDEGraphics2D instance, pass the constructor an instance of a
 * standard Graphics2D object, together with the size of the canvas to
 * paint:</p>
 *
 * <p>PDEGraphics2D g= new PDEGraphics2D(Graphics2D g, int w, int h)<br>
 * or<br>
 * PDEGraphics2D g= new PDEGraphics2D(Graphics2D g, Dimension d)<br></p>
 *
 * <p>Alternatively, pass the component to paint as the input to one of the
 * static paint methods:</p>
 *
 * <p>PDEGraphics2D g=PDEGraphics2D.paint(Component c);<br>
 * PDEGraphics2D g=PDEGraphics2D.paint(JComponent c);<br>
 * PDEGraphics2D g=PDEGraphics2D.paint(Window c);<br>
 * PDEGraphics2D g=PDEGraphics2D.paint(JApplet c);<br></p>
 *
 * <p><strong>Creating animations:</strong></p>
 * <p>The PDEGraphics2D {@code append()} methods can be used to append paints to
 * PDEGraphics2D instance to create animations using Processing. The
 * {@code append()} method should be called after the first use of the instance
 * to paint a component.</p>
 *
 * <p><strong>Write methods:</strong></p>
 * <p>The {@code write} methods create files following a convention that is
 * intended to facilitate the use of the generated scripts with both the <a
 * href="http://www.processing.org" target="_blank">Processing application</a>
 * and <a href="http://processingjs.org" target="_blank">processing.js
 * javascript</a></p>
 *
 * <p>The convention is as follows</p>
 * <p>If you write a file named e.g. "script_name.pde", the file will be created
 * in a folder named "script_name". This folder will be created if it does not
 * exist. Thus a file specified as "/Users/ML/Documents/script_name", would
 * create "/Users/ML/Documents/script_name/script_name.pde" - the .pde extension
 * will be added if not specified.</p>
 *
 * <p>If the the paint methods called with the PDEGraphics2D instance include
 * calls to the {@code drawImage} methods, the required images will be added to
 * a "/script_name/data/" folder as PNG images. Gradient paints, including
 * MultipleGradientPaints, are also supported through drawing images and require
 * a /data folder.<p>
 *
 * <p>The {@code write} methods also support the creation of HTML files. If the
 * {@code htmlFlag} on input is {@code true}, the following files will be
 * created:</p>
 * <p><strong>script_name.html - </strong> a simple file that shows only the
 * graphics</p>
 * <p><strong>index.html - </strong> a styled file that includes titles etc</p>
 * <p><strong>PDEGraphics2D.css - </strong> styling sheet for index.html</p>
 * <p><strong>index.xml - </strong>an xml file describing the contents of the
 * /data folder</p>
 * <p><strong>htppd.py - </strong> a Python 2.7 script that displays index.html
 * in the system default brpwser using a local server. Use this if the default
 * browser's security settings prevent JavaScript accessing the local file
 * system directly.</p>
 *
 * <p>A final, optional argument to the {@code write} methods specified the
 * location of the processing.js JavaScript file. This should be a String,
 * specifying the location relative to the HTML files, or a URL, e.g.</p>
 *
 * <p>"http://cloud.github.com/downloads/processing-js/processing-js/processing-1.4.1.min.js"</p>
 *
 * <p>If the String is "processing.js", indicating that the file is in the same
 * folder as the HTML files, a copy of processing.js will be written there.</p>
 *
 * <p>PDEGraphics2D has been tested with the following browsers</p>
 * <p><center>
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
 * </center></p>
 *
 * <p><strong>Adding code and comments to the scripts:</strong></p>
 * Several methods allow the generated script to be modified on-the-fly when
 * users have access to the paint methods of the components being painted. These
 * methods are defined in AbstractGraphics2D, the PDEGraphics superclass.</p>
 * <ul>
 * <li><strong>public void setPaintObject(Object referenceObject)</strong></li>
 * Places a comment in file. Custom-subclasses could override this to do
 * something more complex.
 * <li><strong>public void addComment(String comment)</strong></li>
 * Adds a comment to the script at the current writing location. Format markers
 * will be added automatically.
 * <li><strong>public void addCode(String comment)</strong></li>
 * Inserts the specified code at the current write location
 * <li><strong>public void prependCode(String code)</strong></li>
 * Prepends the specified code to the Processing script before
 * {@code void setup() {...}}
 * <li><strong>public void appendCode(String code)</strong></li>
 * Adds the code to the end of the generated script.
 * </ul>
 *
 * <p><strong>Titles etc</strong></p>
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
 * <p><strong>PDEGraphics can only render graphics normally rendered through
 * Java2D graphics calls. When painting using a proprietary, platform-specific,
 * Java look-and-feel, some features may be painted using native graphics calls
 * and will not appear in the generated scripts. For example, JFrames will not
 * be decorated with close/maximize buttons etc.</strong></p>
 *
 * <p>In addition, some components may not render fully perhaps because the
 * componentUIs are cloning the wrapped Graphics2D instance instead of the
 * PDEGraphics2D instance or maybe generating a new instance with a call to
 * Component.getGraphics(). This seems to affect mainly the Mac Aqua L&F. Some
 * fixes/workarounds are included for specific L&Fs and AWT/Swing components
 * where the cause of the problem has been identified.</p>
 *
 * @version 1.0.0
 * @author Malcolm Lidierth
 */
public class PDEGraphics2D extends AbstractGraphics2D {

    /**
     * When true, text will be rendered as shapes rather than by using
     * Processing's text mechanisms.
     *
     * Note that:
     * <ul>
     * <li>setting this true hugely increases file sizes<li>
     * <li>text will be drawn in outline - not filled - as filling is:<li>
     * <ul>
     * <li>complex</li>
     * <li>requires features not presently implemented in Processing.js</li>
     * </ul>
     * </ul>
     */
    private boolean textAsShapes = false;
    /**
     * Stroke used when rendering text as shapes.
     *
     * This static variable applies across instances of the PDEGraphics2D class
     * in a JVM session.
     */
    private static final BasicStroke glyphStroke = new BasicStroke(0.5f);
    /**
     * The current stroke weight
     */
    private float strokeLineWidth = -1f;
    /**
     * The current stroke end cap
     */
    private int strokeEndCap = -1;
    /**
     * The current stroke join mode
     */
    private int strokeJoin = -1;
    /**
     * List of custom shapes
     */
    private ArrayList<String> customShapes = new ArrayList<String>();
    private LinkedHashMap<String, Integer> code = new LinkedHashMap<String, Integer>();
    /**
     * Dimensions of the drawn component
     */
    private Dimension dimension;
    /**
     * Map of images to be drawn
     */
    private LinkedHashMap<Integer, ImageIcon> images = new LinkedHashMap<Integer, ImageIcon>();
    private LinkedHashMap<Integer, Rectangle2D> imageDim = new LinkedHashMap<Integer, Rectangle2D>();
    private Font baseFont = null;
    private LinkedHashMap<String, Font> fontList = new LinkedHashMap<String, Font>();
    String prependedCode = "";
    String appendedCode = "";
    /**
     * For HTML etc
     */
    private ArrayList<String> keyWords = new ArrayList<String>();
    private String title = "Title";
    private String description = "Description:";
    /**
     * L&F specific workarounds/fixes
     */
    /**
     * Constants
     */
    /**
     * MacOS Aqua L&F
     */
    public final static int aquaJButton = 1;
    public final static int aquaFixes = aquaJButton;
    /**
     * L&F workarounds for this instance.
     */
    private int lfFixes = UIManager.getLookAndFeel().getName().contains("Mac") ? aquaFixes : 0;

    /**
     * Private constructor for internal use.
     *
     * @param g Graphics2D object for the java component to be drawn.
     */
    private PDEGraphics2D(Graphics2D g) {
        super(g);
    }

    /**
     * Constructs an initialised PDEGraphics2D object of specified width and
     * height.
     *
     * * @param g Graphics2D object for the java component to be drawn.
     * @param w the width of the component
     * @param h the height of the component
     */
    public PDEGraphics2D(Graphics2D g, int w, int h) {
        this(g, new Dimension(w, h));
    }

    /**
     * Constructs an initialised PDEGraphics2D object of specified width and
     * height.
     *
     * @param g Graphics2D object for the java component to be drawn.
     * @param d Dimension of the component
     */
    public PDEGraphics2D(Graphics2D g, Dimension d) {
        super(g);
        pictures.add(0, new Formatter(new StringBuilder(1024)));
        initialise(d);

    }

    /**
     * Convenience static method for painting an AWT Component and its contents.
     * Returns a PDEGraphics instance that is ready to write to file.
     *
     * @param c the component to paint
     * @return a ready-to-write PDEGraphics instance.
     */
    public static PDEGraphics2D paint(final Component c) {
        final PDEGraphics2D g = new PDEGraphics2D((Graphics2D) c.getGraphics(), c.getSize());
        c.paint(g);
        return g;
    }

    /**
     * Convenience static method for painting an Swing JComponent and its
     * contents. Returns a PDEGraphics instance that is ready to write to file.
     *
     * @param c the component to paint
     * @return a ready-to-write PDEGraphics instance.
     */
    public static PDEGraphics2D paint(final JComponent c) {
        final PDEGraphics2D g = new PDEGraphics2D((Graphics2D) c.getGraphics(), c.getSize());
        c.paint(g);
        return g;
    }

    /**
     * Convenience static method for painting an AWT Window and its contents.
     * Returns a PDEGraphics instance that is ready to write to file.
     *
     * @param c the component to paint
     * @return a ready-to-write PDEGraphics instance.
     */
    public static PDEGraphics2D paint(final Window c) {
        final PDEGraphics2D g = new PDEGraphics2D((Graphics2D) c.getGraphics(), c.getSize());
        c.paint(g);
        return g;
    }

    /**
     * Convenience static method for painting an Swing JApplet and its contents.
     * Returns a PDEGraphics instance that is ready to write to file.
     *
     * @param c the component to paint
     * @return a ready-to-write PDEGraphics instance.
     */
    public static PDEGraphics2D paint(final JApplet c) {
        final PDEGraphics2D g = new PDEGraphics2D((Graphics2D) c.getGraphics(), c.getSize());
        c.paint(g);
        return g;
    }

    /**
     * Returns an integer value, the bits of which indicate the use of
     * fixes/workarounds for the look and feel in use.
     *
     * @return the lfFixes
     */
    public int getLFFixes() {
        return lfFixes;
    }

    /**
     * Returns a shallow copy the the keywords.
     *
     * @return an ArrayList<String>
     */
    public ArrayList<String> getKeyWords() {
        return new ArrayList<String>(keyWords);
    }

    /**
     * Sets the keyWords list.
     *
     * @param keyWords the keyWords to set
     */
    public void setKeyWords(ArrayList<String> keyWords) {
        this.keyWords = keyWords;
    }

    public void addKeyWord(String word) {
        keyWords.add(word);
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Creates a title. This will be display in index.html and used as the frame
     * title by Processing.app.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Creates a description. This will be display in index.html.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the textAsShapes setting.
     *
     * If true, text will be rendered as glyphs. If false, the default
     * Processing font will be used.
     *
     * @return the textAsShapes
     */
    public boolean isTextAsShapes() {
        return textAsShapes;
    }

    /**
     * Sets the textAsShapes setting. If true, text will be rendered as glyphs.
     * If false, the default Processing font will be used.
     *
     * @param textAsShapes the textAsShapes to set
     */
    public void setTextAsShapes(boolean textAsShapes) {
        this.textAsShapes = textAsShapes;
    }

    /**
     * Internal use.
     *
     * @param d a Dimension instance
     */
    private void initialise(Dimension d) {
        if (getFont() != null) {
            baseFont = getFont();
            fontList.put(getFont().getName(), getFont());
        }
        dimension = d;
    }

    @Override
    public Graphics create() {
        return (Graphics) clone();
    }

    @Override
    public Graphics create(int x, int y, int width, int height) {
        PDEGraphics2D g = new PDEGraphics2D((Graphics2D) parentGraphics.create(x, y, width, height));
        g.customShapes = customShapes;
        g.pictures = pictures;
        g.setTextAsShapes(isTextAsShapes());
        g.images = images;
        g.imageDim = imageDim;
        g.dimension = dimension;
        g.lfFixes = lfFixes;
        g.fontList = fontList;
        g.code = code;
        g.strokeEndCap = strokeEndCap;
        g.strokeJoin = strokeJoin;
        g.strokeLineWidth = strokeLineWidth;
        return g;
    }

    /**
     * Appends a paint of the specified component to this instance. The
     * component should have the same dimensions as the graphics for this
     * instance.
     *
     * The appended graphics will be painted, when deployed to the target, at
     * the time specified (+ the actionDelay constant value).
     *
     * @param c the component to paint
     * @param elapsedTime the time to paint this component in the deployed code.
     */
    @Override
    public void append(Component c, int elapsedTime) {
        pictures.add(new Formatter(new StringBuilder(1024)));
        latency.add(elapsedTime);
        c.paint(this);
    }

    /**
     * Appends a paint of the specified component to this instance. The
     * component should have the same dimensions as the graphics for this
     * instance.
     *
     * This is equivalent to {@code append(c, elapsedTime)} where elapsedTime is
     * the interval in milliseconds between the instantiation of this instance
     * and this call to {@code append}.
     *
     * @param c the component to paint
     */
    @Override
    public void append(Component c) {
        append(c, (int) (System.currentTimeMillis() - startTime));
    }

    /**
     *
     * @param fileName
     * @throws IOException
     */
    @Override
    public void write(String fileName) throws IOException {
        write(fileName, true, "processing.js");
    }

    public void write(String fileName, boolean htmlFlag) throws IOException {
        write(fileName, htmlFlag, "processing.js");
    }

    /**
     *
     * @param fileName - the file to create
     * @param htmlFlag - if true, an html file to view the script will also be
     * created with an accompanying style sheet.
     * @param jsLoc - String specifying the location of the processing.js file.
     * If this is "processing.js", a copy of the JavaScript file will be created
     * in the folder. Otherwise, the string should point to the location of a
     * processing JavaScript file relative to the created folder, or using a URL
     * e.g.
     *
     * "http://cloud.github.com/downloads/processing-js/processing-js/processing-1.4.1.min.js"
     *
     * @throws IOException
     */
    public void write(String fileName, boolean htmlFlag, String jsLoc) throws IOException {
        if (!fileName.endsWith(".pde")) {
            fileName = fileName.concat(".pde");
        }
        File f = new File(fileName);
        String path = f.toURI().getPath().replace(f.getName(), "");
        String pdeFile = path.concat(f.getName().replace(".pde", File.separator));
        pdeFile = pdeFile.concat(f.getName());
        write(new File(pdeFile), htmlFlag, jsLoc);
    }

    /**
     *
     * @param f - the file to write to
     * @param htmlFlag - if true, an html file to view the script will also be
     * created.
     * @param jsLoc - String specifying the location of the processing.js file.
     * If this is "processing.js", a copy of the JavaScript file will be created
     * in the folder. Otherwise, the string should point to the location of a
     * processing JavaScript file relative to the created folder. To use a copy
     * from the web, specify a URL, e.g.
     *
     * "http://cloud.github.com/downloads/processing-js/processing-js/processing-1.4.1.min.js"
     *
     * @throws IOException
     */
    public void write(File f, boolean htmlFlag, String jsLoc) throws IOException {

        if (!f.getName().endsWith(".pde")) {
            throw new IOException("Not a pde file");
        }


        File folder;
        System.err.println(f.getPath() + " ---- " + f.getParentFile());
        if (f.getParentFile() != null && f.getParentFile().toString().endsWith(f.getName().replace(".pde", ""))) {
            folder = new File(f.getParentFile().toString());
        } else {
            folder = new File(f.getPath().replace(f.getName(), ""));
        }
        if (!folder.isDirectory()) {
            folder.mkdir();
        }


        super.write(folder.getPath().concat(File.separator).concat(f.getName()));

        if (images.size() > 0) {
            InputStream image_lic = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/NOTICE.TXT");
            FileOutputStream output3 = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("NOTICE.TXT"));
            while (image_lic.available() > 0) {
                byte[] buffer = new byte[image_lic.available()];
                int n = image_lic.read(buffer, 0, image_lic.available());
                output3.write(buffer, 0, n);
            }
            output3.close();
            image_lic.close();
            File dataFolder = new File(folder.getPath().concat(File.separator).concat("data"));
            if (!dataFolder.isDirectory()) {
                dataFolder.mkdir();
            }

            for (Integer k : images.keySet()) {
                ImageIcon icon = images.get(k);
                Image img = icon.getImage();
                String name = String.format("image_%d_%x.png", k, icon.hashCode());
                File fi = new File(dataFolder.toString() + File.separator + name);
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    if (img instanceof RenderedImage) {
                        ImageIO.write((RenderedImage) img, "png", fi);
                    } else if (img instanceof VolatileImage) {
                        ImageIO.write(((VolatileImage) img).getSnapshot(), "png", fi);
                    } else {
                        try {
                            BufferedImage buffer = this.getDeviceConfiguration().createCompatibleImage(icon.getIconWidth(), icon.getIconHeight());
                            icon.paintIcon(null, buffer.createGraphics(), 0, 0);
                            ImageIO.write(buffer, "png", fi);
                        } catch (Exception ex) {
                            System.err.println(String.format("Image format not supported yet: %s\n", img.getClass().toString()));
                        }
                    }
                } else {
                    System.err.println(String.format("Image not complete: %s [%s]\n", name, img.getClass().toString()));
                }
            }
            File xml = new File(dataFolder.toString() + File.separator + "image" + ".xml");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(xml), "UTF-8");
            out.write("<?xml version=\"1.0\"?>\n<images>\n");

            for (Integer k : images.keySet()) {
                ImageIcon icon = images.get(k);
                Rectangle2D r = imageDim.get(k);
                out.write(String.format("<image id=\"%d\" hashCode=\"%x\" x=\"%g\" y=\"%g\" width=\"%g\" height=\"%g\"> </image>\n",
                        k.intValue(),
                        icon.hashCode(),
                        r.getX(),
                        r.getY(),
                        r.getWidth(),
                        r.getHeight()));
            }
            out.close();
        }

        if (htmlFlag) {
            Formatter fileFormatter = new Formatter(new StringBuilder(1024));
            fileFormatter.format("<!DOCTYPE html>\n");
            fileFormatter.format("<head>\n");
            fileFormatter.format("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />\n");
            fileFormatter.format("<title>%s</title>\n", title);
            fileFormatter.format("<!--Please leave the PDEGraphics2D keyword in place - they can be indexed by search engines and assist furture development -->\n");

            Formatter keyWordString = new Formatter(new StringBuilder(512));
            keyWordString.format("<meta name=\"keywords\" content=\"PDEGraphics2D, ");
            for (Iterator<String> it = keyWords.iterator(); it.hasNext();) {
                String word = it.next();
                if (it.hasNext()) {
                    keyWordString.format("%s, ", word);
                } else {
                    keyWordString.format("%s", word);
                }
            }
            keyWordString.format("\">\n");

            fileFormatter.format("%s", keyWordString.toString());
            fileFormatter.format("<script src=\"%s\" type=\"text/javascript\"></script>\n", jsLoc);
            fileFormatter.format("</head>\n");
            fileFormatter.format("<body>\n<div>\n");
            fileFormatter.format("<!--\n");
            fileFormatter.format("%s\n", description);
            fileFormatter.format("-->\n");
            fileFormatter.format("<canvas id=\"%s\" data-processing-sources=\"%s\" width=\"%d\" height=\"%d\">\n"
                    + "<p>\nYour browser does not support the canvas tag.\n</p>\n",
                    f.getName().replace(".pde", ""),
                    f.getName(),
                    (int) dimension.getWidth(),
                    (int) dimension.getHeight());
            fileFormatter.format("</canvas>\n");
            fileFormatter.format("</div>\n");
            fileFormatter.format("</body>\n");
            fileFormatter.format("</html>\n");

            File html = new File(folder.getPath().concat(File.separator).concat(f.getName().replace(".pde", ".html")));
            OutputStreamWriter outHTML = new OutputStreamWriter(new FileOutputStream(html), "UTF-8");
            outHTML.write(fileFormatter.toString());
            outHTML.close();

            if (jsLoc.equals("processing.js")) {
                InputStream js = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/pde/processing-1.4.1.min.js");
                FileOutputStream output = new FileOutputStream(
                        folder.getPath()
                        .concat(File.separator)
                        .concat("processing.js"));
                while (js.available() > 0) {
                    byte[] buffer = new byte[js.available()];
                    int n = js.read(buffer, 0, js.available());
                    output.write(buffer, 0, n);
                }
                output.close();
                js.close();
                InputStream lic = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/pde/PROCESSING-JS-LICENSE");
                FileOutputStream output2 = new FileOutputStream(
                        folder.getPath()
                        .concat(File.separator)
                        .concat("PROCESSINGJS-LICENSE.TXT"));
                while (lic.available() > 0) {
                    byte[] buffer = new byte[lic.available()];
                    int n = lic.read(buffer, 0, lic.available());
                    output2.write(buffer, 0, n);
                }
                output2.close();
                lic.close();
            }

            InputStream py = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/pde/httpd.py");
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

            InputStream index = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/pde/index.html");
            FileOutputStream indexFile = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("index.html"));
            OutputStreamWriter indexStream = new OutputStreamWriter(indexFile, "UTF-8");
            while (index.available() > 0) {
                byte[] buffer = new byte[index.available()];
                int n = index.read(buffer, 0, index.available());
                String s = new String(buffer);
                s = s.replace("<!-- KEYWORDS -->", keyWordString.toString());
                s = s.replaceAll("cccccccccc", f.getName().replace(".pde", ""));
                s = s.replace("<!-- TITLE -->", title);
                s = s.replace("<!-- DESCRIPTION -->", description);
                s = s.replaceAll("tttttttttt", String.format("%s", title));
                s = s.replaceAll("dddddddddd", String.format("%s", description));
                s = s.replaceAll("ssssssssss", jsLoc);
                s = s.replaceAll("1111111111", Integer.valueOf(dimension.width).toString());
                s = s.replaceAll("2222222222", Integer.valueOf(dimension.height).toString());
                indexFile.write(s.getBytes("UTF-8"));
            }
            index.close();
            indexFile.close();
            indexStream.close();

            InputStream css = PDEGraphics2D.class.getClassLoader().getResourceAsStream("kcl/waterloo/deploy/pde/PDEGraphics2D.css");
            FileOutputStream cssFile = new FileOutputStream(
                    folder.getPath()
                    .concat(File.separator)
                    .concat("PDEGraphics2D.css"));
            OutputStreamWriter cssStream = new OutputStreamWriter(cssFile, "UTF-8");
            while (css.available() > 0) {
                byte[] buffer = new byte[css.available()];
                int n = css.read(buffer, 0, css.available());
                String s = new String(buffer);
                s = s.replaceAll("1111111111", Integer.valueOf(dimension.width).toString());
                s = s.replaceAll("2222222222", Integer.valueOf(dimension.height).toString());
                cssFile.write(s.getBytes("UTF-8"));
            }
            css.close();
            cssFile.close();
            cssStream.close();

        }
    }

    /**
     * Returns the current formatter to which code writing is directed.
     *
     * @return the current Formatter
     */
    private Formatter getFormatter() {
        return pictures.get(pictures.size() - 1);
    }

    @Override
    public void setStroke(Stroke s2) {
        super.setStroke(s2);
        BasicStroke s = (BasicStroke) s2;
        if (strokeLineWidth != s.getLineWidth()) {
            strokeLineWidth = s.getLineWidth();
            getFormatter().format("strokeWeight(strkS(%3.1f));\n", strokeLineWidth);
        }
        if (s.getEndCap() != strokeEndCap) {
            strokeEndCap = s.getEndCap();
            switch (s.getEndCap()) {
                case BasicStroke.CAP_BUTT:
                    getFormatter().format("strokeCap(PROJECT);\n");
                    break;
                case BasicStroke.CAP_ROUND:
                    getFormatter().format("strokeCap(ROUND);\n");
                    break;
                case BasicStroke.CAP_SQUARE:
                    getFormatter().format("strokeCap(SQUARE);\n");
                    break;
            }
        }
        if (s.getLineJoin() != strokeJoin) {
            strokeJoin = s.getLineJoin();
            switch (s.getLineJoin()) {
                case BasicStroke.JOIN_BEVEL:
                    getFormatter().format("strokeJoin(BEVEL);\n");
                    break;
                case BasicStroke.JOIN_MITER:
                    getFormatter().format("strokeJoin(MITER);\n");
                    break;
                case BasicStroke.JOIN_ROUND:
                    getFormatter().format("strokeJoin(ROUND);\n");
                    break;
            }
        }
    }

    private void localSetFont(Font font) {
        if (font != null && getFont() != null) {
            if (!font.getName().equals(getFont().getName())
                    || font.getSize2D() != getFont().getSize2D()) {
                getFormatter().format("textFont(%s, txtS(%4.1f));\n",
                        font.getName().replaceAll("[. -]", "_"),
                        font.getSize2D());
            }
            super.setFont(font);
            if (!fontList.containsKey(font.getName())) {
                fontList.put(font.getName(), font);
            }
        }
    }

    @Override
    public void draw(Shape s) {

        if (s == null) {
            return;
        }

        super.draw(s);

        if (getPaint() instanceof GradientPaint || getPaint() instanceof MultipleGradientPaint) {
            BufferedImage buffer = getDeviceConfiguration().createCompatibleImage(
                    (int) (s.getBounds().width),
                    (int) (s.getBounds().height),
                    Transparency.TRANSLUCENT);
            Graphics2D g4 = buffer.createGraphics();
            g4.setComposite(getComposite());
            g4.setPaint(getPaint());
            g4.draw(s);
            //System.out.println("Draw gradient");
            drawImage(buffer, getTransform(), null);
            return;
        }

        if (preProcessShape(s, getFormatter())) {
            return;
        }

        Formatter localFormatter = startFunction(true, false);
        s = processShape(s, localFormatter);
        if (s == null) {
            endFunction(localFormatter, true, false);
            return;
        }

        boolean closed = false;

        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }

        double startX = Double.NaN;
        double startY = Double.NaN;
        for (Iterator<GJPathSegmentInfo> it = segments.iterator(); it.hasNext();) {
            GJPathSegmentInfo segment = it.next();
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    closed = false;
                    startX = segment.getData()[0];
                    startY = segment.getData()[1];
                    break;
                case PathIterator.SEG_LINETO:
                    localFormatter.format("line(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                            startX,
                            startY,
                            segment.getData()[0],
                            segment.getData()[1]);
                    startX = segment.getData()[0];
                    startY = segment.getData()[1];
                    break;
                case PathIterator.SEG_QUADTO:
                    localFormatter.format("bezier(%4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f);\n",
                            startX,
                            startY,
                            segment.getData()[0],
                            segment.getData()[1],
                            segment.getData()[0],
                            segment.getData()[1],
                            segment.getData()[2],
                            segment.getData()[3]);
                    startX = segment.getData()[2];
                    startY = segment.getData()[3];
                    break;
                case PathIterator.SEG_CUBICTO:
                    localFormatter.format("bezier(%4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f, %4.1f);\n",
                            startX,
                            startY,
                            segment.getData()[0],
                            segment.getData()[1],
                            segment.getData()[2],
                            segment.getData()[3],
                            segment.getData()[4],
                            segment.getData()[5]);
                    startX = segment.getData()[4];
                    startY = segment.getData()[5];
                    break;
                case PathIterator.SEG_CLOSE:
                    if (!it.hasNext()) {
                        startX = Double.NaN;
                        startY = Double.NaN;
                        localFormatter = endFunction(localFormatter, true, false);
                        closed = true;
                    }
            }
        }
        if (!closed) {
            endFunction(localFormatter, true, false);
        }
    }

    @Override
    public void fill(Shape s) {
        if (s == null) {
            return;
        }
        super.fill(s);

        if (getPaint() instanceof GradientPaint || getPaint() instanceof MultipleGradientPaint) {
            BufferedImage buffer = getDeviceConfiguration().createCompatibleImage(
                    (int) (s.getBounds().width),
                    (int) (s.getBounds().height),
                    Transparency.TRANSLUCENT);
            Graphics2D g4 = buffer.createGraphics();
            g4.setComposite(getComposite());
            g4.setPaint(getPaint());
            g4.fill(s);
            drawImage(buffer, getTransform(), null);
            return;
        }

        if (preProcessShape(s, getFormatter())) {
            return;
        }

        Formatter localFormatter = startFunction(false, true);
        s = processShape(s, localFormatter);
        if (s == null) {
            endFunction(localFormatter, false, true, 0, 0);
            return;
        }


        boolean closed = false;

        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }

        int count = -1;
        double xo = 0d;
        double yo = 0d;
        for (Iterator<GJPathSegmentInfo> it = segments.iterator(); it.hasNext();) {
            GJPathSegmentInfo segment = it.next();
            count++;
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    if (closed == false && count > 0) {
                        localFormatter.format("endShape();\n");
                        localFormatter = endFunction(localFormatter, false, true, xo, yo);
                    }
                    closed = false;
                    xo = segment.getData()[0];
                    yo = segment.getData()[1];
                    localFormatter.format("beginShape();\n");
                    localFormatter.format("vertex(xo + %4.1f,yo + %4.1f);\n",
                            segment.getData()[0] - xo,
                            segment.getData()[1] - yo);
                    break;
                case PathIterator.SEG_LINETO:
                    localFormatter.format("vertex(xo + %4.1f,yo + %4.1f);\n",
                            segment.getData()[0] - xo,
                            segment.getData()[1] - yo);
                    break;
                case PathIterator.SEG_QUADTO:
                    localFormatter.format("quadraticVertex(xo + %4.1f,yo + %4.1f,xo + %4.1f,yo + %4.1f);\n",
                            segment.getData()[0] - xo,
                            segment.getData()[1] - yo,
                            segment.getData()[2] - xo,
                            segment.getData()[3] - yo);
                    break;
                case PathIterator.SEG_CUBICTO:
                    localFormatter.format("bezierVertex(xo + %4.1f,yo + %4.1f,xo + %4.1f,yo + %4.1f,xo + %4.1f,yo + %4.1f);\n",
                            segment.getData()[0] - xo,
                            segment.getData()[1] - yo,
                            segment.getData()[2] - xo,
                            segment.getData()[3] - yo,
                            segment.getData()[4] - xo,
                            segment.getData()[5] - yo);
                    break;
                case PathIterator.SEG_CLOSE:
                    localFormatter.format("endShape();\n");
                    localFormatter = endFunction(localFormatter, false, true, xo, yo);
                    closed = true;
            }
        }
        if (!closed) {
            localFormatter.format("endShape();\n");
            endFunction(localFormatter, false, true, xo, yo);
        }
    }

    /**
     * Deal with "shapes" that have zero area differently.
     */
    private boolean preProcessShape(Shape s, Formatter formatter) {
        if (s instanceof Point2D) {
            /**
             * Point - 1 pixel only so turn off smoothing
             */
            Point2D p = (Point2D) s;
            if (getClip().contains(p)) {
                getTransform().transform(p, p);
                formatter.format("noSmooth();\npoint(%4.1f, %4.1f);\nsmooth()\n",
                        p.getX(),
                        p.getY());
            }
            return true;
        } else if (s instanceof Line2D) {
            /**
             * Lines - create an outline trace with an area corresponding to the
             * line for clipping.
             */
            Line2D line = (Line2D) s;
            double theta = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());
            boolean posSlope;
            if ((theta > -Math.PI / 2 && theta <= 0) || (theta > Math.PI / 2 && theta < Math.PI)) {
                posSlope = true;
            } else {
                posSlope = false;
            }
            Stroke stk = new BasicStroke(1f);
            s = stk.createStrokedShape(line);
            Area area = new Area(s);
            area.intersect(new Area(getClip()));
            s = getTransform().createTransformedShape(area);
            if (posSlope) {
                formatter.format("line(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                        s.getBounds2D().getMinX(),
                        s.getBounds().getMaxY(),
                        s.getBounds().getMaxX(),
                        s.getBounds().getMinY());
            } else {
                formatter.format("line(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                        s.getBounds2D().getMinX(),
                        s.getBounds().getMinY(),
                        s.getBounds().getMaxX(),
                        s.getBounds().getMaxY());
            }
            return true;
        }
        return false;
    }

    private Shape processShape(Shape s, Formatter localFormatter) {
        if (s instanceof Rectangle2D) {
            Shape s2 = doClip(s, localFormatter);
            // Need to check it is still a rectangle after clipping
            if (s2 != null && new Area(s2).isRectangular()) {
                s2 = getTransform().createTransformedShape(s2);
                localFormatter.format("rect(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                        s2.getBounds2D().getX(),
                        s2.getBounds2D().getY(),
                        s2.getBounds2D().getWidth(),
                        s2.getBounds2D().getHeight());
                return null;
            }
        } else if (s instanceof RoundRectangle2D) {
            //TODO:
        } else if (s instanceof Ellipse2D) {
            Rectangle2D r = s.getBounds();
            Shape s2 = doClip(s, localFormatter);
            // Can only do this when inside the clip bounds
            if (s2.getBounds().equals(r)) {
                s2 = getTransform().createTransformedShape(s);
                localFormatter.format("ellipse(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                        s2.getBounds2D().getX(),
                        s2.getBounds2D().getY(),
                        s2.getBounds2D().getWidth(),
                        s2.getBounds2D().getHeight());
                return null;
            }
        } else if (new Area(s).isPolygonal()) {
            Shape s2 = doClip(s, localFormatter);
            s2 = getTransform().createTransformedShape(s2);
            Area area = new Area(s2);
            if (area.isPolygonal()) {
                //TODO:
            }
        }
        s = doClip(s, localFormatter);
        if (s != null) {
            s = getTransform().createTransformedShape(s);
            return s;
        } else {
            return null;
        }

    }

    private Shape doClip(Shape s, Formatter localFormatter) {
        if (getClip() != null) {
            Area area = new Area(s);
            Area clipArea = new Area(getClip());
            if (clipArea.contains(s.getBounds2D())) {
                // Shape is entirely within the clip bounds so just return it
                return s;
            } else {
                // Create a new area limited to the clip region
                area.intersect(clipArea);
                if (!area.isEmpty()) {
                    if (area.isRectangular()) {
                        s = getTransform().createTransformedShape(area);
                        localFormatter.format("rect(%4.1f, %4.1f, %4.1f, %4.1f);\n",
                                s.getBounds2D().getX(),
                                s.getBounds2D().getY(),
                                s.getBounds2D().getWidth(),
                                s.getBounds2D().getHeight());
                        return null;
                    } else {
                        s = area;
                    }
                }
            }
        }
        return s;
    }

    private Formatter startFunction(boolean drawFlag, boolean fillFlag) {
        StringBuilder str = new StringBuilder();
        Formatter localFormatter = new Formatter(str);
        return localFormatter;
    }

    private Formatter endFunction(Formatter localFormatter, boolean drawFlag, boolean fillFlag) {
        return endFunction(localFormatter, drawFlag, fillFlag, 0d, 0d);
    }

    private Formatter endFunction(Formatter localFormatter, boolean drawFlag, boolean fillFlag, double xo, double yo) {
        String codeBody = localFormatter.toString();
        // Prettify
        codeBody = codeBody.replaceAll("\\+ -", "- ");
        if (code.containsKey(codeBody)) {
            if (fillFlag) {
                getFormatter().format("fillShape_%x(%4.1f, %4.1f);\n", Integer.valueOf(code.get(codeBody)), xo, yo);
            } else {
                getFormatter().format("drawShape_%x();\n", Integer.valueOf(code.get(codeBody)));
            }
        } else {
            if (fillFlag) {
                String s = String.format("void fillShape_%x(float xo, float yo) {\n", localFormatter.hashCode());
                s = s.concat(String.format("pushStyle();\nnoStroke();\n"));
                s = s.concat(codeBody);
                s = s.concat(String.format("popStyle();\n}\n\n"));
                customShapes.add(s);
                getFormatter().format("fillShape_%x(%4.1f, %4.1f);\n", localFormatter.hashCode(), xo, yo);
            } else {
                String s = String.format("void drawShape_%x() {\n", localFormatter.hashCode());
                s = s.concat(String.format("pushStyle();\nnoFill();\n"));
                s = s.concat(codeBody);
                s = s.concat(String.format("popStyle();\n}\n\n"));
                customShapes.add(s);
                getFormatter().format("drawShape_%x();\n", localFormatter.hashCode());
            }
            code.put(codeBody, Integer.valueOf(localFormatter.hashCode()));
        }
        localFormatter.close();
        return startFunction(drawFlag, fillFlag);
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, w, h);
        fill(rect);
    }

    @Override
    public void drawRect(int x, int y, int w, int h) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, w, h);
        draw(rect);
    }

    @Override
    public void drawString(String str, int x, int y) {
        drawString(str, (float) x, (float) y);
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        drawString(iterator, (float) x, (float) y);
    }

    @Override
    public void drawString(String str, float x, float y) {
        if (textAsShapes) {
            GlyphVector gv = getFont().createGlyphVector(getFontRenderContext(), str);
            drawGlyphVector(gv, x, y);
        } else {
            localSetFont(getFont());
            super.drawString(str, x, y);
            Point2D.Float p = new Point2D.Float();
            getTransform().transform(new Point2D.Float(x, y), p);
            FontMetrics metrics = getFontMetrics(getFont());
            str = str.replace("\"", "\\\"");
            if (getClipBounds() != null) {
                while (str != null && str.length() > 2
                        && p.x + metrics.stringWidth(str) >= getTransform().getTranslateX() + getClipBounds().width) {
                    str = str.substring(0, str.length() - 1);
                }
            }
            getFormatter().format("text(\"%s\", %4.1f, %4.1f);\n", str, p.x, p.y);
        }
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        char[] next = new char[iterator.getEndIndex() - iterator.getBeginIndex()];
        for (int k = iterator.getBeginIndex(); k < iterator.getEndIndex()-1; k++) {
            next[k] = iterator.current();
            iterator.next();
        }
        drawString(new String(next), x, y);
    }
    
    @Override
    public void drawGlyphVector(GlyphVector vec, float x, float y) {
        Shape glyphOutline = vec.getOutline(x, y);
        Stroke old = getStroke();
        setStroke(glyphStroke);
        draw(glyphOutline);
        setStroke(old);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        draw(line);
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        super.clearRect(x, y, width, height);
        setPaint(super.getPaint());
        fillRect(x, y, width, height);
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        draw(new RoundRectangle2D.Double((double) x, (double) y, (double) width, (double) height,
                (double) arcWidth, (double) arcHeight));
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        fill(new RoundRectangle2D.Double((double) x, (double) y, (double) width, (double) height,
                (double) arcWidth, (double) arcHeight));
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        draw(new Ellipse2D.Double((double) x, (double) y, (double) width, (double) height));
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        draw(new Ellipse2D.Double((double) x, (double) y, (double) width, (double) height));
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Arc2D arc = new Arc2D.Float((float) x, (float) y, (float) width, (float) height,
                (float) startAngle, (float) arcAngle, Arc2D.OPEN);
        draw(arc);
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Arc2D arc = new Arc2D.Float((float) x, (float) y, (float) width, (float) height,
                (float) startAngle, (float) arcAngle, Arc2D.PIE);
        fill(arc);
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        for (int k = 1; k < xPoints.length; k++) {
            drawLine(xPoints[k - 1], yPoints[k - 1], xPoints[k], yPoints[k]);
        }
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        draw(new Polygon(xPoints, yPoints, nPoints));
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        fill(new Polygon(xPoints, yPoints, nPoints));
    }

    @Override
    public void setColor(Color c) {
        if (c != getPaint()) {
            super.setColor(c);
            float alpha = ((AlphaComposite) parentGraphics.getComposite()).getAlpha();
            String s = String.format("%d,%d,%d,%d", c.getRed(), c.getGreen(), c.getBlue(), (int) ((float) c.getAlpha() * alpha));
            getFormatter().format("fill(%s);\n", s);
            getFormatter().format("stroke(%s);\n", s);
        }
    }

    @Override
    public void setPaint(Paint p) {
        if (p != getPaint()) {
            super.setPaint(p);
            Color c = super.getColor();
            float alpha = ((AlphaComposite) parentGraphics.getComposite()).getAlpha();
            String s = String.format("%d,%d,%d,%d", c.getRed(), c.getGreen(), c.getBlue(), (int) ((float) c.getAlpha() * alpha));
            getFormatter().format("fill(%s);\n", s);
            getFormatter().format("stroke(%s);\n", s);
        }
    }

    @Override
    public boolean copyImage(Image img, int dx, int dy, int sx, int sy,
            int width, int height, Color bgcolor, ImageObserver observer) {
        return drawImage(img, dx, dy, dx + width, dy + height, sx, sy, sx + width, sy + height, bgcolor, observer);
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        super.copyArea(x, y, width, height, dx, dy);
    }

    @Override
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        super.drawBytes(data, offset, length, x, y);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null, observer);
    }

    @Override
    public boolean drawImage(final Image img, final int dx1, final int dy1,
            final int dx2, final int dy2, final int sx1, final int sy1,
            final int sx2, final int sy2, final Color bgcolor, final ImageObserver observer) {
        drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer, getTransform());
        return super.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    private void drawImage(final Image img, final int dx1, final int dy1,
            final int dx2, final int dy2, final int sx1, final int sy1,
            final int sx2, final int sy2, final Color bgcolor, final ImageObserver observer, final AffineTransform xform) {
        if (observer != null) {
            System.out.println(observer.getClass());
        }
        BufferedImage buffer;
        buffer = getDeviceConfiguration().createCompatibleImage(img.getWidth(observer),
                img.getHeight(observer),
                Transparency.TRANSLUCENT);
        Graphics2D g4 = buffer.createGraphics();
        g4.setComposite(getComposite());
        g4.setRenderingHints(getRenderingHints());
        g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g4.drawImage(img, null, null);
        try {
            buffer = buffer.getSubimage(sx1, sy1, sx2 - sx1, sy2 - sy1);
            Image img2 = buffer.getScaledInstance(dx2 - dx1, dy2 - dy1, Image.SCALE_SMOOTH);
            processImage(img2, xform, dx1, dy1, observer);
        } catch (RasterFormatException ex) {
            System.out.println(ex);
            final AffineTransform xform2 = new AffineTransform(xform);
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1,
                            sx2, sy2, bgcolor, observer, xform2);
                }
            });
        }
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return drawImage(img, x, y, width, height, null, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        BufferedImage buffer;
        if (observer != null && (observer instanceof JButton) && ((lfFixes & aquaJButton) == 1)) {
            JButton button = (JButton) observer;
            buffer = getDeviceConfiguration().createCompatibleImage(button.getWidth(),
                    button.getHeight(),
                    Transparency.TRANSLUCENT);
            Graphics2D g4 = buffer.createGraphics();
            g4.setComposite(getComposite());
            g4.setRenderingHints(getRenderingHints());
            g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            button.paint(g4);
            processImage(buffer, getTransform(), 0, 0, observer);
        } else {
            buffer = getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            Graphics2D g4 = buffer.createGraphics();
            g4.setComposite(getComposite());
            g4.setRenderingHints(getRenderingHints());
            g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g4.drawImage(img, 0, 0, width, height, bgcolor, null);
            processImage(buffer, getTransform(), x, y, observer);
        }
        return super.drawImage(img, x, y, width, height, bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver observer) {
        if (observer != null) {
            System.out.println(observer.getClass());
        }
        if (img == null) {
            return true;
        }
        if (xform == null || xform.isIdentity()) {
            return drawImage(img, 0, 0, img.getWidth(observer), img.getHeight(observer), observer);
        }
        if (xform.getDeterminant() != 0) {
            if (xform.equals(getTransform())) {
                processImage(img, xform, 0, 0, observer);
            } else {
                AffineTransform xform2 = (AffineTransform) xform.clone();
                xform2.concatenate(getTransform());
                processImage(img, xform2, 0, 0, observer);
            }
        }
        return super.drawImage(img, xform, observer);
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        BufferedImage buffer = getDeviceConfiguration()
                .createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g4 = buffer.createGraphics();
        // N.B. Ignore hints in op.
        g4.setRenderingHints(getRenderingHints());
        g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g4.drawImage(img, op, 0, 0);
        writeImage(buffer, (int) getTransform().getTranslateX() + x,
                (int) getTransform().getTranslateY() + y,
                buffer.getWidth(),
                buffer.getHeight(),
                false);
        super.drawImage(img, op, x, y);
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        BufferedImage buffer = getDeviceConfiguration()
                .createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g4 = buffer.createGraphics();
        g4.setRenderingHints(getRenderingHints());
        g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g4.drawRenderedImage(img, new AffineTransform());
        processImage(buffer, xform, 0, 0, null);
        super.drawRenderedImage(img, xform);
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        BufferedImage buffer = getDeviceConfiguration()
                .createCompatibleImage((int) img.getWidth(), (int) img.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g4 = buffer.createGraphics();
        g4.setRenderingHints(getRenderingHints());
        g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g4.drawRenderableImage(img, new AffineTransform());
        processImage(buffer, xform, 0, 0, null);
        super.drawRenderableImage(img, xform);
    }

    @Override
    public String getCode() {
        Formatter lF = new Formatter(new StringBuilder(1024));
        lF.format("/*\n* Processing script created using PDEGraphics2D which is part of\n* Project Waterloo\n");
        lF.format("*             http://sigtool.sourceforge.net\n");
        lF.format("* Project Waterloo and Waterloo Scientific Graphics are King's College London projects\n");
        lF.format("* Author: Malcolm Lidierth, Wolfson Centre for Age-Related Diseases\n*\n");
        lF.format("*\n* This file was created: %s\n", new Date().toString());
        lF.format("*\n* Display area size was %d x %d pixels when created but may be edited\n", dimension.width, dimension.height);
        lF.format("* within the setup() function below.\n");
        lF.format("*\n* Coordinates are in pixels. To scale and move the drawing in the drawing area,\n");
        lF.format("* alter the scaling and translate factors set in the draw() function below.\n");
        lF.format("*\n* All displayed co-ordinates will be derived as (original script value*scale)+translation offset.\n");
        lF.format("* This for crisp lines:\n");
        lF.format("* @pjs crisp=\"true\";\n");

        String lastImage = "";
        if (images.size() > 0) {
            lF.format("* @pjs preload=\"");
            for (Iterator<Integer> it = images.keySet().iterator(); it.hasNext();) {
                Integer k = it.next();
                ImageIcon icon = images.get(k);
                lF.format("./data/image_%d_%x.png", k.intValue(), icon.hashCode());
                if (it.hasNext()) {
                    lF.format(",");
                } else {
                    lastImage = String.format("img_%d_%x", k.intValue(), icon.hashCode());
                }
            }
            lF.format("\";\n");
        }
        lF.format("*/\n");

        lF.format("\n/**\n* %s\n*\n* %s\n*/\n", title, description);

        if (fontList.size() > 0) {
            lF.format("// Font creation: the default font will be used if these are not available\n");
            for (String fontname : fontList.keySet()) {
                lF.format("PFont %s = createFont(\"%s\", 48, true);\n",
                        fontList.get(fontname).getName().replaceAll("[. -]", "_"),
                        fontList.get(fontname).getName());
            }
        }

        double shortestDelay = Double.MAX_VALUE;

        lF.format("\n// User-definable function to set text sizes\nfloat txtS(float sz) {return sz;}\n// User-definable function so set line widths\nfloat strkS(float w) {return w;}\n");
        if (pictures.size() > 1) {
            for (int k = 1; k < latency.size() - 1; k++) {
                shortestDelay = Math.min(shortestDelay, latency.get(k + 1) - latency.get(k));
            }
            lF.format("\nint counter=0;\n%s\nvoid setup() {\ncounter=millis();\n", prependedCode);
        } else {
            lF.format("\n%s\nvoid setup() {\n", prependedCode);
        }
        lF.format("size(%d,%d);\nellipseMode(CORNER);\n", dimension.width, dimension.height);

        if (title != null && !title.isEmpty() && !title.equals("Title")) {
            lF.format("try {\nframe.setTitle(\"%s\");\n}\ncatch (Exception ex) {\n//javascript: frame not defined}\n}\n", title);
        }

        lF.format("}\n\n\nvoid draw() {\nsmooth();\nscale(1,1);\ntranslate(0,0);\ntextAlign(LEFT,BOTTOM);\n");
        if (baseFont != null) {
            lF.format("textFont(%s, txtS(%4.1f));\n",
                    baseFont.getName().replaceAll("[. -]", "_"),
                    baseFont.getSize2D());
        }
        if (pictures.size() > 1) {
            lF.format("if (millis() - counter < %d) {\n", latency.get(1).intValue() + getActionDelay());
        }

        String s2 = super.getCode();
        s2 = lF.toString().concat(s2);

        if (!lastImage.isEmpty() && pictures.size() <= 1) {
            s2 = s2.concat(String.format("if (%s.width > 0) {\nnoLoop();\n}\n} //EOF DRAW\n", lastImage));
        } else if (pictures.size() <= 1) {
            s2 = s2.concat(String.format("noLoop();\n}//EOF DRAW\n\n"));
        }

        if (pictures.size() > 1) {
            s2 = s2.concat(String.format("}\n"));
            for (int k = 1; k < pictures.size() - 1; k++) {
                s2 = s2.concat(String.format("if (millis() - counter > %d && millis() - counter < %d) {\npicture%d();\n}\n",
                        latency.get(k) + getActionDelay(), latency.get(k + 1) + getActionDelay(), k));
            }
            if (shortestDelay != Double.MAX_VALUE) {
                s2 = s2.concat(String.format("frameRate(%4.1f);\n", (1.0 / shortestDelay) * 2d));
            }
            s2 = s2.concat(String.format("if (millis() - counter > %d) {\npicture%d();\n/**\n* To prevent cyling, change this line to \"noLoop();\"\n*/\ncounter=millis()-%d;\n}\n",
                    latency.get(pictures.size() - 1).intValue() + getActionDelay(), pictures.size() - 1, getActionDelay()));
            s2 = s2.concat(String.format("}//EOF[DRAW]\n\n"));
        }


        if (images.size() > 0) {
            String declarations = String.format("/** Images */\n");
            String loadcalls = "";
            for (Integer k : images.keySet()) {
                ImageIcon icon = images.get(k);
                declarations = declarations.concat(String.format(
                        "PImage img_%d_%x;\n", k.intValue(), icon.hashCode()));
                loadcalls = loadcalls.concat(String.format("img_%d_%x=loadImage(\"./data/image_%d_%x.png\");\n", k.intValue(), icon.hashCode(), k.intValue(), icon.hashCode()));
            }
            s2 = s2.replace("void setup() {", String.format("%s\nvoid setup() {\n%s", declarations, loadcalls));
        }

        for (Iterator<String> it = customShapes.iterator(); it.hasNext();) {
            String s3 = it.next();
            s2 = s2.concat(s3);
        }


        for (int k = 1; k < pictures.size(); k++) {
            Formatter f3 = pictures.get(k);
            String s3 = String.format("\nvoid picture%d() {\n", k);
            s3 = s3.concat(f3.toString());
            s3 = s3.concat(String.format("}//EOF[PICTURE][%d]\n", k));
            s2 = s2.concat(s3);
        }

        /**
         * Check for background image i.e. any image matching the position &
         * size of this canvas. If present, suppress background painting for the
         * component.
         */
        if (images.size() > 0) {
            for (Integer k : imageDim.keySet()) {
                Rectangle2D r = imageDim.get(k);
                if (r.getX() == 0f && r.getY() == 0f
                        && r.getWidth() == dimension.getWidth()
                        && r.getHeight() == dimension.getHeight()) {
                    String target = String.format("rect(%4.1f, %4.1f, %4.1f, %4.1f);",
                            0f, 0f, dimension.getWidth(), dimension.getHeight());
                    s2 = s2.replaceAll(target, "//" + target);
                }
            }
        }

        s2 = s2.concat(String.format("\n%s\n", appendedCode));
        return s2;
    }

    @Override
    public void setPaintObject(Object referenceObject) {
        if (referenceObject != null) {
            if (referenceObject instanceof Component) {
                Component c = (Component) referenceObject;
                getFormatter().format("/* Painting %s : %x */\n",
                        c.getName() == null || c.getName().isEmpty() ? c.getClass().toString() : c.getName(),
                        c.hashCode());
            } else {
                getFormatter().format("/* Painting %s : %x */\n", referenceObject.getClass().toString(), referenceObject.hashCode());
            }
        }
    }

    @Override
    public void addComment(String comment) {
        if (comment.startsWith("/")) {
            // Already formatted - better not to do this as it may not work with
            // future subclasses of AbstractGraphics2D supporting other graphics
            // languages
            getFormatter().format("%s", comment);
        } else {
            if (comment.length() < 40) {
                getFormatter().format("/* %s */\n", comment);
            } else {
                getFormatter().format("/**\n* %s\n*/\n", comment);
            }
        }
    }

    @Override
    public void addCode(String code) {
        getFormatter().format("%s", code);
    }

    @Override
    public void prependCode(String code) {
        prependedCode = prependedCode.concat(code);
    }

    @Override
    public void appendCode(String code) {
        appendedCode = appendedCode.concat(code);
    }

    @Override
    protected Object clone() {
        PDEGraphics2D g = new PDEGraphics2D((Graphics2D) parentGraphics.create());
        g.customShapes = customShapes;
        g.pictures = pictures;
        g.setTextAsShapes(isTextAsShapes());
        g.images = images;
        g.imageDim = imageDim;
        g.dimension = dimension;
        g.lfFixes = lfFixes;
        g.fontList = fontList;
        g.code = code;
        g.strokeEndCap = strokeEndCap;
        g.strokeJoin = strokeJoin;
        g.strokeLineWidth = strokeLineWidth;
        return g;
    }

    private void writeImage(Image img, int x, int y, int width, int height, boolean flag) {
        this.writeImage(img, (float) x, (float) y, (float) width, (float) height, flag);
    }

    private void writeImage(Image img, float x, float y, float width, float height, boolean flag) {
        ImageIcon icon = new ImageIcon(img);
        images.put(images.size(), icon);
        imageDim.put(images.size() - 1, new Rectangle2D.Float(x, y, width, height));
        getFormatter().format("image(img_%d_%x,%4.1f, %4.1f, %4.1f, %4.1f);//Flip=%b\n",
                images.size() - 1,
                icon.hashCode(),
                x,
                y,
                width,
                height,
                flag);
    }

    private void processImage(Image img, AffineTransform xform, int x, int y, ImageObserver obs) {
        processImage(img, xform, true, x, y, obs);
    }

    private void processImage(Image img, AffineTransform xform, boolean canDoWrite, int x, int y, ImageObserver obs) {
        if (xform == null) {
            xform = new AffineTransform();
        }
        BufferedImage buffer;
        if ((xform.getType() & AffineTransform.TYPE_GENERAL_ROTATION) != 0
                || (xform.getType() & AffineTransform.TYPE_QUADRANT_ROTATION) != 0
                || (xform.getType() & AffineTransform.TYPE_FLIP) != 0) {
            //System.out.println("Has rotation --------- " + ((xform.getType() & AffineTransform.TYPE_FLIP) != 0));
            Shape r = xform.createTransformedShape(new Rectangle2D.Double(0, 0, img.getWidth(obs), img.getHeight(obs)));
            if (r.getBounds().width > 0 && r.getBounds().height > 0) {
                buffer = getDeviceConfiguration().createCompatibleImage(
                        dimension.width,
                        dimension.height,
                        Transparency.TRANSLUCENT);
                Graphics2D g4 = buffer.createGraphics();
                g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g4.drawImage(img, xform, obs);
                buffer = buffer.getSubimage(r.getBounds().x,
                        r.getBounds().y,
                        r.getBounds().width,
                        r.getBounds().height);
                if (canDoWrite) {
                    // TODO: check if this is OK for all flips
                    if ((xform.getType() & AffineTransform.TYPE_FLIP) != 0) {
                        int tmp = x;
                        x = y;
                        y = tmp;
                    }
                    writeImage(buffer,
                            (int) (r.getBounds().x + x),
                            (int) (r.getBounds().y + y),
                            buffer.getWidth(),
                            buffer.getHeight(),
                            (xform.getType() & AffineTransform.TYPE_FLIP) != 0);
                }
            }
        } else {
            Shape r = xform.createTransformedShape(new Rectangle2D.Double(0, 0, img.getWidth(obs), img.getHeight(obs)));
            if (r.getBounds().width > 0 && r.getBounds().height > 0) {
                buffer = getDeviceConfiguration().createCompatibleImage(
                        r.getBounds().width,
                        r.getBounds().height,
                        Transparency.TRANSLUCENT);
                Graphics2D g4 = buffer.createGraphics();
                g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                AffineTransform af = (AffineTransform) xform.clone();
                af.translate(-r.getBounds().x, -r.getBounds().y);
                g4.drawImage(img, af, obs);
                if (canDoWrite) {
                    writeImage(buffer, (float) xform.getTranslateX() + x,
                            (float) xform.getTranslateY() + y,
                            (float) buffer.getWidth(obs),
                            (float) buffer.getHeight(obs),
                            (xform.getType() & AffineTransform.TYPE_FLIP) != 0);
                }
            }
        }
    }
}
