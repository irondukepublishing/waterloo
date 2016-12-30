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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * {@code DockableBanner} provides a Panel in a {@code Dockable} to support
 * dragging out of the {@code DockContainer}.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class DockableBanner extends JPanel {

    private Object constraints;
    private Dimension dim;

    private JLabel title = new JLabel();
    private Point dragStart = null;
    private boolean docked = true;
    private int dockedPosition = -1;
    private Rectangle dockedBounds;
    private Container dockedParent;
    private JDialog frame = null;
    private Component initialHost;
    private GradientPaint gradient;
    

    private Color color2 = new Color(112,164,209);
    private Color color1 = color2.brighter();

    DockableBanner() {
        LineBorder border = new LineBorder(Color.DARK_GRAY, 1, true);
        setBorder(border);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        add(title);
        setPreferredSize(new Dimension(32767, 20));
        Handler handler = new Handler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        JButton dockingButton = new JButton("^");
        layout.putConstraint(SpringLayout.EAST, dockingButton, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, dockingButton, -18, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, dockingButton, 2, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, dockingButton, -2, SpringLayout.SOUTH, this);
        add(dockingButton);
    }

    DockableBanner(Component c, String title) {
        this();
        setTitleText(title);
    }

    /**
     * @return the title
     */
    public String getTitleText() {
        return title.getText();
    }

    /**
     * @param title the title to set
     */
    public final void setTitleText(String title) {
        this.title.setText(title);
    }

    /**
     * @return the dragStart
     */
    public boolean isDragging() {
        return dragStart != null;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (gradient == null
                || gradient.getPoint2().getX() != getWidth() / 2
                || gradient.getPoint2().getY() != getHeight()) {
            gradient = new GradientPaint(getWidth() / 2, 0f,
                    color1,
                    getWidth() / 2,
                    getHeight(),
                    color2,
                    true);
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        paintBorder(g);
        paintChildren(g);
    }

    private class Handler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent me) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
            dragStart = me.getPoint();
            getParent().getParent().repaint();
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            dragStart = null;
            getParent().getParent().repaint();
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }

        @Override
        public void mouseDragged(final MouseEvent me) {
            if (frame != null) {
                Point p = me.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(p, frame.getOwner());
                frame.setLocation(p.x + initialHost.getX(), p.y + initialHost.getY());
            } else if (dragStart != null && docked) {
                if (Math.hypot((double) (me.getPoint().getY() - dragStart.getY()),
                        (double) (me.getPoint().getX() - dragStart.getX())) > 5) {
                    unDock(me);
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent me) {
        }
    }

    private void unDock(final MouseEvent me) {
        dragStart = null;
        docked = false;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Point p = me.getComponent().getLocationOnScreen();
                // Get the Dockable...
                Container c = getParent();
                //... and its parent
                dockedParent = c.getParent();
                LayoutManager layout = dockedParent.getLayout();
                if (layout instanceof BorderLayout) {
                    constraints = ((BorderLayout) layout).getConstraints(c);
                }
                dim = getSize();
                dockedPosition = (new ArrayList<Component>(Arrays.asList(c.getParent().getComponents()))).indexOf(c);
                dockedBounds = c.getBounds();
                initialHost = ((JComponent) me.getComponent()).getTopLevelAncestor();
                frame = new JDialog((Frame) ((JComponent) me.getComponent()).getTopLevelAncestor(), title.getText(), false);
                frame.setLocation(p);
                c.setPreferredSize(new Dimension(dockedBounds.width, dockedBounds.height));
                frame.setContentPane(c);

                frame.addWindowListener(exitAdapter);
                frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                frame.setVisible(true);
                frame.pack();

                if (dockedParent instanceof JComponent) {
                    ((JComponent) dockedParent).validate();
                    ((JComponent) dockedParent).repaint();
                } else {
                    dockedParent.validate();
                }
            }
        });
    }
    public WindowListener exitAdapter = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            getParent().setPreferredSize(new Dimension(dockedBounds.width, dockedBounds.height));
            setVisible(true);
            LayoutManager layout = dockedParent.getLayout();
            Container dockable = ((JDialog) e.getWindow()).getContentPane();
            if (dockable.getComponentCount() <= dockedPosition) {
                if (layout instanceof BorderLayout) {
                    dockedParent.add(dockable, constraints);
                } else {
                    dockedParent.add(dockable, dockedPosition);
                }
            } else {
                if (layout instanceof BorderLayout) {
                    dockedParent.add(dockable, constraints);
                } else {
                    dockedParent.add(dockable, dockedPosition);
                }
            }
            
            if (dockedParent instanceof JSplitPane) {
                if (((JSplitPane) dockedParent).getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                    ((JSplitPane) dockedParent).setDividerLocation(dim.height);
                } else {
                    ((JSplitPane) dockedParent).setDividerLocation(dim.width);
                }
            }

            if (dockedParent instanceof JComponent) {
                ((JComponent) dockedParent).revalidate();
                ((JComponent) dockedParent).repaint();
            } else {
                dockedParent.validate();
            }
            e.getWindow().dispose();
            frame = null;
            docked = true;
        }
    };
}
