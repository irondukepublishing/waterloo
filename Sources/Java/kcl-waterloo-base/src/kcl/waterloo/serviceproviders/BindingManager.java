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
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import kcl.waterloo.graphics.GJAbstractGraph;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.graphics.transforms.GJDataTransformInterface;
import kcl.waterloo.widget.GJColorComboBox;
import kcl.waterloo.widget.GJDial;
import org.jdesktop.swingx.painter.effects.NeonBorderEffect;
import org.jdesktop.swingx.painter.effects.ShadowPathEffect;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
@SuppressWarnings("ConstantConditions")
public class BindingManager implements ActionListener, ChangeListener, FocusListener {

    static final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    static final LinkedHashMap<String, GJDataTransformInterface> transformList = kcl.waterloo.graphics.transforms.Services.getAvailable();
    private static final NumberFormat nf = NumberFormat.getInstance();

    private static class FontControl {

        public JComboBox font = null;
        public JComboBox size = null;
        public JToggleButton bold = null;
        public JToggleButton italic = null;
    }
    private final FontControl fontControl = new FontControl();
    ArrayList<JComponent> componentList;
    GJEditorInterface editor = null;
    GJAbstractGraphContainer graphContainer = null;

    public BindingManager(GJEditorInterface gui) {
        editor = gui;
        graphContainer = editor.getGraphContainer();
        componentList = getComponentsAsList((JComponent) gui);
        componentList = getRequiredFromList(componentList);
        init();
        addListeners();
    }

    final void init() {
        int idx = editor.getSelectedTab();
        for (JComponent o : componentList) {
            initContainer(o, fonts);
        }
        for (int k = 0; k < editor.getTabbedPane().getTabCount(); k++) {
            editor.setSelectedTab(k);
            Component c = editor.getTabbedPane().getSelectedComponent();
            for (JComponent o : componentList) {
                if (SwingUtilities.isDescendingFrom(o, c)) {
                    initTabContents(o);
                }
            }
        }
        editor.setSelectedTab(idx);
    }

    private void addListeners() {
        addAsActionListener(componentList);
        for (JComponent thisComponent : componentList) {
            thisComponent.addFocusListener(this);
        }
    }

