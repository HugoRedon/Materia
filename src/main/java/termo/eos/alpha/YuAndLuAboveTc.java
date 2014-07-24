package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo
 */
public class YuAndLuAboveTc extends Alpha{

    public YuAndLuAboveTc(){
        setEquation("\\alpha(T) = 10^{\\left(A+B+C\\right)\\left(1-T_r\\right)}");
    }
    @Override
    public double alpha(double temperature, Compound component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature / component.getCriticalTemperature();
        
        double log10 = (A+B+C) * (1-tr);
        return Math.pow(10, log10);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
        double A = component.getA_YuAndLu();
        double B = component.getB_YuAndLu();
        double C = component.getC_YuAndLu();
        
        double tr = temperature / component.getCriticalTemperature();
        
        return -tr*(A+B+C);
    }
    
      @Override
    public int numberOfParameters() {
        return 3;
    }
    
        @Override
    public void setParameter(double value, Compound component, int index) {
        switch(index){
            case 0: component.setA_YuAndLu(value);
                break;
            case 1: component.setB_YuAndLu(value);
                break;
            case 2: component.setC_YuAndLu(value);
                break;
        }
    }

    @Override
    public double getParameter(Compound component, int index) {
          switch(index){
            case 0: 
                return component.getA_YuAndLu();
            case 1: 
                return component.getB_YuAndLu();
            case 2: 
                return component.getC_YuAndLu();
            default:
                return 0;
                
        }
    }
	@Override
	public String getParameterName(int index) {
		switch(index){
	        case 0 : return "A";
	        case 1: return "B";
	        case 2: return "C";
	        default: return null;
		}

	}
    
}
