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
package kcl.waterloo.graphics.transforms;

import java.util.ArrayList;

/**
 * Concrete implementation of GJAbstractGraphAbstractTransform providing linear
 * axis support for graphs implementing GJGraphInterface
 *
 * NOPTransform is a single instance class. Retrieve a reference to the instance
 * by calling the static <code>getInstance</code> method
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class NOPTransform extends GJAbstractDataTransform {

    private static final NOPTransform Instance = new NOPTransform();
    private static final long serialVersionUID = 1L;

    /**
     * Private constructor
     */
    private NOPTransform() {
        super();
    }

    /**
     * Retrieves a reference to the instance
     *
     * @return the instance
     */
    public static NOPTransform getInstance() {
        return Instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getData(double val) {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getData(final double[] arr) {
        return arr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getInverse(double val) {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "linear";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getTickLabel(double val) {
        return nf.format(val);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ArrayList<Double> getAxisTickPositions(double start, double stop, double inc) {
        ArrayList<Double> pos = new ArrayList<Double>();
        //TODO
        // Trace back to stop this happenning during setup
        if ((stop - start) / inc > 200) {
            return pos;
        }

        for (double s = start; s < stop; s += inc) {
            pos.add(s);
        }
        return pos;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ArrayList<Double> getMinorGridPositions(double start, double stop, double inc, int n) {
        ArrayList<Double> pos = new ArrayList<Double>();
        //TODO
        // Trace back to stop this happenning during setup
        if ((stop - start) / inc > 200) {
            return pos;
        }
        for (double s = start; s < stop; s += inc / (double) n) {
            pos.add(Double.valueOf(s));
//            if (pos.size()>20){
//                return pos;
//            }
        }
        return pos;
    }

}
