
package termo.optimization;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import termo.activityModel.WilsonActivityModel;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
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
    Component water;
    Component methanol;
    HeterogeneousMixture mixture; 
    
    ArrayList<ExperimentalDataBinary> experimental = new ArrayList<>();
    public InteractionParameterOptimizerTest() {
        water = ComponentsForTests.getWater();
        methanol = ComponentsForTests.getMethanol();

        Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        ArrayList<Component> components = new ArrayList<>();
        components.add(water);
        components.add(methanol);
        MixingRule mr = new VDWMixingRule();
        
        InteractionParameter parameters = new InteractionParameter();
        
        
        
        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
        
        
         
        
        double pressure = 0.14991*101325;
       

        experimental.add(new ExperimentalDataBinary( methanol, water, 327.4, pressure, 0, 0));
        experimental.add(new ExperimentalDataBinary( methanol, water,318.05, pressure, 0.1, 0.433386));
        experimental.add(new ExperimentalDataBinary( methanol, water,312.4, pressure, 0.2, 0.62119));
        experimental.add(new ExperimentalDataBinary( methanol, water,308.55, pressure, 0.3, 0.725289));
        experimental.add(new ExperimentalDataBinary( methanol, water,305.7, pressure, 0.4, 0.79242));     
        experimental.add(new ExperimentalDataBinary( methanol, water,303.5, pressure, 0.5, 0.840656));
        experimental.add(new ExperimentalDataBinary( methanol, water,301.7, pressure, 0.6, 0.87857));
        experimental.add(new ExperimentalDataBinary( methanol, water,300.1, pressure, 0.7, 0.910835));
        experimental.add(new ExperimentalDataBinary( methanol, water,298.7, pressure, 0.8, 0.940365));
        experimental.add(new ExperimentalDataBinary( methanol, water,297.4, pressure, 0.9, 0.969404));
        experimental.add(new ExperimentalDataBinary( methanol, water,296.1, pressure, 1, 1));
      
    }

    @Test
    public void testSolve() {
        mixture.getInteractionParameters().setSymmetric(true);
        TemperatureErrorFunction errorFunction = 
                (TemperatureErrorFunction)mixture.getOptimizer().getErrorFunction();
        errorFunction.setExperimental(experimental);
        mixture.getOptimizer().solve();
        double result = mixture.getInteractionParameters().getValue(methanol, water);
        
        double expected = -0.050392324220228706;
        assertEquals(expected,result,1e-4);
          
    }
    @Test public void testSolveWithNonSymetricInteractionParameter(){//optimización multivariable (2 K12 Y K21)
         //interactionParameters default false
        TemperatureErrorFunction errorFunction = 
                (TemperatureErrorFunction)mixture.getOptimizer().getErrorFunction();
        errorFunction.setExperimental(experimental);
        mixture.getOptimizer().solve();
        double result12 = mixture.getInteractionParameters().getValue(methanol, water);
        double result21 = mixture.getInteractionParameters().getValue(water, methanol);
        
        System.out.println("12 : " + result12+", 21 : " +result21);
        int compare = Double.compare(result12, result21);
        assertEquals(true, compare == 0);
    }
    @Test public void testSolveWilson_WongSandler_Parameters(){
         Cubic eos = EquationOfStateFactory.pengRobinsonBase();
        Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
        ArrayList<Component> components = new ArrayList<>();
        components.add(water);
        components.add(methanol);
        WilsonActivityModel wilson = new WilsonActivityModel();
        WongSandlerMixingRule mr = new WongSandlerMixingRule(wilson, eos);
        
        
        ActivityModelBinaryParameter parameters = new ActivityModelBinaryParameter();
        
        mixture = new HeterogeneousMixture(eos, alpha, mr, components, parameters);
        
        
         TemperatureErrorFunction errorFunction = 
                (TemperatureErrorFunction)mixture.getOptimizer().getErrorFunction();
        errorFunction.setExperimental(experimental);
        mixture.getOptimizer().setApplyErrorDecreaseTechnique(true);
        mixture.getOptimizer().solve();
        
        
        for (int i = 0; i < mixture.getOptimizer().getErrorFunction().numberOfParameters(); i++) {
            System.out.println("Parámetro " + i + " : " + mixture.getOptimizer().getErrorFunction().getParameter(i));
        }
        fail();
        
    }
    
}
