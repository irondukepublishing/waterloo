/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.marker;

import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.graphics.data.GJXYSeries;
import kcl.waterloo.math.ArrayMath;
import kcl.waterloo.math.ArrayUtils;

/**
 *
 * @author ML
 */
public class ShapeUtils {

    private ShapeUtils() {
    }

    public static ArrayList<GJPathSegmentInfo> getSegments(Shape p) {
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = p.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        return segments;
    }

    public static GJXYSeries getXY(Shape s) {
        ArrayList<Double> x = new ArrayList<Double>();
        ArrayList<Double> y = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (Iterator<GJPathSegmentInfo> it = segments.iterator(); it.hasNext();) {
            GJPathSegmentInfo segment = it.next();
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
//                    break;
                case PathIterator.SEG_LINETO:
                    x.add(segment.getData()[0]);
                    y.add(segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    x.add(segment.getData()[0]);
                    x.add(segment.getData()[2]);
                    y.add(segment.getData()[1]);
                    y.add(segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    x.add(segment.getData()[0]);
                    x.add(segment.getData()[2]);
                    x.add(segment.getData()[4]);
                    y.add(segment.getData()[1]);
                    y.add(segment.getData()[3]);
                    y.add(segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
            }
        }
        return new GJXYSeries(x, y);
    }

    public static double[] getX(Shape s) {
        ArrayList<Double> arr = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (Iterator<GJPathSegmentInfo> it = segments.iterator(); it.hasNext();) {
            GJPathSegmentInfo segment = it.next();
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
//                    break;
                case PathIterator.SEG_LINETO:
                    arr.add(segment.getData()[0]);
                    break;
                case PathIterator.SEG_QUADTO:
                    arr.add(segment.getData()[0]);
                    arr.add(segment.getData()[2]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    arr.add(segment.getData()[0]);
                    arr.add(segment.getData()[2]);
                    arr.add(segment.getData()[4]);
                    break;
                case PathIterator.SEG_CLOSE:
            }
        }
        return ArrayUtils.asDouble(arr);
    }

    public static GJXYSeries getFlattenedXY(Shape s) {
        return getFlattenedXY(s, Math.min(s.getBounds2D().getWidth() / 1000d, s.getBounds2D().getHeight() / 1000d));
    }

    public static GJXYSeries getFlattenedXY(Shape s, double flatness) {
        FlatteningPathIterator f = new FlatteningPathIterator(s.getPathIterator(null), flatness);
        ArrayList<Double> x = new ArrayList<Double>();
        ArrayList<Double> y = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = f; !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    x.add(segment.getData()[0]);
                    y.add(segment.getData()[1]);
                    break;
                case PathIterator.SEG_CLOSE:
                    break;
                default:
            }
        }
        return new GJXYSeries(x, y);
    }

    public static double[] getFlattenedX(Shape s) {
        return getFlattenedX(s, Math.min(s.getBounds2D().getWidth() / 1000d, s.getBounds2D().getHeight() / 1000d));
    }

