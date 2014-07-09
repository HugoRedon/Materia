/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.eos.alpha.commonaAlphaEquationsImplementation;

import termo.component.Compound;
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
    public double getParameter(Compound component,int index) {
	if(index == 0 ){ return component.getA_Mathias();}
        else{ return 0;}
    }
    
    @Override
    public void setParameter(double paramValue, Compound component,int index) {
        if(index ==0){
            component.setA_Mathias(paramValue);
        }
    }
    
    
}