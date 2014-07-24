package termo.eos.mixingRule;

import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.phase.Phase;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class VDWMixingRuleTest {
    Mixture mixture ; 
    HashMap<Substance, Double> fractions;
    InteractionParameter b;
    Compound ethane;
    public VDWMixingRuleTest() {
	
	
	
	ethane = new Compound("ethane");
	
	//ethane.setName();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	Cubic eos = EquationsOfState.pengRobinson();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	Substance ethanePure = new Substance();
	ethanePure.setCubicEquationOfState(eos);
	ethanePure.setAlpha(alpha);
	ethanePure.setComponent(ethane);
	
	Compound propane = new Compound("propane");
	//propane.setName();
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	
	
	Substance propanePure = new Substance();
	propanePure.setCubicEquationOfState(eos);
	propanePure.setAlpha(alpha);
	propanePure.setComponent(propane);
	
	//fractions = new HashMap();
	
	HashSet<Compound> components = new HashSet();
	components.add(ethane);
	components.add(propane);
	
	
	 b = new InteractionParameter(true);
	b.setValue(propane, ethane, 0.05);
        
        VDWMixingRule vdw = new VDWMixingRule();
        mixture = new Mixture(eos, alpha, components, Phase.VAPOR, vdw, b);
        
	
	mixture.setFraction(propane, 0.7);
	mixture.setFraction(ethane, 0.3);
	
	//fractions.put(propanePure, 0.7);
	//fractions.put(ethanePure, 0.3);
	
	//rule.setFractions(fractions);

    }

    @Test
    public void testA() {
	System.out.println("a");
	double temperature =298;
	mixture.setTemperature(temperature);
	
	double expResult = 950499.221;
	double result = mixture.calculate_a_cubicParameter();
	assertEquals(expResult, result, 1e-3);
    }

    @Test
    public void vdwFugacitiy(){
        
        InteractionParameter k = new InteractionParameter();

        mixture.setBinaryParameters(k);



        mixture.setTemperature(298);
        mixture.setPressure(101325);

        double expResult = 0.992262;
        double result = mixture.calculateFugacity(ethane);

        assertEquals(expResult, result,1e-3);
        
    }

}