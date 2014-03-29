package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.matter.Mixture;
import termo.matter.PureSubstance;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     

     public abstract double a(Mixture mixture);
     
     public abstract double b(Mixture mixture);
     
     public abstract double oneOverNParcial_aN2RespectN(
             PureSubstance iComponent,
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
  
}
