
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

}
