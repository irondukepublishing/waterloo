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

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This class is extended by the GJAbstractGraph class.
 *
 * Its presence as a separate class is cosmetic: it serves only to encapsulate
 * methods that that serve to convert between pixels and axis coordinates.
 *
 * @author ML
 */
public abstract class GJAbstractGraph0 extends GJBasicPanel implements GJGraphInterface {

    /**
     * Value for the x-axis at the left
     */
    double xLeft;
    /**
     * Value for the x-axis at the right
     */
    double xRight;
    /**
     * Value for the y-axis at the bottom
     */
    double yBottom;
    /**
     * Value for the y-axis at the top
     */
    double yTop;
    /**
     * Value for the origin on the x-axis. Internal axes are painted here.
     */
    double originX;
    /**
     * Value for the origin on the x-axis. Internal axes are painted here.
     */
    double originY;

    @Override
    public final double yPositionToPixel(double position) {
        double height = (double) getHeight();
        return height - ((position - yBottom) * height / (yTop - yBottom));
    }

    @Override
    public final double[] yPositionToPixel(double[] position) {
        final double[] out = new double[position.length];
        final double height = (double) getHeight();
        final double pixelHeight = height / (yTop - yBottom);
        for (int k = position.length; --k >= 0;) {
            out[k] = height - ((position[k] - yBottom) * pixelHeight);
        }
        return out;
    }

    @Override
    public final double[] yPositionToPixeli(double[] position) {
        if (position != null) {
            final double height = (double) getHeight();
            final double pixelHeight = height / (yTop - yBottom);
            for (int k = position.length; --k >= 0;) {
                position[k] = height - ((position[k] - yBottom) * pixelHeight);
            }
        }
        return position;
    }

    @Override
    public final double xPositionToPixel(double position) {
        return (position - xLeft) * (double) getWidth() / (xRight - xLeft);
    }

    @Override
    public final double[] xPositionToPixel(double[] position) {
        final double[] out = new double[position.length];
        final double pixelWidth = (double) getWidth() / (xRight - xLeft);
        for (int k = position.length; --k >= 0;) {
            out[k] = (position[k] - xLeft) * pixelWidth;
        }
        return out;
    }

    @Override
    public final double[] xPositionToPixeli(double[] position) {
        if (position != null) {
            final double pixelWidth = (double) getWidth() / (xRight - xLeft);
            for (int k = position.length; --k >= 0;) {
                position[k] = (position[k] - xLeft) * pixelWidth;
            }
        }
        return position;
    }

    @Override
    public final double xPixelToPosition(double pixel) {
        return xLeft + pixel * (xRight - xLeft) / (double) getWidth();
    }

    @Override
    public final double[] xPixelToPosition(final double[] pixel) {
        final double[] out = new double[pixel.length];
        final double pixelWidth = (xRight - xLeft) / (double) getWidth();
        for (int k = pixel.length; --k >= 0;) {
            out[k] = xLeft + pixel[k] * pixelWidth;
        }
        return out;
    }

    @Override
    public final double[] xPixelToPositioni(final double[] pixel) {
        final double pixelWidth = (xRight - xLeft) / (double) getWidth();
        for (int k = pixel.length; --k >= 0;) {
            pixel[k] = xLeft + pixel[k] * pixelWidth;
        }
        return pixel;
    }

    @Override
    public final double getPixelWidth() {
        return (xRight - xLeft) / getWidth();
    }

