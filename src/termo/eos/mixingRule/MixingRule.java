package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
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
     
     public abstract double b(HashMap<Component,Double>singleBs,
             ArrayList<Component> components,
             HashMap<Component,Double> fractions);
     
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
           ArrayList<Component> components, 
           HashMap<Component,Double> fractions,
            HashMap<Component,Double> single_as,
            HashMap<Component, Double> single_bs, 
             HashMap<Component,Double> alphaDerivatives,
             BinaryInteractionParameter k
           );
     
     public String getName(){
         return this.name;
     }
  
}
