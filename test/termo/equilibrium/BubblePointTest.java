
package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.substance.PureSubstance;
import termo.substance.Substance;

/**
 *
 * @author
 * Hugo
 */
public class BubblePointTest {
    PureSubstance substance ; 
    public BubblePointTest() {
	Component ethane = new Component();
	
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(4.8797134e6);
	ethane.setPrsvKappa(-0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	substance = new PureSubstance();
	substance.setCubicEquationOfState(eos);
	substance.setAlpha(alpha);
	substance.setComponent(ethane);
    }

    /**
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testGetTemperature() {
	System.out.println("getTemperature");
	
	double pressure =101325;
	double result = substance.bubbleTemperature(pressure);	
	double tolerance = 1e-1;
	double expResult = 184.607519;
	
	assertEquals(expResult, result,tolerance);
	
    }

    /**
     * Test of getPressureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetPressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature =298;
	Component ethane = new Component();
	
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(4.8797134e6);
	ethane.setPrsvKappa(-0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	PureSubstance asubstance = new PureSubstance();
	asubstance.setCubicEquationOfState(eos);
	asubstance.setAlpha(alpha);
	asubstance.setComponent(ethane);
	
	
	double result = asubstance.bubblePressureEstimate(temperature);
	double tolerance = 1e-2;
	double expResult = 4.21241675e6;
	assertEquals(expResult, result,tolerance);
    }

    /**
     * Test of getTemperatureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	double result = substance.bubbleTemperatureEstimate(pressure);
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult, result,tolerance);
    }

    /**
     * Test of getPressure method, of class BubblePoint.
     */
    @Test
    public void testGetPressure() {
	System.out.println("getPressure");
	double temperature = 0.0;
	double p = 0.0;
	ArrayList<Component> components = null;
	HashMap<Component, Double> liquidFractions = null;
	HashMap<Component, Double> vaporFractions = null;
	Cubic eos = null;
	BinaryInteractionParameter kinteraction = null;
	double tolerance = 0.0;
	BubblePoint instance = new BubblePoint();
	EquilibriaPhaseSolution expResult = null;
	EquilibriaPhaseSolution result = instance.getPressure(temperature, p, components, liquidFractions, vaporFractions, eos, kinteraction, tolerance);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}