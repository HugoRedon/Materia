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
import termo.substance.MixtureSubstance;
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
	
	MixtureSubstance mixture ;
    public NRTLActivityModelTest() {
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
	
	
	
	
	eos = EquationOfStateFactory.pengRobinsonBase();
	alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	ci = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	PureSubstance cj = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
	
	fractions.put(ci, 0.3);
	fractions.put(cj, 0.7);
	
	
//	 hv = new HuronVidalMixingRule(new WilsonActivityModel(),eos);
        
        ActivityModelBinaryParameter param = new ActivityModelBinaryParameter();
	mixture = new MixtureSubstance();
        mixture.setComponents(components);
        mixture.setFraction(propane, 0.7);
        mixture.setFraction(ethane, 0.3);
        mixture.setBinaryParameters(param);
	
    }

    @Test
    public void testExcessGibbsEnergy() {
	System.out.println("excessGibbsEnergy");
	
	
	double temperature = 298;
        mixture.setTemperature(temperature);
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 0.0;
	double result = instance.excessGibbsEnergy(mixture);
	assertEquals(expResult, result, 1e-3);
	
    }

    @Test
    public void testActivityCoefficient() {
	System.out.println("activityCoefficient");
	
	
	double temperature = 298;
        mixture.setTemperature(temperature);
	NRTLActivityModel instance = new NRTLActivityModel();
	double expResult = 1;
	double result = instance.activityCoefficient(ci,mixture);
	assertEquals(expResult, result,1e-3);
	
    }


 
}