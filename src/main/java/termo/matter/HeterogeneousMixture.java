
package termo.matter;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.optimization.NewtonMethodSolver;
import termo.optimization.errorfunctions.TemperatureErrorFunction;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public final class HeterogeneousMixture extends Heterogeneous {
    private Cubic equationOfState;
    private Alpha alpha;
    private MixingRule mixingRule;
    
    private InteractionParameter interactionParameters;
    
     private HashMap<String,Double> zFractions = new HashMap(); 
     private HashSet<Component> components;
     
     private NewtonMethodSolver optimizer;
    
    public HeterogeneousMixture(){
         liquid = new Mixture();
        liquid.setPhase(Phase.LIQUID);
        vapor = new Mixture();
        vapor.setPhase(Phase.VAPOR);
        
        mpcs.addPropertyChangeListener(liquid);
        mpcs.addPropertyChangeListener(vapor);
        
        
        
        
       
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt); 
        String name =evt.getPropertyName();
        switch(name){
            case "components":
                components = (HashSet<Component> )evt.getNewValue();
                break;
            case "zFractions":
                zFractions = (HashMap<String,Double>)evt.getNewValue();
                break;
        }
        
    }
   
    public HeterogeneousMixture(
	    Cubic eos,
	    Alpha alpha,
	    MixingRule mixingrule, 
	    HashSet<Component> components, InteractionParameter k){
        this();
        setEquationOfState(eos);
        setAlpha(alpha);
        setComponents(components);
        setMixingRule(mixingrule);
        setInteractionParameters(k);
        
        TemperatureErrorFunction errorFunction = new TemperatureErrorFunction(this);
         optimizer = new NewtonMethodSolver(errorFunction);
    }
    
    
    
    @Override
    public int bubbleTemperatureEstimate() {
	copyZfractionsToliquid();
	double temp =  300;
	double error = 100;
	double deltaT =1;      
	double tol = 1e-4;

	int iterations =0;
	while (error >tol  && iterations < 1000){
	    iterations++;
	      double T_  = temp + deltaT;
	      double vaporPressure = calculateVaporPressure(temp);
	      double vaporPressure_ = calculateVaporPressure(T_);
	     error = Math.log(vaporPressure / pressure);
	     double error_ = Math.log(vaporPressure_ / pressure);
	     temp = (temp * T_ *(error_ - error)) / (T_ * error_ - temp * error);
	} 
	setTemperature(temp);
	for(Substance component: getVapor().getPureSubstances()){
	    double vp = component.calculatetAcentricFactorBasedVaporPressure();
	    double yi = vp * getLiquid().getFraction(component) / pressure;
	    getVapor().setFraction(component.getComponent(), yi);
	}
	return iterations;
    }

    @Override
    public int bubbleTemperature(){
        bubbleTemperatureEstimate();
        return bubbleTemperature(temperature);
    }
    
    public int bubbleTemperature(double estimate) {

	 HashMap<Component,Double> K;
	double e = 100;
	double deltaT = 1;

	 
	 
	 double temp = estimate;
	 
	double tolerance = 1e-4;
	int count = 0;
	while(Math.abs(e) >= tolerance && count < 1000){
	    setTemperature(temp);
	    K = equilibriumRelations() ;
	    double sy = calculateSy(K);
	    e = Math.log(sy);
	    double T_ = temp + deltaT;
	    setTemperature(T_);
	    HashMap<Component, Double> k_ = equilibriumRelations();
	    double Sy_ = calculateSy(k_);
	    double e_ = Math.log(Sy_);
	    temp = temp * T_ * (e_ - e) / (T_ * e_ - temp * e);
	    calculateNewYFractions(K, sy);
	}
	
	setTemperature(temp);
	return count;
	//return new MixtureEquilibriaPhaseSolution(temperature, pressure, (HashMap<Component,Double>)getLiquid().getReadOnlyFractions().clone(), (HashMap<Component,Double>)getVapor().getReadOnlyFractions().clone(), count);
    }

    @Override
    public void bubblePressureEstimate() {
	
	copyZfractionsToliquid();

	  HashMap<Substance,Double> vaporPressures = new HashMap<>();
      double p =0;
      int  iterations = 0;
      setTemperature(temperature);
      for( Substance component : getLiquid().getPureSubstances()){
	  double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	  vaporPressures.put(component, vaporP);
	  p += vaporP * getLiquid().getFraction(component);  
      }
      setPressure(p);
      setVaporFractionsRaoultsLaw( vaporPressures);
     // return new EquilibriaSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);   
      
    }

    @Override
    public int bubblePressureImpl() {
	BubblePressureFunctions function = new BubblePressureFunctions();
	return minimizePressure(function, temperature,Phase.VAPOR);

    }
    
    
    

    @Override
    public int dewTemperatureEstimate() {
	
	copyZfractionsToVapor();
	
	double temp =  300;
      double calcPressure;
      double error = 100;
      double deltaT =1;
      double T_;
      double tol = 1e-4;
      double calcP_;
      double error_;  
      
      double denominator_;
      double denominator;
      
      
      HashMap<Substance,Double> vaporPressures = new HashMap();
      int iterations =0;
      while (Math.abs(error) >tol && iterations < 1000 ){
          iterations++;
            calcPressure = 0;
            calcP_ = 0;
            denominator = 0;
            denominator_ = 0;
            
            T_  = temp + deltaT;
           for (Substance component : getVapor().getPureSubstances() ){
	       setTemperature(temp);
               double vaporPressure =component.calculatetAcentricFactorBasedVaporPressure();
	       vaporPressures.put(component, vaporPressure);
               denominator += getVapor().getFraction(component) / vaporPressure;
               setTemperature(T_);
               double vaporPressure_ =component.calculatetAcentricFactorBasedVaporPressure();
               denominator_ += getVapor().getFraction(component) / vaporPressure_;
           }
           
           calcPressure = 1/denominator;
           calcP_ = 1/ denominator_;
           
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temp = (temp * T_ *(error_ - error)) / (T_ * error_ - temp * error);
      } 
      
      setTemperature(temp);
      setLiquidFractionsRaoultsLaw(vaporPressures);
      return iterations;
    }
    
    @Override
    public int dewTemperature() {
	
        HashMap<Component,Double> K;
	
        double sx;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<Component,Double> k_;
        double sx_;
        double e_;
        
	 dewTemperatureEstimate();
	 
	 double temp = temperature;

        double tolerance  = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count < 1000){
	    setTemperature(temp);
            K = equilibriumRelations(); 
            sx = calculateSx(K);
		    
            e = Math.log(sx);
            T_ = temp + deltaT;
	    setTemperature(T_);
            k_ = equilibriumRelations();//equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx_ = calculateSx(k_);//, vaporFractions, components);
            e_ = Math.log(sx_);
            temp = temp * T_ * (e_ - e) / (T_ * e_ - temp * e);
           // K = equilibriumRelations(temperature, pressure);//equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            //sx = calculateSx(K);//, vaporFractions, components);
	    
            calculateNewXFractions(K, sx);
        }
	setTemperature(temp);
        return count; //new MixtureEquilibriaPhaseSolution(temperature,pressure, molarFractions,liquidFractions, count);
    }

    @Override
    public void dewPressureEstimate() {
	
	copyZfractionsToVapor();
	
	
	
	HashMap<Substance,Double> vaporPressures = new HashMap<>();
	int  iterations = 0;
	double denominator=0;
	//setTemperature(temperature);
	for( Substance component : getVapor().getPureSubstances()){
	      double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	      vaporPressures.put(component, vaporP);
	      denominator += getVapor().getFraction(component) / vaporP;
	}
	
       setPressure( 1/denominator);
       setLiquidFractionsRaoultsLaw( vaporPressures);
       
      // HashMap<PureSubstance,Double> liquidFractions = getLiquidFractionsRaoultsLaw(pressure, molarFractions, vaporPressures);
	//return pressure;//new MixtureEquilibriaPhaseSolution(temperature,pressure, null,null, iterations);
    }

    @Override
    public int dewPressureImpl() {
	MixtureEquilibriaFunction function = new DewPressureFunctions();
	return minimizePressure(function, temperature, Phase.LIQUID);
    }
    
    public double flash(double temperature,double pressure){
	setTemperature(temperature);
	setPressure(pressure);
	
	
	double vF = flashEstimate(temperature, pressure);
	
	double tolerance  = 1e-4;
            HashMap<Component,Double> K;
            double error=100;
            HashMap<Component,Double> x_;

            HashMap<Component,Double> y_;
            while(error >= tolerance){
		setTemperature(temperature);
                K = equilibriumRelations();
                error = calculateError(pressure, temperature);
                vF = rachfordRice(K, vF,tolerance);
                x_=x_( K, vF);
		
		getLiquid().setFractions(newFractions(x_));
                
		y_ = y_(x_, K);
		getVapor().setFractions(newFractions(y_));
                
            }
        
        return  vF;
    }
    
    
    
