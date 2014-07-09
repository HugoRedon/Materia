package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo Redon Rivera
 */
public class GCEOSEquation extends Alpha{
    private double A;
    private double B;
    private double C;
    private double D;
    
    private double k1;
    private double k2;
    private double k3;
    private double k4;
    private double k5;

    @Override
    public double alpha(double temperature, Compound component) {
        
        double tr = temperature / component.getCriticalTemperature();
        
        double k0 = k0(component);
        double k  = k0 + (k1+ (k2 - k3 *tr)* (1 - Math.pow(tr,k4)  ))*((1 + Math.sqrt(tr))*(0.7 - tr)) * Math.pow(temperature, k5);
      
        return Math.pow(1 + k *( 1 - Math.sqrt(tr)),2);
    }
    public double k0(Compound component){
         double acentric = component.getAcentricFactor();
         return  A + B * acentric  + C * Math.pow(acentric,2) + D *Math.pow(acentric,3);
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
         double tr = temperature / component.getCriticalTemperature();
         double k0 = k0(component);
         
         double k1k2 = k1 + (k2 - k3*tr)*(1- Math.pow(tr, k4));
         double tr07 = ((1+Math.sqrt(tr))   *  (0.7 - tr))* Math.pow(tr, k5);
         
         double sqrtAlpha = Math.sqrt(alpha(temperature, component));
         
         double firstTerm = (-k3 * ( 1- Math.pow(tr, k4))- ((k2-k3*tr)*k4 * Math.pow(tr, k4))/tr)*tr07;
         double secondTerm = k1k2 * Math.pow(tr,k5)*(-1-Math.sqrt(tr) + (0.7 - tr)/(2* Math.sqrt(tr)));
         double thirdTerm = k1k2 * tr07 * k5;
         
         double firstparenthesis = firstTerm + secondTerm + thirdTerm;
         
         return (2 * tr / sqrtAlpha) * (firstparenthesis * ( 1 + Math.sqrt(tr))      +   (k0+ k1k2 * tr07)/(2*Math.sqrt(tr)));
         
         
    }

    /**
     * @return the A
     */
    public double getA() {
        return A;
    }

    /**
     * @param A the A to set
     */
    public void setA(double A) {
        this.A = A;
    }

    /**
     * @return the B
     */
    public double getB() {
        return B;
    }

    /**
     * @param B the B to set
     */
    public void setB(double B) {
        this.B = B;
    }

    /**
     * @return the C
     */
    public double getC() {
        return C;
    }

    /**
     * @param C the C to set
     */
    public void setC(double C) {
        this.C = C;
    }

    /**
     * @return the D
     */
    public double getD() {
        return D;
    }

    /**
     * @param D the D to set
     */
    public void setD(double D) {
        this.D = D;
    }

    /**
     * @return the k1
     */
    public double getK1() {
        return k1;
    }

    /**
     * @param k1 the k1 to set
     */
    public void setK1(double k1) {
        this.k1 = k1;
    }

    /**
     * @return the k2
     */
    public double getK2() {
        return k2;
    }

    /**
     * @param k2 the k2 to set
     */
    public void setK2(double k2) {
        this.k2 = k2;
    }

    /**
     * @return the k3
     */
    public double getK3() {
        return k3;
    }

    /**
     * @param k3 the k3 to set
     */
    public void setK3(double k3) {
        this.k3 = k3;
    }

    /**
     * @return the k4
     */
    public double getK4() {
        return k4;
    }

    /**
     * @param k4 the k4 to set
     */
    public void setK4(double k4) {
        this.k4 = k4;
    }

    /**
     * @return the k5
     */
    public double getK5() {
        return k5;
    }

    /**
     * @param k5 the k5 to set
     */
    public void setK5(double k5) {
        this.k5 = k5;
    }

    @Override
    public int numberOfParameters() {
        return 0;
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
  
    }

    @Override
    public double getParameter(Compound component, int index) {
        return 0;
    }

    
      
}
