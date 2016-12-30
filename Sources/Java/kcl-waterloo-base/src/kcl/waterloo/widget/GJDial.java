 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * Project Waterloo is free software:  you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Project Waterloo is distributed in the hope that it will  be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.widget;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SliderUI;

/**
 * <p>
 * <code>GJDial</code> is a subclass of the standard swing
 * <code>JSlider</code>.</p>
 *
 * <p>
 * <code>GJDial</code> provides a rotating dial instead of a slider to change
 * the settings of the widget. By default the dial covers the range between the
 * minimum and maximum in a single turn - equivalent to the sweep length of the
 * JSlider. However, the number of turns available can be set programmatically
 * using the <code>setAvailableTurns</code> method, e.g. with
 * <code>setAvailableTurns(10)</code> the each rotation would cover 1/10th of
 * the range of the GJDial.</p>
 *
 * <p>
 * Dials are circular and will be drawn at the center of the GJDial's bounds
 * with a diameter determined from the lesser of the width and the height - so
 * it makes sense generally to specify square sizes.</p>
 *
 * <p>
 * The following methods are specific to GJDial or have been overridden.
 * </p>
 * <p>
 * <code>getTurnCount</code> - returns the number of turns made</p>
 * <p>
 * <code>getValue</code> - returns the value calculated from the current dial
 * settings.</p>
 *
 * <p>
 * The following also have suitable getters.</p>
 * <p>
 * <code>setAvailableTurns(int)</code> - sets the number of turns for the
 * dial</p>
 * <p>
 * <code>setDisplayValue(true/false)</code> provides a text display of the value
 * in the center of the dial</p><p>
 * <code>setLineColor(Color/Paint)</code> - sets the line color</p>
 * <p>
 * <code>setFill(Color/Paint)</code> - sets the fill color for the dial</p>
 * <p>
 * <code>setMajorTickSpacing(int n)</code> - displays n major ticks around the
 * dial</p>
 * <p>
 * <code>setMinorTickSpacing(int n)</code> - displays n minor ticks around the
 * dial</p>
 * <p>
 * <code>setText(String)</code> provides a JLabel with the specified string in
 * the center of the dial</p>
 * <p>
 * <code>setValue(int)</code> - positions the dial (and internal settings as
 * appropriate)</p>
 *
 * <p>
 * Other JSlider methods, e.g. <code>setMinimum</code> and
 * <code>setMaximum</code> act as you would expect or are ignored e.g.
 * <code>setExtent</code> so a GJDial should work as a drop-in replacement for a
 * JSlider.</p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJDial extends JSlider implements MouseListener, MouseMotionListener, PropertyChangeListener {

    private static final String uiClassID = "BasicGJDialUI";
    private Paint BackgroundFill = null;
    private Paint Fill = java.awt.Color.white;
    private Color LineColor = java.awt.Color.gray;
    private JLabel label = new JLabel();
    private boolean displayValue = false;
    private int mouseX;
    private int mouseY;
    private double Theta = 0;
    private double ThetaAtDragStart;
    private double MouseThetaAtDragStart;
    private static final float DialLineWidth = 2.5f;
    private static final BasicStroke pen1 = new BasicStroke(DialLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
    private static final BasicStroke pen2 = new BasicStroke(DialLineWidth / 2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
    private static NumberFormat nf = NumberFormat.getInstance();
    private GJRoundedBoundedDataModel model = new GJRoundedBoundedDataModel();

    private ActionListener actionListener = null;

    /**
     * Default constructor returns a 100x100 pixel single turn GJDial with no
     * display of the value and no JLabel text
     */
    public GJDial() {
        this(100, 1, false);
    }

    /**
     * Constructor returns a GjDial with no display of the value and no JLabel
     * text
     *
     * @param diameter diameter of the GJDial in pixels
     * @param n number of available turns
     */
    public GJDial(int diameter, int n) {
        this(diameter, n, false);
    }

    /**
     * Constructor
     *
     * @param diameter diameter of the GJDial in pixels
     * @param n number of available turns
     * @param flag true to display the values as text
     */
    public GJDial(int diameter, int n, boolean flag) {
        setup();
        model.setAvailableTurns(n);
        displayValue = flag;
        setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        super.setModel(model);
        super.setOpaque(false);
        setPreferredSize(new Dimension(diameter, diameter));
        updateUI();
    }

    private void setup() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, label, 0, SpringLayout.VERTICAL_CENTER, this);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
        revalidate();
        setMajorTickSpacing(20);
        addMouseListener(this);
        addMouseMotionListener(this);
        setValue(getMinimum());
    }

    public void addActionListener(ActionListener listener) {
        actionListener = listener;
    }

    /**
     * {@inheritDoc}
     *
     * @param val
     */
    @Override
    public void setMinimum(int val) {
        super.setMinimum(val);
        repaint();
    }

    /**
     * {@inheritDoc}
     *
     * @param val
     */
    @Override
    public void setMaximum(int val) {
        super.setMaximum(val);
        repaint();
    }

    /**
     * {@inheritDoc}
     *
     * @return the current value of the dial.
     */
    @Override
    public int getValue() {
        return getValue(getTheta());
    }

    private int getValue(double theta) {
        double perTurn = (getMaximum() - getMinimum()) / model.getAvailableTurns();
        double val = getMinimum() + (model.getTurnCount() * perTurn) + getFraction(theta);
        return (int) val;
    }

    /**
     * {@inheritDoc}
     *
     * @param val
     */
    @Override
    public void setValue(int val) {
        int old = getValue();
        double perTurn = (getMaximum() - getMinimum()) / model.getAvailableTurns();
        val = val - getMinimum();
        model.setTurnCount((int) Math.max(0, Math.floor(val / perTurn)));
        setTheta((2 * Math.PI) - (val % perTurn) / perTurn * Math.PI * 2);
        firePropertyChange("value", old, getValue());
    }

    private double getFraction(double theta) {
        double perTurn = (getMaximum() - getMinimum()) / model.getAvailableTurns();
        return perTurn - (perTurn * theta / Math.PI / 2);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public BasicGJDialUI getUI() {
        return (BasicGJDialUI) super.getUI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateUI() {
        setUI(BasicGJDialUI.createUI(this));
        invalidate();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * Sets the fill for the dial
     *
     * @return the Paint object
     */
    public Paint getFill() {
        return Fill;
    }

    /**
     * Returns the fill for the dial
     *
     * @param p the Paint object e.g. a Color
     */
    public void setFill(Paint p) {
        Fill = p;
    }

    public double getTheta() {
        return Theta;
    }

    private void setTheta(double theta) {
        Theta = theta;
        fireStateChanged();
        if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "theta"));
        }
        repaint();
    }

