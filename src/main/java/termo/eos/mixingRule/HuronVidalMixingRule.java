/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.mixingRule;

import termo.activityModel.ActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
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
    public double getParameter(Compound referenceComponent, Compound nonReferenceComponent, InteractionParameter params, int index) {
        return activityModel.getParameter(referenceComponent, nonReferenceComponent, (ActivityModelBinaryParameter)params, index);
    }

    @Override
    public void setParameter(double value, Compound referenceComponent, Compound nonReferenceComponent, InteractionParameter params, int index) {
        activityModel.setParameter(value, referenceComponent, nonReferenceComponent, (ActivityModelBinaryParameter)params, index);
    }

    @Override
    public int numberOfParameters() {
        return activityModel.numberOfParameters();
    }
	@Override
	public String getParameterName(int index) {
		return activityModel.getParameterName( index);
	}

   
 
    
    
}
