//package termo.activityModel;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import termo.binaryParameter.BinaryInteractionParameters;
//import termo.component.Component;
//import termo.binaryParameter.WilsonParameters;
//
///**
// *
// * @author Hugo Redon Rivera
// */
//public class Wilson {
//    public double excessGibbsEnergy(ArrayList<Component> components,
//            HashMap<Component,Double> fractions,
//            BinaryInteractionParameters k,
//            double temperature){
//        
//         WilsonParameters params = (WilsonParameters)k;
//        
//        double excessGibbs = 0;
//        for (Component ci : components){
//            double xi = fractions.get(ci);
//            excessGibbs -= xi * Math.log( summa(ci, components, fractions,params,temperature));
//        }
//        return excessGibbs;
//    }
//    public double activityCoefficient(ArrayList<Component> components,
//            Component ci,
//            HashMap<Component,Double> fractions,
//            BinaryInteractionParameters k,
//            double temperature){
//        
//        WilsonParameters params = (WilsonParameters)k;
//        double thirdTerm = 0;
//        for(Component cj: components){
//            double xj = fractions.get(cj);
//            thirdTerm += (summa(cj, components, fractions, params,temperature));
//        }
//    return  - Math.log(summa(ci, components, fractions, params,temperature)) + 1 - thirdTerm;
//    }
//    private double summa(
//            Component ci, 
//            ArrayList<Component> components, 
//            HashMap<Component,Double> fractions,
//            WilsonParameters params,
//            double temperature){
//        
//        double summa = 0;
//         for(Component cj: components){
//                double xj = fractions.get(cj);
//                double gammaij = params.Aij(ci, cj, temperature);  
//                summa += xj * gammaij;
//            }
//        return summa;
//    }
//}
