package termo.matrix;

/**
 *
 * @author Hugo
 */
public abstract class Matrix {
    protected double[][] matrix;
    public Matrix(double[][] matrix ){
        this.matrix = matrix;
    }
    
    public double a(int i , int j){
        return matrix[i-1][j-1];
    }
    protected double menorWithouthSign(int f1,int f2,int c1,int c2){
        return a(f1,c1)*a(f2,c2)- a(f1,c2)*a(f2,c1);
    }
    
    public abstract double menor(int i , int j);
    public abstract double[][] inverse();
    public abstract double determinant();
    
    public double[][] cofactorMatrix(){
        double[][] cofactor = new double[matrix.length][matrix[0].length];
        
        
        for(int i=0;i<matrix.length; i++){
            for(int j = 0; j<matrix.length;j++){
                cofactor[i][j] = menor(i+1,j+1);
            }
        }
        
        return cofactor;
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
       
    public double[][] scalarMultiplication(double scalar){
        double[][] product = new  double[matrix.length][matrix[0].length];
        
        for(int i = 0; i <matrix.length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                product[i][j] = matrix[i][j]*scalar;
            }
        }
        
        return product;
    }
    public double[] matrixVectorMultiplication(double[] vector){
        double[] result = new double[vector.length];
        for(int i = 1; i <= matrix.length; i++){
            double sum =0 ;   
            for(int j = 1; j <= matrix.length; j++){
                sum+= a(i,j) * vector[j-1];
            }
            result[i-1] = sum;
        }
        return result;
    }
}
