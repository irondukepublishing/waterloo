/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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
package kcl.waterloo.common.deploy;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;

/**
 * Abstract class for generating graphics scripts.
 *
 * <p>
 * AbstractDeployableGraphics2D is extended e.g. by PDEGraphics2D which provides
 * a concrete implementation for generating Processing script output.</p>
 *
 * <p>
 * Other subclasses may be added in the future to support other scripting
 * languages.</p>
 *
 *
 * <p>
 * Revisions:</p>
 * <p>
 * 14.05.2013 Change class name to avoid conflicts with third-party
 * packages.</p>
 * <p>
 * 27.05.2013 Do not clone parentGraphics in constructor</p>
 * <p>
 * 08.12.2013 Remove some left-over pde specific code</p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/pdegraphics2d/discussion/?source=navbar">
 * [Contact]</a>
 */
public abstract class AbstractDeployableGraphics2D extends Graphics2D {

    /**
     * Graphics2D instance supplied to an AbstractDeployableGraphics2D subclass
     * constructor.
     */
    protected Graphics2D parentGraphics;
    /**
     * ArrayList of Formatters representing the code for each picture. The
     * Formatter at element zero contains the code for the body of the main
     * draw() method (including, therefore, calls to functions represented in
     * later elements/pictures).
     */
    protected ArrayList<Formatter> pictures = new ArrayList<Formatter>();
    /**
     * A constant delay to add to the display time for each picture when
     * multiple pictures are created using the append method. This is useful,
     * particularly with web viewing to start an animation only once the image
     * is displayed.
     *
     * This also sets the interval between replays of the entire sequence.
     */
    private int actionDelay = 5000;
    /**
     * ArrayList of Integers representing the delay from the start of painting
     * to painting each picture in milliseconds. The actual delay will be the
     * sum of this value and @code actionDelay}.
     */
    protected ArrayList<Integer> latency = new ArrayList<Integer>();
    /**
     * If true, graphics will be drawn also to the parentGraphics instance.
     */
    private boolean alsoPaintToSource = true;

    /**
     * Super-constructor that should be called from sub-class constructors.
     *
     * @param g the parent Graphics2D object for the component to be painted.
     */
    protected AbstractDeployableGraphics2D(Graphics2D g) {
        parentGraphics = g;//Do not clone 1.0.0c
        latency.add(0, Integer.valueOf(0));
    }

    public boolean isAlsoPaintToSource() {
        return alsoPaintToSource;
    }

    public void setAlsoPaintToSource(boolean alsoPaintToSource) {
        this.alsoPaintToSource = alsoPaintToSource;
    }

    /**
     * @return the actionDelay
     */
    public int getActionDelay() {
        return actionDelay;
    }

    /**
     * @param actionDelay the actionDelay to set
     */
    public void setActionDelay(int actionDelay) {
        this.actionDelay = Math.max(actionDelay, 1);
    }

    public abstract void append(Component c, int elapsedTime);

    public abstract void append(Component c);

    protected long startTime = System.currentTimeMillis();

    /**
     * @return the code
     */
    public String getCode() {
        return pictures.get(0).toString();
    }

