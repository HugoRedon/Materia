package termo.substance;

import termo.matter.PureSubstance;
import termo.matter.Mixture;
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
    Mixture substance ;
    PureSubstance propanePure ;
    public MixtureSubstanceTest() {
//	substance = new MixtureSubstance();
	
        Component ethane = new Component("ethane");
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	PureSubstance ethanePure = new PureSubstance();
	//ethanePure.setCubicEquationOfState(eos);
	ethanePure.setAlpha(alpha);
	ethanePure.setComponent(ethane);
	
	Component propane = new Component("propane");
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	
	
	 propanePure = new PureSubstance();
	//propanePure.setCubicEquationOfState(eos);
	propanePure.setAlpha(alpha);
	propanePure.setComponent(propane);
	
	substance = new Mixture();
	
	
	substance.addComponent(propanePure, 0.7);
	substance.addComponent(ethanePure, 0.3);
	substance.setCubicEquationOfState(eos);
	
	InteractionParameter b = new InteractionParameter();
	b.setValue(propane, ethane, 0);
//	MixingRule mr = new VDWMixingRule();
//	
//	
//	substance.setBinaryParameters(b);
//	//substance.setCubicEquationOfState(eos);
//	substance.setMixingRule(mr);
    }
    
    @Test 
    public void testChangeAlphaAndMolarFractionsStillWork(){
        System.out.println("change alpha");
        substance.setAlpha(AlphaFactory.getAdachiAndLu());
        System.out.println("propane pure alpha should be adachi" + propanePure.getAlpha());
        
        double fraction = substance.getFraction(propanePure);
        assertEquals(fraction, 0.7,0.000001);
    }

}