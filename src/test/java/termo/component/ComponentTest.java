/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import static org.junit.Assert.fail;
import org.junit.Test;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;

/**
 *
 * @author Hugo
 */
public class ComponentTest {
    
    public ComponentTest() {
    }

    @Test
    public void testSomeMethod() {
        Component component = new Component("test");
        Alpha alpha = AlphaFactory.getAdachiAndLu();
        System.out.println(alpha.getName());
        ArrayList<Field> fields = component.getAlphaParametersFor(alpha);
        for(Field field :fields){
            System.out.println(field.getName());
        }
        fail();
    }
    
}
