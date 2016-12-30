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
package kcl.waterloo.math;

import java.util.ArrayList;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public final class ArrayUtils {

    private final static Object lock = new Object();
//    private static boolean doSecureDoubleCopy = false;

    private ArrayUtils() {
    }

//    public static double[] doDoubleCopy(double[] extra) {
//        synchronized (lock) {
//            if (getSecureDoubleCopy() == true) {
//                if (extra == null) {
//                    return null;
//                } else {
//                    return extra.clone();
//                }
//            } else {
//                return extra;
//            }
//        }
//    }
    public static double[] asDouble(Double[] in) {
        double[] out = new double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = in[k].doubleValue();
        }
        return out;
    }

    public static Double[] boxed(double[] in) {
        Double[] out = new Double[in.length];
        for (int k = 0; k < in.length; k++) {
            out[k] = Double.valueOf(in[k]);
        }
        return out;
    }

    public static ArrayList<Double> toArrayList(double[] arr) {
        ArrayList<Double> list = new ArrayList<Double>();
        for (int k = 0; k < arr.length; k++) {
            list.add(Double.valueOf(arr[k]));
        }
        return list;
    }

    public static double[] asDouble(ArrayList<Double> in) {
        double[] out = new double[in.size()];
        for (int k = 0; k < in.size(); k++) {
            out[k] = in.get(k).doubleValue();
        }
        return out;
    }

//    public static void setSecureDoubleCopy(boolean flag) {
//        synchronized (lock) {
//            doSecureDoubleCopy = flag;
//        }
//    }
//
//    public static boolean getSecureDoubleCopy() {
//        synchronized (lock) {
//            return doSecureDoubleCopy;
//        }
//    }
}
