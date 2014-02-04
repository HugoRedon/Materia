package termo.substance;

import java.util.HashMap;
import termo.Constants;
import termo.component.Component;
import termo.eos.alpha.Alpha;
import termo.equilibrium.EquilibriaPhaseSolution;
import termo.equilibrium.EquilibriumFunctions;
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
    public double bubbleTemperature(Double pressure) {
	double temperature = bubbleTemperatureEstimate(pressure);
	double tolerance = 1e-4;
        double e = 100;
        double deltaT = 1;
        int count = 0;
	
        while(e > tolerance && count < 1000){
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
    
    public double bubbleTemperatureEstimate(Double pressure){
	
	double temperature =  300;
	double error = 100;
	double deltaT =1;
	double tol = 1e-4;
	int iterations =0;
	
	while (error >tol  || iterations < 1000){
	    iterations++;
	    double T_  = temperature + deltaT;
	    double vaporPressure = getAcentricFactorBasedVaporPressure( temperature);
	    double vaporPressure_ = getAcentricFactorBasedVaporPressure( T_);
	    error = Math.log(vaporPressure / pressure);
	    double error_ = Math.log(vaporPressure_ / pressure);
	    temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
	} 
    return temperature;
    }
    public double bubblePressureEstimate(Double temperature){
	// HashMap<Component,Double> vaporPressures = new HashMap<>();
      //double pressure= 0;
      int  iterations = 0;
     // for( Component component : liquidFractions.keySet()){
          double vaporP = getAcentricFactorBasedVaporPressure(temperature);
         // vaporPressures.put(component, vaporP);
         // pressure += vaporP * liquidFractions.get(component);  
      //}
//      HashMap<Component,Double>  vaporFractions = EquilibriumFunctions.getVaporFractionsRaoultsLaw(pressure, liquidFractions, vaporPressures);
//      return new EquilibriaPhaseSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);
      
      return vaporP;
    }
    public  double getAcentricFactorBasedVaporPressure(double temperature){
	double pc = component.getCriticalPressure();
	double w = component.getAcentricFactor();
	double tc = component.getCriticalTemperature();

	return  pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
    }
    
    
    
    
    
}