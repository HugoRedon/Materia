//package termo.eos;
//
//import termo.Constants;
//import termo.component.Component;
//import termo.eos.alpha.Alpha;
//
///**
// *
// * @author Hugo Redon Rivera
// */
// public class CubicAlpha extends Cubic{
////    private Alpha alpha;
//    private double omega_a;
//    private double omega_b;
//    
////    public CubicAlpha(Alpha alpha){
////       // setAlpha(alpha);
////    }
//    
//    protected CubicAlpha(){
//        
//    }
//    
////    final protected void setAlpha(Alpha alpha){
////        this.alpha = alpha;
////       // initialize();
////        
//////        alpha.setEOSName(getName());      
////    }
////    protected Alpha getAlpha(){
////        return this.alpha;
////    }
//    
//    protected void setOmega_a(double omega_a){
//        this.omega_a = omega_a;
//    }
//    protected double getOmega_a(){
//        return this.omega_a;
//    }
//    protected void setOmega_b(double omega_b){
//        this.omega_b = omega_b;
//    }
//    protected double getOmega_b(){
//        return this.omega_b;
//    }
//    
//   // abstract protected void initialize();
//    
////    @Override
////    protected void  parametersOneComponent(double aTemperature,Component aComponent){
////        
////        set_a(single_a(aTemperature, aComponent));
////        set_b( single_b( aComponent));
////        
////    }
//    @Override
//    protected double single_a(double temperature, Component component){
//        
//        double tc = component.getCriticalTemperature();
//        double pc = component.getCriticalPressure();
//        
//        return (omega_a * Math.pow( Constants.R  * tc,2) *getAlpha().alpha(temperature,component) )/ (pc);
//        
//    }
//    @Override
//    protected  double single_b(Component aComponent){
//       
//        double tc = aComponent.getCriticalTemperature();
//        double pc = aComponent.getCriticalPressure();
//        
//        return (omega_b * tc* Constants.R)/ (pc); 
//    }
//     
//}
