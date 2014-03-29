package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.binaryParameter.WilsonParameters;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Wilson {
    public double excessGibbsEnergy(
            HashMap<Substance,Double> fractions,
            InteractionParameter k,
            double temperature){
        
         WilsonParameters params = (WilsonParameters)k;
        
        double excessGibbs = 0;
        for (Substance ci : fractions.keySet()){
            double xi = fractions.get(ci);
            excessGibbs -= xi * Math.log( summa(ci.getComponent(),  fractions,params,temperature));
        }
        return excessGibbs;
    }
    public double activityCoefficient(ArrayList<Component> components,
            Component ci,
            HashMap<Substance,Double> fractions,
            InteractionParameter k,
            double temperature){
        
        WilsonParameters params = (WilsonParameters)k;
        double thirdTerm = 0;
        for(Component cj: components){
            double xj = fractions.get(cj);
            thirdTerm += (summa(cj,  fractions, params,temperature));
        }
    return  - Math.log(summa(ci,  fractions, params,temperature)) + 1 - thirdTerm;
    }
    private double summa(
            Component ci, 
            HashMap<Substance,Double> fractions,
            WilsonParameters params,
            double temperature){
        
        double summa = 0;
         for(Substance cj: fractions.keySet()){
                double xj = fractions.get(cj);
                double gammaij = params.Aij(ci, cj.getComponent(), temperature);  
                summa += xj * gammaij;
            }
        return summa;
    }
}
