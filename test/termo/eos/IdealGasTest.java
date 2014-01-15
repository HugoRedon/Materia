package termo.eos;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Hugo Redon Rivera
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
        //Temperature in [K]
        double temperature = 300;
        //Molar volume [m3/mol]
        double volume = 13;
        
        double result = idealGasInstance.getPressure(temperature, volume);
        //Pressure [Pa]

        double expectedResult = 191.8724307692;
        
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
        double expResult = 0.0410287293;
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
        double expResult =369403.31026432;

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
