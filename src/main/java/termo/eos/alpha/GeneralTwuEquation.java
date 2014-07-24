package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo Redon Rivera
 */
public class GeneralTwuEquation extends Alpha {

    private double L0;
    private double N0 ;
    private double M0;
    
    private double L1;
    private double N1 ;
    private double M1;

 

    
    @Override
    public double alpha(double temperature, Compound component) {
        double tc = component.getCriticalTemperature();
        double tr = temperature / tc;
        
        double acentricFactor = component.getAcentricFactor();
        
        double alpha0 = alpha_twu(tr, getN0(), getM0(), getL0());
        double alpha1 = alpha_twu(tr, getN1(), getM1(), getL1());
        
        
        return alpha0 +  acentricFactor *(alpha1 - alpha0 );
                
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
                double tc = component.getCriticalTemperature();
        double tr = temperature / tc;
        
        double acentricFactor = component.getAcentricFactor();
        
        double dalpha0 = dalpha(tr, getN0(), getM0(), getL0());
        double dalpha1 = dalpha(tr, getN1(), getM1(), getL1());
        
        
        return dalpha0 +  acentricFactor *(dalpha1 - dalpha0 );
                
    }
    
    private double dalpha(double tr, double N, double M, double L){
        return N * (M-1) - L * Math.pow(tr, N * M ) * N * M;
    }
    
    private double alpha_twu(double tr, double N, double M, double L){
        
        return  Math.pow(tr,N * (M-1) * Math.exp(L * (1- Math.pow(tr, N * M))));
  
    }
    
    
    
    

    /**
     * @return the L0
     */
    public double getL0() {
        return L0;
    }

    /**
     * @param L0 the L0 to set
     */
    public void setL0(double L0) {
        this.L0 = L0;
    }

    /**
     * @return the N0
     */
    public double getN0() {
        return N0;
    }

    /**
     * @param N0 the N0 to set
     */
    public void setN0(double N0) {
        this.N0 = N0;
    }

    /**
     * @return the M0
     */
    public double getM0() {
        return M0;
    }

    /**
     * @param M0 the M0 to set
     */
    public void setM0(double M0) {
        this.M0 = M0;
    }

    /**
     * @return the L1
     */
    public double getL1() {
        return L1;
    }

    /**
     * @param L1 the L1 to set
     */
    public void setL1(double L1) {
        this.L1 = L1;
    }

    /**
     * @return the N1
     */
    public double getN1() {
        return N1;
    }

    /**
     * @param N1 the N1 to set
     */
    public void setN1(double N1) {
        this.N1 = N1;
    }

    /**
     * @return the M1
     */
    public double getM1() {
        return M1;
    }

    /**
     * @param M1 the M1 to set
     */
    public void setM1(double M1) {
        this.M1 = M1;
    }

   @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.L0) ^ (Double.doubleToLongBits(this.L0) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.N0) ^ (Double.doubleToLongBits(this.N0) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.M0) ^ (Double.doubleToLongBits(this.M0) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.L1) ^ (Double.doubleToLongBits(this.L1) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.N1) ^ (Double.doubleToLongBits(this.N1) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.M1) ^ (Double.doubleToLongBits(this.M1) >>> 32));
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
        final GeneralTwuEquation other = (GeneralTwuEquation) obj;
        if (Double.doubleToLongBits(this.L0) != Double.doubleToLongBits(other.L0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.N0) != Double.doubleToLongBits(other.N0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.M0) != Double.doubleToLongBits(other.M0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.L1) != Double.doubleToLongBits(other.L1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.N1) != Double.doubleToLongBits(other.N1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.M1) != Double.doubleToLongBits(other.M1)) {
            return false;
        }
        return true;
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

	@Override
	public String getParameterName(int index) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
