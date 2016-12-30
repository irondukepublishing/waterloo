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
package kcl.waterloo.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author ML
 */
public class GJQuarterGauge extends GJGauge {

    public GJQuarterGauge() {
        super();
    }

    @Override
    public void setValue(int val) {
        super.setValue(val / 4);
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        if (buffer == null) {
            // If the buffer is null, fill it

            if (useBuffer) {
                buffer = g2.getDeviceConfiguration().createCompatibleImage(getWidth(), getHeight());
                g2 = buffer.createGraphics();
                g2.setClip(g.getClip());
            }

            if (isOpaque()) {
                g2.setPaint(getBackground());
                g2.fill(getBounds());
            }

            dialInset = getWidth() / 50d;
            dialWidth = getWidth() / 10d;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle r = getBounds();

            // Outer Rim
            outerGradientPaint = new GradientPaint(getWidth() / 2,
                    0,
                    primaryColor.darker(),
                    getWidth() / 2,
                    getHeight() / 2,
                    Color.WHITE,
                    true);
            Arc2D arc0 = new Arc2D.Double(-getWidth(),
                    r.getY() + 1,
                    2 * r.getWidth(),
                    r.getHeight() * 1.8 - 2,
                    0d, 180d, Arc2D.CHORD);
            Arc2D arc1 = new Arc2D.Double(-getWidth() + outerRimWidth / 2d,
                    r.getY() + outerRimWidth / 2d,
                    2 * r.getWidth() - outerRimWidth,
                    r.getHeight() * 1.8 - outerRimWidth,
                    0d, 180d, Arc2D.CHORD);
            Area area = new Area(arc0);
            area.subtract(new Area(arc1));
            g2.setPaint(outerGradientPaint);
            g2.fill(area);

            innerGradientPaint = new GradientPaint((int) arc1.getCenterX(), (int) arc1.getY(),
                    primaryColor, (int) arc1.getCenterX(), (int) arc1.getHeight(), Color.WHITE, true);
            g2.setPaint(innerGradientPaint);
            g2.fill(arc1);

            // Dial
            arc2 = new Arc2D.Double(arc1.getX() + dialInset,
                    arc1.getY() + dialInset,
                    arc1.getWidth() - 2 * dialInset,
                    arc1.getHeight() - 2 * dialInset,
                    0d, 180d, Arc2D.CHORD);
            Arc2D arc3 = new Arc2D.Double(arc2.getX() + dialWidth + dialInset,
                    arc2.getY() + dialWidth + dialInset,
                    arc2.getWidth() - 2 * dialWidth - 2 * dialInset,
                    arc2.getHeight() - 2 * dialWidth - 2 * dialInset,
                    0d, 180d, Arc2D.CHORD);
            dialGradientPaint = new GradientPaint((int) arc2.getX(), (int) arc2.getY(),
                    primaryColor.brighter(), (int) arc2.getWidth(), (int) arc2.getHeight(), Color.WHITE, true);
            Area area1 = new Area(arc2);
            area1.subtract(new Area(arc3));
            g2.setPaint(dialGradientPaint);
            g2.fill(area1);

            g2.setPaint(Color.BLACK);
            g2.draw(arc0);
            g2.draw(arc1);
            g2.draw(arc2);

            // Paint ticks
            g2.setPaint(getForeground());
            double maxR = Math.max(arc0.getWidth(), arc0.getHeight()) / 2d;

            arc4 = new Arc2D.Double(arc2.getX() + ((dialWidth + dialInset) / 2d),
                    arc2.getY() + ((dialWidth + dialInset) / 2d),
                    arc2.getWidth() - dialWidth - dialInset,
                    arc2.getHeight() - dialWidth - dialInset,
                    0d, 360d, Arc2D.CHORD);
            Area area2 = new Area(arc2);
            area2.subtract(new Area(arc4));
            g2.setStroke(new BasicStroke(1.5f));
            Area clip = new Area(g.getClip());
            area2.intersect(clip);
            g2.setClip(area2);
            for (double theta = -Math.PI / 2d; theta < 0; theta += Math.PI / ((getMaximum() - getMinimum()) / getMajorTickSpacing())) {
                double x0, y0, x1, y1;
                x0 = arc2.getCenterX();
                y0 = arc2.getCenterY();
                x1 = arc2.getCenterX() + Math.cos(theta) * maxR;
                y1 = arc2.getCenterY() + Math.sin(theta) * maxR;
                g2.drawLine((int) x0, (int) y0, (int) x1, (int) y1);
            }

            Arc2D arc5 = new Arc2D.Double(arc2.getX() + ((dialWidth + dialInset) / 4d),
                    arc2.getY() + ((dialWidth + dialInset) / 4d),
                    arc2.getWidth() - ((dialWidth + dialInset) / 2d),
                    arc2.getHeight() - ((dialWidth + dialInset) / 2d),
                    0d, 360d, Arc2D.CHORD);
            Area area3 = new Area(arc2);
            area3.subtract(new Area(arc5));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(arc5);
            area3.intersect(clip);
            g2.setClip(area3);
            for (double theta = -Math.PI / 2d; theta < 0d; theta += 0.5 * Math.PI / ((getMaximum() - getMinimum()) / getMinorTickSpacing())) {
                double x0, y0, x1, y1;
                x0 = arc2.getCenterX();
                y0 = arc2.getCenterY();
                x1 = arc2.getCenterX() + (Math.cos(theta) * maxR);
                y1 = arc2.getCenterY() + (Math.sin(theta) * maxR);
                g2.drawLine((int) x0, (int) y0, (int) x1, (int) y1);
            }

            g2.setClip(clip);

            double label = 0;
            FontMetrics metrics = g2.getFontMetrics();

            Arc2D newArc2 = new Arc2D.Double(arc1.getX() + dialInset,
                    arc1.getY() + dialInset,
                    arc1.getWidth() - 2 * dialInset,
                    arc1.getHeight() - 2 * dialInset,
                    0d, 360d, Arc2D.CHORD);

            Arc2D newArc4 = new Arc2D.Double(newArc2.getX() + ((dialWidth + dialInset) / 2d),
                    newArc2.getY() + ((dialWidth + dialInset) / 2d),
                    newArc2.getWidth() - dialWidth - dialInset,
                    newArc2.getHeight() - dialWidth - dialInset,
                    0d, 360d, Arc2D.CHORD);

            // Code is based on the JavaGeom library available at {@link http://geom-java.sourceforge.net/index.html}
            for (double theta = -Math.PI / 2d; theta < 0d; theta += 0.5 * Math.PI / ((getMaximum() - getMinimum()) / getMajorTickSpacing())) {
                double x1 = newArc2.getCenterX();
                double y1 = newArc2.getCenterY();
                double x2 = newArc2.getCenterX() + (Math.cos(theta) * newArc4.getHeight());
                double y2 = newArc2.getCenterY() + (Math.sin(theta) * newArc4.getWidth());
                double horzAngle = (Math.atan2(y2 - y1, x2 - x1) + Math.PI * 2) % (Math.PI * 2);

                double xc = newArc4.getCenterX();
                double yc = newArc4.getCenterY();
                double r1 = newArc4.getWidth() / 2d - newArc4.getWidth() / 30d;
                double r2 = newArc4.getHeight() / 2d - newArc4.getHeight() / 30d;
                double cot = Math.cos(0);
                double sit = Math.sin(0);

                double x = xc + r1 * Math.cos(horzAngle) * cot - r2 * Math.sin(horzAngle) * sit;
                double y = yc + r1 * Math.cos(horzAngle) * sit + r2 * Math.sin(horzAngle) * cot;

                String s = String.format("%2.0f", label);
                g2.drawString(s, (float) (x - metrics.stringWidth(s) / 2f), (float) y);
                label += getMajorTickSpacing();
            }
            g2.dispose();
            g2 = (Graphics2D) g.create();
        }

        // Paint the buffer if required then superimpose needle and glass cover
        if (useBuffer && buffer != null) {
            g2.drawImage(buffer, null, 0, 0);
        }

        //Needle
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Arc2D arc6 = new Arc2D.Double(arc2.getCenterX() - arc2.getWidth() / 40d,
                arc2.getCenterY() - arc2.getWidth() / 40d,
                arc2.getWidth() / 20d,
                arc2.getWidth() / 20d,
                0d, 360d, Arc2D.CHORD);
        Area area4 = new Area(arc6);
        double minDim = Math.min(arc2.getHeight(), arc2.getWidth() / 2d);
        Rectangle2D r1 = new Rectangle2D.Double(arc6.getCenterX() - arc6.getWidth() / 4d,
                arc6.getCenterY() - minDim * 0.75f,
                arc6.getWidth() / 2d,
                minDim * 0.75f);
        area4.add(new Area(r1));
        int[] tx = new int[]{(int) r1.getX(),
            (int) r1.getCenterX(),
            (int) (r1.getX() + r1.getWidth())};
        int[] ty = new int[]{(int) r1.getY() + 1,
            (int) r1.getY() - 10,
            (int) r1.getY() + 1};
        area4.add(new Area(new Polygon(tx, ty, 3)));
        AffineTransform af = AffineTransform.getRotateInstance(-getTheta(), arc2.getCenterX(), arc2.getCenterY());
        area4 = area4.createTransformedArea(af);
        GradientPaint needlePaint = new GradientPaint((int) area4.getBounds().getCenterX(),
                area4.getBounds().y, needleColor,
                (int) area4.getBounds().getCenterX() + area4.getBounds().width / 2,
                area4.getBounds().y + area4.getBounds().height, needleColor.brighter(), true);
        g2.setPaint(needlePaint);
        g2.fill(area4);

        if (shadowShown) {
            shadow.apply(g2, area4, 20, 20);
        }

        if (glassShown) {
            Arc2D arc7 = new Arc2D.Double(getWidth() / 2f - getWidth() / 4f,
                    getHeight() / 3f,
                    getWidth() / 2f,
                    getHeight() / 2f,
                    45d, 90d, Arc2D.CHORD);
            GradientPaint glass = new GradientPaint((float) arc7.getCenterX(),
                    (float) arc7.getY(),
                    new Color(1f, 1f, 1f, 0.8f),
                    (float) arc7.getCenterX(),
                    getHeight() / 2f,
                    new Color(1f, 1f, 1f, 0.0f));
            g2.setPaint(glass);
            g2.fill(arc7);
        }

        g2.dispose();

    }
}
