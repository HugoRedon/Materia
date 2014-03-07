
package termo.binaryParameter;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class NRTLBinaryParameter extends BinaryInteractionParameter{

 private BinaryInteractionParameter alpha;


    /**
     * @return the alpha
     */
    public BinaryInteractionParameter getAlpha() {
        return alpha;
    }
    
    public double get_gji(Component cj, Component ci){
        return getValue(cj, ci);   
    }
    
    

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(BinaryInteractionParameter alpha) {
        this.alpha = alpha;
    }
    
}
