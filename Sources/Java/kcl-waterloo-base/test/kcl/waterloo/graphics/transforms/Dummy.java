 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.graphics.transforms;

import java.util.ArrayList;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Dummy extends GJAbstractDataTransform {

    private static final Dummy Instance = new Dummy();

    private Dummy() {
        super();
    }

    public static Dummy getInstance() {
        return Instance;
    }

    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public double getData(double val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getData(double[] src) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getInverse(double src) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getTickLabel(double val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Double> getAxisTickPositions(double start, double stop, double inc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Double> getMinorGridPositions(double start, double stop, double inc, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
