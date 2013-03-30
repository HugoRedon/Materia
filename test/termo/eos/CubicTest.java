/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.componentsForTests.ComponentsForTests;
import termo.eos.alpha.Mathias_StryjekVera;
import termo.phase.Phase;

/**
 *
 * @author Chilpayate
 */
public class CubicTest {
    
    Cubic prsv;
    Cubic rksm;
    
    double tol;
    
    ArrayList<Component> components1;
    ArrayList<Component> components2;
    HashMap<Component, Double> fractions ;
    
    Component methanol;
       Component water;
       BinaryInteractionParameter ki;
    
    public CubicTest() {
        prsv = EOS.pengRobinsonStryjekVera();//new PengRobinson(new Mathias_StryjekVera());
        rksm = EOS.redlichKwongSoaveMathias(); //new RedlichKwongSoave(new Mathias_StryjekVera());
//        methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
//        methanol.setPrsvk1(0.3938);
        methanol = ComponentsForTests.getMethanol();
        
        water = ComponentsForTests.getWater();
//        water = new Component(4, "Water", 0.344d, 221.2d, 647.13d, 0.0571d);
//        water.setPrsvk1(-0.0767d);
        
        components1 = new ArrayList<>();
        components1.add(methanol);
        
        components2 = new ArrayList<>();
        components2.add(methanol);
        components2.add(water);
        
        fractions = null;
        
        ki = new BinaryInteractionParameter();
        
        tol = 0.000001;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

//    /**
//     * Test of get_a method, of class Cubic.
//     */
//    @Test
//    public void testGet_a() {
//        System.out.println("get_a");
//        
//        prsv.set_a(4);
//        rksm.set_a(5);
//        
//        double prsvexpResult = 4;
//        double prsvresult = prsv.get_a();
//        
//        double rksmexpResult = 5;
//        double rksmresult = rksm.get_a();
//        
//        assertEquals(prsvexpResult, prsvresult, tol);
//        assertEquals(rksmexpResult, rksmresult, tol);
//    }


//    /**
//     * Test of get_b method, of class Cubic.
//     */
//    @Test
//    public void testGet_b() {
//        System.out.println("get_b");
//       
//        prsv.set_b(3);
//        rksm.set_b(7);
//        
//        double prsvexpResult = 3;
//        double prsvresult = prsv.get_b();
//        
//        double rksmexpResult = 7;
//        double rksmresult = rksm.get_b();
//        
//        assertEquals(prsvexpResult, prsvresult, tol);
//        assertEquals(rksmexpResult, rksmresult, tol);
//    }



    /**
     * Test of get_u method, of class Cubic.
     */
    @Test
    public void testGet_u() {
        System.out.println("get_u");
        Cubic instance = EOS.pengRobinson();//new PengRobinson();
        double expResult = 2;
        double result = instance.get_u();
        assertEquals(expResult, result, tol);
    }



    /**
     * Test of get_w method, of class Cubic.
     */
    @Test
    public void testGet_w() {
        System.out.println("get_w");
        Cubic instance = EOS.pengRobinson();//new PengRobinson();
        double expResult = -1;
        double result = instance.get_w();
        assertEquals(expResult, result, tol);
    }



    /**
     * Test of isCubic method, of class Cubic.
     */
    @Test
    public void testIsCubic() {
        System.out.println("isCubic");
        Cubic instance =EOS.pengRobinson();// new PengRobinson();
        boolean expResult = true;
        boolean result = instance.isCubic();
        assertEquals(expResult, result);
    }



    /**
     * Test of calculatePressure method, of class Cubic.
     */
    @Test
    public void testGetPressure() {
        System.out.println("getPressure");
        double temperature =425.87 ;
        double volume = 1.33 ;
      
        
        double prsvexpResult = 20.6351177035781;
        double prsvresult = prsv.calculatePressure(temperature, volume, components1, fractions,ki);
        
        double rksmexpResult = 20.8563922098908;
        double rksmresult = rksm.calculatePressure(temperature, volume, components1, fractions, ki);
      
        assertEquals(prsvexpResult, prsvresult, tol);
        assertEquals(rksmexpResult, rksmresult, tol);
  
    }

    /**
     * Test of oneRoot method, of class Cubic.
     */
    @Test
    public void testOneRoot() {
        System.out.println("oneRoot");
        double pressure = 24;
        double temperature = 425.87;

        boolean prsvexpResult = false;
        boolean prsvresult = prsv.oneRoot(pressure, temperature, components1, fractions,null);
        
        boolean rksmexpResult = false;
        boolean rksmresult = rksm.oneRoot(pressure, temperature, components1, fractions,null);
        
        assertEquals(rksmexpResult, rksmresult);
        assertEquals(prsvexpResult, prsvresult);
    }

    /**
     * Test of calculateCompresibilityFactor method, of class Cubic.
     */
    @Test
    public void testGetCompresibilityFactor() {
        System.out.println("getCompresibilityFactor");
        double pressure = 24;
        double temperature =  425.87;
        Phase liquid = Phase.LIQUID;
        Phase vapor = Phase.VAPOR;
        
        double prsvLiquidexpResult = 0.0417461569101531;
        double prsvLiquidresult = prsv.calculateCompresibilityFactor(pressure, temperature, components1, fractions, liquid,null);
        
        double prsvVaporexpResult =0.738911429256551;
        double prsvVaporresult = prsv.calculateCompresibilityFactor(pressure, temperature, components1, fractions, vapor,null);
        
        double rksmLiquidexpResult =0.047362482085529;
        double rksmLiquidresult = rksm.calculateCompresibilityFactor(pressure, temperature, components1, fractions, liquid,null);
        
        double rksmVaporexpResult = 0.751837581086835;
        double rksmVaporresult = rksm.calculateCompresibilityFactor(pressure, temperature, components1, fractions, vapor,null);
        
        assertEquals(prsvLiquidexpResult, prsvLiquidresult,tol);
        assertEquals(rksmLiquidexpResult, rksmLiquidresult,tol);
        
        assertEquals(prsvVaporexpResult, prsvVaporresult,tol);
        assertEquals(rksmVaporexpResult, rksmVaporresult,tol);
        

    }
//cuando se prueben las reglas de mezclado se deberá probar este método
//    /**
//     * Test of calculateParameters method, of class Cubic.
//     */
//    @Test
//    public void testCalculateParameters() {
//        System.out.println("calculateParameters");
//        double aTemperature = 0.0;
//       
//      //  instance.calculateParameters(aTemperature, components1, fractions);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//cuando se prueben las reglas de mezclado se deberá probar este método
//    /**
//     * Test of parametersWithMixingRule method, of class Cubic.
//     */
//    @Test
//    public void testParametersWithMixingRule() {
//        System.out.println("parametersWithMixingRule");
//        double aTemperature = 0.0;
//        ArrayList<Component> components1 = null;
//        HashMap<Component, Double> fractions = null;
//        Cubic instance = new CubicImpl();
//        instance.parametersWithMixingRule(aTemperature, components1, fractions);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of parametersOneComponent method, of class Cubic.
//     */
//    @Test
//    public void testParametersOneComponent() {
//        System.out.println("parametersOneComponent");
//        double aTemperature = 0.0;
//        
//       // instance.parametersOneComponent(aTemperature, aComponent);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDefaultMixingRule method, of class Cubic.
//     */
//    @Test
//    public void testSetDefaultMixingRule() {
//        System.out.println("setDefaultMixingRule");
//        Cubic instance = new CubicImpl();
//        instance.setDefaultMixingRule();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMixingRule method, of class Cubic.
//     */
//    @Test
//    public void testSetMixingRule() {
//        System.out.println("setMixingRule");
//        MixingRule newMixingRule = null;
//        Cubic instance = new CubicImpl();
//        instance.setMixingRule(newMixingRule);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of calculateVolume method, of class Cubic.
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
         double pressure = 24;
        double temperature =  425.87;
        Phase liquid = Phase.LIQUID;
        Phase vapor = Phase.VAPOR;
        
        double prsvLiquidexpResult = 0.0607874352209752;
        double prsvLiquidresult = prsv.calculateVolume(temperature, pressure, components1, fractions, liquid,null);
        
        double prsvVaporexpResult =1.07594408598236;
        double prsvVaporresult = prsv.calculateVolume(temperature, pressure, components1, fractions, vapor,null);
        
        double rksmLiquidexpResult =0.0689654814903089;
        double rksmLiquidresult = rksm.calculateVolume(temperature, pressure, components1, fractions, liquid,null);
        
        double rksmVaporexpResult = 1.09476612075627;
        double rksmVaporresult = rksm.calculateVolume(temperature, pressure, components1, fractions, vapor,null);
        
        assertEquals(prsvLiquidexpResult, prsvLiquidresult,tol);
        assertEquals(rksmLiquidexpResult, rksmLiquidresult,tol);
        
        assertEquals(prsvVaporexpResult, prsvVaporresult,tol);
        assertEquals(rksmVaporexpResult, rksmVaporresult,tol);
    
    }
//
//    /**
//     * Test of getTemperature method, of class Cubic.
//     */
//    @Test
//    public void testGetTemperature() {
//        System.out.println("getTemperature");
//        double pressure = 0.0;
//        double volume = 0.0;
//        ArrayList<Component> components1 = null;
//        Cubic instance = new CubicImpl();
//        double expResult = 0.0;
//        double result = instance.getTemperature(pressure, volume, components1);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of calculateFugacity method, of class Cubic.
     */
    @Test
    public void testGetFugacity() {
        System.out.println("getFugacity");
              double pressure = 24;
        double temperature =  425.87;
        Phase liquid = Phase.LIQUID;
        Phase vapor = Phase.VAPOR;
        
        double prsvLiquidexpResult = 0.580871852744437;
        double prsvLiquidresult = prsv.calculateFugacity(pressure , temperature, components1, components1.get(0),fractions, liquid,null);
        
        double prsvVaporexpResult = 0.793548170919521;
        double prsvVaporresult = prsv.calculateFugacity(pressure , temperature, components1, components1.get(0),fractions, vapor,null);
        
        double rksmLiquidexpResult = 1.26745792786797;
        double rksmLiquidresult = rksm.calculateFugacity(pressure , temperature, components1, components1.get(0),fractions, liquid,null);
        
        double rksmVaporexpResult = 0.808927234002065;
        double rksmVaporresult = rksm.calculateFugacity(pressure , temperature, components1, components1.get(0),fractions, vapor,null);
        
        assertEquals(prsvLiquidexpResult, prsvLiquidresult,tol);
        assertEquals(rksmLiquidexpResult, rksmLiquidresult,tol);
        
        assertEquals(prsvVaporexpResult, prsvVaporresult,tol);
        assertEquals(rksmVaporexpResult, rksmVaporresult,tol);
    
    }

