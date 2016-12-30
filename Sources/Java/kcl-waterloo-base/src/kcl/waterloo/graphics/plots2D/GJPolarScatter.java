/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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
 *
 * @author ML
 */
public class GJPolarScatter extends GJScatter implements GJPolarPlotInterface {

    public GJPolarScatter() {
        super();
    }

    public GJPolarScatter(GJAbstractPlot p) {
        super(p);
    }

    public static GJPlotInterface createInstance() {
        GJPlotInterface instance = GJAbstractPlot.createInstance(new GJPolarScatter());
        instance.getDataModel().setExtraObject(new PolarExtra());
        return instance;
    }

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
}
