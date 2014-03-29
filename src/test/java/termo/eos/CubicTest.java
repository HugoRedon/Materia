package termo.eos;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.cp.DIPPR_107_Equation;
import termo.eos.alpha.AlphaFactory;
import termo.phase.Phase;
import termo.matter.PureSubstance;

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
	String expResult = "P = \\frac{RT}{v - b} - \\frac{a} { v^2 + u b v + w b^2}";
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
	double volume = 0.1455;
	double a = 557542.4546;
	double b = 0.065047879;
	Cubic instance = new Cubic();
	instance.setU(0);
	instance.setW(0);

	double expResult = 4667920.29500558;
	double result = instance.calculatePressure(temperature, volume, a, b);

	assertEquals(expResult, result, 1e-5);
	
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
	double temperature = 300;
	double pressure = 100000;
	double a = 5;
	Cubic instance = new Cubic();
	double expResult = 8.03633e-8;
	double result = instance.get_A(temperature, pressure, a);
	assertEquals(expResult, result, 1e-4);
	
    }

    /**
     * Test of get_B method, of class Cubic.
     */
    @Test
    public void testGet_B() {
	System.out.println("get_B");
	double temperature = 300;
	double pressure = 100000;
	double b = 2;
	Cubic instance = new Cubic();
	double expResult = 0.080181479;
	double result = instance.get_B(temperature, pressure, b);
	assertEquals(expResult, result, 1e-4);
	
    }

    /**
     * Test of calculateCompresibilityFactor method, of class Cubic.
     */
    @Test
    public void testCalculateCompresibilityFactor() {
	System.out.println("calculateCompresibilityFactor");
	
	double temperature = 241.255013;
	double pressure = 1013250;
	
	double a = 6.476341e5 ;
	double b = 0.045089;
	

	Cubic instance = new Cubic();
	
	
	instance.setU(1);
	instance.setW(0);

	double A = instance.get_A(temperature, pressure, a);
	double B = instance.get_B(temperature, pressure, b);
	
	double expResultLiquid = 0.035052;
	double expResultVapor = 0.838578;
	double liquidz = instance.calculateCompresibilityFactor(A, B, Phase.LIQUID);
	double vaporz = instance.calculateCompresibilityFactor(A, B, Phase.VAPOR);
	
	assertEquals(expResultLiquid, liquidz, 1e-4);
	assertEquals(expResultVapor, vaporz, 1e-4);

    }

    /**
     * Test of calculateVolume method, of class Cubic.
     */
    @Test
    public void testCalculateVolume() {
	System.out.println("calculateVolume");
	double temperature = 241.255013;
	double pressure = 1013250;
	double z = 0.035052;
	Cubic instance = new Cubic();
	double expResult = 69.393414e-3;
	double result = instance.calculateVolume(temperature, pressure, z);
	assertEquals(expResult, result, 1e-4);
	
    }

    /**
     * Test of calculateFugacity method, of class Cubic.
     */
    @Test
    public void testCalculateFugacity() {
	System.out.println("calculateFugacity");
	double temperature = 241.255013;
	double pressure = 1013250;
	
	double a = 6.476341e5 ;
	double b = 0.045089;
	double parciala = 2*a;
	double bi = 0.045089;
	
	Cubic instance = new Cubic();
	instance.setU(1);
	instance.setW(0);
	
	double expLiquidResult = 0.8609433;
	double expVaporResult = 0.8609429;
	double liquidResult = instance.calculateFugacity(temperature, pressure, a, b, parciala, bi, Phase.LIQUID);
	double vaporResult = instance.calculateFugacity(temperature, pressure, a, b, parciala, bi, Phase.VAPOR);
	
	assertEquals(expVaporResult, vaporResult, 1e-4);
	assertEquals(expLiquidResult, liquidResult, 1e-4);

    }

    /**
     * Test of calculateL method, of class Cubic.
     */
    @Test
    public void testCalculateL() {
	System.out.println("calculateL");
	double volume = 0.069391;
	double b = 0.045089;
	Cubic instance = new Cubic();
	instance.setU(1);
	instance.setW(0);
	
	double expResult = 0.5006393;
	double result = instance.calculateL(volume, b);
	assertEquals(expResult, result, 1e-4);
    }

   
  
}