/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.math;

import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class VectorTest {

    public VectorTest() {
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
     * Test of abs method, of class ArrayMath.
     */
    @Test
    public void testAbs() {
        System.out.println("abs");
        double[] in = {0d, -1d, Double.NEGATIVE_INFINITY};
        double[] expResult = {0d, 1d, Double.POSITIVE_INFINITY};
        double[] result = ArrayMath.abs(in);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of absi method, of class ArrayMath.
     */
    @Test
    public void testAbsi() {
        System.out.println("absi");
        double[] in = {0d, -1d, Double.NEGATIVE_INFINITY};
        double[] expResult = {0d, 1d, Double.POSITIVE_INFINITY};
        double[] result = ArrayMath.absi(in);
        assertTrue(Arrays.equals(expResult, result) && in.equals(result));
    }

    /**
     * Test of neg method, of class ArrayMath.
     */
    @Test
    public void testNeg() {
        System.out.println("neg");
        double[] in = {0d, -1d, Double.NEGATIVE_INFINITY, 22d};
        double[] expResult = {-0d, 1d, Double.POSITIVE_INFINITY, -22d};
        double[] result = ArrayMath.neg(in);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of negi method, of class ArrayMath.
     */
    @Test
    public void testNegi() {
        System.out.println("negi");
        double[] in = {0d, -1d, Double.NEGATIVE_INFINITY, 22d};
        double[] expResult = {-0d, 1d, Double.POSITIVE_INFINITY, -22d};
        double[] result = ArrayMath.negi(in);
        assertTrue(Arrays.equals(expResult, result) && in.equals(result));
    }

    /**
     * Test of diff method, of class ArrayMath.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        double[] in = {1d, 20d, 1d, Double.NaN, 1d, Double.POSITIVE_INFINITY, 1d};
        double[] expResult = {19d, -19d, Double.NaN, Double.NaN,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        double[] result = ArrayMath.diff(in);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of diffi method, of class ArrayMath.
     */
    @Test
    public void testDiffi() {
        System.out.println("diffi");
        double[] in = {1d, 20d, 1d, Double.NaN, 1d, Double.POSITIVE_INFINITY, 1d};
        double[] expResult = {19d, -19d, Double.NaN, Double.NaN,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN};
        double[] result = ArrayMath.diffi(in);
        assertTrue(Arrays.equals(expResult, result) && in.equals(result));
    }

    /**
     * Test of fill method, of class ArrayMath.
     */
    @Test
    public void testFill() {
        System.out.println("fill");
        int len = 5;
        int start = 10;
        int inc = 10;
        double[] expResult = {10d, 20d, 30d, 40d, 50d};
        double[] result = ArrayMath.fill(len, start, inc);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of div method, of class ArrayMath.
     */
    @Test
    public void testDiv_doubleArr_doubleArr() {
        System.out.println("div");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {1d, 1d, 1d};
        double[] result = ArrayMath.div(in1, in2);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of divi method, of class ArrayMath.
     */
    @Test
    public void testDivi_doubleArr_doubleArr() {
        System.out.println("divi");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {1d, 1d, 1d};
        double[] result = ArrayMath.divi(in1, in2);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of div method, of class ArrayMath.
     */
    @Test
    public void testDiv_doubleArr_double() {
        System.out.println("div");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {1d, 2d, 3d};
        double[] result = ArrayMath.div(in1, 10d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of divi method, of class ArrayMath.
     */
    @Test
    public void testDivi_doubleArr_double() {
        System.out.println("divi");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {1d, 2d, 3d};
        double[] result = ArrayMath.divi(in1, 10d);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of mul method, of class ArrayMath.
     */
    @Test
    public void testMul_doubleArr_doubleArr() {
        System.out.println("mul");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {100d, 400d, 900d};
        double[] result = ArrayMath.mul(in1, in2);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of muli method, of class ArrayMath.
     */
    @Test
    public void testMuli_doubleArr_doubleArr() {
        System.out.println("muli");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {100d, 400d, 900d};
        double[] result = ArrayMath.muli(in1, in2);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of mul method, of class ArrayMath.
     */
    @Test
    public void testMul_doubleArr_double() {
        System.out.println("mul");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {100d, 200d, 300d};
        double[] result = ArrayMath.mul(in1, 10d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of muli method, of class ArrayMath.
     */
    @Test
    public void testMuli_doubleArr_double() {
        System.out.println("muli");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {100d, 200d, 300d};
        double[] result = ArrayMath.muli(in1, 10d);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of add method, of class ArrayMath.
     */
    @Test
    public void testAdd_doubleArr_doubleArr() {
        System.out.println("add");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {20d, 40d, 60d};
        double[] result = ArrayMath.add(in1, in2);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of addi method, of class ArrayMath.
     */
    @Test
    public void testAddi_doubleArr_doubleArr() {
        System.out.println("addi");
        double[] in1 = {10d, 20d, 30d};
        double[] in2 = {10d, 20d, 30d};
        double[] expResult = {20d, 40d, 60d};
        double[] result = ArrayMath.addi(in1, in2);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of add method, of class ArrayMath.
     */
    @Test
    public void testAdd_doubleArr_double() {
        System.out.println("add");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {20d, 30d, 40d};
        double[] result = ArrayMath.add(in1, 10d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of addi method, of class ArrayMath.
     */
    @Test
    public void testAddi_doubleArr_double() {
        System.out.println("addi");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {20d, 30d, 40d};
        double[] result = ArrayMath.addi(in1, 10d);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of sub method, of class ArrayMath.
     */
    @Test
    public void testSub_doubleArr_doubleArr() {
        System.out.println("sub");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {0d, 0d, 0d};
        double[] result = ArrayMath.sub(in1, in1);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of subi method, of class ArrayMath.
     */
    @Test
    public void testSubi_doubleArr_doubleArr() {
        System.out.println("subi");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {0d, 0d, 0d};
        double[] result = ArrayMath.subi(in1, in1);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of sub method, of class ArrayMath.
     */
    @Test
    public void testSub_doubleArr_double() {
        System.out.println("subi");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {0d, 10d, 20d};
        double[] result = ArrayMath.sub(in1, 10d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of subi method, of class ArrayMath.
     */
    @Test
    public void testSubi_doubleArr_double() {
        System.out.println("subi");
        double[] in1 = {10d, 20d, 30d};
        double[] expResult = {0d, 10d, 20d};
        double[] result = ArrayMath.subi(in1, 10d);
        assertTrue(Arrays.equals(expResult, result) && in1.equals(result));
    }

    /**
     * Test of subi method, of class ArrayMath.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        double[] in1 = {2d, 3d, 4d};
        double result = ArrayMath.dot(in1, in1);
        assertTrue(result == 4d + 9d + 16d);
    }

    /**
     * Test of isZero method, of class ArrayMath.
     */
    @Test
    public void testIsZero() {
        System.out.println("isZero");
        double[] in1 = {0d, -0d, 30d};
        boolean[] expResult = {true, true, false};
        boolean[] result = ArrayMath.isZero(in1);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isEqual method, of class ArrayMath.
     */
    @Test
    public void testIsEqual() {
        System.out.println("isEqual");
        double[] in = {0d, -7d, Double.MIN_VALUE};
        boolean[] expResult = {true, false, false};
        boolean[] result = ArrayMath.isEqual(in, 0d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isLT method, of class ArrayMath.
     */
    @Test
    public void testIsLT() {
        System.out.println("isLT");
        double[] in = {0d, -7d, Double.MIN_VALUE};
        boolean[] expResult = {false, true, false};
        boolean[] result = ArrayMath.isLT(in, 0d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isLE method, of class ArrayMath.
     */
    @Test
    public void testIsLE() {
        System.out.println("isLE");
        double[] in = {0d, -7d, Double.MIN_VALUE};
        boolean[] expResult = {true, true, false};
        boolean[] result = ArrayMath.isLE(in, 0d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isGT method, of class ArrayMath.
     */
    @Test
    public void testIsGT() {
        System.out.println("isGT");
        double[] in = {0d, -7d, Double.MIN_VALUE};
        boolean[] expResult = {false, false, true};
        boolean[] result = ArrayMath.isGT(in, 0d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isGE method, of class ArrayMath.
     */
    @Test
    public void testIsGE() {
        System.out.println("isGE");
        double[] in = {0d, -7d, Double.MIN_VALUE};
        boolean[] expResult = {true, false, true};
        boolean[] result = ArrayMath.isGE(in, 0d);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isNaN method, of class ArrayMath.
     */
    @Test
    public void testIsNaN() {
        System.out.println("isNaN");
        double[] in = {0d, -7d, Double.NaN};
        boolean[] expResult = {false, false, true};
        boolean[] result = ArrayMath.isNaN(in);
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of isInfinite method, of class ArrayMath.
     */
    @Test
    public void testIsInfinite() {
        System.out.println("isInfinite");
        double[] in = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN};
        boolean[] expResult = {true, true, false};
        boolean[] result = ArrayMath.isInfinite(in);
        assertTrue(Arrays.equals(expResult, result));
    }
}
