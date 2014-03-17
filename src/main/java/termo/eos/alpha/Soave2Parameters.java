package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class Soave2Parameters extends Alpha{

    public Soave2Parameters(){
        setEquation("\\alpha(T) = 1+ (1- T_r) \\left( A + \\frac{B}{T_r}   \\right)");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_Soave();
        double B = component.getB_Soave();
        double tr = temperature / component.getCriticalTemperature();
        return 1 + (1-tr)* (A + (B/tr));
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_Soave();
        double B = component.getB_Soave();
        double tc = component.getCriticalTemperature();
        double tr = temperature / tc;
        return (- (tr) *(A + (B/tr)) - (B/tr) *(1-tr))/ alpha(temperature, component);
    }
    
}
