package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
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
              BinaryInteractionParameter kinteraction,
              double tolerance);
    
    public EquilibriaPhaseSolution getTemperature(
            double pressure,
            HashMap<Component,Double> mixtureFracions,
            ArrayList<Component> components,
            Cubic eos,
            BinaryInteractionParameter kinteraction,
            double tolerance
            ){
        
       EquilibriaPhaseSolution estimate =  getTemperatureEstimate(pressure, mixtureFracions);
        return getTemperature(estimate, components, eos, kinteraction, tolerance);
    }
  
    public EquilibriaPhaseSolution getTemperature(
            EquilibriaPhaseSolution estimate,
            ArrayList<Component> components,
            Cubic eos,
            BinaryInteractionParameter kinteraction,
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
            BinaryInteractionParameter kinteraction,
            double tolerance);
     
      private EquilibriaPhaseSolution getPressure(double temperature, 
              HashMap<Component, Double> mixtureFractions, 
              ArrayList<Component> components2, 
              Cubic cubic, 
              BinaryInteractionParameter kinteraction, 
              double tol) {
          
          EquilibriaPhaseSolution estimate = getPressureEstimate(temperature, mixtureFractions);
          return getPressure(temperature, estimate, components2, cubic, kinteraction, tol);
    }

      
           private EquilibriaPhaseSolution getPressure(double temperature, 
                   EquilibriaPhaseSolution estimate,
                ArrayList<Component> components,
               Cubic eos,
                BinaryInteractionParameter kinteraction,
              double tolerance
            ){
               double pressure = estimate.getPressure();
               HashMap<Component,Double> mixtureFractions = estimate.getMixtureFractions();
               HashMap<Component,Double> solutionFractions = estimate.getSolutionFractions();
               
               return getPressure(temperature, pressure, components, mixtureFractions, solutionFractions, eos, kinteraction, tolerance);
           }
     
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
            BinaryInteractionParameter kinteraction,
            Cubic eos,
            double tolerance) {
        return getTemperatureDiagram(pressure, component1,component2, kinteraction, eos,101, tolerance);
    
}
//Todo getTemperatureDiagram getPresureDiagram seems to be repetitive
    
     public ArrayList<EquilibriaPhaseSolution> getTemperatureDiagram(
             double pressure, 
             Component component1,
             Component component2,
             BinaryInteractionParameter kinteraction, 
             Cubic eos, 
             int numberCalculations,
             double tolerance) {
         ArrayList<Component> components2 = new ArrayList<>();
         components2.add(component1);
         components2.add(component2);
        
        
        ArrayList<EquilibriaPhaseSolution> result = new ArrayList<>();
        
        double step = 1d / numberCalculations;
        
        for(double i= 0; i <= 1; i += step){
            HashMap<Component,Double> mixtureFracions = new HashMap<>();
            mixtureFracions.put(component1, i);
            mixtureFracions.put(component2, 1-i);
            
            result.add(getTemperature(pressure, mixtureFracions, components2,eos , kinteraction, tolerance));
           
        }

        return result;
        
    }

    public ArrayList<EquilibriaPhaseSolution> getPressureDiagram(double temperature, 
            Component component1,
            Component component2, 
            BinaryInteractionParameter kinteraction, 
            Cubic cubic, 
            int numberCalculations,
            double tol
            ) {
             ArrayList<Component> components2 = new ArrayList<>();
         components2.add(component1);
         components2.add(component2);
        
        
        ArrayList<EquilibriaPhaseSolution> result = new ArrayList<>();
        
        double step = 1d / numberCalculations;
        
        for(double i= 0; i <= 1; i += step){
            HashMap<Component,Double> solutionFractions = new HashMap<>();
            solutionFractions.put(component1, i);
            solutionFractions.put(component2, 1-i);
            
            result.add(getPressure(temperature, solutionFractions, components2,cubic , kinteraction, tol));
           
        }
        return result;
    }
       public ArrayList<EquilibriaPhaseSolution> getPressureDiagram(double temperature, 
            Component component1,
            Component component2, 
            BinaryInteractionParameter kinteraction, 
            Cubic cubic, 
            double tol
            ) {
           return getPressureDiagram(temperature, component1, component2, kinteraction, cubic, 101, tol);
       }

   
       
       public ArrayList<EquilibriaPhaseSolution> getPhaseEnvelope(
               ArrayList<Component> components,
               HashMap<Component,Double> mixtureFractions,
               BinaryInteractionParameter kinteraction,
               Cubic cubic,
               double tol
               ){
           ArrayList<EquilibriaPhaseSolution> phaseEnvelope = new ArrayList<>();
           
          
           
           return phaseEnvelope;
       }
}
