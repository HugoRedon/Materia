package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
//import termo.component.BinaryInteractionParameters;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VDWMixingRule extends MixingRule{
    public VDWMixingRule(){
        this.name = "Van Der Waals";
    }
    
   @Override
  public double a(HashMap<Component,Double> singleAs,ArrayList<Component> components,HashMap<Component,Double> fractions,BinaryInteractionParameters k){
       double a = 0;
       
      for(Component iComponent:components){
          for(Component jComponent:components){
              double xi = fractions.get(iComponent);
              double xj = fractions.get(jComponent);
              
              double ai = singleAs.get(iComponent);
              double aj = singleAs.get(jComponent);
              
              //TODO: depends on the equation of state 
              double kij = k.getValue(iComponent, jComponent);
             // double kij = BinaryInteractionParameters.getk(iComponent, jComponent);
              
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij);
          }
          
      }
       
       return a;
  }
   
    @Override
   public double oneOveraNParcialN2RespectN(HashMap<Component,Double> singleAs,
        ArrayList<Component> components,
        Component iComponent,
        HashMap<Component,Double> fractions,
        BinaryInteractionParameters k){
        
       double sum = 0;
       
       double ai = singleAs.get(iComponent);
       
       for(Component kComponent : components){
           double xk = fractions.get(kComponent);          
           double ak = singleAs.get(kComponent);        
           
           double kik = k.getValue(iComponent, kComponent);
           //double kik = BinaryInteractionParameters.getk(iComponent, kComponent);
           
           sum += xk * Math.sqrt(ai * ak ) * (1- kik);
       }
      
       double a = a(singleAs, components, fractions,k);
       
       return  2 * sum / a;
   }

    @Override
    public double b(HashMap<Component, Double> singleBs,ArrayList<Component> components,HashMap<Component,Double> fractions) {
         double b = 0;
       
      for(Component iComponent:components){
         
            double xi = fractions.get(iComponent);
            double bi = singleBs.get(iComponent);
            b += xi * bi ;
      }
       
       return b;
    }
 
    
}