    /**
     * Test of needsComponents method, of class Cubic.
     */
    @Test
    public void testNeedsComponents() {
        System.out.println("needsComponents");     
        boolean expResult = true;
        boolean result = prsv.needsComponents();
        assertEquals(expResult, result);
    }

    @Test
    public void testSingle_as(){
        System.out.println("single_as");
        double temperature = 425.87;
        
        try {
            Method single_as = Cubic.class.getDeclaredMethod("single_as",ArrayList.class,double.class);
            single_as.setAccessible(true);
            
            HashMap<Component,Double> as = (HashMap<Component,Double> )single_as.invoke(prsv, components2,temperature);
            
            System.out.println("single_as reflection succeded");
            double expaWater =7.91873692603462;
            double expaMethanol = 12.1481424413572;
            
            double aWater = as.get(water);
            double aMethanol = as.get(methanol);
            
           assertEquals(expaWater, aWater,tol);
            assertEquals(expaMethanol, aMethanol,tol);
            
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CubicTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("reflection failed" + ex.getMessage());
        } 
        
        
        
        
    }
    
  @Test
    public void testSingle_bs(){
        System.out.println("single_bs");

        
        try {
            Method single_bs = Cubic.class.getDeclaredMethod("single_bs",ArrayList.class);
            single_bs.setAccessible(true);
            
            HashMap<Component,Double> bs = (HashMap<Component,Double> )single_bs.invoke(prsv, components2);
            
            System.out.println("single_bs reflection succeded");
            double expbWater =0.0186628490784125;
            double expbMethanol = 0.0409059036484463;
            
            double bWater = bs.get(water);
            double bMethanol = bs.get(methanol);
            
           assertEquals(expbWater, bWater,tol);
            assertEquals(expbMethanol, bMethanol,tol);
            
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CubicTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("reflection failed" + ex.getMessage());
        } 
    }


}
