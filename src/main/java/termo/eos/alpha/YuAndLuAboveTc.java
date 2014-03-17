package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class YuAndLuAboveTc extends Alpha{

    public YuAndLuAboveTc(){
        setEquation("\\alpha(T) = 10^{\\left(A+B+C\\right)\\left(1-T_r\\right)}");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature / component.getCriticalTemperature();
        
        double log10 = (A+B+C) * (1-tr);
        return Math.pow(10, log10);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature / component.getCriticalTemperature();
        
        return -tr*(A+B+C);
    }
    
}
