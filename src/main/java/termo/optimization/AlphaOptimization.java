
package termo.optimization;

import termo.matrix.Matrix;
import termo.substance.HeterogeneousPureSubstance;

/**
 *
 * @author Hugo
 */
public class AlphaOptimization {
    HeterogeneousPureSubstance substance;
    double[][] experimental ;
    double pass = 0.0001;
    
    public AlphaOptimization(HeterogeneousPureSubstance substance,double[][] experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    public AlphaOptimization(HeterogeneousPureSubstance substance,double[][] experimental, double pass){
       this(substance, experimental);
       this.pass = pass;
    }
    
    public double vaporPressureError( double paramValue) {
        substance.getComponent().setPrsvKappa(paramValue);
        double error =0;
        for (double[] pair: experimental){
            substance.dewPressure(pair[0]);
            double expP = pair[1];
            
            error += Math.pow((substance.getPressure() - expP)/expP,2);
        }   
        return error;
    }
    public double derivativeVaporPressureError( double value){
        
        double error = vaporPressureError( value);
        double error_ = vaporPressureError( value+pass);
        
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
    public double solveVapoPressureRegression (double firstEstimate){
        
        double beforeError;
        double error;
        double q =firstEstimate;
        
        double criteria =50;
        while(Math.abs(criteria) > 1e-4){
            
            beforeError = vaporPressureError( q);
            q = nextValue(q );
            
            error = vaporPressureError( q);
            
            criteria = error-beforeError;
        }
        return q;
    }
    
    
    
    public double vaporPressureErrorMathiasCopeman(double A, double B, double C){
        substance.getComponent().setA_Mathias_Copeman(A);
        substance.getComponent().setB_Mathias_Copeman(B);
        substance.getComponent().setC_Mathias_Copeman(C);
        double error =0;
        for (double[] pair: experimental){
            substance.dewPressure(pair[0]);
            double expP = pair[1];
            
            error += Math.pow((substance.getPressure() - expP)/expP,2);
        }   
        return error;
    }
    public double derivative_A( double A, double B, double C){
        double error = vaporPressureErrorMathiasCopeman( A,B,C);
        double error_ = vaporPressureErrorMathiasCopeman( A+pass,B,C);
        return (error_- error)/pass;
    }
    public double derivative_B( double A, double B, double C){
        double error = vaporPressureErrorMathiasCopeman( A,B,C);
        double error_ = vaporPressureErrorMathiasCopeman( A,B+pass,C);
        return (error_- error)/pass;
    }
    public double derivative_C(double A, double B, double C){
        double error = vaporPressureErrorMathiasCopeman( A,B,C);
        double error_ = vaporPressureErrorMathiasCopeman( A,B,C+ pass);
        return (error_- error)/pass;
    }
    public double[] gradient(double A, double B, double C){
        double[] gradient ={
            derivative_A(A, B, C),
            derivative_B(A, B, C),
            derivative_C(A, B, C)
        };
        return gradient;
    }
    
    
    
    public double doubleDerivAA(double A, double B, double C){
        double deriv = derivative_A(A, B, C);
        double deriv_ = derivative_A(A+pass, B, C);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivAB(double A, double B, double C){
        double deriv = derivative_B(A, B, C);
        double deriv_ = derivative_B(A+pass, B, C);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivAC(double A, double B, double C){
        double deriv = derivative_C(A, B, C);
        double deriv_ = derivative_C(A+pass, B, C);
        
        return (deriv_-deriv)/pass;
    }
    
    
    
    
     public double doubleDerivBA(double A, double B, double C){
        double deriv = derivative_A(A, B, C);
        double deriv_ = derivative_A(A, B+pass, C);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivBB(double A, double B, double C){
        double deriv = derivative_B(A, B, C);
        double deriv_ = derivative_B(A, B+pass, C);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivBC(double A, double B, double C){
        double deriv = derivative_C(A, B, C);
        double deriv_ = derivative_C(A, B+pass, C);
        
        return (deriv_ - deriv)/pass;
    }
    
    
    
    
    
      public double doubleDerivCA(double A, double B, double C){
        double deriv = derivative_A(A, B, C);
        double deriv_ = derivative_A(A, B, C+pass);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivCB(double A, double B, double C){
        double deriv = derivative_B(A, B, C);
        double deriv_ = derivative_B(A, B , C+pass);
        
        return (deriv_-deriv)/pass;
    }
    public double doubleDerivCC(double A, double B, double C){
        double deriv = derivative_C(A, B, C);
        double deriv_ = derivative_C(A, B , C+pass);
        
        return (deriv_ - deriv)/pass;
    }
       
    public double[][] hessian(double A, double B, double C){
        double[][] hess = {
            {doubleDerivAA(A, B, C),doubleDerivAB(A, B, C),doubleDerivAC(A, B, C)},
            {doubleDerivBA(A, B, C),doubleDerivBB(A, B, C),doubleDerivBC(A, B, C)},
            {doubleDerivCA(A, B, C),doubleDerivCB(A, B, C),doubleDerivCC(A, B, C)}
        };
        return hess;
    }
    
    public double[] nextValueMathias(double A, double B, double C){
        Matrix hessian = new Matrix(hessian(A,B,C));
        Matrix hessianInverse = new Matrix(hessian.inverse());
        double[] gradient = gradient(A, B, C);
        return hessianInverse.matrixVectorMultiplication(gradient);
        
    }
    
    
    public double[] solveMathiasCopemanVapoPressureRegression (double Afirst, double Bfirst, double Cfirst){
        
        double beforeError;
        double error;
        
        double A = Afirst;
        double B = Bfirst;
        double C = Cfirst;
        
        double criteria =50;
        while(Math.abs(criteria) > 1e-4){
            
            beforeError = vaporPressureErrorMathiasCopeman( A,B,C);
            double[] x = nextValueMathias(A,B,C );
            
            A = A- x[0];
            B = B-x[1];
            C = C -x[2];
            
            error = vaporPressureErrorMathiasCopeman(A,B,C);
            
            criteria = error-beforeError;
        }
        
        double[] sol = {A,B,C};
        return sol;
    }
}
