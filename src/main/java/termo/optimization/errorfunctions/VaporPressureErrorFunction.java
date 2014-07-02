/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.optimization.errorfunctions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import termo.component.Component;
import termo.data.Experimental;
import termo.data.ExperimentalData;
import termo.eos.alpha.Alpha;
import termo.matter.HeterogeneousSubstance;
import termo.optimization.ErrorData;
import termo.optimization.NewtonMethodSolver;

/**
 *
 * @author Hugo
 */
public class VaporPressureErrorFunction extends ErrorFunction implements PropertyChangeListener {
    private HeterogeneousSubstance substance;
    private List<ExperimentalData> experimental = new ArrayList();
     
   protected ArrayList<ErrorData> errorForEachExperimentalData = new ArrayList();
     private Double totalError;
     
     PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
    private NewtonMethodSolver optimizer ;
     
     public VaporPressureErrorFunction(HeterogeneousSubstance substance){
         this.substance= substance;
         optimizer = new NewtonMethodSolver(this);
         mpcs.addPropertyChangeListener(optimizer);
     }
     
     
    @Override 
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        
        if(propertyName == "alpha"){
            mpcs.firePropertyChange(evt);
        }
    }
    
   public void minimize(){
        getOptimizer().solve();
   }
      public ArrayList<ErrorData> getErrorForEachExperimentalData() {
       // if(experimental.size() != errorForEachExperimentalData.size()){
            error();//para calcular por primera vez
        //}
        return errorForEachExperimentalData;
        
    }

    public VaporPressureErrorFunction(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    
    @Override
    public int numberOfParameters(){
        Alpha alpha = substance.getVapor().getAlpha();
        if(alpha !=null){
            return alpha.numberOfParameters();
        }else{
            return 0;
        }
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
     * @return the experimental
     */
    public List<ExperimentalData> getExperimental() {
        return experimental;
    }

    /**
     * @param experimental the experimental to set
     */
    @Override
    public void setExperimental(List<? extends Experimental> experimental) {
        this.experimental = (List<ExperimentalData>) experimental;
    }

    /**
     * @return the optimizer
     */
    public NewtonMethodSolver getOptimizer() {
        return optimizer;
    }

    /**
     * @param optimizer the optimizer to set
     */
    public void setOptimizer(NewtonMethodSolver optimizer) {
        this.optimizer = optimizer;
    }

    
   
}
