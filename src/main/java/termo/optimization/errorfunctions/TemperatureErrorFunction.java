
package termo.optimization.errorfunctions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.data.ExperimentalDataBinary;
import termo.matter.HeterogeneousMixture;
import termo.optimization.NewtonMethodSolver;

/**
 *
 * @author Hugo
 */
public class TemperatureErrorFunction extends ErrorFunction implements PropertyChangeListener{
    private Component referenceComponent;
    private Component nonReferenceComponent;
    private HeterogeneousMixture mixture;
    private ArrayList<ExperimentalDataBinary> experimental;
    
    PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
    
    NewtonMethodSolver optimizer ;
    
    public TemperatureErrorFunction(HeterogeneousMixture mixture){
        this.mixture = mixture;
        optimizer = new NewtonMethodSolver(this);
        mpcs.addPropertyChangeListener(optimizer);
    }
    
    @Override
    public double getParameter(int index) {
        InteractionParameter params = mixture.getInteractionParameters();
        return mixture.getMixingRule().getParameter( referenceComponent, nonReferenceComponent, params, index);
    }

    @Override
    public int numberOfParameters() {
        return mixture.getMixingRule().numberOfParameters();
    }

    @Override
    public void setParameter(double value, int index) {
        InteractionParameter params = mixture.getInteractionParameters();
        mixture.getMixingRule().setParameter(value, referenceComponent, nonReferenceComponent, params, index);
        
    }

    @Override
    public double error() {
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

    /**
     * @return the experimental
     */
    public ArrayList<ExperimentalDataBinary> getExperimental() {
        return experimental;
    }

    /**
     * @param experimental the experimental to set
     */
    @Override
    public void setExperimental(ArrayList<? extends ExperimentalData> experimental) {
        this.experimental = (ArrayList< ExperimentalDataBinary>)experimental;
        mixture.setPressure(experimental.get(0).getPressure());
         referenceComponent = this.experimental.get(0).getReferenceComponent();
        nonReferenceComponent = this.experimental.get(0).getNonReferenceComponent();
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        
        if(propertyName == "mixingRule"){
            mpcs.firePropertyChange(evt);
        }
    }
    
    public void minimize(){
        optimizer.solve();
    }

    /**
     * @return the referenceComponent
     */
    public Component getReferenceComponent() {
        return referenceComponent;
    }

    /**
     * @param referenceComponent the referenceComponent to set
     */
    public void setReferenceComponent(Component referenceComponent) {
        this.referenceComponent = referenceComponent;
    }

    /**
     * @return the nonReferenceComponent
     */
    public Component getNonReferenceComponent() {
        return nonReferenceComponent;
    }

    /**
     * @param nonReferenceComponent the nonReferenceComponent to set
     */
    public void setNonReferenceComponent(Component nonReferenceComponent) {
        this.nonReferenceComponent = nonReferenceComponent;
    }
}
