/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.binaryParameter;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import termo.Constants;
import termo.activityModel.NRTLActivityModel;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alphas;
import termo.eos.mixingRule.MixingRules;
import termo.matter.HeterogeneousMixture;

/**
 *
 * @author Hugo
 */
public class InteractionParameterTest {
    Compound com1;
    Compound com2;
    InteractionParameter b;
    ActivityModelBinaryParameter ac;
    public InteractionParameterTest() {
        com1 = new Compound("com1");
        com1.setCriticalTemperature(500);
        com1.setK_StryjekAndVera(0);
        
        com2 = new Compound("com2");
        com2.setCriticalTemperature(350);
        com2.setK_StryjekAndVera(1.2);
        b = new InteractionParameter();
        ac = new ActivityModelBinaryParameter();
    }

    @Test
    public void testvalue() {
        int expected = 2;
        b.setValue(com1, com2, expected);
        assertEquals(expected, b.getValue(com1, com2),1e-6);
    }
    @Test public void testmutableObject(){
        int expected = 3;
        b.setValue(com1, com2, expected);
        com1.setK_StryjekAndVera(5.9);
        assertEquals(expected, b.getValue(com1, com2),1e-6);
    }
    
    @Test public void testmutableActivityMOdelBinary(){
        int expected = 3;
        ac.getAlpha().setValue(com1, com2, expected);
        com1.setK_StryjekAndVera(5.9);
        assertEquals(expected, ac.getAlpha().getValue(com1, com2),1e-6);
    }
    
    @Test public void testHeterogeneousMixtureInteractionParameters(){
    	Compound referenceCompound = new Compound();
    	referenceCompound.setName("reference");
    	Compound nonReferenceCompound = new Compound();
    	nonReferenceCompound.setName("nonReference");
    	//referenceCompound = availableCompounds.getCompoundByExactName("isopropanol");
		
		referenceCompound.setK_StryjekAndVera(0.23264);
		//nonReferenceCompound = availableCompounds.getCompoundByExactName("water");
		nonReferenceCompound.setK_StryjekAndVera(-0.06635);
		
		Set<Compound> components = new HashSet<>();
		
		components.add(referenceCompound);
		components.add(nonReferenceCompound);
		
		Cubic equationOfState = EquationsOfState .pengRobinson();
		NRTLActivityModel nrtl = new NRTLActivityModel();
		
		ActivityModelBinaryParameter k = new ActivityModelBinaryParameter();
		
		k.getAlpha().setSymmetric(true);
		k.getAlpha().setValue(referenceCompound, nonReferenceCompound, 0.2893);
		
		k.getA().setValue(referenceCompound, nonReferenceCompound, 0.7882*Constants.R*353);
		k.getB().setValue(referenceCompound, nonReferenceCompound, 3.9479*Constants.R*353);
		HeterogeneousMixture hm = new HeterogeneousMixture(equationOfState,
				Alphas.getStryjekAndVeraExpression(),
				MixingRules.huronVidal(nrtl, equationOfState), 
				components, k);
		hm.setTemperature(353);
		
		ActivityModelBinaryParameter hmk = (ActivityModelBinaryParameter) hm.getInteractionParameters();
		ActivityModelBinaryParameter lk = (ActivityModelBinaryParameter)hm.getLiquid().getBinaryParameters();
		ActivityModelBinaryParameter vk = (ActivityModelBinaryParameter)hm.getVapor().getBinaryParameters();
		
		assertEquals(true,hmk.equals(vk));
		assertEquals(true,hmk.equals(lk));
		assertEquals(0.7882*Constants.R*353,lk.getA().getValue(referenceCompound, nonReferenceCompound),1e-6);
		
		double result = lk.getA().getValue(referenceCompound, nonReferenceCompound);
		System.out.println(result);
		
    }
    
    
}
