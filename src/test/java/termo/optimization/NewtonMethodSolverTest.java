    package termo.optimization;

import java.util.ArrayList;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import termo.component.Compound;
import termo.cp.DIPPR_107_Equation;
import termo.data.ExperimentalData;
import termo.eos.Cubic;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;
import termo.matter.HeterogeneousSubstance;
import termo.optimization.errorfunctions.VaporPressureErrorFunction;

/**
 *
 * @author Hugo
 */
public class NewtonMethodSolverTest {
    Compound component = new Compound("Acetaldehído");
    
     private ArrayList<ExperimentalData> list;
     
    public NewtonMethodSolverTest() {
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
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getMathiasAndCopemanExpression();//tres parametros
        
        alpha.setParameter(50, component,0);
        alpha.setParameter(20, component,1);
        alpha.setParameter(1, component,2);
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        
        double a = alpha.getParameter(component,0);
        assertEquals(false, Double.isNaN(a));
        assertEquals(true,substance.getErrorFunction().getOptimizer().isIndeter());
    }
    
    @Test public void testsNoPersistentDerivativeIncrementInA(){
        System.out.println("testsNoPersistentDerivativeIncrement");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getAndroulakisEtAl();//tres parametros
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        double[] args ={0,0,0};
        
        
        
        substance.getErrorFunction().getOptimizer().centralDerivative(args,0);//no importa el valor
        double a = alpha.getParameter(component,0);
        assertEquals(0, a,1e-8);
        
    }
//    
//    @Test public void testsNoPersistentDerivativeIncrementInB(){
//        System.out.println("testsNoPersistentDerivativeIncrement");
//        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getAndroulakisEtAl();//tres parametros
//        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
//        double[] args ={0,0,0};
//        
//        substance.getErrorFunction.getOptimizer().centralDerivativeB(args);//no importa el valor
//        double b = alpha.getAlphaParameterB(component);
//        assertEquals(0, b,1e-6);
//        
//    }
//    
//    
//    @Test public void testsNoPersistentDerivativeIncrementInC(){
//        System.out.println("testsNoPersistentDerivativeIncrement");
//        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getAndroulakisEtAl();//tres parametros
//        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
//        double[] args ={0,0,0};
//        
//        substance.getErrorFunction.getOptimizer().centralDerivativeC(args);//no importa el valor
//        double c = alpha.getAlphaParameterC(component);
//        assertEquals(0, c,1e-6);
//        
//    }
//    
    
    
     @Test
    public void testPRSV(){
        System.out.println("testPRSV");
        Compound ethanol = new Compound("ethanol");
        //ethanol.setName("ethanol");
        ethanol.setAcentricFactor(0.64439);
        ethanol.setCriticalTemperature(513.92);
        ethanol.setCriticalPressure(60.676*101325);
        ethanol.setK_StryjekAndVera(0.9);
        
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getStryjekAndVeraExpression();
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, ethanol);
        
       
         
        
         VaporPressureErrorFunction errorFunction = new VaporPressureErrorFunction(substance);
         errorFunction.setExperimental(list);
        NewtonMethodSolver op = new NewtonMethodSolver(errorFunction);
        
       
        
        double expResult = -0.03408846732973704;
        //double expResult = 0.009988;
        double[] initialValues = {0};
        double[] result = op.solveVapoPressureRegression(initialValues);
        assertEquals(expResult, result[0] , 1e-2);
        
        
    }  
    
    

    @Test
    public void test3VariablesOptimization() {
        System.out.println("test3VariablesOptimization");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getAndroulakisEtAl();
        
        Compound ethanol = new Compound("etanol");
       // ethanol.setName("Etanol");
        ethanol.setCriticalTemperature(514);
        ethanol.setCriticalPressure(6.13700E+06);
        ethanol.setAcentricFactor(0.643558);
        
        ethanol.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-2.34950E+08);
        ethanol.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(2.80640E+05);
        
        ethanol.setA_dippr107Cp(4.9200E+04);
        ethanol.setB_dippr107Cp(1.4577E+05);
        ethanol.setC_dippr107Cp(1.6628E+03);
        ethanol.setD_dippr107Cp(9.3900E+04);
        ethanol.setE_dippr107Cp(7.4470E+02);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, ethanol);
        substance.optimizeTo(list);
        
        printHistory(substance);
       
        
