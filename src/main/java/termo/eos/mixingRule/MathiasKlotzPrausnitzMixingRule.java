package termo.eos.mixingRule;

import java.util.ArrayList;
import java.util.HashMap;
import termo.binaryParameter.BinaryInteractionParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class MathiasKlotzPrausnitzMixingRule extends MixingRule {
    public MathiasKlotzPrausnitzMixingRule(){
        name = "Mathias-Klotz-Prausnitz";
    }

    @Override
    public double a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double b(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double oneOverNParcial_aN2RespectN(Substance iComponent, Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double temperatureParcial_a(Mixture mixture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

//    @Override
//    public double a(
//            double temperature,
//            HashMap<Component, Double> singleAs,
//            HashMap<Component, Double> singleBs,
//            ArrayList<Component> components, 
//            HashMap<Component, Double> fractions, 
//            BinaryInteractionParameter k) {
//         double a = 0;
//       
//       
//      for(Component iComponent:components){
//          for(Component jComponent:components){
//              double xi = fractions.get(iComponent);
//              double xj = fractions.get(jComponent);
//              
//              double ai = singleAs.get(iComponent);
//              double aj = singleAs.get(jComponent);
//              
//              double kij = k.getValue(iComponent, jComponent);
//              double kji = k.getValue(jComponent, iComponent);
//              
//              a += xi * xj * Math.sqrt(ai * aj) * (1-kij) +  + xi * Math.pow(xj,3) * Math.sqrt(ai * aj) * (kij - kji);
//          }
//          
//      }
//       
//       return a;
//    }
//
//    @Override
//      public double b(HashMap<Component, Double> singleBs,ArrayList<Component> components,HashMap<Component,Double> fractions) {
//         double b = 0;
//       
//      for(Component iComponent:components){
//         
//            double xi = fractions.get(iComponent);
//            double bi = singleBs.get(iComponent);
//            b += xi * bi ;
//      }
//       
//       return b;
//    }
//
//    @Override
//    public double oneOverNParcial_aN2RespectN(
//            double temperature,
//            HashMap<Component, Double> single_as,
//            HashMap<Component, Double> single_bs,
//            HashMap<Component, Double> singleAlphas, 
//            ArrayList<Component> components, 
//            Component cl, 
//            HashMap<Component, Double> fractions, 
//            BinaryInteractionParameter k) {
//        
//        double xl =0;
//        double xj =0;
//        double xi =0;
//        
//        double al =0;
//        double aj =0;
//        double ai =0;
//        
//        double alphaiDeriv =0;
//        double alphajDeriv =0;
//        
//        double klj =0;
//        double kjl =0;
//        
//        double firstTerm =0;
//        double secondTerm =0;
//        double thirdTerm =0;
//        
//        double firstFactor =0;
//        double secondFactor =0;
//        
//        
//        for(Component cj: components){
//            xj =fractions.get(cj);
//            aj = single_as.get(cj);
//
//            klj = k.getValue(cl, cj);
//            kjl = k.getValue(cj, cl);
//            
//            firstTerm += xj * Math.sqrt(al * aj)*(2 - klj - kjl);
//            secondTerm += xj * Math.pow(al*aj, 1d/6d) * Math.pow(klj- kjl, 1d/3d);
//          
//        }
//        
//        for(Component ci : components){
//            
//        }
//        
//        
//        
//        
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public double temperatureParcial_a(
//            double temperature,
//            ArrayList<Component> components, 
//            HashMap<Component, Double> fractions,
//            HashMap<Component, Double> single_as,
//            HashMap<Component, Double> single_bs, 
//            HashMap<Component, Double> alphaDerivatives,
//            BinaryInteractionParameter k) {
//        
//        double xi =0;
//        double xj =0;
//        
//        double ai =0;
//        double aj =0;
//        
//        double alphaiDeriv =0;
//        double alphajDeriv =0;
//        
//        double kij =0;
//        double kji =0;
//        
//        double firstTerm =0;
//        double secondTerm =0;
//        
//        
//        double firstFactor =0;
//        double secondFactor =0;
//        
//        for(Component ci: components){
//            xi =fractions.get(ci);
//            ai = single_as.get(ci);
//            alphaiDeriv = alphaDerivatives.get(ci);
//            
//            firstFactor =0;
//            secondFactor =0;
//            
//            for(Component cj : components){
//                
//                xj = fractions.get(cj);
//                aj = single_as.get(cj);
//                alphajDeriv = alphaDerivatives.get(cj);
//                
//                kij = k.getValue(ci, cj);
//                kji = k.getValue(cj, ci);
//                
//                firstTerm +=  xi * xj * Math.sqrt(ai *aj)*(1-kij)*(alphaiDeriv + alphajDeriv);
//                
//                firstFactor += xj *Math.pow(ai*aj, 1d/6d) * Math.pow(kij-kji, 1d/3d);
//                secondFactor += xj*Math.pow(ai*aj, 1d/6d)*Math.pow(kij - kji,1d/3d) * (alphaiDeriv+alphajDeriv);
//            }
//            secondTerm += xi * Math.pow(firstFactor,2) * secondFactor;
//        }
//        return (1d/2d)*(firstTerm + secondFactor);
//    }


}
