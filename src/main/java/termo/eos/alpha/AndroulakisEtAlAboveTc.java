package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo
 */
public class AndroulakisEtAlAboveTc extends Alpha{
    public AndroulakisEtAlAboveTc(){
        setEquation("\\alpha(T) = e^{A\\left( 1 - T_r^{\\frac{2}{3}} \\right)}");
    }
    @Override
    public double alpha(double temperature, Compound component) {
        double A = component.getA_AndroulakisEtAl();
        double tr = temperature / component.getCriticalTemperature();
        return Math.exp(A*(1- Math.pow(tr, 2d/3d)));
    }
    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
        double A = component.getA_AndroulakisEtAl();
        double tr = temperature / component.getCriticalTemperature();
        
        return -(2d/3d) * A * Math.pow(tr,2d/3d);
    }

    @Override
    public int numberOfParameters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getParameter(Compound component, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
