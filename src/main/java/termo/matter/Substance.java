package termo.matter;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;
import termo.Constants;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Substance extends Homogeneous {
    private Compound component;
    private Alpha alpha;
    private Double molarFraction;
    
    public Substance(){
	
    }

//    public Substance(Cubic eos,Alpha alpha,Compound component){
//	super(eos);
//	this.alpha = alpha;
//	this.component = component;
//    }
//    private Substance(Alpha alpha , Compound component){
//	this.alpha = alpha;
//	this.component = component;
//    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt); //To change body of generated methods, choose Tools | Templates.
        switch(evt.getPropertyName()){
            case "alpha":
                this.alpha = (Alpha)evt.getNewValue();
                break;
            case "component":
                this.component =(Compound)evt.getNewValue();
                break;
        }
    }
    
    
    
    public Substance(Cubic eos,Alpha alpha,Compound component,Phase phase ){
	super(eos,phase);
	
	this.alpha = alpha;
	this.component = component;
    }
    
    public ArrayList<Field> getComponentAlphaParameters(){
        return this.component.getAlphaParametersFor(alpha);
    }

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
	final Substance other = (Substance) obj;
	if (!Objects.equals(this.component, other.component)) {
	    return false;
	}
	if (!Objects.equals(this.alpha, other.alpha)) {
	    return false;
	}
	return true;
    }
    
    
    public double calculateFugacity(){
	return super.calculateFugacity(this);
	
    }
//    @Override
//    public double calculateFugacity(Compound component) {
//	
//    }
    
    
    public boolean equals(Substance substance){
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
    public double calculateIdealGasEnthalpy(){
        double enthalpyReference = component.getEnthalpyofFormationofIdealgasat298_15Kand101325Pa();
        double referenceTemp = 298.15;
         return  component.getCp().Enthalpy(super.getTemperature(), referenceTemp, enthalpyReference);
    }

    @Override
    public double temperatureParcial_a(){
        double a = calculate_a_cubicParameter();
        double ToveralphaDerivativeAlpha = alpha.TempOverAlphaTimesDerivativeAlphaRespectTemperature(super.getTemperature(), component);
        return a * ToveralphaDerivativeAlpha;
    }

    
    @Override
    public double oneOver_N_Parcial_a( Substance pureSubstance){
        return 2 * calculate_a_cubicParameter();
    }
    
    
    

    @Override
    public double calculate_a_cubicParameter(){
        
        double omega_a = getCubicEquationOfState().getOmega_a();
        
	double tc = component.getCriticalTemperature();
        double pc = component.getCriticalPressure();
        
	double alphaE = alpha.alpha(super.getTemperature(),component);
	
	
        return (omega_a * Math.pow( Constants.R  * tc,2) *alphaE )/ (pc);  
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
    public Compound getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Compound component) {
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
    public  double calculateIdealGasEntropy() {
        double entropyReference = component.getAbsoluteEntropyofIdealGasat298_15Kand101325Pa();//
	
	//calculating idealgas of formation from enthalpy and gibbs
	double enthalpy = component.getEnthalpyofFormationofIdealgasat298_15Kand101325Pa();
	double gibbs = component.getGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa();
	double entropyFormation = (enthalpy - gibbs)/temperature;
	
	entropyReference = entropyFormation;
	
        double referenceTemperature = 298.15;
        double referencePressure = 101325;
        return component.getCp().idealGasEntropy(super.getTemperature(), referenceTemperature, super.getPressure(), referencePressure, entropyReference);
    }

    
    
    @Override
    public  double calculatetAcentricFactorBasedVaporPressure(){
	double pc = component.getCriticalPressure();
	double w = component.getAcentricFactor();
	double tc = component.getCriticalTemperature();

	return  pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
    }

    /**
     * @return the molarFraction
     */
    public Double getMolarFraction() {
        return molarFraction;
    }

    /**
     * @param molarFraction the molarFraction to set
     */
    public void setMolarFraction(Double molarFraction) {
        this.molarFraction = molarFraction;
    }

  

    
}







