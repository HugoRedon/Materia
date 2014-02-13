package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MathiasAboveTcAlphaEquation extends Alpha{
    private CommonAlphaEquation belowTcAlphaEquation ;
    
    public MathiasAboveTcAlphaEquation(CommonAlphaEquation belowTcAlphaEquation){
        this.belowTcAlphaEquation = belowTcAlphaEquation;
    }

    @Override
    public double alpha(double temperature, Component component) {
         double criticalTemperature = component.getCriticalTemperature();
     
        
        double reducedTemperature = temperature / criticalTemperature;
        
             double c = c(component);
            double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
            return Math.exp(calc);
    }
     
    private double c(Component component){
        double q = belowTcAlphaEquation.get_q(component);
	double omega = component.getAcentricFactor();
        return 1+0.5*belowTcAlphaEquation.m(omega)+0.3*q;
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        
          double c = c(component);
           double tr = temperature /component.getCriticalTemperature();      
            return - (c-1) * (Math.pow(tr,c));
    }

    /**
     * @return the belowTcAlphaEquation
     */
    public CommonAlphaEquation getBelowTcAlphaEquation() {
        return belowTcAlphaEquation;
    }

    /**
     * @param belowTcAlphaEquation the belowTcAlphaEquation to set
     */
    public void setBelowTcAlphaEquation(CommonAlphaEquation belowTcAlphaEquation) {
        this.belowTcAlphaEquation = belowTcAlphaEquation;
    }
    
}
