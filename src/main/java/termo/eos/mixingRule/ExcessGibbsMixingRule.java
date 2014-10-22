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
import termo.eos.EquationsOfState;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo
 */
public abstract class ExcessGibbsMixingRule extends MixingRule {
    ActivityModel activityModel;
 

	private double L;
	private double L2 =0;
   

    public ExcessGibbsMixingRule(ActivityModel activityModel, Cubic equationOfState){
        this.activityModel = activityModel;
        L = equationOfState.calculateL(1, 1);
    }
    public ExcessGibbsMixingRule(ActivityModel activityModel,Cubic equationOfState ,boolean isModified){
    	this(activityModel,equationOfState);
    	L = equationOfState.calculateL(1.235, 1);
    	L2= L;
    }

    @Override
    public double a(Mixture mixture) {
      
        double b = b(mixture);
        double excessGibbs = activityModel.excessGibbsEnergy(mixture);
        double alphai = 0;
        double modifiedHVTerm = 0;
        for (Substance ci : mixture.getPureSubstances()) {
            double xi = ci.getMolarFraction();
            double ai = ci.calculate_a_cubicParameter(); 
            double bi = ci.calculate_b_cubicParameter(); 
            alphai += xi * (ai) / bi;
            modifiedHVTerm += xi*Math.log(b/bi);
        }
        double c2 = (L2==0)?0:1/L2;
        return b * (alphai +c2*modifiedHVTerm*Constants.R * mixture.getTemperature()+ excessGibbs / L);
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
        
        double b = mixture.calculate_b_cubicParameter();
        double modifiedTerm = Math.log(b/bi) + (bi-b)/b;
        
        double c2 = (L2==0)?0:1/L2;
        
    	return alphai +c2*modifiedTerm + Math.log(gammai)/L;
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
	public double getL2() {
		return L2;
	}
	public void setL2(double l2) {
		L2 = l2;
	}    
    
}
