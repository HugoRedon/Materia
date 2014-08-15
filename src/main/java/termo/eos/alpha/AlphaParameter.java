package termo.eos.alpha;

public class AlphaParameter {
	public AlphaParameter() {
	}
	
	public AlphaParameter(double value, String name) {
		this.value = value;
		this.name = name;
	}

	private double value;
	private String name;
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
