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
import java.awt.geom.Path2D;
import java.util.Formatter;
import static kcl.waterloo.graphics.plots2D.GJAbstractPlot.printData;
import kcl.waterloo.math.ArrayMath;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * Revisions: 06.10.2013 Fix multiplexing of line colors.
 *
 */
public final class GJErrorBar extends GJAbstractPlot {

    public GJErrorBar() {
        super();
    }

    public GJErrorBar(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface p = GJAbstractPlot.createInstance(new GJErrorBar());
        p.getDataModel().setExtraObject(new ErrorBarExtra());
        return p;
    }

    @Override
    public final void paintPlot(Graphics2D g2) {

        if (getParentGraph() == null || getDataModel() == null) {
            return;
        }

        /* Note: The ErrorBarExtra class was added for 1.1 Alpha3.
         * Need to account for its absence when deserializing earlier files here.
         */
        ErrorBarExtra extra = null;
        if (getDataModel().getExtraObject() != null) {
            extra = (ErrorBarExtra) getDataModel().getExtraObject();
        }

        /**
         * Extent sets the half-width of the lines drawn at the ends of the
         * error bars.
         */
        double extent = 7d;
        if (extra != null) {
            extent = extra.getExtent();
        }

        super.paintPlot(g2);

        final double[] xposition = getParentGraph().xPositionToPixeli(getXDataValues());
        final double[] yposition = getParentGraph().yPositionToPixeli(getYDataValues());

        getScreenDataArray().clear();

        Path2D path = new Path2D.Double();

        int N = getMultiplexLength();

        final double[] x = this.getXData().getRawDataValues();
        final double[] y = this.getYData().getRawDataValues();

        if (getDataModel().getExtraData3() != null && getDataModel().getExtraData3().length > 0) {
            final double[] lower
                    = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(
                                    ArrayMath.sub(y, getDataModel().getExtraData3())));
            boolean[] flag = new boolean[lower.length];
            if (extra != null && extra.getMode() == ErrorBarExtra.MODE.DATASIGN) {
                flag = ArrayMath.isLT(getParentGraph().getYTransform().getData(y), 0d);
            } else {
                ArrayMath.filli(flag, Boolean.TRUE);
            }
            for (int idx = 0; idx < N; idx++) {
                path.reset();
                for (int i = idx; i < xposition.length; i += N) {
                    if (!Double.isNaN(lower[i]) && flag[i]) {
                        path.moveTo(xposition[i] - extent, lower[i]);
                        path.lineTo(xposition[i] + extent, lower[i]);
                        path.moveTo(xposition[i], lower[i]);
                        path.lineTo(xposition[i], yposition[i]);
                    }
                }
                g2.setPaint(getVisualModel().getLineColor().get(idx));
                g2.setStroke(getVisualModel().getLineStroke().get(idx));
                g2.draw(path);
                getScreenDataArray().add(path);
            }
        }

        if (getDataModel().getExtraData1() != null && getDataModel().getExtraData1().length > 0) {
            final double[] upper
                    = getParentGraph().yPositionToPixeli(getParentGraph().getYTransform().getData(
                                    ArrayMath.add(y, getDataModel().getExtraData1())));
            boolean[] flag = new boolean[upper.length];
            if (extra != null && extra.getMode() == ErrorBarExtra.MODE.DATASIGN) {
                flag = ArrayMath.isGE(getParentGraph().getYTransform().getData(y), 0d);
            } else {
                ArrayMath.filli(flag, Boolean.TRUE);
            }
            for (int idx = 0; idx < N; idx++) {
                path.reset();
                for (int i = idx; i < xposition.length; i += N) {
                    if (!Double.isNaN(upper[i]) && flag[i]) {
                        path.moveTo(xposition[i] - extent, upper[i]);
                        path.lineTo(xposition[i] + extent, upper[i]);
                        path.moveTo(xposition[i], upper[i]);
                        path.lineTo(xposition[i], yposition[i]);
                    }
                }
                g2.setPaint(getVisualModel().getLineColor().get(idx));
                g2.setStroke(getVisualModel().getLineStroke().get(idx));
                g2.draw(path);
                getScreenDataArray().add(path);
            }
        }

