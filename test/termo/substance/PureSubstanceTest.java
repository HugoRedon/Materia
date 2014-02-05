package termo.substance;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class PureSubstanceTest {
     PureSubstance substance ; 
    public PureSubstanceTest() {
	Component ethane = new Component();
	
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(4879761.338);
	ethane.setPrsvKappa(-0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	substance = new PureSubstance();
	substance.setCubicEquationOfState(eos);
	substance.setAlpha(alpha);
	substance.setComponent(ethane);
    }

    @Test
    public void testCalculateFugacity() {
	System.out.println("calculateFugacity");
	double temperature = 0.0;
	double pressure = 0.0;
	Phase aPhase = null;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.calculateFugacity(temperature, pressure, aPhase);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testEquals() {
	System.out.println("equals");
	PureSubstance substance = null;
	PureSubstance instance = new PureSubstance();
	boolean expResult = false;
	boolean result = instance.equals(substance);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testToString() {
	System.out.println("toString");
	PureSubstance instance = new PureSubstance();
	String expResult = "";
	String result = instance.toString();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculateIdealGasEnthalpy() {
	System.out.println("calculateIdealGasEnthalpy");
	double temperature = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.calculateIdealGasEnthalpy(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testTemperatureParcial_a() {
	System.out.println("temperatureParcial_a");
	double temperature = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.temperatureParcial_a(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testOneOver_N_Parcial_a() {
	System.out.println("oneOver_N_Parcial_a");
	double temperature = 0.0;
	PureSubstance pureSubstance = null;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.oneOver_N_Parcial_a(temperature, pureSubstance);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculate_a_cubicParameter() {
	System.out.println("calculate_a_cubicParameter");
	double temperature = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.calculate_a_cubicParameter(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculate_b_cubicParameter() {
	System.out.println("calculate_b_cubicParameter");
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.calculate_b_cubicParameter();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetComponent() {
	System.out.println("getComponent");
	PureSubstance instance = new PureSubstance();
	Component expResult = null;
	Component result = instance.getComponent();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetComponent() {
	System.out.println("setComponent");
	Component component = null;
	PureSubstance instance = new PureSubstance();
	instance.setComponent(component);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetAlpha() {
	System.out.println("getAlpha");
	PureSubstance instance = new PureSubstance();
	Alpha expResult = null;
	Alpha result = instance.getAlpha();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetAlpha() {
	System.out.println("setAlpha");
	Alpha alpha = null;
	PureSubstance instance = new PureSubstance();
	instance.setAlpha(alpha);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculateIdealGasEntropy() {
	System.out.println("calculateIdealGasEntropy");
	double temperature = 0.0;
	double pressure = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.calculateIdealGasEntropy(temperature, pressure);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getTemperature method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperature() {
	System.out.println("getTemperature");
	
	double pressure =101325;
	double result = substance.bubbleTemperature(pressure);	
	double tolerance = 1e-3;
	double expResult = 184.607519;
	
	assertEquals(expResult, result,tolerance);
	
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
	double expResult = 41.57334499;
	assertEquals(expResult, result/101325,tolerance);
    }

    /**
     * Test of getTemperatureEstimate method, of class BubblePoint.
     */
    @Test
    public void testBubbleTemperatureEstimate() {
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
    public void testBubblePressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
	double result = substance.bubblePressure(temperature);
	double tolerance =  1e-3;
	double expResult =41.432724;
	assertEquals(expResult, result/101325,tolerance);
    }

    @Test
    public void testDewTemperature() {
	System.out.println("dewTemperature");
	double pressure = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.dewTemperature(pressure);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testDewTemperatureEstimate() {
	System.out.println("dewTemperatureEstimate");
	double pressure = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.dewTemperatureEstimate(pressure);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testDewPressureEstimate() {
	System.out.println("dewPressureEstimate");
	double temperature = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.dewPressureEstimate(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testDewPressure() {
	System.out.println("dewPressure");
	double temperature = 0.0;
	PureSubstance instance = new PureSubstance();
	double expResult = 0.0;
	double result = instance.dewPressure(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetAcentricFactorBasedVaporPressure() {
	System.out.println("getAcentricFactorBasedVaporPressure");
	double temperature = 298;
	
	double expResult = 41.57334499;
	double result = substance.getAcentricFactorBasedVaporPressure(temperature);
	assertEquals(expResult, result, 1e-3);
	
    }
}