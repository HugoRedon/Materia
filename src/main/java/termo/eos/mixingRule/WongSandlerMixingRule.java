
package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
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
public class WongSandlerMixingRule extends ExcessGibbsMixingRule {
       
    
    
    public WongSandlerMixingRule(ActivityModel activityModel,Cubic eos){
	super(activityModel, eos);
        name = "Wong Sandler (" + activityModel.getName()+")";
    }
    
    
    
    

    

    
    
       @Override
    public double b(Mixture mixture) {
	
	ActivityModelBinaryParameter params = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
	
	double b = 0;
	
	double numer = 0;
	double denomSum =0;
	
	for(Substance ci: mixture.getPureSubstances()){
	    for(Substance cj: mixture.getPureSubstances()){
			double xi = ci.getMolarFraction();
			double xj = cj.getMolarFraction();
			
			double bi = ci.calculate_b_cubicParameter();
			double ai = ci.calculate_a_cubicParameter();
			
			double bj = cj.calculate_b_cubicParameter();
			double aj = cj.calculate_a_cubicParameter();
			
			double kij = params.getK().getValue(ci.getComponent(), cj.getComponent());
			
			double R = Constants.R;
			
			denomSum += xi*(ai/(bi*R*mixture.getTemperature()));
			
			
			double isum = bi - ai/(R*mixture.getTemperature());
			double jsum = bj - aj/(R*mixture.getTemperature());
			
			numer+= xi*xj*((isum+jsum)/2)*(1-kij);
			
	    }
	}
	
	
	double ge = activityModel.excessGibbsEnergy(mixture);
	
	double denom = 1 + ge/(getL()*mixture.getTemperature() * Constants.R) - denomSum;
	
	return numer/denom;
	
    }

    
    
   

    



        @Override
    public double temperatureParcial_a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getParameter(Compound referenceComponent, Compound nonReferenceComponent, InteractionParameter params, int index) {
       int numberOfParametersOfActivity = activityModel.numberOfParameters();
       ActivityModelBinaryParameter activityParams = (ActivityModelBinaryParameter)params;
       
       if(index == numberOfParametersOfActivity){
           return activityParams.getK().getValue(referenceComponent, nonReferenceComponent);
       }else if(index == numberOfParametersOfActivity + 1){
           return activityParams.getK().getValue(nonReferenceComponent, referenceComponent);
       }else{
           return activityModel.getParameter(referenceComponent, nonReferenceComponent, activityParams, index);
       }
       
    }

    @Override
    public void setParameter(double value, Compound referenceComponent, Compound nonReferenceComponent, InteractionParameter params, int index) {
         int activityParameterCount = activityModel.numberOfParameters();
       ActivityModelBinaryParameter activityParams = (ActivityModelBinaryParameter)params;
       
       if(index == activityParameterCount){//simetrico
            activityParams.getK().setValue(referenceComponent, nonReferenceComponent,value);
       }
       else if(index == activityParameterCount + 1){
            activityParams.getK().setValue(nonReferenceComponent, referenceComponent,value);
       }
       else{
            activityModel.setParameter(value, referenceComponent, nonReferenceComponent, activityParams, index);
       }
    }

    @Override
    public int numberOfParameters() {
        return 2 + activityModel.numberOfParameters();
    }

   
}
