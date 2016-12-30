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
public class NOPTransformTest {

    public NOPTransformTest() {
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
        assertEquals(NOPTransform.getInstance(), NOPTransform.getInstance());
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_double() {
        System.out.println("getData");
        assertTrue(NOPTransform.getInstance().getData(1d) == 1d);
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_doubleArr() {
        System.out.println("getInverse");
        double[] arr = new double[]{1d, 2d, 3d, 4d};
        NOPTransform instance = NOPTransform.getInstance();
        double[] expResult = new double[]{1d, 2d, 3d, 4d};
        double[] result = instance.getData(arr);
        assertTrue(Arrays.equals(expResult, result));
    }
}
