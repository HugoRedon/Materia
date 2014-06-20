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
    private double experimentalVaporFraction;
    
    private double calculatedVaporFraction;
    private double experimentalTemperature; 
    private double calculatedTemperature;
    private double relativeError;
    
//    TemperatureMixtureErrorData(double liquidFraction, double tempExp, double tempCalc) {
//        this.liquidFraction= liquidFraction;
//        this.experimentalTemperature = tempExp;
//        this.calculatedTemperature = tempCalc;
//    }

    public TemperatureMixtureErrorData(double liquidFraction, double experimentalVaporFraction, double calculatedVaporFraction, double experimentalTemperature, double calculatedTemperature,
            double relativeError) {
        this.liquidFraction = liquidFraction;
        this.experimentalVaporFraction = experimentalVaporFraction;
        this.calculatedVaporFraction = calculatedVaporFraction;
        this.experimentalTemperature = experimentalTemperature;
        this.calculatedTemperature = calculatedTemperature;
        this.relativeError = relativeError;
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

    /**
     * @return the experimentalVaporFraction
     */
    public double getExperimentalVaporFraction() {
        return experimentalVaporFraction;
    }

    /**
     * @param experimentalVaporFraction the experimentalVaporFraction to set
     */
    public void setExperimentalVaporFraction(double experimentalVaporFraction) {
        this.experimentalVaporFraction = experimentalVaporFraction;
    }

    /**
     * @return the calculatedVaporFraction
     */
    public double getCalculatedVaporFraction() {
        return calculatedVaporFraction;
    }

    /**
     * @param calculatedVaporFraction the calculatedVaporFraction to set
     */
    public void setCalculatedVaporFraction(double calculatedVaporFraction) {
        this.calculatedVaporFraction = calculatedVaporFraction;
    }

    /**
     * @return the relativeError
     */
    public double getRelativeError() {
        return relativeError;
    }

    /**
     * @param relativeError the relativeError to set
     */
    public void setRelativeError(double relativeError) {
        this.relativeError = relativeError;
    }
    
}
