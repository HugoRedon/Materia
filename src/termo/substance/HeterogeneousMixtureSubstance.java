
package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.equilibrium.EquilibriaSolution;
import termo.equilibrium.MixtureEquilibriaPhaseSolution;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class HeterogeneousMixtureSubstance extends HeterogeneousSubstance{
    Cubic equationOfState;
    Alpha alpha;
    MixingRule mixingRule;
    
    private ArrayList<Component> components;
    
//    private MixtureSubstance vapor;
//    private MixtureSubstance liquid;
    
   
    
    
    
    private HashMap<Component,Double> zFractions = new HashMap(); 
    
    public HeterogeneousMixtureSubstance(
	    Cubic eos,
	    Alpha alpha,
	    MixingRule mixingrule, 
	    ArrayList<Component> components){
	this.equationOfState = eos;
	this.alpha = alpha;
	this.components = components;
	this.mixingRule = mixingrule;
	
	vapor = new MixtureSubstance(equationOfState, alpha,mixingrule, components,Phase.VAPOR);
	liquid = new MixtureSubstance(equationOfState,alpha,mixingrule,components,Phase.LIQUID);
    }
    public HeterogeneousMixtureSubstance(
	    Cubic eos,
	    Alpha alpha,
	    MixingRule mixingrule, 
	    ArrayList<Component> components, InteractionParameter k){
	this.equationOfState = eos;
	this.alpha = alpha;
	this.components = components;
	this.mixingRule = mixingrule;
	
	vapor = new MixtureSubstance(equationOfState, alpha,mixingrule, components,Phase.VAPOR,k);
	liquid = new MixtureSubstance(equationOfState,alpha,mixingrule,components,Phase.LIQUID,k);
    }
    
    
    public int bubbleTemperatureEstimate() {
	copyZfractionsToliquid();
	double temperature =  300;
	double error = 100;
	double deltaT =1;      
	double tol = 1e-4;

	HashMap<PureSubstance,Double> vaporPressures = new HashMap();
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
	setTemperature(temperature);
	for(PureSubstance component: getVapor().getPureSubstances()){
	    double vp = component.calculatetAcentricFactorBasedVaporPressure();
	    double yi = vp * getLiquid().getMolarFractions().get(component) / pressure;
	    getVapor().getMolarFractions().put(component, yi);
	}
	return iterations;
	//sreturn temperature;
    }

    
    @Override
    public int bubbleTemperature() {

	 HashMap<Component,Double> K;
	double e = 100;
	double deltaT = 1;

	 temperature = bubbleTemperatureEstimate(pressure);
	double tolerance = 1e-4;
	int count = 0;
	while(Math.abs(e) >= tolerance && count < 1000){
	    K = equilibriumRelations() ;
	    double sy = calculateSy(K);
	    e = Math.log(sy);
	    double T_ = temperature + deltaT;
	    setTemperature(T_);
	    HashMap<Component, Double> k_ = equilibriumRelations();
	    double Sy_ = calculateSy(k_);
	    double e_ = Math.log(Sy_);
	    temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
	    calculateNewYFractions(K, sy);
	}
	return count;
	//return new MixtureEquilibriaPhaseSolution(temperature, pressure, (HashMap<Component,Double>)getLiquid().getReadOnlyFractions().clone(), (HashMap<Component,Double>)getVapor().getReadOnlyFractions().clone(), count);
    }

    @Override
    public void bubblePressureEstimate() {
	
	copyZfractionsToliquid();
	for (Component component:getComponents()){
	    getLiquid().setFraction(component, getzFractions().get(component));
	}

	  HashMap<PureSubstance,Double> vaporPressures = new HashMap<>();
      pressure= 0;
      int  iterations = 0;
      setTemperature(temperature);
      for( PureSubstance component : getLiquid().getPureSubstances()){
	  double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	  vaporPressures.put(component, vaporP);
	  pressure += vaporP * getLiquid().getMolarFractions().get(component);  
      }
      setVaporFractionsRaoultsLaw( vaporPressures);
     // return new EquilibriaSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);   
      setPressure(pressure);
    }

    @Override
    public int bubblePressure() {
	BubblePressureFunctions function = new BubblePressureFunctions();
	
	bubblePressureEstimate(temperature);
	return minimizePressure(function, temperature,Phase.VAPOR);

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
    
    
    

    public double dewTemperatureEstimate(double pressure) {
	
	copyZfractionsToVapor();
	
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
      
      
      HashMap<PureSubstance,Double> vaporPressures = new HashMap();
      int iterations =0;
      while (Math.abs(error) >tol && iterations < 1000 ){
          iterations++;
            calcPressure = 0;
            calcP_ = 0;
            denominator = 0;
            denominator_ = 0;
            
            T_  = temperature + deltaT;
           for (PureSubstance component : getVapor().getPureSubstances() ){
	       setTemperature(temperature);
               double vaporPressure =component.calculatetAcentricFactorBasedVaporPressure();
	       vaporPressures.put(component, vaporPressure);
               denominator += getVapor().getMolarFractions().get(component) / vaporPressure;
               setTemperature(T_);
               double vaporPressure_ =component.calculatetAcentricFactorBasedVaporPressure();
               denominator_ += getVapor().getMolarFractions().get(component) / vaporPressure_;
           }
           
           calcPressure = 1/denominator;
           calcP_ = 1/ denominator_;
           
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      
      setLiquidFractionsRaoultsLaw(vaporPressures);
      return temperature;
    }
    
    public double dewTemperature(double pressure) {
	
        HashMap<Component,Double> K;
	
        double sx;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<Component,Double> k_;
        double sx_;
        double e_;
        
	 dewTemperatureEstimate(pressure);

        double tolerance  = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count < 1000){
	    setTemperature(temperature);
            K = equilibriumRelations(); 
            sx = calculateSx(K);
		    
            e = Math.log(sx);
            T_ = temperature + deltaT;
	    setTemperature(T_);
            k_ = equilibriumRelations();//equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx_ = calculateSx(k_);//, vaporFractions, components);
            e_ = Math.log(sx_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
           // K = equilibriumRelations(temperature, pressure);//equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            //sx = calculateSx(K);//, vaporFractions, components);
            calculateNewXFractions(K, sx);
        }
        return temperature; //new MixtureEquilibriaPhaseSolution(temperature,pressure, molarFractions,liquidFractions, count);
    }

    @Override
    public void dewPressureEstimate() {
	//return 12.2533971*101325;
	copyZfractionsToVapor();
	
	
	
	HashMap<PureSubstance,Double> vaporPressures = new HashMap<>();
	int  iterations = 0;
	double denominator=0;
	setTemperature(temperature);
	for( PureSubstance component : getVapor().getPureSubstances()){
	      double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	      vaporPressures.put(component, vaporP);
	      denominator += getVapor().getMolarFractions().get(component) / vaporP;
	}
	
       setPressure( 1/denominator);
       setLiquidFractionsRaoultsLaw( vaporPressures);
       
      // HashMap<PureSubstance,Double> liquidFractions = getLiquidFractionsRaoultsLaw(pressure, molarFractions, vaporPressures);
	//return pressure;//new MixtureEquilibriaPhaseSolution(temperature,pressure, null,null, iterations);
    }

    public double dewPressure(double temperature) {
	
	
	MixtureEquilibriaFunction function = new DewPressureFunctions();
	 dewPressureEstimate(temperature);
	return minimizePressure(function, temperature, Phase.LIQUID);
    }
    
    public double flash(double temperature,double pressure){
	
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
//	for (PureSubstance component :vapor.getPureSubstances()){
//	    double vaporPressure = component.calculatetAcentricFactorBasedVaporPressure(temperature);
//	    
//	    double xi = zFractions.get(component.getComponent())*pressure / vaporPressure;
//	    X.put(component, xi);
//	    sx += xi;
//	}
//	
//	
//	for(PureSubstance component : liquid.getPureSubstances()){
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
//	for(PureSubstance component: liquid.getPureSubstances()){
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
	
	HashMap<PureSubstance,Double> X = new HashMap<>();
	setTemperature(temperature);
	for (PureSubstance component :getVapor().getPureSubstances()){
	    double vaporPressure = component.calculatetAcentricFactorBasedVaporPressure();
	    
	    double xi = getzFractions().get(component.getComponent())*pressure / vaporPressure;
	    X.put(component, xi);
	    sx += xi;
	}
	
	
	for(PureSubstance component : getLiquid().getPureSubstances()){
	    double xi = X.get(component);
	    double normXi = xi/sx;
	    getLiquid().getMolarFractions().put(component, normXi);
	}
     }
     
     public HashMap<Component,Double> flashEstimateEquilibriumRelations(double temperature, double pressure){
	 HashMap<Component,Double> k = new HashMap();
	setTemperature(temperature);
	
	for(PureSubstance pure: getLiquid().getPureSubstances()){
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
            double zi = getzFractions().get(component);
            double ki = k.get(component);
            
            x_.put(component, zi / ( 1 + vF*(ki - 1)));
        }
        return x_;
    }
    
 
    
    
    
    
    
    
    
    private  double calculateError(double pressure,double temperature){
	double result=0;
	setTemperature(temperature);
	setPressure(pressure);
	for(PureSubstance component: getVapor().getPureSubstances()){
	    double xi = getLiquid().getMolarFractions().get(component);
	    double yi = getVapor().getMolarFractions().get(component);
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
            
            double zi = getzFractions().get(component);
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
            double zi = getzFractions().get(component);
            double ki = k.get(component);
            result += (- zi * Math.pow(ki - 1,2))/(Math.pow(1 + vF * (ki-1),2));
        }
        
        return result;
    }
    
    
    
    
    
    
    
    
   public void setZFraction(Component component, double d) {
	getzFractions().put(component, d);
    }
    

 
public void setVaporFractionsRaoultsLaw(HashMap<PureSubstance,Double> vaporPressures){ 
      for( PureSubstance component : getLiquid().getPureSubstances()){
          double y = vaporPressures.get(component) * getLiquid().getMolarFractions().get(component)/pressure;
            getVapor().getMolarFractions().put(component, y);  
      }
}
        

    
    
    
    
    
    public double calculateVaporPressure(double temperature){
	double vaporPressure = 0;
	setTemperature(temperature);
	 for (PureSubstance component : getVapor().getPureSubstances() ){
               vaporPressure += component.calculatetAcentricFactorBasedVaporPressure()*getzFractions().get(component.getComponent());     
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
    
	//double pressure =pressureEstimate;
	int count = 0;
	while(Math.abs(e) > tolerance && count < 10000 ){         
	    count++;
	    setPressure(pressure);
	    K =   equilibriumRelations();
	    e = function.errorFunction(K);
	    double pressure_ = pressure * (1 + deltaP);
	    setPressure(pressure_);
	    double e_ = function.errorFunction(equilibriumRelations());
	    pressure = function.newPressureFunction(pressure, pressure_, e, e_);
	    updateFractions(K, aPhase);
	}    
      
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
    public ArrayList<Component> getComponents() {
	return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(ArrayList<Component> components) {
	this.components = components;
    }

    /**
     * @return the vapor
     */
    @Override
    public MixtureSubstance getVapor() {
	return (MixtureSubstance)vapor;
    }

    /**
     * @param vapor the vapor to set
     */
    public void setVapor(MixtureSubstance vapor) {
	this.vapor = vapor;
    }

    /**
     * @return the liquid
     */
    @Override
    public MixtureSubstance getLiquid() {
	return (MixtureSubstance)liquid;
    }

    /**
     * @param liquid the liquid to set
     */
    public void setLiquid(MixtureSubstance liquid) {
	this.liquid = liquid;
    }

    /**
     * @return the zFractions
     */
    public HashMap<Component,Double> getzFractions() {
	return zFractions;
    }

    /**
     * @param zFractions the zFractions to set
     */
    public void setzFractions(HashMap<Component,Double> zFractions) {
	this.zFractions = zFractions;
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
	    getLiquid().setFraction(component, getzFractions().get(component));
	}
    }
   

   
    
    
      public  void setLiquidFractionsRaoultsLaw(
        HashMap<PureSubstance,Double> vaporPressures){
    
        for( PureSubstance component : getVapor().getPureSubstances()){
            double x =  getVapor().getMolarFractions().get(component)*pressure/vaporPressures.get(component) ;
            getLiquid().getMolarFractions().put(component, x);  
        }   
      
    }

    private void copyZfractionsToVapor(){
	for (Component component:getComponents()){
	    getVapor().setFraction(component, getzFractions().get(component));
	}
    }
    
    
    
//  public Double equilibriumRelation(Component component, double temperature, double pressure) {
//	
//    }
//  
    
//    public Double equilibriumRelation(PureSubstance pure, double temperature, double pressure){
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
    
 
}
