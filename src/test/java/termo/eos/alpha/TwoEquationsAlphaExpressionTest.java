/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.alpha;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.eos.alpha.commonaAlphaEquationsImplementation.MathiasAlpha;
import termo.eos.alpha.commonaAlphaEquationsImplementation.PengAndRobinsonAlpha;

/**
 *
 * @author
 * Hugo
 */
public class TwoEquationsAlphaExpressionTest {
	Component component;
	
    public TwoEquationsAlphaExpressionTest() {
	component = new Component("component");
	component.setAcentricFactor(0.09781);
	component.setCriticalTemperature(305.43);
	component.setCriticalPressure(48.1595*101325);
	component.setK_StryjekAndVera(0.02669);
	component.setA_Mathias(-2);//solo para pruebas
    }

    
    Alpha mathias = AlphaFactory.getMathiasExpression();
    @Test
    public void testMathiasAlphaBelowTc() {
	System.out.println("alpha");
	double temperature = component.getCriticalTemperature()*0.3;	
	double expResult = 3.41276371;
	double result = mathias.alpha(temperature, component);
	assertEquals(expResult, result, 1e-3);
	
    }
  
    
    @Test
    public void testMathiasAlphaAboveTc(){
	System.out.println("alpha");
	double temperature = component.getCriticalTemperature()*1.3;	
	double expResult = 1.17704;
	double result = mathias.alpha(temperature, component);
	assertEquals(expResult, result, 1e-3);
    }

    
    @Test
    public void testMathiasAlphaDerivBelowTc(){
	System.out.println("alphaDeriv");
	double temperature = component.getCriticalTemperature()*0.3;	
	double expResult = -0.90291242;
	double result = mathias.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
	assertEquals(expResult, result, 1e-3);
    }
    
     
    @Test
    public void testMathiasAlphaDerivAboveTc(){
	System.out.println("alphaDeriv");
	double temperature = component.getCriticalTemperature()*1.3;	
	double expResult =0.68161;
	double result = mathias.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
	assertEquals(expResult, result, 1e-3);
    }
    
    
    
    
    
    
        
    Alpha stryjekVera = AlphaFactory.getStryjekAndVeraExpression();
    @Test
    public void testStryjekAndVeraAlphaBelowTc() {
	System.out.println("alpha");
	double temperature = component.getCriticalTemperature()*0.5;	
	double expResult = 1.33598166;
	double result = stryjekVera.alpha(temperature, component);
	assertEquals(expResult, result, 1e-3);
	
    }
  
    
    @Test
    public void testStryjekAndVeraAlphaAboveTc(){
	System.out.println("alpha");
	double temperature = component.getCriticalTemperature()*1.3;	
	double expResult = 0.85875593;
	double result = stryjekVera.alpha(temperature, component);
	assertEquals(expResult, result, 1e-3);
	
    }

    
    @Test
    public void testStryjekAndVeraAlphaDerivBelowTc(){
	System.out.println("alphaDeriv");
	double temperature = component.getCriticalTemperature()*0.5;	
	double expResult = -0.33610576;
	double result = stryjekVera.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
	assertEquals(expResult, result, 1e-3);
	
    }
    
     
    @Test
    public void testStryjekAndVeraAlphaDerivAboveTc(){
	System.out.println("alphaDeriv");
	double temperature = component.getCriticalTemperature()*1.3;	
	double expResult =-0.6434623;
	double result = stryjekVera.TempOverAlphaTimesDerivativeAlphaRespectTemperature(temperature, component);
	assertEquals(expResult, result, 1e-3);
	
    }
    
    
    
    
    
    
 
    @Test
    public void testGetProperAlpha() {
	System.out.println("getProperAlpha");
	double temperature = 50;
	Component component1 = new Component("component1");
	component1.setCriticalTemperature(100);
	
	Alpha alpha1 = new PengAndRobinsonAlpha();
	Alpha alpha2 = new MathiasAlpha();
	TwoEquationsAlphaExpression instance = new TwoEquationsAlphaExpression();
	instance.setAlphaBelowTc(alpha1);
	instance.setAlphaAboveTc(alpha2);
	
	Alpha expResult = alpha1;
	Alpha result = instance.getProperAlpha(temperature, component1);
	assertEquals(expResult, result);
	
    }


}