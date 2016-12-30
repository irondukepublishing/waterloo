/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.data;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class PrimitiveDoubleVectorTest {

    public PrimitiveDoubleVectorTest() {
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

    @Test
    public void testConstructor() {
        System.out.println("PrimitiveDoubleVector(int)");
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer((int) 10);
        assertEquals(instance.getDimension(), 10);
    }

    /**
     * Test of getData method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setData(expResult);
        double[] result = instance.getData();
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of setData method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        double[] arr = new double[]{0, 1, 2, 3, 4, 5};
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer();
        instance.setData(arr);
        assertFalse(instance.getData().equals(arr));
        assertTrue(Arrays.equals(instance.getData(), arr));
    }

    /**
     * Test of getDataRef method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testGetDataRef() {
        System.out.println("getDataRef");
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.data = expResult;
        double[] result = instance.getDataRef();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDataRef method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testSetDataRef() {
        System.out.println("setDataRef");
        double[] arr = null;
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer();
        instance.setDataRef(arr);
        assertEquals(arr, instance.getDataRef());
    }

    /**
     * Test of getEntry method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        int index = 0;
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer(new double[]{0d});
        double expResult = 0.0;
        double result = instance.getEntry(index);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setEntry method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testSetEntry() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer(new double[]{Double.POSITIVE_INFINITY});
        instance.setEntry(index, val);
        assertEquals(val, instance.getData()[0], 0.0);
    }

    /**
     * Test of getDimension method, of class PrimitiveDoubleBuffer.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        PrimitiveDoubleBuffer instance = new PrimitiveDoubleBuffer(new double[]{0, 1, 2});
        int expResult = 3;
        int result = instance.getDimension();
        assertEquals(expResult, result);
    }
}
