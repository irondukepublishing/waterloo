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
package kcl.waterloo.plotmodel2D;

import java.util.ArrayList;

/**
 * The GJScreenDataInterface specifies methods used when highlighting specific
 * plots by providing.
 * <ul>
 * <li> a setter for use when painting a plot</li>
 * <li> getters for use when responding e.g. to mouse clicks in a graph</li>
 * </ul>
 * The screen data array contains a Shape that is effectively a pixel-based copy
 * of the graphics drawn to screen during plot painting.
 *
 * @author Malcolm Lidierth
 */
public interface GJScreenDataInterface<T> {

    /**
     * @return the ScreenDataArray
     */
    public ArrayList<T> getScreenDataArray();

    /**
     * @return a shallow copy of the ScreenDataArray
     */
    public ArrayList<T> getScreenDataArrayAsCopy();

    /**
     * @param ScreenDataArray the ScreenDataArray to set
     */
    public void setScreenDataArray(ArrayList<T> ScreenDataArray);
}