//        assertEquals(-7.098408047518564,component.getA_AndroulakisEtAl(),1e-4);
        
        boolean isIndeter = substance.getErrorFunction().getOptimizer().isIndeter();
        boolean isMaxReached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        assertEquals(false,  (isIndeter || isMaxReached) );
        //compila se ejecuta y no entra en un loop infinito
    }
    

    

    
    @Test public void test2VariableOptimization(){
        System.out.println("test2VariableOptimization");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getSoave2Parameters();
        component.setA_Soave(1);
        component.setB_Soave(0.3);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
//        substance.getErrorFunction.getOptimizer().setDamp(0.1);
        substance.optimizeTo(list);
        System.out.println("pengrobinson soave 2 ");
        System.out.println("parameter b debe ser parecido a -0.8443033101544569 :" + component.getB_Soave());//-2.405345971823838
        System.out.println("parametro a debe ser parecido a 3.7271215535951887:"  + component.getA_Soave() );
        
       // assertEquals(3.7271215535951887, component.getA_Soave(),1e-4);
        boolean indeter = substance.getErrorFunction().getOptimizer().isIndeter();
        boolean maxREached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        assertEquals(false, (indeter|| maxREached));
    }
    
    @Test
    public void fixParameterB(){
        System.out.println("fixParameterB");
         Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getSoave2Parameters();
  
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        
        
        substance.getErrorFunction().getOptimizer().getFixParameters()[1] = true;
                //setFixParameterB(true);
        component.setB_Soave(-2);
        
        substance.optimizeTo(list);
        
        System.out.println("Parameter a " + component.getA_Soave());
        assertEquals(-2, component.getB_Soave(),1e-4);
    }
    
    @Test 
    public void fixParameter(){
        System.out.println("fixParameter");
         Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getSoave2Parameters();
        component.setA_Soave(3);
        component.setB_Soave(0.3);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
//        substance.getErrorFunction().getOptimizer().setDamp(0.1);
        substance.getErrorFunction().getOptimizer().getFixParameters()[0] = true;
                //setFixParameterA(true);
        
        substance.optimizeTo(list);
        System.out.println("pengrobinson soave 2 fijando a--------");
        System.out.println("parameter b debe ser parecido a -0.8443033101544569 :" + component.getB_Soave());//-2.405345971823838
        System.out.println("parametro a debe ser exactamente a 3:"  + component.getA_Soave() );
        System.out.println("----------");
        assertEquals(3, component.getA_Soave(),1e-4);
    }
    
     @Test 
    public void fixParameterB_in3VariableOptimization(){
        System.out.println("fixParameter");
         Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getTwuExpression();
        alpha.setParameter(0.5, component, 0);
        alpha.setParameter(0.4, component, 1);
        alpha.setParameter(0.3, component, 2);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
//        substance.getErrorFunction().getOptimizer().setDamp(0.1);
        substance.getErrorFunction().getOptimizer().getFixParameters()[1] = true;
                //setFixParameterA(true);
        
        substance.optimizeTo(list);
        
        System.out.println("parameter 0: " + alpha.getParameter(component, 0));
        System.out.println("parameter 1: " + alpha.getParameter(component, 1));
        System.out.println("parameter 2: " + alpha.getParameter(component, 2));
         System.out.println(substance.getErrorFunction().getOptimizer().getMessage());
        assertEquals(true, !substance.getErrorFunction().getOptimizer().isIndeter() && !substance.getErrorFunction().getOptimizer().isMaxIterationsReached());
    }
    
    
    
    
    @Test
    public void test1VariableOptimization() {
        System.out.println("test1VariableOptimization");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getStryjekAndVeraExpression();
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
       assertEquals(2.715531696763059, component.getK_StryjekAndVera(),0.01);
        //compila se ejecuta y no entra en un loop infinito
    }
    
    @Test
    public void testConstraintInParameterA(){
        System.out.println("parameterAConstraint");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getStryjekAndVeraExpression();
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        
        
        substance.optimizeTo(list);
        
        int iterationsWithoutConstraint = substance.getErrorFunction().getOptimizer().getIterations();
        
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() + " A: " + err.getParameters()[0]);
            
        }
        
        System.out.println("iterationsWithoutConstraint:" +iterationsWithoutConstraint);
        System.out.println("Result A: " + alpha.getParameter(component,0));
        
        substance.getErrorFunction().getOptimizer().getConstrainParameters()[0]=true;
        substance.getErrorFunction().getOptimizer().getMaxVariationParameters()[0]=0.1;
        
