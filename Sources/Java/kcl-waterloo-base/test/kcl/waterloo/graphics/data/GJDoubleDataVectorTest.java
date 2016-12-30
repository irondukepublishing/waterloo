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
public class GJDoubleDataVectorTest {

    public GJDoubleDataVectorTest() {
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
     * Test of setDataBufferData method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_doubleArr() {
        System.out.println("setDataBufferData");
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        GJDoubleDataVector instance = new GJDoubleDataVector();
        instance.setDataBufferData(expResult);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == expResult[0]);
    }

    /**
     * Test of setDataBufferData method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_intArr() {
        System.out.println("setDataBufferData");
        int[] v = {0, 1, 2, 3, 4, 5};
        GJDoubleDataVector instance = new GJDoubleDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == 0d);
    }

    /**
     * Test of setDataBufferData method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetDataBufferData_AbstractList() {
        System.out.println("setDataBufferData");
        AbstractList<Integer> v = new ArrayList<Integer>();
        v.add(new Integer(1));
        v.add(new Integer(10));
        v.add(new Integer(100));
        GJDoubleDataVector instance = new GJDoubleDataVector();
        instance.setDataBufferData(v);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == 1d
                && instance.getDataValues(NOPTransform.getInstance())[1] == 10d
                && instance.getDataValues(NOPTransform.getInstance())[2] == 100d);
    }

    /**
     * Test of setDataBuffer method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetDataBuffer() {
        System.out.println("setDataBuffer");
        Double[] v = new Double[]{0d, 1d, 2d, 3d, 4d, 5d};
        GJDoubleDataVector instance = new GJDoubleDataVector();
        instance.setDataBuffer(v);
        assertTrue(instance.getDataBuffer().equals(v));
    }

    /**
     * Test of getDataBuffer method, of class GJDoubleDataVector.
     */
    @Test
    public void testGetDataBuffer() {
        System.out.println("getDataBuffer");
        GJDoubleDataVector instance = new GJDoubleDataVector();
        Double[] expResult = new Double[]{0d};
        instance.setDataBuffer(expResult);
        Double[] result = instance.getDataBuffer();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDataValues method, of class GJDoubleDataVector.
     */
    @Test
    public void testGetDataValues() {
        System.out.println("getDataValues");
        GJDoubleDataVector instance = new GJDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        // Check we get back a copy
        assertTrue(!instance.getDataValues(NOPTransform.getInstance()).equals(expResult));
        // Check contants are equal
        assertTrue(Arrays.equals(instance.getDataValues(NOPTransform.getInstance()), expResult));
    }

    /**
     * Test of getRawDataValues method, of class GJDoubleDataVector.
     */
    @Test
    public void testGetRawDataValues() {
        System.out.println("getRawDataValues");
        GJDoubleDataVector instance = new GJDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(Arrays.equals(instance.getRawDataValues(), expResult));
    }

    /**
     * Test of getEntry method, of class GJDoubleDataVector.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        int index = 0;
        GJDoubleDataVector instance = new GJDoubleDataVector();
        double[] expResult = new double[]{0, 1, 2, 3, 4, 5};
        instance.setDataBufferData(expResult);
        assertTrue(new Double(instance.getEntry(0).toString()) == 0d);
    }

    /**
     * Test of setCategory method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetEntry() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        GJDoubleDataVector instance = new GJDoubleDataVector();
        instance.setDataBuffer(new Double[]{1d});
        instance.setEntry(index, val);
        assertTrue(new Double(instance.getEntry(index).toString()) == 0d);
    }

    /**
     * Test of getDimension method, of class GJDoubleDataVector.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        GJDoubleDataVector instance = new GJDoubleDataVector();
        int[] expResult = {0};
        instance.setDataBufferData(expResult);
        int result = instance.getDimension();
        assertEquals(1, result);
    }

    /**
     * Test of setCategory method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetEntry_int_double() {
        System.out.println("setEntry");
        int index = 0;
        double val = 0.0;
        GJDoubleDataVector instance = new GJDoubleDataVector();
        Double[] expResult = new Double[]{1d};
        instance.setDataBuffer(expResult);
        instance.setEntry(index, val);
        assertTrue(instance.getRawDataValues()[0] == 0d);
    }

    /**
     * Test of setCategory method, of class GJDoubleDataVector.
     */
    @Test
    public void testSetEntry_int_Double() {
        System.out.println("setEntry");
        int index = 0;
        Double val = new Double(0);
        GJDoubleDataVector instance = new GJDoubleDataVector();
        Double[] expResult = new Double[]{1d};
        instance.setDataBuffer(expResult);
        instance.setEntry(index, val);
        assertTrue(instance.getDataValues(NOPTransform.getInstance())[0] == 0d);
    }
}
