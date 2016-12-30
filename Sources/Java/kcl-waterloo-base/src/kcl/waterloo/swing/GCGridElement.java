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
package kcl.waterloo.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import kcl.waterloo.graphics.GJBasicPanel;

/**
 * A
 * <code>GCGridElement</code> instance houses a <strong>single</strong>
 * component in the CENTER panel of a BorderLayout.
 *
 * Note that calls to the <code>add</code> method replace any existing component
 * in the CENTER panel.
 *
 * All components added to a  <code>GCGrid should be parented by a
 * <code>GCGridElement</code> instance.
 *
 * @author Malcolm Lidierth
 */
public final class GCGridElement extends GJBasicPanel {

    private GCElementProperties properties = new GCElementProperties();

    private Component titleComponent = null;

    public GCGridElement() {
        super(new BorderLayout());
    }

    public GCGridElement(Component c) {
        this();
        add(c);
    }

    public void setRow(double row) {
        properties.setRow(row);
    }

    public void setColumn(double col) {
        properties.setColumn(col);
    }

    public void setColumnWidth(double width) {
        properties.setColumnWidth(width);
    }

    public void setRowHeight(double height) {
        properties.setRowHeight(height);
    }

    public void bringForward() {
        if (getParent() != null) {
            int z = getComponentZOrder(getParent());
            getParent().setComponentZOrder(this, Math.min(getParent().getComponentCount() - 1, z + 1));
            getProperties().setZOrder(getParent().getComponentZOrder(this));
        }
    }

    public void bringToFront() {
        if (getParent() != null) {
            getParent().setComponentZOrder(this, 1);
            getProperties().setZOrder(0);
        }
    }

    public void sendBackward() {
        if (getParent() != null) {
            int z = getComponentZOrder(getParent());
            getParent().setComponentZOrder(this, Math.max(1, z - 1));
            getProperties().setZOrder(getParent().getComponentZOrder(this));
        }
    }

    public void sendToBack() {
        if (getParent() != null) {
            getParent().setComponentZOrder(this, getParent().getComponentCount() - 1);
            getProperties().setZOrder(getParent().getComponentZOrder(this));
        }
    }

    public Component get() {
        return ((BorderLayout) getLayout()).getLayoutComponent(this, BorderLayout.CENTER);
    }

    /**
     * @return the properties
     */
    public GCElementProperties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(GCElementProperties properties) {
        this.properties = properties;
    }

    public void setTitleComponent(Component c) {
        titleComponent = c;
        this.add(c, BorderLayout.NORTH);
    }

    public Component getTitleComponent() {
        return titleComponent;
    }

}
