package termo.componentsForTests;

import termo.component.Compound;

/**
 *
 * @author Hugo Redon Rivera
 */
public class ComponentsForTests {

    public static Compound getMethanol (){
        Compound methanol = new Compound("Methanol");
//        methanol.setName();
        methanol.setCasNumber("67-56-1");
        
        //data from dippr
        methanol.setCriticalPressure(8.08400e6);
        methanol.setCriticalTemperature(512.5);
        methanol.setAcentricFactor(0.565831);
        
        //data from hysys
        methanol.setK_StryjekAndVera(0.39379);
        
        return methanol;
    }
    public static Compound getWater(){
        Compound water = new Compound("Water");
       // water.setName("Water");
        water.setCasNumber("7732-18-5");
        
        ///data from dippr
        water.setCriticalPressure(2.2064e7);
        water.setCriticalTemperature( 647.096);
        water.setAcentricFactor(0.344861);
        
        //data from hysys
        water.setK_StryjekAndVera(-0.0767);
 
        return water;
    }
}
