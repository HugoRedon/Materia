package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;
import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class WilsonActivityModel extends ActivityModel{

    boolean calculateWith_b = true;
    
    public WilsonActivityModel(){
        super.setName("Wilson");
    }
    
    @Override
    public double excessGibbsEnergy(
            Mixture mixture) {
       
        double excessGibbs = 0;
        
        for (Substance ci : mixture.getPureSubstances()){
            double xi = ci.getMolarFraction();
            excessGibbs -= xi * Math.log( summa(ci, mixture));
        }
        return excessGibbs*termo.Constants.R* mixture.getTemperature();
    }

    @Override
    public double activityCoefficient(
            Substance ci,
           Mixture mixture) {
          
//        double thirdTerm = 0;
//        for(Component cj: components){
//            double xj = fractions.get(cj);
//            thirdTerm += (summa(cj, components, fractions, k,temperature));
//        }
//    double loggamma = - Math.log(summa(ci, components, fractions, k,temperature)) + 1 - thirdTerm;
//    return Math.exp(loggamma);
        
        double xj = 0;
        double xk =0;
        
        double denominator = 0;
        double thirdTerm = 0;
        
        for(Substance cj: mixture.getPureSubstances()){
            xj = cj.getMolarFraction();
            denominator = summa(cj,mixture);
           
            thirdTerm += xj * lambda(cj, ci, (ActivityModelBinaryParameter)mixture.getBinaryParameters()
                    , mixture.getTemperature()) / denominator;
        }
        double logGamma =  - Math.log(summa(ci, mixture))+1 - thirdTerm;
        return Math.exp(logGamma);
    }

    private double summa(Substance ci,  Mixture mixture) {
	    
        double summa = 0;
         for(Substance cj: mixture.getPureSubstances()){
                double xj = cj.getMolarFraction();
                summa += xj * lambda( ci, cj, 
                        (ActivityModelBinaryParameter)mixture.getBinaryParameters(),
                        mixture.getTemperature());
            }
        return summa;
    }
    
    public double lambda (Substance ci,Substance cj, ActivityModelBinaryParameter k ,double T){
	
	double Vj;
	double Vi;
		
	if(calculateWith_b){
	    Vj = cj.calculate_b_cubicParameter();
	    Vi = ci.calculate_b_cubicParameter();
	}else{
             Vj = cj.getComponent().getLiquidMolarVolumeat298_15K();
             Vi = ci.getComponent().getLiquidMolarVolumeat298_15K();
	}
//            if(ci.equals(cj)){
//                return 1;
//            }else{
//            double lambdaij = k.getValue(ci,cj);
//            double lambdaii = k.getValue(ci,ci);
//            double deltaLambda = - (lambdaij - lambdaii);
            double tau = tau(ci.getComponent(), cj.getComponent(), k, T);
                return (Vj / Vi) * Math.exp(-tau  /(termo.Constants.R * T));               
//            }  
    }


    @Override
    public double parcialExcessGibbsRespectTemperature(
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k,
            double temperature) {
        
         double result = 0;
        
        double bji;
        
        double t0 = 0;
        double b1 =0;
        double t1 =0;
        
        double tau = 0;       
        double xi =0;
        double xj =0;
        double lambda =0;
        
        
        for(Component ci: components){
            t0 =0;
            b1 =0;
            t1 =0;
            
            xi = fractions.get(ci);
            for(Component cj : components){
                
                xj = fractions.get(cj);
//                lambda = lambda(ci, cj, k, temperature);
                        
                tau = tau(ci, cj, k, temperature);
                bji = k.getB().getValue(cj, ci);
                
                t0 +=  xj *lambda;
                b1 += xj *lambda * bji / Constants.R;
                t1 +=  xj *lambda * tau ;
            }
            result -=  xi * Constants.R * (  Math.log(t0)   + (t1 -b1)/t0 );
        }
        
        return  result;
    }

 

   

   
}
