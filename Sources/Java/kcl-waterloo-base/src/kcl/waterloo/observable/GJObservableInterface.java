/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
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

/**
 * <p>
 * GJObservableInterface: Components can implement the interface directly or
 * extend the GJAbstractObservable class.</p>
 *
 * <p>
 * Listeners can be added using the addPropertyChangeListener methods and are
 * registered in a PropertyChangeSupport instance stored as a property of the
 * GJAbstractObservable superclass instance.</p>
 *
 * <p>
 * A list of linked objects is maintained in an internal ArrayList. Objects in
 * the list can be modified in propertyChange methods of classes that implement
 * the interface.</p>
 *
 * @author Malcolm Lidierth
 */
public interface GJObservableInterface extends PropertyChangeListener {

    /**
     * Adds a property change listener.
     *
     * Note that addPropertyChangeListener might be called from a
     * super-constructor with some L&Fs so addPropertyChangeListener should test
     * for null PropertyChangeSupport and initialize it if needed.
     *
     * @param listener - the object that listens.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @param propertyName
     * @param listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @return a PropertyChangeListener[]
     */
    public PropertyChangeListener[] getPropertyChangeListeners();

    /**
     * See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @param propertyName
     * @return a PropertyChangeListener[]
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName);

    /**
     * See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @param propertyName
     * @param listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Returns true if this instance has installed listeners. See <a
     * href="http://docs.oracle.com/javase/7/docs/api/java/beans/PropertyChangeSupport.html">
     * java.beans.PropertyChangeSupport</a>
     *
     * @param propertyName
     * @return the flag
     */
    public boolean hasListeners(String propertyName);

    /**
     * Returns true if the implementing instance is a listener.
     *
     * @return true/false
     */
    public boolean hasSelfListener();

    public void firePropertyChange(PropertyChangeEvent pce);
}
