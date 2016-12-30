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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class PropertyListenerNode extends VisualNode {

    /*
     * Creates a root node called "Root Node"
     */
    public PropertyListenerNode() {
        this(null, null, null, null);
    }

    /**
     * Creates a root node.
     *
     * @param value
     */
    public PropertyListenerNode(Object value) {
        this(null, null, null, value);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name of the root (normally a String)
     */
    public PropertyListenerNode(VisualNode parentNode, Object name) {
        this(parentNode, name, new LinkedHashMap(), null);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * Values passed in a LinkedHashMap will be added to the DefaultTreeModel
     * and will therefore be displayed visually but do not form part of the node
     * VisualNode hierarchy.
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name the name of the created node
     * @param attributes a LinkedHashMap of properties associated with the node.
     */
    public PropertyListenerNode(VisualNode parentNode, Object name, Map attributes) {
        this(parentNode, name, attributes, null);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * The label for the JTree display will be {@code name.toString} unless name
     * is null, in which case {@code value.toString} will be used.
     *
     * Note that when {@code name} is used, it permanently sets the JTree
     * display but when {@code value} is used, the display can be updated using
     * {@code setValue}
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name the name of the created node
     * @param value an Object associated with this node
     */
    public PropertyListenerNode(VisualNode parentNode, Object name, Object value) {
        this(parentNode, name, null, value);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * The label for the JTree display will be {@code name.toString} unless name
     * is null, in which case {@code value.toString} will be used.
     *
     * Note that when {@code name} is used, it permanently sets the JTree
     * display but when {@code value} is used, the display can be updated using
     * {@code setValue}
     *
     * Values passed in a LinkedHashMap will be added to the DefaultTreeModel
     * and will therefore be displayed visually but do not form part of the node
     * VisualNode hierarchy.
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name the name of the created node
     * @param attributes a LinkedHashMap of properties associated with the node.
     * @param value an Object associated with this node
     */
    public PropertyListenerNode(VisualNode parentNode, Object name, Map attributes, Object value) {
        super(parentNode, name, attributes, value);
        ListenerInstaller.installListeners(this);
    }


}
