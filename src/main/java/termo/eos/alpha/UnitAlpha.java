
package termo.eos.alpha;

import termo.component.Compound;

/**
 *
 * @author Hugo Redon Rivera
 */
public class UnitAlpha extends Alpha {
    public UnitAlpha(){
        setName(AlphaNames.vdwIndependent);
        setEquation("\\alpha(T) = 1");
    }

    @Override
    public double alpha(double temperature, Compound component) {
        return 1;
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Compound component) {
       return 0;
    }
    @Override
    public int numberOfParameters() {
        return 0;
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
        
    }

    @Override
    public double getParameter(Compound component, int index) {
        return 0;
    }

	@Override
	public String getParameterName(int index) {
		// TODO Auto-generated method stub
		return null;
	}
    
   
}
