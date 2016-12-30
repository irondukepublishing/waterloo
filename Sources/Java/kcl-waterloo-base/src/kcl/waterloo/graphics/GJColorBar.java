/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.graphics;

import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.math.ArrayUtils;

/**
 * Color bar. Provides a key for interpreting the color levels in a plot.
 *
 * @author ML
 */
public class GJColorBar extends GJRoi implements ActionListener {

    /**
     * The plot with which this color bar is associated.
     */
    private GJPlotInterface parentPlot;
    /**
     * Pop up menu. Publicly accessible via getPopup so user settable/editable.
     */
    private JPopupMenu popup;

    /**
     * Enum specifying possible orientations: VERTICAL or HORIZONTAL.
     */
    public enum ORIENTATION {

        /**
         * Vertically oriented.
         */
        VERTICAL,
        /**
         * Horizontally oriented.
         */
        HORIZONTAL
    }

    /**
     * Current orientation
     */
    private ORIENTATION orientation = ORIENTATION.VERTICAL;

    /**
     * Enum specifying possible label positions: CENTER or BORDER.
     */
    public enum LABELPOSITION {

        /**
         * Label for the Nth color will be drawn at the center of the color box.
         */
        CENTER,
        /**
         * Label for the Nth color will be drawn at the border of the Nth with
         * the N+1th color box.
         */
        BORDER
    }

    /**
     * Labels for the color boxes in the color bar.
     *
     */
    private String[] labels = null;
    /**
     * Position on the labels.
     */
    private LABELPOSITION labelPosition = LABELPOSITION.BORDER;
    private int labelInterval = 1;

    public GJColorBar() {
        super();
        setBorder(null);
        removeIcons();
        setBackgroundPainted(false);
        addPopUp();
        minSiz = 20;
    }

    /**
     * Factory method for creating an instance in the center of the specified
     * graph.
     *
     * @param gr the target graph
     * @return
     */
    public static GJColorBar createInstance(GJPlotInterface plot) {
        GJAbstractGraph gr = (GJAbstractGraph) plot.getParentGraph();
        Rectangle2D r = gr.getAxesBounds();
        return createInstance(plot, new Rectangle2D.Double(r.getX() + r.getWidth() - (0.15 * r.getWidth()),
                r.getY() + r.getHeight() - (0.5 * r.getHeight()),
                0.05 * r.getWidth(),
                0.4 * r.getHeight()));
    }

