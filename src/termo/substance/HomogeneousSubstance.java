
package termo.substance;

import termo.Constants;
import termo.eos.Cubic;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class  HomogeneousSubstance  {
    
    private Cubic cubicEquationOfState;    
    private Phase phase;
    protected double temperature;
    protected double pressure;
    
    public HomogeneousSubstance(){
	
    }
    public HomogeneousSubstance(Cubic eos){
	this.cubicEquationOfState= eos;
    }
    public HomogeneousSubstance(Cubic eos,Phase phase){
	this.cubicEquationOfState = eos;
	this.phase = phase;
    }

    public abstract double temperatureParcial_a();
    public abstract double calculate_a_cubicParameter();
    public abstract double calculateIdealGasEnthalpy();
    public abstract double calculate_b_cubicParameter();
    public  abstract double calculateIdealGasEntropy() ;
    public abstract double oneOver_N_Parcial_a(PureSubstance pureSubstance);
    
    public double calculateMolarVolume(){
	//return 220.746180;
	double z = calculateCompresibilityFactor();
	
	
	return cubicEquationOfState.calculateVolume(temperature, pressure, z);
    }
    public double calculateCompresibilityFactor(){
	double a = calculate_a_cubicParameter();
	double b = calculate_b_cubicParameter();
	
	double A = cubicEquationOfState.get_A(temperature, pressure, a);
	double B = cubicEquationOfState.get_B(temperature, pressure, b);
	
	return  cubicEquationOfState.calculateCompresibilityFactor(A, B, getPhase());
    }
    
//     public double bi(PureSubstance pureSubstance){
//        return pureSubstance.calculate_b_cubicParameter();
//    }
    
        
    public double calculateFugacity( PureSubstance pureSubstance){
        double a = calculate_a_cubicParameter();
        double b = calculate_b_cubicParameter();

	
	
        double  parcialb = pureSubstance.calculate_b_cubicParameter();//bi(pureSubstance);
        double parciala = oneOver_N_Parcial_a( pureSubstance);
        
        return getCubicEquationOfState().calculateFugacity(temperature, pressure, a, b, parciala, parcialb, getPhase());
    }
    
    private double calculateEntropy( double volume){
        double idealGasEntropy = calculateIdealGasEntropy();
        double b = calculate_b_cubicParameter();
        double Temp_parcial_a = temperatureParcial_a( );
        
        double L = cubicEquationOfState.calculateL(volume, b);
	double z = calculateCompresibilityFactor();
        
        //return idealGasEntropy +  Constants.R * Math.log( (pressure *(volume - b))/(Constants.R * temperature)) + L * (Temp_parcial_a)/(b);
	return idealGasEntropy +  Constants.R * Math.log( (z *(volume - b))/(volume)) + L * (Temp_parcial_a)/(temperature*b);
    }
    public double calculateEntropy(){
	double volume = calculateMolarVolume();
	return calculateEntropy( volume);
    }
    private  double calculateEnthalpy( double volume){
        double idealGasEnthalpy = calculateIdealGasEnthalpy();
        double a = calculate_a_cubicParameter();
        double b = calculate_b_cubicParameter();
        double L = cubicEquationOfState.calculateL(volume, b);
        //double alphaValue = alpha.alpha(temperature, component);
        
        double Temp_parcial_a = temperatureParcial_a( );
        
        return idealGasEnthalpy + ((Temp_parcial_a - a)/b) * L  + pressure * volume - Constants.R *temperature;
    }
    public final double calculateEnthalpy(){
	double volume = calculateMolarVolume();
	return calculateEnthalpy( volume);
    }
        
            /**
     * @return the cubicEquationOfState
     */
    public Cubic getCubicEquationOfState() {
        return cubicEquationOfState;
    }

    /**
     * @param cubicEquationOfState the cubicEquationOfState to set
     */
    public void setCubicEquationOfState(Cubic cubicEquationOfState) {
        this.cubicEquationOfState = cubicEquationOfState;
    }

    public abstract double calculatetAcentricFactorBasedVaporPressure();
//    public abstract EquilibriaSolution bubbleTemperature(double pressure);
//    public abstract EquilibriaSolution bubblePressure(double temperature);
//    public abstract EquilibriaSolution bubbleTemperatureEstimate(double pressure);
//    public abstract double bubblePressureEstimate(double temperature);
//    public abstract EquilibriaSolution dewTemperature(double pressure);
//    public abstract EquilibriaSolution dewTemperatureEstimate(double pressure);
//    public abstract EquilibriaSolution dewPressureEstimate(double temperature);
//    public abstract EquilibriaSolution dewPressure(double temperature);

    /**
     * @return the phase
     */
    public Phase getPhase() {
	return phase;
    }

    /**
     * @param phase the phase to set
     */
    public void setPhase(Phase phase) {
	this.phase = phase;
    }

    public double calculateGibbs() {
	double enthalpy = calculateEnthalpy();
	double entropy = calculateEntropy();
	
	return enthalpy - temperature * entropy;
    }

    /**
     * @return the temperature
     */
    public double getTemperature() {
	return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
	this.temperature = temperature;
    }

    /**
     * @return the pressure
     */
    public double getPressure() {
	return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(double pressure) {
	this.pressure = pressure;
    }
}
