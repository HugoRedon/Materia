package termo.optimization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.type.ErrorType;

import org.junit.Test;

import termo.activityModel.ActivityModel;
import termo.activityModel.NRTLActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Compound;
import termo.data.ExperimentalDataBinary;
import termo.data.ExperimentalDataBinaryType;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.WongSandlerMixingRule;
import termo.matter.HeterogeneousMixture;

public class CarbonDioxideMethanolCase {
	public Compound getCarbonDioxide(){
		Compound carbonD= new Compound("Carbon dioxide");
		carbonD.setCriticalTemperature(304.21);
		carbonD.setCriticalPressure(7383000);
		carbonD.setAcentricFactor(0.223621);
		return carbonD;
	}
	
	public Compound getMethanol(){
		Compound methanol = new Compound("Methanol");
		methanol.setCriticalTemperature(512.64);
		methanol.setCriticalPressure(8097000);
		methanol.setAcentricFactor(0.565);
		return methanol;
	}
	
	public List<ExperimentalDataBinary> getExperimentalDataBinary(){
		String data = "7.792 0.0596 0.9761\n" + 
				"18.814 0.1548 0.9907\n" + 
				"30.246 0.2601 0.9920\n" + 
				"39.503 0.3495 0.9929\n" + 
				"48.664 0.4886 0.9930\n" + 
				"55.013 0.6451 0.9931\n" + 
				"57.509 0.7685 0.9930\n" + 
				"58.749 0.9002 0.9930\n";
		double temperature = 273.15+25;
		
		List<ExperimentalDataBinary> result = new ArrayList();
		for(String line: data.split("\n")){
			String[] words = line.split("\\s+");
			double pressure = 101325*Double.valueOf(words[0]);
			double x = Double.valueOf(words[1]);
			double y= Double.valueOf(words[2]);
			result.add(new ExperimentalDataBinary(temperature,pressure,x,y));
			//System.out.println(line);
		}
		return result;
	}
	@Test
	public void carbondioxideOptimizationWithVaporPressureErrorFunction(){
		Cubic eos= EquationOfStateFactory.pengRobinsonBase();
		Alpha alpha = AlphaFactory.getPengAndRobinsonExpression();
		ActivityModel activityModel = new NRTLActivityModel();
		MixingRule mr = new WongSandlerMixingRule(activityModel, eos);
		
		Compound carbonDioxide =getCarbonDioxide();
		Compound methanol = getMethanol();
		
		
		Set<Compound> compounds = new HashSet();
		compounds.add(carbonDioxide);
		compounds.add(methanol);
		
		ActivityModelBinaryParameter k = new ActivityModelBinaryParameter();
		
		HeterogeneousMixture mix = new HeterogeneousMixture(eos, alpha, mr, compounds, k);
		
		List<ExperimentalDataBinary> experimental = getExperimentalDataBinary();
		
		mix.getErrorfunction().setExperimental(experimental,ExperimentalDataBinaryType.isothermic);
		mix.getErrorfunction().setReferenceComponent(carbonDioxide);
		mix.getErrorfunction().setNonReferenceComponent(methanol);
		
		double error =mix.getErrorfunction().error();
		
		
		k.getA().setValue(carbonDioxide, methanol, 0.1);
		k.getA().setValue(methanol, carbonDioxide, 0.1);
		
		k.getB().setValue(carbonDioxide, methanol, 0.1);
		k.getB().setValue(methanol, carbonDioxide, 0.1);
		
		mix.getErrorfunction().getOptimizer().setApplyErrorDecreaseTechnique(true);
		mix.getErrorfunction().minimize();
		boolean falseOptim =mix.getErrorfunction().getOptimizer().isIndeter() || mix.getErrorfunction().getOptimizer().isMaxIterationsReached();
		
		if(falseOptim){
			fail();
		}
		
		double errorAfterOptim =mix.getErrorfunction().error();
		if(errorAfterOptim == Double.NaN || errorAfterOptim > error){
			fail();
		}
		
		
		System.out.println("error before: "+error);
		System.out.println("Error after: "+ errorAfterOptim);
		
		
		System.out.println("K12: " + k.getK().getValue(carbonDioxide, methanol));
		System.out.println("K21: " + k.getK().getValue( methanol,carbonDioxide));
		
		System.out.println("A12: " + k.getA().getValue(carbonDioxide, methanol));
		System.out.println("A21: " + k.getA().getValue( methanol,carbonDioxide));
		
		System.out.println("B12: " + k.getB().getValue(carbonDioxide, methanol));
		System.out.println("B21: " + k.getB().getValue( methanol,carbonDioxide));
		
		
	}
}
