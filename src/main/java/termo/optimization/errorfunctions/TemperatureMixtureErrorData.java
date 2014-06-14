/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.optimization.errorfunctions;

/**
 *
 * @author Hugo
 */
public class TemperatureMixtureErrorData {
    private double liquidFraction;
    private double experimentalTemperature; 
    private double calculatedTemperature;
    TemperatureMixtureErrorData(double liquidFraction, double tempExp, double tempCalc) {
        this.liquidFraction= liquidFraction;
        this.experimentalTemperature = tempExp;
        this.calculatedTemperature = tempCalc;
    }

    /**
     * @return the liquidFraction
     */
    public double getLiquidFraction() {
        return liquidFraction;
    }

    /**
     * @param liquidFraction the liquidFraction to set
     */
    public void setLiquidFraction(double liquidFraction) {
        this.liquidFraction = liquidFraction;
    }

    /**
     * @return the experimentalTemperature
     */
    public double getExperimentalTemperature() {
        return experimentalTemperature;
    }

    /**
     * @param experimentalTemperature the experimentalTemperature to set
     */
    public void setExperimentalTemperature(double experimentalTemperature) {
        this.experimentalTemperature = experimentalTemperature;
    }

    /**
     * @return the calculatedTemperature
     */
    public double getCalculatedTemperature() {
        return calculatedTemperature;
    }

    /**
     * @param calculatedTemperature the calculatedTemperature to set
     */
    public void setCalculatedTemperature(double calculatedTemperature) {
        this.calculatedTemperature = calculatedTemperature;
    }
    
}
