package tutorial;

import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.substance.HeterogeneousPureSubstance;

/**
 *
 * @author Hugo
 */
public class TutorialTest {
    HeterogeneousPureSubstance substance;
     public TutorialTest(){
//        Component water = new Component();
//        water.setMolecularWeight(18.01528);
//        water.setCriticalTemperature(647.096);
//        water.setCriticalPressure(2.20640E+07);
        
        
	Component ethane = new Component();
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setPrsvKappa(0.02669);//en eqfases2 tiene un signo negativo ...
	
	Cubic eos = EquationOfStateFactory.redlichKwongSoaveBase();
	Alpha alpha = AlphaFactory.getMathiasExpression();
	
	substance = new HeterogeneousPureSubstance(eos, alpha, ethane);
    }
    
    
    @Test 
    public void testRKSHeterogeneousBubbleTemp(){
	System.out.println("bubbleTemp");
	double pressure = 101325;
	double expResult = 184.933865;
	double result = substance.bubbleTemperature();
	
	
	assertEquals(expResult,result,1e-3);
    }
    
            
}
