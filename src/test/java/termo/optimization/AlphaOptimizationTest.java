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
import termo.matter.HeterogeneousSubstance;

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
    
    
    @Test public void testIndetermination(){
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getMathiasAndCopemanExpression();//tres parametros
        
        alpha.setAlphaParameterA(50, component);
        alpha.setAlphaParameterB(20, component);
        alpha.setAlphaParameterC(1, component);
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        
        double a = alpha.getAlphaParameterA(component);
        assertEquals(false, Double.isNaN(a));
        assertEquals(true,substance.getAlphaOptimizer().isIsIndeter());
    }
    
    @Test public void testsNoPersistentDerivativeIncrementInA(){
        System.out.println("testsNoPersistentDerivativeIncrement");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();//tres parametros
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        double[] args ={0,0,0};
        
        substance.getAlphaOptimizer().derivative_A(args);//no importa el valor
        double a = alpha.getAlphaParameterA(component);
        assertEquals(0, a,1e-6);
        
    }
    
    @Test public void testsNoPersistentDerivativeIncrementInB(){
        System.out.println("testsNoPersistentDerivativeIncrement");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();//tres parametros
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        double[] args ={0,0,0};
        
        substance.getAlphaOptimizer().derivative_B(args);//no importa el valor
        double b = alpha.getAlphaParameterB(component);
        assertEquals(0, b,1e-6);
        
    }
    
    
    @Test public void testsNoPersistentDerivativeIncrementInC(){
        System.out.println("testsNoPersistentDerivativeIncrement");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();//tres parametros
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        double[] args ={0,0,0};
        
        substance.getAlphaOptimizer().derivative_C(args);//no importa el valor
        double c = alpha.getAlphaParameterC(component);
        assertEquals(0, c,1e-6);
        
    }
    
    
    
     @Test
    public void testPRSV(){
        System.out.println("testPRSV");
        Component ethanol = new Component("ethanol");
        //ethanol.setName("ethanol");
        ethanol.setAcentricFactor(0.64439);
        ethanol.setCriticalTemperature(513.92);
        ethanol.setCriticalPressure(60.676*101325);
        ethanol.setK_StryjekAndVera(0.9);
        
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, ethanol);
        
       
         
        
      
        AlphaOptimization op = new AlphaOptimization(substance,list);
        
       
        
        double expResult = -0.03408846732973704;
        //double expResult = 0.009988;
        double[] result = op.solveVapoPressureRegression(0);
        assertEquals(expResult, result[0] , 1e-3);
        
        
    }  
    
    

    @Test
    public void test3VariablesOptimization() {
        System.out.println("test3VariablesOptimization");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        

        assertEquals(-7.098408047518564,component.getA_AndroulakisEtAl(),1e-4);
        //compila se ejecuta y no entra en un loop infinito
    }
    
    @Test public void deltaonc(){
        System.out.println("deltaonc");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getAndroulakisEtAl();
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);

        
        double result = substance.getAlphaOptimizer().applyDeltaOnC(0,0,0)[2];
        double exp = 1e-4;
        
        assertEquals(exp, result,1e-6);
        
        
    }
    

    
    @Test public void test2VariableOptimization(){
        System.out.println("test2VariableOptimization");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getSoave2Parameters();
        component.setA_Soave(1);
        component.setB_Soave(0.3);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.getAlphaOptimizer().setDamp(0.1);
        substance.optimizeTo(list);
        System.out.println("pengrobinson soave 2 ");
        System.out.println("parameter b debe ser parecido a -0.8443033101544569 :" + component.getB_Soave());//-2.405345971823838
        System.out.println("parametro a debe ser parecido a 3.7271215535951887:"  + component.getA_Soave() );
        
        assertEquals(3.7271215535951887, component.getA_Soave(),1e-4);
    }
    
    @Test
    public void fixParameterB(){
        System.out.println("fixParameterB");
         Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getSoave2Parameters();
  
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        
        
        substance.getAlphaOptimizer().setFixParameterB(true);
        component.setB_Soave(-2);
        
        substance.optimizeTo(list);
        
        System.out.println("Parameter a " + component.getA_Soave());
        assertEquals(-2, component.getB_Soave(),1e-4);
    }
    
    @Test 
    public void fixParameter(){
        System.out.println("fixParameter");
         Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getSoave2Parameters();
        component.setA_Soave(3);
        component.setB_Soave(0.3);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.getAlphaOptimizer().setDamp(0.1);
        substance.getAlphaOptimizer().setFixParameterA(true);
        
        substance.optimizeTo(list);
        System.out.println("pengrobinson soave 2 fijando a--------");
        System.out.println("parameter b debe ser parecido a -0.8443033101544569 :" + component.getB_Soave());//-2.405345971823838
        System.out.println("parametro a debe ser exactamente a 3:"  + component.getA_Soave() );
        System.out.println("----------");
        assertEquals(3, component.getA_Soave(),1e-4);
    }
    
    
    
    @Test
    public void test1VariableOptimization() {
        System.out.println("test1VariableOptimization");
        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
       // assertEquals(2.715531696763059, component.getK_StryjekAndVera(),1e-4);
        //compila se ejecuta y no entra en un loop infinito
    }
    
}
