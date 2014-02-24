package termo.eos.mixingRule;

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
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class VDWMixingRuleTest {
    VDWMixingRule rule ; 
    HashMap<PureSubstance, Double> fractions;
    InteractionParameter b;
    public VDWMixingRuleTest() {
	rule = new VDWMixingRule();
	
	
	Component ethane = new Component( );
	
	ethane.setName("ethane");
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	PureSubstance ethanePure = new PureSubstance();
	ethanePure.setCubicEquationOfState(eos);
	ethanePure.setAlpha(alpha);
	ethanePure.setComponent(ethane);
	
	Component propane = new Component();
	propane.setName("propane");
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setPrsvKappa(0.03136);
	
	
	
	PureSubstance propanePure = new PureSubstance();
	propanePure.setCubicEquationOfState(eos);
	propanePure.setAlpha(alpha);
	propanePure.setComponent(propane);
	
	fractions = new HashMap();
	
	fractions.put(propanePure, 0.7);
	fractions.put(ethanePure, 0.3);
	
	
	 b = new InteractionParameter(true);
	b.setValue(propane, ethane, 0.05);
	MixingRule mr = new VDWMixingRule();
	
	

    }

    @Test
    public void testA() {
	System.out.println("a");
	double temperature =298;

	for(PureSubstance pure: fractions.keySet()){
	    pure.setTemperature(temperature);
	}
	
	double expResult = 950499.221;
	double result = rule.a(temperature, fractions, b);
	assertEquals(expResult, result, 1e-3);
	
    }

    @Test
    public void testB() {
//	System.out.println("b");
//	HashMap<Component, Double> singleBs = null;
//	ArrayList<Component> components = null;
//	HashMap<Component, Double> fractions = null;
//	VDWMixingRule instance = new VDWMixingRule();
//	double expResult = 0.0;
//	double result = instance.b(singleBs, components, fractions);
//	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testTemperatureParcial_a() {
	System.out.println("temperatureParcial_a");
	double temperature = 350;
	
	for(PureSubstance pure: fractions.keySet()){
	    pure.setTemperature(temperature);
	}
	
	
	InteractionParameter k = new InteractionParameter();
	VDWMixingRule instance = new VDWMixingRule();
	double expResult = 0.0;
	double result = instance.temperatureParcial_a(temperature, fractions, k);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testOneOverNParcial_aN2RespectN() {
	System.out.println("oneOverNParcial_aN2RespectN");
	double temperature = 0.0;
	PureSubstance iComponent = null;
	HashMap<PureSubstance, Double> fractions = null;
	InteractionParameter k = null;
	VDWMixingRule instance = new VDWMixingRule();
	double expResult = 0.0;
	double result = instance.oneOverNParcial_aN2RespectN(temperature, iComponent, fractions, k);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}