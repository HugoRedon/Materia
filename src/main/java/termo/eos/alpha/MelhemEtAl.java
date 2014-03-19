package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class MelhemEtAl extends Alpha{

    
    public MelhemEtAl(){
        setEquation("\\ln{\\alpha(T)} = A\\left( 1- T_r\\right) + B (1-\\sqrt{T_r})^2");
    }
    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_MelhemEtAl();
        double B = component.getB_MelhemEtAl();
        double tr = temperature / component.getCriticalTemperature();
        
        double ln =  A*(1-tr)+ B*Math.pow((1-Math.sqrt(tr)),2);
        return Math.exp(ln);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_MelhemEtAl();
        double B = component.getB_MelhemEtAl();
        double tr = temperature / component.getCriticalTemperature();
        
        return - A * tr - (B/Math.sqrt(tr))* (1-Math.sqrt(tr));
    }
    
    @Override
    public int numberOfParameters() {
        return 2;
    }
    
    @Override
    public double getAlphaParameterA(Component component) {
	return component.getA_MelhemEtAl();
    } 
    
    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_MelhemEtAl(paramValue);
    }
    
    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        component.setB_MelhemEtAl(paramValue);
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return component.getB_MelhemEtAl();
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        
    }

    @Override
    public double getAlphaParameterC(Component component) {
        return 0;
    }
    
}
