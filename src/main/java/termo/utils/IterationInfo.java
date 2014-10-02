package termo.utils;

public class IterationInfo {
	
	Integer iteration;
	Double temperature;
	Double pressure;
	
	Double temperature_;
	Double pressure_;
	
	Double error;
	Double error_;
	
	Double newTemperature;
	Double newPressure;
	
	Double delta ;
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Double getPressure() {
		return pressure;
	}
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	public Double getTemperature_() {
		return temperature_;
	}
	public void setTemperature_(Double temperature_) {
		this.temperature_ = temperature_;
	}
	public Double getPressure_() {
		return pressure_;
	}
	public void setPressure_(Double pressure_) {
		this.pressure_ = pressure_;
	}
	public Double getError() {
		return error;
	}
	public void setError(Double error) {
		this.error = error;
	}
	public Double getError_() {
		return error_;
	}
	public void setError_(Double error_) {
		this.error_ = error_;
	}
	public Double getNewTemperature() {
		return newTemperature;
	}
	public void setNewTemperature(Double newTemperature) {
		this.newTemperature = newTemperature;
	}
	public Double getNewPressure() {
		return newPressure;
	}
	public void setNewPressure(Double newPressure) {
		this.newPressure = newPressure;
	}
	public Double getDelta() {
		return delta;
	}
	public void setDelta(Double delta) {
		this.delta = delta;
	}
	public Integer getIteration() {
		return iteration;
	}
	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}	
	
}
