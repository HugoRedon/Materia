package termo.eos;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class CubicAlphaTest {
   CubicAlpha prsv ;
   CubicAlpha rksM;
       double tol;
       Component component;
    public CubicAlphaTest() {
        tol = 0.00001;
        prsv = EOS.pengRobinsonStryjekVera();
        rksM =EOS.redlichKwongSoaveMathias(); 
        
         component = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        component.setPrsvk1(0.3938);
    }
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    /**
     * Test of single_a method, of class CubicAlpha.
     */
    @Test
    public void testSingle_a() {
        System.out.println("single_a");
        double tBelow = 425.87;
        double tAbove = 604.23;
       
        double prexpResult = 12.5194340883597;
        double rksexpResult =11.9723279041348;
        double prAboveExp =7.72478512262222;
        double rksAboveExp = 7.01588202630302;
        
        
        double prresult = prsv.single_a(tBelow, component);
        double rksresult = rksM.single_a(tBelow, component);
        double prAbove = prsv.single_a(tAbove, component);
        double rksAbove = rksM.single_a(tAbove, component);
        
        assertEquals(prexpResult, prresult, tol);
        assertEquals(rksexpResult, rksresult, tol);
        assertEquals(prAboveExp, prAbove, tol);
        assertEquals(rksAboveExp, rksAbove, tol);
        
    }

    /**
     * Test of single_b method, of class CubicAlpha.
     */
    @Test
    public void testSingle_b() {
        System.out.println("single_b");
        
        double prexpResult = 0.0409358347974574;
        double rksexpResult =0.0455896406082637;
        
       double prresult = prsv.single_b( component);
        double rksresult = rksM.single_b( component);
        
         assertEquals(prexpResult, prresult, tol);
        assertEquals(rksexpResult, rksresult, tol);
      
    }
    
    
    
}
