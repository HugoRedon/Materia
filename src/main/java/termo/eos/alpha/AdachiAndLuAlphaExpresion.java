package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class AdachiAndLuAlphaExpresion extends Alpha{

    public AdachiAndLuAlphaExpresion(){
        setEquation("\\alpha(T) = A10^{B\\left(1-T_r\\right)}");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_AdachiAndLu();
        double B = component.getB_AdachiAndLu();
        double tr = temperature / component.getCriticalTemperature();
        return A* Math.pow(10,B*(1-tr));
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_AdachiAndLu();
        double B = component.getB_AdachiAndLu();
        double tr = temperature /component.getCriticalTemperature();
        
        return - (B*tr);
    }
    
}
