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
        StringBuilder b = new StringBuilder();
        b.append("\\alpha(T) = e^{\\left[ \\left( \\frac{c-1}{c}\\right) \\left( 1-T_r^c \\right)   \\right]}");
        b.append("\\\\");
        b.append("c=\\frac{m}{2} + 0.3A");
        setEquation(b.toString());
    }

    @Override
    public double alpha(double temperature, Component component) {
         double criticalTemperature = component.getCriticalTemperature();
     
        
        double reducedTemperature = temperature / criticalTemperature;
        
             double c = c(component);
            //double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
	     double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
            return Math.exp(calc);
    }
     
    private double c(Component component){
        double q = belowTcAlphaEquation.getAlphaParameterA(component);
	double omega = component.getAcentricFactor();
        return 1+0.5*belowTcAlphaEquation.m(omega)+0.3*q;
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        
          double c = c(component);
           double tr = temperature /component.getCriticalTemperature();      
            return - 2*(c-1) * (Math.pow(tr,c));
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
    
    @Override
    public int numberOfParameters() {
        return 3;//belowTc contiene 3
    }
    
    @Override
    public double getAlphaParameterA(Component component) {
	return component.getA_Mathias_Copeman();
    } 
    
    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_Mathias_Copeman(paramValue);
    }
    
    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        component.setB_Mathias_Copeman(paramValue);
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return component.getB_Mathias_Copeman();
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        component.setC_Mathias_Copeman(paramValue);
    }

    @Override
    public double getAlphaParameterC(Component component) {
        return component.getC_Mathias_Copeman();
    }
    
    
}
