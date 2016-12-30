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

/**
 * PolarExtra object. Provides support for graphs to be specified using both
 * Cartesian and polar coordinates.
 *
 *
 * @author malcolm
 */
public class PolarExtra extends FontSupport {

    public enum DATAMODE {

        POLAR, CARTESIAN, LOGPOLAR, CUSTOM
    }

    //    public enum DISPLAYMODE {
//
//        POLAR, CARTESIAN, POLAR_SQRT
//    }
    private DATAMODE dataMode = DATAMODE.POLAR;
//    private DISPLAYMODE displayMode = DISPLAYMODE.POLAR;

    /**
     * @return the dataMode
     */
    public DATAMODE getDataMode() {
        return dataMode;
    }

    /**
     * @param dataMode the dataMode to set
     */
    public void setDataMode(DATAMODE mode) {
        this.dataMode = mode;
    }

//    /**
//     * @return the displayMode
//     */
//    public DISPLAYMODE getDisplayMode() {
//        return displayMode;
//    }
//
//    /**
//     * @param displayMode the displayMode to set
//     */
//    public void setDisplayMode(DISPLAYMODE displayMode) {
//        this.displayMode = displayMode;
//    }
}
