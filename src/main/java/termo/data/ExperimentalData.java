package termo.data;

/**
 *
 * @author Hugo
 */
public class ExperimentalData {
    private double temperature;
    private double pressure;

    public ExperimentalData(){
        
    }
    public ExperimentalData(double temperature, double pressure){
        this.temperature = temperature;
        this.pressure = pressure;
    }
    
    /**
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
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
