package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.BinaryInteractionParameter;
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
public class MixtureSubstanceTest {
    MixtureSubstance substance ;
    
    public MixtureSubstanceTest() {
	substance = new MixtureSubstance();
	
	Component ethane = new Component();
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	PureSubstance ethanePure = new PureSubstance();
	//ethanePure.setCubicEquationOfState(eos);
	ethanePure.setAlpha(alpha);
	ethanePure.setComponent(ethane);
	
	Component propane = new Component();
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setPrsvKappa(0.03136);
	
	
	
	PureSubstance propanePure = new PureSubstance();
	//propanePure.setCubicEquationOfState(eos);
	propanePure.setAlpha(alpha);
	propanePure.setComponent(propane);
	
	
	substance.setCubicEquationOfState(eos);
	substance.addComponent(propanePure, 0.7);
	substance.addComponent(ethanePure, 0.3);
	
	
	InteractionParameter b = new InteractionParameter();
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
	double result = instance.calculate_b_cubicParameter(0);
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
    public void testSetBinaryParameters() {
	System.out.println("setBinaryParameters");
	BinaryInteractionParameter binaryParameters = null;
	MixtureSubstance instance = new MixtureSubstance();
	//instance.setBinaryParameters(binaryParameters);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

}