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

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class ListenerInstaller {

    public static void installListeners(VisualNode n) {

        ((Component) n.value()).addPropertyChangeListener(new NodePropertyChangeListener(n));
        
        Method m;
        try {
            try {
                m = n.value().getClass().getMethod("addChangeListener", new Class[]{ChangeListener.class});
            } catch (NoSuchMethodException ex) {
                return;
            } catch (SecurityException ex) {
                return;
            }
            if (m != null) {
                //System.out.println("Adding TabbedPane Listener");
                ChangeListener listener = new NodeStateChangeListener(n);
                m.invoke(n.value(), listener);
            }
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

    }
}
