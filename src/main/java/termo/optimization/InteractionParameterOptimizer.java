
package termo.optimization;

import java.util.ArrayList;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.data.ExperimentalDataBinary;
import termo.eos.Cubic;
import termo.matrix.Matrix2x2;
import termo.matrix.Matrix3x3;
import termo.matter.HeterogeneousMixture;

/**
 *
 * @author Hugo
 */
public class InteractionParameterOptimizer {
    private final HeterogeneousMixture mixture;
    private ArrayList<ExperimentalDataBinary> experimental;
    private Compound referenceComponent;
    private Compound nonReferenceComponent;

    
    double optimizationTolerance = 1e-5;
    double deltaK = 0.00001;
    
    public InteractionParameterOptimizer(HeterogeneousMixture mixture) {
        this.mixture = mixture;
    }
    
    void optimizeTo(ArrayList<ExperimentalDataBinary> experimental) {
         this.experimental = experimental;
        
//         referenceComponent = experimental.get(0).getReferenceComponent();
//         nonReferenceComponent = experimental.get(0).getNonReferenceComponent();
         mixture.setPressure(experimental.get(0).getPressure());
         
         //  if(data.getDataType().equals(ExperimentalDataType.PRESSURE_CONSTANT)){
             bubbleTemperatureOptimization( );
   //     }else{
 //           return bubblePressureOptimization(eos,data);
  //      }
         
         
       // this.mixture.getInteractionParameters().setValue(comp1,comp2, -0.0765771);
    }
     
    
    
   
    
    
    
    
    

    private void bubbleTemperatureOptimization() {
        if(mixture.getInteractionParameters().isSymmetric()){
            oneVariableOptimization();
        }else if(!mixture.getInteractionParameters().isSymmetric()){
            twoVariableOptimization();
        }
       
    }
    private void oneVariableOptimization(){
         double objectiveFunction = 1000;
        double iterations =0;
        
        while(objectiveFunction > optimizationTolerance && iterations < 1000){
            iterations++;
            double d1 = derivative_A();
            double d2 = doubleDerivAA();
            double oldValue = mixture.getInteractionParameters().getValue(referenceComponent, nonReferenceComponent);
            double newValue = oldValue  - d1 / d2;
            mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, newValue);
            objectiveFunction = d1;
            
        }
    }
    private void twoVariableOptimization(){
        double objectiveFunction = 1000;
        double iterations = 0;
        
        double beforeError =0;
        double error = 0;
        
        while(Math.abs(objectiveFunction) > optimizationTolerance && iterations < 1000){
            iterations++;
            
            beforeError = calculateTempError();
            InteractionParameter params = mixture.getInteractionParameters();
            double k12 = params.getValue(referenceComponent, nonReferenceComponent);
            double k21 = params.getValue(nonReferenceComponent, referenceComponent);
            
            double[] before = {k12,k21};
            
            double[] newValues = nextValue(before);
            
            setNewValues(newValues);
            error = calculateTempError();
            objectiveFunction = error- beforeError;
            
        }
        
   
        
        
    }
    public void setNewValues(double... newValues){
        setK12(newValues[0]);
        setK21(newValues[1]);
        
    }
    
    
    public double[] nextValue(double... args){
        Matrix2x2 hessian = new Matrix2x2(hessian());
        Matrix2x2 hessianInverse = new Matrix2x2(hessian.inverse());
        double[] gradient = gradient();
        double []del = hessianInverse.matrixVectorMultiplication(gradient);

        double[] result = {args[0]- del[0], args[1]- del[1]};
        return result;
    }
   
    
     public double[][] hessian(){
        double[][] result = {
            {doubleDerivAA(),doubleDerivAB()},
            {doubleDerivBA(),doubleDerivBB()}
        };
        return result;
     }
     
  
    private double getK12(){
        return mixture.getInteractionParameters().getValue(referenceComponent, nonReferenceComponent);
    }
    private void setK12(double value){
        mixture.getInteractionParameters().setValue(referenceComponent, nonReferenceComponent, value);
    }
    
    private double getK21 (){
        return mixture.getInteractionParameters().getValue(nonReferenceComponent, referenceComponent);
    }
    private void setK21(double value){
        mixture.getInteractionParameters().setValue(nonReferenceComponent, referenceComponent, value);
    }
    
     
    private double doubleDerivAA(){
       
        double m = derivative_A();
        
        
        applyDeltaOnA();
        double m_ = derivative_A();
        
        return (m_ - m) / deltaK;
    }
       
   
    
    private double derivative_A(){
        
        double error = calculateTempError();
        applyDeltaOnA();
        double error_ = calculateTempError();
        
        return (error_ - error) / deltaK;
    }
    
    private double derivative_B(){
        
        double errror = calculateTempError();
        applyDeltaOnB();
        double error_ = calculateTempError();
        
        return (error_ - errror) / deltaK;
    }
    
     private double doubleDerivBB(){
        double m = derivative_B();
        applyDeltaOnB();
        double m_ = derivative_B();
        
        return (m_ - m) / deltaK;
    }
     
     
    public double doubleDerivAB(){        
        double deriv = derivative_B();
        applyDeltaOnA();
        double deriv_ = derivative_B();
        return (deriv_-deriv)/deltaK;
    }
    
    public double doubleDerivBA(){        
        double deriv = derivative_A();
        applyDeltaOnB();
        double deriv_ = derivative_A();
        return (deriv_-deriv)/deltaK;
    }
    
    public void applyDeltaOnA(){
         double k = getK12();
         double k_ = k + deltaK;
        setK12( k_);
    }
    
    public void applyDeltaOnB(){
         double k = getK21();
         double k_ = k + deltaK;
        setK21( k_);
    }
    
     public double[] gradient(){
        double[] gradient = {derivative_A(),derivative_B()};
        return gradient;
     }
