/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.gui.file;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Malcolm Lidierth
 */
public class FileUtil {

    private FileUtil() {
    }

    public static boolean endsWith(String s, ArrayList<String> s2) {
        for (String ext : s2) {
            if (s.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public static String getExtension(File f) {
        int i = f.getPath().lastIndexOf('.');
        if (i > 0 && i < f.getPath().length() - 1) {
            return f.getPath().substring(i + 1).toLowerCase();
        } else {
            return "";
        }
    }
}
