/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import java.awt.Component;
import javax.swing.JLabel;

/**
 * GJGridInterface defines methods that need to be implemented by a subclass if
 * it is to work as a grid interface for GJAbstractGraphs.
 *
 * A GJGridInterface allows multiple GJAbstractGraphContainers to be arranged in
 * grids. The interface defines methods used in the GJAbstractGraph class to
 * communicate with the grid class. By default, the GJGridInterface reference in
 * GJAbstractGraph is set to null. GJGridInterface classes need to call
 * setGridInterface on the graph when it is added to the grid.
 *
 * Only implemented in GCGrid at present.
 *
 * <p>
 * <strong>This interface, and related classes in the
 * {@code kcl.waterloo.swing package}, are under development. They are likely to
 * change in the future.</strong></p>
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJGridInterface {

    /**
     * Returns the x,y location of the index
     *
     * @param idx index
     * @return an int[] containing the x,y location
     */
    public double[] getLocation(Component c);

    /**
     * Returns a reference to the JLabel used to output the mouse position
     *
     * @return a JLabel
     */
    public JLabel getMousePositionIndicator();

    /**
     * Returns a reference to the JLabel used to output the grid position
     *
     * @return a JLabel
     */
    public JLabel getGridIndicator();
}
