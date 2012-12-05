package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public final class Mathias_StryjekVera extends Alpha_R {
   

    public Mathias_StryjekVera(){
        setName( AlphaNames.Mathias_StryjekVera);
    }
    @Override
    public double alpha(double temperature,Component component) {
         
        double criticalTemperature = component.getCriticalTemperature();
        double q = component.getPrsvk1();
        
        double reducedTemperature = temperature / criticalTemperature;
      
        if(reducedTemperature <= 1){
            double calc = 1 + m(component)* (1 - Math.sqrt(reducedTemperature)) 
                    +q*(1-reducedTemperature)*(0.7-reducedTemperature) ;
            return Math.pow( calc, 2);
        }else{
            // Mathias 
            double c = c(component);
            double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
            return Math.exp(calc);
            //stryjek and vera is diferent
        }
    }   
    
    @Override
    public double TOverAlphaTimesDalpha(double temperature,Component component){
        
        double tr = temperature /component.getCriticalTemperature();      
        double q = component.getPrsvk1();
        
        if(tr <= 1){
            
            return (1d/ Math.sqrt(alpha(temperature,component)))*(- m(component) * Math.sqrt(tr) 
                    + q * ( 3.4*tr - 4*Math.pow(tr, 2) ));
        }else{
            //revisar
            
            double c = c(component);
            return -2 * (c-1) * (Math.pow(tr,c));
        }
        
        
    }

 
    private double c(Component component){
        double q = component.getPrsvk1();
        return 1+0.5*m(component)+0.3*q;
    }
   
}
