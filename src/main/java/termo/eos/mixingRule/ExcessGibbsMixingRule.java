/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.eos.mixingRule;

import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo
 */
public abstract class ExcessGibbsMixingRule extends MixingRule {
    ActivityModel activityModel;
 

	private double L;
   

    public ExcessGibbsMixingRule(ActivityModel activityModel, Cubic equationOfState){
        this.activityModel = activityModel;
        L = equationOfState.calculateL(1, 1);
    }

    @Override
    public double a(Mixture mixture) {
      
        double b = b(mixture);
        double excessGibbs = activityModel.excessGibbsEnergy(mixture);
        double alphai = 0;
        for (Substance ci : mixture.getPureSubstances()) {
            double xi = ci.getMolarFraction();
            double ai = ci.calculate_a_cubicParameter(); 
            double bi = ci.calculate_b_cubicParameter(); 
            alphai += xi * (ai) / bi;
        }
        return b * (alphai + excessGibbs / (getL()));
    }
    
        @Override
    public double oneOverNParcial_aN2RespectN(            
        Substance ci, Mixture mixture) {
         
        double b = b( mixture);
        double a =a(mixture);
        double parcial_b = oneOverNParcial_bNRespectN(ci, mixture);
        
        double D = a/(b*Constants.R * mixture.getTemperature() );
        double parcial_D= parcialD_respectN(ci,mixture);
        return ( Constants.R * mixture.getTemperature() )* (b*parcial_D + D*parcial_b );
    }
    
    public double parcialD_respectN(Substance ci,Mixture mixture){
    	double ai = ci.calculate_a_cubicParameter();
		double bi = ci.calculate_b_cubicParameter();
		double alphai = ai/(bi*Constants.R * mixture.getTemperature());
	
        double gammai = activityModel.activityCoefficient( ci,mixture);
    	return alphai +  Math.log(gammai)/L;
    }
        
    @Override
    public double oneOverNParcial_bNRespectN(Substance iComponent,
    		Mixture mixture) {
    	return iComponent.calculate_b_cubicParameter();
    }
    
    @Override
    public double temperatureParcial_a(Mixture mixture) {    	
    	double b = mixture.calculate_b_cubicParameter();
    	double firstTerm =0; 
    	for(Substance subi: mixture.getPureSubstances()){
    		double xi = subi.getMolarFraction();
    		double ai = subi.calculate_a_cubicParameter();
    		double bi = subi.calculate_b_cubicParameter();
    		double temp = subi.getTemperature();
    		Compound comp = subi.getComponent();
    		double alphaDerivative_i = subi.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temp, comp);
    		
    		firstTerm += xi*(ai/bi)*alphaDerivative_i;
    	}
    	double temperature =mixture.getTemperature();
    	double secondTerm = temperature *activityModel.parcialExcessGibbsRespectTemperature(mixture)*(-1d/L);
    	return b*(firstTerm+secondTerm);
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
    
    
    public ActivityModel getActivityModel() {
 		return activityModel;
 	}

 	public void setActivityModel(ActivityModel activityModel) {
 		this.activityModel = activityModel;
 	}    
    
}