//    public double getThetaOffset() {
//        return ThetaOffset;
//    }
//
//    public void setThetaOffset(double theta) {
//        ThetaOffset = theta;
//    }
    private double getThetaAtDragStart() {
        return ThetaAtDragStart;
    }

    private void setThetaAtDragStart(double theta) {
        ThetaAtDragStart = theta;
    }

    private double getMouseThetaAtDragStart() {
        return MouseThetaAtDragStart;
    }

    private void setMouseThetaAtDragStart(double theta) {
        MouseThetaAtDragStart = theta;
    }

    /**
     * Returns the number of turns made - by mouse or programmatically via
     * <code>setValue</code>
     *
     * @return
     */
    public int getTurnCount() {
        return model.getTurnCount();
    }

    private void setTurnCount(int val) {
        model.setTurnCount(val);
    }

    /**
     * Sets the text for a JLabel displayed at the center of the dial. Note that
     * this will overlap displayed values if      <code>isDisplayValue</code. is
     * <code>true,/code>.
     *
     * @param str
     */
    public void setText(String str) {
        label.setText(str);
    }

    /**
     * Returns the JLabel's text
     *
     * @return
     */
    public String getText() {
        return label.getText();
    }

    /**
     * Displays the current value at the center of the dial
     *
     * @param flag
     */
    public void setDisplayValue(boolean flag) {
        displayValue = flag;
    }

    /**
     * Determines if values will be displayed at the center of the dial
     *
     * @return
     */
    public boolean isDisplayValue() {
        return displayValue;
    }

    private void paintBackground(Graphics2D g2d) {
        if (isOpaque()) {
            if (BackgroundFill == null) {
                this.getUI().update(g2d, this);
            } else {
                g2d.setPaint(BackgroundFill);
                g2d.fill(new java.awt.Rectangle(0, 0, getWidth(), getHeight()));
            }
        }
    }

    private void paintTicks(Graphics2D g2d) {
        if (getPaintTicks()) {
            Graphics2D g2d2 = (Graphics2D) g2d.create();
            float radius = Math.min(getWidth(), getHeight()) / 2;
            radius = radius - 0.1f * radius;
            int xoffset = getWidth() / 2;
            int yoffset = getHeight() / 2;
            g2d2.setColor(getLineColor());
            g2d2.setStroke(pen1);
            for (int k = 0; k <= getMajorTickSpacing(); k++) {
                g2d2.drawLine(xoffset, yoffset, (int) (xoffset + radius + Math.max(5, radius / 15)), yoffset);
                g2d2.rotate(2 * Math.PI / getMajorTickSpacing(), xoffset, yoffset);
            }
            g2d2.setStroke(pen2);
            for (int k = 0; k <= getMinorTickSpacing(); k++) {
                g2d2.drawLine(xoffset, yoffset, (int) (xoffset + radius + Math.max(2.5, radius / 20)), yoffset);
                g2d2.rotate(2 * Math.PI / getMinorTickSpacing(), xoffset, yoffset);
            }
            g2d2.dispose();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Background
        paintBackground(g2d);
        paintTicks(g2d);
        //Dial
        g2d.setColor(getLineColor());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(pen1);

        // Set up
        float diam = Math.min(getWidth(), getHeight());
        diam = diam - (0.1f * diam);
        float xoffset = (getWidth() - diam) / 2;
        float yoffset = (getHeight() - diam) / 2;
        Ellipse2D.Float circle = new Ellipse2D.Float(xoffset, yoffset, diam, diam);
        g2d.setColor(getLineColor());
        g2d.draw(circle);
        g2d.setPaint(Fill);
        g2d.fill(circle);

        g2d.setColor(getLineColor());

        if (displayValue) {
            FontMetrics metrics = g2d.getFontMetrics();
            String str = nf.format(getValue());
            g2d.drawString(str,
                    getWidth() / 2 - metrics.stringWidth(str) / 2,
                    getHeight() / 2 + metrics.getHeight() / 2);
        }

        //Notch
        Ellipse2D.Float circle2 = new Ellipse2D.Float(xoffset + diam - diam / 5f, yoffset + diam / 2 - diam / 15, diam / 7.5f, diam / 7.5f);
        g2d.rotate(-Theta, xoffset + diam / 2, yoffset + diam / 2);
        g2d.draw(circle2);
        g2d.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
        setThetaAtDragStart(getTheta());
        setMouseThetaAtDragStart(getTheta());
        mouseDragged(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
        double theta = mouseTheta(mouseX, mouseY);
        setMouseThetaAtDragStart(theta);
        setThetaAtDragStart(getTheta());
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {

        double oldTheta = getTheta();

        mouseX = me.getX();
        mouseY = me.getY();
        double thisTheta = mouseTheta(mouseX, mouseY);
        if (oldTheta == thisTheta) {
            return;
        }

        // This prevents theta jumping to the initial mouse position at drag
        // initiation via the mousePressed callback.
        // The size of the arc for this drag
        double thisArc = getMouseThetaAtDragStart() - thisTheta;
        // The new dial position
        double newTheta = arclength(getThetaAtDragStart(), thisArc);

        // calculate normallyClockWise - this will be true for clockwise rotations
        // over most of the unit circle BUT the logic will invert when crossing 0
        // and 2PI
        double sz = oldTheta - newTheta;
        boolean normallyClockWise = (sz > 0);
        int zeroCrossing = 0;
        if (normallyClockWise
                && oldTheta > 2 * Math.PI - Math.PI / 2
                && newTheta < Math.PI / 2) {
            // Therefore anti-clockwise crossing
            zeroCrossing = -1;

        } else if (!normallyClockWise
                && oldTheta < Math.PI / 2
                && newTheta > 2 * Math.PI - Math.PI / 2) {
            // Therefore clockwise crossing
            zeroCrossing = 1;
        }

        if (zeroCrossing == -1) {
            if (getTurnCount() > 0) {
                setTurnCount(getTurnCount() - 1);
            } else {
                return;
            }
        } else if (zeroCrossing == 1) {
            if (getTurnCount() + 1 < model.getAvailableTurns()) {
                setTurnCount(getTurnCount() + 1);
            } else {
                return;
            }
        }

        // Tidy up any rounding errors that could affect subsequent sums
        // around the crossing point
        if (newTheta < 0) {
            newTheta = 0;
        }
        if (newTheta > 2 * Math.PI) {
            newTheta = 2 * Math.PI;
        }

        setTheta(newTheta);

    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    private static double arclength(double theta1, double theta2) {
        double val = theta1 - theta2;
        if (val > 2 * Math.PI || val < 0) {
            val = val % (2 * Math.PI);
        }
        return val;
    }

    private double mouseTheta(double x, double y) {
        x = x - getWidth() / 2;
        y = getHeight() - y - getHeight() / 2;
        return getTheta2PI(x, y);
    }

    private static double getTheta2PI(double x, double y) {
        double sintheta = y / Math.hypot(x, y);
        double theta = Math.asin(sintheta);
        if (x <= 0 && y >= 0) {
            theta = Math.PI - theta;
        } else if (x <= 0 && y <= 0) {
            theta = Math.PI - theta;
        } else if (x >= 0 && y <= 0) {
            theta = 2 * Math.PI + theta;
        }
        return theta;
    }

    /**
     * @return the LineColor
     */
    public Color getLineColor() {
        return LineColor;
    }

    /**
     * @param LineColor the LineColor to set
     */
    public void setLineColor(Color LineColor) {
        this.LineColor = LineColor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    private class GJRoundedBoundedDataModel extends DefaultBoundedRangeModel {

        private int availableTurns = 1;
        private int turnCount = 0;

        public GJRoundedBoundedDataModel() {
        }

        public int getAvailableTurns() {
            return availableTurns;
        }

        public void setAvailableTurns(int t) {
            availableTurns = t;
        }

        public int getTurnCount() {
            return turnCount;
        }

        public void setTurnCount(int val) {
            turnCount = val;
        }

        @Override
        public String toString() {
            String str = super.toString();
            str = str.substring(0, str.length() - 2);
            str = str + String.format(", turns=%d]\n", availableTurns);
            return str;
        }
    }

    public static class BasicGJDialUI extends SliderUI {

        public static ComponentUI createUI(JComponent c) {
            return new BasicGJDialUI();
        }
    }
}
