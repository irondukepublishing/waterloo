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
public class GJFloatDataVectorTest {

    public GJFloatDataVectorTest() {
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
     * Test of setDataBufferData method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testSetDataBufferData_doubleArr() {
        System.out.println("setDataBufferData");
        Float[] expResult = new Float[]{0f, 1f, 2f, 3f, 4f, 5f};
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBuffer(expResult);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == expResult[0]);
    }

    /**
     * Test of setDataBufferData method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testSetDataBufferData_intArr() {
        System.out.println("setDataBufferData");
        int[] v = {0, 1, 2, 3, 4, 5};
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == 0d);
    }

    /**
     * Test of setDataBufferData method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testSetDataBufferData_AbstractList() {
        System.out.println("setDataBufferData");
        AbstractList<Double> v = new ArrayList<Double>();
        v.add(Double.POSITIVE_INFINITY);
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == Double.POSITIVE_INFINITY);
    }

    /**
     * Test of setDataBuffer method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testSetDataBuffer() {
        System.out.println("setDataBuffer");
        Float[] v = new Float[]{0f};
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBuffer(v);
        assertTrue(instance.getDataBuffer().equals(v));
        assertTrue(Arrays.equals(instance.getDataBuffer(), v));
    }

    /**
     * Test of getDataBuffer method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testGetDataBuffer() {
        System.out.println("getDataBuffer");
        GJFloatDataVector instance = new GJFloatDataVector();
        Float[] expResult = new Float[]{0f};
        instance.setDataBuffer(expResult);
        assertEquals(expResult, instance.getDataBuffer());
    }

    /**
     * Test of getDataValues method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testGetDataValues() {
        System.out.println("getDataValues");
        GJFloatDataVector instance = new GJFloatDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        // Check we get back a copy
        assertTrue(!instance.getDataValues(NOPTransform.getInstance()).equals(expResult));
        // Check contants are equal
        assertTrue(Arrays.equals(instance.getDataValues(NOPTransform.getInstance()), expResult));
    }

    /**
     * Test of getRawDataValues method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testGetRawDataValues() {
        System.out.println("getRawDataValues");
        GJFloatDataVector instance = new GJFloatDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(Arrays.equals(instance.getRawDataValues(), expResult));
    }

    /**
     * Test of getEntry method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        int index = 0;
        GJFloatDataVector instance = new GJFloatDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(new Double(instance.getEntry(0).toString()) == 0d);
    }

    /**
     * Test of setCategory method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testSetEntry() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBuffer(new Float[]{1f});
        instance.setEntry(index, val);
        assertTrue(new Double(instance.getEntry(index).toString()) == 0d);
    }

    /**
     * Test of getDimension method, of class GJFloatDataVectorTest.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        GJFloatDataVector instance = new GJFloatDataVector();
        int[] expResult = {0};
        instance.setDataBufferData(expResult);
        int result = instance.getDimension();
        assertEquals(1, result);
    }

    /**
     * Test of setCategory method, of class GJFloatDataVector.
     */
    @Test
    public void testSetEntry_int_double() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBufferData(new double[]{Double.POSITIVE_INFINITY});
        instance.setEntry(index, val);
        assertTrue(new Double(instance.getEntry(index).toString()) == 0d);
    }

    /**
     * Test of setCategory method, of class GJFloatDataVector.
     */
    @Test
    public void testSetEntry_int_Float() {
        System.out.println("setEntry");
        int index = 0;
        Float val = 0f;
        GJFloatDataVector instance = new GJFloatDataVector();
        instance.setDataBuffer(new Float[]{Float.POSITIVE_INFINITY});
        instance.setEntry(index, val);
        assertTrue(new Float(instance.getEntry(index).toString()) == 0d);
    }
}
