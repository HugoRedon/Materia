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
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class HuronVidalMixingRule extends ExcessGibbsMixingRule{

    
    
    public HuronVidalMixingRule(ActivityModel activityModel, Cubic equationOfState){
        super(activityModel, equationOfState);
        name = "Huron Vidal (" + activityModel.getName()+ ")";
    }
//

    
    



    
    @Override
    public double b(Mixture mixture) {
         double b = 0;
      for(Substance iComponent:mixture.getPureSubstances()){
            double xi = iComponent.getMolarFraction();
            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }


    
    


    
   




    @Override
    public double temperatureParcial_a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getParameter(Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        return activityModel.getParameter(referenceComponent, nonReferenceComponent, (ActivityModelBinaryParameter)params, index);
    }

    @Override
    public void setParameter(double value, Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        activityModel.setParameter(value, referenceComponent, nonReferenceComponent, (ActivityModelBinaryParameter)params, index);
    }

    @Override
    public int numberOfParameters() {
        return activityModel.numberOfParameters();
    }

   
 
    
    
}
