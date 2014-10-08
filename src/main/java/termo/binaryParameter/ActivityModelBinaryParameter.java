package termo.binaryParameter;

public class ActivityModelBinaryParameter extends InteractionParameter{
    private InteractionParameter a = new InteractionParameter();
    private InteractionParameter b= new InteractionParameter();
    private InteractionParameter alpha= new InteractionParameter(true);//nrtl
    private InteractionParameter k = new InteractionParameter(true);//wong sandler
    private InteractionParameter A_vanLaar = new InteractionParameter();
    private InteractionParameter twoParameterVanDerWaals = new InteractionParameter();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((A_vanLaar == null) ? 0 : A_vanLaar.hashCode());
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((alpha == null) ? 0 : alpha.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((k == null) ? 0 : k.hashCode());
		result = prime
				* result
				+ ((twoParameterVanDerWaals == null) ? 0
						: twoParameterVanDerWaals.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityModelBinaryParameter other = (ActivityModelBinaryParameter) obj;
		if (A_vanLaar == null) {
			if (other.A_vanLaar != null)
				return false;
		} else if (!A_vanLaar.equals(other.A_vanLaar))
			return false;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (alpha == null) {
			if (other.alpha != null)
				return false;
		} else if (!alpha.equals(other.alpha))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (k == null) {
			if (other.k != null)
				return false;
		} else if (!k.equals(other.k))
			return false;
		if (twoParameterVanDerWaals == null) {
			if (other.twoParameterVanDerWaals != null)
				return false;
		} else if (!twoParameterVanDerWaals
				.equals(other.twoParameterVanDerWaals))
			return false;
		return true;
	}

	public InteractionParameter getA_vanLaar() {
		return A_vanLaar;
	}

	public void setA_vanLaar(InteractionParameter a_vanLaar) {
		A_vanLaar = a_vanLaar;
	}

	public InteractionParameter getTwoParameterVanDerWaals() {
		return twoParameterVanDerWaals;
	}

	public void setTwoParameterVanDerWaals(
			InteractionParameter twoParameterVanDerWaals) {
		this.twoParameterVanDerWaals = twoParameterVanDerWaals;
	}

  
    
    
}
