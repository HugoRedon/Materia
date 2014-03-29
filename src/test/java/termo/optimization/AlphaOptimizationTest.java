package termo.optimization;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import termo.component.Component;
import termo.cp.DIPPR_107_Equation;
import termo.data.ExperimentalData;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.substance.HeterogeneousPureSubstance;

/**
 *
 * @author Hugo
 */
public class AlphaOptimizationTest {
    Component component = new Component("Acetaldehído");
    
     private ArrayList<ExperimentalData> list;
    
    public AlphaOptimizationTest() {
        list = new ArrayList<ExperimentalData>();
        
        double[][] experimental = {//temperature[C], pressure[kPa]
                                {93.48+ 273.15,179.321*1000},
                                {82.36+ 273.15,118.719*1000},
                                {74.98+ 273.15,88.763*1000},
                                {54.66+ 273.15,36.76*1000},
                                {36.61+ 273.15,14.981*1000},
                                {19.62+ 273.15,5.726*1000}
                            };
        
        for (double[] pair: experimental){
            list.add(new ExperimentalData(pair[0],pair[1]));
        }
        
        
       // component.setName();
        component.setCriticalPressure(5.57e6);
        component.setCriticalTemperature(466);
        component.setAcentricFactor(0.262493);
        
        component.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-1.71e8);
        component.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(2.6384e5);
        
        component.setA_dippr107Cp(4.8251E+04);
        component.setB_dippr107Cp(1.0665E+05);
        component.setC_dippr107Cp(1.9929E+03);
        component.setD_dippr107Cp(7.8851E+04);
        component.setE_dippr107Cp(9.1278E+02);
        
        component.setCp(new DIPPR_107_Equation(component));
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
        
       
         
        
      
        AlphaOptimization op = new AlphaOptimization(substance,list);
        
       
        
        double expResult = -0.03408846732973704;
        //double expResult = 0.009988;
        double[] result = op.solveVapoPressureRegression(0);
        assertEquals(expResult, result[0] , 1e-3);
        
        
    }  
    
    

    @Test
    public void test3VariablesOptimization() {
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();
        
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, component);
        substance.optimizeTo(list);

        //assertEquals(5.559239095754101,component.getA_AndroulakisEtAl(),1e-4);
        //compila se ejecuta y no entra en un loop infinito
    }
    
    @Test public void test2VariableOptimization(){
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getSoave2Parameters();
        
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        //assertEquals(2.715531696763059/*este no es el valor*/, component.getA_Soave(),1e-4);
    }
    @Test
    public void test1VariableOptimization() {
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, component);
        substance.optimizeTo(list);
       // assertEquals(2.715531696763059, component.getK_StryjekAndVera(),1e-4);
        //compila se ejecuta y no entra en un loop infinito
    }
    
}
