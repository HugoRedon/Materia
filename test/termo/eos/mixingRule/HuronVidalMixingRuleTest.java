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
import termo.component.VanDerWaalsParameters;
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
public class HuronVidalMixingRuleTest {
    Component ethane;
    Component propane;
    HashMap<PureSubstance, Double> fractions;
    HuronVidalMixingRule instance;
    InteractionParameter k = new ActivityModelBinaryParameter();
    
    ArrayList<Component> components = new ArrayList<>();
    PureSubstance ethanePure;
    PureSubstance propanePure;
    
    Cubic eos;
    
    
    public final void createComponents(){
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
	
    }
//    public final void createPureSubstaces(){
//	eos = EquationOfStateFactory.pengRobinsonBase();
//	
//	ethanePure = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
//	propanePure = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
//    }
    
    public HuronVidalMixingRuleTest() {
	
	createComponents();
//	createPureSubstaces();
	eos =EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	ethanePure = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	
	
	instance = new HuronVidalMixingRule(eos, alpha, components, Phase.VAPOR, k, new WilsonActivityModel());
	instance.setBinaryParameters(k);
	instance.setFraction(propane, 0.7);
	instance.setFraction(ethane, 0.3);
    }

    @Test
    public void testA() {
	System.out.println("a");
	double temperature = 298;

//	for(PureSubstance pure: fractions.keySet()){
//	    pure.setTemperature(temperature);
//	}
	instance.setTemperature(temperature);
	double expResult = 9.70355653e5;
	double result = instance.calculate_a_cubicParameter();
	assertEquals(expResult, result, 1e-3);

    }

    @Test
    public void testB() {
//	System.out.println("b");
//	HashMap<PureSubstance, Double> fractions = null;
//	HuronVidalMixingRule instance = null;
//	double expResult = 0.0;
//	double result = instance.calculate_b_cubicParameter(fractions, 0,null);
//	assertEquals(expResult, result, 0.0);
//	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testOneOverNParcial_aN2RespectN() {
	System.out.println("oneOverNParcial_aN2RespectN");
	double temperature = 298;
	

	    
	double expResult = 0.0;
	instance.setTemperature(temperature);
	ethanePure.setTemperature(temperature);
	double result = instance.oneOver_N_Parcial_a( ethanePure);
	assertEquals(expResult, result, 1e-3);

    }
    
    
    @Test
    public void vdwFugacitiy(){
//	VDWMixingRule vdw = new VDWMixingRule();
//	//InteractionParameter k = new InteractionParameter();
//	
//	vdw.setBinaryParameters(k);
//	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
//	Cubic cubic = EquationOfStateFactory.pengRobinsonBase();
//	
//	
//	PureSubstance ethanePure = new PureSubstance(eos, alpha, ethane, Phase.LIQUID);
//	PureSubstance propanePure = new PureSubstance(eos,alpha,propane,Phase.LIQUID);
//	
//	
//	vdw.setCubicEquationOfState(cubic);
//	vdw.addComponent(ethanePure, 0.3);
//	vdw.addComponent(propanePure, 0.7);
//	
//	
//	vdw.setTemperature(298);
//	vdw.setPressure(101325);
//	
//	double expResult = 0;
//	double result = vdw.calculateFugacity(ethane);
//		
	fail();
    }
    
    
    @Test public void huronFugacity(){
//	InteractionParameter k = new ActivityModelBinaryParameter();
//	
//	MixtureSubstance ms = new MixtureSubstance(EquationOfStateFactory.pengRobinsonBase(), AlphaFactory.getStryjekAndVeraExpression(), instance, components,Phase.LIQUID,k);
//	
//	//ms.setMolarFractions(fractions);
//	ms.setFraction(ethane, 0.3);
//	ms.setFraction(propane, 0.7);
//	
	instance.setTemperature(298);
	instance.setPressure(101325);
	instance.setPhase(Phase.LIQUID);
	double expResult = 24.8948;
	
	double result = instance.calculateFugacity(ethane);
	assertEquals(expResult, result,1e-3);
//	fail();
    }
    
    
    @Test public void nrtlHuronFugacity(){
//	InteractionParameter k = new ActivityModelBinaryParameter();
//	NRTLActivityModel nrtl = new NRTLActivityModel();
//	
//	
//	HuronVidalMixingRule hv = new HuronVidalMixingRule(nrtl, eos);
//	
	NRTLActivityModel nrtl = new NRTLActivityModel();
	MixtureSubstance ms = new HuronVidalMixingRule(EquationOfStateFactory.pengRobinsonBase(), AlphaFactory.getStryjekAndVeraExpression(), components, Phase.LIQUID, k,nrtl);
//	
//	//ms.setMolarFractions(fractions);
	ms.setFraction(ethane, 0.3);
	ms.setFraction(propane, 0.7);
//	
	ms.setTemperature(298);
	ms.setPressure(101325);
	double expResult = 25.3062;
	double result = ms.calculateFugacity(ethane);
	
	assertEquals(expResult, result, 1e-3);
//	fail();
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
	InteractionParameter k = null;
	HuronVidalMixingRule instance = null;
	double expResult = 0.0;
//	double result = instance.temperatureParcial_a(temperature, components, fractions, single_as, single_bs, alphaDerivatives, k);
//	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testGetL() {
	System.out.println("getL");
	HuronVidalMixingRule instance = null;
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
	HuronVidalMixingRule instance = null;
	instance.setL(L);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}