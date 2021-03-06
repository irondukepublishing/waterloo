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
package kcl.waterloo.graphics.transforms;

import java.util.ArrayList;

import kcl.waterloo.graphics.GJUtilities;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class Log10Transform extends GJAbstractDataTransform {

    private static final Log10Transform Instance = new Log10Transform();
    private static final long serialVersionUID = 1L;
    private static final ArrayList<Double> minorGrid = minorGridMaker();
    private static ArrayList<Double> majorGrid = majorGridMaker();

    private Log10Transform() {
        super();
    }

    public static Log10Transform getInstance() {
        return Instance;
    }

    @Override
    public String getName() {
        return "log10";
    }

    @Override
    public final double getData(double val) {
        return Math.log10(val);
    }

    @Override
    public final double[] getData(final double[] arr) {
        for (int k = 0; k < arr.length; k++) {
            arr[k] = Math.log10(arr[k]);
        }
        return arr;
    }

    @Override
    public final double getInverse(double val) {
        return Math.pow(10, val);
    }

    @Override
    public final String getTickLabel(double val) {
        if (val == -0) {
            val = 0;
        }
        return "10" + GJUtilities.getSuperscripts(nf.format(val));
    }

    @Override
    public ArrayList<Double> getAxisTickPositions(double start, double stop, double inc) {
        if (stop - start < 10) {
            //double diff=java.lang.Math.ceil(java.lang.Math.log10(java.lang.Math.pow(2,java.lang.Math.getExponent(stop-start))));
            if (inc < 1) {
                Log10Transform.majorGrid = majorGridMaker(inc * 2d);
            } else {
                Log10Transform.majorGrid = majorGridMaker();
            }
        }

        start = Math.pow(10, start);
        stop = Math.pow(10, stop);
        if (start <= 0) {
            start = 10e-15;
        }

        int idx0 = 0, idx1 = majorGrid.size() - 1;
        for (int k = 1; k < majorGrid.size() - 1; k++) {
            if (Math.pow(10, majorGrid.get(k)) > start) {
                idx0 = k - 1;
                break;
            }
        }
        for (int k = 1; k < majorGrid.size() - 3; k++) {
            if (Math.pow(10, majorGrid.get(k)) > stop) {
                idx1 = k + 2;
                break;
            }
        }
        return new ArrayList<Double>(majorGrid.subList(idx0, idx1));
    }

    private static ArrayList<Double> majorGridMaker() {
        return majorGridMaker(1d);
    }

    private static ArrayList<Double> majorGridMaker(double inc) {
        ArrayList<Double> pos = new ArrayList<Double>();
        for (double s = -15; s <= 15; s += inc) {
            pos.add(s);
        }
        return pos;
    }

    @Override
    public ArrayList<Double> getMinorGridPositions(double start, double stop, double inc, int n) {
        return minorGrid;
    }

    private static ArrayList<Double> minorGridMaker() {
        ArrayList<Double> pos = new ArrayList<Double>();
        for (double s = 1e-16; s < 1e16; s *= 10d) {
            for (double s2 = 2d; s2 <= 9d; s2++) {
                pos.add(Math.log10(s * s2));
            }
        }
        return pos;
    }

}
