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

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformList;
import kcl.waterloo.graphics.transforms.NOPTransform;
import kcl.waterloo.marker.GJPathSegmentInfo;

/**
 * GJAbstractGraph1 provides support for data transforms on each axis.
 *
 * Its presence as a separate class is cosmetic: it serves only to encapsulate
 * methods that that support/require transforms.
 *
 * @author ML
 */
public abstract class GJAbstractGraph1 extends GJAbstractGraph0 {

    /**
     * Data transforms for the values on each axis
     */
    private final GJDataTransformList transforms
            = new GJDataTransformList(NOPTransform.getInstance(), NOPTransform.getInstance());

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public final GJDataTransformInterface getXTransform() {
        return transforms.get(0);
    }

    /**
     * {@inheritDoc}
     *
     * @param tr
     */
    @Override
    public final void setXTransform(GJDataTransformInterface tr) {
        GJDataTransformInterface old = getXTransform();
        if (tr == null) {
            tr = NOPTransform.getInstance();
        }
        if (tr.equals(old)) {
            return;
        }
        transforms.set(0, tr);
        firePropertyChange("XTransform", old, tr);
    }

    @Override
    public final GJDataTransformInterface getYTransform() {
        return transforms.get(1);
    }

    @Override
    public final void setYTransform(GJDataTransformInterface tr) {
        GJDataTransformInterface old = getYTransform();
        if (tr == null) {
            tr = NOPTransform.getInstance();
        }
        if (tr.equals(old)) {
            return;
        }
        //TODO:
        transforms.set(1, tr);
        firePropertyChange("YTransform", old, tr);
    }

    public Path2D transformPositionToPixel(Shape s) {
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        Path2D p = new Path2D.Double();
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    p.moveTo(xPositionToPixel(getXTransform().getData(segment.getData()[0])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[1])));
                    break;
                case PathIterator.SEG_LINETO:
                    p.lineTo(xPositionToPixel(getXTransform().getData(segment.getData()[0])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[1])));
                    break;
                case PathIterator.SEG_QUADTO:
                    p.quadTo(xPositionToPixel(getXTransform().getData(segment.getData()[0])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[1])),
                            xPositionToPixel(getXTransform().getData(segment.getData()[2])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[3])));
                    break;
                case PathIterator.SEG_CUBICTO:
                    p.curveTo(xPositionToPixel(getXTransform().getData(segment.getData()[0])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[1])),
                            xPositionToPixel(getXTransform().getData(segment.getData()[2])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[3])),
                            xPositionToPixel(getXTransform().getData(segment.getData()[4])),
                            yPositionToPixel(getYTransform().getData(segment.getData()[5])));
                    break;
                case PathIterator.SEG_CLOSE:
                    p.closePath();
            }
        }
        return p;
    }

    public Path2D transformPixelToPosition(Shape s) {
        ArrayList<GJPathSegmentInfo> segments = new ArrayList<GJPathSegmentInfo>();
        double[] d = new double[6];
        int type;
        for (PathIterator it = s.getPathIterator(null); !it.isDone(); it.next()) {
            type = it.currentSegment(d);
            segments.add(new GJPathSegmentInfo(type, d));
        }
        Path2D p = new Path2D.Double();
        for (GJPathSegmentInfo segment : segments) {
            switch (segment.getType()) {
                case PathIterator.SEG_MOVETO:
                    p.moveTo(getXTransform().getData(xPositionToPixel(segment.getData()[0])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[1])));
                    break;
                case PathIterator.SEG_LINETO:
                    p.lineTo(getXTransform().getData(xPositionToPixel(segment.getData()[0])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[1])));
                    break;
                case PathIterator.SEG_QUADTO:
                    p.quadTo(getXTransform().getData(xPositionToPixel(segment.getData()[0])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[1])),
                            getXTransform().getData(xPositionToPixel(segment.getData()[2])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[3])));
                    break;
                case PathIterator.SEG_CUBICTO:
                    p.curveTo(getXTransform().getData(xPositionToPixel(segment.getData()[0])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[1])),
                            getXTransform().getData(xPositionToPixel(segment.getData()[2])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[3])),
                            getXTransform().getData(xPositionToPixel(segment.getData()[4])),
                            getYTransform().getData(yPositionToPixel(segment.getData()[5])));
                    break;
                case PathIterator.SEG_CLOSE:
                    p.closePath();
            }
        }
        return p;
    }

    public Rectangle2D.Double transformPositionToPixel(Rectangle2D r) {
        double x0 = xPositionToPixel(getXTransform().getData(r.getX()));
        double y0 = yPositionToPixel(getYTransform().getData(r.getY()));
        double x1 = xPositionToPixel(getXTransform().getData(r.getX() + r.getWidth()));
        double y1 = yPositionToPixel(getYTransform().getData(r.getY() + r.getHeight()));
        return reflectRectangleAsNeeded(new Rectangle2D.Double(x0, y0, x1 - x0, y0 - y1));
    }

    public Rectangle2D.Double transformPixelToPosition(Rectangle2D r) {
        double x0 = getXTransform().getInverse(xPixelToPosition(r.getX()));
        double y0 = getYTransform().getInverse(yPixelToPosition(r.getY()));
        double x1 = getXTransform().getInverse(xPixelToPosition(r.getX() + r.getWidth()));
        double y1 = getYTransform().getInverse(yPixelToPosition(r.getY() + r.getHeight()));
        return reflectRectangleAsNeeded(new Rectangle2D.Double(x0, y0, x1 - x0, y0 - y1));
    }

    private Rectangle2D.Double reflectRectangleAsNeeded(Rectangle2D.Double r) {
        if (r.getWidth() < 0) {
            r.setRect(r.x + r.width, r.y, -r.width, r.height);
        }
        if (r.getHeight() < 0) {
            r.setRect(r.x, r.y + r.height, r.width, -r.height);
        }
        return r;
    }

    @Override
    public final double getXAxisLabelRotation(double number) {
        number = getXTransform().getInverse(number);
        if (isCategorical(GJAxisPanel.Orientation.X)) {
            for (GJPlotInterface p : getPlots()) {
                if (p.getXData().getCategories() != null && p.getXData().getCategories().containsKey(number)) {
                    return p.getXData().getCategories().get(number).getRotation();
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    public final double getYAxisLabelRotation(double number) {
        number = getYTransform().getInverse(number);
        if (isCategorical(GJAxisPanel.Orientation.X)) {
            for (GJPlotInterface p : getPlots()) {
                if (p.getYData().getCategories() != null && p.getYData().getCategories().containsKey(number)) {
                    return p.getYData().getCategories().get(number).getRotation();
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    public final String formatXAxisLabel(double number) {
        if (isCategorical(GJAxisPanel.Orientation.X)) {
            for (GJPlotInterface p : getPlots()) {
                if (p.getXData().getCategories() != null
                        && p.getXData().getCategories().containsKey(getXTransform().getInverse(number))) {
                    return p.getXData().getCategories().get(getXTransform().getInverse(number)).getText();
                }
            }
            return "";
        } else {
            return (String) getXTransform().getTickLabel(number);
        }
    }

    @Override
    public final String formatYAxisLabel(double number) {
        if (isCategorical(GJAxisPanel.Orientation.Y)) {
            for (GJPlotInterface p : getPlots()) {
                if (p.getYData().getCategories() != null
                        && p.getYData().getCategories().containsKey(getYTransform().getInverse(number))) {
                    return p.getYData().getCategories().get(getYTransform().getInverse(number)).getText();
                }
            }
            return "";
        } else {
            return (String) getYTransform().getTickLabel(number);
        }
    }
}
