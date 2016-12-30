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
package kcl.waterloo.util;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Version {

    private long majorVersion = 1L;
    private long minorVersion = 1L;
    private long branchVersion = 0L;
    private static Version instance = new Version();

    private Version() {
    }

    public static Version getVersion() {
        return instance;
    }

    /**
     * @return the majorVersion
     */
    public long getMajorVersion() {
        return majorVersion;
    }

    /**
     * @return the minorVersion
     */
    public long getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return the branchVersion
     */
    public long getBranchVersion() {
        return branchVersion;
    }

    public String getBranchString() {
        return "King's College London";
    }

    @Override
    public String toString() {
        String releaseType = "(RC2)";
        return "Waterloo Graphics Version " + majorVersion + "." + minorVersion + "."
                + branchVersion + releaseType;
    }
}
