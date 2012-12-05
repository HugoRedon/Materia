package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.eos.CubicAlpha;
import termo.eos.EOS;
import termo.eos.alpha.Mathias_StryjekVera;

/**
 *
 * @author Hugo Redon Rivera
 */
public class BubblePointTest {
    
       CubicAlpha prsv;
       Component methanol;
       Component water;
           ArrayList<Component> components2;
           
           double fractionsTol;
           double tol;
           
           BinaryInteractionParameters kinteraction;
    BubblePoint bubbleCalculator;
    
    public BubblePointTest() {
        prsv = EOS.pengRobinsonStryjekVera();
        bubbleCalculator = new BubblePoint();
        fractionsTol = 0.09;
        tol = 3;
       // rksm = new RedlichKwongSoave(new Mathias_StryjekVera());
        methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        methanol.setPrsvk1(0.39379);
        
        water = new Component(4, "Water", 0.344861d, 217.665927d, 647.13d, 0.0571d);
        water.setPrsvk1(-7.66990e-002);
        
        components2 = new ArrayList<>();
        components2.add(methanol);
        components2.add(water);
        
//        BinaryInteractionParameters.setK(methanol, water, -0.0778);
//        BinaryInteractionParameters.setK(water, methanol, -0.0778);
         kinteraction = new BinaryInteractionParameters();
        kinteraction.setValue(methanol, water, -0.0778, true);
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
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        double temperature =400;
        double pressure =12.17863311478 ;
        ArrayList<Component> components = new ArrayList<>();
        components.add(water);
        HashMap<Component, Double> liquidFractions = null;
        HashMap<Component, Double> vaporFractions = null;
        
        double expResult = 462.35220927919;
        EquilibriaPhaseSolution result = bubbleCalculator.getTemperature(temperature,
                pressure, 
                components,
                liquidFractions,
                vaporFractions,
                prsv,
                kinteraction,
                0.00001);
        double resultTemp = result.getTemperature();
        assertEquals(expResult, resultTemp, tol);
      
    }

