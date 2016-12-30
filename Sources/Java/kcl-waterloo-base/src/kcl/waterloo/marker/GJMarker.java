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
package kcl.waterloo.marker;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import kcl.waterloo.plotmodel2D.GJCyclicArrayList;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJMarker {

    private static final class maps {

        private static final HashMap<Double, Shape> circles = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> squares = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> triangles = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> itriangles = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> ltriangles = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> rtriangles = new HashMap<Double, Shape>();
        private static final HashMap<Double, Shape> diamonds = new HashMap<Double, Shape>();
        private static final HashMap<String, Shape> strings = new HashMap<String, Shape>();
        private static final GJMarker dot = makeDot();
        private static final HashMap<Double, GJMarker> circleMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> squareMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> triangleMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> itriangleMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> ltriangleMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> rtriangleMarkers = new HashMap<Double, GJMarker>();
        private static final HashMap<Double, GJMarker> diamondMarkers = new HashMap<Double, GJMarker>();
    }

    public static void reset() {
        maps.circleMarkers.clear();
        maps.squareMarkers.clear();
        maps.triangleMarkers.clear();
        maps.itriangleMarkers.clear();
        maps.ltriangleMarkers.clear();
        maps.rtriangleMarkers.clear();
        maps.diamondMarkers.clear();

        maps.circles.clear();
        maps.squares.clear();
        maps.triangles.clear();
        maps.itriangles.clear();
        maps.ltriangles.clear();
        maps.rtriangles.clear();
        maps.diamonds.clear();
        maps.strings.clear();
    }
    // Main code block
    private final Shape path;

    public GJMarker() {
        path = new Path2D.Double();

    }

    /**
     * GLMarker constructor
     *
     * @param p a Path2D object
     */
    public GJMarker(Path2D p) {
        path = p;
    }

    /**
     * GLMarker constructor
     *
     * @param p a Path2D.Double object
     */
    public GJMarker(Path2D.Double p) {
        path = p;
    }

    /**
     * GLMarker constructor
     *
     * @param s any object implementing the Shape interface
     */
    public GJMarker(Shape s) {
        this();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        setSegments(segments);
    }

    /**
     * GLMarker constructor
     *
     * @param arr
     */
    public GJMarker(ArrayList<GJPathSegmentInfo> arr) {
        this();
        setSegments(arr);
    }

    public static GJMarker circle(double width) {
        if (!maps.circles.containsKey(width)) {
            maps.circles.put(width, makeCircle(width));
            maps.circleMarkers.put(width, new GJMarker(maps.circles.get(width)));
        }
        return maps.circleMarkers.get(width);
    }

    public static GJMarker square(double width) {
        if (!maps.squares.containsKey(width)) {
            maps.squares.put(width, makeSquare(width));
            maps.squareMarkers.put(width, new GJMarker(maps.squares.get(width)));
        }
        return maps.squareMarkers.get(width);
    }

    public static GJMarker triangle(double width) {
        if (!maps.triangles.containsKey(width)) {
            maps.triangles.put(width, makeTriangle(width));
            maps.triangleMarkers.put(width, new GJMarker(maps.triangles.get(width)));
        }
        return maps.triangleMarkers.get(width);
    }

    public static GJMarker invertedTriangle(double width) {
        if (!maps.itriangles.containsKey(width)) {
            maps.itriangles.put(width, makeInvertedTriangle(width));
            maps.itriangleMarkers.put(width, new GJMarker(maps.itriangles.get(width)));
        }
        return maps.itriangleMarkers.get(width);
    }

    public static GJMarker leftTriangle(double width) {
        if (!maps.ltriangles.containsKey(width)) {
            maps.ltriangles.put(width, makeLeftTriangle(width));
            maps.ltriangleMarkers.put(width, new GJMarker(maps.ltriangles.get(width)));
        }
        return maps.ltriangleMarkers.get(width);
    }

    public static GJMarker rightTriangle(double width) {
        if (!maps.rtriangles.containsKey(width)) {
            maps.rtriangles.put(width, makeRightTriangle(width));
            maps.rtriangleMarkers.put(width, new GJMarker(maps.rtriangles.get(width)));
        }
        return maps.rtriangleMarkers.get(width);
    }

    public static GJMarker diamond(double width) {
        if (!maps.diamonds.containsKey(width)) {
            maps.diamonds.put(width, makeDiamond(width));
            maps.diamondMarkers.put(width, new GJMarker(maps.diamonds.get(width)));
        }
        return maps.diamondMarkers.get(width);
    }

    public static GJMarker dot(double width) {
        return maps.dot;
    }

    /**
     * Creates a GJMarler from the string on input (typically, but not
     * necessarily a single character) using 18 point size and the "Sans Serif"
     * virtual font.
     *
     * The marker will be offset by its centroid so that it "looks right" when
     * used in a scatter plot.
     *
     * @param s input fromString
     * @return a GJMarker
     */
    public static GJMarker fromString(String s) {
        if (!maps.strings.containsKey(s)) {
            String2D p = makeCharMarker(s, 18);
            Point2D tr = ShapeUtils.getCentroid(p);
            p.transform(AffineTransform.getTranslateInstance(-tr.getX(), -tr.getY()));
            maps.strings.put(s, p);
        }
        return new GJMarker(maps.strings.get(s));
    }

    /**
     * Creates a GJMarker from the string on input (typically, but not
     * necessarily a single character) using the specified point size and the
     * "Sans Serif" virtual font.
     *
     * The marker will be offset by its centroid so that it "looks right" when
     * used in a scatter plot.
     *
     * @param s input fromString
     * @param pointSize the point size for the font
     * @return a GJMarker
     */
    public static GJMarker fromString(String s, int pointSize) {
        return fromString(s, pointSize, "Sans Serif");
    }

    public static GJMarker fromString(String s, int pointSize, String font) {
        Path2D p = makeMarkerFromFont(s, pointSize, font);
        Point2D tr = ShapeUtils.getCentroid(p);
        p.transform(AffineTransform.getTranslateInstance(-tr.getX(), -tr.getY()));
        return new GJMarker(p);
    }

    public final Rectangle getBounds() {
        return path.getBounds();
    }

    public final Rectangle2D getBounds2D() {
        return path.getBounds2D();
    }

    public final double getWidth() {
        return path.getBounds2D().getWidth();
    }

    public final double getHeight() {
        return path.getBounds2D().getHeight();
    }

    public final Path2D getPath() {
        return new Path2D.Double(path);
    }

    /**
     * This method is retained for backwards compatability during
     * de-serialization of pre-1.1 Alpha3 xml files only.
     *
     * @deprecated
     */
    public final void setPath(Shape p) {
        setSegments(ShapeUtils.getSegments(p));
    }

    public final Shape getShape() {
        return path;
    }

    public final void setShape(Shape s) {
        setSegments(ShapeUtils.getSegments(s));
    }

    public final PathIterator getPathIterator() {
        return path.getPathIterator(null);
    }

    public final PathIterator getPathIterator(AffineTransform af) {
        return path.getPathIterator(af);
    }

    public final PathIterator getPathIterator(AffineTransform af, double flatness) {
        return path.getPathIterator(af, flatness);
    }

    public static GJMarker getMarker(String s, double width) {
        if (s.equals("Circle")) {
            return circle(width);
        } else if (s.equals("Square")) {
            return square(width);
        } else if (s.equals("Triangle")) {
            return triangle(width);
        } else if (s.equals("Diamond")) {
            return diamond(width);
        } else if (s.equals("Inverted Triangle")) {
            return invertedTriangle(width);
        } else if (s.equals("Left Triangle")) {
            return leftTriangle(width);
        } else if (s.equals("Right Triangle")) {
            return rightTriangle(width);
        } else {
            return dot(width);
        }
    }

    public static Path2D makeArrow(double x1, double y1, double sc) {
        Path2D thisArrow = new Path2D.Double();
        thisArrow.moveTo(x1 - x1 / sc, y1 - y1 / sc);
        thisArrow.lineTo(x1, y1);
        thisArrow.lineTo(x1 - x1 / sc, y1 + y1 / sc);
        return thisArrow;
    }

    public static Path2D makeArrow(double x1, double y1, double dx, double dy, boolean closeFlag) {
        Path2D thisArrow = new Path2D.Double();
        thisArrow.moveTo(x1 - dx, y1 - dy);
        thisArrow.lineTo(x1, y1);
        thisArrow.lineTo(x1 - dx, y1 + dy);
        if (closeFlag) {
            thisArrow.closePath();
        }
        return thisArrow;
    }

    private static GJMarker makeDot() {
        GJMarker dotMarker;
        Path2D instance = new Path2D.Double();
        Shape temp = new Rectangle2D.Double(-0.5, -0.5, 1, 1);
        instance.append(temp, false);
        dotMarker = new GJMarker(instance);
        return dotMarker;
    }

    private static Rectangle2D makeRectangle(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }

    private static Ellipse2D makeCircle(double r) {
        return new Ellipse2D.Double(-r, -r, 2 * r, 2 * r);
    }

    private static Rectangle2D makeSquare(double r) {
        return makeRectangle(-r, -r, 2 * r, 2 * r);
    }

    private static Triangle2D makeTriangle(double r) {
        return makeTriangle(r, 0);
    }

    private static Triangle2D makeTriangle(double r, double theta) {
        double[] x = {-r, 0, r, -r};
        double centroid = r / 3;
        double[] y = {r - centroid, -r - centroid, r - centroid, r - centroid};
        return new Triangle2D(makeMarker(x, y), theta);
    }

    private static Triangle2D makeInvertedTriangle(double r) {
        return makeTriangle(r, Math.PI);
    }

    private static Triangle2D makeLeftTriangle(double r) {
        return makeTriangle(r, Math.PI * 1.5);
    }

    private static Triangle2D makeRightTriangle(double r) {
        return makeTriangle(r, Math.PI / 2);
    }

    private static Shape makeDiamond(double r) {
        Path2D tr = new Path2D.Double(makeSquare(r));
        tr.transform(AffineTransform.getRotateInstance(Math.PI / 4, 0, 0));
        return tr;
    }

    // Size in points
    public static String2D makeStop(int size) {
        return makeCharMarker(".", size);
    }

    public static String2D makePlus(int size) {
        return makeCharMarker("+", size);
    }

    public static String2D makeCross(int size) {
        return makeCharMarker("x", size);
    }

    /**
     * Creates a Path2D from the string on input (typically, but not necessarily
     * a single character) using the specified point size and the "Sans Serif"
     * vitual font.
     *
     *
     * @param str
     * @param size
     * @return
     */
    public static String2D makeCharMarker(String str, int size) {
        return makeMarkerFromFont(str, size, "Serif");
    }

    public static String2D makeMarkerFromFont(String str, int size, String fontname) {
        Font f = new Font(fontname, Font.PLAIN, size);
        FontRenderContext context = new java.awt.font.FontRenderContext(null, true, false);
        GlyphVector vec = f.createGlyphVector(context, str);
        Shape s = vec.getOutline(0, 0);
        return new String2D(s, 0, str);
    }

    public static Path2D makeMarker(double[] x, double[] y) {
        Path2D path = new Path2D.Double();
        path.moveTo(x[0], y[0]);
        int i;
        for (i = 1; i < x.length; i++) {
            path.lineTo(x[i], y[i]);
        }
        return path;
    }

    public static GJMarker getWidestMarker(GJCyclicArrayList<GJMarker> p) {
        int index = -1;
        double maxWidth = 0;
        for (GJMarker m : p) {
            if (m.getBounds2D().getWidth() > maxWidth) {
                index = p.indexOf(m);
            }
        }
        return p.get(index);
    }

    public static GJMarker getTallestMarker(GJCyclicArrayList<GJMarker> p) {
        int index = -1;
        double maxHeight = 0;
        for (GJMarker m : p) {
            if (m.getBounds2D().getHeight() > maxHeight) {
                index = p.indexOf(m);
            }
        }
        return p.get(index);
    }

    public final ArrayList<GJPathSegmentInfo> getSegments() {
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        return segments;
    }

    public final void setSegments(ArrayList<GJPathSegmentInfo> arr) {
        ((Path2D) path).reset();
        Path2D p = (Path2D) path;
        for (GJPathSegmentInfo segment : arr) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    p.moveTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    p.lineTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    p.quadTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    p.curveTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3],
                            segment.getData()[4], segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    p.closePath();
            }
        }
    }

    public static Path2D makePath(ArrayList<GJPathSegmentInfo> arr) {
        Path2D p = new Path2D.Double();
        for (GJPathSegmentInfo segment : arr) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    p.moveTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    p.lineTo(segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    p.quadTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    p.curveTo(segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3],
                            segment.getData()[4], segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    p.closePath();
            }
        }
        return p;
    }
}
