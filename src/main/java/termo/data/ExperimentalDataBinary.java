    package termo.data;

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
