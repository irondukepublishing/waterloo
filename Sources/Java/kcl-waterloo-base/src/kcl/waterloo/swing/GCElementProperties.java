/* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London  2013-
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
package kcl.waterloo.swing;

/**
 *
 * @author Malcolm Lidierth
 */
public class GCElementProperties {

    private double row = 0d;
    private double column = 0d;
    private double columnWidth = 100d;
    private double rowHeight = 100d;
    private int zOrder = -1;
    private double rotation = 0d;

    /**
     * @return the row
     */
    public double getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(double row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public double getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(double column) {
        this.column = column;
    }

    /**
     * @return the columnWidth
     */
    public double getColumnWidth() {
        return columnWidth;
    }

    /**
     * @param columnWidth the columnWidth to set
     */
    public void setColumnWidth(double columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * @return the rowHeight
     */
    public double getRowHeight() {
        return rowHeight;
    }

    /**
     * @param height the rowHeight to set
     */
    public void setRowHeight(double height) {
        this.rowHeight = height;
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

    /**
     * @return the zOrder
     */
    public int getZOrder() {
        return zOrder;
    }

    /**
     * @param zOrder the zOrder to set
     */
    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

}
