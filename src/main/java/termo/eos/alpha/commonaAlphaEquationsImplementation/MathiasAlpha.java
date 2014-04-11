/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.eos.alpha.commonaAlphaEquationsImplementation;

import termo.component.Component;
import termo.eos.alpha.AlphaNames;
import termo.eos.alpha.CommonAlphaEquation;

/**
 *
 * @author Hugo
 */
public class MathiasAlpha extends CommonAlphaEquation{
    public MathiasAlpha(){
	setName(AlphaNames.Mathias);
	setR1(0.48508);
        setR2(1.55191);
        setR3(-0.15613);
        setR4(0);
	setX(-1);
        
        
        StringBuilder b = new StringBuilder();
        
        b.append("\\alpha(T) = \\left[ ");
        b.append("1+ m \\left(1-\\sqrt{T_r}\\right) ");
        b.append("-A (1-T_r)(0.7-T_r)");
        b.append("\\right]^2  ");
        b.append("\\\\");
        b.append(m());
        
        setEquation(b.toString());
    }
    
    @Override
    public int numberOfParameters() {
        return 1;
    }
    
    @Override
    public double getAlphaParameterA(Component component) {
	return component.getA_Mathias();
    }
    
    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setA_Mathias(paramValue);
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