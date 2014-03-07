/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.HuronVidalMixingRule;
import termo.phase.Phase;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class NRTLActivityModelTest {
	Component ethane ;
	Component propane;
	HashMap<PureSubstance,Double> fractions =new HashMap();
	ArrayList<Component> components = new ArrayList();
	Cubic eos;
	Alpha alpha;
	
	PureSubstance ci ;
	
	HuronVidalMixingRule hv ;
    public NRTLActivityModelTest() {
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
	
	
	
	
	eos = EquationOfStateFactory.pengRobinsonBase();
	alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ci = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	PureSubstance cj = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
	
	fractions.put(ci, 0.3);
	fractions.put(cj, 0.7);
	
	
//	 hv = new HuronVidalMixingRule(new WilsonActivityModel(),eos);
	
	
    }

    @Test
    public void testExcessGibbsEnergy() {
	System.out.println("excessGibbsEnergy");
	
	ActivityModelBinaryParameter param = new ActivityModelBinaryParameter();
	double temperature = 298;
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 0.0;
	double result = instance.excessGibbsEnergy(fractions, param, temperature);
	assertEquals(expResult, result, 1e-3);
	
    }

    @Test
    public void testActivityCoefficient() {
	System.out.println("activityCoefficient");
	
	ActivityModelBinaryParameter k = new ActivityModelBinaryParameter();
	double temperature = 298;
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 1;
	double result = instance.activityCoefficient(ci, fractions, k, temperature);
	assertEquals(expResult, result,1e-3);
	
    }

    @Test
    public void testG() {
	System.out.println("G");
	Component cj = null;
	Component ci = null;
	ActivityModelBinaryParameter param = null;
	double temperature = 0.0;
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 0.0;
	double result = instance.G(cj, ci, param, temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    @Test
    public void testParcialExcessGibbsRespectTemperature() {
	System.out.println("parcialExcessGibbsRespectTemperature");
	ArrayList<Component> components = null;
	HashMap<Component, Double> fractions = null;
	ActivityModelBinaryParameter param = null;
	double temperature = 0.0;
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 0.0;
	double result = instance.parcialExcessGibbsRespectTemperature(components, fractions, param, temperature);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}