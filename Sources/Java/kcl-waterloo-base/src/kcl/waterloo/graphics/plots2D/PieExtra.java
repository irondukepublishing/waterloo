 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2012-
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
package kcl.waterloo.graphics.plots2D;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author malcolm
 */
public class PieExtra extends FontSupport {

    /**
     * For bar plots, indicates the display mode for multiplexed data.
     */
    public enum MODE {

        QUANTITIES, PERCENTAGES, DEGREES, RADIANS
    }

    public enum LABELPOSITION {

        INSIDE, OUTSIDE
    }

    public enum LABELORIENTAION {

        AUTO, HORIZONTAL, VERTICAL
    }

    /**
     * Mode for interpreting the y-values: as a quantity, percentage, degrees or
     * radians.
     */
    private kcl.waterloo.graphics.plots2D.PieExtra.MODE mode = kcl.waterloo.graphics.plots2D.PieExtra.MODE.QUANTITIES;
    /**
     * Determines if labels are drawn
     */
    private boolean labeled = true;
    /**
     * String Label for each segment
     */
    private ArrayList<String> labels = new ArrayList<String>();
    /**
     * If set, and labels is not populated with Strings, this is used to format
     * the numbers used for labeling.
     */
    private NumberFormat labelFormat = null;
    /**
     * Sets the orientation of the labels. Presently ignored.
     */
    private LABELORIENTAION labelOrientation = LABELORIENTAION.AUTO;
    /**
     * Array of booleans. Set true to explode the corresponding segment.
     */
    private boolean[] explode = new boolean[0];
    /**
     * Outer radius for the pie chart relative to the minimum dimension of the
     * view.
     */
    private double outerRadius = 0.95d;
    /**
     * Sets how far exploded segments are offset as fraction of the unit circle
     * radius.
     */
    private double explodeExtent = 0.02d;
    /**
     * Inner radius - if non-zero sets the inner radius for a "wheel" plot.
     */
    private double innerRadius = 0d;
    /**
     * Radius for the labels.
     */
    private double labelRadius = 0.7d;

    /**
     * Sets the mode. YData values will be interpreted as QUANTITIES (default),
     * PERCENTAGES, DEGREES or RADIANS.
     *
     * @return the mode
     */
    public kcl.waterloo.graphics.plots2D.PieExtra.MODE getMode() {
        return mode;
    }

    /**
     * Sets the mode. YData values will be interpreted as QUANTITIES (default),
     * PERCENTAGES, DEGREES or RADIANS.
     *
     * @param mode the mode to set
     */
    public void setMode(kcl.waterloo.graphics.plots2D.PieExtra.MODE mode) {
        this.mode = mode;
    }

    /**
     * Sets the mode. YData values will be interpreted as QUANTITIES (default),
     * PERCENTAGES, DEGREES or RADIANS.
     *
     * @param s "QUANTITIES", "PERCENTAGES", "DEGREES" or "RADIANS".
     */
    public void setMode(String s) {
        s = s.toUpperCase();
        if (s.contains("QUANTITIES")) {
            mode = MODE.QUANTITIES;
        } else if (s.contains("PERCENTAGES")) {
            mode = MODE.PERCENTAGES;
        } else if (s.contains("DEGREES")) {
            mode = MODE.DEGREES;
        } else if (s.contains("RADIANS")) {
            mode = MODE.RADIANS;
        }
    }

    /**
     * Returns the custom labels for the pie segments as an ArrayList of
     * Strings.
     *
     * @return the labels
     */
    public ArrayList<String> getLabels() {
        return labels;
    }

    /**
     * Sets the custom labels for the pie segments as an ArrayList of Strings.
     *
     * @param labels the labels to set
     */
    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    /**
     * Sets the custom labels for the pie segments as a String[].
     *
     * @param labels
     */
    public void setLabels(String[] labels) {
        ArrayList<String> copy = new ArrayList<String>();
        copy.addAll(Arrays.asList(labels));
        this.labels = copy;
    }

