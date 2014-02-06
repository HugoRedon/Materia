package termo.substance;

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
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;

/**
 *
 * @author
 * Hugo
 */
public class MixtureSubstanceTest {
    MixtureSubstance substance ;
    
    public MixtureSubstanceTest() {
	substance = new MixtureSubstance();
	
	Component ethane = new Component();
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(-0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	PureSubstance ethanePure = new PureSubstance();
	ethanePure.setCubicEquationOfState(eos);
	ethanePure.setAlpha(alpha);
	ethanePure.setComponent(ethane);
	
	Component propane = new Component();
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setPrsvKappa(-0.03136);
	
	
	
	PureSubstance propanePure = new PureSubstance();
	propanePure.setCubicEquationOfState(eos);
	propanePure.setAlpha(alpha);
	propanePure.setComponent(propane);
	
	
	
	substance.addComponent(propanePure, 0.7);
	substance.addComponent(ethanePure, 0.3);
	
	
	BinaryInteractionParameter b = new BinaryInteractionParameter();
	b.setValue(propane, ethane, 0, true);
	MixingRule mr = new VDWMixingRule();
	
	
	substance.setBinaryParameters(b);
	//substance.setCubicEquationOfState(eos);
	substance.setMixingRule(mr);
    }

    @Test
    public void testAddComponent() {
	System.out.println("addComponent");
	PureSubstance pureSubstance = null;
	double molarFraction = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	instance.addComponent(pureSubstance, molarFraction);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testRemoveComponent() {
	System.out.println("removeComponent");
	PureSubstance pureSubstance = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.removeComponent(pureSubstance);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetCubicEquationOfState() {
	System.out.println("setCubicEquationOfState");
	Cubic cubic = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.setCubicEquationOfState(cubic);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSingle_as() {
	System.out.println("single_as");
	double aTemperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.single_as(aTemperature);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSingle_bs() {
	System.out.println("single_bs");
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.single_bs();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSingle_Alphas() {
	System.out.println("single_Alphas");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.single_Alphas(temperature);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testAlphaDerivatives() {
	System.out.println("alphaDerivatives");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.alphaDerivatives(temperature);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testTemperatureParcial_a() {
	System.out.println("temperatureParcial_a");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.temperatureParcial_a(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculate_a_cubicParameter() {
	System.out.println("calculate_a_cubicParameter");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.calculate_a_cubicParameter(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetComponents() {
	System.out.println("getComponents");
	MixtureSubstance instance = new MixtureSubstance();
	ArrayList expResult = null;
	ArrayList result = instance.getComponents();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetFractions() {
	System.out.println("getFractions");
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.getFractions();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculateIdealGasEnthalpy() {
	System.out.println("calculateIdealGasEnthalpy");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.calculateIdealGasEnthalpy(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculate_b_cubicParameter() {
	System.out.println("calculate_b_cubicParameter");
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.calculate_b_cubicParameter();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testCalculateIdealGasEntropy() {
	System.out.println("calculateIdealGasEntropy");
	double temperature = 0.0;
	double pressure = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.calculateIdealGasEntropy(temperature, pressure);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testOneOver_N_Parcial_a() {
	System.out.println("oneOver_N_Parcial_a");
	double temperature = 0.0;
	PureSubstance pureSubstance = null;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.oneOver_N_Parcial_a(temperature, pureSubstance);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetMixingRule() {
	System.out.println("getMixingRule");
	MixtureSubstance instance = new MixtureSubstance();
	MixingRule expResult = null;
	MixingRule result = instance.getMixingRule();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetMixingRule() {
	System.out.println("setMixingRule");
	MixingRule mixingRule = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.setMixingRule(mixingRule);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetPureSubstances() {
	System.out.println("getPureSubstances");
	MixtureSubstance instance = new MixtureSubstance();
	ArrayList expResult = null;
	ArrayList result = instance.getPureSubstances();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetPureSubstances() {
	System.out.println("setPureSubstances");
	ArrayList<PureSubstance> pureSubstances = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.setPureSubstances(pureSubstances);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetMolarFractions() {
	System.out.println("getMolarFractions");
	MixtureSubstance instance = new MixtureSubstance();
	HashMap expResult = null;
	HashMap result = instance.getMolarFractions();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetMolarFractions() {
	System.out.println("setMolarFractions");
	HashMap<PureSubstance, Double> molarFractions = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.setMolarFractions(molarFractions);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetBinaryParameters() {
	System.out.println("getBinaryParameters");
	MixtureSubstance instance = new MixtureSubstance();
	BinaryInteractionParameter expResult = null;
	BinaryInteractionParameter result = instance.getBinaryParameters();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetBinaryParameters() {
	System.out.println("setBinaryParameters");
	BinaryInteractionParameter binaryParameters = null;
	MixtureSubstance instance = new MixtureSubstance();
	instance.setBinaryParameters(binaryParameters);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testBubbleTemperature() {
	System.out.println("bubbleTemperature");
	fail("The test case is a prototype.");
    }

    @Test
    public void testBubbleTemperatureEstimate() {
	System.out.println("bubbleTemperatureEstimate");
	
	double pressure = 101325;
	double expResult = 204.911544;
	double result = substance.bubbleTemperatureEstimate(pressure).getTemperature();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void testBubblePressure() {
	System.out.println("bubblePressure");
	double temperature = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.bubblePressure(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
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
	double pressure = 0.0;
	MixtureSubstance instance = new MixtureSubstance();
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
	MixtureSubstance instance = new MixtureSubstance();
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
	MixtureSubstance instance = new MixtureSubstance();
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
	MixtureSubstance instance = new MixtureSubstance();
	double expResult = 0.0;
	double result = instance.dewPressure(temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}