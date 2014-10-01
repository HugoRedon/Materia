
package termo.matter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;
import termo.Constants;
import termo.eos.Cubic;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class  Homogeneous  implements PropertyChangeListener{
    
    private Cubic cubicEquationOfState;    
    private Phase phase;
    protected double temperature;
    protected double pressure;
    
    protected PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
    
      @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch(propertyName){
            case "cubic":
                setCubicEquationOfState((Cubic)evt.getNewValue());
                break;
            case "phase":
                setPhase((Phase)evt.getNewValue());
                break;
            case "temperature":
                setTemperature((Double)evt.getNewValue());
                break;
            case "pressure":
                setPressure((Double)evt.getNewValue());
                break;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.cubicEquationOfState);
        hash = 41 * hash + Objects.hashCode(this.phase);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.temperature) ^ (Double.doubleToLongBits(this.temperature) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.pressure) ^ (Double.doubleToLongBits(this.pressure) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.mpcs);
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
        final Homogeneous other = (Homogeneous) obj;
        if (!Objects.equals(this.cubicEquationOfState, other.cubicEquationOfState)) {
            return false;
        }
        if (this.phase != other.phase) {
            return false;
        }
        if (Double.doubleToLongBits(this.temperature) != Double.doubleToLongBits(other.temperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.pressure) != Double.doubleToLongBits(other.pressure)) {
            return false;
        }
        if (!Objects.equals(this.mpcs, other.mpcs)) {
            return false;
        }
        return true;
    }

 
    
    
    public Homogeneous(){
	
    }
    
    
//    public Homogeneous(Cubic eos){
//	this.cubicEquationOfState= eos;
//    }
    public Homogeneous(Cubic eos,Phase phase){
	this.cubicEquationOfState = eos;
	this.phase = phase;
    }
    
    public String enthalpyLatexEquation(){
        StringBuilder b = new StringBuilder();
        b.append("h= h_{ideal }");
        b.append("+ \\left[" );
        b.append("\\frac{ T \\left(   \\frac{\\partial a }{\\partial T}  \\right)  - a }{b  \\sqrt{u^2 -4 w}}");
        b.append("\\right]");
        b.append("\\ln{" );
        b.append("\\left[" );
        b.append("\\frac{2 v + b \\left(u + \\sqrt{u^2 - 4w}\\right)}");
        b.append("{2 v + b \\left(u - \\sqrt{u^2 - 4w}\\right)}");
        b.append("\\right]");
        b.append("}");
        b.append("+ pv - RT");
        return b.toString();
    }
    public String entropyLatexEquation(){
        StringBuilder b = new StringBuilder();
        b.append("s= s_{ideal }");
        b.append("+ R  \\ln{\\frac{z \\left(v-b\\right) }{v}}");
        b.append("+ \\left[");
        b.append("\\frac{ \\frac{\\partial a }{\\partial T} }{b  \\sqrt{u^2 -4 w}}");
        b.append("\\right]");
        b.append("\\ln{");
        b.append("\\left[");
        b.append("\\frac{2 v + b \\left(u + \\sqrt{u^2 - 4w}\\right)}");
        b.append("{2 v + b \\left(u - \\sqrt{u^2 - 4w}\\right)}");
        b.append("\\right]");
        b.append("}");
        return b.toString();
    }
    public String gibbsLatexEquation(){
        return "  g = h - T * s";
    }
    public double calculatePressure(double temperature,double volume){
    	setTemperature(temperature);
    	
    	double a = calculate_a_cubicParameter();
    	double b = calculate_b_cubicParameter();
    	
    	setPressure(cubicEquationOfState.calculatePressure(temperature, volume, a, b));
    	return pressure;
    }

    public abstract double partial_aPartial_temperature();
    public abstract double calculate_a_cubicParameter();
    public abstract double calculateIdealGasEnthalpy();
    public abstract double calculate_b_cubicParameter();
    public  abstract double calculateIdealGasEntropy() ;
    public abstract double oneOver_N_Parcial_a(Substance pureSubstance);
    public double oneOver_N_Parcial_b(Substance pureSubstance){
    	return pureSubstance.calculate_b_cubicParameter();
    }
    
    public double calculateMolarVolume(){
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
    protected double calculateFugacityCoefficient( Substance pureSubstance){
        double a = calculate_a_cubicParameter();
        double b = calculate_b_cubicParameter();
        double  parcialb = oneOver_N_Parcial_b( pureSubstance);//bi(pureSubstance);TODO: esto no es correcto para reglas de mezclado como wong sandler
        double parciala = oneOver_N_Parcial_a( pureSubstance);
        
        return getCubicEquationOfState().calculateFugacity(temperature, pressure, a, b, parciala, parcialb, getPhase());
    }  
    protected double calculateFugacity(Substance pureSubstance){
    	double result = 0;
    	double fugacityCoefficient = calculateFugacityCoefficient(pureSubstance);
    	double fraction = pureSubstance.getMolarFraction();
    	result = pressure * fraction *fugacityCoefficient;
    			
    	return result;
    }
    private double calculateEntropy( double volume){
        double idealGasEntropy = calculateIdealGasEntropy();
        double b = calculate_b_cubicParameter();
        double Temp_parcial_a = partial_aPartial_temperature( );
        
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
        double partial_aPartial_temperature = partial_aPartial_temperature( );
        
        return idealGasEnthalpy + ((partial_aPartial_temperature - a)/b) * L  + pressure * volume - Constants.R *temperature;
    }
    public final double calculateEnthalpy(){
		double volume = calculateMolarVolume();
		return calculateEnthalpy( volume);
    }
    public Cubic getCubicEquationOfState() {
        return cubicEquationOfState;
    }
    public void setCubicEquationOfState(Cubic cubicEquationOfState) {
        Cubic oldCubic = this.cubicEquationOfState;
        this.cubicEquationOfState = cubicEquationOfState;
        mpcs.firePropertyChange("cubic", oldCubic, cubicEquationOfState);
    }
    public abstract double calculatetAcentricFactorBasedVaporPressure();
    public Phase getPhase() {
		return phase;
    }
    public void setPhase(Phase phase) {
        Phase oldPhase = this.phase;
	this.phase = phase;
        mpcs.firePropertyChange("phase", oldPhase, phase);
    }
    public double calculateGibbs() {
		double enthalpy = calculateEnthalpy();
		double entropy = calculateEntropy();
		
		return enthalpy - temperature * entropy;
    }
    public double getTemperature() {
    	return temperature;
    }
    public void setTemperature(double temperature) {
        double oldTemperature = this.temperature;
	this.temperature = temperature;
        mpcs.firePropertyChange("temperature", oldTemperature, temperature);
    }
    public double getPressure() {
	return pressure;
    }
    public void setPressure(double pressure) {
        double oldPressure = this.pressure;
	this.pressure = pressure;
        mpcs.firePropertyChange("pressure",oldPressure,pressure);
    }
}
