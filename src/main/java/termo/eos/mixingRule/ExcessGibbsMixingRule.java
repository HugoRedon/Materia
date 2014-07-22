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
        //            ArrayList<PureSubstance> components = new ArrayList();
        //	    HashMap<Component,Double> fra = new HashMap();
        //	    for(Substance pure: fractions.keySet()){
        //		components.add(pure);
        //		fra.put(pure.getComponent(), fractions.get(pure));
        //	    }
        double b = b(mixture);
        double excessGibbs = activityModel.excessGibbsEnergy(mixture);
        double firstTerm = 0;
        for (Substance ci : mixture.getPureSubstances()) {
            double xi = ci.getMolarFraction();
            double ai = ci.calculate_a_cubicParameter(); //singleAs.get(ci);
            double bi = ci.calculate_b_cubicParameter(); //singleBs.get(ci);
            firstTerm += xi * (ai) / bi;
            // secondTerm = Constants.R * temperature * c1 *xi * Math.log(b / bi);
        }
        return b * (firstTerm + excessGibbs / (getL()));
    }
    
        @Override
    public double oneOverNParcial_aN2RespectN(
            
            
            Substance ci, Mixture mixture) {
         
        double b = b( mixture);
        double a =a(mixture);
        
        
        //double alphai = ci.getAlpha().alpha(temperature, ci.getComponent());//singleAlphas.get( ci);
	
	double ai = ci.calculate_a_cubicParameter();
	double bi = ci.calculate_b_cubicParameter();
	double alphai = ai/(bi*Constants.R * mixture.getTemperature());
	
        double gammai = activityModel.activityCoefficient( ci,mixture);
//        double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
//	double ai = ci.calculate_a_cubicParameter(temperature);
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
    
    
    
    
}
