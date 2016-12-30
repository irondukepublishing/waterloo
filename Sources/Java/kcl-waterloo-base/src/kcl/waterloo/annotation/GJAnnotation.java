 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.annotation;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.SwingConstants;
import kcl.waterloo.defaults.GJDefaults;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.marker.GJMarker;

/**
 * GJAnnotation is an abstract class that provides concrete internal classes to
 * annotate parentGraph containers and static factory methods to construct them.
 *
 * Annotations can be added to parentGraph containers by calling the container's
 * {@code add} method (in which case the position of the annotation will be set
 * in the coordinate space of that container's View) or by calling the
 * {@code add} method of a parentGraph layer in the View of a container (in
 * which case the position of the annotation will be set in that layer's
 * coordinate space).
 *
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAnnotation
        implements GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> {

    /**
     * Specifies the parentGraph whose coordinate space is to be used to
     * position this annotation. If null, the View will be used,
     */
    private GJGraphInterface parentGraph = null;
    /**
     * text to render.
     */
    private String text = "";
    /**
     * GJMarker instance - used as a wrapper for a Path2D object.
     */
    private GJMarker marker = null;
    private GJMarker extra = null;
    /**
     * Stroke to be used for drawing.
     */
    private Stroke lineStroke = null;
    /**
     * Line color to be used for drawing.
     */
    private Paint lineColor = (Paint) GJDefaults.getMap().get("GJAnnotation.lineColor");
    /**
     * text color to be used for drawing.
     */
    private Paint textColor = (Paint) GJDefaults.getMap().get("GJAnnotation.textColor");
    /**
     * text color to be used for drawing.
     */
    private Paint textBackground = (Paint) GJDefaults.getMap().get("GJAnnotation.textBackground");
    /**
     * fill color to be used. Null for no fill.
     */
    private Paint fill = (Paint) GJDefaults.getMap().get("GJAnnotation.fill");
    /**
     * For text annotations only, sets the left hand X-position
     */
    private double xData = 0;
    /**
     * For text annotations only, sets the Y-position
     */
    private double yData = 0;
    /**
     * User-settable fromString used to identity this annotation.
     */
    private String name = "";
    /**
     * Font used for text.
     */
    private Font font = new Font("Sans Serif", Font.PLAIN, 10);
    /**
     * For line annotations, {@code lineTextPosition} sets the position of the
     * text relative to the bounding rectangle of the line.
     * <p>
     * Recognized values are:</p>
     * <p>
     * SwingConstants.NORTH - centers the text at the top</p>
     * <p>
     * SwingConstants.NORTH_EAST - centers the text at the top-right corner</p>
     * <p>
     * SwingConstants.NORTH_WEST - centers the text at the top-left corner</p>
     * <p>
     * SwingConstants.SOUTH - centers the text at the bottom</p>
     * <p>
     * SwingConstants.SOUTH_EAST - centers the text at the bottom-right
     * corner</p>
     * <p>
     * SwingConstants.SOUTH_WEST - centers the text at the bottom-left
     * corner</p>
     * <p>
     * SwingConstants.LEADNG - puts the text to the left of center</p>
     * <p>
     * SwingConstants.TRAILING - puts the text to the right of center</p>
     *
     */
    private int lineTextPosition = SwingConstants.TOP;

    /**
     * Constructor.
     *
     */
    public GJAnnotation() {
    }

    /**
     * Returns a clone of the Path2D object.
     *
     * @return the marker
     */
    @Override
    public Path2D getPath() {
        if (marker == null) {
            return null;
        } else {
            return getMarker().getPath();
        }
    }

    /**
     * Sets the Path2D object.
     *
     * @param p
     */
    @Override
    public void setPath(Path2D p) {
        setMarker(new GJMarker(p));
    }

    /**
     * Returns the parentGraph reference for setting the coordinate system for
     * this annotation.
     *
     * @return the plot
     */
    @Override
    public GJGraphInterface getParentGraph() {
        return parentGraph;
    }

    /**
     * Sets the Path2D object.
     *
     * @param p
     */
    @Override
    public void setExtra(GJMarker p) {
        extra = p;
    }

    /**
     * Returns the parentGraph reference for setting the coordinate system for
     * this annotation.
     *
     * @return the plot
     */
    @Override
    public GJMarker getExtra() {
        return extra;
    }

    /**
     * setParentGraph method. This is called when an annotation is added using
     * the {@code add} method of a parentGraph - which is recommended - so need
     * not be called directly by the user.
     *
     * @param gr
     */
    @Override
    public void setParentGraph(GJGraphInterface gr) {
        parentGraph = gr;
    }

    /**
     * @return the lineStroke
     */
    @Override
    public Stroke getLineStroke() {
        return lineStroke;
    }

    /**
     *
     */
    @Override
    public void setLineStroke(Stroke LineStroke) {
        this.lineStroke = LineStroke;
    }

    /**
     * @return the lineColor
     */
    @Override
    public Paint getLineColor() {
        return lineColor;
    }

    /**
     */
    @Override
    public void setLineColor(Paint LineColor) {
        this.lineColor = LineColor;
    }

    /**
     * @return the Font
     */
    @Override
    public Font getFont() {
        return font;
    }

    /**
     * @param Font the Font to set
     */
    @Override
    public void setFont(Font Font) {
        this.font = Font;
    }

    /**
     * @return the fill
     */
    @Override
    public Paint getFill() {
        return fill;
    }

    /**
     */
    @Override
    public void setFill(Paint Fill) {
        this.fill = Fill;
    }

    /**
     * @return the marker
     */
    @Override
    public GJMarker getMarker() {
        return marker;
    }

    /**
     */
    @Override
    public void setMarker(GJMarker Marker) {
        this.marker = Marker;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     */
    @Override
    public void setName(String Name) {
        this.name = Name;
    }

    /**
     * @return the text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     */
    @Override
    public void setText(String Text) {
        this.text = Text;
    }

    /**
     * @return the X
     */
    @Override
    public double getXData() {
        return xData;
    }

    /**
     * @param X the X to set
     */
    @Override
    public void setXData(double X) {
        this.xData = X;
    }

    /**
     * @return the Y
     */
    @Override
    public double getYData() {
        return yData;
    }

    /**
     * @param Y the Y to set
     */
    @Override
    public void setYData(double Y) {
        this.yData = Y;
    }

    /**
     * @return the textColor
     */
    @Override
    public Paint getTextColor() {
        return textColor;
    }

    /**
     * Sets the textColor
     *
     */
    @Override
    public void setTextColor(Paint TextColor) {
        this.textColor = TextColor;
    }

    /**
     * @return the lineTextPosition
     */
    public int getLineTextPosition() {
        return lineTextPosition;
    }

    /**
     * @param pos the lineTextPosition to set
     */
    public void setLineTextPosition(int pos) {
        this.lineTextPosition = pos;
    }

    @Override
    public void remove() {
        if (this.getParentGraph() != null && this.getParentGraph().getGraphContainer() != null) {
            this.getParentGraph().getGraphContainer().
                    getAnnotations().remove(this);
        }
    }

    @Override
    public void remove(String name) {
        if (this.getParentGraph() != null && this.getParentGraph().getGraphContainer() != null) {
            for (GJAnnotationInterface<Path2D, GJGraphInterface, Stroke, Paint, Font> a : this.getParentGraph().getGraphContainer().getAnnotations()) {
                if (a.getName().matches(name)) {
                    a.remove();
                }
            }
        }
    }

    /**
     * paintAnnotation method. This has public scope to allow it to be called
     * from the parentGraph container's paint methods. When using a standard
     * Project Waterloo parentGraph container where annotations have been added
     * using the {@code add} method, this is done automatically.
     *
     * @param g2 the graphics object to render to
     * @param af an AffineTransform instance with scale and translate set to
     * position this annotation correctly in the container using the
     * parentGraph's coordinate system.
     */
    @Override
    public void paintAnnotation(Graphics2D g2, AffineTransform af) {
        FontMetrics metrics;
        Rectangle2D lineBounds;
        Path2D p;

        if (lineStroke == null) {
            lineStroke = new BasicStroke(1f);
        }

        float X = 0, Y = 0;

        if (this instanceof TextAnnotation) {
            if (!text.isEmpty()) {
                g2.setFont(font);
                metrics = g2.getFontMetrics(font);
                lineBounds = metrics.getStringBounds(text, g2);
                Point2D pt = af.transform(new Point2D.Double(getXData(), getYData()), null);
                if (textBackground != null || lineColor != null) {
                    Rectangle2D r = new Rectangle2D.Double(pt.getX() - 5,
                            pt.getY() - lineBounds.getBounds2D().getHeight() - 3,
                            lineBounds.getBounds2D().getWidth() + 10,
                            lineBounds.getBounds2D().getHeight() + 10);
                    GJMarker marker2 = new GJMarker(r);
                    p = marker2.getPath();
                    if (textBackground != null) {
                        g2.setPaint(textBackground);
                        g2.fill(p);
                    }
                    if (lineColor != null) {
                        g2.setPaint(getLineColor());
                        g2.setStroke(getLineStroke());
                        g2.draw(p);
                    }
                }
                g2.setPaint(getTextColor());
                g2.setStroke(getLineStroke());
                g2.drawString(text, (float) pt.getX(), (float) pt.getY());
            }
        } else if (this instanceof ShapeAnnotation) {
            p = this.getPath();
            p.transform(af);
            Paint f = getFill();
            if (f != null) {
                g2.setPaint(f);
                g2.fill(p);
            }
            g2.setPaint(getLineColor());
            g2.setStroke(getLineStroke());
            g2.draw(p);
            if (!text.isEmpty()) {
                g2.setFont(font);
                metrics = g2.getFontMetrics();
                lineBounds = metrics.getStringBounds(text, g2);
                X = (float) (p.getBounds2D().getCenterX() - lineBounds.getCenterX());
                Y = (float) (p.getBounds2D().getCenterY()
                        + metrics.getLineMetrics(text, g2).getDescent());
                g2.drawString(text, X, Y);
            }
        } else if (this instanceof LineAnnotation || this instanceof ArrowAnnotation) {
            p = this.getPath();
            p.transform(af);
            g2.setPaint(getLineColor());
            g2.setStroke(getLineStroke());
            g2.draw(p);

            if (this instanceof ArrowAnnotation && fill != null) {
                // Fill the arrowhead
                Path2D p2 = getExtra().getPath();
                p2.transform(af);
                g2.setPaint(fill);
                g2.fill(p2);
            }

            if (!text.isEmpty()) {

                g2.setFont(font);
                metrics = g2.getFontMetrics();
                lineBounds = metrics.getStringBounds(text, g2);

                if (lineTextPosition == SwingConstants.NORTH) {
                    X = (float) (p.getBounds2D().getCenterX() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            - metrics.getLineMetrics(text, g2).getHeight());
                    g2.drawString(text, X, Y);
                } else if (lineTextPosition == SwingConstants.NORTH_EAST) {
                    X = (float) (p.getBounds2D().getX() + p.getBounds().getWidth() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            - metrics.getLineMetrics(text, g2).getHeight());
                    g2.drawString(text, X, Y);
                } else if (lineTextPosition == SwingConstants.NORTH_WEST) {
                    X = (float) (p.getBounds().getX() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            - metrics.getLineMetrics(text, g2).getHeight());
                    g2.drawString(text, X, Y);
                } else if (lineTextPosition == SwingConstants.SOUTH) {
                    X = (float) (p.getBounds2D().getCenterX() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            + p.getBounds2D().getHeight()
                            + metrics.getLineMetrics(text, g2).getHeight());
                    g2.drawString(text, X, Y);
                } else if (lineTextPosition == SwingConstants.SOUTH_EAST) {
                    X = (float) (p.getBounds2D().getX() + p.getBounds().getWidth() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            + p.getBounds2D().getHeight()
                            + metrics.getLineMetrics(text, g2).getHeight());
                    g2.drawString(text, X, Y);
                } else if (lineTextPosition == SwingConstants.SOUTH_WEST) {
                    X = (float) (p.getBounds2D().getX() - lineBounds.getCenterX());
                    Y = (float) (p.getBounds2D().getY()
                            + p.getBounds2D().getHeight()
                            + metrics.getLineMetrics(text, g2).getHeight());
                } else if (lineTextPosition == SwingConstants.TRAILING) {
                    X = (float) (p.getBounds2D().getCenterX() + 5);
                    Y = (float) (p.getBounds2D().getCenterY() - lineBounds.getCenterY());
                } else if (lineTextPosition == SwingConstants.LEADING) {
                    X = (float) (p.getBounds2D().getCenterX() - lineBounds.getWidth() - 5);
                    Y = (float) (p.getBounds2D().getCenterY() - lineBounds.getCenterY());
                }

                if (textBackground != null || lineColor != null) {
                    Rectangle2D r = new Rectangle2D.Double(X - 1,
                            Y - metrics.getLineMetrics(text, g2).getHeight()
                            + metrics.getLineMetrics(text, g2).getDescent()
                            - 1,
                            lineBounds.getWidth() + 1,
                            lineBounds.getHeight() + 1);
                    GJMarker marker2 = new GJMarker(r);
                    p = marker2.getPath();
                    if (textBackground != null) {
                        g2.setPaint(textBackground);
                        g2.fill(p);
                    }
                    if (lineColor != null) {
                        g2.setPaint(getLineColor());
                        g2.setStroke(new BasicStroke(1f));
                        g2.draw(p);
                    }
                }

                g2.setPaint(getTextColor());
                g2.drawString(text, X, Y);
            }
        }
    }

    /**
     * Static method to create a line between {x1,y1} and {x2,y2}.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return the the annotation
     */
    public static LineAnnotation createLine(double x1, double y1, double x2, double y2) {
        return new LineAnnotation(x1, y1, x2, y2);
    }

    /**
     * Static method to create a line between {x1,y1} and {x2,y2}.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param szx
     * @param szy
     * @return the annotation
     */
    public static ArrowAnnotation createArrow(double x1, double y1, double x2, double y2, double szx, double szy) {
        return new ArrowAnnotation(x1, y1, x2, y2, szx, szy);
    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param szx
     * @param szy
     * @return the annotation
     */
    public static ArrowAnnotation createArrow(double x1, double y1, double x2, double y2, double x3, double y3, double szx, double szy) {
        return new ArrowAnnotation(x1, y1, x2, y2, x3, y3, szx, szy);
    }

    /**
     * Static method to create a line between {x1,y1} and {x2,y2}.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @return the LineAnnotation
     */
    public static LineAnnotation createLine(double x1, double y1, double x2, double y2, double x3, double y3) {
        return new LineAnnotation(x1, y1, x2, y2, x3, y3);
    }

    /**
     * Static method to create a rectangle where {X,Y} is the top left corner
     * and w and h are the width and height.
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @return a GJAnnotation$ShapeAnnotation
     */
    public static ShapeAnnotation createBox(double x, double y, double w, double h) {
        return new ShapeAnnotation(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Static method to create an ellipse where {X,Y} is the top left corner and
     * w and h are the width and height of the bounding rectangle.
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @return a GJAnnotation$ShapeAnnotation
     */
    public static ShapeAnnotation createEllipse(double x, double y, double w, double h) {
        Shape s = new Ellipse2D.Double(x, y, w, h);
        return new ShapeAnnotation(s);
    }

    /**
     * Creates an annotation using the specified object which should implement
     * the {code Shape} interface.
     *
     * @param s a Shape.
     * @return a GJAnnotation$ShapeAnnotation
     */
    public static ShapeAnnotation createShape(Shape s) {
        return new ShapeAnnotation(s);
    }

    public static TextAnnotation createText(double x, double y, String s) {
        return new TextAnnotation(x, y, s);
    }

    /**
     * @return the textBackground
     */
    public Paint getTextBackground() {
        return textBackground;
    }

    /**
     * @param textBackground the textBackground to set
     */
    public void setTextBackground(Paint textBackground) {
        this.textBackground = textBackground;
    }

    /**
     * Internal class for line annotations
     */
    public static class LineAnnotation extends GJAnnotation {

        /**
         * Default constructor
         */
        public LineAnnotation() {
        }

        /**
         * Creates a line between the point x1,y1 and x2,y2.
         *
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         */
        public LineAnnotation(double x1, double y1, double x2, double y2) {
            Path2D p = new Path2D.Double();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);
            setPath(p);
        }

        /**
         * Create a line using quadTo(x1,y1,x2,y2,x3,y3).
         *
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         * @param x3
         * @param y3
         */
        public LineAnnotation(double x1, double y1, double x2, double y2, double x3, double y3) {
            Path2D p = new Path2D.Double();
            p.moveTo(x1, y1);
            p.quadTo(x2, y2, x3, y3);
            setPath(p);
        }
    }

    /**
     * Internal class for arrow annotations
     */
    public static class ArrowAnnotation extends GJAnnotation {

        public ArrowAnnotation() {
        }

        public ArrowAnnotation(double x1, double y1, double x2, double y2, double szx, double szy) {
            Path2D p = new Path2D.Double();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);
            setPath(p);
            double dX = x2 - x1;
            double dY = y2 - y1;
            Shape arrowhead = GJMarker.makeArrow(x2, y2, szx, szy, true);
            AffineTransform af = new AffineTransform();
            af.setToRotation(Math.atan2(dY, dX), x2, y2);
            arrowhead = af.createTransformedShape(arrowhead);
            p.append(arrowhead, false);
            setExtra(new GJMarker(arrowhead));

        }

        public ArrowAnnotation(double x1, double y1, double x2, double y2, double x3, double y3, double szx, double szy) {
            Path2D p = new Path2D.Double();
            p.moveTo(x1, y1);
            p.quadTo(x2, y2, x3, y3);
            setPath(p);
            double dX = x3 - x2;
            double dY = y3 - y2;
            Shape arrowhead = GJMarker.makeArrow(x3, y3, szx, szy, true);
            AffineTransform af = new AffineTransform();
            af.setToRotation(Math.atan2(dY, dX), x3, y3);
            arrowhead = af.createTransformedShape(arrowhead);
            p.append(arrowhead, false);
            setExtra(new GJMarker(arrowhead));
        }
    }

    /**
     * Internal class for shape annotations
     */
    public static class ShapeAnnotation extends GJAnnotation {

        public ShapeAnnotation() {
        }

        public ShapeAnnotation(Shape s) {
            setMarker(new GJMarker(s));
        }
    }

    /**
     * Internal class for text annotations
     */
    public static class TextAnnotation extends GJAnnotation {

        public TextAnnotation() {
        }

        public TextAnnotation(double x, double y, String text) {
            setXData(x);
            setYData(y);
            setText(text);
        }
    }
}
