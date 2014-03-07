package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class MixtureSubstance extends HomogeneousSubstance{
    //private MixingRule mixingRule;
    protected ArrayList<PureSubstance> pureSubstances = new ArrayList<>();
    protected HashMap<PureSubstance,Double> molarFractions = new HashMap<>();
    protected InteractionParameter binaryParameters = new InteractionParameter();

    @Override
    public void setTemperature(double temperature) {
	super.setTemperature(temperature); 
	for(PureSubstance pure: pureSubstances){
	    pure.setTemperature(temperature);
	}
    }

    @Override
    public void setPressure(double pressure){
	super.setPressure(pressure); 
	for(PureSubstance pure: pureSubstances){
	    pure.setPressure(pressure);
	}
    }
    
    
     public MixtureSubstance(Cubic equationOfState,Alpha alpha,  ArrayList<Component> components,Phase phase, InteractionParameter k){
	super(equationOfState,phase);
	
	for (Component component:components){
	    PureSubstance sub = new PureSubstance(equationOfState, alpha, component, phase);
	    pureSubstances.add(sub);
	}
	this.binaryParameters = k;
    }
    public MixtureSubstance(Cubic cubic ,Phase phase){
	super(cubic, phase);
    }
//    public MixtureSubstance(Cubic equationOfState, Alpha alpha,MixingRule mixingRule, ArrayList<Component> components, Phase phase) {
//	super(equationOfState,phase);
//	//this.mixingRule = mixingRule;
//	for (Component component:components){
//	    PureSubstance sub = new PureSubstance(equationOfState, alpha, component, phase);
//	    pureSubstances.add(sub);
//	}
//    }
  

    @Override
    public void setPhase(Phase phase) {
	super.setPhase(phase); 
	for (PureSubstance pure : pureSubstances){
	    pure.setPhase(phase);
	}
    }
    
    private PureSubstance getPureSubstance(Component component){
	PureSubstance result = null;
	for(PureSubstance pure: pureSubstances){
	    if(component.equals(pure.getComponent())){
		result = pure;
	    }
	}
	return result;
    }
    
    
    public double calculateFugacity(Component component){
	PureSubstance pure = getPureSubstance(component);
	return calculateFugacity(pure);
	//throw new Exception("La mezcla no contiene el componente " + component.toString());
	
    }
    
    
    
    public MixtureSubstance(Cubic eos, Alpha alpha, MixingRule mixingRule, ArrayList<Component> components){
	super(eos);
	//this.mixingRule = mixingRule;
	for(Component component: components){
	    PureSubstance sub = new PureSubstance(eos, alpha, component);
	    pureSubstances.add(sub);
	}
    }
    
    public void addComponent(PureSubstance pureSubstance, double molarFraction){
        pureSubstance.setCubicEquationOfState(getCubicEquationOfState());
        getPureSubstances().add(pureSubstance);
        getMolarFractions().put(pureSubstance, molarFraction);
    }
    public void removeComponent(PureSubstance pureSubstance){
      	getPureSubstances().remove(pureSubstance);
      	getMolarFractions().remove(pureSubstance);
    }
    @Override
    public void setCubicEquationOfState(Cubic cubic){
        super.setCubicEquationOfState(cubic);
            for (PureSubstance pureSubstance: getPureSubstances()){
            pureSubstance.setCubicEquationOfState(cubic);
        }
    }

    public HashMap<Component,Double> single_as(double aTemperature){
        HashMap<Component, Double> singleAs = new HashMap<>();
       
        for(PureSubstance pureSubstance : getPureSubstances()){
            Component component = pureSubstance.getComponent();
            double a = pureSubstance.calculate_a_cubicParameter();
            singleAs.put(component, a);
        }
        return singleAs;
    }
//    public HashMap<Component,Double> single_bs(){
//         HashMap<Component,Double> singleBs = new HashMap<>();
//           for(PureSubstance pureSubstance : getPureSubstances()){
//            Component component = pureSubstance.getComponent();
//            double b = pureSubstance.calculate_b_cubicParameter();
//            singleBs.put(component,b);
//        }
//          return singleBs;
//    }
        public HashMap<Component,Double> single_Alphas(double temperature){
         HashMap<Component,Double> singleAlphas = new HashMap<>();
         
        for(PureSubstance pureSubstance : getPureSubstances()){
            Component component = pureSubstance.getComponent();
            
            double alpha = pureSubstance.getAlpha().alpha(temperature, component);
            singleAlphas.put(component,alpha);
        }
           
          return singleAlphas;
    }
    
    public HashMap<Component,Double> alphaDerivatives(double temperature){
        HashMap<Component,Double> alphaDerivatives = new HashMap<>();
        for(PureSubstance pureSubstance: getPureSubstances()){
            Component component = pureSubstance.getComponent();
            double alphaDerivative = pureSubstance.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
            
            alphaDerivatives.put(component, alphaDerivative);
        }
        return alphaDerivatives;
    }
    
//    @Override
//    public double temperatureParcial_a() {
//        return getMixingRule().temperatureParcial_a(super.getTemperature(), molarFractions,binaryParameters );
//    }

//    @Override
//    public abstract double calculate_a_cubicParameter();
//    
//     @Override
//    public double calculate_b_cubicParameter() {       
//        return getMixingRule().b(molarFractions,super.getTemperature(), binaryParameters);
//    }
    
    
    public ArrayList<Component> getComponents(){
        ArrayList<Component> components = new ArrayList<>();
        for (PureSubstance pureSubstance : getPureSubstances()){
            components.add(pureSubstance.getComponent());
        }
        return components;
    }
    public HashMap<Component,Double> getReadOnlyFractions(){
        HashMap<Component,Double> fractions = new HashMap<>();
        for (PureSubstance pureSubstance : getPureSubstances()){
            Component component = pureSubstance.getComponent();
            double molarFraction = getMolarFractions().get(pureSubstance);
            fractions.put(component, molarFraction);
        }
        return fractions;
    }

    @Override
    public double calculateIdealGasEnthalpy() {
        double idealGasEnthalpy = 0;
        for(PureSubstance pureSubstance: getPureSubstances()){
            double xi = getMolarFractions().get(pureSubstance);
            double idealGasEnthalpyFori = pureSubstance.calculateIdealGasEnthalpy();
            
            idealGasEnthalpy += xi *idealGasEnthalpyFori;
        }
        return idealGasEnthalpy;
    }

    @Override
    public double calculateIdealGasEntropy() {

           double term1 = 0;
           double term2 = 0;
        
        for(PureSubstance pureSubstance: getPureSubstances()){
            double xi = getMolarFractions().get(pureSubstance);
            double entropyFori = pureSubstance.calculateIdealGasEntropy();
            
            term1 += xi * entropyFori;
            
            term2 += xi * Math.log(xi);
            
        }
        
        return term1  - Constants.R * term2;
    }

//    @Override
//    public double oneOver_N_Parcial_a(PureSubstance pureSubstance) {
//        Component component = pureSubstance.getComponent();
//       return getMixingRule().oneOverNParcial_aN2RespectN(
//               super.getTemperature(), 
//               pureSubstance, 
//               molarFractions,
//	       binaryParameters);
//    }

//    /**
//     * @return the mixingRule
//     */
//    public MixingRule getMixingRule() {
//	return mixingRule;
//    }
//
//    /**
//     * @param mixingRule the mixingRule to set
//     */
//    public void setMixingRule(MixingRule mixingRule) {
//	this.mixingRule = mixingRule;
//    }

    /**
     * @return the pureSubstances
     */
    public ArrayList<PureSubstance> getPureSubstances() {
	return pureSubstances;
    }

//    /**
//     * @param pureSubstances the pureSubstances to set
//     */
//    private void setPureSubstances(ArrayList<PureSubstance> pureSubstances) {
//	this.pureSubstances = pureSubstances;
//    }

    /**
     * @return the molarFractions
     */
    public HashMap<PureSubstance,Double> getMolarFractions() {
	return molarFractions;
    }

    /**
     * @param molarFractions the molarFractions to set
     */
//    private void setMolarFractions(HashMap<PureSubstance,Double> molarFractions) {
//	this.molarFractions = molarFractions;
//    }

//    /**
//     * @return the binaryParameters
//     */
//    public InteractionParameter getBinaryParameters() {
//	return binaryParameters;
//    }
      public BinaryInteractionParameter getBinaryParameters() {
	return null;
    }

    /**
     * @param binaryParameters the binaryParameters to set
     */
    public void setBinaryParameters(InteractionParameter binaryParameters) {
	this.binaryParameters = binaryParameters;
    }

    

    private void setFraction(PureSubstance component, double i) {
	molarFractions.put(component, i);
    }

    public void setFraction(Component component, Double fraction) {
	for (PureSubstance pure : pureSubstances){
	    if(pure.getComponent().equals(component)){
		molarFractions.put(pure, fraction);
	    }
	}
    }
    
    public void setFractions(HashMap<Component,Double> fractions){
	for(PureSubstance pure: pureSubstances){
	    molarFractions.put(pure, fractions.get(pure.getComponent()));
	}
    }

    @Override
    public double calculatetAcentricFactorBasedVaporPressure() {
	double result = 0;
	for( PureSubstance component : pureSubstances){
	    double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	    result += vaporP * molarFractions.get(component);  
	}
	return result;
    }
}

