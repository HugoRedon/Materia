package termo.substance;

import termo.Constants;
import termo.component.Component;
import termo.eos.alpha.Alpha;
import termo.equilibrium.EquilibriaPhaseSolution;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public class PureSubstance extends Substance{
    private Component component;
    private Alpha alpha;
    
    
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
    public double bubbleTemperature(double pressure) {
	double temperature = bubbleTemperatureEstimate(pressure).getTemperature();
	double tolerance = 1e-4;
        double e = 100;
        double deltaT = 1;
        int count = 0;
	
        while(Math.abs(e) > tolerance && count < 1000){
	    count++;
	    double K = calculateFugacity(temperature, pressure, Phase.LIQUID)/ calculateFugacity(temperature, pressure, Phase.VAPOR);
            e = Math.log( K);
            double T_ = temperature + deltaT;
            double k_ =calculateFugacity(T_, pressure, Phase.LIQUID)/ calculateFugacity(T_, pressure, Phase.VAPOR); 
            double e_ = Math.log(k_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
        }
        return temperature;
    }
    @Override
    public double bubblePressure(double temperature) {
	double p = bubblePressureEstimate(temperature);
	double tolerance = 1e-4; 
      double deltaP = 0.0001;
      double e = 10;
 
      int count = 0;
      while(Math.abs(e) > tolerance && count < 1000 ){         
            count++;
            double k =calculateFugacity(temperature, p, Phase.LIQUID)/calculateFugacity(temperature, p, Phase.VAPOR) ;
            e = k -1;
            double p_ = p * (1 + deltaP); 
            double k_ =calculateFugacity(temperature, p_, Phase.LIQUID)/calculateFugacity(temperature, p_, Phase.VAPOR) ;  
            double e_ = k_-1;
            p = ((p * p_ )* (e_ - e)) / ((p_ * e_) - (p * e));      
      }  

      return p;
    }
    @Override
    public EquilibriaPhaseSolution dewPressure(double temperature) {
	
	double pressure = dewPressureEstimate(temperature).getPressure();
	double tolerance = 1e-4;
	double deltaP = 0.0001;
	double e = 10;
	 int count = 0;
	while(Math.abs(e) > tolerance && count < 1000 ){         
	    count++;
            double K = calculateFugacity(temperature, pressure, Phase.LIQUID)/calculateFugacity(temperature, pressure, Phase.VAPOR);
            e = (1/K) -1;
	    double p_ = pressure * (1 + deltaP);
            double K_ = calculateFugacity(temperature, p_, Phase.LIQUID)/calculateFugacity(temperature, p_, Phase.VAPOR);
            double  e_ = (1/K_)-1;
            pressure =  pressure - e * (p_ - pressure)/ (e_ - e);
      }    
	return new EquilibriaPhaseSolution(temperature, pressure, count); 
    }
    @Override
    public EquilibriaPhaseSolution bubbleTemperatureEstimate(double pressure){
	
	double temperature =  300;
	double error = 100;
	double deltaT =1;
	double tol = 1e-4;
	int iterations =0;
	
	while (Math.abs(error) >tol  && iterations < 1000){
	    iterations++;
	    double T_  = temperature + deltaT;
	    double vaporPressure = getAcentricFactorBasedVaporPressure( temperature);
	    double vaporPressure_ = getAcentricFactorBasedVaporPressure( T_);
	    error = Math.log(vaporPressure / pressure);
	    double error_ = Math.log(vaporPressure_ / pressure);
	    temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
	} 
	
	return new EquilibriaPhaseSolution(temperature, pressure, iterations);
    //return temperature;
    }
    @Override
    public double bubblePressureEstimate(double temperature){
	return getAcentricFactorBasedVaporPressure(temperature);
    }
    @Override
    public EquilibriaPhaseSolution dewTemperature(double pressure) {

	double temperature = dewTemperatureEstimate(pressure).getTemperature();
        double e = 100;
        double deltaT = 0.1;
        double tolerance = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count == 1000){
            double K = calculateFugacity(temperature, pressure, Phase.LIQUID)/calculateFugacity(temperature, pressure, Phase.VAPOR);
            e = Math.log(1/K);
            double T_ = temperature + deltaT;
            double k_ = calculateFugacity(T_, pressure, Phase.LIQUID)/calculateFugacity(T_, pressure, Phase.VAPOR);
            double e_ = Math.log(1/k_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);

        }

	return new EquilibriaPhaseSolution(temperature, pressure, count);
    }
    @Override
    public EquilibriaPhaseSolution dewTemperatureEstimate(double pressure) {
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
	return new EquilibriaPhaseSolution(temperature, pressure, iterations);
	//return temperature;
    }
    @Override
    public EquilibriaPhaseSolution dewPressureEstimate(double temperature) {
	return new EquilibriaPhaseSolution(temperature, getAcentricFactorBasedVaporPressure(temperature), 0);
	
	
    }

  
    public  double getAcentricFactorBasedVaporPressure(double temperature){
	double pc = component.getCriticalPressure();
	double w = component.getAcentricFactor();
	double tc = component.getCriticalTemperature();

	return  pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
    }
}