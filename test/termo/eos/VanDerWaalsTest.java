/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos;

import java.util.ArrayList;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.Component;

/**
 *
 * @author Chilpayate
 */
public class VanDerWaalsTest {
       VanDerWaals instance ;
       double tolerance;
       Component aComponent;
    public VanDerWaalsTest() {
        tolerance = 0.00001;
        instance = new VanDerWaals();
        
         aComponent = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        aComponent.setPrsvk1(0.3938);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

//    /**
//     * Test of parametersOneComponent method, of class VanDerWaals.
//     */
//    @Test
//    public void testParametersOneComponent() {
//        System.out.println("parametersOneComponent");
//        double aTemperature = 450;
//
//        instance.parametersOneComponent(aTemperature, aComponent);
//        double a_result = instance.get_a();
//        double b_result = instance.get_b();
//        
//        double a_expected = 9.32404916196523;
//        double b_expected = 0.0657261696272204;
//        
//        assertEquals(a_expected,a_result,tolerance);
//        assertEquals(b_expected,b_result, tolerance);
//    }

    /**
     * Test of single_a method, of class VanDerWaals.
     */
    @Test
    public void testSingleA() {
        System.out.println("singleA");
        double aTemperature = 0.0;
        double expResult = 9.32404916196523;
        double result = instance.single_a(aTemperature, aComponent);
        
        assertEquals(expResult, result, tolerance);

    }

    /**
     * Test of single_b method, of class VanDerWaals.
     */
    @Test
    public void testSingleB() {
        System.out.println("singleB");
        double expResult = 0.0657261696272204 ;
        double result = instance.single_b(aComponent);
        
        assertEquals(expResult, result, tolerance);
    }
    /**
     * Test of getPressure method, of super class Cubic
     */
    @Test
    public void testGetPressure(){
        System.out.println("getPressure");
        double temperature = 455;
        double volume = 1.33;
        ArrayList<Component> componentList = new ArrayList<>();
        componentList.add(aComponent);
        double expResult = 24.2399116478063;
        double result = instance.getPressure(temperature, volume, componentList, null,null);
      
        // must add multicomponent tests
        assertEquals(expResult,result,tolerance);    
    }
}
