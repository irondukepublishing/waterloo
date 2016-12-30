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
package kcl.waterloo.defaults;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import kcl.waterloo.math.ArrayMath;

/**
 * Colors and color utilities.
 *
 * Defines a set of web colors corresponding to those in JavaFX. See
 * http://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html for
 * details.
 *
 * N.B. Future versions of the library are likely to add JavaFX support.
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Colors {

    /**
     * LinkedHashMap of standard named web colors.
     */
    private static final LinkedHashMap<String, java.awt.Color> color = new LinkedHashMap<String, java.awt.Color>();
    private static final LinkedHashMap<Integer, java.awt.Color> blueDarkOrange18 = new LinkedHashMap<Integer, java.awt.Color>();
    private static final LinkedHashMap<Integer, java.awt.Color> blueDarkRed18 = new LinkedHashMap<Integer, java.awt.Color>();
    private static LinkedHashMap<Integer, java.awt.Color> defaultMap = blueDarkRed18;

    //   CIELab reference values: Observer= 2 degrees, Illuminant= D65
    private static final double ref_X = 95.047;
    private static final double ref_Y = 100.000;
    private static final double ref_Z = 108.883;
    /**
     * Static instance. The instance is not made accessible - it is here only to
     * force instantiation of the hash map when the class is first referenced in
     * a JVM session.
     */
    private static final Colors instance = new Colors();

    /**
     *
     */
    private Colors() {
        color.put("TRANSPARENT", new java.awt.Color(0, 0, 0, 0));
        color.put("ALICEBLUE", new Color(240, 248, 255));
        color.put("ANTIQUEWHITE", new Color(250, 235, 215));
        color.put("AQUA", new Color(0, 255, 255));
        color.put("AQUAMARINE", new Color(127, 255, 212));
        color.put("AZURE", new Color(240, 255, 255));
        color.put("BEIGE", new Color(245, 245, 220));
        color.put("BISQUE", new Color(255, 228, 196));
        color.put("BLACK", new Color(0, 0, 0));
        color.put("BLANCHEDALMOND", new Color(255, 235, 205));
        color.put("BLUE", new Color(0, 0, 255));
        color.put("BLUEVIOLET", new Color(138, 43, 226));
        color.put("BROWN", new Color(165, 42, 42));
        color.put("BURLYWOOD", new Color(222, 184, 135));
        color.put("CADETBLUE", new Color(95, 158, 160));
        color.put("CHARTREUSE", new Color(127, 255, 0));
        color.put("CHOCOLATE", new Color(210, 105, 30));
        color.put("CORAL", new Color(255, 127, 80));
        color.put("CORNFLOWERBLUE", new Color(100, 149, 237));
        color.put("CORNSILK", new Color(255, 248, 220));
        color.put("CRIMSON", new Color(220, 20, 60));
        color.put("CYAN", new Color(0, 255, 255));
        color.put("DARKBLUE", new Color(0, 0, 139));
        color.put("DARKCYAN", new Color(0, 139, 139));
        color.put("DARKGOLDENROD", new Color(184, 134, 11));
        color.put("DARKGRAY", new Color(169, 169, 169));
        color.put("DARKGREEN", new Color(0, 100, 0));
        color.put("DARKGREY", new Color(169, 169, 169));
        color.put("DARKKHAKI", new Color(189, 183, 107));
        color.put("DARKMAGENTA", new Color(139, 0, 139));
        color.put("DARKOLIVEGREEN", new Color(85, 107, 47));
        color.put("DARKORANGE", new Color(255, 140, 0));
        color.put("DARKORCHID", new Color(153, 50, 204));
        color.put("DARKRED", new Color(139, 0, 0));
        color.put("DARKSALMON", new Color(233, 150, 122));
        color.put("DARKSEAGREEN", new Color(143, 188, 143));
        color.put("DARKSLATEBLUE", new Color(72, 61, 139));
        color.put("DARKSLATEGRAY", new Color(47, 79, 79));
        color.put("DARKSLATEGREY", new Color(47, 79, 79));
        color.put("DARKTURQUOISE", new Color(0, 206, 209));
        color.put("DARKVIOLET", new Color(148, 0, 211));
        color.put("DEEPPINK", new Color(255, 20, 147));
        color.put("DEEPSKYBLUE", new Color(0, 191, 255));
        color.put("DIMGRAY", new Color(105, 105, 105));
        color.put("DIMGREY", new Color(105, 105, 105));
        color.put("DODGERBLUE", new Color(30, 144, 255));
        color.put("FIREBRICK", new Color(178, 34, 34));
        color.put("FLORALWHITE", new Color(255, 250, 240));
        color.put("FORESTGREEN", new Color(34, 139, 34));
        color.put("FUCHSIA", new Color(255, 0, 255));
        color.put("GAINSBORO", new Color(220, 220, 220));
        color.put("GHOSTWHITE", new Color(248, 248, 255));
        color.put("GOLD", new Color(255, 215, 0));
        color.put("GOLDENROD", new Color(218, 165, 32));
        color.put("GRAY", new Color(128, 128, 128));
        color.put("GREEN", new Color(0, 128, 0));
        color.put("GREENYELLOW", new Color(173, 255, 47));
        color.put("GREY", new Color(128, 128, 128));
        color.put("HONEYDEW", new Color(240, 255, 240));
        color.put("HOTPINK", new Color(255, 105, 180));
        color.put("INDIANRED", new Color(205, 92, 92));
        color.put("INDIGO", new Color(75, 0, 130));
        color.put("IVORY", new Color(255, 255, 240));
        color.put("KHAKI", new Color(240, 230, 140));
        color.put("LAVENDER", new Color(230, 230, 250));
        color.put("LAVENDERBLUSH", new Color(255, 240, 245));
        color.put("LAWNGREEN", new Color(124, 252, 0));
        color.put("LEMONCHIFFON", new Color(255, 250, 205));
        color.put("LIGHTBLUE", new Color(173, 216, 230));
        color.put("LIGHTCORAL", new Color(240, 128, 128));
        color.put("LIGHTCYAN", new Color(224, 255, 255));
        color.put("LIGHTGOLDENRODYELLOW", new Color(250, 250, 210));
        color.put("LIGHTGRAY", new Color(211, 211, 211));
        color.put("LIGHTGREEN", new Color(144, 238, 144));
        color.put("LIGHTGREY", new Color(211, 211, 211));
        color.put("LIGHTPINK", new Color(255, 182, 193));
        color.put("LIGHTSALMON", new Color(255, 160, 122));
        color.put("LIGHTSEAGREEN", new Color(32, 178, 170));
        color.put("LIGHTSKYBLUE", new Color(135, 206, 250));
        color.put("LIGHTSLATEGRAY", new Color(119, 136, 153));
        color.put("LIGHTSLATEGREY", new Color(119, 136, 153));
        color.put("LIGHTSTEELBLUE", new Color(176, 196, 222));
        color.put("LIGHTYELLOW", new Color(255, 255, 224));
        color.put("LIME", new Color(0, 255, 0));
        color.put("LIMEGREEN", new Color(50, 205, 50));
        color.put("LINEN", new Color(250, 240, 230));
        color.put("MAGENTA", new Color(255, 0, 255));
        color.put("MAROON", new Color(128, 0, 0));
        color.put("MEDIUMAQUAMARINE", new Color(102, 205, 170));
        color.put("MEDIUMBLUE", new Color(0, 0, 205));
        color.put("MEDIUMORCHID", new Color(186, 85, 211));
        color.put("MEDIUMPURPLE", new Color(147, 112, 219));
        color.put("MEDIUMSEAGREEN", new Color(60, 179, 113));
        color.put("MEDIUMSLATEBLUE", new Color(123, 104, 238));
        color.put("MEDIUMSPRINGGREEN", new Color(0, 250, 154));
        color.put("MEDIUMTURQUOISE", new Color(72, 209, 204));
        color.put("MEDIUMVIOLETRED", new Color(199, 21, 133));
        color.put("MIDNIGHTBLUE", new Color(25, 25, 112));
        color.put("MINTCREAM", new Color(245, 255, 250));
        color.put("MISTYROSE", new Color(255, 228, 225));
        color.put("MOCCASIN", new Color(255, 228, 181));
        color.put("NAVAJOWHITE", new Color(255, 222, 173));
        color.put("NAVY", new Color(0, 0, 128));
        color.put("OLDLACE", new Color(253, 245, 230));
        color.put("OLIVE", new Color(128, 128, 0));
        color.put("OLIVEDRAB", new Color(107, 142, 35));
        color.put("ORANGE", new Color(255, 165, 0));
        color.put("ORANGERED", new Color(255, 69, 0));
        color.put("ORCHID", new Color(218, 112, 214));
        color.put("PALEGOLDENROD", new Color(238, 232, 170));
        color.put("PALEGREEN", new Color(152, 251, 152));
        color.put("PALETURQUOISE", new Color(175, 238, 238));
        color.put("PALEVIOLETRED", new Color(219, 112, 147));
        color.put("PAPAYAWHIP", new Color(255, 239, 213));
        color.put("PEACHPUFF", new Color(255, 218, 185));
        color.put("PERU", new Color(205, 133, 63));
        color.put("PINK", new Color(255, 192, 203));
        color.put("PLUM", new Color(221, 160, 221));
        color.put("POWDERBLUE", new Color(176, 224, 230));
        color.put("PURPLE", new Color(128, 0, 128));
        color.put("RED", new Color(255, 0, 0));
        color.put("ROSYBROWN", new Color(188, 143, 143));
        color.put("ROYALBLUE", new Color(65, 105, 225));
        color.put("SADDLEBROWN", new Color(139, 69, 19));
        color.put("SALMON", new Color(250, 128, 114));
        color.put("SANDYBROWN", new Color(244, 164, 96));
        color.put("SEAGREEN", new Color(46, 139, 87));
        color.put("SEASHELL", new Color(255, 245, 238));
        color.put("SIENNA", new Color(160, 82, 45));
        color.put("SILVER", new Color(192, 192, 192));
        color.put("SKYBLUE", new Color(135, 206, 235));
        color.put("SLATEBLUE", new Color(106, 90, 205));
        color.put("SLATEGRAY", new Color(112, 128, 144));
        color.put("SLATEGREY", new Color(112, 128, 144));
        color.put("SNOW", new Color(255, 250, 250));
        color.put("SPRINGGREEN", new Color(0, 255, 127));
        color.put("STEELBLUE", new Color(70, 130, 180));
        color.put("TAN", new Color(210, 180, 140));
        color.put("TEAL", new Color(0, 128, 128));
        color.put("THISTLE", new Color(216, 191, 216));
        color.put("TOMATO", new Color(255, 99, 71));
        color.put("TURQUOISE", new Color(64, 224, 208));
        color.put("VIOLET", new Color(238, 130, 238));
        color.put("WHEAT", new Color(245, 222, 179));
        color.put("WHITE", new Color(255, 255, 255));
        color.put("WHITESMOKE", new Color(245, 245, 245));
        color.put("YELLOW", new Color(255, 255, 0));
        color.put("YELLOWGREEN", new Color(154, 205, 50));

        // These non-standard colors are Waterloo defined
        color.put("AREAFILL", new Color(245, 222, 179, 200));
        color.put("TRANSPARENTWHITE", new Color(255, 255, 255, 200));

        // http://www.ncl.ucar.edu/Document/Graphics/ColorTables/BlueDarkOrange18.shtml
        blueDarkOrange18.put(0, new Color(0, 102, 102));
        blueDarkOrange18.put(1, new Color(0, 153, 153));
        blueDarkOrange18.put(2, new Color(0, 204, 204));
        blueDarkOrange18.put(3, new Color(0, 255, 255));
        blueDarkOrange18.put(4, new Color(51, 255, 255));
        blueDarkOrange18.put(5, new Color(101, 255, 255));
        blueDarkOrange18.put(6, new Color(153, 255, 255));
        blueDarkOrange18.put(7, new Color(178, 255, 255));
        blueDarkOrange18.put(8, new Color(203, 255, 255));
        blueDarkOrange18.put(9, new Color(229, 255, 255));
        blueDarkOrange18.put(10, new Color(255, 229, 203));
        blueDarkOrange18.put(11, new Color(255, 202, 153));
        blueDarkOrange18.put(12, new Color(255, 173, 101));
        blueDarkOrange18.put(13, new Color(255, 142, 51));
        blueDarkOrange18.put(14, new Color(255, 110, 0));
        blueDarkOrange18.put(15, new Color(204, 85, 0));
        blueDarkOrange18.put(16, new Color(153, 61, 0));
        blueDarkOrange18.put(17, new Color(102, 39, 0));

        blueDarkRed18.put(0, new Color(36, 0, 216));
        blueDarkRed18.put(1, new Color(24, 28, 247));
        blueDarkRed18.put(2, new Color(40, 87, 255));
        blueDarkRed18.put(3, new Color(61, 135, 255));
        blueDarkRed18.put(4, new Color(86, 176, 255));
        blueDarkRed18.put(5, new Color(117, 211, 255));
        blueDarkRed18.put(6, new Color(153, 234, 255));
        blueDarkRed18.put(7, new Color(188, 249, 255));
        blueDarkRed18.put(8, new Color(234, 255, 255));
        blueDarkRed18.put(9, new Color(255, 255, 234));
        blueDarkRed18.put(10, new Color(255, 241, 188));
        blueDarkRed18.put(11, new Color(255, 214, 153));
        blueDarkRed18.put(12, new Color(255, 172, 117));
        blueDarkRed18.put(13, new Color(255, 120, 86));
        blueDarkRed18.put(14, new Color(255, 61, 61));
        blueDarkRed18.put(15, new Color(247, 39, 53));
        blueDarkRed18.put(16, new Color(216, 21, 47));
        blueDarkRed18.put(17, new Color(165, 0, 33));
    }

    /**
     * Returns the standard web colors sorted by hue
     *
     * @return ArrayList<Color>
     */
    public static ArrayList<Color> sortByHue() {
        ArrayList<Color> c = new ArrayList<Color>(color.values());
        Collections.sort(c, HueSorter.getInstance());
        return c;
    }

    /**
     * Returns the standard web colors sorted by saturation
     *
     * @return ArrayList<Color>
     */
    public static ArrayList<Color> sortBySaturation() {
        ArrayList<Color> c = new ArrayList<Color>(color.values());
        Collections.sort(c, SaturationSorter.getInstance());
        return c;
    }

    /**
     * Returns the standard web colors sorted by lightness
     *
     * @return ArrayList<Color>
     */
    public static ArrayList<Color> sortByLightness() {
        ArrayList<Color> c = new ArrayList<Color>(color.values());
        Collections.sort(c, LightnessSorter.getInstance());
        return c;
    }

    /**
     * Returns the standard web colors sorted by luminance
     *
     * @return ArrayList<Color>
     */
    public static ArrayList<Color> sortByLuminance() {
        ArrayList<Color> c = new ArrayList<Color>(color.values());
        Collections.sort(c, LuminanceSorter.getInstance());
        return c;
    }

    /**
     *
     * Returns the standard web colors sorted alphabetically by name
     *
     * @return ArrayList<Color>
     */
    public static LinkedHashMap<String, java.awt.Color> getColors() {
        return color;
    }

    /**
     * Returns the color specified by a name.
     *
     * @param s name of the web color
     * @return an AWT Color
     */
    public static java.awt.Color getColor(String s) {
        return color.get(s.toUpperCase());
    }

    public static java.awt.Color[] getColor(String... s) {
        Color[] p = new Color[s.length];
        for (int k = 0; k < s.length; k++) {
            p[k] = color.get(s[k].toUpperCase());
        }
        return p;
    }

    /**
     * Returns the name associated with a color (or "" if the color is not
     * recognized).
     *
     * @param c AWT Color
     * @return name of the web color or "".
     */
    public static String getColor(Color c) {
        if (color.containsValue(c)) {
            for (Entry<String, Color> x : color.entrySet()) {
                if (x.getValue().equals(c)) {
                    return x.getKey();
                }
            }
        }
        return "UNKNOWN";
    }

    public static Color getColor(int i) {
        return defaultMap.get(Integer.valueOf(i));
    }

    public static Color getColor(Integer i) {
        return defaultMap.get(i);
    }

    /**
     * getLuminance returns the luminance as a weighted average of the RGB
     * components.
     *
     * @param color an AWT Color value
     * @return
     */
    public static float getLuminance(Color color) {
        return color.getRed() * 0.3f + color.getGreen() * 0.59f + color.getBlue() * 0.11f;
    }

    /**
     * getTheta returns the angle of the color in the color circle
     *
     * @param c an AWT Color value
     * @return
     */
    public static float getTheta(Color c) {
        float[] hsl = toHSL(c);
        return hsl[1] * 360f;
    }

    /**
     *
     * @param colors an AWT Color array
     * @return
     */
    public static float[] getLuminance(Color[] colors) {
        float[] lum = new float[colors.length];
        for (int k = 0; k < colors.length; k++) {
            lum[k] = getLuminance(colors[k]);
        }
        return lum;
    }

    private static double hue2RGB(double v1, double v2, double vH) {
        if (vH < 0d) {
            vH = vH + 1d;
        }
        if (vH > 1d) {
            vH = vH - 1d;
        }
        if (6d * vH < 1d) {
            return v1 + (v2 - v1) * 6d * vH;
        }
        if (2d * vH < 1d) {
            return v2;
        }
        if (3d * vH < 2d) {
            return v1 + (v2 - v1) * (2d / 3d - vH) * 6d;
        }
        return v1;

    }

    /**
     * Convert HSL to RGB
     *
     * @param h hue
     * @param s saturation
     * @param l luminance
     * @return an AWT Color value
     */
    public static Color HSLtoRGB(float h, float s, float l) {
        if (s == 0d) {
            return new java.awt.Color(l, l, l);
        } else {
            double var2;
            if (l < 0.5d) {
                var2 = l * (1 + s);
            } else {
                var2 = (l + s) - (s * l);
            }
            double var1 = 2d * l - var2;
            float r = (float) hue2RGB(var1, var2, h + 1d / 3d);
            float g = (float) hue2RGB(var1, var2, h);
            float b = (float) hue2RGB(var1, var2, h - 1d / 3d);
            return new java.awt.Color(r, g, b);
        }
    }

    /**
     * toHSL converts RGB to HSL Based on source at <a
     * href="http://www.easyrgb.com/index.php?X=MATH">EasyRGB</a>
     *
     * @param color an AWT Color value
     * @return HSL values as a float[]
     */
    public static float[] toHSL(Color color) {
        double r = color.getRed() / 255d;
        double g = color.getGreen() / 255d;
        double b = color.getBlue() / 255d;
        double mn = ArrayMath.min(new double[]{r, g, b});
        double mx = ArrayMath.max(new double[]{r, g, b});
        double delta = mx - mn;
        double l = (mx + mn) / 2d;
        double h = Double.NaN;
        double s;
        if (delta == 0d) {
            h = 0d;
            s = 0d;
        } else {
            if (l < 0.5d) {
                s = delta / (mx + mn);
            } else {
                s = delta / (2d - mx - mn);
            }
            double deltaR = (((mx - r) / 6d) + (delta / 2d)) / delta;
            double deltaG = (((mx - g) / 6d) + (delta / 2d)) / delta;
            double deltaB = (((mx - b) / 6d) + (delta / 2d)) / delta;
            if (r == mx) {// Fp equality ok
                h = deltaB - deltaG;
            } else if (g == mx) {
                h = (1 / 3d) + deltaR - deltaB;
            } else if (b == mx) {
                h = (2d / 3d) + deltaG - deltaR;
            }
            if (h < 0d) {
                h = h + 1;
            } else if (h > 1d) {
                h = h - 1;
            }
        }
        return new float[]{(float) h, (float) s, (float) l};
    }

    /**
     * Convert an AWT Color array to hsl values
     *
     * @param colors an AWT Color array
     * @return the hsl values as a float[][]
     */
    public static float[][] toHSL(Color[] colors) {
        float[][] hsl = new float[3][colors.length];
        for (int k = 0; k < colors.length; k++) {
            hsl[k] = toHSL(colors[k]);
        }
        return hsl;
    }

    /**
     * toXYZ converts RGB to XYZ Based on source at <a
     * href="http://www.easyrgb.com/index.php?X=MATH">EasyRGB</a>
     *
     * @param color
     * @return
     */
    public static float[] toXYZ(Color color) {
        double var_R = (color.getRed() / 255d);   //R from 0 to 255
        double var_G = (color.getGreen() / 255d);       //G from 0 to 255
        double var_B = (color.getBlue() / 255d);      //B from 0 to 255

        if (var_R > 0.04045d) {
            var_R = Math.pow(((var_R + 0.055d) / 1.055d), 2.4d);
        } else {
            var_R = var_R / 12.92d;
        }
        if (var_G > 0.04045d) {
            var_G = Math.pow(((var_G + 0.055d) / 1.055d), 2.4d);
        } else {
            var_G = var_G / 12.92d;
        }
        if (var_B > 0.04045d) {
            var_B = Math.pow(((var_B + 0.055d) / 1.055d), 2.4d);
        } else {
            var_B = var_B / 12.92d;
        }

        var_R = var_R * 100d;
        var_G = var_G * 100d;
        var_B = var_B * 100d;

        //Observer. = 2 degrees, Illuminant = D65
        float X = (float) (var_R * 0.4124d + var_G * 0.3576d + var_B * 0.1805d);
        float Y = (float) (var_R * 0.2126d + var_G * 0.7152d + var_B * 0.0722d);
        float Z = (float) (var_R * 0.0193d + var_G * 0.1192d + var_B * 0.9505d);
        return new float[]{X, Y, Z};
    }

    /**
     * toXYZ converts RGB Color array to XYZ Based on source at
     * http://www.easyrgb.com/index.php?X=MATH&H=18#text18
     *
     * @param colors an AWT Color array
     * @return the XYZ color values as a float[][]
     */
    public static float[][] toXYZ(Color[] colors) {
        float[][] xyz = new float[3][colors.length];
        for (int k = 0; k < colors.length; k++) {
            xyz[k] = toXYZ(colors[k]);
        }
        return xyz;
    }

    /**
     * toLab converts RGB to CIELab Based on source at <a
     * href="http://www.easyrgb.com/index.php?X=MATH">EasyRGB</a>
     *
     * @param color an AWT Color value
     * @return CIELab values as float[]
     */
    public static float[] toLab(Color color) {
        float[] xyzcolor = Colors.toXYZ(color);

        //   Observer= 2 degrees, Illuminant= D65
        double var_X = xyzcolor[0] / ref_X;
        double var_Y = xyzcolor[1] / ref_Y;
        double var_Z = xyzcolor[2] / ref_Z;

        if (var_X > 0.008856) {
            var_X = Math.pow(var_X, 1 / 3d);
        } else {
            var_X = (7.787 * var_X) + (16 / 116);
        }
        if (var_Y > 0.008856) {
            var_Y = Math.pow(var_Y, 1 / 3d);
        } else {
            var_Y = (7.787 * var_Y) + (16 / 116);
        }
        if (var_Z > 0.008856) {
            var_Z = Math.pow(var_Z, 1 / 3d);
        } else {
            var_Z = (7.787 * var_Z) + (16 / 116);
        }

        float CIEL = (float) (116 * var_Y - 16d);
        float CIEa = (float) (500 * (var_X - var_Y));
        float CIEb = (float) (200 * (var_Y - var_Z));
        return new float[]{CIEL, CIEa, CIEb};
    }

    /**
     * toLab converts RGB Color[] to CIELab Based on source at <a
     * href="http://www.easyrgb.com/index.php?X=MATH">EasyRGB</a>
     *
     * @param colors AWT Color array
     * @return CIELab values as float[][]
     */
    public static float[][] toLab(Color[] colors) {
        float[][] lab = new float[3][colors.length];
        for (int k = 0; k < colors.length; k++) {
            lab[k] = toLab(colors[k]);
        }
        return lab;
    }

    /**
     * Converts CIELab to XYZ Based on source at <a
     * href="http://www.easyrgb.com/index.php?X=MATH">EasyRGB</a>
     *
     * @param xyz XYZ values
     * @return CIELab values
     */
    public static float[] labToXYZ(float[] xyz) {
        double var_Y = (xyz[0] + 16d) / 116d;
        double var_X = xyz[1] / 500d + var_Y;
        double var_Z = var_Y - xyz[2] / 200d;

        if (Math.pow(var_Y, 3) > 0.008856) {
            var_Y = Math.pow(var_Y, 3);
        } else {
            var_Y = (var_Y - 16 / 116) / 7.787;
        }

        if (Math.pow(var_X, 3) > 0.008856) {
            var_X = Math.pow(var_X, 3);
        } else {
            var_X = (var_X - 16 / 116) / 7.787;

        }
        if (Math.pow(var_Z, 3) > 0.008856) {
            var_Z = Math.pow(var_Z, 3);
        } else {
            var_Z = (var_Z - 16 / 116) / 7.787;
        }
        //     Observer= 2 degrees, Illuminant= D65
        float X = (float) (ref_X * var_X);
        float Y = (float) (ref_Y * var_Y);
        float Z = (float) (ref_Z * var_Z);
        return new float[]{X, Y, Z};
    }

    /**
     * Convert RGB to XYZ.
     *
     * @param xyz XYZ values
     * @return RGB values
     */
    public static Color XYZToRGB(float[] xyz) {

        // Observer = 2 degrees, Illuminant = D65
        double var_X = xyz[0] / 100;
        double var_Y = xyz[1] / 100;
        double var_Z = xyz[2] / 100;

        double var_R = var_X * 3.2406d + var_Y * -1.5372d + var_Z * -0.4986d;
        double var_G = var_X * -0.9689d + var_Y * 1.8758d + var_Z * 0.0415d;
        double var_B = var_X * 0.0557d + var_Y * -0.2040d + var_Z * 1.0570d;

        if (var_R > 0.0031308d) {
            var_R = 1.055d * Math.pow(var_R, 1d / 2.4d) - 0.055d;
        } else {
            var_R = 12.92d * var_R;
        }

        if (var_G > 0.0031308d) {
            var_G = 1.055d * Math.pow(var_G, 1d / 2.4d) - 0.055d;

        } else {
            var_G = 12.92 * var_G;

        }

        if (var_B > 0.0031308d) {
            var_B = 1.055d * Math.pow(var_B, 1d / 2.4d) - 0.055d;

        } else {
            var_B = 12.92d * var_B;

        }
        float R = (float) var_R;
        float G = (float) var_G;
        float B = (float) var_B;
        return new Color(R, G, B);
    }

    /**
     *
     * @param lab CIELab array
     * @return an AWT Color.
     */
    public static Color labToRGB(float[] lab) {
        return XYZToRGB(labToXYZ(lab));
    }

    /**
     * getArcSeries generates a set of colors of different hue.
     *
     * Example: colors=getArcSeries(color, n, theta)
     *
     * where color is the central color of a set of n colors that subtend an arc
     * of +/-(theta/2) in the color circle. Theta is specified in degrees and
     * should be 0 to 360.
     *
     * @param color
     * @param n
     * @param theta
     * @return
     */
    public static Color[] getArcSeries(Color color, int n, float theta) {
        float[] hsl = toHSL(color);
        theta = theta / (2 * 360);
        double[] s = new double[1];
        if (n > 1) {
            s = ArrayMath.linspace(hsl[0] - theta, hsl[0] + theta, n);
        } else {
            s[0] = hsl[0] + theta;
        }
        Color[] colors = new Color[s.length];
        for (int k = 0; k < s.length; k++) {
            colors[k] = HSLtoRGB((float) s[k], hsl[1], hsl[2]);
        }
        return colors;
    }

    /**
     * getComplement returns the complement of a color (rotated 180 degrees on
     * the color circle).
     *
     * Example:
     *
     * color=getComplement(color);
     *
     * @param color the reference color
     * @return the complement of the reference color
     */
    public static Color getComplement(Color color) {
        float[] hsl = toHSL(color);
        float s = hsl[0] - 0.5f;
        return HSLtoRGB(s, hsl[1], hsl[2]);
    }

    /**
     * getSplitComplements returns the 2 split complements of the input i.e. the
     * colors at +/- 150 degrees. Example: color=getSplitComplements(color);
     *
     * @param color an AWT Color
     * @return
     */
    public static Color[] getSplitComplements(Color color) {
        return getArcSeries(color, 2, 300);
    }

    /**
     * getTriads returns the two triadic complements of the input colors. i.e.
     * the colors at +/- 120 degrees.
     *
     * Example:
     *
     * color=getTriads(color);
     *
     * @param color an AWT Color
     * @return
     */
    public static Color[] getTriads(Color color) {
        return getArcSeries(color, 2, 240);
    }

    /**
     * getAnalagous returns neigbouring colors i.e. the colors at +/- 30
     * degrees.
     *
     * Example:
     *
     * color=getAnalagous(color);
     *
     * @param color an AWT Color
     * @return
     */
    public static Color[] getAnalagous(Color color) {
        return getArcSeries(color, 2, 60);
    }

    /**
     * getMonochrome returns a monochrome series by varying the lightness of the
     * reference color.
     *
     * Example:
     *
     * colors=getMonochrome(color, n)
     *
     * @param color an AWT Color
     * @param n
     * @return
     */
    public static Color[] getMonochromeSeries(Color color, int n) {
        return getMonochromeSeries(color, n, 1);
    }

    /**
     * getMonochrome returns a monochrome series by varying the lightness of the
     * reference color.
     *
     * Example:
     *
     * colors=getMonochrome(color, n, llim)
     *
     * where color on input is the starting color and n=number of colors to
     * return. Terminate the series at higher lightness by specifying llim on
     * input where llim/2=proportion of the reference color lightness to use.
     *
     * @param color an AWT Color
     * @param n
     * @param llim
     * @return
     */
    public static Color[] getMonochromeSeries(Color color, int n, double llim) {
        float[] hsl = toHSL(color);
        double[] l = ArrayMath.linspace(hsl[2] + (llim * hsl[2]), hsl[2] - (llim * hsl[2]), n);
        Color[] out = new Color[l.length];
        for (int k = 0; k < l.length; k++) {
            out[k] = HSLtoRGB(hsl[0], hsl[1], (float) l[k]);
        }
        return out;
    }

    /**
     *
     * @param color an AWT Color
     * @param n
     * @return
     */
    public static Color[] getSatSeries(Color color, int n) {
        float[] hsl = toHSL(color);
        double[] s = ArrayMath.linspace(0, 1, n);
        Color[] out = new Color[s.length];
        for (int k = 0; k < s.length; k++) {
            out[k] = HSLtoRGB(hsl[0], (float) s[k], hsl[2]);
        }
        return out;
    }

    /**
     *
     * getLightnessSeries generates a set of colors of different lightness.
     *
     * Example:
     *
     * colors=getLightnessSeries(color, n)
     *
     * where the output is a set of n colors whose lightness ranges from 0 to 1
     * but which have the same hue and saturation as the input with the
     * reference color at the centre.
     *
     * @param color
     * @param n
     * @return
     */
    public static Color[] getLightnessSeries(Color color, int n) {
        float[] hsl = toHSL(color);
        double[] l = ArrayMath.linspace(0, 1, n);
        Color[] out = new Color[l.length];
        for (int k = 0; k < l.length; k++) {
            out[k] = HSLtoRGB(hsl[0], hsl[1], (float) l[k]);
        }
        return out;
    }

    /**
     * Returns a Color[] in reverse order to the input.
     *
     * @param in an AWT Color[] to reverse
     * @return the reversed AWT Color[]
     */
    public static Color[] reverse(Color[] in) {
        Color[] out = new Color[in.length];
        int n = 0;
        for (int k = in.length - 1; k >= 0; k--) {
            out[n++] = in[k];
        }
        return out;
    }

    /**
     * @return the defaultMap
     */
    public static LinkedHashMap<Integer, java.awt.Color> getDefaultMap() {
        return defaultMap;
    }

    /**
     * @param aDefaultMap the defaultMap to set
     */
    public static void setDefaultMap(LinkedHashMap<Integer, java.awt.Color> aDefaultMap) {
        defaultMap = aDefaultMap;
    }

    /**
     * Sorts HSL color sequence by hue.
     */
    private static final class HueSorter implements Comparator<Color> {

        private static final Colors.HueSorter instance = new Colors.HueSorter();

        private HueSorter() {
        }

        private static Colors.HueSorter getInstance() {
            return instance;
        }

        @Override
        public final int compare(Color c0, Color c1) {
            if (Colors.toHSL(c0)[0] > Colors.toHSL(c1)[0]) {
                return 1;
            } else if (Colors.toHSL(c0)[0] < Colors.toHSL(c1)[0]) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Sorts HSL color sequence by saturation.
     */
    private static final class SaturationSorter implements Comparator<Color> {

        private static Colors.SaturationSorter instance = new Colors.SaturationSorter();

        private SaturationSorter() {
        }

        private static Colors.SaturationSorter getInstance() {
            return instance;
        }

        @Override
        public final int compare(Color c0, Color c1) {
            if (Colors.toHSL(c0)[1] > Colors.toHSL(c1)[1]) {
                return 1;
            } else if (Colors.toHSL(c0)[1] < Colors.toHSL(c1)[1]) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Sorts HSL color sequence by lightness.
     */
    private static final class LightnessSorter implements Comparator<Color> {

        private static final Colors.LightnessSorter instance = new Colors.LightnessSorter();

        private LightnessSorter() {
        }

        private static Colors.LightnessSorter getInstance() {
            return instance;
        }

        @Override
        public final int compare(Color c0, Color c1) {
            if (Colors.toHSL(c0)[2] > Colors.toHSL(c1)[2]) {
                return 1;
            } else if (Colors.toHSL(c0)[2] < Colors.toHSL(c1)[2]) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Sorts RGB color sequence by luminance.
     */
    private static final class LuminanceSorter implements Comparator<Color> {

        private static final Colors.LuminanceSorter instance = new Colors.LuminanceSorter();

        private LuminanceSorter() {
        }

        private static Colors.LuminanceSorter getInstance() {
            return instance;
        }

        @Override
        public final int compare(Color c0, Color c1) {
            if (Colors.getLuminance(c0) > Colors.getLuminance(c1)) {
                return 1;
            } else if (Colors.getLuminance(c0) < Colors.getLuminance(c1)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
