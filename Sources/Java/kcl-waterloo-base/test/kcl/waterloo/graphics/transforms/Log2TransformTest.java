/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.transforms;

import java.util.ArrayList;
import java.util.Arrays;
import kcl.waterloo.graphics.GJGraphInterface;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Log2TransformTest {

    public Log2TransformTest() {
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
     * Test of getInstance method, of class Log10Transform.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        assertEquals(Log2Transform.getInstance(), Log2Transform.getInstance());
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_double() {
        System.out.println("getData");
        assertTrue(Log2Transform.getInstance().getData(4) == 2d);
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_doubleArr() {
        System.out.println("getData");
        double[] arr = new double[]{1d, 2d, 4d, 8d};
        Log2Transform instance = Log2Transform.getInstance();
        double[] expResult = new double[]{0d, 1d, 2d, 3d};
        double[] result = instance.getData(arr);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of getData method, of class Log2Transform.
     */
    @Test
    public void testGetInverse() {
        System.out.println("getInverse");
        double val = 2d;
        Log2Transform instance = Log2Transform.getInstance();
        assertTrue(instance.getInverse(instance.getData(val)) == val);
    }
}
