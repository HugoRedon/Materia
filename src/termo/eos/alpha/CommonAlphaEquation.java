package termo.eos.alpha;

import java.io.Serializable;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class CommonAlphaEquation extends Alpha implements Serializable{
    private double r1;
    private double r2;
    private double r3;
    private double r4;
    

    public abstract double get_q(Component component);

    @Override
    public  double alpha(double temperature, Component component){
	double q = get_q(component);
	double omega = component.getAcentricFactor();
	double reducedTemperature = temperature  / component.getCriticalTemperature();

            double calc = 1 + m(omega)* (1 - Math.sqrt(reducedTemperature)) 
                    +q*(1-reducedTemperature)*(0.7-reducedTemperature) ;
            return Math.pow( calc, 2);
    
            
    }

    
    protected double m(double acentricFactor){
        //double acentricFactor = component.getAcentricFactor();
        
        return getR1() + getR2() * acentricFactor +getR3() * Math.pow(acentricFactor, 2) + getR4() * Math.pow(acentricFactor, 3);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component){
	double tr = temperature /component.getCriticalTemperature();    
	double omega = component.getAcentricFactor();
	double q = get_q(component); 
	
	return (1d/ Math.sqrt(alpha(temperature,component)))*(- m(omega) * Math.sqrt(tr) 
                    -  q * ( 3.4*tr - 4*Math.pow(tr, 2) ));
    }

    /**
     * @return the r1
     */
    public double getR1() {
        return r1;
    }

    /**
     * @param r1 the r1 to set
     */
    public void setR1(double r1) {
        this.r1 = r1;
    }

    /**
     * @return the r2
     */
    public double getR2() {
        return r2;
    }

    /**
     * @param r2 the r2 to set
     */
    public void setR2(double r2) {
        this.r2 = r2;
    }

    /**
     * @return the r3
     */
    public double getR3() {
        return r3;
    }

    /**
     * @param r3 the r3 to set
     */
    public void setR3(double r3) {
        this.r3 = r3;
    }

    /**
     * @return the r4
     */
    public double getR4() {
        return r4;
    }

    /**
     * @param r4 the r4 to set
     */
    public void setR4(double r4) {
        this.r4 = r4;
    }

    /**
     * @return the x
     */
//    public double getX() {
//        return x;
//    }
//
//    /**
//     * @param x the x to set
//     */
//    public void setX(double x) {
//        this.x = x;
//    }
    
        @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.r1) ^ (Double.doubleToLongBits(this.r1) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.r2) ^ (Double.doubleToLongBits(this.r2) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.r3) ^ (Double.doubleToLongBits(this.r3) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.r4) ^ (Double.doubleToLongBits(this.r4) >>> 32));
       // hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommonAlphaEquation other = (CommonAlphaEquation) obj;
        if (Double.doubleToLongBits(this.r1) != Double.doubleToLongBits(other.r1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.r2) != Double.doubleToLongBits(other.r2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.r3) != Double.doubleToLongBits(other.r3)) {
            return false;
        }
        if (Double.doubleToLongBits(this.r4) != Double.doubleToLongBits(other.r4)) {
            return false;
        }
//        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
//            return false;
//        }
        return true;
    }

  
    
}






class StryjekAndVera extends CommonAlphaEquation {
    public StryjekAndVera(){
	setName(AlphaNames.StryjekAndVera);
	setR1(0.378893);
        setR2(1.4897153);
        setR3(-0.17131848);
        setR4(0.0196554);
    }
    @Override
    public double get_q(Component component) {
	return component.getPrsvKappa();
    }
}

class SoaveAlpha extends CommonAlphaEquation{
    public SoaveAlpha(){
	setName(AlphaNames.Soave); 
        setR1(0.48508);
        setR2(1.55171);
        setR3(-0.15613);
        setR4(0);
    }
    @Override
    public double get_q(Component component) {
	return 0;
    }
}

class MathiasAlpha extends CommonAlphaEquation{
    public MathiasAlpha(){
	setName(AlphaNames.Mathias);
	setR1(0.48508);
        setR2(1.55171);
        setR3(-0.15613);
        setR4(0);
    }
    @Override
    public double get_q(Component component) {
	return -component.getSRK_A();
    }
}


class PengAndRobinsonAlpha extends CommonAlphaEquation{
    public PengAndRobinsonAlpha(){
	setName(AlphaNames.PengAndRobinson);
        
        setR1(0.37464);
        setR2(1.54226);
        setR3(-0.2699);
        setR4(0);
    }
    @Override
    public double get_q(Component component) {
	return 0;
    } 
}

