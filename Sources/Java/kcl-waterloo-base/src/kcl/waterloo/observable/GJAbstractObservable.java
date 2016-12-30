 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.observable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * /**
 * Abstract implementation of the GJObservableInterface.
 *
 * Listeners can be added using the addPropertyChangeListener methods and are
 * registered in a PropertyChangeSupport instance stored as a property of the
 * GJAbstractObservable superclass instance.
 *
 * A list of linked objects is maintained in an internal ArrayList. Objects in
 * the list can be modified in propertyChange methods of super-classes that
 * extend the GJAbstractObservable class.
 *
 * @author Malcolm Lidierth <sigtool at kcl.ac.uk>
 */
abstract public class GJAbstractObservable implements GJObservableInterface, GJLinkableInterface {

    private final PropertyChangeSupport PCS = new PropertyChangeSupport(this);
    private ArrayList<Object> links = new ArrayList<Object>();

    /**
     *
     * @param o
     */
    @Override
    public void addLink(Object o) {
        links.add(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLink(Object o) {
        links.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Object> getLinks() {
        return links;
    }

    // observable interface
    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PCS.addPropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        PCS.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return PCS.getPropertyChangeListeners();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return PCS.getPropertyChangeListeners(propertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        PCS.removePropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        PCS.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasListeners(String propertyName) {
        return PCS.hasListeners(propertyName);
    }


    /**
     * @return the pcs
     */
    public final PropertyChangeSupport getPCS() {
        return PCS;
    }


    /**
     * @param Links
     */
    @Override
    public void setLinks(ArrayList<Object> Links) {
        this.links = Links;
    }

    @Override
    public boolean hasSelfListener() {
        return Arrays.asList(PCS.getPropertyChangeListeners()).contains(this);
    }

    @Override
    public void firePropertyChange(PropertyChangeEvent pce) {
        PCS.firePropertyChange(pce);
    }
}
