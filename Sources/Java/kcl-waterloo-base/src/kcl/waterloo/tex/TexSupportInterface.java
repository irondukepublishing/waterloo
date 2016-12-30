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
package kcl.waterloo.tex;

/**
 * Defines an interface for TeX support. Currently, Waterloo graphics supports
 * TeX via the GPL supplement, which uses JLatexMath or via JavaFX, which can
 * display TeX in a WevView using MathJax. To guarantee portability to platforms
 * without either installed, include a standard component using the image
 * returned by getImage or the label returned by getLabel. For the kcl-gpl-tex
 * package using JLatexMath these return a BufferedImage and a JLabel
 * respectively.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface TexSupportInterface {

    /**
     * Returns true if this object supports TeX
     *
     * @return the flag
     */
    public boolean isTeXSupported();

    /**
     * Returns the TeX as an image. The class of the returned image is not
     * specified. For Swing it will generally be a
     * {@code java.awt.image.BufferedImage}.
     *
     * @return an image object
     */
    public Object getImage();

    /**
     * Returns the TeX as a label. The class of the returned label is not
     * specified. For Swing it will generally be a {@code javax.swing.JLabel}.
     *
     * @return a label object
     */
    public Object getLabel();
}
