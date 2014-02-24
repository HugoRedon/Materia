/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.substance;

import termo.equilibrium.EquilibriaSolution;

/**
 *
 * @author
 * Hugo
 */
public interface HeterogeneousSubstance {
    public EquilibriaSolution bubbleTemperature(double pressure);
    
    public HomogeneousSubstance getLiquid();
    public HomogeneousSubstance getVapor();
}
