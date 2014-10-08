package termo.eos.mixingRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alphas;
import termo.matter.HeterogeneousMixture;

public class TwoParameterVDWTest {
	Compound referenceCompound ;
	Compound nonReferenceCompound;
	
	HeterogeneousMixture hm;
	
	public TwoParameterVDWTest(){
		referenceCompound = new Compound("isopropanol");
		referenceCompound.setCriticalTemperature(508.40);
		referenceCompound.setCriticalPressure(47.64*100000);
		referenceCompound.setAcentricFactor(0.66372);
		referenceCompound.setK_StryjekAndVera(0.23264);
		
		nonReferenceCompound = new Compound("water");
		nonReferenceCompound.setCriticalTemperature(647.29);
		nonReferenceCompound.setCriticalPressure(220.90*100000);
		nonReferenceCompound.setAcentricFactor(0.3438);
		nonReferenceCompound.setK_StryjekAndVera(-0.06635);
		
		Set<Compound> components = new HashSet<>();
		
		components.add(referenceCompound);
		components.add(nonReferenceCompound);
		
		Cubic equationOfState = EquationsOfState .pengRobinson();
		MixingRule mr = new TwoParameterVanDerWaals();
		
		ActivityModelBinaryParameter k = new ActivityModelBinaryParameter();
					
		k.getTwoParameterVanDerWaals().setValue(referenceCompound, nonReferenceCompound, 0.0953);
		k.getTwoParameterVanDerWaals().setValue(nonReferenceCompound, referenceCompound, 0.0249);
		
		hm = new HeterogeneousMixture(equationOfState,
				Alphas.getStryjekAndVeraExpression(),
				mr, 
				components, k);
		
		hm.setTemperature(353);
	}
	
	@Test
	public void bubblePressureTest0(){
		hm.setZFraction(referenceCompound, 0);
		hm.setZFraction(nonReferenceCompound, 1);
		
		hm.bubblePressure();
		double pressure = hm.getPressure();
		System.out.println("bubblepressure : " + pressure);
	}
	@Test
	public void bubblePressureTestZ(){
		double z = 0.8d;
		hm.setZFraction(referenceCompound, z);
		hm.setZFraction(nonReferenceCompound, 1-z);
		
		Map<String,Double> yestimates =new HashMap<>();
		yestimates.put(referenceCompound.getName(), 0.5);
		yestimates.put(nonReferenceCompound.getName(),0.5);
		hm.bubblePressure();
		//hm.dewPressure();
		double pressure = hm.getPressure();
		System.out.println("bubblepressure : " + pressure);
	}
}
