package termo.binaryParameter;

public class ActivityModelBinaryParameter extends InteractionParameter{
    private BinaryInteractionParameter a = new BinaryInteractionParameter();
    private BinaryInteractionParameter b= new BinaryInteractionParameter();
    private BinaryInteractionParameter alpha= new BinaryInteractionParameter();

    public BinaryInteractionParameter getA() {
        return a;
    }

    public void setA(BinaryInteractionParameter a) {
        this.a = a;
    }

    public BinaryInteractionParameter getB() {
        return b;
    }

    public void setB(BinaryInteractionParameter b) {
        this.b = b;
    }

    public BinaryInteractionParameter getAlpha() {
        return alpha;
    }

    public void setAlpha(BinaryInteractionParameter alpha) {
        this.alpha = alpha;
    }

    
    
}
