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
package kcl.waterloo.graphics.plots2D;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import kcl.waterloo.math.geom.Cartesian;
import kcl.waterloo.math.geom.Coordinates;
import kcl.waterloo.math.ArrayMath;

/**
 * GJAbstractPolarPlot - abstract class extended by polar plots.
 *
 * @author ML
 */
public abstract class GJAbstractPolarPlot extends GJAbstractPlot implements GJPolarPlotInterface {

    public GJAbstractPolarPlot() {
        super();
    }

    public GJAbstractPolarPlot(GJAbstractPlot p) {
        super(p);
    }
//    public void setDataMode(String mode) {
//        mode = mode.toUpperCase();
//        if (mode.equals("POLAR")) {
//            setDataMode(PolarExtra.DATAMODE.POLAR);
//        } else if (mode.equals("CARTESIAN")) {
//            setDataMode(PolarExtra.DATAMODE.CARTESIAN);
//        } else if (mode.equals("CUSTOM")) {
//            setDataMode(PolarExtra.DATAMODE.CARTESIAN);
//        }
//    }

//    /**
//     * This does nothing. The methods should be implemented in concrete
//     * sub-classes if setMode is supported.
//     *
//     * @param mode
//     */
//    @Override
//    public void setDataMode(PolarExtra.DATAMODE mode) {
//    }
//
//    @Override
//    public PolarExtra.DATAMODE getDataMode() {
//        return ((PolarExtra) getDataModel().getExtraObject()).getDataMode();
//    }
    @Override
    public Rectangle2D getDataRange() {
        double[] xposition, yposition;
        if (this.getXDataValues() != null && this.getYDataValues() != null) {
            if (((PolarExtra) getDataModel().getExtraObject()).getDataMode() == PolarExtra.DATAMODE.POLAR) {
                Cartesian cart = Coordinates.polarToCartesian(getXDataValues(), getYDataValues());
                xposition = cart.getX();
                yposition = cart.getY();
            } else {
                xposition = getXDataValues();
                yposition = getYDataValues();
            }
            double[] x = ArrayMath.minmax(xposition);
            double[] y = ArrayMath.minmax(yposition);
            Rectangle2D r = new Rectangle.Double(x[0], y[0], x[1] - x[0], y[1] - y[0]);
            Rectangle2D r2;
            for (GJPlotInterface p : getPlotList()) {
                r2 = p.getDataRange();
                r = r.createUnion(r2);
            }
            return r;
        } else {
            return new Rectangle.Double(-1, -1, 2, 2);
        }
    }
//    public GJDataVectorInterface getThetaData() {
//        return getXData();
//    }
//
//    public GJDataVectorInterface getRhoData() {
//        return getYData();
//    }
//
//    public final void setThetaData(GJDataVectorInterface<?> o) {
//        setXData(o);
//    }
//
//    public final void setRhoData(GJDataVectorInterface<?> o) {
//        setYData(o);
//
//    }
//
//    public final void setThetaData(double[] x) {
//        setXData(x);
//    }
//
//    public final void setRhoData(double[] y) {
//        setYData(y);
//    }
//
//    public final void setThetaData(AbstractList<? extends Number> x) {
//        setXData(x);
//    }
//
//    public final void setThetaData(Object mx) {
//        setXData(mx);
//    }
//
//    public final void setThetaData(int[] x) {
//        setXData(x);
//    }
//
//    public final void setRhoData(int[] y) {
//        setYData(y);
//    }
//
//    public final void setRhoData(AbstractList<? extends Number> y) {
//        setYData(y);
//    }
//
//    public final void setRhoData(Object my) {
//        setYData(my);
//    }
//
//    public final double[] getThetaDataValues() {
//        return getXDataValues();
//    }
//
//    public final double[] getRhoDataValues() {
//        return getYDataValues();
//    }
}
