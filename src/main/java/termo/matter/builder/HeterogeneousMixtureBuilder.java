package termo.matter.builder;

import termo.matter.HeterogeneousMixture;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.phase.Phase;

public class HeterogeneousMixtureBuilder {

	
	public HeterogeneousMixture fromMixture(Mixture mix){
		
		MixtureBuilder mb= new MixtureBuilder();
		
		mb.setEquationOfState(mix.getCubicEquationOfState());
		mb.setInteractionParameter(mix.getBinaryParameters());
		mb.setMixingRule(mix.getMixingRule());
		
		
		for(Substance sub:mix.getPureSubstances()){
			mb.addCompound(sub.getComponent(), sub.getAlpha(), sub.getMolarFraction());		
		}
				
		HeterogeneousMixture hm = new HeterogeneousMixture(mix.getCubicEquationOfState(),
				mix.getAlpha(),mix.getMixingRule(),
				mix.getComponents(),mix.getBinaryParameters()
				);
		hm.setLiquid(mb.setPhase(Phase.LIQUID).build());
		hm.setVapor(mb.setPhase(Phase.VAPOR).build());
		
		for(Substance sub:mix.getPureSubstances()){
			hm.setZFraction(sub.getComponent(), sub.getMolarFraction());
		}
		
		return hm;
	}
}
