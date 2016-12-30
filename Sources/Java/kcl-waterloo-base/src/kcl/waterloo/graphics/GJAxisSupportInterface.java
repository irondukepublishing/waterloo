/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
package kcl.waterloo.graphics;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import kcl.waterloo.graphics.data.Category;

/**
 * Defines axis support methods for graphs.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public interface GJAxisSupportInterface {

    /**
     * @return true if the top axis is painted
     */
    public boolean isTopAxisPainted();

    /**
     * @return true if the left axis is painted
     */
    public boolean isLeftAxisPainted();

    /**
     * @return true if the right axis is painted
     */
    public boolean isRightAxisPainted();

    /**
     * @return true if the bottom axis is painted
     */
    public boolean isBottomAxisPainted();

    /**
     * Sets the flag for painting the top axis
     *
     * @param flag the setting to use
     */
    public void setTopAxisPainted(boolean flag);

    /**
     * Sets the flag for painting the left axis
     *
     * @param flag the setting to use
     */
    public void setLeftAxisPainted(boolean flag);

    /**
     * Sets the flag for painting the right axis
     *
     * @param flag the setting to use
     */
    public void setRightAxisPainted(boolean flag);

    /**
     * Sets the flag for painting the bottom axis
     *
     * @param flag the setting to use
     */
    public void setBottomAxisPainted(boolean flag);

    /**
     * Returns the panel for drawing the top axis.
     *
     * @return a GJAxisPanel instance
     */
    public GJAxisPanel getTopAxisPanel();

    /**
     * Returns the panel for drawing the left axis.
     *
     * @return a GJAxisPanel instance
     */
    public GJAxisPanel getLeftAxisPanel();

    /**
     * Returns the panel for drawing the right axis.
     *
     * @return a GJAxisPanel instance
     */
    public GJAxisPanel getRightAxisPanel();

    /**
     * Returns the panel for drawing the bottom axis.
     *
     * @return a GJAxisPanel instance
     */
    public GJAxisPanel getBottomAxisPanel();

    /**
     * Returns the string for the x-axis label
     *
     * @return the String
     */
    public String getXLabel();

    /**
     * Returns the string for the y-axis label
     *
     * @return the String
     */
    public String getYLabel();

    /**
     * Sets the string for the x-axis label
     *
     * @param s the String
     */
    public void setXLabel(String s);

    /**
     * Sets the string for the x-axis label
     *
     * @param s the String
     */
    public void setYLabel(String s);

    /**
     * Returns true if the x-axis and data are painted in reverse
     *
     * @return the reversal flag
     */
    public boolean isXReversed();

    /**
     * Sets the axis reversal flag for the x-axis.
     *
     * @param flag
     */
    public void setReverseX(boolean flag);

    /**
     * Returns true if the y-axis and data are painted in reverse
     *
     * @return the reversal flag
     */
    public boolean isYReversed();

    /**
     * Sets the axis reversal flag for the x-axis.
     *
     * @param flag
     */
    public void setReverseY(boolean flag);

    /**
     * Returns the minimum value for the x-axis display
     *
     * @return the value
     */
    public double getXMin();

    /**
     * Returns the maximum value for the x-axis display
     *
     * @return the value
     */
    public double getXMax();

    /**
     * Returns the minimum value for the y-axis display
     *
     * @return the value
     */
    public double getYMin();

    /**
     * Returns the maximum value for the x-axis display
     *
     * @return the value
     */
    public double getYMax();

    /**
     * Returns the value displayed at the left of the x-axis
     *
     * @return the value
     */
    public double getXLeft();

    /**
     * Sets the value displayed at the left of the x-axis
     *
     * @param val the value
     */
    public void setXLeft(double val);

    /**
     * Returns the value displayed at the right of the x-axis
     *
     * @return the value
     */
    public double getXRight();

    /**
     * Sets the value displayed at the right of the x-axis
     *
     * @param val the value
     */
    public void setXRight(double val);

    /**
     * Returns the value displayed at the top of the y-axis
     *
     * @return the value
     */
    public double getYTop();

    /**
     * Sets the value displayed at the top of the y-axis
     *
     * @param val the value
     */
    public void setYTop(double val);

    /**
     * Returns the value displayed at the bottom of the y-axis
     *
     * @return the value
     */
    public double getYBottom();

    /**
     * Sets the value displayed at the bottom of the y-axis
     *
     * @param val the value
     */
    public void setYBottom(double val);

    /**
     * Returns the origin through which internal axes will be painted.
     *
     * @return a Point2D object
     */
    public Point2D getOrigin();

    /**
     * Sets the origin - through which internal axes will be painted.
     *
     * @param p a Point2D object
     */
    public void setOrigin(Point2D p);

    /**
     * Returns the origin for the x-dimension
     *
     * @return a double
     */
    public double getOriginY();

    /**
     * Returns the origin for the y-dimension
     *
     * @return a double
     */
    public double getOriginX();

    /**
     * Sets the origin for the x-dimension
     *
     * @param v a double
     */
    public void setOriginX(double v);

    /**
     * Sets the origin for the y-dimension
     *
     * @param v a double
     */
    public void setOriginY(double v);

    /**
     * Returns a hint for the number of minor grids to paint per major grid
     * division for the x-axis.
     *
     * @return an int
     */
    public int getMinorCountXHint();

    /**
     * Returns a hint for the number of minor grids to paint per major grid
     * division for the y-axis.
     *
     * @return an int
     */
    public int getMinorCountYHint();

    /**
     * Returns true if the left axis is labelled
     *
     * @return the label flag
     */
    public boolean isLeftAxisLabelled();

    /**
     * Returns true if the right axis is labelled
     *
     * @return the label flag
     */
    public boolean isRightAxisLabelled();

    /**
     * Returns true if the top axis is labelled
     *
     * @return the label flag
     */
    public boolean isTopAxisLabelled();

    /**
     * Returns true if the bottom axis is labelled
     *
     * @return the label flag
     */
    public boolean isBottomAxisLabelled();

    /**
     * Sets the left axis is labelling flag
     *
     * @param flag the label flag
     */
    public void setLeftAxisLabelled(boolean flag);

    /**
     * Sets the right axis is labelling flag
     *
     * @param flag the label flag
     */
    public void setRightAxisLabelled(boolean flag);

    /**
     * Sets the top axis is labelling flag
     *
     * @param flag the label flag
     */
    public void setTopAxisLabelled(boolean flag);

    /**
     * Sets the bottom axis is labelling flag
     *
     * @param flag the label flag
     */
    public void setBottomAxisLabelled(boolean flag);

    /**
     * Returns true if the major grid is painted
     *
     * @return the flag
     */
    public boolean isMajorGridPainted();

    /**
     * Sets the flag for painting the major grid
     *
     * @param flag the boolean value
     */
    public void setMajorGridPainted(boolean flag);

    /**
     * Returns true if the minor grid is painted
     *
     * @return the flag
     */
    public boolean isMinorGridPainted();

    /**
     * Sets the flag for painting the minor grid
     *
     * @param flag the boolean value
     */
    public void setMinorGridPainted(boolean flag);

    /**
     * Returns true if the inner axis is painted
     *
     * @return the flag
     */
    public boolean isInnerAxisPainted();

    /**
     * Sets the flag for painting the inner axis
     *
     * @param flag the boolean value
     */
    public void setInnerAxisPainted(boolean flag);

    /**
     * Returns the flag for painting the inner axis labels
     *
     * @return the flag
     */
    public boolean isInnerAxisLabelled();

    /**
     * Sets the flag for painting the inner axis labels
     *
     * @param flag the boolean value
     */
    public void setInnerAxisLabelled(boolean flag);

    /**
     * Returns the axis painting color
     *
     * @return the color
     */
    public Color getAxisColor();

    /**
     * Sets the color for painting the axis
     *
     * @param axisColor the color
     */
    public void setAxisColor(Color axisColor);

    /**
     * Returns the major grid painting color
     *
     * @return the color
     */
    public Color getMajorGridColor();

    /**
     * Sets the color for painting the major grid
     *
     * @param majorGridColor the color
     */
    public void setMajorGridColor(Color majorGridColor);

    /**
     * Returns the minor grid painting color
     *
     * @return the color
     */
    public Color getMinorGridColor();

    /**
     * Sets the color for painting the minor grid
     *
     * @param minorGridColor the color
     */
    public void setMinorGridColor(Color minorGridColor);

    /**
     * Returns a string for axis labels formatted by an internal formatter
     *
     * @param v the value to format
     * @return the String
     */
    public String format(double v);

    /**
     * Returns a string for x-axis labels formatted by an internal formatter
     *
     * @param v the value to format
     * @return the String
     */
    public String formatXAxisLabel(double v);

    /**
     * Returns a string for y-axis labels formatted by an internal formatter
     *
     * @param v the value to format
     * @return the String
     */
    public String formatYAxisLabel(double v);

    /**
     * Returns the rotation for x-axis labels at the location specified by v
     *
     * @param v the location
     * @return the rotation in radians
     */
    public double getXAxisLabelRotation(double v);

    /**
     * Returns the rotation for y-axis labels at the location specified by v
     *
     * @param v the location
     * @return the rotation in radians
     */
    public double getYAxisLabelRotation(double v);

    /**
     * Returns true if the data on the specified axis contains categorical
     * values.
     *
     * @param o a GJAxisPanel.Orientation value
     * @return true if categorical data are present
     */
    public boolean isCategorical(GJAxisPanel.Orientation o);

    /**
     * Returns the categorical data on the specified axis
     *
     * @param o a GJAxisPanel.Orientation value
     * @return an ArrayList<Category>
     */
    public ArrayList<Category> getCategoricalLabels(GJAxisPanel.Orientation o);

    /**
     * Returns the mouse handler for this axis
     *
     * @return a MouseAdapter
     */
    public MouseAdapter getAxisMouseHandler();

    /**
     * Returns the bounds of the axes (xLeft, yBottom, width, height)
     *
     * @return a Rectangle2D
     */
    public Rectangle2D getAxesBounds();

    /**
     * Sets the bounds of the axes (xLeft, yBottom, width, height)
     *
     * @param r a Rectangle2D
     */
    public void setAxesBounds(Rectangle2D r);

    /**
     * Provides a hint to how many minor grid lines should be painted.
     *
     * @param minorCountX number for the x-axis
     */
    public void setMinorCountXHint(int minorCountX);

    /**
     * Provides a hint to how many minor grid lines should be painted.
     *
     * @param minorCountY number for the y-axis
     */
    public void setMinorCountYHint(int minorCountY);

    /**
     * Automatically sets the hint
     */
    public void setMajorXHint();

    /**
     * Returns the value for the hint
     *
     * @return the value
     */
    public double getMajorXHint();

    /**
     * Sets the hint to the specified value
     *
     * @param hint the value
     */
    public void setMajorXHint(double hint);

    /**
     * Automatically sets the hint
     */
    public void setMajorYHint();

    /**
     * Returns the value for the hint
     *
     * @return the value
     */
    public double getMajorYHint();

    /**
     * Sets the hint to the specified value
     *
     * @param hint the value
     */
    public void setMajorYHint(double hint);

    /**
     * Returns the stroke thickness for axes
     *
     * @return thickness in pixels
     */
    public float getAxisStrokeWeight();

    /**
     * Sets the width of lines for the axes
     *
     * @param axisStroke the stroke thickness in pixels
     */
    public void setAxisStrokeWeight(float axisStroke);

    /**
     * Returns the stroke thickness for minor grids
     *
     * @return thickness in pixels
     */
    public float getMinorGridStrokeWeight();

    /**
     * Sets the stroke thickness for minor grids
     *
     * @param gridStroke the thickness in pixels
     */
    public void setMinorGridStrokeWeight(float gridStroke);

    /**
     * Returns the stroke thickness for major grids
     *
     * @return thickness in pixels
     */
    public float getMajorGridStrokeWeight();

    /**
     * Sets the stroke thickness for major grids
     *
     * @param majorGridStrokeWeight the thickness in pixels
     */
    public void setMajorGridStrokeWeight(float majorGridStrokeWeight);
}
