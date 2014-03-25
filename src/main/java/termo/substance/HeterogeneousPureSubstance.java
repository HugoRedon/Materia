package termo.substance;

import java.util.ArrayList;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.equilibrium.EquilibriaFunction;
import termo.equilibrium.EquilibriaSolution;
import termo.optimization.AlphaOptimization;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousPureSubstance extends HeterogeneousSubstance{

//    private Cubic cubicEquationOfState;
//    private Alpha alpha;
//
//    private Component component;
//    private PureSubstance liquid;
//    private PureSubstance vapor;
    
    public HeterogeneousPureSubstance(){
        
    }
    
    public HeterogeneousPureSubstance(
	    Cubic eos,
	    Alpha alpha,
	    Component component){
//	this.cubicEquationOfState = eos;
//	this.alpha = alpha;
//	this.component = component;
//	
	liquid = new PureSubstance(eos, alpha, component, Phase.LIQUID);
	vapor = new PureSubstance(eos, alpha, component, Phase.VAPOR);
    }
        
    public HeterogeneousPureSubstance(PureSubstance pure){
        liquid = new PureSubstance(
                pure.getCubicEquationOfState(), pure.getAlpha(), pure.getComponent(), Phase.LIQUID);
        vapor = new PureSubstance(
                pure.getCubicEquationOfState(), pure.getAlpha(), pure.getComponent(), Phase.VAPOR);
    }
    
    
    
    
    
    
    
    @Override
    public int bubbleTemperatureEstimate(){
	return temperatureEstimate();
    }

  
    
    @Override
    public int bubbleTemperature() {
	
	EquilibriaFunction function = new BubbleTemperatureFunctions();
	return minimizeTemperature(function);
    }
       
    @Override
    public void bubblePressureEstimate(){
	setPressure(liquid.calculatetAcentricFactorBasedVaporPressure());
    }
    
    @Override
    public int bubblePressure() {
	EquilibriaFunction function = new BubblePressureFunctions();
	return minimizePressure(function);
    }
    

    @Override
    public int dewTemperatureEstimate() {
	return temperatureEstimate();
    }
    

    /**
     *
     * @return
     */
    @Override
    public int dewTemperature() {
	EquilibriaFunction function = new DewTemperatureFunctions();
	return minimizeTemperature(function);
    }
     
    @Override
    public void dewPressureEstimate() {
	setPressure(vapor.calculatetAcentricFactorBasedVaporPressure());
	//return new EquilibriaSolution(temperature, vapor.calculatetAcentricFactorBasedVaporPressure(), 0);
    }
    
    @Override
    public int dewPressure() {
	
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
    public PureSubstance getLiquid() {
	return (PureSubstance)liquid;
    }

    @Override
    public PureSubstance getVapor() {
	return (PureSubstance) vapor;
    }
    
    
    
    
//    public Cubic getCubicEquationOfState() {
//	return cubicEquationOfState;
//    }   
//    void setCubicEquationOfState(Cubic eos) {
//	this.cubicEquationOfState = eos;
//    }
//    void setAlpha(Alpha alpha) {
//	this.alpha = alpha;
//    }
//    public Alpha getAlpha() {
//	return alpha;
//    }
  

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
	bubblePressureEstimate(temperature);
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
	    p =  function.newVariableFunction(p, pressure_, e, e_);
	}  
	
	setPressure(p);
	return count;
	//return new EquilibriaSolution(temperature, pressure, count);
             
}

//    /**
//     * @return the component
//     */
//    public Component getComponent() {
//	return component;
//    }
//
//    /**
//     * @param component the component to set
//     */
//    public void setComponent(Component component) {
//	this.component = component;
//    }

    private AlphaOptimization alphaOptimizer = new AlphaOptimization(this);
    public void optimizeTo(ArrayList<ExperimentalData> expData) {
        alphaOptimizer.setExperimental(expData);
        alphaOptimizer.solve();
    }

    /**
     * @return the alphaOptimizer
     */
    public AlphaOptimization getAlphaOptimizer() {
        return alphaOptimizer;
    }

    /**
     * @param alphaOptimizer the alphaOptimizer to set
     */
    public void setAlphaOptimizer(AlphaOptimization alphaOptimizer) {
        this.alphaOptimizer = alphaOptimizer;
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