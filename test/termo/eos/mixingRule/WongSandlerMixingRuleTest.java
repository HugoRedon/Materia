/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.activityModel.NRTLActivityModel;
import termo.activityModel.WilsonActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.phase.Phase;
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class WongSandlerMixingRuleTest {
    
    Component ethane;
    Component propane;
    HashMap<PureSubstance, Double> fractions;
    HuronVidalMixingRule instance;
    InteractionParameter k = new ActivityModelBinaryParameter();
    
    ArrayList<Component> components = new ArrayList<>();
    PureSubstance ci;
    
    Cubic eos;
    
    public WongSandlerMixingRuleTest() {
	ethane = new Component();
	
	ethane.setName("Ethane");
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);
	
	propane = new Component();
	
	 propane.setName("Propane");
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setPrsvKappa(0.03136);
	
	components.add(ethane);
	components.add(propane);
	
	
	fractions = new HashMap();
	
	eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ci = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	PureSubstance cj = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
	
	fractions.put(ci, 0.3);
	fractions.put(cj, 0.7);
	
	
	 instance = new HuronVidalMixingRule(new WilsonActivityModel(),eos);
	
	
    }

    
    @Test 
    public void wongSandlerWilsonFugacity(){
	System.out.println("fugacity");
	double temperature = 298;
	double pressure = 101325;
	
	WilsonActivityModel w = new WilsonActivityModel();
	
	WongSandlerMixingRule ws = new WongSandlerMixingRule(w,eos);
	
	
	MixtureSubstance ms = new MixtureSubstance(eos, AlphaFactory.getStryjekAndVeraExpression(), ws, components, Phase.LIQUID, k);
	ms.setMolarFractions(fractions);
	ms.setTemperature(temperature);
	ms.setPressure(pressure);
	double expResult = 23.7019;//quien sabe
	double resutl = ms.calculateFugacity(ci);
	assertEquals(expResult, resutl,1e-3);
    }
    
    @Test
    public void wongSandlerNrtlFugacity(){
	System.out.println("fugacity");
	double temperature= 298;
	double pressure = 101325;
	
	NRTLActivityModel nrtl = new NRTLActivityModel();
	WongSandlerMixingRule ws = new WongSandlerMixingRule(nrtl, eos);
	
	MixtureSubstance ms = new MixtureSubstance(eos, AlphaFactory.getStryjekAndVeraExpression(), ws, components, Phase.LIQUID, k);
	ms.setTemperature(temperature);
	ms.setPressure(pressure);
	ms.setMolarFractions(fractions);
	double expResult = 23.7019;//quien sabe
	double resutl = ms.calculateFugacity(ci);
	assertEquals(expResult, resutl,1e-3);
    }
    
    
    @Test
    public void testA() {
	System.out.println("a");
	double temperature = 0.0;
	HashMap<PureSubstance, Double> fractions = null;
	InteractionParameter k = null;
	WongSandlerMixingRule instance = null;
	double expResult = 0.0;
	double result = instance.a(temperature, fractions, k);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testB() {
	System.out.println("b");
	HashMap<PureSubstance, Double> fractions = null;
	double temperature = 0.0;
	ActivityModelBinaryParameter params = null;
	WongSandlerMixingRule instance = null;
	double expResult = 0.0;
	double result = instance.b(fractions, temperature, params);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testOneOverNParcial_aN2RespectN() {
	System.out.println("oneOverNParcial_aN2RespectN");
	double temperature = 0.0;
	PureSubstance ci = null;
	HashMap<PureSubstance, Double> fractions = null;
	InteractionParameter k = null;
	WongSandlerMixingRule instance = null;
	double expResult = 0.0;
	double result = instance.oneOverNParcial_aN2RespectN(temperature, ci, fractions, k);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testTemperatureParcial_a() {
	System.out.println("temperatureParcial_a");
	double temperature = 0.0;
	ArrayList<Component> components = null;
	HashMap<Component, Double> fractions = null;
	HashMap<Component, Double> single_as = null;
	HashMap<Component, Double> single_bs = null;
	HashMap<Component, Double> alphaDerivatives = null;
	BinaryInteractionParameter k = null;
	WongSandlerMixingRule instance = null;
	double expResult = 0.0;
	double result = instance.temperatureParcial_a(temperature, components, fractions, single_as, single_bs, alphaDerivatives, k);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetL() {
	System.out.println("getL");
	WongSandlerMixingRule instance = null;
	double expResult = 0.0;
	double result = instance.getL();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testSetL() {
	System.out.println("setL");
	double L = 0.0;
	WongSandlerMixingRule instance = null;
	instance.setL(L);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}