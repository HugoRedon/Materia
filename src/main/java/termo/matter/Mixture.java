package termo.matter;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public final class Mixture extends Homogeneous{
    private MixingRule mixingRule;
    protected Set<Substance> pureSubstances = new HashSet<>();
//    protected HashMap<String,Double> molarFractions = new HashMap<>();
    protected InteractionParameter binaryParameters = new ActivityModelBinaryParameter();

    private Alpha alpha;
    
    public Mixture(){
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt); 
         String propertyName = evt.getPropertyName();
        switch(propertyName){
            case "alpha":
                setAlpha((Alpha)evt.getNewValue());
                break;
            case "components":
                setComponents((HashSet<Compound>)evt.getNewValue());
                break;
          
            case"mixingRule":
                setMixingRule((MixingRule)evt.getNewValue());
                break;
            case"interactionParameters":
                setBinaryParameters((InteractionParameter)evt.getNewValue());
                break;
        }
    }

    public Mixture(Cubic equationOfState,Alpha alpha,  Set<Compound> components,Phase phase,MixingRule mixingRule ,InteractionParameter k){
    	this(equationOfState, phase, mixingRule, k);
    	setAlpha(alpha);
        setComponents(components);
    }
     
    public Mixture(Cubic equationOfState, Phase phase,MixingRule mixingRule ,InteractionParameter k){
    	super(equationOfState,phase);
            setMixingRule(mixingRule);
            
            setBinaryParameters(k);
    }


    public void setComponents(Set<Compound> components){
        pureSubstances.clear();
        //molarFractions.clear();
        double fraction = 1d/components.size();
        for(Compound component : components){ //implement property change listener
            Substance pure = new Substance(super.getCubicEquationOfState(), alpha, component, super.getPhase());
            pure.setTemperature(temperature);
            pure.setPressure(pressure);
            pure.setMolarFraction(fraction);
            mpcs.addPropertyChangeListener(pure);
            pureSubstances.add(pure);
           
            //setFraction(pure, fraction);
        }
    }

    
    public Substance getPureSubstance(Compound component){
	Substance result = null;
	for(Substance pure: pureSubstances){
	    if(component.equals(pure.getComponent())){
		result = pure;
	    }
	}
	return result;
    }
    
    
    public double calculateFugacityCoefficient(Compound component){
		Substance pure = getPureSubstance(component);
		return calculateFugacityCoefficient(pure);
		//throw new Exception("La mezcla no contiene el componente " + component.toString());
    }
    public double calculateFugacity(Compound compound){
    	Substance pure = getPureSubstance(compound);
    	return super.calculateFugacity(pure);
    }
    

    
    public void addComponent(Substance pureSubstance, double molarFraction){
        pureSubstance.setMolarFraction(molarFraction);
        //pureSubstance.setCubicEquationOfState(getCubicEquationOfState());
        mpcs.addPropertyChangeListener(pureSubstance);
        getPureSubstances().add(pureSubstance);
//        getMolarFractions().put(pureSubstance.getComponent().getName(), molarFraction);
    }
    
    public void addCompounds(Set<Substance> pureSubstancesToAdd ){
    	Set<Substance> substancesAccepted = new HashSet();
    	for(Substance substance :pureSubstancesToAdd){
    		if(!this.pureSubstances.contains(substance)){
    			substancesAccepted.add(substance);
    			mpcs.addPropertyChangeListener(substance);
    		}    		
    	}
    	this.pureSubstances.addAll(substancesAccepted);
    }
    
    public void removeComponent(Substance pureSubstance){
        mpcs.removePropertyChangeListener(pureSubstance);
      	getPureSubstances().remove(pureSubstance);
//      	getMolarFractions().remove(pureSubstance.getComponent().getName());
    }


    @Override
    public double partial_aPartial_temperature() {
        return getMixingRule().temperatureParcial_a(this);
    }

    @Override
    public double calculate_a_cubicParameter(){
       
        return mixingRule.a(this);
    }
    
     @Override
    public double calculate_b_cubicParameter() {       
        return getMixingRule().b(this);
    }
    
    
    public HashSet<Compound> getComponents(){
        HashSet<Compound> components = new HashSet<>();
        for (Substance pureSubstance : getPureSubstances()){
            components.add(pureSubstance.getComponent());
        }
        return components;
    }
    public HashMap<Compound,Double> getReadOnlyFractions(){
        HashMap<Compound,Double> fractions = new HashMap<>();
        for (Substance pureSubstance : getPureSubstances()){
            Compound component = pureSubstance.getComponent();
            double molarFraction = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            fractions.put(component, molarFraction);
        }
        return fractions;
    }
    public HashMap<String,Double> getFractions(){
        HashMap<String,Double> fractions = new HashMap<>();
        for (Substance pureSubstance : getPureSubstances()){
            Compound component = pureSubstance.getComponent();
            double molarFraction = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            fractions.put(component.getName(), molarFraction);
        }
        return fractions;
    }

    
    @Override
    public double calculateIdealGasEnthalpy() {
        double idealGasEnthalpy = 0;
        for(Substance pureSubstance: getPureSubstances()){
            double xi = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            double idealGasEnthalpyFori = pureSubstance.calculateIdealGasEnthalpy();
            
            idealGasEnthalpy += xi *idealGasEnthalpyFori;
        }
        return idealGasEnthalpy;
    }

    @Override
    public double calculateIdealGasEntropy() {

           double term1 = 0;
           double term2 = 0;
        
        for(Substance pureSubstance: getPureSubstances()){
            double xi = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            double entropyFori = pureSubstance.calculateIdealGasEntropy();
            
            term1 += xi * entropyFori;
            
            term2 +=(xi!=0)? xi * Math.log(xi):0;
            
        }
        
        return term1  - Constants.R * term2;
    }

    @Override
    public double oneOver_N_Parcial_a(Substance pureSubstance) {
       return getMixingRule().oneOverNParcial_aN2RespectN(
               pureSubstance, 
               this);
    }

    
    @Override
    public double oneOver_N_Parcial_b(Substance pureSubstance) {
    	return mixingRule.oneOverNParcial_bNRespectN(pureSubstance, this);    	
    }
    /**
     * @return the mixingRule
     */
    public MixingRule getMixingRule() {
	return mixingRule;
    }

    /**
     * @param mixingRule the mixingRule to set
     */
    public void setMixingRule(MixingRule mixingRule) {
        MixingRule oldMixingRule = this.mixingRule;
	this.mixingRule = mixingRule;
        mpcs.firePropertyChange("mixingRule", oldMixingRule, mixingRule);
    }

    /**
     * @return the pureSubstances
     */
    public Set<Substance> getPureSubstances() {
	return pureSubstances;
    }