//        substance.getErrorFunction().getOptimizer().setConstrainParameterA(true);
//        substance.getErrorFunction().getOptimizer().setParameterAMaxVariation(0.1);
        alpha.setParameter(0, component,0);
        substance.optimizeTo(list);
        int iterationsWithConstraint = substance.getErrorFunction().getOptimizer().getIterations();
        
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() + " A: " + err.getParameters()[0]);
            
        }
        
        System.out.println("iterationsWithConstraint:"+iterationsWithConstraint);
        
        System.out.println("Result A: " + alpha.getParameter(component,0));
        
        
        assertEquals(true, iterationsWithConstraint>iterationsWithoutConstraint);
        
    }
    
    @Test public void testConstraintInparameterBFor2VariableOptimization(){
        System.out.println("testConstraintInparameterBFor2VariableOptimization");
        Cubic eos = EquationsOfState.pengRobinson();
          Alpha alpha = Alphas.getSoave2Parameters();
        component.setA_Soave(1);
        component.setB_Soave(0.3);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, component);
        substance.optimizeTo(list);
        
        
         for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1]);
            
        }
         int iterations = substance.getErrorFunction().getOptimizer().getIterations();
        System.out.println("iterationsWithoutConstraint:"+iterations);
        
        
        
        
        
        substance.getErrorFunction().getOptimizer().getConstrainParameters()[1]=true;
        substance.getErrorFunction().getOptimizer().getMaxVariationParameters()[1]=0.05;
        
//        substance.getErrorFunction().getOptimizer().setConstrainParameterB(true);
//        substance.getErrorFunction().getOptimizer().setParameterBMaxVariation(0.05);
        
        alpha.setParameter(1, component,0);
        alpha.setParameter(0.3, component,1);
        substance.optimizeTo(list);
        
        int iterationsWithconstraint = substance.getErrorFunction().getOptimizer().getIterations();
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1]);
            
        }
         
        System.out.println("iterationsWithConstraint:"+iterationsWithconstraint);
        assertEquals(true, iterationsWithconstraint >iterations);//resultados diferentes
        
        
    }
    
    
    
    @Test
    public void testConstraintInparameterB_CFor3VariableOptimization() {
        System.out.println("testConstraintInparameterB_CFor3VariableOptimization");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getAndroulakisEtAl();
        
        
        Compound ethanol = new Compound("etanol");
       // ethanol.setName("Etanol");
        ethanol.setCriticalTemperature(514);
        ethanol.setCriticalPressure(6.13700E+06);
        ethanol.setAcentricFactor(0.643558);
        
        ethanol.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-2.34950E+08);
        ethanol.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(2.80640E+05);
        
        ethanol.setA_dippr107Cp(4.9200E+04);
        ethanol.setB_dippr107Cp(1.4577E+05);
        ethanol.setC_dippr107Cp(1.6628E+03);
        ethanol.setD_dippr107Cp(9.3900E+04);
        ethanol.setE_dippr107Cp(7.4470E+02);
        
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, ethanol);
        substance.optimizeTo(list);
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1] +
                    " C: " + err.getParameters()[2]);
            
        }
        
        boolean isIndeter = substance.getErrorFunction().getOptimizer().isIndeter();
        boolean isMaxReached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        if((isIndeter || isMaxReached)){
            Assert.fail("optimización con errores " + substance.getErrorFunction().getOptimizer().getMessage());
        }
        
        
        int iterationsWithNoRestriction = substance.getErrorFunction().getOptimizer().getIterations();
        
        
        substance.getErrorFunction().getOptimizer().getConstrainParameters()[1]=true;
        substance.getErrorFunction().getOptimizer().getMaxVariationParameters()[1]=1;
        substance.getErrorFunction().getOptimizer().getConstrainParameters()[2]=true;
        substance.getErrorFunction().getOptimizer().getMaxVariationParameters()[2]=1;
        
