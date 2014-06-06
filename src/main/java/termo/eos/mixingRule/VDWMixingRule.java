package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VDWMixingRule extends MixingRule{

    public VDWMixingRule(){
        this.name = "Van Der Waals";
 
    }


    public double a(Mixture mixture){
        double a= 0;
        for(Substance iComponent: mixture.getPureSubstances()){
               for(Substance jComponent: mixture.getPureSubstances()){
              double xi = mixture.getFraction(iComponent);
              double xj = mixture.getFraction(jComponent);
              
              double ai = iComponent.calculate_a_cubicParameter();
              double aj = jComponent.calculate_a_cubicParameter();
              
              
              double kij = mixture.getBinaryParameters().getValue(iComponent.getComponent(), jComponent.getComponent());
             
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij);
          }
        }
        return a;
    }
    


    @Override
    public double temperatureParcial_a(Mixture mixture) {
        double result = 0;
       for(Substance ci: mixture.getPureSubstances()){
            for (Substance cj: mixture.getPureSubstances()){
                double xi = mixture.getFraction(ci);
                double tempAlphaDerivativeAlphai =ci.getAlpha()
                        .TempOverAlphaTimesDerivativeAlphaRespectTemperature(
                                mixture.getTemperature(), ci.getComponent()) ;
		double a = this.a(mixture);
                double xj =mixture.getFraction(cj);
                double tempAlphaDerivativeAlphaj = cj.getAlpha()
                        .TempOverAlphaTimesDerivativeAlphaRespectTemperature(
                                mixture.getTemperature(), cj.getComponent());
                result += (1d/2d) * xi * xj * a*(tempAlphaDerivativeAlphai + tempAlphaDerivativeAlphaj);
            }
       }
       return result;
    }

    @Override
    public double oneOverNParcial_aN2RespectN( Substance iComponent, 
            Mixture mixture) {
        double sum = 0;
        double ai = iComponent.calculate_a_cubicParameter();
        for(Substance kComponent : mixture.getPureSubstances()){
            double xk = mixture.getFraction(kComponent);          
            double ak = kComponent.calculate_a_cubicParameter();//singleAs.get(kComponent);        
            double kik = mixture.getBinaryParameters().getValue(iComponent.getComponent(), kComponent.getComponent());
            sum += xk * Math.sqrt(ai * ak ) * (1- kik);
        }

        return  2 * sum ;
    }

    @Override
    public double b(Mixture mixture) {
        double b = 0;
        for(Substance iComponent:mixture.getPureSubstances()){
              double xi = mixture.getFraction(iComponent);
              double bi = iComponent.calculate_b_cubicParameter();
              b += xi * bi ;
        }
        return b;
    }

    @Override
    public double getParameter(Component referenceComponent, 
            Component nonReferenceComponent,
            InteractionParameter params,
            int index) {
            if(index ==0){
                return params.getValue(referenceComponent, nonReferenceComponent);
            }else {
                return 0;
            }
    }

    @Override
    public void setParameter(double value, Component referenceComponent, Component nonReferenceComponent, 
            InteractionParameter params,
            int index) {
        if(index ==0){
            params.setValue(referenceComponent, nonReferenceComponent, value);
        }
    }

    @Override
    public int numberOfParameters() {
        return 1;
    }

 

   
  
 

    
}
