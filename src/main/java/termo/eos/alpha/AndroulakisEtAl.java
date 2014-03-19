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

    @Override
    public int numberOfParameters() {
        return 3;
    }

    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_AndroulakisEtAl(paramValue);
    }

    @Override
    public double getAlphaParameterA(Component component) {
        return component.getA_AndroulakisEtAl();
    }

    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        component.setB_AndroulakisEtAl(paramValue);
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return component.getB_AndroulakisEtAl();
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        component.setC_AndroulakisEtAl(paramValue);
    }

    @Override
    public double getAlphaParameterC( Component component) {
        return component.getC_AndroulakisEtAl();
    }
    
}
