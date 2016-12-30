 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2012-
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
package kcl.waterloo.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class LogoPanel extends GradientPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final ImageIcon img = new ImageIcon(LogoPanel.class.getResource("/kcl/waterloo/gui/images/sigTOOLSinc.png"));
    private static final ImageIcon img2 = new ImageIcon(LogoPanel.class.getResource("/kcl/waterloo/gui/images/sigtool.png"));
    JLabel label = new JLabel("Graph Editor");
    JLabel centralLabel = new JLabel("");

    public LogoPanel() {
        init();
    }

    public LogoPanel(String s) {
        label.setText(s);
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(700, 65));
        setBorder(new LineBorder(new Color(0, 0, 0), 2));
        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.SOUTH, this);
        setLayout(springLayout);

        JButton LogoButton = new JButton("");
        LogoButton.setIcon(new ImageIcon(LogoPanel.class.getResource("/kcl/waterloo/gui/images/IrondukeLogo.png")));
        springLayout.putConstraint(SpringLayout.NORTH, LogoButton, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, LogoButton, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, LogoButton, 0, SpringLayout.SOUTH, this);
        add(LogoButton);

        label.setForeground(new Color(0, 0, 128));
        label.setFont(new Font("Lucida Grande", Font.BOLD, 24));
        springLayout.putConstraint(SpringLayout.EAST, label, -10, SpringLayout.EAST, this);
        add(label);

        JLabel lblProjectWaterlooScientific = new JLabel("http://waterloo.sourceforge.net");
        springLayout.putConstraint(SpringLayout.SOUTH, lblProjectWaterlooScientific, -1, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblProjectWaterlooScientific, 0, SpringLayout.HORIZONTAL_CENTER, this);
        lblProjectWaterlooScientific.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        add(lblProjectWaterlooScientific);

        JLabel lblProjectWaterloo = new JLabel("Project Waterloo");
        springLayout.putConstraint(SpringLayout.NORTH, lblProjectWaterloo, 2, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblProjectWaterloo, 0, SpringLayout.HORIZONTAL_CENTER, label);
        add(lblProjectWaterloo);

        centralLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        centralLabel.setForeground(Color.ORANGE);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, centralLabel, 0, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, centralLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        add(centralLabel);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), img.getImageObserver());
        g.drawImage(img2.getImage(), label.getX() + label.getWidth() - 75, label.getY() - 10, 75, 17, img2.getImageObserver());
    }

    public void setLabelText(String lbl) {
        label.setText(lbl);
    }

    public void setCentralText(String lbl) {
        centralLabel.setText(lbl);
    }
}
