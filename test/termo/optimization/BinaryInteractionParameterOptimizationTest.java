/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.optimization;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.Component;
import termo.componentsForTests.ComponentsForTests;
import termo.data.ExperimentalDataBinary;
import termo.eos.Cubic;
import termo.eos.EOS;
import termo.eos.alpha.Mathias_StryjekVera;

/**
 *
 * @author Chilpayate
 */
public class BinaryInteractionParameterOptimizationTest {
    
    ArrayList<ExperimentalDataBinary> experimental;
    Component methanol; 
    Component water;
    ArrayList<Component> components2 = new ArrayList();
    //BinaryExperimentalData data;
    
    public BinaryInteractionParameterOptimizationTest() {
        experimental =  new ArrayList<>();
        
        double pressure = 0.14991;
        
//        methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
//        methanol.setPrsvk1(-0.03374);
//        
//        water = new Component(4, "Water", 0.344861d, 217.665927d, 647.13d, 0.0571d);
//        water.setPrsvk1(-0.0767d);
//        
                methanol = ComponentsForTests.getMethanol();
        water = ComponentsForTests.getWater();
        
        
        experimental = new ArrayList<>();

        experimental.add(new ExperimentalDataBinary(0, methanol, water, 327.4, pressure, 0, 0));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,318.05, pressure, 0.1, 0.433386));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,312.4, pressure, 0.2, 0.62119));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,308.55, pressure, 0.3, 0.725289));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,305.7, pressure, 0.4, 0.79242));     
        experimental.add(new ExperimentalDataBinary(0, methanol, water,303.5, pressure, 0.5, 0.840656));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,301.7, pressure, 0.6, 0.87857));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,300.1, pressure, 0.7, 0.910835));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,298.7, pressure, 0.8, 0.940365));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,297.4, pressure, 0.9, 0.969404));
        experimental.add(new ExperimentalDataBinary(0, methanol, water,296.1, pressure, 1, 1));

        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     @Test
     public void testOptimizeBinaryInteractionParameter() {
         VDWBinaryParameterOptimizer optimizer = new VDWBinaryParameterOptimizer();
         Cubic  eos = EOS.pengRobinsonStryjekVera();
         
         double expectedResult = -0.0778;
        double k12 =   optimizer.optimizeParameter( eos,  experimental);
        
       assertEquals(expectedResult,k12, 0.01);
     }
}
