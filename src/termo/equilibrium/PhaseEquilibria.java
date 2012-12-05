package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.BinaryInteractionParameters;
import termo.component.Component;
import termo.eos.Cubic;

/**
 *
 * @author Hugo Redon Rivera
 */
public  abstract class  PhaseEquilibria {
     public abstract EquilibriaPhaseSolution getTemperature(
             double temperatureEstimate,
              double pressureEstimate, 
              ArrayList<Component> components,
              HashMap<Component,Double> mixtureFractionsZ, 
              HashMap<Component,Double> estimationFractions,
              Cubic eos,
              BinaryInteractionParameters kinteraction,
              double tolerance);
    
    public EquilibriaPhaseSolution getTemperature(
            double pressure,
            HashMap<Component,Double> liquidFractions,
            ArrayList<Component> components,
            Cubic eos,
            BinaryInteractionParameters kinteraction,
            double tolerance
            ){
        
       EquilibriaPhaseSolution estimate =  getTemperatureEstimate(pressure, liquidFractions);
        return getTemperature(estimate, components, eos, kinteraction, tolerance);
    }
  
    public EquilibriaPhaseSolution getTemperature(
            EquilibriaPhaseSolution estimate,
            ArrayList<Component> components,
            Cubic eos,
            BinaryInteractionParameters kinteraction,
            double tolerance
            ){
        double temperature = estimate.getTemperature();
        double pressure =estimate.getPressure();
        HashMap<Component,Double> liquidFractions = estimate.getMixtureFractions();
        HashMap<Component,Double> vaporFractions = estimate.getSolutionFractions();
        
        
       return  getTemperature(temperature, pressure, components, liquidFractions, vaporFractions, eos, kinteraction, tolerance);
    }
    
     public abstract EquilibriaPhaseSolution getPressure(double temperature,
             double pressure,
            ArrayList<Component> components,
            HashMap<Component,Double> mixtureFractionsZ,
            HashMap<Component,Double> estimationFractions, 
            Cubic eos,
            BinaryInteractionParameters kinteraction,
            double tolerance);
     
       public abstract EquilibriaPhaseSolution getPressureEstimate(
            double temperature,
            HashMap<Component,Double> mixtureFractionsZ
          );
       public abstract EquilibriaPhaseSolution getTemperatureEstimate(
               double pressure,
                HashMap<Component,Double> mixtureFractionsZ
          );
       
 
     public ArrayList<EquilibriaPhaseSolution> getTemperatureDiagram(
            double pressure, 
            Component component1,
            Component component2, 
            BinaryInteractionParameters kinteraction,
            Cubic eos,
            double tolerance) {
        return getTemperatureDiagram(pressure, component1,component2, kinteraction, eos,100, tolerance);
     
    }

    
     public ArrayList<EquilibriaPhaseSolution> getTemperatureDiagram(
             double pressure, 
             Component component1,
             Component component2,
             BinaryInteractionParameters kinteraction, 
             Cubic eos, 
             int numberCalculations,
             double tolerance) {
         ArrayList<Component> components2 = new ArrayList<>();
         components2.add(component1);
         components2.add(component2);
        
        
        ArrayList<EquilibriaPhaseSolution> result = new ArrayList<>();
        
        double step = 1d / numberCalculations;
        
        for(double i= 0; i <=1; i += step){
            HashMap<Component,Double> solutionFractions = new HashMap<>();
            solutionFractions.put(component1, i);
            solutionFractions.put(component2, 1-i);
            
            result.add(getTemperature(pressure, solutionFractions, components2,eos , kinteraction, tolerance));
           
        }

        return result;
        
    }
}
