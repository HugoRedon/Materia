package termo.matter.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.componentsForTests.ComponentsForTests;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.matter.HeterogeneousMixture;
import termo.matter.Mixture;
import termo.phase.Phase;

public class HeterogeneousMixtureBuilderTest {

	@Test
	public void test() {
		Set<Compound> compounds = new HashSet<>();
		Compound water = ComponentsForTests.getWater();
		Compound methanol = ComponentsForTests.getMethanol();
		
		compounds.add(water);
		compounds.add(methanol);
		HeterogeneousMixture hm = new HeterogeneousMixture(
				EquationsOfState.pengRobinson(),
				Alphas.getStryjekAndVeraExpression(),
				new VDWMixingRule(),compounds,new InteractionParameter());
		
		
		hm.setTemperature(300d);
		Double temp = hm.getLiquid().getTemperature();
		Double t = hm.getLiquid().getPureSubstances().iterator().next().getTemperature(); 
		
		assertEquals(true,temp.equals(300d)&& t.equals(300d));
	}
	
	@Test
	public void test2(){
		Set<Compound> compounds = new HashSet<>();
		Compound water = ComponentsForTests.getWater();
		Compound methanol = ComponentsForTests.getMethanol();
		
		compounds.add(water);
		compounds.add(methanol);
		
		Cubic eos= EquationsOfState.pengRobinson();
		Alpha alpha = Alphas.getStryjekAndVeraExpression();
		MixingRule mr = new VDWMixingRule();
		InteractionParameter k =new InteractionParameter(); 
		
		HeterogeneousMixture hm = new HeterogeneousMixture(
				eos,alpha,mr,compounds,k);
		
		boolean normalTest = testTemperature(hm);
		
		Mixture mix = new Mixture(eos, alpha, compounds, Phase.LIQUID, mr, k);		
		HeterogeneousMixture hmb = new HeterogeneousMixtureBuilder().fromMixture(mix);		
		
		boolean builderTest = testTemperature(hmb);
		
		hmb.dewPressure();
		System.out.print(hmb.getPressure());
		
		
		assertEquals(true,normalTest &&builderTest);
	}
	
	public boolean testTemperature(HeterogeneousMixture hm){
		hm.setTemperature(300d);
		Double temp = hm.getLiquid().getTemperature();
		Double tv = hm.getVapor().getPureSubstances().iterator().next().getTemperature();
		Double t = hm.getLiquid().getPureSubstances().iterator().next().getTemperature();
		return temp.equals(300d)&& t.equals(300d) && tv.equals(300d);
	}

}
