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

import java.text.NumberFormat;
import java.util.ArrayList;

import kcl.waterloo.graphics.GJUtilities;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class LogTransform extends GJAbstractDataTransform {

    private final static LogTransform Instance = new LogTransform();
    private static final long serialVersionUID = 1L;
    private static NumberFormat nf = NumberFormat.getInstance();
    public static final ArrayList<Double> minorGrid = minorGridMaker();
    public static final ArrayList<Double> majorGrid = majorGridMaker();

    private LogTransform() {
        super();
    }

    public static LogTransform getInstance() {
        return Instance;
    }

    @Override
    public String getName() {
        return "ln";
    }

    @Override
    public final double getData(double val) {
        val = Math.log(val);
        return val;
    }

    @Override
    public final double[] getData(double[] arr) {
        for (int k = 0; k < arr.length; k++) {
            arr[k] = Math.log(arr[k]);
        }
        return arr;
    }

    @Override
    public final double getInverse(double val) {
        return Math.pow(Math.E, val);
    }

    @Override
    public final String getTickLabel(double val) {
        return "e" + GJUtilities.getSuperscripts(nf.format(val));
    }

    @Override
    public ArrayList<Double> getAxisTickPositions(double start, double stop, double inc) {
        return majorGrid;
    }

    private static ArrayList<Double> majorGridMaker() {
        ArrayList<Double> pos = new ArrayList<Double>();
        for (double s = -35d; s <= 35d; s++) {
            pos.add(s);
        }
        return pos;
    }

    @Override
    public ArrayList<Double> getMinorGridPositions(double start, double stop, double inc, int n) {
        return minorGrid;
    }

    private static ArrayList<Double> minorGridMaker() {
        return new ArrayList<Double>();
    }
}
