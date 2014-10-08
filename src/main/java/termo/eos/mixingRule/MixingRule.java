package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.optimization.ContainsParameters;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule implements ContainsParameters {
    protected String name;
     

     public abstract double a(Mixture mixture);
     
     
     public double b(Mixture mixture) {
          double b = 0;
       for(Substance iComponent:mixture.getPureSubstances()){
             double xi = iComponent.getMolarFraction();
             double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
             b += xi * bi ;
       }
        return b;
     }
     
     public abstract double oneOverNParcial_aN2RespectN(
             Substance iComponent,
             Mixture mixture);
     
     public double oneOverNParcial_bNRespectN(Substance iComponent,
     		Mixture mixture) {
     	return iComponent.calculate_b_cubicParameter();
     			
     }
     
     @Override public String toString(){
         return this.name;
     }
     public abstract double temperatureParcial_a(
             Mixture mixture
           );
     
     public String getName(){
         return this.name;
     }
  
    public abstract double getParameter(Compound referenceComponent,
            Compound nonReferenceComponent,
            InteractionParameter params,
            int index);
    public abstract void setParameter(double value, 
            Compound referenceComponent,
            Compound nonReferenceComponent,
            InteractionParameter params,
            int index);
    
    public abstract int numberOfParameters() ;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MixingRule other = (MixingRule) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
