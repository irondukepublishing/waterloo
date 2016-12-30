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
package kcl.waterloo.graphics;

/**
 * This class is used to establish a link between the axes of two graphs.
 *
 * An <em>AxisLink</em> instance is added to a graph using its standard
 * <em>addLink</em> method to establish a link between the axes.
 *
 * The link is then specified as <strong>from</strong> the graph to which the
 * <em>AxisLink</em> instance is added and <strong>to</strong> the graph
 * referenced in the instance <em>pairedTraget</em> property.
 *
 * Paired <em>AxisLink</em> instances will usually be added to each graph to
 * establish a bilateral linkage.
 *
 * Note that an AxisLink instance is serializable to XML using the XMLEncoder,
 * but that a Persistence delegate must be specified to call the constructor. An
 * XML file containing an AxisLink instance will contain full information to
 * reconstruct the linked graph even when that graph is not part of the host
 * graph's hierarchy.
 *
 * @author Malcolm Lidierth
 */
public class AxisLink {

    /**
     * Specifies the axes to be linked.
     */
    public enum PAIRING {

        XX, YY, XY, YX
    }

    /**
     * Specifies the axes to be linked for this instance.
     */
    private final AxisLink.PAIRING pairing;
    /**
     * The graph to which this instance links (from the graph to which the link
     * is added).
     */
    private final GJAbstractGraph pairedTarget;

    /**
     * Constructor for an <em>AxisLink</em> instance.
     *
     * @param target the graph to link to
     * @param p an <em>AxisLink.PAIRING</em> instance specifying which axes
     * should be linked.
     */
    public AxisLink(GJAbstractGraph target, AxisLink.PAIRING p) {
        this.pairedTarget = target;
        this.pairing = p;
    }

    /**
     * Returns the linked graph for this instance.
     *
     * @return the pairedTarget
     */
    public GJAbstractGraph getPairedTarget() {
        return pairedTarget;
    }

    /**
     * Returns the AxisLink.PAIRING value for this instance.
     *
     * @return the pairing
     */
    public AxisLink.PAIRING getPairing() {
        return pairing;
    }

}
