
package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class WongSandlerMixingRule extends ExcessGibbsMixingRule {
       
    
    private double L;
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
	
	double denom = 1 - ge/(L*mixture.getTemperature() * Constants.R) - denomSum;
	
	return numer/denom;
	
    }

    
    
   

    



        @Override
    public double temperatureParcial_a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getParameter(Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParameter(double value, Component referenceComponent, Component nonReferenceComponent, InteractionParameter params, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numberOfParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
