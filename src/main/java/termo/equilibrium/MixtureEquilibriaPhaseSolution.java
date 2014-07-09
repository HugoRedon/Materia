/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.HashMap;
import termo.component.Compound;
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class MixtureEquilibriaPhaseSolution extends EquilibriaSolution{
    private HashMap<Compound,Double> vaporFractions;
    private HashMap<Compound,Double> liquidFractions;
    public MixtureEquilibriaPhaseSolution(
	 double temperature, 
                double pressure,
                HashMap<Compound,Double> liquidFractions, 
                HashMap<Compound,Double> solutionFractions,
                int iterations){
            super(temperature, pressure, iterations);
            
            this.liquidFractions = liquidFractions;
            this.vaporFractions = solutionFractions;
            
    }
      /**
         * @return the vaporFractions
         */
        public HashMap<Compound,Double> getLiquidFractions() {
            return liquidFractions;
        }

        /**
         * @param vaporFractions the vaporFractions to set
         */
        public void setLiquidFractions(HashMap<Compound,Double> mixtureFractions) {
            this.liquidFractions = mixtureFractions;
        }
	 /**
     * @return the vaporFractions
     */
    public HashMap<Compound,Double> getVaporFractions() {
        return vaporFractions;
    }

    /**
     * @param vaporFractions the vaporFractions to set
     */
    public void setVaporFractions(HashMap<Compound,Double> vaporFractions) {
        this.vaporFractions = vaporFractions;
    }

}
