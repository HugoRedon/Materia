package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.phase.Phase;

/**
 *
 * @author Hugo Redon Rivera
 */
public class EquilibriumFunctions {
        public static HashMap<Component,Double> equilibriumRelations (double temperature,
            ArrayList<Component> components,
            HashMap<Component,Double> liquidFractions,
            double pressure,
            HashMap<Component,Double> vaporFractions , 
            Cubic eos,
            BinaryInteractionParameter k
            ){
        throw new UnsupportedOperationException("Not supported yet.");
    
//         HashMap<Component,Double> equilibriumRelations  = new HashMap<>();
//         
//         for (Component aComponent : components){
//           
//           double liquidFug = eos.calculateFugacity(pressure, temperature, components, aComponent, liquidFractions, Phase.LIQUID,k);
//           double vaporFug = eos.calculateFugacity(pressure, temperature, components, aComponent, vaporFractions, Phase.VAPOR,k);          
//           
//           double equilRel = liquidFug/ vaporFug;
//           
//           equilibriumRelations.put(aComponent, equilRel);
//            
//         }
//         return equilibriumRelations;
    }
        
public static HashMap<Component,Double> getLiquidFractionsRaoultsLaw(double pressure,
        HashMap<Component,Double> vaporFractions,
        HashMap<Component,Double> vaporPressures){
    
        HashMap<Component,Double> liquidFractions = new HashMap<>();
        
        for( Component component : vaporFractions.keySet()){
            double x =  vaporFractions.get(component)*pressure/vaporPressures.get(component) ;
            liquidFractions.put(component, x);  
        }   
        return liquidFractions;
}

public static HashMap<Component,Double> getVaporFractionsRaoultsLaw(double pressure,
        HashMap<Component,Double> liquidFractions,
        HashMap<Component,Double> vaporPressures){
    
        HashMap<Component,Double> vaporFractions = new HashMap<>();
        
      for( Component component : liquidFractions.keySet()){
          double y = vaporPressures.get(component) * liquidFractions.get(component)/pressure;
          vaporFractions.put(component, y);  
      }
        
        return vaporFractions;
    
}
        

    
        
        
    
          
}
