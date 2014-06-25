package termo.data;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalData implements Serializable {
    private double temperature;
    private double pressure;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = false)
    private ExperimentalDataList dataList;

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

    /**
     * @return the dataList
     */
    public ExperimentalDataList getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(ExperimentalDataList dataList) {
        this.dataList = dataList;
    }
}