    /**
     * Returns the orientation of the labels. This value is presently ignored
     * and labels are always drawn horizontally.
     *
     * @return the labelOrientation
     */
    public LABELORIENTAION getLabelOrientation() {
        return labelOrientation;
    }

    /**
     * Sets the orientation of the labels. This value is presently ignored and
     * labels are always drawn horizontally.
     *
     * @param labelOrientation the labelOrientation to set
     */
    public void setLabelOrientation(LABELORIENTAION labelOrientation) {
        this.labelOrientation = labelOrientation;
    }

    /**
     * Sets the orientation of the labels. This value is presently ignored and
     * labels are always drawn horizontally.
     *
     * @param s
     */
    public void setLabelOrientation(String s) {
        s = s.toUpperCase();
        if (s.matches("AUTO")) {
            setLabelOrientation(LABELORIENTAION.AUTO);
        } else if (s.matches("HORIZONTAL")) {
            setLabelOrientation(LABELORIENTAION.HORIZONTAL);
        } else if (s.matches("VERTICAL")) {
            setLabelOrientation(LABELORIENTAION.VERTICAL);
        }
    }

    /**
     * Returns the radius of the pie chart where a value of 1.0 fills the
     * available graph space.
     *
     * @return the outerRadius
     */
    public double getOuterRadius() {
        return outerRadius;
    }

    /**
     * Sets the radius of the pie chart where a value of 1.0 fills the available
     * graph space
     *
     * @param outerRadius the outerRadius to set
     */
    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    /**
     * Returns the radius of inner circle for a "wheel" plot where a value of
     * 1.0 fills the available graph space
     *
     * @return the innerRadius
     */
    public double getInnerRadius() {
        return innerRadius;
    }

    /**
     * Sets the radius of inner circle for a "wheel" plot where a value of 1.0
     * fills the available graph space
     *
     * @param innerRadius the innerRadius to set
     */
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    /**
     * Returns the radius of the circle where labels will be drawn. a radius of
     * 1.0 fills the available graph space
     *
     * @return the labelRadius
     */
    public double getLabelRadius() {
        return labelRadius;
    }

    /**
     * Sets the radius of the circle where labels will be drawn. a radius of 1.0
     * fills the available graph space
     *
     * @param labelRadius the labelRadius to set
     */
    public void setLabelRadius(double labelRadius) {
        this.labelRadius = labelRadius;
    }

    /**
     * Returns the array of booleans used to "explode" segments. A value of true
     * offsets the segment from the main circle.
     *
     * @return the explode
     */
    public boolean[] getExplode() {
        return explode;
    }

    /**
     * Sets the array of booleans used to "explode" segments. A value of true
     * offsets the segment from the main circle.
     *
     * @param explode the explode to set
     */
    public void setExplode(boolean[] explode) {
        this.explode = explode;
    }

    /**
     * Returns the size of the offset for exploded segments relative to the
     * radius of the unit circle.
     *
     * @return the explodeExtent
     */
    public double getExplodeExtent() {
        return explodeExtent;
    }

    /**
     * Sets the size of the offset for exploded segments relative to the radius
     * of the unit circle.
     *
     * @param explodeExtent the explodeExtent to set
     */
    public void setExplodeExtent(double explodeExtent) {
        this.explodeExtent = explodeExtent;
    }

    /**
     * Returns the formatter used to format labels (if custom Strings have not
     * been set).
     *
     * @return the labelFormat
     */
    public NumberFormat getLabelFormat() {
        return labelFormat;
    }

    /**
     * Sets the formatter used to format labels (if custom Strings have not been
     * set).
     *
     * @param labelFormat the labelFormat to set
     */
    public void setLabelFormat(NumberFormat labelFormat) {
        this.labelFormat = labelFormat;
    }

    /**
     * @return the labeled
     */
    public boolean isLabeled() {
        return labeled;
    }

    /**
     * @param labeled the labeled to set
     */
    public void setLabeled(boolean showLabels) {
        this.labeled = showLabels;
    }
}
