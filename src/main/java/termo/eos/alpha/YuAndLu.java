package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class YuAndLu extends Alpha{

    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature /component.getCriticalTemperature();
        
        double log10 = (A + B * tr + C * Math.pow(tr,2))*(1-tr);
        return Math.pow(10, log10);
        
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature /component.getCriticalTemperature();
        return Math.log(10)*((B*tr+2*C*tr)*(1-tr)
                -tr*(A+B*tr+C*Math.pow(tr, 2)));
    }
    
}
