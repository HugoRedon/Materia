package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.mixingRule.MixingRule;
import termo.equilibrium.EquilibriaSolution;
import termo.equilibrium.MixtureEquilibriaPhaseSolution;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MixtureSubstance extends Substance{
    private MixingRule mixingRule;
    private ArrayList<PureSubstance> pureSubstances = new ArrayList<>();
    private HashMap<PureSubstance,Double> molarFractions = new HashMap<>();
    private InteractionParameter binaryParameters = new InteractionParameter();
    
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
        return getMixingRule().a(temperature, molarFractions, binaryParameters);
    }
    
     @Override
    public double calculate_b_cubicParameter() {       
        return getMixingRule().b(single_bs(), getComponents(), getFractions());
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
               pureSubstance, 
               molarFractions,
	       binaryParameters);
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

    @Override
    public EquilibriaSolution bubbleTemperatureEstimate(double pressure) {
	//return 204.911544;
	  double temperature =  300;
      
      double error = 100;
      double deltaT =1;
      
      double tol = 1e-4;
      
      
      HashMap<PureSubstance,Double> vaporFractions  = new HashMap<>();
      
      int iterations =0;
      while (error >tol  && iterations < 1000){
          iterations++;
	    double T_  = temperature + deltaT;
            double vaporPressure = calculateVaporPressure(temperature);
            double vaporPressure_ = calculateVaporPressure(T_);
           error = Math.log(vaporPressure / pressure);
           double error_ = Math.log(vaporPressure_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      for(PureSubstance component: getPureSubstances()){
          double vp = component.getAcentricFactorBasedVaporPressure(temperature);
          double yi = vp * molarFractions.get(component) / pressure;
          vaporFractions.put(component, yi);
      }
      return new MixtureEquilibriaPhaseSolution(temperature, pressure,molarFractions,vaporFractions, iterations);
	//return temperature;
    }
    public double calculateVaporPressure(double temperature){
	double vaporPressure = 0;
	 for (PureSubstance component : getPureSubstances() ){
               vaporPressure += component.getAcentricFactorBasedVaporPressure(temperature)*molarFractions.get(component);     
           }
	 
	 return vaporPressure;
    }
    
    
    
    @Override
    public MixtureEquilibriaPhaseSolution bubbleTemperature(double pressure) {
	
	 HashMap<PureSubstance,Double> K;
	  HashMap<PureSubstance,Double> vaporFractions = new HashMap();
        double sy;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<PureSubstance,Double> k_;
        double Sy_;
        double e_; 
	
	
	double temperature = bubbleTemperatureEstimate(pressure).getTemperature();
//        if(components.size() == 1){
//            liquidFractions = new HashMap<>();
//            liquidFractions.put(components.get(0), 1.0);
//            vaporFractions = new HashMap<>();
//            vaporFractions.put(components.get(0), 1.0);
//        }
	
	double tolerance = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count < 1000){
            K = equilibriumRelations(temperature, pressure) ;//equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sy = calculateSy(K);//, liquidFractions, components);
            e = Math.log(sy);
            T_ = temperature + deltaT;
            k_ = equilibriumRelations(T_, pressure);//equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            Sy_ = calculateSy(k_);//, liquidFractions, components);
            e_ = Math.log(Sy_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
           // K = equilibriumRelations(temperature, pressure);//equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
           // sy = calculateSy(K, liquidFractions, components);
            vaporFractions = calculateNewYFractions(K, sy);
        }
        return new MixtureEquilibriaPhaseSolution(temperature,pressure, (HashMap<PureSubstance,Double>)molarFractions.clone(),vaporFractions, count); 
	
	
    }

    
     public  HashMap<PureSubstance,Double> calculateNewYFractions(
	     HashMap<PureSubstance,Double> equilibriumRelations, 
	     double s){
         HashMap<PureSubstance,Double> newFractions = new  HashMap<>();
        for (PureSubstance aComponent: pureSubstances){
            double ki = equilibriumRelations.get(aComponent);
            double x = molarFractions.get(aComponent);
            newFractions.put(aComponent, ki * x / s);
        }
        
        return newFractions;
    }
    
      @Override
    public MixtureEquilibriaPhaseSolution dewPressure(double temperature) {
	MixtureEquilibriaFunction function = new DewPressureFunctions();
	double pressureEstimate =  dewPressureEstimate(temperature).getPressure();
	return minimizePressure(function, temperature, temperature);
    }
      
      private MixtureEquilibriaPhaseSolution minimizePressure(MixtureEquilibriaFunction function,double temperature, double pressureEstimate){
	  HashMap<PureSubstance,Double> K = new HashMap();
	double deltaP = 0.0001;
	double e = 100;
	double tolerance = 1e-4;
    
	double pressure =pressureEstimate;
	int count = 0;
	while(Math.abs(e) > tolerance && count < 1000 ){         
	    count++;
	    K =   equilibriumRelations(temperature, pressure);
	    e = function.errorFunction(K);
	    double pressure_ = pressure * (1 + deltaP);
	    double e_ = function.errorFunction(equilibriumRelations(temperature, pressure_));
	    pressure = function.newPressureFunction(pressure, pressure_, e, e_);
	}    
      double sx = calculateSx(K);
	HashMap<PureSubstance,Double> liquidFractions = calculateNewXFractions(K, sx);
      return new MixtureEquilibriaPhaseSolution(temperature,pressure,molarFractions,liquidFractions  , count);
      }
      
 
      interface MixtureEquilibriaFunction{
	  public double errorFunction(HashMap<PureSubstance,Double > equilibriumRelations);
	  public double newPressureFunction(double pressure, double pressure_, double e, double e_);
      }
      
    class DewPressureFunctions implements MixtureEquilibriaFunction{
	@Override
        public double errorFunction(HashMap<PureSubstance,Double> equilibriumRelations ){
	double sx = calculateSx(equilibriumRelations);
	return sx -1;
    }
	@Override
	public double newPressureFunction(double pressure,double pressure_, double e, double e_){
	return  pressure - e * (pressure_ - pressure)/ (e_ - e);
    }
    }
    
     class BubblePressureFunctions implements MixtureEquilibriaFunction{
	@Override
	public double errorFunction(HashMap<PureSubstance,Double> equlibriumRelations){
	double sy = calculateSy(equlibriumRelations);//, liquidFractions, components);
	return sy -1;
    }
	@Override
	public double newPressureFunction(double pressure, double pressure_, double e , double e_){
	    return ((pressure * pressure_ )* (e_ - e)) / ((pressure_ * e_) - (pressure * e));   
	}
	
    }
    @Override
    public MixtureEquilibriaPhaseSolution bubblePressure(double temperature) {
	BubblePressureFunctions function = new BubblePressureFunctions();
	
	double pressureEstimate = bubblePressureEstimate(temperature);
	return minimizePressure(function, temperature, pressureEstimate);
	
//	HashMap<PureSubstance,Double> vaporFractions ;
//	HashMap<PureSubstance,Double> k = new HashMap() ;
//	double deltaP = 0.0001;
//	double e = 100;
//	
//	double pressure = bubblePressureEstimate(temperature);
//	double tolerance = 1e-4;
//	int count = 0;
//	while(Math.abs(e) > tolerance && count < 1000 ){         
//	    count++;
//	    k = equilibriumRelations(temperature, pressure);
//	    e = function.errorFunction(k);
//	    double pressure_ = pressure * (1 + deltaP); 
//	    double e_ = function.errorFunction(equilibriumRelations(temperature, pressure_));
//	    pressure =    function.newPressureFunction(pressure, pressure_, e, e_);
//	}  
//	double sy = calculateSy(k);
//	vaporFractions = calculateNewYFractions(k, sy);       
//	return new MixtureEquilibriaPhaseSolution(temperature,pressure,molarFractions, vaporFractions, count);
	
    }
   
    @Override
    public double bubblePressureEstimate(double temperature) {
	  HashMap<Component,Double> vaporPressures = new HashMap<>();
      double pressure= 0;
      int  iterations = 0;
      for( PureSubstance component : getPureSubstances()){
          double vaporP =  component.getAcentricFactorBasedVaporPressure(temperature);
          //vaporPressures.put(component, vaporP);
          pressure += vaporP * molarFractions.get(component);  
      }
     // HashMap<Component,Double>  vaporFractions = EquilibriumFunctions.getVaporFractionsRaoultsLaw(pressure, liquidFractions, vaporPressures);
     // return new EquilibriaSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);   
      return pressure;
    }

    @Override
    public EquilibriaSolution dewTemperature(double pressure) {
	
        HashMap<PureSubstance,Double> K;
	HashMap<PureSubstance,Double> liquidFractions = new HashMap<>();;
        double sx;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<PureSubstance,Double> k_;
        double sx_;
        double e_;
        
	double temperature = dewTemperatureEstimate(pressure).getTemperature();

        double tolerance  = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count < 1000){
            K = equilibriumRelations(temperature, pressure); 
            sx = calculateSx(K);
		    
            e = Math.log(sx);
            T_ = temperature + deltaT;
            k_ = equilibriumRelations(T_, pressure);//equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx_ = calculateSx(k_);//, vaporFractions, components);
            e_ = Math.log(sx_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
           // K = equilibriumRelations(temperature, pressure);//equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            //sx = calculateSx(K);//, vaporFractions, components);
            //calculateNewXFractions(K, vaporFractions, components, sx);
        }
        return new MixtureEquilibriaPhaseSolution(temperature,pressure, molarFractions,liquidFractions, count);
    }

    @Override
    public MixtureEquilibriaPhaseSolution dewTemperatureEstimate(double pressure) {
	      double temperature =  300;
      double calcPressure;
      double error = 100;
      double deltaT =1;
      double T_;
      double tol = 1e-4;
      double calcP_;
      double error_;  
      
      double denominator_;
      double denominator;
      
      HashMap<PureSubstance,Double> liquidFractions  = new HashMap<>();
      
      int iterations =0;
      while (Math.abs(error) >tol && iterations < 1000 ){
          iterations++;
            calcPressure = 0;
            calcP_ = 0;
            denominator = 0;
            denominator_ = 0;
            
            T_  = temperature + deltaT;
           for (PureSubstance component : molarFractions.keySet() ){
               double vaporPressure =component.getAcentricFactorBasedVaporPressure(temperature);
               denominator += molarFractions.get(component) / vaporPressure;
               
               double vaporPressure_ =component.getAcentricFactorBasedVaporPressure(T_);
               denominator_ += molarFractions.get(component) / vaporPressure_;
           }
           
           calcPressure = 1/denominator;
           calcP_ = 1/ denominator_;
           
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      for(PureSubstance component: molarFractions.keySet()){
          double vp = component.getAcentricFactorBasedVaporPressure( temperature);
          double xi =  pressure *molarFractions.get(component) /vp ;
          liquidFractions.put(component, xi);
      }
      return new MixtureEquilibriaPhaseSolution( temperature, pressure,liquidFractions,molarFractions, iterations);
    }

    @Override
    public MixtureEquilibriaPhaseSolution dewPressureEstimate(double temperature) {
	//return 12.2533971*101325;
	   HashMap<PureSubstance,Double> vaporPressures = new HashMap<>();
      int  iterations = 0;
      double denominator=0;
      for( PureSubstance component : molarFractions.keySet()){
            double vaporP =  component.getAcentricFactorBasedVaporPressure(temperature);
            vaporPressures.put(component, vaporP);
            denominator += molarFractions.get(component) / vaporP;
      }
     double pressure = 1/denominator;
     HashMap<PureSubstance,Double> liquidFractions = getLiquidFractionsRaoultsLaw(pressure, molarFractions, vaporPressures);
      return new MixtureEquilibriaPhaseSolution(temperature,pressure, liquidFractions,molarFractions, iterations);
    }

  
    
    
    private HashMap<PureSubstance,Double> calculateNewXFractions(
	    HashMap<PureSubstance,Double> equilibriumRelations, 
	    double s){
         HashMap<PureSubstance,Double> newFractions = new  HashMap<>();
        for (PureSubstance aComponent: pureSubstances){
            double ki = equilibriumRelations.get(aComponent);
            double y = molarFractions.get(aComponent);
            newFractions.put(aComponent,   y / (ki*s));
        }
        
        return newFractions;
    }
    

    public  HashMap<PureSubstance,Double> equilibriumRelations (
	    double temperature,
	    double pressure
            ){
         HashMap<PureSubstance,Double> equilibriumRelations  = new HashMap<>();
         
         for (PureSubstance aComponent : getPureSubstances()){
           
           double liquidFug = calculateFugacity(aComponent,temperature,pressure,Phase.LIQUID);
           double vaporFug = calculateFugacity(aComponent, temperature, pressure, Phase.VAPOR);     
           double equilRel = liquidFug/ vaporFug;
           equilibriumRelations.put(aComponent, equilRel);
         }
         return equilibriumRelations;
    }
    public  double calculateSx(
	    HashMap<PureSubstance,Double> equilibriumRelations){
        
         double s = 0;
        for (PureSubstance aComponent : getPureSubstances()){
              double equilRel = equilibriumRelations.get(aComponent);
           s +=  molarFractions.get(aComponent)/equilRel;
            
        }
        
        return s;
    }
    
    public double calculateSy(HashMap<PureSubstance,Double> equilibriumRelations){
        
         double s = 0;
        for (PureSubstance aComponent : molarFractions.keySet()){
              double equilRel = equilibriumRelations.get(aComponent);
           s += equilRel * molarFractions.get(aComponent);
        }
        
        return s;
    }
    
    public static HashMap<PureSubstance,Double> getLiquidFractionsRaoultsLaw(double pressure,
        HashMap<PureSubstance,Double> vaporFractions,
        HashMap<PureSubstance,Double> vaporPressures){
    
        HashMap<PureSubstance,Double> liquidFractions = new HashMap<>();
        
        for( PureSubstance component : vaporFractions.keySet()){
            double x =  vaporFractions.get(component)*pressure/vaporPressures.get(component) ;
            liquidFractions.put(component, x);  
        }   
        return liquidFractions;
}

    public void setFraction(PureSubstance component, double i) {
	molarFractions.put(component, i);
    }
}

