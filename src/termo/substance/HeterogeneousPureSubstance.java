package termo.substance;

import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.equilibrium.EquilibriaFunction;
import termo.equilibrium.EquilibriaSolution;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousPureSubstance {

    private Cubic cubicEquationOfState;
    private Alpha alpha;

    private Component component;
    private PureSubstance liquid;
    private PureSubstance vapor;
    
    
    public HeterogeneousPureSubstance(
	    Cubic eos,
	    Alpha alpha,
	    Component component){
	this.cubicEquationOfState = eos;
	this.alpha = alpha;
	this.component = component;
	
	liquid = new PureSubstance(eos, alpha, component, Phase.LIQUID);
	vapor = new PureSubstance(eos, alpha, component, Phase.VAPOR);
    }
        
    public EquilibriaSolution bubbleTemperatureEstimate(double pressure){
	return temperatureEstimate(pressure);
    }
    
    public EquilibriaSolution bubbleTemperature(double pressure) {
	
	EquilibriaFunction function = new BubbleTemperatureFunctions();
	return minimizeTemperature(function, pressure);
    }
       
    public double bubblePressureEstimate(double temperature){
	return liquid.getAcentricFactorBasedVaporPressure(temperature);
    }
    
    public EquilibriaSolution bubblePressure(double temperature) {
	EquilibriaFunction function = new BubblePressureFunctions();
	return minimizePressure(function, temperature);
    }
    
    
    
    
    public EquilibriaSolution dewTemperatureEstimate(double pressure) {
	return temperatureEstimate(pressure);
    }

    public EquilibriaSolution dewTemperature(double pressure) {
	EquilibriaFunction function = new DewTemperatureFunctions();
	return minimizeTemperature(function, pressure);
    }
     
    public EquilibriaSolution dewPressureEstimate(double temperature) {
	return new EquilibriaSolution(temperature, vapor.getAcentricFactorBasedVaporPressure(temperature), 0);
    }
    
    public EquilibriaSolution dewPressure(double temperature) {
	
	EquilibriaFunction function = new DewPressureFunctions();
	return minimizePressure(function, temperature);
    }
    
    
    
    
    
    public EquilibriaSolution temperatureEstimate(double pressure){
	double temperature =  300;	
	double error = 100;
	double deltaT =1;
	double tol = 1e-3; 
	int iterations =0;
	while (Math.abs(error) >tol && iterations < 1000 ){
	    iterations++;
	    double T_  = temperature + deltaT;
	    double vaporPressure = vapor.getAcentricFactorBasedVaporPressure(temperature);
	    double vaporPressure_ = vapor.getAcentricFactorBasedVaporPressure(T_);
	    error = Math.log(vaporPressure / pressure);
	    double error_ = Math.log(vaporPressure_ / pressure);
	    temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
	} 
	return new EquilibriaSolution(temperature, pressure, iterations);
	//return temperature;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Cubic getCubicEquationOfState() {
	return cubicEquationOfState;
    }   
    void setCubicEquationOfState(Cubic eos) {
	this.cubicEquationOfState = eos;
    }
    void setAlpha(Alpha alpha) {
	this.alpha = alpha;
    }
    public Alpha getAlpha() {
	return alpha;
    }
  

    private EquilibriaSolution minimizeTemperature(EquilibriaFunction function,double pressure){
	
	double temperature = temperatureEstimate(pressure).getTemperature();
	double tolerance = 1e-4;
        double e = 100;
        double deltaT = 1;
        int count = 0;
	
        while(Math.abs(e) > tolerance && count < 1000){
	    count++;
            e = function.errorFunction(equilibriaRelation(temperature, pressure));
            double temperature_ = temperature + deltaT;
            double e_ = function.errorFunction(equilibriaRelation(temperature_, pressure));
            temperature = function.newVariableFunction(temperature, temperature_, e, e_);
        }
        return new EquilibriaSolution(temperature, pressure, count);
    }
    
     
    
    private EquilibriaSolution minimizePressure(EquilibriaFunction function,double temperature){
	double pressure = bubblePressureEstimate(temperature);
	double tolerance = 1e-4; 
	double deltaP = 0.0001;
	double e = 100;
 
	int count = 0;
	while(Math.abs(e) > tolerance && count < 1000 ){         
	    count++;
	    e =  function.errorFunction(equilibriaRelation(temperature, pressure));
	    double pressure_ = pressure * (1 + deltaP); 
	    double e_ = function.errorFunction(equilibriaRelation(temperature, pressure_));
	    pressure =  function.newVariableFunction(pressure, pressure_, e, e_);
	}  
	return new EquilibriaSolution(temperature, pressure, count);
     
}

    /**
     * @return the component
     */
    public Component getComponent() {
	return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Component component) {
	this.component = component;
    }
class BubbleTemperatureFunctions implements EquilibriaFunction{
    
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
	return liquid.calculateFugacity(temperature, pressure)/vapor.calculateFugacity(temperature, pressure);
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