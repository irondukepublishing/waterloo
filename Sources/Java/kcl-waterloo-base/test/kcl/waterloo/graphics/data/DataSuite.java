/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({kcl.waterloo.graphics.data.GJPrimitiveDoubleDataVectorTest.class, kcl.waterloo.graphics.data.PrimitiveDoubleVectorTest.class, kcl.waterloo.graphics.data.GJDoubleDataVectorTest.class, kcl.waterloo.graphics.data.GJFloatDataVectorTest.class, kcl.waterloo.graphics.data.GJBigDecimalDataVectorTest.class})
public class DataSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
