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
package kcl.waterloo.graphics;

/**
 * Components that implement the {@code GJCustomGraphLayoutInterface} are
 * treated specially by some Waterloo custom {@code SpringLayout} subclasses.
 * The {@code setConstraints} method will be called within the layout's
 * {@code layoutContainer} method <strong> before </strong> the superclass
 * {@code layoutContainer} method is called. This allows the constraints to be
 * updated, e.g. to align them with changing axes.
 *
 * @author ML
 */
public interface GJCustomGraphLayoutInterface {

    /**
     * Called by the layout to update the constraints.
     */
    public void setConstraints();
}
