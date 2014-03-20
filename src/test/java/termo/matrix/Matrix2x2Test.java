/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.matrix;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo
 */
public class Matrix2x2Test {
    
    public Matrix2x2Test() {
    }

    @Test
    public void testdeterminant() {
        System.out.println("2x2Determinant");
        double[][] matrix = {{3,4},{2,1}};
        Matrix2x2 m2 = new Matrix2x2(matrix);
        double det =m2.determinant();
        double expResult = -5;
        assertEquals(expResult,det,0);
    }
    
    @Test public void testmenor(){
        System.out.println("menor 2x2");
        double[][] matrix = {{3,4},{2,1}};
        Matrix2x2 m2 = new Matrix2x2(matrix);
        double result =m2.menor(1, 1);
        double expResult = 1;
        assertEquals(expResult,result,0);
    }
    @Test public void testmenor12(){
        System.out.println("menor12 2x2");
        double[][] matrix = {{3,4},{2,1}};
        Matrix2x2 m2 = new Matrix2x2(matrix);
        double result =m2.menor(1, 2);
        double expResult = -2;
        assertEquals(expResult,result,0);
    }
    
    @Test public void inverse(){
        System.out.println("inverse2x2");
        double[][] matrix = {{3,1},{2,1}};
        Matrix2x2 m2 = new Matrix2x2(matrix);
        double[][] result =m2.inverse();
        double[][] expResult = {{1, -1},{-2,3}};
        
        Assert.assertArrayEquals(expResult, result);
    }
}
