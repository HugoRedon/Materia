package termo.optimization;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.component.VanDerWaalsParameters;
import termo.data.ExperimentalDataBinary;
import termo.eos.Cubic;
import termo.equilibrium.BubblePoint;
import termo.equilibrium.EquilibriaPhaseSolution;

/**
 *
 * @author Hugo Redon
 */
public class VDWBinaryParameterOptimizer {

    BubblePoint bubbleCalculator;
    
    public VDWBinaryParameterOptimizer() {
        bubbleCalculator = new BubblePoint();
    }

    public double optimizeParameter(Cubic eos, ArrayList<ExperimentalDataBinary> data) {
      //  if(data.getDataType().equals(ExperimentalDataType.PRESSURE_CONSTANT)){
            return bubbleTemperatureOptimization(eos,data, 1e-4,1e-5);
   //     }else{
 //           return bubblePressureOptimization(eos,data);
  //      }
    }

    private double bubbleTemperatureOptimization(Cubic eos, ArrayList<ExperimentalDataBinary> data, double phaseEquilibriaTolerance,double optimizationTolerance) {
        
        ExperimentalDataBinary data0 =data.get(0);
        Component referenceComponent = data0.getReferenceComponent();
        Component nonReferenceComponent = data0.getNonReferenceComponent();
        
        VanDerWaalsParameters k = new VanDerWaalsParameters();
        
        double k12 = 0;
        k.setValue(referenceComponent, nonReferenceComponent, k12, true);
        
        double objectiveFunction = firstDerivative(data, k, eos, phaseEquilibriaTolerance);
        double iterations =0;
        
        while(objectiveFunction > optimizationTolerance | iterations == 1000){
            iterations++;
            double d1 = firstDerivative(data, k, eos, phaseEquilibriaTolerance);
            double d2 = secondDerivative(data, k, eos, phaseEquilibriaTolerance);
            
            k12 = k12 - d1 / d2;
            
            k.setValue(referenceComponent, nonReferenceComponent, k12, true);
            objectiveFunction = firstDerivative(data, k, eos, phaseEquilibriaTolerance);
            
        }
     
        return k12;
        
    }
    
    private double secondDerivative(ArrayList<ExperimentalDataBinary> data,VanDerWaalsParameters kinteraction,Cubic eos, double tolerance){
             
        ExperimentalDataBinary data0 =data.get(0);   
         Component c1 = data0.getReferenceComponent();
        Component c2 = data0.getNonReferenceComponent();
        
        double k1 = kinteraction.getValue(c1, c2);
        double deltaK = 0.001;
        double k2 = k1 + deltaK;
        
        VanDerWaalsParameters kinteraction_ = new VanDerWaalsParameters();
        kinteraction_.setValue(c1, c2, k2, true);
        
        double m1 = firstDerivative(data, kinteraction, eos, tolerance);
        double m2 = firstDerivative(data, kinteraction_, eos, tolerance);
        
        return (m2 - m1) / deltaK;
    }
    
    private double firstDerivative(ArrayList<ExperimentalDataBinary>  data,VanDerWaalsParameters kinteraction,Cubic eos, double tolerance){
       ExperimentalDataBinary data0 =data.get(0);   
        Component c1 = data0.getReferenceComponent();
        Component c2 = data0.getNonReferenceComponent();
        
        double k1 = kinteraction.getValue(c1, c2);
        double deltaK = 0.001;
        double k2 = k1 + deltaK;
        
        VanDerWaalsParameters kinteraction_ = new VanDerWaalsParameters();
        kinteraction_.setValue(c1, c2, k2, true);
        
        double error1 = calculateTempError(data, kinteraction, eos, tolerance);
        double error2 = calculateTempError(data, kinteraction_, eos, tolerance);
        
        return (error2 - error1) / deltaK;
        
    }
    
    private double calculateTempError(ArrayList<ExperimentalDataBinary> dataList,BinaryInteractionParameters kinteraction, Cubic eos, double tolerance){
            ExperimentalDataBinary data0 =dataList.get(0);  
            ArrayList<Component> components2 = data0.getComponents();     
            
            double error = 0;
        for(ExperimentalDataBinary data : dataList){
            double pressure =data.getPressure();
            HashMap<Component,Double> expLiquidFractions = data.getLiquidFractions();
      
            EquilibriaPhaseSolution solution = bubbleCalculator.getTemperature(pressure, expLiquidFractions, components2, eos, kinteraction, tolerance);
            
            double tempCalc = solution.getTemperature();
            double tempExp = data.getTemperature();
            
            error += Math.pow((tempCalc- tempExp)/tempExp,2);
            
        }
        
        return error;
    }

    private double bubblePressureOptimization(Cubic eos, ArrayList<ExperimentalDataBinary> data) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
