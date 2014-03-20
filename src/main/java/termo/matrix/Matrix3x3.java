package termo.matrix;

/**
 *
 * @author Hugo
 */
public class Matrix3x3 extends Matrix {//implementation for 3X3 matrix
    
    public Matrix3x3(double[][] matrix){
        super(matrix);
    }
    
    
    @Override
    public double[][] inverse(){
        double det = determinant();
        Matrix3x3 cofMatrix =new Matrix3x3( cofactorMatrix());
        Matrix3x3 transposeMatrix = new Matrix3x3(cofMatrix.transposeMatrix());
        return transposeMatrix.scalarMultiplication(1/det);
    }
    @Override
    public double menor(int i,int j){

        
        //todo refactor
        double result =0;
        if(i==1){
            if(j==1){
                result = menorWithouthSign(2,3,2,3);//a(2, 2)*a(3,3)-a(2,3)*a(3,2);
            }else if(j==2){
                result = menorWithouthSign(2,3,1,3);//a(2, 1)*a(3,3)-a(2,3)*a(3,1);
            }else if(j==3){
                result = menorWithouthSign(2,3,1,2);
            }
        }else if(i==2){
            if(j==1){
                result = menorWithouthSign(1,3, 2,3);
            }else if(j==2){
                result = menorWithouthSign(1,3,1,3);
            }else if(j==3){
                result = menorWithouthSign(1,3,1,2);
            }
        }else if(i==3){
            if(j==1){
                result = menorWithouthSign(1,2, 2,3);
            }else if(j==2){
                result = menorWithouthSign(1,2,1,3);
            }else if(j==3){
                result = menorWithouthSign(1,2,1,2);
            }
        }        
        return Math.pow(-1,i+j)* result ;
        //return -11;
    }
    
    
    
    
    @Override
    public double determinant(){
        double a11 = matrix[0][0];
        double a12 = matrix[0][1];
        double a13 = matrix[0][2];
        
        double a21 = matrix[1][0];
        double a22 = matrix[1][1];
        double a23 = matrix[1][2];
        
        double a31 = matrix[2][0];
        double a32 = matrix[2][1];
        double a33 = matrix[2][2];
        
        
        return a11*(a22*a33 - a23*a32)
                -a12*(a21*a33 - a23*a31)
                + a13*(a21*a32- a22*a31);
        
        
    }
}
