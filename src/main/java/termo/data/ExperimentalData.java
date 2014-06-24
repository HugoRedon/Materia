package termo.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalData implements Serializable {
    private double temperature;
    private double pressure;
    @Id
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
