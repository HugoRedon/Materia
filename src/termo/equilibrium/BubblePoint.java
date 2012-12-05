package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.eos.Cubic;
import static termo.equilibrium.EquilibriumFunctions.*;

/**
 *
 * @author Hugo Redon Rivera
 */
public class BubblePoint extends PhaseEquilibria{
   
    

    
    @Override
      public EquilibriaPhaseSolution getTemperature(
                double temperature,
              double pressure, 
              ArrayList<Component> components,
              HashMap<Component,Double> liquidFractions, 
              HashMap<Component,Double> vaporFractions,
              Cubic eos,
              BinaryInteractionParameters kinteraction,
              double tolerance){
        
        HashMap<Component,Double> K;
        double sy;
        double e = 100;
        double deltaT = 1;
        double T_;
        HashMap<Component,Double> k_;
        double Sy_;
        double e_;     
        if(components.size() == 1){
            liquidFractions = new HashMap<>();
            liquidFractions.put(components.get(0), 1.0);
            vaporFractions = new HashMap<>();
            vaporFractions.put(components.get(0), 1.0);
        }
        int count = 0;
        while(e >= tolerance || count == 1000){
            K =  equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sy = calculateSy(K, liquidFractions, components);
            e = Math.log(sy);
            T_ = temperature + deltaT;
            k_ = equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            Sy_ = calculateSy(k_, liquidFractions, components);
            e_ = Math.log(Sy_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
            K = equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sy = calculateSy(K, liquidFractions, components);
            vaporFractions = calculateNewYFractions(K, liquidFractions, components, sy);
        }
        return new EquilibriaPhaseSolution(temperature,pressure, liquidFractions,vaporFractions, count);  
    }
    
    @Override
  public EquilibriaPhaseSolution getPressureEstimate(
        double temperature,
        HashMap<Component,Double> liquidFractions)
  {
      HashMap<Component,Double> vaporPressures = new HashMap<>();
      double pressure= 0;
      int  iterations = 0;
      for( Component component : liquidFractions.keySet()){
          double vaporP =  EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);//pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
          vaporPressures.put(component, vaporP);
          pressure += vaporP * liquidFractions.get(component);  
      }
      HashMap<Component,Double>  vaporFractions = EquilibriumFunctions.getVaporFractionsRaoultsLaw(pressure, liquidFractions, vaporPressures);
      return new EquilibriaPhaseSolution(temperature,pressure,liquidFractions, vaporFractions, iterations);    
  }
    @Override
  public EquilibriaPhaseSolution getTemperatureEstimate(double pressure,
          HashMap<Component,Double> liquidFractions
          ){
      double temperature =  300;
      double calcPressure;
      double error = 100;
      double deltaT =1;
      double T_;
      double tol = 1e4;
      double calcP_;
      double error_;  
      HashMap<Component,Double> vaporFractions  = new HashMap<>();
      
      int iterations =0;
      while (error >tol  || iterations == 1000){
          iterations++;
            calcPressure = 0;
            calcP_ = 0;
            T_  = temperature + deltaT;
           for (Component component : liquidFractions.keySet() ){
               double vaporPressure = EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);
               double pi = vaporPressure * liquidFractions.get(component);
               calcPressure += pi;
               double vaporPressure_ = EquilibriumFunctions.getPureComponentVaporPressure(component, T_);
               double pi_ = vaporPressure_ * liquidFractions.get(component);
               calcP_  += pi_;
           }
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      for(Component component: liquidFractions.keySet()){
          double vp = EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);
          double yi = vp * liquidFractions.get(component) / pressure;
          vaporFractions.put(component, yi);
      }
      return new EquilibriaPhaseSolution(temperature, pressure,liquidFractions,vaporFractions, iterations);
  }
  
  @Override
    public EquilibriaPhaseSolution getPressure(
            double temperature,
            double p,
            ArrayList<Component> components,
            HashMap<Component,Double> liquidFractions,
            HashMap<Component,Double> vaporFractions,
            Cubic eos,
            BinaryInteractionParameters kinteraction,
            double tolerance){ 
    
      HashMap<Component,Double> k ;
      HashMap<Component,Double> k_;
      double sy;
      double sy_;
      double deltaP = 0.0001;
      double e = 10;
      double e_;
      double p_;
       
      if(components.size() == 1){
          liquidFractions = new HashMap<>();
          liquidFractions.put(components.get(0), 1.0);
          vaporFractions = new HashMap<>();
          vaporFractions.put(components.get(0), 1.0);
      } 
      
      int count = 0;
     
      while(Math.abs(e) > tolerance || count == 1000 ){         
            count++;
            k = equilibriumRelations(temperature, components, liquidFractions, p, vaporFractions, eos,kinteraction);
            sy = calculateSy(k, liquidFractions, components);
            e = sy -1;
            p_ = p * (1 + deltaP); 
            k_ = equilibriumRelations(temperature, components, liquidFractions, p_, vaporFractions, eos,kinteraction);
            sy_ = calculateSy(k_, liquidFractions, components);    
            e_ = sy_-1;
            p = ((p * p_ )* (e_ - e)) / ((p_ * e_) - (p * e));      
            k = equilibriumRelations(temperature, components, liquidFractions, p, vaporFractions, eos,kinteraction);
            sy = calculateSy(k, liquidFractions, components);    
            vaporFractions = calculateNewYFractions(k, liquidFractions, components, sy);          
      }  
      return new EquilibriaPhaseSolution(temperature,p,liquidFractions, vaporFractions, count);
    }
}
