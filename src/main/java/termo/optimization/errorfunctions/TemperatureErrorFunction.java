
package termo.optimization.errorfunctions;

import java.util.ArrayList;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.data.ExperimentalDataBinary;
import termo.matter.HeterogeneousMixture;

/**
 *
 * @author Hugo
 */
public class TemperatureErrorFunction implements ErrorFunction{
    private Component referenceComponent;
    private Component nonReferenceComponent;
    private HeterogeneousMixture mixture;
    private ArrayList<ExperimentalDataBinary> experimental;
    
    public TemperatureErrorFunction(HeterogeneousMixture mixture){
        this.mixture = mixture;
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
    public void setExperimental(ArrayList<ExperimentalDataBinary> experimental) {
        this.experimental = experimental;
        mixture.setPressure(experimental.get(0).getPressure());
         referenceComponent = experimental.get(0).getReferenceComponent();
        nonReferenceComponent = experimental.get(0).getNonReferenceComponent();
        
    }
    
}
