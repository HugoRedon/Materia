package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class YuAndLu extends Alpha{

    public YuAndLu(){
        setEquation("\\alpha(T) = 10^{\\left(A+BT_r + CT_r^2\\right)\\left(1-T_r \\right)}");
    }
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
    @Override
    public int numberOfParameters() {
        return 3;
    }
    
    @Override
    public double getAlphaParameterA(Component component) {
	return component.getA_YuAndLu();
    } 
    
    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_YuAndLu(paramValue);
    }
    
    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        component.setB_YuAndLu(paramValue);
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return component.getB_YuAndLu();
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        component.setC_YuAndLu(paramValue);
    }

    @Override
    public double getAlphaParameterC(Component component) {
        return component.getC_YuAndLu();
    }
    
}
