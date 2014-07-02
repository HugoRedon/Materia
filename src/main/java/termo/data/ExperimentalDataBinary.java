    package termo.data;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hugo Redon Rivera
 */
    @Entity
public class ExperimentalDataBinary extends Experimental{
        
            
    private double liquidFraction;
    private double vaporFraction;
//            private double temperature;
//            private double pressure;

    @ManyToOne(fetch = FetchType.EAGER)
    private ExperimentalDataBinaryList experimentalDataList;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.liquidFraction) ^ (Double.doubleToLongBits(this.liquidFraction) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.vaporFraction) ^ (Double.doubleToLongBits(this.vaporFraction) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.experimentalDataList);
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
        final ExperimentalDataBinary other = (ExperimentalDataBinary) obj;
        if (Double.doubleToLongBits(this.liquidFraction) != Double.doubleToLongBits(other.liquidFraction)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vaporFraction) != Double.doubleToLongBits(other.vaporFraction)) {
            return false;
        }
        if (!Objects.equals(this.experimentalDataList, other.experimentalDataList)) {
            return false;
        }
        return true;
    }

  


    public ExperimentalDataBinary() {
    }

    public ExperimentalDataBinary(double temperature, double pressure, double liquidFraction, double vaporFraction){
        this.temperature = temperature;
        this.pressure = pressure;
        this.liquidFraction = (liquidFraction);

        this.vaporFraction =  (vaporFraction);
    }



    public void setLiquidFraction(double liquidFraction){
        this.liquidFraction =  (liquidFraction);
    }
    public double getLiquidFraction(){
        return liquidFraction;
    }


    public void setVaporFraction(double vaporFraction){
        this.vaporFraction =  (vaporFraction);
    }
    public double getVaporFraction(){
        return vaporFraction;
    }

    /**
     * @return the experimentalDataList
     */
    public ExperimentalDataBinaryList getExperimentalDataList() {
        return experimentalDataList;
    }

    /**
     * @param experimentalDataList the experimentalDataList to set
     */
    public void setExperimentalDataList(ExperimentalDataBinaryList experimentalDataList) {
        this.experimentalDataList = experimentalDataList;
    }

          
}
