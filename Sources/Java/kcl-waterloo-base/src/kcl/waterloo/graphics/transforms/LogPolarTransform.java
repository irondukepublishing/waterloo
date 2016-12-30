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
package kcl.waterloo.graphics.transforms;

import java.util.ArrayList;

/**
 *
 * @author ML
 */
public class LogPolarTransform implements GJMultiAxisDataTransformInterface {

    public LogPolarTransform() {
    }

    public String getName() {
        return "logPolar";
    }

    public double getRho(double x, double y) {
        return Math.log10(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public double getTheta(double x, double y) {
        return Math.atan(y / x);
    }

    public double[] getData(double x, double y) {
        return new double[]{getTheta(x, y), getRho(x, y)};
    }

    public double[][] getData(double[] x, double[] y) {
        double[] rho = new double[x.length];
        double[] theta = new double[x.length];
        for (int k = 0; k < x.length; k++) {
            rho[k] = getRho(x[k], y[k]);
            theta[k] = getTheta(x[k], y[k]);
        }
        return new double[][]{rho, theta};
    }

    public double getX(double theta, double rho) {
        return Math.pow(Math.E, rho) * Math.cos(theta);
    }

    public double getY(double theta, double rho) {
        return Math.pow(Math.E, rho) * Math.sin(theta);
    }

    //@Override
    public double[] getInverse(double theta, double rho) {
        return new double[]{getX(theta, rho), getY(theta, rho)};
    }

    //@Override
    public double[][] getInverse(double[] theta, double[] rho) {
        double[] x = new double[theta.length];
        double[] y = new double[theta.length];
        for (int k = 0; k < x.length; k++) {
            x[k] = getX(x[k], y[k]);
            y[k] = getY(x[k], y[k]);
        }
        return new double[][]{x, y};
    }

    //@Override
    public Object getTickLabel(double val) {
        return null;
    }

    //@Override
    public ArrayList getAxisTickPositions(Object o, double start, double stop, double inc) {
        return null;
    }

    //@Override
    public ArrayList getMinorGridPositions(Object o, double start, double stop, double inc, int n) {
        return null;
    }
}
