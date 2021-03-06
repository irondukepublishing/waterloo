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
import java.util.Arrays;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * @param <E>
 */
public class GJCyclicArrayList<E> extends ArrayList<E> {

    public GJCyclicArrayList() {
    }

    public GJCyclicArrayList(int n) {
        super(n);
    }

    public GJCyclicArrayList(ArrayList<E> list) {
        super(list);
    }

    public GJCyclicArrayList(E element) {
        super();
        add(element);
    }

    public GJCyclicArrayList(E[] elements) {
        addAll(Arrays.asList(elements));
    }

    @Override
    public E get(int index) {
        if (size() > 0) {
            return super.get(index % size());
        } else {
            return null;
        }
    }
}
