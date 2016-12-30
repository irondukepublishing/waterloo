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
package kcl.waterloo.serviceproviders;

import java.beans.PropertyChangeEvent;
import javax.swing.JTabbedPane;
import kcl.waterloo.graphics.GJAbstractGraphContainer;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.gui.gjgraph.GeneralOptions;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJEditorInterface {

    public void setState(int i);

    public int getState();

    public boolean isShowing();

    public void setVisible(boolean flag);

    public void refresh();

    public GJAbstractGraphContainer getGraphContainer();

    public int getSelectedTab();

    public void setSelectedTab(int i);

    public GJGraphInterface getLayerForTab();

    public JTabbedPane getTabbedPane();

    public GeneralOptions getContainerOptionsPanel();

    public void propertyChange(PropertyChangeEvent evt);
}
