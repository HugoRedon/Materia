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
public class WilsonActivityModel implements ActivityModel{

    @Override
    public double excessGibbsEnergy(
            ArrayList<Component> components, 
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k,
            double temperature) {
       
        double excessGibbs = 0;
        
        for (Component ci : components){
            double xi = fractions.get(ci);
            excessGibbs -= xi * Math.log( summa(ci, components, fractions,k,temperature));
        }
        return excessGibbs;
    }

    @Override
    public double activityCoefficient(
            ArrayList<Component> components, 
            Component ci,
            HashMap<Component, Double> fractions, 
            ActivityModelBinaryParameter k,
            double temperature) {
          
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
        
        for(Component cj: components){
            xj = fractions.get(cj);
            denominator = summa(cj, components, fractions, k, temperature);
           
            thirdTerm += xj * lambda(cj, ci, k, xk) / denominator;
        }
        double logGamma =  - Math.log(summa(ci, components, fractions, k, temperature))+1 - thirdTerm;
        return Math.exp(logGamma);
    }

    private double summa(Component ci, ArrayList<Component> components, HashMap<Component, Double> fractions, ActivityModelBinaryParameter k, double temperature) {
 
        double summa = 0;
         for(Component cj: components){
                double xj = fractions.get(cj);
                summa += xj * lambda( ci, cj, k,temperature);
            }
        return summa;
    }
    
    public double lambda (Component ci,Component cj, ActivityModelBinaryParameter k ,double T){
            double Vj = cj.getLiquidMolarVolumeat298_15K();
            double Vi = ci.getLiquidMolarVolumeat298_15K();
        
//            if(ci.equals(cj)){
//                return 1;
//            }else{
//            double lambdaij = k.getValue(ci,cj);
//            double lambdaii = k.getValue(ci,ci);
//            double deltaLambda = - (lambdaij - lambdaii);
            double tau = tau(ci, cj, k, T);
                return (Vj / Vi) * Math.exp(-tau  /(termo.Constants.R * T));               
//            }  
    }
    public double tau(Component ci,Component cj, ActivityModelBinaryParameter k ,double T){
        double aij = k.getA().getValue(ci, cj);
        double bij = k.getB().getValue(ci, cj);
        
        return (aij + bij * T)/(Constants.R * T);
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
                lambda = lambda(ci, cj, k, temperature);
                        
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
