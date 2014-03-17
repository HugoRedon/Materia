package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MathiasCopemanEquation extends Alpha{

    private double x;
    
    

    @Override
    public String getEquation() {
        String term = " \\left( 1- \\sqrt{T_r}\\right)";
        
        StringBuilder b = new StringBuilder();
        b.append("\\alpha(T) =");
        b.append("\\left[ 1 +  A");
        b.append(term);
        
        
        if(x != 0){
            b.append("+ B").append(term).append("^2");
            b.append("+ C ").append(term).append("^3");
        }
        b.append("\\right]^2");
        return b.toString();
    }
    

    @Override
    public double alpha(double temperature, Component component) {
       double A = component.getA_Mathias_Copeman();
       double B = component.getB_Mathias_Copeman();
       double C = component.getC_Mathias_Copeman();
       
       double tr = temperature / component.getCriticalTemperature();
       
       return Math.pow( 
               1 +
               A * ( 1 - Math.sqrt(tr) ) + 
               getX() * (B * Math.pow( 1 - Math.sqrt(tr),2)  + 
                C * Math.pow( 1 - Math.sqrt(tr),3)), 2);
       
       
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_Mathias_Copeman();
       double B = component.getB_Mathias_Copeman();
       double C = component.getC_Mathias_Copeman();
       
       double tr = temperature / component.getCriticalTemperature();
       
       double sqrtTr = Math.sqrt(tr);
       return (sqrtTr/Math.sqrt(alpha(temperature, component))) * (- A + x * (- 2 * B*(1- sqrtTr)  - 3 * C * Math.pow(1-sqrtTr, 2)));
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

}
