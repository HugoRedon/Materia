package termo.data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalData extends Experimental {
//    private double temperature;
//    private double pressure;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = false)
    private ExperimentalDataList dataList;

    public ExperimentalData(){
        
    }

    public ExperimentalData( double temperature, double pressure) {
        super(temperature, pressure);
     
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
