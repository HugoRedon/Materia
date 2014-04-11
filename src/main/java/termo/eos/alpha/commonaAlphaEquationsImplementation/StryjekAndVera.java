

package termo.eos.alpha.commonaAlphaEquationsImplementation;

import termo.component.Component;
import termo.eos.alpha.AlphaNames;
import termo.eos.alpha.CommonAlphaEquation;

/**
 *
 * @author Hugo
 */
public class StryjekAndVera extends CommonAlphaEquation {
    public StryjekAndVera(){
	setName(AlphaNames.StryjekAndVera);
	setR1(0.378893);
        setR2(1.4897153);
        setR3(-0.17131848);
        setR4(0.0196554);
    }

    @Override
    public String getEquation() {
        StringBuilder b = new StringBuilder();
        b.append("\\alpha(T) = \\left[ ");
        b.append("1+ m \\left(1-\\sqrt{T_r}\\right) ");
        if(x != 0 ){
            b.append("-k_1 (1-T_r)(0.7-T_r)");
        }
        
        b.append("\\right]^2  ");
        b.append("\\\\");
        b.append(m());
        return b.toString();                
    }
    
    
    @Override
    public double getAlphaParameterA(Component component) {
	return component.getK_StryjekAndVera();
    }

    @Override
    public int numberOfParameters() {
        return 1;
    }

    @Override
    public void setAlphaParameterA(double paramValue, Component component) {
        component.setK_StryjekAndVera(paramValue);
    }

//    @Override
//    public double getAlphaParameterA(Component component) {
//        return component.getK_StryjekAndVera();
//    }

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
