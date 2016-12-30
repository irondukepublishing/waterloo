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
package kcl.waterloo.graphics.plots2D;

/**
 *
 * @author ML
 */
public class ErrorBarExtra {

    private double extent = 7d;

    /**
     *
     */
    public enum MODE {

        NORMAL, DATASIGN
    }

    private MODE mode = MODE.NORMAL;

    /**
     * @return the extent
     */
    public double getExtent() {
        return extent;
    }

    /**
     * @param extent the extent to set
     */
    public void setExtent(double extent) {
        this.extent = extent;
    }

    /**
     * @return the mode
     */
    public MODE getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public void setMode(String s) {
        if (s.contains("DATA")) {
            mode = MODE.DATASIGN;
        } else {
            mode = MODE.NORMAL;
        }

    }
}
