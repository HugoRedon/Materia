package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public final class Soave_PengRobinsonAlpha extends Alpha_R{
   
    public Soave_PengRobinsonAlpha(){        
        setName(AlphaNames.Soave_PengRobinson);
    }
  
   
    @Override
     public double alpha(double temperature,Component component){
        
        double tr = temperature/component.getCriticalTemperature();
        
        
        return Math.pow( 1 + m(component)* (1 - Math.sqrt(tr)) , 2);
        
    }
      @Override
    public double TOverAlphaTimesDalpha(double temperature,Component component){
        
         double tr = temperature/component.getCriticalTemperature();  
        return (1d / Math.sqrt(alpha(temperature,component))) * (- m(component)  * Math.sqrt(tr));
    }
      
}
