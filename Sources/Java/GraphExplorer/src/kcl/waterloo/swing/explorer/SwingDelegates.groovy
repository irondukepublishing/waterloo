/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
package kcl.waterloo.swing.explorer

import javax.swing.*

/**
 *
 * @author Malcolm Lidierth, King's College London <a href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
class SwingDelegates {

    def static frameClosurePolicy=JFrame.DISPOSE_ON_CLOSE
    
    static def metaChange(Object c){
        
        c.frameConsoleDelegates=frameConsoleDelegates
        
        c.metaClass.updateTitle={->if (frame.properties.containsKey('title')) {
                if (scriptFile != null) {
                    frame.title = scriptFile.name + (dirty?" * ":"") + " - Groovy Console"
                } else {
                    frame.title = "Waterloo Graph Explorer"
                }
            }
        } 
        
        //TODO: Switch off ths menu for this
        c.prefs.putBoolean('detachedOutput', false)
    }
        
        
    static def frameConsoleDelegates = [
            rootContainerDelegate:{
                frame(
                    title: 'GroovyConsole',
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