//    public double[] gradient(double... args){
//        double[] gradient = new double[args.length];
//        if(args.length >=1){
//            gradient[0] = derivative_A(args);
//        }if(args.length >=2){
//            gradient[1] = derivative_B(args);
//        }if(args.length >=3){
//            gradient[2] = derivative_C(args);
//        }
//        
//        return gradient;
//    }
    
    
//    public double[] nextValue(double... args){
//        if(args.length ==1){
//            double[] result = {args[0] -derivative_A(args)/doubleDerivAA(args)};
//            return result;
//        }else if(args.length ==2){
//            Matrix2x2 hessian = new Matrix2x2(hessian(args));
//            Matrix2x2 hessianInverse = new Matrix2x2(hessian.inverse());
//            double[] gradient = gradient(args);
//            double []del = hessianInverse.matrixVectorMultiplication(gradient);
//
//            double[] result = {args[0]- del[0], args[1]- del[1]};
//            return result;
//        }else if(args.length ==3){
//            Matrix3x3 hessian = new Matrix3x3(hessian(args));
//            Matrix3x3 hessianInverse = new Matrix3x3(hessian.inverse());
//            double[] gradient = gradient(args);
//            double[] del= hessianInverse.matrixVectorMultiplication(gradient);
//            
//            double[] result = {args[0]- del[0], args[1]- del[1], args[2]-del[2]};
//            return result;
//        }
//        return null;
//    }
    
    
//     public double[][] hessian(double... args){
//        if(args.length==1){
//            double[][] result =  {{doubleDerivAA(args)}};
//            return result;
//        }else if(args.length==2){
//            double[][] result = {
//                {doubleDerivAA(args),doubleDerivAB(args)},
//                {doubleDerivBA(args),doubleDerivBB(args)}
//            };
//            return result;
//        
//        }else if(args.length==3){
//            double[][] result = {
//                {doubleDerivAA(args),doubleDerivAB(args),doubleDerivAC(args)},
//                {doubleDerivBA(args),doubleDerivBB(args),doubleDerivBC(args)},
//                {doubleDerivCA(args),doubleDerivCB(args),doubleDerivCC(args)}
//            };
//            return result;
//        }
//        return null;
//    }
    
    
    
    
 
    
 
    
    private double calculateTempError(){
 
            double error = 0;
        for(ExperimentalDataBinary data : experimental){
            
            mixture.setZFraction(referenceComponent, data.getLiquidFraction());
            mixture.setZFraction(nonReferenceComponent, 1-data.getLiquidFraction());
            
            mixture.bubbleTemperature();
            double tempCalc = mixture.getTemperature();
            double tempExp = data.getTemperature();
            
            error += Math.pow((tempCalc- tempExp)/tempExp,2);
            
        }
        
        return error;
    }

    private double bubblePressureOptimization(Cubic eos, ArrayList<ExperimentalDataBinary> data) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
