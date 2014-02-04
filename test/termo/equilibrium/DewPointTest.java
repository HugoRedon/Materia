/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.equilibrium;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class DewPointTest {
    PureSubstance substance ; 
    public DewPointTest() {
	Component ethane = new Component();
	
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(4.8797134e6);
	ethane.setPrsvKappa(-0.02669);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	substance = new PureSubstance();
	substance.setCubicEquationOfState(eos);
	substance.setAlpha(alpha);
	substance.setComponent(ethane);
    }

    @Test
    public void testGetTemperature() {
	System.out.println("getTemperature");
	
	double pressure =101325;
	double result = substance.dewTemperature(pressure);	
	double tolerance = 1;
	double expResult = 184.607519;
	
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testGetTemperatureEstimate() {
	System.out.println("getTemperatureEstimate");
	double pressure =101325;
	double result = substance.dewTemperatureEstimate(pressure);
	double tolerance = 1e-3;
	double expResult = 184.338452;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testGetPressureEstimate() {
	System.out.println("getPressureEstimate");
	double temperature =298;
	double result = substance.dewPressureEstimate(temperature);
	double tolerance = 1e3;
	double expResult = 4.21241675e6;
	assertEquals(expResult, result,tolerance);
    }

    @Test
    public void testGetPressure() {
	System.out.println("getPressure");
	
	double temperature = 298;
	double result = substance.dewPressure(temperature);
	double tolerance =  1e5;
	double expResult =4198171.468575;// 4.198171e6;
	assertEquals(expResult, result,tolerance);
    }
}