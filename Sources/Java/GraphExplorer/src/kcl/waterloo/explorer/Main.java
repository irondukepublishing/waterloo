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
package kcl.waterloo.explorer;

import javax.swing.UIManager;

/**
 * Main entry for stand-alone operation. Initiates Groovy Console and sets
 * required Java class paths to use Waterloo graphics.
 *
 * Assumes Java is installed !
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {


        // Honour any laf setting from the user
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
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e2) {
                        try {
                            System.out.println("System LAF not available");
                            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                        } catch (Exception e3) {
                        }
                    }
                }
            }

            //FrameDelegate.setFrameClosurePolicy(JFrame.EXIT_ON_CLOSE);
            GraphExplorer.createInstance();

        }
    }
}
