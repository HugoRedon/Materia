/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.cp;

import termo.Constants;
import termo.component.Component;

/**
 *
 * @author
 * Hugo
 */
public class PolinomialCpEquation implements CpEquation {
    private double A;
    private double B;
    private double C;
    private double D;
    private double E;
    private double F;
    public PolinomialCpEquation(Component component){
	A = component.getA_PolinomialCp();
	B = component.getB_PolinomialCp();
	C = component.getC_PolinomialCp();
	D = component.getD_PolinomialCp();
	E = component.getE_PolinomialCp();
	F = component.getF_PolinomialCp();
	
    }

    @Override
    public double cp(double temperature) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double Enthalpy(double temperature, double referenceTemperature, double enthalpyReference) {
	  return enthalpyReference + IH(temperature) - IH(referenceTemperature);
    }

    @Override
    public double idealGasEntropy(double temperature, double referenceTemeperature, double pressure, double referencePressure, double entropyReference) {
	return entropyReference+IS(temperature) - IS(referenceTemeperature) 
		- Constants.R *Math.log(pressure/referencePressure);
    }

    @Override
    public String getMathEquation() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private double IS(double temperature){
	
	return A * Math.log(temperature)+
		B * temperature+
		(C/2) * Math.pow(temperature, 2) +
		(D/3) * Math.pow(temperature,3) + 
		(E/4) * Math.pow(temperature, 4) + 
		(F/5) * Math.pow(temperature, 5);
    }
    private double IH(double temperature) {
	return A * temperature + 
	    (B/2) * Math.pow(temperature,2) +
		(C/3) * Math.pow(temperature,3)+ 
		    (D/4)*Math.pow(temperature,4)+ 
			(E/5) * Math.pow(temperature, 5)+
			    (F/6) * Math.pow(temperature,6);
		
    }
}
