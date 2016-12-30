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
package kcl.waterloo.marker;

import java.awt.geom.PathIterator;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJPathSegmentInfo {

    private int type;
    private double[] data = new double[6];

    public GJPathSegmentInfo() {
        type = PathIterator.SEG_CLOSE + PathIterator.SEG_CUBICTO;
    }

    public GJPathSegmentInfo(int t, double[] d) {
        type = t;
        setData(d);
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param t the type to set
     */
    public void setType(int t) {
        this.type = t;
    }

    /**
     * @return the data
     */
    public double[] getData() {
        return data.clone();
    }

    /**
     * @param d
     */
    public final void setData(double[] d) {
        data = d.clone();
    }
}
