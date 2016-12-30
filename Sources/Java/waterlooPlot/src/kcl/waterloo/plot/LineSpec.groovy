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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.plot

import java.awt.Color
import kcl.waterloo.graphics.GJUtilities

/**
 *
 */
class LineSpec {

    Closure marker
    Closure stroke
    Color color

    String _marker = 's'
    String _stroke = '-'
    String _color = 'b'

    LineSpec(String input) {

        input.each { c ->
            switch (c){
                case '+':
                    case 'o':
                    case '*':
                    case '^':
                    case 'x':
                    case 's':
                    case 'd':
                    case 'v':
                    case '<':
                    case '>':
                    case 'p':
                    case 'h':
                    _marker=c
                    break
                case  'y':
                    case 'm':
                    case 'c':
                    case 'r':
                    case 'g':
                    case 'v':
                    case 'w':
                    case 'k':
                    _color=c
                    break
            }

        }

        if (input.contains('--'))
            _stroke='--'
        else if (input.contains('._'))
            _stroke='.-'
        else if (input.contains(':'))
            _stroke=':'
        else if (input.contains('-'))
            _stroke='-'

        marker = WPlot.convertMarkerType(_marker)

        stroke = {-> GJUtilities.createStroke _stroke}

        color = WPlot.convertColor(_color)
    }

}