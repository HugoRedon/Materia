/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.HashMap;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class MixtureEquilibriaPhaseSolution extends EquilibriaPhaseSolution{
    private HashMap<PureSubstance,Double> solutionFractions;
    private HashMap<PureSubstance,Double> mixtureFractions;
    public MixtureEquilibriaPhaseSolution(
	 double temperature, 
                double pressure,
                HashMap<PureSubstance,Double> zFraction, 
                HashMap<PureSubstance,Double> solutionFractions,
                int iterations){
            super(temperature, pressure, iterations);
            
            this.mixtureFractions = zFraction;
            this.solutionFractions = solutionFractions;
            
    }
      /**
         * @return the vaporFractions
         */
        public HashMap<PureSubstance,Double> getMixtureFractions() {
            return mixtureFractions;
        }

        /**
         * @param vaporFractions the vaporFractions to set
         */
        public void setMixtureFractions(HashMap<PureSubstance,Double> mixtureFractions) {
            this.mixtureFractions = mixtureFractions;
        }
	 /**
     * @return the vaporFractions
     */
    public HashMap<PureSubstance,Double> getSolutionFractions() {
        return solutionFractions;
    }

    /**
     * @param vaporFractions the vaporFractions to set
     */
    public void setSolutionFractions(HashMap<PureSubstance,Double> vaporFractions) {
        this.solutionFractions = vaporFractions;
    }

}
