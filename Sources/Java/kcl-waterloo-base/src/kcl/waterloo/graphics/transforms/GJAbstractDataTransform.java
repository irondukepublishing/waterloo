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

/**
 * Provides an abstract implementation of the GJDataTransformInterface.
 *
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public abstract class GJAbstractDataTransform implements GJDataTransformInterface {

    // 1.1
    // Change this from being static - should be intance property
    // Remove 
    protected final NumberFormat nf = NumberFormat.getInstance();

    /**
     * Returns the inverse transformed data for a double[]
     *
     * @param arr
     * @return a double[]
     */
    @Override
    public double[] getInverse(final double[] arr) {
        for (int k = 0; k < arr.length; k++) {
            arr[k] = getInverse(arr[k]);
        }
        return arr;
    }

    public NumberFormat getNumberFormatter() {
        return nf;
    }
}
