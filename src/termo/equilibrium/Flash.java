/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.phase.Phase;

/**
 *
 * @author Chilpayate
 */
public class Flash {
    
    public static FlashSolution getFlash(double pressure,
            double temperature, 
            HashMap<Component,Double> mixtureFractions,
            double vF,
            HashMap<Component,Double> vaporFractions,
            HashMap<Component,Double> liquidFractions,
            ArrayList<Component> components,
            Cubic eos,
            BinaryInteractionParameter kinteraction,
            double tolerance
            ){
            HashMap<Component,Double> K;
            double error=100;
            HashMap<Component,Double> x_;
            //HashMap<Component,Double> x;
            
            HashMap<Component,Double> y_;
           // HashMap<Component,Double> y;
            while(error >= tolerance){
                K = EquilibriumFunctions.equilibriumRelations(temperature, components, liquidFractions, pressure, vaporFractions, eos,kinteraction);
                error = calculateError(liquidFractions, vaporFractions, components, eos, pressure, temperature,kinteraction);
                vF = rachfordRice(K, mixtureFractions, vF,tolerance);
                x_=x_(mixtureFractions, K, vF);
                liquidFractions = newFractions(x_);
                y_ = y_(x_, K);
                vaporFractions = newFractions(y_);
                
            }
        
        return new FlashSolution(vF, liquidFractions, vaporFractions);
    }
    
    
    private static HashMap<Component,Double> y_(HashMap<Component,Double> x_,
            HashMap<Component,Double> k){
        HashMap<Component,Double> y_ = new HashMap<>();
        
        for(Component component: x_.keySet()){
            double ki = k.get(component);
            double x_i = x_.get(component);
            y_.put(component, x_i * ki);
        }
        return y_;
    }
    
    private static HashMap<Component,Double> newFractions(HashMap<Component,Double> x_){
        HashMap<Component,Double> x = new HashMap<>();
        double s =0;
        for(Component component: x_.keySet()){
            s += x_.get(component);
        }
        
        for(Component component: x_.keySet()){
            
            double x_i = x_.get(component);
            x.put(component, x_i /s);
        }
        
        return x;
    }
    
    private static HashMap<Component,Double> x_(
            HashMap<Component,Double> mixtureFractions,
            HashMap<Component,Double> k,
            double vF
            ){
        
        HashMap<Component,Double> x_ = new HashMap<>();
        
        for(Component component : k.keySet()){
            double zi = mixtureFractions.get(component);
            double ki = k.get(component);
            
            x_.put(component, zi / ( 1 + vF*(ki - 1)));
        }
        return x_;
    }
    
    private static double rachfordRice(HashMap<Component,Double> k, 
            HashMap<Component,Double> mixtureFractions,
            double vF,
            double tolerance
            ){
        
        double s=100;
        double s_;
        while(s > tolerance){
            s = s(k, mixtureFractions, vF);
            s_ = s_(k, mixtureFractions, vF);
            vF = vF - (s /s_);
        }
        return vF;
    }
    
    private static double s(HashMap<Component,Double> k,
            HashMap<Component,Double> mixtureFractions,   
            double vF
            ){
        
        double result =0;
        for(Component component : k.keySet()){
            
            double zi = mixtureFractions.get(component);
            double ki = k.get(component);
            
            result += (zi * (ki - 1 ))/( 1 + vF * ( ki - 1));
        }
        
        return result;
    }
    private static double s_(HashMap<Component,Double> k,
            HashMap<Component,Double> mixtureFractions,   
            double vF
            ){
        
        double result =0;
        for(Component component : k.keySet()){
            double zi = mixtureFractions.get(component);
            double ki = k.get(component);
            
            result += (- zi * Math.pow(ki - 1,2))/(Math.pow(1 + vF * (ki-1),2));
        }
        
        return result;
    }
    
    
    
    private static double calculateError(HashMap<Component,Double> liquidFractions,
            HashMap<Component,Double> vaporFractions, 
            ArrayList<Component> components,
            Cubic eos,
            double pressure,
            double temperature,
            BinaryInteractionParameter kinteraction
            ){
         throw new UnsupportedOperationException("Not supported yet.");
    
//        double result=0;
//        for(Component component: components){
//            
//            double xi = liquidFractions.get(component);
//            double yi = vaporFractions.get(component);
//            
//            double liquidFug = eos.calculateFugacity(pressure, 
//                    temperature, 
//                    components, 
//                    component, 
//                    liquidFractions, 
//                    Phase.LIQUID,
//                    kinteraction);
//            
//            double vaporFug = eos.calculateFugacity(pressure, 
//                    temperature,
//                    components, 
//                    component, 
//                    vaporFractions,
//                    Phase.VAPOR,
//                    kinteraction);
//            
//            result += Math.abs(xi* liquidFug - yi * vaporFug    );
//        }
//        
//        return result;
    }
    
}
