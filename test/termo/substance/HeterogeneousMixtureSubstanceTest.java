/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

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

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousMixtureSubstanceTest {
    HeterogeneousMixtureSubstance substance;
    Component ethane;
    Component propane;
    public HeterogeneousMixtureSubstanceTest(){
	 ethane = new Component();
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);
	
	 propane = new Component();
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setPrsvKappa(0.03136);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ArrayList<Component> components = new ArrayList();
	components.add(ethane);
	components.add(propane);
	
	InteractionParameter b = new InteractionParameter();
	b.setValue(propane, ethane, 0, true);
	MixingRule mr = new VDWMixingRule();
	
	substance = new HeterogeneousMixtureSubstance(eos,alpha,mr,components);
	
	substance.setZFraction(propane,0.7);
	substance.setZFraction(ethane, 0.3);
    }
 
    @Test
    public void testBubbleTemperature() {
	System.out.println("bubbleTemperature");
	double pressure = 101325;
	double expResult =205.780184;
	double result = substance.bubbleTemperature(pressure).getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("bubbleTemperatureEstimate");
	
	double pressure = 101325;
	double expResult = 204.911544;
	double result = substance.bubbleTemperatureEstimate(pressure);
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubblePressure() {
	System.out.println("bubblePressure");
	double temperature = 298;
	
	double expResult = 17.126777;
	double result = substance.bubblePressure(temperature);
	assertEquals(expResult, result/101325, 1e-3);
	
    }

    @Test
    public void testBubblePressureEstimate() {
	System.out.println("bubblePressureEstimate");
	
	double temperature = 298;
	double expResult = 19.058900;
	double result = substance.bubblePressureEstimate(temperature);
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewTemperature() {
	System.out.println("dewTemperature");
	double pressure = 101325;
	double expResult =224.493492;
	double result = substance.dewTemperature(pressure);
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("dewTemperatureEstimate");
	double pressure = 101325;
	double expResult = 223.967265;
	double result = substance.dewTemperatureEstimate(pressure);
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("dewPressureEstimate");
	double temperature = 298;
	
	double expResult = 12.253971;
	double result = substance.dewPressureEstimate(temperature);
	assertEquals(expResult, result/101325, 1e-3);

    }

    @Test
    public void testDewPressure() {
	System.out.println("dewPressure");
	double temperature = 298;
	double expResult = 12.584984;
	double result = substance.dewPressure(temperature);
	assertEquals(expResult, result/101325, 1e-3);

    }
    
    @Test
    public void testFlash(){
	System.out.println("flash");
	double temperature = 220;
	double pressure = 101325;
	double expResult = 0.553888;
	
	double vFEstimate = 0.563536;
	
	
	substance.liquid.setFraction(ethane, 0.0512682);
	substance.liquid.setFraction(propane, 0.948732);
	substance.vapor.setFraction(ethane, 0.772672);
	substance.vapor.setFraction(propane, 0.227328);
	
	
	double result = substance.flash(temperature,pressure,vFEstimate);
	assertEquals(expResult, result, 1e-3);
    }
    
}