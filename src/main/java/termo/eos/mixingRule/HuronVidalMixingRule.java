/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.mixingRule;

import java.util.ArrayList;
import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.phase.Phase;
import termo.matter.Mixture;
import termo.matter.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class HuronVidalMixingRule extends MixingRule{

    ActivityModel activityModel;
    private double L;
    
    public HuronVidalMixingRule(ActivityModel activityModel, Cubic equationOfState){
        this.activityModel = activityModel;
        L = equationOfState.calculateL(1, 1);
    }


    
    

    @Override
    public double a(Mixture mixture) {
            
            double b = b(mixture);
            double excessGibbs = activityModel.excessGibbsEnergy( mixture);
            
            double firstTerm = 0;
           
            for(PureSubstance ci :mixture.getPureSubstances()){
                 double xi = ci.getMolarFraction();
                 double ai = ci.calculate_a_cubicParameter();//singleAs.get(ci);
                 double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
                
                firstTerm += xi * (ai) / bi ;
            }
       return b* (firstTerm  - excessGibbs/(getL()));
    }

    
    @Override
    public double b(Mixture mixture) {
         double b = 0;
      for(PureSubstance iComponent:mixture.getPureSubstances()){
            double xi = iComponent.getMolarFraction();
            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }


    
    
    @Override
    public double oneOverNParcial_aN2RespectN(
	PureSubstance ci,
        Mixture mixture) {
         
        double b = b(mixture);
        double a =a(mixture);
        
        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
	double ai = ci.calculate_a_cubicParameter();
	double bi = ci.calculate_b_cubicParameter();
	double alphai = ai/(bi*Constants.R * mixture.getTemperature());
	
        double gammai = activityModel.activityCoefficient( ci,mixture   );

        return b * Constants.R * mixture.getTemperature()*( alphai -  Math.log(gammai)/L) + a * bi / b;
    }

    
   

    /**
     * @return the L
     */
    public double getL() {
	return L;
    }

    /**
     * @param L the L to set
     */
    public void setL(double L) {
	this.L = L;
    }



    @Override
    public double temperatureParcial_a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
 
    
    
}
