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
package kcl.waterloo.graphics;

import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import kcl.waterloo.xml.GJEncoder;

/**
 *
 * <@code GJGraphContainer> container for graphs
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class GJGraphContainer extends GJAbstractGraphContainer {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor returns a GJGraphContainer with no view
     */
    public GJGraphContainer() {
        super();
//        //TODO make thread-safe
        addListeners();
    }

//    /**
//     * GJGraphContainer constructor uses the specified GJGraphInterface as its
//     * view
//     *
//     * @param gr the View
//     */
//    private GJGraphContainer(GJGraphInterface gr) {
//        this();
//        setView(gr);
//        //gr.createAxes();
//    }
    public static GJGraphContainer createInstance() {
        return new GJGraphContainer();
    }

    public static GJGraphContainer createInstance(GJAbstractGraph gr) {
        GJGraphContainer grc = new GJGraphContainer();
        // Remove existing axes
        gr.removeAxes();
        // Set view
        grc.setView(gr);
        // Create new axes
        gr.createAxes();
        grc.addListeners();
        gr.addPropertyChangeListener(grc);
        return grc;
    }

    public static GJGraphContainer createInstance(Container c, GJAbstractGraph gr) {
        GJGraphContainer grc = GJGraphContainer.createInstance(gr);
        c.add(grc);
        return grc;
    }


    private void addListeners() {
        addComponentListener(this);
        getAddedComponentMouseHandler().setParentComponent(this);
        getContainerMouseHandler().setParentContainer(this);
        addPropertyChangeListener(this);
    }

    public void removeComponentListener() {
        this.removeComponentListener(this);
    }

    public void saveAsXML(String fileName) throws FileNotFoundException, IOException {
        GJEncoder.save(fileName, this);
    }

    /**
     * Component resize listener routine
     *
     * @param ce the event
     */
    @Override
    public void componentResized(ComponentEvent ce) {
    }

    /**
     * Component moved listener routine
     *
     * @param ce the event
     */
    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    /**
     * Component shown listener routine
     *
     * @param ce the event
     */
    @Override
    public void componentShown(ComponentEvent ce) {
//        revalidate();
//        repaint();
    }

    /**
     * Component hidden listener routine
     *
     * @param ce the event
     */
    @Override
    public void componentHidden(ComponentEvent ce) {
    }
}
