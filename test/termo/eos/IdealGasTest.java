/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Chilpayate
 */
public class IdealGasTest {
    double tolerance;
    IdealGas idealGasInstance;
    public IdealGasTest() {
         tolerance = 0.00001;
         idealGasInstance = new IdealGas();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getPressure method, of class IdealGas.
     */
    @Test
    public void testGetPressure() {
        System.out.println("getPressure");
              
        double temperature = 300;
        double volume = 13;
        
        double result = idealGasInstance.getPressure(temperature, volume);
        
        double expectedResult =191872.43077;
        
        assertEquals(expectedResult, result,tolerance);
    }

    /**
     * Test of getVolume method, of class IdealGas.
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        double pressure = 101325;
        double temperature = 500;
        double expResult = 41.02872934;
        double result = idealGasInstance.getVolume(pressure, temperature);
        assertEquals(expResult, result, tolerance);
       
    }

    /**
     * Test of getTemperature method, of class IdealGas.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        double pressure = 203000.23;
        double volume = 15.13;
        double expResult =369.4033103;
        double result = idealGasInstance.getTemperature(pressure, volume);
        assertEquals(expResult, result, tolerance);
       
    }

    /**
     * Test of getEquation method, of class IdealGas.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        String expResult = "PV = nRT";
        String result = idealGasInstance.getEquation();
        assertEquals(expResult, result);
    }

    /**
     * Test of needsComponents method, of class IdealGas.
     */
    @Test
    public void testNeedsComponents() {
        System.out.println("needsComponents");
        boolean expResult = false;
        boolean result = idealGasInstance.needsComponents();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCubic method, of class IdealGas.
     */
    @Test
    public void testIsCubic() {
        System.out.println("isCubic");
        boolean expResult = false;
        boolean result = idealGasInstance.isCubic();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of getCompresibilityFactor method, of class IdealGas.
     */
    @Test
    public void testGetCompresibilityFactor() {
        System.out.println("getCompresibilityFactor");
        double expResult = 1;
        double result = idealGasInstance.getCompresibilityFactor();
        assertEquals(expResult, result, tolerance);
   
    }

    /**
     * Test of getFugacity method, of class IdealGas.
     */
    @Test
    public void testGetFugacity() {
        System.out.println("getFugacity");
        double expResult = 1;
        double result = idealGasInstance.getFugacity();
        assertEquals(expResult, result, tolerance);
    }
}
