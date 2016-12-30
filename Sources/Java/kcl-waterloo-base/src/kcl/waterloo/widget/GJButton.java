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
package kcl.waterloo.widget;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import kcl.waterloo.gui.images.Images;

/**
 * An XML serializable JButton subclass to display an image.
 *
 * If the filename begins with "http:" the displayed image will be assumed to be
 * a web resource. The filename string should be a valid URL.
 *
 * Otherwise, the image will be assumed to exist in the kcl/waterloo/gui/images
 * package and be loaded from the jar file.
 *
 * @author ML
 */
public class GJButton extends JButton {

    private String fileName = "";

    public GJButton() {
        setBorder(null);
        setBorderPainted(false);
        setBackground(new Color(0, 0, 0, 0));
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public GJButton(String filename) {
        this();
        this.fileName = filename;
        setIcon(Images.getIcon(filename));
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public final void setFileName(String filename) throws MalformedURLException {
        this.fileName = filename;
        if (filename != null) {
            if (fileName.startsWith("http:")) {
                URL url = new URL(fileName);
                setIcon(new ImageIcon(url));
            } else {
                setIcon(Images.getIcon(filename));
            }
        }
    }
}
