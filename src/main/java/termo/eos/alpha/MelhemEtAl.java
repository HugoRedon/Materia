package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo
 */
public class MelhemEtAl extends Alpha{

    
    public MelhemEtAl(){
        setEquation("\\ln{\\alpha(T)} = A\\left( 1- T_r\\right) + B (1-\\sqrt{T_r})^2");
    }
    @Override
    public double alpha(double temperature, Compound component) {
        double A = component.getA_MelhemEtAl();
        double B = component.getB_MelhemEtAl();
        double tr = temperature / component.getCriticalTemperature();
        
        double ln =  A*(1-tr)+ B*Math.pow((1-Math.sqrt(tr)),2);
        return Math.exp(ln);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
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
    public double getParameter(Compound component, int index) {
        switch(index){
            case 0 : return component.getA_MelhemEtAl();
            case 1: return component.getB_MelhemEtAl();
            default: return 0;
        }
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
        switch(index){
            case 0 : component.setA_MelhemEtAl(value);
                break;
            case 1: component.setB_MelhemEtAl(value);
                break;
        }
    }
    
     
    
    
}
