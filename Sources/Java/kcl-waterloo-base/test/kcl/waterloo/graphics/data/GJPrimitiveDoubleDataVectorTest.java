/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.data;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import kcl.waterloo.graphics.transforms.NOPTransform;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class GJPrimitiveDoubleDataVectorTest {

    public GJPrimitiveDoubleDataVectorTest() {
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
     * Test of setDataBufferData method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_doubleArr() {
        System.out.println("setDataBufferData");
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        instance.setDataBufferData(expResult);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == expResult[0]);
    }

    /**
     * Test of setDataBufferData method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_intArr() {
        System.out.println("setDataBufferData");
        int[] v = {0, 1, 2, 3, 4, 5};
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == 0d);
    }

    /**
     * Test of setDataBufferData method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_AbstractList() {
        System.out.println("setDataBufferData");
        AbstractList<Double> v = new ArrayList<Double>();
        v.add(Double.POSITIVE_INFINITY);
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == Double.POSITIVE_INFINITY);
    }

    /**
     * Test of setDataBuffer method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testSetDataBuffer() {
        System.out.println("setDataBuffer");
        PrimitiveDoubleBuffer v = new PrimitiveDoubleBuffer();
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        instance.setDataBuffer(v);
        assertEquals(instance.getDataBuffer(), v);
    }

    /**
     * Test of getDataBuffer method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testGetDataBuffer() {
        System.out.println("getDataBuffer");
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        PrimitiveDoubleBuffer expResult = new PrimitiveDoubleBuffer();
        instance.setDataBuffer(expResult);
        PrimitiveDoubleBuffer result = instance.getDataBuffer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDataValues method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testGetDataValues() {
        System.out.println("getDataValues");
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        // Check we get back a copy
        assertTrue(!instance.getDataValues(NOPTransform.getInstance()).equals(expResult));
        // Check contants are equal
        assertTrue(Arrays.equals(instance.getDataValues(NOPTransform.getInstance()), expResult));
    }

    /**
     * Test of getRawDataValues method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testGetRawDataValues() {
        System.out.println("getRawDataValues");
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(Arrays.equals(instance.getRawDataValues(), expResult));
    }

    /**
     * Test of getEntry method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        int index = 0;
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(new Double(instance.getEntry(0).toString()) == 0d);
    }

    /**
     * Test of setCategory method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testSetEntry() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        instance.setDataBuffer(new PrimitiveDoubleBuffer(new double[]{1}));
        instance.setEntry(index, val);
        assertTrue(new Double(instance.getEntry(index).toString()) == 0d);
    }

    /**
     * Test of getDimension method, of class GJPrimitiveDoubleDataVector.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        GJPrimitiveDoubleDataVector instance = new GJPrimitiveDoubleDataVector();
        int[] expResult = {0};
        instance.setDataBufferData(expResult);
        int result = instance.getDimension();
        assertEquals(1, result);
    }
}
