
package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class WongSandlerMixingRule extends MixingRule {
       ActivityModel activityModel;
    
    private double L;
    public WongSandlerMixingRule(ActivityModel activityModel,Cubic eos){
	this.activityModel = activityModel;
	
	L = eos.calculateL(1, 1);
	
    }
    
    
    
    

    
       @Override
    public double a(MixtureSubstance mixture) {
        
//            ArrayList<PureSubstance> components = new ArrayList();
//	    HashMap<Component,Double> fra = new HashMap();
//	    for(PureSubstance pure: fractions.keySet()){
//		components.add(pure);
//		fra.put(pure.getComponent(), fractions.get(pure));
//	    }
            
	
	
            double b = b( mixture);
            double excessGibbs = activityModel.excessGibbsEnergy( mixture);
            
            double firstTerm = 0;
           
            for(PureSubstance ci : mixture.getPureSubstances()){
                 double xi = ci.getMolarFraction();
                 double ai = ci.calculate_a_cubicParameter();//singleAs.get(ci);
                 double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
                
                firstTerm += xi * (ai) / bi ;
               // secondTerm = Constants.R * temperature * c1 *xi * Math.log(b / bi);
            }
       return b* (firstTerm  - excessGibbs/(getL()));
    }

    
    
       @Override
    public double b(MixtureSubstance mixture) {
//         double b = 0;
//      for(PureSubstance iComponent:fractions.keySet()){
//            double xi = fractions.get(iComponent);
//            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
//            b += xi * bi ;
//      }
//       return b;
	
	ActivityModelBinaryParameter params = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
	
	double b = 0;
	
	double numer = 0;
	double denomSum =0;
	
	for(PureSubstance ci: mixture.getPureSubstances()){
	    for(PureSubstance cj: mixture.getPureSubstances()){
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
    public double oneOverNParcial_aN2RespectN(
            
            
            PureSubstance ci, MixtureSubstance mixture) {
         
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

    
    
    public double temperatureParcial_a(double temperature, ArrayList<Component> components, HashMap<Component, Double> fractions, HashMap<Component, Double> single_as, HashMap<Component, Double> single_bs, HashMap<Component, Double> alphaDerivatives, BinaryInteractionParameter k) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

//    @Override
//    public double temperatureParcial_a(double temperature, HashMap<PureSubstance, Double> fractions, InteractionParameter k) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }



    @Override
    public double temperatureParcial_a(MixtureSubstance mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
