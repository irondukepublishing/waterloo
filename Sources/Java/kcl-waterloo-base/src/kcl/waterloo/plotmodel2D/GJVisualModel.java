 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.plotmodel2D;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import kcl.waterloo.marker.GJMarker;

/**
 * Concrete implementation of {@code GJAbstractVisualModel<GJMarker, Paint,
 * BasicStroke, Dimension, Shape>}
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJVisualModel extends GJAbstractVisualModel<GJMarker, Paint, BasicStroke, Dimension, Shape> {

    public GJVisualModel() {
        super();
    }
}
