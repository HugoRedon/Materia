package termo.substance;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
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
public class MixtureSubstance extends HomogeneousSubstance{
    private MixingRule mixingRule;
    protected ArrayList<PureSubstance> pureSubstances = new ArrayList<>();
//    protected HashMap<String,Double> molarFractions = new HashMap<>();
    protected InteractionParameter binaryParameters = new InteractionParameter();

    private Alpha alpha;
    
    public MixtureSubstance(){
        
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
                setComponents((ArrayList<Component>)evt.getNewValue());
                break;
          
            case"mixingRule":
                setMixingRule((MixingRule)evt.getNewValue());
                break;
        }
    }

    public MixtureSubstance(Cubic equationOfState,Alpha alpha,  ArrayList<Component> components,Phase phase,MixingRule mixingRule ,InteractionParameter k){
	super(equationOfState,phase);
	this.mixingRule = mixingRule;
        this.alpha = alpha;
        
        setComponents(components);
	this.binaryParameters = k;
    }
     


    public void setComponents(ArrayList<Component> components){
        pureSubstances.clear();
        //molarFractions.clear();
        double fraction = 1d/components.size();
        for(Component component : components){ //implement property change listener
            PureSubstance pure = new PureSubstance(super.getCubicEquationOfState(), alpha, component, super.getPhase());
            pure.setMolarFraction(fraction);
            mpcs.addPropertyChangeListener(pure);
            pureSubstances.add(pure);
           
            //setFraction(pure, fraction);
        }
    }

    
    public PureSubstance getPureSubstance(Component component){
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

    
    public void addComponent(PureSubstance pureSubstance, double molarFraction){
        pureSubstance.setMolarFraction(molarFraction);
        //pureSubstance.setCubicEquationOfState(getCubicEquationOfState());
        mpcs.addPropertyChangeListener(pureSubstance);
        getPureSubstances().add(pureSubstance);
//        getMolarFractions().put(pureSubstance.getComponent().getName(), molarFraction);
    }
    public void removeComponent(PureSubstance pureSubstance){
        mpcs.removePropertyChangeListener(pureSubstance);
      	getPureSubstances().remove(pureSubstance);
//      	getMolarFractions().remove(pureSubstance.getComponent().getName());
    }


    @Override
    public double temperatureParcial_a() {
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
            double molarFraction = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            fractions.put(component, molarFraction);
        }
        return fractions;
    }

    @Override
    public double calculateIdealGasEnthalpy() {
        double idealGasEnthalpy = 0;
        for(PureSubstance pureSubstance: getPureSubstances()){
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
        
        for(PureSubstance pureSubstance: getPureSubstances()){
            double xi = pureSubstance.getMolarFraction();//getMolarFractions().get(pureSubstance.getComponent().getName());
            double entropyFori = pureSubstance.calculateIdealGasEntropy();
            
            term1 += xi * entropyFori;
            
            term2 += xi * Math.log(xi);
            
        }
        
        return term1  - Constants.R * term2;
    }

    @Override
    public double oneOver_N_Parcial_a(PureSubstance pureSubstance) {
       return getMixingRule().oneOverNParcial_aN2RespectN(
               pureSubstance, 
               this);
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
    public ArrayList<PureSubstance> getPureSubstances() {
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
    public double getFraction(PureSubstance pure){
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
     * @param binaryParameters the binaryParameters to set
     */
    public void setBinaryParameters(InteractionParameter binaryParameters) {
	this.binaryParameters = binaryParameters;
    }

    

    private void setFraction(PureSubstance component, double i) {
        component.setMolarFraction(i);
	//molarFractions.put(component.getComponent().getName(), i);
    }

    public void setFraction(Component component, Double fraction) {
        getPureSubstance(component).setMolarFraction(fraction);
        //molarFractions.put(component.getName(), fraction);

    }
    
    
    public void setFractions(HashMap<Component,Double> fractions){
	for(Component comp: fractions.keySet()){
          //  molarFractions = fractions;
            getPureSubstance(comp).setMolarFraction(fractions.get(comp));
            //molarFractions.put(comp.getName(), fractions.get(comp));
        }
    }

    @Override
    public double calculatetAcentricFactorBasedVaporPressure() {
	double result = 0;
	for( PureSubstance component : pureSubstances){
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

