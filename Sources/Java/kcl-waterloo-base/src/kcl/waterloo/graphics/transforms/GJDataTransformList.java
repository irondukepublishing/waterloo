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
import java.util.Arrays;

/**
 *
 * @author ML
 */
public class GJDataTransformList extends ArrayList<GJDataTransformInterface> {

    /**
     * Set true if the transform on an axis is dependent on the values on any
     * other axes: x'=f(x,y) rather than x'=f(x) for example.
     */
    private boolean crossDependent = false;

    public GJDataTransformList() {
    }

    public GJDataTransformList(GJDataTransformInterface... in) {
        addAll(Arrays.asList(in));
    }

    /**
     * @return the crossDependent
     */
    public boolean isCrossDependent() {
        return crossDependent;
    }

    /**
     * @param crossDependent the crossDependent to set
     */
    public void setCrossDependent(boolean crossDependent) {
        this.crossDependent = crossDependent;
    }
}