    /**
     * Factory method for creating an instance.
     *
     * @param plot
     * @param r a Rectangle2D in axes units
     * @return
     */
    public static GJColorBar createInstance(final GJPlotInterface plot, final Rectangle2D.Double r) {
        final GJAbstractGraph gr = (GJAbstractGraph) plot.getParentGraph();
        final GJColorBar colorbar = new GJColorBar();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                colorbar.parentPlot = plot;
                gr.add(colorbar);
                colorbar.setAxesRectangle(r);
                if (gr.getGraphContainer() != null) {
                    gr.getGraphContainer().addComponentListener(colorbar);
                    gr.getGraphContainer().addPropertyChangeListener(colorbar);
                }
                gr.addPropertyChangeListener(colorbar);
                colorbar.setFont(gr.getFont().deriveFont(8));
                gr.repaint();
            }
        });
        return colorbar;
    }

    /**
     * @return the parentPlot
     */
    public GJPlotInterface getParentPlot() {
        return parentPlot;
    }

    /**
     * @param parentPlot the parentPlot to set
     */
    public void setParentPlot(GJPlotInterface p) {
        if (parentPlot != null && !p.getParentGraph().equals(parentPlot.getParentGraph())) {
            ((GJAbstractGraph) p.getParentGraph()).add(this);
        }
        parentPlot = p;
    }

    /**
     * @return the orientation
     */
    public ORIENTATION getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(ORIENTATION orientation) {
        //ORIENTATION old = getOrientation();
        this.orientation = orientation;
        setPixelRectangle(new Rectangle2D.Double(getX(), getY(), getHeight(), getWidth()));
        //updateConstraints();
        revalidate();
        //this.firePropertyChange("orientation", old, orientation);
    }

    /**
     * @return the labels
     */
    public String[] getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    /**
     * @param labels the double[] values for the labels to set
     */
    public void setLabels(double[] labels) {
        setLabels(ArrayUtils.boxed(labels), "%3.2f");
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(double[] labels, String format) {
        setLabels(ArrayUtils.boxed(labels), format);
    }

    /**
     * @param labels the Double[] values for the labels to set
     */
    public void setLabels(Double[] labels) {
        setLabels(labels, "%3.2f");
    }

    /**
     * @param labels the Double[] values for the labels to set
     */
    public void setLabels(Double[] labels, String format) {
        this.labels = new String[labels.length];
        for (int k = 0; k < labels.length; k++) {
            if (labels[k].isNaN()) {
                this.labels[k] = "";
            } else {
                this.labels[k] = String.format(format, labels[k]);
            }
        }
    }

    /**
     * @return the labelPosition
     */
    public LABELPOSITION getLabelPosition() {
        return labelPosition;
    }

    /**
     * @param labelPosition the labelPosition to set
     */
    public void setLabelPosition(LABELPOSITION labelPosition) {
        this.labelPosition = labelPosition;
    }

    @Override
    public final void paintComponent(Graphics g) {
        if (parentPlot.getFill() != null && parentPlot.getFill().size() > 0) {
            Graphics2D g2 = (Graphics2D) g.create();

            paintBackground(g2);

            double inc;
            Rectangle2D bounds = getBounds();
            Rectangle2D r = new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

            FontMetrics metrics = g2.getFontMetrics();
            int maxStringWidth = 0;
            int stringAscent = metrics.getAscent();
            if (labels != null) {
                for (int k = 0; k < labels.length; k++) {
                    if (!labels[k].isEmpty()) {
                        maxStringWidth = Math.max(maxStringWidth, metrics.stringWidth(labels[k]));
                    }
                }
            }

            switch (orientation) {
                case VERTICAL:
                    inc = (double) getHeight() / (double) parentPlot.getMultiplexLength();
                    if (labels == null) {
                        r.setRect(0, r.getHeight() - inc, r.getWidth(), inc);
                    } else {
                        r.setRect(0, r.getHeight() - inc, Math.max(r.getWidth() / 4d, r.getWidth() - 1.2 * maxStringWidth), inc);
                    }
                    for (int k = 0; k < parentPlot.getMultiplexLength(); k++) {
                        g2.setPaint(parentPlot.getFill().get(k));
                        g2.fill(r);
                        if (labels != null && k < labels.length && (Math.IEEEremainder((double) k, (double) labelInterval) == 0)) {
                            if (!labels[k].isEmpty()) {
                                g2.setPaint(getForeground());
                                if (labelPosition == LABELPOSITION.CENTER) {
                                    g2.drawString(labels[k], (float) (r.getWidth() + 2d), (float) (r.getY() + inc - stringAscent / 2d));
                                } else {
                                    if (k < labels.length - 1) {
                                        g2.drawString(labels[k], (float) (r.getWidth() + 2d), (float) (r.getY() + stringAscent / 2d));
                                    }
                                }
                            }
                        }
                        r.setRect(0, r.getY() - inc, r.getWidth(), inc);
                    }
                    break;
                case HORIZONTAL:
                    inc = (double) getWidth() / (double) parentPlot.getMultiplexLength();
                    if (labels == null) {
                        r.setRect(0, 0, inc, getHeight());
                    } else {
                        r.setRect(0, 0, inc, getHeight() / 4d);
                    }
                    AffineTransform af = g2.getTransform();
                    for (int k = 0; k < parentPlot.getMultiplexLength(); k++) {
                        g2.setPaint(parentPlot.getFill().get(k));
                        g2.fill(r);
                        if (labels != null && k < labels.length && (Math.IEEEremainder((double) k, (double) labelInterval) == 0)) {
                            if (!labels[k].isEmpty()) {
                                g2.setPaint(getForeground());
                                if (labelPosition == LABELPOSITION.CENTER) {
                                    g2.rotate(Math.PI / 2,
                                            (float) (r.getX() + inc / 2d - stringAscent / 2d),
                                            (float) (r.getHeight() + 2d));
                                    g2.drawString(labels[k], (float) (r.getX() + inc / 2d - stringAscent / 2d),
                                            (float) (r.getHeight() + 2d));
                                } else {
                                    if (k < labels.length - 1) {
                                        g2.rotate(Math.PI / 2,
                                                (float) (r.getX() + inc - stringAscent / 2d),
                                                (float) (r.getHeight() + 2d));
                                        g2.drawString(labels[k], (float) (r.getX() + inc - stringAscent / 2d),
                                                (float) (r.getHeight() + 2d));
                                    }
                                }
                            }
                        }
                        r.setRect(r.getX() + inc, 0, inc, r.getHeight());
                        g2.setTransform(af);
                    }
            }
            g2.dispose();
        }
    }

    private void addPopUp() {
        popup = new JPopupMenu();
        JMenu menu = new JMenu("Alignment");
        JMenuItem menuItem = menu.add(new JMenuItem("Vertical"));
        menuItem.setActionCommand("Vertical");
        menuItem.addActionListener(this);
        menuItem = menu.add(new JMenuItem("Horizontal"));
        menuItem.setActionCommand("Horizontal");
        menuItem.addActionListener(this);
        popup.add(menu);

        menu = new JMenu("Color Scheme");
        popup.add(menu);

        MouseListener popupListener = new PopupListener();
        addMouseListener(popupListener);
        popup.addMouseListener(popupListener);
    }

    class PopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);

        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Vertical")) {
            setOrientation(ORIENTATION.VERTICAL);
        } else if (e.getActionCommand().equals("Horizontal")) {
            setOrientation(ORIENTATION.HORIZONTAL);
        }
    }

    /**
     * @return the labelInterval
     */
    public int getLabelInterval() {
        return labelInterval;
    }

    /**
     * @param labelInterval the labelInterval to set
     */
    public void setLabelInterval(int labelInterval) {
        this.labelInterval = labelInterval;
    }
}
