package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;

import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;
import termo.optimization.ContainsParameters;
import termo.optimization.errorfunctions.ErrorFunction;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class ActivityModel implements ContainsParameters{
    private String name;
   public abstract double excessGibbsEnergy(
            Mixture mixture);
   public abstract double activityCoefficient(
            Substance ci,
           Mixture mixture);
   public abstract double parcialExcessGibbsRespectTemperature(
		   Mixture mixture);
   
    public double tau(Compound ci,Compound cj, ActivityModelBinaryParameter k ,double T){
    double aij = k.getA().getValue(ci, cj);
    double bij = k.getB().getValue(ci, cj);

    return (aij + bij * T)/(Constants.R * T);
    }
    
    public double getParameter(Compound referenceComponent,
            Compound nonReferenceComponent,
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
    @Override
    public String getParameterName(int index){
    	 switch(index){
         case 0: return "Aij";
         case 1: return "Aji";
         case 2: return "Bij";
         case 3: return "Bji";
         default:return "";
     }
    }
    
    public  void setParameter(double value, 
            Compound referenceComponent,
            Compound nonReferenceComponent,
            ActivityModelBinaryParameter params,
            int index){
        
        switch(index){
            case 0: params.getA().setValue(referenceComponent, nonReferenceComponent,value);
                break;
            case 1: params.getA().setValue(nonReferenceComponent, referenceComponent,value);
                break;
            case 2: params.getB().setValue(referenceComponent, nonReferenceComponent,value);
                break;
            case 3: params.getB().setValue(nonReferenceComponent, referenceComponent,value);
                break;
            
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
