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
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import kcl.waterloo.graphics.data.Category;

/**
 *
 * SpringLayout subclass for GJAxisPanels.
 *
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class AxisLayout extends SpringLayout {

    /**
     * Null constructor
     */
    public AxisLayout() {
    }

    /**
     * Called when revalidating a GJAxisPanel before calling the SpringLayout
     * super class layout method.
     *
     * @param panel the parent GJAxisPanel
     */
    final void localRevalidate(GJAxisPanel panel) {
        int textOffset = (int) panel.getDynamicExtent() + 2;
        GJAbstractGraphContainer parent = (GJAbstractGraphContainer) panel.getParent();
        if (parent != null) {
            SpringLayout layout1 = (SpringLayout) parent.getLayout();
            SpringLayout layout2 = (SpringLayout) panel.getLayout();
            GJAbstractGraph thisView = (GJAbstractGraph) parent.getView();

            JLabel label = panel.getLabel();

            switch (panel.getPosition()) {
                case LEFT:
                    if (label != null) {
                        panel.setExtent(textOffset + 2 + label.getWidth());
                        panel.setPreferredSize(new Dimension(panel.getExtent(), 32767));
                        layout1.putConstraint(SpringLayout.EAST, panel, -panel.getOffset(),
                                SpringLayout.WEST, thisView);
                        panel.setPreferredSize(new Dimension(panel.getExtent(), 32767));
                        layout1.putConstraint(SpringLayout.NORTH, panel, 0,
                                SpringLayout.NORTH, thisView);
                        layout1.putConstraint(SpringLayout.SOUTH, panel, 0,
                                SpringLayout.SOUTH, thisView);

                        layout2.putConstraint(SpringLayout.NORTH, label,
                                panel.getHeight() / 2 - label.getHeight() / 2,
                                SpringLayout.NORTH,
                                panel);
                        layout2.putConstraint(SpringLayout.EAST, label,
                                -textOffset,
                                SpringLayout.EAST,
                                panel);
                    }
                    revalidateAxisLeft(panel);
                    break;
                case RIGHT:
                    if (label != null) {
                        panel.setExtent(textOffset + 2 + label.getWidth());
                        layout1.putConstraint(SpringLayout.WEST, panel, panel.getOffset(),
                                SpringLayout.EAST, thisView);
                        panel.setPreferredSize(new Dimension(panel.getExtent(), 32767));
                        layout1.putConstraint(SpringLayout.NORTH, panel, 0,
                                SpringLayout.NORTH, thisView);
                        layout1.putConstraint(SpringLayout.SOUTH, panel, 0,
                                SpringLayout.SOUTH, thisView);

                        layout2.putConstraint(SpringLayout.NORTH, label,
                                panel.getHeight() / 2 - label.getHeight() / 2,
                                SpringLayout.NORTH,
                                panel);
                        layout2.putConstraint(SpringLayout.EAST, label,
                                0,
                                SpringLayout.EAST,
                                panel);
                    }
                    revalidateAxisRight(panel);
                    break;
                case BOTTOM:
                    if (label != null) {
                        panel.setExtent((int) panel.getDynamicExtent() + label.getHeight() + 2);
                        layout1.putConstraint(SpringLayout.NORTH, panel, panel.getOffset(),
                                SpringLayout.SOUTH, thisView);
                        panel.setPreferredSize(new Dimension(32767, panel.getExtent()));
                        layout1.putConstraint(SpringLayout.EAST, panel, 0,
                                SpringLayout.EAST, thisView);
                        layout1.putConstraint(SpringLayout.WEST, panel, 0,
                                SpringLayout.WEST, thisView);

                        layout2.putConstraint(SpringLayout.WEST, label,
                                panel.getWidth() / 2 - label.getWidth() / 2,
                                SpringLayout.WEST,
                                panel);
                        layout2.putConstraint(SpringLayout.SOUTH, label,
                                0,
                                SpringLayout.SOUTH,
                                panel);
                    }
                    revalidateAxisBottom(panel);
                    break;
                case TOP:
                    if (label != null) {
                        panel.setExtent((int) panel.getDynamicExtent() + label.getHeight() + 2);
                        layout1.putConstraint(SpringLayout.SOUTH, panel, -panel.getOffset(),
                                SpringLayout.NORTH, thisView);
                        panel.setPreferredSize(new Dimension(32767, panel.getExtent()));
                        layout1.putConstraint(SpringLayout.EAST, panel, 0,
                                SpringLayout.EAST, thisView);
                        layout1.putConstraint(SpringLayout.WEST, panel, 0,
                                SpringLayout.WEST, thisView);

                        layout2.putConstraint(SpringLayout.WEST, label,
                                panel.getWidth() / 2 - label.getWidth() / 2,
                                SpringLayout.WEST,
                                panel);
                        layout2.putConstraint(SpringLayout.NORTH, label,
                                0,
                                SpringLayout.NORTH,
                                panel);
                    }
                    revalidateAxisTop(panel);
                    break;
                default:
            }
        }

    }

    private void revalidateAxisTop(GJAxisPanel panel) {
        Graphics2D g2 = (Graphics2D) ((Component) panel.getParentGraph()).getGraphics();
        if (!panel.isTickLabelsPainted()) {
            panel.setDynamicExtent(10d);
        } else {
            if (g2 != null) {
                FontMetrics metrics = g2.getFontMetrics();
                if (panel.getParentGraph().isCategorical(GJAxisPanel.Orientation.X)) {
                    ArrayList<Category> labels
                            = panel.getParentGraph().getCategoricalLabels(GJAxisPanel.Orientation.X);
                    double len = 0;
                    for (Category s : labels) {
                        if (metrics.stringWidth(s.getText()) > len) {
                            len = metrics.stringWidth(s.getText());
                        }
                    }
                    panel.setDynamicExtent(len + panel.getMajorTickLength());
                } else {
                    panel.setDynamicExtent(metrics.getHeight() + panel.getMajorTickLength());
                }
            }
        }
    }

    private void revalidateAxisLeft(GJAxisPanel panel) {
        if (!panel.isTickLabelsPainted()) {
            panel.setDynamicExtent(10d);
        } else {
            Graphics2D g2 = (Graphics2D) ((Component) panel.getParentGraph()).getGraphics();
            if (g2 != null) {
                FontMetrics metrics = g2.getFontMetrics();
                panel.setDynamicExtent(Double.NEGATIVE_INFINITY);
                double startY = Math.floor(panel.getParentGraph().getYMin() / panel.getParentGraph().getMajorYHint()) * panel.getParentGraph().getMajorYHint();
                ArrayList<Double> it = panel.getParentGraph().getYTransform().getAxisTickPositions(
                        startY,
                        panel.getParentGraph().getYMax() + panel.getParentGraph().getMajorYHint(),
                        panel.getParentGraph().getMajorYHint());
                double y2;
                for (Double s : it) {
                    y2 = s.doubleValue();
                    if (y2 >= panel.getParentGraph().getYMin() && y2 <= panel.getParentGraph().getYMax()) {
                        String str = panel.getParentGraph().formatYAxisLabel(y2);
                        panel.setDynamicExtent(Math.max(metrics.stringWidth(str) + panel.getMajorTickLength(), panel.getDynamicExtent()));
                    }
                }
            }
        }
    }

    private void revalidateAxisRight(GJAxisPanel panel) {
        if (!panel.isTickLabelsPainted()) {
            panel.setDynamicExtent(10d);
        } else {
            Graphics2D g2 = (Graphics2D) ((Component) panel.getParentGraph()).getGraphics();
            if (g2 != null) {
                FontMetrics metrics = g2.getFontMetrics();
                panel.setDynamicExtent(Double.NEGATIVE_INFINITY);
                double startY = Math.floor(panel.getParentGraph().getYMin() / panel.getParentGraph().getMajorYHint())
                        * panel.getParentGraph().getMajorYHint();
                ArrayList<Double> it = panel.getParentGraph().getYTransform().getAxisTickPositions(
                        startY,
                        panel.getParentGraph().getYMax() + panel.getParentGraph().getMajorYHint(),
                        panel.getParentGraph().getMajorYHint());

                double y2;
                for (Double s : it) {
                    y2 = s.doubleValue();
                    if (y2 >= panel.getParentGraph().getYMin() && y2 <= panel.getParentGraph().getYMax()) {
                        String str = panel.getParentGraph().formatYAxisLabel(y2);
                        panel.setDynamicExtent(Math.max(metrics.stringWidth(str) + panel.getMajorTickLength(), panel.getDynamicExtent()));
                    }
                }
            }
        }
    }

    private void revalidateAxisBottom(GJAxisPanel panel) {
        Graphics2D g2 = (Graphics2D) ((Component) panel.getParentGraph()).getGraphics();
        if (!panel.isTickLabelsPainted()) {
            panel.setDynamicExtent(10d);
        } else {
            if (g2 != null) {
                FontMetrics metrics = g2.getFontMetrics();
                if (panel.getParentGraph().isCategorical(GJAxisPanel.Orientation.X)) {
                    ArrayList<Category> labels
                            = panel.getParentGraph().getCategoricalLabels(GJAxisPanel.Orientation.X);
                    double len = 0;
                    for (Category s : labels) {
                        if (metrics.stringWidth(s.getText()) > len) {
                            len = metrics.stringWidth(s.getText());
                        }
                    }
                    panel.setDynamicExtent(len + panel.getMajorTickLength());
                } else {
                    panel.setDynamicExtent(metrics.getHeight() + panel.getMajorTickLength());
                }
            }
        }
    }
}
