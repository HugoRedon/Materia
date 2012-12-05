package termo.equilibrium;

import java.util.HashMap;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class EquilibriaPhaseSolution {
     private double Temperature;
     private double pressure;
     private HashMap<Component,Double> solutionFractions;
        private HashMap<Component,Double> mixtureFractions;
        private int iterations;
        
        public EquilibriaPhaseSolution(
                double temperature, 
                double pressure,
                HashMap<Component,Double> zFraction, 
                HashMap<Component,Double> solutionFractions,
                int iterations){
            
            this.Temperature = temperature;
            this.pressure = pressure;
            this.mixtureFractions = zFraction;
            this.solutionFractions = solutionFractions;
            this.iterations = iterations;
        }

        /**
         * @return the pressure
         */
        public double getTemperature() {
            return Temperature;
        }

        /**
         * @param pressure the pressure to set
         */
        public void setTemperature(double pressure) {
            this.Temperature = pressure;
        }

        /**
         * @return the vaporFractions
         */
        public HashMap<Component,Double> getMixtureFractions() {
            return mixtureFractions;
        }

        /**
         * @param vaporFractions the vaporFractions to set
         */
        public void setMixtureFractions(HashMap<Component,Double> mixtureFractions) {
            this.mixtureFractions = mixtureFractions;
        }

        /**
         * @return the iterations
         */
        public int getIterations() {
            return iterations;
        }

        /**
         * @param iterations the iterations to set
         */
        public void setIterations(int iterations) {
            this.iterations = iterations;
        }

    /**
     * @return the vaporFractions
     */
    public HashMap<Component,Double> getSolutionFractions() {
        return solutionFractions;
    }

    /**
     * @param vaporFractions the vaporFractions to set
     */
    public void setSolutionFractions(HashMap<Component,Double> vaporFractions) {
        this.solutionFractions = vaporFractions;
    }

    /**
     * @return the pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
        
}
