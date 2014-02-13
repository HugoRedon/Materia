
package termo.substance;

import java.util.ArrayList;
import java.util.HashMap;
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
public class HeterogeneousMixtureSubstance extends Substance{
    Cubic equationOfState;
    Alpha alpha;
    MixingRule mixingRule;
    
    private ArrayList<Component> components;
    
    MixtureSubstance vapor;
    MixtureSubstance liquid;
    
    
    HashMap<Component,Double> zFractions = new HashMap(); 
    
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
    
    public double bubbleTemperatureEstimate(double pressure) {
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
	for(PureSubstance component: vapor.getPureSubstances()){
	    double vp = component.getAcentricFactorBasedVaporPressure(temperature);
	    double yi = vp * liquid.getMolarFractions().get(component) / pressure;
	    vapor.getMolarFractions().put(component, yi);
	}
	return temperature;
    }

    public EquilibriaSolution bubbleTemperature(double pressure) {

	 HashMap<PureSubstance,Double> K;
	double e = 100;
	double deltaT = 1;

	double temperature = bubbleTemperatureEstimate(pressure);
	double tolerance = 1e-4;
	int count = 0;
	while(Math.abs(e) >= tolerance && count < 1000){
	    K = equilibriumRelations(temperature, pressure) ;
	    double sy = calculateSy(K);
	    e = Math.log(sy);
	    double T_ = temperature + deltaT;
	    HashMap<PureSubstance, Double> k_ = equilibriumRelations(T_, pressure);
	    double Sy_ = calculateSy(k_);
	    double e_ = Math.log(Sy_);
	    temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
	    calculateNewYFractions(K, sy);
	}
	return new MixtureEquilibriaPhaseSolution(temperature, pressure, (HashMap<Component,Double>)liquid.getFractions().clone(), (HashMap<Component,Double>)vapor.getFractions().clone(), count);
    }

    public double bubblePressureEstimate(double temperature) {
	copyZfractionsToliquid();
	for (Component component:getComponents()){
	    liquid.setFraction(component, zFractions.get(component));
	}

	  HashMap<PureSubstance,Double> vaporPressures = new HashMap<>();
      double pressure= 0;
      int  iterations = 0;
      for( PureSubstance component : liquid.getPureSubstances()){
	  double vaporP =  component.getAcentricFactorBasedVaporPressure(temperature);
	  vaporPressures.put(component, vaporP);
	  pressure += vaporP * liquid.getMolarFractions().get(component);  
      }
      setVaporFractionsRaoultsLaw(pressure, vaporPressures);
     // return new EquilibriaSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);   
      return pressure;
    }

    public double bubblePressure(double temperature) {
	BubblePressureFunctions function = new BubblePressureFunctions();

	double pressureEstimate = bubblePressureEstimate(temperature);
	return minimizePressure(function, temperature, pressureEstimate,Phase.VAPOR);

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
           for (PureSubstance component : vapor.getPureSubstances() ){
               double vaporPressure =component.getAcentricFactorBasedVaporPressure(temperature);
	       vaporPressures.put(component, vaporPressure);
               denominator += vapor.getMolarFractions().get(component) / vaporPressure;
               
               double vaporPressure_ =component.getAcentricFactorBasedVaporPressure(T_);
               denominator_ += vapor.getMolarFractions().get(component) / vaporPressure_;
           }
           
           calcPressure = 1/denominator;
           calcP_ = 1/ denominator_;
           
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      
      setLiquidFractionsRaoultsLaw(pressure, vaporPressures);
      return temperature;
    }
    
