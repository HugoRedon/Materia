/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.HashMap;
import termo.component.Component;

/**
 *
 * @author Chilpayate
 */
public class FlashSolution {
    
    double vF;
    private HashMap<Component,Double> liquidFractions;
    private HashMap<Component,Double> vaporFractions;
    
    public FlashSolution(double vF,HashMap<Component,Double> liquidFractions, HashMap<Component,Double> vaporFractions){
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
    public HashMap<Component,Double> getLiquidFractions() {
        return liquidFractions;
    }

    /**
     * @param liquidFractions the liquidFractions to set
     */
    public void setLiquidFractions(HashMap<Component,Double> liquidFractions) {
        this.liquidFractions = liquidFractions;
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