        if (getDataModel().getExtraData2() != null && getDataModel().getExtraData2().length > 0) {
            path = new Path2D.Double();
            final double[] left
                    = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(
                                    ArrayMath.sub(x, getDataModel().getExtraData2())));
            boolean[] flag = new boolean[left.length];
            if (extra != null && extra.getMode() == ErrorBarExtra.MODE.DATASIGN) {
                flag = ArrayMath.isLT(getParentGraph().getYTransform().getData(y), 0d);
            } else {
                ArrayMath.filli(flag, Boolean.TRUE);
            }
            for (int idx = 0; idx < N; idx++) {
                path.reset();
                for (int i = idx; i < xposition.length; i += N) {
                    if (!Double.isNaN(left[i]) && flag[i]) {
                        path.moveTo(xposition[i], yposition[i]);
                        path.lineTo(left[i], yposition[i]);
                        path.moveTo(left[i], yposition[i] - extent);
                        path.lineTo(left[i], yposition[i] + extent);
                    }
                }
                g2.setPaint(getVisualModel().getLineColor().get(idx));
                g2.setStroke(getVisualModel().getLineStroke().get(idx));
                g2.draw(path);
                getScreenDataArray().add(path);
            }
        }

        if (getDataModel().getExtraData0() != null && getDataModel().getExtraData0().length > 0) {
            final double[] right
                    = getParentGraph().xPositionToPixeli(getParentGraph().getXTransform().getData(
                                    ArrayMath.add(x, getDataModel().getExtraData0())));
            boolean[] flag = new boolean[right.length];
            if (extra != null && extra.getMode() == ErrorBarExtra.MODE.DATASIGN) {
                flag = ArrayMath.isGE(getParentGraph().getYTransform().getData(y), 0d);
            } else {
                ArrayMath.filli(flag, Boolean.TRUE);
            }
            for (int idx = 0; idx < N; idx++) {
                path.reset();
                for (int i = idx; i < xposition.length; i += N) {
                    if (!Double.isNaN(right[i]) && flag[i]) {
                        path.moveTo(xposition[i], yposition[i]);
                        path.lineTo(right[i], yposition[i]);
                        path.moveTo(right[i], yposition[i] - extent);
                        path.lineTo(right[i], yposition[i] + extent);
                    }
                }
                g2.setPaint(getVisualModel().getLineColor().get(idx));
                g2.setStroke(getVisualModel().getLineStroke().get(idx));
                g2.draw(path);
                getScreenDataArray().add(path);
            }
        }
    }

    @Override
    protected String stringSupplement() {
        String str = "";
        StringBuilder s = new StringBuilder();
        Formatter f = new Formatter(s);
        if (getDataModel().getExtraObject() != null) {
            switch (((ErrorBarExtra) getDataModel().getExtraObject()).getMode()) {
                case NORMAL:
                    f.format("Mode:\tNORMAL");
                case DATASIGN:
                    f.format("Mode:\tDATASIGN");
            }
        }
        if (getDataModel().getExtraData1() != null && getDataModel().getExtraData1().length > 0) {
            f.format("Upper\t");
            printData(f, getDataModel().getExtraData1());
        }
        if (getDataModel().getExtraData3() != null && getDataModel().getExtraData3().length > 0) {
            f.format("Lower\t");
            printData(f, getDataModel().getExtraData3());
        }
        if (getDataModel().getExtraData2() != null && getDataModel().getExtraData2().length > 0) {
            f.format("Left\t");
            printData(f, getDataModel().getExtraData2());
        }
        if (getDataModel().getExtraData0() != null && getDataModel().getExtraData0().length > 0) {
            f.format("Right\t");
            printData(f, getDataModel().getExtraData0());
        }
        str = str.concat(f.toString());
        return str;
    }
}
