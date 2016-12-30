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
package kcl.waterloo.actions;

import java.awt.Container;
import java.util.ArrayList;
import kcl.waterloo.graphics.GJGraphInterface;
import kcl.waterloo.graphics.plots2D.GJPlotInterface;
import java.awt.event.MouseListener;
import java.awt.event.ComponentListener;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;

/**
 * <code>GJEventManager</code> provides a singleton instance class to manage
 * events within a session.
 *
 * The GJEventManager instance is added as a property change listener to all
 * graphs during their construction.
 *
 *
 * @author ML
 */
public class GJEventManager implements PropertyChangeListener, ActionListener, ContainerListener, ComponentListener, MouseListener {

    /**
     * Singleton instance
     */
    private static final GJEventManager instance = new GJEventManager();

    /**
     * Reference to the graph containing the plot(s) in gxgco. Null if the last
     * created plot is not in a graph.
     */
    private GJGraphInterface gxgca = null;
    /**
     * Reference to the frame containing the plot(s) in gxgco. Null if gxgca is
     * not in a top level ancestor.
     */
    private Container gxgcf = null;

    /**
     * Private constructor for singleton instance.
     */
    private GJEventManager() {
    }

    /**
     * Public getter for the singleton instance.
     *
     * @return the singleton instance
     */
    public static GJEventManager getInstance() {
        return instance;
    }

    /**
     * @return the gxgcf
     */
    public Container getGxgcf() {
        return gxgcf;
    }

    /**
     * @param gxgcf the gxgcf to set
     */
    private void setGxgcf(Container gxgcf) {
        this.gxgcf = gxgcf;
    }

    /**
     * @return the gxgca
     */
    public GJGraphInterface getGxgca() {
        return gxgca;
    }

    /**
     * @param gxgca the gxgca to set
     */
    private void setGxgca(GJGraphInterface gxgca) {
        this.gxgca = gxgca;
    }

    /**
     * @return the gxgco
     */
    public ArrayList<GJPlotInterface> getGxgco() {
        return gxgca.getSelectedPlots();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
    }

    @Override
    public void componentAdded(ContainerEvent e) {
    }

    @Override
    public void componentRemoved(ContainerEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof GJGraphInterface) {
            GJGraphInterface g = (GJGraphInterface) e.getSource();
            setGxgca(g);
            setGxgcf(((JComponent) g).getTopLevelAncestor());

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
