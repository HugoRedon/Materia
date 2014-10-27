package termo.activityModel;

import java.util.HashMap;

import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class UNIQUACActivityModel extends ActivityModel {
    private double z = 10d; // default value
    
    public UNIQUACActivityModel() {
    	setName("UNIQUAC");
	}

    @Override
    public double excessGibbsEnergy(Mixture mixture) {
        
        double combE = combinatorialExcessGibbsEnergy(mixture);
        double resE = residualExcessGibbsEnergyOverRT(mixture);
        double excess = combE + resE;
        return excess * Constants.R *mixture.getTemperature();
    }

    @Override
    public double activityCoefficient(Substance ci, Mixture mixture) {
        
    	ActivityModelBinaryParameter k = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
    	double temperature = mixture.getTemperature();
    	
        double phi_i = phi(ci,mixture);
        double xi = ci.getMolarFraction();
        double thetai = theta(ci,mixture);
        double qi = ci.getComponent().getQ_UNIQUAC();        
        double li = l(ci.getComponent());
        
        double firstTerm =Math.log(phi_i / xi) ; 
        double secondTerm = (this.z / 2d) * qi *Math.log(thetai/ phi_i) ;
        double thirdTerm = li;
        
        
       
        
        double fourthTerm =0;        
        double fifthTermSum = 0;
        double seventhTerm =0;
        
        
        
        for(Substance cj: mixture.getPureSubstances()){
            double xj = cj.getMolarFraction();
            double lj = l(cj.getComponent());
            double thetaj = theta(cj, mixture);
            double tauji = tau(cj.getComponent(), ci.getComponent(), k, temperature);
            double tauij = tau(ci.getComponent(), cj.getComponent(), k, temperature);
                                   
            fourthTerm += -(phi_i / xi) * xj*lj;
            fifthTermSum +=  thetaj * tauji ;
            
            
            double sum =0;
            for(Substance ck : mixture.getPureSubstances()){
                double thethak =theta(ck, mixture);
                double taukj = tau(ck.getComponent(), cj.getComponent(), k, temperature);
                
                sum+= thethak * taukj;
            }
            
            seventhTerm += -qi * thetaj * tauij / sum;
        }
        
        double fifthTerm = -qi * Math.log(fifthTermSum);
        double sixthTerm = qi;
        
        double log = firstTerm +    secondTerm+   thirdTerm  +fourthTerm + fifthTerm + sixthTerm + seventhTerm;
        
        return Math.exp(log);
    }

    public double l(Compound ci){
        double r = ci.getR_UNIQUAC();
        double q = ci.getQ_UNIQUAC();
        return (this.z / 2) * (r - q ) - (r - 1);
    }
    
    private double phi(Substance ci, Mixture mixture) {
        double ri = ci.getComponent().getR_UNIQUAC();
        double xi = ci.getMolarFraction();
        
        double denominator = 0;
        for (Substance cj : mixture.getPureSubstances()){
            double rj = cj.getComponent().getR_UNIQUAC();
            double xj = cj.getMolarFraction();
             denominator += rj * xj;
        }
        
        return ri * xi / denominator;
    }

    private double combinatorialExcessGibbsEnergy(Mixture mixture) {
        
        double firstTerm = 0;
        double secondTerm = 0;
          for (Substance ci : mixture.getPureSubstances()){
            double xi = ci.getMolarFraction();
            double phi = phi(ci, mixture);
            
            firstTerm += xi * Math.log(phi / xi);
            
            double qi = ci.getComponent().getQ_UNIQUAC();
            double thetai = theta(ci, mixture);
            secondTerm += (z / 2d) * qi * xi * Math.log(thetai / phi);
        }
        return firstTerm + secondTerm;
    }

    private double theta(Substance ci, Mixture mixture) {
        double qi = ci.getComponent().getQ_UNIQUAC();
        double xi = ci.getMolarFraction();
        
        double denominator = 0;
        
        for ( Substance cj : mixture.getPureSubstances()){
            double qj =cj.getComponent().getQ_UNIQUAC();
            double xj = cj.getMolarFraction();
            
            denominator += qj * xj;
        }
        
        return qi * xi / denominator;
    }
    
    

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }

    public double residualExcessGibbsEnergyOverRT(Mixture mixture) {
       double excess = 0;
        ActivityModelBinaryParameter k = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
        double temperature = mixture.getTemperature();
        for (Substance ci : mixture.getPureSubstances()){
            double qi = ci.getComponent().getQ_UNIQUAC();
            double xi = ci.getMolarFraction();
            
            double insideLog = 0;
            for ( Substance cj: mixture.getPureSubstances()){
                double thetaResidual =theta(cj,mixture);
                double tau = tau(cj.getComponent(),ci.getComponent(),k,temperature);
                
               insideLog += thetaResidual * tau;
            }
            excess += - qi * xi  * Math.log(insideLog);
        }       
        return excess;
    }

//    private double thetaResidual(Compound ci,ArrayList<Component> components, HashMap<Component, Double> fractions) {
//        double qqi = ci.getQq_UNIQUAC();
//        double xi = fractions.get(ci);
//        
//        double denominator = 0;
//        for(Compound cj: components){
//            double qqj = cj.getQq_UNIQUAC();
//            double xj = fractions.get(cj);
//            
//            denominator += qqj * xj;
//        }
//        
//        return qqi * xi / denominator;
//    }

    @Override
    public double tau(Compound cj,Compound ci,ActivityModelBinaryParameter deltaU, double temperature) {
        double u = u(cj, ci, deltaU, temperature);
        return Math.exp(- u/(Constants.R * temperature));
    }
    public double u(Compound cj,Compound ci,ActivityModelBinaryParameter param,double temperature){
        double aji = param.getA().getValue(cj, ci);
        double bji = param.getB().getValue(cj, ci);
        
        return (aji + bji * temperature);
    }


    
    public double parcialExcessGibbsRespectTemperature(Mixture mixture) {
    	
    	ActivityModelBinaryParameter k = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
    	double temperature = mixture.getTemperature();
    	
        double combinatorial = Constants.R * combinatorialExcessGibbsEnergy(mixture);
        
        double residual = 0;
        
        double bji;
        
        double t0 = 0;
        double b1 =0;
        double u1 =0;
        
        double theta = 0;
        double tau = 0;
        double qi =0;
        double xi =0;
        double uji = 0;
        
        for(Substance ci: mixture.getPureSubstances()){
            t0 =0;
            b1 =0;
            u1 =0;
            
            qi = ci.getComponent().getQ_UNIQUAC();
            xi = ci.getMolarFraction();
            for(Substance cj : mixture.getPureSubstances()){
                
                theta = theta(cj,  mixture);
                tau = tau(cj.getComponent(), ci.getComponent(), k, temperature);
                bji = k.getB().getValue(cj.getComponent(), ci.getComponent());
                uji = u(cj.getComponent(), ci.getComponent(), k, temperature);
                
                
                t0 += theta * tau;
                b1 += theta *tau* bji / Constants.R;
                u1 += theta * tau * uji;
            }
            residual -= qi * xi * Constants.R * (  Math.log(t0)   + (u1 -b1)/t0 );
        }
        
        return combinatorial + residual;
        
    }

   
   

}