//    public double flashEstimate(double temperature, double pressure){
//	double vF = 0.5;
//	double sx = 0;
//	double sy = 0;
//	HashMap<PureSubstance,Double> X = new HashMap<>();
//	HashMap<PureSubstance,Double> Y = new HashMap<>();
//	for (Substance component :vapor.getPureSubstances()){
//	    double vaporPressure = component.calculatetAcentricFactorBasedVaporPressure(temperature);
//	    
//	    double xi = zFractions.get(component.getComponent())*pressure / vaporPressure;
//	    X.put(component, xi);
//	    sx += xi;
//	}
//	
//	
//	for(Substance component : liquid.getPureSubstances()){
//	    double xi = X.get(component);
//	    double normXi = xi/sx;
//	    liquid.getMolarFractions().put(component, normXi);
//	}
//	
//	
//	
//	
//	
//	HashMap<PureSubstance,Double> k = new HashMap();
//	
//	for(Substance component: liquid.getPureSubstances()){
//	    double vaporPressure = component.calculatetAcentricFactorBasedVaporPressure(temperature);
//	    double ki =  vaporPressure/ pressure;
//	    k.put(component, ki);
//	}
//	
//
//	vF = rachfordRice(k, vF, 1e-4);
//	
//	
//	HashMap<PureSubstance, Double> y_ = y_(X, k);
//	vapor.setMolarFractions( newFractions(y_));
//	
//	//calculateSy(k);
//	
//	return vF;
//    }
    
    
    
     public double flashEstimate(double temperature,double pressure){
	double vF = 0.5;
	flashEstimateLiquidFractions(temperature, pressure);
	
	double tolerance  = 1e-4;
            HashMap<Component,Double> K;           
            HashMap<Component,Double> x_;

            HashMap<Component,Double> y_;
           
	    K = flashEstimateEquilibriumRelations(temperature, pressure);
	   // error = calculateError(pressure, temperature);
	    vF = rachfordRice(K, vF,tolerance);
	    x_=x_( K, vF);

	    getLiquid().setFractions(newFractions(x_));

	    y_ = y_(x_, K);
	    getVapor().setFractions(newFractions(y_));  
        return  vF;
    }
    
     
     public void flashEstimateLiquidFractions(double temperature, double pressure){
	double sx = 0;
	
	HashMap<Substance,Double> X = new HashMap<>();
	setTemperature(temperature);
	for (Substance component :getVapor().getPureSubstances()){
	    double vaporPressure = component.calculatetAcentricFactorBasedVaporPressure();
	    
	    double xi = getzFractions().get(component.getComponent().getName())*pressure / vaporPressure;
	    X.put(component, xi);
	    sx += xi;
	}
	
	
	for(Substance component : getLiquid().getPureSubstances()){
	    double xi = X.get(component);
	    double normXi = xi/sx;
	    getLiquid().setFraction(component.getComponent(), normXi);
	}
     }
     
     public HashMap<Component,Double> flashEstimateEquilibriumRelations(double temperature, double pressure){
	 HashMap<Component,Double> k = new HashMap();
	setTemperature(temperature);
	
	for(Substance pure: getLiquid().getPureSubstances()){
	    double vaporPressure = pure.calculatetAcentricFactorBasedVaporPressure();
	    double ki =  vaporPressure/ pressure;
	    k.put(pure.getComponent(), ki);
	}
	return k;
     }
    
    
    
    
    
    
    private double sumS(HashMap<Component,Double> x_){
	double s =0;
        for(Component component: components){
            s += x_.get(component);
        }
	return s;
    }
    
       
    private  HashMap<Component,Double> y_(HashMap<Component,Double> x_,
            HashMap<Component,Double> k){
        HashMap<Component,Double> y_ = new HashMap<>();
        
        for(Component component: components){
            double ki = k.get(component);
            double x_i = x_.get(component);
            y_.put(component, x_i * ki);
        }
        return y_;
    }
    
    private  HashMap<Component,Double> newFractions(HashMap<Component,Double> x_){
        HashMap<Component,Double> x = new HashMap<>();
        double s = sumS(x_);
        
        for(Component component: components){
            
            double x_i = x_.get(component);
            x.put(component, x_i /s);
        }
        
        return x;
    }
    
    private HashMap<Component,Double> x_(
            
            HashMap<Component,Double> k,
            double vF
            ){
        
        HashMap<Component,Double> x_ = new HashMap<>();
        
        for(Component component : components){
            double zi = getzFractions().get(component.getName());
            double ki = k.get(component);
            
            x_.put(component, zi / ( 1 + vF*(ki - 1)));
        }
        return x_;
    }
    
 
    
    
    
    
    
    
    
    private  double calculateError(double pressure,double temperature){//for flash
	double result=0;
	setTemperature(temperature);
	setPressure(pressure);
	for(Component component: getComponents()){
	    double xi = getLiquid().getReadOnlyFractions().get(component);
	    double yi = getVapor().getReadOnlyFractions().get(component);
	    double liquidFug = getLiquid().calculateFugacity(component);
	    double vaporFug = getVapor().calculateFugacity(component);
	    result += Math.abs(xi* liquidFug - yi * vaporFug    );
	}
	return result;
    }
    
    private  double rachfordRice(HashMap<Component,Double> k, 	 
	 double vF,
	 double tolerance
	 ){
        
        double s=100;
        double s_;
        while(s > tolerance){
            s = s(k, vF);
            s_ = s_(k, vF);
            vF = vF - (s /s_);
        }
        return vF;
    }
    
    
    
      private  double s(HashMap<Component,Double> k,double vF){
        
        double result =0;
        for(Component component :components){
            
            double zi = getzFractions().get(component.getName());
            double ki = k.get(component);
            
            result += (zi * (ki - 1 ))/( 1 + vF * ( ki - 1));
        }
        
        return result;
    }
    private double s_(HashMap<Component,Double> k,
              
            double vF
            ){
        double result =0;
        for(Component component : components){
            double zi = getzFractions().get(component.getName());
            double ki = k.get(component);
            result += (- zi * Math.pow(ki - 1,2))/(Math.pow(1 + vF * (ki-1),2));
        }
        
        return result;
    }
    
    
    
    
    
    
    
    
   public void setZFraction(Component component, double d) {
	getzFractions().put(component.getName(), d);
	getLiquid().setFraction(component, d);
	getVapor().setFraction(component, d);
    }
    

 

        

    
    
    
    
    
    public double calculateVaporPressure(double temperature){
	double vaporPressure = 0;
	setTemperature(temperature);
	 for (Substance component : getVapor().getPureSubstances() ){
               vaporPressure += component.calculatetAcentricFactorBasedVaporPressure()*getzFractions().get(component.getComponent().getName());     
           }
	 
	 return vaporPressure;
    }
  

     public  void calculateNewYFractions(
	     HashMap<Component,Double> equilibriumRelations, 
	     double s){
         
        for (Component aComponent: components){
            double ki = equilibriumRelations.get(aComponent);
            double x = getLiquid().getReadOnlyFractions().get(aComponent);
            getVapor().setFraction(aComponent, ki * x / s);
        }
        
        
    }
     
    
      
      private int minimizePressure(MixtureEquilibriaFunction function,double temperature,Phase aPhase){
	  HashMap<Component,Double> K;
	double deltaP = 0.0001;
	double e = 100;
	double tolerance = 1e-5;
    
	double p = pressure ;
	int count = 0;
	while(Math.abs(e) > tolerance && count < 10000 ){         
	    count++;
	    setPressure(p);
	    K =   equilibriumRelations();
	    e = function.errorFunction(K);
	    double pressure_ = p * (1 + deltaP);
	    setPressure(pressure_);
	    double e_ = function.errorFunction(equilibriumRelations());
	    p = function.newPressureFunction(p, pressure_, e, e_);
	    updateFractions(K, aPhase);
	}    
      
	setPressure(p);
	//HashMap<PureSubstance,Double> liquidFractions =new HashMap<>();//
      return count;
      //return pressure;//new MixtureEquilibriaPhaseSolution(temperature,pressure,null,liquidFractions  , count);
      }
      
      private void updateFractions(HashMap<Component , Double> K, Phase aPhase){
	  if(aPhase.equals(Phase.LIQUID)){
	    double sx = calculateSx(K);
	    calculateNewXFractions(K, sx);
	  }else{
	    double sy = calculateSy(K);
	    calculateNewYFractions(K, sy);
	  }
	  
      }
    private void calculateNewXFractions(
	    HashMap<Component,Double> equilibriumRelations, 
	    double s){
         HashMap<Component,Double> newFractions = new  HashMap<>();
        for (Component aComponent: components){
            double ki = equilibriumRelations.get(aComponent);
            double y = getVapor().getReadOnlyFractions().get(aComponent);
            getLiquid().setFraction(aComponent,   y / (ki*s));
        }
    }

    /**
     * @return the components
     */
    public HashSet<Component> getComponents() {
	return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(HashSet<Component> components) {
        HashSet<Component> oldComponents = this.components;
	this.components = components;
        mpcs.firePropertyChange("components", oldComponents, components);
    }

    /**
     * @return the vapor
     */
    @Override
    public Mixture getVapor() {
	return (Mixture)vapor;
    }

    /**
     * @param vapor the vapor to set
     */
    public void setVapor(Mixture vapor) {
	this.vapor = vapor;
    }

    /**
     * @return the liquid
     */
    @Override
    public Mixture getLiquid() {
	return (Mixture)liquid;
    }

    /**
     * @param liquid the liquid to set
     */
    public void setLiquid(Mixture liquid) {
	this.liquid = liquid;
    }

    /**
     * @return the zFractions
     */
    public HashMap<String,Double> getzFractions() {
	return zFractions;
    }

//    /**
//     * @param zFractions the zFractions to set
//     */
//    public void setzFractions(HashMap<String,Double> zFractions) {
//	this.zFractions = zFractions;
//	
//	getLiquid().setFractions(zFractions);
//	getVapor().setFractions(zFractions);
//    }

    /**
     * @return the equationOfState
     */
    public Cubic getEquationOfState() {
        return equationOfState;
    }

    /**
     * @param equationOfState the equationOfState to set
     */
    public void setEquationOfState(Cubic equationOfState) {
        Cubic oldEquation = this.equationOfState;
        this.equationOfState = equationOfState;
        mpcs.firePropertyChange("cubic", oldEquation, equationOfState);
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
        Alpha oldAlpha = this.alpha;
        this.alpha = alpha;
        mpcs.firePropertyChange("alpha", oldAlpha, alpha);
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
     * @return the interactionParameters
     */
    public InteractionParameter getInteractionParameters() {
        return interactionParameters;
    }
   
    /**
     * @param interactionParameters the interactionParameters to set
     */
  public void setInteractionParameters(InteractionParameter interactionParameters) {
        InteractionParameter oldInteractionParameters = this.interactionParameters;
        this.interactionParameters = interactionParameters;
        mpcs.firePropertyChange("interactionParameters", oldInteractionParameters, interactionParameters);
    }

 
    
      
 
      interface MixtureEquilibriaFunction{
	  public double errorFunction(HashMap<Component,Double > equilibriumRelations);
	  public double newPressureFunction(double pressure, double pressure_, double e, double e_);
      }
      
    class DewPressureFunctions implements MixtureEquilibriaFunction{
	@Override
        public double errorFunction(HashMap<Component,Double> equilibriumRelations ){
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
	public double errorFunction(HashMap<Component,Double> equlibriumRelations){
	double sy = calculateSy(equlibriumRelations);//, liquidFractions, components);
	return sy -1;
    }
	@Override
	public double newPressureFunction(double pressure, double pressure_, double e , double e_){
	    return ((pressure * pressure_ )* (e_ - e)) / ((pressure_ * e_) - (pressure * e));   
	}
	
    }
    
    
   
    private void copyZfractionsToliquid(){
	for (Component component:getComponents()){
	    getLiquid().setFraction(component, getzFractions().get(component.getName()));
	}
    }
    
   public void setVaporFractionsRaoultsLaw(
           HashMap<Substance,Double> vaporPressures){ 
      for( Substance component : getLiquid().getPureSubstances()){
          double y = vaporPressures.get(component) * getLiquid().getFraction(component)/pressure;
            getVapor().setFraction(component.getComponent(), y);  
      }
}

   
    
    
      public  void setLiquidFractionsRaoultsLaw(
        HashMap<Substance,Double> vaporPressures){
    
        for( Substance component : getVapor().getPureSubstances()){
            double x =  getVapor().getFraction(component)*pressure/vaporPressures.get(component) ;
            getLiquid().setFraction(component.getComponent(), x);  
        }   
      
    }

    private void copyZfractionsToVapor(){
	for (Component component:getComponents()){
	    getVapor().setFraction(component, getzFractions().get(component.getName()));
	}
    }
    
    
    
//  public Double equilibriumRelation(Component component, double temperature, double pressure) {
//	
//    }
//  
    
//    public Double equilibriumRelation(Substance pure, double temperature, double pressure){
//	double liquidFug = getLiquid().calculateFugacity(pure,temperature,pressure);
//           double vaporFug = getVapor().calculateFugacity(pure, temperature, pressure);     
//           return liquidFug/ vaporFug;
//    }
    public Double equilibriumRelation(Component component){
	double liquidFug = getLiquid().calculateFugacity(component);
	double vaporFug = getVapor().calculateFugacity(component);
	return liquidFug/vaporFug;
    }
    

    public  HashMap<Component,Double> equilibriumRelations ( ){
         HashMap<Component,Double> equilibriumRelations  = new HashMap<>();
         
         for (Component aComponent : components){
	    double equilRel = equilibriumRelation(aComponent);
           equilibriumRelations.put(aComponent, equilRel);
         }
         return equilibriumRelations;
    }
    public  double calculateSx(HashMap<Component,Double> equilibriumRelations){
        
         double s = 0;
        for (Component aComponent : components){
              double equilRel = equilibriumRelations.get(aComponent);
           s +=  getVapor().getReadOnlyFractions().get(aComponent)/equilRel;
            
        }
        
        return s;
    }
    
    public double calculateSy(HashMap<Component,Double> equilibriumRelations){
        
         double s = 0;
        for (Component aComponent : components){
              double equilRel = equilibriumRelations.get(aComponent);
           s += equilRel * getLiquid().getReadOnlyFractions().get(aComponent);
        }
        
        return s;
    }

    public NewtonMethodSolver getOptimizer() {
        return optimizer;
    }

    public void setOptimizer(NewtonMethodSolver optimizer) {
        this.optimizer = optimizer;
    }
 
}
