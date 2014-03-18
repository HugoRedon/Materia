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
	ethane.setK_StryjekAndVera(0.02669);
	ethane.setA_Mathias(-2);//solo para pruebas
	
	
	double r1 = 0.378893;
	double r2=1.4897153;
	double r3 = -0.17131848;
	double r4 = 0.0196554;
	
	alpha =  new TestAlpha();
	alpha.setR1(r1);
	alpha.setR2(r2);
	alpha.setR3(r3);
	alpha.setR4(r4);
	
    }
    
    class TestAlpha extends CommonAlphaEquation{

	@Override
	public double get_q(Component component) {
	    return 0;
	}

        }
      /**
     * Test of m method, of class CommonAlphaEquation.
     */
    @Test
    public void testM() {
	System.out.println("m");

	double expResult = 0.52298;
	double result = alpha.m(ethane.getAcentricFactor());
	assertEquals(expResult, result, 1e-3);
	
    }


    /**
     * Test of alpha method, of class CommonAlphaEquation.
     */
    @Test
    public void testAlpha() {
	System.out.println("alpha");
	double temperature = ethane.getCriticalTemperature()*0.5;
	double expResult = 1.32982;
	double result = alpha.alpha(temperature, ethane);

	assertEquals(expResult, result, 1e-3);
    }

  
    /**
     * Test of TempOverAlphaTimesDerivativeAlphaRespectTemperature method, of class CommonAlphaEquation.
     */
    @Test
    public void testTempOverAlphaTimesDerivativeAlphaRespectTemperature() {
	System.out.println("TempOverAlphaTimesDerivativeAlphaRespectTemperature");
	double temperature = ethane.getCriticalTemperature()*0.5;
	
	double expResult = -0.32068;
	double result = alpha.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	assertEquals(expResult, result, 1e-3);

    }

 
    Alpha soave = new SoaveAlpha();
    @Test
    public void testSoaveAlpha(){
	System.out.println("alpha");
	double temperature = ethane.getCriticalTemperature()*0.5;
	double expResult = 1.40682;
	double result = soave.alpha(temperature, ethane);

	assertEquals(expResult, result, 1e-3);
    }
    
    @Test
    public void testSoaveDeriv(){
	System.out.println("alphaDeriv");
	double temperature = ethane.getCriticalTemperature() * 0.5;
	double result = soave.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	double expResutl = -0.37878;
	assertEquals(expResutl, result, 1e-3);
    }
    
    
    
    Alpha PAndR = new PengAndRobinsonAlpha();
    @Test
    public void testPengAndRobinsonAlpha(){
	System.out.println("alpha");
	double temperature = ethane.getCriticalTemperature()*0.5;
	double expResult = 1.32977;
	double result = PAndR.alpha(temperature, ethane);

	assertEquals(expResult, result, 1e-3);
    }
    
    @Test
    public void testPengAndRobinsonDeriv(){
	System.out.println("alphaDeriv");
	double temperature = ethane.getCriticalTemperature() * 0.5;
	double result = PAndR.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	double expResutl = -0.32064;
	assertEquals(expResutl, result, 1e-3);
    }
    
    
    
    
    Alpha mathias = new MathiasAlpha();
    @Test 
    public void testMathiasAlpha(){
	System.out.println("alpha");
	double temperature = ethane.getCriticalTemperature()*0.3;
	double expResult = 3.41276371;
	double result = mathias.alpha(temperature, ethane);

	assertEquals(expResult, result, 1e-3);
    }
    
    @Test
    public void testMathiasAlphaDeriv(){
	System.out.println("alphaDeriv");
	double temperature = ethane.getCriticalTemperature() * 0.3;
	double result = mathias.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	double expResutl = -0.90291242;
	assertEquals(expResutl, result, 1e-3);
    }
    
    

      Alpha stryjekAndVera = new StryjekAndVera();
    @Test 
    public void testStryjekAndVeraAlpha(){
	System.out.println("alpha");
	double temperature = ethane.getCriticalTemperature()*0.5;
	double expResult = 1.33598166;
	double result = stryjekAndVera.alpha(temperature, ethane);

	assertEquals(expResult, result, 1e-3);
    }
    
    @Test
    public void testStryjekAndVeraAlphaDeriv(){
	System.out.println("alphaDeriv");
	double temperature = ethane.getCriticalTemperature() * 0.5;
	double result = stryjekAndVera.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, ethane);
	double expResutl = -0.33610576;
	assertEquals(expResutl, result, 1e-3);
    }
    
}