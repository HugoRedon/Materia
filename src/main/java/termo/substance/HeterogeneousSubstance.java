/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

/**
 *
 * @author
 * Hugo
 */
public abstract class HeterogeneousSubstance {
    
    protected  HomogeneousSubstance liquid;
    protected HomogeneousSubstance vapor;
    
    protected double temperature;
    protected double pressure;
    
//    
//    public abstract class SubstancePhase {
//	
//	double temperature = this.temperature;
//	double pressure = this.pressure;
//    }
//   
    
    public void setTemperature(double temperature){
	this.temperature = temperature;
	vapor.setTemperature(temperature);
	liquid.setTemperature(temperature);
	
    }
    public void setPressure(double pressure){
	this.pressure = pressure;
	vapor.setPressure(pressure);
	liquid.setPressure(pressure);
    }
    public double getTemperature(){
	return temperature;
    }
    public double getPressure(){
	return pressure;
    }
    
    
    
    public final void bubblePressureEstimate(double temperature){
	setTemperature(temperature);
	bubblePressureEstimate();
    }
    public final int bubbleTemperature(double pressure){
	setPressure(pressure);
	return bubbleTemperature();
    }
    public final  int bubbleTemperatureEstimate(double pressure){
	setPressure(pressure);
	return bubbleTemperatureEstimate();
    }
    public final int bubblePressure(double temperature){
	setTemperature(temperature);
	return bubblePressure();
    }
    
    protected abstract void bubblePressureEstimate();
    protected abstract int bubbleTemperatureEstimate();
    protected abstract int bubbleTemperature();
    protected abstract int bubblePressure();
    
    
    
    
    public final void  dewPressureEstimate(double temperature){
	setTemperature(temperature);
	dewPressureEstimate();
    }
    public final int dewTemperatureEstimate(double pressure){
	setPressure(pressure);
	return dewTemperatureEstimate();
    }
    public final int dewPressure(double temperature){
	setTemperature(temperature);
	return dewPressure();
    }
    public final int dewTemperature(double pressure){
	setPressure(pressure);
	return dewTemperature();
    }
    
    protected abstract void dewPressureEstimate();
    protected abstract int dewTemperatureEstimate();
    protected abstract int dewPressure();
    protected abstract int dewTemperature();
    
    
    public abstract HomogeneousSubstance getLiquid();
    public abstract HomogeneousSubstance getVapor();
}
