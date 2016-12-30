/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London and the author 2011-
 * 
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
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

import groovy.ui.Console
import kcl.waterloo.gui.images.Images

import java.awt.Desktop
import java.awt.Dimension
import java.awt.Font
import javax.swing.JEditorPane
import javax.swing.JFrame
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener
import java.awt.Window

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
class FrameDelegate {
    
    def static frameClosurePolicy=JFrame.DISPOSE_ON_CLOSE
    def static String title="Waterloo Graph Explorer"
    
    
    static def metaChange(Console c){
        c.frameConsoleDelegates=frameConsoleDelegates
        c.metaClass.updateTitle={->if (frame.properties.containsKey('title')) {frame.title = title}   
        }
    }
    

    static def frameConsoleDelegates = [
            rootContainerDelegate:{
                frame(
                    title: 'Waterloo GraphExplorer',
                    //location: [100,100], // in groovy 2.0 use platform default location
                    iconImage: imageIcon('/groovy/ui/ConsoleIcon.png').image,
                    defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,
                ) {
                    try {
                        current.locationByPlatform = true
                    } catch (Exception e) {
                        current.location = [100, 100] // for 1.4 compatibility
                    }
                    containingWindows += current
                }
            },
            menuBarDelegate: {arg->
                current.JMenuBar = build(arg)}
        ];
    	
}


