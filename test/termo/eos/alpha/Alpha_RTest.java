package termo.eos.alpha;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.AfterClass;
import static org.junit.Assert.*;
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
public class Alpha_RTest {
    Component aComponent;
    CubicAlpha pr;
    CubicAlpha rks;
    
    Alpha_R alphaPR;
    Alpha_R alphaRKS;
    
    public Alpha_RTest() {
//         aComponent = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
//        aComponent.setPrsvk1(0.3938);
        aComponent = ComponentsForTests.getMethanol();
        
        pr = EOS.pengRobinson();
        rks = EOS.redlichKwongSoave();
        
        try{
          //  Method getAlpha = pr.getClass().getDeclaredMethod("getAlpha");
            
              Method getAlpha = CubicAlpha.class.getDeclaredMethod("getAlpha");
            getAlpha.setAccessible(true);
            alphaPR = (Alpha_R)getAlpha.invoke(pr);
            alphaRKS =(Alpha_R)getAlpha.invoke(rks);
            
            System.out.println("Reflection succesfull for Alpha_RTTest");
             
        }catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            System.out.println("Reflection failed for Alpha_RTTest: " + e.getCause() +" ;Message:" + e.getMessage());
        }
        
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

 

    /**
     * Test of m method, of class Alpha_R.
     */
    @Test
    public void testM() {
        System.out.println("m");
        
        double expPRResult = 1.17863597936882 ;
        double PRResult = alphaPR.m(aComponent);
        
        double expRKSResult =1.32157488208;
        double RKSResult = alphaRKS.m(aComponent);
        
        assertEquals(expPRResult, PRResult, 0.00001);
        assertEquals(expRKSResult,RKSResult,0.00001);
    }

  
}
