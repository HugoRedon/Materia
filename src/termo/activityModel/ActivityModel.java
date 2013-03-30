package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public interface ActivityModel {
   public double excessGibbsEnergy(ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            ActivityModelBinaryParameter k,
            double temperature);
   public double activityCoefficient(ArrayList<Component> components,
            Component ci,
            HashMap<Component,Double> fractions,
            ActivityModelBinaryParameter k,
            double temperature);
   public double parcialExcessGibbsRespectTemperature(ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            ActivityModelBinaryParameter k,
            double temperature);
   
}
