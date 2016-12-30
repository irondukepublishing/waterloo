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

import kcl.waterloo.xml.FileWrapper
import kcl.waterloo.deploy.image.ImageSupport


/**
 *
 * @author Malcolm Lidierth
 */
public class LoadSupplements {
    
    private LoadSupplements(){}

    public static void load(FileWrapper fileWrapper, GraphExplorer host){
        for (File f : fileWrapper.getSupplements()) {
            Object panel;
            if (ImageSupport.isImageFormatSupported(f)) {
                try {
                    panel=host.imagePanelClass.newInstance(f)
                    host.addTab(panel)
                    System.out.println(f);
                } catch (NoClassDefFoundError ex) {
                    System.err.println("NoClassDefFoundError: Code to support loading of " + f.getName() + " appear not to be included in this distribution");
                }
            }
        }
    }
}
	


