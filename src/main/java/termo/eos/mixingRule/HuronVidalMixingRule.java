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
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class HuronVidalMixingRule extends MixtureSubstance{

    ActivityModel activityModel;
    private double L;
    
    public HuronVidalMixingRule(){
       super(null, null, null, Phase.VAPOR, null, null);
    }
    
//    public HuronVidalMixingRule(ActivityModel activityModel,Cubic eos,Phase aPhase){
//	super(eos, aPhase);
//	this.activityModel = activityModel;
//	
//	L = eos.calculateL(1, 1);
//	
//    }

//    public HuronVidalMixingRule(
//	    Cubic equationOfState, 
//	    Alpha alpha, 
//	    ArrayList<Component> components, 
//	    Phase phase, 
//	    InteractionParameter k, 
//	    ActivityModel activityModel
//	    ) {
//	super(equationOfState, alpha, components, phase, k);
//	
//	this.activityModel = activityModel;
//	L =equationOfState.calculateL(1, 1);
//    }

    
    

    @Override
    public double calculate_a_cubicParameter() {
            
            double b = calculate_b_cubicParameter( );
            double excessGibbs = activityModel.excessGibbsEnergy( molarFractions, (ActivityModelBinaryParameter)binaryParameters, temperature);
            
            double firstTerm = 0;
           
            for(PureSubstance ci : molarFractions.keySet()){
                 double xi = molarFractions.get(ci);
                 double ai = ci.calculate_a_cubicParameter();//singleAs.get(ci);
                 double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
                
                firstTerm += xi * (ai) / bi ;
            }
       return b* (firstTerm  - excessGibbs/(getL()));
    }

    
    @Override
    public double calculate_b_cubicParameter() {
         double b = 0;
      for(PureSubstance iComponent:molarFractions.keySet()){
            double xi = molarFractions.get(iComponent);
            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }

    
    
    @Override
    public double oneOver_N_Parcial_a(
            
	PureSubstance ci) {
         
        double b = calculate_b_cubicParameter();
        double a =calculate_a_cubicParameter();
        
        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)binaryParameters;
	double ai = ci.calculate_a_cubicParameter();
	double bi = ci.calculate_b_cubicParameter();
	double alphai = ai/(bi*Constants.R * temperature);
	
        double gammai = activityModel.activityCoefficient( ci, molarFractions, param, temperature);

        return b * Constants.R * temperature*( alphai -  Math.log(gammai)/L) + a * bi / b;
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
    public double temperatureParcial_a() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
 
    
    
}
