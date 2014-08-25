package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     

     public abstract double a(Mixture mixture);
     
     public abstract double b(Mixture mixture);
     
     public abstract double oneOverNParcial_aN2RespectN(
             Substance iComponent,
             Mixture mixture);
     
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
