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
public class Parameters_Error {
    double[] parameters;
    double error;
    private double gradientAbsSum;
    int iteration;

    public Parameters_Error() {
    }

    public Parameters_Error(double[] parameters, double error, int iteration, double gradientAbsSum) {
        this.parameters = parameters;
        this.error = error;
        this.iteration = iteration;
        this.gradientAbsSum = gradientAbsSum;
    }

    
    
    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }

    public double[] getParameters() {
        return parameters;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    /**
     * @return the gradientAbsSum
     */
    public double getGradientAbsSum() {
        return gradientAbsSum;
    }

    /**
     * @param gradientAbsSum the gradientAbsSum to set
     */
    public void setGradientAbsSum(double gradientAbsSum) {
        this.gradientAbsSum = gradientAbsSum;
    }
    
    
}
