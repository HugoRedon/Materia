/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;

/**
 *
 * @author
 * Hugo
 */
public class BubblePointTest {
    
    public BubblePointTest() {
    }

    /**
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testGetTemperature() {
	System.out.println("getTemperature");
	double temperature = 0.0;
	double pressure = 0.0;
	ArrayList<Component> components = null;
	HashMap<Component, Double> liquidFractions = null;
	HashMap<Component, Double> vaporFractions = null;
	Cubic eos = null;
	BinaryInteractionParameter kinteraction = null;
	double tolerance = 0.0;
	BubblePoint instance = new BubblePoint();
	EquilibriaPhaseSolution expResult = null;
	EquilibriaPhaseSolution result = instance.getTemperature(temperature, pressure, components, liquidFractions, vaporFractions, eos, kinteraction, tolerance);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getPressureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetPressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature = 0.0;
	HashMap<Component, Double> liquidFractions = null;
	BubblePoint instance = new BubblePoint();
	EquilibriaPhaseSolution expResult = null;
	EquilibriaPhaseSolution result = instance.getPressureEstimate(temperature, liquidFractions);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getTemperatureEstimate method, of class BubblePoint.
     */
    @Test
    public void testGetTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure = 0.0;
	HashMap<Component, Double> liquidFractions = null;
	BubblePoint instance = new BubblePoint();
	EquilibriaPhaseSolution expResult = null;
	EquilibriaPhaseSolution result = instance.getTemperatureEstimate(pressure, liquidFractions);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
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