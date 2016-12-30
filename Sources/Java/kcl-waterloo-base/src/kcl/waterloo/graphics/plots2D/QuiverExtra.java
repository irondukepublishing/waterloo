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
package kcl.waterloo.graphics.plots2D;

/**
 * Provides the data model extra object for quiver plots.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class QuiverExtra {

    /**
     * Scale factor.
     */
    private double userScaleFactor = 1d;
    /**
     * If true use quadTo (false use lineTo) for arrows.
     */
    private boolean useQuad = true;

    public QuiverExtra() {
        this(1);
    }

    public QuiverExtra(double user) {
        userScaleFactor = user;
    }

    /**
     * @return the userScaleFactor
     */
    public double getUserScaleFactor() {
        return userScaleFactor;
    }

    /**
     * @param UserScaleFactor the userScaleFactor to set
     */
    public void setUserScaleFactor(double UserScaleFactor) {
        this.userScaleFactor = UserScaleFactor;
    }

    /**
     * @return the useQuad
     */
    public boolean isUseQuad() {
        return useQuad;
    }

    /**
     * @param useQuad the useQuad to set
     */
    public void setUseQuad(boolean useQuad) {
        this.useQuad = useQuad;
    }
}
