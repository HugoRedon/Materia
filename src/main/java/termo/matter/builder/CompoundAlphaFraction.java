package termo.matter.builder;

import termo.component.Compound;
import termo.eos.alpha.Alpha;


public class CompoundAlphaFraction{
	Compound compound;
	Alpha alpha;
	Double fraction;
	public CompoundAlphaFraction(Compound compound, Alpha alpha, Double fraction) {
		super();
		this.compound = compound;
		this.alpha = alpha;
		this.fraction = fraction;
	}
	public Compound getCompound() {
		return compound;
	}
	public void setCompound(Compound compound) {
		this.compound = compound;
	}
	public Alpha getAlpha() {
		return alpha;
	}
	public void setAlpha(Alpha alpha) {
		this.alpha = alpha;
	}
	public Double getFraction() {
		return fraction;
	}
	public void setFraction(Double fraction) {
		this.fraction = fraction;
	}
}
