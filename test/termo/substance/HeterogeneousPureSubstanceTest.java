/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousPureSubstanceTest {
    HeterogeneousPureSubstance substance;
    public HeterogeneousPureSubstanceTest() {
	
	Component ethane = new Component();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);//en eqfases2 tiene un signo negativo ...
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	substance = new HeterogeneousPureSubstance(eos, alpha, ethane);
//	
//	substance.setCubicEquationOfState(eos);
//	substance.setAlpha(alpha);
//	substance.setComponent(ethane);
	
	
    }

     /**
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperature() {
	System.out.println("getTemperature");
	double pressure =101325;
	double result = substance.bubbleTemperature(pressure).getTemperature();	
	double expResult = 184.607519;
	assertEquals(expResult, result, 1e-3);
	
    }
    
     /**
     * Test of getPressureEstimate method, of class BubblePoint.
     */
    @Test
    public void testBubblePressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature =298;
	double result = substance.bubblePressureEstimate(temperature);
	double tolerance = 1e-3;
	double expResult = 41.57334499*101325;
	assertEquals(expResult, result,tolerance);
    }

    /**
     * Test of getTemperatureEstimate method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	double result = substance.bubbleTemperatureEstimate(pressure).getTemperature();
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult, result,tolerance);
    }

    /**
     * Test of getPressure method, of class BubblePoint.
     */
    @Test
    public void testBubblePressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
	double result = substance.bubblePressure(temperature).getPressure();
	double tolerance =  1e-3;
	double expResult =41.432724;
	assertEquals(expResult, result/101325,tolerance);
    }
    
   @Test
    public void testDewTemperature() {
	System.out.println("getTemperature");
	
	double pressure =101325;
	double result = substance.dewTemperature(pressure).getTemperature();	
	double tolerance = 1e-3;
	double expResult = 184.607519;
	
	assertEquals(expResult, result,tolerance);
    }
   
    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	double result = substance.dewTemperatureEstimate(pressure).getTemperature();
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature =298;
	double result = substance.dewPressureEstimate(temperature).getPressure();
	double tolerance = 1e-3;
	double expResult = 41.57334499*101325;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testDewPressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
	double result = substance.dewPressure(temperature).getPressure();
	double tolerance =  1e-3;
	double expResult =41.432724;// 4.198171e6;
	assertEquals(expResult, result/101325,tolerance);
    }

}