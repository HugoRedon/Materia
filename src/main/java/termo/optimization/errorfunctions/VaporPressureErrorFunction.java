/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.optimization.errorfunctions;

import java.util.ArrayList;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.alpha.Alpha;
import termo.matter.Heterogeneous;
import termo.matter.HeterogeneousSubstance;
import termo.optimization.ErrorData;

/**
 *
 * @author Hugo
 */
public class VaporPressureErrorFunction implements ErrorFunction {
    private HeterogeneousSubstance substance;
    private ArrayList<ExperimentalData> experimental = new ArrayList();
     
   
     private Double totalError;
   private ArrayList<ErrorData> errorForEachExperimentalData = new ArrayList();
   
     
    public VaporPressureErrorFunction(HeterogeneousSubstance substance){
        this.substance = substance;
    }
    public VaporPressureErrorFunction(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    
    @Override
    public int numberOfParameters(){
        Alpha alpha = substance.getVapor().getAlpha();
        return alpha.numberOfParameters();
    }
    
       //to class function
    @Override
    public double getParameter(int index){
        
        Component component = substance.getVapor().getComponent();
            Alpha alpha = substance.getVapor().getAlpha();
            return alpha.getParameter( component, index);
    }
      //to class function
    @Override
   public double error(){
        errorForEachExperimentalData.clear();
        double error =0;
        for (ExperimentalData pair: experimental){
            double temperature = pair.getTemperature();
            substance.setTemperature(temperature);
            substance.dewPressure();
            double expP = pair.getPressure();
            double calcP = substance.getPressure();
            double relativeError = (calcP - expP)/expP;
            double squareError = Math.pow(relativeError,2);
            error += squareError;
            errorForEachExperimentalData.add(new ErrorData(expP, calcP, relativeError,temperature));
        }   
        totalError = error;
        return error;   
   }
   
   
   
    //to class function
    @Override
   public void setParameter(double value,int index){
        Component component = substance.getVapor().getComponent();
        Alpha alpha = substance.getVapor().getAlpha();
        alpha.setParameter(value, component, index);
   }
    
   
       /**
     * @return the totalError
     */
    public double getTotalError() {
        if(totalError == null){
            error();
        }
        return totalError;
    }

   

    /**
     * @return the errorForEachExperimentalData
     */
    public ArrayList<ErrorData> getErrorForEachExperimentalData() {
       // if(experimental.size() != errorForEachExperimentalData.size()){
            error();//para calcular por primera vez
        //}
        return errorForEachExperimentalData;
        
    }

    /**
     * @return the experimental
     */
    public ArrayList<ExperimentalData> getExperimental() {
        return experimental;
    }

    /**
     * @param experimental the experimental to set
     */
    public void setExperimental(ArrayList<ExperimentalData> experimental) {
        this.experimental = experimental;
    }
   
}
