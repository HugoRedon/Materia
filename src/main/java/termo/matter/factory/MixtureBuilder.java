package termo.matter.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.phase.Phase;

public class MixtureBuilder {
	Cubic equationOfState;
	Alpha alpha;
	Set<Compound> compounds;
	Map<Compound,Double> compoundsWithFractions = new HashMap();
	Set<Substance> substances;
	Phase phase;
	MixingRule mixingRule;
	InteractionParameter k;
	
	
	public Mixture build(){
		Mixture mix =null;
		if(compounds!= null && compounds.size() > 1){
			mix =createWithCompounds();
			
		}
		if(alpha !=null && equationOfState != null && phase != null){
			mix = new Mixture(equationOfState, phase, mixingRule, k);
		}
		if(true){
			
		}
		
		
		return mix;
	}
	
	private Mixture createWithSubstances(){
		if(alpha !=null && equationOfState != null && phase != null){
			return new Mixture(equationOfState, phase, mixingRule, k);
					
		}else{
			//throw new Exception("Mezcla definida incorrectamente");
			return null;
		}
	}
	
	private Mixture createWithCompounds(){
		if(alpha !=null && equationOfState != null && phase != null){
			return new Mixture(equationOfState, alpha, compounds, phase, mixingRule, k);
		}else{
			//throw new Exception("Mezcla definida incorrectamente");
			return null;
		}
	}
	
	public MixtureBuilder setEquationOfState(Cubic cubic){
		equationOfState = cubic;
		return this;
	}
	
	public MixtureBuilder setAlpha(Alpha alpha){
		this.alpha = alpha;
		return this;
	}
	
	public MixtureBuilder addCompounds(Compound...compounds){
		for(Compound compound:compounds){
			this.compounds.add(compound);
		}
		return this;
	}
	public MixtureBuilder addCompound(Compound compound, double molarFraction){
		compoundsWithFractions.put(compound, molarFraction);
		return this;
	}
	
	public MixtureBuilder addSubstances(Substance... substances){
		for(Substance substance: substances){
			this.substances.add(substance);
		}
		return this;
	}
	
	public MixtureBuilder setPhase(Phase phase){
		this.phase = phase;
		return this;
	}
	
}
