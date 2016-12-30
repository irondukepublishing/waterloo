package kcl.waterloo.graphics.images;

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

    public static Image getImage(String filename) {
        URL url = Images.class.getResource(filename);
        return toolkit.getImage(url);
    }

    public static Icon getIcon(String filename) {
        return new ImageIcon(getImage(filename));
    }
}
