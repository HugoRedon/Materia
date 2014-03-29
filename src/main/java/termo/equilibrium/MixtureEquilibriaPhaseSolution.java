/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.HashMap;
import termo.component.Component;
import termo.matter.Substance;

/**
 *
 * @author
 * Hugo
 */
public class MixtureEquilibriaPhaseSolution extends EquilibriaSolution{
    private HashMap<Component,Double> vaporFractions;
    private HashMap<Component,Double> liquidFractions;
    public MixtureEquilibriaPhaseSolution(
	 double temperature, 
                double pressure,
                HashMap<Component,Double> liquidFractions, 
                HashMap<Component,Double> solutionFractions,
                int iterations){
            super(temperature, pressure, iterations);
            
            this.liquidFractions = liquidFractions;
            this.vaporFractions = solutionFractions;
            
    }
      /**
         * @return the vaporFractions
         */
        public HashMap<Component,Double> getLiquidFractions() {
            return liquidFractions;
        }

        /**
         * @param vaporFractions the vaporFractions to set
         */
        public void setLiquidFractions(HashMap<Component,Double> mixtureFractions) {
            this.liquidFractions = mixtureFractions;
        }
	 /**
     * @return the vaporFractions
     */
    public HashMap<Component,Double> getVaporFractions() {
        return vaporFractions;
    }

    /**
     * @param vaporFractions the vaporFractions to set
     */
    public void setVaporFractions(HashMap<Component,Double> vaporFractions) {
        this.vaporFractions = vaporFractions;
    }

}
