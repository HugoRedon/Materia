package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     

     public abstract double a(MixtureSubstance mixture);
     
     public abstract double b(MixtureSubstance mixture);
     
     public abstract double oneOverNParcial_aN2RespectN(
             PureSubstance iComponent,
             MixtureSubstance mixture);
     
     @Override public String toString(){
         return this.name;
     }
     public abstract double temperatureParcial_a(
             MixtureSubstance mixture
           );
     
     public String getName(){
         return this.name;
     }
  
}
