package termo.data;

import java.util.Objects;
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
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ExperimentalDataList dataList;

    public ExperimentalData(){
        
    }

    public ExperimentalData( double temperature, double pressure) {
        super(temperature, pressure);
     
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.dataList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExperimentalData other = (ExperimentalData) obj;
        if (!Objects.equals(this.dataList, other.dataList)) {
            return false;
        }
        return true;
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
