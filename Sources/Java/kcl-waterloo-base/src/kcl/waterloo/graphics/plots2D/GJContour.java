 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
package kcl.waterloo.graphics.plots2D;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.Formatter;
import kcl.waterloo.graphics.plots2D.contour.Contour;
import kcl.waterloo.graphics.plots2D.contour.ContourExtra;
import kcl.waterloo.graphics.plots2D.contour.ContourExtra.SortedContour;

/**
 *
 * GJContour class for plotting contours.
 *
 * Note that the MarkerArray is not used for this class - Paths are instead
 * represented by the ContourExtra object that provides methods to generate the
 * contour lines from data.
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJContour extends GJAbstractPlot implements GJTransformUpdateInterface {

    /**
     * Set true to label the contours with their levels
     */
    private boolean labelled = false;
    private Font labelFont = null;
    private boolean fillClipping = true;
    private boolean filled = true;

    public GJContour() {
        super();
    }

    public GJContour(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface p = GJAbstractPlot.createInstance(new GJContour());
        p.setXData(new double[]{0d});
        p.setYData(new double[]{0d});
        return p;
    }

    @Override
    public void paintPlot(Graphics2D g) {

        super.paintPlot(g);
        getScreenDataArray().clear();

        ContourExtra extra = (ContourExtra) this.getDataModel().getExtraObject();

        if (extra == null || extra.size() == 0 || extra.getCompounds().size() == 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        double scx = getParentGraph().xPositionToPixel(1) - getParentGraph().xPositionToPixel(0);
        double scy = getParentGraph().yPositionToPixel(0) - getParentGraph().yPositionToPixel(1);

        if (Double.isInfinite(scx) || Double.isNaN(scx)) {
            scx = 1d;
        }

        if (Double.isInfinite(scy) || Double.isNaN(scy)) {
            scy = 1d;
        }

        AffineTransform af1 = AffineTransform.getScaleInstance(scx, -scy);

        AffineTransform af2 = new AffineTransform();
        double xt = getParentGraph().xPositionToPixel(0);
        double yt = getParentGraph().yPositionToPixel(0);
        af2.setToTranslation(xt, yt);

        // If filled, draw the fills first....
        if (isFilled()) {
            doFill(g2, extra, af1, af2);
        }

        //... then draw the lines
        //setTranslation(af2, x[0], y[0]);
        for (Double thisLevel : extra.keySet()) {
            Path2D p = extra.getCompound(thisLevel);
            // Treat as zero if within 1 ulp of 1f
            if (extra.getZeroStroke() != null && Math.abs(thisLevel) <= Math.ulp(1f)) {
                g2.setStroke(extra.getZeroStroke());
            } else {
                g2.setStroke(getEdgeStroke().get(extra.getColorIndex(thisLevel)));
            }

            if (extra.getPositiveLineColor() != null && thisLevel >= 0) {
                g2.setPaint(extra.getPositiveLineColor());
            } else if (extra.getNegativeLineColor() != null && thisLevel < 0) {
                g2.setPaint(extra.getNegativeLineColor());
            } else {
                g2.setPaint(getEdgeColor().get(extra.getColorIndex(thisLevel)));
            }
            p.transform(af1);
            p.transform(af2);
            g2.draw(p);
            getScreenDataArray().add(p);
        }

        //... then the labels if required
        if (isLabelled()) {
            g2.setFont(labelFont);
            for (SortedContour c : extra.sort()) {
                Path2D p = new Path2D.Double();
                Contour contour = c.getContour();
                String str = String.format("%4.2f", contour.getLevel());
                GlyphVector glyph = g2.getFont().createGlyphVector(g2.getFontRenderContext(), str);
                float glyphX = (float) (scx * contour.getLabel().getX() - g2.getFontMetrics().stringWidth(str) / 2d);
                float glyphY = (float) (-scy * contour.getLabel().getY());
                if (!Double.isNaN(glyphX) && !Double.isNaN(glyphY)) {
                    Shape s = glyph.getOutline(glyphX, glyphY);
                    double theta = contour.getTheta();
                    if (Math.abs(theta) > Math.PI / 2) {
                        theta += Math.PI;
                    }
                    p.append(s, false);
                    p.transform(AffineTransform.getRotateInstance(theta,
                            s.getBounds2D().getCenterX(),
                            s.getBounds2D().getCenterY()));
                    p.transform(af2);
                    if (contour.getLabel().getBackground() != null) {
                        g2.setPaint(contour.getLabel().getBackground());
                        Path2D p2 = new Path2D.Double();
                        Rectangle2D r = s.getBounds2D();
                        p2.append(new Rectangle.Double(r.getX() - 1, r.getY() - 1,
                                r.getWidth() + 2, r.getHeight() + 2), false);
                        p2.transform(AffineTransform.getRotateInstance(theta,
                                s.getBounds2D().getCenterX(),
                                s.getBounds2D().getCenterY()));
                        p2.transform(af2);
                        g2.fill(p2);
                    }
                    g2.setPaint(contour.getLabel().getForeground());
                    g2.fill(p);
                }
            }
        }
    }

    private void doFill(Graphics2D g2, ContourExtra extra, AffineTransform af1, AffineTransform af2) {
        Area commonPath;
        Shape clip = g2.getClip();
        if (fillClipping && clip != null) {
            commonPath = extra.getCommonPath();
            if (!commonPath.isEmpty()) {
                commonPath.transform(af1);
                commonPath.transform(af2);
                commonPath.intersect(new Area(clip));
                g2.setClip(commonPath);
            }
        }
        for (SortedContour sortedContour : extra.sort()) {
            Area p = sortedContour.getFillArea();
            p.transform(af1);
            p.transform(af2);
            g2.setPaint(getFill().get(sortedContour.getColorIndex()));
            g2.fill(p);
        }
        g2.setClip(clip);
    }

    @Override
    public boolean isMultiplexible() {
        return false;
    }

    @Override
    public String stringSupplement() {
        Formatter f = new Formatter();
        ContourExtra extra = (ContourExtra) getDataModel().getExtraObject();
        f.format("%n%15s%10s%20s%20s%18s%18s%20s%n", "Level", "Count", "Data Points", "Closed", "Edge Color", "Fill Color", "Color Index");
        int i = 0;
        for (Double thisLevel : extra.getLevelData().keySet()) {
            f.format("%4d:%10.3f", i, thisLevel.doubleValue());
            f.format("%10s", extra.getLevelData().get(thisLevel).size());
            String str = "[";
            for (int k = 0; k < extra.getLevelData().get(thisLevel).size(); k++) {
                str = str.concat(String.format("%d", extra.getLevelData().get(thisLevel).get(k).size()));
                if (k == 4 && extra.getLevelData().get(thisLevel).size() >= 5) {
                    str = str.concat("...");
                    break;
                } else if (k < extra.getLevelData().get(thisLevel).size() - 1) {
                    str = str.concat("+");
                }
            }
            str = str.concat("]");
            f.format("\t%-18s", str);

            str = "[";
            int n = 0;
            for (Contour c : extra.getLevel(thisLevel)) {
                str = str.concat(String.format("%s", c.isClosed() ? "T" : "F"));
                if (n == 4 && extra.getLevel(thisLevel).size() >= 5) {
                    str = str.concat("...");
                    break;
                } else if (n < extra.getLevel(thisLevel).size() - 1) {
                    str = str.concat("+");
                }
                n++;
            }
            str = str.concat("]");
            f.format("\t%-10s", str);

            f.format("%18s", getEdgeColor().get(i).toString().replaceAll("java.awt.Color", ""));
            f.format(" _|-> %12s", getFill().get(i).toString().replaceAll("java.awt.Color", ""));
            f.format("\t%-10d", extra.getColorIndex(thisLevel));

            f.format("%n");
            i++;
        }
        return f.toString();
    }

    /**
     * @return the labelled
     */
    public boolean isLabelled() {
        return labelled;
    }

    /**
     * @param labelled the labelled to set
     */
    public void setLabelled(boolean labelled) {
        this.labelled = labelled;
    }

    /**
     * @return the fillClipping
     */
    public boolean isFillClipping() {
        return fillClipping;
    }

    /**
     * @param fillClipping the fillClipping to set
     */
    public void setFillClipping(boolean fillClipping) {
        this.fillClipping = fillClipping;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("extraObject")
                || e.getPropertyName().equals("parentGraph")
                || e.getPropertyName().equals("dataBuffer")) {
            transformUpdate();
        }
    }

    @Override
    public final boolean transformUpdate() {
        if (getParentGraph() != null) {
            ((ContourExtra) getDataModel().getExtraObject()).generateContours(this);
            ((Component) getParentGraph()).repaint();
            //getParentGraph().autoScale();
            if (labelFont == null) {
                labelFont = ((Component) getParentGraph()).getFont().deriveFont(10f);
            }
        }
        return true;
    }

    /**
     * @return the filled
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * @param filled the filled to set
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * @return the labelFontSize
     */
    public Font getLabelFont() {
        return labelFont;
    }

    /**
     * @param labelFont
     */
    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }
}
