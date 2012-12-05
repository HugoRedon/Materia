package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.CubicAlpha;
import termo.eos.EOS;

/**
 *
 * @author Hugo Redon Rivera
 */
public class DewPointTest {
    CubicAlpha prsv;
    Component methanol;
    Component water;
    ArrayList<Component> components2;
    double tol;
    double fractionsTol;
    
    BinaryInteractionParameters kinteraction;
    private DewPoint dewCalculator;
    
    public DewPointTest() {
    
        dewCalculator = new DewPoint();
        prsv = EOS.pengRobinsonStryjekVera();
        tol = 4;
        fractionsTol = 0.009;
        methanol = new Component(2, "Methanol", 0.572d, 79.94d, 512.6d, 0.118d);
        methanol.setPrsvk1(-0.03374);
        water = new Component(4, "Water", 0.344861d, 217.665927d, 647.13d, 0.0571d);
        water.setPrsvk1(-0.0767d);
        components2 = new ArrayList<>();
        components2.add(methanol);
        components2.add(water);
        kinteraction = new BinaryInteractionParameters();
        kinteraction.setValue(methanol, water, -0.0778, true);
    }
    
    /**
     * Test of getPressureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetPressureEstimate() {
        System.out.println("getPressureEstimate");
        double temperature = 345.45;
        
        HashMap<Component, Double> vaporFractions = new HashMap<>();
        vaporFractions.put(water, 0.4);
        vaporFractions.put(methanol, 0.6);
        
        EquilibriaPhaseSolution result = dewCalculator.getPressureEstimate(temperature,  vaporFractions);
        
        double pressureResult =  result.getPressure();
        HashMap<Component,Double> liquidFractions = result.getMixtureFractions();
        
        double xMethanol = liquidFractions.get(methanol);
        double xWater = liquidFractions.get(water);
        
        double expPressureResult = 0.695626358169442;
        double expxMethanol = 0.310861969376361;
        double expxWater =0.689138030623639;
        
        assertEquals(expPressureResult, pressureResult,tol);
        assertEquals(expxMethanol,xMethanol, fractionsTol);
        assertEquals(expxWater, xWater, fractionsTol);
        
    }

    /**
     * Test of getTemperature method, of class DewPoint.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        double temperature = 400;
        double pressure = 18.00148039008;
        ArrayList<Component> components = new ArrayList<>();
        components.add(water);
        
        HashMap<Component, Double> liquidFractions = null;
        HashMap<Component, Double> vaporFractions = null;
        Cubic eos = prsv;
        
        double tolerance = 0.0001;
        
        double expWaterResult = 480.665402275724;
        EquilibriaPhaseSolution result = dewCalculator.getTemperature(temperature, pressure, components, liquidFractions, vaporFractions, eos, kinteraction,tolerance);
        
        double resultWaterTemp = result.getTemperature();
        
        components.remove(water);
        components.add(methanol);
        result = dewCalculator.getTemperature(temperature, pressure, components, liquidFractions, vaporFractions, eos,kinteraction, tolerance);
        
        double expMethanolResult = 434.031439525355;
        double resultMethanolTemp = result.getTemperature();
        
        vaporFractions = new HashMap<>();
        vaporFractions.put(water, 0.7);
        vaporFractions.put(methanol, 0.3);
        
        liquidFractions = new HashMap<>();
        liquidFractions.put(water, .8);
        liquidFractions.put(methanol, 0.2);
        
        components.add(water);
        
        pressure = 42.66469283241;
        temperature = 500;
          result = dewCalculator.getTemperature(temperature, pressure, components,  vaporFractions,liquidFractions, eos,kinteraction, tolerance);
        
          double expMixtureResultTemp = 506.640937418684;
        double mixtureResultTemp = result.getTemperature();
        
        HashMap<Component,Double> xs = result.getSolutionFractions();
        double xwater = xs.get(water);
        double xmethanol = xs.get(methanol);
        
        double expxwater =0.930398017679116;
        double expxmethanol = 0.0696019823208843;
        
        assertEquals(expMethanolResult,resultMethanolTemp,tol);
        assertEquals(expWaterResult, resultWaterTemp,tol);     
        
        assertEquals(expMixtureResultTemp,mixtureResultTemp, tol);
        assertEquals(expxwater, xwater,0.07);
        assertEquals(expxmethanol,xmethanol,0.07);
    }
    
    @Test
    public void testGetPressure(){
        
         HashMap<Component, Double> liquidFractions = new HashMap<>();
         liquidFractions.put(water, 0.7);
         liquidFractions.put(methanol,0.3);
         
        HashMap<Component, Double> vaporFractions = new HashMap<>();
        vaporFractions.put(water, 0.44);
        vaporFractions.put(methanol, 0.56);
        
        double temperature = 456.765;
        double pressure = 20; 
        EquilibriaPhaseSolution result = dewCalculator.getPressure(temperature,pressure,  components2,  vaporFractions,liquidFractions, prsv,kinteraction, tol);
        
        double expPressureResult = 22.8741176309723;
        double expMethanolx = 0.274291341556221;
        double expWaterx = 0.725708658443779;
        
        double pressureResult = result.getPressure();
        double methanolx = result.getSolutionFractions().get(methanol);
        double waterx = result.getSolutionFractions().get(water);
        
        assertEquals(expWaterx, waterx,0.1);
        assertEquals(expMethanolx, methanolx,0.1);
        assertEquals(expPressureResult, pressureResult, tol);
    }
}
