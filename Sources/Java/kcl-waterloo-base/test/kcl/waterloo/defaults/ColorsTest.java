/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.defaults;

import java.awt.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ML
 */
public class ColorsTest {

    public ColorsTest() {
    }

    /**
     * Test of toHSL method, of class Colors.
     */
    @Test
    public void testHSL_RGB() {
        float[] hsl;
        for (String s : Colors.getColors().keySet()) {
            System.out.println(s);
            Color thisColor = Colors.getColor(s);
            hsl = Colors.toHSL(thisColor);
            Color returnedColor = Colors.HSLtoRGB(hsl[0], hsl[1], hsl[2]);
            assertTrue(thisColor.getRed() == returnedColor.getRed()
                    && thisColor.getBlue() == returnedColor.getBlue()
                    && thisColor.getGreen() == returnedColor.getGreen());

        }
    }
}
