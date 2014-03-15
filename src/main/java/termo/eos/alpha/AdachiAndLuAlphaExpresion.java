/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo
 */
public class AdachiAndLuAlphaExpresion extends Alpha{

    @Override
    public double alpha(double temperature, Component component) {
        double A = component.getA_AdachiAndLu();
        double B = component.getB_AdachiAndLu();
        double tr = temperature / component.getCriticalTemperature();
        return A* Math.pow(10,B*(1-tr));
    }

    @Override
    public double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature, Component component) {
        double A = component.getA_AdachiAndLu();
        double B = component.getB_AdachiAndLu();
        double tr = temperature /component.getCriticalTemperature();
        
        return - (B*tr);
    }
    
}
