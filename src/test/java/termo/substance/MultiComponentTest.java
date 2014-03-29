/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.substance;

import termo.matter.Mixture;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.phase.Phase;

/**
 *
 * @author Hugo
 */
public class MultiComponentTest {
    Component ethane;
    Component propane;
    Component nHeptane;
    
    Mixture vdw ;
    
    public MultiComponentTest(){
        
        ethane = new Component("ethane");
	
	ethane.setAcentricFactor(0.09781);
	ethane.setCriticalTemperature(305.43);
	ethane.setCriticalPressure(48.1595*101325);
	ethane.setK_StryjekAndVera(0.02669);
	
	 propane = new Component("propane");
	
	propane.setAcentricFactor(0.15416);
	propane.setCriticalTemperature(369.82);
	propane.setCriticalPressure(41.9396*101325);
	propane.setK_StryjekAndVera(0.03136);
	
        nHeptane = new Component("n-heptane");
        
        nHeptane.setAcentricFactor(0.35);
        nHeptane.setCriticalTemperature(540.14);
        nHeptane.setCriticalPressure(27.05*101325);
        nHeptane.setK_StryjekAndVera(0.02325);
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        InteractionParameter k = new InteractionParameter(true);
        
        k.setValue(ethane, propane, 0.2);
        k.setValue(ethane, nHeptane, 0.1);
        k.setValue(propane, nHeptane, 0.05);
        
        
        
        ArrayList<Component> components = new ArrayList();
        components.add(ethane);
        components.add(propane);
        components.add(nHeptane);
        
        MixingRule mr = new VDWMixingRule();
        vdw = new Mixture(eos, alpha, components, Phase.VAPOR,mr, k);
        vdw.setFraction(ethane, 0.3);
        vdw.setFraction(propane, 0.3);
        vdw.setFraction(nHeptane, 0.4);
        
        
    }
    
    @Test
    public void compresibility(){
        double pressure = 101325;
        double temperature = 300;
        
        double expResult = 0.969775;
        
        vdw.setTemperature(temperature);
        vdw.setPressure(pressure);
        
        double result = vdw.calculateCompresibilityFactor();
        assertEquals(expResult,result, 1e-3);
    }
    
    
}
