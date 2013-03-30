package termo.eos.mixingRule;

import com.sun.org.apache.xerces.internal.impl.dtd.models.MixedContentModel;
import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MathiasKlotzPrausnitzMixingRule extends MixingRule {

    @Override
    public double a(
            double temperature,
            HashMap<Component, Double> singleAs,
            HashMap<Component, Double> singleBs,
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            BinaryInteractionParameter k) {
         double a = 0;
       
       
      for(Component iComponent:components){
          for(Component jComponent:components){
              double xi = fractions.get(iComponent);
              double xj = fractions.get(jComponent);
              
              double ai = singleAs.get(iComponent);
              double aj = singleAs.get(jComponent);
              
              double kij = k.getValue(iComponent, jComponent);
              double kji = k.getValue(jComponent, iComponent);
              
              a += xi * xj * Math.sqrt(ai * aj) * (1-kij) +  + xi * Math.pow(xj,3) * Math.sqrt(ai * aj) * (kij - kji);
          }
          
      }
       
       return a;
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
