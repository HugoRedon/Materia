package termo.matter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author
 * Hugo
 */
public abstract class Heterogeneous implements PropertyChangeListener{
    
    protected  Homogeneous liquid;
    protected Homogeneous vapor;
    
    protected double temperature;
    protected double pressure;
    
    PropertyChangeSupport mpcs = new PropertyChangeSupport(this);

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "temperature":
                setTemperature((double)evt.getNewValue());
                break;
            case "pressure":
                setPressure((double)evt.getNewValue());
                break;
            case "phase":
                //do nothing we dont want to change phase property. They are already set and not changing
                break;
            default:
                mpcs.firePropertyChange(evt);
                break;
        }
        
    }
    
    
    public void addPropertyChangeListener(PropertyChangeListener listener){
        mpcs.addPropertyChangeListener(listener);
    }
    
    
    public Heterogeneous(){
       
        
    }

    public void setTemperature(double temperature){
        double oldTemperature = this.temperature;
	this.temperature = temperature;
	mpcs.firePropertyChange("temperature", oldTemperature, temperature);
	
    }
    public void setPressure(double pressure){
	double oldPressure = this.pressure;
        this.pressure = pressure;
	mpcs.firePropertyChange("pressure", oldPressure, pressure);
    }
    public double getTemperature(){
	return temperature;
    }
    public double getPressure(){
	return pressure;
    }
    
    
    
//    public final void bubblePressureEstimate(double temperature){
//	setTemperature(temperature);
//	bubblePressureEstimate();
//    }
//    public final int bubbleTemperature(double pressure){
//	setPressure(pressure);
//	return bubbleTemperature();
//    }
//    public final  int bubbleTemperatureEstimate(double pressure){
//	setPressure(pressure);
//	return bubbleTemperatureEstimate();
//    }
//    public final int bubblePressure(double temperature){
//	setTemperature(temperature);
//	return bubblePressure();
//    }
    
    protected abstract void bubblePressureEstimate();
    protected abstract int bubbleTemperatureEstimate();
    protected abstract int bubbleTemperature();
    protected abstract int bubblePressure();
    
    
    
    
//    public final void  dewPressureEstimate(double temperature){
//	setTemperature(temperature);
//	dewPressureEstimate();
//    }
//    public final int dewTemperatureEstimate(double pressure){
//	setPressure(pressure);
//	return dewTemperatureEstimate();
//    }
//    public final int dewPressure(double temperature){
//	setTemperature(temperature);
//	return dewPressure();
//    }
//    public final int dewTemperature(double pressure){
//	setPressure(pressure);
//	return dewTemperature();
//    }
    
    protected abstract void dewPressureEstimate();
    protected abstract int dewTemperatureEstimate();
    protected abstract int dewPressure();
    protected abstract int dewTemperature();
    
    
    public abstract Homogeneous getLiquid();
    public abstract Homogeneous getVapor();
}
