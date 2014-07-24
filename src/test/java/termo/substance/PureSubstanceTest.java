package termo.substance;

import termo.matter.Substance;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.component.Compound;
import termo.cp.PolinomialCpEquation;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class PureSubstanceTest {
     Substance substance ; 
    public PureSubstanceTest() {
	Compound ethane = new Compound("ethane");
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);//en eqfases2 tiene un signo negativo ...
	
//	ethane.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-83767.2);
//	
//	
//	ethane.setA_dippr107Cp(5.40056);
//	ethane.setB_dippr107Cp(0.177817);
//	ethane.setC_dippr107Cp(-6.92626e-5);
//	ethane.setD_dippr107Cp(8.69858e-9);
	
	ethane.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-8.37672E+07);
	ethane.setGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa(-32896.6*1000);
	//ethane.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(-32896.6);
	
	ethane.setA_PolinomialCp(5.40056*1000);
	ethane.setB_PolinomialCp(0.177817*1000);
	ethane.setC_PolinomialCp(-69262e-5*1000);
	ethane.setD_PolinomialCp(8.69858e-9*1000);
	
	ethane.setA_dippr107Cp(4.4256E+04);
	ethane.setB_dippr107Cp(8.4737E+04);
	ethane.setC_dippr107Cp(8.7224E+02);
	ethane.setD_dippr107Cp(6.7130E+04);
	ethane.setE_dippr107Cp(2.4304E+03);
	
	ethane.setCp(new PolinomialCpEquation(ethane));//ugly stuff
	
	
	Cubic eos = EquationsOfState.pengRobinson();
	Alpha alpha = Alphas.getStryjekAndVeraExpression();
	
	
	
	substance = new Substance();
	substance.setPhase(Phase.LIQUID);
	substance.setCubicEquationOfState(eos);
	substance.setAlpha(alpha);
	substance.setComponent(ethane);
    }



    
    @Test
    public void testCalculateMolarVolume(){
	System.out.println("calculateMolarVolume: in PureSubstance it is not a formal test");
	double temperature = 200;
	double pressure = 1*101325;
	
	double expResult = 52.815879/1000;
	
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	double result = substance.calculateMolarVolume();
	assertEquals(expResult, result, 1e-3);
	
    }
    
    @Test
    public void testCalculateCompresibilityFactor(){
	System.out.println("calculateCompresibilityFactor :in PureSubstance it is not a formal test");
	double temperature = 200;
	double pressure = 1*101325;
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	double expResult = 0.003218;
	double result = substance.calculateCompresibilityFactor();
	assertEquals(expResult, result, 1e-3);
    }
    
    @Test
    public void testCalculateIdealGasEnthalpy() {
	System.out.println("calculateIdealGasEnthalpy");
	double temperature = 298.15;
	
	
	
	double expResult = -8.37672E+07;
	substance.setTemperature(temperature);
	double result = substance.calculateIdealGasEnthalpy();
	assertEquals(expResult, result, 1e-3);

    }
    
        @Test
    public void testCalculateEnthalpy() {
	System.out.println("calculateIdealGasEnthalpy");
	double temperature =298.15;
	double pressure = 101325;
	
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	substance.setPhase(Phase.VAPOR);
	
	double expResult = -83825.95855;
	double result = substance.calculateEnthalpy();
	assertEquals(expResult, result/1000, 1e-3);

    }
    
	
    @Test
    public void testCalculateIdealGasEntropy(){
	System.out.println("calculateIdealGasentropy");
	double temperature = 298.15;
	double pressure = 101325;
	
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	substance.setPhase(Phase.VAPOR);
	
	
	
	double expResult = -170.62;
	
	double result = substance.calculateIdealGasEntropy();
	
	assertEquals(expResult, result/1000,1e-3);
    }
	
    @Test
    public void testCalculateEntropy() {
	System.out.println("calculateEntropy");
	double temperature = 298.15;
	double pressure = 101325;
	
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	substance.setPhase(Phase.VAPOR);
	
	double expResult = -170.747664;
	//double volume = substance.calculateMolarVolume(temperature, pressure);///
	
	double result = substance.calculateEntropy();
	assertEquals(expResult, result/1000, 1e-3);

    }
    @Test
    public void testCalculateGibbs() {
	System.out.println("calculateIdealGasEntropy");
	double temperature = 298.15;
	double pressure = 101325;
	
	substance.setTemperature(temperature);
	substance.setPressure(pressure);
	substance.setPhase(Phase.VAPOR);
	
	double expResult = -32917.542595;
	
	double result = substance.calculateGibbs();
	assertEquals(expResult, result/1000, 1e-3);

    }
    
    

   


   
   
    
    @Test
    public void testGetAcentricFactorBasedVaporPressure() {
	System.out.println("getAcentricFactorBasedVaporPressure");
	double temperature = 298;
	substance.setTemperature(temperature);
	double expResult = 41.57334499;
	double result = substance.calculatetAcentricFactorBasedVaporPressure();
	assertEquals(expResult, result/101325, 1e-3);
	
    }
}