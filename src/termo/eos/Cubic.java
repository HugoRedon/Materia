package termo.eos;

import termo.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class Cubic extends EOS{
  
    private double u ;
    private double w;
//    private double b;
//    private double a;
    private MixingRule mixingRule;
  
//    protected double get_a(){
//        return this.a;
//    }
//    protected void set_a(double a ){
//        this.a = a;
//    }
//    
//    protected double get_b(){
//        return this.b;
//    }
//    protected void set_b(double b){
//        this.b = b;
//    }
   
    protected double get_u(){
        return this.u;
    }
    protected void set_u(double u){
        this.u = u;
    }
    
    protected double get_w(){
        return this.w;
    }
    protected void set_w(double w){
        this.w = w;
    }
    
    public Cubic(){
        setDefaultMixingRule();
    }
    
    @Override
    public final boolean isCubic(){
        return true;
    }
   
    @Override
    public String getEquation(){
        return "P = RT/(v - b) - a / (v^2 + " + this.u + " b v + " + this.w +  " b^2) ";
    }
 
    
    /**
     *
     * @param temperature Temperature in Kelvin
     * @param volume Volumen in [mol / L]
     * @param components List of components of the mixture
     * @param fractions Mol fractions of the mixture
     * @return The calculated pressure with the current equation of state
     */
    public double getPressure(double temperature, 
            double volume,ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            BinaryInteractionParameters k
            ){    
        //needed before every calculation
        //calculateParameters(temperature,components,fractions);
        
        double a = get_a(temperature,components,fractions,k);
        double b = get_b(components,fractions);
        
        return ( Constants.R * temperature / (volume - b)    )
                - ( a / (Math.pow(volume ,  2 ) +  this.u  *  b  *  volume  +  this.w  *  Math.pow( b ,  2 ) ) ) ;
    }
    
    public boolean oneRoot(double pressure,
            double temperature,
            ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            BinaryInteractionParameters k
            ){
          //needed before every calculations
        //calculateParameters(temperature,components,fractions);       
        
        double a = get_a(temperature,components,fractions,k);
        double b = get_b(components,fractions);
        
        
        double A = a * pressure /Math.pow(Constants.R * temperature,2);
        double B = b * pressure / (Constants.R * temperature); 
                
        double alpha = 1-(this.u - 1 ) * B;
        double beta = A - this.u * B - this.u * Math.pow(B, 2) + this.w * Math.pow(B, 2);
        double gama = A*B + this.w * Math.pow(B,2) + this.w * Math.pow(B, 3);
        
        double C = 3 * beta - Math.pow( alpha , 2 );
        double D = - Math.pow( alpha , 3 ) + 4.5d * alpha * beta - 13.5 * gama;
        double Q = Math.pow(C, 3) + Math.pow( D , 2 );
        
        if(Q <= 0){
            return false;
        }else{
            return true;
        }
            
    }
    public double getCompresibilityFactor(double pressure,
            double temperature,
            ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            Phase aPhase,
            BinaryInteractionParameters k
            ){
          //needed before every calculations
        //calculateParameters(temperature,components,fractions);
        
        double a = get_a(temperature,components,fractions,k);
        double b = get_b(components,fractions);
        
        double A = a * pressure /Math.pow(Constants.R * temperature,2);
        double B = b * pressure / (Constants.R * temperature); 
                
        double alpha = 1-(this.u - 1 ) * B;
        double beta = A - this.u * B - this.u * Math.pow(B, 2) + this.w * Math.pow(B, 2);
        double gama = A*B + this.w * Math.pow(B,2) + this.w * Math.pow(B, 3);
        
        double C = 3 * beta - Math.pow(alpha, 2);
        double D = - Math.pow(alpha, 3) + 4.5d * alpha * beta - 13.5 * gama;
        double Q = Math.pow(C, 3) + Math.pow(D, 2);
        
        if(Q <= 0){
         //  if(oneRoot(pressure, temperature, components, fractions)){
            double theta = Math.acos(-D / Math.sqrt(- Math.pow(C, 3)));
            
            double liquidz = (1d/3d) * (alpha + 2 * Math.sqrt(-C) * Math.cos((theta / 3) + 120*(Math.PI / 180)));
            double vaporz = (1d/3d) * (alpha + 2 * Math.sqrt(-C) * Math.cos(theta / 3 ) );
            
            if(liquidz < B){
                liquidz = vaporz;
            }
            
            if(aPhase.equals(Phase.LIQUID)){
                return liquidz;
            }else{
                return vaporz;
            }
                
        }else {
           // Math.pow(negative number, fractional ) returns NaN
            
            
            double firstSum = -D + Math.sqrt(Q);
            double secondSum = -D - Math.sqrt(Q);
            
            double firstTerm = (firstSum < 0)? -Math.pow(-firstSum, 1d/3d): Math.pow(firstSum, 1d/3d);     
            double secondTerm= (secondSum < 0)? -Math.pow(-secondSum, 1d/3d): Math.pow(secondSum, 1d/3d);       
            
           // double z = (1d/3d) * (alpha + Math.pow(-D + Math.sqrt(Q), 1d/3) + Math.pow(- D - Math.sqrt(Q), 1d/3));
              double z = (1d/3d) * (alpha + firstTerm + secondTerm);
              return z;
        }
        
        

    }
   
//    /**
//     * Calculates the parameters of the equation of state at a given temperature in [K]
//     * @param aTemperature The temperature in [K] 
//     */
//    protected  void calculateParameters(double aTemperature,
//            ArrayList<Component> components,
//            HashMap<Component,Double> fractions){
//        if(components.size() > 1){
//            parametersWithMixingRule(aTemperature,components,fractions);
//        }else{
//            
//            parametersOneComponent(aTemperature,components.get(0));
//        }    
//    }
    
    protected double get_a(double temperature, 
              ArrayList<Component> components,
              HashMap<Component,Double> fractions,
              BinaryInteractionParameters k){
        
        
        boolean isMultiComponent = (fractions == null)? false: (fractions.size() > 1)? true:false;

        if(isMultiComponent){
            return get_aWithMixingRule(temperature,components,fractions,k);
        
        }else{
           
           
            return single_a(temperature, components.get(0));
        
        }    
        
    }
    protected double get_b(ArrayList<Component> components,
            HashMap<Component, Double> fractions){
           
        if(components.size() > 1){
            return get_bWithMixingRule(components,fractions);
 
        }else{
            return single_b(components.get(0));
    
        }    
        
    }
    private double get_bWithMixingRule(ArrayList<Component> components,HashMap<Component, Double> fractions){
        
        HashMap<Component,Double> single_bs = single_bs(components);
        
        //temporal
       // ArrayList<Component> components = (ArrayList) fractions.keySet();
        
        return  this.mixingRule.b(single_bs ,/*should change*/components,fractions);
       
    }
    
    private double get_aWithMixingRule(double temperature, 
            ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            BinaryInteractionParameters k
            ) {
        
        HashMap<Component,Double> single_as = single_as(components, temperature);
        
        //temporal
       // ArrayList<Component> components = (ArrayList) fractions.keySet();
        
        return  this.mixingRule.a(single_as ,/*should change*/components,fractions,k);
    }
  
    private HashMap<Component,Double> single_as(ArrayList<Component> components,double aTemperature){
        HashMap<Component, Double> singleAs = new HashMap<>();
       
        for(Component component : components){
            singleAs.put(component, single_a(aTemperature,component));
            
        }
        return singleAs;
    }
    private HashMap<Component,Double> single_bs(ArrayList<Component> components){
         HashMap<Component,Double> singleBs = new HashMap<>();
          for(Component component : components){
            
            singleBs.put(component,single_b( component));
        }
          return singleBs;
    }
    
//    protected void parametersWithMixingRule(double aTemperature,ArrayList<Component> components,HashMap<Component,Double> fractions){
//        setDefaultMixingRule();     
//        this.a = this.mixingRule.a(single_as(components, aTemperature),components,fractions);
//        this.b = this.mixingRule.b(single_bs(components),components,fractions);
//        
//    }
//    protected abstract void parametersOneComponent(double aTemperature,Component aComponent);
    
    private void setDefaultMixingRule(){
        if(mixingRule == null){
            this.mixingRule = new VDWMixingRule();
        }
    }
    public void setMixingRule(MixingRule newMixingRule){
        this.mixingRule = newMixingRule;
    }
   
    public double getVolume(double temperature, 
            double pressure,ArrayList<Component> components,
            HashMap<Component,Double> fractions,
            Phase aPhase,
            BinaryInteractionParameters k 
            ){
        double z = getCompresibilityFactor(pressure, temperature, components, fractions, aPhase,k);
        return z * Constants.R * temperature / pressure;
    }
   
    public double getTemperature(double pressure,double volume,ArrayList<Component> components){
       
        throw new UnsupportedOperationException("Not ready");
    }
    
    public double getFugacity(double pressure,
            double temperature,
            ArrayList<Component> components,
            Component component,
            HashMap<Component,Double> fractions,
            Phase aPhase,
            BinaryInteractionParameters k){
        
     //   calculateParameters(temperature, components, fractions);
        double a = get_a(temperature,components,fractions,k);
        double b = get_b(components,fractions);
        
      
        double z = getCompresibilityFactor(pressure, temperature, components, fractions, aPhase,k);
        double volume = getVolume(temperature, pressure, components, fractions, aPhase,k);
        
        double parcialb;
        double parciala;
        if(components.size() == 1){
            parcialb = 1;
            parciala = 2;
        }else{
             parcialb = single_b(component)/b;
             parciala  = this.mixingRule.oneOveraNParcialN2RespectN(single_as(components, temperature), 
                     components,
                     component,
                     fractions, 
                     k);
        }
        
       
        
        double delta = Math.sqrt(Math.pow(this.u,2) - 4 * this.w);
        double L;
        
         if( delta == 0 ){
             L = b / volume;
         }else{
             L = (1 / delta)* Math.log((2 * volume + (2 + delta) * b)/(2 * volume  + (2 - delta) * b));
         }
         
        
        double lnfug = -Math.log((volume-b)/volume) + (z-1) * (parcialb) + (a / (Constants.R * temperature * b))*(parcialb - parciala)* L - Math.log(z);
        return Math.exp(lnfug);

    }
  
    

    
    @Override
    public boolean needsComponents() {
        return true;
    }
    
//    public boolean needsMixingRule(){  
//        isReadyToCalculate();
//        return needsMixingRule;
//    }

//    protected abstract void initialize();  
    
//    @Override public boolean isReadyToCalculate( ){
//        
//        //TODO:Check if i can calculate
//         if(components.size() > 1 ){
//            needsMixingRule = true;
//            if(mixingRule == null){
//                readyToCalculate = false;
//            }
//        }else{
//            needsMixingRule = false;
//        }       
//        return readyToCalculate;
//    }
   
    protected abstract double single_a(double aTemperature,Component aComponent);    
    protected abstract double single_b(Component aComponent);



}
