/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.math;

import kcl.waterloo.math.geom.Matrix;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class MatrixTest {

    public MatrixTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toDouble method, of class Matrix.
     */
    @Test
    public void testToDouble() {
        System.out.println("toDouble");
        double[][] in = {{1d, 2d, 3d}, {1d, 2d, 3d}};
        Double[][] expResult = {{new Double(1d), new Double(2d), new Double(3d)},
        {new Double(1d), new Double(2d), new Double(3d)}};
        Double[][] result = Matrix.toDouble(in);
        assertEquals(expResult, result);
    }
}
