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

    @Override
    public int numberOfParameters() {
        return 2;
    }

    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_AdachiAndLu(paramValue);
    }

    @Override
    public double getAlphaParameterA(Component component) {
        return component.getA_AdachiAndLu();
    }

    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        component.setB_AdachiAndLu(paramValue);
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return component.getB_AdachiAndLu();
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        System.out.println("No se debería esta llamando a este método");
    }

    @Override
    public double getAlphaParameterC(Component component) {
        System.out.println("No se debería estar llamando a este método");
        return 0;
    }
    
}
