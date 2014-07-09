
package termo.optimization;

import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import termo.activityModel.NRTLActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.componentsForTests.ComponentsForTests;
import termo.data.ExperimentalDataBinary;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.eos.mixingRule.WongSandlerMixingRule;
import termo.matter.HeterogeneousMixture;
import termo.optimization.errorfunctions.TemperatureErrorFunction;

/**
 *
 * @author Hugo
 */
public class InteractionParameterOptimizerTest {
    Compound water;
    Compound methanol;
    HeterogeneousMixture mixture; 
    
    ArrayList<ExperimentalDataBinary> experimental = new ArrayList<>();
    public InteractionParameterOptimizerTest() {
        water = ComponentsForTests.getWater();
        methanol = ComponentsForTests.getMethanol();

        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        HashSet<Compound> components = new HashSet<>();
        components.add(water);
        components.add(methanol);
        MixingRule mr = new VDWMixingRule();
        
        InteractionParameter parameters = new InteractionParameter();
        
        
        
        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
        
        
         
        
        double pressure = 0.14991*101325;
       

        experimental.add(new ExperimentalDataBinary( 327.4, pressure, 0, 0));
        experimental.add(new ExperimentalDataBinary( 318.05, pressure, 0.1, 0.433386));
        experimental.add(new ExperimentalDataBinary( 312.4, pressure, 0.2, 0.62119));
        experimental.add(new ExperimentalDataBinary( 308.55, pressure, 0.3, 0.725289));
        experimental.add(new ExperimentalDataBinary( 305.7, pressure, 0.4, 0.79242));     
        experimental.add(new ExperimentalDataBinary( 303.5, pressure, 0.5, 0.840656));
        experimental.add(new ExperimentalDataBinary( 301.7, pressure, 0.6, 0.87857));
        experimental.add(new ExperimentalDataBinary( 300.1, pressure, 0.7, 0.910835));
        experimental.add(new ExperimentalDataBinary( 298.7, pressure, 0.8, 0.940365));
        experimental.add(new ExperimentalDataBinary( 297.4, pressure, 0.9, 0.969404));
        experimental.add(new ExperimentalDataBinary( 296.1, pressure, 1, 1));
      
    }

    @Test
    public void testSolve() {
        mixture.getInteractionParameters().setSymmetric(true);
        mixture.getErrorfunction().setExperimental(experimental);
        mixture.getErrorfunction().setReferenceComponent(methanol);
        mixture.getErrorfunction().setNonReferenceComponent(water);
        mixture.getErrorfunction().minimize();
                
        double result = mixture.getInteractionParameters().getValue(methanol, water);
        
        double expected = -0.050392324220228706;
        assertEquals(expected,result,1e-4);
          
    }
    
        @Test
    public void testSolveInteractionParameterInVaporAndLiquid() {
        mixture.getInteractionParameters().setSymmetric(true);
        mixture.getErrorfunction().setExperimental(experimental);
        mixture.getErrorfunction().setReferenceComponent(methanol);
        mixture.getErrorfunction().setNonReferenceComponent(water);
        mixture.getErrorfunction().minimize();
                
        double result = mixture.getInteractionParameters().getValue(methanol, water);
        double liquidResult = mixture.getLiquid().getBinaryParameters().getValue(methanol, water);
        double vaporResult = mixture.getVapor().getBinaryParameters().getValue(methanol, water);
        
        double expected = -0.050392324220228706;
        double tol = 1e-4;
        assertEquals(true, equalValue(expected, result,tol) && equalValue(expected,liquidResult,tol) 
                && equalValue(expected, vaporResult, tol) );
        
          
    }
    
