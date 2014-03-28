

package termo.substance;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.optimization.AlphaOptimization;


public class PolarMathiasTest {
    ArrayList<ExperimentalData> experimental;
    public PolarMathiasTest(){
          double[][] experimentalarray = {//temperature[C], pressure[kPa]
            {93.48,179.321},
            {82.36,118.719},
            {74.98,88.763},
            {54.66,36.76},
            {36.61,14.981},
            {19.62,5.726}
        };
         experimentalarray = converToKandPa(experimentalarray);
         
         experimental = convertToArrayList(experimentalarray);
         
    }
  
    private ArrayList<ExperimentalData> convertToArrayList(double[][] experimentalarray) {
        ArrayList<ExperimentalData> result = new ArrayList();
        for(double[]pair: experimentalarray){
            result.add(new ExperimentalData(pair[0],pair[1]));
        }
        return result;
    }
    
    @Test
    public void testPRSV(){
        
        Component ethanol = new Component("ethanol");
        //ethanol.setName("ethanol");
        ethanol.setAcentricFactor(0.64439);
        ethanol.setCriticalTemperature(513.92);
        ethanol.setCriticalPressure(60.676*101325);
        ethanol.setK_StryjekAndVera(0.9);
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, ethanol);
        
       
         
        
      
        AlphaOptimization op = new AlphaOptimization(substance,experimental);
        
       
        
        double expResult = -0.03858;
        //double expResult = 0.009988;
        double[] result = op.solveVapoPressureRegression(0);
        assertEquals(expResult, result[0] , 1e-3);
        
        
    }  
    
    public  double[][] converToKandPa(double[][] expe){
        double[][] result = new double[expe.length][expe[0].length];
        int i =0;
        for(double[] pair: expe){
            double[] newPair ={pair[0]+273.15, pair[1]*1000};
            
            result[i++] = newPair;
            
        }
        
        return result;
    }
    
    
     @Test
    public void testPRMathiasCopeman(){
        
        Component ethanol = new Component("ethanol");
        //ethanol.setName("ethanol");
        ethanol.setAcentricFactor(0.64439);
        ethanol.setCriticalTemperature(513.92);
        ethanol.setCriticalPressure(60.676*101325);
        ethanol.setA_Mathias_Copeman(0);
        ethanol.setB_Mathias_Copeman(0);
        ethanol.setC_Mathias_Copeman(0);
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getMathiasAndCopemanExpression();
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, ethanol);
      
        
        AlphaOptimization op = new AlphaOptimization(substance,experimental);
        
        
        
        double[] expResult = {0,0,0};
        
        double[] result = op.solveVapoPressureRegression( 0,0,0);
        
        assertEquals(expResult, result );
        
        
    }  

    
    

    
    
    
    
}
