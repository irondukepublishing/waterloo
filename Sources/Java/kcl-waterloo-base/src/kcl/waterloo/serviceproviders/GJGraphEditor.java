 /* 
 * <p>This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/></p>
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
package kcl.waterloo.serviceproviders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kcl.waterloo.defaults.Colors;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.gui.gjgraph.GeneralOptions;
import kcl.waterloo.gui.gjgraph.LayerEditor;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
class GJGraphEditor extends JPanel implements GJEditorInterface, PropertyChangeListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    public JTabbedPane tabbedPane;
    transient BindingManager mgr;
    GJGraphInterface graph;
    LayerEditor layerEditor;
    private GeneralOptions containerOptionsPanel;

    /**
     * Create the panel.
     *
     * @param gr GJGraphInterface to edit
     */
    GJGraphEditor(GJGraphInterface gr) {
        graph = gr;
        setBorder(null);
        setLayout(new VerticalLayout());

        containerOptionsPanel = new GeneralOptions();
        if (gr.getGraphContainer() != null) {
            containerOptionsPanel.getBackGroundCombo().setMap(Colors.getColors(), gr.getGraphContainer().getBackground());
            containerOptionsPanel.getFontPanel().getFontSelector().getColorCombo().setMap(Colors.getColors(), gr.getGraphContainer().getForeground());
        }
        containerOptionsPanel.setPreferredSize(new Dimension(665, 200));
        add(containerOptionsPanel);

        tabbedPane = new JTabbedPane(SwingConstants.TOP);
        //tabbedPane.setPreferredSize(new Dimension(665, 370));
        tabbedPane.setName("$LayerTabbedPane");
        tabbedPane.setAutoscrolls(true);
        add(tabbedPane);

        if (gr != null) {
            int n = 0;
            for (GJGraphInterface layer : gr.getLayers()) {
                layerEditor = new LayerEditor();
                layerEditor.setBorder(new LineBorder(new Color(0, 0, 0)));
                layerEditor.getBackGroundCombo().setMap(Colors.getColors(), ((Component) layer).getBackground());
                layerEditor.getGridCombo().setMap(Colors.getColors(), layer.getMajorGridColor());
                layerEditor.getAxisCombo().setMap(Colors.getColors(), layer.getAxisColor());
                tabbedPane.addTab("Layer " + n++, layerEditor);
            }
        } else {
            layerEditor = new LayerEditor();
            layerEditor.setBorder(new LineBorder(new Color(0, 0, 0)));
            layerEditor.getBackGroundCombo().setMap(Colors.getColors(), Color.WHITE);
            layerEditor.getGridCombo().setMap(Colors.getColors(), Color.BLACK);
            layerEditor.getAxisCombo().setMap(Colors.getColors(), Color.BLACK);
            tabbedPane.addTab("Layer 0", layerEditor);
        }
        init();
    }

    private void init() {
        tabbedPane.setSelectedIndex(graph.getCurrentLayer().indexOf());
        mgr = new BindingManager(this);
        graph.setEditor(this);
        tabbedPane.addChangeListener(this);
    }

    @Override
    public void setState(int state) {
        Frame f = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
        if (f != null) {
            f.setExtendedState(state);
            if (state == Frame.NORMAL) {
                f.toFront();
            }
        }
    }

    @Override
    public int getState() {
        Frame f = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
        return f.getExtendedState();
    }

    @Override
    public boolean isShowing() {
        Frame f = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
        return f != null && f.isShowing();
    }

    @Override
    public void setVisible(boolean flag) {
        Frame f = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
        if (f != null) {
            f.setVisible(flag);
        }
        refresh();
    }

    @Override
    public void refresh() {
        mgr.init();
    }

    @Override
    public GJAbstractGraphContainer getGraphContainer() {
        return graph.getGraphContainer();
    }

    @Override
    public int getSelectedTab() {
        return tabbedPane.getSelectedIndex();
    }

    @Override
    public void setSelectedTab(int n) {
        tabbedPane.setSelectedIndex(n);
    }

    @Override
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    @Override
    public GJGraphInterface getLayerForTab() {
        int k = tabbedPane.getSelectedIndex();
        return graph.getLayer(k);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        int idx = graph.getLayers().indexOf(graph.getCurrentLayer());
        if (idx < 0) {
            idx = 0;
        }
        tabbedPane.setSelectedIndex(idx);
        refresh();
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        graph.setCurrentLayerIndex(tabbedPane.getSelectedIndex());
    }

    /**
     * @return the containerOptionsPanel
     */
    @Override
    public GeneralOptions getContainerOptionsPanel() {
        return containerOptionsPanel;
    }
}
