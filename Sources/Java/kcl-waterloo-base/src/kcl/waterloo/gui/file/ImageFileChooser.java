/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.gui.file;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ImageFileChooser extends JFileChooser implements ActionListener {

    /**
     * Singleton instance
     */
    private static ImageFileChooser instance;
    private static File currentSaveDirectory;
    private static File currentOpenDirectory;
    protected static ArrayList<String> targetExtension = new ArrayList<String>();

    /**
     * Private constructor. This will be called once for each session. The same
     * singleton instance will be used for both opening and saving files.
     *
     * @param str the folder for the current directory property.
     */
    protected ImageFileChooser(String str) {
        super(str);
        setDefaultExtensions();
        this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        for (int k = 0; k < targetExtension.size(); k++) {
            String s = targetExtension.get(k);
            if (s.equals("bmp")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Bitmap (." + s + ")", s.replace(".", "")));
            } else if (s.equals("tif") || s.equals("tiff")) {
                addChoosableFileFilter(new FileNameExtensionFilter("TIF Image (." + s + ")", s.replace(".", "")));
            } else if (s.equals("png")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Portable Network Graphic (." + s + ")", s.replace(".", "")));
            } else if (s.equals("wbmp")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Windows bitmap (." + s + ")", s.replace(".", "")));
            } else if (s.equals("jpg") || s.equals("jpeg")) {
                addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image (." + s + ")", s.replace(".", "")));
            } else if (s.equals("svg")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Scalable Vector Format (." + s + ")", s.replace(".", "")));
            } else if (s.equals("gz")) {
                addChoosableFileFilter(new FileNameExtensionFilter("GZIP Compressed SVG (.svg." + s + ")", s.replace(".", "")));
            } else if (s.equals("pdf")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Portable Document Format (." + s + ")", s.replace(".", "")));
            } else if (s.equals("gif")) {
                addChoosableFileFilter(new FileNameExtensionFilter("GIF Image (." + s + ")", s.replace(".", "")));
            } else if (s.equals("html")) {
                addChoosableFileFilter(new FileNameExtensionFilter("HTML (." + s + ")", s.replace(".", "")));
            } else if (s.equals("pde")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Processing (." + s + ")", s.replace(".", "")));
            } else if (s.equals("eps")) {
                addChoosableFileFilter(new FileNameExtensionFilter("Encapsulated PS (." + s + ")", s.replace(".", "")));
            } else {
                addChoosableFileFilter(new FileNameExtensionFilter("Image (." + s + ")", s.replace(".", "")));
            }
        }
    }

    /**
     * Static method to return the singleton instance.
     *
     * @return the {@code FileChooser}
     */
    public static ImageFileChooser getInstance() {
        if (instance == null) {
            instance = new ImageFileChooser("");
            instance.addActionListener(instance);
        }
        return instance;
    }

    @Override
    public boolean accept(File f) {
        return targetExtension.contains("." + getExtension(f));
    }

    /**
     * @return the targetExtension
     */
    private static ArrayList<String> getTargetExtension() {
        return targetExtension;
    }



    private static void setDefaultExtensions() {
        targetExtension.clear();
        String[] s = javax.imageio.ImageIO.getWriterFileSuffixes();
        targetExtension.add("eps");
        targetExtension.add("pde");
        targetExtension.add("pdf");
        targetExtension.add("svg");
        targetExtension.add("gz");
        for (int k = 0; k < s.length; k++) {
            targetExtension.add(s[k].toLowerCase());
        }
    }

    /**
     * Static method that redirects to the singleton instance
     *
     * @param s the folder to set as the current directory.
     */
    private static void setCurrentDirectory(String s) {
        instance.setCurrentDirectory(new File(s));
    }

    /**
     * Static method to create and display a save dialog using the singleton
     * instance. Sets the current save folder to the default system save folder.
     *
     * @return the FileChooser instance.
     */
    public static int createSaveDialog() {
        return createSaveDialog(null,"");
    }

    /**
     * Static method to create and display a save dialog using the singleton
     * instance.
     *
     * @param parent
     * @param str the root folder for the display
     * @return the FileChooser instance.
     */
    public static int createSaveDialog(Component parent, String str) {
        if (instance == null) {
            instance = new ImageFileChooser(str);
            instance.addActionListener(instance);
        } else {
            instance.setCurrentDirectory(new File(str));
        }
        instance.setCurrentDirectory(currentSaveDirectory);
        int value = instance.showSaveDialog(parent);
        currentSaveDirectory = instance.getCurrentDirectory();
        return value;
    }

    public static int createOpenDialog() {
        return createOpenDialog("");
    }

    /**
     * Static method to create and display a file open dialog using the
     * singleton instance.
     *
     * @param str
     * @return the FileChooser instance.
     */
    public static int createOpenDialog(String str) {
        if (instance == null) {
            instance = new ImageFileChooser(str);
            instance.addActionListener(instance);
        }
        instance.setCurrentDirectory(currentOpenDirectory);
        int value = instance.showOpenDialog(null);
        currentOpenDirectory = instance.getCurrentDirectory();
        return value;
    }

    private static boolean endsWith(String s) {
        for (String ext : getTargetExtension()) {
            if (s.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private static String getExtension(String s) {
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            return s.substring(i + 1).toLowerCase();
        } else {
            return "";
        }
    }

    private static String getExtension(File f) {
        return ImageFileChooser.getExtension(f.getName());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            if (getFileFilter() != null) {
                String s = getFileFilter().getDescription();
                int idx0 = s.indexOf("(");
                int idx1 = s.indexOf(")");
                s = s.substring(idx0 + 1, idx1);
                setSelectedFile(new File(getSelectedFile().getPath() + s));
            }
        }
    }
}
