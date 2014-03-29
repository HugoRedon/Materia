
package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.HuronVidalMixingRule;
import termo.eos.mixingRule.MixingRule;
import termo.phase.Phase;
import termo.matter.HeterogeneousMixture;
import termo.matter.Mixture;
import termo.matter.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class WilsonActivityModelTest {
    
//      HeterogeneousMixture substance;
    Mixture substance;
       ArrayList<Component> components = new ArrayList();
       Component ethane ;
       Component propane;
       
       Cubic eos;
       Alpha alpha;
    public WilsonActivityModelTest(){
	
	
	
	ethane = new Component("ethane");
	
//	ethane.setName();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	propane = new Component("propane");
	
//	 propane.setName("Propane");
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
	eos = EquationOfStateFactory.pengRobinsonBase();
	alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	
	components.add(ethane);
	components.add(propane);
	
	
	WilsonActivityModel activity = new WilsonActivityModel();
	MixingRule huronVidal = new HuronVidalMixingRule(activity,eos);
        
	
	InteractionParameter param = new ActivityModelBinaryParameter();
	
        
        
	substance = new Mixture(eos, alpha, components, Phase.LIQUID, huronVidal, param);
        
	
	substance.setFraction(propane, 0.7);
        substance.setFraction(ethane, 0.3);
	
//	substance.setZFraction(propane,0.7);
//	substance.setZFraction(ethane, 0.3);
    }
    
    
    @Test
    public void wilsonTest(){
	System.out.println("Huron-Vidal");
	
	double temperature = 298;
	
	//double result = substance.bubblePressure(temperature);
        
//	double expResult = 16.885234;
	substance.setTemperature(temperature);
	substance.setPressure(101325);
	double z = substance.calculateCompresibilityFactor();
	assertEquals(0.003550,z,1e-3);
	
//	for(PureSubstance pure : substance.getLiquid().getPureSubstances()){
//	    if(pure.getComponent().equals(ethane)){
//		
//		
//		double fug = substance.getLiquid().calculateFugacity(pure, 298, 101325);
//		assertEquals(24.8948, fug,1e-3);
//	    }
//	}
	
	
//	
//	assertEquals(expResult,result/101325,1e-3);
    }

    @Test
    public void testExcessGibbsEnergy() {
	System.out.println("excessGibbsEnergy");
	
       
	
	double temperature = 298;
        substance.setTemperature(temperature);
	
	double expResult = -2.69617643e4;
        WilsonActivityModel activity =new WilsonActivityModel();
	double result = activity.excessGibbsEnergy(substance);
	assertEquals(expResult, result, 1e-3);

    }

    @Test
    public void testActivityCoefficient() {
	System.out.println("activityCoefficient");
	
	
        PureSubstance ci = new PureSubstance();
        for(PureSubstance pure:  substance.getPureSubstances()){
            if(pure.getComponent().getName().equals("ethane")){
                ci = pure;
            }
        }	

	double temperature = 298;
        substance.setTemperature(temperature);
        
	WilsonActivityModel instance = new WilsonActivityModel();
	double expResult = 0.97339;
	double result = instance.activityCoefficient(ci,substance);
	assertEquals(expResult, result, 1e-3);

    }

    @Test
    public void testLambda() {
	System.out.println("lambda");
	PureSubstance ci = new PureSubstance(eos, alpha, ethane, Phase.VAPOR);
	PureSubstance cj = new PureSubstance(eos, alpha, propane, Phase.VAPOR);
	ActivityModelBinaryParameter k = new ActivityModelBinaryParameter();
	double T = 298;
	ci.setTemperature(T);
	cj.setTemperature(T);
	WilsonActivityModel instance = new WilsonActivityModel();
	double expResult = 1.3903892;
	double result = instance.lambda(ci, cj, k, T);
	assertEquals(expResult, result, 1e-3);
	
    }

  

 
}