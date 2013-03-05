package termo.eos.alpha;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.Component;
import termo.componentsForTests.ComponentsForTests;
import termo.eos.CubicAlpha;
import termo.eos.EOS;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Mathias_StryjekVeraTest {
      Component component;
    Mathias_StryjekVera prsvAlpha;
    Mathias_StryjekVera rksmAlpha;
    public Mathias_StryjekVeraTest() {
//        component = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
//        component.setPrsvk1(0.3938);
        component = ComponentsForTests.getMethanol();
        

        CubicAlpha  prsv = EOS.pengRobinsonStryjekVera();
        CubicAlpha rksm = EOS.redlichKwongSoaveMathias();
        
        try{
          //  Method getAlpha = pr.getClass().getDeclaredMethod("getAlpha");
              Method getAlpha = CubicAlpha.class.getDeclaredMethod("getAlpha");
            getAlpha.setAccessible(true);
            prsvAlpha = (Mathias_StryjekVera)getAlpha.invoke(prsv);
            rksmAlpha =(Mathias_StryjekVera)getAlpha.invoke(rksm);
            
            System.out.println("Reflection succesfull for Mathias_StryjekVeraTest");
             
        }catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            System.out.println("Reflection failed for Mathias_StryjekVeraTest: " + e.getCause() +" ;Message:" + e.getMessage());
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    double tol = 0.0001;
    
    
    /**
     * Test of c method, of class Mathias_StryjekVera.
     */
    @Test
    public void testC(){
        System.out.println("c");
        
        Method c;
        try {
            c = Mathias_StryjekVera.class.getDeclaredMethod("c", Component.class);
            c.setAccessible(true);
       
            double prsvResult =  (Double)c.invoke(prsvAlpha, component);
            double rksmResult = (Double)c.invoke(rksmAlpha,component);
            System.out.println("Reflection method c Test succeded" );
            
            double prsvExpResult = 1.70745798968441;
             double rksmExpResult = 1.77892744104;
             
             assertEquals(prsvExpResult, prsvResult,tol);
             assertEquals(rksmExpResult, rksmResult,tol);
             
                
            } catch (    IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(Mathias_StryjekVeraTest.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        
        
    }
    
    
    /**
     * Test of alpha method for temperature below critical temperature, of class Mathias_StryjekVera.
     */
    @Test
    public void testAlpha() {
        System.out.println("alpha");
        double temperature = 425.87;
      
        double prsvexpResult = 1.23886494170679;
        double prsvResult = prsvAlpha.alpha(temperature, component);
        
        
        double rksmexpResult = 1.26719019563283;
        double rksmResult = rksmAlpha.alpha(temperature, component);
        
        assertEquals(prsvexpResult, prsvResult, tol);
        assertEquals(rksmexpResult, rksmResult, tol);
    }
    
     /**
     * Test of alpha method for temperature over critical temperature, of class Mathias_StryjekVera.
     */
    @Test
    public void testAlpha2(){
        System.out.println("alpha2");
        double temperature = 604.23;
      
        double prsvexpResult =0.764408790612414;
        double prsvResult = prsvAlpha.alpha(temperature, component);
        
        
        double rksmexpResult = 0.742583813994715;
        double rksmResult = rksmAlpha.alpha(temperature, component);
        
        assertEquals(prsvexpResult, prsvResult, tol);
        assertEquals(rksmexpResult, rksmResult, tol);
    }

    /**
     * Test of TOverAlphaTimesDalpha method, of class Mathias_StryjekVera.
     */
    @Test
    public void testTOverAlphaTimesDalpha() {
        System.out.println("TOverAlphaTimesDalpha");
        double aboveTemp = 604.23;
        double belowTemp = 425.87;
        
//        component = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
//        component.setPrsvk1(-0.03374);

        double exprksmAboveTcResult = -1.70719975719279;
        double rksmAboveTcresult = rksmAlpha.TOverAlphaTimesDalpha(aboveTemp, component);
        
        double expprsvAboveTcResult = -1.5019225384658;
        double prsvAboveTcResult = prsvAlpha.TOverAlphaTimesDalpha(aboveTemp, component);
        
        double exprksmBelowTcResult = -1.08108869319346;
        double rksmBelowTcResult = rksmAlpha.TOverAlphaTimesDalpha(belowTemp, component);
        
        double expprsvBelowTcResult = -0.975425142854947;
        double prsvBelowTcResult = prsvAlpha.TOverAlphaTimesDalpha(belowTemp, component);
        
        assertEquals(expprsvAboveTcResult, prsvAboveTcResult,tol);
        assertEquals(exprksmAboveTcResult, rksmAboveTcresult, tol);
        
        assertEquals(exprksmBelowTcResult, rksmBelowTcResult,tol);
        assertEquals(expprsvBelowTcResult, prsvBelowTcResult, tol);
        
    }
}
