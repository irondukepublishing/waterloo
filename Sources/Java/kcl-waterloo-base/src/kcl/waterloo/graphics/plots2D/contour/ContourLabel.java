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
package kcl.waterloo.graphics.plots2D.contour;

import java.awt.Color;
import java.awt.Paint;
import kcl.waterloo.defaults.Colors;

/**
 *
 * @author ML
 */
public class ContourLabel {

    private double x;
    private double y;
    private double theta;
    private Paint background = Colors.getColor("TRANSPARENTWHITE");
    private Paint foreground = Color.DARK_GRAY;

    public ContourLabel() {
    }

    ContourLabel(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the theta
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * @return the background
     */
    public Paint getBackground() {
        return background;
    }

    /**
     * @param background the background to set
     */
    public void setBackground(Paint background) {
        this.background = background;
    }

    /**
     * @return the foreground
     */
    public Paint getForeground() {
        return foreground;
    }

    /**
     * @param foreground the foreground to set
     */
    public void setForeground(Paint foreground) {
        this.foreground = foreground;
    }
}
