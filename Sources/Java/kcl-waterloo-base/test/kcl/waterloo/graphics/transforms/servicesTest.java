/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kcl.waterloo.graphics.transforms;

import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Malcolm Lidierth, King's College London <a
 * href="http://sourceforge.net/p/waterloo/discussion/"> [Contact]</a>
 */
public class servicesTest {

    public servicesTest() {
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
     * Test of addClass method, of class Services.
     */
    @Test
    public void testAddClass() {
        System.out.println("addClass");
        Class clz = Dummy.class;
        Services.addClass(clz);
        assertEquals(Services.getInstanceForName("Dummy").getClass(), Dummy.class);
    }

    /**
     * Test of getAvailable method, of class Services.
     */
    @Test
    public void testGetAvailable() {
        System.out.println("getAvailable");
        assertEquals(Services.getAvailable().size(), Services.getClasses().size());
    }

    /**
     * Test of getInstanceForName method, of class Services.
     */
    @Test
    public void testGetInstanceForName() {
        System.out.println("getInstanceForName");
        assertEquals(Services.getInstanceForName("Dummy").getClass(), Dummy.class);
    }
}
