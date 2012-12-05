package termo.eos.alpha;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import termo.component.Component;
import termo.eos.CubicAlpha;
import termo.eos.EOS;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Soave_PengRobinsonAlphaTest {
    Component component;
    Soave_PengRobinsonAlpha prAlpha;
    Soave_PengRobinsonAlpha rksAlpha;
      double tol =0.00001;
    public Soave_PengRobinsonAlphaTest() {
        component = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        component.setPrsvk1(0.3938);
        

         CubicAlpha  pr = EOS.pengRobinson();
        CubicAlpha rks = EOS.redlichKwongSoave();
        
        try{
          //  Method getAlpha = pr.getClass().getDeclaredMethod("getAlpha");
              Method getAlpha = CubicAlpha.class.getDeclaredMethod("getAlpha");
            getAlpha.setAccessible(true);
            prAlpha = (Soave_PengRobinsonAlpha)getAlpha.invoke(pr);
            rksAlpha =(Soave_PengRobinsonAlpha)getAlpha.invoke(rks);
            
            System.out.println("Reflection succesfull for Soave_PengRobinsonAlphaTest");
             
        }catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            System.out.println("Reflection failed for Soave_PengRobinsonAlphaTest: " + e.getCause() +" ;Message:" + e.getMessage());
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of alpha method, of class Soave_PengRobinsonAlpha.
     */
    @Test
    public void testAlpha() {
        System.out.println("alpha");
        double temperature = 425.87;
        
        double rksExpResult = 1.24764441279253;
        double prExpResult = 1.21953969871451;
        
        double rksResult = rksAlpha.alpha(temperature, component);
        double prResult = prAlpha.alpha(temperature, component);
        
              
        assertEquals(rksExpResult, rksResult, tol);
        assertEquals(prExpResult, prResult, tol);
        
    }

    /**
     * Test of TOverAlphaTimesDalpha method, of class Soave_PengRobinsonAlpha.
     */
    @Test
    public void testTOverAlphaTimesDalpha() {
        System.out.println("TOverAlphaTimesDalpha");
        
        double temperature = 425.87;
        
        component = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        component.setPrsvk1(-0.03374);
        
        double expprResult = -0.972816540611861;
        double prresult = prAlpha.TOverAlphaTimesDalpha(temperature, component);
        
        double exprkResult =-1.0784390017006;
        double rkResult = rksAlpha.TOverAlphaTimesDalpha(temperature, component);
        
        assertEquals(exprkResult, rkResult, tol);
        assertEquals(expprResult, prresult, tol);
        
    }
    
}
