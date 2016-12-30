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
package kcl.waterloo.graphics.plots2D;

/**
 * Interface to support updating of plots when the x or y transform of the
 * parent graph is changed.
 *
 * @author ML
 */
public interface GJTransformUpdateInterface {

    /**
     * Called by the parent graph property change listener if the transform
     * applied on either x or y axis is changed.
     *
     * The output indicates whether the graph needs some unspecified action
     * after the update. Custom plots with custom listeners can make use of
     * this.
     *
     * @return
     */
    public boolean transformUpdate();
}
