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
package kcl.waterloo.deploy.image;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;

/**
 * ImagePanel provides support for viewing image files.
 *
 * Still being developed.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ImagePanel extends JPanel {

    final static boolean isQuartz = System.getProperty("os.name").matches("Mac OS X") && System.getProperty("apple.awt.graphics.UseQuartz").matches("true");
    static boolean isQuality = true;

    public ImagePanel(String s) {
        this(new File(s));
    }

    public ImagePanel(File file) {
        setLayout(new BorderLayout());

        if (file.getName().endsWith(".svg")) {
            Component canvas = new JSVGCanvas();
            JSVGCanvas svgCanvas = (JSVGCanvas) canvas;
            svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
                @Override
                public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
                }

                @Override
                public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
                }
            });
            svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
                @Override
                public void gvtBuildStarted(GVTTreeBuilderEvent e) {
                }

                @Override
                public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                }
            });
            svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
                @Override
                public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                }

                @Override
                public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                }
            });
            svgCanvas.setRecenterOnResize(true);
            svgCanvas.setURI(file.toURI().toString());
            add(svgCanvas, BorderLayout.CENTER);
            svgCanvas.validate();
        } else if (ImageSupport.isImageFormatSupported(file)) {
            Component canvas = new ImageCanvas(file);
            canvas.addComponentListener((ImageCanvas) canvas);
            add(canvas, BorderLayout.CENTER);
            repaint();
        }
    }

    private class ImageCanvas extends JPanel implements ComponentListener {

        private File file;
        private BufferedImage image;
        double aspectRatio = -1d;
        private BufferedImage buffer = null;
        private int requiredHeight = -1;
        private int requiredWidth = -1;

        public ImageCanvas(File file) {
            this.file = file;
        }

        @Override
        public void componentResized(ComponentEvent ce) {
            rescale();
        }

        @Override
        public void componentMoved(ComponentEvent ce) {
        }

        @Override
        public void componentShown(ComponentEvent ce) {
            rescale();
        }

        @Override
        public void componentHidden(ComponentEvent ce) {
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buffer, (getWidth() - requiredWidth) / 2,
                    (getHeight() - requiredHeight) / 2,
                    requiredWidth, requiredHeight, this);
        }

        public final void rescale() {

            // Load the image if needed
            if (image == null) {
                try {
                    image = ImageIO.read(file);
                } catch (IOException ex) {
                }
            }

            // Get required dimesions
            if (image.getWidth() != -1) {
                aspectRatio = (double) image.getWidth() / (double) image.getHeight();
            }

            requiredHeight = (int) (getWidth() / aspectRatio);
            requiredWidth = (int) (requiredHeight * aspectRatio);

            if (requiredHeight > getHeight() || requiredWidth > getWidth()) {
                requiredWidth = (int) (getHeight() * aspectRatio);
                requiredHeight = (int) (requiredWidth / aspectRatio);
            }

            // User slower rescaling for scaling < 0.5 (except on Mac Quartz which manages anyway).
            if (!isQuartz && (buffer == null || requiredWidth < buffer.getWidth() / 2 || requiredWidth > buffer.getWidth() * 2)) {
                try {
                    image = ImageIO.read(file);
                    buffer = this.getGraphicsConfiguration().createCompatibleImage(requiredWidth,
                            requiredHeight, Transparency.TRANSLUCENT);
                    Graphics2D g = buffer.createGraphics();
                    g.drawImage(image.getScaledInstance(requiredWidth, requiredHeight, Image.SCALE_SMOOTH), 0, 0, requiredWidth, requiredHeight, this);
                    g.dispose();
                    image = buffer;
                    repaint();
                    return;
                } catch (IOException ex) {
                }
            }

            // Rescale the image in buffer
            buffer = this.getGraphicsConfiguration().createCompatibleImage(requiredWidth,
                    requiredHeight, Transparency.TRANSLUCENT);
            Graphics2D g = buffer.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(image, 0, 0, requiredWidth, requiredHeight, this);
            g.dispose();
            repaint();
        }
    }

}
