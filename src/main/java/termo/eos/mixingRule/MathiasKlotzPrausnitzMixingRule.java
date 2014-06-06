package termo.eos.mixingRule;

import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MathiasKlotzPrausnitzMixingRule extends MixingRule {
    public MathiasKlotzPrausnitzMixingRule(){
        name = "Mathias-Klotz-Prausnitz";
    }

   
    @Override
    public double a(Mixture mixture) {
         //double a = 0;
       
         InteractionParameter k = mixture.getBinaryParameters();
       double firstTerm  = 0;
       double secondTerm = 0;
      for(Substance iComponent:mixture.getPureSubstances()){
          double insideFactor = 0;
          double xi = iComponent.getMolarFraction();
          for(Substance jComponent:mixture.getPureSubstances()){
              
              double xj = jComponent.getMolarFraction();
              
              double ai = iComponent.calculate_a_cubicParameter();
              double aj = jComponent.calculate_a_cubicParameter();
              
              double kij = k.getValue(iComponent.getComponent(), jComponent.getComponent());
              double kji = k.getValue(jComponent.getComponent(), iComponent.getComponent());
              
              firstTerm += xi * xj * Math.sqrt(ai * aj) * (1-kij) ;
              
              insideFactor += xj * Math.pow(ai * aj , 1d/6d) * Math.pow(kij - kji, 1d/3d);
              //a +=  + xi * Math.pow(xj,3) * Math.sqrt(ai * aj) * (kij - kji);
          }
          
         secondTerm += xi * Math.pow(insideFactor, 3);
      }
       
       return firstTerm + secondTerm;
    }

    @Override
      public double b(Mixture mixture) {
         double b = 0;
       
      for(Substance iComponent:mixture.getPureSubstances()){
         
            double xi = iComponent.getMolarFraction();
            double bi = iComponent.calculate_b_cubicParameter();
            b += xi * bi ;
      }
       
       return b;
    }

    @Override
    public double oneOverNParcial_aN2RespectN(
            Substance cl,
            Mixture mixture) {
        
        double xl =0;
        
        
        
        double al =0;
        
        
        
        double alphaiDeriv =0;
        double alphajDeriv =0;
        
        
        
        double firstTerm =0;
        double secondTerm =0;
        
        
        double firstFactor =0;
        double secondFactor =0;
        
        InteractionParameter k = mixture.getBinaryParameters();
        
        for(Substance cj: mixture.getPureSubstances()){
            double xj = cj.getMolarFraction();
            double aj = cj.getMolarFraction();

            double klj = k.getValue(cl.getComponent(), cj.getComponent());
            double kjl = k.getValue(cj.getComponent(), cl.getComponent());
            
            firstTerm += xj * Math.sqrt(al * aj)*(2 - klj - kjl);
            secondTerm += xj * Math.pow(al*aj, 1d/6d) * Math.pow(klj- kjl, 1d/3d);
          
        }
        
        double thirdTerm = 0;
        
        for(Substance ci : mixture.getPureSubstances()){
            double xi = ci.getMolarFraction();
            double ai = ci.calculate_a_cubicParameter();
            double commonTerm = 0;
            double insideDeriv = 0;
            for(Substance cj : mixture.getPureSubstances()){
                double xj = cj.getMolarFraction();
                double aj = cj.calculate_a_cubicParameter();
                
                double kij = k.getValue(ci.getComponent(), cj.getComponent());
                double kji = k.getValue(cj.getComponent(), ci.getComponent());
                
                commonTerm+= xj *Math.pow(ai*aj,1d/6d) * Math.pow(kij-kji, 1d/3d);
                insideDeriv+= Math.pow(ai*aj,1d/6d) * Math.pow(kij-kji, 1d/3d);
            }
            
            thirdTerm += xi*(Math.pow(commonTerm,2)* 3* insideDeriv - 2*commonTerm);
            
        }        
        return firstTerm + Math.pow(secondTerm,3) + thirdTerm;
                
    }
    
    
 

    @Override
    public double temperatureParcial_a(Mixture mixture) {
        
        double xi =0;
        double xj =0;
        
        double ai =0;
        double aj =0;
        
        double alphaiDeriv =0;
        double alphajDeriv =0;
        
        double kij =0;
        double kji =0;
        
        double firstTerm =0;
        double secondTerm =0;
        
        
        double firstFactor =0;
        double secondFactor =0;
        
        InteractionParameter k = mixture.getBinaryParameters();
        
        for(Substance ci: mixture.getPureSubstances()){
            xi = ci.getMolarFraction();
            ai = ci.calculate_a_cubicParameter();
            alphaiDeriv = ci.getAlpha()
                    .TempOverAlphaTimesDerivativeAlphaRespectTemperature(
                            ci.getTemperature(),
                            ci.getComponent() 
                    );
            
            firstFactor =0;
            secondFactor =0;
            
            for(Substance cj : mixture.getPureSubstances()){
                
                xj = cj.getMolarFraction();
                aj = cj.calculate_a_cubicParameter();
                alphajDeriv = cj.getAlpha()
                        .TempOverAlphaTimesDerivativeAlphaRespectTemperature(
                                cj.getTemperature(), cj.getComponent()
                        );
                
                kij = k.getValue(ci.getComponent(), cj.getComponent());
                kji = k.getValue(cj.getComponent(), ci.getComponent());
                
                firstTerm +=  xi * xj * Math.sqrt(ai *aj)*(1-kij)*(alphaiDeriv + alphajDeriv);
                
                firstFactor += xj *Math.pow(ai*aj, 1d/6d) * Math.pow(kij-kji, 1d/3d);
                secondFactor += xj*Math.pow(ai*aj, 1d/6d)*Math.pow(kij - kji,1d/3d) * (alphaiDeriv+alphajDeriv);
            }
            secondTerm += xi * Math.pow(firstFactor,2) * secondFactor;
        }
        return (1d/2d)*(firstTerm + secondFactor);
    }

    @Override
    public double getParameter(Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        switch(index){
            case 0: return params.getValue(referenceComponent, nonReferenceComponent);
            case 1: return params.getValue(nonReferenceComponent, referenceComponent);
            default: return 0;
        }
        
    }

    @Override
    public void setParameter(double value, Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        switch(index){
            case 0: 
                params.setValue(referenceComponent, nonReferenceComponent,value);
                break;
            case 1:  
                params.setValue(nonReferenceComponent, referenceComponent,value);
                break;
        }
    }

    @Override
    public int numberOfParameters() {
        return 2;
    }


}
