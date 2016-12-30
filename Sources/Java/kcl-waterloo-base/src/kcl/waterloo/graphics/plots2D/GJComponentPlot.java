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
package kcl.waterloo.graphics.plots2D;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * GJComponentPlot is an exception to the norm. Instead of plotting data,
 * GJComponentPlot adds Swing JComponents to the graph at the locations
 * specified in XData and YData - JLabels, JButtons, even JPanels with web pages
 * in them if you really want to.
 *
 * As graph objects use a SpringLayout, the preferred size property of the added
 * components can be used to control their size. They will be centered on the
 * point defined in XData and YData (horizontally and vertically).
 *
 * Effectively, GJComponentPlot creates a scatter plot where each point is
 * represented by a Swing component. These components are programmable just as
 * they would be in any GUI so you can use them to allow a high level of
 * user-interaction with the graph.
 *
 * <p>
 * This code is part of the Waterloo Scientific Graphics package from King's
 * College London <http://waterloo.sourceforge.net/></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJComponentPlot extends GJAbstractPlot {

    private static final long serialVersionUID = 1L;
    /**
     * ComponentData are private for this plot type
     */
    private ArrayList<JComponent> componentArray = new ArrayList<JComponent>();
    /**
     * Flag used to control adding of components to the graph
     */
    private boolean upToDate = true;

    public GJComponentPlot() {
    }

    public static GJPlotInterface createInstance() {
        return GJAbstractPlot.createInstance(new GJComponentPlot());
    }

    public void setComponentsVisible(boolean flag) {
        for (JComponent c : componentArray) {
            c.setVisible(flag);
        }
    }

    public ArrayList<JComponent> getComponentArray() {
        return componentArray;
    }

    public final void setComponentArray(ArrayList<JComponent> c) {
        componentArray = c;
        upToDate = false;
    }

    public final JComponent getComponent(int index) {
        if (index < componentArray.size()) {
            return componentArray.get(index);
        } else {
            return null;
        }
    }

    public final void setPreferredSize(int width, int height) {
        for (JComponent c : componentArray) {
            c.setPreferredSize(new Dimension(width, height));
        }
    }

    public final ArrayList<Dimension> getPreferredSize() {
        ArrayList<Dimension> szs = new ArrayList<Dimension>(componentArray.size());
        for (JComponent c : componentArray) {
            szs.add(c.getPreferredSize());
        }
        return szs;
    }

    public final void setPreferredSize(ArrayList<Dimension> arr) {
        int k = 0;
        for (Dimension d : arr) {
            componentArray.get(k).setPreferredSize(d);
            k++;
        }
    }

    public void setComponents(JComponent c) {
        setComponents(c.getClass());
    }

    public final void setComponents(final Class clzz) {
        final ArrayList<JComponent> arr = new ArrayList<JComponent>();
//        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
        double[] theseXData = getXDataValues();
        double[] theseYData = getYDataValues();

        if (theseXData == null || theseYData == null) {
            return;
        }

        for (int k = 0; k < theseXData.length; k++) {
            try {
                JComponent c = (JComponent) clzz.newInstance();
                c.setPreferredSize(new Dimension(15, 15));
                arr.add(c);
            } catch (InstantiationException ex) {
                //Logger.getLogger(GJComponentPlot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                //Logger.getLogger(GJComponentPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//                return;

//            @Override
//            public void done() {
        setComponentArray(arr);
        paintPlot(null);
    }
//            }
//        };
//        worker.execute();
//    }

    /**
     * Removes all components associated with this plot from the graph
     */
    public void removeComponents() {
        for (JComponent c : componentArray) {
            c.getParent().remove(c);
        }
        upToDate = false;
    }

    /**
     * This paintPlot for does NOT do any painting. Instead it calls the add
     * method of the parent graph and places swing components at the required
     * locations. These will become children of the graph, which is a JComponent
     * itself and will therefore be painted as normal from the paintChildren
     * method of the graph.
     *
     * The components need be added only once i.e. the first call to paintPlot.
     * The upToDate flag is used to control this. upToDate should be set false
     * when the data are first added to the plot. Adding the components to the
     * graph in this way allows us to construct a plot with "orphan" JComponents
     * so it behaves in the same way as other plot types rather than being
     * linked to a parent JComponent.
     *
     * The update includes a test for components that have been added previously
     * using the standard add(Component) method. This will usually only be the
     * case when added as part of a de-serialization.
     *
     * @param g2
     */
    @Override
    public void paintPlot(Graphics2D g2) {
        if (!upToDate) {
            double[] theseXData = getXDataValues();
            double[] theseYData = getYDataValues();

            if (theseXData == null || theseYData == null || componentArray == null || getParentGraph() == null) {
                return;
            }

            int i = 0;
            for (JComponent c : componentArray) {
                if (c.getParent() == null) {
                    // Add components
                    getParentGraph().add(c, theseXData[i], theseYData[i]);
                } else {
                    // Components have been added during deserialization. Ensure sizes are set
                    // and re-add them using overridden add method in the graph.
                    setPreferredSize(getPreferredSize());
                    getParentGraph().add(c, theseXData[i], theseYData[i]);
                    c.revalidate();
                }
                i++;
            }
            ((JComponent) getParentGraph()).revalidate();

            upToDate = true;
        } else if (getParentGraph() != null) {
            ((JComponent) getParentGraph()).revalidate();
        }
    }

    @Override
    public boolean isMultiplexible() {
        return false;
    }
}
