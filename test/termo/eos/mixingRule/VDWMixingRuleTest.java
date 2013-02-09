package termo.eos.mixingRule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.component.VanDerWaalsParameters;
import termo.eos.Cubic;
import termo.eos.CubicTest;
import termo.eos.EOS;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VDWMixingRuleTest {
    
       Cubic prsv;
    Cubic rksm;
    
    double tol;
   
    ArrayList<Component> components2;
    HashMap<Component, Double> fractions ;
    VanDerWaalsParameters k;
    
    Component methanol;
       Component water;
    
    public VDWMixingRuleTest() {   
        prsv = EOS.pengRobinsonStryjekVera();
        rksm = EOS.redlichKwongSoaveMathias();
        methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        methanol.setPrsvk1(0.3938);
        
        water = new Component(4, "Water", 0.344d, 221.2d, 647.13d, 0.0571d);
        water.setPrsvk1(-0.0767d);
        
        components2 = new ArrayList<>();
        components2.add(methanol);
        components2.add(water);
        
//        BinaryInteractionParameters.setK(methanol, water, -0.0778);
//        BinaryInteractionParameters.setK(water, methanol, -0.0778);
        
         k = new VanDerWaalsParameters();
        k.setValue(methanol, water, -0.0778, true);
        
        fractions = new HashMap<>();
        
        tol = 0.000001;
    }

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

    /**
     * Test of a method, of class VDWMixingRule.
     */
    @Test
    public void testA() {
        System.out.println("a");
         double temperature = 425.87;
         try {
             
             //for single as
            Method single_as = Cubic.class.getDeclaredMethod("single_as",ArrayList.class,double.class);
            single_as.setAccessible(true);
            
            HashMap<Component,Double> singleAs = (HashMap<Component,Double> )single_as.invoke(prsv, components2,temperature);
            
            System.out.println("testA reflection succeded");
            
            //the fractions
            fractions.put(methanol,0.6);
            fractions.put(water,0.4);
    
            VDWMixingRule vdw = new VDWMixingRule();
            
            double expResult = 10.9204307481091;
            double result = vdw.a(singleAs, components2, fractions,k);
            
            fractions.put(methanol,0.0);
            fractions.put(water,1.0);
 
            double exp2Result = 7.90913475823673;
            double result2 = vdw.a(singleAs, components2, fractions,k);
            
            
            assertEquals(expResult, result, tol); 
            assertEquals(exp2Result, result2, tol); 
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CubicTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("reflection failed" + ex.getMessage());
        } 
    }

    /**
     * Test of oneOveraNParcialN2RespectN method, of class VDWMixingRule.
     */
    @Test
    public void testOneOveraNParcialN2RespectN() {
        System.out.println("oneOveraNParcialN2RespectN");
        double temperature = 425.87;
         try {
             
             //for single as
            Method single_as = Cubic.class.getDeclaredMethod("single_as",ArrayList.class,double.class);
            single_as.setAccessible(true);
            HashMap<Component,Double> singleAs = (HashMap<Component,Double> )single_as.invoke(prsv, components2,temperature);
            
            System.out.println("testOneOveraNParcialN2RespectN reflection succeded");
            
            //the fractions
            fractions.put(methanol,0.6);
            fractions.put(water,0.4);
    
            VDWMixingRule vdw = new VDWMixingRule();
            double expMethanolResult = 2.16138688196173;
            double methanolResult = vdw.oneOveraNParcialN2RespectN(singleAs, components2, methanol,fractions,k);
          
            double expWaterResult = 1.75791967705741;
            double waterResult = vdw.oneOveraNParcialN2RespectN(singleAs, components2, water,fractions,k);
            
            
            assertEquals(expMethanolResult, methanolResult, tol); 
            assertEquals(expWaterResult, waterResult, tol); 
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CubicTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("reflection failed" + ex.getMessage());
        } 
    }

    /**
     * Test of b method, of class VDWMixingRule.
     */
    @Test
    public void testB() {
        System.out.println("b");
         try {
             
             //for single bs
            Method single_bs = Cubic.class.getDeclaredMethod("single_bs",ArrayList.class);
            single_bs.setAccessible(true);
            HashMap<Component,Double> singleBs = (HashMap<Component,Double> )single_bs.invoke(prsv, components2);
            
            System.out.println("testB reflection succeded");
            
            //the fractions
            fractions.put(methanol,0.6);
            fractions.put(water,0.4);
    
            VDWMixingRule vdw = new VDWMixingRule();
            double expResult = 0.0320086818204328;
            double result = vdw.b(singleBs, components2, fractions);
          
            assertEquals(expResult, result, tol); 
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CubicTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("reflection failed" + ex.getMessage());
        } 
    }
}
