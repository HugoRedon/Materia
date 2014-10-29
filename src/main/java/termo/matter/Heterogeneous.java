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
    
    double vF;
    
    public double calculateEnthalpy(){
    	//flash for mixture before this calculation;
    	double vaporEnthalpy  =vF *vapor.calculateEnthalpy();
    	double liquidEnthalpy = (1-vF)*liquid.calculateEnthalpy();
    	return vaporEnthalpy + liquidEnthalpy;
    }
    
    public double calculateEntropy(){
    	double vaporEntropy = vF*vapor.calculateEntropy();
    	double liquidEntropy = (1-vF) *liquid.calculateEntropy();
    	
    	return vaporEntropy + liquidEntropy;
    }
    
    public double calculateGibbs(){
    	double vaporGibbs = vF*vapor.calculateGibbs();
    	double liquidGibbs = (1-vF) *liquid.calculateGibbs();
    	
    	return vaporGibbs + liquidGibbs;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((liquid == null) ? 0 : liquid.hashCode());
		long temp;
		temp = Double.doubleToLongBits(pressure);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vapor == null) ? 0 : vapor.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Heterogeneous other = (Heterogeneous) obj;
		if (liquid == null) {
			if (other.liquid != null)
				return false;
		} else if (!liquid.equals(other.liquid))
			return false;
		if (Double.doubleToLongBits(pressure) != Double
				.doubleToLongBits(other.pressure))
			return false;
		if (Double.doubleToLongBits(temperature) != Double
				.doubleToLongBits(other.temperature))
			return false;
		if (vapor == null) {
			if (other.vapor != null)
				return false;
		} else if (!vapor.equals(other.vapor))
			return false;
		return true;
	}
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
    
    
 
//    
//    protected abstract void bubblePressureEstimate();
//    protected abstract int bubbleTemperatureEstimate();
//    protected abstract int bubbleTemperature();
//    protected abstract int bubblePressureImpl();
//    
//    
//    
//   
//    
//
//    protected abstract void dewPressureEstimate();
//    protected abstract int dewTemperatureEstimate();
//    protected abstract int dewPressureImpl();
//    protected abstract int dewTemperature();
//    
    
    public abstract Homogeneous getLiquid();
    public abstract Homogeneous getVapor();

	public double getvF() {
		return vF;
	}

	public void setvF(double vF) {
		this.vF = vF;
	}
}
