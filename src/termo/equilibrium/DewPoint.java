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
public class DewPoint extends PhaseEquilibria{
    
    @Override
     public  EquilibriaPhaseSolution getTemperature(
             double temperature,
              double pressure, 
              ArrayList<Component> components,
              HashMap<Component,Double> vaporFractions,
              HashMap<Component,Double> liquidFractions, 
              Cubic eos,
              BinaryInteractionParameters kinteraction,
              double tolerance){
        
        HashMap<Component,Double> K;
        double sx;
        double e = 100;
        double deltaT = 0.1;
        double T_;
        HashMap<Component,Double> k_;
        double sx_;
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
            sx = calculateSx(K, vaporFractions, components);
            e = Math.log(sx);
            T_ = temperature + deltaT;
            k_ = equilibriumRelations(T_, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx_ = calculateSx(k_, vaporFractions, components);
            e_ = Math.log(sx_);
            temperature = temperature * T_ * (e_ - e) / (T_ * e_ - temperature * e);
            K = equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx = calculateSx(K, vaporFractions, components);
            liquidFractions = calculateNewXFractions(K, vaporFractions, components, sx);
        }
        return new EquilibriaPhaseSolution(temperature,pressure, vaporFractions,liquidFractions, count);
    }
    
    @Override
    public EquilibriaPhaseSolution getTemperatureEstimate(double pressure,
          HashMap<Component,Double> vaporFractions
          ){
      double temperature =  300;
      double calcPressure;
      double error = 100;
      double deltaT =1;
      double T_;
      double tol = 1e4;
      double calcP_;
      double error_;  
      
      double denominator_;
      double denominator;
      
      HashMap<Component,Double> liquidFractions  = new HashMap<>();
      
      int iterations =0;
      while (error >tol ){
          iterations++;
            calcPressure = 0;
            calcP_ = 0;
            denominator = 0;
            denominator_ = 0;
            
            T_  = temperature + deltaT;
           for (Component component : vaporFractions.keySet() ){
               double vaporPressure = EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);
               denominator += vaporFractions.get(component) / vaporPressure;
               
               double vaporPressure_ = EquilibriumFunctions.getPureComponentVaporPressure(component, T_);
               denominator_ += vaporFractions.get(component) / vaporPressure_;
           }
           
           calcPressure = 1/denominator;
           calcP_ = 1/ denominator_;
           
           error = Math.log(calcPressure / pressure);
           error_ = Math.log(calcP_ / pressure);
           temperature = (temperature * T_ *(error_ - error)) / (T_ * error_ - temperature * error);
      } 
      for(Component component: vaporFractions.keySet()){
          double vp = EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);
          double xi =  pressure *vaporFractions.get(component) /vp ;
          liquidFractions.put(component, xi);
      }
      return new EquilibriaPhaseSolution( temperature, pressure,liquidFractions,vaporFractions, iterations);
  }
     @Override
      public  EquilibriaPhaseSolution getPressureEstimate(
                double temperature,
                HashMap<Component,Double> vaporFractions
              ){        
      HashMap<Component,Double> vaporPressures = new HashMap<>();
      int  iterations = 0;
      double denominator=0;
      for( Component component : vaporFractions.keySet()){
            double vaporP =  EquilibriumFunctions.getPureComponentVaporPressure(component, temperature);//pc * Math.pow(10,(-7d/3d)* (1+w) * ((tc/temperature) - 1 ) );
            vaporPressures.put(component, vaporP);
            denominator += vaporFractions.get(component) / vaporP;
      }
     double pressure = 1/denominator;
     HashMap<Component,Double> liquidFractions = EquilibriumFunctions.getLiquidFractionsRaoultsLaw(pressure, vaporFractions, vaporPressures);
      return new EquilibriaPhaseSolution(temperature,pressure, liquidFractions,vaporFractions, iterations);
  }
     @Override
    public EquilibriaPhaseSolution getPressure(
             double temperature,
             double pressure,
            ArrayList<Component> components,
            HashMap<Component,Double> vaporFractions, 
             HashMap<Component,Double> liquidFractions,
            Cubic eos,
            BinaryInteractionParameters kinteraction,
            double tolerance){

      HashMap<Component,Double> K ;
      HashMap<Component,Double> K_;
      double sx;
      double sx_;
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
            K = equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx = calculateSx(K, vaporFractions, components);
            e = sx -1;
            p_ = pressure * (1 + deltaP);
            K_ = equilibriumRelations(temperature, components, liquidFractions, p_, vaporFractions, eos,kinteraction);
            sx_ = calculateSx(K_, vaporFractions, components);
            e_ = sx_-1;
            pressure =  pressure - e * (p_ - pressure)/ (e_ - e);//*/ ((pressure * p_ )* (e_ - e)) / ((p_ * e_) - (pressure * e));
            K = equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
            sx = calculateSx(K, vaporFractions, components);
            liquidFractions = calculateNewXFractions(K, vaporFractions, components, sx);
      }    
      return new EquilibriaPhaseSolution(temperature,pressure,vaporFractions, liquidFractions , count);
    }

    

}