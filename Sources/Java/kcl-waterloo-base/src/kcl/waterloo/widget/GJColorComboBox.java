 /* 
 * This code is part of Project Waterloo from King's College London
 * <http://waterloo.sourceforge.net/>
 *
 * Copyright King's College London 2011-
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
package kcl.waterloo.widget;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Malcolm Lidierth
 */
public class GJColorComboBox extends JComboBox {

    Map<String, Color> map;

    public GJColorComboBox() {
        this.setRenderer(new Renderer());
    }

    public GJColorComboBox(Map<String, Color> colormap) {
        this(colormap, null);
    }

    public GJColorComboBox(Map<String, Color> colormap, Color color) {
        this.setRenderer(new Renderer());
        setMap(colormap, color);
    }

    public final void setMap(Map<String, Color> colormap) {
        setMap(colormap, null);
    }

    public final void setMap(Map<String, Color> colormap, Color color) {
        map = colormap;
        for (String s : colormap.keySet()) {
            JLabel text = new JLabel(s);
            addItem(text);
        }
        if (color != null) {
            setSelectedColor(color);
        }
    }

    public void setSelectedColor(Color color) {
        if (map.containsValue(color)) {
            Color[] colors = new Color[map.size()];
            colors = map.values().toArray(colors);
            for (int k = 0; k < colors.length; k++) {
                if (colors[k].equals(color)) {
                    setSelectedIndex(k);
                    return;
                }
            }
        } else {
            String s = "[R=" + color.getRed() + ",G="
                    + color.getGreen() + ",B="
                    + color.getBlue() + ",\u03B1="
                    + color.getAlpha() + "]";
            map.put(s, color);
            JLabel text = new JLabel(s);
            addItem(text);
            setSelectedItem(text);
        }
    }

    public String getSelectedColorDescription() {
        return ((JLabel) getSelectedItem()).getText();
    }

    public Color getSelectedColor() {
        return map.get(((JLabel) getSelectedItem()).getText());
    }

    private class Renderer extends JLabel implements ListCellRenderer {

        public Renderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Color color = null;
            if (map != null) {
                color = map.get(((JLabel)value).getText());
            }
            if (color == null) {
                color = Color.WHITE;
            }
            if (value != null) {
                setText(((JLabel)value).getText());
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                if (hsb[2] > 0.5) {
                    hsb[2] = hsb[2] - 0.5f;
                } else {
                    hsb[2] = hsb[2] + 0.5f;
                }
                setForeground(isSelected ? color : Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
                setBackground(isSelected ? Color.getHSBColor(hsb[0], hsb[1], hsb[2]) : color);
            }
            return this;
        }

    }
}
