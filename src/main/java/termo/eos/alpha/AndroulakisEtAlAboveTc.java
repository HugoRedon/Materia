package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class AndroulakisEtAlAboveTc extends Alpha{
    public AndroulakisEtAlAboveTc(){
        setEquation("\\alpha(T) = e^{A\\left( 1 - T_r^{\\frac{2}{3}} \\right)}");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_AndroulakisEtAl();
        double tr = temperature / component.getCriticalTemperature();
        return Math.exp(A*(1- Math.pow(tr, 2d/3d)));
    }
    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_AndroulakisEtAl();
        double tr = temperature / component.getCriticalTemperature();
        
        return -(2d/3d) * A * Math.pow(tr,2d/3d);
    }
}
