/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.math.geom;

import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class CoordinatesTest {

    public CoordinatesTest() {
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
     * Test of cartesianToPolar method, of class Coordinates.
     */
    @Test
    public void testCartesianToPolar_doubleArr_doubleArr() {
        System.out.println("polarToCartesian -> cartesianToPolar");
        double[] theta = {-Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI};
        double[] radius = {0.1d, 1d, 77d, 110d, 1d};
        Cartesian cart = Coordinates.polarToCartesian(theta, radius);
        Polar result = Coordinates.cartesianToPolar(cart);
        assertTrue(
                Arrays.equals(result.getTheta(), theta)
                && Arrays.equals(result.getRadius(), radius));
    }
}
