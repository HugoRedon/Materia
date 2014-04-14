/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.binaryParameter;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class InteractionParameterTest {
    Component com1;
    Component com2;
    InteractionParameter b;
    public InteractionParameterTest() {
        com1 = new Component("com1");
        com1.setCriticalTemperature(500);
        com1.setK_StryjekAndVera(0);
        
        com2 = new Component("com2");
        com2.setCriticalTemperature(350);
        com2.setK_StryjekAndVera(1.2);
        b = new InteractionParameter();
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

    
}