//        substance.getErrorFunction().getOptimizer().setConstrainParameterB(true);
//        substance.getErrorFunction().getOptimizer().setParameterBMaxVariation(1);
//        substance.getErrorFunction().getOptimizer().setConstrainParameterC(true);
//        substance.getErrorFunction().getOptimizer().setParameterCMaxVariation(1);
        
        alpha.setParameter(0, ethanol,0);
        alpha.setParameter(0, ethanol,1);
        alpha.setParameter(0, ethanol,2);
        
        substance.optimizeTo(list);
        
         for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1] +
                    " C: " + err.getParameters()[2]);
            
        }
        
        isIndeter = substance.getErrorFunction().getOptimizer().isIndeter();
        isMaxReached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        if((isIndeter || isMaxReached)){
            Assert.fail("optimización con errores " + substance.getErrorFunction().getOptimizer().getMessage());
        }
        
        int iterationsWithRestriction = substance.getErrorFunction().getOptimizer().getIterations();
        
        
        
        
       
        
        assertEquals(true, iterationsWithRestriction > iterationsWithNoRestriction);
        

        

    }
    
    
    
    
    
    
     @Test
    public void testErrorDecreaseTechnique() {
        System.out.println("testErrorDecreaseTechnique");
        Cubic eos = EquationsOfState.pengRobinson();
        Alpha alpha = Alphas.getAndroulakisEtAl();
        
        
        Compound ethanol = new Compound("etanol");
       // ethanol.setName("Etanol");
        ethanol.setCriticalTemperature(514);
        ethanol.setCriticalPressure(6.13700E+06);
        ethanol.setAcentricFactor(0.643558);
        

        alpha.setParameter(30, ethanol,0);
        
        HeterogeneousSubstance substance = new HeterogeneousSubstance(eos, alpha, ethanol);
        substance.optimizeTo(list);
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1] +
                    " C: " + err.getParameters()[2]);
            
        }
        
        boolean isIndeter = substance.getErrorFunction().getOptimizer().isIndeter();
        boolean isMaxReached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        if((isIndeter || isMaxReached)){
            Assert.fail("optimización con errores " + substance.getErrorFunction().getOptimizer().getMessage());
        }
        
        
        int iterationsWithNoRestriction = substance.getErrorFunction().getOptimizer().getIterations();
        
        
        substance.getErrorFunction().getOptimizer().setApplyErrorDecreaseTechnique(true);
        
        alpha.setParameter(30, ethanol,0);
        alpha.setParameter(0, ethanol,1);
        alpha.setParameter(0, ethanol,2);
        
        substance.optimizeTo(list);
        
         for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            System.out.println("iteración:" + err.getIteration() +
                    " A: " + err.getParameters()[0] + " B: " + err.getParameters()[1] +
                    " C: " + err.getParameters()[2]);
            
        }
        
        isIndeter = substance.getErrorFunction().getOptimizer().isIndeter();
        isMaxReached = substance.getErrorFunction().getOptimizer().isMaxIterationsReached();
        if((isIndeter || isMaxReached)){
            Assert.fail("optimización con errores " + substance.getErrorFunction().getOptimizer().getMessage());
        }
        
        int iterationsWithDecreaseTechnique = substance.getErrorFunction().getOptimizer().getIterations();
        
        
        
        
       if(substance.getErrorFunction().getOptimizer().getErrorDecreaseIterations() ==0){
           fail("no se realizó la tecnica de disminucion de error");
       }
         
        
        assertEquals(true, iterationsWithDecreaseTechnique < iterationsWithNoRestriction);
        

        

    }
    
    
    
    
    
    
    
    
    
    private void printHistory(HeterogeneousSubstance substance){
        for(Parameters_Error err: substance.getErrorFunction().getOptimizer().getConvergenceHistory()){
            StringBuffer sb = new StringBuffer("iteración:" + err.getIteration() + "(");
            for(double param: err.getParameters()){
                sb.append(param + " ");
            }
            sb.append(")");
            
            System.out.println(sb);
            
        }
        System.out.println(substance.getErrorFunction().getOptimizer().getMessage());
    }
    
}
