package termo.matrix;

/**
 *
 * @author Hugo
 */
public class Matrix {
    protected double[][] matrix;
    public Matrix(double[][] matrix ){
        this.matrix = matrix;
    }
    
    public double a(int i , int j){
        return matrix[i-1][j-1];
    }
   
     public double determinant(){
        double result =0;
        
        if(matrix.length==1){
            result = a(1,1);
        }else{
            //determinante usando menores de la primer fila
            int firstRow =1;
            for(int col =1; col <= matrix.length; col++){
                double minor = menor(firstRow,col);
                result += a(firstRow,col) * minor;

            }
        }
        return result;
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
        double[][] minorMatrix = minorMatrix(i, j);
        double minorWithoutSign = 0;
        if(minorMatrix.length == 0){
            minorWithoutSign =1;
        }else{
            minorWithoutSign = minorWithothSign(minorMatrix);
        }
            
        return Math.pow(-1,i+j)* minorWithoutSign;
    }
    
    protected double minorWithothSign(double[][] minorMatrix){
        Matrix minor = new Matrix(minorMatrix);
        return minor.determinant();
    }
    
    
      public double[][] inverse(){
        double det = determinant();
        Matrix cofMatrix =new Matrix( cofactorMatrix());
        Matrix transposeMatrix = new Matrix(cofMatrix.transposeMatrix());
        return transposeMatrix.scalarMultiplication(1/det);
    }
    
    
    
    private double[][] minorMatrix(int row , int col){
        int minorDimension = matrix.length-1;
        double[][] minor = new double[minorDimension][minorDimension];
        int minorRow =0;
        int minorCol =0;
        for(int i =1 ; i <= matrix.length; i++){
            
            for(int j =1; j<=matrix.length; j++){
                
                if(i != row && j != col){
                    minor[minorRow][minorCol] = a(i,j);
                    
                }
                if(j != col){
                    minorCol++;
                }
            }
            if(i != row){
                minorRow++;
            }
            minorCol = 0;
        }
        return minor;
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
