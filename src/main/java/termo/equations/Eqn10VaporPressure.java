/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.equations;

import java.io.Serializable;
import javax.persistence.Entity;
import termo.component.Component;

/**
 *
 * @author Hugo
 */
@Entity
public class Eqn10VaporPressure extends EqnVaporPressure implements Serializable{
  
    
   
    @Override
    public double vaporPressure(double temperature){
        return Math.exp(A - B/(C + temperature));
    }


}
