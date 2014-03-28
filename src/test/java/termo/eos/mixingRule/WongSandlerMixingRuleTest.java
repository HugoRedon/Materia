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
	ethane = new Component("Ethane");
	
	//ethane.setName();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	propane = new Component("Propane");
	
	 //propane.setName();
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	components.add(ethane);
	components.add(propane);
	
	
	fractions = new HashMap();
	
	eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ci = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	PureSubstance cj = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
	
	fractions.put(ci, 0.3);
	fractions.put(cj, 0.7);
	
	
//	 instance = new HuronVidalMixingRule(new WilsonActivityModel(),eos);
	
	
    }

    
    @Test 
    public void wongSandlerWilsonFugacity(){
//	System.out.println("fugacity");
//	double temperature = 298;
//	double pressure = 101325;
//	
//	WilsonActivityModel w = new WilsonActivityModel();
//	
//	WongSandlerMixingRule ws = new WongSandlerMixingRule(w,eos);
//	
//	
//	MixtureSubstance ms = new MixtureSubstance(eos, AlphaFactory.getStryjekAndVeraExpression(), ws, components, Phase.LIQUID, k);
//	//ms.setMolarFractions(fractions);
//	ms.setFraction(ethane, 0.3);
//	ms.setFraction(propane, 0.7);
//	ms.setTemperature(temperature);
//	ms.setPressure(pressure);
//	double expResult = 23.7019;//quien sabe
//	double resutl = ms.calculateFugacity(ethane);
//	assertEquals(expResult, resutl,1e-3);+
	fail();
    }
    
    @Test
    public void wongSandlerNrtlFugacity(){
//	System.out.println("fugacity");
//	double temperature= 298;
//	double pressure = 101325;
//	
//	NRTLActivityModel nrtl = new NRTLActivityModel();
//	WongSandlerMixingRule ws = new WongSandlerMixingRule(nrtl, eos);
//	
//	MixtureSubstance ms = new MixtureSubstance(eos, AlphaFactory.getStryjekAndVeraExpression(), ws, components, Phase.LIQUID, k);
//	
//	//ms.setMolarFractions(fractions);
//	ms.setFraction(ethane, 0.3);
//	ms.setFraction(propane, 0.7);
//	
//	
//	ms.setTemperature(temperature);
//	ms.setPressure(pressure);
//	
//	double expResult = 23.7019;//quien sabe
//	double resutl = ms.calculateFugacity(ethane);
//	assertEquals(expResult, resutl,1e-3);
	fail();
    }
 
}