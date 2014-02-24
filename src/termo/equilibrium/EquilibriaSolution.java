package termo.equilibrium;

import java.util.HashMap;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class EquilibriaSolution {
    private double Temperature;
    private double pressure;
    private int iterations;
    private double vFRelation;
    
    private double estimateTemperature;
    
    
    
   
    
    public EquilibriaSolution(){
	
    }
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
    public Double getTemperature() {
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
    public Integer getIterations() {
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
    public Double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * @return the vFRelation
     */
    public Double getvFRelation() {
	return vFRelation;
    }

    /**
     * @param vFRelation the vFRelation to set
     */
    public void setvFRelation(Double vFRelation) {
	this.vFRelation = vFRelation;
    }

//    /**
//     * @return the estimateSolution
//     */
//    public EstimateSolution getEstimateSolution() {
//	return estimateSolution;
//    }
//
//    /**
//     * @param estimateSolution the estimateSolution to set
//     */
//    public void setEstimateSolution(EstimateSolution estimateSolution) {
//	this.estimateSolution = estimateSolution;
//    }

    /**
     * @return the estimateTemperature
     */
    public double getEstimateTemperature() {
	return estimateTemperature;
    }

    /**
     * @param estimateTemperature the estimateTemperature to set
     */
    public void setEstimateTemperature(double estimateTemperature) {
	this.estimateTemperature = estimateTemperature;
    }
        
}

