 /*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
package kcl.waterloo.xml;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.zip.GZIPInputStream;
import javax.swing.SwingWorker;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.logging.CommonLogger;

/**
 * Static library for deserializing the Waterloo xml files. Any changes here
 * need to be tested for backwards compatibility.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJDecoder {

    private static Class defaultVectorClass = GJDefaults.getDataClass();
    private static LinkedHashMap<String, Object> defaultMap = GJDefaults.getMap();

    private static boolean updateMessage = false;

    private static final CommonLogger logger = new CommonLogger(GJDecoder.class);

    private static String fileToLoad = "";

    /**
     * @return the defaultMap
     */
    protected static LinkedHashMap<String, Object> getDefaultMap() {
        return defaultMap;
    }

    private GJDecoder() {
    }

    static InputStream createInputStream(String fileName) {
        BufferedInputStream buffer;
        try {
            int dotPos = fileName.lastIndexOf(".");
            String extension = fileName.substring(dotPos);
            FileInputStream input = new FileInputStream(fileName);
            buffer = new BufferedInputStream(input);
            fileToLoad = fileName;
            if (extension.equalsIgnoreCase(".gz")) {
                try {
                    return new GZIPInputStream(buffer);
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                return buffer;
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public static Object load(String fileName) {
        fileToLoad = fileName;
        return load(fileName, createInputStream(fileName));
    }

    /**
     * Returns the graphics data from the specified file.
     *
     * @param fileName the name of the file to load
     * @param stream the InputStream for the file
     * @return the loaded Object
     */
    @SuppressWarnings("unchecked")
    public static Object load(final String fileName, final InputStream stream) {

        if (fileName.isEmpty()) {
            fileToLoad = "Unknown source";
        } else {
            fileToLoad = fileName;
        }
        updateMessage = true;

        SwingWorker<Object, Void> worker = new SwingWorker<Object, Void>() {

            @Override
            @SuppressWarnings("unchecked")
            protected Object doInBackground() throws Exception {
                synchronized (GJDefaults.getInstance()) {
                    defaultVectorClass = GJDefaults.getDataClass();
                    defaultMap = GJDefaults.getMap();
                    GJDefaults.setDeserializing(true);

                    Thread.currentThread().setContextClassLoader(GJDecoder.class.getClassLoader());
                    XMLDecoder d;

                    d = new XMLDecoder(stream);

                    d.setOwner(GJDefaults.getInstance());
                    // Can not do this - may get calls to GJDataModel on the same thread
                    // which will fail? Look into this
                    GJDefaults.setDataClass(null);

                    d.setExceptionListener(exHandler);

                    Object thisObject;

                    // Read the header added by GJEncoder
                    thisObject = d.readObject();// Attribution
                    thisObject = d.readObject();// Version String
                    Long majorVersion = (Long) d.readObject();// Version as long
                    Long minorVersion = (Long) d.readObject();// Version as long
                    thisObject = d.readObject();
                    String Version = (String) d.readObject();
                    GJDefaults fileDefaults = (GJDefaults) d.readObject();

                    if (majorVersion.compareTo(GJEncoder.Header.majorVersion) > 0) {
                        logger.warn(fileName.concat(" was written with a more recent version of the Waterloo"));
                    } else if (majorVersion.compareTo(GJEncoder.Header.majorVersion) == 0
                            && minorVersion.compareTo(GJEncoder.Header.minorVersion) > 0) {
                        logger.warn(fileName.concat(" was written with a more recent version of the Waterloo"));

                    }

                    // Read the contents
                    LinkedHashMap<String, Object> map = null;
                    thisObject = d.readObject();
                    if (thisObject instanceof Separator) {
                        // The seperator indicates we have a compound file with multiple graphs
                        // (a .kclfig file)
                        thisObject = compoundLoader(d);
                        // Objects are stored in an array list
                        ArrayList<Object> arrayList = (ArrayList<Object>) thisObject;
                        map = new LinkedHashMap<String, Object>();
                        // First n-1 objects are the graphics, Dimensions etc
                        ArrayList<Object> list = new ArrayList<Object>(arrayList.subList(0, arrayList.size() - 1));
                        map.put("Graphics", list);
                        // Exception log in final element
                        map.put("ExceptionLog", arrayList.get(arrayList.size() - 1));
                    } else if (thisObject instanceof LinkedHashMap) {
                        // Initial object is a map with the Dimension (a .kclf file)
                        map = (LinkedHashMap<String, Object>) thisObject;
                        // Add the graphics to it
                        thisObject = d.readObject();
                        map.put("Graphics", thisObject);
                        // Set the preferred size from the saved Dimension
                        ((Component) thisObject).setPreferredSize((Dimension) map.get("Dimension"));
                        // Retrieve and add the exception log
                        map.put("ExceptionLog", d.readObject());
                    }
                    doClose(d);
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        logger.error(ex.getMessage());
                    }
                    if (map == null) {
                        return thisObject;
                    } else {
                        return map;
                    }
                }
            }
        };
        worker.execute();
        try {
            // Time-out after 50s
            return worker.get(50L, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        } catch (ExecutionException ex) {
            logger.error(ex.getMessage());
        } catch (TimeoutException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    private static void doClose(XMLDecoder d) {
        GJDefaults.setDataClass(defaultVectorClass);
        GJDefaults.setMap(getDefaultMap());
        GJDefaults.setDeserializing(false);
        d.close();
    }

    @SuppressWarnings(value = "unchecked")
    private static Object compoundLoader(XMLDecoder d) {
        Object thisObject;
        ArrayList<Object> list = new ArrayList<Object>();
        while (true) {
            try {
                thisObject = d.readObject();
            } catch (ArrayIndexOutOfBoundsException ex) {
                return list;
            }
            if (thisObject instanceof GJAbstractGraphContainer) {
                list.add(thisObject);
            } else if (thisObject instanceof LinkedHashMap) {
                list.add(thisObject);
            } else if (thisObject instanceof ArrayList) {
                list.add(thisObject);
            }
        }
    }

    private static final ExceptionListener exHandler = new ExceptionListener() {
        @Override
        public void exceptionThrown(Exception excptn) {
            if (excptn.toString().contains("java.lang.NullPointerException")) {
                logger.warn("NullPointerException: This may reflect a change in the defaults table between Waterloo versions.");
                logger.warn("Attempting to continue with standard defaults instead of those from file.");
                GJDefaults.setMap(defaultMap);
            } else if (excptn.toString().contains("GJGraph.setCurrentMousePosition")) {
                if (updateMessage) {
                    logger.warn("setCurrentMousePosition: GJGraph.setCurrentMousePosition now private (Alpha3)");
                    logger.warn(fileToLoad.concat(" read OK, but it should be re-saved to update it"));
                    updateMessage = false;
                }
            } else if (excptn.toString().contains("GJDoubleDataVector.setParentPlot")) {
                if (updateMessage) {
                    logger.warn(fileToLoad.concat(" read OK, but it should be re-saved to update it"));
                    updateMessage = false;
                }
            } else if (excptn.toString().contains(".setAxis(GJDataVectorInterface$AXIS)")) {
                if (updateMessage) {
                    logger.warn(fileToLoad.concat(" read OK, but it should be re-saved to update it"));
                    updateMessage = false;
                }
            } else {
                logger.error(excptn.getMessage());
            }
        }
    };
}
