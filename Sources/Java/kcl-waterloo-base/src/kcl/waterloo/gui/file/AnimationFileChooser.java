/*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

/*
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class AnimationFileChooser extends JFileChooser {

    /**
     * Singleton instance
     */
    private static AnimationFileChooser instance;
    private static File currentSaveDirectory;
    private static File currentOpenDirectory;
    private static final ArrayList<String> targetExtension = new ArrayList<String>();

    /**
     * Private constructor. This will be called once for each session. The same
     * singleton instance will be used for both opening and saving files.
     *
     * @param str the folder for the current directory property.
     */
    private AnimationFileChooser(String str) {
        super(str);
        setDefaultExtensions();
        this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        addChoosableFileFilter(new FileNameExtensionFilter("Graphic Interchange (GIF)", "gif"));
    }

    /**
     * Static method to return the singleton instance.
     *
     * @return the {@code AnimationFileChooser}
     */
    public static AnimationFileChooser getInstance() {
        if (instance == null) {
            instance = new AnimationFileChooser("");
        }
        return instance;
    }

    /**
     * @return the targetExtension
     */
    private static ArrayList<String> getTargetExtension() {
        return targetExtension;
    }

    private static void setDefaultExtensions() {
        targetExtension.clear();
        targetExtension.add(".gif");
    }

    /**
     * Static method to create and display a save dialog using the singleton
     * instance. Sets the current save folder to the default system save folder.
     *
     * @return the FileChooser instance.
     */
    public static int createSaveDialog() {
        return createSaveDialog("");
    }

    /**
     * Static method to create and display a save dialog using the singleton
     * instance.
     *
     * @param str the root folder for the display
     * @return the AnimationFileChooser instance.
     */
    public static int createSaveDialog(String str) {
        if (instance == null) {
            instance = new AnimationFileChooser(str);
        } else {
            instance.setCurrentDirectory(new File(str));
        }
        instance.setCurrentDirectory(currentSaveDirectory);
        int value = instance.showSaveDialog(null);
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
     * @param str the currentDirectoryPath as a String
     * @return the AnimationFileChooser instance.
     */
    public static int createOpenDialog(String str) {
        if (instance == null) {
            instance = new AnimationFileChooser(str);
        }
        instance.setCurrentDirectory(currentOpenDirectory);
        int value = instance.showOpenDialog(null);
        currentOpenDirectory = instance.getCurrentDirectory();
        return value;
    }

}
