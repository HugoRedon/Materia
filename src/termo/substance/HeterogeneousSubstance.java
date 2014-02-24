/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import termo.equilibrium.EquilibriaSolution;

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
    
    
    public void setTemperature(double temperature){
	
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
    
    public abstract void bubblePressureEstimate();
    public abstract int bubbleTemperatureEstimate();
    public abstract int bubbleTemperature();
    public abstract int bubblePressure();
    
    
    
    
    public final void  dewPressureEstimate(double temperature){
	setTemperature(temperature);
	dewPressureEstimate();
    }
    
    public abstract void dewPressureEstimate();
    
    
    
    public abstract HomogeneousSubstance getLiquid();
    public abstract HomogeneousSubstance getVapor();
}
