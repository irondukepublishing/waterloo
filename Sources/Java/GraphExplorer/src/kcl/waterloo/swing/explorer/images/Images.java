package kcl.waterloo.swing.explorer.images;

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

    static final Toolkit toolKit = Toolkit.getDefaultToolkit();

    public static Image getImage(String filename) {
        URL url = Images.class.getResource(filename);
        Image image = toolKit.getImage(url);
        return image;  
    }

    public static Icon getIcon(String filename) {
        return new ImageIcon(getImage(filename));
    }
}
