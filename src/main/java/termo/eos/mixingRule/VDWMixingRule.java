package termo.eos.mixingRule;

import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
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
    public double a(double temperature, HashMap<PureSubstance, Double> molarFractions, InteractionParameter binaryParameters) {
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
    public double temperatureParcial_a(double temperature, HashMap<PureSubstance, Double> molarFractions, InteractionParameter k) {
        double result = 0;
       for(PureSubstance ci: molarFractions.keySet()){
            for (PureSubstance cj: molarFractions.keySet()){
                double xi = molarFractions.get(ci);
                double tempAlphaDerivativeAlphai =ci.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ci.getComponent()) ;
		double a = this.a(temperature, molarFractions, k);
                double xj = molarFractions.get(cj);
                double tempAlphaDerivativeAlphaj = cj.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, cj.getComponent());
                result += (1d/2d) * xi * xj * a*(tempAlphaDerivativeAlphai + tempAlphaDerivativeAlphaj);
            }
       }
       return result;
    }

    @Override
    public double oneOverNParcial_aN2RespectN(double temperature, PureSubstance iComponent, HashMap<PureSubstance, Double> molarFractions, InteractionParameter binaryParameters) {
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
    public double b(HashMap<PureSubstance, Double> molarFractions, double temperature, InteractionParameter k) {
        double b = 0;
        for(PureSubstance iComponent:molarFractions.keySet()){
              double xi = molarFractions.get(iComponent);
              double bi = iComponent.calculate_b_cubicParameter();
              b += xi * bi ;
        }
        return b;
    }
   
    
    

   
  
 

    
}
