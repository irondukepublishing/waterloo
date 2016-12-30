 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2011-
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
package kcl.waterloo.marker;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJRadialGradientFactory implements Paint {

    public float cx;
    public float cy;
    public float radius;
    public float[] fractions;
    public Color[] colors;

    public GJRadialGradientFactory(float CX, float CY, float radius, float[] fractions, Color[] colors) {
        cx = CX;
        cy = CY;
        this.radius = radius;
        this.fractions = fractions.clone();
        this.colors = colors.clone();
    }

    public RadialGradientPaint getPaint(float x, float y) {
        return new RadialGradientPaint(x, y, radius, x + cx, x + cy, fractions, colors, RadialGradientPaint.CycleMethod.REPEAT);
    }

    /**
     * @return the cx
     */
    public float getCx() {
        return cx;
    }

    /**
     * @param cx the cx to set
     */
    public void setCx(float cx) {
        this.cx = cx;
    }

    /**
     * @return the cy
     */
    public float getCy() {
        return cy;
    }

    /**
     * @param cy the cy to set
     */
    public void setCy(float cy) {
        this.cy = cy;
    }

    /**
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * @return the fractions
     */
    public float[] getFractions() {
        return fractions.clone();
    }

    /**
     * @param fractions the fractions to set
     */
    public void setFractions(float[] fractions) {
        this.fractions = fractions.clone();
    }

    /**
     * @return the colors
     */
    public Color[] getColors() {
        return colors.clone();
    }

    /**
     * @param colors the colors to set
     */
    public void setColors(Color[] colors) {
        this.colors = colors.clone();
    }

    public void setColor(int index, Color color) {
        colors[index] = color;
    }

    /**
     *
     * @param cm
     * @param rctngl
     * @param rd
     * @param at
     * @param rh
     * @return a PaintContext
     */
    @Override
    public PaintContext createContext(ColorModel cm, Rectangle rctngl, Rectangle2D rd, AffineTransform at, RenderingHints rh) {
        return this.getPaint(0, 0).createContext(cm, rctngl, rd, at, rh);
    }

    /**
     * Returns the transparency as an integer
     *
     * @return an int
     */
    @Override
    public int getTransparency() {
        return this.getPaint(0, 0).getColors()[0].getAlpha();
    }
}
