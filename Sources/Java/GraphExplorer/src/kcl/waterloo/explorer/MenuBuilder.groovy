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

package kcl.waterloo.explorer

import groovy.swing.SwingBuilder
import kcl.waterloo.defaults.GJDefaults

import javax.swing.*
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

import static javax.swing.KeyStroke.getKeyStroke

/**
 *
 * @author Malcolm Lidierth
 */
class MenuBuilder {

    static JMenuBar createMenu(GraphExplorer host) {

        SwingBuilder bldr = host.getConsole().getSwing()

        return bldr.menuBar({
                menu(text: "File", mnemonic: 'F'){
                    menuItem text: "Open Graph",
                    mnemonic: 'O',
                    accelerator: getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK),
                    actionPerformed: host.openGraph
                    menuItem(text: "Save Graph", 
                        mnemonic: 'S',
                        accelerator: getKeyStroke(KeyEvent.VK_S,  InputEvent.ALT_MASK),
                        actionPerformed: host.saveGraph)
                    menuItem(text: "Save Graph", 
                        mnemonic: 'A',
                        accelerator: getKeyStroke(KeyEvent.VK_A,  InputEvent.ALT_MASK),
                        actionPerformed: host.saveGraphAs)
                    menuItem(text: "Close Graph", 
                        mnemonic: 'C', 
                        accelerator: getKeyStroke(KeyEvent.VK_C,  InputEvent.ALT_MASK | InputEvent.SHIFT_MASK),
                        actionPerformed: host.closeGraph)
                    menuItem(text: "Print", 
                        mnemonic: 'P', 
                        accelerator: getKeyStroke(KeyEvent.VK_P,  InputEvent.ALT_MASK),
                        actionPerformed: host.printGraph)
                }
                menu(text: "Edit", mnemonic: 'E'){
                    menuItem(text: "Copy",
                        mnemonic: 'C',
                        accelerator: getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.CTRL_MASK),
                        actionPerformed: host.copyGraph)
                    menuItem(text: "Copy As Image",
                        mnemonic: 'I',
                        accelerator: getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_MASK | InputEvent.CTRL_MASK),
                        actionPerformed: host.copyGraphAsImage)
                    menuItem(text: "Preferences",
                        mnemonic: 'P',
                        accelerator: getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_MASK | InputEvent.CTRL_MASK),
                        actionPerformed: {GJDefaults.editDefaults()})
                }
            }
        )

    }
    
    
    static JMenu createHelpMenu(GraphExplorer host) {
        SwingBuilder bldr = host.getConsole().getSwing()
        return bldr.menu(text: "Help", mnemonic: 'H'){
            menuItem(text: "About GraphExplorer", 
                actionPerformed: {host.showAbout(host.getConsole())})
            menuItem(text: "Acknowlegements", 
                actionPerformed: {host.showAcknowlegements(host.getConsole())})
            menu(text: "Project WebSite"){
                menuItem(text: "Home Page", 
                    actionPerformed: {java.awt.Desktop.getDesktop().browse(new URI("http://sigtool.sourceforge.net/"))}
                )
                menuItem(text: "Wiki", 
                    actionPerformed: {java.awt.Desktop.getDesktop().browse(new URI("http://sourceforge.net/p/waterloo/wiki/Home/"))}
                )
                menuItem(text: "SourceForge Discussion", 
                    actionPerformed: {java.awt.Desktop.getDesktop().browse(new URI("http://sourceforge.net/p/waterloo/discussion/"))}
                )
                menuItem(text: "Bug Reports", 
                    actionPerformed: {java.awt.Desktop.getDesktop().browse(new URI("http://sourceforge.net/p/waterloo/bugs/"))}
                )
            }
        
        }
    }
    
    
    
    
}



