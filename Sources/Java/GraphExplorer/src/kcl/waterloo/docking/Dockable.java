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
package kcl.waterloo.docking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DragGestureEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * {@code Dockable} provides a wrapper for {@code Components} added to a
 * {@code DockContainer}.
 *
 * {@code Dockable} components are added in the wrapper to the
 * {@code DockContainer} together with a {@code DockableBanner} that supports
 * dragging of the {@code Dockable} out of the {@code DockContainer} (as with
 * JToolbars).
 *
 * When dragged out of the {@code DockContainer}, the {@code Dockable} is
 * displayed in a JDialog. Closing the JDialog returns the {@code Dockable} to
 * the {@code DockContainer}.
 *
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Dockable extends JPanel  {

    static Color color = new Color(.5f, .5f, 1f, 0.25f);
    private DockableBanner banner = new DockableBanner();
    private JComponent contentPane = new JPanel();

    private Dockable() {
        setLayout(new BorderLayout());
        //contentPane.setPreferredSize(new Dimension(32767, 200));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.white);
        add(contentPane, BorderLayout.CENTER);
    }

    public Dockable(Component c) {
        this();
        banner.setTitleText(c.getName());
        contentPane.add(c);
        add(banner, BorderLayout.NORTH);
    }

//    public Dockable(String title) {
//        this();
//        banner = new DockableBanner();
//        banner.setTitleText(title);
//    }

    public Dockable(Component c, String title) {
        this(c);
        banner.setTitleText(title);   
    }


    /**
     * @return the contentPane
     */
    public JComponent getContentPane() {
        return contentPane;
    }

    /**
     * @param contentPane the contentPane to set
     */
    public void setContentPane(JComponent contentPane) {
        this.contentPane = contentPane;
    }

    @Override
    public Component add(Component c) {
        return contentPane.add(c);
    }

    @Override
    public void remove(Component c) {
        contentPane.remove(c);
    }

    @Override
    public void paintChildren(Graphics g) {
        super.paintChildren(g);
        if (banner.isDragging()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(color);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }


}