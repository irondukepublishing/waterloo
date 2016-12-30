/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.plots2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import kcl.waterloo.plotmodel2D.GJCyclicArrayList;

/**
 *
 * @author malcolm
 */
public class FontSupport {

    private Font font = null;
    private GJCyclicArrayList<Paint> fontBackground = new GJCyclicArrayList<Paint>();
    private GJCyclicArrayList<Paint> fontForeground = new GJCyclicArrayList<Paint>(Color.BLACK);

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the fontForeground
     */
    public GJCyclicArrayList<Paint> getFontForeground() {
        return fontForeground;
    }

    public Paint getFontForeground(int index) {
        return fontForeground.get(index);
    }

    public void setFontForeground(Paint fontForeground) {
        this.fontForeground.clear();
        this.fontForeground.add(fontForeground);
    }

    /**
     * @param fontForeground the fontForeground to set
     */
    public void setFontForeground(GJCyclicArrayList<Paint> fontForeground) {
        this.fontForeground = fontForeground;
    }

    public void setFontForeground(Paint[] fontBackground) {
        this.fontForeground = new GJCyclicArrayList<Paint>(fontBackground);
    }

    /**
     * @return the fontBackground
     */
    public GJCyclicArrayList<Paint> getFontBackground() {
        return fontBackground;
    }

    public Paint getFontBackground(int index) {
        return fontBackground.get(index);
    }

    /**
     * @param fontBackground the fontBackground to set
     */
    public void setFontBackground(GJCyclicArrayList<Paint> fontBackground) {
        this.fontBackground = fontBackground;
    }

    public void setFontBackground(Paint fontBackground) {
        this.fontBackground.clear();
        this.fontBackground.add(fontBackground);
    }

    public void setFontBackground(Paint[] fontBackground) {
        this.fontBackground = new GJCyclicArrayList<Paint>(fontBackground);
    }
}
