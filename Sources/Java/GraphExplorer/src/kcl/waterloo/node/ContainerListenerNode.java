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
import groovy.util.NodeList;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * Provides a VisualNode subclass that listens to the addition/removal of
 * components from a Container.
 *
 * ContainerListenerNode needs to be installed only on the top container. The
 * node hierarchy will then be updated automatically via the ContainerListeners
 * as components are added.
 *
 * When components that are Containers themselves are added, they too will be
 * listened to, so the JTree should remain up-to-date.
 *
 * With a pre-existing container, a ContainerListenerNode can be created using
 * the static {@code createNode(Container c)} method which searches the
 * container hierarchy recursively to populate the node.
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ContainerListenerNode extends VisualNode implements ContainerListener {

    /*
     * Creates a root node called "Root Node"
     */
    public ContainerListenerNode() {
        this(null, null, null, null);
    }

    /**
     * Creates a root node.
     *
     * @param value
     */
    public ContainerListenerNode(Object value) {
        this(null, null, null, value);
    }

    /**
     * Creates a node as a child of a specified node.
     *
     * @param parentNode the parent VisualNode (set null to create a root node)
     * @param name of the root (normally a String)
     */
    public ContainerListenerNode(VisualNode parentNode, Object name) {
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
    public ContainerListenerNode(VisualNode parentNode, Object name, Map attributes) {
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
    public ContainerListenerNode(VisualNode parentNode, Object name, Object value) {
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
    public ContainerListenerNode(VisualNode parentNode, Object name, Map attributes, Object value) {
        super(parentNode, name, attributes, value);
        ((Container) value).addContainerListener(this);
        ListenerInstaller.installListeners(this);
        addAttributesAsNodes();
    }

    @Override
    protected final void addAttributesAsNodes() {
        if (attributes() != null) {
            for (Object key : this.attributes().keySet()) {
                PropertyListenerNode node = new PropertyListenerNode(this, key, null, attributes().get(key));
                append(node);
            }
        }
    }

    public static ContainerListenerNode createNode(Container container) {
        ContainerListenerNode node = new ContainerListenerNode(container);
        if (container.getComponentCount() > 0) {
            for (int k = 0; k < container.getComponentCount(); k++) {
                Component c = container.getComponent(k);
                if (c instanceof Container) {
                    addContainerToHierarchy(node, (Container) c);
                } else {
                    node.append(new PropertyListenerNode(node, null, null, c));
                }
            }
        }
        return node;
    }

    @Override
    public boolean remove(Node child) {
        if (child.value() instanceof NodeList) {
            NodeList list = (NodeList) child.value();
            for (Object o : list) {
                if (o instanceof Container) {
                    ((Container) o).removeContainerListener(this);
                }
            }
        } else {
            ((Container) child.value()).removeContainerListener(this);
        }
        return super.remove(child);
    }

    @Override
    public void componentAdded(ContainerEvent ce) {
        if (ce.getChild() instanceof Container) {
            addContainerToHierarchy(this, (Container) ce.getChild());
        } else {
            append(new PropertyListenerNode(this, null, null, ce.getChild()));
        }
        //reload();
    }

    private static void addContainerToHierarchy(VisualNode parent, Container container) {

        ContainerListenerNode node = new ContainerListenerNode(parent, null, null, container);
        ListenerInstaller.installListeners(node);

        parent.append(node);

        if (container.getComponentCount() > 0) {
            for (int k = 0; k < container.getComponentCount(); k++) {
                Component c = container.getComponent(k);
                if (c instanceof Container) {
                    addContainerToHierarchy(node, (Container) c);
                } else {
                    parent.append(new PropertyListenerNode(node, null, null, c));
                }
            }
        }
    }

    @Override
    public void componentRemoved(ContainerEvent ce) {
        remove(findNodeForValue(ce.getChild()));
        reload();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        JTree localTree = (JTree) me.getSource();
        TreePath path = localTree.getSelectionPath();
        if (path != null) {
            Object o1 = path.getLastPathComponent();
            if (o1 != null) {
                Object o2 = ((MutableTreeNodeForNode) o1).getUserObject();
                if (o2 instanceof NodeList) {
                    o2 = ((NodeList) o2).get(0);
                }
                if (o2 instanceof Component) {
                    Component c = (Component) o2;
                    LinkedHashMap<Component, Method> hierarchy = new LinkedHashMap<Component, Method>();
                    Component target = c;
                    Component parent = c.getParent();
                    while (parent != null) {
                        try {
                            Method m = parent.getClass().getMethod("setSelectedComponent", new Class[]{Component.class});
                            if (m != null) {
                                hierarchy.put(target, m);
                            }
                        } catch (NoSuchMethodException ex) {
                        } catch (SecurityException ex) {
                        }
                        target = parent;
                        parent = parent.getParent();
                    }
                    for (Map.Entry<Component, Method> set : hierarchy.entrySet()) {
                        try {
                            set.getValue().invoke(set.getKey().getParent(), set.getKey());
                        } catch (IllegalAccessException ex) {
                        } catch (IllegalArgumentException ex) {
                        } catch (InvocationTargetException ex) {
                        }
                    }
                }
            }
        }
        // Restore the tree selection path - may have been alter by change listeners invoked by
        // setSelectedComponent above.
        localTree.setSelectionPath(path);
    }
}
