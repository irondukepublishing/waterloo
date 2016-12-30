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

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Formatter;
import kcl.waterloo.marker.GJMarker;
import kcl.waterloo.math.ArrayMath;

/**
 *
 * GJQuiver or vector plot.
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJQuiver extends GJAbstractPlot {

    /**
     * An affine transform used to scale the arrow heads
     */
    private final AffineTransform af = new AffineTransform();

    public GJQuiver() {
        super();
    }

    public GJQuiver(GJAbstractPlot p) {
        super(p);
    }

//    private GJQuiver() {
//    }
    public static GJPlotInterface createInstance() {
        GJPlotInterface p = GJAbstractPlot.createInstance(new GJQuiver());
        p.getDataModel().setExtraObject(new QuiverExtra(1));
        return p;
    }

    /**
     * Paints the plot.
     *
     * @param g2
     */
    @Override
    public final void paintPlot(Graphics2D g2) {

        Path2D path;
        super.paintPlot(g2);
        getScreenDataArray().clear();

        final double[] X = getXData().getRawDataValues();
        final double[] Y = getYData().getRawDataValues();
        final double[] dX;
        final double[] dY;

        if (X != null && Y != null) {

            // Deal with user-specified scaling
            // N.B. If UserScaleFactor == 0, scale will be set to 1d
            final double scale = getFinalScaleFactor(getDataModel().getExtraData0(), getDataModel().getExtraData1());
            if (scale != 1d) {
                dX = ArrayMath.mul(getDataModel().getExtraData0(), scale);
                dY = ArrayMath.mul(getDataModel().getExtraData1(), scale);
            } else {
                dX = getDataModel().getExtraData0();
                dY = getDataModel().getExtraData1();
            }

            double Yopix;
            double Xopix;
            double dXpix, dYpix;

            double xsz, ysz;
            Shape arrowhead;

            double x0, x1;

            for (int i = 0; i < dX.length; i++) {

                path = new Path2D.Double();
                Xopix = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData(X[i]));
                Yopix = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(Y[i]));
                dXpix = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData(X[i] + dX[i]));
                dYpix = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(Y[i] + dY[i]));

                if (!Double.isNaN(Xopix) && !Double.isNaN(Yopix)
                        && !Double.isInfinite(Xopix) && !Double.isInfinite(Yopix)) {

                    path.moveTo(Xopix, Yopix);
                    if (isUseQuad()) {
                        x0 = getParentGraph().xPositionToPixel(getParentGraph().getXTransform().getData(X[i] + dX[i] / 2d));
                        x1 = getParentGraph().yPositionToPixel(getParentGraph().getYTransform().getData(Y[i] + dY[i] * 2d / 3d));

                        if (Double.isNaN(x0) || Double.isNaN(x1)) {
                            continue;
                        }

                        path.quadTo(x0, x1, dXpix, dYpix);

                    } else {
                        path.lineTo(dXpix, dYpix);
                    }

                    if (getVisualModel().getDynamicMarkerSize().size() == 0) {
                        xsz = 1.5;
                        ysz = 1.5;
                    } else {
                        xsz = getVisualModel().getDynamicMarkerSize().get(i).getWidth();
                        ysz = getVisualModel().getDynamicMarkerSize().get(i).getHeight();
                    }

                    arrowhead = GJMarker.makeArrow(dXpix, dYpix, xsz, ysz, getVisualModel().getFill() != null);
                    af.setToRotation(Math.atan2(dYpix - Yopix, dXpix - Xopix), dXpix, dYpix);
                    arrowhead = af.createTransformedShape(arrowhead);

                    g2.setStroke(getVisualModel().getLineStroke().get(i));
                    g2.setPaint(getVisualModel().getLineColor().get(i));
                    g2.draw(path);

                    if (getVisualModel().getFill() != null) {
                        g2.setPaint(getVisualModel().getFill().get(i));
                        g2.fill(arrowhead);
                    }
                    g2.setPaint(getVisualModel().getLineColor().get(i));
                    g2.draw(arrowhead);

                    path.append(arrowhead, false);
                    getScreenDataArray().add(path);

                }
            }
        }

    }

    private double getFinalScaleFactor(final double[] dX, final double[] dY) {

        if (dX == null || dY == null || dX.length != dY.length || dX.length == 0) {
            return 1d;
        }

        QuiverExtra q = (QuiverExtra) getDataModel().getExtraObject();

        //Find the maximum spacing between vector origins in X and Y palce
        double spacingX = ArrayMath.max(ArrayMath.absi(ArrayMath.diffi(getXData().getRawDataValues())));
        double spacingY = ArrayMath.max(ArrayMath.absi(ArrayMath.diffi(getYData().getRawDataValues())));

        // Maximum distance for vectors along X and Y axes
        double dU = ArrayMath.max(dX);
        double dV = ArrayMath.max(dY);

        // Scale factor required for vectors to just fill the available x,y
        // spacing
        double scx = spacingX / dU;
        double scy = spacingY / dV;
        double scale2 = Math.min(scx, scy);

        // Calculate the final scaling based on user-setting
        double scale;
        if (q.getUserScaleFactor() == 0) {
            // If zero, do not scale - just use the values supplied in dX and dY
            scale = 1d;
        } else {
            // Stretch or shrink the factors according to the user-specified scale
            scale = q.getUserScaleFactor() * scale2;
        }
        return scale;
    }

    /**
     * @return the useQuad
     */
    public boolean isUseQuad() {
        QuiverExtra q = (QuiverExtra) getDataModel().getExtraObject();
        return q.isUseQuad();
    }

    /**
     * @param useQuad the useQuad to set
     */
    public void setUseQuad(boolean useQuad) {
        QuiverExtra q = (QuiverExtra) getDataModel().getExtraObject();
        boolean old = q.isUseQuad();
        q.setUseQuad(useQuad);
        getPCS().firePropertyChange("useQuad", old, useQuad);
    }

    public void setScale(double scale) {
        QuiverExtra q = (QuiverExtra) getDataModel().getExtraObject();
        double old = q.getUserScaleFactor();
        q.setUserScaleFactor(scale);
        getPCS().firePropertyChange("Scale", old, scale);
    }

    public double getScale() {
        QuiverExtra q = (QuiverExtra) getDataModel().getExtraObject();
        return q.getUserScaleFactor();
    }

    @Override
    protected String stringSupplement() {
        String str = "";
        StringBuilder s = new StringBuilder();
        Formatter f = new Formatter(s);
        if (getDataModel().getExtraData0() != null && getDataModel().getExtraData0().length > 0) {
            f.format("dx:\t");
            printData(f, getDataModel().getExtraData0());
        }
        if (getDataModel().getExtraData1() != null && getDataModel().getExtraData1().length > 0) {
            f.format("dy:\t");
            printData(f, getDataModel().getExtraData1());
        }
        str = str.concat(f.toString());
        return str;
    }
}
