/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.transforms;

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
@Suite.SuiteClasses({kcl.waterloo.graphics.transforms.LogTransformTest.class, kcl.waterloo.graphics.transforms.Log2TransformTest.class, kcl.waterloo.graphics.transforms.Log10TransformTest.class, kcl.waterloo.graphics.transforms.servicesTest.class, kcl.waterloo.graphics.transforms.NOPTransformTest.class, kcl.waterloo.graphics.transforms.pTransformTest.class})
public class TransformsSuite {

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
