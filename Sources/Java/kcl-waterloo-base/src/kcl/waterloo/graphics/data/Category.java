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
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package kcl.waterloo.graphics.data;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Category {

    private String text;
    private double xOffset;
    private double yOffset;
    private double rotation = -Math.PI / 4;
    private final static Category emptyInstance = new Category();

    public Category() {
    }

    public Category(String s) {
        text = s;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the xOffset
     */
    public double getXOffset() {
        return xOffset;
    }

    /**
     * @param xOffset the xOffset to set
     */
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * @return the yOffset
     */
    public double getYOffset() {
        return yOffset;
    }

    /**
     * @param yOffset the yOffset to set
     */
    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public static Category getEmptyInstance() {
        return emptyInstance;
    }

    /**
     * @return the rotation
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
