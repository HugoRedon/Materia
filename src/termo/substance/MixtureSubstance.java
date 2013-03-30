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
        pureSubstances.add(pureSubstance);
        molarFractions.put(pureSubstance, molarFraction);
    }
    public void removeComponent(PureSubstance pureSubstance){
      pureSubstances.remove(pureSubstance);
      molarFractions.remove(pureSubstance);
    }
    @Override
    public void setCubicEquationOfState(Cubic cubic){
        super.setCubicEquationOfState(cubic);
        for (PureSubstance pureSubstance: pureSubstances){
            pureSubstance.setCubicEquationOfState(cubic);
        }
    }

    public HashMap<Component,Double> single_as(double aTemperature){
        HashMap<Component, Double> singleAs = new HashMap<>();
       
        for(PureSubstance pureSubstance : pureSubstances){
            Component component = pureSubstance.getComponent();
            double a = pureSubstance.calculate_a_cubicParameter(aTemperature);
            singleAs.put(component, a);
        }
        return singleAs;
    }
    public HashMap<Component,Double> single_bs(){
         HashMap<Component,Double> singleBs = new HashMap<>();
           for(PureSubstance pureSubstance : pureSubstances){
            Component component = pureSubstance.getComponent();
            double b = pureSubstance.calculate_b_cubicParameter();
            singleBs.put(component,b);
        }
          return singleBs;
    }
    
    public HashMap<Component,Double> alphaDerivatives(double temperature){
        HashMap<Component,Double> alphaDerivatives = new HashMap<>();
        for(PureSubstance pureSubstance: pureSubstances){
            Component component = pureSubstance.getComponent();
            double alphaDerivative = pureSubstance.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
            
            alphaDerivatives.put(component, alphaDerivative);
        }
        return alphaDerivatives;
    }
    
    @Override
    public double temperatureParcial_a(double temperature) {
        return mixingRule.temperatureParcial_a(getComponents(),getFractions(),single_as(temperature), alphaDerivatives(temperature), binaryParameters);
    }

    @Override
    public double calculate_a_cubicParameter(double temperature) {
        return mixingRule.a(temperature, single_as(temperature), single_bs(), getComponents(), getFractions(), binaryParameters);
    }
    public ArrayList<Component> getComponents(){
        ArrayList<Component> components = new ArrayList<>();
        for (PureSubstance pureSubstance : pureSubstances){
            components.add(pureSubstance.getComponent());
        }
        return components;
    }
    public HashMap<Component,Double> getFractions(){
        HashMap<Component,Double> fractions = new HashMap<>();
        for (PureSubstance pureSubstance : pureSubstances){
            Component component = pureSubstance.getComponent();
            double molarFraction = molarFractions.get(pureSubstance);
            fractions.put(component, molarFraction);
        }
        return fractions;
    }

    @Override
    public double calculateIdealGasEnthalpy(double temperature) {
        double idealGasEnthalpy = 0;
        for(PureSubstance pureSubstance: pureSubstances){
            double xi = molarFractions.get(pureSubstance);
            double idealGasEnthalpyFori = pureSubstance.calculateIdealGasEnthalpy(temperature);
            
            idealGasEnthalpy += xi *idealGasEnthalpyFori;
        }
        return idealGasEnthalpy;
    }

    @Override
    public double calculate_b_cubicParameter() {
        double b =0;
        
        for(PureSubstance pureSubstance: pureSubstances){
            double xi = molarFractions.get(pureSubstance);
            b+= xi * pureSubstance.calculate_b_cubicParameter();
        }
        
        return b;
    }

    @Override
    public double calculateIdealGasEntropy(double temperature, double pressure) {
           
           
           double term1 = 0;
           double term2 = 0;
        
        for(PureSubstance pureSubstance: pureSubstances){
            double xi = molarFractions.get(pureSubstance);
            double entropyFori = pureSubstance.calculateIdealGasEntropy(temperature, pressure);
            
            term1 += xi * entropyFori;
            
            term2 += xi * Math.log(xi);
            
        }
        
        return term1  - Constants.R * term2;
    }

    @Override
    public double oneOver_N_Parcial_a(double temperature,PureSubstance pureSubstance) {
        Component component = pureSubstance.getComponent();
       return mixingRule.oneOverNParcial_aN2RespectN(temperature, single_as(temperature), single_bs(), getComponents(), component, getFractions(), binaryParameters);
    }


    
    
}
