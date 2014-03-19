package termo.optimization;

import java.util.ArrayList;
import static org.junit.Assert.fail;
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
    Component component = new Component();
    
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
        
        
        component.setName("Acetaldehído");
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
    public void test3VariablesOptimization() {
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();
        
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        //compila se ejecuta y no entra en un loop infinito... para eso es esta prueba.
    }
    
    @Test
    public void test1VariableOptimization() {
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        
        HeterogeneousPureSubstance substance = new HeterogeneousPureSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        //compila se ejecuta y no entra en un loop infinito... para eso es esta prueba.
    }
    
}
