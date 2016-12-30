/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kcl.waterloo.explorer

import groovy.swing.SwingBuilder
import kcl.waterloo.gui.images.Images
import kcl.waterloo.defaults.GJDefaults

import javax.swing.JToolBar

/**
 *
 * @author Malcolm Lidierth
 */
class GraphToolBar {

    public static JToolBar getToolBar(GraphExplorer host) {

        SwingBuilder bldr = host.getConsole().getSwing()

        return bldr.toolBar() {
            button(actionPerformed: host.openGraph,
                toolTipText: "Open graph",
                icon: Images.getIcon("book_open.png"))
            button(actionPerformed: host.saveGraph,
                toolTipText: "Save graph",
                icon: Images.getIcon("disk.png"))
            button(actionPerformed: host.saveGraphAs,
                toolTipText: "Save as ...",
                icon: Images.getIcon("save_as.png"))
            button(actionPerformed: host.deployGraph,
                toolTipText: "Deploy to web",
                icon: Images.getIcon("world.png"))
            button(actionPerformed: host.closeGraph,
                toolTipText: "Close graph",
                icon: Images.getIcon("cross.png"))
            button(actionPerformed: host.printGraph,
                toolTipText: "Print",
                icon: Images.getIcon("printer.png"))
            separator()
            button(actionPerformed: host.copyGraph,
                toolTipText: "Copy",
                icon: Images.getIcon("page_copy.png"))
            button(actionPerformed: host.copyGraphAsImage,
                toolTipText: "Copy as bit image",
                icon: Images.getIcon("camera_go.png"))
            separator()
            button(actionPerformed: {GJDefaults.editDefaults()},
                toolTipText: "Edit Preferences",
                icon: Images.getIcon("key.png"))
        }
    }


}

