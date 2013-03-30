package termo.eos;

import termo.Constants;

/**
 *
 * @author Hugo Redon Rivera
 */
public class IdealGas extends EOS{

    public IdealGas(){
        setName( "Gas ideal");
    }
    
 
    public  double getPressure(double temperature, double volume){       
        return Constants.R * temperature / volume;
    }  
  
    public double getVolume(double pressure, double temperature){        
       return Constants.R * temperature / pressure;     
    }  
   
    public double getTemperature(double pressure, double volume){
        return pressure * volume / Constants.R;
    }


    @Override
    public String getEquation() {
        return "PV = nRT";
    }
//    @Override
//    public boolean isReadyToCalculate() {
//        return true;
//    }

   

    public double getCompresibilityFactor() {
       
        return 1;
    }
    public double getFugacity(){
        return 1;
    }
    


    
}

