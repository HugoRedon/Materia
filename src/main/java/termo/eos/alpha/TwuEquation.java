package termo.eos.alpha;

import termo.component.Compound;

public class TwuEquation extends Alpha {
    public TwuEquation(){
        setEquation("\\alpha(T) = T_r^{N(M-1)}e^{L\\left( 1-T_r^{NM} \\right)}");
    }
    
    @Override
    public double alpha(double temperature, Compound component) {
        double tc = component.getCriticalTemperature();
        double tr = temperature / tc;
        
        double N = component.getN_Twu();
        double M = component.getM_Twu();
        double L = component.getL_Twu();
                
        return Math.pow(tr, N * (M -1)) * Math.exp(L* (1- Math.pow(tr, N * M)));
 
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
        double tc = component.getCriticalTemperature();
        double tr = temperature / tc;
        
        double N = component.getN_Twu();
        double M = component.getM_Twu();
        double L = component.getL_Twu();
        
        return N*( M-1) - L * N * M * Math.pow(tr, N*M);
    }
    

     @Override
    public int numberOfParameters() {
        return 3;
    }
       @Override
    public void setParameter(double value, Compound component, int index) {
        switch(index){
            case 0: component.setN_Twu(value);
                break;
            case 1: component.setM_Twu(value);
                break;
            case 2: component.setL_Twu(value);
                break;
        }
    }

    @Override
    public double getParameter(Compound component, int index) {
          switch(index){
            case 0: 
                return component.getN_Twu();
            case 1: 
                return component.getM_Twu();
            case 2: 
                return component.getL_Twu();
            default:
                return 0;
                
        }
    }

	@Override
	public String getParameterName(int index) {
		switch(index){
	        case 0 : return "N";
	        case 1: return "M";
	        case 2: return "L";
	        default: return null;
		}
	
	}
    
  

   

}
