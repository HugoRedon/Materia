
package termo.matter;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.eos.mixingRule.MixingRule;
import termo.optimization.errorfunctions.MixtureErrorFunction;
import termo.phase.Phase;
import termo.utils.IterationInfo;

/**
 *
 * @author
 * Hugo
 */
public final class HeterogeneousMixture extends Heterogeneous implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7958256517053288917L;
	private Cubic equationOfState;
    private Alpha alpha;
    private MixingRule mixingRule;
    
    private InteractionParameter interactionParameters = new ActivityModelBinaryParameter();
    
     private final Map<String,Double> zFractions = new HashMap<>(); 
     private Set<Compound> components = new HashSet<>();
     
     private MixtureErrorFunction errorFunction;
     
     private Integer maxIterations = 100;
    // private NewtonMethodSolver optimizer;
    
    public HeterogeneousMixture(){
    	liquid = new Mixture();
        liquid.setPhase(Phase.LIQUID);
        vapor = new Mixture();
        vapor.setPhase(Phase.VAPOR);
        
        mpcs.addPropertyChangeListener(liquid);
        mpcs.addPropertyChangeListener(vapor);
        
       
        errorFunction = new MixtureErrorFunction(this);
        mpcs.addPropertyChangeListener(errorFunction);
        
       
    }
    
    public void calculateVF(){
    	vF=flash(temperature,pressure);
    	if(vF< 0){
    		vF=0;
    		copyZfractionsToliquid();
    	}else if (vF>1){
    		vF =1;
    		copyZfractionsToVapor();
    	}
    }
     
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Mezcla heterogénea, ");
    	Iterator<Compound> it = components.iterator();
    	sb.append("compuestos: (");
    	while(it.hasNext()){
    		Compound c= it.next();
    		String coma = (it.hasNext())?", ":"";
    		sb.append(c.getName()+ coma);
    	}
    	sb.append("), ");
    	
    	sb.append("regla de mezclado: "+mixingRule.getName());
    	sb.append(", ecuación de estado: " + equationOfState.getName());
    	return sb.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt); 
        String name =evt.getPropertyName();
        switch(name){
            case "components":
                setComponents((Set<Compound> )evt.getNewValue());
                break;
//            case "zFractions":
//                zFractions = (HashMap<String,Double>)evt.getNewValue();
//                break;
            case "mixingRule":
                setMixingRule((MixingRule)evt.getNewValue());
                break;
            case "alpha":
                setAlpha((Alpha)evt.getNewValue());
                break;
            case "cubic":
                setEquationOfState((Cubic) evt.getNewValue());
                break;
        }
        
    }
   
    public HeterogeneousMixture(
	    Cubic eos,
	    Alpha alpha,
	    MixingRule mixingrule, 
	    Set<Compound> components, 
	    InteractionParameter k){
    	
        this();
        setEquationOfState(eos);
        setAlpha(alpha);
        setComponents(components);
        setMixingRule(mixingrule);
        setInteractionParameters(k);
        
        
          
         
    }
    //from superclass
	    public final int bubblePressure(double pressureEstimate,Map<String,Double>vaporFractionsEstimate){
	    	for(Compound c: components){
	    		double fraction = vaporFractionsEstimate.get(c.getName());
	    		getVapor().setFraction(c, fraction);
	    	}
	        setPressure(pressureEstimate);
	        return bubblePressureImpl();
	    }
	    public final int bubblePressure(){
	        bubblePressureEstimate();
	        return bubblePressureImpl();
	    }
	
	    public final int dewPressure(double pressureEstimate,Map<String,Double> liquidFractionsEstimate){
	    	for(Compound c: components){
	    		double fraction = liquidFractionsEstimate.get(c.getName());
	    		getLiquid().setFraction(c, fraction);
	    	}
			setPressure(pressureEstimate);
			return dewPressureImpl();
	    }
	    public final int dewPressure(){
	        dewPressureEstimate();
	        return dewPressureImpl();
	    }
    //----
    
    
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

    
    public int bubbleTemperature(){
        bubbleTemperatureEstimate();
        return bubbleTemperature(temperature);
    }
    
    public int bubbleTemperature(double estimate) {
    	copyZfractionsToliquid();
	 HashMap<Compound,Double> K;
	double e = 100;
	double deltaT = 1;

	 
	 
	 double temp = estimate;
	 
	double tolerance = 1e-4;
	int count = 0;
	while(Math.abs(e) >= tolerance && count < maxIterations){
		count++;
	    setTemperature(temp);
	    K = equilibriumRelations() ;
	    double sy = calculateSy(K);
	    e = Math.log(sy);
	    double T_ = temp + deltaT;
	    setTemperature(T_);
	    HashMap<Compound, Double> k_ = equilibriumRelations();
	    double Sy_ = calculateSy(k_);
	    double e_ = Math.log(Sy_);
	    temp = temp * T_ * (e_ - e) / (T_ * e_ - temp * e);
	    calculateNewYFractions(K, sy);
	}
	
	setTemperature(temp);
	return count;
	//return new MixtureEquilibriaPhaseSolution(temperature, pressure, (HashMap<Component,Double>)getLiquid().getReadOnlyFractions().clone(), (HashMap<Component,Double>)getVapor().getReadOnlyFractions().clone(), count);
    }

    
    public void bubblePressureEstimate() {
	
	copyZfractionsToliquid();

	  HashMap<Substance,Double> vaporPressures = new HashMap<>();
      double p =0;
      int  iterations = 0;
      setTemperature(temperature);
      for( Substance component : getLiquid().getPureSubstances()){
	  double vaporP =  component.calculatetAcentricFactorBasedVaporPressure();
	  vaporPressures.put(component, vaporP);
	  p += vaporP * component.getMolarFraction();  
      }
      setPressure(p);
      setVaporFractionsRaoultsLaw( vaporPressures);
     // return new EquilibriaSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);   
      
    }

    
    public int bubblePressureImpl() {
    	copyZfractionsToliquid();
		BubblePressureFunctions function = new BubblePressureFunctions();
		return minimizePressure(function, temperature,Phase.VAPOR);

    }
    
    
    

    
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
      while (Math.abs(error) >tol && iterations < maxIterations ){
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
    
    
    public int dewTemperature(){
    	dewTemperatureEstimate();
    	return dewTemperature(temperature);
    }
    
    public int dewTemperature(double estimate) {
    	copyZfractionsToVapor();
        HashMap<Compound,Double> K;
	
        double sx;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<Compound,Double> k_;
        double sx_;
        double e_;
        
	 //dewTemperatureEstimate();
	 
	 double temp = estimate;

        double tolerance  = 1e-4;
        int count = 0;
        while(Math.abs(e) >= tolerance && count < 1000){
        	count++;
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

    
    public int dewPressureImpl() {
    	copyZfractionsToVapor();
	MixtureEquilibriaFunction function = new DewPressureFunctions();
	return minimizePressure(function, temperature, Phase.LIQUID);
    }
    
    public double flash(double temperature,double pressure,
    		Map<String,Double> vaporFractions, 
    		Map<String,Double> liquidFractions,double vF){
    	for(Compound c: components){
    		double liquidFraction = liquidFractions.get(c.getName());
    		getLiquid().setFraction(c, liquidFraction);
    		double vaporFraction = vaporFractions.get(c.getName());
    		getVapor().setFraction(c, vaporFraction);
    	}
    	
    	return flashImpl( temperature,  pressure,vF);
    }
    public double flashImpl(double temperature, double pressure,double vFEstimate){
    	double vF = vFEstimate;
    	double tolerance  = 1e-4;
        HashMap<Compound,Double> K;
        double error=100;
        HashMap<Compound,Double> x_;

        HashMap<Compound,Double> y_;
        while(error >= tolerance){
			setTemperature(temperature);
            K = equilibriumRelations();
            error = calculateError(pressure, temperature);
            Double newVF =rachfordRice(K, vF,tolerance); 
            System.out.println("vF: " + newVF);
            if(Double.isFinite(newVF)){
            	vF =newVF; 
            }else{
            	break;
            }
            x_=x_( K, vF);
			getLiquid().setFractions(newFractions(x_));
			y_ = y_(x_, K);
			getVapor().setFractions(newFractions(y_)); 
        }
        return  vF;
    }
    
    
    public double flash(double temperature,double pressure){
		setTemperature(temperature);
		setPressure(pressure);		
		double vF = flashEstimate(temperature, pressure);
		System.out.println("flash estimate: " + vF);
		return flashImpl(temperature, pressure, vF);
		
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
        HashMap<Compound,Double> K;           
        HashMap<Compound,Double> x_;

        HashMap<Compound,Double> y_;
           
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
     
     public HashMap<Compound,Double> flashEstimateEquilibriumRelations(double temperature, double pressure){
	 HashMap<Compound,Double> k = new HashMap();
	setTemperature(temperature);
	
	for(Substance pure: getLiquid().getPureSubstances()){
	    double vaporPressure = pure.calculatetAcentricFactorBasedVaporPressure();
	    double ki =  vaporPressure/ pressure;
	    k.put(pure.getComponent(), ki);
	}
	return k;
     }
    
    
    
    
    
    
    private double sumS(HashMap<Compound,Double> x_){
	double s =0;
        for(Compound component: components){
            s += x_.get(component);
        }
	return s;
    }
    
       
    private  HashMap<Compound,Double> y_(HashMap<Compound,Double> x_,
            HashMap<Compound,Double> k){
        HashMap<Compound,Double> y_ = new HashMap<>();
        
        for(Compound component: components){
            double ki = k.get(component);
            double x_i = x_.get(component);
            y_.put(component, x_i * ki);
        }
        return y_;
    }
    
    private  HashMap<Compound,Double> newFractions(HashMap<Compound,Double> x_){
        HashMap<Compound,Double> x = new HashMap<>();
        double s = sumS(x_);
        
        for(Compound component: components){
            
            double x_i = x_.get(component);
            x.put(component, x_i /s);
        }
        
        return x;
    }
    
    private HashMap<Compound,Double> x_(
            
            HashMap<Compound,Double> k,
            double vF
            ){
        
        HashMap<Compound,Double> x_ = new HashMap<>();
        
        for(Compound component : components){
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
	for(Compound component: getComponents()){
	    double xi = getLiquid().getReadOnlyFractions().get(component);
	    double yi = getVapor().getReadOnlyFractions().get(component);
	    double liquidFug = getLiquid().calculateFugacityCoefficient(component);
	    double vaporFug = getVapor().calculateFugacityCoefficient(component);
	    result += Math.abs(xi* liquidFug - yi * vaporFug    );
	}
	return result;
    }
    
    private  double rachfordRice(HashMap<Compound,Double> k, 	 
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
    
    
    
      private  double s(HashMap<Compound,Double> k,double vF){
        
        double result =0;
        for(Compound component :components){
            
            double zi = getzFractions().get(component.getName());
            double ki = k.get(component);
            
            result += (zi * (ki - 1 ))/( 1 + vF * ( ki - 1));
        }
        
        return result;
    }
    private double s_(HashMap<Compound,Double> k,
              
            double vF
            ){
        double result =0;
        for(Compound component : components){
            double zi = getzFractions().get(component.getName());
            double ki = k.get(component);
            result += (- zi * Math.pow(ki - 1,2))/(Math.pow(1 + vF * (ki-1),2));
        }
        
        return result;
    }
    
    
    
    
    
    
    
    
   public void setZFraction(Compound component, double d) {
	getzFractions().put(component.getName(), d);
//	getLiquid().setFraction(component, d);
//	getVapor().setFraction(component, d);
    }
    
//   public void onlySetZFraction(Compound component,double d){
//	   getzFractions().put(component.getName(),d);
//   }

 

        

    
    
    
    
    
    public double calculateVaporPressure(double temperature){
	double vaporPressure = 0;
	setTemperature(temperature);
	 for (Substance pureSubstance : getVapor().getPureSubstances()){
             double acentricFactorVaporPressure=pureSubstance.calculatetAcentricFactorBasedVaporPressure();
             Compound componentObject = pureSubstance.getComponent();
             String componentName = componentObject.getName();
            // System.out.println("componentName" + componentName);
             Map<String,Double> zFractionss =getzFractions();
             double zFraction = zFractionss.get(componentName);
             double vaporPressureS = acentricFactorVaporPressure*zFraction;     
               vaporPressure += vaporPressureS;
           }
	 
	 return vaporPressure;
    }
  

     public  void calculateNewYFractions(
	     HashMap<Compound,Double> equilibriumRelations, 
	     double s){
         for(Substance sub: getLiquid().getPureSubstances()){
            double ki = equilibriumRelations.get(sub.getComponent());
            double x = sub.getMolarFraction();
            getVapor().setFraction(sub.getComponent(), ki * x / s);
        }
        
        
    }
     
    private List<IterationInfo> calculationReport = new ArrayList<>(); 
      
     private int minimizePressure(MixtureEquilibriaFunction function,double temperature,Phase aPhase){
    	 calculationReport.clear();
    	 HashMap<Compound,Double> K;
		double deltaP = 0.0001;
		double e = 100;
		double tolerance = 1e-7;
	    
		double p = pressure ;
		int count = 0;
		while(Math.abs(e) > tolerance && count < maxIterations ){
			IterationInfo ii = new IterationInfo();
		    ii.setPressure(p);
			ii.setIteration(count);
		    count++;
		    setPressure(p);
		    
		    
		    
		    K =   equilibriumRelations();
		    e = function.errorFunction(K);
		    
		    if(Math.abs(e) < tolerance){
		    	updateFractions(K,aPhase);
		    	break;
		    }
		    double pressure_ = p * (1 + deltaP);
		    setPressure(pressure_);
		    double e_ = function.errorFunction(equilibriumRelations());
		    p = function.newPressureFunction(p, pressure_, e, e_);
		   // setPressure(p);
		    
		    updateFractions(K, aPhase);
		    		    
		    ii.setPressure_(pressure_);
		    ii.setTemperature(temperature);
		    
		    ii.setNewPressure(p);
		    ii.setError(e);
		    ii.setError_(e_);
		    calculationReport.add(ii);
		}    
	  
		setPressure(p);
		updateFractions(equilibriumRelations()	, aPhase);
	
		return count;
      
      }
      
      private void updateFractions(HashMap<Compound , Double> K, Phase aPhase){    	      	
		  if(aPhase.equals(Phase.LIQUID)){
		    double sx = calculateSx(K);
		    calculateNewXFractions(K, sx);
		  }else{
		
		    double sy = calculateSy(K);
		    calculateNewYFractions(K, sy);
		  }	  		 
      }
      
      
      
    private void calculateNewXFractions(
	    HashMap<Compound,Double> equilibriumRelations, 
	    double s){
         HashMap<Compound,Double> newFractions = new  HashMap<>();
        for (Compound aComponent: components){
            double ki = equilibriumRelations.get(aComponent);
            double y = getVapor().getReadOnlyFractions().get(aComponent);
            getLiquid().setFraction(aComponent,   y / (ki*s));
        }
    }

    /**
     * @return the components
     */
    public Set<Compound> getComponents() {
	return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(Set<Compound> components) {
        Set<Compound> oldComponents = this.components;
	this.components = components;
        mpcs.firePropertyChange("components", oldComponents, components);
    }
    
    public void removeComponent(Compound component){
        if(components.contains(component)){
            Set<Compound> oldComponents =new HashSet(components);//.clone();
            components.remove(component);
            mpcs.firePropertyChange("components", oldComponents, components);
        }else{
            System.out.println("Warning---: componente no presente en la mezcla");
        }
    }
    
    public void addComponent(Compound component){
        if(components.contains(component)){
            System.out.println("Warning---: el componente ya estaba en la lista");
        }else{
            HashSet<Compound> oldComponents = new HashSet( components);//.clone();
            components.add(component);
            mpcs.firePropertyChange("components", oldComponents, components);
        }
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
	mpcs.addPropertyChangeListener(vapor);
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
    	mpcs.addPropertyChangeListener(liquid);
    }

    /**
     * @return the zFractions
     */
    public Map<String,Double> getzFractions() {
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

    /**
     * @return the errorfunction
     */
    public MixtureErrorFunction getErrorfunction() {
        return errorFunction;
    }

    /**
     * @param errorfunction the errorfunction to set
     */
    public void setErrorfunction(MixtureErrorFunction errorfunction) {
        this.errorFunction = errorfunction;
    }

 
    
      
 
      interface MixtureEquilibriaFunction{
	  public double errorFunction(HashMap<Compound,Double > equilibriumRelations);
	  public double newPressureFunction(double pressure, double pressure_, double e, double e_);
      }
      
    class DewPressureFunctions implements MixtureEquilibriaFunction{
	@Override
        public double errorFunction(HashMap<Compound,Double> equilibriumRelations ){
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
	public double errorFunction(HashMap<Compound,Double> equlibriumRelations){
	double sy = calculateSy(equlibriumRelations);//, liquidFractions, components);
	return sy -1;
    }
		@Override
		public double newPressureFunction(double pressure, double pressure_, double e , double e_){
		
			double newPressure =((pressure * pressure_ )* (e_ - e)) / ((pressure_ * e_) - (pressure * e));
			
//			if(newPressure > pressure*(1.1)){
//				newPressure = pressure*1.1;
//			}else if(newPressure< pressure*(0.9)){
//				newPressure = pressure*0.9;
//			}
				
				
		    return  newPressure;  
	    		
		}
	
    }
    
    
   
    private void copyZfractionsToliquid(){
	for (Compound component:getComponents()){
            //System.out.println("component: " + component);
            double zFraction = getzFractions().get(component.getName());
            //System.out.println("copyzfractions to liquid " + component.getName()+": "+ zFraction);
	    getLiquid().setFraction(component, zFraction);
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
	for (Compound component:getComponents()){
	    getVapor().setFraction(component, getzFractions().get(component.getName()));
	}
    }
    
    
    
//  public Double equilibriumRelation(Compound component, double temperature, double pressure) {
//	
//    }
//  
    
//    public Double equilibriumRelation(Substance pure, double temperature, double pressure){
//	double liquidFug = getLiquid().calculateFugacity(pure,temperature,pressure);
//           double vaporFug = getVapor().calculateFugacity(pure, temperature, pressure);     
//           return liquidFug/ vaporFug;
//    }
    public Double equilibriumRelation(Compound component){
	double liquidFug = getLiquid().calculateFugacityCoefficient(component);
	double vaporFug = getVapor().calculateFugacityCoefficient(component);
	return liquidFug/vaporFug;
    }
    

    public  HashMap<Compound,Double> equilibriumRelations ( ){
         HashMap<Compound,Double> equilibriumRelations  = new HashMap<>();
         
         for (Compound aComponent : components){
	    double equilRel = equilibriumRelation(aComponent);
           equilibriumRelations.put(aComponent, equilRel);
         }
         return equilibriumRelations;
    }
    public  double calculateSx(HashMap<Compound,Double> equilibriumRelations){
        
         double s = 0;
        for (Compound aComponent : components){
              double equilRel = equilibriumRelations.get(aComponent);
           s +=  getVapor().getReadOnlyFractions().get(aComponent)/equilRel;
            
        }
        
        return s;
    }
    
    public double calculateSy(HashMap<Compound,Double> equilibriumRelations){
        
         double s = 0;
        for (Compound aComponent : components){
              double equilRel = equilibriumRelations.get(aComponent);
           s += equilRel * getLiquid().getReadOnlyFractions().get(aComponent);
        }
        
        return s;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((alpha == null) ? 0 : alpha.hashCode());
		result = prime * result
				+ ((components == null) ? 0 : components.hashCode());
		result = prime * result
				+ ((equationOfState == null) ? 0 : equationOfState.hashCode());
		result = prime
				* result
				+ ((interactionParameters == null) ? 0 : interactionParameters
						.hashCode());
		result = prime * result
				+ ((mixingRule == null) ? 0 : mixingRule.hashCode());
		result = prime * result
				+ ((zFractions == null) ? 0 : zFractions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeterogeneousMixture other = (HeterogeneousMixture) obj;
		if (alpha == null) {
			if (other.alpha != null)
				return false;
		} else if (!alpha.equals(other.alpha))
			return false;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (equationOfState == null) {
			if (other.equationOfState != null)
				return false;
		} else if (!equationOfState.equals(other.equationOfState))
			return false;
		if (interactionParameters == null) {
			if (other.interactionParameters != null)
				return false;
		} else if (!interactionParameters.equals(other.interactionParameters))
			return false;
		if (mixingRule == null) {
			if (other.mixingRule != null)
				return false;
		} else if (!mixingRule.equals(other.mixingRule))
			return false;
		if (zFractions == null) {
			if (other.zFractions != null)
				return false;
		} else if (!zFractions.equals(other.zFractions))
			return false;
		return true;
	}

	public List<IterationInfo> getCalculationReport() {
		return calculationReport;
	}

	public void setCalculationReport(List<IterationInfo> calculationReport) {
		this.calculationReport = calculationReport;
	}

	public Integer getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(Integer maxIterations) {
		this.maxIterations = maxIterations;
	}

//    public NewtonMethodSolver getOptimizer() {
//        return optimizer;
//    }
//
//    public void setOptimizer(NewtonMethodSolver optimizer) {
//        this.optimizer = optimizer;
//    }
 
}