    public boolean equalValue(double expected, double value,double  tolerance){
        return Math.abs(value-expected) < tolerance;
            
    }
//    @Test public void testSolveWithNonSymetricInteractionParameter(){//optimización multivariable (2 K12 Y K21)
//         //interactionParameters default false
//        TemperatureErrorFunction errorFunction = 
//                (TemperatureErrorFunction)mixture.getOptimizer().getErrorFunction();
//        errorFunction.setExperimental(experimental);
//        mixture.getOptimizer().solve();
//        double result12 = mixture.getInteractionParameters().getValue(methanol, water);
//        double result21 = mixture.getInteractionParameters().getValue(water, methanol);
//        
//        System.out.println("12 : " + result12+", 21 : " +result21);
//        int compare = Double.compare(result12, result21);
//        assertEquals(true, compare == 0);
//    }
//    @Test public void testSolveWilson_WongSandler_Parameters(){
//         Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
//        ArrayList<Component> components = new ArrayList<>();
//        components.add(water);
//        components.add(methanol);
//        WilsonActivityModel wilson = new WilsonActivityModel();
//        WongSandlerMixingRule mr = new WongSandlerMixingRule(wilson, eos);
//        
//        
//        ActivityModelBinaryParameter parameters = new ActivityModelBinaryParameter();
//        
//        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
//        
//        
//         TemperatureErrorFunction errorFunction = 
//                (TemperatureErrorFunction)mixture.getOptimizer().getErrorFunction();
//        errorFunction.setExperimental(experimental);
//        mixture.getOptimizer().setApplyErrorDecreaseTechnique(true);
//        mixture.getOptimizer().solve();
//        
//        
//        for (int i = 0; i < mixture.getOptimizer().getErrorFunction().numberOfParameters(); i++) {
//            System.out.println("Parámetro " + i + " : " + mixture.getOptimizer().getErrorFunction().getParameter(i));
//        }
//        fail();
//        
//    }
//    
//     @Test public void testSolveNRTL_WongSandler_Parameters(){
//         Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
//        HashSet<Component> components = new HashSet<>();
//        components.add(water);
//        components.add(methanol);
//         NRTLActivityModel nrtl = new NRTLActivityModel();
//        WongSandlerMixingRule mr = new WongSandlerMixingRule(nrtl, eos);
//        
//        
//        
//        
//        ActivityModelBinaryParameter parameters = new ActivityModelBinaryParameter();
//        
//        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
//       
//        
//        mixture.getErrorfunction().setReferenceComponent(methanol);
//        mixture.getErrorfunction().setNonReferenceComponent(water);
//        mixture.getErrorfunction().setExperimental(experimental);
//        mixture.getErrorfunction().getOptimizer().setApplyErrorDecreaseTechnique(true);
//        
//        mixture.getErrorfunction().getOptimizer().getErrorFunction().setParameter(1, 0);
//        mixture.getErrorfunction().getOptimizer().getErrorFunction().setParameter(1, 1);
//        
//        
//        mixture.getErrorfunction().getOptimizer().solve();
//        
//        
//        for (int i = 0; i < mixture.getErrorfunction().getOptimizer().getErrorFunction().numberOfParameters(); i++) {
//            System.out.println("Parámetro " + i + " : " + mixture.getErrorfunction().getOptimizer().getErrorFunction().getParameter(i));
//        }
//        
//         //System.out.println("Error: "+ mixture.getOptimizer().getErrorFunction().get);
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            StringBuilder sb = new StringBuilder();
//            sb.append("------------iteración " + error.getIteration() + " ");
//            double[] params = error.getParameters();
//            for(int i =0; i< params.length; i++){
//                sb.append("(parametro "+i+" : "+ params[i] + ") ");
//            }
//            sb.append("error : "+ error.getError() + "-----------");
//            System.out.println(sb);
//        }
//        
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            System.out.println("Suma de absolutos del gradiente : " +error.getGradientAbsSum());
//        }
//        
//        fail();
//        
//    }
//     @Test public void stopWithGradientCriterion(){
//         
//        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
//        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
//        HashSet<Component> components = new HashSet<>();
//        components.add(water);
//        components.add(methanol);
//         NRTLActivityModel nrtl = new NRTLActivityModel();
//        WongSandlerMixingRule mr = new WongSandlerMixingRule(nrtl, eos);
//        
//        
//        
//        
//        ActivityModelBinaryParameter parameters = new ActivityModelBinaryParameter();
//        
//        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
//       
//        
////         TemperatureErrorFunction errorFunction = 
////                (TemperatureErrorFunction)mixture.getErrorfunction().getOptimizer().getErrorFunction();
//        mixture.getErrorfunction().setReferenceComponent(methanol);
//        mixture.getErrorfunction().setNonReferenceComponent(water);
//        mixture.getErrorfunction().setExperimental(experimental);
//        mixture.getErrorfunction().getOptimizer().setApplyErrorDecreaseTechnique(true);
//        
//        mixture.getErrorfunction().setParameter(1, 0);
//        mixture.getErrorfunction().setParameter(1, 1);
//        mixture.getErrorfunction().getOptimizer().setTolerance(1e-3);
//        
//        mixture.getErrorfunction().getOptimizer().solve();
//        
//          
//        for (int i = 0; i < mixture.getErrorfunction().getOptimizer().getErrorFunction().numberOfParameters(); i++) {
//            System.out.println("Parámetro " + i + " : " + mixture.getErrorfunction().getOptimizer().getErrorFunction().getParameter(i));
//        }
//        
//         //System.out.println("Error: "+ mixture.getOptimizer().getErrorFunction().get);
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            StringBuilder sb = new StringBuilder();
//            sb.append("------------iteración " + error.getIteration() + " ");
//            double[] params = error.getParameters();
//            for(int i =0; i< params.length; i++){
//                sb.append("(parametro "+i+" : "+ params[i] + ") ");
//            }
//            sb.append("error : "+ error.getError() + "-----------");
//            System.out.println(sb);
//        }
//        
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            System.out.println("Suma de absolutos del gradiente : " +error.getGradientAbsSum());
//        }
//         
//        
//         int iterationsWithErrorDifferenceCriterion =  mixture.getErrorfunction().getOptimizer().getIterations();
//         
//         
//        mixture.setInteractionParameters(new ActivityModelBinaryParameter()) ;
//        
//        mixture.getErrorfunction().getOptimizer().getErrorFunction().setParameter(1, 0);
//        mixture.getErrorfunction().getOptimizer().getErrorFunction().setParameter(1, 1);
//        
//        mixture.getErrorfunction().getOptimizer().setErrorDiferenceCriterion(false);
//        mixture.getErrorfunction().getOptimizer().setGradientCriterion(true);
//        mixture.getErrorfunction().getOptimizer().setGradientCriterionTolerance(1e-5);
//        mixture.getErrorfunction().getOptimizer().solve();
//        
//        
//        int iterationsWithGradientAbsSumCriterion = mixture.getErrorfunction().getOptimizer().getIterations();
//        
//         
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        for (int i = 0; i < mixture.getErrorfunction().getOptimizer().getErrorFunction().numberOfParameters(); i++) {
//            System.out.println("Parámetro " + i + " : " + mixture.getErrorfunction().getOptimizer().getErrorFunction().getParameter(i));
//        }
//        
//         //System.out.println("Error: "+ mixture.getOptimizer().getErrorFunction().get);
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            StringBuilder sb = new StringBuilder();
//            sb.append("------------iteración " + error.getIteration() + " ");
//            double[] params = error.getParameters();
//            for(int i =0; i< params.length; i++){
//                sb.append("(parametro "+i+" : "+ params[i] + ") ");
//            }
//            sb.append("error : "+ error.getError() + "-----------");
//            System.out.println(sb);
//        }
//        
//        for(Parameters_Error error:mixture.getErrorfunction().getOptimizer().getConvergenceHistory()){
//            System.out.println("Suma de absolutos del gradiente : " +error.getGradientAbsSum());
//        }
//         assertEquals(true, iterationsWithErrorDifferenceCriterion < iterationsWithGradientAbsSumCriterion);
//     }
    
}
