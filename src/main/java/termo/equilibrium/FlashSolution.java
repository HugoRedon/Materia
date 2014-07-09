/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.HashMap;
import termo.component.Compound;

/**
 *
 * @author Chilpayate
 */
public class FlashSolution {
    
    double vF;
    private HashMap<Compound,Double> liquidFractions;
    private HashMap<Compound,Double> vaporFractions;
    
    public FlashSolution(double vF,HashMap<Compound,Double> liquidFractions, HashMap<Compound,Double> vaporFractions){
        this.vF = vF;
        this.liquidFractions = liquidFractions;
        this.vaporFractions = vaporFractions;
    }
    double getVF() {
        return vF;
    }

    /**
     * @return the liquidFractions
     */
    public HashMap<Compound,Double> getLiquidFractions() {
        return liquidFractions;
    }

    /**
     * @param liquidFractions the liquidFractions to set
     */
    public void setLiquidFractions(HashMap<Compound,Double> liquidFractions) {
        this.liquidFractions = liquidFractions;
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
