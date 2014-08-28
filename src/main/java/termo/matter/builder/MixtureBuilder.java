package termo.matter.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.matter.HeterogeneousMixture;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.phase.Phase;

public class MixtureBuilder {
	Cubic equationOfState;
	Alpha alpha;
	Set<Compound> compounds = new HashSet();

	Map<Compound,Alpha> compoundsWithAlpha = new HashMap();
	Set<Substance> substances = new HashSet();
	Phase phase;
	MixingRule mixingRule;
	InteractionParameter k;
	
	List<CompoundAlphaFraction> compoundAlphaFraction =new ArrayList<>();
	


	
	
	
	public Mixture build(){
		Mixture mix =null;
		if(compounds!= null && compounds.size() > 1){
			mix =createWithCompounds();
			
		}else if(compoundsWithAlpha != null && compoundsWithAlpha.size()>1){
			//fracciones igual 
			mix = createWithDifferentAlphas();
		}else if(compoundAlphaFraction != null && compoundAlphaFraction.size()>1){
			mix =createWithDiffAlphasAndFractions();
		}
		
		return mix;
	}
	
	private Mixture createWithDiffAlphasAndFractions(){
		Mixture result=null;
		if(equationOfState != null 
				&& phase != null
				&& mixingRule !=null
				&& k != null){
			
			result = new Mixture(equationOfState,phase,mixingRule,k);
			Set<Substance> substancesToAdd = new HashSet<>();
			for(CompoundAlphaFraction caf: compoundAlphaFraction){
				Compound c = caf.getCompound();
				Alpha a = caf.getAlpha();
				double f =caf.getFraction();
				Substance pureSubstance = new Substance(equationOfState, a, c, phase);
				pureSubstance.setMolarFraction(f);
				substancesToAdd.add(pureSubstance);
			}
			result.addCompounds(substancesToAdd);
		}
		return result;
	}
	
	private Mixture createWithDifferentAlphas(){
		if(equationOfState != null 
				&& phase != null
				&& mixingRule !=null
				&& k != null){
			Mixture mixture = new Mixture(equationOfState, phase, mixingRule, k);
			Set<Substance> substancesToAdd = new HashSet();
			int n = compoundsWithAlpha.size();
			double z = 1d/Double.valueOf(n);
			for(Compound comp: compoundsWithAlpha.keySet()){
				Alpha alpha =compoundsWithAlpha.get(comp);
				Substance substance = new Substance(equationOfState, alpha, comp, phase);
				substance.setMolarFraction(z);
				substancesToAdd.add(substance);
			}
			
			mixture.addCompounds(substancesToAdd);
		return mixture;
		}else{
			return new Mixture();
		}
	}
	
	private Mixture createWithCompounds(){
		if(alpha !=null 
				&& equationOfState != null 
				&& phase != null
				&& mixingRule !=null
				&& k != null){
			return new Mixture(equationOfState, alpha, compounds, phase, mixingRule, k);
		}else{
			//throw new Exception("Mezcla definida incorrectamente");
			return new Mixture();
		}
	}
	

	
	public MixtureBuilder setInteractionParameter(InteractionParameter k){
		this.k = k;
		return this;
	}
	
	public MixtureBuilder setMixingRule(MixingRule mixingRule){
		this.mixingRule = mixingRule;
		return this;
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
	public MixtureBuilder addCompound(Compound compound, Alpha alpha){
		compoundsWithAlpha.put(compound, alpha);
		return this;
	}
	
	public MixtureBuilder addCompound(Compound compound,Alpha alpha,Double fraction){
		compoundAlphaFraction.add(new CompoundAlphaFraction(compound, alpha, fraction));
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
