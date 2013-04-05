package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.component.VanDerWaalsParameters;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VDWMixingRule extends MixingRule{

    public VDWMixingRule(){
        this.name = "Van Der Waals";
 
    }
    
   @Override
  public double a(double temperature,
           HashMap<Component,Double> singleAs,
           HashMap<Component,Double> singleBs,
           ArrayList<Component> components,
           HashMap<Component,Double> fractions,
           BinaryInteractionParameter k){
       double a = 0;
       
       
      for(Component iComponent:components){
          for(Component jComponent:components){
              double xi = fractions.get(iComponent);
              double xj = fractions.get(jComponent);
              
              double ai = singleAs.get(iComponent);
              double aj = singleAs.get(jComponent);
              
              //TODO: depends on the equation of state 
              double kij = k.getValue(iComponent, jComponent);
             // double kij = BinaryInteractionParameter.getk(iComponent, jComponent);
              
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij);
          }
          
      }
       
       return a;
  }
   
    @Override
   public double temperatureParcial_a(
            double temperature,
           ArrayList<Component> components, 
           HashMap<Component,Double> fractions,
            HashMap<Component,Double> single_as,
            HashMap<Component, Double> single_bs, 
             HashMap<Component,Double> alphaDerivatives,
             BinaryInteractionParameter k
           ){
       
       double result = 0;
       for(Component ci: components){
            for (Component cj: components){
                double xi = fractions.get(ci);
                double ai = single_as.get(ci);
                double tempAlphaDerivativeAlphai = alphaDerivatives.get(ci);
                
                double xj = fractions.get(cj);
                  double aj = single_as.get(cj);
                double tempAlphaDerivativeAlphaj = alphaDerivatives.get(cj);
                
                result += (1d/2d) * xi * xj * Math.sqrt(ai * aj )*(1- k.getValue(ci, cj))*(tempAlphaDerivativeAlphai + tempAlphaDerivativeAlphaj);
                
            }
       }
       return result;
   }
   
   
   
    @Override
   public double oneOverNParcial_aN2RespectN(
            double temperature,
            HashMap<Component,Double> singleAs,
            HashMap<Component,Double> singleBs,
            HashMap<Component, Double> singleAlphas, 
        ArrayList<Component> components,
        Component iComponent,
        HashMap<Component,Double> fractions,
        BinaryInteractionParameter k){
        
    
        
       double sum = 0;
       
       double ai = singleAs.get(iComponent);
       
       for(Component kComponent : components){
           double xk = fractions.get(kComponent);          
           double ak = singleAs.get(kComponent);        
           
           double kik = k.getValue(iComponent, kComponent);
           //double kik = BinaryInteractionParameter.getk(iComponent, kComponent);
           
           sum += xk * Math.sqrt(ai * ak ) * (1- kik);
       }
      
//       double a = a(temperature,singleAs, singleBs,components, fractions,k);
       
       return  2 * sum ;
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
