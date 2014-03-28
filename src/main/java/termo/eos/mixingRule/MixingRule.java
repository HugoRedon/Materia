package termo.eos.mixingRule;

import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.substance.PureSubstance;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     

     public abstract double a(double temperature,
           
             HashMap<PureSubstance,Double> fractions,
             InteractionParameter k);
     
     public abstract double b(HashMap<PureSubstance,Double> fractions,double temperature,InteractionParameter k );
     
     public abstract double oneOverNParcial_aN2RespectN(
             double temperature,
             PureSubstance iComponent,
             HashMap<PureSubstance,Double> fractions, 
             InteractionParameter k);
     
     @Override public String toString(){
         return this.name;
     }
     public abstract double temperatureParcial_a(
             double temperature,        
           HashMap<PureSubstance,Double> fractions,
             InteractionParameter k
           );
     
     public String getName(){
         return this.name;
     }
  
}
