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

import java.awt.AlphaComposite;

/**
 *
 * @author ML
 */
public interface GJFillableInterface<T, U> {

    /**
     * Returns a shape representing the area to be filled
     *
     * @return T
     */
    public T getFillable();

    /**
     * Returns the object used to paint the area e.g. an AWT paint instance.
     *
     * @return U
     */
    public U getAreaPaint();

    /**
     * Sets the object used to paint the area e.g. an AWT paint instance.
     *
     */
    public void setAreaPaint(U paint);

    public AlphaComposite getFillComposite();

    /**
     * @param composite the composite to set
     */
    public void setFillComposite(AlphaComposite composite);

    public float getFillAlpha();

    public void setFillAlpha(float alpha);
}
