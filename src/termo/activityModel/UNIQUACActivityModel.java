package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class UNIQUACActivityModel implements ActivityModel{
    private double z = 10; // default value

    @Override
    public double excessGibbsEnergy(
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k, 
            double temperature) {
        double excess =0;
        double combE = combinatorialExcessGibbsEnergy(components, fractions);
        double resE = residualExcessGibbsEnergyOverRT(temperature,components,fractions,k);
        excess = combE + resE;
        return excess * Constants.R *temperature;
    }

    @Override
    public double activityCoefficient(
            ArrayList<Component> components,
            Component ci, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k, 
            double temperature) {
        
        double phi_i = phi(ci, fractions, components);
        double xi = fractions.get(ci);
        double thetai = theta(ci, fractions, components);
        double qi = ci.getQ_UNIQUAC();
        double li = l(ci);
        
        double log = Math.log(phi_i / xi) +    (this.z / 2d) * qi *Math.log(thetai/ phi_i) +     li;
        
        double xj =0;
        double lj = 0;
        double thetaj =0;
        double tauji = 0;
        double tauij =0;
        double thethak =0;
        double taukj =0;
        
        double thirdTerm =0;
        double fourthTerm =0;
        double sixthTerm =0;
        double sum =0;
        
        
        for(Component cj: components){
            xj = fractions.get(cj);
            lj = l(cj);
            thetaj = theta(cj, fractions, components);
            tauji = tau(cj, ci, k, temperature);
            tauij = tau(ci, cj, k, temperature);
            
            thirdTerm -= (phi_i / xi) * xj*lj;
            fourthTerm -= qi * thetaj * tauji ;
            
            
            sum =0;
            for(Component ck : components){
                thethak =theta(ck, fractions, components);
                taukj = tau(ck, cj, k, temperature);
                
                sum+= thethak * taukj;
            }
            
            sixthTerm -= qi * thetaj * tauij / sum;
        }
        
        
        
        log += thirdTerm + fourthTerm + qi + sixthTerm;
        return Math.exp(log);
    }

    public double l(Component ci){
        double r = ci.getR_UNIQUAC();
        double q = ci.getQ_UNIQUAC();
        return (this.z / 2) * (r - q ) - (r - 1);
    }
    
    private double phi(Component ci, HashMap<Component, Double> fractions, ArrayList<Component> components) {
        double ri = ci.getR_UNIQUAC();
        double xi = fractions.get(ci);
        
        double denominator = 0;
        for (Component cj : components){
            double rj = cj.getR_UNIQUAC();
            double xj = fractions.get(cj);
             denominator += rj * xj;
        }
        
        return ri * xi / denominator;
    }

    private double combinatorialExcessGibbsEnergy(ArrayList<Component> components, HashMap<Component, Double> fractions) {
        
        double firstTerm = 0;
        double secondTerm = 0;
          for (Component ci : components){
            double xi = fractions.get(ci);
            double phi = phi(ci, fractions,components);
            
            firstTerm += xi * Math.log(phi / xi);
            
            double qi = ci.getQ_UNIQUAC();
            double theta = theta(ci, fractions ,components);
            secondTerm = (z / 2) * qi * xi * Math.log(theta / phi);
        }
        return firstTerm + secondTerm;
    }

    private double theta(Component ci, HashMap<Component, Double> fractions, ArrayList<Component> components) {
        double qi = ci.getQ_UNIQUAC();
        double xi = fractions.get(ci);
        
        double denominator = 0;
        
        for ( Component cj : components){
            double qj =cj.getQ_UNIQUAC();
            double xj = fractions.get(cj);
            
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

    public double residualExcessGibbsEnergyOverRT(
            double temperature, 
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k) {
       double excess = 0;
        
        for (Component ci : components){
            double qi = ci.getQ_UNIQUAC();
            double xi = fractions.get(ci);
            
            double insideLog = 0;
            for ( Component cj: components){
                double thetaResidual =theta(cj, fractions, components);
                double tau = tau(cj,ci,k,temperature);
                
               insideLog += thetaResidual * tau;
            }
            excess = - qi * xi  * Math.log(insideLog);
        }       
        return excess;
    }

//    private double thetaResidual(Component ci,ArrayList<Component> components, HashMap<Component, Double> fractions) {
//        double qqi = ci.getQq_UNIQUAC();
//        double xi = fractions.get(ci);
//        
//        double denominator = 0;
//        for(Component cj: components){
//            double qqj = cj.getQq_UNIQUAC();
//            double xj = fractions.get(cj);
//            
//            denominator += qqj * xj;
//        }
//        
//        return qqi * xi / denominator;
//    }

    private double tau(Component cj,Component ci,ActivityModelBinaryParameter deltaU, double temperature) {
        double u = u(cj, ci, deltaU, temperature);
        return Math.exp(- u);
    }
    public double u(Component cj,Component ci,ActivityModelBinaryParameter param,double temperature){
        double aji = param.getA().getValue(cj, ci);
        double bji = param.getB().getValue(cj, ci);
        
        return (aji + bji * temperature)/(Constants.R * temperature);
    }


    @Override
    public double parcialExcessGibbsRespectTemperature(
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k,
            double temperature) {
        double combinatorial = Constants.R * combinatorialExcessGibbsEnergy(components, fractions);
        
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
        
        for(Component ci: components){
            t0 =0;
            b1 =0;
            u1 =0;
            
            qi = ci.getQ_UNIQUAC();
            xi = fractions.get(ci);
            for(Component cj : components){
                
                theta = theta(cj,  fractions,components);
                tau = tau(cj, ci, k, temperature);
                bji = k.getB().getValue(cj, ci);
                uji = u(cj, ci, k, temperature);
                
                
                t0 += theta * tau;
                b1 += theta *tau* bji / Constants.R;
                u1 += theta * tau * uji;
            }
            residual -= qi * xi * Constants.R * (  Math.log(t0)   + (u1 -b1)/t0 );
        }
        
        return combinatorial + residual;
        
    }

}
