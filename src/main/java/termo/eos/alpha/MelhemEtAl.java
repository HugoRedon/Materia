package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class MelhemEtAl extends Alpha{

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
    
}