    public double dewTemperature(double pressure) {
	
        HashMap<PureSubstance,Double> K;
	HashMap<PureSubstance,Double> liquidFractions = new HashMap<>();;
        double sx;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<PureSubstance,Double> k_;
        double sx_;
        double e_;
        
	double temperature = dewTemperatureEstimate(pressure);

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
            calculateNewXFractions(K, sx);
        }
        return temperature; //new MixtureEquilibriaPhaseSolution(temperature,pressure, molarFractions,liquidFractions, count);
    }

    public double dewPressureEstimate(double temperature) {
	//return 12.2533971*101325;
	copyZfractionsToVapor();
	
	
	
	HashMap<PureSubstance,Double> vaporPressures = new HashMap<>();
	int  iterations = 0;
	double denominator=0;
	for( PureSubstance component : vapor.getPureSubstances()){
	      double vaporP =  component.getAcentricFactorBasedVaporPressure(temperature);
	      vaporPressures.put(component, vaporP);
	      denominator += vapor.getMolarFractions().get(component) / vaporP;
	}
       double pressure = 1/denominator;
       
       setLiquidFractionsRaoultsLaw(pressure, vaporPressures);
      // HashMap<PureSubstance,Double> liquidFractions = getLiquidFractionsRaoultsLaw(pressure, molarFractions, vaporPressures);
	return pressure;//new MixtureEquilibriaPhaseSolution(temperature,pressure, null,null, iterations);
    }

    public double dewPressure(double temperature) {
	
	
	MixtureEquilibriaFunction function = new DewPressureFunctions();
	double pressureEstimate =  dewPressureEstimate(temperature);
	return minimizePressure(function, temperature, pressureEstimate, Phase.LIQUID);
    }
    
    public double flash(double temperature,double pressure){
	
	double vF = flashEstimate(temperature, pressure);
	
	double tolerance  = 1e-4;
            HashMap<PureSubstance,Double> K;
            double error=100;
            HashMap<PureSubstance,Double> x_;

            HashMap<PureSubstance,Double> y_;
            while(error >= tolerance){
                K = equilibriumRelations(temperature, pressure);
                error = calculateError(pressure, temperature);
                vF = rachfordRice(K, vF,tolerance);
                x_=x_( K, vF);
		
		liquid.setMolarFractions(newFractions(x_));
                
		y_ = y_(x_, K);
		vapor.setMolarFractions( newFractions(y_));
                
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
//	    double vaporPressure = component.getAcentricFactorBasedVaporPressure(temperature);
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
//	    double vaporPressure = component.getAcentricFactorBasedVaporPressure(temperature);
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
            HashMap<PureSubstance,Double> K;           
            HashMap<PureSubstance,Double> x_;

            HashMap<PureSubstance,Double> y_;
           
	    K = flashEstimateEquilibriumRelations(temperature, pressure);
	   // error = calculateError(pressure, temperature);
	    vF = rachfordRice(K, vF,tolerance);
	    x_=x_( K, vF);

	    liquid.setMolarFractions(newFractions(x_));

	    y_ = y_(x_, K);
	    vapor.setMolarFractions( newFractions(y_));
                
            
        
        return  vF;
    }
    
     
     public void flashEstimateLiquidFractions(double temperature, double pressure){
	double sx = 0;
	
	HashMap<PureSubstance,Double> X = new HashMap<>();
	
	for (PureSubstance component :vapor.getPureSubstances()){
	    double vaporPressure = component.getAcentricFactorBasedVaporPressure(temperature);
	    
	    double xi = zFractions.get(component.getComponent())*pressure / vaporPressure;
	    X.put(component, xi);
	    sx += xi;
	}
	
	
	for(PureSubstance component : liquid.getPureSubstances()){
	    double xi = X.get(component);
	    double normXi = xi/sx;
	    liquid.getMolarFractions().put(component, normXi);
	}
     }
     
     public HashMap<PureSubstance,Double> flashEstimateEquilibriumRelations(double temperature, double pressure){
	 HashMap<PureSubstance,Double> k = new HashMap();
	
	for(PureSubstance component: liquid.getPureSubstances()){
	    double vaporPressure = component.getAcentricFactorBasedVaporPressure(temperature);
	    double ki =  vaporPressure/ pressure;
	    k.put(component, ki);
	}
	return k;
     }
    
    
    
    
    
    
    private double sumS(HashMap<PureSubstance,Double> x_){
	double s =0;
        for(PureSubstance component: vapor.getPureSubstances()){
            s += x_.get(component);
        }
	return s;
    }
    
       
    private  HashMap<PureSubstance,Double> y_(HashMap<PureSubstance,Double> x_,
            HashMap<PureSubstance,Double> k){
        HashMap<PureSubstance,Double> y_ = new HashMap<>();
        
        for(PureSubstance component: vapor.getPureSubstances()){
            double ki = k.get(component);
            double x_i = x_.get(component);
            y_.put(component, x_i * ki);
        }
        return y_;
    }
    
    private  HashMap<PureSubstance,Double> newFractions(HashMap<PureSubstance,Double> x_){
        HashMap<PureSubstance,Double> x = new HashMap<>();
        double s = sumS(x_);
        
        for(PureSubstance component: vapor.getPureSubstances()){
            
            double x_i = x_.get(component);
            x.put(component, x_i /s);
        }
        
        return x;
    }
    
    private HashMap<PureSubstance,Double> x_(
            
            HashMap<PureSubstance,Double> k,
            double vF
            ){
        
        HashMap<PureSubstance,Double> x_ = new HashMap<>();
        
        for(PureSubstance component : vapor.getPureSubstances()){
            double zi = zFractions.get(component.getComponent());
            double ki = k.get(component);
            
            x_.put(component, zi / ( 1 + vF*(ki - 1)));
        }
        return x_;
    }
    
 
    
    
    
    
    
    
    
    private  double calculateError(double pressure,double temperature){
	double result=0;
	for(PureSubstance component: vapor.getPureSubstances()){
	    double xi = liquid.getMolarFractions().get(component);
	    double yi = vapor.getMolarFractions().get(component);
	    double liquidFug = liquid.calculateFugacity(component,temperature,pressure);
	    double vaporFug = vapor.calculateFugacity(component, temperature, pressure) ;
	    result += Math.abs(xi* liquidFug - yi * vaporFug    );
	}
	return result;
    }
    
    private  double rachfordRice(HashMap<PureSubstance,Double> k, 	 
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
    
    
    
      private  double s(HashMap<PureSubstance,Double> k,double vF){
        
        double result =0;
        for(PureSubstance component :vapor.getPureSubstances()){
            
            double zi = zFractions.get(component.getComponent());
            double ki = k.get(component);
            
            result += (zi * (ki - 1 ))/( 1 + vF * ( ki - 1));
        }
        
        return result;
    }
    private double s_(HashMap<PureSubstance,Double> k,
              
            double vF
            ){
        double result =0;
        for(PureSubstance component : k.keySet()){
            double zi = zFractions.get(component.getComponent());
            double ki = k.get(component);
            result += (- zi * Math.pow(ki - 1,2))/(Math.pow(1 + vF * (ki-1),2));
        }
        
        return result;
    }
    
    
    
    
    
    
    
    
   public void setZFraction(Component component, double d) {
	zFractions.put(component, d);
    }
    

 
public void setVaporFractionsRaoultsLaw(double pressure, 
        HashMap<PureSubstance,Double> vaporPressures){ 
      for( PureSubstance component : liquid.getPureSubstances()){
          double y = vaporPressures.get(component) * liquid.getMolarFractions().get(component)/pressure;
          vapor.getMolarFractions().put(component, y);  
      }
}
        

    
    
    
    
    
    public double calculateVaporPressure(double temperature){
	double vaporPressure = 0;
	 for (PureSubstance component : vapor.getPureSubstances() ){
               vaporPressure += component.getAcentricFactorBasedVaporPressure(temperature)*zFractions.get(component.getComponent());     
           }
	 
	 return vaporPressure;
    }
  

     public  void calculateNewYFractions(
	     HashMap<PureSubstance,Double> equilibriumRelations, 
	     double s){
         
        for (PureSubstance aComponent: vapor.getPureSubstances()){
            double ki = equilibriumRelations.get(aComponent);
            double x = liquid.getMolarFractions().get(aComponent);
            vapor.getMolarFractions().put(aComponent, ki * x / s);
        }
        
        
    }
     
    
      
      private double minimizePressure(MixtureEquilibriaFunction function,double temperature, double pressureEstimate,Phase aPhase){
	  HashMap<PureSubstance,Double> K;
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
	    updateFractions(K, aPhase);
	}    
      
	//HashMap<PureSubstance,Double> liquidFractions =new HashMap<>();//
      
      return pressure;//new MixtureEquilibriaPhaseSolution(temperature,pressure,null,liquidFractions  , count);
      }
      
      private void updateFractions(HashMap<PureSubstance , Double> K, Phase aPhase){
	  if(aPhase.equals(Phase.LIQUID)){
	    double sx = calculateSx(K);
	    calculateNewXFractions(K, sx);
	  }else{
	    double sy = calculateSy(K);
	    calculateNewYFractions(K, sy);
	  }
	  
      }
    private void calculateNewXFractions(
	    HashMap<PureSubstance,Double> equilibriumRelations, 
	    double s){
         HashMap<PureSubstance,Double> newFractions = new  HashMap<>();
        for (PureSubstance aComponent: liquid.getPureSubstances()){
            double ki = equilibriumRelations.get(aComponent);
            double y = vapor.getMolarFractions().get(aComponent);
            liquid.getMolarFractions().put(aComponent,   y / (ki*s));
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
    
    
   
    private void copyZfractionsToliquid(){
	for (Component component:getComponents()){
	    liquid.setFraction(component, zFractions.get(component));
	}
    }
   

   
    
    
      public  void setLiquidFractionsRaoultsLaw(double pressure,
        HashMap<PureSubstance,Double> vaporPressures){
    
        for( PureSubstance component : vapor.getPureSubstances()){
            double x =  vapor.getMolarFractions().get(component)*pressure/vaporPressures.get(component) ;
            liquid.getMolarFractions().put(component, x);  
        }   
      
    }

    private void copyZfractionsToVapor(){
	for (Component component:getComponents()){
	    vapor.setFraction(component, zFractions.get(component));
	}
    }
    
    
    
  
  
    
    

    

    public  HashMap<PureSubstance,Double> equilibriumRelations (
	    double temperature,
	    double pressure
            ){
         HashMap<PureSubstance,Double> equilibriumRelations  = new HashMap<>();
         
         for (PureSubstance aComponent : liquid.getPureSubstances()){
	   
	    double liquidFug = liquid.calculateFugacity(aComponent,temperature,pressure);
           double vaporFug = vapor.calculateFugacity(aComponent, temperature, pressure);     
           double equilRel = liquidFug/ vaporFug;
           equilibriumRelations.put(aComponent, equilRel);
         }
         return equilibriumRelations;
    }
    public  double calculateSx(HashMap<PureSubstance,Double> equilibriumRelations){
        
         double s = 0;
        for (PureSubstance aComponent : vapor.getPureSubstances()){
              double equilRel = equilibriumRelations.get(aComponent);
           s +=  vapor.getMolarFractions().get(aComponent)/equilRel;
            
        }
        
        return s;
    }
    
    public double calculateSy(HashMap<PureSubstance,Double> equilibriumRelations){
        
         double s = 0;
        for (PureSubstance aComponent : liquid.getMolarFractions().keySet()){
              double equilRel = equilibriumRelations.get(aComponent);
           s += equilRel * liquid.getMolarFractions().get(aComponent);
        }
        
        return s;
    }
    
 
}
