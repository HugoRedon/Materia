package termo.eos.mixingRule;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alphas;
import termo.matter.HeterogeneousMixture;

public class TestA {
	
	@Test
	public void aTest(){
		Compound methane = new Compound("methane");
		methane.setCriticalTemperature(190.55);
		methane.setCriticalPressure(45.95*100000);
		methane.setAcentricFactor(0.01045);
		
		methane.setK_StryjekAndVera(-0.00159);
		
		Compound water = new Compound("water");
		water.setCriticalTemperature(647.29);;
		water.setCriticalPressure(220.9*100000);
		water.setAcentricFactor(.3438);
		
		water.setK_StryjekAndVera(-0.06635);
		
		Set<Compound> compounds = new HashSet<>();
		compounds.add(methane);
		compounds.add(water);
		
		InteractionParameter k = new InteractionParameter();
		k.setSymmetric(true);
		k.setValue(methane, water, 0.0215);
		
		HeterogeneousMixture hm = new HeterogeneousMixture(
				EquationsOfState.pengRobinson(), 
				Alphas.getStryjekAndVeraExpression(), 
				MixingRules.vanDerWaals(), compounds, k);
		hm.setTemperature(310);
		double z = 0.232668;
		hm.setZFraction(methane, z);
		hm.setZFraction(water, 1-z);
		
		hm.bubblePressure();
		
		z = 0.242668;
		hm.setZFraction(methane, z);
		hm.setZFraction(water, 1-z);
		
		
		hm.bubblePressure(hm.getPressure(), hm.getVapor().getFractions());
		
		
		double p =hm.getPressure();
		System.out.println("Presión: " + p);
	}

}
