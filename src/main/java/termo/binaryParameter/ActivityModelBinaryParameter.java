package termo.binaryParameter;

public class ActivityModelBinaryParameter extends InteractionParameter{
    private InteractionParameter a = new InteractionParameter();
    private InteractionParameter b= new InteractionParameter();
    private InteractionParameter alpha= new InteractionParameter();
    private InteractionParameter k = new InteractionParameter();
    
    public InteractionParameter getA() {
        return a;
    }

    public void setA(InteractionParameter a) {
        this.a = a;
    }

    public InteractionParameter getB() {
        return b;
    }

    public void setB(InteractionParameter b) {
        this.b = b;
    }

    public InteractionParameter getAlpha() {
        return alpha;
    }

    public void setAlpha(InteractionParameter alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the k
     */
    public InteractionParameter getK() {
	return k;
    }

    /**
     * @param k the k to set
     */
    public void setK(InteractionParameter k) {
	this.k = k;
    }

    
    
}
