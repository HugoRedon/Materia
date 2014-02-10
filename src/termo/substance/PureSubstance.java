package termo.substance;

import java.util.Objects;
import termo.Constants;
import termo.component.Component;
import termo.eos.alpha.Alpha;
import termo.equilibrium.EquilibriaFunction;
import termo.equilibrium.EquilibriaSolution;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public class PureSubstance extends Substance{
    private Component component;
    private Alpha alpha;

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 53 * hash + Objects.hashCode(this.component);
	hash = 53 * hash + Objects.hashCode(this.alpha);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final PureSubstance other = (PureSubstance) obj;
	if (!Objects.equals(this.component, other.component)) {
	    return false;
	}
	if (!Objects.equals(this.alpha, other.alpha)) {
	    return false;
	}
	return true;
    }
    
    
    public double calculateFugacity(double temperature, double pressure, Phase aPhase){
	return super.calculateFugacity(this, temperature, pressure, aPhase);
	
    }
    
    public boolean equals(PureSubstance substance){
        if(substance.getComponent().equals(component) &&
                substance.getCubicEquationOfState().equals(this.getCubicEquationOfState()) &&
                substance.getAlpha().equals(this.getAlpha())){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        String equationOfStateName = getCubicEquationOfState().getName();
        String alphaName = getAlpha().getName();
        String componentName = getComponent().getName();
        
        return "Pure substance with: Cubic Equation of State: " + equationOfStateName + 
                " Alpha Expression: " + alphaName + 
                " Component: " + componentName;
        
    }
    
    @Override
    public double calculateIdealGasEnthalpy(double temperature){
        double enthalpyReference = component.getEnthalpyofFormationofIdealgasat298_15Kand101325Pa();
        double referenceTemp = 298.15;
         return  component.getCp().Enthalpy(temperature, referenceTemp, enthalpyReference);
    }

    @Override
    public double temperatureParcial_a(double temperature){
        double a = calculate_a_cubicParameter(temperature);
        double ToveralphaDerivativeAlpha = alpha.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
        return a * ToveralphaDerivativeAlpha;
    }

    
    @Override
    public double oneOver_N_Parcial_a(double temperature, PureSubstance pureSubstance){
        return 2 * calculate_a_cubicParameter(temperature);
    }
    
    
    

    @Override
    public double calculate_a_cubicParameter(double temperature){
        
        double omega_a = getCubicEquationOfState().getOmega_a();
        
             double tc = component.getCriticalTemperature();
        double pc = component.getCriticalPressure();
        
        return (omega_a * Math.pow( Constants.R  * tc,2) *getAlpha().alpha(temperature,component) )/ (pc);  
    }

    @Override
    public double calculate_b_cubicParameter(){
         
        double omega_b = getCubicEquationOfState().getOmega_b();
        
          double tc = component.getCriticalTemperature();
        double pc = component.getCriticalPressure();
        
        return (omega_b * tc* Constants.R)/ (pc); 
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
        this.alpha = alpha;
    }
    @Override
    public  double calculateIdealGasEntropy(double temperature, double pressure) {
        double entropyReference = component.getAbsoluteEntropyofIdealGasat298_15Kand101325Pa();
        double referenceTemperature = 298.15;
        double referencePressure = 101325;
        return component.getCp().idealGasEntropy(temperature, referenceTemperature, pressure, referencePressure, entropyReference);
    }

    @Override
    public EquilibriaSolution bubbleTemperature(double pressure) {
	
	EquilibriaFunction function = new BubbleTemperatureFunctions();
	return minimizeTemperature(function, pressure);
    }
    
    @Override
    public EquilibriaSolution bubblePressure(double temperature) {
	EquilibriaFunction function = new BubblePressureFunctions();
	return minimizePressure(function, temperature);
    }
    @Override
    public EquilibriaSolution dewPressure(double temperature) {
	
	EquilibriaFunction function = new DewPressureFunctions();
	return minimizePressure(function, temperature);
    }
    
    
    @Override
    public EquilibriaSolution dewTemperatureEstimate(double pressure) {
	return temperatureEstimate(pressure);
    }

    
    
        @Override
    public EquilibriaSolution bubbleTemperatureEstimate(double pressure){
	return temperatureEstimate(pressure);
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
    
    
    public double equilibriaRelation(double temperature, double pressure){
	return calculateFugacity(temperature, pressure, Phase.LIQUID)/calculateFugacity(temperature, pressure, Phase.VAPOR);
    }
    

    @Override
    public double bubblePressureEstimate(double temperature){
	return getAcentricFactorBasedVaporPressure(temperature);
    }
    
    @Override
    public EquilibriaSolution dewTemperature(double pressure) {
	EquilibriaFunction function = new DewTemperatureFunctions();
	return minimizeTemperature(function, pressure);
    }
    
    
    
    @Override
    public EquilibriaSolution dewPressureEstimate(double temperature) {
	return new EquilibriaSolution(temperature, getAcentricFactorBasedVaporPressure(temperature), 0);
    }

  
    public  double getAcentricFactorBasedVaporPressure(double temperature){
	double pc = component.getCriticalPressure();
	double w = component.getAcentricFactor();
	double tc = component.getCriticalTemperature();

	return  pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
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
	    double vaporPressure =getAcentricFactorBasedVaporPressure(temperature);
	    double vaporPressure_ = getAcentricFactorBasedVaporPressure(T_);
	    error = Math.log(vaporPressure / pressure);
	    double error_ = Math.log(vaporPressure_ / pressure);
	    temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
	} 
	return new EquilibriaSolution(temperature, pressure, iterations);
	//return temperature;
    }
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