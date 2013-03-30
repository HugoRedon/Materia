package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class TwoEquationsAlphaExpression extends Alpha{
    
    private Alpha alphaAboveTc;
    private Alpha alphaBelowTc;
    
    @Override
     public  double alpha(double temperature,Component component){
           return getProperAlpha(temperature, component).alpha(temperature, component);
    }
       @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
            return getProperAlpha(temperature, component).TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
    }
       
       public Alpha getProperAlpha(double temperature ,Component component){
                double criticalTemperature = component.getCriticalTemperature();
        double q = component.getPrsvKappa();
        
        double reducedTemperature = temperature / criticalTemperature;
        
        if(reducedTemperature <1){
            return alphaBelowTc;
        }else{
            return alphaAboveTc;
        }
       }

    /**
     * @return the alphaAboveTc
     */
    public Alpha getAlphaAboveTc() {
        return alphaAboveTc;
    }

    /**
     * @param alphaAboveTc the alphaAboveTc to set
     */
    public void setAlphaAboveTc(Alpha alphaAboveTc) {
        this.alphaAboveTc = alphaAboveTc;
    }

    /**
     * @return the alphaBelowTc
     */
    public Alpha getAlphaBelowTc() {
        return alphaBelowTc;
    }

    /**
     * @param alphaBelowTc the alphaBelowTc to set
     */
    public void setAlphaBelowTc(Alpha alphaBelowTc) {
        this.alphaBelowTc = alphaBelowTc;
    }

  
 

}
