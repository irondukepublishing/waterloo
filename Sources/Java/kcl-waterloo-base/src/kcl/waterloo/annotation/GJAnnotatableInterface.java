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
package kcl.waterloo.annotation;

import java.util.ArrayList;

/**
 * Specifies the methods required for a container to support annotations.
 *
 * @param <T> for a Swing container, Path2D
 * @param <U> for a Swing container, GJGraphInterface
 * @param <V> for a Swing container, Stroke
 * @param <W> for a Swing container, Paint
 * @param <X> for a Swing container, Font
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJAnnotatableInterface<T, U, V, W, X> {

    /**
     * Adds an annotation to the set.
     *
     * @param a the annotation to add.
     */
    public void add(GJAnnotationInterface<T, U, V, W, X> a);

    /**
     * Returns an ArrayList of annotations
     *
     * @return the ArrayList
     */
    public ArrayList<GJAnnotationInterface<T, U, V, W, X>> getAnnotations();

    /**
     * Replaces the ArrayList of annotations with the supplied instance.
     *
     * @param a an ArrayList
     */
    public void setAnnotations(ArrayList<GJAnnotationInterface<T, U, V, W, X>> a);
}
