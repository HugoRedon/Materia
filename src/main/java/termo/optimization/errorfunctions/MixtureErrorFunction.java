
package termo.optimization.errorfunctions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.data.Experimental;
import termo.data.ExperimentalDataBinary;
import termo.data.ExperimentalDataBinaryList;
import termo.data.ExperimentalDataBinaryType;
import termo.matter.HeterogeneousMixture;
import termo.optimization.NewtonMethodSolver;

/**
 *
 * @author Hugo
 */
public class MixtureErrorFunction extends ErrorFunction implements PropertyChangeListener{
    private Compound referenceComponent;
    private Compound nonReferenceComponent;
    private final HeterogeneousMixture mixture;
    private ArrayList<ExperimentalDataBinary> experimental;
    
    PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
    
    private NewtonMethodSolver optimizer ;
    
    
    ExperimentalDataBinaryType dataType = ExperimentalDataBinaryType.isobaric;//default
    
    public MixtureErrorFunction(HeterogeneousMixture mixture){
        this.mixture = mixture;
        optimizer = new NewtonMethodSolver(this);
        mpcs.addPropertyChangeListener(optimizer);
    }
    
    @Override
    public double getParameter(int index) {
        InteractionParameter params = getMixture().getInteractionParameters();
        return getMixture().getMixingRule().getParameter( referenceComponent, nonReferenceComponent, params, index);
    }

    @Override
    public int numberOfParameters() {
        if(getMixture().getMixingRule() !=null){
            return getMixture().getMixingRule().numberOfParameters();    
        }
        return 0;
    }

    @Override
    public void setParameter(double value, int index) {
        InteractionParameter params = getMixture().getInteractionParameters();
        mixture.getMixingRule().setParameter(value, referenceComponent, nonReferenceComponent, params, index);
        
    }

    @Override
    public double error(){
    	if(dataType.equals(ExperimentalDataBinaryType.isobaric)){
    		return isobaricError();
    	}else {//(dataType.equals(ExperimentalDataBinaryType.isothermic)){
    		return isothermicError();
    	}
    }
    
    
    public double isobaricError() {
        errorForEachExperimentalData.clear();
        double error = 0;
        for(ExperimentalDataBinary data : experimental){   
            
            mixture.setZFraction(referenceComponent, data.getLiquidFraction());
            mixture.setZFraction(nonReferenceComponent, 1-data.getLiquidFraction());

            mixture.bubbleTemperature();
            double tempCalc = mixture.getTemperature();
            double tempExp = data.getTemperature();
            
            double relativeError = (tempCalc- tempExp)/tempExp;
            error += Math.pow(relativeError,2);
            
            TemperatureMixtureErrorData errorData= 
                    new TemperatureMixtureErrorData(data.getLiquidFraction(),
                            data.getVaporFraction(),
                            mixture.getVapor().getFraction(mixture.getVapor().getPureSubstance(referenceComponent)) ,
                            data.getTemperature(), 
                            tempCalc,
                    relativeError);
            errorForEachExperimentalData.add(errorData);
        }
        return error;
    }
    
    public double isothermicError(){
    	errorForEachExperimentalData.clear();
        double error = 0;
        for(ExperimentalDataBinary data : experimental){   
            
            mixture.setZFraction(referenceComponent, data.getLiquidFraction());
            mixture.setZFraction(nonReferenceComponent, 1-data.getLiquidFraction());

            mixture.bubblePressure();
            double pressureCalc = mixture.getPressure();
            double pressureExp = data.getPressure();
            
            double relativeError = (pressureCalc- pressureExp)/pressureExp;
            error += Math.pow(relativeError,2);
            
            TemperatureMixtureErrorData errorData= new TemperatureMixtureErrorData();
            
            errorData.setCalculatedPressure(pressureCalc);
            errorData.setExperimentalPressure(pressureExp);
            
            errorData.setLiquidFraction(data.getLiquidFraction());
            errorData.setExperimentalVaporFraction(data.getVaporFraction());
            errorData.setCalculatedVaporFraction(mixture.getVapor().getReadOnlyFractions().get(referenceComponent));
            errorData.setRelativeError(relativeError);
            
            
            errorForEachExperimentalData.add(errorData);
        }
        return error;
    }
  
    
    
    
    
    
    
    
    
    
    
    ArrayList<TemperatureMixtureErrorData> errorForEachExperimentalData = new ArrayList();
    public Iterable<TemperatureMixtureErrorData> getErrorForEachExperimentalData() {
        error();
        return errorForEachExperimentalData;
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
    public void setExperimental(List<? extends Experimental> experimental) {
        this.experimental = (ArrayList< ExperimentalDataBinary>)experimental;
        if(this.dataType.equals(ExperimentalDataBinaryType.isobaric)){
    		getMixture().setPressure(experimental.get(0).getPressure());
    	}else{
    		getMixture().setTemperature(experimental.get(0).getTemperature());
    	}
        
//         referenceComponent = this.experimental.get(0).getReferenceComponent();
//        nonReferenceComponent = this.experimental.get(0).getNonReferenceComponent();
//        
    }
    
    
    public void setExperimental(ExperimentalDataBinaryList binaryList){
    	setExperimental(binaryList.getList(), binaryList.getType());
    	setReferenceComponent(binaryList.getReferenceComponent());
    	setNonReferenceComponent(binaryList.getNonReferenceComponent());
    }
    
    public void setExperimental(List<? extends Experimental> experimental,ExperimentalDataBinaryType datatype){
    	this.experimental= (ArrayList<ExperimentalDataBinary>)experimental;
    	this.dataType = datatype;
    	if(this.dataType.equals(ExperimentalDataBinaryType.isobaric)){
    		getMixture().setPressure(experimental.get(0).getPressure());
    	}else{
    		getMixture().setTemperature(experimental.get(0).getTemperature());
    	}
    	
    }
    

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        
        if(propertyName == "mixingRule"){
            mpcs.firePropertyChange(evt);
        }
    }
    
    public void minimize(){
        getOptimizer().solve();
    }

    /**
     * @return the referenceComponent
     */
    public Compound getReferenceComponent() {
        return referenceComponent;
    }

    /**
     * @param referenceComponent the referenceComponent to set
     */
    public void setReferenceComponent(Compound referenceComponent) {
        this.referenceComponent = referenceComponent;
    }

    /**
     * @return the nonReferenceComponent
     */
    public Compound getNonReferenceComponent() {
        return nonReferenceComponent;
    }

    /**
     * @param nonReferenceComponent the nonReferenceComponent to set
     */
    public void setNonReferenceComponent(Compound nonReferenceComponent) {
        this.nonReferenceComponent = nonReferenceComponent;
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

    /**
     * @return the mixture
     */
    public HeterogeneousMixture getMixture() {
        return mixture;
    }

  
}
