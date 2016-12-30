 /*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
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
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
package kcl.waterloo.defaults;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kcl.waterloo.graphics.GJAbstractGraph;
import kcl.waterloo.graphics.GJGraph;
import kcl.waterloo.graphics.GJGraphContainer;
import kcl.waterloo.graphics.GJUtilities;
import kcl.waterloo.graphics.data.GJDoubleDataVector;
import kcl.waterloo.graphics.plots2D.GJLine;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.graphics.plots2D.GJScatter;
import kcl.waterloo.gui.PreferencesDialog;
import kcl.waterloo.logging.CommonLogger;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.widget.GJColorComboBox;
import kcl.waterloo.xml.GJEncoder;
import org.jdesktop.swingx.painter.effects.GlowPathEffect;

/**
 * This class provides default values used during construction of Waterloo
 * graphics objects. It is a singleton instance class.
 *
 * <p>
 * <strong>IMPORTANT: Relevant default values are saved in Waterloo XML files
 * and the singleton instance of GJDefaults class is used as the owner of the
 * XML encoder/decoder. The XML persistence delegate for this class ensures that
 * values in the singleton instance are temporarily altered during
 * de-serialization to reflect the map being used when a file was created. Care
 * should be taken not to break the serialization/de-serialization code and to
 * maintain backwards file compatibility when the key set or classes of the
 * values for the key/value pairs is changed. Generally, that would require
 * changes to the code in the relevant constructors and/or changes to the XML
 * code.</p><p>
 * You can safely edit the default settings using the supplied
 * GUIs.</strong></p><p>
 * setMap and getMap methods use defensive copying and do not leak references to
 * the internal hash maps.</p>
 *
 * Note that these default entries will be used by reference so, generally, only
 * immutable objects should be included here.
 *
 * Where exceptions to this rule are made remember that editing the object in
 * one graph will affect all graphs. Mutable objects - such as number formatters
 * - are included where that is likely to be the desired behaviour. To set
 * mutable objects that affect only one Waterloo object, replace the default
 * object with a new instance by calling the appropriate setter method on that
 * instance after constructing the Waterloo object instance.
 *
 * <p>
 * The relevant values are:</p>
 *
 * <p>
 * dataClass - This value is used when a plot data model is created. During
 * serialization, the value is retrieved from the saved data rather than from
 * the defaults table so the default value may be altered during a JVM session.
 * The class must implement the <@code GJDataVectorInterface>.</p>
 *
 * <p>
 * map - contains default values for various settings as a
 * {@code LinkedHashMap}. The entire map is saved during serialization. On
 * de-serialization - values from the map on file are temporarily copied into
 * the default map.</p>
 *
 * <p>
 * map2 - contains default values as a {@code LinkedHashMap} that are not
 * serialized because they are not required or are not serializable or are
 * platform/JVM-version specific and therefore not portable.</p>
 *
 * <p>
 * De-serializing - Atomic boolean flag set true during de-serialization.</p>
 *
 * <p>
 * Getters and setters for these are synchronized on the singleton instance of
 * {@code GJDefaults}. The {@code LinkedHashMap} is copied and returned as a
 * {@code LinkedHashMap} by {@code getMap}. Similarly, {@code setMap} requires a
 * {@code LinkedHashMap} as input and will create a new {@code LinkedHashMap}
 * from it. To replace entries in the current {@code LinkedHashMap}, use
 * {@code copyEntries} which call {@code putAll} on the map.</p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJDefaults {

    /**
     *********************************************************************
     * This sets the GJDataVectorInterface implementation for storing data. A
     * new instance will be generated for each of the x- and y-data using
     * reflection in the {@code GJDataModel} constructor. Data will be saved in
     * this format in generated XML files.
     *
     ********************************************************************
     */
    private static Class dataClass = GJDoubleDataVector.class;
    /**
     * User-editable set of default settings for colors, line patterns etc.
     */
    private static LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(64);
    /**
     * Second map used for non-serializable defaults
     */
    private static final LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>(16);
    /**
     * Atomic boolean flag set true only while deserializing.
     */
    private static final AtomicBoolean Deserializing = new AtomicBoolean(false);
    private static final GlowPathEffect glow = new GlowPathEffect();
    /**
     * Singleton instance for this class
     */
    private static final GJDefaults instance = new GJDefaults();
    
    private static CommonLogger logger = new CommonLogger(GJDefaults.class);

    private GJDefaults() {
        map = getDefaultDefaults();
        setMap2();
        try {
            SecurityManager manager = System.getSecurityManager();
            if (manager != null) {
                manager.checkPermission(new FilePermission(getPreferencesFolder(), "read"));
            }
        } catch (SecurityException ex) {
            logger.warn("Security Exception: System files are not available to read");
            return;
        }
        File f = getDefaultsFile();
        if (f.isFile()) {
            LinkedHashMap<String, Object> filedMap = readPreferencesFile(f, true);
            for (String key : filedMap.keySet()) {
                if (map.containsKey(key) && (filedMap.get(key).getClass().isAssignableFrom(map.get(key).getClass()))) {
                    map.put(key, filedMap.get(key));
                }
            }
        }
    }

    /**
     * Returns the singleton instance. This is used in serialization to alter
     * the state of the dataClass static field.
     *
     * The instance is used as the "owner" of the XML encoder/decoder.
     *
     * @return the singleton instance
     */
    public static GJDefaults getInstance() {
        return instance;
    }

    /**
     * Resets the map values to the pre-programmed defaults.
     *
     */
    private static LinkedHashMap<String, Object> getDefaultDefaults() {
        /**
         * NB Read the documentation above if tempted to change any of this.
         */
        LinkedHashMap<String, Object> localMap = new LinkedHashMap<String, Object>();
        localMap.put("GJBasicPanel.backgroundColor", Color.white);

        localMap.put("GJAbstractGraphContainer.backgroundColor", Color.white);
        localMap.put("GJAbstractGraphContainer.backgroundPainted", Boolean.TRUE);
        // NB Font will default to Dialog if the specified font is not available.
        // SansSerif is a virtual font available on all platforms.
        localMap.put("GJAbstractGraphContainer.font", new Font("SansSerif", Font.BOLD, 10));

        localMap.put("GJAbstractGraph.axisStroke", Float.valueOf(1.5f));
        localMap.put("GJAbstractGraph.minorGridStrokeWeight", Float.valueOf(0.6f));
        localMap.put("GJAbstractGraph.majorGridStrokeWeight", Float.valueOf(1.0f));
        localMap.put("GJAbstractGraph.minorCountXHint", Integer.valueOf(4));
        localMap.put("GJAbstractGraph.minorCountYHint", Integer.valueOf(4));
        localMap.put("GJAbstractGraph.majorGridPainted", Boolean.FALSE);

        Color COLOR = Colors.getColor("CORNFLOWERBLUE");
        localMap.put("GJAbstractGraph.majorGridColor", COLOR);
        localMap.put("GJAbstractGraph.minorGridPainted", Boolean.FALSE);
        localMap.put("GJAbstractGraph.minorGridColor", COLOR);
        localMap.put("GJAbstractGraph.backgroundColor", Color.white);
        localMap.put("GJAbstractGraph.backgroundPainted", Boolean.FALSE);
        localMap.put("GJAbstractGraph.axisColor", Color.black);
        localMap.put("GJAbstractGraph.innerAxisPainted", Boolean.FALSE);
        localMap.put("GJAbstractGraph.innerAxisLabelled", Boolean.FALSE);
        localMap.put("GJAbstractGraph.mouseTextAsInverse", Boolean.TRUE);
        localMap.put("GJAbstractGraph.mainFormatter", NumberFormat.getInstance());
        localMap.put("GJAbstractGraph.secondFormatter", new DecimalFormat("0.##E0"));
        localMap.put("GJAbstractGraph.mousePositionTextFormat", "x= %.3f, y= %.3f ");
        localMap.put("GJAbstractGraph.axesPadding", Double.valueOf(0.05d));
        localMap.put("GJAbstractGraph.tightAxes", Boolean.FALSE);

        // GJGraph defaults are applied in the createInstance method
        // not the constructor. Deserialization via the constructor 
        // honours any user-set values
        localMap.put("GJGraph.leftAxisPainted", Boolean.TRUE);
        localMap.put("GJGraph.rightAxisPainted", Boolean.FALSE);
        localMap.put("GJGraph.topAxisPainted", Boolean.FALSE);
        localMap.put("GJGraph.bottomAxisPainted", Boolean.TRUE);

        localMap.put("GJGraph.leftAxisLabelled", Boolean.TRUE);
        localMap.put("GJGraph.rightAxisLabelled", Boolean.FALSE);
        localMap.put("GJGraph.topAxisLabelled", Boolean.FALSE);
        localMap.put("GJGraph.bottomAxisLabelled", Boolean.TRUE);

        localMap.put("GJAbstractPlot.markerSymbol", "Circle");
        localMap.put("GJAbstractPlot.markerSize", Double.valueOf(5d));
        localMap.put("GJAbstractPlot.edgeColor", Colors.getColors().get("BLACK"));
        localMap.put("GJAbstractPlot.edgeStrokeWidth", Float.valueOf(1.5f));
        localMap.put("GJAbstractPlot.lineStyle", "Solid");
        localMap.put("GJAbstractPlot.lineColor", Colors.getColors().get("BLACK"));
        localMap.put("GJAbstractPlot.lineStrokeWidth", Float.valueOf(1.5f));
        localMap.put("GJAbstractPlot.fill", Color.lightGray);
        localMap.put("GJAbstractPlot.antiAliasing", Boolean.TRUE);

        glow.setBrushColor(Colors.getColor("ORANGERED"));
        localMap.put("GJAbstractPlot.selection", glow);

        localMap.put("GJAnnotation.lineColor", Color.black);
        localMap.put("GJAnnotation.textColor", Color.black);
        localMap.put("GJAnnotation.textBackground", Color.lightGray);
        localMap.put("GJAnnotation.fill", Color.lightGray);

        localMap.put("Clipboard.formatSVG", Boolean.TRUE);
        localMap.put("Clipboard.formatSVGAsText", Boolean.TRUE);
        localMap.put("Clipboard.formatPS", Boolean.FALSE);
        localMap.put("Clipboard.formatEPS", Boolean.FALSE);
        localMap.put("Clipboard.formatPDF", Boolean.TRUE);

        localMap.put("SVG.inline", Boolean.FALSE);
        localMap.put("SVG.jsLocation", "./canvg-min.js");
        localMap.put("SVG.cssLocation", "./WSVGGraphics2D.css");
        localMap.put("SVG.httpd", Boolean.TRUE);
        localMap.put("SVG.canvg", Boolean.TRUE);
        localMap.put("SVG.userHTML", "");

        localMap.put("Processing.jsLocation", "./processing.js");
        localMap.put("Processing.cssLocation", "./PDEGraphics2D.css");
        localMap.put("Processing.httpd", Boolean.TRUE);
        localMap.put("Processing.userHTML", "");

        return localMap;

    }

    public static void setDeserializing(Boolean flag) {
        synchronized (instance) {
            Deserializing.set(flag);
        }
    }

    public static boolean getDeserializing() {
        synchronized (instance) {
            return Deserializing.get();
        }
    }

    private static void setMap2() {
        // These values are not preserved when serializing to XML:
        map2.put("GJAbstractGraphContainer.keyAntialiasing", RenderingHints.VALUE_ANTIALIAS_ON);
        map2.put("GJAbstractGraphContainer.textAntialiasing", RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        map2.put("GJAbstractGraph.keyAntialiasing", map2.get("GJAbstractGraphContainer.keyAntialiasing"));
        map2.put("GJAbstractGraph.textAntialiasing", map2.get("GJAbstractGraphContainer.textAntialiasing"));
        map2.put("GJAbstractVisualModel.keyAntialiasing", map2.get("GJAbstractGraphContainer.keyAntialiasing"));
        map2.put("GJAbstractVisualModel.textAntialiasing", map2.get("GJAbstractGraphContainer.textAntialiasing"));

        map2.put("DeployMode", "svg");//or "pde"
        
        map2.put("DeveloperMode", Integer.valueOf(1));
    }

    /**
     * Provides a synchronized getter for the private static dataClass field
     *
     * @return the dataClass
     */
    public static Class getDataClass() {
        synchronized (instance) {
            return GJDefaults.dataClass;
        }
    }

    /**
     * Provides a synchronized setter for the private static dataClass field
     *
     * @param aDataClass the dataClass to set
     */
    public static void setDataClass(Class aDataClass) {
        synchronized (instance) {
            GJDefaults.dataClass = aDataClass;
        }

    }

    /**
     * Returns a copy of the defaults map.
     *
     * @return a LinkedHashMap<String, Object>
     */
    public static LinkedHashMap<String, Object> getMap() {
        synchronized (instance) {
            return new LinkedHashMap<String, Object>(map);
        }
    }

    /**
     * Replaces the internal map with a shallow copy of the input map.
     *
     * @param input the LinkedHashMap<String, Object> to use.
     */
    public static void setMap(LinkedHashMap<String, Object> input) {
        synchronized (instance) {
            map = new LinkedHashMap<String, Object>(input);
        }
    }

    /**
     * Copies the value fields from the input to the internal map.
     *
     * Key/value pairs present in the input but not the internal map will be
     * duplicated in the internal map.
     *
     * When keys are present in both input and internal maps, the value in the
     * input will replace the value in the internal map if, but only if, the
     * class of the value in the internal map is assignable from the class of
     * the value in the input.
     *
     * @param input a LinkedHashMap<String, Object>
     */
    public static void copyEntries(LinkedHashMap<String, Object> input) {
        synchronized (instance) {
            for (String key : input.keySet()) {
                if (map.containsKey(key) && (input.get(key).getClass().isAssignableFrom(map.get(key).getClass()))) {
                    map.put(key, input.get(key));
                }
            }
        }
    }

    /**
     * Returns a copy of map2.
     *
     * @return copy of map2.
     */
    public static LinkedHashMap<String, Object> getMap2() {
        return new LinkedHashMap<String, Object>(map2);
    }

    public static String getPreferencesFolder() {
        String path = System.getProperty("user.home");
        return path.concat("/waterlooSettings");
    }

    private static boolean createPreferencesFolder(File folder) {
        String s = "<html>Waterloo wants to create folder<br>" + folder.getPath() + "<br>Is that OK?</html>";
        int ok = JOptionPane.showConfirmDialog(null, s, "Create Preferences Folder", JOptionPane.YES_NO_OPTION);
        boolean TF = false;
        if (ok == JOptionPane.OK_OPTION) {
            TF = folder.mkdir();
        }
        if (ok == JOptionPane.OK_OPTION && !TF) {
            JOptionPane.showMessageDialog(null, "Folder creation failed", "Create Preferences Folder", JOptionPane.ERROR_MESSAGE);
        }
        return TF;
    }

    public static void editDefaults() {
        PreferencesDialog dialog = PreferencesDialog.getInstance();
        LocalActionListener.getInstance().setDialog(dialog);
        dialog.getApplyButton().addActionListener(LocalActionListener.getInstance());
        dialog.getCancelButton().addActionListener(LocalActionListener.getInstance());
        dialog.getSaveButton().addActionListener(LocalActionListener.getInstance());
        dialog.getLoadButton().addActionListener(LocalActionListener.getInstance());
        dialog.getResetButton().addActionListener(LocalActionListener.getInstance());
        setupDialog(dialog);
        dialog.setVisible(true);
    }

    private static void setupDialog(PreferencesDialog dialog) {
        GJGraphContainer gr = GJGraphContainer.createInstance(GJGraph.createInstance());
        gr.setAspectRatio(1.0d);
        gr.getView().setAxesBounds(new Rectangle.Double(-0.5, -0.5, 1.0, 1.0));
        GJPlotInterface scatter = GJScatter.createInstance();
        scatter.setXData(new double[]{-0.5, -0.25, 0, 0.25, 0.5});
        scatter.setYData(new double[]{-0.5, -0.25, 0, 0.25, 0.5});
        gr.getView().add(scatter);
        GJPlotInterface line = GJLine.createInstance();
        scatter.add(line);
        gr.getView().autoScale();
        LocalActionListener.getInstance().setPreview(gr);
        dialog.getPreviewPanel().add(gr, BorderLayout.CENTER);
        initDialog(dialog);
        installDialogListeners(dialog);
    }

    private static void initDialog(PreferencesDialog dialog) {

        dialog.getAxesColorCombo().setMap(Colors.getColors());
        dialog.getAxesColorCombo().setSelectedColor((Color) map.get("GJAbstractGraph.axisColor"));

        dialog.getAxisWeightCombo().setSelectedItem(map.get("GJAbstractGraph.axisStroke"));

        dialog.getMajorGridColorCombo().setMap(Colors.getColors());
        dialog.getMajorGridColorCombo().setSelectedColor((Color) map.get("GJAbstractGraph.majorGridColor"));

        dialog.getMajorGridWeigthCombo().setSelectedItem(map.get("GJAbstractGraph.majorGridStrokeWeight"));

        dialog.getMinorGridColorCombo().setMap(Colors.getColors());
        dialog.getMinorGridColorCombo().setSelectedColor((Color) map.get("GJAbstractGraph.minorGridColor"));

        dialog.getMinorGridWeigthCombo().setSelectedItem(map.get("GJAbstractGraph.minorGridStrokeWeight"));

        dialog.getLeftAxisPainted().setSelected(((Boolean) map.get("GJGraph.leftAxisPainted")).booleanValue());
        dialog.getRightAxisPainted().setSelected(((Boolean) map.get("GJGraph.rightAxisPainted")).booleanValue());
        dialog.getTopAxisPainted().setSelected(((Boolean) map.get("GJGraph.topAxisPainted")).booleanValue());
        dialog.getBottomAxisPainted().setSelected(((Boolean) map.get("GJGraph.bottomAxisPainted")).booleanValue());

        dialog.getLeftAxisLabelled().setSelected(((Boolean) map.get("GJGraph.leftAxisLabelled")).booleanValue());
        dialog.getRightAxisLabelled().setSelected(((Boolean) map.get("GJGraph.rightAxisLabelled")).booleanValue());
        dialog.getTopAxisLabelled().setSelected(((Boolean) map.get("GJGraph.topAxisLabelled")).booleanValue());
        dialog.getBottomAxisLabelled().setSelected(((Boolean) map.get("GJGraph.bottomAxisLabelled")).booleanValue());

        dialog.getInnerAxisPainted().setSelected(((Boolean) map.get("GJAbstractGraph.innerAxisPainted")).booleanValue());

        dialog.getMinorGridPainted().setSelected(((Boolean) map.get("GJAbstractGraph.minorGridPainted")).booleanValue());

        dialog.getMajorGridPainted().setSelected(((Boolean) map.get("GJAbstractGraph.majorGridPainted")).booleanValue());

        dialog.getInnerAxisLabelled().setSelected(((Boolean) map.get("GJAbstractGraph.innerAxisLabelled")).booleanValue());

        dialog.getXDivSpinner().setValue(((Integer) map.get("GJAbstractGraph.minorCountXHint")).intValue());
        dialog.getXDivSpinner().addChangeListener(LocalStateChangeListener.getInstance());

        dialog.getYDivSpinner().setValue(((Integer) map.get("GJAbstractGraph.minorCountYHint")).intValue());
        dialog.getYDivSpinner().addChangeListener(LocalStateChangeListener.getInstance());

        //Graph panel
        dialog.getContainerBackground().setMap(Colors.getColors());
        dialog.getContainerBackground().setSelectedColor((Color) map.get("GJAbstractGraphContainer.backgroundColor"));

        dialog.getContainerBackgroundPainted().setSelected(((Boolean) map.get("GJAbstractGraphContainer.backgroundPainted")).booleanValue());

        dialog.getViewBackground().setMap(Colors.getColors());
        dialog.getViewBackground().setSelectedColor((Color) map.get("GJAbstractGraph.backgroundColor"));

        dialog.getViewBackgroundPainted().setSelected(((Boolean) map.get("GJAbstractGraph.backgroundPainted")).booleanValue());

        dialog.getPadAxes().setSelected(((Boolean) map.get("GJAbstractGraph.tightAxes")).booleanValue());

        dialog.getAxisPadding().setSelectedItem(((Double) map.get("GJAbstractGraph.axesPadding")).doubleValue() * 100d);

        //Plots
        dialog.getMarkerSymbol().setSelectedItem(map.get("GJAbstractPlot.markerSymbol"));

        dialog.getMarkerFill().setMap(Colors.getColors());
        dialog.getMarkerFill().setSelectedColor((Color) map.get("GJAbstractPlot.fill"));

        dialog.getMarkerEdgeColor().setMap(Colors.getColors());
        dialog.getMarkerEdgeColor().setSelectedColor((Color) map.get("GJAbstractPlot.edgeColor"));

        dialog.getMarkerSize().setSelectedItem(map.get("GJAbstractPlot.markerSize"));

        dialog.getMarkerEdgeWeight().setSelectedItem(map.get("GJAbstractPlot.edgeStrokeWidth"));

        dialog.getLineWeight().setSelectedItem(map.get("GJAbstractPlot.lineStrokeWidth"));
        dialog.getLineColor().setMap(Colors.getColors());
        dialog.getLineColor().setSelectedColor((Color) map.get("GJAbstractPlot.lineColor"));
        Color color = ((GlowPathEffect) map.get("GJAbstractPlot.selection")).getBrushColor();
        dialog.getHighlightColor().setMap(Colors.getColors());
        dialog.getHighlightColor().setSelectedColor(color);

        dialog.getAntialiased().setSelected(((Boolean) map.get("GJAbstractPlot.antiAliasing")).booleanValue());

        dialog.getFormatEPS().setSelected(((Boolean) map.get("Clipboard.formatEPS")).booleanValue());
        dialog.getFormatPS().setSelected(((Boolean) map.get("Clipboard.formatPS")).booleanValue());
        dialog.getFormatSVG().setSelected(((Boolean) map.get("Clipboard.formatSVG")).booleanValue());
        dialog.getFormatSVGAsText().setSelected(((Boolean) map.get("Clipboard.formatSVGAsText")).booleanValue());
        dialog.getFormatPDF().setSelected(((Boolean) map.get("Clipboard.formatPDF")).booleanValue());

        dialog.getSvgCSSLoc().setSelectedItem(map.get("SVG.cssLocation"));
        dialog.getSvgJSLoc().setSelectedItem(map.get("SVG.jsLocation"));
        dialog.getSvgHTTPD().setSelected(((Boolean) map.get("SVG.httpd")).booleanValue());
        dialog.getInline().setSelected(((Boolean) map.get("SVG.inline")).booleanValue());
        dialog.getCanvg().setSelected(((Boolean) map.get("SVG.canvg")).booleanValue());

        dialog.getCssLocation().setSelectedItem(map.get("Processing.cssLocation"));
        dialog.getJavaScriptLocation().setSelectedItem(map.get("Processing.jsLocation"));
        dialog.getHttpd().setSelected(((Boolean) map.get("Processing.httpd")).booleanValue());
    }

    private static void installDialogListeners(PreferencesDialog dialog) {
        ActionListener listener = LocalActionListener.getInstance();
        dialog.getAxesColorCombo().addActionListener(listener);
        dialog.getAxisWeightCombo().addActionListener(listener);
        dialog.getMajorGridColorCombo().addActionListener(listener);
        dialog.getMajorGridWeigthCombo().addActionListener(listener);
        dialog.getMinorGridColorCombo().addActionListener(listener);
        dialog.getMinorGridWeigthCombo().addActionListener(listener);
        dialog.getLeftAxisPainted().addActionListener(listener);
        dialog.getRightAxisPainted().addActionListener(listener);
        dialog.getTopAxisPainted().addActionListener(listener);
        dialog.getBottomAxisPainted().addActionListener(listener);
        dialog.getLeftAxisLabelled().addActionListener(listener);
        dialog.getRightAxisLabelled().addActionListener(listener);
        dialog.getTopAxisLabelled().addActionListener(listener);
        dialog.getBottomAxisLabelled().addActionListener(listener);
        dialog.getInnerAxisPainted().addActionListener(listener);
        dialog.getMinorGridPainted().addActionListener(listener);
        dialog.getMajorGridPainted().addActionListener(listener);
        dialog.getInnerAxisLabelled().addActionListener(listener);
        dialog.getContainerBackground().addActionListener(listener);
        dialog.getContainerBackgroundPainted().addActionListener(listener);
        dialog.getViewBackground().addActionListener(listener);
        dialog.getViewBackgroundPainted().addActionListener(listener);
        dialog.getPadAxes().addActionListener(listener);
        dialog.getAxisPadding().addActionListener(listener);

        dialog.getMarkerSymbol().addActionListener(listener);
        dialog.getMarkerSize().addActionListener(listener);
        dialog.getMarkerFill().addActionListener(listener);
        dialog.getMarkerEdgeColor().addActionListener(listener);
        dialog.getMarkerEdgeWeight().addActionListener(listener);
        dialog.getHighlightColor().addActionListener(listener);

        dialog.getLineColor().addActionListener(listener);
        dialog.getLineWeight().addActionListener(listener);
        dialog.getLineStyle().addActionListener(listener);

        dialog.getAntialiased().addActionListener(listener);

        dialog.getFormatEPS().addActionListener(listener);
        dialog.getFormatPS().addActionListener(listener);
        dialog.getFormatSVG().addActionListener(listener);
        dialog.getFormatSVGAsText().addActionListener(listener);
        dialog.getFormatPDF().addActionListener(listener);

        dialog.getSvgCSSLoc().addActionListener(listener);
        dialog.getSvgHTTPD().addActionListener(listener);
        dialog.getSvgJSLoc().addActionListener(listener);
        dialog.getInline().addActionListener(listener);

        dialog.getCssLocation().addActionListener(listener);
        dialog.getJavaScriptLocation().addActionListener(listener);
        dialog.getHttpd().addActionListener(listener);

        dialog.getXDivSpinner().addChangeListener(LocalStateChangeListener.getInstance());
        dialog.getYDivSpinner().addChangeListener(LocalStateChangeListener.getInstance());
    }

    private static File getDefaultsFile() {
        String s = getPreferencesFolder();
        File folder = new File(s);
        if (!folder.isDirectory()) {
            createPreferencesFolder(folder);
        }
        File newFile = new File(s.concat("/waterloo-preferences.xml"));

        if (!newFile.canWrite()) {
            newFile.setWritable(true);
        }

        return newFile;

    }

    private static void saveMap(File file, LinkedHashMap<String, Object> localMap) {
        int ok;
        if (file.isFile()) {
            ok = JOptionPane.showConfirmDialog(null, "Overwrite existing file", "Create Preferences File", JOptionPane.YES_NO_OPTION);
            file.renameTo(new File(file.getPath().concat(".backup")));
        } else {
            ok = JOptionPane.showConfirmDialog(null, "<html>Create preferences file<br>" + file.getPath() + "</html>", "Create Preferences File", JOptionPane.YES_NO_OPTION);
        }
        FileOutputStream buffer;
        if (ok == JOptionPane.OK_OPTION) {
            try {
                buffer = new java.io.FileOutputStream(file);
            } catch (FileNotFoundException ex) {
                logger.error(ex.getMessage());
                return;

            }
            Thread.currentThread().setContextClassLoader(GJDefaults.class
                    .getClassLoader());
            final XMLEncoder e = new XMLEncoder(buffer);

            GJEncoder.addDelegates(e);

            e.writeObject(localMap);

            e.close();

            try {
                buffer.close();
            } catch (IOException ex) {
                logger.error("Buffer closure failed");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static LinkedHashMap<String, Object> readPreferencesFile(File file, boolean silent) {
        int ok;
        if (!silent) {
            if (file.isFile()) {
                ok = JOptionPane.showConfirmDialog(null, "Load existing file", "Load Preferences File", JOptionPane.YES_NO_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "File does not exist", "Load Preferences File", JOptionPane.INFORMATION_MESSAGE);
                return getMap();
            }
        } else {
            ok = JOptionPane.OK_OPTION;
        }
        if (ok == JOptionPane.OK_OPTION) {
            FileInputStream buffer;
            try {
                buffer = new java.io.FileInputStream(file);
            } catch (FileNotFoundException ex) {
                logger.error(ex.getMessage());
                return null;

            }
            Thread.currentThread().setContextClassLoader(GJDefaults.class
                    .getClassLoader());
            final XMLDecoder e = new XMLDecoder(buffer);
            LinkedHashMap<String, Object> localMap = (LinkedHashMap<String, Object>) e.readObject();

            e.close();

            try {
                buffer.close();
            } catch (IOException ex) {
                logger.error("Buffer closure failed");
                return null;
            }
            return localMap;
        } else {
            return getMap();

        }
    }

    private static class LocalStateChangeListener implements ChangeListener {

        private static final LocalStateChangeListener instance = new LocalStateChangeListener();

        static LocalStateChangeListener getInstance() {
            return instance;
        }

        @Override
        public void stateChanged(ChangeEvent ce) {
            String command = ((Component) ce.getSource()).getName();
            if (command.equals("$XDivSpinner")) {
                JSpinner thisComponent = (JSpinner) ce.getSource();
                LocalActionListener.instance.localMap.put("GJAbstractGraph.minorCountXHint", thisComponent.getValue());
                LocalActionListener.instance.preview.getView().setMinorCountXHint(((Integer) thisComponent.getValue()).intValue());
            } else if (command.equals("$YDivSpinner")) {
                JSpinner thisComponent = (JSpinner) ce.getSource();
                LocalActionListener.instance.localMap.put("GJAbstractGraph.minorCountYHint", thisComponent.getValue());
                LocalActionListener.instance.preview.getView().setMinorCountYHint(((Integer) thisComponent.getValue()).intValue());
            }
        }
    }

    private static class LocalActionListener implements ActionListener {

        private static LocalActionListener instance = new LocalActionListener();
        private GJGraphContainer preview;
        private LinkedHashMap<String, Object> localMap = GJDefaults.getMap();//Clone
        private PreferencesDialog dialog;

        static LocalActionListener getInstance() {
            return instance;
        }

        private void setPreview(GJGraphContainer preview) {
            this.preview = preview;
        }

        private void setDialog(PreferencesDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String command = ae.getActionCommand();
            if (command.equals("leftAxisPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.leftAxisPainted", Boolean.valueOf(thisComponent.isSelected()));
                localMap.put("GJGraph.leftAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getLeftAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setLeftAxisPainted(thisComponent.isSelected());
                preview.getView().setLeftAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("rightAxisPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.rightAxisPainted", Boolean.valueOf(thisComponent.isSelected()));
                localMap.put("GJGraph.rightAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getRightAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setRightAxisPainted(thisComponent.isSelected());
                preview.getView().setRightAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("topAxisPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.topAxisPainted", Boolean.valueOf(thisComponent.isSelected()));
                localMap.put("GJGraph.topAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getTopAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setTopAxisPainted(thisComponent.isSelected());
                preview.getView().setTopAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("bottomAxisPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.bottomAxisPainted", Boolean.valueOf(thisComponent.isSelected()));
                localMap.put("GJGraph.bottomAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getBottomAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setBottomAxisPainted(thisComponent.isSelected());
                preview.getView().setBottomAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("leftAxisLabelled")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.leftAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getLeftAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setLeftAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("rightAxisLabelled")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.rightAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getRightAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setRightAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("topAxisLabelled")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.topAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getTopAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setTopAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("bottomAxisLabelled")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJGraph.bottomAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getBottomAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setBottomAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("axisColor")) {
                GJColorComboBox thisComponent = (GJColorComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.axisColor", thisComponent.getSelectedColor());
                preview.getView().setAxisColor(thisComponent.getSelectedColor());
            } else if (command.equals("axisWeight")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.axisStroke", thisComponent.getSelectedItem());
                preview.getView().setAxisStrokeWeight(((Float) thisComponent.getSelectedItem()).floatValue());
            } else if (command.equals("majorGridColor")) {
                GJColorComboBox thisComponent = (GJColorComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.majorGridColor", thisComponent.getSelectedColor());
                preview.getView().setMajorGridColor(thisComponent.getSelectedColor());
            } else if (command.equals("minorGridColor")) {
                GJColorComboBox thisComponent = (GJColorComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.minorGridColor", thisComponent.getSelectedColor());
                preview.getView().setMinorGridColor(thisComponent.getSelectedColor());
            } else if (command.equals("minorGridStrokeWeight")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.minorGridStrokeWeight", thisComponent.getSelectedItem());
                preview.getView().setMinorGridStrokeWeight((Float) thisComponent.getSelectedItem());
            } else if (command.equals("majorGridStrokeWeight")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.majorGridStrokeWeight", thisComponent.getSelectedItem());
                preview.getView().setMajorGridStrokeWeight((Float) thisComponent.getSelectedItem());
            } else if (command.equals("innerAxisPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraph.innerAxisPainted", Boolean.valueOf(thisComponent.isSelected()));
                preview.getView().setInnerAxisPainted(thisComponent.isSelected());
                dialog.getInnerAxisLabelled().setSelected(thisComponent.isSelected());
                preview.getView().setInnerAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("innerAxisLabelled")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraph.innerAxisLabelled", Boolean.valueOf(thisComponent.isSelected()));
                preview.getView().setInnerAxisLabelled(thisComponent.isSelected());
            } else if (command.equals("majorGridPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraph.majorGridPainted", Boolean.valueOf(thisComponent.isSelected()));
                //localMap.put("GJAbstractGraph.minorGridPainted", Boolean.valueOf(thisComponent.isSelected()));                             
                preview.getView().setMajorGridPainted(thisComponent.isSelected());
                //dialog.getMinorGridPainted().setSelected(thisComponent.isSelected());
                //preview.getView().setMinorGridPainted(thisComponent.isSelected());
            } else if (command.equals("minorGridPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraph.minorGridPainted", Boolean.valueOf(thisComponent.isSelected()));
                preview.getView().setMinorGridPainted(thisComponent.isSelected());
            } else if (command.equals("containerBackground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) ae.getSource();
                localMap.put("GJAbstractGraphContainer.backgroundColor", thisComponent.getSelectedColor());
                preview.setBackground(thisComponent.getSelectedColor());
            } else if (command.equals("containerBackgroundPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraphContainer.containerBackgroundPainted", Boolean.valueOf(thisComponent.isSelected()));
                preview.setBackgroundPainted(thisComponent.isSelected());
            } else if (command.equals("viewBackground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.backgroundColor", thisComponent.getSelectedColor());
                ((GJAbstractGraph) preview.getView()).setBackground(thisComponent.getSelectedColor());
            } else if (command.equals("viewBackgroundPainted")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("GJAbstractGraph.viewBackgroundPainted", Boolean.valueOf(thisComponent.isSelected()));
                preview.getView().setBackgroundPainted(thisComponent.isSelected());
            } else if (command.equals("padAxes")) {
                JToggleButton thisComponent = (JToggleButton) ae.getSource();
                localMap.put("GJAbstractGraph.tightAxes", !Boolean.valueOf(thisComponent.isSelected()));
                ((GJAbstractGraph) preview.getView()).setTightAxes(!thisComponent.isSelected());
                preview.getView().autoScale();
            } else if (command.equals("axesPadding")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("GJAbstractGraph.axesPadding", ((Double) thisComponent.getSelectedItem()).doubleValue() / 100d);
                ((GJAbstractGraph) preview.getView()).setAxesPadding(((Double) thisComponent.getSelectedItem()).doubleValue() / 100d);
                preview.getView().autoScale();
            } else if (command.equals("markerSymbol") || command.equals("markerSize")
                    || command.equals("markerEdgeWeight") || command.equals("markerEdgeColor")
                    || command.equals("markerFill")) {
                JComboBox symbolCombo = (JComboBox) dialog.getMarkerSymbol();
                String symbol = (String) symbolCombo.getSelectedItem();
                JComboBox sizeCombo = (JComboBox) dialog.getMarkerSize();
                double markerWidth = ((Double) sizeCombo.getSelectedItem()).doubleValue();
                if (symbol.equals("Dot")) {
                    markerWidth = 0.5;
                }
                JComboBox edgeCombo = (JComboBox) dialog.getMarkerEdgeWeight();
                float edgeWeight = ((Float) edgeCombo.getSelectedItem()).floatValue();
                GJColorComboBox edgeColorCombo = dialog.getMarkerEdgeColor();
                Color edgeColor = edgeColorCombo.getSelectedColor();
                GJColorComboBox markerFillCombo = dialog.getMarkerFill();
                Color fill = markerFillCombo.getSelectedColor();

                if (command.equals("markerSymbol")) {
                    localMap.put("GJAbstractPlot.markerSymbol", symbolCombo.getSelectedItem());
                    //symbolCombo.setSelectedItem(symbol);
                    preview.getView().getPlots().get(0).setMarker(0, GJMarker.getMarker(symbol, markerWidth));
                } else if (command.equals("markerEdgeWeight")) {
                    localMap.put("GJAbstractPlot.edgeStrokeWidth", Float.valueOf(edgeWeight));
                    preview.getView().getPlots().get(0).setEdgeStroke(new BasicStroke(edgeWeight));
                    //edgeCombo.setSelectedItem(edgeWeight);
                } else if (command.equals("markerSize")) {
                    localMap.put("GJAbstractPlot.markerSize", Double.valueOf(markerWidth));
                    preview.getView().getPlots().get(0).setMarker(0, GJMarker.getMarker(symbol, markerWidth));
                } else if (command.equals("markerFill")) {
                    localMap.put("GJAbstractPlot.fill", fill);
                    //markerFillCombo.setSelectedColor(fill);
                    preview.getView().getPlots().get(0).setFill(fill);
                } else if (command.equals("markerEdgeColor")) {
                    localMap.put("GJAbstractPlot.edgeColor", edgeColor);
                    preview.getView().getPlots().get(0).setEdgeColor(edgeColor);
                    //edgeColorCombo.setSelectedColor(edgeColor);
                }
                preview.getView().autoScale();
            } else if (command.equals("highlightColor")) {
                Color highlightColor = dialog.getHighlightColor().getSelectedColor();
                ((GlowPathEffect) localMap.get("GJAbstractPlot.selection")).setBrushColor(highlightColor);
            } else if (command.equals("lineColor") || command.equals("lineWeight")
                    || command.equals("lineStyle")) {
                GJColorComboBox colorCombo = dialog.getLineColor();
                Color lineColor = colorCombo.getSelectedColor();
                JComboBox lineWeightCombo = dialog.getLineWeight();
                float weight = ((Float) lineWeightCombo.getSelectedItem()).floatValue();
                JComboBox lineStyleCombo = dialog.getLineStyle();
                String lineStyle = (String) lineStyleCombo.getSelectedItem();
                if (command.equals("lineColor")) {
                    localMap.put("GJAbstractPlot.lineColor", lineColor);
                    preview.getView().getPlots().get(0).getNode().get(1).setLineColor(lineColor);
                } else if (command.equals("lineWeight")) {
                    localMap.put("GJAbstractPlot.lineStrokeWidth", weight);
                    preview.getView().getPlots().get(0).getNode().get(1).setLineStroke(GJUtilities.makeStroke(weight, lineStyle));
                } else if (command.equals("lineStyle")) {
                    localMap.put("GJAbstractPlot.lineStyle", lineStyle);
                    preview.getView().getPlots().get(0).getNode().get(1).setLineStroke(GJUtilities.makeStroke(weight, lineStyle));
                }
            } else if (command.equals("antiAliased")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                if (thisComponent.isSelected()) {
                    preview.getView().getPlots().get(0).getNode().get(1).setAntialiasing(true);
                    preview.getView().getPlots().get(0).setAntialiasing(true);
                    localMap.put("GJAbstractPlot.antiAliasing", Boolean.TRUE);
                } else {
                    preview.getView().getPlots().get(0).getNode().get(1).setAntialiasing(false);
                    preview.getView().getPlots().get(0).setAntialiasing(false);
                    localMap.put("GJAbstractPlot.antiAliasing", Boolean.FALSE);
                }
            } else if (command.equals("formatSVG")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Clipboard.formatSVG", Boolean.valueOf(thisComponent.isSelected()));
//                dialog.getFormatEPS().setSelected(thisComponent.isSelected());
//                dialog.getFormatPS().setSelected(thisComponent.isSelected());
                dialog.getFormatPDF().setSelected(thisComponent.isSelected());
                localMap.put("Clipboard.formatPDF", Boolean.valueOf(thisComponent.isSelected()));
                dialog.getFormatSVGAsText().setSelected(thisComponent.isSelected());
                localMap.put("Clipboard.formatSVGAsText", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("formatSVGAsText")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Clipboard.formatSVGAsText", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("formatEPS")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Clipboard.formatEPS", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("formatPS")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Clipboard.formatPS", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("formatPDF")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Clipboard.formatPDF", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("httpd")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("Processing.httpd", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("cssLocation")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("Processing.cssLocation", thisComponent.getSelectedItem());
            } else if (command.equals("javaScriptLocation")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("Processing.jsLocation", thisComponent.getSelectedItem());
            } else if (command.equals("svgInline")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("SVG.inline", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("canvg")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("SVG.canvg", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("svgHTTPD")) {
                JCheckBox thisComponent = (JCheckBox) ae.getSource();
                localMap.put("SVG.httpd", Boolean.valueOf(thisComponent.isSelected()));
            } else if (command.equals("svgCSSLoc")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("SVG.cssLocation", thisComponent.getSelectedItem());
            } else if (command.equals("canvgLoc")) {
                JComboBox thisComponent = (JComboBox) ae.getSource();
                localMap.put("SVG.jsLocation", thisComponent.getSelectedItem());
            } else if (command.equals("Apply")) {
                GJDefaults.copyEntries(localMap);
            } else if (command.equals("Save")) {
                GJDefaults.copyEntries(localMap);
                GJDefaults.saveMap(GJDefaults.getDefaultsFile(), map);
            } else if (command.equals("Cancel")) {
                dialog.dispose();
            } else if (command.equals("Reset")) {
                map = GJDefaults.getDefaultDefaults();
                GJDefaults.initDialog(dialog);
            } else if (command.equals("Load")) {
                GJDefaults.copyEntries(GJDefaults.readPreferencesFile(GJDefaults.getDefaultsFile(), false));
                GJDefaults.initDialog(dialog);
            }

            preview.revalidate();
            preview.repaint();
        }
    }
}
