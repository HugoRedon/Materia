package termo.matter.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.MixingRules;
import termo.matter.Mixture;
import termo.phase.Phase;


public class MixtureBuilderTest {

	@Test
	public void testBuild() {
		System.out.println("buildertest");
		Compound cyclohexane = new Compound("Cyclohexane");
		cyclohexane.setCriticalPressure(4073000);
		cyclohexane.setCriticalTemperature(553.5);
		cyclohexane.setAcentricFactor(0.211);
		
		Compound pentane = new Compound("N-pentane");
		pentane.setCriticalPressure(3370000);
		pentane.setCriticalTemperature(469.7);
		pentane.setAcentricFactor(0.251);
		
		Cubic equationOfState = EquationsOfState.pengRobinson();
		Alpha alpha = Alphas.getMathiasAndCopemanExpression();
		MixingRule mixingRule = MixingRules.vanDerWaals();
		
		InteractionParameter k = new InteractionParameter();
		
		
		Mixture mixture = new MixtureBuilder()
					.addCompounds(cyclohexane,pentane)
					.setAlpha(alpha)
					.setEquationOfState(equationOfState)
					.setPhase(Phase.VAPOR)
					.setMixingRule(mixingRule)
					.setInteractionParameter(k)
					.build();

		double a = mixture.calculate_a_cubicParameter();
		double b = mixture.calculate_b_cubicParameter();
		
		
		System.out.println(a);
		System.out.println(b);
	}
	
	@Test 
	public void testBuildWithDifferentAlphas(){
		System.out.println("differentAlphas");
		Compound cyclohexane = new Compound("Cyclohexane");
		cyclohexane.setCriticalPressure(4073000);
		cyclohexane.setCriticalTemperature(553.5);
		cyclohexane.setAcentricFactor(0.211);
		
		Compound pentane = new Compound("N-pentane");
		pentane.setCriticalPressure(3370000);
		pentane.setCriticalTemperature(469.7);
		pentane.setAcentricFactor(0.251);
		
		Cubic eos = EquationsOfState.pengRobinson();
		Phase phase = Phase.LIQUID;
		
		InteractionParameter k = new InteractionParameter();
		MixingRule mixingRule = MixingRules.vanDerWaals();
		Mixture mixture = new MixtureBuilder()
					.addCompound(cyclohexane,Alphas.getPengAndRobinsonExpression())
					.addCompound(pentane,Alphas.getStryjekAndVeraExpression())
					.setEquationOfState(eos)
					.setPhase(phase)
					.setMixingRule(mixingRule)
					.setInteractionParameter(k)
					.build();

		double a = mixture.calculate_a_cubicParameter();
		double b = mixture.calculate_b_cubicParameter();
		
		System.out.println(a);
		System.out.println(b);
		
		mixture.setAlpha(Alphas.getMathiasAndCopemanExpression());
		
		 a = mixture.calculate_a_cubicParameter();
		 b = mixture.calculate_b_cubicParameter();
		 
		assertEquals(2220744.973595802,a ,1e-5);
		System.out.println(a);
		System.out.println(b);
	}

}
