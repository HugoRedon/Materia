package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixingRule {
    protected String name;
     
     public abstract double a(HashMap<Component,Double> singleAs,
             ArrayList<Component> components,
             HashMap<Component,Double> fractions,
             BinaryInteractionParameters k);
     
     public abstract double b(HashMap<Component,Double>singleBs,
             ArrayList<Component> components,
             HashMap<Component,Double> fractions);
     
     public abstract double oneOveraNParcialN2RespectN(HashMap<Component,Double> singleAs,
             ArrayList<Component> components,
             Component iComponent,
             HashMap<Component,Double> fractions, 
             BinaryInteractionParameters k);
     
     @Override public String toString(){
         return this.name;
     }
     
     public String getName(){
         return this.name;
     }
  
}
