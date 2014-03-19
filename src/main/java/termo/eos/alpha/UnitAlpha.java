
package termo.eos.alpha;

import termo.component.Component;

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
    public double alpha(double temperature, Component component) {
        return 1;
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
       return 0;
    }
    @Override
    public int numberOfParameters() {
        return 0;
    }
    
    @Override
    public double getAlphaParameterA(Component component) {
	return 0;
    } 
    
    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        
    }
    
    @Override
    public void setAlphaParameterB(double paramValue, Component component) {
        
    }

    @Override
    public double getAlphaParameterB(Component component) {
        return 0;
    }

    @Override
    public void setAlphaParameterC(double paramValue, Component component) {
        
    }

    @Override
    public double getAlphaParameterC(Component component) {
        return 0;
    }
}
