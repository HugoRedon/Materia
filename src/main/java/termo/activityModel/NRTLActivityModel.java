package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Component;
import termo.substance.MixtureSubstance;
import termo.substance.PureSubstance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class NRTLActivityModel extends ActivityModel{

    
    @Override
    public double excessGibbsEnergy(MixtureSubstance mixture) {
        double gibbsExcess =0;
        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter) mixture.getBinaryParameters();
        
        for(PureSubstance ci: mixture.getPureSubstances()){
            double xi = ci.getMolarFraction();
            
            double numerator = 0;
            double denominator = 0;
            
            for ( PureSubstance cj : mixture.getPureSubstances()){
                double xj = cj.getMolarFraction();
                double  tau = tau(cj.getComponent(), ci.getComponent(), param, mixture.getTemperature());// param.get_gji(cj,ci) / (Constants.R * temperature);
                double Gji = G(cj.getComponent(), ci.getComponent(), param, mixture.getTemperature());//= Math.exp(- param.getAlpha().getValue(cj,ci) * tau);
                
                numerator += xj * tau * Gji;
                denominator += xj * Gji;
            }
            
            gibbsExcess += xi * numerator / denominator;
        }

        return gibbsExcess *Constants.R * mixture.getTemperature();                
    }

    @Override
    public double activityCoefficient(
            //ArrayList<Component> components, 
            PureSubstance cip, 
            MixtureSubstance mixture) {
        
	ArrayList<Component> components = new ArrayList<>();
	HashMap<Component,Double> fractions = new HashMap();
	for(PureSubstance c: mixture.getPureSubstances()){
	    components.add(c.getComponent());
	    fractions.put(c.getComponent(), c.getMolarFraction());
	}
	
	Component ci = cip.getComponent();
        double xj = 0;
        double tauij = 0;
        double gij = 0;
        
        double t0 = 0;
        ActivityModelBinaryParameter k = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
        double summa2 = summa2(ci, components, fractions, k, mixture.getTemperature());
        double summa1 = summa1(ci, components, fractions, k, mixture.getTemperature());
        
        double secondTerm = 0;

        for(Component cj : components){
            xj = fractions.get(cj);
            gij = G(ci, cj, k, mixture.getTemperature());
            tauij = tau(ci, cj, k, mixture.getTemperature());
            
            double sum1kj = summa1(cj, components, fractions, k, mixture.getTemperature());
            double sum2mk = summa2(cj, components, fractions, k, mixture.getTemperature());
            
            secondTerm += (xj * gij / sum1kj )* (tauij - (sum2mk / sum1kj));
            
        }
        double firstTerm = summa2 / summa1;     
        double result= firstTerm - secondTerm;
	
	return Math.exp(result);
    }
    
    private double summa1 (Component ci, ArrayList<Component> components, HashMap<Component, Double> fractions, ActivityModelBinaryParameter k, double temperature){
         double summa = 0;
         for(Component cj: components){
                double xj = fractions.get(cj);
                summa += xj * G( cj, ci, k,temperature);
            }
        return summa;
    }
      private double summa2 (Component ci, ArrayList<Component> components, HashMap<Component, Double> fractions, ActivityModelBinaryParameter k, double temperature){
         double summa = 0;
         for(Component cj: components){
                double xj = fractions.get(cj);
                double tauji = tau(cj, ci, k, temperature);
                double gji = G(cj, ci, k, temperature);
                
                summa += xj * tauji * gji;
            }
        return summa;
    }
    
    
//    public double tau(Component cj,Component ci, ActivityModelBinaryParameter param,double temperature){
////        double gji = param.get_gji(cj, ci);
////        double gii = param.get_gji(ci, ci);
////        double delta = gji - gii;
////        
////        return delta/ (Constants.R * temperature);
//        
//        double aji = param.getA().getValue(cj, ci);
//        double bji = param.getB().getValue(cj, ci);
//        
//        return (aji + bji * temperature)/(Constants.R * temperature);
//    }
    public double G(Component cj,Component ci, ActivityModelBinaryParameter param,double temperature){
         double tau =tau(cj, ci, param, temperature);
        return Math.exp(- param.getAlpha().getValue(cj,ci) * tau);
    }

    @Override
    public double parcialExcessGibbsRespectTemperature(
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter param,
            double temperature) {
//        NRTLBinaryParameter param = (NRTLBinaryParameter)k;
        
        double T0 ;
        double T1;
        double B1 ;
        double A1;
        double A0;
        
      
        double xi ;
        
        double bji ;
        double alphaji ;
        double tauji ;
        double xj ;
        
        double t0;
        double t1;
        
          double result =0;
        
        for(Component ci: components){
           xi = fractions.get(ci);
           
           T0 =0;
           T1 =0;
           B1 =0;
           A1 =0;
           A0 =0;
           
            for(Component cj: components){
                bji = param.getB().getValue(cj, ci);
                alphaji = param.getAlpha().getValue(cj, ci);
                tauji = tau(cj, ci, param, temperature);
                xj = fractions.get(cj);
                
                t0 = xj* G(cj, ci, param, temperature);
                t1 = t0 * tauji;
                T0 += t0;
                T1 += t1;
                B1 += t0*bji/Constants.R;
                A1 += t1* alphaji *(tauji - (bji/Constants.R)) ;
                A0 += t0 * alphaji*(tauji - (bji/Constants.R)) ;
                
            }   
            
            
            result += xi* (((B1 + A1)/T0) - (T1*A0 / Math.pow(T0,2)));
        }
        
        return Constants.R * result;
    }




}
