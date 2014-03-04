

package termo.substance;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;


public class PolarMathiasTest {
  
    @Test
    public void test(){
        
        Component ethanol = new Component();
        ethanol.setName("ethanol");
        ethanol.setAcentricFactor(0.64439);
        ethanol.setCriticalTemperature(513.92);
        ethanol.setCriticalPressure(60.676*101325);
        ethanol.setPrsvKappa(0.9);
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, ethanol);
        
        
        
        double[][] experimental = {//temperature[C], pressure[kPa]
            {93.48,179.321},
            {82.36,118.719},
            {74.98,88.763},
            {54.66,36.76},
            {36.61,14.981},
            {19.62,5.726}
        };
        experimental = converToKandPa(experimental);
      
        
        double expResult = 0.102104;
        //double expResult = 0.009988;
        double result = substance.solveVapoPressureRegression(experimental,0);
        assertEquals(expResult, result , 1e-3);
        
        
    }  
    int i =0;
    public  double[][] converToKandPa(double[][] expe){
        double[][] result = new double[expe.length][expe[0].length];
        
        for(double[] pair: expe){
            double[] newPair ={pair[0]+273.15, pair[1]*1000};
            
            result[i++] = newPair;
            
        }
        
        return result;
    }
    
    
}
