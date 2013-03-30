package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class ExcessGibbsMixingRule extends MixingRule{

    private ActivityModel activityModel;
    private double c1;
    private double c2;
    
    @Override
    public double a(double temperature,
            HashMap<Component, Double> singleAs, 
            HashMap<Component, Double> singleBs, 
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            BinaryInteractionParameter k) {
        
            ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)k;
            
            double b = b(singleBs, components, fractions);
            double excessGibbs = activityModel.excessGibbsEnergy(components, fractions, param, temperature);
            
            double firstTerm = 0;
            double secondTerm = 0;
            
            double xi=0;
            double ai=0;
            double bi = 0;
            
            for(Component ci : components){
                 xi = fractions.get(ci);
                 ai = singleAs.get(ci);
                 bi = singleBs.get(ci);
                
                firstTerm = xi * (ai) / bi ;
                secondTerm = Constants.R * temperature * c1 *xi * Math.log(b / bi);
                
            }
       return b* (firstTerm + secondTerm + c2 * excessGibbs);
    }

    @Override
    public double b(HashMap<Component, Double> singleBs,ArrayList<Component> components,HashMap<Component,Double> fractions) {
         double b = 0;
      for(Component iComponent:components){
            double xi = fractions.get(iComponent);
            double bi = singleBs.get(iComponent);
            b += xi * bi ;
      }
       return b;
    }

    @Override
    public double oneOverNParcial_aN2RespectN(
            double temperature,
            HashMap<Component, Double> singleAs,
            HashMap<Component, Double> singleBs, 
            ArrayList<Component> components,
            Component iComponent, 
            HashMap<Component, Double> fractions, 
            BinaryInteractionParameter k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double temperatureParcial_a(ArrayList<Component> components, HashMap<Component, Double> fractions, HashMap<Component, Double> single_as, HashMap<Component, Double> alphaDerivatives, BinaryInteractionParameter k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
