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
package kcl.waterloo.annotation;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import kcl.waterloo.marker.GJMarker;

/**
 *
 * Defines the methods for use by annotations.
 *
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <W>
 * @param <X>
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJAnnotationInterface<T, U, V, W, X> {

    /**
     * @return the Annotation
     */
    public T getPath();

    /**
     * @param annotation
     */
    public void setPath(T annotation);

    /**
     * @return the Marker
     */
    public GJMarker getMarker();

    /**
     * @param Marker the Marker to set
     */
    public void setMarker(GJMarker Marker);

    /**
     * @return the plot
     */
    public U getParentGraph();

    /**
     * @param plot the plot to set
     */
    public void setParentGraph(U plot);

    /**
     * @return the LineStroke
     */
    public V getLineStroke();

    /**
     * @param LineStroke the LineStroke to set
     */
    public void setLineStroke(V LineStroke);

    /**
     * @return the LineColor
     */
    public W getLineColor();

    /**
     * @param LineColor the LineColor to set
     */
    public void setLineColor(W LineColor);

    /**
     * @return the Fill
     */
    public W getFill();

    /**
     * @param Fill the Fill to set
     */
    public void setFill(W Fill);

    /**
     * @return the Font
     */
    public X getFont();

    /**
     * @param Font the Font to set
     */
    public void setFont(X Font);

    /**
     * @return the X
     */
    public double getXData();

    /**
     * @param X the X to set
     */
    public void setXData(double X);

    /**
     * @return the Y
     */
    public double getYData();

    /**
     * @param Y the Y to set
     */
    public void setYData(double Y);

    /**
     * @return the Text
     */
    public String getText();

    /**
     * @param Text the Text to set
     */
    public void setText(String Text);

    /**
     * @return the TextColor
     */
    public W getTextColor();

    /**
     * @param TextColor the TextColor to set
     */
    public void setTextColor(W TextColor);

    /**
     * @return the Name
     */
    public String getName();

    /**
     * @param Name the Name to set
     */
    public void setName(String Name);

    /**
     * Sets an extra data object used internally by some annotations.
     *
     * @param o
     */
    public void setExtra(GJMarker o);

    /**
     * Gets the extra data.
     *
     * @return the GJMarker
     */
    public GJMarker getExtra();

    /**
     * Removes the annotation
     *
     */
    public void remove();

    /**
     * Removes annotation(s) named by the string
     *
     * @param s the name of the annotation(s) to remove
     */
    public void remove(String s);

    /**
     * Paints the annotation to the specified Graphics2D object. The
     * AffineTransform should be set up to scale and translate the graphics from
     * the GJGraphInterface space specified in the GJAnnotation instance.
     *
     * @param g2 Graphics2D instance
     * @param af AffineTransform
     */
    public void paintAnnotation(Graphics2D g2, AffineTransform af);
}
