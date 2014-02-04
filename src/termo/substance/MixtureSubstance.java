package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.mixingRule.MixingRule;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MixtureSubstance extends Substance{
    private MixingRule mixingRule;
    private ArrayList<PureSubstance> pureSubstances = new ArrayList<>();
    private HashMap<PureSubstance,Double> molarFractions = new HashMap<>();
    private BinaryInteractionParameter binaryParameters;
    
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
            double a = pureSubstance.calculate_a_cubicParameter(aTemperature);
            singleAs.put(component, a);
        }
        return singleAs;
    }
    public HashMap<Component,Double> single_bs(){
         HashMap<Component,Double> singleBs = new HashMap<>();
           for(PureSubstance pureSubstance : getPureSubstances()){
            Component component = pureSubstance.getComponent();
            double b = pureSubstance.calculate_b_cubicParameter();
            singleBs.put(component,b);
        }
          return singleBs;
    }
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
    
    @Override
    public double temperatureParcial_a(double temperature) {
        return getMixingRule().temperatureParcial_a(
                temperature,
                getComponents(),
                getFractions(),
                single_as(temperature), 
                single_bs(),
                alphaDerivatives(temperature), getBinaryParameters());
    }

    @Override
    public double calculate_a_cubicParameter(double temperature) {
        return getMixingRule().a(temperature, single_as(temperature), single_bs(), getComponents(), getFractions(), getBinaryParameters());
    }
    public ArrayList<Component> getComponents(){
        ArrayList<Component> components = new ArrayList<>();
        for (PureSubstance pureSubstance : getPureSubstances()){
            components.add(pureSubstance.getComponent());
        }
        return components;
    }
    public HashMap<Component,Double> getFractions(){
        HashMap<Component,Double> fractions = new HashMap<>();
        for (PureSubstance pureSubstance : getPureSubstances()){
            Component component = pureSubstance.getComponent();
            double molarFraction = getMolarFractions().get(pureSubstance);
            fractions.put(component, molarFraction);
        }
        return fractions;
    }

    @Override
    public double calculateIdealGasEnthalpy(double temperature) {
        double idealGasEnthalpy = 0;
        for(PureSubstance pureSubstance: getPureSubstances()){
            double xi = getMolarFractions().get(pureSubstance);
            double idealGasEnthalpyFori = pureSubstance.calculateIdealGasEnthalpy(temperature);
            
            idealGasEnthalpy += xi *idealGasEnthalpyFori;
        }
        return idealGasEnthalpy;
    }

    @Override
    public double calculate_b_cubicParameter() {       
        return getMixingRule().b(single_bs(), getComponents(), getFractions());
    }

    @Override
    public double calculateIdealGasEntropy(double temperature, double pressure) {

           double term1 = 0;
           double term2 = 0;
        
        for(PureSubstance pureSubstance: getPureSubstances()){
            double xi = getMolarFractions().get(pureSubstance);
            double entropyFori = pureSubstance.calculateIdealGasEntropy(temperature, pressure);
            
            term1 += xi * entropyFori;
            
            term2 += xi * Math.log(xi);
            
        }
        
        return term1  - Constants.R * term2;
    }

    @Override
    public double oneOver_N_Parcial_a(double temperature,PureSubstance pureSubstance) {
        Component component = pureSubstance.getComponent();
       return getMixingRule().oneOverNParcial_aN2RespectN(
               temperature, 
               single_as(temperature),
               single_bs(), 
               single_Alphas(temperature),
               getComponents(), 
               component, 
               getFractions(), getBinaryParameters());
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
	this.mixingRule = mixingRule;
    }

    /**
     * @return the pureSubstances
     */
    public ArrayList<PureSubstance> getPureSubstances() {
	return pureSubstances;
    }

    /**
     * @param pureSubstances the pureSubstances to set
     */
    public void setPureSubstances(ArrayList<PureSubstance> pureSubstances) {
	this.pureSubstances = pureSubstances;
    }

    /**
     * @return the molarFractions
     */
    public HashMap<PureSubstance,Double> getMolarFractions() {
	return molarFractions;
    }

    /**
     * @param molarFractions the molarFractions to set
     */
    public void setMolarFractions(HashMap<PureSubstance,Double> molarFractions) {
	this.molarFractions = molarFractions;
    }

    /**
     * @return the binaryParameters
     */
    public BinaryInteractionParameter getBinaryParameters() {
	return binaryParameters;
    }

    /**
     * @param binaryParameters the binaryParameters to set
     */
    public void setBinaryParameters(BinaryInteractionParameter binaryParameters) {
	this.binaryParameters = binaryParameters;
    }

    @Override
    public double bubbleTemperature(Double pressure) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
