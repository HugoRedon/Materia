package termo.eos;

import termo.Constants;
import termo.component.Component;


/**
 *
 * @author Hugo Redon Rivera
 */
public final class VanDerWaals extends Cubic{
   
    protected VanDerWaals(){
        setName(EOSNames.VanDerWaals);
        set_u(0);
        set_w(0);
    }

//    @Override
//    protected void parametersOneComponent(double aTemperature, Component aComponent) {
//        set_a( single_a(aTemperature, aComponent ));
//        set_b( single_b( aComponent));
//    }

    @Override
    protected double single_a(double aTemperature, Component aComponent) {
        double tc = aComponent.getCriticalTemperature();
        double pc = aComponent.getCriticalPressure();  
        return (27d/64d)*Math.pow(Constants.R * tc,2) / pc;
    }

    @Override
    protected double single_b( Component aComponent) {
        double tc = aComponent.getCriticalTemperature();
        double pc = aComponent.getCriticalPressure();  
        return (1d/8d) * (Constants.R * tc) / pc;
    }

   

 

}

