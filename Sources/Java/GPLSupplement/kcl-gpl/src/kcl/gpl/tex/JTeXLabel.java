 /* 
 * This code is part of Project Waterloo from King's College London
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
 */
package kcl.gpl.tex;

import kcl.waterloo.tex.TexSupportInterface;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class JTeXLabel extends JLabel
        implements PropertyChangeListener, ComponentListener, TexSupportInterface {

    boolean backgroundPainted = true;
    double textRotation = 0d;
    TeXFormula formula = new TeXFormula("");
    Icon icon = null;
    Double textSize = 18d;

    public JTeXLabel() {
        addPropertyChangeListener(this);
        addComponentListener(this);
    }

    private JTeXLabel(String str) {
        setText(str);
    }

    public static JTeXLabel createInstance(final String str) {
        JTeXLabel label = new JTeXLabel(str);
        label.addPropertyChangeListener(label);
        label.addComponentListener(label);
        label.refresh();
        return label;
    }

    @Override
    public final void setText(String str) {
        String old = getText();
        super.setText(str);
        formula = new TeXFormula(str);
        firePropertyChange("text", old, getText());
    }

    @Override
    public void setBackground(Color color) {
        Color old = getBackground();
        super.setBackground(color);
        firePropertyChange("color", old, getBackground());
    }

    public TeXFormula getFormula() {
        return formula;
    }

    public final int getTextSize() {
        int sz;
        if (textSize.equals(Double.NaN)) {
            sz = getHeight() / 2;
        } else {
            sz = textSize.intValue();
        }
        return sz;
    }

    public void setTextSize(double v) {
        textSize = new Double(v);
        repaint();
    }

    public double getTextRotation() {
        return textRotation;
    }

    public void setTextRotation(double rot) {
        textRotation = rot;
        repaint();
    }

    public void setBackgroundPainted(boolean flag) {
        backgroundPainted = flag;
        repaint();
    }

    public boolean isBackgroundPainted() {
        return backgroundPainted;
    }

    @SuppressWarnings(value = "unchecked")
    protected void paintBackground(Graphics2D g2) {
        if (isBackgroundPainted()) {
            g2.setColor(getBackground());
            g2.fill(g2.getClipBounds());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (icon != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            paintBackground(g2);
            if (textRotation != 0) {
                g2.rotate(textRotation, this.getWidth() / 2, this.getHeight() / 2);
            }
            icon.paintIcon(this, g2, this.getWidth() / 2 - icon.getIconWidth() / 2,
                    this.getHeight() / 2 - icon.getIconHeight() / 2);
            g2.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh();
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        refresh();
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentShown(ComponentEvent ce) {
        refresh();
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
    }

    private void refresh() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (formula != null) {
                    icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, getTextSize());
                    repaint();
                }
            }
        });
    }

    @Override
    public boolean isTeXSupported() {
        return true;
    }

    @Override
    public BufferedImage getImage() {
        if (formula != null) {
            BufferedImage im = (BufferedImage) formula.createBufferedImage(TeXConstants.STYLE_DISPLAY, getTextSize(), Color.black, Color.white);
            return im;
        } else {
            return null;
        }
    }

    @Override
    public JLabel getLabel() {
        if (formula != null) {
            BufferedImage im = (BufferedImage) formula.createBufferedImage(TeXConstants.STYLE_DISPLAY, getTextSize(), Color.black, Color.white);
            return new JLabel(new ImageIcon(im));
        } else {
            return new JLabel();
        }
    }
}