    public static double[] getFlattenedX(Shape s, double flatness) {
        FlatteningPathIterator f = new FlatteningPathIterator(s.getPathIterator(null), flatness);
        ArrayList<Double> arr = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = f; !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    arr.add(segment.getData()[0]);
                    break;
                case PathIterator.SEG_CLOSE:
                    break;
                default:
            }
        }
        return ArrayUtils.asDouble(arr);
    }

    public static double[] getY(Shape s) {
        ArrayList<Double> arr = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    arr.add(segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    arr.add(segment.getData()[1]);
                    arr.add(segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    arr.add(segment.getData()[1]);
                    arr.add(segment.getData()[3]);
                    arr.add(segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
            }
        }
        return ArrayUtils.asDouble(arr);
    }

    public static double[] getFlattenedY(Shape s) {
        return getFlattenedY(s, Math.min(s.getBounds2D().getWidth() / 1000d, s.getBounds2D().getHeight() / 1000d));
    }

    public static double[] getFlattenedY(Shape s, double flatness) {
        FlatteningPathIterator f = new FlatteningPathIterator(s.getPathIterator(null), flatness);
        ArrayList<Double> arr = new ArrayList<Double>();
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = f; !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    arr.add(segment.getData()[1]);
                    break;
                case PathIterator.SEG_CLOSE:
                    break;
                default:
            }
        }
        return ArrayUtils.asDouble(arr);
    }

    public static Path2D getFromZeroX(GJGraphInterface gr, Shape p) {
        return getFromX(gr, p, 0d);
    }

    public static Path2D getFromX(GJGraphInterface gr, Shape p, double y) {
        Path2D path = new Path2D.Double();
        double[] d = new double[6];
        PathIterator it = p.getPathIterator(null);
        it.currentSegment(d);
        path.moveTo(d[0], gr.yPositionToPixel(y));
        path.append(p, true);
        while (!it.isDone()) {
            it.currentSegment(d);
            it.next();
        }
        path.lineTo(d[0], gr.yPositionToPixel(y));
        return path;
    }

    public static Path2D getFromZeroY(GJGraphInterface gr, Shape p, double x) {
        return getFromY(gr, p, x);
    }

    public static Path2D getFromY(GJGraphInterface gr, Shape p, double x) {
        Path2D path = new Path2D.Double();
        double[] d = new double[6];
        PathIterator it = p.getPathIterator(null);
        it.currentSegment(d);
        path.moveTo(gr.xPositionToPixel(x), d[1]);
        path.append(p, true);
        while (!it.isDone()) {
            it.currentSegment(d);
            it.next();
        }
        path.lineTo(gr.xPositionToPixel(x), d[1]);
        return path;
    }


    /**
     * Returns a numerical estimate of the area of a shape.
     *
     * @param s a Shape
     * @return the area.
     */
    public static double getArea(Shape s) {
        Area area = new Area(s);
        if (area.isEmpty()) {
            return 0d;
        }
        double[] dX = ArrayMath.diff(ShapeUtils.getFlattenedX(area));
        double[] y1 = ShapeUtils.getFlattenedY(area);
        double[] y2 = new double[y1.length - 1];
        double[] y3 = new double[y1.length - 1];
        System.arraycopy(y1, 0, y2, 0, y1.length - 1);
        System.arraycopy(y1, 1, y3, 0, y1.length - 1);
        ArrayMath.addi(y2, y3);
        ArrayMath.divi(y2, 2);
        ArrayMath.muli(y2, dX);
        return Math.abs(ArrayMath.sum(y2));
    }

    /**
     * Returns a numerical estimate of the centroid (center of mass) of the
     * specified Shape.
     *
     * @param s a Shape object
     * @return the estimate as a Point2d.Double instance.
     */
    public static Point2D.Double getCentroid(Shape s) {
        double[] x = ShapeUtils.getFlattenedX(s);
        double[] y = ShapeUtils.getFlattenedY(s);
        int n;
        if (isClosed(s)) {
            n = x.length - 2;
        } else {
            n = x.length - 1;
        }
        double sx = ArrayMath.sum(x, 0, n);
        double sy = ArrayMath.sum(y, 0, n);
        return new Point2D.Double(sx / (double) n, sy / (double) n);
    }

    /**
     * Tests whether shape s2 lays within shape s1.
     *
     * @param s1 a Shape
     * @param s2 a Shape
     * @return true if s2 lies within s1.
     */
    public static boolean contains(Shape s1, Shape s2) {
        Area area1 = new Area(s1);
        Area area2 = new Area(s2);
        area2.subtract(area1);
        return area2.isEmpty();
    }

    /**
     * Tests whether shape s2 lays within shape s1.
     *
     * @param s1 a Shape
     * @param s2 a Shape
     * @return true if s2 lies within s1.
     */
    public static boolean intersects(Shape s1, Shape s2) {
        Area area1 = new Area(s1);
        Area area2 = new Area(s2);
        area2.intersect(area1);
        return !area2.isEmpty();
    }

    public static double sumdX(Shape s) {
        double[] x = getX(s);
        return ArrayMath.sum(ArrayMath.diff(x));
    }

    public static boolean isClosed(Shape s) {
        return sumdX(s) == 0;
    }

    public static String toString(Shape s) {
        StringBuilder str = new StringBuilder();
        Formatter f = new Formatter(str);

        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        f.format("%s [%x]\n", s.getClass().toString(), s.hashCode());
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    f.format("moveTo:\t%10.4f\t%10.4f\n", segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    f.format("lineTo:\t%10.4f\t%10.4f\n", segment.getData()[0], segment.getData()[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    f.format("quadTo:\t%10.4f\t%10.4f\t%10.4f\t%10.4f\n",
                            segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    f.format("curveTo:\t%10.4f\t%10.4f\t%10.4f\t%10.4f\t%10.4f\t%10.4f\n",
                            segment.getData()[0], segment.getData()[1],
                            segment.getData()[2], segment.getData()[3],
                            segment.getData()[4], segment.getData()[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    f.format("closePath\n");
            }
        }
        return f.toString();
    }

    /**
     * Given an arc, arc0 and an angle theta, getXYOnArc returns the XY
     * coordinates for the intersection of theta on the second arc, arc1.
     *
     * @param arc0
     * @param arc1
     * @param theta
     * @return
     */
    public static double[] getXYOnArc(Ellipse2D arc0, Ellipse2D arc1, double theta) {
        double x1 = arc0.getCenterX();
        double y1 = arc0.getCenterY();
        double x2 = x1 + (Math.cos(theta) * arc1.getHeight());
        double y2 = y1 + (Math.sin(theta) * arc1.getWidth());
        double horzAngle = (Math.atan2(y2 - y1, x2 - x1) + Math.PI * 2) % (Math.PI * 2);

        double xc = arc1.getCenterX();
        double yc = arc1.getCenterY();
        double r1 = arc1.getWidth() / 2d - arc1.getWidth() / 30d;
        double r2 = arc1.getHeight() / 2d - arc1.getHeight() / 30d;
        double cot = Math.cos(0);
        double sit = Math.sin(0);

        double xl = xc + r1 * Math.cos(horzAngle) * cot - r2 * Math.sin(horzAngle) * sit;
        double yl = yc + r1 * Math.cos(horzAngle) * sit + r2 * Math.sin(horzAngle) * cot;

        return new double[]{xl, yl};
    }

    public static double[] getStringLocationToArc(double[] xy, double theta, String s, FontMetrics metrics) {

        if (theta == 0) {
            xy[1] += metrics.getAscent() / 2d;
        } else if (Math.abs(theta) == Math.PI / 2d) {
            xy[0] -= metrics.stringWidth(s) / 2d;
        } else if (Math.abs(theta) == Math.PI) {
            xy[1] += metrics.getAscent() / 2d;
        } else if (Math.abs(theta) == 1.5d * Math.PI) {
            xy[0] -= metrics.stringWidth(s) / 2d;
            xy[1] += metrics.getAscent();
        } else if (Math.abs(theta) < Math.PI / 2d) {
            //Do nothing
        } else if (Math.abs(theta) < Math.PI) {
            xy[0] -= metrics.stringWidth(s);
        } else if (Math.abs(theta) < 1.5 * Math.PI) {
            xy[0] -= metrics.stringWidth(s);
            xy[1] += metrics.getAscent() / 2d;
        } else if (Math.abs(theta) > 1.5 * Math.PI) {
            xy[1] += metrics.getAscent() / 2d;
        }

        return xy;
    }

}
