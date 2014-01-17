/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class CubicTest {
  
    
    public CubicTest() {
    }

    /**
     * Test of getEquation method, of class Cubic.
     */
    @Test
    public void testGetEquation() {
	System.out.println("getEquation");
	Cubic instance = new Cubic();
	String expResult = "\\( P = \\frac{RT}{v - b} - \\frac{a} { v^2 + u b v + w b^2} \\)";
	String result = instance.getEquation();
	assertEquals(expResult, result);
	
    }

    /**
     * Test of calculatePressure method, of class Cubic.
     */
    @Test
    public void testCalculatePressure() {
	System.out.println("calculatePressure");
	double temperature = 300;
	double volume = 2;
	double a = 3;
	double b = 5;
	Cubic instance = new Cubic();
	instance.setU(1);
	instance.setW(-1);
	
	double expResult = -831446.9273;
	double result = instance.calculatePressure(temperature, volume, a, b);

	assertEquals(expResult, result, 1e-3);
	
    }

    /**
     * Test of oneRoot method, of class Cubic.
     */
    @Test
    public void testOneRoot() {
	System.out.println("oneRoot");
	double pressure = 100000;
	double temperature = 300;
	double a = 5;
	double b = 2;
	Cubic instance = new Cubic();
	instance.setU(1);
	instance.setW(-1);
	boolean expResult = false;
	boolean result = instance.oneRoot(pressure, temperature, a, b);
	assertEquals(expResult, result);
	
    }

    /**
     * Test of get_A method, of class Cubic.
     */
    @Test
    public void testGet_A() {
	System.out.println("get_A");
	double temperature = 0.0;
	double pressure = 0.0;
	double a = 0.0;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.get_A(temperature, pressure, a);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of get_B method, of class Cubic.
     */
    @Test
    public void testGet_B() {
	System.out.println("get_B");
	double temperature = 0.0;
	double pressure = 0.0;
	double b = 0.0;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.get_B(temperature, pressure, b);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of calculateCompresibilityFactor method, of class Cubic.
     */
    @Test
    public void testCalculateCompresibilityFactor() {
	System.out.println("calculateCompresibilityFactor");
	double A = 0.0;
	double B = 0.0;
	Phase aPhase = null;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.calculateCompresibilityFactor(A, B, aPhase);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of calculateVolume method, of class Cubic.
     */
    @Test
    public void testCalculateVolume() {
	System.out.println("calculateVolume");
	double temperature = 0.0;
	double pressure = 0.0;
	double z = 0.0;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.calculateVolume(temperature, pressure, z);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of calculateFugacity method, of class Cubic.
     */
    @Test
    public void testCalculateFugacity() {
	System.out.println("calculateFugacity");
	double temperature = 0.0;
	double pressure = 0.0;
	double a = 0.0;
	double b = 0.0;
	double parciala = 0.0;
	double bi = 0.0;
	Phase aPhase = null;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.calculateFugacity(temperature, pressure, a, b, parciala, bi, aPhase);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of calculateL method, of class Cubic.
     */
    @Test
    public void testCalculateL() {
	System.out.println("calculateL");
	double volume = 0.0;
	double b = 0.0;
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.calculateL(volume, b);
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setOmega_a method, of class Cubic.
     */
    @Test
    public void testSetOmega_a() {
	System.out.println("setOmega_a");
	double omega_a = 0.0;
	Cubic instance = new Cubic();
	instance.setOmega_a(omega_a);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getOmega_a method, of class Cubic.
     */
    @Test
    public void testGetOmega_a() {
	System.out.println("getOmega_a");
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.getOmega_a();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setOmega_b method, of class Cubic.
     */
    @Test
    public void testSetOmega_b() {
	System.out.println("setOmega_b");
	double omega_b = 0.0;
	Cubic instance = new Cubic();
	instance.setOmega_b(omega_b);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getOmega_b method, of class Cubic.
     */
    @Test
    public void testGetOmega_b() {
	System.out.println("getOmega_b");
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.getOmega_b();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getU method, of class Cubic.
     */
    @Test
    public void testGetU() {
	System.out.println("getU");
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.getU();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setU method, of class Cubic.
     */
    @Test
    public void testSetU() {
	System.out.println("setU");
	double u = 0.0;
	Cubic instance = new Cubic();
	instance.setU(u);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of getW method, of class Cubic.
     */
    @Test
    public void testGetW() {
	System.out.println("getW");
	Cubic instance = new Cubic();
	double expResult = 0.0;
	double result = instance.getW();
	assertEquals(expResult, result, 0.0);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of setW method, of class Cubic.
     */
    @Test
    public void testSetW() {
	System.out.println("setW");
	double w = 0.0;
	Cubic instance = new Cubic();
	instance.setW(w);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Cubic.
     */
    @Test
    public void testHashCode() {
	System.out.println("hashCode");
	Cubic instance = new Cubic();
	int expResult = 0;
	int result = instance.hashCode();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Cubic.
     */
    @Test
    public void testEquals() {
	System.out.println("equals");
	Object obj = null;
	Cubic instance = new Cubic();
	boolean expResult = false;
	boolean result = instance.equals(obj);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}