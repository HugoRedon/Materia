package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class ActivityModel {
   public abstract double excessGibbsEnergy(
            Mixture mixture);
   public abstract double activityCoefficient(
            Substance ci,
           Mixture mixture);
   public abstract double parcialExcessGibbsRespectTemperature(ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            ActivityModelBinaryParameter k,
            double temperature);
   
    public double tau(Component ci,Component cj, ActivityModelBinaryParameter k ,double T){
    double aij = k.getA().getValue(ci, cj);
    double bij = k.getB().getValue(ci, cj);

    return (aij + bij * T)/(Constants.R * T);
    }
   
}
