

package termo.matrix;

/**
 *
 * @author Hugo
 */
public class Matrix {//implementation for 3X3 matrix
    
    double[][] matrix ;
    
    public Matrix(double[][] matrix ){
        this.matrix = matrix;
    }
    
    public double[] matrixVectorMultiplication(double[] vector){
        double[] result = new double[vector.length];
        for(int i = 1; i <=3; i++){
            
            double sum =0 ;
            
            for(int j = 1; j <=3; j++){
                sum+= a(i,j) * vector[j-1];
            }
            
            result[i-1] = sum;
            
        }
        
        return result;
    }
    
    public double[][] inverse(){
        double det = determinant();
        Matrix cofMatrix =new Matrix( cofactorMatrix());
        Matrix transposeMatrix = new Matrix(cofMatrix.transposeMatrix());
        return transposeMatrix.scalarMultiplication(1/det);
    }
    
    public double[][] scalarMultiplication(double scalar){
        double[][] product = new  double[matrix.length][matrix[0].length];
        
        for(int i = 0; i <matrix.length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                product[i][j] = matrix[i][j]*scalar;
            }
        }
        
        return product;
    }
    
    public double[][] transposeMatrix(){
        double[][] transpose = new double[matrix.length][matrix[0].length];
        
        for(int i =0 ;i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length;j++ ){
                transpose[j][i] = matrix[i][j];
            }
                
        }
        
        return transpose;
    }
    
    public double[][] cofactorMatrix(){
        double[][] cofactor = new double[matrix.length][matrix[0].length];
        
        
        for(int i=0;i<matrix.length; i++){
            for(int j = 0; j<matrix.length;j++){
                cofactor[i][j] = menor(i+1,j+1);
            }
        }
        
        return cofactor;
    }
    
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
    
    private double menorWithouthSign(int f1,int f2,int c1,int c2){
        return a(f1,c1)*a(f2,c2)- a(f1,c2)*a(f2,c1);
    }
    
    public double a(int i , int j){
        return matrix[i-1][j-1];
    }
    
    
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
