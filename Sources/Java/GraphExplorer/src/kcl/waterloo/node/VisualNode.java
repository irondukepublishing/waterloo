/*
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London and the author 2011-
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
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
import groovy.util.NodeList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * VisualNode extends the groovy.util.Node class to support a parallel
 * DefaultTreeModel that can be used to display the node hierarchy.
 *
 * A JTree can be generated for any node in a hierarchy simply by calling the
 * {@code getTree} method.
 *
 * {@code  VisualNodes} can listen to TreeSelection and Mouse events from JTrees
 * created using the getTree method.
 *
 * <p>Differences between this class and the groovy.util,Node class:</p>
 * <P>Nodes can appear only once. Duplication and circular references are not
 * supported. Nodes will be removed from their existing parent when added to a
 * new parent.</p> <p> Tree nodes are represented via the MutableTreeNodeForNode
 * class that sub-classes the standard DefaultMutableTreeNode.</p>
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class VisualNode extends Node implements TreeModelListener, TreeSelectionListener, MouseListener {

    /**
     * DefaultMutableTreeNode subclass generated when a VisualNode is
     * constructed. Not use settable.
     */
    private MutableTreeNodeForNode treeNode = null;
    /**
     * A DefaultTreeModel associated with this VisualNode when getTree is called
     * on the node. Not user settable independently of the {@code getTree}
     * method.
     */
    private DefaultTreeModel model = null;
    private ArrayList<JTree> treeLookup = null;


    /*
     * Creates a root node called "Root Node"
     */
    public VisualNode() {
        this(null, null, null, null);
    }

    /**
     * Creates a root node.
     *
     * @param value
     */
    public VisualNode(Object value) {
        this(null, null, null, value);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name of the node (normally a String)
     */
    public VisualNode(VisualNode parentNode, Object name) {
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
     * @param name of the node (normally a String)
     * @param attributes a LinkedHashMap of properties associated with the node.
     */
    public VisualNode(VisualNode parentNode, Object name, Map attributes) {
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
     * @param name of the node (normally a String)
     * @param value an Object associated with this node
     */
    public VisualNode(VisualNode parentNode, Object name, Object value) {
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
     * @param name of the node (normally a String)
     * @param attributes a LinkedHashMap of properties associated with the node.
     * @param value an Object associated with this node
     */
    public VisualNode(VisualNode parentNode, Object name, Map attributes, Object value) {
        super(parentNode, name, attributes, value);
        if (parentNode == null) {
            if (name == null && value == null) {
                treeNode = new MutableTreeNodeForNode("Root Node", this);
            } else if (name != null) {
                treeNode = new MutableTreeNodeForNode(name, this);
            } else if (value != null) {
                treeNode = new MutableTreeNodeForNode(value, this);
            }
        } else {
            if (name != null) {
                treeNode = new MutableTreeNodeForNode(name, this);
            } else if (value != null) {
                treeNode = new MutableTreeNodeForNode(value, this);
            } else {
                treeNode = new MutableTreeNodeForNode(null, this);
            }
            ((VisualNode) parent()).treeNode.add(treeNode);
        }
        reload();
    }

    protected void addAttributesAsNodes() {
    }

    /**
     * Appends a VisualNode to this VisualNode.
     *
     * If the supplied child node already has a parent, it is removed from that
     * parent before being appended.
     *
     * @param child
     * @return
     */
    @Override
    public final boolean append(Node child) {
        if (!(child instanceof VisualNode)) {
            throw (new IllegalArgumentException("VisualNodeNode expected as input"));
        }
        if (child.parent() != null) {
            super.remove(child);
        }
        boolean flag = super.append(child);
        // Implicit removal from existing parent via DefaultMutableTreeNode method
        getTreeNode().add(((VisualNode) child).getTreeNode());
        reload();
        return flag;
    }

    @Override
    public boolean remove(Node child) {
        boolean flag = super.remove(child);
        getTreeNode().remove(((VisualNode) child).getTreeNode());
        reload();
        return flag;
    }

    @Override
    public VisualNode appendNode(Object name, Map attributes) {   
        return new VisualNode(this, name, attributes);
    }

    @Override
    public VisualNode appendNode(Object name) {
        return new VisualNode(this, name);
    }

    @Override
    public VisualNode appendNode(Object name, Object value) {
        return new VisualNode(this, name, value);
    }

    @Override
    public VisualNode appendNode(Object name, Map attributes, Object value) {
        return new VisualNode(this, name, attributes, value);
    }


    @Override
    public Object clone()  {
        return null;
    }

    /**
     * Call the {@code DefaultTreeModel#reload} method of all nodes that have an
     * associated model.
     */
    public final void reload() {
        NodeList list = (NodeList) getRoot().depthFirst();
        for (Object node : list) {
            if (((VisualNode) node).getModel() != null) {
                ((VisualNode) node).getModel().reload();
            }
        }
    }

    /**
     * Returns the root node.
     *
     * @return
     */
    public final VisualNode getRoot() {
        VisualNode thisNode = this;
        while (thisNode.parent() != null) {
            thisNode = (VisualNode) thisNode.parent();
        }
        return thisNode;
    }

    /**
     * Returns a JTree using the DefaultTreeModel for this node (which will be
     * generated as required).
     *
     * The returned JTree will be added to the treeLookup for this node.
     *
     * @return
     */
    public JTree getTree() {
        if (model == null) {
            model = new DefaultTreeModel(getTreeNode());
            model.addTreeModelListener(this);
        }
        JTree tree = new JTree(model);
        if (treeLookup == null) {
            treeLookup = new ArrayList<JTree>();
        }
        treeLookup.add(tree);
        tree.addTreeSelectionListener(this);
        tree.addMouseListener(this);
        tree.setExpandsSelectedPaths(true);
        return tree;
    }

    /**
     * Sets the value for this node.
     *
     * If the UserObject of the DefaultMutableTreeNode is equal to the existing
     * value of this node, the localTree node will also be updated with a call
     * to setUserObject.
     *
     * @param val
     */
    @Override
    public void setValue(Object val) {
        if (getTreeNode().getUserObject() == value()) {
            getTreeNode().setUserObject(val);
        }
        super.setValue(val);
        //();
    }

    /**
     * Returns the first node encountered with the specified value in a
     * depthFirst search.
     *
     * @param val
     * @return
     */
    public Node findNodeForValue(Object val) {
        NodeList arr = (NodeList) getRoot().depthFirst();
        for (Object o1 : arr) {
            Node node = (Node) o1;
            Object list = node.value();
            if (list != null && list instanceof NodeList) {
                NodeList list2 = (NodeList) list;
                if (list2.size() > 0 && list2.get(0) != null && list2.get(0).equals(val)) {
                    return node;
                }
            } else if (list.equals(val)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Returns the path to the specified VisualNode in the root node's JTree
     * model.
     *
     * @return
     */
    public TreeNode[] findPathToRoot() {
        if (getRoot().getModel() == null) {
            return null;
        } else {
            return getRoot().getModel().getPathToRoot(getTreeNode());
        }
    }

    public TreePath findTreePathToRoot() {
        ArrayList<TreeNode> copy = new ArrayList<TreeNode>(Arrays.asList(findPathToRoot()));
        return new TreePath(copy.toArray());
    }

    /**
     * Returns the path to the specified VisualNode entry in the supplied JTree
     * model.
     *
     * @param node
     * @param treeModel
     * @return
     */
    public TreeNode[] find(VisualNode node, DefaultTreeModel treeModel) {
        if (treeModel == null) {
            return null;
        } else {
            return treeModel.getPathToRoot(node.getTreeNode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
    }

    /**
     * @return the treeNode
     */
    public MutableTreeNodeForNode getTreeNode() {
        return treeNode;
    }

    /**
     * @return the model
     */
    private DefaultTreeModel getModel() {
        return model;
    }

    /**
     * @return the treeLookup
     */
    public ArrayList<JTree> getTreeLookup() {
        return treeLookup;
    }

    @Override
    public void treeNodesChanged(TreeModelEvent tme) {
    }

    @Override
    public void treeNodesInserted(TreeModelEvent tme) {
        reload();
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent tme) {
        reload();
    }

    @Override
    public void treeStructureChanged(TreeModelEvent tme) {
    }
}
