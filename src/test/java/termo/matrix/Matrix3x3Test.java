package termo.matrix;

import org.junit.Test;
import static org.junit.Assert.*;

public class Matrix3x3Test {
    
    double[][] matrix ={
        {1,0,2},
        {2,-1,3},
        {4,1,8}
    };
 
    
//     @Test
//    public void testMatrixVectorMultiplication() {
//        System.out.println("matrixVectorMultiplication");
//        
//        Matrix3x3 instance = new Matrix3x3(matrix);
//        double[] vector = {5,3,2};
//        
//        double[] expResult  ={
//            9,
//            13,
//            39
//        };
//        double[] result = instance.matrixVectorMultiplication(vector);
//         assertArrayEquals(expResult, result);
//    }

    
    @Test
    public void testMatrixVectorMultiplication(){
        System.out.println("MatrixVectorMultiplication");
        
        Matrix3x3 instance = new Matrix3x3(matrix);
        double[] vector = {5,3,2};
        double[] expResult = {
            9,
            13,
            39
        };
        double[] result = instance.matrixVectorMultiplication(vector);
        assertArrayEquals(matrix, matrix);
    }

    /**
     * Test of inverse method, of class Matrix3x3.
     */
    @Test
    public void testInverse() {
        System.out.println("inverse");
        
        Matrix3x3 instance = new Matrix3x3(matrix);
        double[][] expResult  ={
            {-11,2,2},
            {-4,0,1},
            {6,-1,-1}
        };
        double[][] result = instance.inverse();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of cofactorMatrix method, of class Matrix3x3.
     */
    @Test
    public void testCofactorMatrix() {
        System.out.println("cofactorMatrix");
        
        Matrix3x3 instance = new Matrix3x3(matrix);
        double[][] expResult ={
            {-11,-4,6},
            {2,0,-1},
            {2,1,-1}
        };
        double[][] result = instance.cofactorMatrix();
        assertArrayEquals(expResult, result);
       
        
    }

    /**
     * Test of menor method, of class Matrix3x3.
     */
    @Test
    public void testMenor11() {
        System.out.println("menor");
        int i = 1;
        int j = 1;
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = -11;
        double result = instance.menor(i, j);
        assertEquals(expResult, result, 0.0);
       
    }
    
        @Test
    public void testMenor12() {
        System.out.println("menor");
        int i = 1;
        int j = 2;
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = -4;
        double result = instance.menor(i, j);
        assertEquals(expResult, result, 0.0);
       
    }

    @Test
    public void testMenor13() {
        System.out.println("menor");
        int i = 1;
        int j = 3;
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = 6;
        double result = instance.menor(i, j);
        assertEquals(expResult, result, 0.0);
       
    }
    @Test
    public void testMenor21() {
        System.out.println("menor");
        int i = 2;
        int j = 1;
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = 2;
        double result = instance.menor(i, j);
        assertEquals(expResult, result, 0.0);
       
    }
     @Test
    public void testMenor22() {
        System.out.println("menor");
        int i = 2;
        int j = 2;
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = 0;
        double result = instance.menor(i, j);
        assertEquals(expResult, result, 0.0);
       
    }
    
    /**
     * Test of determinant method, of class Matrix3x3.
     */
    @Test
    public void testDeterminant() {
        System.out.println("determinant");
        
        Matrix3x3 instance = new Matrix3x3(matrix);
        double expResult = 1;
        double result = instance.determinant();
        assertEquals(expResult, result, 0.0);
    
    }

    /**
     * Test of transposeMatrix method, of class Matrix3x3.
     */
    @Test
    public void testTransposeMatrix() {
         System.out.println("transpose");
        
        Matrix3x3 instance = new Matrix3x3(matrix);
        double[][] expResult ={
            {1,2,4},
            {0,-1,1},
            {2,3,8}
        };
        double[][] result = instance.transposeMatrix();
        assertArrayEquals(expResult, result);
       
        
    }


    
}
