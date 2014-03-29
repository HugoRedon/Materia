/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import termo.matter.HeterogeneousMixture;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousMixtureSubstanceTest {
    HeterogeneousMixture substance;
    Component ethane;
    Component propane;
    public HeterogeneousMixtureSubstanceTest(){
	 ethane = new Component("ethane");
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	 propane = new Component("propane");
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ArrayList<Component> components = new ArrayList();
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
	substance.bubbleTemperature(pressure);
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("bubbleTemperatureEstimate");
	
	double pressure = 101325;
	double expResult = 204.911544;
	substance.bubbleTemperatureEstimate(pressure);
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubblePressure() {
	System.out.println("bubblePressure");
	double temperature = 298;
	
	double expResult = 17.126777;
	substance.bubblePressure(temperature);
	double result = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);
	
    }

    @Test
    public void testBubblePressureEstimate() {
	System.out.println("bubblePressureEstimate");
	
	double temperature = 298;
	double expResult = 19.058900;
	
	substance.bubblePressureEstimate(temperature);
	double result  = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewTemperature() {
	System.out.println("dewTemperature");
	double pressure = 101325;
	double expResult =224.493492;
	substance.dewTemperature(pressure);
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("dewTemperatureEstimate");
	double pressure = 101325;
	double expResult = 223.967265;
	substance.dewTemperatureEstimate(pressure);
	double result = substance.getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("dewPressureEstimate");
	double temperature = 298;
	
	double expResult = 12.253971;
	substance.dewPressureEstimate(temperature);
	double result = substance.getPressure();
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewPressure() {
	System.out.println("dewPressure");
	double temperature = 298;
	double expResult = 12.584984;
	substance.dewPressure(temperature);
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
    
}