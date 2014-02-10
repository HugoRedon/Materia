package termo.equilibrium;

/**
 *
 * @author Hugo Redon Rivera
 */
public class EquilibriaSolution {
    private double Temperature;
    private double pressure;
    private int iterations;
    
    public EquilibriaSolution( 
	    double temperature, 
	    double pressure,
	    int iterations){
	this.Temperature = temperature;
	this.pressure = pressure;
	this.iterations = iterations;
    }
    
    /**
     * @return the pressure
     */
    public double getTemperature() {
	return Temperature;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setTemperature(double pressure) {
	this.Temperature = pressure;
    }
    
    /**
     * @return the iterations
     */
    public int getIterations() {
	return iterations;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
	this.iterations = iterations;
    }
    
    /**
     * @return the pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
        
}

