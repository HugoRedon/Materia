
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
				double ai = ci.calculate_a_cubicParameter();
				double bi = ci.calculate_b_cubicParameter();
				double R = Constants.R;
				
		    	denomSum += xi*(ai/(bi*R*mixture.getTemperature()));
				double bMinusAOverRtij = bMinusAOverRT(ci, cj,params);
				
				numer+= bMinusAOverRtij;
				
		    }
		}
	
	
		double ge = activityModel.excessGibbsEnergy(mixture);
		
		double denom = 1 - ge/(getL()*mixture.getTemperature() * Constants.R) - denomSum;
		
		return numer/denom;
	
    }

       
       public double bMinusAOverRT(Substance ck,Substance cj,ActivityModelBinaryParameter params){
		   double T = ck.getTemperature();
		   double xk = ck.getMolarFraction();
			double xj = cj.getMolarFraction();
			
			double bk = ck.calculate_b_cubicParameter();
			double ak = ck.calculate_a_cubicParameter();
			
			double bj = cj.calculate_b_cubicParameter();
			double aj = cj.calculate_a_cubicParameter();
			
			double Kkj = params.getK().getValue(ck.getComponent(), cj.getComponent());
			
			double R = Constants.R;
								
			double ksum = bk - ak/(R*T);
			double jsum = bj - aj/(R*T);
			 return xk*xj*((ksum+jsum)/2)*(1-Kkj);
       }
    
    
   
	@Override
	public double oneOverNParcial_bNRespectN(Substance iComponent,
			Mixture mixture) {
		
		ActivityModelBinaryParameter params = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
		
		double T =mixture.getTemperature();
		
		double a = mixture.calculate_a_cubicParameter();
		double b = mixture.calculate_b_cubicParameter();
		double alpha = a/(b*Constants.R *T);
	
		double ai = iComponent.calculate_a_cubicParameter();
		double bi = iComponent.calculate_b_cubicParameter(); 
		double alphai = ai/(bi*Constants.R*T);
		
		double lnGammai = Math.log(activityModel.activityCoefficient(iComponent, mixture));
		
		double firstSum = 0;
		for(Substance cj: mixture.getPureSubstances()){
			double xj = cj.getMolarFraction();
			double bMinusART = bMinusAOverRT(iComponent, cj, params);
					
			firstSum += 2*xj * bMinusART;
		}
		
		double secondSum = 0;
		for(Substance ck: mixture.getPureSubstances()){
			for(Substance cj : mixture.getPureSubstances()){
				double xk = ck.getMolarFraction();
				double xj =cj.getMolarFraction();
				double bMinusARTkj = bMinusAOverRT(ck, cj, params);
				
				secondSum += xk*xj*bMinusARTkj;
			}
		}
		
		
		
		double result = (firstSum*(1-alpha)- (1-alphai+ lnGammai/getL())*(secondSum))/Math.pow(1-alpha, 2);
		return result;
		
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
	public String getParameterName(int index) {
		 int numberOfParametersOfActivity = activityModel.numberOfParameters();	       
	       
	       if(index == numberOfParametersOfActivity){
	           return "Kij";
	       }else if(index == numberOfParametersOfActivity + 1){
	           return "Kji";
	       }else{
	           return activityModel.getParameterName(index);
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
