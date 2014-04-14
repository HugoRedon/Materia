package termo.substance;

import java.beans.PropertyChangeSupport;
import termo.matter.Substance;
import termo.matter.Mixture;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.cp.DIPPR_107_Equation;
import termo.eos.Cubic;
import termo.eos.EquationOfStateFactory;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaFactory;
import termo.eos.mixingRule.MixingRule;
import termo.eos.mixingRule.VDWMixingRule;
import termo.phase.Phase;

/**
 *
 * @author
 * Hugo
 */
public class MixtureSubstanceTest {
    Mixture substance ;
    Substance ethanolPure ;
    Component acetaldehyd;
    Component formaldehyde;
    ArrayList<Component> components = new ArrayList();
    public MixtureSubstanceTest() {
//	substance = new MixtureSubstance();
	
        Component ethanol = new Component("etanol");
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
        
        
         acetaldehyd = new Component("Acetaldehído");
        //acetaldehyd.setName();
        acetaldehyd.setCriticalPressure(5.57e6);
        acetaldehyd.setCriticalTemperature(466);
        acetaldehyd.setAcentricFactor(0.262493);
        
        acetaldehyd.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-1.71e8);
        acetaldehyd.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(2.6384e5);
        
        acetaldehyd.setA_dippr107Cp(4.8251E+04);
        acetaldehyd.setB_dippr107Cp(1.0665E+05);
        acetaldehyd.setC_dippr107Cp(1.9929E+03);
        acetaldehyd.setD_dippr107Cp(7.8851E+04);
        acetaldehyd.setE_dippr107Cp(9.1278E+02);
        
        acetaldehyd.setCp(new DIPPR_107_Equation(acetaldehyd));
        
         formaldehyde = new Component("Formaldehído");
        //formaldehyde.setName();
        formaldehyde.setCriticalTemperature(420);
        formaldehyde.setCriticalPressure(6.59000E+06);
        formaldehyde.setAcentricFactor(0.167887);
        
        formaldehyde.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-1.38600E+08);
        formaldehyde.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(2.49000E+05);
        
        formaldehyde.setA_dippr107Cp(3.3503E+04);
        formaldehyde.setB_dippr107Cp(4.9394E+04);
        formaldehyde.setC_dippr107Cp(1.9280E+03);
        formaldehyde.setD_dippr107Cp(2.9728E+04);
        formaldehyde.setE_dippr107Cp(9.6504E+02);
        
        formaldehyde.setCp(new DIPPR_107_Equation(formaldehyde));
        
        
        Component ammonia = new Component("Amoníaco");
        //ammonia.setName();
        ammonia.setCriticalTemperature(405.65);
        ammonia.setCriticalPressure(1.12800E+07);
        ammonia.setAcentricFactor(0.252608);
        
        ammonia.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-4.58980E+07);
        ammonia.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(1.92660E+05);
        
        ammonia.setA_dippr107Cp(3.3427E+04);
        ammonia.setB_dippr107Cp(4.8980E+04);
        ammonia.setC_dippr107Cp(2.0360E+03);
        ammonia.setD_dippr107Cp(2.2560E+04);
        ammonia.setE_dippr107Cp(8.8200E+02);
        
        ammonia.setCp(new DIPPR_107_Equation(ammonia));
        
        
        Component water = new Component("Agua");
        //water.setName();
        water.setCriticalTemperature(647.096);
        water.setCriticalPressure(2.20640E+07);
        water.setAcentricFactor(0.344861);
        
        water.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(-2.41818E+08);
        water.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(1.88825E+05);
        
        water.setA_dippr107Cp(3.3363E+04);
        water.setB_dippr107Cp(2.6790E+04);
        water.setC_dippr107Cp(2.6105E+03);
        water.setD_dippr107Cp(8.8960E+03);
        water.setE_dippr107Cp(1.1690E+03);
        
        water.setCp(new DIPPR_107_Equation(water));
        
        components.add(ethanol);
        components.add(acetaldehyd);
        components.add(formaldehyde);
        components.add(water);
        components.add(ammonia);
	
	Cubic eos = EquationOfStateFactory.pengRobinsonBase();
	Alpha alpha = AlphaFactory.getStryjekAndVeraExpression();
	
	
	
                
	
	
	
	
	 ethanolPure = new Substance();
	//propanePure.setCubicEquationOfState(eos);
	ethanolPure.setAlpha(alpha);
	ethanolPure.setComponent(ethanol);
	
	substance = new Mixture();
        
        substance.setComponents(components);
	
//	
//	substance.addComponent(ethanolPure, 0.7);
//	substance.addComponent(ethanePure, 0.3);
        substance.setFraction(ethanol, 0.7);
        substance.setFraction(ammonia, 0.3);
	substance.setCubicEquationOfState(eos);
	substance.setAlpha(alpha);
        substance.setPhase(Phase.VAPOR);
	InteractionParameter b = new InteractionParameter();
	//b.setValue(propane, ethane, 0);
	MixingRule mr = new VDWMixingRule();
//	
//	
	substance.setBinaryParameters(b);
	substance.setCubicEquationOfState(eos);
	substance.setMixingRule(mr);
    }
    
    @Test 
    public void testChangeAlphaAndMolarFractionsStillWork(){
        System.out.println("change alpha");
        substance.setAlpha(AlphaFactory.getAdachiAndLu());
        System.out.println("propane pure alpha should be adachi" + ethanolPure.getAlpha());
        
        double fraction = substance.getFraction(ethanolPure);
        assertEquals(fraction, 0.7,0.000001);
    }
    @Test 
    public void testChangeComponentsAndPropertiesWork(){
        System.out.println("change components");
        PropertyChangeSupport mpcs = new PropertyChangeSupport(this);
        mpcs.addPropertyChangeListener(substance);
        
        substance.setTemperature(298);
        substance.setPressure(101325);
        
        double entropy = substance.calculateEntropy();
        System.out.println("entropy"  + entropy);
        
        ArrayList<Component> differentList = new ArrayList();
        differentList.add(acetaldehyd);
        differentList.add(formaldehyde);
        mpcs.firePropertyChange("components", components, differentList);
        entropy = substance.calculateEntropy();
        System.out.println("entropy after change" + entropy);
        
        assertEquals(false, Double.isNaN(entropy));
    }

}