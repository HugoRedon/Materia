package termo.matrix;

/**
 *
 * @author Hugo
 */
public class Matrix3x3 extends Matrix {//implementation for 3X3 matrix
    
    public Matrix3x3(double[][] matrix){
        super(matrix);
    }
    
   
//    @Override
//    public double determinant(){
//        double a11 = matrix[0][0];
//        double a12 = matrix[0][1];
//        double a13 = matrix[0][2];
//        
//        double a21 = matrix[1][0];
//        double a22 = matrix[1][1];
//        double a23 = matrix[1][2];
//        
//        double a31 = matrix[2][0];
//        double a32 = matrix[2][1];
//        double a33 = matrix[2][2];
//        
//        
//        return a11*(a22*a33 - a23*a32)
//                -a12*(a21*a33 - a23*a31)
//                + a13*(a21*a32- a22*a31);
//        
//        
//    }
  
}