//    /**
//     * @param pureSubstances the pureSubstances to set
//     */
//    private void setPureSubstances(ArrayList<PureSubstance> pureSubstances) {
//	this.pureSubstances = pureSubstances;
//    }

//    /**
//     * @return the molarFractions
//     */
//    private HashMap<String,Double> getMolarFractions() {
//	return molarFractions;
//    }
//    
    public double getFraction(Substance pure){
        return getPureSubstance(pure.getComponent()).getMolarFraction();
       // return molarFractions.get(pure.getComponent().getName());
    }

    /**
     * @param molarFractions the molarFractions to set
     */
//    private void setMolarFractions(HashMap<PureSubstance,Double> molarFractions) {
//	this.molarFractions = molarFractions;
//    }

    /**
     * @return the binaryParameters
     */
    public InteractionParameter getBinaryParameters() {
	return binaryParameters;
    }
//      public BinaryInteractionParameter getBinaryParameters() {
//	return null;
//    }



    /**
     * @param interactionParameters the interactionParameters to set
     */
    public void setBinaryParameters(InteractionParameter interactionParameters) {
        InteractionParameter oldInteractionParameters = this.binaryParameters;
        this.binaryParameters = interactionParameters;
        mpcs.firePropertyChange("interactionParameters", oldInteractionParameters, interactionParameters);
    }

    private void setFraction(Substance component, double i) {
        component.setMolarFraction(i);
	//molarFractions.put(component.getComponent().getName(), i);
    }

    public void setFraction(Compound component, Double fraction) {
        getPureSubstance(component).setMolarFraction(fraction);
        //molarFractions.put(component.getName(), fraction);

    }
    
    
    public void setFractions(HashMap<Compound,Double> fractions){
	for(Compound comp: fractions.keySet()){
          //  molarFractions = fractions;
            getPureSubstance(comp).setMolarFraction(fractions.get(comp));
            //molarFractions.put(comp.getName(), fractions.get(comp));
        }
    }

    @Override
    public double calculatetAcentricFactorBasedVaporPressure() {
	double result = 0;
	for( Substance component : pureSubstances){
	    double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	    result += vaporP * component.getMolarFraction();//molarFractions.get(component.getComponent().getName());  
	}
	return result;
    }

    /**
     * @return the alpha
     */
    public Alpha getAlpha() {
        return alpha;
    }
    public void setAlpha(Alpha alpha){
        Alpha oldAlpha = this.alpha;
        this.alpha = alpha;
        mpcs.firePropertyChange("alpha", oldAlpha, alpha);
    }


}