    /**
     * Test of getPressureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetPressureEstimate() {
        System.out.println("getPressureEstimate");
        double temperature = 345.45;
        
        HashMap<Component, Double> liquidFractions = new HashMap<>();
        liquidFractions.put(water, 0.4);
        liquidFractions.put(methanol, 0.6);
        
        EquilibriaPhaseSolution result = bubbleCalculator.getPressureEstimate(temperature,  liquidFractions);
        
        
        double pressureResult =  result.getPressure();
        HashMap<Component,Double> vaporFractions = result.getSolutionFractions();
        
        double yMethanol = vaporFractions.get(methanol);
        double yWater = vaporFractions.get(water);
        
        double expPressureResult = 0.967090615253736;
        double expyMethanol = 0.832997633270477;
        double expyWater =0.167002366729523;
        
        assertEquals(expPressureResult, pressureResult,tol);
        assertEquals(expyMethanol,yMethanol, tol);
        assertEquals(expyWater, yWater, tol);
        
    }

    /**
     * Test of getPressure method, of class BubblePoint.
     */
    @Test
    public void testGetPressure() {
        System.out.println("getPressure");
        double temperature =425.87;
        
        HashMap<Component, Double> liquidFractions = new HashMap<>();
        liquidFractions.put(methanol, 1.0);
        liquidFractions.put(water, 0.0);
       
        
        double p = 1;
        HashMap<Component, Double> ys = new HashMap<>();
        
        ys.put(water, 0.0);
        ys.put(methanol, 1.0);
        
        double tolerance = 0.00001;
        
        EquilibriaPhaseSolution result = bubbleCalculator.getPressure(temperature, p,  components2, liquidFractions,ys, prsv,kinteraction, tolerance);
        
        
        double pressureResult = result.getPressure();
        
        HashMap<Component,Double> vaporFractions = result.getSolutionFractions();
        
        double resultYMethanol = vaporFractions.get(methanol);
        double resultYWater = vaporFractions.get(water);
        
        
        liquidFractions.put(methanol, 0.0);
        liquidFractions.put(water, 1.0);
        ys.put(water, 1.0);
        ys.put(methanol, 0.0);
        
        EquilibriaPhaseSolution result2 = bubbleCalculator.getPressure(temperature, p,  components2, liquidFractions,ys, prsv, kinteraction,tolerance);
        
        double pressureResult2 = result2.getPressure();
        
        HashMap<Component,Double> vaporFractions2 = result2.getSolutionFractions();
        
        double result2YMethanol = vaporFractions2.get(methanol);
       double result2YWater = vaporFractions2.get(water);
       
        liquidFractions.put(methanol, 0.5);
        liquidFractions.put(water, 0.5);
        ys.put(water, 0.3);
        ys.put(methanol, 0.7);
        
        EquilibriaPhaseSolution result3 = bubbleCalculator.getPressure(temperature, p, components2, liquidFractions, ys, prsv, kinteraction,tolerance);
        
        double pressureResult3 = result3.getPressure();
        
        HashMap<Component,Double> vaporFractions3 =result3.getSolutionFractions();
        
        double result3YMethanol = vaporFractions3.get(methanol);
       double result3YWater = vaporFractions3.get(water);
        
        
        double expPressureMethanolResult =14.9142600607065;
        double expYMethanolResult =1.0;
         double expYWaterResult = 0.0;
         
         double expPressureWaterResult =4.98457194631296;
        double expYMethanolResult2 =0.0;
         double expYWaterResult2 = 1.0;
         
         double expPressureResult3 =10.9596802615024;
        double expYMethanolResult3 =0.708317028792769;
         double expYWaterResult3 = 0.291682971207231;
         
       assertEquals(expPressureMethanolResult, pressureResult, tol);
        assertEquals(expYMethanolResult,resultYMethanol, tol);
        assertEquals(expYWaterResult,resultYWater, tol);
        
        assertEquals(expPressureWaterResult, pressureResult2, tol);
        assertEquals(expYMethanolResult2,result2YMethanol, tol);
        assertEquals(expYWaterResult2,result2YWater, tol);
        
        assertEquals(expPressureResult3, pressureResult3, tol);
        assertEquals(expYMethanolResult3,result3YMethanol, 0.01);
        assertEquals(expYWaterResult3,result3YWater, 0.01);
       
    }
    
    
    @Test 
    public void testTemperatureDiagram(){
        double pressure = 0.14991;
        
        BinaryInteractionParameters k = new BinaryInteractionParameters();
        k.setValue(methanol, water, -0.180000007152557, true);
        
        int  numberCalculations = 10;
        double [][] expResult = {
          //z                          y                                                          T[K]
            {0,	                    0		                    ,327.0666962},
            {0.1,	0.156250778		,326.1462841},
            {0.2,	0.359451832		,323.8869765},
            {0.3,	0.547858019		,320.8555452,},
            {0.4,	0.694861589		,317.6223334},
            {0.5,	0.800254127		,314.5364857},
            {0.6,	0.873200144		,311.756577},
            {0.7,	0.923327854		,309.325045},
            {0.8,	0.958029948		,307.2282632},
            {0.9,	0.982456809		,305.4271805},
            {1,                   	1		                    ,303.8763123}
  
        };
        
        
        ArrayList<EquilibriaPhaseSolution> diagram = bubbleCalculator.getTemperatureDiagram( pressure, methanol,water , k,  prsv, numberCalculations,tol); 
        
        
        for(EquilibriaPhaseSolution  sol : diagram){
            int index = diagram.indexOf(sol);
            double[] zyT = expResult[index];
            
            assertEquals(zyT[0], sol.getMixtureFractions().get(methanol),tol);
            assertEquals(zyT[1], sol.getSolutionFractions().get(methanol),tol);
            assertEquals(zyT[2], sol.getTemperature(),tol);
            
        }
        
    }

//    /**
//     * Test of getTemperatureEstimate method, of class BubblePoint.
//     */
//    @Test
//    public void testGetTemperatureEstimate() {
//        System.out.println("getTemperatureEstimate");
//        double pressure = 0.0;
//        HashMap<Component, Double> liquidFractions = null;
//        BubbleTemperatureSolution expResult = null;
//        BubbleTemperatureSolution result = BubblePoint.getTemperatureEstimate(pressure, liquidFractions);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
