/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.activityModel.WilsonActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class HuronVidalMixingRule extends MixingRule{

    WilsonActivityModel activityModel;
    
    private double L;
    public HuronVidalMixingRule(WilsonActivityModel activityModel,Cubic eos){
	this.activityModel = activityModel;
	
	L = eos.calculateL(1, 1);
	
    }
    
    
    
    

    public double a(double temperature, HashMap<PureSubstance, Double> fractions, InteractionParameter k) {
        
//            ArrayList<PureSubstance> components = new ArrayList();
//	    HashMap<Component,Double> fra = new HashMap();
//	    for(PureSubstance pure: fractions.keySet()){
//		components.add(pure);
//		fra.put(pure.getComponent(), fractions.get(pure));
//	    }
            
            double b = b( fractions);
            double excessGibbs = activityModel.excessGibbsEnergy( fractions, (ActivityModelBinaryParameter)k, temperature);
            
            double firstTerm = 0;
           
            for(PureSubstance ci : fractions.keySet()){
                 double xi = fractions.get(ci);
                 double ai = ci.calculate_a_cubicParameter(temperature);//singleAs.get(ci);
                 double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
                
                firstTerm += xi * (ai) / bi ;
               // secondTerm = Constants.R * temperature * c1 *xi * Math.log(b / bi);
            }
       return b* (firstTerm  - excessGibbs/(getL()));
    }

    
    public double b(HashMap<PureSubstance,Double> fractions) {
         double b = 0;
      for(PureSubstance iComponent:fractions.keySet()){
            double xi = fractions.get(iComponent);
            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }

    
    public double oneOverNParcial_aN2RespectN(
            double temperature, 
            
            PureSubstance ci, 
            HashMap<PureSubstance, Double> fractions, 
            InteractionParameter k) {
         
        double b = b( fractions);
        double a =a(temperature,  fractions, k);
        
        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)k;
        //double alphai = ci.getAlpha().alpha(temperature, ci.getComponent());//singleAlphas.get( ci);
	
	double ai = ci.calculate_a_cubicParameter(temperature);
	double bi = ci.calculate_b_cubicParameter();
	double alphai = ai/(bi*Constants.R * temperature);
	
        double gammai = activityModel.activityCoefficient( ci, fractions, param, temperature);
//        double bi = ci.calculate_b_cubicParameter();//singleBs.get(ci);
//	double ai = ci.calculate_a_cubicParameter(temperature);
        return b * Constants.R * temperature*( alphai -  Math.log(gammai)/L) + a * bi / b;
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

   
 
    
    
}
