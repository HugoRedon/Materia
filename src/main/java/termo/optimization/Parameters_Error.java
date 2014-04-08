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

    public Parameters_Error() {
    }

    public Parameters_Error(double[] parameters, double error) {
        this.parameters = parameters;
        this.error = error;
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
    
    
}