    final void addAsActionListener(ArrayList<JComponent> list) {
        for (JComponent thisComponent : list) {
            Method meth = null;
            try {
                meth = thisComponent.getClass().getMethod("addActionListener", ActionListener.class);
            } catch (SecurityException e) {
            } catch (NoSuchMethodException e) {
                try {
                    meth = thisComponent.getClass().getMethod("addChangeListener", ChangeListener.class);
                } catch (SecurityException ex0) {
                } catch (NoSuchMethodException ex1) {
                }
            }
            if (meth != null) {
                try {
                    meth.invoke(thisComponent, this);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }
    }

    final ArrayList<JComponent> getComponentsAsList(JComponent obj) {
        ArrayList<JComponent> list = new ArrayList<JComponent>();
        JComponent thisComponent;
        list.add(obj);
        for (int k = 0; k < obj.getComponentCount(); k++) {
            try {
                thisComponent = (JComponent) obj.getComponent(k);
                if (thisComponent.getComponentCount() > 0) {
                    list.addAll(getComponentsAsList(thisComponent));
                } else {
                    list.add(thisComponent);
                }
            } catch (ClassCastException ex) {
            }
        }
        return list;
    }

    final ArrayList<JComponent> getRequiredFromList(ArrayList<JComponent> list) {
        ArrayList<JComponent> newlist = new ArrayList<JComponent>();
        for (JComponent thisComponent : list) {
            if (thisComponent.getName() != null
                    && !thisComponent.getName().isEmpty()
                    && thisComponent.getName().charAt(0) == '$') {
                newlist.add(thisComponent);
            }
        }
        return newlist;
    }

    ArrayList<JComponent> getComponentList() {
        return componentList;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            eventHandler((JComponent) arg0.getSource());
            //TODO Get rid of these by including code in graph/container
            graphContainer.revalidate();
            graphContainer.repaint();
        } catch (ParseException ex) {

        }
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        try {
            eventHandler((JComponent) ce.getSource());
        } catch (ParseException ex) {

        }
        graphContainer.revalidate();
        graphContainer.repaint();
    }

    @Override
    public void focusGained(FocusEvent arg0) {
    }

    @Override
    public void focusLost(FocusEvent arg0) {
        try {
            eventHandler((JComponent) arg0.getSource());
            //TODO Get rid of these by including code in graph/container
            graphContainer.revalidate();
            graphContainer.repaint();
        } catch (ParseException ex) {

        }
    }

    @SuppressWarnings("unchecked")
    final void initContainer(JComponent o, String[] fonts) {
        if (graphContainer != null) {
            if (o.getName().equals("$Title")) {
                JTextField thisComponent = (JTextField) o;
                thisComponent.setText(graphContainer.getTitleText());
            } else if (o.getName().equals("$SubTitle")) {
                JTextField thisComponent = (JTextField) o;
                thisComponent.setText(graphContainer.getSubTitleText());
            } else if (o.getName().equals("$Rotation")) {
                JComboBox thisComponent = (JComboBox) o;
                thisComponent.setSelectedItem(graphContainer.getRotation()
                        * 180d / Math.PI);
            } else if (o.getName().equals("$rotateDial")) {
                GJDial thisComponent = (GJDial) o;
                thisComponent.setValue((int) (graphContainer.getRotation() * 180d / Math.PI));
            } else if (o.getName().equals("$Zoom")) {
                JComboBox thisComponent = (JComboBox) o;
                thisComponent.setSelectedItem(graphContainer.getZoom() * 100d);
            } else if (o.getName().equals("$zoomDial")) {
                GJDial thisComponent = (GJDial) o;
                thisComponent.setValue((int) (graphContainer.getZoom() * 100d));
            } else if (o.getName().equals("$Font")) {
                JComboBox thisComponent = (JComboBox) o;
                if (thisComponent.getItemCount() == 0) {
                    for (String font : fonts) {
                        thisComponent.addItem(font);
                    }
                    fontControl.font = thisComponent;
                }
                thisComponent.setSelectedItem(graphContainer.getFont().getFamily());
            } else if (o.getName().equals("$FontSize")) {
                JComboBox thisComponent = (JComboBox) o;
                if (thisComponent.getItemCount() == 0) {
                    for (int k = 2; k < 32; k += 2) {
                        thisComponent.addItem(new Integer(k).toString());
                    }
                    fontControl.size = thisComponent;
                }
                int sz = graphContainer.getFont().getSize();
                thisComponent.setSelectedItem(new Integer(sz).toString());
            } else if (o.getName().equals("$Bold")) {
                JToggleButton thisComponent = (JToggleButton) o;
                if (fontControl.bold == null) {
                    fontControl.bold = thisComponent;
                }
                thisComponent.setSelected(graphContainer.getFont().isBold());
            } else if (o.getName().equals("$Italic")) {
                JToggleButton thisComponent = (JToggleButton) o;
                if (fontControl.italic == null) {
                    fontControl.italic = thisComponent;
                }
                thisComponent.setSelected(graphContainer.getFont().isItalic());
            } else if (o.getName().equals("$AspectRatio")) {
                JComboBox thisComponent = (JComboBox) o;
                double aspectRatio = graphContainer.getAspectRatio();
                if (aspectRatio <= 0) {
                    thisComponent.setSelectedIndex(0);
                } else {
                    thisComponent.setSelectedItem(Double.valueOf(aspectRatio).toString());
                }
            } else if (o.getName().equals("$ContainerForeground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) o;
                thisComponent.setSelectedColor(graphContainer.getForeground());
            } else if (o.getName().equals("$ContainerBackgroundPainted")) {
                JCheckBox thisComponent = (JCheckBox) o;
                thisComponent.setSelected(graphContainer.isBackgroundPainted());
            } else if (o.getName().equals("$ContainerBackground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) o;
                thisComponent.setSelectedColor(graphContainer.getBackground());
            } else if (o.getName().equals("$Shadow")) {
                JCheckBox thisComponent = (JCheckBox) o;
                thisComponent.setSelected(graphContainer.getEffect() instanceof ShadowPathEffect);
            } else if (o.getName().equals("$Frame")) {
                JCheckBox thisComponent = (JCheckBox) o;
                thisComponent.setSelected(graphContainer.getEffect() instanceof NeonBorderEffect);
            }
        }
    }

    @SuppressWarnings("unchecked")
    final void initTabContents(JComponent o) {
        if (o.getName().equals("$AxisRangeLeft")) {
            JTextField thisComponent = (JTextField) o;
            Double val = editor.getLayerForTab().getXLeft();
            if (!val.isNaN() && !val.isInfinite()) {
                thisComponent.setText(nf.format(val));
            }
        } else if (o.getName().equals("$AxisRangeRight")) {
            JTextField thisComponent = (JTextField) o;
            Double val = editor.getLayerForTab().getXRight();
            if (!val.isNaN() && !val.isInfinite()) {
                thisComponent.setText(nf.format(val));
            }
        } else if (o.getName().equals("$AxisRangeBottom")) {
            JTextField thisComponent = (JTextField) o;
            Double val = editor.getLayerForTab().getYBottom();
            if (!val.isNaN() && !val.isInfinite()) {
                thisComponent.setText(nf.format(val));
            }
        } else if (o.getName().equals("$AxisRangeTop")) {
            JTextField thisComponent = (JTextField) o;
            Double val = editor.getLayerForTab().getYTop();
            if (!val.isNaN() && !val.isInfinite()) {
                thisComponent.setText(nf.format(val));
            }
        } else if (o.getName().equals("$Left")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isLeftAxisPainted());
        } else if (o.getName().equals("$Right")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isRightAxisPainted());
        } else if (o.getName().equals("$Top")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isTopAxisPainted());
        } else if (o.getName().equals("$Bottom")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isBottomAxisPainted());
        } else if (o.getName().equals("$XAxisLabel")) {
            JTextField thisComponent = (JTextField) o;
            thisComponent.setText(editor.getLayerForTab().getXLabel());
        } else if (o.getName().equals("$YAxisLabel")) {
            JTextField thisComponent = (JTextField) o;
            thisComponent.setText(editor.getLayerForTab().getYLabel());
        } else if (o.getName().equals("$XReverse")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(editor.getLayerForTab().isXReversed());
        } else if (o.getName().equals("$YReverse")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(editor.getLayerForTab().isYReversed());
        } else if (o.getName().equals("$XTransform")) {
            JComboBox thisComponent = (JComboBox) o;
            if (thisComponent.getItemCount() == 0) {
                for (String item : transformList.keySet()) {
                    thisComponent.addItem(item);
                }
            }
            thisComponent.setSelectedItem(editor.getLayerForTab().getXTransform().getName());
        } else if (o.getName().equals("$YTransform")) {
            JComboBox thisComponent = (JComboBox) o;
            if (thisComponent.getItemCount() == 0) {
                for (String item : transformList.keySet()) {
                    thisComponent.addItem(item);
                }
            }
            thisComponent.setSelectedItem(editor.getLayerForTab().getYTransform().getName());
        } else if (o.getName().equals("$Left")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isLeftAxisPainted());
        } else if (o.getName().equals("$Right")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isRightAxisPainted());
        } else if (o.getName().equals("$Top")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isTopAxisPainted());
        } else if (o.getName().equals("$Bottom")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isBottomAxisPainted());
        } else if (o.getName().equals("$LeftLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isLeftAxisLabelled());
        } else if (o.getName().equals("$RightLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isRightAxisLabelled());
        } else if (o.getName().equals("$TopLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isTopAxisLabelled());
        } else if (o.getName().equals("$BottomLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isBottomAxisLabelled());
        } else if (o.getName().equals("$MajorGrid")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isMajorGridPainted());
        } else if (o.getName().equals("$MinorGrid")) {
            JToggleButton thisComponent = (JToggleButton) o;
            thisComponent.setSelected(editor.getLayerForTab().isMinorGridPainted());
        } else if (o.getName().equals("$InnerAxis")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(editor.getLayerForTab().isInnerAxisPainted());
        } else if (o.getName().equals("$InnerAxisLabels")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(editor.getLayerForTab().isInnerAxisLabelled());
        } else if (o.getName().equals("$XDiv")) {
            JSpinner thisComponent = (JSpinner) o;
            thisComponent.setValue(editor.getLayerForTab().getMinorCountXHint());
        } else if (o.getName().equals("$YDiv")) {
            JSpinner thisComponent = (JSpinner) o;
            thisComponent.setValue(editor.getLayerForTab().getMinorCountYHint());
        } else if (o.getName().equals("$Alpha")) {
            JComboBox thisComponent = (JComboBox) o;
            thisComponent.setSelectedItem(Float.valueOf(((GJAbstractGraph) editor.getLayerForTab()).getAlpha()).toString());
        } else if (o.getName().equals("$GridColor")) {
            GJColorComboBox thisComponent = (GJColorComboBox) o;
            thisComponent.setSelectedColor(editor.getLayerForTab().getMajorGridColor());
        } else if (o.getName().equals("$AxisColor")) {
            GJColorComboBox thisComponent = (GJColorComboBox) o;
            thisComponent.setSelectedColor(editor.getLayerForTab().getAxisColor());
        } else if (o.getName().equals("$Polar")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(editor.getLayerForTab().isPolar());
        } else if (o.getName().equals("$BackgroundPainted")) {
            JCheckBox thisComponent = (JCheckBox) o;
            thisComponent.setSelected(((GJAbstractGraph) editor.getLayerForTab()).isBackgroundPainted());
        }

    }

    private void eventHandler(final JComponent o) throws ParseException {

        if (!EventQueue.isDispatchThread()) {
            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        eventHandler(o);
                    } catch (ParseException ex) {
                    }
                }

            });
            return;
        }

        Double val;
        if (o.getName().equals("$AxisRangeLeft")) {
            JTextField thisComponent = (JTextField) o;
            val = nf.parse(thisComponent.getText()).doubleValue();
            if (!val.isNaN() && !val.isInfinite()) {
                editor.getLayerForTab().setXLeft(val);
            }
        } else if (o.getName().equals("$AxisRangeRight")) {
            JTextField thisComponent = (JTextField) o;
            val = nf.parse(thisComponent.getText()).doubleValue();
            if (!val.isNaN() && !val.isInfinite()) {
                editor.getLayerForTab().setXRight(val);
            }
        } else if (o.getName().equals("$AxisRangeBottom")) {
            JTextField thisComponent = (JTextField) o;
            val = nf.parse(thisComponent.getText()).doubleValue();
            if (!val.isNaN() && !val.isInfinite()) {
                editor.getLayerForTab().setYBottom(val);
            }
        } else if (o.getName().equals("$AxisRangeTop")) {
            JTextField thisComponent = (JTextField) o;
            val = nf.parse(thisComponent.getText()).doubleValue();
            if (!val.isNaN() && !val.isInfinite()) {
                editor.getLayerForTab().setYTop(val);
            }
        } else if (o.getName().equals("$Left")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setLeftAxisPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$Right")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setRightAxisPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$Top")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setTopAxisPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$Bottom")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setBottomAxisPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$LeftLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setLeftAxisLabelled(thisComponent.isSelected());
        } else if (o.getName().equals("$RightLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setRightAxisLabelled(thisComponent.isSelected());
        } else if (o.getName().equals("$TopLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setTopAxisLabelled(thisComponent.isSelected());
        } else if (o.getName().equals("$BottomLabels")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setBottomAxisLabelled(thisComponent.isSelected());
        } else if (o.getName().equals("$XAxisLabel")) {
            JTextField thisComponent = (JTextField) o;
            editor.getLayerForTab().setXLabel(thisComponent.getText());
        } else if (o.getName().equals("$YAxisLabel")) {
            JTextField thisComponent = (JTextField) o;
            editor.getLayerForTab().setYLabel(thisComponent.getText());
        } else if (o.getName().equals("$XReverse")) {
            JCheckBox thisComponent = (JCheckBox) o;
            editor.getLayerForTab().setReverseX(thisComponent.isSelected());
        } else if (o.getName().equals("$YReverse")) {
            JCheckBox thisComponent = (JCheckBox) o;
            editor.getLayerForTab().setReverseY(thisComponent.isSelected());
        } else if (o.getName().equals("$XTransform")) {
            JComboBox thisComponent = (JComboBox) o;
            String name = (String) thisComponent.getSelectedItem();
            GJDataTransformInterface tr = kcl.waterloo.graphics.transforms.Services.getInstanceForName(name);
            if (editor.getLayerForTab().getXTransform().getName().equals(name)) {
                return;
            }
            double l = editor.getLayerForTab().getXTransform().getInverse(editor.getLayerForTab().getXLeft());
            double r = editor.getLayerForTab().getXTransform().getInverse(editor.getLayerForTab().getXRight());
            l = tr.getData(l);
            r = tr.getData(r);
            if (Double.isNaN(l) || Double.isInfinite(l)) {
                l = 0;
            }
            if (Double.isNaN(r) || Double.isInfinite(r)) {
                r = 0;
            }
            Rectangle2D bounds = editor.getLayerForTab().getAxesBounds();
            editor.getLayerForTab().setAxesBounds(
                    new Rectangle2D.Double(r >= l ? l : r,
                            bounds.getY(),
                            r >= l ? r - l : l - r,
                            bounds.getHeight()));
            editor.getLayerForTab().setXTransform(tr);
        } else if (o.getName().equals("$YTransform")) {
            JComboBox thisComponent = (JComboBox) o;
            String name = (String) thisComponent.getSelectedItem();
            GJDataTransformInterface tr = kcl.waterloo.graphics.transforms.Services.getInstanceForName(name);
            if (editor.getLayerForTab().getYTransform().getName().equals(name)) {
                return;
            }
            double t = editor.getLayerForTab().getYTransform().getInverse(editor.getLayerForTab().getYTop());
            double b = editor.getLayerForTab().getYTransform().getInverse(editor.getLayerForTab().getYBottom());
            t = tr.getData(t);
            b = tr.getData(b);
            if (Double.isNaN(t) || Double.isInfinite(t)) {
                t = 0;
            }
            if (Double.isNaN(b) || Double.isInfinite(b)) {
                b = 0;
            }
            Rectangle2D bounds = editor.getLayerForTab().getAxesBounds();
            editor.getLayerForTab().setAxesBounds(
                    new Rectangle2D.Double(bounds.getX(),
                            t >= b ? b : t,
                            bounds.getWidth(),
                            t >= b ? t - b : b - t));
            editor.getLayerForTab().setYTransform(tr);
        } else if (o.getName().equals("$MajorGrid")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setMajorGridPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$MinorGrid")) {
            JToggleButton thisComponent = (JToggleButton) o;
            editor.getLayerForTab().setMinorGridPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$InnerAxis")) {
            JCheckBox thisComponent = (JCheckBox) o;
            editor.getLayerForTab().setInnerAxisPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$InnerAxisLabels")) {
            JCheckBox thisComponent = (JCheckBox) o;
            editor.getLayerForTab().setInnerAxisLabelled(thisComponent.isSelected());
        } else if (o.getName().equals("$XDiv")) {
            JSpinner thisComponent = (JSpinner) o;
            editor.getLayerForTab().setMinorCountXHint(((Number) thisComponent.getValue()).intValue());
        } else if (o.getName().equals("$YDiv")) {
            JSpinner thisComponent = (JSpinner) o;
            editor.getLayerForTab().setMinorCountYHint(((Number) thisComponent.getValue()).intValue());
        } else if (o.getName().equals("$Alpha")) {
            JComboBox thisComponent = (JComboBox) o;
            ((GJAbstractGraph) editor.getLayerForTab()).setAlpha(Float.valueOf((String) thisComponent.getSelectedItem()));
        } else if (o.getName().equals("$BackgroundPainted")) {
            JCheckBox thisComponent = (JCheckBox) o;
            editor.getLayerForTab().setBackgroundPainted(thisComponent.isSelected());
        } else if (o.getName().equals("$Background")) {
            GJColorComboBox thisComponent = (GJColorComboBox) o;
            ((GJAbstractGraph) editor.getLayerForTab()).setBackground(thisComponent.getSelectedColor());
        } else if (o.getName().equals("$GridColor")) {
            GJColorComboBox thisComponent = (GJColorComboBox) o;
            //NB Preserve order
            editor.getLayerForTab().setMajorGridColor(thisComponent.getSelectedColor());
            editor.getLayerForTab().setMinorGridColor(thisComponent.getSelectedColor());
        } else if (o.getName().equals("$AxisColor")) {
            GJColorComboBox thisComponent = (GJColorComboBox) o;
            editor.getLayerForTab().setAxisColor(thisComponent.getSelectedColor());
        } else if (o.getName().equals("$Polar")) {
            JCheckBox thisComponent = (JCheckBox) o;
            GJAbstractGraph gr = ((GJAbstractGraph) editor.getLayerForTab());
            gr.setPolar(thisComponent.isSelected());

            if (thisComponent.isSelected()) {
                if (gr.getGraphContainer() != null) {
                    gr.getGraphContainer().setAspectRatio(1d);
                }
                gr.setInnerAxisLabelled(true);
                gr.setTopAxisPainted(false);
                gr.setBottomAxisPainted(false);
                gr.setLeftAxisPainted(false);
                gr.setRightAxisPainted(false);
            } else {
                gr.setInnerAxisLabelled(false);
                gr.setBottomAxisPainted(true);
                gr.setLeftAxisPainted(true);
            }
        }

        if (graphContainer != null) {
            if (o.getName().equals("$Title")) {
                JTextField thisComponent = (JTextField) o;
                graphContainer.setTitleText(thisComponent.getText());
            } else if (o.getName().equals("$SubTitle")) {
                JTextField thisComponent = (JTextField) o;
                graphContainer.setSubTitleText(thisComponent.getText());
            } else if (o.getName().equals("$Rotation")) {
                JComboBox thisComponent = (JComboBox) o;
                graphContainer.setRotation(new Double(thisComponent.getSelectedItem().toString()).doubleValue()
                        * Math.PI
                        / 180d);
            } else if (o.getName().equals("$rotateDial")) {
                GJDial thisComponent = (GJDial) o;
                graphContainer.setRotation(thisComponent.getValue() * Math.PI / 180d);
            } else if (o.getName().equals("$Zoom")) {
                JComboBox thisComponent = (JComboBox) o;
                graphContainer.setZoom(new Double(thisComponent.getSelectedItem().toString()).doubleValue()
                        / 100d);
            } else if (o.getName().equals("$zoomDial")) {
                GJDial thisComponent = (GJDial) o;
                graphContainer.setZoom(thisComponent.getValue() / 100d);
            } else if (o.getName().equals("$AspectRatio")) {
                JComboBox thisComponent = (JComboBox) o;
                String s = thisComponent.getSelectedItem().toString();
                if (s.matches("Auto")) {
                    graphContainer.setAspectRatio(0);
                } else {
                    graphContainer.setAspectRatio(new Double(thisComponent.getSelectedItem().toString()).doubleValue());
                }
            } else if (o.getName().equals("$Font")
                    || (o.getName().equals("$FontSize"))
                    || (o.getName().equals("$Bold"))
                    || (o.getName().equals("$Italic"))) {
                int Style = Font.PLAIN;
                if (fontControl.bold.isSelected()) {
                    Style = Style + Font.BOLD;
                }
                if (fontControl.italic.isSelected()) {
                    Style = Style + Font.ITALIC;
                }
                Font font = new Font((String) fontControl.font.getSelectedItem(),
                        Style,
                        new Integer(fontControl.size.getSelectedItem().toString()).intValue());
                graphContainer.setFont(font, true);
            } else if (o.getName().equals("$ContainerForeground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) o;
                graphContainer.setForeground(thisComponent.getSelectedColor(), true);
            } else if (o.getName().equals("$ContainerBackgroundPainted")) {
                JCheckBox thisComponent = (JCheckBox) o;
                graphContainer.setBackgroundPainted(thisComponent.isSelected());
            } else if (o.getName().equals("$ContainerBackground")) {
                GJColorComboBox thisComponent = (GJColorComboBox) o;
                graphContainer.setBackground(thisComponent.getSelectedColor());
            } else if (o.getName().equals("$Shadow")) {
                JCheckBox thisComponent = (JCheckBox) o;
                if (thisComponent.isSelected()) {
                    editor.getContainerOptionsPanel().getFrameEffect().setSelected(false);
                    ShadowPathEffect effect = new ShadowPathEffect();
                    graphContainer.setEffect(effect);
                } else {
                    graphContainer.setEffect(null);
                }
            } else if (o.getName().equals("$Frame")) {
                JCheckBox thisComponent = (JCheckBox) o;
                if (thisComponent.isSelected()) {
                    editor.getContainerOptionsPanel().getShadowEffect().setSelected(false);
                    NeonBorderEffect effect = new NeonBorderEffect();
                    effect.setEdgeColor(Color.DARK_GRAY);
                    graphContainer.setEffect(effect);
                } else {
                    graphContainer.setEffect(null);
                }
            }
        }

    }
}
