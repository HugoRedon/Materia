
package termo.optimization;

import java.util.ArrayList;
import termo.component.Component;
import termo.data.ExperimentalDataBinary;
import termo.eos.Cubic;
import termo.matter.HeterogeneousMixture;

/**
 *
 * @author Hugo
 */
public class InteractionParameterOptimizer {
    private final HeterogeneousMixture mixture;
    private ArrayList<ExperimentalDataBinary> experimental;
    private Component referenceComponent;
    private Component nonReferenceComponent;
    
    double optimizationTolerance = 1e-5;
    double deltaK = 0.00001;
    
    public InteractionParameterOptimizer(HeterogeneousMixture mixture) {
        this.mixture = mixture;
    }
    
    void optimizeTo(ArrayList<ExperimentalDataBinary> experimental) {
         this.experimental = experimental;
        
         referenceComponent = experimental.get(0).getReferenceComponent();
         nonReferenceComponent = experimental.get(0).getNonReferenceComponent();
         mixture.setPressure(experimental.get(0).getPressure());
         
         //  if(data.getDataType().equals(ExperimentalDataType.PRESSURE_CONSTANT)){
             bubbleTemperatureOptimization( );
   //     }else{
 //           return bubblePressureOptimization(eos,data);
  //      }
         
         
       // this.mixture.getInteractionParameters().setValue(comp1,comp2, -0.0765771);
    }
     

    private void bubbleTemperatureOptimization() {
        
        double objectiveFunction = 1000;
        double iterations =0;
        
        while(objectiveFunction > optimizationTolerance && iterations < 1000){
            iterations++;
            double d1 = firstDerivative();
            double d2 = secondDerivative();
            
            double oldValue = mixture.getInteractionParameters().getValue(referenceComponent, nonReferenceComponent);
            
            double newValue = oldValue  - d1 / d2;
            
            mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, newValue);
            objectiveFunction = d1;
            
        }
     
        
        
    }
    
    private double secondDerivative(){
            
        double k1 = mixture.getInteractionParameters().getValue(referenceComponent, nonReferenceComponent);
        
        double m1 = firstDerivative();
        
        double k2 = k1 + deltaK;
        
        
        mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, k2);
        
        
        double m2 = firstDerivative();
        
        
        mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, k1);
        
        return (m2 - m1) / deltaK;
    }
    
    private double firstDerivative(){
  
        double k1 = mixture.getInteractionParameters().getValue(referenceComponent, nonReferenceComponent);
        double error1 = calculateTempError();
        
        double k2 = k1 + deltaK;
        
        mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, k2);
        double error2 = calculateTempError();
        
        mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, k1);//regresando original 
        return (error2 - error1) / deltaK;
        
    }
    
    private double calculateTempError(){
 
            double error = 0;
        for(ExperimentalDataBinary data : experimental){
            
            mixture.setZFraction(referenceComponent, data.getLiquidFraction());
            mixture.setZFraction(nonReferenceComponent, 1-data.getLiquidFraction());
            
            mixture.bubbleTemperature();
            double tempCalc = mixture.getTemperature();
            double tempExp = data.getTemperature();
            
            error += Math.pow((tempCalc- tempExp)/tempExp,2);
            
        }
        
        return error;
    }

    private double bubblePressureOptimization(Cubic eos, ArrayList<ExperimentalDataBinary> data) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
