package termo.eos.mixingRule;

import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.activityModel.NRTLActivityModel;
import termo.activityModel.WilsonActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.phase.Phase;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class HuronVidalMixingRuleTest {
    Compound ethane;
    Compound propane;
//    HashMap<PureSubstance, Double> fractions;
//    HuronVidalMixingRule instance;
    InteractionParameter k = new ActivityModelBinaryParameter();
    
    HashSet<Compound> components = new HashSet<>();
    Substance ethanePure;
    Substance propanePure;
    
    Cubic eos;
    
        Mixture mixture;
    
    
    public final void createComponents(){
	ethane = new Compound("Ethane");
	
	//ethane.setName();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	propane = new Compound("Propane");
	
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
//	ethanePure = new Substance(eos, alpha, ethane, Phase.VAPOR);
//	propanePure = new Substance(eos, alpha, propane, Phase.VAPOR);
//    }
    
    public HuronVidalMixingRuleTest() {
	
	createComponents();
//	createPureSubstaces();
	eos =EquationsOfState.pengRobinson();
	Alpha alpha = Alphas.getStryjekAndVeraExpression();
	ethanePure = new Substance(eos, alpha, ethane, Phase.VAPOR);
	HuronVidalMixingRule hv = new HuronVidalMixingRule(new WilsonActivityModel(), eos);
	System.out.println("hv L :" + hv.getL());
	mixture = new Mixture(eos, alpha, components, Phase.VAPOR, hv, k);
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

//	for(Substance pure: fractions.keySet()){
//	    pure.setTemperature(temperature);
//	}
	mixture.setTemperature(temperature);
	//double expResult = 965895.394357623;
	double expResult = 970355.6533255058;
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
//	Mixture ms = new Mixture(EquationOfStateFactory.pengRobinsonBase(), AlphaFactory.getStryjekAndVeraExpression(), instance, components,Phase.LIQUID,k);
//	
//	//ms.setMolarFractions(fractions);
//	ms.setFraction(ethane, 0.3);
//	ms.setFraction(propane, 0.7);
//	
	mixture.setTemperature(298);
	mixture.setPressure(101325);
	mixture.setPhase(Phase.LIQUID);
	//double expResult = 25.722068047672103;

	double expResult = 24.894819044989497;
	
	double result = mixture.calculateFugacityCoefficient(ethane);
	assertEquals(expResult, result,1e-3);
//	fail();
    }
    
    
    @Test public void nrtlHuronFugacity(){
//	InteractionParameter k = new ActivityModelBinaryParameter();
	NRTLActivityModel nrtl = new NRTLActivityModel();
	
	
	HuronVidalMixingRule hv = new HuronVidalMixingRule(nrtl, eos);
//	
//	NRTLActivityModel nrtl = new NRTLActivityModel();
        //MixtureSubstance ms = new Mixture();
//	Mixture ms = new Mixture(
//                , 
//                ,
//                components, 
//                Phase.LIQUID,
//                
//                k,nrtl);
        Mixture mix = new Mixture(
                EquationsOfState.pengRobinson(), 
                Alphas.getStryjekAndVeraExpression(), 
                components, Phase.LIQUID, hv, k);
	
	
	mix.setFraction(ethane, 0.3);
	mix.setFraction(propane, 0.7);
//	
	mix.setTemperature(298);
	mix.setPressure(101325);
	double expResult = 25.3062;
	double result = mix.calculateFugacityCoefficient(ethane);
	
	assertEquals(expResult, result, 1e-3);
//	fail();
    }



}