package termo.componentsForTests;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class ComponentsForTests {

    public static Component getMethanol (){
        Component methanol = new Component();
        methanol.setName("Methanol");
        methanol.setCasNumber("67-56-1");
        
        methanol.setCriticalPressure(79.94);
        methanol.setCriticalTemperature(512.6);
        methanol.setAcentricFactor(0.572);
        methanol.setPrsvKappa(0.3938);
        
        return methanol;
    }
    public static Component getWater(){
        Component water = new Component();
        water.setName("Water");
        water.setCasNumber("7732-18-5");
        
        water.setCriticalPressure(221.2);
        water.setCriticalTemperature( 647.13);
        water.setAcentricFactor(0.344);
        water.setPrsvKappa(-0.0767);
 
        return water;
    }
}
