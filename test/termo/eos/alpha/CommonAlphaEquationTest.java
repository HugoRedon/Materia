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
	Component ethane;
	CommonAlphaEquation alpha;
    public CommonAlphaEquationTest() {
	ethane = new Component();
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);
	
	
	double r1 = 0.378893;
	double r2=1.4897153;
	double r3 = -0.17131848;
	double r4 = 0.0196554;
	
	alpha =  new CommonAlphaEquation();
	alpha.setR1(r1);
	alpha.setR2(r2);
	alpha.setR3(r3);
	alpha.setR4(r4);
	
    }

    /**
     * Test of alpha method, of class CommonAlphaEquation.
     */
    @Test
    public void testAlpha() {
	System.out.println("alpha");
	
	
	
	double temperature = ethane.getCriticalTemperature()*0.5;
	
	
	alpha.setX(1);
		
	
	double expResult = 1.33598;
	double result = alpha.alpha(temperature, ethane);
	
	double expAboveResult = 0.81754;
	double tempabove  = ethane.getCriticalTemperature()*1.4;
	alpha.setX(0);
	double aboveResult = alpha.alpha(tempabove, ethane);
	assertEquals(expResult, result, 1e-3);
	assertEquals(expAboveResult, aboveResult, 1e-3);
	
    }

    /**
     * Test of m method, of class CommonAlphaEquation.
     */
    @Test
    public void testM() {
	System.out.println("m");

	double expResult = 0.52298;
	double result = alpha.m(ethane);
	assertEquals(expResult, result, 1e-3);
	
    }

    /**
     * Test of TempOverAlphaTimesDerivativeAlphaRespectTemperature method, of class CommonAlphaEquation.
     */
    @Test
    public void testTempOverAlphaTimesDerivativeAlphaRespectTemperature() {
	System.out.println("TempOverAlphaTimesDerivativeAlphaRespectTemperature");
	double temperature = ethane.getCriticalTemperature()*0.5;
	
	alpha.setX(1);
	double expResult = -0.33611;
	double result = alpha.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	assertEquals(expResult, result, 1e-3);

    }

 

    /**
     * Test of equals method, of class CommonAlphaEquation.
     */
    @Test
    public void testEquals() {
	System.out.println("equals");
	Object obj = new CommonAlphaEquation();
	
	CommonAlphaEquation alpha1 = new CommonAlphaEquation();
	double r1 = 0.378893;
	double r2=1.4897153;
	double r3 = -0.17131848;
	double r4 = 0.0196554;
	
	
	alpha1.setR1(r1);
	alpha1.setR2(r2);
	alpha1.setR3(r3);
	alpha1.setR4(r4);
	alpha1.setX(0);
	
	alpha.setX(0);
	
	
	boolean expResult = false;
	boolean result = alpha.equals(obj);
	assertEquals(expResult, result);
	
	assertEquals(alpha.equals(alpha1),true);

    }
}