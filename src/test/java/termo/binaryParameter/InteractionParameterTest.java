/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.binaryParameter;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.Compound;

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
    
    
}
