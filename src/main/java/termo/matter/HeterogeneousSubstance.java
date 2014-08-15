package termo.matter;

import java.util.ArrayList;
import java.util.List;

import termo.component.Compound;
import termo.data.ExperimentalData;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaParameter;
import termo.equilibrium.EquilibriaFunction;
import termo.equilibrium.EquilibriaSolution;
import termo.optimization.errorfunctions.VaporPressureErrorFunction;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousSubstance extends Heterogeneous{

    private Alpha alpha ;
    private Compound component;
    public HeterogeneousSubstance(){
        
       
         liquid = new Substance();
        liquid.setPhase(Phase.LIQUID);
        vapor = new Substance();
        vapor.setPhase(Phase.VAPOR);
        
        
        mpcs.addPropertyChangeListener(liquid);
        mpcs.addPropertyChangeListener(vapor);
        
         errorFunction = new VaporPressureErrorFunction(this);
        addPropertyChangeListener(errorFunction);
    }
    
    public HeterogeneousSubstance(
	    Cubic eos,
	    Alpha alpha,
	    Compound component){
        this();
	this.component = component;
        

        Substance liquidImplementation  =(Substance)liquid;
        liquidImplementation.setCubicEquationOfState(eos);
        liquidImplementation.setAlpha(alpha);
        liquidImplementation.setComponent(component);
        
	Substance vaporImplementation  =(Substance)vapor;
        vaporImplementation.setCubicEquationOfState(eos);
        vaporImplementation.setAlpha(alpha);
        vaporImplementation.setComponent(component);
        
        setAlpha(alpha);    
        
    }
    
    public List<AlphaParameter> alphaParameters(){
    	List<AlphaParameter> result = new ArrayList();
    	
    	int numberOfParameters = alpha.numberOfParameters();
    	for(int i =0; i < numberOfParameters ; i++){
    		double value = alpha.getParameter(component, i);
    		String name = alpha.getParameterName(i);
    		
    		AlphaParameter param = new AlphaParameter(value,name);
    		
    		result.add(param);
    	}
    	
    	return result;
    }
    
    public int saturationPressure(double pressureEstimate){
    	return bubblePressure(pressureEstimate);
    }
    public int saturationPressure(){
    	return bubblePressure();
    }
    
    public int saturationTemperature(){
    	return bubbleTemperature();
    }
    public int saturationTemperature(double temperatureEstimate){
    	return bubbleTemperature(temperatureEstimate);
    }
    

    //from super class
	    public final int bubblePressure(double pressureEstimate){
	        setPressure(pressureEstimate);
	        return bubblePressureImpl();
	    }
	    public final int bubblePressure(){
	        bubblePressureEstimate();
	        return bubblePressureImpl();
	    }
	
	    public final int dewPressure(double pressureEstimate){
			setPressure(pressureEstimate);
			return dewPressure();
	    }
	    public final int dewPressure(){
	        dewPressureEstimate();
	        return dewPressureImpl();
	    }
    
    //---
    
    public int bubbleTemperatureEstimate(){
    	return temperatureEstimate();
    }
    public int bubbleTemperature(double temperatureEstimate){
    	setTemperature(temperatureEstimate);
    	return bubbleTemperatureImpl();
    }
    public int bubbleTemperature() {
    	bubbleTemperatureEstimate();
		return bubbleTemperatureImpl();
    }
    public void bubblePressureEstimate(){
    	setPressure(liquid.calculatetAcentricFactorBasedVaporPressure());
    }
    public int bubblePressureImpl() {
		EquilibriaFunction function = new BubblePressureFunctions();
		return minimizePressure(function);
    }
    
    public int bubbleTemperatureImpl(){
    	EquilibriaFunction function = new BubbleTemperatureErrorFunction();
    	return minimizeTemperature(function);
    }
    

    public int dewTemperatureEstimate() {
    	return temperatureEstimate();
    }
    public int dewTemperature() {
		EquilibriaFunction function = new DewTemperatureFunctions();
		return minimizeTemperature(function);
    }
    public void dewPressureEstimate() {
    	setPressure(vapor.calculatetAcentricFactorBasedVaporPressure());
    }
    public int dewPressureImpl() {
		EquilibriaFunction function = new DewPressureFunctions();
		return minimizePressure(function);
    }
    
    
    
    
    
    public int temperatureEstimate(){
	
	//setTemperature(300);
	double temp = 300;
	double error = 100;
	double deltaT =1;
	double tol = 1e-3; 
	int iterations =0;
	while (Math.abs(error) >tol && iterations < 1000 ){
	    iterations++;
	    double T_  = temp + deltaT;
	    
	    setTemperature(temp);
	    double vaporPressure = vapor.calculatetAcentricFactorBasedVaporPressure();
	    setTemperature(T_);
	    double vaporPressure_ = vapor.calculatetAcentricFactorBasedVaporPressure();
	    error = Math.log(vaporPressure / pressure);
	    double error_ = Math.log(vaporPressure_ / pressure);
	    temp = (temp * T_ *(error_ - error)) / (T_ * error_ - temp * error);
	} 
	//temperature = temp;
	setTemperature(temp);
	return  iterations;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
      @Override
    public Substance getLiquid() {
    	  return (Substance)liquid;
    }

    @Override
    public Substance getVapor() {
    	return (Substance) vapor;
    }
    
   
   

    private int minimizeTemperature(EquilibriaFunction function){
		EquilibriaSolution result = new EquilibriaSolution();
		temperatureEstimate();
		result.setEstimateTemperature(temperature);
		
		double temp = temperature;
		double tolerance = 1e-4;
	        double e = 100;
	        double deltaT = 1;
	        int count = 0;
		
	        while(Math.abs(e) > tolerance && count < 1000){
		    count++;
	            e = function.errorFunction(equilibriaRelation(temp, pressure));
	            double temperature_ = temp + deltaT;
	            double e_ = function.errorFunction(equilibriaRelation(temperature_, pressure));
	            temp = function.newVariableFunction(temp, temperature_, e, e_);
	        }
		
		setTemperature(temp);
		result.setTemperature(temperature);
		result.setPressure(pressure);
		result.setIterations(count);
	        //return new EquilibriaSolution(temperature, pressure, count);
		return count;
    }
    
     
    
    private int minimizePressure(EquilibriaFunction function){
	
	double tolerance = 1e-5; 
	double deltaP = 0.0001;
	double e = 100;
 
	
	double p=pressure;
	int count = 0;
	while(Math.abs(e) > tolerance && count < 1000 ){         
	    count++;
	    e =  function.errorFunction(equilibriaRelation(temperature, p));
	    double pressure_ = p * (1 + deltaP); 
	    double e_ = function.errorFunction(equilibriaRelation(temperature, pressure_));
            if(e == 0){
                break;
            }
	    p =  function.newVariableFunction(p, pressure_, e, e_);
	}  
	
	setPressure(p);
	return count;

             
}

    private VaporPressureErrorFunction errorFunction  ;
    public void optimizeTo(ArrayList<ExperimentalData> expData) {
        errorFunction.setExperimental(expData);
        errorFunction.minimize();
    }


    /**
     * @return the errorFunction
     */
    public VaporPressureErrorFunction getErrorFunction() {
        return errorFunction;
    }

    /**
     * @param errorFunction the errorFunction to set
     */
    public void setErrorFunction(VaporPressureErrorFunction errorFunction) {
        this.errorFunction = errorFunction;
    }

    /**
     * @return the alpha
     */
    public Alpha getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(Alpha alpha) {
        Alpha oldAlpha = this.alpha;
        this.alpha = alpha;
         mpcs.firePropertyChange("alpha", oldAlpha, alpha);
    }

    /**
     * @return the component
     */
    public Compound getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Compound component) {
        Compound oldComponent = this.component;
        this.component = component;
        mpcs.firePropertyChange("component",oldComponent,component);
    }

   
class BubbleTemperatureErrorFunction implements EquilibriaFunction{
    
    @Override
    public double errorFunction(double equilibriaRelation){
	//double k = equilibriaRelation(temperature, pressure);
	return Math.log(equilibriaRelation);
    }
    @Override
    public double newVariableFunction(double temperature, double temperature_, double e, double e_){
	return temperature * temperature_ * (e_ - e) / (temperature_ * e_ - temperature * e);
    }
    
}
    
    
    
    
    public double equilibriaRelation(double temperature, double pressure){
	  setTemperature(temperature);
	  setPressure(pressure);
	return getLiquid().calculateFugacity()/getVapor().calculateFugacity();
    }
  
    
    
    

   
}















class BubblePressureFunctions implements EquilibriaFunction{
    
    @Override
    public double errorFunction(double equilibriaRelation){
	return equilibriaRelation-1;
    }
    @Override
    public double newVariableFunction(double pressure, double pressure_,double e, double e_){
	return ((pressure * pressure_ )* (e_ - e)) / ((pressure_ * e_) - (pressure * e));
    }
    
}

class DewPressureFunctions implements EquilibriaFunction{
    @Override
    public double errorFunction(double equilibriaRelation){
	return (1/equilibriaRelation) -1;
    }
    @Override
    public double newVariableFunction(double pressure, double pressure_,double e, double e_){
	return  pressure - e * (pressure_ - pressure)/ (e_ - e);
    }
}

class DewTemperatureFunctions implements EquilibriaFunction{
    @Override
    public double errorFunction(double equilibriaRelation){
	return  Math.log(1/equilibriaRelation);
    }
    @Override
    public double newVariableFunction(double temperature, double temperature_, double e, double e_){
	return temperature * temperature_ * (e_ - e) / (temperature_ * e_ - temperature * e);
    }
}