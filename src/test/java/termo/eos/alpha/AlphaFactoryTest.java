/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.eos.alpha;

import java.lang.reflect.Field;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Hugo
 */
public class AlphaFactoryTest {
    
    public AlphaFactoryTest() {
    }

    @Test
    public void getAllAvailableAlphasTest() {
        ArrayList<Alpha> alphas = AlphaFactory.getAllAvailableAlphas();
        boolean pass = true;
        ArrayList<String > alphaNames = new ArrayList();
        for(Field f:AlphaNames.class.getDeclaredFields()){
            try {
                f.setAccessible(true);
                String alphaName = (String)f.get(null);
                alphaNames.add(alphaName);
            } catch (Exception ex) {
                System.out.println("Error in getAllAvailableAlphasTest ");
            }
        }
        
        for (String alphaName: alphaNames){
            boolean contains = false;
            for(Alpha alpha : alphas){
                if(alphaName.equals(alpha.getName())){
                    contains = true;
                }
            }
            if(contains ==false){
                pass = false;
                System.out.println(" no contiene el elemento "+ alphaName);
            }
        }
        assertEquals(true,pass);        
    }
    
}
