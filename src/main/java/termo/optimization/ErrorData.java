/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.optimization;

/**
 *
 * @author Hugo
 */
public class ErrorData{
    private double experimentalPressure;
    private double calculatedPressure;
    private double error;
    private double temperature;
    
    public ErrorData(double exp, double calc, double error, double temperature){
        this.experimentalPressure = exp;
        this.calculatedPressure = calc;
        this.error = error;
        this.temperature = temperature;
    }

    /**
     * @return the experimentalPressure
     */
    public double getExperimentalPressure() {
        return experimentalPressure;
    }

    /**
     * @param experimentalPressure the experimentalPressure to set
     */
    public void setExperimentalPressure(double experimentalPressure) {
        this.experimentalPressure = experimentalPressure;
    }

    /**
     * @return the calculatedPressure
     */
    public double getCalculatedPressure() {
        return calculatedPressure;
    }

    /**
     * @param calculatedPressure the calculatedPressure to set
     */
    public void setCalculatedPressure(double calculatedPressure) {
        this.calculatedPressure = calculatedPressure;
    }

    /**
     * @return the error
     */
    public double getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(double error) {
        this.error = error;
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
}