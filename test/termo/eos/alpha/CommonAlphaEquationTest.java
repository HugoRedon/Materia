package termo.eos.alpha;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;

/**
 *
 * @author
 * Hugo
 */
public class CommonAlphaEquationTest {
    
    public CommonAlphaEquationTest() {
    }

    /**
     * Test of alpha method, of class CommonAlphaEquation.
     */
    @Test
    public void testAlpha() {
	System.out.println("alpha");
	double temperature = 0.0;
	Component component = null;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.alpha(temperature, component);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of m method, of class CommonAlphaEquation.
     */
    @Test
    public void testM() {
	System.out.println("m");
	Component component = null;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.m(component);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of TempOverAlphaTimesDerivativeAlphaRespectTemperature method, of class CommonAlphaEquation.
     */
    @Test
    public void testTempOverAlphaTimesDerivativeAlphaRespectTemperature() {
	System.out.println("TempOverAlphaTimesDerivativeAlphaRespectTemperature");
	double temperature = 0.0;
	Component component = null;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getR1 method, of class CommonAlphaEquation.
     */
    @Test
    public void testGetR1() {
	System.out.println("getR1");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.getR1();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setR1 method, of class CommonAlphaEquation.
     */
    @Test
    public void testSetR1() {
	System.out.println("setR1");
	double r1 = 0.0;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	instance.setR1(r1);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getR2 method, of class CommonAlphaEquation.
     */
    @Test
    public void testGetR2() {
	System.out.println("getR2");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.getR2();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setR2 method, of class CommonAlphaEquation.
     */
    @Test
    public void testSetR2() {
	System.out.println("setR2");
	double r2 = 0.0;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	instance.setR2(r2);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getR3 method, of class CommonAlphaEquation.
     */
    @Test
    public void testGetR3() {
	System.out.println("getR3");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.getR3();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setR3 method, of class CommonAlphaEquation.
     */
    @Test
    public void testSetR3() {
	System.out.println("setR3");
	double r3 = 0.0;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	instance.setR3(r3);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getR4 method, of class CommonAlphaEquation.
     */
    @Test
    public void testGetR4() {
	System.out.println("getR4");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.getR4();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setR4 method, of class CommonAlphaEquation.
     */
    @Test
    public void testSetR4() {
	System.out.println("setR4");
	double r4 = 0.0;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	instance.setR4(r4);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class CommonAlphaEquation.
     */
    @Test
    public void testGetX() {
	System.out.println("getX");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	double expResult = 0.0;
	double result = instance.getX();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setX method, of class CommonAlphaEquation.
     */
    @Test
    public void testSetX() {
	System.out.println("setX");
	double x = 0.0;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	instance.setX(x);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class CommonAlphaEquation.
     */
    @Test
    public void testHashCode() {
	System.out.println("hashCode");
	CommonAlphaEquation instance = new CommonAlphaEquation();
	int expResult = 0;
	int result = instance.hashCode();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class CommonAlphaEquation.
     */
    @Test
    public void testEquals() {
	System.out.println("equals");
	Object obj = null;
	CommonAlphaEquation instance = new CommonAlphaEquation();
	boolean expResult = false;
	boolean result = instance.equals(obj);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}