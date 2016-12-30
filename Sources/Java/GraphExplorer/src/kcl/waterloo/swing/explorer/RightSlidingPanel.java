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
package kcl.waterloo.swing.explorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import kcl.waterloo.swing.explorer.images.Images;
import kcl.waterloo.docking.DockContainer;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class RightSlidingPanel extends JPanel {

    JButton logo;
    JPanel upper;

    RightSlidingPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        SpringLayout layout = new SpringLayout();
        upper = new JPanel(layout);
        logo = new JButton(Images.getIcon("IrondukeLogo.png"));
        logo.addActionListener(new LocalListener());
        upper.setPreferredSize(new Dimension(75, 60));

        upper.setPreferredSize(new Dimension(32767, 75));
        layout.putConstraint(SpringLayout.WEST, logo, -90, SpringLayout.EAST, upper);
        layout.putConstraint(SpringLayout.EAST, logo, -5, SpringLayout.EAST, upper);
        layout.putConstraint(SpringLayout.NORTH, upper, 5, SpringLayout.NORTH, logo);
        upper.add(logo);

        JPanel lower = new DockContainer();
        add(upper, BorderLayout.NORTH);
        add(lower, BorderLayout.CENTER);

    }

    private class LocalListener implements ActionListener {

        public LocalListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("http://www.kcl.ac.uk/biohealth/research/divisions/wolfson/index.aspx"));
                } catch (IOException ex) {
                }
            } catch (URISyntaxException ex) {
            }
        }
    }
}
