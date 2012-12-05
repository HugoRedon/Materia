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
    IdealGas instance;
    public IdealGasTest() {
         tolerance = 0.00001;
         instance = new IdealGas();
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
        
        IdealGas idealGasObject = new IdealGas();
        double temperature = 300;
        double volume = 0.3;
        
        double result = idealGasObject.getPressure(temperature, volume);
        
        double expectedResult = 82.06;
        
        assertEquals(expectedResult, result,tolerance);
    }

    /**
     * Test of getVolume method, of class IdealGas.
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        double pressure = 24;
        double temperature = 500;
        double expResult = 1.70833333333333;
        double result = instance.getVolume(pressure, temperature);
        assertEquals(expResult, result, tolerance);
       
    }

    /**
     * Test of getTemperature method, of class IdealGas.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        double pressure = 23.23;
        double volume = 15.13;
        double expResult =4286.21829268293;
        double result = instance.getTemperature(pressure, volume);
        assertEquals(expResult, result, tolerance);
       
    }

    /**
     * Test of getEquation method, of class IdealGas.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        String expResult = "PV = nRT";
        String result = instance.getEquation();
        assertEquals(expResult, result);
    }

    /**
     * Test of needsComponents method, of class IdealGas.
     */
    @Test
    public void testNeedsComponents() {
        System.out.println("needsComponents");
        boolean expResult = false;
        boolean result = instance.needsComponents();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCubic method, of class IdealGas.
     */
    @Test
    public void testIsCubic() {
        System.out.println("isCubic");
        boolean expResult = false;
        boolean result = instance.isCubic();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of getCompresibilityFactor method, of class IdealGas.
     */
    @Test
    public void testGetCompresibilityFactor() {
        System.out.println("getCompresibilityFactor");
        double expResult = 1;
        double result = instance.getCompresibilityFactor();
        assertEquals(expResult, result, tolerance);
   
    }

    /**
     * Test of getFugacity method, of class IdealGas.
     */
    @Test
    public void testGetFugacity() {
        System.out.println("getFugacity");
        double expResult = 1;
        double result = instance.getFugacity();
        assertEquals(expResult, result, tolerance);
    }
}
