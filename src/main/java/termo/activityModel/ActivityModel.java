package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.optimization.errorfunctions.ErrorFunction;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class ActivityModel {
    private String name;
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
    
    public double getParameter(Component referenceComponent,
            Component nonReferenceComponent,
            ActivityModelBinaryParameter params,
            int index){
        
        switch(index){
            case 0: return params.getA().getValue(referenceComponent, nonReferenceComponent);
            case 1: return params.getA().getValue(nonReferenceComponent, referenceComponent);
            case 2: return params.getB().getValue(referenceComponent, nonReferenceComponent);
            case 3: return params.getB().getValue(nonReferenceComponent, referenceComponent);
            default:return 0;
        }
                
    }
    public  void setParameter(double value, 
            Component referenceComponent,
            Component nonReferenceComponent,
            ActivityModelBinaryParameter params,
            int index){
        
        switch(index){
            case 0: params.getA().setValue(referenceComponent, nonReferenceComponent,value);
            case 1: params.getA().setValue(nonReferenceComponent, referenceComponent,value);
            case 2: params.getB().setValue(referenceComponent, nonReferenceComponent,value);
            case 3: params.getB().setValue(nonReferenceComponent, referenceComponent,value);
            
        }
        
    }
    
    public int numberOfParameters() {
        return 4;
    }
    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
   
}
