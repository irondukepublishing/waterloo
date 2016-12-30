/**
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * Project Waterloo is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.xml;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import java.beans.*;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.swing.SwingWorker;
import kcl.waterloo.actions.GJEventManager;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.graphics.AxisLink;
import kcl.waterloo.graphics.GJAbstractGraph;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.graphics.GJRoi;
import kcl.waterloo.graphics.data.GJXYSeries;
import kcl.waterloo.graphics.plots2D.GJAbstractPlot;
import kcl.waterloo.widget.GJButton;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.graphics.transforms.Services;
import kcl.waterloo.logging.CommonLogger;
import kcl.waterloo.plotmodel2D.GJCyclicArrayList;
import kcl.waterloo.swing.GCFrame;
import kcl.waterloo.swing.GCGridContainer;
import kcl.waterloo.swing.GCTabbedGridContainer;

/**
 * A static library used to serialize all Waterloo graphics to an XML file.
 *
 * Uses the standard java.bean.util.XMLEncoder.
 *
 * Note:
 *
 * The singleton instance of GJDefaults is set as the owner of the XMLEncoder.
 * The defaults are written to each XML file together with the appropriate
 * setting for the data vector class used by plots.
 *
 * The matching XML decoder routine needs to save and restore the default
 * settings when de-serializing the generated XML file (the supplied GJDecoder
 * does this).
 *
 * Any changes here need to be tested for backwards compatibility.
 *
 * The GJDefaults also set the verbosity of exception logging: 0 (quiet) to 2
 * (verbose). Exceptions are also logged to the file (verbosely) Exceptions are
 * expected: e.g. when non-serializable listeners are used. They do not
 * (generally) represent errors.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJEncoder {

    /**
     * Data written to XML files as a header
     */
    static final class Header {

        static final String attribution = "Waterloo Scientific Graphics: Copyright King's College London 2012-";
        static final String versionString = "kcl.waterloo.xml.GJEncoder/GJDecoder version:";
        static final Long majorVersion = 1L;
        static final Long minorVersion = 0L;
    }
    /**
     * Stores exceptions thrown during the last call to the {@code save}
     * function as an ArrayList.
     */
    static final ArrayList<String> exceptionLog = new ArrayList<String>();

    public static final LinkedHashMap<Class, PersistenceDelegate> customDelegates = new LinkedHashMap<Class, PersistenceDelegate>();

    private static final CommonLogger logger = new CommonLogger(GJEncoder.class);

    /**
     * Enumerated type for supported compression modes. Presently none or gzip.
     */
    public enum CompressionMode {

        none, gzip
    }

    /**
     * The current compression mode.
     */
    private static CompressionMode compression = CompressionMode.gzip;

    /**
     * Non-instantiable class providing a static library.
     */
    private GJEncoder() {
    }

    /**
     * Static method for saving Waterloo graphics objects. Presently supported
     * objects on input are GCFrames, GCPanels, graph containers and graphs.
     *
     * @param fileName fully qualified file name as a String. Compression will
     * be used if the file extension is ".gz". Otherwise, compression will be
     * applied if it has been selected as the default by a call to
     * setCompression.
     * @param comp the component to save
     */
    public static void save(final String fileName, final Object comp) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                saveOnThread(fileName, comp);
                //XMLChecker.check(fileNameCopy);
                return null;
            }
        };
        worker.execute();
    }

    public static void saveOnEDT(final String fileName, final Object comp) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                saveOnThread(fileName, comp);
            }

        });
    }

    public static void saveOnThread(final String fileName, final Object comp) {
        final String fileNameCopy = setExtension(fileName);
        if (comp instanceof GCFrame) {
            GCFrame f = (GCFrame) comp;
            if (f.getContentPane() instanceof GCGridContainer) {
                gcPanelEncoder(fileNameCopy, f.getContentPane());
            } else if (f.getContentPane() instanceof GCTabbedGridContainer) {
                gcPanelEncoder(fileNameCopy, f.getContentPane());
            } else {
                gcPanelEncoder(fileNameCopy, f.getContentPane());
            }

        } else if (comp instanceof GCGridContainer) {
            gcPanelEncoder(fileNameCopy, (GCGridContainer) comp);
        } else if (comp instanceof GCTabbedGridContainer) {
            gcPanelEncoder(fileNameCopy, (GCTabbedGridContainer) comp);
        } else if (comp instanceof GJAbstractGraphContainer) {
            containerEncoder(fileNameCopy, (GJAbstractGraphContainer) comp);
        } else if (comp instanceof GJAbstractGraph) {
            graphEncoder(fileNameCopy, (GJAbstractGraph) comp);
        } else if (comp instanceof GJAbstractPlot) {
            // Added at 1.1RC2 - support saving plots with no Swing hierarchy
            GJAbstractPlot p = (GJAbstractPlot) comp;
            final OutputStream out = createOutputStream(fileName);
            final XMLEncoder e = new XMLEncoder(out);
            logger.debug("Writing to ".concat(fileName));
            writeHeader(e);
            GJGraphInterface gr = p.getParentGraph();
            p.setParentGraph(null);
            e.writeObject(p);
            p.setParentGraph(gr);
            doClose(e);
            try {
                out.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }

        Integer debugLevel = (Integer) GJDefaults.getMap2().get("DeveloperMode");
        if (debugLevel.intValue() > 0) {
            int sz = exceptionLog.size();
            if (sz > 0) {
                logger.warn("Debug Info: file log size = " + exceptionLog.size());
            }
        }
    }

    /**
     * Creates an OutputStream for use by the encoder.
     *
     * @param fileName the name of the file
     * @return the OutputStream
     */
    private static OutputStream createOutputStream(String fileName) {
        BufferedOutputStream buffer;
        fileName = setExtension(fileName);
        try {
            buffer = new BufferedOutputStream(new java.io.FileOutputStream(fileName));
            if (fileName.contains(".gz")) {
                return new GZIPOutputStream(buffer);
            } else {
                return buffer;
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    private static void doClose(XMLEncoder e) {
        e.writeObject(exceptionLog);
        e.close();
    }

    /**
     * Sets the compression mode to be used by all output streams subsequently
     * created using createOutputStream.
     *
     * @param mode the CompressionMode
     */
    public static void setCompressionMode(CompressionMode mode) {
        compression = mode;
    }

    /**
     * Returns the current compression mode.
     *
     * @return the CompressionMode
     */
    public static CompressionMode getCompressionMode() {
        return compression;
    }

    /**
     * Sets the compression mode to be used by all output streams subsequently
     * created using createOutputStream. If set true, gzip compression mode will
     * be used. If set false, no compression will be used.
     *
     * @param flag true to activate compression
     */
    public static void setCompression(boolean flag) {
        if (flag) {
            compression = CompressionMode.gzip;
        } else {
            compression = CompressionMode.none;
        }
    }

    /**
     * Returns true if the compression mode is anything other than
     * {@code CompressionMode.none}
     *
     * @return the flag
     */
    public static boolean getCompression() {
        switch (compression) {
            case none:
                return false;
            default:
                return true;
        }
    }

    /**
     * Initializes an XMLEncoder by installing the required persistence
     * delegates, initializes the XMLEncoder's owner to the GJDefaults singleton
     * instance, writes the header to the XML file, and saves the present
     * default settings in preparation for later deserialization. Used
     * internally but can be called if creating a file long-hand.
     *
     * Note that users can supplement/replace the persistence delegates on the
     * XMLEncoder instance after calling this method.
     *
     * @param e the XMLEncoder instance
     */
    private static void writeHeader(XMLEncoder e) {
        Thread.currentThread().setContextClassLoader(GJEncoder.class.getClassLoader());
        exceptionLog.clear();
        addDelegates(e);
        e.setOwner(GJDefaults.getInstance());
        e.setExceptionListener(exHandler);
        e.writeObject(Header.attribution);
        e.writeObject(Header.versionString);
        e.writeObject(Header.majorVersion);
        e.writeObject(Header.minorVersion);
        e.writeObject("Created: " + new java.util.Date());
        e.writeObject(kcl.waterloo.util.Version.getVersion().toString());
        e.writeObject(e.getOwner());
    }

    public static void setContext(XMLEncoder e) {
        Thread.currentThread().setContextClassLoader(GJEncoder.class.getClassLoader());
    }

    private static void gcPanelEncoder(String fileName, final Container comp) {
        final OutputStream out = createOutputStream(fileName);
        final XMLEncoder e = new XMLEncoder(out);
        writeHeader(e);
        e.writeObject(createComponentHeader(comp));
        e.writeObject(comp);
        doClose(e);
        try {
            out.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    private static void containerEncoder(String fileName, final GJAbstractGraphContainer comp) {
        final OutputStream out = createOutputStream(fileName);
        final XMLEncoder e = new XMLEncoder(out);
        writeHeader(e);
        containerEncoder(e, comp);
        doClose(e);
        try {
            out.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    private static void containerEncoder(XMLEncoder e, GJAbstractGraphContainer comp) {
        e.writeObject(createComponentHeader(comp));
        localWrite(e, comp);
    }

    private static void graphEncoder(String fileName, GJAbstractGraph graph) {
        final OutputStream out = createOutputStream(fileName);
        final XMLEncoder e = new XMLEncoder(out);
        writeHeader(e);
        graphEncoder(e, graph);
        doClose(e);
        try {
            out.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    private static void graphEncoder(XMLEncoder e, GJGraphInterface graph) {
        e.writeObject(createComponentHeader((Component) graph));
        localWrite(e, graph);
    }

    private static void localWrite(XMLEncoder e, Object o) {
        e.writeObject(o);
    }

    private static LinkedHashMap<String, Object> createComponentHeader(Component c) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Dimension", c.getSize());
        return map;
    }

    public static ArrayList<String> getExceptionLog() {
        return exceptionLog;
    }

    /**
     * Static method that adds the required persistence delegates to an
     * XMLEncoder instance.
     *
     * @param e the XMLEncoder
     */
    public static void addDelegates(XMLEncoder e) {

        // Waterloo delegates
        e.setPersistenceDelegate(GJCyclicArrayList.class, e.getPersistenceDelegate(List.class));

        e.setPersistenceDelegate(GradientPaint.class, new DefaultPersistenceDelegate(
                new String[]{"point1", "color1", "point2", "color2"}));

        e.setPersistenceDelegate(GJXYSeries.class, new DefaultPersistenceDelegate(
                new String[]{"x", "y"}));

        LinkedHashMap<String, GJDataTransformInterface> trs = Services.getAvailable();
        GJDataTransformInterface[] arr = new GJDataTransformInterface[trs.size()];
        arr = trs.values().toArray(arr);
        for (GJDataTransformInterface arr1 : arr) {
            e.setPersistenceDelegate(arr1.getClass(), transformInterfaceDelegate);
        }

        e.setPersistenceDelegate(GJRoi.class, new DefaultPersistenceDelegate(
                new String[]{"axesRectangle"}));

        e.setPersistenceDelegate(GJButton.class, new DefaultPersistenceDelegate(
                new String[]{"fileName"}));

        // AWT delegates
        e.setPersistenceDelegate(Rectangle2D.Double.class, new DefaultPersistenceDelegate(
                new String[]{"x", "y", "width", "height"}));

        e.setPersistenceDelegate(BasicStroke.class, new DefaultPersistenceDelegate() {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BasicStroke old = (BasicStroke) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(), "new",
                        new Object[]{old.getLineWidth(),
                            old.getEndCap(),
                            old.getLineJoin(),
                            old.getMiterLimit(),
                            old.getDashArray(),
                            old.getDashPhase()
                        });
            }
        });

        e.setPersistenceDelegate(GJEventManager.class, new DefaultPersistenceDelegate() {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, GJEventManager.class, "getInstance", null);
            }
        });

        e.setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BigDecimal bd = (BigDecimal) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(),
                        "new",
                        new Object[]{bd.toString()});
            }
        });

        e.setPersistenceDelegate(AxisLink.class, new DefaultPersistenceDelegate() {
            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                AxisLink bd = (AxisLink) oldInstance;
                return new Expression(oldInstance, oldInstance.getClass(),
                        "new",
                        new Object[]{bd.getPairedTarget(), bd.getPairing()});
            }
        });

        e.setPersistenceDelegate(GJDefaults.class, new DefaultPersistenceDelegate() {
            /**
             * IMPORTANT: This causes the defaults to be changed to match those
             * on file during de-serialization. The decoder must therefore save
             * then restore the user defaults after reading a file.
             *
             * CAUTION: Changing this could break backwards compatibility.
             */
            @Override
            protected void initialize(Class type, Object oldInstance, Object newInstance, Encoder out) {
                Object o = ((XMLEncoder) out).getOwner();
                Object[] arr = new Object[]{GJDefaults.getMap()};
                // The decoder will invoke copyEntries on the singleton instance of GJDefaults 
                // during de-serialization
                out.writeStatement(new Statement(o, "copyEntries", arr));
            }
        });

        for (Class clzz : customDelegates.keySet()) {
            e.setPersistenceDelegate(clzz, customDelegates.get(clzz));
        }
    }

    private static String setExtension(String fileName) {
        boolean requestFileCompress;
        if (fileName.contains(".kclf") && !fileName.contains(".gz")) {
            requestFileCompress = false;
        } else if (fileName.contains(".gz")) {
            requestFileCompress = true;
        } else {
            requestFileCompress = getCompression();
        }
        fileName = fileName.replaceAll(".gz", "");
        int dotPos = fileName.lastIndexOf(".kclf");
        if (dotPos < 0) {
            fileName = fileName.concat(".kclf");
        }
        if (requestFileCompress) {
            dotPos = fileName.lastIndexOf(".gz");
            if (dotPos < 0) {
                fileName = fileName.concat(".gz");
            }
        }
        return fileName;
    }
    /**
     * Exception log. Note that exceptions can be expected if non-serializable
     * components have been added by users to graphs. This will not always
     * represent a bug.
     */
    private static ExceptionListener exHandler = new ExceptionListener() {
        @Override
        public void exceptionThrown(Exception excptn) {
            String message = excptn.getMessage();

            if (message.contains("Listener") || message.contains("AncestorNotifier")) {
                exceptionLog.add(excptn.getCause().toString() + " " + message);
            }

            Integer debugLevel = (Integer) GJDefaults.getMap2().get("DeveloperMode");
            switch (debugLevel.intValue()) {
                case 0:
                    break;
                case 1:
                    if (!(message.contains("Listener") || message.contains("AncestorNotifier")
                            || message.contains("GraphMouseHandler") || message.contains("GraphCycler"))) {
                        logger.error(excptn.getMessage());
                    }
                    break;
                case 2:
                    logger.error(excptn.getMessage());
                    break;
            }
        }
    };

    /**
     * Delegate for all data transforms
     */
    private static final PersistenceDelegate transformInterfaceDelegate = new PersistenceDelegate() {
        @Override
        protected Expression instantiate(Object oldInstance, Encoder out) {
            return new Expression(oldInstance, oldInstance.getClass(), "getInstance", null);
        }
    };

    /**
     * Used to save Waterloo graphics housed in a MATLAB figure.
     *
     * @param fileName - the file name.
     * @param compList - a list of GJGraphContainers.
     * @param pos - the pixel positions of those in the MATLAB figure.
     * @return a SwingWorker<Void, Void> instance that saves the data on a
     * background thread.
     */
    public static SwingWorker<Void, Void> createForMATLAB(final String fileName, final Component[] compList, final ArrayList<double[]> pos) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                OutputStream out = createOutputStream(fileName);
                XMLEncoder e = new XMLEncoder(out);
                writeHeader(e);
                for (int k = compList.length - 1; k >= 0; k--) {
                    e.writeObject(new Separator());
                    LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                    double[] positions = pos.get(k);
                    map.put("Bounds", new Rectangle2D.Double(positions[0], positions[1], positions[2], positions[3]));
                    e.writeObject(map);
                    containerEncoder(e, (GJAbstractGraphContainer) compList[k]);
                }
                doClose(e);
                try {
                    out.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
                return null;
            }
        };
        worker.execute();
        return worker;
    }
}
