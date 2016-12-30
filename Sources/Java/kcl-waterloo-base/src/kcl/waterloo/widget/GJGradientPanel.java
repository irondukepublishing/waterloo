//
// Author: Malcolm Lidierth
// Copyright King's College London 2010
//
//
//This code was developed as part of Project Waterloo, which
//itself is part of the sigTOOL project at King's College London.
//
//SIGTOOL, PROJECT WATERLOO AND THE IMAGEJ FOR MATLAB INTERFACE ARE
//DISTRIBUTED UNDER VERSION 3 OF THE GNU GENERAL PUBLIC LICENCE.
//
//A copy of the GNU GPL licence is included in the distribution and can be
//downloaded from http://www.gnu.org/licenses/gpl.html.
//
//THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY
//APPLICABLE LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT
//HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM "AS IS" WITHOUT WARRANTY
//OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,
//THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
//PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE PROGRAM
//IS WITH YOU.  SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF
//ALL NECESSARY SERVICING, REPAIR OR CORRECTION.
//
//--------------------------------------------------------------------------
package kcl.waterloo.widget;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GJGradientPanel extends JPanel implements Serializable, PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private int Anchor;
    private float Repeats;
    private Color Color1 = Color.lightGray;
    private Color Color2 = Color.gray;
    private GradientPaint Gradient;
    private Dimension lastPaintedDimension;

    /**
     * default constructor
     */
    public GJGradientPanel() {
        this(SwingConstants.NORTH);
    }

    public GJGradientPanel(int a) {
        Anchor = a;
        Repeats = 1f;
        updateGradient();
        addPropertyChangeListener(this);
    }

    public void setBackground(GradientPaint p) {
        Color1 = p.getColor1();
        Color2 = p.getColor2();
        Gradient = p;
        firePropertyChange("Gradient", null, null);
    }

    public float getRepeats() {
        return Repeats;
    }

    public void setRepeats(float r) {
        float old = Repeats;
        Repeats = r;
        updateGradient();
        firePropertyChange("Repeats", old, Repeats);
    }

    public int getAnchor() {
        return Anchor;
    }

    /**
     * Sets the anvhor to use
     *
     * @param anchor from SwingConstants
     */
    public void setAnchor(int anchor) {
        int old = Anchor;
        if (anchor == SwingConstants.NORTH
                || anchor == SwingConstants.SOUTH
                || anchor == SwingConstants.WEST
                || anchor == SwingConstants.EAST
                || anchor == SwingConstants.NORTH_EAST
                || anchor == SwingConstants.NORTH_WEST) {
            Anchor = anchor;
            firePropertyChange("Anchor", old, Anchor);
        } 
    }

    public Color getColor1() {
        return Color1;
    }

    public void setColor1(Color c) {
        Color old = Color1;
        Color1 = c;
        firePropertyChange("Color1", old, Color1);
    }

    public Color getColor2() {
        return Color2;
    }

    public void setColor2(Color c) {
        Color old = Color2;
        Color2 = c;
        firePropertyChange("Color1", old, Color1);
    }

    private Point getPoint1() {
        if (Anchor == SwingConstants.NORTH) {
            return new Point(this.getWidth() / 2, 0);
        } else if (Anchor == SwingConstants.SOUTH) {
            return new Point(this.getWidth() / 2, this.getHeight());
        } else if (Anchor == SwingConstants.WEST) {
            return new Point(0, this.getHeight() / 2);
        } else if (Anchor == SwingConstants.EAST) {
            return new Point(getWidth(), this.getHeight() / 2);
        } else if (Anchor == SwingConstants.NORTH_WEST) {
            return new Point(0, 0);
        } else if (Anchor == SwingConstants.NORTH_EAST) {
            return new Point(this.getWidth(), 0);
        } else {
            return null;
        }
    }

    private Point getPoint2() {
        if (Anchor == SwingConstants.NORTH) {
            return new Point(this.getWidth() / 2, this.getHeight());
        } else if (Anchor == SwingConstants.SOUTH) {
            return new Point(this.getWidth() / 2, 0);
        } else if (Anchor == SwingConstants.WEST) {
            return new Point(this.getWidth(), this.getHeight() / 2);
        } else if (Anchor == SwingConstants.EAST) {
            return new Point(0, this.getHeight() / 2);
        } else if (Anchor == SwingConstants.NORTH_WEST) {
            return new Point(this.getWidth(), this.getHeight());
        } else if (Anchor == SwingConstants.NORTH_EAST) {
            return new Point(0, this.getHeight());
        } else {
            return null;
        }
    }

    private void updateGradient() {
        Point point1 = this.getPoint1();
        Point point2 = this.getPoint2();
        if (Repeats != 1f) {
            if (Anchor == SwingConstants.NORTH) {
                point2 = new Point((int) point2.getX(), (int) (point2.getY() / Repeats));
            } else if (Anchor == SwingConstants.WEST) {
                point2 = new Point((int) (point2.getX() / Repeats), (int) (point2.getY()));
            } else if (Anchor == SwingConstants.SOUTH) {
                point1 = new Point((int) (point1.getX()), (int) (point1.getY() / Repeats));
            } else if (Anchor == SwingConstants.EAST) {
                point1 = new Point((int) (point1.getX() / Repeats), (int) (point1.getY()));
            } else if (Anchor == SwingConstants.NORTH_WEST) {
                point2 = new Point((int) (point2.getX() / Repeats), (int) (point2.getY() / Repeats));
            } else if (Anchor == SwingConstants.NORTH_EAST) {
                point2 = new Point((int) (point1.getX() - point1.getX() / Repeats), (int) (point2.getY() / Repeats));
            }
        }
        Gradient = new GradientPaint(point1, Color1, point2, Color2, true);
        lastPaintedDimension = this.getSize();
    }

    private boolean isValidGradient() {
        return lastPaintedDimension == this.getSize();
    }

    /**
     * Overridden paintComponent method
     *
     * @param g an AWT Graphics instance
     */
    @Override
    public void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        if (!isValidGradient()) {
            updateGradient();
        }
        super.paintComponent(g);
        g2d.setPaint(Gradient);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        //setOpaque(false);
        //setOpaque(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        updateGradient();
        repaint();
    }
}