    public void write(File f) throws IOException {
        FileOutputStream fout = null;
        OutputStreamWriter out = null;
        try {
            fout = new FileOutputStream(f);
            out = new OutputStreamWriter(fout, "UTF-8");
            out.write(getCode());
        } catch (IOException ex) {
            throw (ex);
        } finally {
            if (out != null) {
                out.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }

    /**
     * Writes the generated code to
     *
     * @param f the file name
     * @throws IOException
     */
    public void write(String f) throws IOException {
        write(new File(f));
    }

    /**
     * Call this routine in the painting mechanism of an AWT/Swing hierarchy.
     * How a concrete class responds is an implementation detail for the
     * subclass. Typically, this will simply be used to add a comment to the
     * generated code in a custom component e.g.
     *
     * <p>
     * public void paintComponent(Graphics g){<br>
     * if (g instanceof AbstractDeployableGraphics2D) {<br>
     * ((AbstractDeployableGraphics2D)g).setPaintObject(this);<br>
     * }<br>
     * }<br></p>
     *
     * @param paintObject the paintObject to set
     */
    public abstract void setPaintObject(Object paintObject);

    /**
     * Sets a string that will be added to the code output as a comment. Do not
     * added comment markers - these should be added by any concrete
     * implementations automatically and will be specific to the code language
     * being used.
     *
     * @param comment the comment to set as a String
     */
    public abstract void addComment(String comment);

    /**
     * Inserts user-specified code at the start of the generated code, but after
     * any automatically generated declarations (which are therefore potentially
     * available for use in that code).
     *
     * @param code fully formatted code string
     */
    public abstract void prependCode(String code);

    /**
     * Inserts user-specified code into the coded output at the current
     * location.
     *
     * @param code fully formatted code string
     */
    public abstract void addCode(String code);

    /**
     * Adds user-specified code to the end of the generated code.
     *
     * @param code the code to append
     */
    public abstract void appendCode(String code);

    /**
     * Implemented in some Graphics2D subclasses e.g. com.sun.java.Graphics2D.
     * This is not part of the java.awt.Graphics2D API.
     *
     * @param img
     * @param dx
     * @param dy
     * @param sx
     * @param sy
     * @param width
     * @param height
     * @param bgcolor
     * @param observer
     * @return true or false
     */
    public abstract boolean copyImage(Image img, int dx, int dy, int sx, int sy,
            int width, int height, Color bgcolor, ImageObserver observer);

    /**
     * AWT Graphics and Graphics2D classes generally implement draw3DRect
     * through calls to other methods. Consequently there is no need to
     * implement any code here explicitly <strong>as long as</strong> the
     * concrete implementation of Graphics2D in parentGraphics works similarly,
     * e.g. by a call to the Graphics2D methods.
     */
    @Override
    public final void draw3DRect(int x, int y, int width, int height, boolean raised) {
        parentGraphics.draw3DRect(x, y, width, height, raised);
    }

    /**
     * AWT Graphics and Graphics2D classes generally implement fill3DRect
     * through calls to other methods. Consequently there is no need to
     * implement any code here explicitly <strong>as long as</strong> the
     * concrete implementation of Graphics2D in parentGraphics works similarly,
     * e.g. by a call to the Graphics2D methods.
     */
    @Override
    public final void fill3DRect(int x, int y, int width, int height, boolean raised) {
        parentGraphics.fill3DRect(x, y, width, height, raised);
    }

    @Override
    public final void setTransform(AffineTransform Tx) {
        parentGraphics.setTransform(Tx);
    }

    @Override
    public AffineTransform getTransform() {
        return parentGraphics.getTransform();
    }

    @Override
    public Stroke getStroke() {
        return parentGraphics.getStroke();
    }

    @Override
    public void clip(Shape s) {
        parentGraphics.clip(s);
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        return parentGraphics.getFontRenderContext();
    }

    @Override
    public Color getColor() {
        return parentGraphics.getColor();
    }

    @Override
    public void setColor(Color c) {
        parentGraphics.setColor(c);
    }

    @Override
    public void setPaintMode() {
        parentGraphics.setPaintMode();
    }

    @Override
    public final Font getFont() {
        return parentGraphics.getFont();
    }

    @Override
    public void setFont(Font font) {
        parentGraphics.setFont(font);
    }

    @Override
    public Rectangle getClipBounds() {
        return parentGraphics.getClipBounds();
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {
        parentGraphics.clipRect(x, y, width, height);
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        parentGraphics.setClip(x, y, width, height);
    }

    @Override
    public Shape getClip() {
        return parentGraphics.getClip();
    }

    @Override
    public void setClip(Shape clip) {
        parentGraphics.setClip(clip);
    }

    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return parentGraphics.hit(rect, s, onStroke);
    }

    @Override
    public void setComposite(Composite comp) {
        parentGraphics.setComposite(comp);
    }

    @Override
    public void setPaint(Paint paint) {
        parentGraphics.setPaint(paint);
    }

    @Override
    public void setStroke(Stroke s) {
        parentGraphics.setStroke(s);
    }

    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        parentGraphics.setRenderingHint(hintKey, hintValue);
    }

    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return parentGraphics.getRenderingHint(hintKey);
    }

    @Override
    public void setRenderingHints(Map<?, ?> hints) {
        parentGraphics.setRenderingHints(hints);
    }

    @Override
    public void addRenderingHints(Map<?, ?> hints) {
        parentGraphics.addRenderingHints(hints);
    }

    @Override
    public RenderingHints getRenderingHints() {
        return parentGraphics.getRenderingHints();
    }

    @Override
    public void setXORMode(Color c1) {
        parentGraphics.setXORMode(c1);
    }

