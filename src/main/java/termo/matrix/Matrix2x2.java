package termo.matrix;

/**
 *
 * @author Hugo
 */
public class Matrix2x2 extends Matrix{
    
    public Matrix2x2(double[][] matrix){
        super(matrix);
    }
    @Override
    public double[][] inverse(){
        double det = determinant();
        Matrix2x2 cofMatrix =new Matrix2x2( cofactorMatrix());
        Matrix2x2 transposeMatrix = new Matrix2x2(cofMatrix.transposeMatrix());
        return transposeMatrix.scalarMultiplication(1/det);
    }
    
     
    @Override
    public double menor(int i,int j){
        double result =0;
        if(i== 1){
            if(j ==1){
                result = a(2,2);
            }else if(j ==2){
                result = a(2,1);
            }
        }else if( i == 2){
            if(j ==1){
                result = a(1,2);
            }else if(j ==2){
                result = a(1,1);
            }
        }
        
        return Math.pow(-1,i+j)* result ;
    }
    
    public double determinant(){
        return a(1,1)*a(2,2) -a(1,2)*a(2,1);
    }
}
