
package termo.substance;

import termo.Constants;
import termo.eos.Cubic;
import termo.equilibrium.EquilibriaSolution;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class  HomogeneousSubstance {
    
    private Cubic cubicEquationOfState;    
    private Phase phase;

    public abstract double temperatureParcial_a(double temperature);
    public abstract double calculate_a_cubicParameter(double temperature);
    public abstract double calculateIdealGasEnthalpy(double temperature);
    public abstract double calculate_b_cubicParameter();
    public  abstract double calculateIdealGasEntropy(double temperature, double pressure) ;
    public abstract double oneOver_N_Parcial_a(double temperature,PureSubstance pureSubstance);
    
    public double calculateMolarVolume(double temperature,double pressure){
	//return 220.746180;
	double z = calculateCompresibilityFactor(temperature, pressure);
	
	
	return cubicEquationOfState.calculateVolume(temperature, pressure, z);
    }
    public double calculateCompresibilityFactor(double temperature,double pressure){
	double a = calculate_a_cubicParameter(temperature);
	double b = calculate_b_cubicParameter();
	
	double A = cubicEquationOfState.get_A(temperature, pressure, a);
	double B = cubicEquationOfState.get_B(temperature, pressure, b);
	
	return  cubicEquationOfState.calculateCompresibilityFactor(A, B, getPhase());
    }
    
     public double bi(PureSubstance pureSubstance){
        return pureSubstance.calculate_b_cubicParameter();
    }
    
        
    public double calculateFugacity( PureSubstance pureSubstance,double temperature, double pressure){
        double a = calculate_a_cubicParameter(temperature);
        double b = calculate_b_cubicParameter();

        double  parcialb = bi(pureSubstance);
        double parciala = oneOver_N_Parcial_a( temperature,pureSubstance);
        
        return getCubicEquationOfState().calculateFugacity(temperature, pressure, a, b, parciala, parcialb, getPhase());
    }
    
    public double calculateEntropy(double temperature, double pressure, double volume){
        double idealGasEntropy = calculateIdealGasEntropy(temperature, pressure);
        double b = calculate_b_cubicParameter();
        double Temp_parcial_a = temperatureParcial_a( temperature);
        
        double L = cubicEquationOfState.calculateL(volume, b);
        
        return idealGasEntropy +  Constants.R * Math.log( (pressure *(volume - b))/(Constants.R * temperature)) + L * (Temp_parcial_a)/(b);
    }
    public final double calculateEnthalpy(double temperature, double pressure, double volume){
        double idealGasEnthalpy = calculateIdealGasEnthalpy(temperature);
        double a = calculate_a_cubicParameter(temperature);
        double b = calculate_b_cubicParameter();
        double L = cubicEquationOfState.calculateL(volume, b);
        //double alphaValue = alpha.alpha(temperature, component);
        
        double Temp_parcial_a = temperatureParcial_a( temperature);
        
        return idealGasEnthalpy + ((Temp_parcial_a - a)/b) * L  + pressure * volume - Constants.R *temperature;
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
}
