/* 
 * This code is part  of a supplement to Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
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
 *
 */
package kcl.gpl.tex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to set L&F");
        }

        TeX label = TeX.createInstance("e=mc^2");
        label.setBackground(Color.yellow);
        create(label);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        label.setText("\\[(a+b)^3  = a^3+3a^2b+3ab^2+b^3\\]");
        label.setTextRotation(Math.PI / 10);

    }

    public static JFrame create(final TeX label) {
        final JFrame frame = new JFrame();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                label.setPreferredSize(new Dimension(450, 200));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(label);
                frame.setVisible(true);
                frame.pack();
            }
        });

        return frame;

    }
}
