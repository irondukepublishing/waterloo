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

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.TreeMap;

/**
 *
 * @author ML
 */
class ContourSet extends TreeMap<Double, ArrayList<Contour>> {

    private final Area commonArea = new Area();

    public ContourSet() {
    }

    public final Area getCommonArea() {
        return (Area) commonArea.clone();
    }

    public final void setCommonArea() {
        final Path2D commonPath = new Path2D.Double();
        for (ArrayList<Contour> arr : this.values()) {
            for (Contour c : arr) {
                if (c.isClosed()) {
                    commonPath.append(c.getPath(), false);
                } else {
                    commonPath.append(c.getPath(), true);
                }
            }
        }
        commonArea.reset();
        commonArea.add(new Area(commonPath));
    }

    public final Rectangle2D getBounds2D() {
        Rectangle2D r = new Rectangle2D.Double(0, 0, 0, 0);
        for (Double level : keySet()) {
            ArrayList<Contour> c = get(level);
            for (Contour c1 : c) {
                r.setRect(r.createUnion(c1.getPath().getBounds2D()));
            }
        }
        return r;
    }

    /**
     * Returns the contours as an ArrayList sorted in order of the contour inner
     * areas - largest first.
     *
     * @return sorted ArrayList<Contour>
     */
    public final ArrayList<Contour> sort() {
        return byArea();
    }

    /**
     * Returns the list ordered with those whose inner area is largest.
     *
     * @return
     */
    private ArrayList<Contour> byArea() {
        ArrayList<Contour> all = new ArrayList<Contour>();
        for (Double level : keySet()) {
            ArrayList<Contour> c = get(level);
            all.addAll(c);
        }
        Collections.sort(all, Collections.reverseOrder(AreaSorter.getInstance()));
        return all;
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        String clss = getClass().toString().replace("kcl.waterloo.graphics.plots2D.contour.", "");
        clss = clss.replace("class", "");
        f.format("Waterloo graphics %s [@%x]:\n", clss, hashCode());
        f.format("NOTE: Double.NaN, when present, are ignored\n");
        for (Double key : this.keySet()) {
            ArrayList<Contour> list = get(key);
            f.format("\nLevel %7.3f:", key.doubleValue());
            int i = 0;
            for (Contour c : list) {
                Double[] val;
                val = new Double[Math.min(c.getX().size(), 20)];
                val = c.getX().subList(0, Math.min(c.getX().size(), 20)).toArray(val);
                f.format("\nx%d: ", i);
                outputVector(f, val);
                val = new Double[Math.min(c.getY().size(), 20)];
                val = c.getY().subList(0, Math.min(c.getY().size(), 20)).toArray(val);
                f.format("\ny%d: ", i);
                outputVector(f, val);
                i++;
            }
        }
        f.format("\n");
        return f.toString();
    }

    private static void outputVector(Formatter f, Double[] val) {
        if (val.length >= 16) {
            for (int k = 0; k < 8; k++) {
                f.format("%7.3f ", val[k].doubleValue());
            }
            f.format(" ...");
            for (int k = val.length - 8; k < val.length; k++) {
                f.format("%7.3f ", val[k].doubleValue());
            }
        } else {
            for (int k = 0; k < val.length; k++) {
                f.format("%7.3f ", val[k].doubleValue());
            }
        }
    }

    private static final class AreaSorter implements Comparator<Contour> {

        private static AreaSorter instance = new AreaSorter();

        private AreaSorter() {
        }

        private static AreaSorter getInstance() {
            return instance;
        }

        @Override
        public final int compare(Contour c0, Contour c1) {
            if (c0.getArea() > c1.getArea()) {
                return 1;
            } else if (c0.getArea() < c1.getArea()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
