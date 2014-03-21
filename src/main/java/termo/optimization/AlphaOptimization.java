
package termo.optimization;

import java.util.ArrayList;
import termo.data.ExperimentalData;
import termo.matrix.Matrix2x2;
import termo.matrix.Matrix3x3;
import termo.substance.HeterogeneousPureSubstance;

/**
 *
 * @author Hugo
 */
public class AlphaOptimization {
    HeterogeneousPureSubstance substance;
    //double[][] experimental ;
    ArrayList<ExperimentalData> experimental;
    double pass = 0.0001;
    
    public AlphaOptimization(HeterogeneousPureSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    public AlphaOptimization(HeterogeneousPureSubstance substance,ArrayList<ExperimentalData> experimental, double pass){
       this(substance, experimental);
       this.pass = pass;
    }
    
    
    
    
    public void solve(){
        int numberOfParameters = substance.getVapor().getAlpha().numberOfParameters();
        if(numberOfParameters == 1){
            solveVapoPressureRegression(
                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent())
            );
        }else if(numberOfParameters == 2){
            solveVapoPressureRegression(
                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent()),
                    substance.getVapor().getAlpha().getAlphaParameterB(substance.getVapor().getComponent())
            );
        }else if(numberOfParameters == 3){
            solveVapoPressureRegression(
                    substance.getVapor().getAlpha().getAlphaParameterA(substance.getVapor().getComponent()),
                    substance.getVapor().getAlpha().getAlphaParameterB(substance.getVapor().getComponent()), 
                    substance.getVapor().getAlpha().getAlphaParameterC(substance.getVapor().getComponent())
            );
        }
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
        double error_ = AlphaOptimization.this.vaporPressureError( value+pass);
        
        double deriv = (error_- error)/pass;
        
        return deriv;
        
    }
    public double secondDerivativeVaporPressureError( double value){
        
        double de = derivativeVaporPressureError( value);
        double de_ =derivativeVaporPressureError( value+pass);
        
        double deriv = (de_ - de)/pass;
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
        if(params.length >= 1){
            substance.getVapor().getAlpha().setAlphaParameterA(params[0], substance.getVapor().getComponent());
        } if(params.length >=2){
            substance.getVapor().getAlpha().setAlphaParameterB(params[1], substance.getVapor().getComponent());
        }if(params.length >=3){
            substance.getVapor().getAlpha().setAlphaParameterC(params[2], substance.getVapor().getComponent());
        }
        double error =0;
        for (ExperimentalData pair: experimental){
            substance.dewPressure(pair.getTemperature());
            double expP = pair.getPressure();
            
            error += Math.pow((substance.getPressure() - expP)/expP,2);
        }   
        return error;   
    }
//    public double derivative_A( double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A+pass,B,C);
//        return (error_- error)/pass;
//    }
    public double derivative_A(double... args){
        double error = vaporPressureError(args);
        args = applyDeltaOnA(args);
        double error_ = vaporPressureError(args);
        return(error_ - error)/pass;
    }
    
    public double[] applyDeltaOnA(double... args){
        args[0] = args[0] + pass;
        return args;
    }
    public double[] applyDeltaOnB(double... args){
        args[1] = args[1] + pass;
        return args;
    }
   
//    
//    public double derivative_B( double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A,B+pass,C);
//        return (error_- error)/pass;
//    }
    
    public double derivative_B(double... args){
        double error = vaporPressureError(args);
        args = applyDeltaOnB(args);
        double error_ = vaporPressureError(args);
        return(error_ - error)/pass;
    }
    
//    public double derivative_C(double A, double B, double C){
//        double error = vaporPressureError( A,B,C);
//        double error_ = vaporPressureError( A,B,C+ pass);
//        return (error_- error)/pass;
//    }
    public double derivative_C(double... args){
        double error = vaporPressureError(args);
        double error_ = vaporPressureError(args[0] , args[1],args[2]+ pass);
        return(error_ - error)/pass;
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
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivAB(double... args){
        double deriv = derivative_B(args);
        args = applyDeltaOnA(args);
        double deriv_ = derivative_B(args);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivAC(double... args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0] +pass, args[1], args[2]);
        
        return (deriv_-deriv)/pass;
    }
    
    
    
    
     public double doubleDerivBA(double...args){
        double deriv = derivative_A(args);
        args = applyDeltaOnB(args);
        double deriv_ = derivative_A(args);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivBB(double...args){
        double deriv = derivative_B(args);
        args = applyDeltaOnB(args);
        double deriv_ = derivative_B(args);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivBC(double... args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0], args[1]+pass, args[2]);
        
        return (deriv_ - deriv)/pass;
    }
    
    
    
    
    
      public double doubleDerivCA(double...args){
        double deriv = derivative_A(args);
        double deriv_ = derivative_A(args[0], args[1], args[2]+pass);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivCB(double... args){
        double deriv = derivative_B(args);
        double deriv_ = derivative_B(args[0], args[1] , args[2]+pass);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivCC(double...args){
        double deriv = derivative_C(args);
        double deriv_ = derivative_C(args[0], args[1] , args[2]+pass);
        
        return (deriv_ - deriv)/pass;
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
    
    
    public double[] nextValueMathias(double A, double B, double C){
        Matrix3x3 hessian = new Matrix3x3(hessian(A,B,C));
        Matrix3x3 hessianInverse = new Matrix3x3(hessian.inverse());
        double[] gradient = gradient(A, B, C);
        return hessianInverse.matrixVectorMultiplication(gradient);
        
    }
    
    public double[] nextValueMathias(double... args){
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
    
    
    public double[] solveVapoPressureRegression (double...args){
        
        double beforeError;
        double error;
        
        double criteria =50;
        while(Math.abs(criteria) > 1e-4){
            
            beforeError = vaporPressureError( args);
            args = nextValueMathias(args );
            
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
}
