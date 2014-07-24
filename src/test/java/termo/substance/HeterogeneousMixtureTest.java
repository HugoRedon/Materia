/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import termo.matter.HeterogeneousMixture;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.componentsForTests.ComponentsForTests;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousMixtureTest {
    HeterogeneousMixture substance;
    Compound ethane;
    Compound propane;
    public HeterogeneousMixtureTest(){
	 ethane = new Compound("ethane");
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	 propane = new Compound("propane");
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	Cubic eos = EquationsOfState.pengRobinson();
	Alpha alpha = Alphas.getStryjekAndVeraExpression();
	
	HashSet<Compound> components = new HashSet();
	components.add(ethane);
	components.add(propane);
	
	InteractionParameter k = new InteractionParameter();
//	calculate_b_cubicParameter.setValue(propane, ethane, 0, true);
	MixingRule mr = new VDWMixingRule();
        substance = new HeterogeneousMixture(eos, alpha, mr, components, k);
	
	
	
	substance.setZFraction(propane,0.7);
	substance.setZFraction(ethane, 0.3);
    }
 
    @Test
    public void testBubbleTemperature() {
	System.out.println("bubbleTemperature");
	double pressure = 101325;
	double expResult =205.780184;
        substance.setPressure(pressure);
	substance.bubbleTemperature();
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("bubbleTemperatureEstimate");
	
	double pressure = 101325;
	double expResult = 204.911544;
        substance.setPressure(pressure);
	substance.bubbleTemperatureEstimate();
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubblePressure() {
	System.out.println("bubblePressure");
	double temperature = 298;
	
	double expResult = 17.126777;
        substance.setTemperature(temperature);
	substance.bubblePressure();
	double result = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);
	
    }

    @Test
    public void testBubblePressureEstimate() {
	System.out.println("bubblePressureEstimate");
	
	double temperature = 298;
	double expResult = 19.058900;
	substance.setTemperature(temperature);
	substance.bubblePressureEstimate();
	double result  = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewTemperature() {
	System.out.println("dewTemperature");
	double pressure = 101325;
	double expResult =224.493492;
        substance.setPressure(pressure);
	substance.dewTemperature();
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("dewTemperatureEstimate");
	double pressure = 101325;
	double expResult = 223.967265;
        substance.setPressure(pressure);
	substance.dewTemperatureEstimate();
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("dewPressureEstimate");
	double temperature = 298;
	
	double expResult = 12.253971;
        substance.setTemperature(temperature);
	substance.dewPressureEstimate();
	double result = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewPressure() {
	System.out.println("dewPressure");
	double temperature = 298;
	double expResult = 12.584984;
        substance.setTemperature(temperature);
	substance.dewPressure();
	double result = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);

    }
    
    @Test
    public void testFlash(){
	System.out.println("flash");
	double temperature = 220;
	double pressure = 101325;
	double expResult = 0.553888;
	
//	double vFEstimate = 0.563536;
//	
//	
//	substance.liquid.setFraction(ethane, 0.0512682);
//	substance.liquid.setFraction(propane, 0.948732);
//	
//	substance.vapor.setFraction(ethane, 0.772672);
//	substance.vapor.setFraction(propane, 0.227328);
//	
	
	double result = substance.flash(temperature,pressure);
	assertEquals(expResult, result, 1e-3);
    }
//    @Test
//    public void testFlashEstimate(){
//	System.out.println("flashEstimate");
//	
//	double pressure = 101325;
//	double temperature = 220;
//	
//	double expResult = 0.563536;
//	
//	double result = substance.flashEstimate(temperature, pressure);
//	
//	assertEquals(expResult, result, 1e-3);
//    }
    
    
    @Test
    public void testInteractionParameterChange() {
	System.out.println("bubbleTemperature");
	double pressure = 101325;
	double expResult =205.780184;//no necesario en esta prueba pero se conserva para futuras referencias
        substance.setPressure(pressure);
	substance.bubbleTemperature();
        double result = substance.getTemperature();
        
        InteractionParameter interaction= new InteractionParameter();
        interaction.setValue(propane, ethane, 0.5);
        
        
        substance.setInteractionParameters(interaction);
        substance.bubbleTemperature();
        double result2 = substance.getTemperature();
	       System.out.println("result: "+result + " ,result2: " + result2);
        boolean equals = (Math.abs(result - result2) < 1e-6);
        
        boolean expected = false;
        
	assertEquals(expected, equals);
    }
    
//    @Test public void noEstimateTest(){
//         Compound water = ComponentsForTests.getWater();
//        Compound methanol = ComponentsForTests.getMethanol();
//
//        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
//        ArrayList<Component> components = new ArrayList<>();
//        components.add(water);
//        components.add(methanol);
//        MixingRule mr = new VDWMixingRule();
//        
//        InteractionParameter parameters = new InteractionParameter(true);
//        HeterogeneousMixture mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
//        
//        double pressure = 0.14991 * 101325;
//        mixture.setPressure(pressure);
//        mixture.setZFraction( methanol,0.1);
//        mixture.setZFraction(water, 0.9);
//        
//        mixture.bubbleTemperature();
//        double result = mixture.getTemperature();
//        
//        assertEquals(true,Double.isNaN(result));
//        
//    }
    
    @Test public void estimateDefined(){
            Compound water = ComponentsForTests.getWater();
        Compound methanol = ComponentsForTests.getMethanol();

        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getStryjekAndVeraExpression();
        HashSet<Compound> components = new HashSet<>();
        components.add(water);
        components.add(methanol);
        MixingRule mr = new VDWMixingRule();
        
        InteractionParameter parameters = new InteractionParameter(true);
        HeterogeneousMixture mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
        
        double pressure = 0.14991*101325;
        mixture.setPressure(pressure);
        mixture.setZFraction( methanol,0.1);
        mixture.setZFraction(water, 0.9);
        
        mixture.bubbleTemperature(189);
        Double result = mixture.getTemperature();
        
        assertEquals(false,Double.isNaN(result));
        
    }
}