package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.Component;
import termo.component.VanDerWaalsParameters;
import termo.eos.Cubic;
import termo.eos.EOS;

/**
 *
 * @author Hugo Redon Rivera
 */
public class FlashTest {
    
    double tol;
    Component methanol;
    Component water;
    
      VanDerWaalsParameters kinteraction;
    
    public FlashTest() {
        tol = 0.1;
         methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        methanol.setPrsvk1(-0.03374);
      //  methanol.setPrsvk1(-0.1682);
        water = new Component(4, "Water", 0.344861d, 217.665927d, 647.13d, 0.0571d);
        water.setPrsvk1(-0.0767d);
        
//         BinaryInteractionParameters.setK(methanol, water, -0.0778);
//        BinaryInteractionParameters.setK(water, methanol, -0.0778);
        
        kinteraction = new VanDerWaalsParameters();
        kinteraction.setValue(methanol, water, -0.0778, true);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getFlash method, of class Flash.
     */
    @Test
    public void testGetFlash() {
        System.out.println("getFlash");
        
        double pressure =9.0260054306752;
        double temperature =423.45;
        double vF=0.8;
        
        Cubic eos = EOS.pengRobinsonStryjekVera();
        ArrayList<Component> components = new ArrayList<>();
        components.add(methanol);
        components.add(water);
        
        HashMap<Component,Double> mixtureFractions= new HashMap<>();
        mixtureFractions.put(water, 0.45);
        mixtureFractions.put(methanol, 0.55);
        
        HashMap<Component,Double> liquidFractions= new HashMap<>();
        liquidFractions.put(water, 0.01);
        liquidFractions.put(methanol, 0.99);
        
        HashMap<Component,Double> vaporFractions= new HashMap<>();
        vaporFractions.put(water, 0.5);
        vaporFractions.put(methanol, 0.5);
                
        
        FlashSolution result = Flash.getFlash(pressure, 
                temperature, 
                mixtureFractions,
                vF,
                vaporFractions,
                liquidFractions,
                components, 
                eos,
                kinteraction,
                0.0001);
        
        
        double expVFResult = 0.899436263020161;
        double resultVF = result.getVF();
        
        assertEquals(expVFResult, resultVF,tol);
    }
}
