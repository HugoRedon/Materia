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
public class SoaveAlpha extends CommonAlphaEquation{
    public SoaveAlpha(){
	setName(AlphaNames.Soave); 
        setR1(0.48508);
        setR2(1.55171);
        setR3(-0.15613);
        setR4(0);
        
        setEquation(
                " \\alpha(T)    =    \\left[{   1 +   m \\left({    1-     \\sqrt{  \\frac{T}{T_{c}}   } }\\right)   }\\right] ^2 " + 
                 " \\\\ " +
                 m() 
        );
        
    }
    
    @Override
    public int numberOfParameters() {
        return 0;
    }

    @Override
    public double getParameter(Compound component, int index) {
        return 0;
    }

    @Override
    public void setParameter(double value, Compound component, int index) {
       //no tiene parametros 
    }

	@Override
	public String getParameterName(int index) {
		// TODO Auto-generated method stub
		return null;
	}
    
   
    
}