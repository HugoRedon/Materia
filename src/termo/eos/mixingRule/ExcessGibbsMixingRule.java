//package termo.eos.mixingRule;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import termo.Constants;
//import termo.activityModel.ActivityModel;
//import termo.binaryParameter.ActivityModelBinaryParameter;
//import termo.binaryParameter.BinaryInteractionParameter;
//import termo.binaryParameter.InteractionParameter;
//import termo.component.Component;
//import termo.substance.PureSubstance;
//
///**
// *
// * @author Hugo Redon Rivera
// */
//public class ExcessGibbsMixingRule {
//
//    private ActivityModel activityModel;
//    private double c1;
//    private double c2;
//    
////    @Override
////    public double a(double temperature,
////            HashMap<Component, Double> singleAs, 
////            HashMap<Component, Double> singleBs, 
////            ArrayList<Component> components, 
////            HashMap<Component, Double> fractions, 
////            BinaryInteractionParameter k) {
////        
////            ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)k;
////            
////            double b = b(singleBs, components, fractions);
////            double excessGibbs = activityModel.excessGibbsEnergy(components, fractions, param, temperature);
////            
////            double firstTerm = 0;
////            double secondTerm = 0;
////            
////            double xi=0;
////            double ai=0;
////            double bi = 0;
////            
////            for(Component ci : components){
////                 xi = fractions.get(ci);
////                 ai = singleAs.get(ci);
////                 bi = singleBs.get(ci);
////                
////                firstTerm = xi * (ai) / bi ;
////                secondTerm = Constants.R * temperature * c1 *xi * Math.log(b / bi);
////                
////            }
////       return b* (firstTerm + secondTerm + c2 * excessGibbs);
////    }
//
////    @Override
//    public double b(HashMap<PureSubstance,Double> fractions) {
//         double b = 0;
//      for(PureSubstance iComponent:fractions.keySet()){
//            double xi = fractions.get(iComponent);
//            double bi = iComponent.calculate_b_cubicParameter();//singleBs.get(iComponent);
//            b += xi * bi ;
//      }
//       return b;
//    }
////    @Override
////    public double oneOverNParcial_aN2RespectN(
////            double temperature,
////            HashMap<Component, Double> singleAs,
////            HashMap<Component, Double> singleBs, 
////            HashMap<Component, Double> singleAlphas, 
////            ArrayList<Component> components,
////            Component ci, 
////            HashMap<Component, Double> fractions, 
////            BinaryInteractionParameter k) {
////         
////        double b = b(singleBs, components, fractions);
////        double a =a(temperature, singleAs, singleBs, components, fractions, k);
////        
////        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)k;
////        double alphai = singleAlphas.get( ci);
////        double gammai = activityModel.activityCoefficient(components, ci, fractions, param, temperature);
////        double bi = singleBs.get(ci);
////
////        return b * Constants.R * temperature*( alphai + this.c1 * (Math.log(b / bi) + ((bi- b)/b)) + this.c2 * Math.log(gammai)) + a * bi / b;
////    }
//
////    @Override
//    public double temperatureParcial_a(
//            double temperature,
//            
//            HashMap<PureSubstance, Double> fractions, 
//        
//            BinaryInteractionParameter k) {
////        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)k;
//        
//        double b = b( fractions);
//        double excessDeriv = activityModel.parcialExcessGibbsRespectTemperature(null, null, param, temperature);
//        
//        double xi =0;
//        double ai =0;
//        double bi =0;
//        double alphaiDeriv =0;
//        
//        double firstTerm = 0;
//        double secondTerm =0;
//        double thirdTerm =0;
//        
//        
//        for(PureSubstance ci:fractions.keySet()){
//            ai = ci.calculate_a_cubicParameter(temperature);//single_as.get(ci);
//            bi = ci.calculate_b_cubicParameter();//single_bs.get(ci);
//            alphaiDeriv = ci.getAlpha().TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ci.getComponent());//alphaDerivatives.get(ci);
//            
//            firstTerm += xi * (ai / bi) * alphaiDeriv;
//            secondTerm += Constants.R * temperature * this.c1 * xi  * Math.log(b / bi);
//            thirdTerm += temperature * this.c2 * excessDeriv;
//            
//            
//        }
//        return b * (firstTerm + secondTerm + thirdTerm);
//        
//    }
//
//
////    @Override
//    public double oneOverNParcial_aN2RespectN(double temperature, PureSubstance iComponent, HashMap<PureSubstance, Double> fractions, InteractionParameter k) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
////    @Override
//    public double a(double temperature, HashMap<PureSubstance, Double> fractions, InteractionParameter k) {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
// 
//    
//}
