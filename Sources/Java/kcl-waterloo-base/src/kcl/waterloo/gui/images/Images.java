package kcl.waterloo.gui.images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Malcolm Lidierth <malcolm.lidierth at kcl.ac.uk>
 */
public class Images {

    static Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static Image getImage(String filename) throws SecurityException {
        URL url = Images.class.getResource(filename);
        if (url != null) {
            return toolkit.getImage(url);
        } else {
            return null;
        }

    }

    public static Icon getIcon(String filename) {
        Image image = getImage(filename);
        if (image != null) {
            return new ImageIcon(image);
        } else {
            return new ImageIcon();
        }
    }
}
