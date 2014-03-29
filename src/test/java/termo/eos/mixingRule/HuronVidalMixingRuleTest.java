package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.activityModel.NRTLActivityModel;
import termo.activityModel.WilsonActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
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
public class HuronVidalMixingRuleTest {
    Component ethane;
    Component propane;
//    HashMap<PureSubstance, Double> fractions;
//    HuronVidalMixingRule instance;
    InteractionParameter k = new ActivityModelBinaryParameter();
    
    ArrayList<Component> components = new ArrayList<>();
    PureSubstance ethanePure;
    PureSubstance propanePure;
    
    Cubic eos;
    
        MixtureSubstance mixture;
    
    
    public final void createComponents(){
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
	HuronVidalMixingRule hv = new HuronVidalMixingRule(new WilsonActivityModel(), eos);
	mixture = new MixtureSubstance(eos, alpha, components, Phase.VAPOR, hv, k);
//	instance = new HuronVidalMixingRule(eos, alpha, components, Phase.VAPOR, k, new WilsonActivityModel());
        
        mixture.setMixingRule(hv);
	mixture.setBinaryParameters(k);
	mixture.setFraction(propane, 0.7);
	mixture.setFraction(ethane, 0.3);
    }

    @Test
    public void testA() {
	System.out.println("a");
	double temperature = 298;

//	for(PureSubstance pure: fractions.keySet()){
//	    pure.setTemperature(temperature);
//	}
	mixture.setTemperature(temperature);
	double expResult = 9.70355653e5;
	double result = mixture.calculate_a_cubicParameter();
	assertEquals(expResult, result, 1e-3);

    }

//
//    @Test
//    public void testOneOverNParcial_aN2RespectN() {
//	System.out.println("oneOverNParcial_aN2RespectN");
//	double temperature = 298;
//	
//
//	    
//	double expResult = 0.0;
//	mixture.setTemperature(temperature);
//	ethanePure.setTemperature(temperature);
//	double result = mixture.oneOver_N_Parcial_a( ethanePure);
//	assertEquals(expResult, result, 1e-3);
//
//    }
    
    
   
    
    
    @Test public void huronFugacity(){
//	InteractionParameter k = new ActivityModelBinaryParameter();
//	
//	MixtureSubstance ms = new MixtureSubstance(EquationOfStateFactory.pengRobinsonBase(), AlphaFactory.getStryjekAndVeraExpression(), instance, components,Phase.LIQUID,k);
//	
//	//ms.setMolarFractions(fractions);
//	ms.setFraction(ethane, 0.3);
//	ms.setFraction(propane, 0.7);
//	
	mixture.setTemperature(298);
	mixture.setPressure(101325);
	mixture.setPhase(Phase.LIQUID);
	double expResult = 24.8948;
	
	double result = mixture.calculateFugacity(ethane);
	assertEquals(expResult, result,1e-3);
//	fail();
    }
    
    
    @Test public void nrtlHuronFugacity(){
//	InteractionParameter k = new ActivityModelBinaryParameter();
	NRTLActivityModel nrtl = new NRTLActivityModel();
	
	
	HuronVidalMixingRule hv = new HuronVidalMixingRule(nrtl, eos);
//	
//	NRTLActivityModel nrtl = new NRTLActivityModel();
        //MixtureSubstance ms = new MixtureSubstance();
//	MixtureSubstance ms = new MixtureSubstance(
//                , 
//                ,
//                components, 
//                Phase.LIQUID,
//                
//                k,nrtl);
        MixtureSubstance mix = new MixtureSubstance(
                EquationOfStateFactory.pengRobinsonBase(), 
                AlphaFactory.getStryjekAndVeraExpression(), 
                components, Phase.LIQUID, hv, k);
	
	
	mix.setFraction(ethane, 0.3);
	mix.setFraction(propane, 0.7);
//	
	mix.setTemperature(298);
	mix.setPressure(101325);
	double expResult = 25.3062;
	double result = mix.calculateFugacity(ethane);
	
	assertEquals(expResult, result, 1e-3);
//	fail();
    }



}