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
public class PengAndRobinsonAlpha extends CommonAlphaEquation{
    public PengAndRobinsonAlpha(){
	setName(AlphaNames.PengAndRobinson);
        
        setR1(0.37464);
        setR2(1.54226);
        setR3(-0.2699);
        setR4(0);
        
        StringBuilder b = new StringBuilder();
       
        b.append("\\alpha(T) = \\left[  1+ m \\left(1-\\sqrt{T_r})\\right)     \\right]^2");
        b.append("\\\\");
        b.append(m());
       
        
        setEquation(b.toString());
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
    

