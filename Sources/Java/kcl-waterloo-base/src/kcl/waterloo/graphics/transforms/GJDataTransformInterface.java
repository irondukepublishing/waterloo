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

/**
 * Provides facilities to transform data from the plots and to provide string
 * and grid position data for the graph The interface needs to be implemented by
 * classes providing e.g. a log transform of the plotted data and axes
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJDataTransformInterface {

    /**
     * The name for this transform as a String
     *
     * @return a name
     */
    public String getName();

    /**
     * Transforms and returns a single value.
     *
     * @param val the value to transform
     * @return the transformed value
     */
    public double getData(double val);

    /**
     * Transforms and returns an array of values.
     *
     * Note that, as any concrete implementation can transform the src array by
     * reference, it is recommended that src be a defensive copy of the required
     * input.
     *
     * An output is required for pass-by-value systems like MATLAB
     *
     * @param arr array to transform
     * @return a tranformed array that may be a reference to src or a copy.
     */
    public double[] getData(double[] arr);

    /**
     * Returns the inverse transform of the specified value. Returns Double.NaN
     * if the method is not implemented.
     *
     * @param val
     * @return a double value
     */
    public double getInverse(double val);

    /**
     * Returns the inverse transform of the specified array. Returns Double.NaNs
     * if the method is not implemented.
     *
     * @param val
     * @return a double[]
     */
    public double[] getInverse(double[] val);

    /**
     * Returns a String based on the input value which can be used to label the
     * axes. The output string can be empty if no label is required
     *
     * @param val
     * @return an Object
     */
    public Object getTickLabel(double val);

    /**
     *
     *
     * @param start typically the left-hand/bottom of the axis range
     * @param stop typically the right-hand/top of the axis range
     * @param inc distance between major grids
     * @return values for the major grid positions (already transformed)
     */
    public ArrayList<Double> getAxisTickPositions(double start, double stop, double inc);

    /**
     *
     *
     * @param start
     * @param stop
     * @param inc
     * @param n number of minor grid positions
     * @return values for the minor grid positions (n-2 entries as the 0th and
     * nth coincide with the major grid). These values should be added to the
     * axis tick positions to get the minor grid positions (already transformed)
     *
     */
    public ArrayList<Double> getMinorGridPositions(double start, double stop, double inc, int n);
}
