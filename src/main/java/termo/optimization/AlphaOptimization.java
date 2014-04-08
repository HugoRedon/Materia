
package termo.optimization;

import java.util.ArrayList;
import java.util.List;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.alpha.Alpha;
import termo.matrix.Matrix2x2;
import termo.matrix.Matrix3x3;
import termo.matter.HeterogeneousSubstance;

/**
 *
 * @author Hugo
 */
public class AlphaOptimization {
    HeterogeneousSubstance substance;
    //double[][] experimental ;
    private ArrayList<ExperimentalData> experimental;
    private double numericalDerivativeDelta = 0.0001;
    
    private boolean fixParameterA;
    private boolean fixParameterB;
    private boolean fixParameterC;
    
    public int fixedVariables(){
        int result = 0;
        if(fixParameterA){
            result++;
        }if(fixParameterB){
            result++;
        }if(fixParameterC){
            result++;
        }
        return result;
    }
    
    public AlphaOptimization(HeterogeneousSubstance substance){
        this.substance = substance;
    }
    
    public AlphaOptimization(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    public AlphaOptimization(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental, double pass){
       this(substance, experimental);
       this.numericalDerivativeDelta = pass;
    }
    
    
    
    
    public void solve(){
        int numberOfParameters = substance.getVapor().getAlpha().numberOfParameters();
        Component component = substance.getVapor().getComponent();
        Alpha alpha = substance.getVapor().getAlpha();
        
        int fixedParameters = fixedVariables();
        
        int numberOfVariablesToOptimize = numberOfParameters- fixedParameters;
        if(numberOfVariablesToOptimize == 0){
            return;
        }
        double[] initialValues = new double[numberOfVariablesToOptimize];
        
   
        
        if(numberOfVariablesToOptimize >=1){
            if(!fixParameterA){
                initialValues[0] = alpha.getAlphaParameterA(component);
            }else if(!fixParameterB){
                initialValues[0] = alpha.getAlphaParameterB(component);
            }else if(!fixParameterC){
                initialValues[0] = alpha.getAlphaParameterC(component);
            }
        }
        
        if(numberOfVariablesToOptimize >=2){
            if(!fixParameterB){
                initialValues[1] = alpha.getAlphaParameterB(component);
            }else if(!fixParameterC){
                initialValues[1] = alpha.getAlphaParameterC(component);
            }
            
        }
        
        if(numberOfVariablesToOptimize >=3){
           initialValues[2] = alpha.getAlphaParameterC(component);
        }
        
        
        
        solveVapoPressureRegression(initialValues);
        
//        if(numberOfParameters == 1){
//            solveVapoPressureRegression(
//                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent())
//            );
//        }else if(numberOfParameters == 2){
//            solveVapoPressureRegression(
//                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent()),
//                    substance.getVapor().getAlpha().getAlphaParameterB(substance.getVapor().getComponent())
//            );
//        }else if(numberOfParameters == 3){
//            solveVapoPressureRegression(
//                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent()),
//                    substance.getVapor().getAlpha().getAlphaParameterB(substance.getVapor().getComponent()), 
//                    substance.getVapor().getAlpha().getAlphaParameterC(substance.getVapor().getComponent())
//            );
//        }
    }
    
    
    
    
    
    
    
    
    
    
    //newton raphson de una variable
//    public double vaporPressureError( double paramValue) {
//        substance.getAlpha().setAlphaParameterA(paramValue,substance.getComponent());
//       // substance.getComponent().setK_StryjekAndVera(paramValue);// generalizar para mathias
//        double error =0;
//        for (ExperimentalData pair: experimental){
//            substance.dewPressure(pair.getTemperature());
//            double expP = pair.getPressure();
//            
//            error += Math.pow((substance.getPressure() - expP)/expP,2);
//        }   
//        return error;
//    }
    public double derivativeVaporPressureError( double value){
        
        double error = AlphaOptimization.this.vaporPressureError( value);
        double error_ = AlphaOptimization.this.vaporPressureError( value+numericalDerivativeDelta);
        
        double deriv = (error_- error)/numericalDerivativeDelta;
        
        return deriv;
        
    }
    public double secondDerivativeVaporPressureError( double value){
        
        double de = derivativeVaporPressureError( value);
        double de_ =derivativeVaporPressureError( value+numericalDerivativeDelta);
        
        double deriv = (de_ - de)/numericalDerivativeDelta;
        return deriv;
    }
    public double nextValue( double before){
        
        return before -derivativeVaporPressureError( before)/secondDerivativeVaporPressureError( before);
    }
//    public double solveVapoPressureRegression (double firstEstimate){
//        
//        double beforeError;
//        double error;
//        double q =firstEstimate;
//        
//        double criteria =50;
//        while(Math.abs(criteria) > 1e-4){
//            
//            beforeError = AlphaOptimization.this.vaporPressureError( q);
//            q = nextValue(q );
//            
//            error = AlphaOptimization.this.vaporPressureError( q);
//            
//            criteria = error-beforeError;
//        }
//        return q;
//    }
    
    
    
    
    
    //Newton raphson multivariable (hasta 3 variables)
    
   
    
    public double vaporPressureError(double... params){
        
         int numberOfParameters = substance.getVapor().getAlpha().numberOfParameters();
        Component component = substance.getVapor().getComponent();
        Alpha alpha = substance.getVapor().getAlpha();
        
        int fixedParameters = fixedVariables();
        
        int numberOfVariablesToOptimize = numberOfParameters- fixedParameters;
        if(numberOfVariablesToOptimize >=1){
            if(!fixParameterA){
                alpha.setAlphaParameterA(params[0],component);
            }else if(!fixParameterB){
                alpha.setAlphaParameterB(params[0],component);
            }else if(!fixParameterC){
                alpha.setAlphaParameterC(params[0],component);
            }
        }
        
        if(numberOfVariablesToOptimize >=2){
            if(!fixParameterB){
                alpha.setAlphaParameterB(params[1],component);
            }else if(!fixParameterC){
                alpha.setAlphaParameterC(params[1],component);
            }
            
        }
        
        if(numberOfVariablesToOptimize >=3){
           alpha.setAlphaParameterC(params[2],component);
        }
        
        
        
        
//        if(params.length >= 1 && !fixParameterA){
//            substance.getVapor().getAlpha().setAlphaParameterA(params[0], substance.getVapor().getComponent());
//        } if(params.length >=2 && !fixParameterB){
//            substance.getVapor().getAlpha().setAlphaParameterB(params[1], substance.getVapor().getComponent());
//        }if(params.length >=3 && !fixParameterC){
//            substance.getVapor().getAlpha().setAlphaParameterC(params[2], substance.getVapor().getComponent());
//        }
        double error =0;
        for (ExperimentalData pair: experimental){
            substance.dewPressure(pair.getTemperature());
            double expP = pair.getPressure();
            double calcP = substance.getPressure();
            error += Math.pow((calcP - expP)/expP,2);
        }   
        return error;   
    }
//    public double derivative_A( double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A+numericalDerivativeDelta,B,C);
//        return (error_- error)/pass;
//    }
    public double derivative_A(double... args){
        double error = vaporPressureError(args);
        args = applyDeltaOnA(args);
        double error_ = vaporPressureError(args);
        return(error_ - error)/numericalDerivativeDelta;
    }
    
    public double[] applyDeltaOnA(double... args){
        args[0] = args[0] + numericalDerivativeDelta;
        return args;
    }
    public double[] applyDeltaOnB(double... args){
        args[1] = args[1] + numericalDerivativeDelta;
        return args;
    }
   
//    
//    public double derivative_B( double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A,B+numericalDerivativeDelta,C);
//        return (error_- error)/pass;
//    }
    
    public double derivative_B(double... args){
        double error = vaporPressureError(args);
        args = applyDeltaOnB(args);
        double error_ = vaporPressureError(args);
        return(error_ - error)/numericalDerivativeDelta;
    }
    
//    public double derivative_C(double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A,B,C+ numericalDerivativeDelta);
//        return (error_- error)/pass;
//    }
    public double derivative_C(double... args){
        double error = vaporPressureError(args);
        double error_ = vaporPressureError(args[0] , args[1],args[2]+ numericalDerivativeDelta);
        return(error_ - error)/numericalDerivativeDelta;
    }
    
    
//    
//    public double[] gradient(double A, double B, double C){
//        double[] gradient ={
//            derivative_A(A, B, C),
//            derivative_B(A, B, C),
//            derivative_C(A, B, C)
//        };
//        return gradient;
//    }
    public double[] gradient(double... args){
        double[] gradient = new double[args.length];
        if(args.length >=1){
            gradient[0] = derivative_A(args);
        }if(args.length >=2){
            gradient[1] = derivative_B(args);
        }if(args.length >=3){
            gradient[2] = derivative_C(args);
        }
        
        return gradient;
    }
    
    
    
    public double doubleDerivAA(double...args){
        double deriv = derivative_A(args);
        args = applyDeltaOnA(args);
        double deriv_ = derivative_A(args);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivAB(double... args){
        double deriv = derivative_B(args);
        args = applyDeltaOnA(args);
        double deriv_ = derivative_B(args);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivAC(double... args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0] +numericalDerivativeDelta, args[1], args[2]);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    
    
    
    
     public double doubleDerivBA(double...args){
        double deriv = derivative_A(args);
        args = applyDeltaOnB(args);
        double deriv_ = derivative_A(args);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivBB(double...args){
        double deriv = derivative_B(args);
        args = applyDeltaOnB(args);
        double deriv_ = derivative_B(args);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivBC(double... args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0], args[1]+numericalDerivativeDelta, args[2]);
        
        return (deriv_ - deriv)/numericalDerivativeDelta;
    }
    
    
    
    
    
      public double doubleDerivCA(double...args){
        double deriv = derivative_A(args);
        double deriv_ = derivative_A(args[0], args[1], args[2]+numericalDerivativeDelta);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivCB(double... args){
        double deriv = derivative_B(args);
        double deriv_ = derivative_B(args[0], args[1] , args[2]+numericalDerivativeDelta);
        
        return (deriv_-deriv)/numericalDerivativeDelta;
    }
    public double doubleDerivCC(double...args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0], args[1] , args[2]+numericalDerivativeDelta);
        
        return (deriv_ - deriv)/numericalDerivativeDelta;
    }
       
//    public double[][] hessian(double A, double B, double C){
//        double[][] hess = {
//            {doubleDerivAA(A, B, C),doubleDerivAB(A, B, C),doubleDerivAC(A, B, C)},
//            {doubleDerivBA(A, B, C),doubleDerivBB(A, B, C),doubleDerivBC(A, B, C)},
//            {doubleDerivCA(A, B, C),doubleDerivCB(A, B, C),doubleDerivCC(A, B, C)}
//        };
//        return hess;
//    }
    
    public double[][] hessian(double... args){
        
        
        if(args.length==1){
            double[][] result =  {{doubleDerivAA(args)}};
            return result;
        }else if(args.length==2){
            double[][] result = {
                {doubleDerivAA(args),doubleDerivAB(args)},
                {doubleDerivBA(args),doubleDerivBB(args)}
            };
            return result;
        
        }else if(args.length==3){
            double[][] result = {
                {doubleDerivAA(args),doubleDerivAB(args),doubleDerivAC(args)},
                {doubleDerivBA(args),doubleDerivBB(args),doubleDerivBC(args)},
                {doubleDerivCA(args),doubleDerivCB(args),doubleDerivCC(args)}
            };
            return result;
        }
        return null;
    }
    
    
//    public double[] nextValueMathias(double A, double B, double C){
//        Matrix3x3 hessian = new Matrix3x3(hessian(A,B,C));
//        Matrix3x3 hessianInverse = new Matrix3x3(hessian.inverse());
//        double[] gradient = gradient(A, B, C);
//        return hessianInverse.matrixVectorMultiplication(gradient);
//        
//    }
    
    public double[] nextValue(double... args){
        if(args.length ==1){
            double[] result = {args[0] -derivative_A(args)/doubleDerivAA(args)};
            return result;
        }else if(args.length ==2){
            Matrix2x2 hessian = new Matrix2x2(hessian(args));
            Matrix2x2 hessianInverse = new Matrix2x2(hessian.inverse());
            double[] gradient = gradient(args);
            double []del = hessianInverse.matrixVectorMultiplication(gradient);

            double[] result = {args[0]- del[0], args[1]- del[1]};
            return result;
        }else if(args.length ==3){
            Matrix3x3 hessian = new Matrix3x3(hessian(args));
            Matrix3x3 hessianInverse = new Matrix3x3(hessian.inverse());
            double[] gradient = gradient(args);
            double[] del= hessianInverse.matrixVectorMultiplication(gradient);
            
            double[] result = {args[0]- del[0], args[1]- del[1], args[2]-del[2]};
            return result;
        }
        return null;
    }
    
    private int iterations;
    private double tolerance = 1e-4;
    
    private ArrayList<Parameters_Error> convergenceHistory = new ArrayList();
    
    public double[] solveVapoPressureRegression (double...args){
        
        double beforeError;
        double error;
        
        double criteria =50;
        
        iterations = 0;
        
        while(Math.abs(criteria) > tolerance){
            iterations++;
            beforeError = vaporPressureError( args);
            double[] before = args;
            
            Parameters_Error pe = new Parameters_Error(before, beforeError);
            convergenceHistory.add(pe);
            
            
            args = nextValue(args );
            args = applyDamping(before, args);
//            if(args.length >=1){
//                args[0] = args[0]- x[0];
//            }if(args.length >=2){
//                args[1] = args[1]-x[1];
//            }if(args.length >=3){
//                args[2] = args[2] -x[2];
//            }
            error = vaporPressureError(args);
            
            criteria = error-beforeError;
        }
        if(args.length ==1){
            double[] result = {args[0]};
            return result;
        }else if(args.length ==2){
            double[] result = {args[0],args[1]};
            return result;
        }else if(args.length ==3){
            double[] result = {args[0],args[1],args[2]};
            return result;
        }
        
        return null;
    }
    private double damp = 1;
    public double[] applyDamping(double[] before, double[]newValues ){
        double[] result = new double[before.length];
        for(int i = 0; i < result.length;i++){
            double step = newValues[i] - before[i];
            result[i] = before[i] + step* getDamp();
            
        }
        return result;
    }

    /**
     * @return the damp
     */
    public double getDamp() {
        return damp;
    }

    /**
     * @param damp the damp to set
     */
    public void setDamp(double damp) {
        this.damp = damp;
    }

    /**
     * @return the numericalDerivativeDelta
     */
    public double getNumericalDerivativeDelta() {
        return numericalDerivativeDelta;
    }

    /**
     * @param numericalDerivativeDelta the numericalDerivativeDelta to set
     */
    public void setNumericalDerivativeDelta(double numericalDerivativeDelta) {
        this.numericalDerivativeDelta = numericalDerivativeDelta;
    }

    /**
     * @return the experimental
     */
    public ArrayList<ExperimentalData> getExperimental() {
        return experimental;
    }

    /**
     * @param experimental the experimental to set
     */
    public void setExperimental(ArrayList<ExperimentalData> experimental) {
        this.experimental = experimental;
    }

    /**
     * @return the iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * @return the tolerance
     */
    public double getTolerance() {
        return tolerance;
    }

    /**
     * @param tolerance the tolerance to set
     */
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * @return the fixParameterA
     */
    public boolean isFixParameterA() {
        return fixParameterA;
    }

    /**
     * @param fixParameterA the fixParameterA to set
     */
    public void setFixParameterA(boolean fixParameterA) {
        this.fixParameterA = fixParameterA;
    }

    /**
     * @return the fixParameterB
     */
    public boolean isFixParameterB() {
        return fixParameterB;
    }

    /**
     * @param fixParameterB the fixParameterB to set
     */
    public void setFixParameterB(boolean fixParameterB) {
        this.fixParameterB = fixParameterB;
    }

    /**
     * @return the fixParameterC
     */
    public boolean isFixParameterC() {
        return fixParameterC;
    }

    /**
     * @param fixParameterC the fixParameterC to set
     */
    public void setFixParameterC(boolean fixParameterC) {
        this.fixParameterC = fixParameterC;
    }

    /**
     * @return the convergenceHistory
     */
    public ArrayList<Parameters_Error> getConvergenceHistory() {
        return convergenceHistory;
    }

    /**
     * @param convergenceHistory the convergenceHistory to set
     */
    public void setConvergenceHistory(ArrayList<Parameters_Error> convergenceHistory) {
        this.convergenceHistory = convergenceHistory;
    }
}
