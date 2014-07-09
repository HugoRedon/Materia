package termo.equations;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import termo.component.Compound;

/**
 *
 * @author Hugo
 */
@Entity
public class Eqn101VaporPressure extends EqnVaporPressure implements Serializable {    
    
    
    private double D;
    private double E;
    
    @Override
    public double vaporPressure(double temperature){
        return Math.exp(getA()+ getB()/temperature + getC() * Math.log(temperature) + getD()*Math.pow(temperature, getE()));
                
    }

    
    /**
     * @return the D
     */
    public double getD() {
        return D;
    }

    /**
     * @param D the D to set
     */
    public void setD(double D) {
        this.D = D;
    }

    /**
     * @return the E
     */
    public double getE() {
        return E;
    }

    /**
     * @param E the E to set
     */
    public void setE(double E) {
        this.E = E;
    }

   
}
