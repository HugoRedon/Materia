package termo.eos.alpha;

import termo.component.Compound;

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
    public double alpha(double temperature, Compound component) {
        double A = component.getA_AndroulakisEtAl();
        double B = component.getB_AndroulakisEtAl();
        double C = component.getC_AndroulakisEtAl();
               
        double tr = temperature / component.getCriticalTemperature();
        double oneMinustrPow = (1 - Math.pow(tr, 2d/3d));
        return 1 + A *oneMinustrPow + B * Math.pow(oneMinustrPow, 2) 
                + C * Math.pow(oneMinustrPow, 3);
        
        
    }
   

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
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
    public void setParameter(double value, Compound component, int index) {
        switch(index){
            case 0: component.setA_AndroulakisEtAl(value);
                break;
            case 1: component.setB_AndroulakisEtAl(value);
                break;
            case 2: component.setC_AndroulakisEtAl(value);
                break;
        }
    }

    @Override
    public double getParameter(Compound component, int index) {
          switch(index){
            case 0: 
                return component.getA_AndroulakisEtAl();
            case 1: 
                return component.getB_AndroulakisEtAl();
            case 2: 
                return component.getC_AndroulakisEtAl();
            default:
                return 0;
                
        }
    }

    @Override
	public String getParameterName(int index){
    	switch(index){
        case 0: 
            return "A";
        case 1: 
            return "B";
        case 2: 
            return "C";
        default:
            return null;
            
    }
    }
    
    
    
    
}
