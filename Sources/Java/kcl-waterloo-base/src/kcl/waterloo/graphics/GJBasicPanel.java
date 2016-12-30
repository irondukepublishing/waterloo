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
package kcl.waterloo.graphics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.RepaintManager;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.common.deploy.AbstractDeployableGraphics2D;
import kcl.waterloo.actions.ActionServices;
import kcl.waterloo.export.ExportFactory;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJBasicPanel extends JXPanel implements ContainerListener, ComponentListener, Printable {

    boolean backgroundPainted = false;
    double ID = Double.NaN;

    public GJBasicPanel() {
        this(true);
    }

    public GJBasicPanel(LayoutManager layout) {
        this(true);
        setLayout(layout);
    }

    public GJBasicPanel(boolean flag) {
        super(flag);
        setOpaque(false);
        setBackground(Color.white);
    }

    public void setID(double n) {
        ID = n;
    }

    public double getID() {
        return ID;
    }

    /**
     * Returns true when the method is called on the EDT. Useful in systems that
     * provide a mechanism to refer all method calls for an instance to the EDT
     * e.g. via javaObjectEDT in MATLAB.
     *
     * @return true when called on the EDT
     */
    public boolean isOnEDT() {
        return EventQueue.isDispatchThread();
    }

    /**
     * Defines whether or not the background painted by this component.When
     * background painting is disabled, background painting is deferred to the
     * parent.
     *
     * @return true if background is painted, false otherwise
     * @see #setBackgroundPainted(boolean)
     * @see #getBackground()
     */
    public boolean isBackgroundPainted() {
        return backgroundPainted;
    }

    /**
     * <p>
     * Enables or disables the painting of background depending on the value of
     * the parameter. Background painting is enabled by default.</p>
     *
     * @param backPainted if true, axis labels are painted
     * @see #isBackgroundPainted()
     * @see #setBackground(Color)
     */
    public void setBackgroundPainted(boolean backPainted) {
        this.backgroundPainted = backPainted;
    }

    public static ArrayList<JComponent> getAllComponents(JComponent c) {
        ArrayList<JComponent> arr = new ArrayList<JComponent>();
        JComponent c2;
        arr.add(c);
        for (int k = 0; k < c.getComponentCount(); k++) {
            c2 = (JComponent) c.getComponent(k);
            if (c2.getComponentCount() > 0) {
                ArrayList<JComponent> arr2 = getAllComponents(c2);
                arr.addAll(arr2);
            } else {
                arr.add(c2);
            }
        }
        return arr;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof AbstractDeployableGraphics2D) {
            ((AbstractDeployableGraphics2D) g).setPaintObject(this);
        }
        super.paintComponent(g);
    }

    /**
     *
     * <p>
     * This method is called by the subclasses to paint the background.</p>
     *
     * <p>
     * Fills the background with a solid color as defined by
     * {@code #BackgroundColor1}.</p>
     *
     * <p>
     * Subclasses can implement an {@code #isBackgroundPainted()} method to
     * determine whether this superclass method should be called.</p>
     *
     * <p>
     * It is recommended that subclasses honor the contract defined by
     * {@code #isBackgroundPainted()} and also therefore provide a
     * {@code #setBackgroundPainted(boolean)} method.</p>
     *
     * @param g2 the graphics surface on which the background must be drawn
     */
    @SuppressWarnings(value = "unchecked")
    final void paintBackground(Graphics2D g2) {
        if (isBackgroundPainted()) {
            //Graphics2D g = (Graphics2D) g2.create();
            Painter<JXPanel> p = this.<JXPanel>getBackgroundPainter();
            if (p != null) {
                p.<JXPanel>paint(g2, this, getWidth(), getHeight());

            } else {
                Color c = g2.getColor();
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(c);
            }
            //g.dispose();
        }
    }

    public void print() {
        PrinterJob job = PrinterJob.getPrinterJob();
        //HashPrintRequestAttributeSet hashSet = new HashPrintRequestAttributeSet();
        job.setPrintable(this);
        boolean flag = job.printDialog();
        if (flag) {
            try {
                job.print();
            } catch (PrinterException ex) {
            }
        }

    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        RepaintManager currentManager = RepaintManager.currentManager(this);
        boolean dB = currentManager.isDoubleBufferingEnabled();
        currentManager.setDoubleBufferingEnabled(false);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(pf.getImageableX(), pf.getImageableY());
        double xscale = pf.getImageableWidth() / (double) getWidth();
        double yscale = pf.getImageableHeight() / (double) getHeight();
        double scale = Math.min(xscale, yscale);
        g2.translate((pf.getImageableWidth() - getWidth() * scale) / 2d, (pf.getImageableHeight() - getHeight() * scale) / 2d);
        g2.scale(scale, scale);
        super.printAll(g);
        currentManager.setDoubleBufferingEnabled(dB);
        return PAGE_EXISTS;
    }

    /**
     * Paints the component into a {@code BufferedImage}
     *
     * @return a BufferedImage
     */
    public BufferedImage getImage() {
        if (this.getGraphicsConfiguration() != null) {
            BufferedImage img = this.getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
            Graphics2D g2 = img.createGraphics();
            paint(g2);
            return img;
        } else {
            return null;
        }
    }

    /**
     * Returns a {@code Transferable} for this panel for use in CCP/DnD.
     *
     * @return custom {@code Transferable} subclass
     */
    public LocalTransferable getTransferable() {
        return getTransferable(true);
    }

    /**
     * Returns a {@code Transferable} for this panel for use in CCP/DnD. If
     * vectorFlag is true, the returned {@code Transferable} will support both
     * image and vector graphics. Otherwise, image data only.
     *
     *
     * @param vectorFlag
     * @return custom {@code Transferable} subclass
     */
    public LocalTransferable getTransferable(boolean vectorFlag) {
        return new LocalTransferable(this, vectorFlag);
    }

    @Override
    public void componentAdded(ContainerEvent e) {
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    public class LocalTransferable implements Transferable, ClipboardOwner {

        private final DataFlavor svgFlavor = new DataFlavor("image/svg+xml", "Scalable Vector Format");
        private final DataFlavor pdfFlavor = new DataFlavor("application/pdf", "Portable Document Format (PDF)");
        private final DataFlavor epsFlavor = new DataFlavor("application/postscript", "Encapsulated PostScript (EPS)");
        private final DataFlavor psFlavor = new DataFlavor("application/postscript", "PostScript (PS)");

        private final DataFlavor textFlavor = DataFlavor.stringFlavor;
        GJBasicPanel panel;
        boolean vectorFlag;
        /**
         * Buffer used for svg - generate only once per instance.
         */
        private String svgData = null;

        LocalTransferable(GJBasicPanel p, boolean flag) {
            super();
            panel = p;
            vectorFlag = flag;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            if (vectorFlag) {
                ArrayList<DataFlavor> flavors = new ArrayList<DataFlavor>();
//                if (((Boolean) GJDefaults.getMap().get("Clipboard.formatSVG")).booleanValue()) {
//                    flavors.add(svgFlavor);
//                }
//                if (((Boolean) GJDefaults.getMap().get("Clipboard.formatSVGAsText")).booleanValue()) {
//                    flavors.add(textFlavor);
//                }
                if (((Boolean) GJDefaults.getMap().get("Clipboard.formatPDF")).booleanValue()) {
                    flavors.add(pdfFlavor);
                }
                if (((Boolean) GJDefaults.getMap().get("Clipboard.formatPS")).booleanValue()) {
                    flavors.add(psFlavor);
                }
                if (((Boolean) GJDefaults.getMap().get("Clipboard.formatEPS")).booleanValue()) {
                    flavors.add(epsFlavor);
                }
                flavors.add(DataFlavor.imageFlavor);
                DataFlavor[] arr = new DataFlavor[flavors.size()];
                arr = flavors.toArray(arr);
                return arr;
            } else {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor df) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (DataFlavor flavor : flavors) {
                if (flavor.equals(df)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object getTransferData(DataFlavor df) throws IOException {
            if (df.equals(DataFlavor.imageFlavor)) {
                return panel.getImage();
            } else if (df.equals(svgFlavor) && ((Boolean) GJDefaults.getMap().get("Clipboard.formatSVG")).booleanValue()) {
                doSVG();
                return new ByteArrayInputStream(svgData.getBytes("UTF-8"));
            } else if (df.equals(textFlavor) && ((Boolean) GJDefaults.getMap().get("Clipboard.formatSVGAsText")).booleanValue()) {
                doSVG();
                return svgData;
            } else if (df.equals(pdfFlavor) && ((Boolean) GJDefaults.getMap().get("Clipboard.formatPDF")).booleanValue()) {
                if (ActionServices.isExportFactoryPresent()) {
                    doSVG();
                    return ExportFactory.convertSVG2PDF(svgData);
                } 
            } else if (df.equals(psFlavor) && ((Boolean) GJDefaults.getMap().get("Clipboard.formatPS")).booleanValue()) {
                if (ActionServices.isExportFactoryPresent()) {
                    return new ByteArrayInputStream(ExportFactory.saveAsPS(panel).toByteArray());
                }
            } else if (df.equals(epsFlavor) && ((Boolean) GJDefaults.getMap().get("Clipboard.formatEPS")).booleanValue()) {
                if (ActionServices.isExportFactoryPresent()) {
                    return new ByteArrayInputStream(ExportFactory.saveAsEPS(panel).toByteArray());
                } 
            } 
            return null;
        }

        @Override
        public void lostOwnership(Clipboard clpbrd, Transferable t) {
        }

        private void doSVG() throws IOException {
            if (svgData == null) {
                svgData = ExportFactory.saveAsSVG(panel);
            }
        }
    }
}
