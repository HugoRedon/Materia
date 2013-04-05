package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     
//     public abstract double a(HashMap<Component,Double> singleAs,
//             ArrayList<Component> components,
//             HashMap<Component,Double> fractions,
//             BinaryInteractionParameter k);
     public abstract double a(double temperature,
             HashMap<Component,Double> singleAs,
             HashMap<Component,Double> singleBs,
             ArrayList<Component> components,
             HashMap<Component,Double> fractions,
             BinaryInteractionParameter k);
     
     public abstract double b(HashMap<Component,Double>singleBs,
             ArrayList<Component> components,
             HashMap<Component,Double> fractions);
     
     public abstract double oneOverNParcial_aN2RespectN(
             double temperature,
             HashMap<Component,Double> singleAs,
             HashMap<Component,Double> singleBs,
             HashMap<Component, Double> singleAlphas, 
             ArrayList<Component> components,
             Component iComponent,
             HashMap<Component,Double> fractions, 
             BinaryInteractionParameter k);
     
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