    @Override
    public FontMetrics getFontMetrics(Font f) {
        return parentGraphics.getFontMetrics(f);
    }

    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        parentGraphics.copyArea(x, y, width, height, dx, dy);
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        return parentGraphics.getDeviceConfiguration();
    }

    @Override
    public void translate(int x, int y) {
        parentGraphics.translate(x, y);
    }

    @Override
    public void translate(double tx, double ty) {
        parentGraphics.translate(tx, ty);
    }

    @Override
    public void rotate(double theta) {
        parentGraphics.rotate(theta);
    }

    @Override
    public void rotate(double theta, double x, double y) {
        parentGraphics.rotate(theta, x, y);
    }

    @Override
    public void scale(double sx, double sy) {
        parentGraphics.scale(sx, sy);
    }

    @Override
    public void shear(double shx, double shy) {
        parentGraphics.shear(shx, shy);
    }

    @Override
    public void transform(AffineTransform Tx) {
        parentGraphics.transform(Tx);
    }

    @Override
    public Paint getPaint() {
        return parentGraphics.getPaint();
    }

    @Override
    public Composite getComposite() {
        return parentGraphics.getComposite();
    }

    @Override
    public void setBackground(Color color) {
        parentGraphics.setBackground(color);
    }

    @Override
    public Color getBackground() {
        return parentGraphics.getBackground();
    }

    /**
     *
     * @param s
     */
    @Override
    public void draw(Shape s) {
        if (alsoPaintToSource) {
            parentGraphics.draw(s);
        }
    }

    @Override
    public void drawString(String str, int x, int y) {
        if (alsoPaintToSource) {
            parentGraphics.drawString(str, x, y);
        }
    }

    @Override
    public void drawString(String str, float x, float y) {
        if (alsoPaintToSource) {
            parentGraphics.drawString(str, x, y);
        }
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        if (alsoPaintToSource) {
            parentGraphics.drawString(iterator, x, y);
        }
    }

    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, float y) {
        if (alsoPaintToSource) {
            parentGraphics.drawString(iterator, x, y);
        }
    }

    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        if (alsoPaintToSource) {
            parentGraphics.drawGlyphVector(g, x, y);
        }
    }

    @Override
    public void fill(Shape s) {
        if (alsoPaintToSource) {
            parentGraphics.fill(s);
        }
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        if (alsoPaintToSource) {
            parentGraphics.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        if (alsoPaintToSource) {
            parentGraphics.fillRect(x, y, width, height);
        }
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        if (alsoPaintToSource) {
            parentGraphics.fillRect(x, y, width, height);
        }
    }

    @Override
    public void clearRect(int x, int y, int width, int height) {
        if (alsoPaintToSource) {
            parentGraphics.clearRect(x, y, width, height);
        }
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (alsoPaintToSource) {
            parentGraphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (alsoPaintToSource) {
            parentGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        }
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        if (alsoPaintToSource) {
            parentGraphics.drawOval(x, y, width, height);
        }
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        if (alsoPaintToSource) {
            parentGraphics.fillOval(x, y, width, height);
        }
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        if (alsoPaintToSource) {
            parentGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
        }
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        if (alsoPaintToSource) {
            parentGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
        }
    }

    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        if (alsoPaintToSource) {
            parentGraphics.drawPolyline(xPoints, yPoints, nPoints);
        }
    }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        if (alsoPaintToSource) {
            parentGraphics.drawPolyline(xPoints, yPoints, nPoints);
        }
    }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        if (alsoPaintToSource) {
            parentGraphics.fillPolygon(xPoints, yPoints, nPoints);
        }
    }

    @Override
    public void drawBytes(byte[] data, int offset, int length, int x, int y) {
        if (alsoPaintToSource) {
            parentGraphics.drawBytes(data, offset, length, x, y);
        }
    }

    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return parentGraphics.drawImage(img, x, y, width, height, null);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), bgcolor, observer);
    }

    @Override
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return parentGraphics.drawImage(img, x, y, width, height, bgcolor, null);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        return drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null, observer);
    }

    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
        return parentGraphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, null);
    }

    @Override
    public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
        return parentGraphics.drawImage(img, xform, null);
    }

    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        parentGraphics.drawImage(img, op, x, y);
    }

    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        parentGraphics.drawRenderedImage(img, xform);
    }

    @Override
    public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
        parentGraphics.drawRenderableImage(img, xform);
    }

    @Override
    public void dispose() {
        parentGraphics.dispose();
    }

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
     *
     *
     */
    protected static class GJPathSegmentInfo {

        private int type = -1;
        private double[] data = new double[6];

        public GJPathSegmentInfo() {
            type = PathIterator.SEG_CLOSE + PathIterator.SEG_CUBICTO;
        }

        public GJPathSegmentInfo(int t, double[] d) {
            type = t;
            setData(d);
        }

        /**
         * @return the type
         */
        public final int getType() {
            return type;
        }

        /**
         * @param t the type to set
         */
        public final void setType(int t) {
            this.type = t;
        }

        /**
         * @return the data
         */
        public final double[] getData() {
            return data.clone();
        }

        /**
         * @param d
         */
        public final void setData(double[] d) {
            data = d.clone();
        }
    }
}
