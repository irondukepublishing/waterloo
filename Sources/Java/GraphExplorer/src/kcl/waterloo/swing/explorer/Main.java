/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.swing.explorer;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import kcl.waterloo.swing.explorer.SwingDelegates;

/**
 * Main entry for stand-alone operation. Initiates Groovy Console and sets
 * required Java class paths to use Waterloo graphics.
 *
 * Assumes Java is installed !
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (System.getProperty("swing.defaultlaf") == null) {

            if (System.getProperty("os.name").contains("Windows")
                    || System.getProperty("os.name").matches("Mac OS X")) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e1) {
                    try {
                        System.out.println("System LAF not available");
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    } catch (Exception e2) {
                    }
                }
            } else {
                try {
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                        }
                    }
                } catch (Exception e1) {
                    System.out.println("Nimbus L&F not available");
                    try {
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    } catch (Exception e2) {
                    }
                }
            }

            SwingDelegates.setFrameClosurePolicy(JFrame.EXIT_ON_CLOSE);
            ConsoleHost.createInstance();

        }
    }
}