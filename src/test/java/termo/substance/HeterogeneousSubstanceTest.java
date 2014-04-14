/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import termo.matter.HeterogeneousSubstance;
import java.beans.PropertyChangeSupport;
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
public class HeterogeneousSubstanceTest {
    HeterogeneousSubstance substance;
    public HeterogeneousSubstanceTest() {
	
	Component ethane = new Component("ethane");
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);//en eqfases2 tiene un signo negativo ...
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	substance = new HeterogeneousSubstance(eos, alpha, ethane);
//	
//	substance.setCubicEquationOfState(eos);
//	substance.setAlpha(alpha);
//	substance.setComponent(ethane);
	
	
    }
    @Test public void testAlphaChangeForLiquidAndVaporWithPropertyChangeListener(){
        System.out.println("propertyChangeListener for alpha");
        HeterogeneousSubstance impl = new HeterogeneousSubstance(null, AlphaFactory.getAdachiAndLu(), null);
        PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
        mpcs.addPropertyChangeListener(impl);
        mpcs.firePropertyChange("alpha", AlphaFactory.getAdachiAndLu(), AlphaFactory.getPengAndRobinsonExpression());
        
        assertEquals(impl.getLiquid().getAlpha(), AlphaFactory.getPengAndRobinsonExpression());
    }
    
    
     /**
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperature() {
	System.out.println("getTemperature");
	double pressure =101325;
	
	substance.setPressure(pressure);
	substance.bubbleTemperature();
	double result = substance.getTemperature();
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
        substance.setTemperature(temperature);
	substance.bubblePressureEstimate();
	
	double result = substance.getPressure();
	
	double expResult = 41.57334499;
	assertEquals(expResult,result/101325,1e-3);
    }

    /**
     * Test of getTemperatureEstimate method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	substance.setPressure(pressure);
	substance.bubbleTemperatureEstimate();
	double result = substance.getTemperature();
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult,result,tolerance);
    }

    /**
     * Test of getPressure method, of class BubblePoint.
     */
    @Test
    public void testBubblePressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
        substance.setTemperature(temperature);
	substance.bubblePressure();
	double result = substance.getPressure();
	double tolerance =  1e-3;
	double expResult =41.432724;
	assertEquals(expResult, result/101325,tolerance);
    }
    
   @Test
    public void testDewTemperature() {
	System.out.println("getTemperature");
	
	double pressure =101325;
        substance.setPressure(pressure);
	substance.dewTemperature();	
	double result = substance.getTemperature();
	double tolerance = 1e-3;
	double expResult = 184.607519;
	
	assertEquals(expResult, result,tolerance);
    }
   
    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	
        substance.setPressure(pressure);
	substance.dewTemperatureEstimate();
	double result = substance.getTemperature();
	
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature =298;
        substance.setTemperature(temperature);
	substance.dewPressureEstimate();
	double result = substance.getPressure();
	double tolerance = 1e-3;
	double expResult = 41.57334499*101325;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testDewPressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
        substance.setTemperature(temperature);
	substance.dewPressure();
	double result = substance.getPressure();
	double tolerance =  1e-3;
	double expResult =41.432724;// 4.198171e6;
	assertEquals(expResult, result/101325,tolerance);
    }

}