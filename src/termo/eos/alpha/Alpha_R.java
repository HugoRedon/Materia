package termo.eos.alpha;

import termo.component.Component;
import termo.eos.EOSNames;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class Alpha_R extends Alpha{
    
    private double[] r;
  

     protected double m(Component component){
        double acentricFactor = component.getAcentricFactor();
        
        return getR()[0] + getR()[1] * acentricFactor + getR()[2] * Math.pow(acentricFactor, 2) + getR()[3] * Math.pow(acentricFactor, 3);
    }

    /**
     * @return the r
     */
    public double[] getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(double[] r) {
        this.r = r;
    }
     
    
}
