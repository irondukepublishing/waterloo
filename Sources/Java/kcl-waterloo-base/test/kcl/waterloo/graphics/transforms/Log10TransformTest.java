/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.transforms;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class Log10TransformTest {

    public Log10TransformTest() {
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
        assertEquals(Log10Transform.getInstance(), Log10Transform.getInstance());
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_double() {
        System.out.println("getData");
        assertTrue(Log10Transform.getInstance().getData(10) == 1d);
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetData_doubleArr() {
        System.out.println("getData");
        double[] arr = new double[]{1d, 10d, 100d, 1000d};
        Log10Transform instance = Log10Transform.getInstance();
        double[] expResult = new double[]{0d, 1d, 2d, 3d};
        double[] result = instance.getData(arr);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of getData method, of class Log10Transform.
     */
    @Test
    public void testGetInverse() {
        System.out.println("getInverse");
        double val = 10d;
        Log10Transform instance = Log10Transform.getInstance();
        assertTrue(instance.getInverse(instance.getData(val)) == val);
    }
}
