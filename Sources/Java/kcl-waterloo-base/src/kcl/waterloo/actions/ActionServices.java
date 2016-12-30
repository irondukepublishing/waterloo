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
package kcl.waterloo.actions;

import java.awt.AWTPermission;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.deploy.image.ImageSupport;
import kcl.waterloo.deploy.pde.PDEGraphics2D;
import kcl.waterloo.export.ExportFactory;
import kcl.waterloo.graphics.GJBasicPanel;
import kcl.waterloo.graphics.GJBasicPanel.LocalTransferable;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.gui.file.FileUtil;
import kcl.waterloo.gui.file.ImageFileChooser;
import kcl.waterloo.logging.CommonLogger;
import kcl.waterloo.swing.GCFrame;
import kcl.waterloo.swing.GCGridContainer;
import kcl.waterloo.xml.FileWrapper;
import kcl.waterloo.xml.GJDecoder;
import kcl.waterloo.xml.GJEncoder;

/**
 * This class provides static methods for commmon menu and button actions used
 * in various Waterloo project packages. These methods are intended primarily
 * for use within Waterloo code - but may also be useful in user-developed
 * menus/GUIs.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ActionServices {

    private static class Singleton {

        // Not used, but causes exportFactoryPresent to be set
        private final static ActionServices INSTANCE = new ActionServices();
        private static boolean exportFactoryPresent;
        private static boolean useChooser = true;
        private static CommonLogger logger;

    }

    /**
     * Non-instantiable class - static library.
     */
    private ActionServices() {
        try {
            Class<?> c = Class.forName("kcl.waterloo.export.ExportFactory");
            if (c != null) {
                Singleton.exportFactoryPresent = true;
            }
        } catch (ClassNotFoundException ex) {
            Singleton.exportFactoryPresent = false;
        }
        Singleton.logger = new CommonLogger(ActionServices.class.getName());
    }

    /**
     * @return the useChooser flag
     */
    public static boolean isUseChooser() {
        return Singleton.useChooser;
    }

    /**
     * @param aUseChooser if true, file dialogs will use the cross-platform
     * JFileChooser. If false, a FileDialog will be used.
     */
    public static void setUseChooser(boolean aUseChooser) {
        Singleton.useChooser = aUseChooser;
    }

    /**
     * 
     * @return 
     */
    public static FileWrapper open() {
        return open(null);
    }

    /**
     * Displays a file open dialog, loads the selected Waterloo graphics XML
     * file and returns the details in a FileWrapper instance.
     *
     * If the file contains multiple objects, the contents will be an ArrayList
     * of objects.
     *
     * @param parent the component whose parent Frame (if any) will be used
     * @return the de-serialized file contents
     */
    @SuppressWarnings(value = "unchecked")
    public static FileWrapper open(Component parent) {
        File file;
        if (isUseChooser()) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Waterloo graphics (.kclf)", "kclf"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Compressed Waterloo graphics (.kclf.gz)", "gz"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Waterloo MATLAB folders (.kclfig)", "kclfig"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("All Waterloo Files", new String[]{"kclf", "gz", "kclfig"}));
            int val = chooser.showOpenDialog(parent);
            if (val != JFileChooser.APPROVE_OPTION) {
                return new FileWrapper(null, null);
            }
            file = chooser.getSelectedFile();
        } else {
            parent = (parent != null) ? SwingUtilities.windowForComponent(parent) : null;
            Frame parent2 = (parent instanceof Frame) ? (Frame) SwingUtilities.windowForComponent(parent) : null;
            FileDialog chooser = new FileDialog(parent2, "Open File", FileDialog.LOAD);
            chooser.setLocationByPlatform(true);
            chooser.setFilenameFilter(new WaterlooFileOpenFilter());
            chooser.setVisible(true);
            file = new File(chooser.getDirectory(), chooser.getFile());
        }

        Singleton.logger.debug(String.format("Loading: %s", file.getPath()));

        FileWrapper fileWrapper;
        if (file.isDirectory() && file.getName().endsWith(".kclfig")) {
            fileWrapper = getKCLFFile(file);
        } else {
            fileWrapper = new FileWrapper(file, null);
        }

        Object loadedObject;

        String s = fileWrapper.getFile().toString();
        loadedObject = GJDecoder.load(s);

        if (loadedObject instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) loadedObject;
            Object newObj = map.get("Graphics");
            fileWrapper.setContents(newObj);
            fileWrapper.setErrorLog((ArrayList<String>) map.get("ErrorLog"));
            Singleton.logger.debug(String.format("Loaded: %s", file.getPath()));
            return fileWrapper;
        } else {
            Singleton.logger.debug(String.format("Loaded: %s", file.getPath()));
            return new FileWrapper(null, null);
        }

    }

    /**
     * 
     * @param o
     * @return 
     */
    public static FileWrapper save(Object o) {
        return save(null, o);
    }

    /**
     * Displays a file chooser and saves the specified Object to file by calling
     * the GJEncoder save method.
     *
     * @param parent
     * @param o Object recognizable by GJDecoder
     * @return a FileWrapper
     */
    public static FileWrapper save(Component parent, Object o) {
        File file;
        if (isUseChooser()) {
            JFileChooser chooser = new JFileChooser();
            int val = chooser.showSaveDialog(parent);
            if (val != JFileChooser.APPROVE_OPTION) {
                return new FileWrapper(null, null);
            }
            file = chooser.getSelectedFile();
        } else {
            parent = (parent != null) ? SwingUtilities.windowForComponent(parent) : null;
            Frame parent2 = (parent instanceof Frame) ? (Frame) SwingUtilities.windowForComponent(parent) : null;
            FileDialog chooser = new FileDialog(parent2, "Save File", FileDialog.SAVE);
            chooser.setFilenameFilter(new WaterlooFileSaveFilter());
            chooser.setLocationByPlatform(true);
            chooser.setVisible(true);
            file = new File(chooser.getDirectory(), chooser.getFile());
        }

        Singleton.logger.debug(String.format("Saving: %s", file.getPath()));
        GJEncoder.save(file.getPath(), o);
        Singleton.logger.debug(String.format("Saved: %s", file.getPath()));
        return new FileWrapper(null, null);
    }

    /**
     * 
     * @param c2 
     */
    public static void deployAs(Component c2) {
        deployAs(null, c2);
    }

    /**
     * 
     * @param parent
     * @param c2 
     */
    public static void deployAs(Component parent, final Component c2) {
        String folder;
        File file;
        if (isUseChooser()) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("ProcessingJS (.pde)", "pde"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Scalable Vector Graphics (.svg)", "svg"));
            int val = chooser.showSaveDialog(parent);
            if (val != JFileChooser.APPROVE_OPTION) {
                return;
            }
            file = chooser.getSelectedFile();
            folder = chooser.getCurrentDirectory().getPath();
        } else {
            parent = (parent != null) ? SwingUtilities.windowForComponent(parent) : null;
            Frame parent2 = (parent instanceof Frame) ? (Frame) SwingUtilities.windowForComponent(parent) : null;
            FileDialog chooser = new FileDialog(parent2, "Deploy to Web", FileDialog.SAVE);
            chooser.setFilenameFilter(new WaterlooDeployFilter());
            chooser.setLocationByPlatform(true);
            chooser.setVisible(true);
            file = new File(chooser.getDirectory(), chooser.getFile());
            folder = chooser.getDirectory();
        }
        String filename = file.getName();
        if (!filename.endsWith(".svg") && !filename.endsWith(".pde")) {
            // Default to setting in defaults table
            filename = filename.concat(".").concat((String) GJDefaults.getMap2().get("DeployMode"));
        }

        final String ext = getExtension(filename);
        final File file2 = new File(folder, filename);
        final String folder2 = file2.getPath().replace(".".concat(getExtension(file2.getPath())), "");

        if (ext.equals("pde")) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PDEGraphics2D g2 = PDEGraphics2D.paint(c2);
                    String jsLoc = (String) GJDefaults.getMap().get("Processing.jsLocation");
                    String cssLoc = (String) GJDefaults.getMap().get("Processing.cssLocation");
                    boolean httpd = ((Boolean) GJDefaults.getMap().get("Processing.httpd"));
                    try {
                        g2.write(file2.getPath(), true, jsLoc, cssLoc, httpd);
                        Singleton.logger.debug(String.format("PDE file saved to folder: %s", folder2));
                    } catch (IOException ex) {
                        Singleton.logger.warn("IOException: " + ex.getMessage());
                    } finally {

                    }
                }
            });
        } else if (Singleton.exportFactoryPresent) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        ExportFactory.deploySVG(c2, file2,
                                c2.getSize(),
                                false,
                                ((String) GJDefaults.getMap().get("SVG.cssLocation")),
                                ((Boolean) GJDefaults.getMap().get("SVG.httpd")),
                                new ArrayList<String>(),
                                "",
                                "");
                        Singleton.logger.debug(String.format("SVG file saved to folder: %s", folder2));
                    } catch (IOException ex) {
                        Singleton.logger.warn("IOException: " + ex.getMessage());
                    } finally {

                    }
                }
            });
        }

    }

    /**
     * 
     * @param c 
     */
    public static void saveAs(Component c) {
        saveAs(null, c);
    }

    /**
     * Displays a file save dialog and saves a Component in the selected format.
     *
     * @param parent
     * @param c an AWT component
     */
    public static void saveAs(Component parent, Component c) {
        if ((c instanceof GJGraphInterface) && ((GJGraphInterface) c).getGraphContainer() != null) {
            c = ((GJGraphInterface) c).getGraphContainer();
        }
        boolean opaqueFlag = c.isOpaque();
        if (c instanceof JComponent) {
            ((JComponent) c).setOpaque(true);
        }
        File file = null;
        try {
            if (isUseChooser()) {
                int val = ImageFileChooser.createSaveDialog(parent, "");
                switch (val) {
                    case javax.swing.JFileChooser.APPROVE_OPTION:
                        return;
                }
                ImageFileChooser fc = ImageFileChooser.getInstance();
                file = fc.getSelectedFile();
            } else {
                parent = (parent != null) ? SwingUtilities.windowForComponent(parent) : null;
                Frame parent2 = (parent instanceof Frame) ? (Frame) SwingUtilities.windowForComponent(parent) : null;
                FileDialog chooser = new FileDialog(parent2, "Deploy to Web", FileDialog.SAVE);
                chooser.setLocationByPlatform(true);
                chooser.setVisible(true);
                file = new File(chooser.getDirectory(), chooser.getFile());
            }
            c = getCopyOrSaveTarget(c);
            String ext = FileUtil.getExtension(file);
            Singleton.logger.debug(String.format("Saving: %s", file.getPath()));
            if (Singleton.exportFactoryPresent && ext.equals("pdf")) {
                ExportFactory.saveAsPDF(c, file);
            } else if (ext.equals("eps")) {
                ExportFactory.saveAsEPS(c, file);
            } else if (Singleton.exportFactoryPresent && ext.equals("svg")) {
                ExportFactory.saveAsSVG(c, file);
            } else if (Singleton.exportFactoryPresent && ext.equals("gz")) {
                ExportFactory.saveAsCompressedSVG(c, file);
            } else if (ext.equals("pde")) {
                PDEGraphics2D g2 = new PDEGraphics2D((Graphics2D) c.getGraphics(), c.getSize());
                c.paint(g2);
                g2.write(file.getPath(), false, "");
            } else {
                BufferedImage buffer = c.getGraphicsConfiguration().createCompatibleImage(c.getWidth(), c.getHeight());
                Graphics2D g2 = (Graphics2D) buffer.getGraphics();
                c.paint(g2);
                if (ext.isEmpty()) {
                    ext = "png";
                    file = new File(file.getPath().concat(".png"));
                }
                ImageIO.write(buffer, ext, file);
            }
            Singleton.logger.debug(String.format("Saved: %s", file.getPath()));
        } catch (IOException ex) {
            if (file != null && file.getPath() != null){
                Singleton.logger.warn(String.format("IOException saving file: %s", file.getPath()));
            }
        } finally {
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(opaqueFlag);
            }
        }
    }

    /**
     * Copies the graphics of a GJBasicPanel or subclass to the clipboard.
     *
     * @param panel a GJBasicPanel
     */
    public static void copy(GJBasicPanel panel) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        LocalTransferable t = panel.getTransferable();
        clipboard.setContents(t, t);
    }

    /**
     * Copies the graphics of a GJBasicPanel or subclass to the clipboard in the
     * background. Returns the associated SwingWorker.
     *
     * @param panel a GJBasicPanel
     * @return the SwingWorker<Boolean, Void> for the task
     */
    public static SwingWorker<Boolean, Void> copyGraphics(final GJBasicPanel panel) {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    SecurityManager manager = System.getSecurityManager();
                    if (manager != null) {
                        manager.checkPermission(new AWTPermission("accessClipboard"));
                        //manager.checkSystemClipboardAccess();
                    }
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    GJBasicPanel panel2 = (GJBasicPanel) getCopyOrSaveTarget(panel);
                    LocalTransferable t = panel2.getTransferable();
                    try {
                        clipboard.setContents(t, t);
                    } catch (Exception ex) {
                        // Some versions of Java 7/8 will throw an IOException here.
                        // Java Bug - see https://bugs.openjdk.java.net/browse/JDK-7124253
                        Singleton.logger.warn(ex.getMessage());
                    }
                    return Boolean.TRUE;
                } catch (SecurityException ex) {
                    Singleton.logger.warn("SecurityException: Clipboard access is not available");
                }
                return Boolean.FALSE;
            }
        };
        worker.execute();
        return worker;
    }

    /**
     * Copies the graphics of a GJBasicPanel or subclass to the clipboard in
     * bitmap format only.
     *
     * @param panel a GJBasicPanel
     */
    public static void copyAsImage(GJBasicPanel panel) {
        try {
            SecurityManager manager = System.getSecurityManager();
            if (manager != null) {
                manager.checkPermission(new AWTPermission("accessClipboard"));
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            panel = (GJBasicPanel) getCopyOrSaveTarget(panel);
            LocalTransferable t = panel.getTransferable(false);
            clipboard.setContents(t, t);
        } catch (SecurityException ex) {
            Singleton.logger.warn("SecurityException: Clipboard access is not available");
        }
    }

    /**
     * 
     * @param c
     * @return 
     */
    public static Component getCopyOrSaveTarget(Component c) {
        if (c instanceof GCFrame) {
            c = (Container) ((GCFrame) c).getGraphicsContainer();
        } else if (c instanceof GCGridContainer) {
            c = ((GCGridContainer) c).getGrid();
        } else if (c instanceof GJGraphInterface
                && ((GJGraphInterface) c).getGraphContainer() != null) {
            c = ((GJGraphInterface) c).getGraphContainer();
        }
        return c;
    }

    /**
     * 
     */
    public static void editDefaults() {
        GJDefaults.editDefaults();
    }

    /**
     * 
     * @param file
     * @return 
     */
    private static FileWrapper getKCLFFile(File file) {
        File[] f = file.listFiles();
        File graphicsFile = null;
        ArrayList<File> imageFiles = new ArrayList<File>();
        for (File f1 : f) {
            if (graphicsFile == null && f1.getName().endsWith(".kclf") || f1.getName().endsWith(".kclf.gz")) {
                graphicsFile = f1;
            }
            if (ImageSupport.isImageFormatSupported(f1)) {
                imageFiles.add(f1);
            }
        }
        return new FileWrapper(graphicsFile, null, imageFiles);
    }

    /**
     * @return the Singleton.exportFactoryPresent
     */
    public static boolean isExportFactoryPresent() {
        return Singleton.exportFactoryPresent;
    }

    public static String getExtension(String s) {
        if (s.contains(".")) {
            int idx = s.lastIndexOf('.');
            return s.substring(idx + 1);
        } else {
            return "";

        }
    }

    private static class WaterlooFileOpenFilter implements FilenameFilter {

        private WaterlooFileOpenFilter() {
        }

        @Override
        public boolean accept(File f, String s) {
            String extension = getExtension(s);
            if (extension != null) {
                return extension.equals("kclf") || extension.equals("gz") || extension.equals("kclfig");
            } else {
                return false;
            }
        }

    }

    private static class JWaterlooFileOpenFilter extends FileFilter {

        @Override
        public boolean accept(File s) {
            String extension = FileUtil.getExtension(s);
                return extension.equals("kclf") || extension.equals("gz") || extension.equals("kclfig");
        }

        @Override
        public String getDescription() {
            return "All Waterloo Files";
        }

    }

    private static class WaterlooDeployFilter implements FilenameFilter {

        private WaterlooDeployFilter() {
        }

        @Override
        public boolean accept(File f, String s) {
            String extension = getExtension(s);
            if (extension != null) {
                return extension.equals("pde") || extension.equals("svg");
            } else {
                return false;
            }
        }

    }

    private static class WaterlooFileSaveFilter implements FilenameFilter {

        private WaterlooFileSaveFilter() {
        }

        @Override
        public boolean accept(File f, String s) {
            String extension = getExtension(s);
            if (extension != null) {
                return extension.equals("kclf") || extension.equals("gz");
            } else {
                return false;
            }
        }

    }
}
