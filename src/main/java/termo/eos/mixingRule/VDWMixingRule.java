package termo.eos.mixingRule;

import java.util.ArrayList;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.phase.Phase;
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VDWMixingRule extends MixtureSubstance{
//
//    public VDWMixingRule(){
//        this.name = "Van Der Waals";
// 
//    }
      public VDWMixingRule(Cubic equationOfState,Alpha alpha,  ArrayList<Component> components,Phase phase, InteractionParameter k){
	super(equationOfState,phase);
	//this.mixingRule = mixingRule;
	for (Component component:components){
	    PureSubstance sub = new PureSubstance(equationOfState, alpha, component, phase);
	    pureSubstances.add(sub);
	}
	this.binaryParameters = k;
    }
   
    @Override
  public double calculate_a_cubicParameter(){
	
       double a = 0;
      for(PureSubstance iComponent: molarFractions.keySet()){
          for(PureSubstance jComponent: molarFractions.keySet()){
              double xi = molarFractions.get(iComponent);
              double xj = molarFractions.get(jComponent);
              
              double ai = iComponent.calculate_a_cubicParameter();
              double aj = jComponent.calculate_a_cubicParameter();
              
              double kij = binaryParameters.getValue(iComponent.getComponent(), jComponent.getComponent());
             
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij);
          }
      }
       return a;
  }
   
    @Override
   public double temperatureParcial_a(){
       double result = 0;
       for(PureSubstance ci: molarFractions.keySet()){
            for (PureSubstance cj: molarFractions.keySet()){
                double xi = molarFractions.get(ci);
                double tempAlphaDerivativeAlphai =ci.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ci.getComponent()) ;
		double a = calculate_a_cubicParameter();
                double xj = molarFractions.get(cj);
                double tempAlphaDerivativeAlphaj = cj.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, cj.getComponent());
                result += (1d/2d) * xi * xj * a*(tempAlphaDerivativeAlphai + tempAlphaDerivativeAlphaj);
            }
       }
       return result;
   }
   
    @Override
   public double oneOver_N_Parcial_a(PureSubstance iComponent){
       double sum = 0;
       double ai = iComponent.calculate_a_cubicParameter();
       for(PureSubstance kComponent : molarFractions.keySet()){
           double xk = molarFractions.get(kComponent);          
           double ak = kComponent.calculate_a_cubicParameter();//singleAs.get(kComponent);        
           double kik = binaryParameters.getValue(iComponent.getComponent(), kComponent.getComponent());
           sum += xk * Math.sqrt(ai * ak ) * (1- kik);
       }

       return  2 * sum ;
   }


    @Override
    public double calculate_b_cubicParameter() {
         double b = 0;
      for(PureSubstance iComponent:molarFractions.keySet()){
            double xi = molarFractions.get(iComponent);
            double bi = iComponent.calculate_b_cubicParameter();
            b += xi * bi ;
      }
       return b;
    }
//    @Override
//    public double temperatureParcial_a(double temperature, ArrayList<Component> components, HashMap<Component, Double> fractions, HashMap<Component, Double> single_as, HashMap<Component, Double> single_bs, HashMap<Component, Double> alphaDerivatives, BinaryInteractionParameter k) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

 

   
 
    
}
