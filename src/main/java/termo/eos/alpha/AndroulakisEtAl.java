package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class AndroulakisEtAl extends Alpha{

    public AndroulakisEtAl(){
        String term = "\\left(   1- T_r^{\\frac{2}{3}}\\right)";
        setEquation("\\alpha(T) = 1 + A" + term +" +  B " +term+"^2 + C"+ term + "^3");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_AndroulakisEtAl();
        double B = component.getB_AndroulakisEtAl();
        double C = component.getC_AndroulakisEtAl();
               
        double tr = temperature / component.getCriticalTemperature();
        double oneMinustrPow = (1 - Math.pow(tr, 2d/3d));
        return 1 + A *oneMinustrPow + B * Math.pow(oneMinustrPow, 2) 
                + C * Math.pow(oneMinustrPow, 3);
        
        
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_AndroulakisEtAl();
        double B = component.getB_AndroulakisEtAl();
        double C = component.getC_AndroulakisEtAl();
               
        double tr = temperature / component.getCriticalTemperature();
        
        double oneMinustrPow = (1 - Math.pow(tr, 2d/3d));
        return -(2d/3d)*Math.pow(tr,2d/3d)*(A + 2*B*oneMinustrPow + 3*C*Math.pow(oneMinustrPow,2));
    }
    
}
