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
public class VDWMixingRule extends MixingRule{

    public VDWMixingRule(){
        this.name = "Van Der Waals";
 
    }
    
   
    @Override
  public double a(
	    double temperature,
           HashMap<PureSubstance,Double> fractions,
           InteractionParameter k){
	
       double a = 0;
      for(PureSubstance iComponent: fractions.keySet()){
          for(PureSubstance jComponent: fractions.keySet()){
              double xi = fractions.get(iComponent);
              double xj = fractions.get(jComponent);
              
              double ai = iComponent.calculate_a_cubicParameter();
              double aj = jComponent.calculate_a_cubicParameter();
              
              double kij = k.getValue(iComponent.getComponent(), jComponent.getComponent());
             
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij);
          }
      }
       return a;
  }
   
    @Override
   public double temperatureParcial_a(
            double temperature,
           HashMap<PureSubstance,Double> fractions,
             InteractionParameter k
           ){
       
       double result = 0;
       for(PureSubstance ci: fractions.keySet()){
            for (PureSubstance cj: fractions.keySet()){
                double xi = fractions.get(ci);
               // double ai = ci.calculate_a_cubicParameter(temperature);//single_as.get(ci);
                double tempAlphaDerivativeAlphai =ci.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ci.getComponent()) ;//alphaDerivatives.get(ci);
                
		double a = a(temperature, fractions, k);
		
                double xj = fractions.get(cj);
		//double aj = cj.calculate_a_cubicParameter(temperature);//single_as.get(cj);
                double tempAlphaDerivativeAlphaj = cj.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, cj.getComponent());//alphaDerivatives.get(cj);
                
                result += (1d/2d) * xi * xj * a*(tempAlphaDerivativeAlphai + tempAlphaDerivativeAlphaj);
                
            }
       }
       return result;
   }
   
    @Override
   public double oneOverNParcial_aN2RespectN(
            double temperature,
        PureSubstance iComponent,
        HashMap<PureSubstance,Double> fractions,
        InteractionParameter k){

       double sum = 0;
       double ai = iComponent.calculate_a_cubicParameter();
       
       for(PureSubstance kComponent : fractions.keySet()){
           double xk = fractions.get(kComponent);          
           double ak = kComponent.calculate_a_cubicParameter();//singleAs.get(kComponent);        
           
           double kik = k.getValue(iComponent.getComponent(), kComponent.getComponent());
           //double kik = BinaryInteractionParameter.getk(iComponent, kComponent);
           
           sum += xk * Math.sqrt(ai * ak ) * (1- kik);
       }
      
//       double a = a(temperature,singleAs, singleBs,components, fractions,k);
       
       return  2 * sum ;
   }


    @Override
    public double b(HashMap<PureSubstance,Double> fractions,double temperature,InteractionParameter k) {
         double b = 0;
      for(PureSubstance iComponent:fractions.keySet()){
            double xi = fractions.get(iComponent);
            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }
//    @Override
//    public double temperatureParcial_a(double temperature, ArrayList<Component> components, HashMap<Component, Double> fractions, HashMap<Component, Double> single_as, HashMap<Component, Double> single_bs, HashMap<Component, Double> alphaDerivatives, BinaryInteractionParameter k) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

 

   
 
    
}
