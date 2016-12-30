 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SpringLayout;
import kcl.waterloo.swing.layout.GraphUnitLayout;

/**
 * Implements the {@code GraphUnitLayout} to support graph containers.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GraphContainerLayout extends GraphUnitLayout {

    public GraphContainerLayout() {
    }

    /**
     * Overridden {@code javax.swing.SpringLayout#layoutContainer} method.
     *
     * @param parent
     */
    @Override
    public final void layoutContainer(Container parent) {
        GJAbstractGraphContainer container = (GJAbstractGraphContainer) parent;
        if (container.getView() != null) {
            revalidateAdded(container);
            revalidateExtra(container);

        }

//        for (Component c : parent.getComponents()) {
//            if (c instanceof GJCustomGraphLayoutInterface) {
//                ((GJCustomGraphLayoutInterface) c).setConstraints();
//            }
//        }
        try {
            super.layoutContainer(parent);
        } catch (Exception ex) {

        }

        if (container.getAspectRatio() != 0 && ((Component) container.getView()).getWidth() > 0) {
            Rectangle r = ((Component) container.getView()).getBounds();

            double requiredHeight = r.getWidth() / container.getAspectRatio();
            double requiredWidth = requiredHeight * container.getAspectRatio();

            if (requiredHeight > r.getHeight() || requiredWidth > r.getWidth()) {
                requiredWidth = r.getHeight() * container.getAspectRatio();
                requiredHeight = requiredWidth / container.getAspectRatio();
            }
            putConstraint(SpringLayout.SOUTH, (Component) container.getView(), (int) requiredHeight, SpringLayout.NORTH, (Component) container.getView());
            putConstraint(SpringLayout.EAST, (Component) container.getView(), (int) requiredWidth, SpringLayout.WEST, (Component) container.getView());

            putConstraint(SpringLayout.WEST, (Component) container.getView(), -(int) (requiredWidth / 2d), SpringLayout.HORIZONTAL_CENTER, container);
            putConstraint(SpringLayout.NORTH, (Component) container.getView(), -(int) (requiredHeight / 2d), SpringLayout.VERTICAL_CENTER, container);

            super.layoutContainer(parent);
        }

    }

    /**
     * revalidateAdded recalculates the pixel-based SpringLayout constraints
     * given the current axes coordinates for those components added using axes
     * coordinates
     *
     * @param container
     */
    private void revalidateAdded(GJAbstractGraphContainer container) {
        for (Component c : getComponentMap().keySet()) {
            revalidateComponent(c, container);
        }
    }

    private void revalidateComponent(Component c, GJAbstractGraphContainer container) {
        // Workaround for components not being added during deserialization of XML
        // TODO: work out why not and fix at source? Threading issue?
        if (c.getParent() == null) {
            container.add(c, 0);
        }
        // super class does the work
        super.revalidateComponent(c, container, container.getView(), true);
    }

    /**
     * revalidateExtra is called on the EDT from the overridden revalidate
     * method and recalculates the pixel-based SpringLayout constraints for the
     * titles and axes coordinates.
     *
     * @param container
     */
    private void revalidateExtra(GJAbstractGraphContainer container) {
        GJGraphInterface View = container.getView();

        if (container.getComponentCount() == 0) {
            return;
        }

        double topMargin = 0, leftMargin = 0, bottomMargin = 0, rightMargin = 0;

        ArrayList<GJGraphInterface> Layers = View.getLayers();
        int space = GJAxisPanel.getInterAxisSpace();

        for (GJGraphInterface layer : Layers) {

            if (layer.isTopAxisPainted() && layer.getTopAxisPanel() != null) {
                layer.getTopAxisPanel().setOffset((int) topMargin);
                layer.getTopAxisPanel().revalidate();
                topMargin = topMargin + layer.getTopAxisPanel().getExtent() + space;
            }

            if (layer.isLeftAxisPainted() && layer.getLeftAxisPanel() != null) {
                layer.getLeftAxisPanel().setOffset((int) leftMargin);
                layer.getLeftAxisPanel().revalidate();
                leftMargin = leftMargin + layer.getLeftAxisPanel().getExtent() + space;
            }

            if (layer.isBottomAxisPainted() && layer.getBottomAxisPanel() != null) {
                layer.getBottomAxisPanel().setOffset((int) bottomMargin);
                layer.getBottomAxisPanel().revalidate();
                bottomMargin = bottomMargin + layer.getBottomAxisPanel().getExtent() + space;
            }

            if (layer.isRightAxisPainted() && layer.getRightAxisPanel() != null) {
                layer.getRightAxisPanel().setOffset((int) rightMargin);
                layer.getRightAxisPanel().revalidate();
                rightMargin = rightMargin + layer.getRightAxisPanel().getExtent() + space;
            }

        }

        int minMargin = GJAxisPanel.getMinimumMargin();

        Insets summedInsets = new Insets((int) Math.max(minMargin, topMargin),
                (int) Math.max(minMargin, leftMargin),
                (int) Math.max(minMargin, bottomMargin),
                (int) Math.max(minMargin, rightMargin));

        putConstraint(SpringLayout.WEST, (Component) View, summedInsets.left, SpringLayout.WEST, container);
        putConstraint(SpringLayout.NORTH, (Component) View, summedInsets.top, SpringLayout.NORTH, container);
        putConstraint(SpringLayout.EAST, (Component) View, -summedInsets.right, SpringLayout.EAST, container);
        putConstraint(SpringLayout.SOUTH, (Component) View, -summedInsets.bottom, SpringLayout.SOUTH, container);

        JComponent featurePane = container.getFeaturePane();
        if (featurePane != null) {
            putConstraint(SpringLayout.WEST, featurePane, 0, SpringLayout.WEST, (Component) View);
            putConstraint(SpringLayout.NORTH, featurePane, 0, SpringLayout.NORTH, (Component) View);
            putConstraint(SpringLayout.EAST, featurePane, 0, SpringLayout.EAST, (Component) View);
            putConstraint(SpringLayout.SOUTH, featurePane, 0, SpringLayout.SOUTH, (Component) View);
        }

        JFormattedTextField title = container.getTitle();
        //FontMetrics metrics = container.getFontMetrics(title.getFont());
        //int center = (int) ((View.getWidth() / 2.0d) - (metrics.stringWidth(container.getTitle().getText()) / 2.0d));
        //int center = (int) (View.getBounds().getCenterX() - (metrics.stringWidth(container.getTitle().getText()) / 2.0d));
        putConstraint(SpringLayout.NORTH, title, 2, SpringLayout.NORTH, container);
        putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, (Component) container.getView());

        //metrics = container.getFontMetrics(container.getSubTitle().getFont());
        //center = (int) ((View.getWidth() / 2.0d) - (metrics.stringWidth(container.getSubTitle().getText()) / 2.0d));
        //center = center + View.getMargins().left;
        //center = (int) (View.getBounds().getCenterX() - (metrics.stringWidth(container.getSubTitle().getText()) / 2.0d));
        if (title != null) {
            putConstraint(SpringLayout.NORTH, container.getSubTitle(), 2, SpringLayout.SOUTH, title);
            putConstraint(SpringLayout.HORIZONTAL_CENTER, container.getSubTitle(), 0, SpringLayout.HORIZONTAL_CENTER, (Component) container.getView());
            //putConstraint(SpringLayout.WEST, container.getSubTitle(), center, SpringLayout.WEST, container);
        } else {
            putConstraint(SpringLayout.NORTH, container.getSubTitle(), 10, SpringLayout.NORTH, container);
            putConstraint(SpringLayout.HORIZONTAL_CENTER, container.getSubTitle(), 0, SpringLayout.HORIZONTAL_CENTER, (Component) container.getView());
            //putConstraint(SpringLayout.WEST, container.getSubTitle(), center, SpringLayout.WEST, container);
        }

    }
}