    @Override
    public final Rectangle2D convertPixelsToPosition(Rectangle2D r1) {
        double x, y, w, h;
        x = xPixelToPosition(r1.getX());
        y = yPixelToPosition(r1.getY());
        w = getXTransform().getData(getPixelWidth() * r1.getWidth());
        h = getYTransform().getData(getPixelHeight() * r1.getHeight());
        y = y - h;
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            x = 0d;
        }
        if (Double.isNaN(y) || Double.isInfinite(y)) {
            y = 0d;
        }
        if (Double.isNaN(w) || Double.isInfinite(w)) {
            w = 1d;
        }
        if (Double.isNaN(h) || Double.isInfinite(h)) {
            h = 1d;
        }
        return new Rectangle.Double(x, y, w, h);
    }

    @Override
    public final double yPixelToPosition(double pixel) {
        return yBottom + (getHeight() - pixel) * (yTop - yBottom) / (double) getHeight();
    }

    @Override
    public final double[] yPixelToPosition(double[] pixel) {
        final double[] out = new double[pixel.length];
        final double pixelHeight = (yTop - yBottom) / (double) getHeight();
        final double height = getHeight();
        for (int k = pixel.length; --k >= 0;) {
            out[k] = yBottom - (height - pixel[k]) * pixelHeight;
        }
        return out;
    }

    @Override
    public final double[] yPixelToPositioni(double[] pixel) {
        final double pixelHeight = (yTop - yBottom) / (double) getHeight();
        final double height = getHeight();
        for (int k = pixel.length; --k >= 0;) {
            pixel[k] = yBottom - (height - pixel[k]) * pixelHeight;
        }
        return pixel;
    }

    @Override
    public final double getPixelHeight() {
        return (yTop - yBottom) / getHeight();
    }

    @Override
    public final double getXLeft() {
        return xLeft;
    }

    @Override
    public final void setXLeft(double v) {
        double old = xLeft;
        xLeft = v;
        firePropertyChange("xLeft", old, xLeft);
    }

    @Override
    public final double getXRight() {
        return xRight;
    }

    @Override
    public final void setXRight(double v) {
        double old = xRight;
        xRight = v;
        firePropertyChange("xRight", old, xRight);
    }

    @Override
    public final double getYBottom() {
        return yBottom;
    }

    @Override
    public final void setYBottom(double v) {
        double old = yBottom;
        yBottom = v;
        firePropertyChange("yBottom", old, yBottom);
    }

    @Override
    public final double getYTop() {
        return yTop;
    }

    @Override
    public final void setYTop(double v) {
        double old = yTop;
        yTop = v;
        firePropertyChange("yTop", old, yTop);
    }

    @Override
    public final double getXMin() {
        return Math.min(xLeft, xRight);
    }

    @Override
    public final double getXMax() {
        return Math.max(xLeft, xRight);
    }

    @Override
    public final double getYMin() {
        return Math.min(yBottom, yTop);
    }

    @Override
    public final double getYMax() {
        return Math.max(yBottom, yTop);
    }

    @Override
    public final double getOriginX() {
        return originX;
    }

    @Override
    public final double getOriginY() {
        return originY;
    }

    /**
     * <p>
     * Gets the origin coordinates of the graph. The coordinates are represented
     * as an instance of <code>Point2D</code> and stored in <code>double</code>
     * format.</p>
     *
     * @return the origin coordinates in double format
     * @see #setOrigin(Point2D)
     * @see #setAxesBoundsAndOrigin(Rectangle2D)
     */
    @Override
    public final Point2D getOrigin() {
        return new Point2D.Double(originX, originY);
    }

    /**
     * <p>
     * Sets the origin of the graph. The coordinates of the origin are defined
     * by the coordinates of the point passed as parameter.</p>
     *
     * <p>
     * If the specified view is null, nothing happens.</p>
     *
     * <p>
     * Calling this method leaves the view intact.</p>
     *
     * @param origin the coordinates of the new origin
     * @see #getOrigin()
     * @see #setAxesBoundsAndOrigin(Rectangle2D)
     */
    @Override
    public final void setOrigin(Point2D origin) {
        if (origin == null) {
            return;
        }
        Point2D old = getOrigin();
        originX = origin.getX();
        originY = origin.getY();
        //repaint();
        firePropertyChange("origin", old, getOrigin());
    }

    @Override
    public final void setOriginX(double v) {
        setOrigin(new Point2D.Double(v, originY));
    }

    @Override
    public final void setOriginY(double v) {
        setOrigin(new Point2D.Double(originX, v));
    }
}
