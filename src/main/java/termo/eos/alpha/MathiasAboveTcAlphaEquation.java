package termo.eos.alpha;

import termo.component.Compound;

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
    public double alpha(double temperature, Compound component) {
         double criticalTemperature = component.getCriticalTemperature();
     
        
        double reducedTemperature = temperature / criticalTemperature;
        
             double c = c(component);
            //double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
	     double calc = (2*(c-1)/c)*(1-Math.pow(reducedTemperature, c));
            return Math.exp(calc);
    }
     
    private double c(Compound component){
        double q = belowTcAlphaEquation.getParameter(component,0);
	double omega = component.getAcentricFactor();
        return 1+0.5*belowTcAlphaEquation.m(omega)+0.3*q;
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
        
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
        return 1;
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
       if(index ==0) {
           component.setA_Mathias(value);
       }
    }

    @Override
    public double getParameter(Compound component, int index) {
          if(index ==0) {
                return component.getA_Mathias();
          }else{
          return 0;}
            
    }

	@Override
	public String getParameterName(int index) {
		if(index ==0) {
			return "A";
		}
       
		return null;
	}
    
     
    
    
}
