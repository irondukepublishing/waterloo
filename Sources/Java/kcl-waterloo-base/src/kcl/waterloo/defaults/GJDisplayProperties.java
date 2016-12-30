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
package kcl.waterloo.defaults;

import java.util.LinkedHashMap;

/**
 *
 * @author ML
 */
public class GJDisplayProperties {

    private final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

    public GJDisplayProperties() {
        map.put("grid", Boolean.FALSE);
        map.put("majorgrid", Boolean.FALSE);
        map.put("minorgrid", Boolean.FALSE);

        map.put("leftaxis", Boolean.TRUE);
        map.put("bottomaxis", Boolean.TRUE);
        map.put("rightaxis", Boolean.TRUE);
        map.put("topaxis", Boolean.TRUE);

        map.put("leftlabel", Boolean.FALSE);
        map.put("bottomlabel", Boolean.TRUE);
        map.put("rightlabel", Boolean.FALSE);
        map.put("toplabel", Boolean.TRUE);

        map.put("box", Boolean.FALSE);

        map.put("xtransform", "NOP");
        map.put("ytransform", "NOP");

    }

    public final void put(String key, Object value) {
        if (map.containsKey(key) && (map.get(key).getClass().isAssignableFrom(value.getClass()))) {
            map.put(key, value);
        }
    }

    public final Object get(String key) {
        return map.get(key);
    }
}
