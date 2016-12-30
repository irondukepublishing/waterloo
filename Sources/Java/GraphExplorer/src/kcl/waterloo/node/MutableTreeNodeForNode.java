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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.node;

import groovy.util.Node;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class MutableTreeNodeForNode extends DefaultMutableTreeNode {

    final private Node node;
    private int maxStringlength = 55;

    public MutableTreeNodeForNode(Object o, Node n) {
        super(o);
        node = n;
    }

    /**
     * Copy constructor used by clone.
     *
     * @param n
     */
    private MutableTreeNodeForNode(MutableTreeNodeForNode n) {
        node = n.node;
        maxStringlength = n.maxStringlength;
    }

    /**
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        String s = super.toString();
        if (getMaxStringlength() > 0) {
            if (s.length() > getMaxStringlength()) {
                s = s.substring(0, getMaxStringlength());
                s = s + "...";
            }
        }
        return s;
    }

    /**
     * @return the maxStringlength
     */
    public int getMaxStringlength() {
        return maxStringlength;
    }

    /**
     * @param maxStringlength the maxStringlength to set
     */
    public void setMaxStringlength(int maxStringlength) {
        this.maxStringlength = maxStringlength;
    }

    @Override
    public MutableTreeNodeForNode clone() {
        return new MutableTreeNodeForNode((MutableTreeNodeForNode) super.clone());
    }
}
