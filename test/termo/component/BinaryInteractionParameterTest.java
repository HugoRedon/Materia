package termo.component;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Hugo Redon Rivera
 */
public class BinaryInteractionParameterTest {
    
    
    double tol;
    public BinaryInteractionParameterTest() {
        tol = 0.000001;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getValue method, of class BinaryInteractionParameters.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Component component1 = new Component();     
        Component component2 = new Component();
        
        VanDerWaalsParameters instance = new VanDerWaalsParameters();
        
       // instance.setSymmetric(true);
        instance.setValue(component1, component2, 0.5, true);
        
        double expResult = 0.5;
            
        double result12 = instance.getValue(component1, component2);
        double result21 = instance.getValue(component2, component1);
        
        assertEquals(expResult, result12, tol);
        assertEquals(expResult, result21,tol);
        
         instance.setValue(component1, component2, 0.7, false);
        instance.setValue(component2, component1, 0.1, false);
        
        double exp12Result = 0.7;
        double exp21Result = 0.1;
        
        result12 = instance.getValue(component1, component2);
        result21 = instance.getValue(component2, component1);
        
        assertEquals(exp12Result, result12, tol);
        assertEquals(exp21Result, result21,tol);
        
        VanDerWaalsParameters instance2 = new VanDerWaalsParameters();
        
        double exp = 0;
        double resu = instance2.getValue(component1, component2);
        double resu2 = instance2.getValue(component2, component1);
        
        assertEquals(exp, resu, tol);
        assertEquals(exp, resu2,tol);
        
    }






}
